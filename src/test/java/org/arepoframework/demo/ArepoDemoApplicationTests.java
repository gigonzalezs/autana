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
	public void testSecuentialMaths() {
	
		System.out.println("--BEGIN testSecuentialMaths");
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
				.sequence(seq -> {
					seq.task(py -> {
						System.out.println(py.response);
					});
				})
				.compose();
		
	
		assertThat(c).isNotNull();
		assertThat(c.getSequences()).isNotNull();
		assertThat(c.getSequences().size()).isEqualTo(2);
		assertThat(c.getSequences().get(0).getTasks()).isNotNull();
		assertThat(c.getSequences().get(0).getTasks().size()).isEqualTo(3);
		
		ProcessDirector<BigInteger, BigInteger> director = new ProcessDirector<>();
		
		
		BigInteger result = director
				.composition(c)
				.process(BigInteger.valueOf(5));
		
		assertThat(result).isEqualTo(BigInteger.valueOf(17));
		System.out.println("--END testSecuentialMaths");
	}
	
	@Test
	public void testParallel() {
	
		System.out.println("--BEGIN testParallel");
		ProcessComposition<String, String> c = new ProcessComposer<String, String>()
				.parallel(pll -> {
					
					pll.task(py -> {
						
						System.out.println("tarea 1");
						py.response = "1";
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						})
					.task(py -> {
						
						System.out.println("tarea 2");
						py.response = "2";
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					})
					.task(py -> {
						
						System.out.println("tarea 3");
						py.response = "3";
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					});
				})
				.sequence(seq -> {
					
					seq.task(py -> {
						
						System.out.println("fin.");
						System.out.println(py.response);
					});
				})
				.compose();
		
	
		assertThat(c).isNotNull();
		assertThat(c.getSequences()).isNotNull();
		assertThat(c.getSequences().size()).isEqualTo(2);
		assertThat(c.getSequences().get(0).getTasks()).isNotNull();
		assertThat(c.getSequences().get(0).getTasks().size()).isEqualTo(3);
		
		ProcessDirector<String, String> director = new ProcessDirector<>();
		
		
		String result = director
			.composition(c)
			.process("hola process!");
		System.out.println(result);
	
		System.out.println("--END testParallel");
	}
	
	@Test
	public void testSequence() {
	
		System.out.println("--BEGIN testSequence");
		ProcessComposition<String, String> c = new ProcessComposer<String, String>()
				.sequence(seq -> {
					
					seq.task(py -> {
						
						System.out.println("tarea 1");
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						})
					.task(py -> {
						
						System.out.println("tarea 2");
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					})
					.task(py -> {
						
						System.out.println("tarea 3");
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					});
				})
				.sequence(seq -> {
					seq.task(py -> {
						System.out.println("fin.");
					});
				})
				.compose();
		
	
		assertThat(c).isNotNull();
		assertThat(c.getSequences()).isNotNull();
		assertThat(c.getSequences().size()).isEqualTo(2);
		assertThat(c.getSequences().get(0).getTasks()).isNotNull();
		assertThat(c.getSequences().get(0).getTasks().size()).isEqualTo(3);
		
		ProcessDirector<String, String> director = new ProcessDirector<>();
		
		
		String result = director
			.composition(c)
			.process("hola process!");
		
		System.out.println("--END testSequence");
		
	}


}
