package io.yamia.autana.composition;

import java.util.function.Predicate;

import io.yamia.autana.director.Payload;

public class YawComposition<R,T> extends ContainerComposition<R,T> {

	public YawComposition(AbstractComposition<R, T> parentComposition,
			Predicate<Payload<R,T>> condition) {
		super(parentComposition, false, condition);
		
	}

}
