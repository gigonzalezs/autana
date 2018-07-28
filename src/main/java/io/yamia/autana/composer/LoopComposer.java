package io.yamia.autana.composer;

import io.yamia.autana.composition.AbstractComposition;
import io.yamia.autana.composition.LoopComposition;

public final class LoopComposer<R, T> extends ConditionedComposer<R,T> {
	
	public LoopComposer (AbstractComposer<R,T> parentComposer) {
		super(parentComposer);
	}
	
	@Override
	protected AbstractComposition<R, T> buildComposition(AbstractComposition<R, T> parentComposition) {
		return new LoopComposition<>(parentComposition, this.getPredicate());
	}	
}
