package io.yamia.autana.samples.monitors;

import io.yamia.autana.director.exceptions.DebuggerInterruptionException;
import io.yamia.autana.monitor.SysOutDebuggerMonitor;

public class SysOutDebuggerInterrupterMonitor<R,T> extends SysOutDebuggerMonitor<R,T> {
	
	@Override
	protected void pause() {
		System.out.println("\r\r>>>> Sending process terminate signal...\r\n");
		throw new DebuggerInterruptionException();			
	}

}
