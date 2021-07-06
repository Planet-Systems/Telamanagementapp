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
import com.planetsystems.tela.dto.SchoolStaffDTO;
import com.planetsystems.tela.dto.StaffDailyTimeTableLessonDTO;
import com.planetsystems.tela.dto.response.SystemResponseDTO;
import com.planetsystems.tela.managementapp.shared.MyRequestAction;
import com.planetsystems.tela.managementapp.shared.RequestDelimeters;

public class StaffDailyTimetableLessonHandler {

	public static SystemResponseDTO<StaffDailyTimeTableLessonDTO> saveStaffDailyTimeTableLesson(MyRequestAction action){
		StaffDailyTimeTableLessonDTO dto = (StaffDailyTimeTableLessonDTO) action.getRequestBody().get(MyRequestAction.DATA);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<StaffDailyTimeTableLessonDTO> responseDTO = client.target(ApiResourceUtil.API_LINK).path("StaffDailyTimeTableLessons")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
						new GenericType<SystemResponseDTO<StaffDailyTimeTableLessonDTO>>() {
						});
		client.close();
		System.out.println("SAVE  StaffDailyTimeTableLesson  " + responseDTO);
		return responseDTO;
	}
	
	
	public static SystemResponseDTO<List<StaffDailyTimeTableLessonDTO>> getStaffDailyTimeTableLessons(MyRequestAction action){
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);
		FilterDTO dto = (FilterDTO) action.getRequestBody().get(MyRequestAction.DATA);
		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<List<StaffDailyTimeTableLessonDTO>> responseDTO = client.target(ApiResourceUtil.API_LINK).path("findAllStaffDailyTimetableLessons")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.post(Entity.entity(dto, MediaType.APPLICATION_JSON) ,new GenericType<SystemResponseDTO<List<StaffDailyTimeTableLessonDTO>>>() {
				});
		client.close();
		
		System.out.println("GET  StaffDailyTimeTableLesson  " + responseDTO);
		
		return responseDTO;
	}
	
	public static SystemResponseDTO<StaffDailyTimeTableLessonDTO> getStaffDailyTimeTableLessonById(MyRequestAction action){
		String id = (String) action.getRequestBody().get(RequestDelimeters.ACADEMIC_YEAR_ID);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<StaffDailyTimeTableLessonDTO> responseDTO = client.target(ApiResourceUtil.API_LINK).path("StaffDailyTimeTableLessons")
				.path(id).request(MediaType.APPLICATION_JSON).headers(headers)
				.get(new GenericType<SystemResponseDTO<StaffDailyTimeTableLessonDTO>>() {
				});

		client.close();
		System.out.println("GET  YEAR BY ID " + responseDTO);

		return responseDTO;
	}
	
	
	public static SystemResponseDTO<String> deleteStaffDailyTimeTableLesson(MyRequestAction action){
		String id = (String) action.getRequestBody().get(RequestDelimeters.ACADEMIC_YEAR_ID);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<String> responseDTO = client.target(ApiResourceUtil.API_LINK).path("StaffDailyTimeTableLessons")
				.path(id).request(MediaType.APPLICATION_JSON).headers(headers)
				.delete(new GenericType<SystemResponseDTO<String>>() {
				});

		client.close();
		System.out.println("DELETE YEAR " + responseDTO);

		return responseDTO;
	}
	
	
	public static SystemResponseDTO<StaffDailyTimeTableLessonDTO> updateStaffDailyTimeTableLesson(MyRequestAction action){
		StaffDailyTimeTableLessonDTO dto = (StaffDailyTimeTableLessonDTO) action.getRequestBody().get(MyRequestAction.DATA);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<StaffDailyTimeTableLessonDTO> responseDTO = client.target(ApiResourceUtil.API_LINK).path("StaffDailyTimeTableLessons")
				.path(dto.getId()).request(MediaType.APPLICATION_JSON).headers(headers)
				.put(Entity.entity(dto, MediaType.APPLICATION_JSON),
						new GenericType<SystemResponseDTO<StaffDailyTimeTableLessonDTO>>() {
						});
		client.close();
		System.out.println("UPDATE YEAR " + responseDTO);
		return responseDTO;
	}
	
	
	public static SystemResponseDTO<List<StaffDailyTimeTableLessonDTO>> getSchoolStaffsByLoggedSystemUserProfileSchools(MyRequestAction action){
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);
		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<List<StaffDailyTimeTableLessonDTO>> responseDTO = client.target(ApiResourceUtil.API_LINK).path("schoolStaffsByLoggedSystemUserProfileSchools")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.get(new GenericType<SystemResponseDTO<List<StaffDailyTimeTableLessonDTO>>>() {
				});
		client.close();
		
		
		System.out.println("GET  StaffDailyTimeTableLessonDTO ByLoggedSystemUserProfileSchools" + responseDTO);
		return responseDTO;
	}
	
	
	
}
