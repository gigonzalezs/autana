package org.autanaframework.demo.director;

import org.autanaframework.demo.composer.ContainerStep;
import org.autanaframework.demo.composer.ExceptionHandler;
import org.autanaframework.demo.composer.ExecutionStep;
import org.autanaframework.demo.composer.Step;
import org.autanaframework.demo.composer.declarators.ExceptionHandlerDeclarator;
import org.autanaframework.demo.composition.ContainerComposition;
import org.autanaframework.demo.composition.ProcessComposition;

public class ProcessDirector<R,T>  {
	
	private ProcessComposition<R,T> current_composition;
	private ExceptionHandlerDeclarator rootExceptionHandlerDeclarator;
	
	public ProcessDirector<R,T> composition(ProcessComposition<R,T> composition) {
		current_composition = composition;
		return this;
	}
	
	public ProcessDirector<R,T> onException(ExceptionHandlerDeclarator exceptionHandlerDeclarator) {
		rootExceptionHandlerDeclarator = exceptionHandlerDeclarator;
		return this;
    }
	
	public T process(R request) {
		Payload<R,T> payload = new Payload<>(request);
		process(payload);	
		return payload.response;
	}
	
	public void process(Payload<R,T> payload) {
		current_composition.getSequences().stream()
		.forEach(container -> {
			executeContainer(container, payload);
		});
	}
	
	private void executeStep(Step<R,T> step, Payload<R,T> payload) {
		
		if (step.getClass().isAssignableFrom(ExecutionStep.class)) {
			ExecutionStep<R,T> executionStep = (ExecutionStep<R, T>) step;
			try {
				executionStep.getStepExecutor().execute(payload);
			} catch (Throwable t) {
				handleExecutionException(t);
			}
			
		} else if (step.getClass().isAssignableFrom(ContainerStep.class)) {
			ContainerStep<R,T> containerStep = (ContainerStep<R, T>) step;
			ContainerComposition<R,T> subComposition = ContainerComposition.fromContainerComposer(containerStep.getContainer());
			executeContainer(subComposition, payload);
		}
	}
	
	private void handleExecutionException(Throwable t) {
		if (rootExceptionHandlerDeclarator != null) {
			ExceptionHandler handler = new ExceptionHandler(t);
			rootExceptionHandlerDeclarator.handle(handler);
		} else {
			throw new CompositionException(t);
		}
	}
	
	private void executeContainer(ContainerComposition<R, T> container, Payload<R,T> payload) {
		
		if (container.isConditional() == false 
				||  (container.isConditional() == true 
					&& container.getPredicate().test(payload) == true)) {
		
			if (!container.isParallel()) {
				do {
					container.getSteps().stream().sequential()
					.forEach(t -> {
						executeStep(t,payload);
					});
				} while (container.isConditional() == true 
						&& container.isLoopEnabed() == true 
						&& container.getPredicate().test(payload) == true);
			} else {
				container.getSteps().stream().parallel()
				.forEach(t -> {
					executeStep(t,payload);
				});
			}
		}
	}

}
