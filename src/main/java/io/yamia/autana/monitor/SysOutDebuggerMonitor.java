package io.yamia.autana.monitor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Predicate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.yamia.autana.director.Payload;
import io.yamia.autana.director.exceptions.DebuggerInterruptionException;

public class SysOutDebuggerMonitor<R,T> implements IExecutionMonitor<R,T> {

	private static final ObjectMapper mapper = new ObjectMapper();
	private int current_identation= 0;
	private final List<BreakPoint<R,T>> breakpoints = new ArrayList<>();
	Scanner keyboard = new Scanner(System.in);
	private boolean interactionEnabled = false;
	private Payload<R, T> lastPayload;
	
	public static class BreakPoint<R,T> {
		public String path;
		public Predicate<Payload<R,T>> predicate;
		
		public BreakPoint(String path, Predicate<Payload<R,T>> predicate) {
			this.path = path;
			this.predicate = predicate;
		}
	}
	
	public String getLastPayload() {
		return getPayloadAsJson(lastPayload);
	}
	
	public List<BreakPoint<R,T>> getBreakpoints() {
		return breakpoints;
	}
	
	public SysOutDebuggerMonitor<R,T> addBreakPoint(String path) {
		breakpoints.add(new BreakPoint<R,T>(path, null));
		return this;
	}
	
	public SysOutDebuggerMonitor<R,T> addBreakPoint(String path, Predicate<Payload<R,T>> predicate) {
		breakpoints.add(new BreakPoint<R,T>(path, predicate));
		return this;
	}
	
	public SysOutDebuggerMonitor<R,T> interactive() {
		this.interactionEnabled = true;
		return this;
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
		
		Optional<BreakPoint<R,T>> breakPoint = breakpoints.stream()
		.filter(b -> b.path.compareTo(path) == 0)
		.findFirst();
		
		if (breakPoint.isPresent()) {
			if (breakPoint.get().predicate == null 
			   || breakPoint.get().predicate.test(payload) == true) {
				System.out.println("\r\r>>>> BreakPoint reached at: " + path);
				System.out.println("\r>>> payload: " + getPayloadAsJson(payload));
				pause();
			}
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
			if (current_identation < 0) current_identation = 0;
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
	
	protected void pause() {
		System.out.print("\r\rcontinue? {Y/n} > ");
		if (!this.interactionEnabled) {
			
			System.out.println("user interaction simulation 5000ms...");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Y");
		}
		else {
			
			String res = keyboard.nextLine();
			System.out.print("\r\r");
			
			if (res.toLowerCase().trim().equals("n")) {
				throw new DebuggerInterruptionException();
			}
		}
	}

}
