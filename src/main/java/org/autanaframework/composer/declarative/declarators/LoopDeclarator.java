package org.autanaframework.composer.declarative.declarators;

import org.autanaframework.composer.LoopComposer;

@FunctionalInterface
public interface LoopDeclarator<R,T> {
	void declare(LoopComposer<R,T> sequence);
}
