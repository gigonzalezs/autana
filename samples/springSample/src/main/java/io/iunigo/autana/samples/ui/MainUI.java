package io.iunigo.autana.samples.ui;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.Page;
import com.vaadin.server.Page.Styles;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Slider;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import io.iunigo.autana.director.ProcessDirector;
import io.iunigo.autana.samples.compositions.FibonacciComposition;
import io.iunigo.autana.samples.monitors.PersistenceMonitor;
import io.iunigo.autana.samples.monitors.StopOnBreakpointDebuggerMonitor;
import io.iunigo.autana.samples.monitors.UIProcessMonitor;
import io.iunigo.autana.samples.repository.InstanceTraceRepository;

@SpringUI
public class MainUI extends UI {
	
	private static final long serialVersionUID = 4655472711457407911L;

	@Autowired
	private Page01 page01;
	
	private VerticalLayout layout;
	private VerticalLayout body;

	@Override
    protected void init(VaadinRequest vaadinRequest) { 
		layout = new VerticalLayout();	
		body = new VerticalLayout();
		configureStyles();
		configureHeader();
		configureBody();
		this.setContent(layout);;
    }
	
	@PostConstruct
	public void setUp() {
		
		
	}
	
	private void configureHeader() {
		Image logo = new Image("", new ExternalResource("http://www.yamia.io/autana/images/logo.svg"));
		logo.setWidth("255px");
		logo.setHeight("180px");
		layout.addComponent(logo);
		layout.setStyleName("no");
	}
	
	private void configureBody() {
		body.addComponent(page01);
		page01.setParentContainer(body);
		body.setStyleName("no-margin-left");
		layout.addComponent(body);
	}
	
	private void configureUI2() {
		layout = new VerticalLayout();	
		Image logo = new Image("", new ExternalResource("http://www.yamia.io/autana/images/logo.svg"));
		logo.setWidth("255px");
		logo.setHeight("250px");
		layout.addComponent(logo);
		layout.addComponent(new Label("Autana framework sample"));
		
	}
	
	private void configureStyles() {
		
		Styles styles = Page.getCurrent().getStyles();
		 
		styles.add(".valo .v-margin-top {padding-top: 0px;}");
		
		styles.add(".no-margin-left {padding-left: 0px !important;}");
		
		styles.add(".yamia_min-margin-left {padding-left: 30px !important;}");
		
		
	}
	
}
