package org.autanaframework.composer.declarative.declarators;

import org.autanaframework.composer.ExceptionHandler;

@FunctionalInterface
public interface ExceptionHandlerDeclarator {
	void handle(ExceptionHandler handler);
}
