package io.yamia.autana.monitor;

import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.yamia.autana.director.Payload;

public class LogMonitor<R,T> implements IExecutionMonitor<R,T> {

	private static final Logger LOG = Logger.getLogger(LogMonitor.class.getName());

	@Override
	public void executing(String path, Payload<R,T> payload) {
		log("executing node", path, payload, false);
	}

	@Override
	public void success(String path, Payload<R,T> payload) {
		log("node success", path, payload, false);
	}

	@Override
	public void fail(String path, Payload<R,T> payload, boolean resumeable) {
		log("node fail", path, payload, resumeable);
	}
	
	@Override
	public void start(Payload<R, T> payload) {
		log("start process", null, payload, false);
	}

	@Override
	public void end(Payload<R, T> payload) {
		log("finish process", null, payload, false);
	}
	
	private void log(String label, String path, Payload<R,T> payload, boolean resumeable) {
		try {
			LOG.info(label + ": "+
					(resumeable? "(resumeable) " : "") +
					(path != null ? path : "") +
					"\rpayload: " + payload.toJSONString());
		} catch (JsonProcessingException e) {
			LOG.warning("unable to serialize payload as json: " + e.getMessage());
			LOG.info(label + ": " + path + "\rpayload: {unable to serialize payload as json}");
		}
	}
}
