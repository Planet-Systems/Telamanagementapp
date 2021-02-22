package com.planetsystems.tela.managementapp.client.gin;

import com.gwtplatform.dispatch.rpc.client.gin.RpcDispatchAsyncModule;
import com.gwtplatform.mvp.client.annotations.DefaultPlace;
import com.gwtplatform.mvp.client.annotations.ErrorPlace;
import com.gwtplatform.mvp.client.annotations.UnauthorizedPlace;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.gwtplatform.mvp.client.gin.DefaultModule;
import com.gwtplatform.mvp.shared.proxy.ParameterTokenFormatter;
import com.planetsystems.tela.managementapp.client.place.NameTokens;
import com.planetsystems.tela.managementapp.client.presenter.login.LoginModule;
import com.planetsystems.tela.managementapp.client.presenter.main.MainModule;

/**
 * See more on setting up the PlaceManager on <a href="// See more on:
 * https://github.com/ArcBees/GWTP/wiki/PlaceManager">DefaultModule's >
 * DefaultPlaceManager</a>
 */

public class ClientModule extends AbstractPresenterModule {
	@Override
	protected void configure() {
		install(new DefaultModule.Builder().tokenFormatter(ParameterTokenFormatter.class).build()); 
		//install(new DefaultModule.Builder().tokenFormatter(RouteTokenFormatter.class).build());
		
		install(new RpcDispatchAsyncModule()); // binds DispatchAsync to RpcDispatchAsync
	    install(new MainModule());
	    install(new LoginModule());
		

		// DefaultPlaceManager Places
		bindConstant().annotatedWith(DefaultPlace.class).to(NameTokens.login);
		bindConstant().annotatedWith(ErrorPlace.class).to(NameTokens.login);
		bindConstant().annotatedWith(UnauthorizedPlace.class).to(NameTokens.login);
		

	}
	
	
	
}