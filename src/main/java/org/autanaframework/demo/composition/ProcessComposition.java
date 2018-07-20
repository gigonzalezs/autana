package org.autanaframework.demo.composition;

import java.util.ArrayList;
import java.util.List;

import org.autanaframework.demo.composer.ProcessComposer;

public class ProcessComposition<R,T> {
	
	private List<ContainerComposition<R, T>> sequences = new ArrayList<>();

	public List<ContainerComposition<R, T>> getSequences() {
		return sequences;
	}

	public void setSequences(List<ContainerComposition<R, T>> sequences) {
		this.sequences = sequences;
	}
	
	public ProcessComposer<R,T> createFromDeclarativeCode() {
		return new ProcessComposer<R,T>();
	}
	
}
