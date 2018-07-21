package org.autanaframework.composition;

public class AbstractComposition<R, T> {
	
	private static final String PATH_SEPARATOR = "/"; 
	private static final String COMPOSITION_SUFFIX= "composition";
	private static final String EMPTY= "";
	private final AbstractComposition<R,T> parentComposition;
	private final String nodeName;
	
	protected AbstractComposition (AbstractComposition<R,T> parentComposition) {
		this.parentComposition = parentComposition;
		int nodePosition = calculatePosition(parentComposition);
		this.nodeName = this.parentComposition == null ? "/" 
			: this.parentComposition.getNodeName()
				.concat(PATH_SEPARATOR)
				.concat(this.getClass().getSimpleName().toLowerCase().replace(COMPOSITION_SUFFIX, EMPTY))
				.concat(String.valueOf(nodePosition));
	}

	public AbstractComposition<R,T> getParent() {
		return parentComposition;
	}

	public String getNodeName() {
		return nodeName;
	}
	
	private int calculatePosition(AbstractComposition<R,T> parentComposition) {
		if (parentComposition != null ) {
			if (ContainerComposition.class.isAssignableFrom(parentComposition.getClass())) {
				return ((ContainerComposition<R,T>) parentComposition).getSteps().size() + 1;
			}
			else if (ProcessComposition.class.isAssignableFrom(parentComposition.getClass())) {
				return ((ProcessComposition<R,T>) parentComposition).getChildren().size() + 1;
			}
		}
		return 0;
	}
}
