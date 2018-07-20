package org.arepoframework.demo;


import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigInteger;

import org.autanaframework.demo.composer.ProcessComposer;
import org.autanaframework.demo.composition.ProcessComposition;
import org.autanaframework.demo.director.CompositionException;
import org.autanaframework.demo.director.ProcessDirector;
import org.junit.Test;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class ArepoDemoApplicationTests {

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
	
	@Test
	public void testParallel() {
	
		System.out.println("--BEGIN testParallel");
		ProcessComposition<String, String> c = new ProcessComposer<String, String>()
				.parallel(pll -> {
					
					pll.step(py -> {

						System.out.println("tarea 1");
						py.response = "1";
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					})
					.step(py -> {
						
						System.out.println("tarea 2");
						py.response = "2";
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					})
					.step(py -> {
						
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
					
					seq.step(py -> {
						
						System.out.println("fin.");
						System.out.println(py.response);
					});
				})
				.compose();
		
	
		assertThat(c).isNotNull();
		assertThat(c.getSequences()).isNotNull();
		assertThat(c.getSequences().size()).isEqualTo(2);
		assertThat(c.getSequences().get(0).getSteps()).isNotNull();
		assertThat(c.getSequences().get(0).getSteps().size()).isEqualTo(3);
		
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
					
					seq.step(py -> {
						
						System.out.println("tarea 1");
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						})
					.step(py -> {
						
						System.out.println("tarea 2");
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					})
					.step(py -> {
						
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
					seq.step(py -> {
						System.out.println("fin.");
					});
				})
				.compose();
		
	
		assertThat(c).isNotNull();
		assertThat(c.getSequences()).isNotNull();
		assertThat(c.getSequences().size()).isEqualTo(2);
		assertThat(c.getSequences().get(0).getSteps()).isNotNull();
		assertThat(c.getSequences().get(0).getSteps().size()).isEqualTo(3);
		
		ProcessDirector<String, String> director = new ProcessDirector<>();
		
		
		String result = director
			.composition(c)
			.process("hola process!");
		
		System.out.println("--END testSequence");
		
	}

	@Test
	public void testLoop() {
	
		System.out.println("--BEGIN testLoop");
		ProcessComposition<String, String> c = new ProcessComposer<String, String>()
				
				.sequence(seq -> {
					seq.step(py -> {
						py.set("count", 0);
						py.set("max", 5);
					});
				})
				.loop(l -> {
					
					l.predicate(py -> {
						int count = (int) py.vars("count").get();
						int max = (int) py.vars("max").get();
						return count < max;
					})
					
					.step(py -> {
						
						System.out.println("loop");
						int count = (int) py.vars("count").get();
						System.out.println(count);
						count++;
						py.set("count", count);
						py.response = String.valueOf(count);
					});
				})
				.sequence(seq -> {
					seq.step(py -> {
						System.out.println("fin.");
					});
				})
				.compose();
		
	
		assertThat(c).isNotNull();
		assertThat(c.getSequences()).isNotNull();
		assertThat(c.getSequences().size()).isEqualTo(3);
	
		
		ProcessDirector<String, String> director = new ProcessDirector<>();
		
		
		String result = director
			.composition(c)
			.process("UNO");
		
		System.out.println(result);
		
		assertThat(result).isEqualTo("5");
		
		System.out.println("--END testLoop");
		
	}
	
	@Test
	public void testCondition() {
	
		System.out.println("--BEGIN testCondition");
		ProcessComposition<String, String> c = new ProcessComposer<String, String>()
				
				.yaw(con -> {
					
					con.predicate(py -> py.request.equals("UNO"))
					
					.step(py -> {
						
						System.out.println("tarea 1-A debe ejecutarse");
						py.response = "EJECUTADO1";
						})
					.step(py -> {
						
						System.out.println("tarea 2-A debe ejecutarse");
						py.response = py.response + "-EJECUTADO2";
					});
				})
				.yaw(con -> {
					
					con.predicate(py -> py.request.equals("DOS"))
					
					.step(py -> {
						
						System.out.println("tarea 1-B NO debe ejecutarse");
						throw new RuntimeException("tarea 1-B NO debe ejecutarse");
						})
					.step(py -> {
						
						System.out.println("tarea 2-B NO debe ejecutarse");
						throw new RuntimeException("tarea 2-B NO debe ejecutarse");
					});
				})
				.sequence(seq -> {
					seq.step(py -> {
						System.out.println("fin.");
					});
				})
				.compose();
		
	
		assertThat(c).isNotNull();
		assertThat(c.getSequences()).isNotNull();
		assertThat(c.getSequences().size()).isEqualTo(3);
		assertThat(c.getSequences().get(0).getSteps()).isNotNull();
		assertThat(c.getSequences().get(0).getSteps().size()).isEqualTo(2);
		
		ProcessDirector<String, String> director = new ProcessDirector<>();
		
		
		String result = director
			.composition(c)
			.process("UNO");
		
		assertThat(result).isEqualTo("EJECUTADO1-EJECUTADO2");
		
		System.out.println("--END testCondition");
		
	}
	
	@Test
	public void testNestedSequences() {
	
		System.out.println("--BEGIN testNestedSequences");
		ProcessComposition<String, String> c = new ProcessComposer<String, String>()
				
				.sequence(seq -> {
					
					seq.sequence(seq2 -> {
						
						seq2.step(py -> {
							
							System.out.println("tarea 1-A debe ejecutarse");
							py.response = "EJECUTADO1";
						})
						.step(py -> {
							
							System.out.println("tarea 2-A debe ejecutarse");
							py.response = py.response + "-EJECUTADO2";
						});
					})
					.sequence(seq2 -> {
						
						seq2.step(py -> {
							
							System.out.println("tarea 1-B debe ejecutarse");
							py.response = py.response + "-EJECUTADO3";
						})
						.step(py -> {
							
							System.out.println("tarea 2-B debe ejecutarse");
							py.response = py.response + "-EJECUTADO4";
						});
					})
					.step(py -> {
						System.out.println("tarea 0-A debe ejecutarse");
						py.response = py.response + "-EJECUTADO5";
					});
				})
				.sequence(seq -> {
					seq.step(py -> {
						System.out.println("fin.");
					});
				})
				.compose();
		
	
		assertThat(c).isNotNull();
		assertThat(c.getSequences()).isNotNull();
		assertThat(c.getSequences().size()).isEqualTo(2);
		
		ProcessDirector<String, String> director = new ProcessDirector<>();
		
		
		String result = director
			.composition(c)
			.process("UNO");
		
		assertThat(result).isEqualTo("EJECUTADO1-EJECUTADO2-EJECUTADO3-EJECUTADO4-EJECUTADO5");
		
		System.out.println("--END testNestedSequences");
		
	}
	
	public void testExceptionHandling() {
	
		System.out.println("--BEGIN testCondition");
		ProcessComposition<String, String> c = new ProcessComposer<String, String>()
				
				.yaw(con -> {
					
					con.predicate(py -> py.request.equals("UNO"))
					
					.step(py -> {
						
						System.out.println("tarea 1-A debe ejecutarse");
						py.response = "EJECUTADO1";
						throw new RuntimeException("tarea 1-A ERROR");
						})
					.step(py -> {
						
						System.out.println("tarea 2-A debe ejecutarse");
						py.response = py.response + "-EJECUTADO2";
					});
				})
				.yaw(con -> {
					
					con.predicate(py -> py.request.equals("DOS"))
					
					.step(py -> {
						
						System.out.println("tarea 1-B NO debe ejecutarse");
						throw new RuntimeException("tarea 1-B NO debe ejecutarse");
						})
					.step(py -> {
						
						System.out.println("tarea 2-B NO debe ejecutarse");
						throw new RuntimeException("tarea 2-B NO debe ejecutarse");
					});
				})
				.sequence(seq -> {
					seq.step(py -> {
						System.out.println("fin.");
					});
				})
				.compose();
		
	
		assertThat(c).isNotNull();
		assertThat(c.getSequences()).isNotNull();
		assertThat(c.getSequences().size()).isEqualTo(3);
		assertThat(c.getSequences().get(0).getSteps()).isNotNull();
		assertThat(c.getSequences().get(0).getSteps().size()).isEqualTo(2);
		
		ProcessDirector<String, String> director = new ProcessDirector<>();
		
		
		String result = director
			.composition(c)
			.onException(ex -> {
				assertThat(ex.getException().getClass()).isEqualTo(RuntimeException.class);
				assertThat(ex.getException().getMessage()).isEqualTo("tarea 1-A ERROR");
			})
			.process("UNO");
		
		assertThat(result).isEqualTo("EJECUTADO1-EJECUTADO2");
		
		System.out.println("--END testCondition");
		
	}
	
	@Test(expected = CompositionException.class)
	public void testExceptionBubbleUp() {
	
		System.out.println("--BEGIN testCondition");
		ProcessComposition<String, String> c = new ProcessComposer<String, String>()
				
				.yaw(con -> {
					
					con.predicate(py -> py.request.equals("UNO"))
					
					.step(py -> {
						
						System.out.println("tarea 1-A debe ejecutarse");
						py.response = "EJECUTADO1";
						throw new RuntimeException("tarea 1-A ERROR");
						})
					.step(py -> {
						
						System.out.println("tarea 2-A debe ejecutarse");
						py.response = py.response + "-EJECUTADO2";
					});
				})
				.yaw(con -> {
					
					con.predicate(py -> py.request.equals("DOS"))
					
					.step(py -> {
						
						System.out.println("tarea 1-B NO debe ejecutarse");
						throw new RuntimeException("tarea 1-B NO debe ejecutarse");
						})
					.step(py -> {
						
						System.out.println("tarea 2-B NO debe ejecutarse");
						throw new RuntimeException("tarea 2-B NO debe ejecutarse");
					});
				})
				.sequence(seq -> {
					seq.step(py -> {
						System.out.println("fin.");
					});
				})
				.compose();
		
	
		assertThat(c).isNotNull();
		assertThat(c.getSequences()).isNotNull();
		assertThat(c.getSequences().size()).isEqualTo(3);
		assertThat(c.getSequences().get(0).getSteps()).isNotNull();
		assertThat(c.getSequences().get(0).getSteps().size()).isEqualTo(2);
		
		ProcessDirector<String, String> director = new ProcessDirector<>();
		
		
		String result = director
			.composition(c)
			.process("UNO");
		
		assertThat(result).isEqualTo("EJECUTADO1-EJECUTADO2");
		
		System.out.println("--END testCondition");
		
	}

}
