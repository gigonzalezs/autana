package org.autanaframework.demo.composer.declarators;

import org.autanaframework.demo.composer.ExceptionHandler;

@FunctionalInterface
public interface ExceptionHandlerDeclarator {
	void handle(ExceptionHandler handler);
}
