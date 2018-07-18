package org.arepoframework.demo.composer;

import java.util.function.Predicate;

import org.arepoframework.demo.director.Payload;

public final class LoopComposer<R, T> extends ContainerComposer<R,T> {
	
	private Predicate<Payload<R,T>> predicateFunction;
	
	LoopComposer (IProcessComposer<R,T> composer) {
		super(composer, SEQUENTIAL_CONTAINER, CONDITION_REQUIRED);
	}
	
	public LoopComposer <R, T> predicate(Predicate<Payload<R,T>> predicateFunction) {
		this.predicateFunction = predicateFunction;
		return this;
	}
	
	public Predicate<Payload<R,T>> getPredicate() {
		return this.predicateFunction;
	}
}
