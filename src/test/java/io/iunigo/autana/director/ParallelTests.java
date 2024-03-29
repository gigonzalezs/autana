package io.iunigo.autana.director;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.junit.AssumptionViolatedException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;

import io.iunigo.autana.composition.ProcessComposition;
import io.iunigo.autana.director.ProcessDirector;


public class ParallelTests {
	
	
	private static final Logger logger = Logger.getLogger(ParallelTests.class.getName());

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
	public void testParallel() {
	
		System.out.println("--BEGIN testParallel");
		
		ProcessComposition<String, String> composition = new ProcessComposition<String, String>()
				.createFromDeclarativeCode()
				.parallel(container -> {
					container.step(payload -> {
						System.out.println("step 1");
						payload.var("A").setString("STEP1");
						System.out.println("work simulation 2000ms...");
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					})
					.step(payload -> {
						System.out.println("step 2");
						payload.var("B").setString("-STEP2");
						System.out.println("work simulation 2000ms...");
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					})
					.step(payload -> {
						System.out.println("step 3");
						payload.var("C").setString("-STEP3");
						System.out.println("work simulation 2000ms...");
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					});
				})
				.sequence(container -> {
					container.step(payload -> {
						payload.response = 
								payload.var("A").getString() +
								payload.var("B").getString() +
								payload.var("C").getString();
						System.out.println("end.");
					});
				})
				.compose();
		
		String result = new ProcessDirector<String, String>()
			.composition(composition)
			.process("hello process!");
		
		assertThat(result).isEqualTo("STEP1-STEP2-STEP3");
		
		long delta = 500;
	    assertEquals(2000d, stopwatch.runtime(TimeUnit.MILLISECONDS), delta);
		
		System.out.println("--END testParallel");
		
	}
	
}
