package org.autanaframework.composer.declarative.declarators;

import org.autanaframework.composer.YawComposer;

@FunctionalInterface
public interface YawDeclarator<R,T> {
	void declare(YawComposer<R,T> sequence);
}
