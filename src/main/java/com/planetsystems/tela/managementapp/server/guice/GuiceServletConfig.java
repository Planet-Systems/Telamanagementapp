package com.planetsystems.tela.managementapp.server.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class GuiceServletConfig extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() {
		// TODO Auto-generated method stub
		return Guice.createInjector(new ServerModule(), new DispatchServletModule());
	}

}
