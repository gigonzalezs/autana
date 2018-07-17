package org.arepoframework.demo.composer;

import java.util.ArrayList;
import java.util.List;

public class SequenceComposer<R,T> extends AbstractComposer<R,T> implements ITaskLinker<R,T> {
	
	private List<TaskFunction<R,T>> tasks = new ArrayList<>();
	
	SequenceComposer (IProcessComposer<R,T> composer) {
		super(composer);
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
}
