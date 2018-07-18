package org.arepoframework.demo.composer.declarators;

import org.arepoframework.demo.composer.YawComposer;

@FunctionalInterface
public interface YawDeclarator<R,T> {
	void declare(YawComposer<R,T> sequence);
}
