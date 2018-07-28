package io.yamia.autana.samples.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.yamia.autana.samples.model.InstanceTrace;

public interface InstanceTraceRepository extends JpaRepository<InstanceTrace, String>{

}
