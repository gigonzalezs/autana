package org.autanaframework.demo.composer;

public final class ParallelComposer<R, T> extends ContainerComposer<R,T> {
	
	ParallelComposer (IProcessComposer<R,T> composer) {
		super(composer);
	}
}
