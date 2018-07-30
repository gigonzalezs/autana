package io.yamia.autana.samples;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Slider;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import io.yamia.autana.director.ProcessDirector;
import io.yamia.autana.samples.monitors.PersistenceMonitor;
import io.yamia.autana.samples.monitors.StopOnBreakpointDebuggerMonitor;
import io.yamia.autana.samples.monitors.UIProcessMonitor;
import io.yamia.autana.samples.repository.InstanceTraceRepository;

@SpringUI
public class MainUI extends UI {
	
	private static final long serialVersionUID = 4655472711457407911L;

	@Autowired
	private FibonacciSequenceComposition fibonacci;
	
	@Autowired
	private ProcessDirector<Integer, Integer> director;
	
	@Autowired
	private StopOnBreakpointDebuggerMonitor<Integer, Integer> debugger;
	
	@Autowired
	private UIProcessMonitor monitor;
	
	@Autowired
	private PersistenceMonitor persistenceMonitor;
	
	@Autowired
	private InstanceTraceRepository repository;
	
	private Slider slider;
	private VerticalLayout layout;

	@Override
    protected void init(VaadinRequest vaadinRequest) {
		configureUI(); 
		monitor.setLayout(layout);
    }
	
	@PostConstruct
	public void setUp() {
		
		debugger
		.addBreakPoint("//loop2/javastep3", payload -> {
			Integer i = (Integer) payload.var("i").get();
			return i == slider.getValue().intValue();
		});
		
		director
		.composition(fibonacci)
		.addCustomMonitor(debugger)
		.addCustomMonitor(monitor)
		.addCustomMonitor(persistenceMonitor)
		.onException(handler -> {
			Notification n = new Notification("Error", handler.getException().getMessage());
			n.show(Page.getCurrent());
		});
	}
	
	private void configureUI() {
		layout = new VerticalLayout();	
		layout.addComponent(new Label("Autana framework sample"));
		FormLayout form = new FormLayout();
		slider = new Slider("Numero de pasos antes de interrumpir el proceso: ");
		slider.setMin(0.0);
		slider.setMax(10.0);
		slider.setValue(5.0);
		form.addComponent(slider);
		Button start = new Button("Iniciar");
		start.addClickListener(listener -> {
			repository.deleteAll();
			director.process(0);
		});
		form.addComponent(start);
		layout.addComponent(form);
        setContent(layout);
	}
	
}
