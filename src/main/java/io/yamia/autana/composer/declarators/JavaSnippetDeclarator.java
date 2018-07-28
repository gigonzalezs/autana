package io.yamia.autana.composer.declarators;

import io.yamia.autana.director.Payload;

@FunctionalInterface
public interface JavaSnippetDeclarator<R,T> {
	void execute(Payload<R,T> payload);
}
