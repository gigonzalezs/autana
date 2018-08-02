package io.iunigo.autana.composition;

import java.util.function.Predicate;

import io.iunigo.autana.director.Payload;

public class YawComposition<R,T> extends ContainerComposition<R,T> {

	public YawComposition(AbstractComposition<R, T> parentComposition,
			Predicate<Payload<R,T>> condition) {
		super(parentComposition, false, condition);
		
	}

}
