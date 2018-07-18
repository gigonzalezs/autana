package org.arepoframework.demo.composer.functions;

import org.arepoframework.demo.composer.ParallelComposer;


@FunctionalInterface
public interface ParallelFunction<R,T> {
	void declare(ParallelComposer<R,T> parallel);
}
