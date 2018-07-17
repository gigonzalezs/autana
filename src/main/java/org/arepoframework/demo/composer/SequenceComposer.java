package org.arepoframework.demo.composer;

public class SequenceComposer extends AbstractComposer implements ITaskLinker {
	
	SequenceComposer (IProcessComposer composer) {
		super(composer);
	}
	
	@Override
	public TaskComposer task() {
		return new TaskComposer(super.getComposer());
	}
}
