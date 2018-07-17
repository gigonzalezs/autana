package org.arepoframework.demo.composer.functions;

import org.arepoframework.demo.composer.ParalellComposer;


@FunctionalInterface
public interface ParallelFunction<R,T> {
	void declare(ParalellComposer<R,T> parallel);
}
