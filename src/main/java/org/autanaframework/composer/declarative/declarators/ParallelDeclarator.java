package org.autanaframework.composer.declarative.declarators;

import org.autanaframework.composer.ParallelComposer;

@FunctionalInterface
public interface ParallelDeclarator<R,T> {
	void declare(ParallelComposer<R,T> parallel);
}
