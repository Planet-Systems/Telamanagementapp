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

import com.planetsystems.tela.dto.AcademicTermDTO;
import com.planetsystems.tela.dto.SystemUserProfileDTO;
import com.planetsystems.tela.dto.response.SystemResponseDTO;
import com.planetsystems.tela.managementapp.shared.MyRequestAction;
import com.planetsystems.tela.managementapp.shared.RequestDelimeters;

public class SystemUserProfileHandler {

	
	public static SystemResponseDTO<SystemUserProfileDTO> saveSystemUserProfile(MyRequestAction action){
		SystemUserProfileDTO dto = (SystemUserProfileDTO) action.getRequestBody().get(MyRequestAction.DATA);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<SystemUserProfileDTO> responseDTO = client.target(ApiResourceUtil.API_LINK).path("systemUserProfiles")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
						new GenericType<SystemResponseDTO<SystemUserProfileDTO>>() {
						});
		client.close();
		System.out.println("SAVE  SYSTEM_USER_PROFILE  " + responseDTO);
		return responseDTO;
	}
	
	
	public static SystemResponseDTO<List<SystemUserProfileDTO>> getSystemUserProfiles(MyRequestAction action){
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);
		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<List<SystemUserProfileDTO>> responseDTO = client.target(ApiResourceUtil.API_LINK).path("systemUserProfiles")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.get(new GenericType<SystemResponseDTO<List<SystemUserProfileDTO>>>() {
				});
		client.close();
		
		System.out.println("GET  SYSTEM_USER_PROFILES  " + responseDTO);
		
		return responseDTO;
	}
	
	public static SystemResponseDTO<SystemUserProfileDTO> getSystemUserProfileById(MyRequestAction action){
		String id = (String) action.getRequestBody().get(RequestDelimeters.SYSTEM_USER_PROFILE_ID);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<SystemUserProfileDTO> responseDTO = client.target(ApiResourceUtil.API_LINK).path("systemUserProfiles")
				.path(id).request(MediaType.APPLICATION_JSON).headers(headers)
				.get(new GenericType<SystemResponseDTO<SystemUserProfileDTO>>() {
				});

		client.close();
		System.out.println("GET  SYSTEM_USER_PROFILE_ID BY ID " + responseDTO);

		return responseDTO;
	}
	
	
	public static SystemResponseDTO<String> deleteSystemUserProfile(MyRequestAction action){
		String id = (String) action.getRequestBody().get(RequestDelimeters.SYSTEM_USER_PROFILE_ID);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<String> responseDTO = client.target(ApiResourceUtil.API_LINK).path("systemUserProfiles")
				.path(id).request(MediaType.APPLICATION_JSON).headers(headers)
				.delete(new GenericType<SystemResponseDTO<String>>() {
				});

		client.close();
		System.out.println("DELETE SYSTEM_USER_PROFILE_ID " + responseDTO);

		return responseDTO;
	}
	
	
	public static SystemResponseDTO<SystemUserProfileDTO> updateSystemUserProfile(MyRequestAction action){
		SystemUserProfileDTO dto = (SystemUserProfileDTO) action.getRequestBody().get(MyRequestAction.DATA);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<SystemUserProfileDTO> responseDTO = client.target(ApiResourceUtil.API_LINK).path("academicSYSTEM_USER_PROFILE_IDs")
				.path(dto.getId()).request(MediaType.APPLICATION_JSON).headers(headers)
				.put(Entity.entity(dto, MediaType.APPLICATION_JSON),
						new GenericType<SystemResponseDTO<SystemUserProfileDTO>>() {
						});
		client.close();
		System.out.println("UPDATE SYSTEM_USER_PROFILE_ID " + responseDTO);
		return responseDTO;
	}
	

	
}
