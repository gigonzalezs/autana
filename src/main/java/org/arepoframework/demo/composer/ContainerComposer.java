package org.arepoframework.demo.composer;

import java.util.ArrayList;
import java.util.List;

import org.arepoframework.demo.composer.declarators.TaskDeclarator;

public abstract class ContainerComposer<R,T> extends AbstractComposer<R,T> implements ITaskLinker<R,T> {
	
	private final List<TaskDeclarator<R,T>> tasks = new ArrayList<>();
	
	protected ContainerComposer (IProcessComposer<R,T> composer) {
		super(composer);
	}
	
	@Override
	public TaskComposer<R, T> task(TaskDeclarator<R,T> taskFunction) {
		tasks.add(taskFunction);
		return new TaskComposer<R,T>(super.getComposer(), this);
	}

	@Override
	public List<TaskDeclarator<R, T>> getTasks() {
		return tasks;
	}
}
