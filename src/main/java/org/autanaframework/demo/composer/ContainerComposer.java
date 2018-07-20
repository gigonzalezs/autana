package org.autanaframework.demo.composer;

import java.util.ArrayList;
import java.util.List;

import org.autanaframework.demo.composer.declarators.LoopDeclarator;
import org.autanaframework.demo.composer.declarators.ParallelDeclarator;
import org.autanaframework.demo.composer.declarators.SequenceDeclarator;
import org.autanaframework.demo.composer.declarators.TaskDeclarator;
import org.autanaframework.demo.composer.declarators.YawDeclarator;

public abstract class ContainerComposer<R,T> extends AbstractComposer<R,T> 
	implements ITaskLinker<R,T>, IContainerComposer<R, T> {
	
	private final List<Step<R,T>> tasks = new ArrayList<>();
	
	protected ContainerComposer (IProcessComposer<R,T> composer) {
		super(composer);
	}
	
	@Override
	public TaskComposer<R, T> task(TaskDeclarator<R,T> taskFunction) {
		tasks.add(new ExecutionStep<R,T>(taskFunction));
		return new TaskComposer<R,T>(super.getComposer(), this);
	}

	@Override
	public List<Step<R,T>> getTasks() {
		return tasks;
	}
	
	@Override
	public ContainerComposer<R,T> sequence(SequenceDeclarator<R,T> sequenceFunction) {
		
		SequenceComposer<R,T> sequenceComposer = new SequenceComposer<R,T>(this);
		sequenceFunction.declare(sequenceComposer);
		tasks.add(new ContainerStep<R,T>(sequenceComposer));
		return this;
	}
	
	@Override
	public ContainerComposer<R,T> parallel(ParallelDeclarator<R,T> parallelFunction) {
		
		ParallelComposer<R,T> parallelComposer = new ParallelComposer<R,T>(this);
		parallelFunction.declare(parallelComposer);
		tasks.add(new ContainerStep<R,T>(parallelComposer));
		return this;
	}
	
	@Override
	public ContainerComposer<R,T> yaw(YawDeclarator<R,T> conditionFunction) {
		
		YawComposer<R,T> conditionComposer = new YawComposer<R,T>(this);
		conditionFunction.declare(conditionComposer);
		tasks.add(new ContainerStep<R,T>(conditionComposer));
		return this;
	}
	
	@Override
	public ContainerComposer<R,T> loop(LoopDeclarator<R,T> loopFunction) {
		
		LoopComposer<R,T> loopComposer = new LoopComposer<R,T>(this);
		loopFunction.declare(loopComposer);
		tasks.add(new ContainerStep<R,T>(loopComposer));
		return this;
	}
}
