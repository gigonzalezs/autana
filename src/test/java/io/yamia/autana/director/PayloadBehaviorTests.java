package io.yamia.autana.director;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigInteger;

import org.junit.Test;

import io.yamia.autana.composition.ProcessComposition;
import io.yamia.autana.director.ProcessDirector;

public class PayloadBehaviorTests {

	@Test
	public void testSecuentialMaths() {
	
		System.out.println("--BEGIN testSecuentialMaths");
		
		ProcessComposition<BigInteger, BigInteger> composition = new ProcessComposition<BigInteger, BigInteger>()
				.createFromDeclarativeCode()
				.sequence(container -> {
					container.step(payload -> {
						payload.response = payload.request
										.multiply(BigInteger.valueOf(2));
						})
					.step(payload -> {
						payload.set("A", BigInteger.valueOf(5));
						payload.set("B", BigInteger.valueOf(2));
					})
					.step(payload -> {
						BigInteger a = (BigInteger) payload.vars("A")
								.orElse(BigInteger.ZERO);
						BigInteger b = (BigInteger) payload.vars("B")
								.orElse(BigInteger.ZERO);
						payload.response = payload.response
								.add(a)
								.add(b);
					});
				})
				.sequence(container -> {
					container.step(payload -> {
						System.out.println(payload.response);
					});
				})
				.compose();

		ProcessDirector<BigInteger, BigInteger> director = new ProcessDirector<>();
		
		BigInteger result = director
				.composition(composition)
				.process(BigInteger.valueOf(5));
		
		assertThat(result).isEqualTo(BigInteger.valueOf(17));
		
		System.out.println("--END testSecuentialMaths");
	}
}
