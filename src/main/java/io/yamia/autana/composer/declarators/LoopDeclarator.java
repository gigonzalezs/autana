package io.yamia.autana.composer.declarators;

import io.yamia.autana.composer.LoopComposer;

@FunctionalInterface
public interface LoopDeclarator<R,T> {
	void declare(LoopComposer<R,T> sequence);
}
