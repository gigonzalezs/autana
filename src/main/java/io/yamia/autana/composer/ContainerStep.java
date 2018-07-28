package io.yamia.autana.composer;

public class ContainerStep<R, T> extends Step<R, T> {
	
	private final ContainerComposer<R,T> container;
	
	public ContainerStep(ContainerComposer<R,T> container) {
		super();
		this.container = container;
	}
	
	public ContainerComposer<R,T> getContainer() {
		return container;
	}
}
