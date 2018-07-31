package io.yamia.autana.samples.ui;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;

import io.yamia.autana.samples.model.InstanceTrace;
import io.yamia.autana.samples.repository.InstanceTraceRepository;

@SpringComponent
@UIScope
public class TraceView extends VerticalLayout  {

	private static final long serialVersionUID = 3235761053927417935L;
	
	@Autowired
	private InstanceTraceRepository repository;
	
	@Autowired
	private String vaadinSessionId;
	
	private Grid<InstanceTrace> grid;
		
	@PostConstruct
	public void setUp() {
		grid = new Grid<InstanceTrace>();
		grid.addColumn(InstanceTrace::getStep).setCaption("Step Number");
		grid.addColumn(InstanceTrace::getEvent).setCaption("event");
		grid.addColumn(InstanceTrace::getPath).setCaption("node path");
		grid.addColumn(InstanceTrace::getPayload).setCaption("payload JSON");
		grid.setWidth("100%");
		this.addComponent(grid);
 
	}
	
	@Override
	public void attach() {
		refresh();
		super.attach();
	}
	
	public void refresh() {
		ListDataProvider<InstanceTrace> data;
		List<InstanceTrace> traces = repository.findBySessionId(vaadinSessionId);
		data = DataProvider.fromStream(traces.stream());
		grid.setDataProvider(data);
	}

}
