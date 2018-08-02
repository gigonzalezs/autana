package io.iunigo.autana.recovery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.iunigo.autana.director.Payload;
import io.iunigo.autana.director.exceptions.DebuggerInterruptionException;
import io.iunigo.autana.monitor.IExecutionMonitor;
import io.iunigo.autana.monitor.MonitorAction;

public class SysOutDebuggerInterrupterMonitor<R,T> implements IExecutionMonitor<R,T> {

	private static final ObjectMapper mapper = new ObjectMapper();
	private int current_identation= 0;
	private final List<String> breakpoints = new ArrayList<>();
	private Payload<R, T> lastPayload;
	
	public String getLastPayload() {
		return getPayloadAsJson(lastPayload);
	}

	public List<String> getBreakpoints() {
		return breakpoints;
	}

	@Override
	public void start(Payload<R, T> payload) {
		lastPayload = payload;
		log(MonitorAction.START, null, payload, false);
	}

	@Override
	public void executing(String path, Payload<R, T> payload) {
		lastPayload = payload;
		log(MonitorAction.EXECUTING , path, payload, false);
		if (breakpoints.contains(path)) {
			System.out.println("\r\r>>>> BreakPoint reached at: " + path);
			System.out.println("\r>>>> payload: " + getPayloadAsJson(payload));
			pause();
		}
	}

	@Override
	public void success(String path, Payload<R, T> payload) {
		lastPayload = payload;
		log(MonitorAction.SUCCESS, path, payload, false);
	}

	@Override
	public void fail(String path, Payload<R, T> payload, boolean resumeable) {
		lastPayload = payload;
		log(MonitorAction.FAIL, path, payload, resumeable);
	}

	@Override
	public void end(Payload<R, T> payload) {
		lastPayload = payload;
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
	
	private String getPayloadAsJson(Payload<R,T> payload) {
		try {
			return mapper.writeValueAsString(payload);
		} catch (JsonProcessingException e) {
			return "{unable to serialize payload as json}";
		}
	}
	
	private void pause() {
		System.out.println("\r\r>>>> Sending terminate process execution signal...\r\n");
		throw new DebuggerInterruptionException();			
	}

}
