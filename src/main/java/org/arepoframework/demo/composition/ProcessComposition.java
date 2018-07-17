package org.arepoframework.demo.composition;

import java.util.ArrayList;
import java.util.List;

public class ProcessComposition<R,T> {
	
	private List<SequenceComposition<R, T>> sequences = new ArrayList<>();

	public List<SequenceComposition<R, T>> getSequences() {
		return sequences;
	}

	public void setSequences(List<SequenceComposition<R, T>> sequences) {
		this.sequences = sequences;
	}
	
}
