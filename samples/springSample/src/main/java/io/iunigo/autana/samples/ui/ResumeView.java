package io.iunigo.autana.samples.ui;


import java.io.IOException;
import java.util.Comparator;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

import io.iunigo.autana.director.Payload;
import io.iunigo.autana.director.ProcessDirector;
import io.iunigo.autana.samples.compositions.FibonacciComposition;
import io.iunigo.autana.samples.model.InstanceTrace;
import io.iunigo.autana.samples.monitors.PersistenceMonitor;
import io.iunigo.autana.samples.monitors.UIProcessMonitor;
import io.iunigo.autana.samples.repository.InstanceTraceRepository;

@SpringComponent
@UIScope
public class ResumeView extends VerticalLayout {
	
	private static final long serialVersionUID = 3744060302832768573L;

	@Autowired
	private FibonacciComposition fibonacciComposition;
	
	@Autowired
	private ProcessDirector<Integer, Integer> director;
	
	@Autowired
	private UIProcessMonitor monitor;
	
	@Autowired
	private PersistenceMonitor persistenceMonitor;
	
	@Autowired
	private InstanceTraceRepository repository;
	
	@Autowired
	private String vaadinSessionId;
	
	@Override
	public void attach() {
		setupComposition();
		configureLayout();
		this.addAttachListener(listener -> {
			repository.findBySessionId(vaadinSessionId).stream()
			.sorted(Comparator.comparing(InstanceTrace::getStep).reversed())
			.findFirst()
			.ifPresent(trace -> {
				String path = trace.getPath();
				String payload = trace.getPayload();
				Payload<Integer, Integer> resumingPayload=null;
				try {
					resumingPayload = new Payload<Integer, Integer>(payload);
				} catch (IOException e) {
					e.printStackTrace();
				}
				director.resume(path, resumingPayload);
			});
			
		});
		super.attach();
	}
	
	private void setupComposition() {
		
		persistenceMonitor.setVaadinSessionId(vaadinSessionId);
		
		director
		.composition(fibonacciComposition)
		.addCustomMonitor(monitor)
		.addCustomMonitor(persistenceMonitor)
		.onException(handler -> {
			Notification n = new Notification("Error", handler.getException().getMessage());
			n.show(Page.getCurrent());
		});
	}
	
	private void configureLayout() {
		
	}

}
