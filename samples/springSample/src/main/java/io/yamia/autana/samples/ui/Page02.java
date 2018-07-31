package io.yamia.autana.samples.ui;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.server.ClassResource;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Slider;
import com.vaadin.ui.VerticalLayout;

import io.yamia.autana.director.ProcessDirector;
import io.yamia.autana.samples.compositions.FibonacciComposition;
import io.yamia.autana.samples.monitors.PersistenceMonitor;
import io.yamia.autana.samples.monitors.StopOnBreakpointDebuggerMonitor;
import io.yamia.autana.samples.monitors.UIProcessMonitor;
import io.yamia.autana.samples.repository.InstanceTraceRepository;

@SpringComponent
@UIScope
public class Page02 extends VerticalLayout {
	
	private static final long serialVersionUID = 1918287804072780154L;
	
	@Autowired
	private FibonacciComposition fibonacciComposition;
	
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
	
	
	private VerticalLayout parentContainer;
	private Slider slider;

	@PostConstruct
	public void setUp() {
		setupComposition();
		configureLayout();
	}
	
	private void setupComposition() {
		debugger
		.addBreakPoint("//loop2/javastep3", payload -> {
			Integer i = (Integer) payload.var("i").get();
			Integer sliderValue = slider.getValue().intValue();
			return i.compareTo(sliderValue) == 0;
		});
		
		director
		.composition(fibonacciComposition)
		.addCustomMonitor(debugger)
		.addCustomMonitor(monitor)
		.addCustomMonitor(persistenceMonitor)
		.onException(handler -> {
			Notification n = new Notification("Error", handler.getException().getMessage());
			n.show(Page.getCurrent());
		});
	}
	
	private void configureLayout() {
		
		Label title = new Label("Composition demo: Fibonacci Sequence");
		this.addComponent(title);
		title.setStyleName("h1");
		
		HorizontalLayout hLayout = new HorizontalLayout();
		hLayout.setWidth("100%");
		hLayout.setSpacing(true);
		this.addComponent(hLayout);
		
		VerticalLayout left = new VerticalLayout();
		hLayout.addComponent(left);
		
		VerticalLayout right = new VerticalLayout();
		right.setStyleName("yamia_min-margin-left");
		hLayout.addComponent(right);
		
		Label d1 = new Label("This demo uses a declarative composition to generate a fibonacci sequence of number.");
		left.addComponent(d1);
		
		Label d2 = new Label("The director (the runtime) executes the composition and take care about store the excecution state in a database.");
		left.addComponent(d2);
		
		ClassResource code = new ClassResource("composition.png");
		Image imageCode = new  Image("", code);
		imageCode.setWidth("600px");
		left.addComponent(imageCode);
		
		Label sp1 = new Label(" ");
		right.addComponent(sp1);
		
		Label sp2 = new Label(" ");
		right.addComponent(sp2);
		
		Label d3 = new Label("Each step in the composition is resumeable in case of process interruption");
		right.addComponent(d3);
		
		
		FormLayout form = new FormLayout();
		slider = new Slider("Interrupt the process in the loop number: ");
		slider.setMin(0.0);
		slider.setMax(10.0);
		slider.setValue(5.0);
		form.addComponent(slider);
		Button start = new Button("execute process");
		start.addClickListener(listener -> {
			repository.deleteAll();
			director.process(0);
		});
		form.addComponent(start);
		right.addComponent(form);
		monitor.setLayout(right);
	}

	public VerticalLayout getParentContainer() {
		return parentContainer;
	}

	public void setParentContainer(VerticalLayout parentContainer) {
		this.parentContainer = parentContainer;
	}

}
