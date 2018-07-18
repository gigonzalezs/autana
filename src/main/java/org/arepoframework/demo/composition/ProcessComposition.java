package org.arepoframework.demo.composition;

import java.util.ArrayList;
import java.util.List;

public class ProcessComposition<R,T> {
	
	private List<ContainerComposition<R, T>> sequences = new ArrayList<>();

	public List<ContainerComposition<R, T>> getSequences() {
		return sequences;
	}

	public void setSequences(List<ContainerComposition<R, T>> sequences) {
		this.sequences = sequences;
	}
	
}
