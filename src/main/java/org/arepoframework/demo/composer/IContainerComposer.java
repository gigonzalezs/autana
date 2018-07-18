package org.arepoframework.demo.composer;

import org.arepoframework.demo.composer.declarators.LoopDeclarator;
import org.arepoframework.demo.composer.declarators.ParallelDeclarator;
import org.arepoframework.demo.composer.declarators.SequenceDeclarator;
import org.arepoframework.demo.composer.declarators.YawDeclarator;

public interface IContainerComposer<R, T> {

	IContainerComposer<R, T> sequence(SequenceDeclarator<R, T> sequenceFunction);

	IContainerComposer<R, T> parallel(ParallelDeclarator<R, T> parallelFunction);

	IContainerComposer<R, T> yaw(YawDeclarator<R, T> conditionFunction);

	IContainerComposer<R, T> loop(LoopDeclarator<R, T> loopFunction);

}