package org.arepoframework.demo.composer;

import java.util.ArrayList;
import java.util.List;

import org.arepoframework.demo.composer.functions.SequenceFunction;
import org.arepoframework.demo.composition.ProcessComposition;

public class ProcessComposer<R,T> implements IProcessComposer<R,T> {
	
	private ProcessComposition<R,T> composition = new ProcessComposition<R,T>();
	private List<SequenceComposer<R,T>> sequences = new ArrayList<>();
	
	public ProcessComposer() {}
	
	public ProcessComposer<R,T> sequence(SequenceFunction<R,T> sequenceFunction) {
		
		SequenceComposer<R,T> sequenceComposer = new SequenceComposer<R,T>(this);
		sequenceFunction.declare(sequenceComposer);
		sequences.add(sequenceComposer);
		return this;
	}
	
	public ProcessComposition<R,T> compose() {
		return composition;
	}
}
