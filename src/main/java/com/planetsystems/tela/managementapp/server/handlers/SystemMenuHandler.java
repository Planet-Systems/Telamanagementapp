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

import com.planetsystems.tela.dto.SystemMenuDTO;
import com.planetsystems.tela.dto.response.SystemResponseDTO;
import com.planetsystems.tela.managementapp.shared.MyRequestAction;
import com.planetsystems.tela.managementapp.shared.RequestDelimeters;

public class SystemMenuHandler {

	
	public static SystemResponseDTO<String> saveSystemMenu(MyRequestAction action){
		List<SystemMenuDTO> dto = (List<SystemMenuDTO>) action.getRequestBody().get(MyRequestAction.DATA);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<String> responseDTO = client.target(ApiResourceUtil.API_LINK).path("systemMenus")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
						new GenericType<SystemResponseDTO<String>>() {
						});
		client.close();
		System.out.println("SAVE  SystemMenu  " + responseDTO);
		return responseDTO;
	}
	
	
	public static SystemResponseDTO<List<SystemMenuDTO>> getSystemMenus(MyRequestAction action){
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);
		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<List<SystemMenuDTO>> responseDTO = client.target(ApiResourceUtil.API_LINK).path("systemMenus")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.get(new GenericType<SystemResponseDTO<List<SystemMenuDTO>>>() {
				});
		client.close();
		
		System.out.println("GET  YEARS  " + responseDTO);
		
		return responseDTO;
	}
	
	public static SystemResponseDTO<SystemMenuDTO> getSystemMenuById(MyRequestAction action){
		String id = (String) action.getRequestBody().get(RequestDelimeters.SYSTEM_MENU_ID);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<SystemMenuDTO> responseDTO = client.target(ApiResourceUtil.API_LINK).path("SystemMenus")
				.path(id).request(MediaType.APPLICATION_JSON).headers(headers)
				.get(new GenericType<SystemResponseDTO<SystemMenuDTO>>() {
				});

		client.close();
		System.out.println("GET  YEAR BY ID " + responseDTO);

		return responseDTO;
	}
	
	
	public static SystemResponseDTO<String> deleteSystemMenu(MyRequestAction action){
		String id = (String) action.getRequestBody().get(RequestDelimeters.SYSTEM_MENU_ID);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<String> responseDTO = client.target(ApiResourceUtil.API_LINK).path("SystemMenus")
				.path(id).request(MediaType.APPLICATION_JSON).headers(headers)
				.delete(new GenericType<SystemResponseDTO<String>>() {
				});

		client.close();
		System.out.println("DELETE YEAR " + responseDTO);

		return responseDTO;
	}
	
	public static SystemResponseDTO<String> deleteSystemMenus(MyRequestAction action){
		List<SystemMenuDTO> dtos = (List<SystemMenuDTO>) action.getRequestBody().get(MyRequestAction.DATA);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<String> responseDTO = client.target(ApiResourceUtil.API_LINK).path("delete").path("systemMenus")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.post(Entity.entity(dtos , MediaType.APPLICATION_JSON) ,new GenericType<SystemResponseDTO<String>>() {
				});

		client.close();
		System.out.println("DELETE SystemMenu " + responseDTO);

		return responseDTO;
	}
	
	
	public static SystemResponseDTO<SystemMenuDTO> updateSystemMenu(MyRequestAction action){
		SystemMenuDTO dto = (SystemMenuDTO) action.getRequestBody().get(MyRequestAction.DATA);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<SystemMenuDTO> responseDTO = client.target(ApiResourceUtil.API_LINK).path("SystemMenus")
				.path(dto.getId()).request(MediaType.APPLICATION_JSON).headers(headers)
				.put(Entity.entity(dto, MediaType.APPLICATION_JSON),
						new GenericType<SystemResponseDTO<SystemMenuDTO>>() {
						});
		client.close();
		System.out.println("UPDATE YEAR " + responseDTO);
		return responseDTO;
	}
	
}
