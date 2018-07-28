package io.yamia.autana.composer.declarators;

import io.yamia.autana.composer.ParallelComposer;

@FunctionalInterface
public interface ParallelDeclarator<R,T> {
	void declare(ParallelComposer<R,T> parallel);
}
