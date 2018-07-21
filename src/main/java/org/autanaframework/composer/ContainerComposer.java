package org.autanaframework.composer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.autanaframework.composer.declarative.declarators.JavaSnippetDeclarator;
import org.autanaframework.composer.declarative.declarators.LoopDeclarator;
import org.autanaframework.composer.declarative.declarators.ParallelDeclarator;
import org.autanaframework.composer.declarative.declarators.SequenceDeclarator;
import org.autanaframework.composer.declarative.declarators.YawDeclarator;
import org.autanaframework.composition.AbstractComposition;

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
	
	protected List<AbstractComposition<R,T>> composeChildren(AbstractComposition<R, T> parentComposition) {
		
		return steps.stream()
		.map(composer -> composer.compose(parentComposition))
		.collect(Collectors.toList());
		
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
