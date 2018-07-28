package io.yamia.autana.monitor;

import io.yamia.autana.director.Payload;

public interface IExecutionMonitor<R,T> {
	
	public void start(Payload<R,T> payload);
	public void executing (String path, Payload<R,T> payload);
	public void success (String path, Payload<R,T> payload);
	public void fail(String path, Payload<R,T> payload, boolean resumeable);
	public void end(Payload<R,T> payload);

}
