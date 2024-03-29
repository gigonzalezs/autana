package io.iunigo.autana.samples.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.iunigo.autana.samples.model.InstanceTrace;

public interface InstanceTraceRepository extends JpaRepository<InstanceTrace, String>{

	public void deleteBySessionId(String sessionId);
	
	public List<InstanceTrace> findBySessionId(String sessionId);
}
