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

import com.planetsystems.tela.dto.SchoolDTO;
import com.planetsystems.tela.dto.SchoolCategoryDTO;
import com.planetsystems.tela.dto.response.SystemResponseDTO;
import com.planetsystems.tela.managementapp.shared.MyRequestAction;
import com.planetsystems.tela.managementapp.shared.RequestDelimeters;

public class SchoolCatergoryClassHandler {

	
	public static SystemResponseDTO<SchoolCategoryDTO> saveSchoolCategory(MyRequestAction action){
		SchoolCategoryDTO dto = (SchoolCategoryDTO) action.getRequestBody().get(MyRequestAction.DATA);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<SchoolCategoryDTO> responseDTO = client.target(ApiResourceUtil.API_LINK).path("schoolCategories")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
						new GenericType<SystemResponseDTO<SchoolCategoryDTO>>() {
						});
		client.close();
		System.out.println("Save  CATEGORY" + responseDTO);
		return responseDTO;
	}
	
	
	public static SystemResponseDTO<List<SchoolCategoryDTO>> getSchoolCategorys(MyRequestAction action){
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);
		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<List<SchoolCategoryDTO>> responseDTO = client.target(ApiResourceUtil.API_LINK).path("schoolCategories")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.get(new GenericType<SystemResponseDTO<List<SchoolCategoryDTO>>>() {
				});
		client.close();
		
		System.out.println("GET  CATEGORIES" + responseDTO);
		
		return responseDTO;
	}
	
	
	public static SystemResponseDTO<List<SchoolCategoryDTO>> getAllSchoolCategoriesByLoggedSystemUserProfileSchools(MyRequestAction action){
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);
		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<List<SchoolCategoryDTO>> responseDTO = client.target(ApiResourceUtil.API_LINK).path("schoolCategoriesByLoggedSystemUserProfile")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.get(new GenericType<SystemResponseDTO<List<SchoolCategoryDTO>>>() {
				});
		client.close();
		
		System.out.println("GET  CATEGORIES BY PROFILE SCCHOOLS: " + responseDTO);
		
		return responseDTO;
	}
	
	
	public static SystemResponseDTO<SchoolCategoryDTO> getSchoolCategoryById(MyRequestAction action){
		String id = (String) action.getRequestBody().get(RequestDelimeters.SCHOOL_CATEGORY_ID);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<SchoolCategoryDTO> responseDTO = client.target(ApiResourceUtil.API_LINK).path("schoolCategories")
				.path(id).request(MediaType.APPLICATION_JSON).headers(headers)
				.get(new GenericType<SystemResponseDTO<SchoolCategoryDTO>>() {
				});

		client.close();
		System.out.println("GET  CATEGORY BY ID " + responseDTO);

		return responseDTO;
	}
	
	
	public static SystemResponseDTO<String> deleteSchoolCategory(MyRequestAction action){
		String id = (String) action.getRequestBody().get(RequestDelimeters.SCHOOL_CATEGORY_ID);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<String> responseDTO = client.target(ApiResourceUtil.API_LINK).path("schoolCategories")
				.path(id).request(MediaType.APPLICATION_JSON).headers(headers)
				.delete(new GenericType<SystemResponseDTO<String>>() {
				});

		client.close();
		System.out.println("DELETE CATEGORY " + responseDTO);

		return responseDTO;
	}
	
	
	public static SystemResponseDTO<SchoolCategoryDTO> updateSchoolCategory(MyRequestAction action){
		SchoolCategoryDTO dto = (SchoolCategoryDTO) action.getRequestBody().get(MyRequestAction.DATA);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<SchoolCategoryDTO> responseDTO = client.target(ApiResourceUtil.API_LINK).path("schoolCategories")
				.path(dto.getId()).request(MediaType.APPLICATION_JSON).headers(headers)
				.put(Entity.entity(dto, MediaType.APPLICATION_JSON),
						new GenericType<SystemResponseDTO<SchoolCategoryDTO>>() {
						});
		client.close();
		System.out.println("UPDATE CATEGORY " + responseDTO);
		return responseDTO;
	}
	
	
	
	
	
	
	//////////////////////////////////////////////////SCHOOL//////////////////////////////////////////////////////////////////////////////
public static SystemResponseDTO<SchoolDTO> saveSchool(MyRequestAction action){
		
		SchoolDTO dto = (SchoolDTO) action.getRequestBody().get(MyRequestAction.DATA);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<SchoolDTO> responseDTO = client.target(ApiResourceUtil.API_LINK).path("schools2")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
						new GenericType<SystemResponseDTO<SchoolDTO>>() {
						});

		client.close();
		System.out.println("SAVE SCHOOL " + responseDTO);
		return responseDTO;
	}
	
	
	public static SystemResponseDTO<List<SchoolDTO>> getSchools(MyRequestAction action){
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<List<SchoolDTO>> responseDto = client.target(ApiResourceUtil.API_LINK).path("schools2")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.get(new GenericType<SystemResponseDTO<List<SchoolDTO>>>() {
				});

		client.close();
		System.out.println("GET ALL SCHOOL " + responseDto);
		return responseDto;
	}
	
	public static SystemResponseDTO<SchoolDTO> getSchoolById(MyRequestAction action){
		String id = (String) action.getRequestBody().get(RequestDelimeters.SCHOOL_ID);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<SchoolDTO> responseDto = client.target(ApiResourceUtil.API_LINK).path("schools2")
				.path(id).request(MediaType.APPLICATION_JSON).headers(headers)
				.get(new GenericType<SystemResponseDTO<SchoolDTO>>() {
				});

		client.close();
		System.out.println("GET  SCHOOL BY ID " + responseDto);
		return responseDto;
	}
	
	
	public static SystemResponseDTO<String> deleteSchool(MyRequestAction action){
		String id = (String) action.getRequestBody().get(RequestDelimeters.SCHOOL_ID);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<String> responseDto = client.target(ApiResourceUtil.API_LINK).path("schools2")
				.path(id).request(MediaType.APPLICATION_JSON).headers(headers)
				.delete(new GenericType<SystemResponseDTO<String>>() {
				});

		client.close();
		System.out.println("DELETE  SCHOOL " + responseDto);
		return responseDto;
	}
	
	
	public static SystemResponseDTO<SchoolDTO> updateSchool(MyRequestAction action){

		SchoolDTO dto = (SchoolDTO) action.getRequestBody().get(MyRequestAction.DATA);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<SchoolDTO> responseDTO = client.target(ApiResourceUtil.API_LINK).path("schools2")
				.path(dto.getId()).request(MediaType.APPLICATION_JSON).headers(headers)
				.put(Entity.entity(dto, MediaType.APPLICATION_JSON),
						new GenericType<SystemResponseDTO<SchoolDTO>>() {
						});
		client.close();
		System.out.println("UPDATE  SCHOOL " + responseDTO);
		return responseDTO;
	}
	
	
	public static SystemResponseDTO<List<SchoolDTO>> getSchoolsByCategory(MyRequestAction action){
        String CategoryId = (String) action.getRequestBody().get(RequestDelimeters.SCHOOL_CATEGORY_ID);
		SchoolDTO dto = (SchoolDTO) action.getRequestBody().get(MyRequestAction.DATA);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<List<SchoolDTO>> responseDTO = client.target(ApiResourceUtil.API_LINK).path("schoolCategory2")
				.path(CategoryId).path("schools2").request(MediaType.APPLICATION_JSON).headers(headers)
				.get(new GenericType<SystemResponseDTO<List<SchoolDTO>>>() {
				});

		client.close();
		System.out.println("SCHOOL BY Category " + responseDTO);
		return responseDTO;
	}
	
	
	public static SystemResponseDTO<List<SchoolDTO>> getSchoolsByDistrict(MyRequestAction action){
        String id = (String) action.getRequestBody().get(RequestDelimeters.DISTRICT_ID);
		SchoolDTO dto = (SchoolDTO) action.getRequestBody().get(MyRequestAction.DATA);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<List<SchoolDTO>> responseDTO = client.target(ApiResourceUtil.API_LINK).path("districts2")
				.path(id).path("schools2").request(MediaType.APPLICATION_JSON).headers(headers)
				.get(new GenericType<SystemResponseDTO<List<SchoolDTO>>>() {
				});

		client.close();
		System.out.println("SCHOOL BY District " + responseDTO);
		return responseDTO;
	}
	
	
	public static SystemResponseDTO<List<SchoolDTO>> getAllSchoolsByLoggedSystemUserProfileSchools(MyRequestAction action){
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);
		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<List<SchoolDTO>> responseDTO = client.target(ApiResourceUtil.API_LINK).path("schoolsByLoggedSystemUserProfile")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.get(new GenericType<SystemResponseDTO<List<SchoolDTO>>>() {
				});
		client.close();
		
		System.out.println("GET  CATEGORIES BY PROFILE SCCHOOLS: " + responseDTO);
		
		return responseDTO;
	}
	
	
	
	
}
