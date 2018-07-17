package org.arepoframework.demo.composer;

import java.util.ArrayList;
import java.util.List;

import org.arepoframework.demo.composer.functions.TaskFunction;

public class SequenceComposer<R,T> extends AbstractComposer<R,T> implements ITaskLinker<R,T> {
	
	private List<TaskFunction<R,T>> tasks = new ArrayList<>();
	private final boolean parallel;
	
	SequenceComposer (IProcessComposer<R,T> composer) {
		super(composer);
		this.parallel = false;
	}
	
	protected SequenceComposer (IProcessComposer<R,T> composer, boolean parallel) {
		super(composer);
		this.parallel = parallel;
	}
	
	@Override
	public TaskComposer<R, T> task(TaskFunction<R,T> taskFunction) {
		this.getTasks().add(taskFunction);
		return new TaskComposer<R,T>(super.getComposer(), this);
	}

	@Override
	public List<TaskFunction<R, T>> getTasks() {
		return tasks;
	}
	
	public boolean isParallel() {
		return this.parallel;
	}
}
