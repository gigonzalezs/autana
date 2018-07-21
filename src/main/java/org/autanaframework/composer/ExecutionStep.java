package org.autanaframework.composer;

import org.autanaframework.composer.declarative.declarators.JavaSnippetDeclarator;

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
