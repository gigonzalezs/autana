package io.yamia.autana.composer;

public final class ExceptionHandler {
	private final Throwable exception;
	private boolean resumeable = false;
	
	public ExceptionHandler(Throwable exception) {
		this.exception = exception;
	}

	public Throwable getException() {
		return exception;
	}

	public boolean isResumeable() {
		return resumeable;
	}

	public void resume() {
		this.resumeable = true;
	}
	
	
}
