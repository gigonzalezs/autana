package io.iunigo.autana.composer.declarators;

import io.iunigo.autana.composer.SequenceComposer;

@FunctionalInterface
public interface SequenceDeclarator<R,T> {
	void declare(SequenceComposer<R,T> sequence);
}
