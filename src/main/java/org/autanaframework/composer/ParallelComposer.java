package org.autanaframework.composer;

import org.autanaframework.composition.AbstractComposition;
import org.autanaframework.composition.ParallelComposition;

public final class ParallelComposer<R, T> extends ContainerComposer<R,T> {
	
	public ParallelComposer (AbstractComposer<R,T> parentComposer) {
		super(parentComposer);
	}

	@Override
	public AbstractComposition<R, T> compose(AbstractComposition<R, T> parentComposition) {
		
		ParallelComposition<R,T> composition = new ParallelComposition<>(parentComposition);
		
		super.composeChildren(composition);
		
		return composition;
	}
}
