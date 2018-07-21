package org.autanaframework.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.autanaframework.composition.ProcessComposition;
import org.autanaframework.director.ProcessDirector;
import org.junit.Test;

public class LoopTests {

	@Test
	public void testLoop() {
	
		System.out.println("--BEGIN testLoop");
		
		ProcessComposition<String, String> composition = new ProcessComposition<String, String>()
				.createFromDeclarativeCode()
				
				.sequence(container -> {
					container.step(payload -> {
						payload.set("count", 0);
						payload.set("max", 5);
					});
				})
				.loop(container -> {
					
					container.predicate(payload -> {
						int count = (int) payload.vars("count").get();
						int max = (int) payload.vars("max").get();
						return count < max;
					})
					
					.step(payload -> {
						
						System.out.println("loop");
						int count = (int) payload.vars("count").get();
						System.out.println(count);
						count++;
						payload.set("count", count);
						payload.response = String.valueOf(count);
					});
				})
				.sequence(container -> {
					
					container.step(payload -> {
						
						System.out.println("fin.");
					});
				})
				.compose();
		
	
		assertThat(composition).isNotNull();
		assertThat(composition.getChildren()).isNotNull();
		assertThat(composition.getChildren().size()).isEqualTo(3);
		
		String result = new ProcessDirector<String,String>()
			.composition(composition)
			.process("UNO");
		
		System.out.println(result);
		
		assertThat(result).isEqualTo("5");
		
		System.out.println("--END testLoop");
		
	}
}
