package org.arepoframework.demo.composition;

import java.util.List;

import org.arepoframework.demo.composer.SequenceComposer;
import org.arepoframework.demo.composer.functions.TaskFunction;

public class SequenceComposition<R,T> {
	
	private final List<TaskFunction<R,T>> tasks;
	private final boolean parallel;
	
	public SequenceComposition(boolean parallel, List<TaskFunction<R,T>> tasks) {
		this.parallel = parallel;
		this.tasks = tasks;
	}
	
	public static <R,T> SequenceComposition<R,T> fromSequenceComposer(SequenceComposer<R,T> composer) {
		SequenceComposition<R,T> composition = new SequenceComposition<R,T>(
				composer.isParallel(), composer.getTasks());
		return composition;
	}

	public List<TaskFunction<R, T>> getTasks() {
		return tasks;
	}
	
	public boolean isParallel() {
		return this.parallel;
	}
}
