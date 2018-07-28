package io.yamia.autana.composer;

import io.yamia.autana.composer.declarators.JavaSnippetDeclarator;

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
