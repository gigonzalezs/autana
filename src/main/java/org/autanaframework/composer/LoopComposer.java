package org.autanaframework.composer;

import org.autanaframework.composition.AbstractComposition;
import org.autanaframework.composition.LoopComposition;

public final class LoopComposer<R, T> extends ConditionedComposer<R,T> {
	
	public LoopComposer (AbstractComposer<R,T> parentComposer) {
		super(parentComposer);
	}
	
	@Override
	protected AbstractComposition<R, T> buildComposition(AbstractComposition<R, T> parentComposition) {
		return new LoopComposition<>(parentComposition, this.getPredicate());
	}	
}
