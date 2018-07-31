package io.yamia.autana.samples.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import io.yamia.autana.director.ProcessDirector;
import io.yamia.autana.samples.monitors.StopOnBreakpointDebuggerMonitor;

@Configuration
public class AutanaConfig {

	@Bean()
	@Scope("request")
	public ProcessDirector<Integer, Integer> getDirector() {
		return new ProcessDirector<Integer, Integer>();
	}
	
	@Bean
	@Scope("request")
	public StopOnBreakpointDebuggerMonitor<Integer,Integer> getDebugger() {
		return new StopOnBreakpointDebuggerMonitor<Integer, Integer>();
	}
}
