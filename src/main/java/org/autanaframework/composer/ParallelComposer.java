package org.autanaframework.composer;

import org.autanaframework.composition.AbstractComposition;
import org.autanaframework.composition.ParallelComposition;

public final class ParallelComposer<R, T> extends ContainerComposer<R,T> {
	
	public ParallelComposer (AbstractComposer<R,T> parentComposer) {
		super(parentComposer);
	}

	@Override
	protected AbstractComposition<R, T> buildComposition(AbstractComposition<R, T> parentComposition) {
		return new ParallelComposition<>(parentComposition);
	}
}
