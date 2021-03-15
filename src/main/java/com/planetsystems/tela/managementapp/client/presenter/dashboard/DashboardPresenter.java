package com.planetsystems.tela.managementapp.client.presenter.dashboard;

import java.util.LinkedHashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.planetsystems.tela.managementapp.client.gin.SessionManager;
import com.planetsystems.tela.managementapp.client.place.NameTokens;
import com.planetsystems.tela.managementapp.client.presenter.main.MainPresenter;
import com.planetsystems.tela.managementapp.client.widget.SwizimaLoader;
import com.planetsystems.tela.managementapp.shared.RequestAction;
import com.planetsystems.tela.managementapp.shared.RequestConstant;
import com.planetsystems.tela.managementapp.shared.RequestResult;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

@SuppressWarnings("deprecation")
public class DashboardPresenter extends Presenter<DashboardPresenter.MyView, DashboardPresenter.MyProxy> {

	@Inject
	private DispatchAsync dispatcher;

	@Inject
	private PlaceManager placeManager;

	interface MyView extends View {

		public DashboardPane getDashboardPane();
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

	@Override
	protected void onBind() {
		// TODO Auto-generated method stub
		super.onBind();
		migrateData();
	}

	private void migrateData() {
		getView().getDashboardPane().getRefreshButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				SC.ask("Confrim", "Are you sure you want to migrate data", new BooleanCallback() {

					@Override
					public void execute(Boolean value) {

						if (value) {
							LinkedHashMap<String, Object> map = new LinkedHashMap<>();
							map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());

							SC.showPrompt("", "", new SwizimaLoader());

							dispatcher.execute(new RequestAction(RequestConstant.MIGRATE_DATA, map),
									new AsyncCallback<RequestResult>() {

										@Override
										public void onFailure(Throwable caught) {
											System.out.println(caught.getMessage());
											SC.warn("ERROR", caught.getMessage());
											GWT.log("ERROR " + caught.getMessage());
											SC.clearPrompt();

										}

										@Override
										public void onSuccess(RequestResult result) {

											SC.clearPrompt();
											SessionManager.getInstance().manageSession(result, placeManager);
											if (result != null) {

												if (result.getSystemFeedbackDTO() != null) {

													SC.say(result.getSystemFeedbackDTO().getMessage());

												}
											} else {
												SC.warn("ERROR", "Unknow error");
											}

										}
									});
						}

					}
				});

			}
		});

	}

}