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
import com.planetsystems.tela.dto.LearnerAttendanceDTO;
import com.planetsystems.tela.dto.response.SystemResponseDTO;
import com.planetsystems.tela.managementapp.shared.MyRequestAction;
import com.planetsystems.tela.managementapp.shared.RequestDelimeters;

public class LearnerAttendanceHandler {

	
	public static SystemResponseDTO<LearnerAttendanceDTO> saveLearnerAttendance(MyRequestAction action){
		LearnerAttendanceDTO dto = (LearnerAttendanceDTO) action.getRequestBody().get(MyRequestAction.DATA);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<LearnerAttendanceDTO> responseDTO = client.target(ApiResourceUtil.API_LINK).path("learnerAttendances")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
						new GenericType<SystemResponseDTO<LearnerAttendanceDTO>>() {
						});
		client.close();
		System.out.println("SAVE  LEARNER_ATTENDANCE "+responseDTO);
		
		return responseDTO;
	}
	
	
	public static SystemResponseDTO<List<LearnerAttendanceDTO>> filterLearnerAttendances(MyRequestAction action){
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);
		FilterDTO dto =  (FilterDTO) action.getRequestBody().get(MyRequestAction.DATA);
		Client client = ClientBuilder.newClient();
		

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<List<LearnerAttendanceDTO>> responseDto = client.target(ApiResourceUtil.API_LINK).path("filterLearnerAttendances")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.post(Entity.entity(dto, MediaType.APPLICATION_JSON) , new GenericType<SystemResponseDTO<List<LearnerAttendanceDTO>>>() {
				});
		client.close();
		
		System.out.println("FILTER  LEARNER_ATTENDANCES " + responseDto);
		
		return responseDto;
	}
	
	public static SystemResponseDTO<LearnerAttendanceDTO> getLearnerAttendanceById(MyRequestAction action){
		String id = (String) action.getRequestBody().get(RequestDelimeters.LEARNER_ATTENDANCE_ID);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<LearnerAttendanceDTO> responseDTO = client.target(ApiResourceUtil.API_LINK).path("learnerAttendances")
				.path(id).request(MediaType.APPLICATION_JSON).headers(headers)
				.get(new GenericType<SystemResponseDTO<LearnerAttendanceDTO>>() {
				});

		client.close();
		System.out.println("GET  LEARNER_ATTENDANCE BY ID " + responseDTO);

		return responseDTO;
	}
	
	
	public static SystemResponseDTO<String> deleteLearnerAttendance(MyRequestAction action){
		String id = (String) action.getRequestBody().get(RequestDelimeters.LEARNER_ATTENDANCE_ID);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<String> responseDTO = client.target(ApiResourceUtil.API_LINK).path("learnerAttendances")
				.path(id).request(MediaType.APPLICATION_JSON).headers(headers)
				.delete(new GenericType<SystemResponseDTO<String>>() {
				});

		client.close();
		System.out.println("DELETE LEARNER_ATTENDANCE " + responseDTO);

		return responseDTO;
	}
	
	
	public static SystemResponseDTO<LearnerAttendanceDTO> updateLearnerAttendance(MyRequestAction action){
		LearnerAttendanceDTO dto = (LearnerAttendanceDTO) action.getRequestBody().get(MyRequestAction.DATA);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<LearnerAttendanceDTO> responseDTO = client.target(ApiResourceUtil.API_LINK).path("learnerAttendances")
				.path(dto.getId()).request(MediaType.APPLICATION_JSON).headers(headers)
				.put(Entity.entity(dto, MediaType.APPLICATION_JSON),
						new GenericType<SystemResponseDTO<LearnerAttendanceDTO>>() {
						});
		client.close();
		System.out.println("UPDATE LEARNER_ATTENDANCE " + responseDTO);
		return responseDTO;
	}
	
	
	public static SystemResponseDTO<List<LearnerAttendanceDTO>> getLearnerAttendancesByLoggedSystemUserProfileSchools(MyRequestAction action){
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);
		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<List<LearnerAttendanceDTO>> responseDTO = client.target(ApiResourceUtil.API_LINK).path("learnerAttendancesByLoggedSystemUserProfileSchools")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.get(new GenericType<SystemResponseDTO<List<LearnerAttendanceDTO>>>() {
				});
		client.close();
		
		
		System.out.println("GET  LEARNER_ENROLLMENTS ByLoggedSystemUserProfileSchools" + responseDTO);
		return responseDTO;
	}
	
	

}
