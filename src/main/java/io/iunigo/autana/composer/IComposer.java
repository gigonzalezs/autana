package io.iunigo.autana.composer;

import io.iunigo.autana.composition.AbstractComposition;

public interface IComposer<R,T> {
	
	AbstractComposition<R,T> compose(AbstractComposition<R,T> parentComposition);
	
}