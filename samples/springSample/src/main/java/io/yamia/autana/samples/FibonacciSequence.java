package io.yamia.autana.samples;

import org.springframework.stereotype.Component;

import io.yamia.autana.composition.ProcessComposition;

@Component
public class FibonacciSequence extends ProcessComposition<Integer, Integer> {
	
	public FibonacciSequence() {
		createFromDeclarativeCode()
		.sequence(container -> {
			container.step(payload -> {
				payload.response = 0;
				payload.set("a", 0);
				payload.set("b", 1);
				payload.set("i", 0);
				payload.set("max", 10);
			});
		})
		.loop(loopContainer -> {
			loopContainer.predicate(payload -> {
				Integer i = (Integer) payload.vars("i").get();
				Integer max = (Integer) payload.vars("max").get();
				return i < max;
			})
			.step(payload -> {
				Integer a = (Integer) payload.vars("a").get();
				Integer b = (Integer) payload.vars("b").get();
				payload.response = a + b;
			})
			.step(payload -> {
				Integer b = (Integer) payload.vars("b").get();
				payload.set("a", b);
				payload.set("b", payload.response);
			})
			.step(payload -> {
				Integer i = (Integer) payload.vars("i").get();
				i++;
				payload.set("i", i);
			});
		})
		.composeOverProcess(this);
	}
}
