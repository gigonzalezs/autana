package org.autanaframework.monitor;

import java.util.Arrays;

import org.autanaframework.director.Payload;

public class SysOutTraceMonitor<R,T> implements IExecutionMonitor<R,T> {

	private int current_identation= 0;
	
	@Override
	public void start(Payload<R, T> payload) {
		log(MonitorAction.START, null, payload, false);
	}

	@Override
	public void executing(String path, Payload<R, T> payload) {
		log(MonitorAction.EXECUTING , path, payload, false);
		
	}

	@Override
	public void success(String path, Payload<R, T> payload) {
		log(MonitorAction.SUCCESS, path, payload, false);
	}

	@Override
	public void fail(String path, Payload<R, T> payload, boolean resumeable) {
		log(MonitorAction.FAIL, path, payload, resumeable);
	}

	@Override
	public void end(Payload<R, T> payload) {
		log(MonitorAction.END, null, payload, false);
	}
	
	private void log(MonitorAction action, String path, Payload<R,T> payload, boolean resumeable) {
		String message = getIdentation(action).concat(getLastNode(path));
		if (action == MonitorAction.EXECUTING) {
			System.out.println(message);
		} else if (action == MonitorAction.FAIL) {
			System.out.println("*** FAIL ***: ".concat(path));	
		}
	}
	
	private String getIdentation(MonitorAction action) {
		if (action == MonitorAction.EXECUTING) {
			current_identation += 3;
		} else if (action == MonitorAction.FAIL || action == MonitorAction.SUCCESS) {
			current_identation -=3;
		}
		return new String(new char[current_identation]).replace("\0", "-");
	}
	
	private String getLastNode(String path) {
		return path ==  null ? "/" : Arrays.stream(path.split("/")).reduce((first, second) -> second).get();
	}
	
	

}
