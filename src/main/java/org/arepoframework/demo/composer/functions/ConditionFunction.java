package org.arepoframework.demo.composer.functions;

import org.arepoframework.demo.composer.ConditionComposer;

@FunctionalInterface
public interface ConditionFunction<R,T> {
	void declare(ConditionComposer<R,T> sequence);
}
