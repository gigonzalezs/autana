package org.autanaframework.monitor;

import org.autanaframework.director.Payload;

public interface IExecutionMonitor<R,T> {
	
	public void executing (String path, Payload<R,T> payload);
	public void success (String path, Payload<R,T> payload);
	public void fail(String path, Payload<R,T> payload, boolean resumeable);

}
