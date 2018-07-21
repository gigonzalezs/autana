package org.autanaframework.composer;

import java.util.List;

import org.autanaframework.composition.AbstractComposition;
import org.autanaframework.composition.LoopComposition;

public final class LoopComposer<R, T> extends ConditionedComposer<R,T> {
	
	public LoopComposer (AbstractComposer<R,T> parentComposer) {
		super(parentComposer);
	}

	@Override
	public AbstractComposition<R, T> compose(AbstractComposition<R, T> parentComposition) {
		
		LoopComposition<R,T> composition = new LoopComposition<>(parentComposition, this.getPredicate());
		
		List<AbstractComposition<R,T>> children = super.composeChildren(composition);
		
		composition.getSteps().addAll(children);
		
		return composition;
	}	
}
