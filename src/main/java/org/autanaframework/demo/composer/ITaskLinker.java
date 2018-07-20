package org.autanaframework.demo.composer;

import java.util.List;

import org.autanaframework.demo.composer.declarators.TaskDeclarator;

public interface ITaskLinker<R,T> {

	TaskComposer<R,T> task(TaskDeclarator<R,T> taskFunction);
	
	List<Step<R,T>> getTasks();
}
