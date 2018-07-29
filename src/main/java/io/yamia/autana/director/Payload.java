package io.yamia.autana.director;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Payload<R, T> {
	
	public R request;
	
	public T response;
	
	private Map<String, Variable<?>> payload_vars = new HashMap<>();
	
	public Payload() {
	}
	
	public Payload(R request, T response) {
		this.request=request;
		this.response =response;
	}
	
	public Payload(R request) {
		this(request, null);
	}
	
	public Variable<?> var(String varName) {
		return this.payload_vars.containsKey(varName) ?
				this.payload_vars.get(varName)
				: Variable.emptyVariable(varName, payload_vars);
	}
	
	public static class Variable<X> {
		private final Map<String, Variable<?>> payload_vars;
		private final String name;
		private Optional<X> value = null;
		private Class<?> clazz;
		
		private Variable(String name, X value, Map<String, Variable<?>> payload_vars) {
			this.payload_vars = payload_vars;
			this.name = name;
			this.value = Optional.ofNullable(value);
			this.clazz = this.value.isPresent()? value.getClass() : null;
		}
		
		static Variable<String> emptyVariable(String name, Map<String, Variable<?>> payload_vars) {
			return new Variable<String>(name, null, payload_vars);
		}
		
		static <Y> Variable<Y> fromValue(String name, Y value, Map<String, Variable<?>> payloadvars) {
			return new Variable<Y>(name, value, payloadvars);
		}
		
		public X get() {
			return value.orElse(null);
		}
		
		private <Y> void set(Y value) {
			Variable<Y> newVar = Variable.<Y>fromValue(name, value, payload_vars);
			payload_vars.put(name, newVar); 
		}
		
		public boolean isPresent() {
			return value.isPresent();
		}
		
		public X orElse(X other) {
	        return value.orElse(other);
	    }
		
		public X orElseGet(Supplier<X> other) {
	        return value.orElseGet(other);
	    }
		
		public <Y extends Throwable> Object orElseThrow(Supplier<? extends Y> exceptionSupplier) throws Y {
	       return value.orElseThrow(exceptionSupplier);
	    }
		
		public<U> Optional<U> flatMap(Function<X, Optional<U>> mapper) {
	        return value.flatMap(mapper);
	    }

		public<U> Optional<U> map(Function<? super X, ? extends U> mapper) {
	        return value.map(mapper);
	    }
		
		public Optional<X> filter(Predicate<X> predicate) {
	        return value.filter(predicate);
	    }
		
		public void ifPresent(Consumer<X> consumer) {
		        value.ifPresent(consumer);
		}
		
		public String getString() {
			if (clazz != null && !clazz.isAssignableFrom(String.class)) throw new ClassCastException();
			return value.isPresent()? value.get().toString() : null;
		}
		
		public Integer getInteger() {
			if (clazz != null && !clazz.isAssignableFrom(Integer.class)) throw new ClassCastException();
			return value.isPresent()? (Integer) value.get() : null; 
		} 
		
		public BigInteger getBigInteger() {
			if (clazz != null && !clazz.isAssignableFrom(BigInteger.class)) throw new ClassCastException();
			return value.isPresent()? (BigInteger) value.get() : null; 
		}
		
		public BigDecimal getBigDecimal() {
			if (clazz != null && !clazz.isAssignableFrom(BigDecimal.class)) throw new ClassCastException();
			return value.isPresent()? (BigDecimal) value.get() : null; 
		}
		
		public Boolean getBoolean() {
			if (clazz != null && !clazz.isAssignableFrom(Boolean.class)) throw new ClassCastException();
			return value.isPresent()? (Boolean) value.get() : null; 
		}
		
		public Date getDate() {
			if (clazz != null && !clazz.isAssignableFrom(Date.class)) throw new ClassCastException();
			return value.isPresent()? (Date) value.get() : null; 
		}
		
		public void setString(String value) {
			this.set(value);
		}
		
		public void setInteger(Integer value) {
			this.set(value);
		}
		
		public void setBigInteger(BigInteger value) {
			this.set(value);
		}
		
		public void setBigDecimal(BigDecimal value) {
			this.set(value);
		}
		
		public void setBoolean (Boolean value) {
			this.set(value);
		}
		
		public void setDate(Date value) {
			this.set(value);
		}
	}
}
