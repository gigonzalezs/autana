package org.autanaframework.demo.composition;

import java.util.List;
import java.util.function.Predicate;

import org.autanaframework.demo.composer.ContainerComposer;
import org.autanaframework.demo.composer.LoopComposer;
import org.autanaframework.demo.composer.ParallelComposer;
import org.autanaframework.demo.composer.SequenceComposer;
import org.autanaframework.demo.composer.Step;
import org.autanaframework.demo.composer.YawComposer;
import org.autanaframework.demo.composer.declarators.StepDeclarator;
import org.autanaframework.demo.director.Payload;

public class ContainerComposition<R,T> {
	
	private final List<Step<R,T>> steps;
	private final boolean parallel;
	private final boolean conditional;
	private final boolean loopEnabled;
	private final Predicate<Payload<R,T>> predicate;
	
	public ContainerComposition(boolean parallel, List<Step<R,T>> steps) {
		this.parallel = parallel;
		this.steps = steps;
		this.conditional = false;
		this.predicate = null;
		this.loopEnabled = false;
	}
	
	public ContainerComposition(boolean loopEnabled, Predicate<Payload<R,T>> condition, List<Step<R,T>> steps) {
		this.parallel = false;
		this.steps = steps;
		this.conditional = true;
		this.predicate = condition;
		this.loopEnabled = loopEnabled;
	}
	
	public static <R,T> ContainerComposition<R,T> fromContainerComposer(ContainerComposer<R,T> composer) {
		
		ContainerComposition<R,T> composition = null;
		
		if (composer.getClass().isAssignableFrom(SequenceComposer.class)) {
			
			composition = new ContainerComposition<R,T>(
					false, composer.getSteps());
			
		} else if (composer.getClass().isAssignableFrom(ParallelComposer.class)) {
			
			composition = new ContainerComposition<R,T>(
					true, composer.getSteps());
			
		} else if (composer.getClass().isAssignableFrom(YawComposer.class)) {
			
			YawComposer<R,T> yawComposer = (YawComposer<R, T>) composer;
			composition = new ContainerComposition<R,T>(false,
					yawComposer.getPredicate(), composer.getSteps());
				
		} else if (composer.getClass().isAssignableFrom(LoopComposer.class)) {
			
			LoopComposer<R,T> loopComposer = (LoopComposer<R, T>) composer;
			composition = new ContainerComposition<R,T>(true,
					loopComposer.getPredicate(), composer.getSteps());
		}		
		return composition;
	}

	public List<Step<R,T>> getSteps() {
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
