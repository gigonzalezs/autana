package io.yamia.autana.exceptions;

import org.junit.Ignore;

@Ignore
public class ExceptionHandlingTests {
	/*
	@Test
	public void testExceptionHandlingWhenOnExceptionDeclaration() {
		
		System.out.println("--BEGIN testExceptionHandlingWhenOnExceptionDeclaration");
		ProcessComposition<String, String> composition = new ProcessComposition<String, String>()
				.createFromDeclarativeCode()	
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
			.composition(composition)
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
		ProcessComposition<String, String> composition = new ProcessComposition<String, String>()
				.createFromDeclarativeCode()
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
			.composition(composition)
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
	
		ProcessComposition<String, String> composition = new ProcessComposition<String, String>()
				.createFromDeclarativeCode()
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
		
		new ProcessDirector<String, String>()
			.composition(composition)
			.process("ONE");
			
		System.out.println("--END testExceptionBubbleUpWithoutOnExceptionDeclaration");		
	}
*/
}
