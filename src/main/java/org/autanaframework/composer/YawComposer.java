package org.autanaframework.composer;

import java.util.List;

import org.autanaframework.composition.AbstractComposition;
import org.autanaframework.composition.YawComposition;

public final class YawComposer <R, T> extends ConditionedComposer<R,T> {
	
	public YawComposer (AbstractComposer<R,T> parentComposer) {
		super(parentComposer);
	}

	@Override
	public AbstractComposition<R, T> compose(AbstractComposition<R, T> parentComposition) {
		
		YawComposition<R,T> composition = new YawComposition<>(parentComposition, this.getPredicate());
		
		List<AbstractComposition<R,T>> children = super.composeChildren(composition);
		
		composition.getSteps().addAll(children);
		
		return composition;
	}
}
