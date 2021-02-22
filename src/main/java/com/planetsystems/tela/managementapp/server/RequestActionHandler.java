package com.planetsystems.tela.managementapp.server;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import com.google.inject.Inject;
import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;
import com.planetsystems.tela.dto.AcademicTermDTO;
import com.planetsystems.tela.dto.AcademicYearDTO;
import com.planetsystems.tela.dto.AuthenticationDTO;
import com.planetsystems.tela.dto.DistrictDTO;
import com.planetsystems.tela.dto.RegionDto;
import com.planetsystems.tela.dto.SchoolCategoryDTO;
import com.planetsystems.tela.dto.SchoolClassDTO;
import com.planetsystems.tela.dto.SchoolDTO;
import com.planetsystems.tela.dto.SubjectCategoryDTO;
import com.planetsystems.tela.dto.SubjectDTO;
import com.planetsystems.tela.dto.SystemErrorDTO;
import com.planetsystems.tela.dto.SystemFeedbackDTO;
import com.planetsystems.tela.dto.SystemResponseDTO;
import com.planetsystems.tela.dto.TokenFeedbackDTO;
import com.planetsystems.tela.managementapp.shared.RequestAction;
import com.planetsystems.tela.managementapp.shared.RequestConstant;
import com.planetsystems.tela.managementapp.shared.RequestResult;

public class RequestActionHandler implements ActionHandler<RequestAction, RequestResult> {

	final String API_LINK = APIGateWay.getInstance().getApLink();

	@Inject
	public RequestActionHandler() {
		super();
	}

	@Override
	public RequestResult execute(RequestAction action, ExecutionContext context) throws ActionException {

		try {
			if (action.getRequest().equalsIgnoreCase(RequestConstant.LOGIN)) {
				TokenFeedbackDTO feedback = new TokenFeedbackDTO();

				AuthenticationDTO dto = action.getAuthenticationDTO();

				Client client = ClientBuilder.newClient();

				SystemResponseDTO<TokenFeedbackDTO> loginResponseDTO = client.target(API_LINK).path("authenticate")
						.request(MediaType.APPLICATION_JSON).post(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<TokenFeedbackDTO>>() {
								});
				
				System.out.println("AUTH "+loginResponseDTO);

				if (loginResponseDTO != null) {
					feedback = loginResponseDTO.getData();
				}

				client.close();
				return new RequestResult(feedback);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////			
			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.SAVE_ACADEMIC_YEAR) ) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				AcademicYearDTO dto = (AcademicYearDTO) action.getRequestBody().get(RequestConstant.SAVE_ACADEMIC_YEAR);
				String token = (String)action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);

				System.out.println("Handler DTO " + dto);
				List<AcademicYearDTO> list = new ArrayList<AcademicYearDTO>();

				Client client = ClientBuilder.newClient();

				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<SystemFeedbackDTO> postResponseDTO = client.target(API_LINK).path("academicyears")
						.request(MediaType.APPLICATION_JSON)
						.headers(headers)
						.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (postResponseDTO != null) {
					feedback = postResponseDTO.getData();
					SystemResponseDTO<List<AcademicYearDTO>> getResponseDTO = client.target(API_LINK)
							.path("academicyears").request(MediaType.APPLICATION_JSON)
							.headers(headers)
							.get(new GenericType<SystemResponseDTO<List<AcademicYearDTO>>>() {
							});

					list = getResponseDTO.getData();
					System.out.println("GET DTO " + getResponseDTO);
				}

				client.close();
				return new RequestResult(feedback, list, null);

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.UPDATE_ACADEMIC_YEAR) ) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				AcademicYearDTO dto = (AcademicYearDTO) action.getRequestBody()
						.get(RequestConstant.UPDATE_ACADEMIC_YEAR);
				String token = (String)action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);

				System.out.println("DTO " + dto);
				List<AcademicYearDTO> list = new ArrayList<AcademicYearDTO>();

				Client client = ClientBuilder.newClient();

				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<SystemFeedbackDTO> updateResponseDTO = client.target(API_LINK).path("academicyears")
						.path(dto.getId()).request(MediaType.APPLICATION_JSON).headers(headers)
						.put(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (updateResponseDTO != null) {
					feedback = updateResponseDTO.getData();
					SystemResponseDTO<List<AcademicYearDTO>> getResponseDTO = client.target(API_LINK)
							.path("academicyears").request(MediaType.APPLICATION_JSON).headers(headers)
							.get(new GenericType<SystemResponseDTO<List<AcademicYearDTO>>>() {
							});

					list = getResponseDTO.getData();
					System.out.println("GET DTO " + getResponseDTO);
				}

				client.close();
				return new RequestResult(feedback, list, null);

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.DELETE_ACADEMIC_YEAR) ) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				String id = (String) action.getRequestBody().get(RequestConstant.DELETE_ACADEMIC_YEAR);
				String token = (String)action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);

				System.out.println("DTO " + id);

				List<AcademicYearDTO> list = new ArrayList<AcademicYearDTO>();

				Client client = ClientBuilder.newClient();

				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<SystemFeedbackDTO> deleteResponseDTO = client.target(API_LINK).path("academicyears")
						.path(id).request(MediaType.APPLICATION_JSON).headers(headers)
						.delete(new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
						});

				if (deleteResponseDTO != null) {
					feedback = deleteResponseDTO.getData();

					SystemResponseDTO<List<AcademicYearDTO>> getResponseDTO = client.target(API_LINK)
							.path("academicyears").request(MediaType.APPLICATION_JSON)
							.headers(headers)
							.get(new GenericType<SystemResponseDTO<List<AcademicYearDTO>>>() {
							});

					list = getResponseDTO.getData();
					System.out.println("GET DTO " + getResponseDTO);
				}

				client.close();
				return new RequestResult(feedback, list, null);

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.GET_ACADEMIC_YEAR)) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<AcademicYearDTO> list = new ArrayList<AcademicYearDTO>();

				String token = (String)action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				Client client = ClientBuilder.newClient();

				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<List<AcademicYearDTO>> responseDto = client.target(API_LINK).path("academicyears")
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<List<AcademicYearDTO>>>() {
						});

				list = responseDto.getData();

				System.out.println("RESPONSE " + responseDto);
				System.out.println("RES DATA " + responseDto.getData());
				feedback.setResponse(true);
				feedback.setMessage(responseDto.getMessage());

				client.close();
				return new RequestResult(feedback, list, null);

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////				
			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.SAVE_ACADEMIC_TERM)) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				AcademicTermDTO dto = (AcademicTermDTO) action.getRequestBody().get(RequestConstant.SAVE_ACADEMIC_TERM);
				String token = (String)action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				
				System.out.println("DTO " + dto);
				List<AcademicTermDTO> list = new ArrayList<AcademicTermDTO>();

				Client client = ClientBuilder.newClient();
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);


				SystemResponseDTO<SystemFeedbackDTO> postResponseDTO = client.target(API_LINK).path("academicterms")
						.request(MediaType.APPLICATION_JSON).headers(headers).post(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (postResponseDTO != null) {
					feedback = postResponseDTO.getData();
				}

				SystemResponseDTO<List<AcademicTermDTO>> getResponseDTO = client.target(API_LINK).path("academicterms")
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<List<AcademicTermDTO>>>() {
						});

				list = getResponseDTO.getData();
				System.out.println("GET DTO " + getResponseDTO);

				client.close();
				return new RequestResult(feedback, list, null);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////			
			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.UPDATE_ACADEMIC_TERM)) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				AcademicTermDTO dto = (AcademicTermDTO) action.getRequestBody()
						.get(RequestConstant.UPDATE_ACADEMIC_TERM);
				String token = (String)action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);

				System.out.println("DTO " + dto);
				List<AcademicTermDTO> list = new ArrayList<AcademicTermDTO>();

				Client client = ClientBuilder.newClient();
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<SystemFeedbackDTO> updateResponseDTO = client.target(API_LINK).path("academicterms")
						.path(dto.getId()).request(MediaType.APPLICATION_JSON)
						.headers(headers)
						.put(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (updateResponseDTO != null) {
					feedback = updateResponseDTO.getData();
					SystemResponseDTO<List<AcademicTermDTO>> getResponseDTO = client.target(API_LINK)
							.path("academicterms").request(MediaType.APPLICATION_JSON)
							.headers(headers)
							.get(new GenericType<SystemResponseDTO<List<AcademicTermDTO>>>() {
							});

					list = getResponseDTO.getData();
					System.out.println("GET DTO " + getResponseDTO);
				}

				client.close();
				return new RequestResult(feedback, list, null);

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.DELETE_ACADEMIC_TERM)) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				String id = (String) action.getRequestBody().get(RequestConstant.DELETE_ACADEMIC_TERM);
				String token = (String)action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);

				
				System.out.println("DTO " + id);
				List<AcademicTermDTO> list = new ArrayList<AcademicTermDTO>();

				Client client = ClientBuilder.newClient();
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<SystemFeedbackDTO> deleteResponseDTO = client.target(API_LINK).path("academicterms")
						.path(id).request(MediaType.APPLICATION_JSON).headers(headers)
						.delete(new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
						});

				if (deleteResponseDTO != null) {
					feedback = deleteResponseDTO.getData();
				}

				SystemResponseDTO<List<AcademicTermDTO>> getResponseDTO = client.target(API_LINK).path("academicterms")
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<List<AcademicTermDTO>>>() {
						});

				list = getResponseDTO.getData();
				System.out.println("GET DTO " + getResponseDTO);

				client.close();
				return new RequestResult(feedback, list, null);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.GET_ACADEMIC_TERM)) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<AcademicTermDTO> list = new ArrayList<AcademicTermDTO>();

				String token = (String)action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				
				Client client = ClientBuilder.newClient();
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<List<AcademicTermDTO>> responseDto = client.target(API_LINK).path("academicterms")
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<List<AcademicTermDTO>>>() {
						});

				list = responseDto.getData();

				System.out.println("RESPONSE " + responseDto);
				System.out.println("RES DATA " + responseDto.getData());
				feedback.setResponse(true);
				feedback.setMessage(responseDto.getMessage());

				client.close();
				return new RequestResult(feedback, list, null);

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////				
			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.SAVE_REGION)) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				RegionDto dto = (RegionDto) action.getRequestBody().get(RequestConstant.SAVE_REGION);
				System.out.println("DTO " + dto);
				List<RegionDto> list = new ArrayList<RegionDto>();
				
				String token = (String)action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);
				
				Client client = ClientBuilder.newClient();

				SystemResponseDTO<SystemFeedbackDTO> postResponseDTO = client.target(API_LINK).path("regions")
						.request(MediaType.APPLICATION_JSON).headers(headers).post(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (postResponseDTO != null) {
					feedback = postResponseDTO.getData();
				}

				SystemResponseDTO<List<RegionDto>> getResponseDTO = client.target(API_LINK).path("regions")
						.request(MediaType.APPLICATION_JSON)
						.headers(headers)
						.get(new GenericType<SystemResponseDTO<List<RegionDto>>>() {
						});

				list = getResponseDTO.getData();
				System.out.println("GET DTO " + getResponseDTO);

				client.close();
				return new RequestResult(feedback, list, null);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.UPDATE_REGION)) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				RegionDto dto = (RegionDto) action.getRequestBody().get(RequestConstant.UPDATE_REGION);
				
				String token = (String)action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);
				

				System.out.println("DTO " + dto);
				List<RegionDto> list = new ArrayList<RegionDto>();

				Client client = ClientBuilder.newClient();

				SystemResponseDTO<SystemFeedbackDTO> updateResponseDTO = client.target(API_LINK).path("regions")
						.path(dto.getId()).request(MediaType.APPLICATION_JSON)
						.headers(headers)
						.put(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (updateResponseDTO != null) {
					feedback = updateResponseDTO.getData();

					SystemResponseDTO<List<RegionDto>> getResponseDTO = client.target(API_LINK).path("regions")
							.request(MediaType.APPLICATION_JSON)
							.headers(headers)
							.get(new GenericType<SystemResponseDTO<List<RegionDto>>>() {
							});

					list = getResponseDTO.getData();
					System.out.println("GET DTO " + getResponseDTO);
				}

				client.close();
				return new RequestResult(feedback, list, null);

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////				
			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.DELETE_REGION)) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				String id = (String) action.getRequestBody().get(RequestConstant.DELETE_REGION);
				System.out.println("DTO " + id);
				List<RegionDto> list = new ArrayList<RegionDto>();

				String token = (String)action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);
				
				
				Client client = ClientBuilder.newClient();

				SystemResponseDTO<SystemFeedbackDTO> deleteResponseDTO = client.target(API_LINK).path("regions")
						.path(id).request(MediaType.APPLICATION_JSON)
						.headers(headers)
						.delete(new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
						});

				if (deleteResponseDTO != null) {
					feedback = deleteResponseDTO.getData();

					SystemResponseDTO<List<RegionDto>> getResponseDTO = client.target(API_LINK).path("regions")
							.request(MediaType.APPLICATION_JSON)
							.headers(headers)
							.get(new GenericType<SystemResponseDTO<List<RegionDto>>>() {
							});

					list = getResponseDTO.getData();
					System.out.println("GET DTO " + getResponseDTO);
				}

				client.close();
				return new RequestResult(feedback, list, null);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.GET_REGION)) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				
				List<RegionDto> list = new ArrayList<RegionDto>();
				
				String token = (String)action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);
				
				Client client = ClientBuilder.newClient();

				SystemResponseDTO<List<RegionDto>> responseDto = client.target(API_LINK).path("regions")	
						.request(MediaType.APPLICATION_JSON).headers(headers).get(new GenericType<SystemResponseDTO<List<RegionDto>>>() {
						});

				list = responseDto.getData();

				System.out.println("RESPONSE " + responseDto);
				System.out.println("RES DATA " + responseDto.getData());
				feedback.setResponse(true);
				feedback.setMessage(responseDto.getMessage());

				client.close();
				return new RequestResult(feedback, list, null);

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////				
			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.SAVE_DISTRICT)) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				DistrictDTO dto = (DistrictDTO) action.getRequestBody().get(RequestConstant.SAVE_DISTRICT);
				System.out.println("DTO " + dto);
				List<DistrictDTO> list = new ArrayList<DistrictDTO>();
				
				String token = (String)action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				Client client = ClientBuilder.newClient();

				SystemResponseDTO<SystemFeedbackDTO> postResponseDTO = client.target(API_LINK).path("districts")
						.request(MediaType.APPLICATION_JSON).headers(headers).post(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (postResponseDTO != null) {
					feedback = postResponseDTO.getData();
				}

				SystemResponseDTO<List<DistrictDTO>> getResponseDTO = client.target(API_LINK).path("districts")
						.request(MediaType.APPLICATION_JSON)
						.headers(headers)
						.get(new GenericType<SystemResponseDTO<List<DistrictDTO>>>() {
						});

				list = getResponseDTO.getData();
				System.out.println("GET DTO " + getResponseDTO);

				client.close();
				return new RequestResult(feedback, list, null);

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.UPDATE_DISTRICT)) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				DistrictDTO dto = (DistrictDTO) action.getRequestBody().get(RequestConstant.UPDATE_DISTRICT);

				System.out.println("DTO " + dto);
				List<DistrictDTO> list = new ArrayList<DistrictDTO>();
				
				String token = (String)action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				Client client = ClientBuilder.newClient();

				SystemResponseDTO<SystemFeedbackDTO> updateResponseDTO = client.target(API_LINK).path("districts")
						.path(dto.getId()).request(MediaType.APPLICATION_JSON)
						.headers(headers)
						.put(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (updateResponseDTO != null) {
					feedback = updateResponseDTO.getData();

					SystemResponseDTO<List<DistrictDTO>> getResponseDTO = client.target(API_LINK).path("districts")
							.request(MediaType.APPLICATION_JSON)
							.headers(headers)
							.get(new GenericType<SystemResponseDTO<List<DistrictDTO>>>() {
							});

					list = getResponseDTO.getData();
					System.out.println("GET DTO " + getResponseDTO);
				}

				client.close();
				return new RequestResult(feedback, list, null);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////				
			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.DELETE_DISTRICT)) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				String id = (String) action.getRequestBody().get(RequestConstant.DELETE_DISTRICT);
				System.out.println("DTO " + id);
				List<DistrictDTO> list = new ArrayList<DistrictDTO>();
				
				String token = (String)action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				Client client = ClientBuilder.newClient();

				SystemResponseDTO<SystemFeedbackDTO> deleteResponseDTO = client.target(API_LINK).path("districts")
						.path(id).request(MediaType.APPLICATION_JSON)
						.headers(headers)
						.delete(new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
						});

				if (deleteResponseDTO != null) {
					feedback = deleteResponseDTO.getData();

					SystemResponseDTO<List<DistrictDTO>> getResponseDTO = client.target(API_LINK).path("districts")
							.request(MediaType.APPLICATION_JSON)
							.headers(headers)
							.get(new GenericType<SystemResponseDTO<List<DistrictDTO>>>() {
							});

					list = getResponseDTO.getData();
					System.out.println("GET DTO " + getResponseDTO);
				}

				client.close();
				return new RequestResult(feedback, list, null);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.GET_DISTRICT)) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<DistrictDTO> list = new ArrayList<DistrictDTO>();
				
				String token = (String)action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				Client client = ClientBuilder.newClient();

				SystemResponseDTO<List<DistrictDTO>> responseDto = client.target(API_LINK).path("districts")
						.request(MediaType.APPLICATION_JSON)
						.headers(headers)
						.get(new GenericType<SystemResponseDTO<List<DistrictDTO>>>() {
						});

				list = responseDto.getData();

				System.out.println("RESPONSE " + responseDto);
				System.out.println("RES DATA " + responseDto.getData());
				feedback.setResponse(true);
				feedback.setMessage(responseDto.getMessage());

				client.close();
				return new RequestResult(feedback, list, null);
			}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			else if (action.getRequest().equalsIgnoreCase(RequestConstant.SAVE_SCHOOL_CATEGORY) && action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				SchoolCategoryDTO dto = (SchoolCategoryDTO) action.getRequestBody()
						.get(RequestConstant.SAVE_SCHOOL_CATEGORY);
				System.out.println("DTO " + dto);
				List<SchoolCategoryDTO> list = new ArrayList<SchoolCategoryDTO>();
				
				String token = (String)action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				Client client = ClientBuilder.newClient();

				SystemResponseDTO<SystemFeedbackDTO> postResponseDTO = client.target(API_LINK).path("schoolcategories")
						.request(MediaType.APPLICATION_JSON).headers(headers).post(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (postResponseDTO != null) {
					feedback = postResponseDTO.getData();
				}

				SystemResponseDTO<List<SchoolCategoryDTO>> getResponseDTO = client.target(API_LINK)
						.path("schoolcategories").request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<List<SchoolCategoryDTO>>>() {
						});

				list = getResponseDTO.getData();
				System.out.println("GET DTO " + getResponseDTO);

				client.close();
				return new RequestResult(feedback, list, null);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////				
			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.UPDATE_SCHOOL_CATEGORY) && action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				SchoolCategoryDTO dto = (SchoolCategoryDTO) action.getRequestBody()
						.get(RequestConstant.UPDATE_SCHOOL_CATEGORY);

				System.out.println("DTO " + dto);
				List<SchoolCategoryDTO> list = new ArrayList<SchoolCategoryDTO>();

				Client client = ClientBuilder.newClient();
				String token = (String)action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<SystemFeedbackDTO> updateResponseDTO = client.target(API_LINK)
						.path("schoolcategories").path(dto.getId()).request(MediaType.APPLICATION_JSON).headers(headers)
						.put(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (updateResponseDTO != null) {
					feedback = updateResponseDTO.getData();

					SystemResponseDTO<List<SchoolCategoryDTO>> getResponseDTO = client.target(API_LINK)
							.path("schoolcategories").request(MediaType.APPLICATION_JSON).headers(headers)
							.get(new GenericType<SystemResponseDTO<List<SchoolCategoryDTO>>>() {
							});

					list = getResponseDTO.getData();
					System.out.println("GET DTO " + getResponseDTO);
				}

				client.close();
				return new RequestResult(feedback, list, null);

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.DELETE_SCHOOL_CATEGORY) && action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				String id = (String) action.getRequestBody().get(RequestConstant.DELETE_SCHOOL_CATEGORY);
				System.out.println("DTO " + id);
				List<SchoolCategoryDTO> list = new ArrayList<SchoolCategoryDTO>();

				String token = (String)action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);
				
				Client client = ClientBuilder.newClient();

				SystemResponseDTO<SystemFeedbackDTO> deleteResponseDTO = client.target(API_LINK)
						.path("schoolcategories").path(id).request(MediaType.APPLICATION_JSON)
						.headers(headers)
						.delete(new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
						});

				if (deleteResponseDTO != null) {
					feedback = deleteResponseDTO.getData();

					SystemResponseDTO<List<SchoolCategoryDTO>> getResponseDTO = client.target(API_LINK)
							.path("schoolcategories").request(MediaType.APPLICATION_JSON)
							.headers(headers)
							.get(new GenericType<SystemResponseDTO<List<SchoolCategoryDTO>>>() {
							});

					list = getResponseDTO.getData();
					System.out.println("GET DTO " + getResponseDTO);
				}

				client.close();
				return new RequestResult(feedback, list, null);

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.GET_SCHOOL_CATEGORY) && action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<SchoolCategoryDTO> list = new ArrayList<SchoolCategoryDTO>();

				Client client = ClientBuilder.newClient();
				
				String token = (String)action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<List<SchoolCategoryDTO>> responseDto = client.target(API_LINK)
						.path("schoolcategories").request(MediaType.APPLICATION_JSON)
						.headers(headers)
						.get(new GenericType<SystemResponseDTO<List<SchoolCategoryDTO>>>() {
						});

				list = responseDto.getData();

				System.out.println("RESPONSE " + responseDto);
				System.out.println("RES DATA " + responseDto.getData());
				feedback.setResponse(true);
				feedback.setMessage(responseDto.getMessage());

				client.close();
				return new RequestResult(feedback, list, null);
			}

			//////////////// SCHOOLS

			else if (action.getRequest().equalsIgnoreCase(RequestConstant.SAVE_SCHOOL) && action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				SchoolDTO dto = (SchoolDTO) action.getRequestBody().get(RequestConstant.SAVE_SCHOOL);
				System.out.println("DTO " + dto);
				List<SchoolDTO> list = new ArrayList<SchoolDTO>();

				Client client = ClientBuilder.newClient();
				
				String token = (String)action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<SystemFeedbackDTO> postResponseDTO = client.target(API_LINK).path("schools")
						.request(MediaType.APPLICATION_JSON).headers(headers).post(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (postResponseDTO != null) {
					feedback = postResponseDTO.getData();
				}

				SystemResponseDTO<List<SchoolDTO>> getResponseDTO = client.target(API_LINK).path("schools")
						.request(MediaType.APPLICATION_JSON).headers(headers).get(new GenericType<SystemResponseDTO<List<SchoolDTO>>>() {
						});

				list = getResponseDTO.getData();
				System.out.println("GET DTO " + getResponseDTO);

				client.close();
				return new RequestResult(feedback, list, null);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.UPDATE_SCHOOL) && action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				SchoolDTO dto = (SchoolDTO) action.getRequestBody().get(RequestConstant.UPDATE_SCHOOL);

				System.out.println("DTO " + dto);
				List<SchoolDTO> list = new ArrayList<SchoolDTO>();
				
				String token = (String)action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				Client client = ClientBuilder.newClient();

				SystemResponseDTO<SystemFeedbackDTO> updateResponseDTO = client.target(API_LINK).path("schools")
						.path(dto.getId()).request(MediaType.APPLICATION_JSON)
						.headers(headers)
						.put(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (updateResponseDTO != null) {
					feedback = updateResponseDTO.getData();

					SystemResponseDTO<List<SchoolDTO>> getResponseDTO = client.target(API_LINK).path("schools")
							.request(MediaType.APPLICATION_JSON)
							.headers(headers)
							.get(new GenericType<SystemResponseDTO<List<SchoolDTO>>>() {
							});

					list = getResponseDTO.getData();
					System.out.println("GET DTO " + getResponseDTO);
				}

				client.close();
				return new RequestResult(feedback, list, null);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.DELETE_SCHOOL) && action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				String id = (String) action.getRequestBody().get(RequestConstant.DELETE_SCHOOL);
				System.out.println("DTO " + id);
				List<SchoolDTO> list = new ArrayList<SchoolDTO>();

				Client client = ClientBuilder.newClient();
				
				String token = (String)action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<SystemFeedbackDTO> deleteResponseDTO = client.target(API_LINK).path("schools")
						.path(id).request(MediaType.APPLICATION_JSON)
						.headers(headers)
						.delete(new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
						});

				if (deleteResponseDTO != null) {
					feedback = deleteResponseDTO.getData();

					SystemResponseDTO<List<SchoolDTO>> getResponseDTO = client.target(API_LINK).path("schools")
							.request(MediaType.APPLICATION_JSON)
							.headers(headers)
							.get(new GenericType<SystemResponseDTO<List<SchoolDTO>>>() {
							});

					list = getResponseDTO.getData();
					System.out.println("GET DTO " + getResponseDTO);
				}

				client.close();
				return new RequestResult(feedback, list, null);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.GET_SCHOOL) && action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<SchoolDTO> list = new ArrayList<SchoolDTO>();

				Client client = ClientBuilder.newClient();

				String token = (String)action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);
				
				SystemResponseDTO<List<SchoolDTO>> responseDto = client.target(API_LINK).path("schools")
						.request(MediaType.APPLICATION_JSON).headers(headers).get(new GenericType<SystemResponseDTO<List<SchoolDTO>>>() {
						});

				list = responseDto.getData();

				System.out.println("RESPONSE " + responseDto);
				System.out.println("RES DATA " + responseDto.getData());
				feedback.setResponse(true);
				feedback.setMessage(responseDto.getMessage());

				client.close();
				return new RequestResult(feedback, list, null);
			}
			////////////////// SCHOOL CLASSES

			else if (action.getRequest().equalsIgnoreCase(RequestConstant.SAVE_SCHOOL_CLASS) && action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				SchoolClassDTO dto = (SchoolClassDTO) action.getRequestBody().get(RequestConstant.SAVE_SCHOOL_CLASS);
				System.out.println("DTO " + dto);
				List<SchoolClassDTO> list = new ArrayList<SchoolClassDTO>();

				Client client = ClientBuilder.newClient();
				
				String token = (String)action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<SystemFeedbackDTO> postResponseDTO = client.target(API_LINK).path("schoolclasses")
						.request(MediaType.APPLICATION_JSON).headers(headers).post(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (postResponseDTO != null) {
					feedback = postResponseDTO.getData();
				}

				SystemResponseDTO<List<SchoolClassDTO>> getResponseDTO = client.target(API_LINK).path("schoolclasses")
						.request(MediaType.APPLICATION_JSON)
						.headers(headers)
						.get(new GenericType<SystemResponseDTO<List<SchoolClassDTO>>>() {
						});

				list = getResponseDTO.getData();
				System.out.println("GET DTO " + getResponseDTO);

				client.close();
				return new RequestResult(feedback, list, null);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.UPDATE_SCHOOL_CLASS) && action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				SchoolClassDTO dto = (SchoolClassDTO) action.getRequestBody().get(RequestConstant.UPDATE_SCHOOL_CLASS);

				System.out.println("DTO " + dto);
				List<SchoolClassDTO> list = new ArrayList<SchoolClassDTO>();

				Client client = ClientBuilder.newClient();
				
				String token = (String)action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<SystemFeedbackDTO> updateResponseDTO = client.target(API_LINK).path("schoolclasses")
						.path(dto.getId()).request(MediaType.APPLICATION_JSON)
						.headers(headers)
						.put(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (updateResponseDTO != null) {
					feedback = updateResponseDTO.getData();

					SystemResponseDTO<List<SchoolClassDTO>> getResponseDTO = client.target(API_LINK)
							.path("schoolclasses").request(MediaType.APPLICATION_JSON)
							.headers(headers)
							.get(new GenericType<SystemResponseDTO<List<SchoolClassDTO>>>() {
							});

					list = getResponseDTO.getData();
					System.out.println("GET DTO " + getResponseDTO);
				}

				client.close();
				return new RequestResult(feedback, list, null);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.DELETE_SCHOOL_CLASS) && action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				String id = (String) action.getRequestBody().get(RequestConstant.DELETE_SCHOOL_CLASS);
				System.out.println("DTO " + id);
				List<SchoolClassDTO> list = new ArrayList<SchoolClassDTO>();

				String token = (String)action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);
				
				Client client = ClientBuilder.newClient();

				SystemResponseDTO<SystemFeedbackDTO> deleteResponseDTO = client.target(API_LINK).path("schoolclasses")
						.path(id).request(MediaType.APPLICATION_JSON)
						.headers(headers)
						.delete(new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
						});

				if (deleteResponseDTO != null) {
					feedback = deleteResponseDTO.getData();

					SystemResponseDTO<List<SchoolClassDTO>> getResponseDTO = client.target(API_LINK)
							.path("schoolclasses").request(MediaType.APPLICATION_JSON)
							.headers(headers)
							.get(new GenericType<SystemResponseDTO<List<SchoolClassDTO>>>() {
							});

					list = getResponseDTO.getData();
					System.out.println("GET DTO " + getResponseDTO);
				}

				client.close();
				return new RequestResult(feedback, list, null);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.GET_SCHOOL_CLASS) && action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<SchoolClassDTO> list = new ArrayList<SchoolClassDTO>();

				Client client = ClientBuilder.newClient();
				
				String token = (String)action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<List<SchoolClassDTO>> responseDto = client.target(API_LINK).path("schoolclasses")
						.request(MediaType.APPLICATION_JSON)
						.headers(headers)
						.get(new GenericType<SystemResponseDTO<List<SchoolClassDTO>>>() {
						});

				list = responseDto.getData();

				System.out.println("RESPONSE " + responseDto);
				System.out.println("RES DATA " + responseDto.getData());
				feedback.setResponse(true);
				feedback.setMessage(responseDto.getMessage());

				client.close();
				return new RequestResult(feedback, list, null);
			}

			///////// SUBJECT CATEGORY
			else if (action.getRequest().equalsIgnoreCase(RequestConstant.SAVE_SUBJECT_CATEGORY) && action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				SubjectCategoryDTO dto = (SubjectCategoryDTO) action.getRequestBody()
						.get(RequestConstant.SAVE_SUBJECT_CATEGORY);
				System.out.println("DTO " + dto);
				List<SubjectCategoryDTO> list = new ArrayList<SubjectCategoryDTO>();

				Client client = ClientBuilder.newClient();
				
				String token = (String)action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<SystemFeedbackDTO> postResponseDTO = client.target(API_LINK).path("subjectcategories")
						.request(MediaType.APPLICATION_JSON)
						.headers(headers).post(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (postResponseDTO != null) {
					feedback = postResponseDTO.getData();
				}

				SystemResponseDTO<List<SubjectCategoryDTO>> getResponseDTO = client.target(API_LINK)
						.path("subjectcategories").request(MediaType.APPLICATION_JSON)
						.headers(headers)
						.get(new GenericType<SystemResponseDTO<List<SubjectCategoryDTO>>>() {
						});

				list = getResponseDTO.getData();
				System.out.println("GET DTO " + getResponseDTO);

				client.close();
				return new RequestResult(feedback, list, null);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.UPDATE_SUBJECT_CATEGORY) && action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				SubjectCategoryDTO dto = (SubjectCategoryDTO) action.getRequestBody()
						.get(RequestConstant.UPDATE_SUBJECT_CATEGORY);

				System.out.println("DTO " + dto);
				List<SubjectCategoryDTO> list = new ArrayList<SubjectCategoryDTO>();

				String token = (String)action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);
				
				Client client = ClientBuilder.newClient();

				SystemResponseDTO<SystemFeedbackDTO> updateResponseDTO = client.target(API_LINK)
						.path("subjectcategories").path(dto.getId()).request(MediaType.APPLICATION_JSON)
						.headers(headers)
						.put(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (updateResponseDTO != null) {
					feedback = updateResponseDTO.getData();

					SystemResponseDTO<List<SubjectCategoryDTO>> getResponseDTO = client.target(API_LINK)
							.path("subjectcategories").request(MediaType.APPLICATION_JSON)
							.headers(headers)
							.get(new GenericType<SystemResponseDTO<List<SubjectCategoryDTO>>>() {
							});

					list = getResponseDTO.getData();
					System.out.println("GET DTO " + getResponseDTO);
				}

				client.close();
				return new RequestResult(feedback, list, null);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.DELETE_SUBJECT_CATEGORY) && action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				String id = (String) action.getRequestBody().get(RequestConstant.DELETE_SUBJECT_CATEGORY);
				System.out.println("DTO " + id);
				List<SubjectCategoryDTO> list = new ArrayList<SubjectCategoryDTO>();

				String token = (String)action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);
				
				Client client = ClientBuilder.newClient();
				SystemResponseDTO<SystemFeedbackDTO> deleteResponseDTO = client.target(API_LINK)
						.path("subjectcategories").path(id).request(MediaType.APPLICATION_JSON)
						.headers(headers)
						.delete(new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
						});

				if (deleteResponseDTO != null) {
					feedback = deleteResponseDTO.getData();

					SystemResponseDTO<List<SubjectCategoryDTO>> getResponseDTO = client.target(API_LINK)
							.path("subjectcategories").request(MediaType.APPLICATION_JSON)
							.headers(headers)
							.get(new GenericType<SystemResponseDTO<List<SubjectCategoryDTO>>>() {
							});

					list = getResponseDTO.getData();
					System.out.println("GET DTO " + getResponseDTO);
				}

				client.close();
				return new RequestResult(feedback, list, null);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.GET_SUBJECT_CATEGORY) && action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<SubjectCategoryDTO> list = new ArrayList<SubjectCategoryDTO>();

				Client client = ClientBuilder.newClient();
				
				String token = (String)action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<List<SubjectCategoryDTO>> responseDto = client.target(API_LINK)
						.path("subjectcategories").request(MediaType.APPLICATION_JSON)
						.headers(headers)
						.get(new GenericType<SystemResponseDTO<List<SubjectCategoryDTO>>>() {
						});

				list = responseDto.getData();

				System.out.println("RESPONSE " + responseDto);
				System.out.println("RES DATA " + responseDto.getData());
				feedback.setResponse(true);
				feedback.setMessage(responseDto.getMessage());

				client.close();
				return new RequestResult(feedback, list, null);
			}
			////// subject

			else if (action.getRequest().equalsIgnoreCase(RequestConstant.SAVE_SUBJECT) && action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				SubjectDTO dto = (SubjectDTO) action.getRequestBody().get(RequestConstant.SAVE_SUBJECT);
				System.out.println("DTO " + dto);
				List<SubjectDTO> list = new ArrayList<SubjectDTO>();

				Client client = ClientBuilder.newClient();
				
				String token = (String)action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<SystemFeedbackDTO> postResponseDTO = client.target(API_LINK).path("subjects")
						.request(MediaType.APPLICATION_JSON)
						.headers(headers).post(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (postResponseDTO != null) {
					feedback = postResponseDTO.getData();
				}

				SystemResponseDTO<List<SubjectDTO>> getResponseDTO = client.target(API_LINK).path("subjects")
						.request(MediaType.APPLICATION_JSON)
						.headers(headers)
						.get(new GenericType<SystemResponseDTO<List<SubjectDTO>>>() {
						});

				list = getResponseDTO.getData();
				System.out.println("GET DTO " + getResponseDTO);

				client.close();
				return new RequestResult(feedback, list, null);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.UPDATE_SUBJECT) && action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				SubjectDTO dto = (SubjectDTO) action.getRequestBody().get(RequestConstant.UPDATE_SUBJECT);

				System.out.println("DTO " + dto);
				List<SubjectDTO> list = new ArrayList<SubjectDTO>();

				Client client = ClientBuilder.newClient();
				
				String token = (String)action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<SystemFeedbackDTO> updateResponseDTO = client.target(API_LINK).path("subjects")
						.path(dto.getId()).request(MediaType.APPLICATION_JSON)
						.headers(headers)
						.put(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (updateResponseDTO != null) {
					feedback = updateResponseDTO.getData();

					SystemResponseDTO<List<SubjectDTO>> getResponseDTO = client.target(API_LINK).path("subjects")
							.request(MediaType.APPLICATION_JSON)
							.headers(headers)
							.get(new GenericType<SystemResponseDTO<List<SubjectDTO>>>() {
							});

					list = getResponseDTO.getData();
					System.out.println("GET DTO " + getResponseDTO);
				}

				client.close();
				return new RequestResult(feedback, list, null);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.DELETE_SUBJECT) && action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				String id = (String) action.getRequestBody().get(RequestConstant.DELETE_SUBJECT);
				System.out.println("DTO " + id);
				List<SubjectDTO> list = new ArrayList<SubjectDTO>();

				String token = (String)action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);
				
				Client client = ClientBuilder.newClient();

				SystemResponseDTO<SystemFeedbackDTO> deleteResponseDTO = client.target(API_LINK).path("subjects")
						.path(id).request(MediaType.APPLICATION_JSON)
						.headers(headers)
						.delete(new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
						});

				if (deleteResponseDTO != null) {
					feedback = deleteResponseDTO.getData();

					SystemResponseDTO<List<SubjectDTO>> getResponseDTO = client.target(API_LINK).path("subjects")
							.request(MediaType.APPLICATION_JSON)
							.headers(headers)
							.get(new GenericType<SystemResponseDTO<List<SubjectDTO>>>() {
							});

					list = getResponseDTO.getData();
					System.out.println("GET DTO " + getResponseDTO);
				}

				client.close();
				return new RequestResult(feedback, list, null);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.GET_SUBJECT) && action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<SubjectDTO> list = new ArrayList<SubjectDTO>();

				Client client = ClientBuilder.newClient();

				String token = (String)action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);
				
				SystemResponseDTO<List<SubjectDTO>> responseDto = client.target(API_LINK).path("subjects")
						.request(MediaType.APPLICATION_JSON)
						.headers(headers)
						.get(new GenericType<SystemResponseDTO<List<SubjectDTO>>>() {
						});

				list = responseDto.getData();

				System.out.println("RESPONSE " + responseDto);
				System.out.println("RES DATA " + responseDto.getData());
				feedback.setResponse(responseDto.isStatus());
				feedback.setMessage(responseDto.getMessage());

				client.close();
				return new RequestResult(feedback, list, null);
			}

		}catch (ForbiddenException exception) {
            exception.printStackTrace();
			SystemErrorDTO error = new SystemErrorDTO();
			error.setErrorCode(exception.getResponse().getStatusInfo().getStatusCode());
			error.setMessage("Session timed out. Please login in again");
			//error.setStatus("" + exception.getResponse().getStatus());

			return new RequestResult(error);

		} catch (ProcessingException exception) {
            exception.printStackTrace();
			System.out.println("Exception:: " + exception.getMessage());
			SystemErrorDTO error = new SystemErrorDTO();
			error.setErrorCode(8082);
			error.setMessage(exception.getMessage()
					+ "\n There is a problem accessing the processing data processing engine. Please contact Systems Admin.");

			return new RequestResult(error);

		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("Eception:: " + exception.getMessage());
			SystemErrorDTO error = new SystemErrorDTO();
			error.setErrorCode(500);
			error.setMessage("System Error,  Please contact Systems Admin.");

			return new RequestResult(error);
		}


		return null;
	}

	@Override
	public Class<RequestAction> getActionType() {
		// TODO Auto-generated method stub
		return RequestAction.class;
	}

	@Override
	public void undo(RequestAction action, RequestResult result, ExecutionContext arg2) throws ActionException {
		// TODO Auto-generated method stub

	}

}
