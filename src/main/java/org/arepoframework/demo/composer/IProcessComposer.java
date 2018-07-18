package org.arepoframework.demo.composer;

import org.arepoframework.demo.composition.ProcessComposition;

public interface IProcessComposer<R,T> {
	
	ProcessComposition<R,T> compose();
}