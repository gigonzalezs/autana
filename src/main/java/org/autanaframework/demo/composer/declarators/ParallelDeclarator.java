package org.autanaframework.demo.composer.declarators;

import org.autanaframework.demo.composer.ParallelComposer;

@FunctionalInterface
public interface ParallelDeclarator<R,T> {
	void declare(ParallelComposer<R,T> parallel);
}
