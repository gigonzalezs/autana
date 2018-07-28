package io.yamia.autana.composer;

import java.util.function.Predicate;

import io.yamia.autana.director.Payload;

public abstract class ConditionedComposer<R,T> extends ContainerComposer<R,T> {
	
	private Predicate<Payload<R,T>> predicateFunction;
	
	protected ConditionedComposer(AbstractComposer<R,T> parentComposer) {
		super(parentComposer);
	}
	
	public ConditionedComposer <R, T> predicate(Predicate<Payload<R,T>> predicateFunction) {
		this.predicateFunction = predicateFunction;
		return this;
	}
	
	public Predicate<Payload<R,T>> getPredicate() {
		return this.predicateFunction;
	}
}
