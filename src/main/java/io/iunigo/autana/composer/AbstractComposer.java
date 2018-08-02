package io.iunigo.autana.composer;

public abstract class AbstractComposer<R,T> implements IComposer<R,T> {
	
	private final AbstractComposer<R,T> parentComposer;
	
	protected AbstractComposer (AbstractComposer<R,T> parentComposer) {
		this.parentComposer = parentComposer;
	}

	public AbstractComposer<R,T> getParent() {
		return parentComposer;
	}
}