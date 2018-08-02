package io.iunigo.autana.monitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Predicate;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.iunigo.autana.director.Payload;
import io.iunigo.autana.director.exceptions.DebuggerInterruptionException;

public class DebuggerMonitor<R,T> implements IExecutionMonitor<R,T> {

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
	
	public DebuggerMonitor<R,T> addBreakPoint(String path) {
		breakpoints.add(new BreakPoint<R,T>(path, null));
		return this;
	}
	
	public DebuggerMonitor<R,T> addBreakPoint(String path, Predicate<Payload<R,T>> predicate) {
		breakpoints.add(new BreakPoint<R,T>(path, predicate));
		return this;
	}
	
	public DebuggerMonitor<R,T> interactive() {
		this.interactionEnabled = true;
		return this;
	}

	@Override
	public void start(Payload<R, T> payload) {
		lastPayload = payload;
	}

	@Override
	public void executing(String path, Payload<R, T> payload) {
		lastPayload = payload;		
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
	}

	@Override
	public void fail(String path, Payload<R, T> payload, boolean resumeable) {
		lastPayload = payload;
	}

	@Override
	public void end(Payload<R, T> payload) {
		lastPayload = payload;
	}
	
	private String getPayloadAsJson(Payload<R,T> payload) {
		try {
			return payload.toJSONString();
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
