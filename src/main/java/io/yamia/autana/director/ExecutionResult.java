package io.yamia.autana.director;

import io.yamia.autana.composition.AbstractComposition;

public class ExecutionResult<R,T> {
	
	private final boolean continue_execution;
	private final AbstractComposition<R,T> nextNode;
	
	public ExecutionResult(boolean canContinue, AbstractComposition<R, T> nextNode) {
		super();
		this.continue_execution = canContinue;
		this.nextNode = nextNode;
	}

	public boolean canContinue() {
		return continue_execution;
	}

	public AbstractComposition<R,T> getNextNode() {
		return nextNode;
	}
}
