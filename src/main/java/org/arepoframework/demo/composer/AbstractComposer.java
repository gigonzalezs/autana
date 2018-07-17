package org.arepoframework.demo.composer;

public abstract class AbstractComposer implements IProcessComposer {

	private IProcessComposer composer;
	
	protected AbstractComposer (IProcessComposer composer) {
		this.composer = composer;
	}
	
	protected IProcessComposer getComposer() {
		return composer;
	}

	@Override
	public ProcessComposition compose() {
		return composer.compose();
	}

}