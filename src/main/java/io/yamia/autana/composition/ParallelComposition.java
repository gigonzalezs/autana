package io.yamia.autana.composition;

public class ParallelComposition <R,T> extends ContainerComposition<R,T> {

	public ParallelComposition(AbstractComposition<R, T> parentComposition) {
		super(parentComposition, true);
	}

}
