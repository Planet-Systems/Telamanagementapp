package com.planetsystems.tela.managementapp.server.guice;

import com.gwtplatform.dispatch.rpc.server.actionvalidator.ActionValidator;
import com.gwtplatform.dispatch.rpc.server.guice.HandlerModule;
import com.gwtplatform.dispatch.rpc.server.guice.actionvalidator.DefaultActionValidator;
import com.planetsystems.tela.managementapp.server.MyRequestActionHandler;
import com.planetsystems.tela.managementapp.server.RequestActionHandler;
import com.planetsystems.tela.managementapp.shared.MyRequestAction;
import com.planetsystems.tela.managementapp.shared.RequestAction;

public class ServerModule extends HandlerModule {

	public ServerModule() {}

	@Override
	protected void configureHandlers() {

		bindHandler(RequestAction.class, RequestActionHandler.class);
		bindHandler(MyRequestAction.class, MyRequestActionHandler.class);
	
	}

	public ActionValidator getDefaultActionValidator() {
		return new DefaultActionValidator();
	}

	public RequestActionHandler getRequestActionHandler() {
		return new RequestActionHandler();
	}
	
	public MyRequestActionHandler getMyRequestActionHandler() {
		return new MyRequestActionHandler();
	}

}
