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
import com.planetsystems.tela.dto.LearnerEnrollmentDTO;
import com.planetsystems.tela.dto.response.SystemResponseDTO;
import com.planetsystems.tela.managementapp.shared.MyRequestAction;
import com.planetsystems.tela.managementapp.shared.RequestDelimeters;

public class LearnerEnrollmentHandler {

	
	public static SystemResponseDTO<LearnerEnrollmentDTO> saveLearnerEnrollment(MyRequestAction action){
		LearnerEnrollmentDTO dto = (LearnerEnrollmentDTO) action.getRequestBody().get(MyRequestAction.DATA);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<LearnerEnrollmentDTO> responseDTO = client.target(ApiResourceUtil.API_LINK).path("learnerEnrollments")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
						new GenericType<SystemResponseDTO<LearnerEnrollmentDTO>>() {
						});
		client.close();
		
		System.out.println("SAVE  LEARNER_ENROLLMENTS" + responseDTO);
		
		return responseDTO;
	}
	
	
	public static SystemResponseDTO<List<LearnerEnrollmentDTO>> filterLearnerEnrollments(MyRequestAction action){
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);
		FilterDTO dto = (FilterDTO) action.getRequestBody().get(MyRequestAction.DATA);
		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<List<LearnerEnrollmentDTO>> responseDTO = client.target(ApiResourceUtil.API_LINK).path("filterLearnerEnrollments")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.post(Entity.entity(dto, MediaType.APPLICATION_JSON) , new GenericType<SystemResponseDTO<List<LearnerEnrollmentDTO>>>() {
				});
		client.close();
		
		
		System.out.println("FILTER  LEARNER_ENROLLMENTS" + responseDTO);
		return responseDTO;
	}
	
	
	public static SystemResponseDTO<List<LearnerEnrollmentDTO>> getLearnerEnrollmentsByLoggedSystemUserProfileSchools(MyRequestAction action){
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);
		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<List<LearnerEnrollmentDTO>> responseDTO = client.target(ApiResourceUtil.API_LINK).path("systemUserProfileByLearnerEnrollments")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.get(new GenericType<SystemResponseDTO<List<LearnerEnrollmentDTO>>>() {
				});
		client.close();
		
		
		System.out.println("GET  LEARNER_ENROLLMENTS ByLoggedSystemUserProfileSchools" + responseDTO);
		return responseDTO;
	}
	
	
	public static SystemResponseDTO<List<LearnerEnrollmentDTO>> getLearnerEnrollmentsByAcademicTermSchool(MyRequestAction action){
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);
		Client client = ClientBuilder.newClient();
		
		FilterDTO filterDTO = (FilterDTO) action.getRequestBody().get(MyRequestAction.TOKEN);
		
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<List<LearnerEnrollmentDTO>> responseDTO = client.target(ApiResourceUtil.API_LINK).path("learnerEnrollmentsByAcademicYearAcademicTermDistrictSchool")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.post(Entity.entity(filterDTO, MediaType.APPLICATION_JSON) , new GenericType<SystemResponseDTO<List<LearnerEnrollmentDTO>>>() {
				});
		client.close();
		
		
		System.out.println("GET  LEARNER_ENROLLMENTS BY TERM SCHOOL " + responseDTO);
		return responseDTO;
	}
	
	
	
	
	public static SystemResponseDTO<LearnerEnrollmentDTO> getLearnerEnrollmentById(MyRequestAction action){
		String id = (String) action.getRequestBody().get(RequestDelimeters.LEARNER_ENROLLMENT_ID);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<LearnerEnrollmentDTO> responseDTO = client.target(ApiResourceUtil.API_LINK).path("LearnerEnrollments")
				.path(id).request(MediaType.APPLICATION_JSON).headers(headers)
				.get(new GenericType<SystemResponseDTO<LearnerEnrollmentDTO>>() {
				});

		client.close();
		System.out.println("GET  LEARNER_ENROLLMENTBY ID " + responseDTO);

		return responseDTO;
	}
	
	
	public static SystemResponseDTO<String> deleteLearnerEnrollment(MyRequestAction action){
		String id = (String) action.getRequestBody().get(RequestDelimeters.LEARNER_ENROLLMENT_ID);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<String> responseDTO = client.target(ApiResourceUtil.API_LINK).path("learnerEnrollments")
				.path(id).request(MediaType.APPLICATION_JSON).headers(headers)
				.delete(new GenericType<SystemResponseDTO<String>>() {
				});

		client.close();
		System.out.println("DELETE LEARNER_ENROLLMENT" + responseDTO);

		return responseDTO;
	}
	
	
	public static SystemResponseDTO<LearnerEnrollmentDTO> updateLearnerEnrollment(MyRequestAction action){
		LearnerEnrollmentDTO dto = (LearnerEnrollmentDTO) action.getRequestBody().get(MyRequestAction.DATA);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<LearnerEnrollmentDTO> responseDTO = client.target(ApiResourceUtil.API_LINK).path("LearnerEnrollments")
				.path(dto.getId()).request(MediaType.APPLICATION_JSON).headers(headers)
				.put(Entity.entity(dto, MediaType.APPLICATION_JSON),
						new GenericType<SystemResponseDTO<LearnerEnrollmentDTO>>() {
						});
		client.close();
		System.out.println("UPDATE LEARNER_ENROLLMENT" + responseDTO);
		return responseDTO;
	}
	

}
