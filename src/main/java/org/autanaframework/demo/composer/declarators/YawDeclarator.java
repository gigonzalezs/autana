package org.autanaframework.demo.composer.declarators;

import org.autanaframework.demo.composer.YawComposer;

@FunctionalInterface
public interface YawDeclarator<R,T> {
	void declare(YawComposer<R,T> sequence);
}
