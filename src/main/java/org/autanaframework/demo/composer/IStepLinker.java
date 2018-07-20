package org.autanaframework.demo.composer;

import java.util.List;

import org.autanaframework.demo.composer.declarators.StepDeclarator;

public interface IStepLinker<R,T> {

	StepComposer<R,T> step(StepDeclarator<R,T> StepDeclaratorFunction);
	
	List<Step<R,T>> getSteps();
}
