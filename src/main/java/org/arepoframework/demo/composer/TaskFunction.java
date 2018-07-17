package org.arepoframework.demo.composer;

import org.arepoframework.demo.director.Payload;

@FunctionalInterface
public interface TaskFunction<R,T> {
	void execute(Payload<R,T> payload);
}
