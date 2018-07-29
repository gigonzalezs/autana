package io.yamia.autana.director;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicBoolean;

import io.yamia.autana.composer.ExceptionHandler;
import io.yamia.autana.composer.declarators.ExceptionHandlerDeclarator;
import io.yamia.autana.composition.AbstractComposition;
import io.yamia.autana.composition.ContainerComposition;
import io.yamia.autana.composition.JavaStepComposition;
import io.yamia.autana.composition.LoopComposition;
import io.yamia.autana.composition.ProcessComposition;
import io.yamia.autana.director.exceptions.CompositionException;
import io.yamia.autana.director.exceptions.DebuggerInterruptionException;
import io.yamia.autana.monitor.IExecutionMonitor;
import io.yamia.autana.monitor.LogMonitor;
import io.yamia.autana.monitor.MonitorCollection;
import io.yamia.autana.monitor.SysOutMonitor;
import io.yamia.autana.monitor.SysOutTraceMonitor;

public class ProcessDirector<R,T>  {
	
	private ProcessComposition<R,T> current_composition;
	private ExceptionHandlerDeclarator rootExceptionHandlerDeclarator;
	private MonitorCollection<R,T> monitor = new MonitorCollection<R,T>();
	
	public List<IExecutionMonitor<R,T>> getMonitors() {
		return monitor.getMonitors();
	}

	public ProcessDirector<R,T> composition(ProcessComposition<R,T> composition) {
		current_composition = composition;
		return this;
	}
	
	public ProcessDirector<R,T> onException(ExceptionHandlerDeclarator exceptionHandlerDeclarator) {
		rootExceptionHandlerDeclarator = exceptionHandlerDeclarator;
		return this;
    }
	
	public ProcessDirector<R,T> addCustomMonitor(IExecutionMonitor<R,T> monitor) {
		this.monitor.getMonitors().add(monitor);
		return this;
    }
	
	public ProcessDirector<R,T> addLogMonitor() {
		this.addCustomMonitor(new LogMonitor<R,T>());
		return this;
    }
	
	public ProcessDirector<R,T> addSysOutMonitor() {
		this.addCustomMonitor(new SysOutMonitor<R,T>());
		return this;
    }
	 
	public ProcessDirector<R,T> addSysOutOnlyPathsMonitor() {
		this.addCustomMonitor(new SysOutMonitor<R,T>().disablePayLoad());
		return this;
    }
	
	public ProcessDirector<R,T> addSysOutTraceMonitor() {
		this.addCustomMonitor(new SysOutTraceMonitor<R,T>());
		return this;
    }
	
	public T resume(String nodePath, Payload<R,T> payload) {	
		AbstractComposition<R,T> resumingNode = getNodeByPath(nodePath);
		process(resumingNode, payload);
		return payload.response;
	}
	
	public T process(R request) {
		Payload<R,T> payload = new Payload<>(request);
		process(payload);	
		return payload.response;
	}
	
	public void process(Payload<R,T> payload) {
		AbstractComposition<R, T> startComposition = current_composition.getChildren().get(0);
		process(startComposition, payload);
	}
	
	public void process(AbstractComposition<R, T> startComposition, Payload<R,T> payload) {
		final AtomicBoolean canContinue = new AtomicBoolean(true);
		monitor.start(payload);
		
		AbstractComposition<R, T> composition = startComposition;
		do {
			ExecutionResult<R,T> r = executeStep(composition, payload);
			canContinue.set(r.canContinue());
			if (canContinue.get()) {
				notifyBranchClosing(composition, r.getNextNode(), payload);
				}
			composition = parentIsLoopAndStillExecuting(composition, payload) == false ?
					r.getNextNode()
					: composition.getParent();
		} while (canContinue.get() && composition != null);
		monitor.end(payload);
	}
	
	private boolean parentIsLoopAndStillExecuting(AbstractComposition<R, T> current, Payload<R,T> payload) {
		if (current == null || current.getParent() == null) return false;
		if (!isLastNodeInBranch(current)) return false;
		if (!LoopComposition.class.isAssignableFrom(current.getParent().getClass())) return false;
		
		return isContainerStillExecutable((ContainerComposition<R, T>) current.getParent(), payload);
	}
	
	private boolean isLastNodeInBranch(AbstractComposition<R, T> current) {
		if (current != null && current.getParent() != null
				&& ContainerComposition.class.isAssignableFrom(current.getParent().getClass())) {
			ContainerComposition<R,T> container = (ContainerComposition<R, T>) current.getParent();
			return (container.getSteps().get(container.getSteps().size()-1).equals(current));
		} else {
			return false;
		}
	}
	
	private void notifyBranchClosing(AbstractComposition<R, T> current, 
			AbstractComposition<R, T> next, Payload<R,T> payload) {
		
		if (isLastNodeInBranch(current)) {
			monitor.success(current.getParent().getNodeName(), payload);
		}
	}
	
	private boolean isContainerExecutable(ContainerComposition<R, T> container, Payload<R,T> payload) {
		return container.isConditional() == false 
				||  (container.isConditional() == true 
				&& container.getPredicate().test(payload) == true);
	}
	
	private boolean isContainerStillExecutable(ContainerComposition<R, T> container, Payload<R,T> payload) {
		return container.isConditional() == true 
				&& container.isLoopEnabed() == true 
				&& container.getPredicate().test(payload) == true;
	}
	
	private AbstractComposition<R,T> getNodeByPath(String nodePath) {
		final Stack<AbstractComposition<R, T>> childs = new Stack<>();
		childs.push(current_composition);
		nodePath = nodePath.substring(2);
		Arrays.stream(nodePath.split("/"))
		.map(s -> s.replaceAll("[^\\d.]", ""))
		.map(Integer::valueOf)
		.forEach(index -> {
			final AbstractComposition<R, T> node = childs.peek();
			if (ProcessComposition.class.isAssignableFrom(node.getClass())) {
				 childs.push(((ProcessComposition<R, T>) node).getChildren().get(index-1));
				 
			} else if (ContainerComposition.class.isAssignableFrom(node.getClass())) {
				 childs.push(((ContainerComposition<R, T>) node).getSteps().get(index-1));
			}
		});
		AbstractComposition<R,T> result = childs.pop();
		childs.clear();
		return result;
	}
	
	private ExecutionResult<R,T> executeStep(AbstractComposition<R,T> step, Payload<R,T> payload) {
		
		if (JavaStepComposition.class.isAssignableFrom(step.getClass())) {
			JavaStepComposition<R,T> executionStep = (JavaStepComposition<R, T>) step;
			try {
				monitor.executing(executionStep.getNodeName(), payload);
				executionStep.getSnippetFunction().execute(payload);
				monitor.success(executionStep.getNodeName(), payload);
				return new ExecutionResult<R,T>(true, step.getNextNode());
			} catch (DebuggerInterruptionException dbgEx) {
				return new ExecutionResult<R,T>(false, null);
			} catch (Throwable t) {
				boolean resumeable = handleExecutionException(t);
				monitor.fail(executionStep.getNodeName(), payload, resumeable);
				return new ExecutionResult<R,T>(resumeable, step.getNextNode());
			}
			
		} else if (ContainerComposition.class.isAssignableFrom(step.getClass())) {
			ContainerComposition<R,T> containerStep = (ContainerComposition<R, T>) step;
			return executeContainer(containerStep, payload);
		} else {
			return new ExecutionResult<R,T>(false, null);
		}
	}
	
	private ExecutionResult<R,T> executeContainer(ContainerComposition<R, T> container, Payload<R,T> payload) {
		monitor.executing(container.getNodeName(), payload);
		
		if (container.isParallel()) {
			//TODO: parallel no son recuperables por ahora. deberia iniciar una nueva instancia de otro proceso
			// y suspender el actual hasta que las sub-instancias terminen
			
			ExecutionResult<R,T> r = new ExecutionResult<R,T>(
					container.getSteps().stream().parallel()
					.map(t -> executeStep(t, payload))
					.allMatch(ExecutionResult::canContinue), container.getNextNode());
			monitor.success(container.getNodeName(), payload);
			return r;
		} 
		
		// applies to sequential, yaw and loop
		if (isContainerExecutable(container, payload)) {
			return new ExecutionResult<R,T>(
					true, container.getSteps().get(0)); 
		} else {
			monitor.success(container.getNodeName(), payload);
			return new ExecutionResult<R,T>(
					true, container.getNextNode()); 
		}
	
	}
	
	private boolean handleExecutionException(Throwable t) {
		if (rootExceptionHandlerDeclarator != null) {
			ExceptionHandler handler = new ExceptionHandler(t);
			rootExceptionHandlerDeclarator.handle(handler);
			return handler.isResumeable();
		} else {
			throw new CompositionException(t);
		}
	}
}
