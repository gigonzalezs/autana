package io.yamia.autana.composition;

public final class SequenceComposition<R,T> extends ContainerComposition<R,T> {

	public SequenceComposition(AbstractComposition<R, T> parentComposition) {
		super(parentComposition);
	}
}
