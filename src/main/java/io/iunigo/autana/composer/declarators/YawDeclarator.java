package io.iunigo.autana.composer.declarators;

import io.iunigo.autana.composer.YawComposer;

@FunctionalInterface
public interface YawDeclarator<R,T> {
	void declare(YawComposer<R,T> sequence);
}
