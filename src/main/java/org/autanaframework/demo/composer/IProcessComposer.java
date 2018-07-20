package org.autanaframework.demo.composer;

import org.autanaframework.demo.composition.ProcessComposition;

public interface IProcessComposer<R,T> {
	
	ProcessComposition<R,T> compose();
}