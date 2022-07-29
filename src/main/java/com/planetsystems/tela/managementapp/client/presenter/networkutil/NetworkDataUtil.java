package com.planetsystems.tela.managementapp.client.presenter.networkutil;

import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.planetsystems.tela.managementapp.client.gin.SessionManager;
import com.planetsystems.tela.managementapp.client.widget.SwizimaLoader;
import com.planetsystems.tela.managementapp.shared.RequestAction;
import com.planetsystems.tela.managementapp.shared.RequestConstant;
import com.planetsystems.tela.managementapp.shared.RequestResult;
import com.smartgwt.client.util.SC;

public class NetworkDataUtil {

	public static final String ACTION = "action";

	private NetworkDataUtil() {
		super();
	}

	public static void callNetwork(final DispatchAsync dispatcher, final PlaceManager placeManager,
			Map<String, Object> map, final NetworkResult networkResult) {
		
		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
		
		SC.showPrompt("", "", new SwizimaLoader());

		dispatcher.execute(new RequestAction((String) map.get(ACTION), map), new AsyncCallback<RequestResult>() {
			public void onFailure(Throwable caught) {
				System.out.println(caught.getMessage());
				SC.warn("ERROR", caught.getMessage());
				GWT.log("ERROR " + caught.getMessage());
				SC.clearPrompt();
			}

			public void onSuccess(RequestResult result) {

				SC.clearPrompt();

				SessionManager.getInstance().manageSession(result, placeManager);

				if (result != null) {
					if (result.getSystemFeedbackDTO().isResponse()) {
						networkResult.onNetworkResult(result);
					} else {
						SC.warn("INFO", result.getSystemFeedbackDTO().getMessage());
					}

				} else {
					// SC.warn("ERROR", "Unknown error");
					SC.warn("ERROR", "Service Down");
				}

			}
		});
	}
}
