package org.arepoframework.demo.director;

import org.arepoframework.demo.composition.ProcessComposition;

public class ProcessDirector<R,T>  {
	
	private ProcessComposition<R,T> current_composition;
	
	public ProcessDirector<R,T> composition(ProcessComposition<R,T> composition) {
		current_composition = composition;
		return this;
	}
	
	public T process(R request) {
		Payload<R,T> payload = new Payload<>(request);
		process(payload);	
		return payload.response;
	}
	
	public void process(Payload<R,T> payload) {
		current_composition.getSequences().stream()
		.forEach(s -> {
			if (!s.isParallel()) {
				s.getTasks().stream().sequential()
				.forEach(t -> {t.execute(payload);});
			} else {
				s.getTasks().stream().parallel()
				.forEach(t -> {t.execute(payload);});
			}
		});
	}

}
