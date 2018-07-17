package org.arepoframework.demo.composer;

public class ParalellComposer<R, T> extends SequenceComposer<R,T> {
	
	ParalellComposer (IProcessComposer<R,T> composer) {
		super(composer, true);
	}
}
