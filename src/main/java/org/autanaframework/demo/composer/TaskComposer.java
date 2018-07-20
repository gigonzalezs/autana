package org.autanaframework.demo.composer;

import java.util.List;

import org.autanaframework.demo.composer.declarators.TaskDeclarator;

public final class TaskComposer<R,T> extends AbstractComposer<R,T> implements ITaskLinker<R,T> {

	private final ITaskLinker<R,T> taskLinker;
	
	protected TaskComposer(IProcessComposer<R,T> composer, ITaskLinker<R,T> taskLinker) {
		super(composer);
		this.taskLinker = taskLinker;
	}
	
	@Override
	public TaskComposer<R,T> task(TaskDeclarator<R,T> taskFunction) {
		taskLinker.getTasks().add(new ExecutionStep<R,T>(taskFunction));
		return new TaskComposer<R, T>(super.getComposer(), taskLinker);
	}

	@Override
	public List<Step<R,T>> getTasks() {
		return taskLinker.getTasks();
	}
}
