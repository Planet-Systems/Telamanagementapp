package com.planetsystems.tela.managementapp.client.presenter.login;

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
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
import com.planetsystems.tela.dto.SystemUserGroupDTO;
import com.planetsystems.tela.dto.response.SystemFeedbackDTO;
import com.planetsystems.tela.dto.response.SystemResponseDTO;
import com.planetsystems.tela.dto.response.TokenFeedbackDTO;
import com.planetsystems.tela.managementapp.client.gin.SessionManager;
import com.planetsystems.tela.managementapp.client.place.NameTokens;
import com.planetsystems.tela.managementapp.client.presenter.login.forgotpassword.ForgotPasswordWindow;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkDataUtil;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkDataUtil2;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkResult;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkResult2;
import com.planetsystems.tela.managementapp.client.widget.SwizimaLoader;
import com.planetsystems.tela.managementapp.shared.MyRequestAction;
import com.planetsystems.tela.managementapp.shared.MyRequestResult;
import com.planetsystems.tela.managementapp.shared.RequestAction;
import com.planetsystems.tela.managementapp.shared.RequestConstant;
import com.planetsystems.tela.managementapp.shared.RequestResult;
import com.planetsystems.tela.managementapp.shared.requestcommands.AuthRequestCommand;
import com.planetsystems.tela.managementapp.shared.requestcommands.SystemUserGroupRequestCommand;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

public class LoginPresenter extends Presenter<LoginPresenter.MyView, LoginPresenter.MyProxy> {
	interface MyView extends View {
		LoginPane getLoginPane();
	}

	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_Login = new Type<RevealContentHandler<?>>();

	private DispatchAsync dispatcher;

	@Inject
	private PlaceManager placeManager;

	@NameToken(NameTokens.login)
	@NoGatekeeper
	@ProxyCodeSplit
	interface MyProxy extends ProxyPlace<LoginPresenter> {
	}

	@Inject
	LoginPresenter(EventBus eventBus, MyView view, MyProxy proxy , final DispatchAsync dispatcher) {
		super(eventBus, view, proxy, RevealType.Root);
		this.dispatcher = dispatcher;
	}

	@Override
	protected void onBind() {
		super.onBind();
		//logoIn();
		login();
		//forgotPassword();
		forgotPassword2();
	}

	
	private void forgotPassword2() {
		getView().getLoginPane().getForgotPasswordField().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				final ForgotPasswordWindow window = new ForgotPasswordWindow();
				window.getSaveButton().addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
					String email = window.getEmailField().getValueAsString();
					 if(email == null) {
						SC.say("Enter your email"); 
					 }else {
						 AuthenticationDTO dto = new AuthenticationDTO();
							dto.setUserName(email);
							
						 LinkedHashMap<String, Object> map = new LinkedHashMap<>();
							map.put(MyRequestAction.COMMAND , AuthRequestCommand.RESET_PASSWORD);
							map.put(MyRequestAction.DATA , dto);
						 
						NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {
							
							@Override
							public void onNetworkResult(MyRequestResult result) {
								SystemResponseDTO<String> responseDTO = result.getResponseText();
								SC.say(responseDTO.getMessage());
								GWT.log("pwd "+responseDTO.getData());
								window.close();
							}
						});
					}
					}
				});
				window.show();
			}
		});
	}

	@Deprecated
	private void forgotPassword() {
		getView().getLoginPane().getForgotPasswordField().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				final ForgotPasswordWindow window = new ForgotPasswordWindow();
				window.getSaveButton().addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
					String email = window.getEmailField().getValueAsString();
					 if(email == null) {
						SC.say("Enter your email"); 
					 }else {
						 LinkedHashMap<String, Object> map = new LinkedHashMap<>();
							map.put(NetworkDataUtil.ACTION , RequestConstant.RESET_PASSWORD );
							AuthenticationDTO dto = new AuthenticationDTO();
							dto.setUserName(email);
							map.put(RequestConstant.DATA , dto);
						 
						NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {
							
							@Override
							public void onNetworkResult(RequestResult result) {
								SystemFeedbackDTO feedbackDTO = result.getSystemFeedbackDTO();
								SC.say(feedbackDTO.getMessage());
								GWT.log("pwd "+feedbackDTO.getId());
								window.close();
							}
						});
					}
					}
				});
				window.show();
			}
		});
	}

	private void login() {
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
					Map<String , Object> map = new LinkedHashMap<String, Object>();
					map.put(MyRequestAction.DATA, dto);
					map.put(MyRequestAction.COMMAND, AuthRequestCommand.LOGIN);
					
					
					NetworkDataUtil2.callNetwork2(dispatcher, placeManager,  map , new NetworkResult2() {
						
						@Override
						public void onNetworkResult(MyRequestResult result) {
					
							if (result != null) {
								SystemResponseDTO<String> responseDTO =  result.getResponseText();

								if (responseDTO.isStatus()) {
									clearLoginFields();
					
									Cookies.setCookie(RequestConstant.AUTH_TOKEN , responseDTO.getData());
									Cookies.setCookie(RequestConstant.LOGED_IN , "true");
									Cookies.setCookie(RequestConstant.USERNAME , dto.getUserName());
									getLoggedInSystemUserGroup2();
									
									PlaceRequest placeRequest = new PlaceRequest.Builder()
											.nameToken(NameTokens.dashboard).build();

									placeManager.revealPlace(placeRequest);
									
								} else {
									Cookies.removeCookie(RequestConstant.AUTH_TOKEN);
									Cookies.removeCookie(RequestConstant.LOGED_IN);
									Cookies.removeCookie(RequestConstant.LOGGED_IN_SYSTEM_USER_GROUP_COOKIE);
									SC.warn("INFO", responseDTO.getMessage());
								}

							} else {
								SC.warn("ERROR", "Unknow error");
							}
							
						}
					} );
					
				}

			}
		});
	}
	
	
	@Deprecated
	public void logoIn() {
		getView().getLoginPane().getLoginButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String userName  =   getView().getLoginPane().getUsername().getValueAsString();
				String password =  getView().getLoginPane().getPassword().getValueAsString();
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
									GWT.log("Login ERROR "+caught.getMessage());
									SC.say("Login Error "+caught.getMessage());
								}

								public void onSuccess(RequestResult result) {
									SC.clearPrompt();
									SC.say("Success");
									if (result != null) {
										SC.say("Success");
										TokenFeedbackDTO feedback =  result.getTokenFeedbackDTO();

										if (feedback.isResponse()) {
											clearLoginFields();
//											SC.say("SUCCESS", feedback.getMessage()+" token "+feedback.getToken());
											SC.say("TOKEN " , feedback.getToken());
									
											Cookies.setCookie(RequestConstant.AUTH_TOKEN , feedback.getToken());
											Cookies.setCookie(RequestConstant.LOGED_IN , "true");
											Cookies.setCookie(RequestConstant.USERNAME , dto.getUserName());
											//getLoggedInSystemUserGroup();
											
											PlaceRequest placeRequest = new PlaceRequest.Builder()
													.nameToken(NameTokens.dashboard).build();

											placeManager.revealPlace(placeRequest);
											
										} else {
											Cookies.removeCookie(RequestConstant.AUTH_TOKEN);
											Cookies.removeCookie(RequestConstant.LOGED_IN);
											Cookies.removeCookie(RequestConstant.LOGGED_IN_SYSTEM_USER_GROUP_COOKIE);
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
	
	@Deprecated
	private void getLoggedInSystemUserGroup() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(NetworkDataUtil.ACTION, SystemUserGroupRequestCommand.LOGGEDIN_SYSTEM_USER_GROUP);
		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {
				SystemUserGroupDTO systemUserGroupDTO = result.getSystemUserGroupDTO();
				SC.say("GROUP "+systemUserGroupDTO.getName());
				Cookies.setCookie(RequestConstant.LOGGED_IN_SYSTEM_USER_GROUP_COOKIE , systemUserGroupDTO.getName());	
				System.out.println("USER GROUP "+systemUserGroupDTO);
				System.out.println(" GROUP NAME "+systemUserGroupDTO.getName());
			}
		});
	}
	
	private void getLoggedInSystemUserGroup2() {
	
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(MyRequestAction.COMMAND , SystemUserGroupRequestCommand.LOGGEDIN_SYSTEM_USER_GROUP);
		map.put(MyRequestAction.TOKEN , SessionManager.getInstance().getLoginToken());
	
		NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {
			
			@Override
			public void onNetworkResult(MyRequestResult result) {
				GWT.log("response "+result);
				if(result != null) {
					SystemResponseDTO<SystemUserGroupDTO> response = result.getSystemUserGroupResponse();
					SystemUserGroupDTO systemUserGroupDTO = response.getData();
					//SC.say("GROUP "+systemUserGroupDTO.getName() , "status "+response.isStatus() +" MESSAGE "+response.getMessage());
					Cookies.setCookie(RequestConstant.LOGGED_IN_SYSTEM_USER_GROUP_COOKIE , systemUserGroupDTO.getName());	
				}else {
					SC.say("Unknown Error");
				}
			}
		});
	}

	private void clearLoginFields() {
		getView().getLoginPane().getUsername().clearValue();
		getView().getLoginPane().getPassword().clearValue();
	}

}