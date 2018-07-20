package org.autanaframework.demo.composition;

import java.util.List;
import java.util.function.Predicate;

import org.autanaframework.demo.composer.ContainerComposer;
import org.autanaframework.demo.composer.LoopComposer;
import org.autanaframework.demo.composer.ParallelComposer;
import org.autanaframework.demo.composer.SequenceComposer;
import org.autanaframework.demo.composer.Step;
import org.autanaframework.demo.composer.YawComposer;
import org.autanaframework.demo.composer.declarators.TaskDeclarator;
import org.autanaframework.demo.director.Payload;

public class ContainerComposition<R,T> {
	
	private final List<Step<R,T>> tasks;
	private final boolean parallel;
	private final boolean conditional;
	private final boolean loopEnabled;
	private final Predicate<Payload<R,T>> predicate;
	
	public ContainerComposition(boolean parallel, List<Step<R,T>> tasks) {
		this.parallel = parallel;
		this.tasks = tasks;
		this.conditional = false;
		this.predicate = null;
		this.loopEnabled = false;
	}
	
	public ContainerComposition(boolean loopEnabled, Predicate<Payload<R,T>> condition, List<Step<R,T>> tasks) {
		this.parallel = false;
		this.tasks = tasks;
		this.conditional = true;
		this.predicate = condition;
		this.loopEnabled = loopEnabled;
	}
	
	public static <R,T> ContainerComposition<R,T> fromContainerComposer(ContainerComposer<R,T> composer) {
		
		ContainerComposition<R,T> composition = null;
		
		if (composer.getClass().isAssignableFrom(SequenceComposer.class)) {
			
			composition = new ContainerComposition<R,T>(
					false, composer.getTasks());
			
		} else if (composer.getClass().isAssignableFrom(ParallelComposer.class)) {
			
			composition = new ContainerComposition<R,T>(
					true, composer.getTasks());
			
		} else if (composer.getClass().isAssignableFrom(YawComposer.class)) {
			
			YawComposer<R,T> yawComposer = (YawComposer<R, T>) composer;
			composition = new ContainerComposition<R,T>(false,
					yawComposer.getPredicate(), composer.getTasks());
				
		} else if (composer.getClass().isAssignableFrom(LoopComposer.class)) {
			
			LoopComposer<R,T> loopComposer = (LoopComposer<R, T>) composer;
			composition = new ContainerComposition<R,T>(true,
					loopComposer.getPredicate(), composer.getTasks());
		}		
		return composition;
	}

	public List<Step<R,T>> getTasks() {
		return tasks;
	}
	
	public boolean isParallel() {
		return this.parallel;
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
