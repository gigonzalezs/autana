package org.autanaframework.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigInteger;

import org.autanaframework.demo.composer.ProcessComposer;
import org.autanaframework.demo.composition.ProcessComposition;
import org.autanaframework.demo.director.CompositionException;
import org.autanaframework.demo.director.ProcessDirector;
import org.junit.Test;

public class BasicCompositionTests {

	@Test
	public void testSecuentialMaths() {
	
		System.out.println("--BEGIN testSecuentialMaths");
		ProcessComposition<BigInteger, BigInteger> c = new ProcessComposer<BigInteger, BigInteger>()
				.sequence(seq -> {
					
					seq.step(py -> {
						
						py.response = py.request
										.multiply(BigInteger.valueOf(2));
						})
					.step(py -> {
						
						py.set("A", BigInteger.valueOf(5));
						py.set("B", BigInteger.valueOf(2));
					})
					.step(py -> {
						
						BigInteger a = (BigInteger) py.vars("A")
								.orElse(BigInteger.ZERO);
						BigInteger b = (BigInteger) py.vars("B")
								.orElse(BigInteger.ZERO);
						
						py.response = py.response
								.add(a)
								.add(b);
					});
				})
				.sequence(seq -> {
					seq.step(py -> {
						System.out.println(py.response);
					});
				})
				.compose();
			
		assertThat(c).isNotNull();
		assertThat(c.getSequences()).isNotNull();
		assertThat(c.getSequences().size()).isEqualTo(2);
		assertThat(c.getSequences().get(0).getSteps()).isNotNull();
		assertThat(c.getSequences().get(0).getSteps().size()).isEqualTo(3);
		
		ProcessDirector<BigInteger, BigInteger> director = new ProcessDirector<>();
		
		BigInteger result = director
				.composition(c)
				.process(BigInteger.valueOf(5));
		
		assertThat(result).isEqualTo(BigInteger.valueOf(17));
		System.out.println("--END testSecuentialMaths");
	}
	
	}
