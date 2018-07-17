package org.arepoframework.demo;


import static org.assertj.core.api.Assertions.assertThat;

import org.arepoframework.demo.composer.ProcessComposer;
import org.arepoframework.demo.composer.ProcessComposition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArepoDemoApplicationTests {

	@Test
	public void contextLoads() {
		
		ProcessComposition p = ProcessComposer
				.mainSequence()
				.task()
				.task()
				.task()
				.compose();
		
		assertThat(p).isNotNull();
		
	}

}
