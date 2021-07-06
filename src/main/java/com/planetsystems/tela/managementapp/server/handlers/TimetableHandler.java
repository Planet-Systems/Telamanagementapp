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

import com.planetsystems.tela.dto.FilterDTO;
import com.planetsystems.tela.dto.TimeTableDTO;
import com.planetsystems.tela.dto.response.SystemResponseDTO;
import com.planetsystems.tela.managementapp.shared.MyRequestAction;
import com.planetsystems.tela.managementapp.shared.RequestDelimeters;

public class TimetableHandler {

	public static SystemResponseDTO<TimeTableDTO> saveTimeTable(MyRequestAction action){
		TimeTableDTO dto = (TimeTableDTO) action.getRequestBody().get(MyRequestAction.DATA);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<TimeTableDTO> responseDTO = client.target(ApiResourceUtil.API_LINK).path("timetables2")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
						new GenericType<SystemResponseDTO<TimeTableDTO>>() {
						});
		client.close();
		System.out.println("SAVE  TimeTables  " + responseDTO);
		return responseDTO;
	}
	
	
	
	
	public static SystemResponseDTO<List<TimeTableDTO>> getTimeTables(MyRequestAction action){
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);
		FilterDTO dto = (FilterDTO) action.getRequestBody().get(MyRequestAction.DATA);
		
		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<List<TimeTableDTO>> responseDTO = client.target(ApiResourceUtil.API_LINK).path("filterTimetables")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.post(Entity.entity(dto, MediaType.APPLICATION_JSON) , new GenericType<SystemResponseDTO<List<TimeTableDTO>>>() {
				});
		client.close();
		
		System.out.println("GET  YEARS  " + responseDTO);
		
		return responseDTO;
	}
	
	
	public static SystemResponseDTO<TimeTableDTO> getTimeTableById(MyRequestAction action){
		String id = (String) action.getRequestBody().get(RequestDelimeters.TIMETABLE_ID);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<TimeTableDTO> responseDTO = client.target(ApiResourceUtil.API_LINK).path("timetables2")
				.path(id).request(MediaType.APPLICATION_JSON).headers(headers)
				.get(new GenericType<SystemResponseDTO<TimeTableDTO>>() {
				});

		client.close();
		System.out.println("GET  YEAR BY ID " + responseDTO);

		return responseDTO;
	}
	
	
	
	
	public static SystemResponseDTO<List<TimeTableDTO>> getTimeTablesByLoggedSystemUserProfileSchools(MyRequestAction action){
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);
		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<List<TimeTableDTO>> responseDTO = client.target(ApiResourceUtil.API_LINK).path("timetablesByLoggedSystemUserProfileSchools")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.get(new GenericType<SystemResponseDTO<List<TimeTableDTO>>>() {
				});
		client.close();
		
		System.out.println("GET  TIMETABLE BY PROFILE SCCHOOLS: " + responseDTO);
		
		return responseDTO;
	}
	
	
}
