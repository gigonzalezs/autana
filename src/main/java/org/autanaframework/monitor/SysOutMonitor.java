package org.autanaframework.monitor;

import org.autanaframework.director.Payload;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SysOutMonitor<R,T> implements IExecutionMonitor<R,T> {

	private static final ObjectMapper mapper = new ObjectMapper();
	private boolean payLoadPrinting = true;
	
	public SysOutMonitor<R,T> disablePayLoad() {
		this.payLoadPrinting = false;
		return this;
	}

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
			System.out.println( ">> " +
					label + ": "+
					(resumeable? "(resumeable) " : "") +
					(path != null ? path : "") +
					(payLoadPrinting? "\rpayload: " + mapper.writeValueAsString(payload) : ""));
		} catch (JsonProcessingException e) {
			System.out.println(">> WARNING: unable to serialize payload as json: " + e.getMessage());
			System.out.println(">> " + label + ": " + path + "\rpayload: {unable to serialize payload as json}");
		}
	}
}
