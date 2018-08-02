package io.iunigo.autana.composition;

import io.iunigo.autana.composer.declarators.JavaSnippetDeclarator;

public class JavaStepComposition<R,T> extends AbstractComposition<R,T> {

	private final JavaSnippetDeclarator<R,T> snippetFunction;
	
	public JavaStepComposition(AbstractComposition<R, T> parentComposition,
			JavaSnippetDeclarator<R,T> snippetFunction) {
		super(parentComposition);
		this.snippetFunction = snippetFunction;
	}

	public JavaSnippetDeclarator<R,T> getSnippetFunction() {
		return snippetFunction;
	}

}
