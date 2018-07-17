package org.arepoframework.demo.composer.functions;

import org.arepoframework.demo.director.Payload;

@FunctionalInterface
public interface TaskFunction<R,T> {
	void declare(Payload<R,T> payload);
}
