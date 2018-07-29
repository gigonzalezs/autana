package io.yamia.autana.samples;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.yamia.autana.director.ProcessDirector;
import io.yamia.autana.samples.monitors.StopOnBreakpointDebuggerMonitor;

@Configuration
public class AutanaConfig {

	@Bean
	public ProcessDirector<Integer, Integer> getDirector() {
		return new ProcessDirector<Integer, Integer>();
	}
	
	@Bean
	public StopOnBreakpointDebuggerMonitor<Integer,Integer> getDebugger() {
		return new StopOnBreakpointDebuggerMonitor<Integer, Integer>();
	}
}
