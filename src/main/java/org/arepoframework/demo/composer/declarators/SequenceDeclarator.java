package org.arepoframework.demo.composer.declarators;

import org.arepoframework.demo.composer.SequenceComposer;

@FunctionalInterface
public interface SequenceDeclarator<R,T> {
	void declare(SequenceComposer<R,T> sequence);
}
