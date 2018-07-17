package org.arepoframework.demo.composer;

public class IfComposer <R, T> extends ContainerComposer<R,T> {
	
	IfComposer (IProcessComposer<R,T> composer) {
		super(composer, false, true);
	}
}
