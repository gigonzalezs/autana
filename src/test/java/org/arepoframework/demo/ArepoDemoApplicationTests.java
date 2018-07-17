package org.arepoframework.demo;


import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigInteger;

import org.arepoframework.demo.composer.ProcessComposer;
import org.arepoframework.demo.composition.ProcessComposition;
import org.arepoframework.demo.director.ProcessDirector;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArepoDemoApplicationTests {

	@Test
	public void contextLoads() {
	
		ProcessComposition<BigInteger, BigInteger> c = new ProcessComposer<BigInteger, BigInteger>()
				.sequence(seq -> {
					
					seq.task(py -> {
						
						py.response = py.request
										.multiply(BigInteger.valueOf(2));
						})
					.task(py -> {
						
						py.set("A", BigInteger.valueOf(5));
						py.set("B", BigInteger.valueOf(2));
					})
					.task(py -> {
						
						BigInteger a = (BigInteger) py.vars("A")
								.orElse(BigInteger.ZERO);
						BigInteger b = (BigInteger) py.vars("B")
								.orElse(BigInteger.ZERO);
						
						py.response = py.response
								.add(a)
								.add(b);
					});
				})
				.compose();
		
	
		assertThat(c).isNotNull();
		assertThat(c.getSequences()).isNotNull();
		assertThat(c.getSequences().size()).isEqualTo(1);
		assertThat(c.getSequences().get(0).getTasks()).isNotNull();
		assertThat(c.getSequences().get(0).getTasks().size()).isEqualTo(3);
		
		ProcessDirector<BigInteger, BigInteger> director = new ProcessDirector<>();
		
		
		BigInteger result = director
				.composition(c)
				.process(BigInteger.valueOf(5));
		
		assertThat(result).isEqualTo(BigInteger.valueOf(17));
	}

}
