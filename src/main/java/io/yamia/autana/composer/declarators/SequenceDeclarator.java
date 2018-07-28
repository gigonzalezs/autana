package io.yamia.autana.composer.declarators;

import io.yamia.autana.composer.SequenceComposer;

@FunctionalInterface
public interface SequenceDeclarator<R,T> {
	void declare(SequenceComposer<R,T> sequence);
}
