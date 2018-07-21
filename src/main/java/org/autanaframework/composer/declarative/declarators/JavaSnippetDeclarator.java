package org.autanaframework.composer.declarative.declarators;

import org.autanaframework.director.Payload;

@FunctionalInterface
public interface JavaSnippetDeclarator<R,T> {
	void execute(Payload<R,T> payload);
}
