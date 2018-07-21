package org.autanaframework.composer;

import java.util.List;

import org.autanaframework.composition.AbstractComposition;
import org.autanaframework.composition.SequenceComposition;

public final class SequenceComposer<R,T> extends ContainerComposer<R,T> {
	
	public SequenceComposer (AbstractComposer<R,T> parentComposer) {
		super(parentComposer);
	}
	
	@Override
	public AbstractComposition<R, T> compose(AbstractComposition<R, T> parentComposition) {
		
		SequenceComposition<R,T> composition = new SequenceComposition<>(parentComposition);
		
		List<AbstractComposition<R,T>> children = super.composeChildren(composition);
		
		composition.getSteps().addAll(children);
		
		return composition;
	}	
}
