package org.autanaframework.demo.composer.declarators;

import org.autanaframework.demo.director.Payload;

@FunctionalInterface
public interface StepDeclarator<R,T> {
	void execute(Payload<R,T> payload);
}
