package com.planetsystems.tela.managementapp.shared;

import java.util.Map;

import com.google.gwt.user.client.Cookies;
import com.gwtplatform.dispatch.rpc.shared.UnsecuredActionImpl;
import com.planetsystems.tela.dto.AuthenticationDTO;

public class RequestAction extends UnsecuredActionImpl<RequestResult>{
	private String request;
	private Map<String, Object> requestBody;
	private AuthenticationDTO authenticationDTO;
	private String host;
//	private String token;
	
	public RequestAction() {
		super();
	}
	
	public RequestAction(String request , Map<String, Object> requestBody) {
		this.request = request;
		this.requestBody = requestBody;
		//this.token = token;
		//String token , 
		this.host=Cookies.getCookie(RequestConstant.TENANT_URL);
		System.out.println("Construct setting Host:: "+this.host);
	}
	
	

	public RequestAction(String request , AuthenticationDTO authenticationDTO) {
		super();
		this.request = request;
		this.authenticationDTO = authenticationDTO;
		this.host=Cookies.getCookie(RequestConstant.TENANT_URL);
		System.out.println("Construct setting Host:: "+this.host);
	}

	public String getRequest() {
		return request;
	}

	public Map<String, Object> getRequestBody() {
		return requestBody;
	}

	public AuthenticationDTO getAuthenticationDTO() {
		return authenticationDTO;
	}

	public String getHost() {
		return host;
	}
	
	

//	public String getToken() {
//		return token;
//	}
	
	
	
	
	

}
