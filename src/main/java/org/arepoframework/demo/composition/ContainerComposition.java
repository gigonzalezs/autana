package org.arepoframework.demo.composition;

import java.util.List;

import org.arepoframework.demo.composer.ContainerComposer;
import org.arepoframework.demo.composer.functions.TaskFunction;
import java.util.function.Predicate;
import org.arepoframework.demo.composer.ConditionComposer;
import org.arepoframework.demo.composer.LoopComposer;
import org.arepoframework.demo.director.Payload;

public class ContainerComposition<R,T> {
	
	private final List<TaskFunction<R,T>> tasks;
	private final boolean parallel;
	private final boolean conditional;
	private final boolean loopEnabled;
	private final Predicate<Payload<R,T>> predicate;
	
	public ContainerComposition(boolean parallel, List<TaskFunction<R,T>> tasks) {
		this.parallel = parallel;
		this.tasks = tasks;
		this.conditional = false;
		this.predicate = null;
		this.loopEnabled = false;
	}
	
	public ContainerComposition(boolean loopEnabled, Predicate<Payload<R,T>> condition, List<TaskFunction<R,T>> tasks) {
		this.parallel = false;
		this.tasks = tasks;
		this.conditional = true;
		this.predicate = condition;
		this.loopEnabled = loopEnabled;
	}
	
	public static <R,T> ContainerComposition<R,T> fromContainerComposer(ContainerComposer<R,T> composer) {
		
		ContainerComposition<R,T> composition = null;
		if (!composer.isConditional()) {
			
			composition = new ContainerComposition<R,T>(
					composer.isParallel(), composer.getTasks());
			
		} else if (composer.getClass().isAssignableFrom(ConditionComposer.class)) {
			ConditionComposer<R,T> conditionalComposer = (ConditionComposer<R, T>) composer;
			composition = new ContainerComposition<R,T>(false,
					conditionalComposer.getPredicate(), composer.getTasks());
				
		} else if (composer.getClass().isAssignableFrom(LoopComposer.class)) {
			LoopComposer<R,T> loopComposer = (LoopComposer<R, T>) composer;
			composition = new ContainerComposition<R,T>(true,
					loopComposer.getPredicate(), composer.getTasks());
		}		
		return composition;
	}

	public List<TaskFunction<R, T>> getTasks() {
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
