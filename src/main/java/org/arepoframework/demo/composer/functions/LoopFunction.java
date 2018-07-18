package org.arepoframework.demo.composer.functions;

import org.arepoframework.demo.composer.LoopComposer;

@FunctionalInterface
public interface LoopFunction<R,T> {
	void declare(LoopComposer<R,T> sequence);
}
