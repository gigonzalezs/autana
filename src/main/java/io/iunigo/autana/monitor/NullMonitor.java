package io.iunigo.autana.monitor;

import io.iunigo.autana.director.Payload;

public class NullMonitor<R,T> implements IExecutionMonitor<R,T> {

	@Override
	public void executing(String path, Payload<R,T> payload) {}

	@Override
	public void success(String path, Payload<R,T> payload) {}

	@Override
	public void fail(String path, Payload<R,T> payload, boolean resumeable) {}

	@Override
	public void start(Payload<R, T> payload) {}

	@Override
	public void end(Payload<R, T> payload) {}
}
