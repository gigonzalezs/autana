package org.autanaframework.demo.composer;

import org.autanaframework.demo.composition.ProcessComposition;

public abstract class AbstractComposer<R,T> implements IProcessComposer<R,T> {

	private final IProcessComposer<R,T> composer;
	
	protected AbstractComposer (IProcessComposer<R,T> composer) {
		this.composer = composer;
	}
	
	protected IProcessComposer<R,T> getComposer() {
		return composer;
	}

	@Override
	public ProcessComposition<R,T> compose() {
		return composer.compose();
	}
}