package io.iunigo.autana.samples.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.vaadin.server.VaadinSession;
import com.vaadin.spring.internal.UIScopeImpl;

@Configuration
public class VaadinConfig {
	
	@Bean()
	@Scope(UIScopeImpl.VAADIN_UI_SCOPE_NAME)
	public String getVaadinSessionId() {
		return VaadinSession.getCurrent().getSession().getId();
	}

}
