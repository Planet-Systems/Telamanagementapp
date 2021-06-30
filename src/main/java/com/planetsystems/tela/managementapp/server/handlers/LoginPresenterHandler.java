package com.planetsystems.tela.managementapp.server.handlers;

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
import com.planetsystems.tela.dto.SystemUserGroupDTO;
import com.planetsystems.tela.dto.response.SystemFeedbackDTO;
import com.planetsystems.tela.dto.response.SystemResponseDTO;
import com.planetsystems.tela.managementapp.shared.MyRequestAction;
import com.planetsystems.tela.managementapp.shared.RequestConstant;

public class LoginPresenterHandler implements IsSerializable {
	
	private static final long serialVersionUID = 1L;

	public static SystemResponseDTO<String> login(MyRequestAction action) {

		AuthenticationDTO dto = (AuthenticationDTO) action.getRequestBody().get(MyRequestAction.DATA);

		Client client = ClientBuilder.newClient();

		SystemResponseDTO<String> responseDto = client.target(ApiResourceUtil.API_LINK).path("login")
				.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(dto, MediaType.APPLICATION_JSON) , new GenericType<SystemResponseDTO<String>>() {});

		System.out.println("AUTH " + responseDto);

		client.close();
		return responseDto;
	}
	
	
	
	public static SystemResponseDTO<SystemUserGroupDTO> loggedUserGroup(MyRequestAction action){
		Client client = ClientBuilder.newClient();
        String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<SystemUserGroupDTO> responseDto = client.target(ApiResourceUtil.API_LINK)
				.path("loggedInSystemUserGroup").request(MediaType.APPLICATION_JSON).headers(headers)
				.get(new GenericType<SystemResponseDTO<SystemUserGroupDTO>>() {
				});

		client.close();
		return responseDto;
	}
	
	
	
	public static SystemResponseDTO<String> resetPassword(MyRequestAction action){
		AuthenticationDTO dto = (AuthenticationDTO) action.getRequestBody().get(MyRequestAction.DATA);
		Client client = ClientBuilder.newClient();

		SystemResponseDTO<String> responseDTO = client.target(ApiResourceUtil.API_LINK).path("resetPassword")
				.request(MediaType.APPLICATION_JSON).post(Entity.entity(dto, MediaType.APPLICATION_JSON),
						new GenericType<SystemResponseDTO<String>>() {
						});

		System.out.println("AUTH RESET " + responseDTO);
		client.close();
		return responseDTO;
	}
	
}
