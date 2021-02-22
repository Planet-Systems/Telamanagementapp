package com.planetsystems.tela.managementapp.client.gin;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.Cookies;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import com.planetsystems.tela.dto.SystemErrorDTO;
import com.planetsystems.tela.managementapp.client.place.NameTokens;
import com.planetsystems.tela.managementapp.shared.RequestConstant;
import com.planetsystems.tela.managementapp.shared.RequestResult;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Dialog;

public class SessionManager {

	private static SessionManager instance = new SessionManager();
	
	//private String defaultOrganisation;
	
	//private DispatchAsync dispatcher;
	
    
	private SessionManager() {

	}

	public static SessionManager getInstance() {
		return instance;
	}

	public void manageSession(final RequestResult result, final PlaceManager placeManager) {

		if (result != null) {
			SystemErrorDTO errorDTO = result.getSystemErrorDTO();
//			GWT.log("Manager ERROR  "+errorDTO);
			
			if(errorDTO != null) {
				if (errorDTO.getMessage() != null && errorDTO.getErrorCode() != 0) {
					
					GWT.log("result.getSystemError().getStatus(): "+errorDTO.getErrorCode());
					
					if (errorDTO.getErrorCode() == 403) {
						//token expired and authentication issues

						final Dialog dialogProperties = new Dialog();
						dialogProperties.setShowCloseButton(false);
						dialogProperties.setCanDrag(false);

						SC.warn("ERROR", errorDTO.getMessage(), new BooleanCallback() {

							@Override
							public void execute(Boolean value) {

								if (value) {
									logOut(placeManager);							
								}

							}
						}, dialogProperties);

					} else if (errorDTO.getErrorCode() == 8082) {
						GWT.log("ERROR "+ errorDTO.getMessage());
						//processing exception
						SC.warn("ERROR ", errorDTO.getMessage());
						
					} else if(errorDTO.getErrorCode() == 500) {
						GWT.log("ERROR "+ errorDTO.getMessage());
						SC.warn("ERROR ", errorDTO.getMessage());
					} else {
						GWT.log("ERROR "+ errorDTO.getMessage());
						//exception
						SC.warn("ERROR", errorDTO.getMessage());
					}
			}
			}
		}
	}

//	public void manageServerResonse(final RequestResult result) {
//
//		if (result != null) {
//
//			if (result.getSystemFeedbackDTO() != null) {
//				if (result.getSystemFeedbackDTO().getStatusCode() != null) {
//					if (result.getSystemFeedbackDTO().getStatusCode().equalsIgnoreCase("8082")) {
//						SC.warn("ERROR", result.getSystemFeedbackDTO().getMessage());
//					} else {
//						SC.warn("ERROR", result.getSystemFeedbackDTO().getMessage());
//					}
//				}
//			}
//		}
//	}

//	public void redirectToLoginPage(final PlaceManager placeManager) {
//		Cookies.removeCookie(RequestConstant.LOGIN_TOKEN);
//		Cookies.removeCookie(RequestConstant.LOGED_IN);
//		placeManager.revealDefaultPlace();
//	}

	public String getLoginToken() {
		return "Bearer " + Cookies.getCookie(RequestConstant.AUTH_TOKEN);
	}
	
	public void logOut(PlaceManager placeManager) {
		Cookies.removeCookie(RequestConstant.AUTH_TOKEN);
		Cookies.removeCookie(RequestConstant.LOGED_IN);
		placeManager.revealPlace(new PlaceRequest.Builder()
				.nameToken(NameTokens.login).build());
	}

//	public String getDefaultOrganisation() {
//		return defaultOrganisation;
//	}

//	public void setDefaultOrganisation(String defaultOrganisation) {
//		this.defaultOrganisation = defaultOrganisation;
//	}

//	public DispatchAsync getDispatcher() {
//		return dispatcher;
//	}
//
//	public void setDispatcher(DispatchAsync dispatcher) {
//		this.dispatcher = dispatcher;
//	}

	
	
}
