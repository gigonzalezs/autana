package org.arepoframework.demo.composer;

public final class SequenceComposer<R,T> extends ContainerComposer<R,T> {
	
	SequenceComposer (IProcessComposer<R,T> composer) {
		super(composer, false, false);
	}
}
