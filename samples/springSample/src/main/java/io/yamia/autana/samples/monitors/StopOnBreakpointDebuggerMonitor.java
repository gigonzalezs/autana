package io.yamia.autana.samples.monitors;

import io.yamia.autana.director.exceptions.DebuggerInterruptionException;
import io.yamia.autana.monitor.DebuggerMonitor;

public class StopOnBreakpointDebuggerMonitor<R,T> extends DebuggerMonitor<R,T> {
	
	@Override
	protected void pause() {
		System.out.println("\r\r>>>> Sending process terminate signal...\r\n");
		throw new DebuggerInterruptionException();			
	}

}
