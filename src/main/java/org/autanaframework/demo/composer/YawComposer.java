package org.autanaframework.demo.composer;

public final class YawComposer <R, T> extends ConditionedComposer<R,T> {
	
	YawComposer (IProcessComposer<R,T> composer) {
		super(composer);
	}
}
