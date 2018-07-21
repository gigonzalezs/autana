package org.autanaframework.composer.declarative.declarators;

import org.autanaframework.composer.SequenceComposer;

@FunctionalInterface
public interface SequenceDeclarator<R,T> {
	void declare(SequenceComposer<R,T> sequence);
}
