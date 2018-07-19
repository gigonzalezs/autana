package org.arepoframework.demo.composer;

import java.util.function.Predicate;

import org.arepoframework.demo.director.Payload;

public abstract class ConditionedComposer<R,T> extends ContainerComposer<R,T> {
	
	private Predicate<Payload<R,T>> predicateFunction;
	
	protected ConditionedComposer(IProcessComposer<R, T> composer) {
		super(composer);
	}
	
	public ConditionedComposer <R, T> predicate(Predicate<Payload<R,T>> predicateFunction) {
		this.predicateFunction = predicateFunction;
		return this;
	}
	
	public Predicate<Payload<R,T>> getPredicate() {
		return this.predicateFunction;
	}
}