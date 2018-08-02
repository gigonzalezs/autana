package io.iunigo.autana.composer.declarators;

import io.iunigo.autana.director.Payload;

@FunctionalInterface
public interface JavaSnippetDeclarator<R,T> {
	void execute(Payload<R,T> payload);
}
