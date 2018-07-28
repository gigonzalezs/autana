package io.yamia.autana.samples.model;

import javax.persistence.Entity;

import io.yamia.autana.persistence.AbstractInstanceTrace;

@Entity
public class InstanceTrace extends AbstractInstanceTrace {

	public InstanceTrace() {
		super();
	}

	public InstanceTrace(int step, String event, String path, String payload) {
		super(step, event, path, payload);
	}

	@Override
	public String toString() {
		return "InstanceTrace [id=" + getId() + ", date=" + getDate() + ", path=" + getPath()
				+ ", payload=" + getPayload() + ", step=" + getStep() + ", event=" + getEvent()
				+ "]";
	}
	

}
