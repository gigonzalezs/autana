package org.autanaframework.composer;

import java.util.List;

import org.autanaframework.composer.declarative.declarators.JavaSnippetDeclarator;

public interface IStepLinker<R,T> {

	JavaStepComposer<R,T> step(JavaSnippetDeclarator<R,T> StepDeclaratorFunction);
	
	List<Step<R,T>> getSteps();
}
