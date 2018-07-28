package io.yamia.autana.composer.declarators;

import io.yamia.autana.composer.ExceptionHandler;

@FunctionalInterface
public interface ExceptionHandlerDeclarator {
	void handle(ExceptionHandler handler);
}
