package org.arepoframework.demo.composer;

import java.util.List;

import org.arepoframework.demo.composer.declarators.TaskDeclarator;

public interface ITaskLinker<R,T> {

	TaskComposer<R,T> task(TaskDeclarator<R,T> taskFunction);
	
	List<TaskDeclarator<R,T>> getTasks();
}
