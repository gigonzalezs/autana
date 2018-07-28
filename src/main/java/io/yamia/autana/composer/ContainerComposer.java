package io.yamia.autana.composer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import io.yamia.autana.composer.declarators.JavaSnippetDeclarator;
import io.yamia.autana.composer.declarators.LoopDeclarator;
import io.yamia.autana.composer.declarators.ParallelDeclarator;
import io.yamia.autana.composer.declarators.SequenceDeclarator;
import io.yamia.autana.composer.declarators.YawDeclarator;
import io.yamia.autana.composition.AbstractComposition;
import io.yamia.autana.composition.ContainerComposition;

public abstract class ContainerComposer<R,T> extends AbstractComposer<R,T> 
	implements IContainerComposer<R, T> {

	private final List<AbstractComposer<R,T>> steps = new ArrayList<>();
	
	protected ContainerComposer(AbstractComposer<R,T> parentComposer) {
		super(parentComposer);
	}
	
	public JavaStepComposer<R, T> step(JavaSnippetDeclarator<R,T> snippetFunction) {
		
		JavaStepComposer<R,T> step = new JavaStepComposer<>(this, snippetFunction);
		steps.add(step);
		return step;
	}
	
	public List<AbstractComposer<R,T>> getSteps() {
			return steps;
	}
	
	@Override
	public AbstractComposition<R, T> compose(AbstractComposition<R, T> parentComposition) {
		
		AbstractComposition<R, T> composition = buildComposition(parentComposition);
		composeChildren(composition);
		return composition;
	}
	
	protected abstract AbstractComposition<R, T> buildComposition(AbstractComposition<R, T> parentComposition);
	
	private void composeChildren(AbstractComposition<R, T> parentComposition) {
		
		ContainerComposition<R,T> container = (ContainerComposition<R, T>) parentComposition;
		final Queue<AbstractComposition<R, T>> lastNodes = new LinkedList<>();
		steps.stream()
		.map(composer -> composer.compose(parentComposition))
		.forEach(composition -> {
			container.getSteps().add(composition);
			if (lastNodes.peek() != null) {
				AbstractComposition<R, T> lastNode = lastNodes.poll();
				lastNode.setNextNode(composition);
			}
			lastNodes.add(composition);
		});
		lastNodes.clear();
	}

	public ContainerComposer<R,T> sequence(SequenceDeclarator<R,T> sequenceFunction) {
		
		SequenceComposer<R,T> sequenceComposer = new SequenceComposer<R,T>(this);
		sequenceFunction.declare(sequenceComposer);
		steps.add(sequenceComposer);
		return this;
	}
	
	public ContainerComposer<R,T> parallel(ParallelDeclarator<R,T> parallelFunction) {
		
		ParallelComposer<R,T> parallelComposer = new ParallelComposer<R,T>(this);
		parallelFunction.declare(parallelComposer);
		steps.add(parallelComposer);
		return this;
	}
	
	public ContainerComposer<R,T> yaw(YawDeclarator<R,T> conditionFunction) {
		
		YawComposer<R,T> conditionComposer = new YawComposer<R,T>(this);
		conditionFunction.declare(conditionComposer);
		steps.add(conditionComposer);
		return this;
	}
	
	public ContainerComposer<R,T> loop(LoopDeclarator<R,T> loopFunction) {
		
		LoopComposer<R,T> loopComposer = new LoopComposer<R,T>(this);
		loopFunction.declare(loopComposer);
		steps.add(loopComposer);
		return this;
	}
}
