package org.arepoframework.demo.composer;

import org.arepoframework.demo.composition.ProcessComposition;

public class ProcessComposer<R,T> implements IProcessComposer<R,T> {
	
	private ProcessComposition<R,T> composition = new ProcessComposition<R,T>();
	
	public ProcessComposer() {}
	
	public SequenceComposer<R,T> mainSequence() {
		return new SequenceComposer<R,T>(new ProcessComposer<R,T>());
	}
	
	public ProcessComposition<R,T> compose() {
		return composition;
	}
}
