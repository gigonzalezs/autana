package org.arepoframework.demo.composer.declarators;

import org.arepoframework.demo.director.Payload;

@FunctionalInterface
public interface TaskDeclarator<R,T> {
	void execute(Payload<R,T> payload);
}
