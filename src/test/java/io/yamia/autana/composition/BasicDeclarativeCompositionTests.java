package io.yamia.autana.composition;


import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigInteger;

import org.junit.Test;

import io.yamia.autana.composition.ContainerComposition;
import io.yamia.autana.composition.ProcessComposition;

public class BasicDeclarativeCompositionTests {

	@Test
	public void testSingleSequenceSingleStepDeclarativeComposition() {
	
		System.out.println("--BEGIN testSingleSequenceSingleStepDeclarativeComposition");
		
		ProcessComposition<BigInteger, BigInteger> composition = new ProcessComposition<BigInteger, BigInteger>()
				.createFromDeclarativeCode()
				.sequence(container -> {				
					container.step(payload -> {					
						payload.response = payload.request;
					});
				})
				.compose();
			
		assertThat(composition).isNotNull();
		assertThat(composition.getChildren()).isNotNull();
		assertThat(composition.getChildren().size()).isEqualTo(1);
		
		ContainerComposition <BigInteger, BigInteger> container = 
				(ContainerComposition<BigInteger, BigInteger>) composition.getChildren().get(0);
		assertThat(container.getSteps()).isNotNull();
		assertThat(container.getSteps().size()).isEqualTo(1);
		
		System.out.println("--END testSingleSequenceSingleStepDeclarativeComposition");
	}
	
	@Test
	public void testMultiSequenceSingleStepDeclarativeComposition() {
	
		System.out.println("--BEGIN testMultiSequenceSingleStepDeclarativeComposition");
		
		ProcessComposition<BigInteger, BigInteger> composition = new ProcessComposition<BigInteger, BigInteger>()
				.createFromDeclarativeCode()
				.sequence(container -> {
					container.step(payload -> {
						payload.response = payload.request;
						});
				})
				.sequence(container -> {
					container.step(payload -> {
						payload.response = payload.request;
					});
				})
				.compose();
			
		assertThat(composition).isNotNull();
		assertThat(composition.getChildren()).isNotNull();
		assertThat(composition.getChildren().size()).isEqualTo(2);
		
		ContainerComposition <BigInteger, BigInteger> container = 
				(ContainerComposition<BigInteger, BigInteger>) composition.getChildren().get(0);
		assertThat(container.getSteps()).isNotNull();
		assertThat(container.getSteps().size()).isEqualTo(1);
		
		ContainerComposition <BigInteger, BigInteger> container2 = 
				(ContainerComposition<BigInteger, BigInteger>) composition.getChildren().get(0);
		assertThat(container2.getSteps()).isNotNull();
		assertThat(container2.getSteps().size()).isEqualTo(1);
		
		System.out.println("--END testMultiSequenceSingleStepDeclarativeComposition");
	}
	
	@Test
	public void testMultiSequenceMultiStepsDeclarativeComposition() {
	
		System.out.println("--BEGIN testMultiSequenceMultiStepsDeclarativeComposition");
		
		ProcessComposition<BigInteger, BigInteger> composition = new ProcessComposition<BigInteger, BigInteger>()
				.createFromDeclarativeCode()
				.sequence(container -> {
					container.step(payload -> {
						payload.response = payload.request;
					})
					.step(payload -> {
						payload.response = payload.request;
					})
					.step(payload -> {
						payload.response = payload.request;
					});
				})
				.sequence(container -> {
					container.step(payload -> {
						payload.response = payload.request;
					});
				})
				.compose();
			
		assertThat(composition).isNotNull();
		assertThat(composition.getChildren()).isNotNull();
		assertThat(composition.getChildren().size()).isEqualTo(2);
		
		ContainerComposition <BigInteger, BigInteger> container = 
				(ContainerComposition<BigInteger, BigInteger>) composition.getChildren().get(0);
		assertThat(container.getSteps()).isNotNull();
		assertThat(container.getSteps().size()).isEqualTo(3);
		
		ContainerComposition <BigInteger, BigInteger> container2 = 
				(ContainerComposition<BigInteger, BigInteger>) composition.getChildren().get(1);
		assertThat(container2.getSteps()).isNotNull();
		assertThat(container2.getSteps().size()).isEqualTo(1);
		
		System.out.println("--END testMultiSequenceMultiStepsDeclarativeComposition");
	}
	
}
