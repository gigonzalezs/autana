package org.arepoframework.demo.director;

import java.util.List;
import java.util.function.Supplier;

import org.arepoframework.demo.composer.ContainerStep;
import org.arepoframework.demo.composer.ExceptionHandler;
import org.arepoframework.demo.composer.ExecutionStep;
import org.arepoframework.demo.composer.Step;
import org.arepoframework.demo.composer.declarators.ExceptionHandlerDeclarator;
import org.arepoframework.demo.composition.ContainerComposition;
import org.arepoframework.demo.composition.ProcessComposition;

public class ProcessDirector<R,T>  {
	
	private ProcessComposition<R,T> current_composition;
	private ExceptionHandlerDeclarator rootExceptionHandlerDeclarator;
	
	public ProcessDirector<R,T> composition(ProcessComposition<R,T> composition) {
		current_composition = composition;
		return this;
	}
	
	public T process(R request) {
		Payload<R,T> payload = new Payload<>(request);
		process(payload);	
		return payload.response;
	}
	
	public void onException(ExceptionHandlerDeclarator exceptionHandlerDeclarator) {
		rootExceptionHandlerDeclarator = exceptionHandlerDeclarator;
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
				if (rootExceptionHandlerDeclarator != null) {
					ExceptionHandler handler = new ExceptionHandler(t);
					rootExceptionHandlerDeclarator.handle(handler);
				}
			}
			
		} else if (step.getClass().isAssignableFrom(ContainerStep.class)) {
			ContainerStep<R,T> containerStep = (ContainerStep<R, T>) step;
			ContainerComposition<R,T> subComposition = ContainerComposition.fromContainerComposer(containerStep.getContainer());
			executeContainer(subComposition, payload);
		}
	}
	
	private void executeContainer(ContainerComposition<R, T> container, Payload<R,T> payload) {
		
		if (container.isConditional() == false 
				||  (container.isConditional() == true 
					&& container.getPredicate().test(payload) == true)) {
		
			if (!container.isParallel()) {
				do {
					container.getTasks().stream().sequential()
					.forEach(t -> {
						executeStep(t,payload);
					});
				} while (container.isConditional() == true 
						&& container.isLoopEnabed() == true 
						&& container.getPredicate().test(payload) == true);
			} else {
				container.getTasks().stream().parallel()
				.forEach(t -> {
					executeStep(t,payload);
				});
			}
		}
	}

}
