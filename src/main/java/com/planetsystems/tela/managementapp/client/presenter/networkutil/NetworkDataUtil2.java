package com.planetsystems.tela.managementapp.client.presenter.networkutil;

import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.planetsystems.tela.dto.ParentDTO;
import com.planetsystems.tela.managementapp.client.gin.SessionManager;
import com.planetsystems.tela.managementapp.client.widget.SwizimaLoader;
import com.planetsystems.tela.managementapp.shared.MyRequestAction;
import com.planetsystems.tela.managementapp.shared.MyRequestResult;
import com.smartgwt.client.util.SC;

public class NetworkDataUtil2 {

	public static final String ACTION = "action";

	private NetworkDataUtil2() {
		super();
	}

	public static void callNetwork2(final DispatchAsync dispatcher, final PlaceManager placeManager,
			 Map<String, Object> map , final NetworkResult2 networkResult2) {
		map.put(MyRequestAction.TOKEN, SessionManager.getInstance().getLoginToken());
		SC.showPrompt("", "", new SwizimaLoader());
		
		dispatcher.execute(new MyRequestAction(map), new AsyncCallback<MyRequestResult>() {

			@Override
			public void onFailure(Throwable caught) {
				System.out.println(caught.getMessage());
				SC.warn("ERROR", caught.getMessage());
				GWT.log("ERROR " + caught.getMessage());
				SC.clearPrompt();
				
			}

			@Override
			public void onSuccess(MyRequestResult result) {
				SC.clearPrompt();
				SessionManager.getInstance().manageSession(result, placeManager);
				
			    networkResult2.onNetworkResult(result);
				
			}
		});
	}
}
