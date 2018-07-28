package io.yamia.autana.composer;

import io.yamia.autana.composition.AbstractComposition;

public interface IComposer<R,T> {
	
	AbstractComposition<R,T> compose(AbstractComposition<R,T> parentComposition);
	
}