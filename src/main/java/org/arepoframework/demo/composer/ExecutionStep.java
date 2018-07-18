package org.arepoframework.demo.composer;

import org.arepoframework.demo.composer.declarators.TaskDeclarator;

public final class ExecutionStep<R, T> extends Step<R, T> {
	
	private final TaskDeclarator<R,T> stepExecutor;
	
	public ExecutionStep(TaskDeclarator<R,T> stepExecutor) {
		super();
		this.stepExecutor = stepExecutor;
	}
	
	public TaskDeclarator<R,T> getStepExecutor() {
		return stepExecutor;
	}

}
