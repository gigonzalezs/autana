package org.arepoframework.demo.composer;

import org.arepoframework.demo.composition.ProcessComposition;

public abstract class AbstractComposer<R,T> implements IProcessComposer<R,T> {

	private IProcessComposer<R,T> composer;
	
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