package org.arepoframework.demo.composer;

import java.util.List;

import org.arepoframework.demo.composer.declarators.TaskDeclarator;

public final class TaskComposer<R,T> extends AbstractComposer<R,T> implements ITaskLinker<R,T> {

	private final ITaskLinker<R,T> taskLinker;
	
	protected TaskComposer(IProcessComposer<R,T> composer, ITaskLinker<R,T> taskLinker) {
		super(composer);
		this.taskLinker = taskLinker;
	}
	
	@Override
	public TaskComposer<R,T> task(TaskDeclarator<R,T> taskFunction) {
		taskLinker.getTasks().add(taskFunction);
		return new TaskComposer<R, T>(super.getComposer(), taskLinker);
	}

	@Override
	public List<TaskDeclarator<R, T>> getTasks() {
		return taskLinker.getTasks();
	}
}
