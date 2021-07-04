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

import com.planetsystems.tela.dto.ClockInDTO;
import com.planetsystems.tela.dto.ClockOutDTO;
import com.planetsystems.tela.dto.FilterDTO;
import com.planetsystems.tela.dto.response.SystemResponseDTO;
import com.planetsystems.tela.managementapp.shared.MyRequestAction;

public class ClockInOutHandler {

	
	public static SystemResponseDTO<ClockInDTO> saveClockIn(MyRequestAction action){
		ClockInDTO dto = (ClockInDTO) action.getRequestBody().get(MyRequestAction.DATA);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<ClockInDTO> responseDTO = client.target(ApiResourceUtil.API_LINK).path("clockIns")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
						new GenericType<SystemResponseDTO<ClockInDTO>>() {
						});
		client.close();
		System.out.println("SAVE  CLOCK_IN  " + responseDTO);
		return responseDTO;
	}
	
	
	public static SystemResponseDTO<List<ClockInDTO>> getClockIns(MyRequestAction action){
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);
		FilterDTO dto = (FilterDTO) action.getRequestBody().get(MyRequestAction.DATA);
		
		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<List<ClockInDTO>> responseDTO = client.target(ApiResourceUtil.API_LINK).path("filterClockIns")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.post(Entity.entity(dto, MediaType.APPLICATION_JSON) ,new GenericType<SystemResponseDTO<List<ClockInDTO>>>() {
				});
		client.close();
		
		System.out.println("GET  CLOCK_INS  " + responseDTO);
		
		return responseDTO;
	}
	
	
	public static SystemResponseDTO<List<ClockInDTO>> getClockInsByLoggedSystemUserProfileSchools(MyRequestAction action){
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);
		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<List<ClockInDTO>> responseDTO = client.target(ApiResourceUtil.API_LINK).path("clockInsByLoggedSystemUserProfileSchools")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.get(new GenericType<SystemResponseDTO<List<ClockInDTO>>>() {
				});
		client.close();
		
		
		System.out.println("GET  CLOCK_INS ByLoggedSystemUserProfileSchools" + responseDTO);
		return responseDTO;
	}
	
	
	
	///////////////////////////////////////CLOCK OUT
	
public static SystemResponseDTO<ClockOutDTO> saveClockOut(MyRequestAction action){
		
		ClockOutDTO dto = (ClockOutDTO) action.getRequestBody().get(MyRequestAction.DATA);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<ClockOutDTO> responseDTO = client.target(ApiResourceUtil.API_LINK).path("clockOuts")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
						new GenericType<SystemResponseDTO<ClockOutDTO>>() {
						});

		client.close();
		System.out.println("SAVE CLOCK_OUT " + responseDTO);
		return responseDTO;
	}
	
	
	public static SystemResponseDTO<List<ClockOutDTO>> getClockOuts(MyRequestAction action){
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);
		FilterDTO dto = (FilterDTO) action.getRequestBody().get(MyRequestAction.DATA);
		
		Client client = ClientBuilder.newClient();
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<List<ClockOutDTO>> responseDto = client.target(ApiResourceUtil.API_LINK).path("filterClockOuts")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.post(Entity.entity(dto, MediaType.APPLICATION_JSON) , new GenericType<SystemResponseDTO<List<ClockOutDTO>>>() {
				});

		client.close();
		System.out.println("GET ALL CLOCK_OUT " + responseDto);
		return responseDto;
	}
	

	public static SystemResponseDTO<List<ClockOutDTO>> getClockOutsByLoggedSystemUserProfileSchools(MyRequestAction action){
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);
		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<List<ClockOutDTO>> responseDTO = client.target(ApiResourceUtil.API_LINK).path("clockOutsByLoggedSystemUserProfile")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.get(new GenericType<SystemResponseDTO<List<ClockOutDTO>>>() {
				});
		client.close();
		
		
		System.out.println("GET  CLOCK_OUTS ByLoggedSystemUserProfileSchools" + responseDTO);
		return responseDTO;
	}
	
	
	
	
	
	
}
