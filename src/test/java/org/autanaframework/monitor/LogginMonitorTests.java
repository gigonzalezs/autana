package org.autanaframework.monitor;

import static org.assertj.core.api.Assertions.assertThat;

import org.autanaframework.composition.ProcessComposition;
import org.autanaframework.director.ProcessDirector;
import org.autanaframework.recovery.SysOutDebuggerInterrupterMonitor;
import org.junit.Test;

public class LogginMonitorTests {

	@Test
	public void testNestedSequencesWithLogginMonitor() {
	
		System.out.println("--BEGIN testNestedSequencesWithLogginMonitor");
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
		
		SysOutTraceMonitor<String, String>  debugger = new SysOutTraceMonitor<>();
		//debugger.interactive();
		//debugger.getBreakpoints().add("//sequence1/sequence1/javastep2");
		
		
		String result = new ProcessDirector<String, String>()
			.composition(composition)
			.addCustomMonitor(debugger)
			.process("UNO");
		
		assertThat(result).isEqualTo("EJECUTADO1-EJECUTADO2-EJECUTADO3-EJECUTADO4");
		
		System.out.println("--END testNestedSequencesWithLogginMonitor");
		
	}
}
