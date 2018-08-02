package io.iunigo.autana.composer;

import java.util.List;

import io.iunigo.autana.composer.declarators.JavaSnippetDeclarator;

public interface IStepLinker<R,T> {

	JavaStepComposer<R,T> step(JavaSnippetDeclarator<R,T> StepDeclaratorFunction);
	
	List<Step<R,T>> getSteps();
}
