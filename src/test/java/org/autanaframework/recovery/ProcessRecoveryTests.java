package org.autanaframework.recovery;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.autanaframework.composition.ProcessComposition;
import org.autanaframework.director.Payload;
import org.autanaframework.director.ProcessDirector;
import org.autanaframework.recovery.SysOutDebuggerInterrupterMonitor;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProcessRecoveryTests {

	@Test
	public void testNestedSequencesWithInterruptionAndRecovery() throws JsonParseException, JsonMappingException, IOException {
	
		System.out.println("--BEGIN testNestedSequencesWithInterruptionAndRecovery");
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
	
		
		SysOutDebuggerInterrupterMonitor<String, String>  debugger = new SysOutDebuggerInterrupterMonitor<>();
		debugger.getBreakpoints().add("//sequence1/sequence1/javastep2");
		
		String result = new ProcessDirector<String, String>()
			.composition(composition)
			.addCustomMonitor(debugger)
			.process("UNO");
		
		assertThat(result).isEqualTo("EJECUTADO1");
		assertThat(debugger.getLastPayload()).isEqualTo("{\"request\":\"UNO\",\"response\":\"EJECUTADO1\"}");
		
		ObjectMapper mapper = new ObjectMapper();
		
		Payload<String,String> resumingPayload = mapper.readValue(debugger.getLastPayload(),
				new TypeReference<Payload<String,String>>() {});
		
		String result2 = new ProcessDirector<String, String>()
				.composition(composition)
				.addSysOutTraceMonitor()
				.resume("//sequence1/sequence1/javastep2", resumingPayload);
		
		
		assertThat(result2).isEqualTo("EJECUTADO1-EJECUTADO2-EJECUTADO3-EJECUTADO4");
		
		System.out.println("--END testNestedSequencesWithInterruptionAndRecovery");
		
	}
}
