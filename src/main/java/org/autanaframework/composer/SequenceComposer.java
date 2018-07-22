package org.autanaframework.composer;

import org.autanaframework.composition.AbstractComposition;
import org.autanaframework.composition.SequenceComposition;

public final class SequenceComposer<R,T> extends ContainerComposer<R,T> {
	
	public SequenceComposer (AbstractComposer<R,T> parentComposer) {
		super(parentComposer);
	}
	
	@Override
	protected AbstractComposition<R, T> buildComposition(AbstractComposition<R, T> parentComposition) {
		return new SequenceComposition<>(parentComposition);
	}
}
