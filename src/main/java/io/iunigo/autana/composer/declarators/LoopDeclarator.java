package io.iunigo.autana.composer.declarators;

import io.iunigo.autana.composer.LoopComposer;

@FunctionalInterface
public interface LoopDeclarator<R,T> {
	void declare(LoopComposer<R,T> sequence);
}
