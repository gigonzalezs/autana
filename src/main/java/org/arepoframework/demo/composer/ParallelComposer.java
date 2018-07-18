package org.arepoframework.demo.composer;

public final class ParallelComposer<R, T> extends ContainerComposer<R,T> {
	
	ParallelComposer (IProcessComposer<R,T> composer) {
		super(composer);
	}
}
