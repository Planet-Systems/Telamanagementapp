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
import com.planetsystems.tela.dto.StaffDailyAttendanceSupervisionDTO;
import com.planetsystems.tela.dto.StaffDailyTimeTableDTO;
import com.planetsystems.tela.dto.response.SystemResponseDTO;
import com.planetsystems.tela.managementapp.shared.MyRequestAction;
import com.planetsystems.tela.managementapp.shared.RequestDelimeters;

public class StaffDailyTimetableSupervisionHandler {

	
	public static SystemResponseDTO<StaffDailyAttendanceSupervisionDTO> saveStaffDailyAttendanceSupervision(MyRequestAction action){
		StaffDailyAttendanceSupervisionDTO dto = (StaffDailyAttendanceSupervisionDTO) action.getRequestBody().get(MyRequestAction.DATA);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<StaffDailyAttendanceSupervisionDTO> responseDTO = client.target(ApiResourceUtil.API_LINK).path("staffDailyAttendanceSupervisions2")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
						new GenericType<SystemResponseDTO<StaffDailyAttendanceSupervisionDTO>>() {
						});
		client.close();
		System.out.println("SAVE  StaffDailyAttendanceSupervisionDTO  " + responseDTO);
		return responseDTO;
	}
	
	
	public static SystemResponseDTO<List<StaffDailyAttendanceSupervisionDTO>> getStaffDailyAttendanceSupervisions(MyRequestAction action){
		FilterDTO dto = (FilterDTO) action.getRequestBody().get(MyRequestAction.DATA);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);
		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<List<StaffDailyAttendanceSupervisionDTO>> responseDTO = client.target(ApiResourceUtil.API_LINK).path("filterStaffDailyAttendanceSupervisions")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.post(Entity.entity(dto, MediaType.APPLICATION_JSON) , new GenericType<SystemResponseDTO<List<StaffDailyAttendanceSupervisionDTO>>>() {
				});
		client.close();
		
		System.out.println("GET  StaffDailyAttendanceSupervisionDTOs  " + responseDTO);
		
		return responseDTO;
	}
	
	public static SystemResponseDTO<StaffDailyAttendanceSupervisionDTO> getStaffDailyAttendanceSupervisionById(MyRequestAction action){
		String id = (String) action.getRequestBody().get(RequestDelimeters.STAFF_DAILY_TIMETALE_ID);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<StaffDailyAttendanceSupervisionDTO> responseDTO = client.target(ApiResourceUtil.API_LINK).path("StaffDailyTimeTables")
				.path(id).request(MediaType.APPLICATION_JSON).headers(headers)
				.get(new GenericType<SystemResponseDTO<StaffDailyAttendanceSupervisionDTO>>() {
				});

		client.close();
		System.out.println("GET  StaffDailyAttendanceSupervisionDTO BY ID " + responseDTO);

		return responseDTO;
	}
	
	
	public static SystemResponseDTO<String> deleteStaffDailyAttendanceSupervision(MyRequestAction action){
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
		System.out.println("DELETE StaffDailyAttendanceSupervisionDTO " + responseDTO);

		return responseDTO;
	}
	
	
	public static SystemResponseDTO<List<StaffDailyAttendanceSupervisionDTO>> getStaffDailyTimeTablesByLoggedSystemUserProfileSchools(MyRequestAction action){
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);
		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<List<StaffDailyAttendanceSupervisionDTO>> responseDTO = client.target(ApiResourceUtil.API_LINK).path("staffDailyTimetablesByLoggedSystemUserProfileSchools")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.get(new GenericType<SystemResponseDTO<List<StaffDailyAttendanceSupervisionDTO>>>() {
				});
		client.close();
		
		
		System.out.println("GET  StaffDailyTimeTable ByLoggedSystemUserProfileSchools" + responseDTO);
		return responseDTO;
	}
	
	
	
	
}
