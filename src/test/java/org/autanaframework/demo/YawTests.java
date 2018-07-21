package org.autanaframework.demo;

import static org.assertj.core.api.Assertions.assertThat;
import org.autanaframework.demo.composition.ProcessComposition;
import org.autanaframework.demo.director.ProcessDirector;
import org.junit.Test;

public class YawTests {

	@Test
	public void testCondition() {
	
		System.out.println("--BEGIN testCondition");
		
		ProcessComposition<String, String> composition = new ProcessComposition<String, String>()
				.createFromDeclarativeCode()
				.yaw(container -> {
					
					container.predicate(payload -> payload.request.equals("UNO"))
					
					.step(payload -> {
						
						System.out.println("tarea 1-A debe ejecutarse");
						payload.response = "EJECUTADO1";
						})
					.step(payload -> {
						
						System.out.println("tarea 2-A debe ejecutarse");
						payload.response = payload.response + "-EJECUTADO2";
					});
				})
				.yaw(container -> {
					
					container.predicate(payload -> payload.request.equals("DOS"))
					
					.step(payload -> {
						
						System.out.println("tarea 1-B NO debe ejecutarse");
						throw new RuntimeException("tarea 1-B NO debe ejecutarse");
						})
					.step(payload -> {
						
						System.out.println("tarea 2-B NO debe ejecutarse");
						throw new RuntimeException("tarea 2-B NO debe ejecutarse");
					});
				})
				.sequence(container -> {
					container.step(payload -> {
						System.out.println("fin.");
					});
				})
				.compose();
		
	
		assertThat(composition).isNotNull();
		assertThat(composition.getSequences()).isNotNull();
		assertThat(composition.getSequences().size()).isEqualTo(3);
		assertThat(composition.getSequences().get(0).getSteps()).isNotNull();
		assertThat(composition.getSequences().get(0).getSteps().size()).isEqualTo(2);
		
		String result = new ProcessDirector<String, String>()
			.composition(composition)
			.process("UNO");
		
		assertThat(result).isEqualTo("EJECUTADO1-EJECUTADO2");
		
		System.out.println("--END testCondition");
		
	}
}
