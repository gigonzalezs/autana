package org.arepoframework.demo.composer;

import java.util.function.Predicate;

import org.arepoframework.demo.director.Payload;

public final class ConditionComposer <R, T> extends ContainerComposer<R,T> {
	
	private Predicate<Payload<R,T>> predicateFunction;
	
	ConditionComposer (IProcessComposer<R,T> composer) {
		super(composer, SEQUENTIAL_CONTAINER, CONDITION_REQUIRED);
	}
	
	public ConditionComposer <R, T> predicate(Predicate<Payload<R,T>> predicateFunction) {
		this.predicateFunction = predicateFunction;
		return this;
	}
	
	public Predicate<Payload<R,T>> getPredicate() {
		return this.predicateFunction;
	}
}
