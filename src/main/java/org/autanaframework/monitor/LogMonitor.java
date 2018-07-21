package org.autanaframework.monitor;

import java.util.logging.Logger;

import org.autanaframework.director.Payload;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LogMonitor<R,T> implements IExecutionMonitor<R,T> {

	private static final Logger LOG = Logger.getLogger(LogMonitor.class.getName());
	private static final ObjectMapper mapper = new ObjectMapper();

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
	
	private void log(String label, String path, Payload<R,T> payload, boolean resumeable) {
		try {
			LOG.info(label + ": "+
					(resumeable? "(resumeable) " : "") +
					path + "\rpayload: " + mapper.writeValueAsString(payload));
		} catch (JsonProcessingException e) {
			LOG.warning("unable to serialize payload as json: " + e.getMessage());
			LOG.info(label + ": " + path + "\rpayload: {unable to serialize payload as json}");
		}
	}
}
