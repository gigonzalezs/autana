package org.arepoframework.demo.composer.declarators;

import org.arepoframework.demo.composer.ParallelComposer;

@FunctionalInterface
public interface ParallelDeclarator<R,T> {
	void declare(ParallelComposer<R,T> parallel);
}
