package io.iunigo.autana.composer;

import io.iunigo.autana.composition.AbstractComposition;
import io.iunigo.autana.composition.LoopComposition;

public final class LoopComposer<R, T> extends ConditionedComposer<R,T> {
	
	public LoopComposer (AbstractComposer<R,T> parentComposer) {
		super(parentComposer);
	}
	
	@Override
	protected AbstractComposition<R, T> buildComposition(AbstractComposition<R, T> parentComposition) {
		return new LoopComposition<>(parentComposition, this.getPredicate());
	}	
}
