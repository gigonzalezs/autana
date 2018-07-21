package org.autanaframework.director;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Payload<R, T> {
	
	public final R request;
	
	public T response;
	
	private Map<String, Object> payload_vars = new HashMap<>();
	
	public Payload(R request, T response) {
		this.request=request;
		this.response =response;
	}
	
	public Payload(R request) {
		this(request, null);
	}
	
	public Optional<Object> vars(String varName) {
		return Optional.ofNullable(payload_vars.get(varName));
	}
	
	public Payload<R,T> set(String varName, Object value) {
		this.payload_vars.put(varName, value);
		return this;
	}
}
