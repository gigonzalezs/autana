package org.arepoframework.demo.composer;

public final class SequenceComposer<R,T> extends ContainerComposer<R,T> {
	
	SequenceComposer (IProcessComposer<R,T> composer) {

		super(composer, SEQUENTIAL_CONTAINER, NO_CONDITION_REQUIRED);
	}
}
