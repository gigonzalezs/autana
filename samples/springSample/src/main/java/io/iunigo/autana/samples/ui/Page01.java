package io.iunigo.autana.samples.ui;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.server.ClassResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;

@SpringComponent
@UIScope
public class Page01 extends VerticalLayout {
	
	private static final long serialVersionUID = 1918287804072780154L;
	
	private VerticalLayout parentContainer;
	
	@Autowired
	private Page02 page02;

	@PostConstruct
	public void setUp() {
		ClassResource page = new ClassResource("page1.html");
		BrowserFrame embedded = new BrowserFrame("", page);
		embedded.setWidth("100%");
		embedded.setHeight("500px");
		this.addComponent(embedded);
		Button next = new Button("continue with demo");
		next.addClickListener(listener -> {
			parentContainer.removeAllComponents();
			parentContainer.addComponent(page02);
		});
		this.addComponent(next);
	}

	public VerticalLayout getParentContainer() {
		return parentContainer;
	}

	public void setParentContainer(VerticalLayout parentContainer) {
		this.parentContainer = parentContainer;
	}

}
