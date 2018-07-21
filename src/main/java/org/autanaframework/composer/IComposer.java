package org.autanaframework.composer;

import org.autanaframework.composition.AbstractComposition;

public interface IComposer<R,T> {
	
	AbstractComposition<R,T> compose(AbstractComposition<R,T> parentComposition);
	
}