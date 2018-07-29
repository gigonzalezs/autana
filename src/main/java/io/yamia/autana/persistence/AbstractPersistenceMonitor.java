package io.yamia.autana.persistence;

import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.yamia.autana.director.Payload;
import io.yamia.autana.monitor.IExecutionMonitor;

public abstract class AbstractPersistenceMonitor<R,T> implements IExecutionMonitor<R,T> {

	protected static final Logger LOG = Logger.getLogger(AbstractPersistenceMonitor.class.getName());
	protected int step=0;
	
	protected String getPayloadAsJson(Payload<R, T> payload) {
		String payloadString="";
		try {
			payloadString = payload.toJSONString();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return payloadString;
	}
	
	@Override
	public void executing(String path, Payload<R, T> payload) {
		step++;
		String payloadAsJson = getPayloadAsJson(payload);
		AbstractInstanceTrace trace = buildNewInstanceTrace(step, "EXECUTING", path, payloadAsJson);
		AbstractInstanceTrace saved = save(trace);
		LOG.info("instance trace persisted: " + saved.toString());
	}

	@Override
	public void success(String path, Payload<R, T> payload) {
		step++;
		String payloadAsJson = getPayloadAsJson(payload);
		AbstractInstanceTrace trace = buildNewInstanceTrace(step, "SUCCESS", path, payloadAsJson);
		AbstractInstanceTrace saved = save(trace);
		LOG.info("instance trace persisted: " + saved.toString());
	}
	
	@Override
	public void start(Payload<R, T> payload) {
		step=0;
	}

	@Override
	public void fail(String path, Payload<R, T> payload, boolean resumeable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void end(Payload<R, T> payload) {
		// TODO Auto-generated method stub
		
	}
	
	protected abstract AbstractInstanceTrace buildNewInstanceTrace(int step, String event, String path, String payload);
	
	protected abstract AbstractInstanceTrace save(AbstractInstanceTrace trace);
	
}
