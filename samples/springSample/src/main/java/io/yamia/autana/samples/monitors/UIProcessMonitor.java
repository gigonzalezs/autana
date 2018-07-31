package io.yamia.autana.samples.monitors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import io.yamia.autana.director.Payload;
import io.yamia.autana.monitor.IExecutionMonitor;
import io.yamia.autana.samples.ui.ResumeView;
import io.yamia.autana.samples.ui.TraceView;

@SpringComponent
@UIScope
public class UIProcessMonitor implements IExecutionMonitor<Integer,Integer> {
	
	private VerticalLayout parent;
	private VerticalLayout layout = new VerticalLayout();
	
	@Autowired
	private TraceView traceUI;
	
	@Autowired
	ApplicationContext ctx;
	
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
		
		layout.addComponent(new Label(" "));
		layout.addComponent(new Label("The process was stoped successfully"));
		layout.addComponent(new Label("and the payload was stored in database."));
		layout.addComponent(new Label("Press continue button to watch the process trace"));
		Button next = new Button("watch stored trace");
		next.addClickListener(listener -> {
			final Window window = new Window("process trace");
			window.setWidth(1024.0f, Unit.PIXELS);
	        window.setModal(true);
	        window.center();
	        traceUI.setMargin(true);
	        window.setContent(traceUI);
	        
	        Button resume = new Button("Resume process");
	        resume.addClickListener(click -> {
	        	ResumeView resumeView = ctx.getBean(ResumeView.class);
	        	layout.addComponent(resumeView);
	        });
	        layout.addComponent(resume);
	        
	        UI.getCurrent().addWindow(window);
		});
		layout.addComponent(next);
	}

}
