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

import com.planetsystems.tela.dto.SubjectDTO;
import com.planetsystems.tela.dto.FilterDTO;
import com.planetsystems.tela.dto.SubjectCategoryDTO;
import com.planetsystems.tela.dto.response.SystemResponseDTO;
import com.planetsystems.tela.managementapp.shared.MyRequestAction;
import com.planetsystems.tela.managementapp.shared.RequestDelimeters;

public class SubjectCategoryHandler {

	
	public static SystemResponseDTO<SubjectCategoryDTO> saveSubjectCategory(MyRequestAction action){
		SubjectCategoryDTO dto = (SubjectCategoryDTO) action.getRequestBody().get(MyRequestAction.DATA);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<SubjectCategoryDTO> responseDTO = client.target(ApiResourceUtil.API_LINK).path("subjectCategories")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
						new GenericType<SystemResponseDTO<SubjectCategoryDTO>>() {
						});
		client.close();
		
		return responseDTO;
	}
	
	
	public static SystemResponseDTO<List<SubjectCategoryDTO>> getSubjectCategorys(MyRequestAction action){
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);
		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<List<SubjectCategoryDTO>> responseDto = client.target(ApiResourceUtil.API_LINK).path("subjectCategories")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.get(new GenericType<SystemResponseDTO<List<SubjectCategoryDTO>>>() {
				});
		client.close();
		
		
		
		return responseDto;
	}
	
	public static SystemResponseDTO<SubjectCategoryDTO> getSubjectCategoryById(MyRequestAction action){
		String id = (String) action.getRequestBody().get(RequestDelimeters.SUBJECT_CATEGORY_ID);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<SubjectCategoryDTO> responseDTO = client.target(ApiResourceUtil.API_LINK).path("subjectCategories")
				.path(id).request(MediaType.APPLICATION_JSON).headers(headers)
				.get(new GenericType<SystemResponseDTO<SubjectCategoryDTO>>() {
				});

		client.close();
		System.out.println("GET  SubjectCategory BY ID " + responseDTO);

		return responseDTO;
	}
	
	
	public static SystemResponseDTO<String> deleteSubjectCategory(MyRequestAction action){
		String id = (String) action.getRequestBody().get(RequestDelimeters.SUBJECT_CATEGORY_ID);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<String> responseDTO = client.target(ApiResourceUtil.API_LINK).path("subjectCategories")
				.path(id).request(MediaType.APPLICATION_JSON).headers(headers)
				.delete(new GenericType<SystemResponseDTO<String>>() {
				});

		client.close();
		System.out.println("DELETE SubjectCategory " + responseDTO);

		return responseDTO;
	}
	
	
	public static SystemResponseDTO<SubjectCategoryDTO> updateSubjectCategory(MyRequestAction action){
		SubjectCategoryDTO dto = (SubjectCategoryDTO) action.getRequestBody().get(MyRequestAction.DATA);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<SubjectCategoryDTO> responseDTO = client.target(ApiResourceUtil.API_LINK).path("subjectCategories")
				.path(dto.getId()).request(MediaType.APPLICATION_JSON).headers(headers)
				.put(Entity.entity(dto, MediaType.APPLICATION_JSON),
						new GenericType<SystemResponseDTO<SubjectCategoryDTO>>() {
						});
		client.close();
		System.out.println("UPDATE SubjectCategory " + responseDTO);
		return responseDTO;
	}
	
	
	/////////SUBJECT
	
	public static SystemResponseDTO<SubjectDTO> saveSubject(MyRequestAction action){
		
		SubjectDTO dto = (SubjectDTO) action.getRequestBody().get(MyRequestAction.DATA);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<SubjectDTO> responseDTO = client.target(ApiResourceUtil.API_LINK).path("subjects2")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
						new GenericType<SystemResponseDTO<SubjectDTO>>() {
						});

		client.close();
		System.out.println("SAVE SUBJECT " + responseDTO);
		return responseDTO;
	}
	
	
	public static SystemResponseDTO<List<SubjectDTO>> filterSubjects(MyRequestAction action){
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);
		FilterDTO dto =  (FilterDTO) action.getRequestBody().get(MyRequestAction.DATA);

		Client client = ClientBuilder.newClient();
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<List<SubjectDTO>> responseDto = client.target(ApiResourceUtil.API_LINK).path("filterSubjects")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.post(Entity.entity(dto, MediaType.APPLICATION_JSON) ,new GenericType<SystemResponseDTO<List<SubjectDTO>>>() {
				});

		client.close();
		System.out.println("FILTER ALL SUBJECTS " + responseDto);
		return responseDto;
	}
	
	public static SystemResponseDTO<SubjectDTO> getSubjectById(MyRequestAction action){
		String id = (String) action.getRequestBody().get(RequestDelimeters.SUBJECT_ID);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<SubjectDTO> responseDto = client.target(ApiResourceUtil.API_LINK).path("subjects2")
				.path(id).request(MediaType.APPLICATION_JSON).headers(headers)
				.get(new GenericType<SystemResponseDTO<SubjectDTO>>() {
				});

		client.close();
		System.out.println("GET  SUBJECT BY ID " + responseDto);
		return responseDto;
	}
	
	
	public static SystemResponseDTO<String> deleteSubject(MyRequestAction action){
		String id = (String) action.getRequestBody().get(RequestDelimeters.SUBJECT_ID);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<String> responseDto = client.target(ApiResourceUtil.API_LINK).path("subjects2")
				.path(id).request(MediaType.APPLICATION_JSON).headers(headers)
				.delete(new GenericType<SystemResponseDTO<String>>() {
				});

		client.close();
		System.out.println("DELETE  SUBJECT " + responseDto);
		return responseDto;
	}
	
	
	public static SystemResponseDTO<SubjectDTO> updateSubject(MyRequestAction action){

		SubjectDTO dto = (SubjectDTO) action.getRequestBody().get(MyRequestAction.DATA);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<SubjectDTO> responseDTO = client.target(ApiResourceUtil.API_LINK).path("subjects2")
				.path(dto.getId()).request(MediaType.APPLICATION_JSON).headers(headers)
				.put(Entity.entity(dto, MediaType.APPLICATION_JSON),
						new GenericType<SystemResponseDTO<SubjectDTO>>() {
						});
		client.close();
		System.out.println("UPDATE  SUBJECT " + responseDTO);
		return responseDTO;
	}
	
	
	public static SystemResponseDTO<List<SubjectDTO>> getSubjectBySubjectCategory(MyRequestAction action){
        String SubjectCategoryId = (String) action.getRequestBody().get(RequestDelimeters.SUBJECT_CATEGORY_ID);
		SubjectDTO dto = (SubjectDTO) action.getRequestBody().get(MyRequestAction.DATA);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<List<SubjectDTO>> responseDTO = client.target(ApiResourceUtil.API_LINK).path("subjectCategories")
				.path(SubjectCategoryId).path("subjects").request(MediaType.APPLICATION_JSON).headers(headers)
				.get(new GenericType<SystemResponseDTO<List<SubjectDTO>>>() {
				});

		client.close();
		System.out.println("SUBJECT BY SubjectCategory " + responseDTO);
		return responseDTO;
	}
	
	

	
	
}
