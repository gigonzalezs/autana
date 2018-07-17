package org.arepoframework.demo.composer;

import java.util.List;

import org.arepoframework.demo.composer.functions.TaskFunction;

public interface ITaskLinker<R,T> {

	TaskComposer<R,T> task(TaskFunction<R,T> taskFunction);
	
	List<TaskFunction<R,T>> getTasks();
}
