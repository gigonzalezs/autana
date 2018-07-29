package io.yamia.autana.director;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import io.yamia.autana.composition.ProcessComposition;
import io.yamia.autana.director.ProcessDirector;

public class LoopTests {

	@Test
	public void testLoop() {
	
		System.out.println("--BEGIN testLoop");
		
		ProcessComposition<String, String> composition = new ProcessComposition<String, String>()
				.createFromDeclarativeCode()
				
				.sequence(container -> {
					container.step(payload -> {
						payload.var("count").setInteger(0);
						payload.var("max").setInteger(5);
					});
				})
				.loop(container -> {
					
					container.predicate(payload -> {
						int count = payload.var("count").getInteger();
						int max = payload.var("max").getInteger();
						return count < max;
					})
					
					.step(payload -> {
						
						System.out.println("loop");
						int count = payload.var("count").getInteger();
						System.out.println(count);
						count++;
						payload.var("count").setInteger(count);
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
