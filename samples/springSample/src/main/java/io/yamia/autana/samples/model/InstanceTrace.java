package io.yamia.autana.samples.model;

import javax.persistence.Entity;

import io.yamia.autana.persistence.AbstractInstanceTrace;

@Entity
public class InstanceTrace extends AbstractInstanceTrace {
	
	private String sessionId;

	public InstanceTrace() {
		super();
	}

	public InstanceTrace(String sessionId, int step, String event, String path, String payload) {
		super(step, event, path, payload);
		this.sessionId = sessionId;
	}
	
	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	@Override
	public String toString() {
		return "InstanceTrace [sessionId=" + sessionId + ", getId()=" + getId() + ", getStep()=" + getStep()
				+ ", getDate()=" + getDate() + ", getEvent()=" + getEvent() + ", getPath()=" + getPath()
				+ ", getPayload()=" + getPayload() + "]";
	}
}
