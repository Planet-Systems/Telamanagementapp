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
import com.planetsystems.tela.dto.TimeTableLessonDTO;
import com.planetsystems.tela.dto.response.SystemResponseDTO;
import com.planetsystems.tela.managementapp.shared.MyRequestAction;
import com.planetsystems.tela.managementapp.shared.RequestDelimeters;

public class TimeTableLessonHandler {

	
	public static SystemResponseDTO<TimeTableLessonDTO> saveTimeTableLesson(MyRequestAction action){
		TimeTableLessonDTO dto = (TimeTableLessonDTO) action.getRequestBody().get(MyRequestAction.DATA);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<TimeTableLessonDTO> responseDTO = client.target(ApiResourceUtil.API_LINK).path("timetableLessons")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
						new GenericType<SystemResponseDTO<TimeTableLessonDTO>>() {
						});
		client.close();
		System.out.println("SAVE  TIME_TABLE_LESSONS  " + responseDTO);
		return responseDTO;
	}
	
	
	public static SystemResponseDTO<List<TimeTableLessonDTO>> getTimeTableLessons(MyRequestAction action){
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);
		FilterDTO dto = (FilterDTO) action.getRequestBody().get(MyRequestAction.DATA);
		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<List<TimeTableLessonDTO>> responseDTO = client.target(ApiResourceUtil.API_LINK).path("filterTimetableLessons")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.post(Entity.entity(dto, MediaType.APPLICATION_JSON) , new GenericType<SystemResponseDTO<List<TimeTableLessonDTO>>>() {
				});
		client.close();
		
		System.out.println("GET  TIME_TABLE_LESSONS  " + responseDTO);
		
		return responseDTO;
	}
	
	public static SystemResponseDTO<TimeTableLessonDTO> getTimeTableLessonById(MyRequestAction action){
		String id = (String) action.getRequestBody().get(RequestDelimeters.TIME_TABLE_LESSON_ID);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<TimeTableLessonDTO> responseDTO = client.target(ApiResourceUtil.API_LINK).path("TimeTableLessons")
				.path(id).request(MediaType.APPLICATION_JSON).headers(headers)
				.get(new GenericType<SystemResponseDTO<TimeTableLessonDTO>>() {
				});

		client.close();
		System.out.println("GET  TIME_TABLE_LESSON BY ID " + responseDTO);

		return responseDTO;
	}
	
	
	public static SystemResponseDTO<String> deleteTimeTableLesson(MyRequestAction action){
		String id = (String) action.getRequestBody().get(RequestDelimeters.TIME_TABLE_LESSON_ID);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<String> responseDTO = client.target(ApiResourceUtil.API_LINK).path("timetableLessons").path(id)
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.delete( new GenericType<SystemResponseDTO<String>>() {
				});

		client.close();
		System.out.println("DELETE TIME_TABLE_LESSON " + responseDTO);

		return responseDTO;
	}
	
	
	public static SystemResponseDTO<TimeTableLessonDTO> updateTimeTableLesson(MyRequestAction action){
		TimeTableLessonDTO dto = (TimeTableLessonDTO) action.getRequestBody().get(MyRequestAction.DATA);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<TimeTableLessonDTO> responseDTO = client.target(ApiResourceUtil.API_LINK).path("TimeTableLessons")
				.path(dto.getId()).request(MediaType.APPLICATION_JSON).headers(headers)
				.put(Entity.entity(dto, MediaType.APPLICATION_JSON),
						new GenericType<SystemResponseDTO<TimeTableLessonDTO>>() {
						});
		client.close();
		System.out.println("UPDATE TIME_TABLE_LESSON " + responseDTO);
		return responseDTO;
	}
	

}
