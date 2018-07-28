package io.yamia.autana.composer;

import io.yamia.autana.composition.AbstractComposition;
import io.yamia.autana.composition.SequenceComposition;

public final class SequenceComposer<R,T> extends ContainerComposer<R,T> {
	
	public SequenceComposer (AbstractComposer<R,T> parentComposer) {
		super(parentComposer);
	}
	
	@Override
	protected AbstractComposition<R, T> buildComposition(AbstractComposition<R, T> parentComposition) {
		return new SequenceComposition<>(parentComposition);
	}
}
