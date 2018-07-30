package io.yamia.autana.samples.monitors;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import io.yamia.autana.director.Payload;
import io.yamia.autana.monitor.IExecutionMonitor;
import io.yamia.autana.samples.TraceUI;

@SpringComponent
@UIScope
public class UIProcessMonitor implements IExecutionMonitor<Integer,Integer> {
	
	private VerticalLayout parent;
	private VerticalLayout layout = new VerticalLayout();
	
	@Autowired
	private TraceUI traceUI;
	
	public void setLayout(VerticalLayout parent) {
		this.parent = parent;
		this.parent.addComponent(layout);
	}

	@Override
	public void start(Payload<Integer, Integer> payload) {	
		layout.removeAllComponents();
	}

	@Override
	public void executing(String path, Payload<Integer, Integer> payload) {
	}

	@Override
	public void success(String path, Payload<Integer, Integer> payload) {
		if (path.compareTo("//loop2/javastep3") == 0 ) {
			Label lbl = new Label(String.valueOf(payload.response));
			layout.addComponent(lbl);
		}
	}

	@Override
	public void fail(String path, Payload<Integer, Integer> payload, boolean resumeable) {
	}

	@Override
	public void end(Payload<Integer, Integer> payload) {	
		
		layout.addComponent(new Label("The process was stoped successfully"));
		layout.addComponent(new Label("and the payload was stored in database."));
		layout.addComponent(new Label("Press continue button to watch the process trace"));
		Button next = new Button("Continue");
		next.addClickListener(listener -> {
			parent.removeAllComponents();
			parent.addComponent(traceUI);
		});
		layout.addComponent(next);
	}

}
