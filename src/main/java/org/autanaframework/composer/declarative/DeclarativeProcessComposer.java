package org.autanaframework.composer.declarative;

import java.util.ArrayList;
import java.util.List;
import org.autanaframework.composer.AbstractComposer;
import org.autanaframework.composer.ContainerComposer;
import org.autanaframework.composer.LoopComposer;
import org.autanaframework.composer.ParallelComposer;
import org.autanaframework.composer.SequenceComposer;
import org.autanaframework.composer.YawComposer;
import org.autanaframework.composer.declarative.declarators.LoopDeclarator;
import org.autanaframework.composer.declarative.declarators.ParallelDeclarator;
import org.autanaframework.composer.declarative.declarators.SequenceDeclarator;
import org.autanaframework.composer.declarative.declarators.YawDeclarator;
import org.autanaframework.composition.AbstractComposition;
import org.autanaframework.composition.ProcessComposition;

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
		
		containerComposers.stream()
			.forEach(composer -> {
				AbstractComposition<R,T> composition = composer.compose(processComposition);
				processComposition.getChildren().add(composition);
			});
		
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
