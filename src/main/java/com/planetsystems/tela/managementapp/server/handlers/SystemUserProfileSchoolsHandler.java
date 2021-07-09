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

import com.planetsystems.tela.dto.SchoolDTO;
import com.planetsystems.tela.dto.SystemUserProfileSchoolDTO;
import com.planetsystems.tela.dto.response.SystemResponseDTO;
import com.planetsystems.tela.managementapp.server.APIGateWay;
import com.planetsystems.tela.managementapp.shared.MyRequestAction;
import com.planetsystems.tela.managementapp.shared.RequestConstant;
import com.planetsystems.tela.managementapp.shared.RequestDelimeters;

public class SystemUserProfileSchoolsHandler {

	
	public static SystemResponseDTO<String> saveSystemUserProfileSchool(MyRequestAction action){
		List<SystemUserProfileSchoolDTO> dtos = (List<SystemUserProfileSchoolDTO>) action.getRequestBody().get(MyRequestAction.DATA);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<String> responseDTO = client.target(ApiResourceUtil.API_LINK).path("systemUserProfileSchools")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.post(Entity.entity(dtos , MediaType.APPLICATION_JSON),
						new GenericType<SystemResponseDTO<String>>() {
						});
		client.close();
		System.out.println("SAVE  YEARS  " + responseDTO);
		return responseDTO;
	}
	
	
	public static SystemResponseDTO<List<SystemUserProfileSchoolDTO>> getSystemUserProfileSchools(MyRequestAction action){
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);
		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<List<SystemUserProfileSchoolDTO>> responseDTO = client.target(ApiResourceUtil.API_LINK).path("SystemUserProfileSchools")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.get(new GenericType<SystemResponseDTO<List<SystemUserProfileSchoolDTO>>>() {
				});
		client.close();
		
		System.out.println("GET  YEARS  " + responseDTO);
		
		return responseDTO;
	}
	
	public static SystemResponseDTO<List<SystemUserProfileSchoolDTO>> getSystemUserProfileSchoolByProfileId(MyRequestAction action){
		String id = (String) action.getRequestBody().get(RequestDelimeters.SYSTEM_USER_PROFILE_ID);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<List<SystemUserProfileSchoolDTO>> responseDTO = client.target(ApiResourceUtil.API_LINK).path("systemUserProfileSchools")
				.path(id).path("schools").request(MediaType.APPLICATION_JSON).headers(headers)
				.get(new GenericType<SystemResponseDTO<List<SystemUserProfileSchoolDTO>>>() {
				});

		client.close();
		System.out.println("GET  SystemUserProfileSchoolDTO BY ID " + responseDTO);

		return responseDTO;
	}
	
	
	public static SystemResponseDTO<String> deleteSystemUserProfileSchools(MyRequestAction action){
		List<SystemUserProfileSchoolDTO> dtos = (List<SystemUserProfileSchoolDTO>) action.getRequestBody().get(MyRequestAction.DATA);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<String> responseDTO = client.target(ApiResourceUtil.API_LINK).path("delete")
				.path("systemUserProfileSchools").request(MediaType.APPLICATION_JSON).headers(headers)
				.post(Entity.entity(dtos, MediaType.APPLICATION_JSON) , new GenericType<SystemResponseDTO<String>>() {
				});

		client.close();
		System.out.println("DELETE SystemUserProfileSchoolDTO " + responseDTO);

		return responseDTO;
	}
	
	
	public static SystemResponseDTO<SystemUserProfileSchoolDTO> updateSystemUserProfileSchool(MyRequestAction action){
		SystemUserProfileSchoolDTO dto = (SystemUserProfileSchoolDTO) action.getRequestBody().get(MyRequestAction.DATA);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<SystemUserProfileSchoolDTO> responseDTO = client.target(ApiResourceUtil.API_LINK).path("SystemUserProfileSchools")
				.path(dto.getId()).request(MediaType.APPLICATION_JSON).headers(headers)
				.put(Entity.entity(dto, MediaType.APPLICATION_JSON),
						new GenericType<SystemResponseDTO<SystemUserProfileSchoolDTO>>() {
						});
		client.close();
		System.out.println("UPDATE YEAR " + responseDTO);
		return responseDTO;
	}
	
	
	public static SystemResponseDTO<String> deleteProfileSchoolsByProfileId(MyRequestAction action){
		String profileId = (String) action.getRequestBody().get(RequestDelimeters.SYSTEM_USER_PROFILE_ID);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);
		List<SchoolDTO> dtos = (List<SchoolDTO>) action.getRequestBody().get(RequestConstant.DATA);
		

		Client client = ClientBuilder.newClient();

	
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<String> responseDto = client.target(ApiResourceUtil.API_LINK).path("delete")
				.path("systemUserProfileSchools").path(profileId).request(MediaType.APPLICATION_JSON)
				.headers(headers).post(Entity.entity(dtos, MediaType.APPLICATION_JSON),
						new GenericType<SystemResponseDTO<String>>() {
						});

		client.close();
		return responseDto;
	}
	
	
	public static SystemResponseDTO<List<SchoolDTO>> notProfileSchools(MyRequestAction action) {
		String profileId = (String) action.getRequestBody().get(RequestDelimeters.SYSTEM_USER_PROFILE_ID);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);
		String districtId = (String) action.getRequestBody().get(RequestDelimeters.DISTRICT_ID);


		Client client = ClientBuilder.newClient();

	
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<List<SchoolDTO>> responseDTO = client.target(ApiResourceUtil.API_LINK)
				.path("notProfileSchools").path(profileId).path("districts").path(districtId)
				.path("schools").request(MediaType.APPLICATION_JSON).headers(headers)
				.get(new GenericType<SystemResponseDTO<List<SchoolDTO>>>() {
				});

		client.close();
		
		return responseDTO;
	}
	
	
	

	
	
}
