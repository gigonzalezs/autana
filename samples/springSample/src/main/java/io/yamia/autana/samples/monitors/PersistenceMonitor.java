package io.yamia.autana.samples.monitors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.yamia.autana.persistence.AbstractInstanceTrace;
import io.yamia.autana.persistence.AbstractPersistenceMonitor;
import io.yamia.autana.samples.model.InstanceTrace;
import io.yamia.autana.samples.repository.InstanceTraceRepository;

@Component
public class PersistenceMonitor extends AbstractPersistenceMonitor<Integer,Integer> {
	
	@Autowired
	private InstanceTraceRepository instanceTraceRepository;

	@Override
	protected AbstractInstanceTrace buildNewInstanceTrace(int step, String event, String path, String payload) {
		return new InstanceTrace(step, event, path, payload);
	}

	@Override
	protected AbstractInstanceTrace save(AbstractInstanceTrace trace) {
		return instanceTraceRepository.save((InstanceTrace)trace);
	}

}
