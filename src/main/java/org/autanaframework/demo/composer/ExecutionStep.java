package org.autanaframework.demo.composer;

import org.autanaframework.demo.composer.declarators.StepDeclarator;

public final class ExecutionStep<R, T> extends Step<R, T> {
	
	private final StepDeclarator<R,T> stepExecutor;
	
	public ExecutionStep(StepDeclarator<R,T> stepExecutor) {
		super();
		this.stepExecutor = stepExecutor;
	}
	
	public StepDeclarator<R,T> getStepExecutor() {
		return stepExecutor;
	}

}
