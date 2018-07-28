package io.yamia.autana.monitor;

import java.util.ArrayList;
import java.util.List;

import io.yamia.autana.director.Payload;

public class MonitorCollection<R,T> implements IExecutionMonitor<R,T> {

	private final List<IExecutionMonitor<R,T>> monitors = new ArrayList<>();

	public List<IExecutionMonitor<R, T>> getMonitors() {
		return monitors;
	}

	@Override
	public void start(Payload<R, T> payload) {
		monitors.stream().parallel().forEach(m -> m.start(payload));
	}

	@Override
	public void executing(String path, Payload<R, T> payload) {
		monitors.stream().parallel().forEach(m -> m.executing(path, payload));
	}

	@Override
	public void success(String path, Payload<R, T> payload) {
		monitors.stream().parallel().forEach(m -> m.success(path, payload));
	}

	@Override
	public void fail(String path, Payload<R, T> payload, boolean resumeable) {
		monitors.stream().parallel().forEach(m -> m.fail(path, payload, resumeable));
	}

	@Override
	public void end(Payload<R, T> payload) {
		monitors.stream().parallel().forEach(m -> m.end(payload));
	}
}
