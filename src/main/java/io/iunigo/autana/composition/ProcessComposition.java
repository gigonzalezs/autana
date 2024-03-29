package io.iunigo.autana.composition;

import java.util.ArrayList;
import java.util.List;

import io.iunigo.autana.composer.DeclarativeProcessComposer;

public class ProcessComposition<R,T> extends AbstractComposition<R,T> {
	
	public ProcessComposition() {
		super(null);
	}

	private List<AbstractComposition<R, T>> containerCompositions = new ArrayList<>();

	public List<AbstractComposition<R, T>> getChildren() {
		return containerCompositions;
	}

	public void setChildren(List<AbstractComposition<R, T>> compositions) {
		this.containerCompositions = compositions;
	}
	
	public DeclarativeProcessComposer<R,T> createFromDeclarativeCode() {
		return new DeclarativeProcessComposer<R,T>();
	}
	
}
