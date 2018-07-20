package org.autanaframework.demo.composer.declarators;

import org.autanaframework.demo.director.Payload;

@FunctionalInterface
public interface TaskDeclarator<R,T> {
	void execute(Payload<R,T> payload);
}
