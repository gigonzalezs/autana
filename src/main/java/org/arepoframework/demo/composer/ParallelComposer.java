package org.arepoframework.demo.composer;

public final class ParallelComposer<R, T> extends ContainerComposer<R,T> {
	
	ParallelComposer (IProcessComposer<R,T> composer) {
		super(composer, PARALLEL_CONTAINER, NO_CONDITION_REQUIRED);
	}
}
