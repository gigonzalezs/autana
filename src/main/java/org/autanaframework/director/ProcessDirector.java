package org.autanaframework.director;

import java.util.concurrent.atomic.AtomicBoolean;

import org.autanaframework.composer.ExceptionHandler;
import org.autanaframework.composer.declarative.declarators.ExceptionHandlerDeclarator;
import org.autanaframework.composition.AbstractComposition;
import org.autanaframework.composition.ContainerComposition;
import org.autanaframework.composition.JavaStepComposition;
import org.autanaframework.composition.ProcessComposition;
import org.autanaframework.monitor.IExecutionMonitor;
import org.autanaframework.monitor.NullMonitor;

public class ProcessDirector<R,T>  {
	
	private ProcessComposition<R,T> current_composition;
	private ExceptionHandlerDeclarator rootExceptionHandlerDeclarator;
	private IExecutionMonitor<R,T> monitor = new NullMonitor<R,T>();
	
	public ProcessDirector<R,T> composition(ProcessComposition<R,T> composition) {
		current_composition = composition;
		return this;
	}
	
	public ProcessDirector<R,T> onException(ExceptionHandlerDeclarator exceptionHandlerDeclarator) {
		rootExceptionHandlerDeclarator = exceptionHandlerDeclarator;
		return this;
    }
	
	public ProcessDirector<R,T> monitor(IExecutionMonitor<R,T> monitor) {
		this.monitor = monitor;
		return this;
    }
	
	public T process(R request) {
		Payload<R,T> payload = new Payload<>(request);
		process(payload);	
		return payload.response;
	}
	
	public void process(Payload<R,T> payload) {
		monitor.executing("/", payload);
		current_composition.getChildren().stream()
		.forEach(container -> {
			executeContainer((ContainerComposition<R, T>)container, payload);
		});
		monitor.success("/", payload);
	}
	
	private boolean executeContainer(ContainerComposition<R, T> container, Payload<R,T> payload) {
		
		monitor.executing("/", payload);
		final AtomicBoolean canContinue = new AtomicBoolean(true);
		
		if (isContainerExecutable(container, payload)) {
		
			if (container.isSerial()) {
				
				do {
					container.getSteps().stream().sequential()
					.forEach(t -> {
						if (canContinue.get()) {
							canContinue.set(executeStep(t,payload));
						}
					});
				} while (canContinue.get() && isContainerStillExecutable(container, payload));
				
			} else {
				container.getSteps().stream().parallel()
				.map(t -> new AtomicBoolean(executeStep(t, payload)))
				.allMatch(AtomicBoolean::get);
			}
		}
		monitor.success("/", payload);
		return canContinue.get();
	}
	
	private boolean isContainerExecutable(ContainerComposition<R, T> container, Payload<R,T> payload) {
		return container.isConditional() == false 
				||  (container.isConditional() == true 
				&& container.getPredicate().test(payload) == true);
	}
	
	private boolean isContainerStillExecutable(ContainerComposition<R, T> container, Payload<R,T> payload) {
		return container.isConditional() == true 
				&& container.isLoopEnabed() == true 
				&& container.getPredicate().test(payload) == true;
	}
	
	private boolean executeStep(AbstractComposition<R,T> step, Payload<R,T> payload) {
		
		if (JavaStepComposition.class.isAssignableFrom(step.getClass())) {
			JavaStepComposition<R,T> executionStep = (JavaStepComposition<R, T>) step;
			try {
				monitor.executing("/", payload);
				executionStep.getSnippetFunction().execute(payload);
				monitor.success("/", payload);
				return true;
			} catch (Throwable t) {
				boolean resumeable = handleExecutionException(t);
				monitor.fail("/", payload, resumeable);
				return resumeable;
			}
			
		} else if (ContainerComposition.class.isAssignableFrom(step.getClass())) {
			ContainerComposition<R,T> containerStep = (ContainerComposition<R, T>) step;
			return executeContainer(containerStep, payload);
		} else {
			return false;
		}
	}
	
	private boolean handleExecutionException(Throwable t) {
		if (rootExceptionHandlerDeclarator != null) {
			ExceptionHandler handler = new ExceptionHandler(t);
			rootExceptionHandlerDeclarator.handle(handler);
			return handler.isResumeable();
		} else {
			throw new CompositionException(t);
		}
	}
}
