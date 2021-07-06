package com.planetsystems.tela.managementapp.server.handlers;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import com.planetsystems.tela.dto.DistrictDTO;
import com.planetsystems.tela.dto.RegionDto;
import com.planetsystems.tela.dto.response.SystemFeedbackDTO;
import com.planetsystems.tela.dto.response.SystemResponseDTO;
import com.planetsystems.tela.managementapp.shared.MyRequestAction;
import com.planetsystems.tela.managementapp.shared.RequestConstant;
import com.planetsystems.tela.managementapp.shared.RequestDelimeters;

public class RegionDistrictHandler {

	
	public static SystemResponseDTO<RegionDto> saveRegion(MyRequestAction action){
		RegionDto dto = (RegionDto) action.getRequestBody().get(MyRequestAction.DATA);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<RegionDto> responseDTO = client.target(ApiResourceUtil.API_LINK).path("regions2")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
						new GenericType<SystemResponseDTO<RegionDto>>() {
						});
		client.close();
		
		System.out.println("SAVE REGION " + responseDTO);
		
		return responseDTO;
	}
	
	
	public static SystemResponseDTO<List<RegionDto>> getRegions(MyRequestAction action){
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);
		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<List<RegionDto>> responseDTO = client.target(ApiResourceUtil.API_LINK).path("regions2")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.get(new GenericType<SystemResponseDTO<List<RegionDto>>>() {
				});
		client.close();
		
		System.out.println("GET REGIONS " + responseDTO);
		
		return responseDTO;
	}
	
	public static SystemResponseDTO<List<RegionDto>> getAllRegionsBySystemUserProfileSchools(MyRequestAction action){
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);
		
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		Client client = ClientBuilder.newClient();

		SystemResponseDTO<List<RegionDto>> responseDTO = client.target(ApiResourceUtil.API_LINK)
				.path("regionsBySystemUserProfileSchools").request(MediaType.APPLICATION_JSON)
				.headers(headers).get(new GenericType<SystemResponseDTO<List<RegionDto>>>() {
				});

		client.close();
		System.out.println("Logged BySystemUserProfileSchools Regions " + responseDTO);
		
		return responseDTO;
	}
	
	
	public static SystemResponseDTO<RegionDto> getRegionById(MyRequestAction action){
		String id = (String) action.getRequestBody().get(RequestDelimeters.REGION_ID);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<RegionDto> responseDTO = client.target(ApiResourceUtil.API_LINK).path("regions2")
				.path(id).request(MediaType.APPLICATION_JSON).headers(headers)
				.get(new GenericType<SystemResponseDTO<RegionDto>>() {
				});

		client.close();
		System.out.println("GET REGION BY ID: " + responseDTO);

		return responseDTO;
	}
	
	public static SystemResponseDTO<String> deleteRegion(MyRequestAction action){
		String id = (String) action.getRequestBody().get(RequestDelimeters.REGION_ID);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<String> responseDTO = client.target(ApiResourceUtil.API_LINK).path("regions2")
				.path(id).request(MediaType.APPLICATION_JSON).headers(headers)
				.delete(new GenericType<SystemResponseDTO<String>>() {
				});

		client.close();
		System.out.println("DELETE REGION " + responseDTO);

		return responseDTO;
	}
	
	
	public static SystemResponseDTO<RegionDto> updateRegion(MyRequestAction action){
		RegionDto dto = (RegionDto) action.getRequestBody().get(MyRequestAction.DATA);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<RegionDto> responseDTO = client.target(ApiResourceUtil.API_LINK).path("regions2")
				.path(dto.getId()).request(MediaType.APPLICATION_JSON).headers(headers)
				.put(Entity.entity(dto, MediaType.APPLICATION_JSON),
						new GenericType<SystemResponseDTO<RegionDto>>() {
						});
		client.close();
		System.out.println("UPDATE REGION " + responseDTO);
		return responseDTO;
	}
	
	
	///////////////////DISTRICT
public static SystemResponseDTO<DistrictDTO> saveDistrict(MyRequestAction action){
		
		DistrictDTO dto = (DistrictDTO) action.getRequestBody().get(MyRequestAction.DATA);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<DistrictDTO> responseDTO = client.target(ApiResourceUtil.API_LINK).path("districts2")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
						new GenericType<SystemResponseDTO<DistrictDTO>>() {
						});

		client.close();
		System.out.println("SAVE DISTRICT " + responseDTO);
		return responseDTO;
	}
	
	
	public static SystemResponseDTO<List<DistrictDTO>> getDistricts(MyRequestAction action){
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<List<DistrictDTO>> responseDto = client.target(ApiResourceUtil.API_LINK).path("districts2")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.get(new GenericType<SystemResponseDTO<List<DistrictDTO>>>() {
				});

		client.close();
		System.out.println("GET ALL DISTRICT " + responseDto);
		return responseDto;
	}
	
	public static SystemResponseDTO<List<DistrictDTO>> getAllDistrictsBySystemUserProfileSchools(MyRequestAction action){
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		Client client = ClientBuilder.newClient();

		SystemResponseDTO<List<DistrictDTO>> responseDto = client.target(ApiResourceUtil.API_LINK).path("districtsByLoggedSystemUserProfileSchools")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.get(new GenericType<SystemResponseDTO<List<DistrictDTO>>>() {
				});

		client.close();
		
		System.out.println("GET SISTRICT BY PROFILE SCHOOL DISTRICT " + responseDto);
		return responseDto;
	}
	
	public static SystemResponseDTO<DistrictDTO> getDistrictById(MyRequestAction action){
		String id = (String) action.getRequestBody().get(RequestDelimeters.DISTRICT_ID);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<DistrictDTO> responseDto = client.target(ApiResourceUtil.API_LINK).path("districts2")
				.path(id).request(MediaType.APPLICATION_JSON).headers(headers)
				.get(new GenericType<SystemResponseDTO<DistrictDTO>>() {
				});

		client.close();
		System.out.println("GET  DISTRICT BY ID " + responseDto);
		return responseDto;
	}
	
	
	public static SystemResponseDTO<String> deleteDistrict(MyRequestAction action){
		String id = (String) action.getRequestBody().get(RequestDelimeters.DISTRICT_ID);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<String> responseDto = client.target(ApiResourceUtil.API_LINK).path("districts2")
				.path(id).request(MediaType.APPLICATION_JSON).headers(headers)
				.delete(new GenericType<SystemResponseDTO<String>>() {
				});

		client.close();
		System.out.println("DELETE  DISTRICT " + responseDto);
		return responseDto;
	}
	
	
	public static SystemResponseDTO<DistrictDTO> updateDistrict(MyRequestAction action){

		DistrictDTO dto = (DistrictDTO) action.getRequestBody().get(MyRequestAction.DATA);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<DistrictDTO> responseDTO = client.target(ApiResourceUtil.API_LINK).path("districts2")
				.path(dto.getId()).request(MediaType.APPLICATION_JSON).headers(headers)
				.put(Entity.entity(dto, MediaType.APPLICATION_JSON),
						new GenericType<SystemResponseDTO<DistrictDTO>>() {
						});
		client.close();
		System.out.println("UPDATE  DISTRICT " + responseDTO);
		return responseDTO;
	}
	
	
	public static SystemResponseDTO<List<DistrictDTO>> getDistrictByRegion(MyRequestAction action){
        String RegionId = (String) action.getRequestBody().get(RequestDelimeters.REGION_ID);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<List<DistrictDTO>> responseDTO = client.target(ApiResourceUtil.API_LINK).path("regions2")
				.path(RegionId).path("districts").request(MediaType.APPLICATION_JSON).headers(headers)
				.get(new GenericType<SystemResponseDTO<List<DistrictDTO>>>() {
				});

		client.close();
		System.out.println("DISTRICT BY Region " + responseDTO);
		return responseDTO;
	}
	
	
	public static SystemResponseDTO<List<DistrictDTO>> findAllDistrictsBySystemUserProfileSchoolsRegion(MyRequestAction action){
        String RegionId = (String) action.getRequestBody().get(RequestDelimeters.REGION_ID);
		DistrictDTO dto = (DistrictDTO) action.getRequestBody().get(MyRequestAction.DATA);
		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);

		Client client = ClientBuilder.newClient();
		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
		headers.add(HttpHeaders.AUTHORIZATION, token);

		SystemResponseDTO<List<DistrictDTO>> responseDTO = client.target(ApiResourceUtil.API_LINK).path("systemUserProfileSchools")
				.path("regions").path(RegionId).path("districts").request(MediaType.APPLICATION_JSON).headers(headers)
				.get(new GenericType<SystemResponseDTO<List<DistrictDTO>>>() {
				});

		client.close();
		System.out.println("DISTRICT BY PROFILESCHOOLREGION " + responseDTO);
		return responseDTO;
	}
	
	
	
//	
//	
//	public static SystemResponseDTO<DistrictDTO> deactivateDistrict(MyRequestAction action){
//
//		String DISTRICTId = (String) action.getRequestBody().get(RequestDelimeters.DISTRICT_ID);
//		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);
//
//		Client client = ClientBuilder.newClient();
//		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
//		headers.add(HttpHeaders.AUTHORIZATION, token);
//
//		SystemResponseDTO<DistrictDTO> responseDTO = client.target(ApiResourceUtil.API_LINK).path("districts2")
//				.path(DISTRICTId).path("deactivate").request(MediaType.APPLICATION_JSON).headers(headers)
//				.get(new GenericType<SystemResponseDTO<DistrictDTO>>() {
//				});
//
//		client.close();
//		System.out.println("DEACTIVATE  DISTRICT " + responseDTO);
//		return responseDTO;
//	}
//	
//	
//	
//	public static SystemResponseDTO<DistrictDTO> activateDistrict(MyRequestAction action){
//
//		String DISTRICTId = (String) action.getRequestBody().get(RequestDelimeters.DISTRICT_ID);
//		String token = (String) action.getRequestBody().get(MyRequestAction.TOKEN);
//
//		Client client = ClientBuilder.newClient();
//		MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
//		headers.add(HttpHeaders.AUTHORIZATION, token);
//
//		SystemResponseDTO<DistrictDTO> responseDTO = client.target(ApiResourceUtil.API_LINK).path("districts2")
//				.path(DISTRICTId).path("activate").request(MediaType.APPLICATION_JSON).headers(headers)
//				.get(new GenericType<SystemResponseDTO<DistrictDTO>>() {
//				});
//
//		client.close();
//		System.out.println("ACTIVATE  DISTRICT " + responseDTO);
//		return responseDTO;
//	}
	
	
}
