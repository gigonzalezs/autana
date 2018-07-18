package org.arepoframework.demo.composer;

public final class LoopComposer<R, T> extends ConditionedComposer<R,T> {
	
	LoopComposer (IProcessComposer<R,T> composer) {
		super(composer);
	}
}
