package org.arepoframework.demo.composition;

import java.util.List;

import org.arepoframework.demo.composer.ContainerComposer;
import org.arepoframework.demo.composer.functions.TaskFunction;

public class ContainerComposition<R,T> {
	
	private final List<TaskFunction<R,T>> tasks;
	private final boolean parallel;
	private final boolean conditional;
	
	public ContainerComposition(boolean parallel, boolean conditional, List<TaskFunction<R,T>> tasks) {
		this.parallel = parallel;
		this.tasks = tasks;
		this.conditional = conditional;
	}
	
	public static <R,T> ContainerComposition<R,T> fromContainerComposer(ContainerComposer<R,T> composer) {
		ContainerComposition<R,T> composition = new ContainerComposition<R,T>(
				composer.isParallel(), composer.isConditional(), composer.getTasks());
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
}
