package io.iunigo.autana.composer.declarators;

import io.iunigo.autana.composer.ParallelComposer;

@FunctionalInterface
public interface ParallelDeclarator<R,T> {
	void declare(ParallelComposer<R,T> parallel);
}
