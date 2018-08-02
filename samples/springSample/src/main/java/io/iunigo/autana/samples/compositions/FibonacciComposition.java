package io.iunigo.autana.samples.compositions;

import org.springframework.stereotype.Component;

import io.iunigo.autana.composition.ProcessComposition;

@Component
public class FibonacciComposition extends ProcessComposition<Integer, Integer> {
	
	public FibonacciComposition() {
		createFromDeclarativeCode()
		.sequence(container -> {
			container.step(payload -> {
				payload.response = 0;
				payload.var("a").setInteger(0);
				payload.var("b").setInteger(1);
				payload.var("i").setInteger(0);
				payload.var("max").setInteger(10);
			});
		})
		.loop(loopContainer -> {
			loopContainer.predicate(payload -> {
				Integer i = payload.var("i").getInteger();
				Integer max = payload.var("max").getInteger();
				return i < max;
			})
			.step(payload -> {
				Integer a = payload.var("a").getInteger();
				Integer b = payload.var("b").getInteger();
				payload.response = a + b;
			})
			.step(payload -> {
				Integer b = payload.var("b").getInteger();
				payload.var("a").setInteger(b);
				payload.var("b").setInteger(payload.response);
			})
			.step(payload -> {
				Integer i = payload.var("i").getInteger();
				i++;
				payload.var("i").setInteger(i);
			});
		})
		.composeOverProcess(this);
	}
}
