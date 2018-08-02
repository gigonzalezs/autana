package io.iunigo.autana.composition;

import java.util.function.Predicate;

import io.iunigo.autana.director.Payload;

public class LoopComposition<R,T> extends ContainerComposition<R,T> {

	public LoopComposition(AbstractComposition<R, T> parentComposition, 
			Predicate<Payload<R,T>> condition) {
		super(parentComposition, true, condition);
	}

}
