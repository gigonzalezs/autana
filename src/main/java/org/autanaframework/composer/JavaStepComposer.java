package org.autanaframework.composer;

import org.autanaframework.composer.declarative.declarators.JavaSnippetDeclarator;
import org.autanaframework.composition.AbstractComposition;
import org.autanaframework.composition.JavaStepComposition;

//implements IStepLinker<R,T>
public final class JavaStepComposer<R,T> extends AbstractComposer<R,T>  {
	
	private final JavaSnippetDeclarator<R,T> snippetFunction;
	
	public JavaStepComposer(AbstractComposer<R,T> parentComposer, JavaSnippetDeclarator<R,T> snippetFunction) {
		super(parentComposer);
		this.snippetFunction = snippetFunction;
	}

	@Override
	public AbstractComposition<R, T> compose(AbstractComposition<R, T> parentComposition) {
		JavaStepComposition<R,T> composition = new JavaStepComposition<R,T>(parentComposition, snippetFunction);
		return composition;
	}

	public JavaSnippetDeclarator<R,T> getSnippetFunction() {
		return snippetFunction;
	}
	
	public JavaStepComposer<R, T> step(JavaSnippetDeclarator<R,T> snippetFunction) {
		
		ContainerComposer<R,T> parentContainer = (ContainerComposer<R,T>) super.getParent();
		JavaStepComposer<R,T> step = new JavaStepComposer<>(parentContainer, snippetFunction);
		parentContainer.getSteps().add(step);
		return step;
	}
}
