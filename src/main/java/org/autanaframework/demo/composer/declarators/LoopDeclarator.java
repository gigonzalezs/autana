package org.autanaframework.demo.composer.declarators;

import org.autanaframework.demo.composer.LoopComposer;

@FunctionalInterface
public interface LoopDeclarator<R,T> {
	void declare(LoopComposer<R,T> sequence);
}
