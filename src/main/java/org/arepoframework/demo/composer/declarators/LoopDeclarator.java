package org.arepoframework.demo.composer.declarators;

import org.arepoframework.demo.composer.LoopComposer;

@FunctionalInterface
public interface LoopDeclarator<R,T> {
	void declare(LoopComposer<R,T> sequence);
}
