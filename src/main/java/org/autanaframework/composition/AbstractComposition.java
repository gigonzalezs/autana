package org.autanaframework.composition;

public class AbstractComposition<R, T> {
	
	private final AbstractComposition<R,T> parentComposition;
	
	protected AbstractComposition (AbstractComposition<R,T> parentComposition) {
		this.parentComposition = parentComposition;
	}

	public AbstractComposition<R,T> getParent() {
		return parentComposition;
	}

}
