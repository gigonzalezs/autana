package org.arepoframework.demo.composer.declarators;

import org.arepoframework.demo.composer.ExceptionHandler;

@FunctionalInterface
public interface ExceptionHandlerDeclarator {
	void handle(ExceptionHandler handler);
}
