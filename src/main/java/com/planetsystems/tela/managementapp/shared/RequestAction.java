package com.planetsystems.tela.managementapp.shared;

import java.io.Serializable;
import java.util.Map;

import com.gwtplatform.dispatch.rpc.shared.UnsecuredActionImpl;
import com.planetsystems.tela.dto.AuthenticationDTO;

public class RequestAction extends UnsecuredActionImpl<RequestResult> implements Serializable{
	private String request;
	private Map<String, Object> requestBody;
	private AuthenticationDTO authenticationDTO;
//	private String token;
	/*
	 * request key 
	 * 
	 * action
	 * 
	 */
	
	
	public RequestAction() {
		super();
	}
	
	public RequestAction(String request , Map<String, Object> requestBody) {
		this.request = request;
		this.requestBody = requestBody;
		//this.token = token;
		//String token , 
	}
	
	

	public RequestAction(String request , AuthenticationDTO authenticationDTO) {
		super();
		this.request = request;
		this.authenticationDTO = authenticationDTO;
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


}
