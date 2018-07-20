package org.autanaframework.demo.composer;

import java.util.List;

import org.autanaframework.demo.composer.declarators.StepDeclarator;

public final class StepComposer<R,T> extends AbstractComposer<R,T> implements IStepLinker<R,T> {

	private final IStepLinker<R,T> stepLinker;
	
	protected StepComposer(IProcessComposer<R,T> composer, IStepLinker<R,T> stepLinker) {
		super(composer);
		this.stepLinker =stepLinker;
	}
	
	@Override
	public StepComposer<R,T> step(StepDeclarator<R,T> stepDeclaratorFunction) {
		stepLinker.getSteps().add(new ExecutionStep<R,T>(stepDeclaratorFunction));
		return new StepComposer<R, T>(super.getComposer(), stepLinker);
	}

	@Override
	public List<Step<R,T>> getSteps() {
		return stepLinker.getSteps();
	}
}
