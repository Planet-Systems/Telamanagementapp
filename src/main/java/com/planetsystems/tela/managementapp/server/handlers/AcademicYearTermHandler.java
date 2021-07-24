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

import com.planetsystems.tela.dto.AcademicTermDTO;
import com.planetsystems.tela.dto.AcademicYearDTO;
import com.planetsystems.tela.dto.FilterDTO;
import com.planetsystems.tela.dto.response.SystemResponseDTO;
import com.planetsystems.tela.managementapp.shared.MyRequestAction;
import com.planetsystems.tela.managementapp.shared.RequestDelimeters;

public class AcademicYearTermHandler {

	
	public static SystemResponseDTO<AcademicYearDTO> saveAcademicYear(MyRequestAction action){
		AcademicYearDTO dto = (AcademicYearDTO) action.getRequestBody().get(MyRequestAction.DATA);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<AcademicYearDTO> responseDTO = client.target(ApiResourceUtil.API_LINK).path("academicYears")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
						new GenericType<SystemResponseDTO<AcademicYearDTO>>() {
						});
		client.close();
		System.out.println("SAVE  YEARS  " + responseDTO);
		return responseDTO;
	}
	
	
	public static SystemResponseDTO<List<AcademicYearDTO>> getAcademicYears(MyRequestAction action){
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);
		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<List<AcademicYearDTO>> responseDTO = client.target(ApiResourceUtil.API_LINK).path("academicYears")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.get(new GenericType<SystemResponseDTO<List<AcademicYearDTO>>>() {
				});
		client.close();
		
		System.out.println("GET  YEARS  " + responseDTO);
		
		return responseDTO;
	}
	
	public static SystemResponseDTO<AcademicYearDTO> getAcademicYearById(MyRequestAction action){
		String id = (String) action.getRequestBody().get(RequestDelimeters.ACADEMIC_YEAR_ID);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<AcademicYearDTO> responseDTO = client.target(ApiResourceUtil.API_LINK).path("academicYears")
				.path(id).request(MediaType.APPLICATION_JSON).headers(headers)
				.get(new GenericType<SystemResponseDTO<AcademicYearDTO>>() {
				});

		client.close();
		System.out.println("GET  YEAR BY ID " + responseDTO);

		return responseDTO;
	}
	
	
	public static SystemResponseDTO<String> deleteAcademicYear(MyRequestAction action){
		String id = (String) action.getRequestBody().get(RequestDelimeters.ACADEMIC_YEAR_ID);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<String> responseDTO = client.target(ApiResourceUtil.API_LINK).path("academicYears")
				.path(id).request(MediaType.APPLICATION_JSON).headers(headers)
				.delete(new GenericType<SystemResponseDTO<String>>() {
				});

		client.close();
		System.out.println("DELETE YEAR " + responseDTO);

		return responseDTO;
	}
	
	
	public static SystemResponseDTO<AcademicYearDTO> updateAcademicYear(MyRequestAction action){
		AcademicYearDTO dto = (AcademicYearDTO) action.getRequestBody().get(MyRequestAction.DATA);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<AcademicYearDTO> responseDTO = client.target(ApiResourceUtil.API_LINK).path("academicYears")
				.path(dto.getId()).request(MediaType.APPLICATION_JSON).headers(headers)
				.put(Entity.entity(dto, MediaType.APPLICATION_JSON),
						new GenericType<SystemResponseDTO<AcademicYearDTO>>() {
						});
		client.close();
		System.out.println("UPDATE YEAR " + responseDTO);
		return responseDTO;
	}
	
	
	/////////TERM
	
	public static SystemResponseDTO<AcademicTermDTO> saveAcademicTerm(MyRequestAction action){
		
		AcademicTermDTO dto = (AcademicTermDTO) action.getRequestBody().get(MyRequestAction.DATA);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<AcademicTermDTO> responseDTO = client.target(ApiResourceUtil.API_LINK).path("academicTerms")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
						new GenericType<SystemResponseDTO<AcademicTermDTO>>() {
						});

		client.close();
		System.out.println("SAVE TERM " + responseDTO);
		return responseDTO;
	}
	
	
	public static SystemResponseDTO<List<AcademicTermDTO>> getAcademicTerms(MyRequestAction action){
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<List<AcademicTermDTO>> responseDto = client.target(ApiResourceUtil.API_LINK).path("academicTerms")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.get(new GenericType<SystemResponseDTO<List<AcademicTermDTO>>>() {
				});

		client.close();
		System.out.println("GET ALL TERM " + responseDto);
		return responseDto;
	}
	
	public static SystemResponseDTO<AcademicTermDTO> getAcademicTermById(MyRequestAction action){
		String id = (String) action.getRequestBody().get(RequestDelimeters.ACADEMIC_TERM_ID);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<AcademicTermDTO> responseDTO = client.target(ApiResourceUtil.API_LINK).path("academicTerms")
				.path(id).request(MediaType.APPLICATION_JSON).headers(headers)
				.get(new GenericType<SystemResponseDTO<AcademicTermDTO>>() {
				});

		client.close();
		System.out.println("GET  TERM BY ID " + responseDTO);
		return responseDTO;
	}
	
	
	public static SystemResponseDTO<String> deleteAcademicTerm(MyRequestAction action){
		String id = (String) action.getRequestBody().get(RequestDelimeters.ACADEMIC_TERM_ID);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<String> responseDTO = client.target(ApiResourceUtil.API_LINK).path("academicTerms")
				.path(id).request(MediaType.APPLICATION_JSON).headers(headers)
				.delete(new GenericType<SystemResponseDTO<String>>() {
				});

		client.close();
		System.out.println("DELETE  TERM " + responseDTO);
		return responseDTO;
	}
	
	
	public static SystemResponseDTO<AcademicTermDTO> updateAcademicTerm(MyRequestAction action){

		AcademicTermDTO dto = (AcademicTermDTO) action.getRequestBody().get(MyRequestAction.DATA);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<AcademicTermDTO> responseDTO = client.target(ApiResourceUtil.API_LINK).path("academicTerms")
				.path(dto.getId()).request(MediaType.APPLICATION_JSON).headers(headers)
				.put(Entity.entity(dto, MediaType.APPLICATION_JSON),
						new GenericType<SystemResponseDTO<AcademicTermDTO>>() {
						});
		client.close();
		System.out.println("UPDATE  TERM " + responseDTO);
		return responseDTO;
	}
	
	
	public static SystemResponseDTO<List<AcademicTermDTO>> filterAcademicTerm(MyRequestAction action){
		FilterDTO dto = (FilterDTO) action.getRequestBody().get(MyRequestAction.DATA);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<List<AcademicTermDTO>> responseDTO = client.target(ApiResourceUtil.API_LINK).path("filterAcademicTerms")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.post(Entity.entity(dto, MediaType.APPLICATION_JSON)  , new GenericType<SystemResponseDTO<List<AcademicTermDTO>>>() {
				});

		client.close();
		System.out.println("FILTER ACADEMIC TERM " + responseDTO);
		return responseDTO;
	}
	
	
	
	public static SystemResponseDTO<AcademicTermDTO> deactivateAcademicTerm(MyRequestAction action){

		String termId = (String) action.getRequestBody().get(RequestDelimeters.ACADEMIC_TERM_ID);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<AcademicTermDTO> responseDTO = client.target(ApiResourceUtil.API_LINK).path("academicTerms")
				.path(termId).path("deactivate").request(MediaType.APPLICATION_JSON).headers(headers)
				.get(new GenericType<SystemResponseDTO<AcademicTermDTO>>() {
				});

		client.close();
		System.out.println("DEACTIVATE  TERM " + responseDTO);
		return responseDTO;
	}
	
	
	
	public static SystemResponseDTO<AcademicTermDTO> activateAcademicTerm(MyRequestAction action){

		String termId = (String) action.getRequestBody().get(RequestDelimeters.ACADEMIC_TERM_ID);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<AcademicTermDTO> responseDTO = client.target(ApiResourceUtil.API_LINK).path("academicTerms")
				.path(termId).path("activate").request(MediaType.APPLICATION_JSON).headers(headers)
				.get(new GenericType<SystemResponseDTO<AcademicTermDTO>>() {
				});

		client.close();
		System.out.println("ACTIVATE  TERM " + responseDTO);
		return responseDTO;
	}
	
	
	
}
