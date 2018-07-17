package org.arepoframework.demo.composer;

import java.util.List;

import org.arepoframework.demo.composer.functions.TaskFunction;

public class TaskComposer<R,T> extends AbstractComposer<R,T> implements ITaskLinker<R,T> {

	private ITaskLinker<R,T> taskLinker;
	
	protected TaskComposer(IProcessComposer<R,T> composer, ITaskLinker<R,T> taskLinker) {
		super(composer);
		this.taskLinker = taskLinker;
	}
	
	@Override
	public TaskComposer<R,T> task(TaskFunction<R,T> taskFunction) {
		this.getTasks().add(taskFunction);
		return new TaskComposer<R, T>(super.getComposer(), taskLinker);
	}

	@Override
	public List<TaskFunction<R, T>> getTasks() {
		return taskLinker.getTasks();
	}
}
