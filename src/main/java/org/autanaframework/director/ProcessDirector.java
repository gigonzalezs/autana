package org.autanaframework.director;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.autanaframework.composer.ExceptionHandler;
import org.autanaframework.composer.declarative.declarators.ExceptionHandlerDeclarator;
import org.autanaframework.composition.AbstractComposition;
import org.autanaframework.composition.ContainerComposition;
import org.autanaframework.composition.JavaStepComposition;
import org.autanaframework.composition.ProcessComposition;
import org.autanaframework.monitor.IExecutionMonitor;
import org.autanaframework.monitor.LogMonitor;
import org.autanaframework.monitor.MonitorCollection;
import org.autanaframework.monitor.SysOutMonitor;
import org.autanaframework.monitor.SysOutTraceMonitor;

public class ProcessDirector<R,T>  {
	
	private ProcessComposition<R,T> current_composition;
	private ExceptionHandlerDeclarator rootExceptionHandlerDeclarator;
	private MonitorCollection<R,T> monitor = new MonitorCollection<R,T>();
	
	public List<IExecutionMonitor<R,T>> getMonitors() {
		return monitor.getMonitors();
	}

	public ProcessDirector<R,T> composition(ProcessComposition<R,T> composition) {
		current_composition = composition;
		return this;
	}
	
	public ProcessDirector<R,T> onException(ExceptionHandlerDeclarator exceptionHandlerDeclarator) {
		rootExceptionHandlerDeclarator = exceptionHandlerDeclarator;
		return this;
    }
	
	public ProcessDirector<R,T> addCustomMonitor(IExecutionMonitor<R,T> monitor) {
		this.monitor.getMonitors().add(monitor);
		return this;
    }
	
	public ProcessDirector<R,T> addLogMonitor() {
		this.addCustomMonitor(new LogMonitor<R,T>());
		return this;
    }
	
	public ProcessDirector<R,T> addSysOutMonitor() {
		this.addCustomMonitor(new SysOutMonitor<R,T>());
		return this;
    }
	 
	public ProcessDirector<R,T> addSysOutOnlyPathsMonitor() {
		this.addCustomMonitor(new SysOutMonitor<R,T>().disablePayLoad());
		return this;
    }
	
	public ProcessDirector<R,T> addSysOutTraceMonitor() {
		this.addCustomMonitor(new SysOutTraceMonitor<R,T>());
		return this;
    }
	
	
	public T process(R request) {
		Payload<R,T> payload = new Payload<>(request);
		process(payload);	
		return payload.response;
	}
	
	public void process(Payload<R,T> payload) {
		final AtomicBoolean canContinue = new AtomicBoolean(true);
		monitor.start(payload);
		current_composition.getChildren().stream()
		.forEach(container -> {
			if (canContinue.get()) {
				canContinue.set(
						executeContainer((ContainerComposition<R, T>)container, payload));
			}
		});
		monitor.end(payload);
	}
	
	private boolean executeContainer(ContainerComposition<R, T> container, Payload<R,T> payload) {
		
		monitor.executing(container.getNodeName(), payload);
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
		monitor.success(container.getNodeName(), payload);
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
				monitor.executing(executionStep.getNodeName(), payload);
				executionStep.getSnippetFunction().execute(payload);
				monitor.success(executionStep.getNodeName(), payload);
				return true;
			} catch (DebuggerInterruptionException dbgEx) {
				return false;
			} catch (Throwable t) {
				boolean resumeable = handleExecutionException(t);
				monitor.fail(executionStep.getNodeName(), payload, resumeable);
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
