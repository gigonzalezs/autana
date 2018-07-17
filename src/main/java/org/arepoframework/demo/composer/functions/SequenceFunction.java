package org.arepoframework.demo.composer.functions;

import org.arepoframework.demo.composer.SequenceComposer;


@FunctionalInterface
public interface SequenceFunction<R,T> {
	void declare(SequenceComposer<R,T> sequence);
}
