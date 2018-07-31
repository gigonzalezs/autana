package io.yamia.autana.samples.monitors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.yamia.autana.persistence.AbstractInstanceTrace;
import io.yamia.autana.persistence.AbstractPersistenceMonitor;
import io.yamia.autana.samples.model.InstanceTrace;
import io.yamia.autana.samples.repository.InstanceTraceRepository;

@Component
public class PersistenceMonitor extends AbstractPersistenceMonitor<Integer,Integer> {
	
	private String vaadinSessionId;
	
	@Autowired
	private InstanceTraceRepository instanceTraceRepository;

	@Override
	protected AbstractInstanceTrace buildNewInstanceTrace(int step, String event, String path, String payload) {
		return new InstanceTrace(vaadinSessionId, step, event, path, payload);
	}

	@Override
	protected AbstractInstanceTrace save(AbstractInstanceTrace trace) {
		return instanceTraceRepository.save((InstanceTrace)trace);
	}
	
	public String getVaadinSessionId() {
		return vaadinSessionId;
	}

	public void setVaadinSessionId(String vaadinSessionId) {
		this.vaadinSessionId = vaadinSessionId;
	}

}
