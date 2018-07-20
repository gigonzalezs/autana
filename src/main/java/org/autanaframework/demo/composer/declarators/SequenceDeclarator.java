package org.autanaframework.demo.composer.declarators;

import org.autanaframework.demo.composer.SequenceComposer;

@FunctionalInterface
public interface SequenceDeclarator<R,T> {
	void declare(SequenceComposer<R,T> sequence);
}
