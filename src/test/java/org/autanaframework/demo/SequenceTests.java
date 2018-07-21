package org.autanaframework.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.autanaframework.demo.composition.ProcessComposition;
import org.autanaframework.demo.director.ProcessDirector;
import org.junit.AssumptionViolatedException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;

public class SequenceTests {
	
	private static final Logger logger = Logger.getLogger(SequenceTests.class.getName());

    private static void logInfo(Description description, String status, long nanos) {
        String testName = description.getMethodName();
        logger.info(String.format("Test %s %s, spent %d microseconds",
                                  testName, status, TimeUnit.NANOSECONDS.toMicros(nanos)));
    }
	
	@Rule
    public Stopwatch stopwatch = new Stopwatch() {
        @Override
        protected void succeeded(long nanos, Description description) {
            logInfo(description, "succeeded", nanos);
        }

        @Override
        protected void failed(long nanos, Throwable e, Description description) {
            logInfo(description, "failed", nanos);
        }

        @Override
        protected void skipped(long nanos, AssumptionViolatedException e, Description description) {
            logInfo(description, "skipped", nanos);
        }

        @Override
        protected void finished(long nanos, Description description) {
            logInfo(description, "finished", nanos);
        }
    };

	@Test
	public void testSequence() {
	
		System.out.println("--BEGIN testSequence");
		
		ProcessComposition<String, String> composition = new ProcessComposition<String, String>()
				.createFromDeclarativeCode()
				.sequence(container -> {
					
					container.step(payload -> {
						
						System.out.println("step 1");
						payload.response = "STEP1";
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						})
					.step(payload -> {
						
						System.out.println("step 2");
						payload.response += "-STEP2";
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					})
					.step(payload -> {
						
						System.out.println("step 3");
						payload.response += "-STEP3";
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					});
				})
				.sequence(container -> {
					container.step(payload -> {
						System.out.println("end.");
					});
				})
				.compose();
		
	
		assertThat(composition).isNotNull();
		assertThat(composition.getSequences()).isNotNull();
		assertThat(composition.getSequences().size()).isEqualTo(2);
		assertThat(composition.getSequences().get(0).getSteps()).isNotNull();
		assertThat(composition.getSequences().get(0).getSteps().size()).isEqualTo(3);
		assertThat(composition.getSequences().get(1).getSteps()).isNotNull();
		assertThat(composition.getSequences().get(1).getSteps().size()).isEqualTo(1);
		
		String result = new ProcessDirector<String, String>()
			.composition(composition)
			.process("hello process!");
		
		assertThat(result).isEqualTo("STEP1-STEP2-STEP3");
		
		long delta = 500;
	    assertEquals(6000d, stopwatch.runtime(TimeUnit.MILLISECONDS), delta);
		
		System.out.println("--END testSequence");
		
	}
}
