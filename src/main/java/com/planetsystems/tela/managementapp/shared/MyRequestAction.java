package com.planetsystems.tela.managementapp.shared;

import java.util.Map;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.gwtplatform.dispatch.rpc.shared.UnsecuredActionImpl;

public class MyRequestAction extends UnsecuredActionImpl<MyRequestResult> implements IsSerializable {
	public final static String COMMAND = "COMMAND";
	public final static String DATA = "DATA";
	public final static String TOKEN = "TOKEN";
	private Map<String, Object> requestBody;
	

	public MyRequestAction() {
		super();
	}


	public MyRequestAction(Map<String, Object> requestBody) {
		super();
		this.requestBody = requestBody;
	}


	public Map<String, Object> getRequestBody() {
		return requestBody;
	}
}
