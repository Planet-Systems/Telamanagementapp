package com.planetsystems.tela.managementapp.server.handlers;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.planetsystems.tela.dto.AuthenticationDTO;
import com.planetsystems.tela.dto.SystemMenuDTO;
import com.planetsystems.tela.dto.SystemUserGroupSystemMenuDTO;
import com.planetsystems.tela.dto.response.SystemFeedbackDTO;
import com.planetsystems.tela.dto.response.SystemResponseDTO;
import com.planetsystems.tela.managementapp.shared.MyRequestAction;
import com.planetsystems.tela.managementapp.shared.RequestConstant;

public class MainPresenterHandler implements IsSerializable {

	public MainPresenterHandler() {
		super();
	}
	
	
	public static SystemResponseDTO<List<SystemMenuDTO>> loggedUserSystemMenus(MyRequestAction action){
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);
		
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		Client client = ClientBuilder.newClient();

		SystemResponseDTO<List<SystemMenuDTO>> responseDTO = client.target(ApiResourceUtil.API_LINK).path("loggedUserSystemMenus")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.get(new GenericType<SystemResponseDTO<List<SystemMenuDTO>>>() {
				});
		client.close();

		return responseDTO;
	}
	
	
	public static SystemResponseDTO<String> changePassword(MyRequestAction action){
		AuthenticationDTO dto = (AuthenticationDTO) action.getRequestBody().get(MyRequestAction.DATA);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);
	
		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<String> responseDTO = client.target(ApiResourceUtil.API_LINK).path("changePassword")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
						new GenericType<SystemResponseDTO<String>>() {
						});


		System.out.println("CHANGE PWD RES " + responseDTO);
		

		client.close();
		return responseDTO;
	}

	
}
