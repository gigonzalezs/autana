package org.autanaframework.demo.composer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.autanaframework.demo.composer.declarators.LoopDeclarator;
import org.autanaframework.demo.composer.declarators.ParallelDeclarator;
import org.autanaframework.demo.composer.declarators.SequenceDeclarator;
import org.autanaframework.demo.composer.declarators.YawDeclarator;
import org.autanaframework.demo.composition.ContainerComposition;
import org.autanaframework.demo.composition.ProcessComposition;

public class ProcessComposer<R,T> implements IProcessComposer<R,T>, IContainerComposer<R, T> {
	
	private ProcessComposition<R,T> composition = new ProcessComposition<R,T>();
	private List<ContainerComposer<R,T>> containerComposers = new ArrayList<>();
	
	public ProcessComposer() {}

	@Override
	public ProcessComposer<R,T> sequence(SequenceDeclarator<R,T> sequenceFunction) {
		
		SequenceComposer<R,T> sequenceComposer = new SequenceComposer<R,T>(this);
		sequenceFunction.declare(sequenceComposer);
		containerComposers.add(sequenceComposer);
		return this;
	}
	
	@Override
	public ProcessComposer<R,T> parallel(ParallelDeclarator<R,T> parallelFunction) {
		
		ParallelComposer<R,T> parallelComposer = new ParallelComposer<R,T>(this);
		parallelFunction.declare(parallelComposer);
		containerComposers.add(parallelComposer);
		return this;
	}
	
	@Override
	public ProcessComposer<R,T> yaw(YawDeclarator<R,T> conditionFunction) {
		
		YawComposer<R,T> conditionComposer = new YawComposer<R,T>(this);
		conditionFunction.declare(conditionComposer);
		containerComposers.add(conditionComposer);
		return this;
	}
	
	@Override
	public ProcessComposer<R,T> loop(LoopDeclarator<R,T> loopFunction) {
		
		LoopComposer<R,T> loopComposer = new LoopComposer<R,T>(this);
		loopFunction.declare(loopComposer);
		containerComposers.add(loopComposer);
		return this;
	}
	
	@Override
	public ProcessComposition<R,T> compose() {
		composition.setSequences(
				containerComposers.stream()
				.map(composer -> ContainerComposition.<R, T>fromContainerComposer(composer))
				.collect(Collectors.toList()));
		return composition;
	}
}