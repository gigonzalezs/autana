package io.yamia.autana.composer.declarators;

import io.yamia.autana.composer.YawComposer;

@FunctionalInterface
public interface YawDeclarator<R,T> {
	void declare(YawComposer<R,T> sequence);
}
