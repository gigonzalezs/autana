package org.autanaframework.composition;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.autanaframework.director.Payload;

public abstract class ContainerComposition<R,T> extends AbstractComposition<R,T>{
	
	private final List<AbstractComposition<R,T>> steps;
	private final boolean parallel;
	private final boolean conditional;
	private final boolean loopEnabled;
	private final Predicate<Payload<R,T>> predicate;
	
	public ContainerComposition(AbstractComposition<R,T> parentComposition) {
		super(parentComposition);
		this.parallel = false;
		this.steps =  new ArrayList<>();
		this.conditional = false;
		this.predicate = null;
		this.loopEnabled = false;
	}
	
	public ContainerComposition(AbstractComposition<R,T> parentComposition, boolean parallel) {
		super(parentComposition);
		this.parallel = parallel;
		this.steps = new ArrayList<>();
		this.conditional = false;
		this.predicate = null;
		this.loopEnabled = false;
	}
	
	public ContainerComposition(AbstractComposition<R,T> parentComposition, boolean loopEnabled, 
			Predicate<Payload<R,T>> condition) {
		super(parentComposition);
		this.parallel = false;
		this.steps = new ArrayList<>();
		this.conditional = true;
		this.predicate = condition;
		this.loopEnabled = loopEnabled;
	}
	
	/*
	public static <R,T> ContainerComposition<R,T> fromContainerComposer(String path, ContainerComposer<R,T> composer) {
		
		ContainerComposition<R,T> composition = null;
		
		if (composer.getClass().isAssignableFrom(SequenceComposer.class)) {
			
			composition = new ContainerComposition<R,T>(this,
					false, composer.getSteps());
			
		} else if (composer.getClass().isAssignableFrom(ParallelComposer.class)) {
			
			composition = new ContainerComposition<R,T>(path,
					true, composer.getSteps());
			
		} else if (composer.getClass().isAssignableFrom(YawComposer.class)) {
			
			YawComposer<R,T> yawComposer = (YawComposer<R, T>) composer;
			composition = new ContainerComposition<R,T>(path, false,
					yawComposer.getPredicate(), composer.getSteps());
				
		} else if (composer.getClass().isAssignableFrom(LoopComposer.class)) {
			
			LoopComposer<R,T> loopComposer = (LoopComposer<R, T>) composer;
			composition = new ContainerComposition<R,T>(path, true,
					loopComposer.getPredicate(), composer.getSteps());
		}		
		return composition;
	}
	*/

	public List<AbstractComposition<R,T>> getSteps() {
		return steps;
	}
	
	public boolean isParallel() {
		return this.parallel;
	}
	
	public boolean isSerial() {
		return !this.parallel;
	}
	
	public boolean isConditional() {
		return this.conditional;
	}
	
	public boolean isLoopEnabed() {
		return this.loopEnabled;
	}
	
	public Predicate<Payload<R,T>> getPredicate() {
		return this.predicate;
	}
}
