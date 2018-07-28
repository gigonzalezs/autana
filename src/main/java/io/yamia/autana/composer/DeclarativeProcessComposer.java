package io.yamia.autana.composer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import io.yamia.autana.composer.declarators.LoopDeclarator;
import io.yamia.autana.composer.declarators.ParallelDeclarator;
import io.yamia.autana.composer.declarators.SequenceDeclarator;
import io.yamia.autana.composer.declarators.YawDeclarator;
import io.yamia.autana.composition.AbstractComposition;
import io.yamia.autana.composition.ProcessComposition;

//implements IContainerComposer<R, T>
public class DeclarativeProcessComposer<R,T> extends AbstractComposer<R,T>  {
	
	private List<ContainerComposer<R,T>> containerComposers = new ArrayList<>();
	
	public DeclarativeProcessComposer() {
		super(null);
	}
	
	public ProcessComposition<R,T> compose() {
		return (ProcessComposition<R, T>) this.compose(null);
	}

	@Override
	public AbstractComposition<R, T> compose(AbstractComposition<R, T> parentComposition) {
		
		ProcessComposition<R, T> processComposition = new ProcessComposition<R,T>();
		final Queue<AbstractComposition<R, T>> lastNodes = new LinkedList<>();
		
		containerComposers.stream()
			.forEach(composer -> {
				AbstractComposition<R,T> composition = composer.compose(processComposition);
				processComposition.getChildren().add(composition); 
				if (lastNodes.peek() != null) {
					AbstractComposition<R, T> lastNode = lastNodes.poll();
					lastNode.setNextNode(composition);
				}
				lastNodes.add(composition);
			});
		
		lastNodes.clear();
		return processComposition;
	}
	
	public DeclarativeProcessComposer<R,T> sequence(SequenceDeclarator<R,T> sequenceFunction) {
		
		SequenceComposer<R,T> sequenceComposer = new SequenceComposer<R,T>(this);
		sequenceFunction.declare(sequenceComposer);
		containerComposers.add(sequenceComposer);
		return this;
	}
		
	public DeclarativeProcessComposer<R,T> parallel(ParallelDeclarator<R,T> parallelFunction) {
		
		ParallelComposer<R,T> parallelComposer = new ParallelComposer<R,T>(this);
		parallelFunction.declare(parallelComposer);
		containerComposers.add(parallelComposer);
		return this;
	}

	public DeclarativeProcessComposer<R,T> yaw(YawDeclarator<R,T> yawFunction) {
		
		YawComposer<R,T> yawComposer = new YawComposer<R,T>(this);
		yawFunction.declare(yawComposer);
		containerComposers.add(yawComposer);
		return this;
	}
	
	public DeclarativeProcessComposer<R,T> loop(LoopDeclarator<R,T> loopFunction) {
		
		LoopComposer<R,T> loopComposer = new LoopComposer<R,T>(this);
		loopFunction.declare(loopComposer);
		containerComposers.add(loopComposer);
		return this;
	}


}
