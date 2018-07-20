package org.autanaframework.demo.composer;

import java.util.ArrayList;
import java.util.List;

import org.autanaframework.demo.composer.declarators.LoopDeclarator;
import org.autanaframework.demo.composer.declarators.ParallelDeclarator;
import org.autanaframework.demo.composer.declarators.SequenceDeclarator;
import org.autanaframework.demo.composer.declarators.StepDeclarator;
import org.autanaframework.demo.composer.declarators.YawDeclarator;

public abstract class ContainerComposer<R,T> extends AbstractComposer<R,T> 
	implements IStepLinker<R,T>, IContainerComposer<R, T> {
	
	private final List<Step<R,T>> steps = new ArrayList<>();
	
	protected ContainerComposer (IProcessComposer<R,T> composer) {
		super(composer);
	}
	
	@Override
	public StepComposer<R, T> step(StepDeclarator<R,T> stepDeclaratorFunction) {
		steps.add(new ExecutionStep<R,T>(stepDeclaratorFunction));
		return new StepComposer<R,T>(super.getComposer(), this);
	}

	@Override
	public List<Step<R,T>> getSteps() {
		return steps;
	}
	
	@Override
	public ContainerComposer<R,T> sequence(SequenceDeclarator<R,T> sequenceFunction) {
		
		SequenceComposer<R,T> sequenceComposer = new SequenceComposer<R,T>(this);
		sequenceFunction.declare(sequenceComposer);
		steps.add(new ContainerStep<R,T>(sequenceComposer));
		return this;
	}
	
	@Override
	public ContainerComposer<R,T> parallel(ParallelDeclarator<R,T> parallelFunction) {
		
		ParallelComposer<R,T> parallelComposer = new ParallelComposer<R,T>(this);
		parallelFunction.declare(parallelComposer);
		steps.add(new ContainerStep<R,T>(parallelComposer));
		return this;
	}
	
	@Override
	public ContainerComposer<R,T> yaw(YawDeclarator<R,T> conditionFunction) {
		
		YawComposer<R,T> conditionComposer = new YawComposer<R,T>(this);
		conditionFunction.declare(conditionComposer);
		steps.add(new ContainerStep<R,T>(conditionComposer));
		return this;
	}
	
	@Override
	public ContainerComposer<R,T> loop(LoopDeclarator<R,T> loopFunction) {
		
		LoopComposer<R,T> loopComposer = new LoopComposer<R,T>(this);
		loopFunction.declare(loopComposer);
		steps.add(new ContainerStep<R,T>(loopComposer));
		return this;
	}
}
