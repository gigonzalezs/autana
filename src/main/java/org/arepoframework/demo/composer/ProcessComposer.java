package org.arepoframework.demo.composer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.arepoframework.demo.composer.functions.ConditionFunction;
import org.arepoframework.demo.composer.functions.LoopFunction;
import org.arepoframework.demo.composer.functions.ParallelFunction;
import org.arepoframework.demo.composer.functions.SequenceFunction;
import org.arepoframework.demo.composition.ProcessComposition;
import org.arepoframework.demo.composition.ContainerComposition;

public class ProcessComposer<R,T> implements IProcessComposer<R,T> {
	
	private ProcessComposition<R,T> composition = new ProcessComposition<R,T>();
	private List<ContainerComposer<R,T>> sequenceComposers = new ArrayList<>();
	
	public ProcessComposer() {}
	
	public ProcessComposer<R,T> sequence(SequenceFunction<R,T> sequenceFunction) {
		
		SequenceComposer<R,T> sequenceComposer = new SequenceComposer<R,T>(this);
		sequenceFunction.declare(sequenceComposer);
		sequenceComposers.add(sequenceComposer);
		return this;
	}
	
	public ProcessComposer<R,T> parallel(ParallelFunction<R,T> parallelFunction) {
		
		ParallelComposer<R,T> parallelComposer = new ParallelComposer<R,T>(this);
		parallelFunction.declare(parallelComposer);
		sequenceComposers.add(parallelComposer);
		return this;
	}
	
	public ProcessComposer<R,T> condition(ConditionFunction<R,T> conditionFunction) {
		
		ConditionComposer<R,T> conditionComposer = new ConditionComposer<R,T>(this);
		conditionFunction.declare(conditionComposer);
		sequenceComposers.add(conditionComposer);
		return this;
	}
	
	public ProcessComposer<R,T> loop(LoopFunction<R,T> loopFunction) {
		
		LoopComposer<R,T> loopComposer = new LoopComposer<R,T>(this);
		loopFunction.declare(loopComposer);
		sequenceComposers.add(loopComposer);
		return this;
	}
	
	public ProcessComposition<R,T> compose() {
		composition.setSequences(
				sequenceComposers.stream()
				.map(composer -> ContainerComposition.<R, T>fromContainerComposer(composer))
				.collect(Collectors.toList()));
		return composition;
	}
}
