package org.autanaframework.composer;

import org.autanaframework.composition.AbstractComposition;
import org.autanaframework.composition.YawComposition;

public final class YawComposer <R, T> extends ConditionedComposer<R,T> {
	
	public YawComposer (AbstractComposer<R,T> parentComposer) {
		super(parentComposer);
	}
	
	@Override
	protected AbstractComposition<R, T> buildComposition(AbstractComposition<R, T> parentComposition) {
		return new YawComposition<>(parentComposition, this.getPredicate());
	}
}
