package com.planetsystems.tela.managementapp.client.presenter.login;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.NoGatekeeper;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import com.planetsystems.tela.dto.AuthenticationDTO;
import com.planetsystems.tela.dto.TokenFeedbackDTO;
import com.planetsystems.tela.managementapp.client.place.NameTokens;
import com.planetsystems.tela.managementapp.client.widget.SwizimaLoader;
import com.planetsystems.tela.managementapp.shared.RequestAction;
import com.planetsystems.tela.managementapp.shared.RequestConstant;
import com.planetsystems.tela.managementapp.shared.RequestResult;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

public class LoginPresenter extends Presenter<LoginPresenter.MyView, LoginPresenter.MyProxy> {
	interface MyView extends View {
		LoginPane getLoginPane();
	}

	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_Login = new Type<RevealContentHandler<?>>();

	@Inject
	private DispatchAsync dispatcher;

	@Inject
	private PlaceManager placeManager;

	@NameToken(NameTokens.login)
	@NoGatekeeper
	@ProxyCodeSplit
	interface MyProxy extends ProxyPlace<LoginPresenter> {
	}

	@Inject
	LoginPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy, RevealType.Root);

	}

	@Override
	protected void onBind() {
		super.onBind();
		logoIn();
	}

	public void logoIn() {
		getView().getLoginPane().getLoginButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String userName = getView().getLoginPane().getUsername().getValueAsString();
				String password = getView().getLoginPane().getPassword().getValueAsString();
				if (userName == null || password == null) {
					SC.say("Enter both username and password");
				} else {
					final AuthenticationDTO dto = new AuthenticationDTO();
					dto.setPassword(password);
					dto.setUserName(userName);
					
					SC.showPrompt("", "", new SwizimaLoader());

					dispatcher.execute(new RequestAction(RequestConstant.LOGIN , dto),
							new AsyncCallback<RequestResult>() {

								public void onFailure(Throwable caught) {

									SC.clearPrompt();
									System.out.println(caught.getMessage());
									SC.say("ERROR", caught.getMessage());
								}

								public void onSuccess(RequestResult result) {
									SC.clearPrompt();

									if (result != null) {
										TokenFeedbackDTO feedback = result.getTokenFeedbackDTO();

										if (feedback.isResponse()) {
											clearLoginFields();
											//SC.say("SUCCESS", feedback.getMessage()+" token "+feedback.getToken());
									
											Cookies.setCookie(RequestConstant.AUTH_TOKEN , feedback.getToken());
											Cookies.setCookie(RequestConstant.LOGED_IN , "true");
											Cookies.setCookie(RequestConstant.USERNAME , dto.getUserName());

											PlaceRequest placeRequest = new PlaceRequest.Builder()
													.nameToken(NameTokens.dashboard).build();

											placeManager.revealPlace(placeRequest);
											
										} else {
											Cookies.removeCookie(RequestConstant.AUTH_TOKEN);
											Cookies.removeCookie(RequestConstant.LOGED_IN);
											SC.warn("INFO", feedback.getMessage());
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

	private void clearLoginFields() {
		getView().getLoginPane().getUsername().clearValue();
		getView().getLoginPane().getPassword().clearValue();
	}

}