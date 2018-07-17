package org.arepoframework.demo.composer;

public class TaskComposer extends AbstractComposer implements ITaskLinker {

	protected TaskComposer(IProcessComposer composer) {
		super(composer);
	}
	
	@Override
	public TaskComposer task() {
		return new TaskComposer(super.getComposer());
	}
}
