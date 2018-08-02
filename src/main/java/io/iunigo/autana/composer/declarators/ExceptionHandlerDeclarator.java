package io.iunigo.autana.composer.declarators;

import io.iunigo.autana.composer.ExceptionHandler;

@FunctionalInterface
public interface ExceptionHandlerDeclarator {
	void handle(ExceptionHandler handler);
}
