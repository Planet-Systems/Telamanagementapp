package com.planetsystems.tela.managementapp.server.handlers;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import com.planetsystems.tela.dto.SystemUserGroupDTO;
import com.planetsystems.tela.dto.response.SystemResponseDTO;
import com.planetsystems.tela.managementapp.shared.MyRequestAction;
import com.planetsystems.tela.managementapp.shared.RequestDelimeters;

public class SystemUserGroupHandler {

	
	public static SystemResponseDTO<SystemUserGroupDTO> saveSystemUserGroup(MyRequestAction action){
		SystemUserGroupDTO dto = (SystemUserGroupDTO) action.getRequestBody().get(MyRequestAction.DATA);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<SystemUserGroupDTO> responseDTO = client.target(ApiResourceUtil.API_LINK).path("systemUserGroups")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
						new GenericType<SystemResponseDTO<SystemUserGroupDTO>>() {
						});
		client.close();
		System.out.println("SAVE  SystemUserGroup  " + responseDTO);
		return responseDTO;
	}
	
	
	public static SystemResponseDTO<List<SystemUserGroupDTO>> getSystemUserGroups(MyRequestAction action){
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);
		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<List<SystemUserGroupDTO>> responseDTO = client.target(ApiResourceUtil.API_LINK).path("systemUserGroups")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.get(new GenericType<SystemResponseDTO<List<SystemUserGroupDTO>>>() {
				});
		client.close();
		
		System.out.println("GET  SystemUserGroup  " + responseDTO);
		
		return responseDTO;
	}
	
	public static SystemResponseDTO<SystemUserGroupDTO> getSystemUserGroupById(MyRequestAction action){
		String id = (String) action.getRequestBody().get(RequestDelimeters.SYSTEM_USER_GROUP_ID);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<SystemUserGroupDTO> responseDTO = client.target(ApiResourceUtil.API_LINK).path("SystemUserGroups")
				.path(id).request(MediaType.APPLICATION_JSON).headers(headers)
				.get(new GenericType<SystemResponseDTO<SystemUserGroupDTO>>() {
				});

		client.close();
		System.out.println("GET  SystemUserGroupDTO BY ID " + responseDTO);

		return responseDTO;
	}
	
	
	public static SystemResponseDTO<String> deleteSystemUserGroup(MyRequestAction action){
		String id = (String) action.getRequestBody().get(RequestDelimeters.SYSTEM_USER_GROUP_ID);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<String> responseDTO = client.target(ApiResourceUtil.API_LINK).path("systemUserGroups")
				.path(id).request(MediaType.APPLICATION_JSON).headers(headers)
				.delete(new GenericType<SystemResponseDTO<String>>() {
				});

		client.close();
		System.out.println("DELETE SystemUserGroupDTO " + responseDTO);

		return responseDTO;
	}
	
	
	public static SystemResponseDTO<SystemUserGroupDTO> updateSystemUserGroup(MyRequestAction action){
		SystemUserGroupDTO dto = (SystemUserGroupDTO) action.getRequestBody().get(MyRequestAction.DATA);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<SystemUserGroupDTO> responseDTO = client.target(ApiResourceUtil.API_LINK).path("systemUserGroups")
				.path(dto.getId()).request(MediaType.APPLICATION_JSON).headers(headers)
				.put(Entity.entity(dto, MediaType.APPLICATION_JSON),
						new GenericType<SystemResponseDTO<SystemUserGroupDTO>>() {
						});
		client.close();
		System.out.println("UPDATE SystemUserGroupDTO " + responseDTO);
		return responseDTO;
	}
	
	
	
}
