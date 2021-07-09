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
import com.planetsystems.tela.dto.StaffEnrollmentDto;
import com.planetsystems.tela.dto.response.SystemResponseDTO;
import com.planetsystems.tela.managementapp.shared.MyRequestAction;
import com.planetsystems.tela.managementapp.shared.RequestDelimeters;

public class SchoolStaffEnrollmentHandler {

	

	public static SystemResponseDTO<StaffEnrollmentDto> saveStaffEnrollment(MyRequestAction action){
		StaffEnrollmentDto dto = (StaffEnrollmentDto) action.getRequestBody().get(MyRequestAction.DATA);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<StaffEnrollmentDto> responseDTO = client.target(ApiResourceUtil.API_LINK).path("staffEnrollments")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
						new GenericType<SystemResponseDTO<StaffEnrollmentDto>>() {
						});
		client.close();
		
		System.out.println("SAVE  STAFF_ENROLLMENTS" + responseDTO);
		
		return responseDTO;
	}
	
	
	public static SystemResponseDTO<List<StaffEnrollmentDto>> getStaffEnrollments(MyRequestAction action){
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);
		FilterDTO filterDTO = (FilterDTO) action.getRequestBody().get(MyRequestAction.DATA);
		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<List<StaffEnrollmentDto>> responseDTO = client.target(ApiResourceUtil.API_LINK).path("filterStaffEnrollmentsByAcademicYearAcademicTermDistrictSchool2")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.post(Entity.entity(filterDTO , MediaType.APPLICATION_JSON) , new GenericType<SystemResponseDTO<List<StaffEnrollmentDto>>>() {
				});
		client.close();
		
		
		System.out.println("GET  STAFF_ENROLLMENTS" + responseDTO);
		return responseDTO;
	}
	
	
	public static SystemResponseDTO<List<StaffEnrollmentDto>> getStaffEnrollmentsByLoggedSystemUserProfileSchools(MyRequestAction action){
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);
		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<List<StaffEnrollmentDto>> responseDTO = client.target(ApiResourceUtil.API_LINK).path("staffEnrollmentsByLoggedSystemUserProfile")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.get(new GenericType<SystemResponseDTO<List<StaffEnrollmentDto>>>() {
				});
		client.close();
		
		
		System.out.println("GET  STAFF_ENROLLMENTS ByLoggedSystemUserProfileSchools" + responseDTO);
		return responseDTO;
	}
	
	
//	public static SystemResponseDTO<List<StaffEnrollmentDto>> getStaffEnrollmentsBySchoolStaffSchool(MyRequestAction action){
//		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);
//		Client client = ClientBuilder.newClient();
//		
//		FilterDTO filterDTO = (FilterDTO) action.getRequestBody().get(MyRequestAction.DATA);
//		
//		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
//		headers.add(HttpHeaders.AUTHORIZATION, token);
//
//		SystemResponseDTO<List<StaffEnrollmentDto>> responseDTO = client.target(ApiResourceUtil.API_LINK).path("staffEnrollmentsByAcademicYearSchoolStaffDistrictSchool2")
//				.request(MediaType.APPLICATION_JSON).headers(headers)
//				.post(Entity.entity(filterDTO, MediaType.APPLICATION_JSON) , new GenericType<SystemResponseDTO<List<StaffEnrollmentDto>>>() {
//				});
//		client.close();
//		
//		
//		System.out.println("GET  STAFF_ENROLLMENTS BY SCHOOL_STAFF SCHOOL " + responseDTO);
//		return responseDTO;
//	}
	
	
	
	
	public static SystemResponseDTO<StaffEnrollmentDto> getStaffEnrollmentById(MyRequestAction action){
		String id = (String) action.getRequestBody().get(RequestDelimeters.STAFF_ENROLLMENT_ID);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<StaffEnrollmentDto> responseDTO = client.target(ApiResourceUtil.API_LINK).path("staffEnrollments")
				.path(id).request(MediaType.APPLICATION_JSON).headers(headers)
				.get(new GenericType<SystemResponseDTO<StaffEnrollmentDto>>() {
				});

		client.close();
		System.out.println("GET  STAFF_ENROLLMENTBY ID " + responseDTO);

		return responseDTO;
	}
	
	
	public static SystemResponseDTO<String> deleteStaffEnrollment(MyRequestAction action){
		String id = (String) action.getRequestBody().get(RequestDelimeters.STAFF_ENROLLMENT_ID);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<String> responseDTO = client.target(ApiResourceUtil.API_LINK).path("staffEnrollments")
				.path(id).request(MediaType.APPLICATION_JSON).headers(headers)
				.delete(new GenericType<SystemResponseDTO<String>>() {
				});

		client.close();
		System.out.println("DELETE STAFF_ENROLLMENT" + responseDTO);

		return responseDTO;
	}
	
	
	public static SystemResponseDTO<StaffEnrollmentDto> updateStaffEnrollment(MyRequestAction action){
		StaffEnrollmentDto dto = (StaffEnrollmentDto) action.getRequestBody().get(MyRequestAction.DATA);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<StaffEnrollmentDto> responseDTO = client.target(ApiResourceUtil.API_LINK).path("StaffEnrollments")
				.path(dto.getId()).request(MediaType.APPLICATION_JSON).headers(headers)
				.put(Entity.entity(dto, MediaType.APPLICATION_JSON),
						new GenericType<SystemResponseDTO<StaffEnrollmentDto>>() {
						});
		client.close();
		System.out.println("UPDATE STAFF_ENROLLMENT" + responseDTO);
		return responseDTO;
	}
	
	
	/////////////////////////////// SCHOOL STAFF
	
	
public static SystemResponseDTO<SchoolStaffDTO> saveSchoolStaff(MyRequestAction action){
		
		SchoolStaffDTO dto = (SchoolStaffDTO) action.getRequestBody().get(MyRequestAction.DATA);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<SchoolStaffDTO> responseDTO = client.target(ApiResourceUtil.API_LINK).path("schoolStaffs")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
						new GenericType<SystemResponseDTO<SchoolStaffDTO>>() {
						});

		client.close();
		System.out.println("SAVE SCHOOL_STAFF " + responseDTO);
		return responseDTO;
	}
	
	
	public static SystemResponseDTO<List<SchoolStaffDTO>> getSchoolStaffs(MyRequestAction action){
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);
		FilterDTO filterDTO = (FilterDTO) action.getRequestBody().get(MyRequestAction.DATA);

		Client client = ClientBuilder.newClient();
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<List<SchoolStaffDTO>> responseDto = client.target(ApiResourceUtil.API_LINK).path("filterSchoolStaffByDistrictSchool")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.post(Entity.entity(filterDTO, MediaType.APPLICATION_JSON) , new GenericType<SystemResponseDTO<List<SchoolStaffDTO>>>() {
				});

		client.close();
		System.out.println("GET ALL SCHOOL_STAFF " + responseDto);
		return responseDto;
	}
	
	public static SystemResponseDTO<SchoolStaffDTO> getSchoolStaffById(MyRequestAction action){
		String id = (String) action.getRequestBody().get(RequestDelimeters.SCHOOL_STAFF_ID);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<SchoolStaffDTO> responseDto = client.target(ApiResourceUtil.API_LINK).path("schoolStaffs")
				.path(id).request(MediaType.APPLICATION_JSON).headers(headers)
				.get(new GenericType<SystemResponseDTO<SchoolStaffDTO>>() {
				});

		client.close();
		System.out.println("GET  SCHOOL_STAFF BY ID " + responseDto);
		return responseDto;
	}
	
	
	public static SystemResponseDTO<SchoolStaffDTO> getSchoolStaffByTypeSchool(MyRequestAction action){
		FilterDTO dto = (FilterDTO) action.getRequestBody().get(MyRequestAction.DATA);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<SchoolStaffDTO> responseDto = client.target(ApiResourceUtil.API_LINK).path("filterSchoolStaffByTypeSchool")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.post(Entity.entity(dto, MediaType.APPLICATION_JSON) , new GenericType<SystemResponseDTO<SchoolStaffDTO>>() {
				});

		client.close();
		System.out.println("GET  SCHOOL_STAFF BY ID " + responseDto);
		return responseDto;
	}
	
	
	
	public static SystemResponseDTO<String> deleteSchoolStaff(MyRequestAction action){
		String id = (String) action.getRequestBody().get(RequestDelimeters.SCHOOL_STAFF_ID);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<String> responseDto = client.target(ApiResourceUtil.API_LINK).path("schoolStaffs")
				.path(id).request(MediaType.APPLICATION_JSON).headers(headers)
				.delete(new GenericType<SystemResponseDTO<String>>() {
				});

		client.close();
		System.out.println("DELETE  SCHOOL_STAFF " + responseDto);
		return responseDto;
	}
	
	
	public static SystemResponseDTO<SchoolStaffDTO> updateSchoolStaff(MyRequestAction action){

		SchoolStaffDTO dto = (SchoolStaffDTO) action.getRequestBody().get(MyRequestAction.DATA);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<SchoolStaffDTO> responseDTO = client.target(ApiResourceUtil.API_LINK).path("SchoolStaffs")
				.path(dto.getId()).request(MediaType.APPLICATION_JSON).headers(headers)
				.put(Entity.entity(dto, MediaType.APPLICATION_JSON),
						new GenericType<SystemResponseDTO<SchoolStaffDTO>>() {
						});
		client.close();
		System.out.println("UPDATE  SCHOOL_STAFF " + responseDTO);
		return responseDTO;
	}
	
	
	public static SystemResponseDTO<List<SchoolStaffDTO>> getSchoolStaffsByLoggedSystemUserProfileSchools(MyRequestAction action){
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);
		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<List<SchoolStaffDTO>> responseDTO = client.target(ApiResourceUtil.API_LINK).path("schoolStaffsByLoggedSystemUserProfileSchools")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.get(new GenericType<SystemResponseDTO<List<SchoolStaffDTO>>>() {
				});
		client.close();
		
		
		System.out.println("GET  STAFF_ENROLLMENTS ByLoggedSystemUserProfileSchools" + responseDTO);
		return responseDTO;
	}
	
	
	
//	public static SystemResponseDTO<List<SchoolStaffDTO>> getSchoolStaffByYear(MyRequestAction action){
//        String yearId = (String) action.getRequestBody().get(RequestDelimeters.ACADEMIC_YEAR_ID);
//		SchoolStaffDTO dto = (SchoolStaffDTO) action.getRequestBody().get(MyRequestAction.DATA);
//		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);
//
//		Client client = ClientBuilder.newClient();
//		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
//		headers.add(HttpHeaders.AUTHORIZATION, token);
//
//		SystemResponseDTO<List<SchoolStaffDTO>> responseDTO = client.target(ApiResourceUtil.API_LINK).path("academicYears")
//				.path(yearId).path("SchoolStaffs").request(MediaType.APPLICATION_JSON).headers(headers)
//				.get(new GenericType<SystemResponseDTO<List<SchoolStaffDTO>>>() {
//				});
//
//		client.close();
//		System.out.println("SCHOOL_STAFF BY YEAR " + responseDTO);
//		return responseDTO;
//	}
	
	
}
