package org.autanaframework.composer;

import org.autanaframework.composition.AbstractComposition;
import org.autanaframework.composition.SequenceComposition;

public final class SequenceComposer<R,T> extends ContainerComposer<R,T> {
	
	public SequenceComposer (AbstractComposer<R,T> parentComposer) {
		super(parentComposer);
	}
	
	@Override
	public AbstractComposition<R, T> compose(AbstractComposition<R, T> parentComposition) {
		
		SequenceComposition<R,T> composition = new SequenceComposition<>(parentComposition);
		
		super.composeChildren(composition);
		
		return composition;
	}	
}
