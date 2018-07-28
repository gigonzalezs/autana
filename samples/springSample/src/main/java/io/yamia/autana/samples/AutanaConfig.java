package io.yamia.autana.samples;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.yamia.autana.director.ProcessDirector;
import io.yamia.autana.samples.monitors.SysOutDebuggerInterrupterMonitor;

@Configuration
public class AutanaConfig {

	@Bean
	public ProcessDirector<Integer, Integer> getDirector() {
		return new ProcessDirector<Integer, Integer>();
	}
	
	@Bean
	public SysOutDebuggerInterrupterMonitor<Integer,Integer> getDebugger() {
		return new SysOutDebuggerInterrupterMonitor<Integer, Integer>();
	}
}
