package io.iunigo.autana.composer;

import io.iunigo.autana.composer.declarators.JavaSnippetDeclarator;

public final class ExecutionStep<R, T> extends Step<R, T> {
	
	private final JavaSnippetDeclarator<R,T> stepExecutor;
	
	public ExecutionStep(JavaSnippetDeclarator<R,T> stepExecutor) {
		super();
		this.stepExecutor = stepExecutor;
	}
	
	public JavaSnippetDeclarator<R,T> getStepExecutor() {
		return stepExecutor;
	}

}
