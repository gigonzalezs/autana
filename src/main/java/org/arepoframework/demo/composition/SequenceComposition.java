package org.arepoframework.demo.composition;

import java.util.ArrayList;
import java.util.List;

import org.arepoframework.demo.composer.SequenceComposer;
import org.arepoframework.demo.composer.functions.TaskFunction;

public class SequenceComposition<R, T> {
	
	private List<TaskFunction<R,T>> tasks = new ArrayList<>();
	
	public static <R,T> SequenceComposition<R,T> fromSequenceComposer(SequenceComposer<R,T> composer) {
		SequenceComposition<R,T> composition = new SequenceComposition<R,T>();
		composition.tasks = composer.getTasks();
		return composition;
	}

	public List<TaskFunction<R, T>> getTasks() {
		return tasks;
	}
}
