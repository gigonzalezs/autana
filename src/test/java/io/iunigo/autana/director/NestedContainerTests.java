package io.iunigo.autana.director;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import io.iunigo.autana.composition.ProcessComposition;
import io.iunigo.autana.director.ProcessDirector;

public class NestedContainerTests {

	@Test
	public void testNestedSequences() {
	
		System.out.println("--BEGIN testNestedSequences");
		ProcessComposition<String, String> composition = new ProcessComposition<String, String>()
				.createFromDeclarativeCode()
				
				.sequence(container -> {
					
					container.sequence(container2 -> {
						
						container2.step(payload -> {
							
							System.out.println("tarea 1-A debe ejecutarse");
							payload.response = "EJECUTADO1";
						})
						.step(payload -> {
							
							System.out.println("tarea 2-A debe ejecutarse");
							payload.response += "-EJECUTADO2";
						});
					})
					.step(payload -> {
						System.out.println("tarea 0-A debe ejecutarse");
						payload.response += "-EJECUTADO3";
					});
				})
				.sequence(container -> {
					container.step(payload -> {
						payload.response += "-EJECUTADO4";
						System.out.println("fin.");
					});
				})
				.compose();
		
	
		assertThat(composition).isNotNull();
		assertThat(composition.getChildren()).isNotNull();
		assertThat(composition.getChildren().size()).isEqualTo(2);
		
		String result = new ProcessDirector<String, String>()
			.composition(composition)
			.process("UNO");
		
		assertThat(result).isEqualTo("EJECUTADO1-EJECUTADO2-EJECUTADO3-EJECUTADO4");
		
		System.out.println("--END testNestedSequences");
		
	}
}
