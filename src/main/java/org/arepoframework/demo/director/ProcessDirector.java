package org.arepoframework.demo.director;

import java.util.List;

import org.arepoframework.demo.composer.ExecutionStep;
import org.arepoframework.demo.composer.Step;
import org.arepoframework.demo.composition.ProcessComposition;

public class ProcessDirector<R,T>  {
	
	private ProcessComposition<R,T> current_composition;
	
	public ProcessDirector<R,T> composition(ProcessComposition<R,T> composition) {
		current_composition = composition;
		return this;
	}
	
	public T process(R request) {
		Payload<R,T> payload = new Payload<>(request);
		process(payload);	
		return payload.response;
	}
	
	public void process(Payload<R,T> payload) {
		current_composition.getSequences().stream()
		.filter(s -> s.isConditional() == false 
				||  (s.isConditional() == true && s.getPredicate().test(payload) == true))
		.forEach(s -> {
			if (!s.isParallel()) {
				do {
					s.getTasks().stream().sequential()
					.forEach(t -> {executeStep(t,payload);});
				} while (s.isConditional() == true 
						&& s.isLoopEnabed() == true 
						&& s.getPredicate().test(payload) == true);
			} else {
				s.getTasks().stream().parallel()
				.forEach(t -> {executeStep(t,payload);});
			}
		});
	}
	
	private void executeStep(Step<R,T> step, Payload<R,T> payload) {
		if (step.getClass().isAssignableFrom(ExecutionStep.class)) {
			ExecutionStep<R,T> executionStep = (ExecutionStep<R, T>) step;
			executionStep.getStepExecutor().execute(payload);
		}
	}

}
