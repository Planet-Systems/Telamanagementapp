package com.planetsystems.tela.managementapp.client.presenter.dashboard;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.planetsystems.tela.managementapp.client.presenter.main.MainPresenter;
import com.planetsystems.tela.managementapp.client.place.NameTokens;

@SuppressWarnings("deprecation")
public class DashboardPresenter extends Presenter<DashboardPresenter.MyView, DashboardPresenter.MyProxy> {
	interface MyView extends View {
	}

	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_Dashboard = new Type<RevealContentHandler<?>>();

	@NameToken(NameTokens.dashboard)
	@ProxyCodeSplit
	interface MyProxy extends ProxyPlace<DashboardPresenter> {
	}

	@Inject
	DashboardPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy, MainPresenter.SLOT_Main);

	}

}