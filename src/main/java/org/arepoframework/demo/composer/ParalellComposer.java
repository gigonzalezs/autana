package org.arepoframework.demo.composer;

public class ParalellComposer<R, T> extends ContainerComposer<R,T> {
	
	ParalellComposer (IProcessComposer<R,T> composer) {
		super(composer, true, false);
	}
}
