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
import com.planetsystems.tela.dto.StaffDailyTimeTableDTO;
import com.planetsystems.tela.dto.response.SystemResponseDTO;
import com.planetsystems.tela.managementapp.shared.MyRequestAction;
import com.planetsystems.tela.managementapp.shared.RequestDelimeters;

public class StaffDailyTimetableHandler {

	
	public static SystemResponseDTO<StaffDailyTimeTableDTO> saveStaffDailyTimeTable(MyRequestAction action){
		StaffDailyTimeTableDTO dto = (StaffDailyTimeTableDTO) action.getRequestBody().get(MyRequestAction.DATA);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<StaffDailyTimeTableDTO> responseDTO = client.target(ApiResourceUtil.API_LINK).path("staffDailyTimetables")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
						new GenericType<SystemResponseDTO<StaffDailyTimeTableDTO>>() {
						});
		client.close();
		System.out.println("SAVE  STAFF_DAILY_TIMETALE_IDS  " + responseDTO);
		return responseDTO;
	}
	
	
	public static SystemResponseDTO<List<StaffDailyTimeTableDTO>> getStaffDailyTimeTables(MyRequestAction action){
		FilterDTO dto = (FilterDTO) action.getRequestBody().get(MyRequestAction.DATA);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);
		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<List<StaffDailyTimeTableDTO>> responseDTO = client.target(ApiResourceUtil.API_LINK).path("filterStaffDailyTimetables")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.post(Entity.entity(dto, MediaType.APPLICATION_JSON) , new GenericType<SystemResponseDTO<List<StaffDailyTimeTableDTO>>>() {
				});
		client.close();
		
		System.out.println("GET  STAFF_DAILY_TIMETALE  " + responseDTO);
		
		return responseDTO;
	}
	
	public static SystemResponseDTO<StaffDailyTimeTableDTO> getStaffDailyTimeTableById(MyRequestAction action){
		String id = (String) action.getRequestBody().get(RequestDelimeters.STAFF_DAILY_TIMETALE_ID);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<StaffDailyTimeTableDTO> responseDTO = client.target(ApiResourceUtil.API_LINK).path("StaffDailyTimeTables")
				.path(id).request(MediaType.APPLICATION_JSON).headers(headers)
				.get(new GenericType<SystemResponseDTO<StaffDailyTimeTableDTO>>() {
				});

		client.close();
		System.out.println("GET  STAFF_DAILY_TIMETALE_ID BY ID " + responseDTO);

		return responseDTO;
	}
	
	
	public static SystemResponseDTO<String> deleteStaffDailyTimeTable(MyRequestAction action){
		String id = (String) action.getRequestBody().get(RequestDelimeters.STAFF_DAILY_TIMETALE_ID);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<String> responseDTO = client.target(ApiResourceUtil.API_LINK).path("StaffDailyTimeTables")
				.path(id).request(MediaType.APPLICATION_JSON).headers(headers)
				.delete(new GenericType<SystemResponseDTO<String>>() {
				});

		client.close();
		System.out.println("DELETE STAFF_DAILY_TIMETALE_ID " + responseDTO);

		return responseDTO;
	}
	
	
	public static SystemResponseDTO<StaffDailyTimeTableDTO> updateStaffDailyTimeTable(MyRequestAction action){
		StaffDailyTimeTableDTO dto = (StaffDailyTimeTableDTO) action.getRequestBody().get(MyRequestAction.DATA);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<StaffDailyTimeTableDTO> responseDTO = client.target(ApiResourceUtil.API_LINK).path("StaffDailyTimeTables")
				.path(dto.getId()).request(MediaType.APPLICATION_JSON).headers(headers)
				.put(Entity.entity(dto, MediaType.APPLICATION_JSON),
						new GenericType<SystemResponseDTO<StaffDailyTimeTableDTO>>() {
						});
		client.close();
		System.out.println("UPDATE STAFF_DAILY_TIMETALE_ID " + responseDTO);
		return responseDTO;
	}
	
	
	public static SystemResponseDTO<List<StaffDailyTimeTableDTO>> getStaffDailyTimeTablesByLoggedSystemUserProfileSchools(MyRequestAction action){
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);
		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<List<StaffDailyTimeTableDTO>> responseDTO = client.target(ApiResourceUtil.API_LINK).path("staffDailyTimetablesByLoggedSystemUserProfileSchools")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.get(new GenericType<SystemResponseDTO<List<StaffDailyTimeTableDTO>>>() {
				});
		client.close();
		
		
		System.out.println("GET  StaffDailyTimeTable ByLoggedSystemUserProfileSchools" + responseDTO);
		return responseDTO;
	}
	
	
	
	
}
