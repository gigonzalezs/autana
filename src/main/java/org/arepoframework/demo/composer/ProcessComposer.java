package org.arepoframework.demo.composer;

public class ProcessComposer implements IProcessComposer {
	
	private ProcessComposition composition = new ProcessComposition();
	private ProcessComposer() {}
	
	public static SequenceComposer mainSequence() {
		return new SequenceComposer(new ProcessComposer());
	}
	
	public ProcessComposition compose() {
		return composition;
	}
}
