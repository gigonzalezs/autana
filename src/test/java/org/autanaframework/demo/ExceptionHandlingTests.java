package org.autanaframework.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.autanaframework.demo.composer.ProcessComposer;
import org.autanaframework.demo.composition.ProcessComposition;
import org.autanaframework.demo.director.CompositionException;
import org.autanaframework.demo.director.ProcessDirector;
import org.junit.Test;

public class ExceptionHandlingTests {
	
	@Test
	public void testExceptionHandlingWhenOnExceptionDeclaration() {
		
		System.out.println("--BEGIN testExceptionHandlingWhenOnExceptionDeclaration");
		ProcessComposition<String, String> c = new ProcessComposer<String, String>()
				
				.sequence(s -> {
					
					s.step(payload -> {
						
						payload.response = "STEP1";
						System.out.println(payload.response);
						
						System.out.println("before throw an error");
						throw new RuntimeException("ERROR 1");
						})
					
					.step(payload -> {
						
						payload.response = "STEP2";
						System.out.println(payload.response);
						
						});
				})
				.compose();
		
		String result = new ProcessDirector<String, String>()
			.composition(c)
			.onException(ex -> {
				System.out.println("** handling exception...");
				assertThat(ex.getException().getClass()).isEqualTo(RuntimeException.class);
				assertThat(ex.getException().getMessage()).isEqualTo("ERROR 1");
				System.out.println("** this exception is not resumeable.");
			})
			.process("ONE");
		
		assertThat(result).isEqualTo("STEP1");
		
		System.out.println("--END testExceptionHandlingWhenOnExceptionDeclaration");
	}
	
	@Test
	public void testResumeExecutionAfterExceptionThrown() {
		
		System.out.println("--BEGIN testResumeExecutionAfterExceptionThrown");
		ProcessComposition<String, String> c = new ProcessComposer<String, String>()
				
				.sequence(s -> {
					
					s.step(payload -> {
						
						payload.response = "STEP1";
						System.out.println(payload.response);
						
						System.out.println("before throw an error");
						throw new RuntimeException("ERROR 1");
						})
					
					.step(payload -> {
						
						payload.response = payload.response + "-STEP2";
						System.out.println(payload.response);
						
						});
				})
				.compose();
		
		String result = new ProcessDirector<String, String>()
			.composition(c)
			.onException(ex -> {
				System.out.println("** handling exception...");
				assertThat(ex.getException().getClass()).isEqualTo(RuntimeException.class);
				assertThat(ex.getException().getMessage()).isEqualTo("ERROR 1");
				ex.resume();
				System.out.println("** exception mark as resumeable");
			})
			.process("ONE");
		
		assertThat(result).isEqualTo("STEP1-STEP2");
		
		System.out.println("--END testResumeExecutionAfterExceptionThrown");
	}
	
	@Test(expected = CompositionException.class)
	public void testExceptionBubbleUpWithoutOnExceptionDeclaration() {
		
		System.out.println("--BEGIN testExceptionBubbleUpWithoutOnExceptionDeclaration");	
	
		ProcessComposition<String, String> c = new ProcessComposer<String, String>()
				
				.sequence(s -> {
					
					s.step(payload -> {
						
						payload.response = "STEP1";
						System.out.println(payload.response);
						
						System.out.println("before throw an error");
						throw new RuntimeException("ERROR 1");
						})
					
					.step(payload -> {
						
						payload.response = "STEP2";
						System.out.println(payload.response);
						
						});
				})
				.compose();
		
		String result = new ProcessDirector<String, String>()
			.composition(c)
			.process("ONE");
			
		System.out.println("--END testExceptionBubbleUpWithoutOnExceptionDeclaration");		
	}

}
