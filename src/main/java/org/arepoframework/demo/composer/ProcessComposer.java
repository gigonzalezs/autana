package org.arepoframework.demo.composer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.arepoframework.demo.composer.functions.SequenceFunction;
import org.arepoframework.demo.composition.ProcessComposition;
import org.arepoframework.demo.composition.SequenceComposition;

public class ProcessComposer<R,T> implements IProcessComposer<R,T> {
	
	private ProcessComposition<R,T> composition = new ProcessComposition<R,T>();
	private List<SequenceComposer<R,T>> sequenceComposers = new ArrayList<>();
	
	public ProcessComposer() {}
	
	public ProcessComposer<R,T> sequence(SequenceFunction<R,T> sequenceFunction) {
		
		SequenceComposer<R,T> sequenceComposer = new SequenceComposer<R,T>(this);
		sequenceFunction.declare(sequenceComposer);
		sequenceComposers.add(sequenceComposer);
		return this;
	}
	
	public ProcessComposer<R,T> parallel(SequenceFunction<R,T> parallelFunction) {
		
		ParalellComposer<R,T> parallelComposer = new ParalellComposer<R,T>(this);
		parallelFunction.declare(parallelComposer);
		sequenceComposers.add(parallelComposer);
		return this;
	}
	
	public ProcessComposition<R,T> compose() {
		composition.setSequences(
				sequenceComposers.stream()
				.map(composer -> SequenceComposition.<R, T>fromSequenceComposer(composer))
				.collect(Collectors.toList()));
		return composition;
	}
}
