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
import com.planetsystems.tela.dto.ClockInDTO;
import com.planetsystems.tela.dto.ClockOutDTO;
import com.planetsystems.tela.dto.DistrictDTO;
import com.planetsystems.tela.dto.LearnerAttendanceDTO;
import com.planetsystems.tela.dto.LearnerEnrollmentDTO;
import com.planetsystems.tela.dto.RegionDto;
import com.planetsystems.tela.dto.SchoolCategoryDTO;
import com.planetsystems.tela.dto.SchoolClassDTO;
import com.planetsystems.tela.dto.SchoolDTO;
import com.planetsystems.tela.dto.SchoolStaffDTO;
import com.planetsystems.tela.dto.StaffEnrollmentDto;
import com.planetsystems.tela.dto.SubjectCategoryDTO;
import com.planetsystems.tela.dto.SubjectDTO;
import com.planetsystems.tela.dto.SystemErrorDTO;
import com.planetsystems.tela.dto.SystemFeedbackDTO;
import com.planetsystems.tela.dto.SystemResponseDTO;
import com.planetsystems.tela.dto.TimeTableDTO;
import com.planetsystems.tela.dto.TimeTableLessonDTO;
import com.planetsystems.tela.dto.TokenFeedbackDTO;
import com.planetsystems.tela.managementapp.client.presenter.learnerattendance.FilterLearnerAttendanceWindow;
import com.planetsystems.tela.managementapp.client.presenter.learnerenrollment.FilterLearnerHeadCountWindow;
import com.planetsystems.tela.managementapp.client.presenter.schoolcategory.FilterSchoolClassPane;
import com.planetsystems.tela.managementapp.client.presenter.schoolcategory.FilterSchoolsPane;
import com.planetsystems.tela.managementapp.client.presenter.staffattendance.FilterClockInWindow;
import com.planetsystems.tela.managementapp.client.presenter.staffattendance.FilterClockOutWindow;
import com.planetsystems.tela.managementapp.client.presenter.staffenrollment.FilterStaffHeadCountWindow;
import com.planetsystems.tela.managementapp.client.presenter.staffenrollment.FilterStaffsPane;
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

				System.out.println("AUTH " + loginResponseDTO);

				if (loginResponseDTO != null) {
					feedback = loginResponseDTO.getData();
				}

				client.close();
				return new RequestResult(feedback);

				////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.SAVE_ACADEMIC_YEAR)) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				AcademicYearDTO dto = (AcademicYearDTO) action.getRequestBody().get(RequestConstant.SAVE_ACADEMIC_YEAR);
				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);

				List<AcademicYearDTO> list = new ArrayList<AcademicYearDTO>();

				Client client = ClientBuilder.newClient();

				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<SystemFeedbackDTO> postResponseDTO = client.target(API_LINK).path("academicyears")
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (postResponseDTO != null) {
					feedback = postResponseDTO.getData();
					SystemResponseDTO<List<AcademicYearDTO>> getResponseDTO = client.target(API_LINK)
							.path("academicyears").request(MediaType.APPLICATION_JSON).headers(headers)
							.get(new GenericType<SystemResponseDTO<List<AcademicYearDTO>>>() {
							});

					list = getResponseDTO.getData();
					System.out.println("GET DTO " + getResponseDTO);
				}

				client.close();
				return new RequestResult(feedback, list, null);

				//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.UPDATE_ACADEMIC_YEAR)) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				AcademicYearDTO dto = (AcademicYearDTO) action.getRequestBody()
						.get(RequestConstant.UPDATE_ACADEMIC_YEAR);
				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);

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
			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.DELETE_ACADEMIC_YEAR)) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				String id = (String) action.getRequestBody().get(RequestConstant.DELETE_ACADEMIC_YEAR);
				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);

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
							.path("academicyears").request(MediaType.APPLICATION_JSON).headers(headers)
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

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
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
				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);

				List<AcademicTermDTO> list = new ArrayList<AcademicTermDTO>();

				Client client = ClientBuilder.newClient();
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<SystemFeedbackDTO> postResponseDTO = client.target(API_LINK).path("academicterms")
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
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
				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);

				List<AcademicTermDTO> list = new ArrayList<AcademicTermDTO>();

				Client client = ClientBuilder.newClient();
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<SystemFeedbackDTO> updateResponseDTO = client.target(API_LINK).path("academicterms")
						.path(dto.getId()).request(MediaType.APPLICATION_JSON).headers(headers)
						.put(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (updateResponseDTO != null) {
					feedback = updateResponseDTO.getData();
					SystemResponseDTO<List<AcademicTermDTO>> getResponseDTO = client.target(API_LINK)
							.path("academicterms").request(MediaType.APPLICATION_JSON).headers(headers)
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
				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);

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

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);

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
			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.GET_ACADEMIC_TERMS_IN_ACADEMIC_YEAR)) {
				String id = (String) action.getRequestBody().get(RequestConstant.GET_ACADEMIC_TERMS_IN_ACADEMIC_YEAR);
				// academicyears/ff80818177f1b9680177f1bea7330000/academicterms
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<AcademicTermDTO> list = new ArrayList<AcademicTermDTO>();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);

				Client client = ClientBuilder.newClient();
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<List<AcademicTermDTO>> responseDto = client.target(API_LINK).path("academicyears")
						.path(id).path("academicterms").request(MediaType.APPLICATION_JSON).headers(headers)
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

				List<RegionDto> list = new ArrayList<RegionDto>();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				Client client = ClientBuilder.newClient();

				SystemResponseDTO<SystemFeedbackDTO> postResponseDTO = client.target(API_LINK).path("regions")
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (postResponseDTO != null) {
					feedback = postResponseDTO.getData();
				}

				SystemResponseDTO<List<RegionDto>> getResponseDTO = client.target(API_LINK).path("regions")
						.request(MediaType.APPLICATION_JSON).headers(headers)
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

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				List<RegionDto> list = new ArrayList<RegionDto>();

				Client client = ClientBuilder.newClient();

				SystemResponseDTO<SystemFeedbackDTO> updateResponseDTO = client.target(API_LINK).path("regions")
						.path(dto.getId()).request(MediaType.APPLICATION_JSON).headers(headers)
						.put(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (updateResponseDTO != null) {
					feedback = updateResponseDTO.getData();

					SystemResponseDTO<List<RegionDto>> getResponseDTO = client.target(API_LINK).path("regions")
							.request(MediaType.APPLICATION_JSON).headers(headers)
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

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				Client client = ClientBuilder.newClient();

				SystemResponseDTO<SystemFeedbackDTO> deleteResponseDTO = client.target(API_LINK).path("regions")
						.path(id).request(MediaType.APPLICATION_JSON).headers(headers)
						.delete(new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
						});

				if (deleteResponseDTO != null) {
					feedback = deleteResponseDTO.getData();

					SystemResponseDTO<List<RegionDto>> getResponseDTO = client.target(API_LINK).path("regions")
							.request(MediaType.APPLICATION_JSON).headers(headers)
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

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				Client client = ClientBuilder.newClient();

				SystemResponseDTO<List<RegionDto>> responseDto = client.target(API_LINK).path("regions")
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<List<RegionDto>>>() {
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

				List<DistrictDTO> list = new ArrayList<DistrictDTO>();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				Client client = ClientBuilder.newClient();

				SystemResponseDTO<SystemFeedbackDTO> postResponseDTO = client.target(API_LINK).path("districts")
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (postResponseDTO != null) {
					feedback = postResponseDTO.getData();
				}

				SystemResponseDTO<List<DistrictDTO>> getResponseDTO = client.target(API_LINK).path("districts")
						.request(MediaType.APPLICATION_JSON).headers(headers)
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

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				Client client = ClientBuilder.newClient();

				SystemResponseDTO<SystemFeedbackDTO> updateResponseDTO = client.target(API_LINK).path("districts")
						.path(dto.getId()).request(MediaType.APPLICATION_JSON).headers(headers)
						.put(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (updateResponseDTO != null) {
					feedback = updateResponseDTO.getData();

					SystemResponseDTO<List<DistrictDTO>> getResponseDTO = client.target(API_LINK).path("districts")
							.request(MediaType.APPLICATION_JSON).headers(headers)
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

				List<DistrictDTO> list = new ArrayList<DistrictDTO>();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				Client client = ClientBuilder.newClient();

				SystemResponseDTO<SystemFeedbackDTO> deleteResponseDTO = client.target(API_LINK).path("districts")
						.path(id).request(MediaType.APPLICATION_JSON).headers(headers)
						.delete(new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
						});

				if (deleteResponseDTO != null) {
					feedback = deleteResponseDTO.getData();

					SystemResponseDTO<List<DistrictDTO>> getResponseDTO = client.target(API_LINK).path("districts")
							.request(MediaType.APPLICATION_JSON).headers(headers)
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

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				Client client = ClientBuilder.newClient();

				SystemResponseDTO<List<DistrictDTO>> responseDto = client.target(API_LINK).path("districts")
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<List<DistrictDTO>>>() {
						});

				list = responseDto.getData();

				System.out.println("RESPONSE " + responseDto);
				System.out.println("RES DATA " + responseDto.getData());
				feedback.setResponse(true);
				feedback.setMessage(responseDto.getMessage());

				client.close();
				return new RequestResult(feedback, list, null);
			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.GET_DISTRICTS_IN_REGION)) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<DistrictDTO> list = new ArrayList<DistrictDTO>();
				String id = (String) action.getRequestBody().get(RequestConstant.GET_DISTRICTS_IN_REGION);

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				Client client = ClientBuilder.newClient();

				SystemResponseDTO<List<DistrictDTO>> responseDto = client.target(API_LINK).path("regions").path(id)
						.path("districts").request(MediaType.APPLICATION_JSON).headers(headers)
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
			else if (action.getRequest().equalsIgnoreCase(RequestConstant.SAVE_SCHOOL_CATEGORY)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				SchoolCategoryDTO dto = (SchoolCategoryDTO) action.getRequestBody()
						.get(RequestConstant.SAVE_SCHOOL_CATEGORY);

				List<SchoolCategoryDTO> list = new ArrayList<SchoolCategoryDTO>();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				Client client = ClientBuilder.newClient();

				SystemResponseDTO<SystemFeedbackDTO> postResponseDTO = client.target(API_LINK).path("schoolcategories")
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
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
			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.UPDATE_SCHOOL_CATEGORY)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				SchoolCategoryDTO dto = (SchoolCategoryDTO) action.getRequestBody()
						.get(RequestConstant.UPDATE_SCHOOL_CATEGORY);

				List<SchoolCategoryDTO> list = new ArrayList<SchoolCategoryDTO>();

				Client client = ClientBuilder.newClient();
				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
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
			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.DELETE_SCHOOL_CATEGORY)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				String id = (String) action.getRequestBody().get(RequestConstant.DELETE_SCHOOL_CATEGORY);
				System.out.println("DTO " + id);
				List<SchoolCategoryDTO> list = new ArrayList<SchoolCategoryDTO>();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				Client client = ClientBuilder.newClient();

				SystemResponseDTO<SystemFeedbackDTO> deleteResponseDTO = client.target(API_LINK)
						.path("schoolcategories").path(id).request(MediaType.APPLICATION_JSON).headers(headers)
						.delete(new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
						});

				if (deleteResponseDTO != null) {
					feedback = deleteResponseDTO.getData();

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
			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.GET_SCHOOL_CATEGORY)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<SchoolCategoryDTO> list = new ArrayList<SchoolCategoryDTO>();

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<List<SchoolCategoryDTO>> responseDto = client.target(API_LINK)
						.path("schoolcategories").request(MediaType.APPLICATION_JSON).headers(headers)
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

			else if (action.getRequest().equalsIgnoreCase(RequestConstant.SAVE_SCHOOL)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				SchoolDTO dto = (SchoolDTO) action.getRequestBody().get(RequestConstant.SAVE_SCHOOL);

				List<SchoolDTO> list = new ArrayList<SchoolDTO>();

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<SystemFeedbackDTO> postResponseDTO = client.target(API_LINK).path("schools")
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (postResponseDTO != null) {
					feedback = postResponseDTO.getData();
				}

				SystemResponseDTO<List<SchoolDTO>> getResponseDTO = client.target(API_LINK).path("schools")
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<List<SchoolDTO>>>() {
						});

				list = getResponseDTO.getData();
				System.out.println("GET DTO " + getResponseDTO);

				client.close();
				return new RequestResult(feedback, list, null);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.UPDATE_SCHOOL)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				SchoolDTO dto = (SchoolDTO) action.getRequestBody().get(RequestConstant.UPDATE_SCHOOL);

				List<SchoolDTO> list = new ArrayList<SchoolDTO>();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				Client client = ClientBuilder.newClient();

				SystemResponseDTO<SystemFeedbackDTO> updateResponseDTO = client.target(API_LINK).path("schools")
						.path(dto.getId()).request(MediaType.APPLICATION_JSON).headers(headers)
						.put(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (updateResponseDTO != null) {
					feedback = updateResponseDTO.getData();

					SystemResponseDTO<List<SchoolDTO>> getResponseDTO = client.target(API_LINK).path("schools")
							.request(MediaType.APPLICATION_JSON).headers(headers)
							.get(new GenericType<SystemResponseDTO<List<SchoolDTO>>>() {
							});

					list = getResponseDTO.getData();
					System.out.println("GET DTO " + getResponseDTO);
				}

				client.close();
				return new RequestResult(feedback, list, null);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.DELETE_SCHOOL)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				String id = (String) action.getRequestBody().get(RequestConstant.DELETE_SCHOOL);

				List<SchoolDTO> list = new ArrayList<SchoolDTO>();

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<SystemFeedbackDTO> deleteResponseDTO = client.target(API_LINK).path("schools")
						.path(id).request(MediaType.APPLICATION_JSON).headers(headers)
						.delete(new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
						});

				if (deleteResponseDTO != null) {
					feedback = deleteResponseDTO.getData();

					SystemResponseDTO<List<SchoolDTO>> getResponseDTO = client.target(API_LINK).path("schools")
							.request(MediaType.APPLICATION_JSON).headers(headers)
							.get(new GenericType<SystemResponseDTO<List<SchoolDTO>>>() {
							});

					list = getResponseDTO.getData();
					System.out.println("GET DTO " + getResponseDTO);
				}

				client.close();
				return new RequestResult(feedback, list, null);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.GET_SCHOOL)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<SchoolDTO> list = new ArrayList<SchoolDTO>();

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<List<SchoolDTO>> responseDto = client.target(API_LINK).path("schools")
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<List<SchoolDTO>>>() {
						});

				list = responseDto.getData();

				System.out.println("RESPONSE " + responseDto);
				System.out.println("RES DATA " + responseDto.getData());
				feedback.setResponse(true);
				feedback.setMessage(responseDto.getMessage());

				client.close();
				return new RequestResult(feedback, list, null);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.GET_SCHOOLS_IN_DISTRICT)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				String id = (String) action.getRequestBody().get(RequestConstant.GET_SCHOOLS_IN_DISTRICT);
				List<SchoolDTO> list = new ArrayList<SchoolDTO>();

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<List<SchoolDTO>> responseDto = client.target(API_LINK).path("districts").path(id)
						.path("schools").request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<List<SchoolDTO>>>() {
						});

				list = responseDto.getData();

				System.out.println("RESPONSE " + responseDto);
				System.out.println("RES DATA " + responseDto.getData());
				feedback.setResponse(true);
				feedback.setMessage(responseDto.getMessage());

				client.close();
				return new RequestResult(feedback, list, null);
			}

			else if (action.getRequest().equalsIgnoreCase(RequestConstant.GET_SCHOOLS_IN_SCHOOL_CATEGORY_DISTRICT)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				String districtId = (String) action.getRequestBody().get(FilterSchoolsPane.DISTRICT_ID);
				String schoolCategoryId = (String) action.getRequestBody().get(FilterSchoolsPane.SCHOOL_CATEGORY_ID);
				List<SchoolDTO> list = new ArrayList<SchoolDTO>();

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<List<SchoolDTO>> responseDto = client.target(API_LINK).path("schoolcategories")
						.path(schoolCategoryId).path("districts").path(districtId).path("schools")
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<List<SchoolDTO>>>() {
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

			else if (action.getRequest().equalsIgnoreCase(RequestConstant.SAVE_SCHOOL_CLASS)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				SchoolClassDTO dto = (SchoolClassDTO) action.getRequestBody().get(RequestConstant.SAVE_SCHOOL_CLASS);
				;
				List<SchoolClassDTO> list = new ArrayList<SchoolClassDTO>();

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<SystemFeedbackDTO> postResponseDTO = client.target(API_LINK).path("schoolclasses")
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (postResponseDTO != null) {
					feedback = postResponseDTO.getData();
				}

				SystemResponseDTO<List<SchoolClassDTO>> getResponseDTO = client.target(API_LINK).path("schoolclasses")
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<List<SchoolClassDTO>>>() {
						});

				list = getResponseDTO.getData();
				System.out.println("GET DTO " + getResponseDTO);

				client.close();
				return new RequestResult(feedback, list, null);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.UPDATE_SCHOOL_CLASS)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				SchoolClassDTO dto = (SchoolClassDTO) action.getRequestBody().get(RequestConstant.UPDATE_SCHOOL_CLASS);

				List<SchoolClassDTO> list = new ArrayList<SchoolClassDTO>();

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<SystemFeedbackDTO> updateResponseDTO = client.target(API_LINK).path("schoolclasses")
						.path(dto.getId()).request(MediaType.APPLICATION_JSON).headers(headers)
						.put(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (updateResponseDTO != null) {
					feedback = updateResponseDTO.getData();

					SystemResponseDTO<List<SchoolClassDTO>> getResponseDTO = client.target(API_LINK)
							.path("schoolclasses").request(MediaType.APPLICATION_JSON).headers(headers)
							.get(new GenericType<SystemResponseDTO<List<SchoolClassDTO>>>() {
							});

					list = getResponseDTO.getData();
					System.out.println("GET DTO " + getResponseDTO);
				}

				client.close();
				return new RequestResult(feedback, list, null);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.DELETE_SCHOOL_CLASS)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				String id = (String) action.getRequestBody().get(RequestConstant.DELETE_SCHOOL_CLASS);

				List<SchoolClassDTO> list = new ArrayList<SchoolClassDTO>();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				Client client = ClientBuilder.newClient();

				SystemResponseDTO<SystemFeedbackDTO> deleteResponseDTO = client.target(API_LINK).path("schoolclasses")
						.path(id).request(MediaType.APPLICATION_JSON).headers(headers)
						.delete(new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
						});

				if (deleteResponseDTO != null) {
					feedback = deleteResponseDTO.getData();

					SystemResponseDTO<List<SchoolClassDTO>> getResponseDTO = client.target(API_LINK)
							.path("schoolclasses").request(MediaType.APPLICATION_JSON).headers(headers)
							.get(new GenericType<SystemResponseDTO<List<SchoolClassDTO>>>() {
							});

					list = getResponseDTO.getData();
					System.out.println("GET DTO " + getResponseDTO);
				}

				client.close();
				return new RequestResult(feedback, list, null);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.GET_SCHOOL_CLASS)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<SchoolClassDTO> list = new ArrayList<SchoolClassDTO>();

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<List<SchoolClassDTO>> responseDto = client.target(API_LINK).path("schoolclasses")
						.request(MediaType.APPLICATION_JSON).headers(headers)
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

			else if (action.getRequest().equalsIgnoreCase(RequestConstant.GET_SCHOOL_CLASSES_IN_SCHOOL)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<SchoolClassDTO> list = new ArrayList<SchoolClassDTO>();
				String id = (String) action.getRequestBody().get(RequestConstant.GET_SCHOOL_CLASSES_IN_SCHOOL);

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);
				// schools/ff80818177f1b9680177f1c1f6960007/schoolclasses
				// /schools/{id}/schoolclasses
				SystemResponseDTO<List<SchoolClassDTO>> responseDto = client.target(API_LINK).path("schools").path(id)
						.path("schoolclasses").request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<List<SchoolClassDTO>>>() {
						});

				list = responseDto.getData();

				System.out.println("RESPONSE " + responseDto);
				System.out.println("RES DATA " + responseDto.getData());
				feedback.setResponse(true);
				feedback.setMessage(responseDto.getMessage());

				client.close();
				return new RequestResult(feedback, list, null);
			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.GET_SCHOOL_CLASS_IN_SCHOOL_ACADEMIC)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<SchoolClassDTO> list = new ArrayList<SchoolClassDTO>();
				String schoolId = (String) action.getRequestBody().get(FilterSchoolClassPane.SCHOOL_ID);
				String academicTermId = (String) action.getRequestBody().get(FilterSchoolClassPane.ACADEMIC_TERM_ID);

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);
				/*
				 * /academicterms/ff80818177f1b9680177f1bfcd040002/schools/
				 * ff80818177f1b9680177f1c3329e000b/schoolclasses
				 */
				SystemResponseDTO<List<SchoolClassDTO>> responseDto = client.target(API_LINK).path("academicterms")
						.path(academicTermId).path("schools").path(schoolId).path("schoolclasses")
						.request(MediaType.APPLICATION_JSON).headers(headers)
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
			else if (action.getRequest().equalsIgnoreCase(RequestConstant.SAVE_SUBJECT_CATEGORY)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				SubjectCategoryDTO dto = (SubjectCategoryDTO) action.getRequestBody()
						.get(RequestConstant.SAVE_SUBJECT_CATEGORY);

				List<SubjectCategoryDTO> list = new ArrayList<SubjectCategoryDTO>();

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<SystemFeedbackDTO> postResponseDTO = client.target(API_LINK).path("subjectcategories")
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (postResponseDTO != null) {
					feedback = postResponseDTO.getData();
				}

				SystemResponseDTO<List<SubjectCategoryDTO>> getResponseDTO = client.target(API_LINK)
						.path("subjectcategories").request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<List<SubjectCategoryDTO>>>() {
						});

				list = getResponseDTO.getData();
				System.out.println("GET DTO " + getResponseDTO);

				client.close();
				return new RequestResult(feedback, list, null);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.UPDATE_SUBJECT_CATEGORY)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				SubjectCategoryDTO dto = (SubjectCategoryDTO) action.getRequestBody()
						.get(RequestConstant.UPDATE_SUBJECT_CATEGORY);

				List<SubjectCategoryDTO> list = new ArrayList<SubjectCategoryDTO>();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				Client client = ClientBuilder.newClient();

				SystemResponseDTO<SystemFeedbackDTO> updateResponseDTO = client.target(API_LINK)
						.path("subjectcategories").path(dto.getId()).request(MediaType.APPLICATION_JSON)
						.headers(headers).put(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (updateResponseDTO != null) {
					feedback = updateResponseDTO.getData();

					SystemResponseDTO<List<SubjectCategoryDTO>> getResponseDTO = client.target(API_LINK)
							.path("subjectcategories").request(MediaType.APPLICATION_JSON).headers(headers)
							.get(new GenericType<SystemResponseDTO<List<SubjectCategoryDTO>>>() {
							});

					list = getResponseDTO.getData();
					System.out.println("GET DTO " + getResponseDTO);
				}

				client.close();
				return new RequestResult(feedback, list, null);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.DELETE_SUBJECT_CATEGORY)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				String id = (String) action.getRequestBody().get(RequestConstant.DELETE_SUBJECT_CATEGORY);

				List<SubjectCategoryDTO> list = new ArrayList<SubjectCategoryDTO>();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				Client client = ClientBuilder.newClient();
				SystemResponseDTO<SystemFeedbackDTO> deleteResponseDTO = client.target(API_LINK)
						.path("subjectcategories").path(id).request(MediaType.APPLICATION_JSON).headers(headers)
						.delete(new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
						});

				if (deleteResponseDTO != null) {
					feedback = deleteResponseDTO.getData();

					SystemResponseDTO<List<SubjectCategoryDTO>> getResponseDTO = client.target(API_LINK)
							.path("subjectcategories").request(MediaType.APPLICATION_JSON).headers(headers)
							.get(new GenericType<SystemResponseDTO<List<SubjectCategoryDTO>>>() {
							});

					list = getResponseDTO.getData();
					System.out.println("GET DTO " + getResponseDTO);
				}

				client.close();
				return new RequestResult(feedback, list, null);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.GET_SUBJECT_CATEGORY)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<SubjectCategoryDTO> list = new ArrayList<SubjectCategoryDTO>();

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<List<SubjectCategoryDTO>> responseDto = client.target(API_LINK)
						.path("subjectcategories").request(MediaType.APPLICATION_JSON).headers(headers)
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

			else if (action.getRequest().equalsIgnoreCase(RequestConstant.SAVE_SUBJECT)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				SubjectDTO dto = (SubjectDTO) action.getRequestBody().get(RequestConstant.SAVE_SUBJECT);

				List<SubjectDTO> list = new ArrayList<SubjectDTO>();

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<SystemFeedbackDTO> postResponseDTO = client.target(API_LINK).path("subjects")
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (postResponseDTO != null) {
					feedback = postResponseDTO.getData();
				}

				SystemResponseDTO<List<SubjectDTO>> getResponseDTO = client.target(API_LINK).path("subjects")
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<List<SubjectDTO>>>() {
						});

				list = getResponseDTO.getData();
				System.out.println("GET DTO " + getResponseDTO);

				client.close();
				return new RequestResult(feedback, list, null);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.UPDATE_SUBJECT)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				SubjectDTO dto = (SubjectDTO) action.getRequestBody().get(RequestConstant.UPDATE_SUBJECT);

				List<SubjectDTO> list = new ArrayList<SubjectDTO>();

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<SystemFeedbackDTO> updateResponseDTO = client.target(API_LINK).path("subjects")
						.path(dto.getId()).request(MediaType.APPLICATION_JSON).headers(headers)
						.put(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (updateResponseDTO != null) {
					feedback = updateResponseDTO.getData();

					SystemResponseDTO<List<SubjectDTO>> getResponseDTO = client.target(API_LINK).path("subjects")
							.request(MediaType.APPLICATION_JSON).headers(headers)
							.get(new GenericType<SystemResponseDTO<List<SubjectDTO>>>() {
							});

					list = getResponseDTO.getData();
					System.out.println("GET DTO " + getResponseDTO);
				}

				client.close();
				return new RequestResult(feedback, list, null);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.DELETE_SUBJECT)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				String id = (String) action.getRequestBody().get(RequestConstant.DELETE_SUBJECT);

				List<SubjectDTO> list = new ArrayList<SubjectDTO>();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				Client client = ClientBuilder.newClient();

				SystemResponseDTO<SystemFeedbackDTO> deleteResponseDTO = client.target(API_LINK).path("subjects")
						.path(id).request(MediaType.APPLICATION_JSON).headers(headers)
						.delete(new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
						});

				if (deleteResponseDTO != null) {
					feedback = deleteResponseDTO.getData();

					SystemResponseDTO<List<SubjectDTO>> getResponseDTO = client.target(API_LINK).path("subjects")
							.request(MediaType.APPLICATION_JSON).headers(headers)
							.get(new GenericType<SystemResponseDTO<List<SubjectDTO>>>() {
							});

					list = getResponseDTO.getData();
					System.out.println("GET DTO " + getResponseDTO);
				}

				client.close();
				return new RequestResult(feedback, list, null);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.GET_SUBJECT)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<SubjectDTO> list = new ArrayList<SubjectDTO>();

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<List<SubjectDTO>> responseDto = client.target(API_LINK).path("subjects")
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<List<SubjectDTO>>>() {
						});

				list = responseDto.getData();

				System.out.println("RESPONSE " + responseDto);
				System.out.println("RES DATA " + responseDto.getData());
				feedback.setResponse(responseDto.isStatus());
				feedback.setMessage(responseDto.getMessage());

				client.close();
				return new RequestResult(feedback, list, null);
			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.GET_SUBJECTS_SUBJECT_CATEGORY)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<SubjectDTO> list = new ArrayList<SubjectDTO>();
				String id = (String) action.getRequestBody().get(RequestConstant.GET_SUBJECTS_SUBJECT_CATEGORY);

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<List<SubjectDTO>> responseDto = client.target(API_LINK).path("subjectcategories")
						.path(id).path("subjects").request(MediaType.APPLICATION_JSON).headers(headers)
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

			////////////////////// staff
			else if (action.getRequest().equalsIgnoreCase(RequestConstant.SAVE_SCHOOL_STAFF)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				SchoolStaffDTO dto = (SchoolStaffDTO) action.getRequestBody().get(RequestConstant.SAVE_SCHOOL_STAFF);

				List<SchoolStaffDTO> list = new ArrayList<SchoolStaffDTO>();

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<SystemFeedbackDTO> postResponseDTO = client.target(API_LINK).path("schoolstaffs")
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (postResponseDTO != null) {
					feedback = postResponseDTO.getData();
				}

				SystemResponseDTO<List<SchoolStaffDTO>> getResponseDTO = client.target(API_LINK).path("schoolstaffs")
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<List<SchoolStaffDTO>>>() {
						});

				list = getResponseDTO.getData();
				System.out.println("GET DTO " + getResponseDTO);

				client.close();
				return new RequestResult(feedback, list, null);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.UPDATE_SCHOOL_STAFF)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				SchoolStaffDTO dto = (SchoolStaffDTO) action.getRequestBody().get(RequestConstant.UPDATE_SCHOOL_STAFF);

				List<SchoolStaffDTO> list = new ArrayList<SchoolStaffDTO>();

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<SystemFeedbackDTO> updateResponseDTO = client.target(API_LINK).path("schoolstaffs")
						.path(dto.getId()).request(MediaType.APPLICATION_JSON).headers(headers)
						.put(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (updateResponseDTO != null) {
					feedback = updateResponseDTO.getData();

					SystemResponseDTO<List<SchoolStaffDTO>> getResponseDTO = client.target(API_LINK)
							.path("schoolstaffs").request(MediaType.APPLICATION_JSON).headers(headers)
							.get(new GenericType<SystemResponseDTO<List<SchoolStaffDTO>>>() {
							});

					list = getResponseDTO.getData();
					System.out.println("GET DTO " + getResponseDTO);
				}

				client.close();
				return new RequestResult(feedback, list, null);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.DELETE_SCHOOL_STAFF)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				String id = (String) action.getRequestBody().get(RequestConstant.DELETE_SCHOOL_STAFF);

				List<SchoolStaffDTO> list = new ArrayList<SchoolStaffDTO>();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				Client client = ClientBuilder.newClient();

				SystemResponseDTO<SystemFeedbackDTO> deleteResponseDTO = client.target(API_LINK).path("schoolstaffs")
						.path(id).request(MediaType.APPLICATION_JSON).headers(headers)
						.delete(new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
						});

				if (deleteResponseDTO != null) {
					feedback = deleteResponseDTO.getData();

					SystemResponseDTO<List<SchoolStaffDTO>> getResponseDTO = client.target(API_LINK)
							.path("schoolstaffs").request(MediaType.APPLICATION_JSON).headers(headers)
							.get(new GenericType<SystemResponseDTO<List<SchoolStaffDTO>>>() {
							});

					list = getResponseDTO.getData();
					System.out.println("GET DTO " + getResponseDTO);
				}

				client.close();
				return new RequestResult(feedback, list, null);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.GET_SCHOOL_STAFF)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<SchoolStaffDTO> list = new ArrayList<SchoolStaffDTO>();

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<List<SchoolStaffDTO>> responseDto = client.target(API_LINK).path("schoolstaffs")
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<List<SchoolStaffDTO>>>() {
						});

				list = responseDto.getData();

				System.out.println("RESPONSE " + responseDto);
				System.out.println("RES DATA " + responseDto.getData());
				feedback.setResponse(responseDto.isStatus());
				feedback.setMessage(responseDto.getMessage());

				client.close();
				return new RequestResult(feedback, list, null);
			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.GET_STAFFS_IN_SCHOOL)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<SchoolStaffDTO> list = new ArrayList<SchoolStaffDTO>();
				String id = (String) action.getRequestBody().get(RequestConstant.GET_STAFFS_IN_SCHOOL);

				Client client = ClientBuilder.newClient();
				// schools/ff80818177f1b9680177f1c3329e000b/schoolstaffs
				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<List<SchoolStaffDTO>> responseDto = client.target(API_LINK).path("schools").path(id)
						.path("schoolstaffs").request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<List<SchoolStaffDTO>>>() {
						});

				list = responseDto.getData();

				System.out.println("RESPONSE " + responseDto);
				System.out.println("RES DATA " + responseDto.getData());
				feedback.setResponse(responseDto.isStatus());
				feedback.setMessage(responseDto.getMessage());

				client.close();
				return new RequestResult(feedback, list, null);
			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.GET_SCHOOL_STAFFS_IN_DISTRICT_SCHOOL)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<SchoolStaffDTO> list = new ArrayList<SchoolStaffDTO>();
				String districtId = (String) action.getRequestBody().get(FilterStaffsPane.DISTRICT_ID);
				String schoolId = (String) action.getRequestBody().get(FilterStaffsPane.SCHOOL_ID);

				Client client = ClientBuilder.newClient();
				// districts/{districtId}/schools/ff80818177f1b9680177f1c3329e000b/schoolstaffs
				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<List<SchoolStaffDTO>> responseDto = client.target(API_LINK).path("districts")
						.path(districtId).path("schools").path(schoolId).path("schoolstaffs")
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<List<SchoolStaffDTO>>>() {
						});

				list = responseDto.getData();

				System.out.println("RESPONSE " + responseDto);
				System.out.println("RES DATA " + responseDto.getData());
				feedback.setResponse(responseDto.isStatus());
				feedback.setMessage(responseDto.getMessage());

				client.close();
				return new RequestResult(feedback, list, null);
			}

			/////////// staff enrollment
			else if (action.getRequest().equalsIgnoreCase(RequestConstant.SAVE_STAFF_ENROLLMENT)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				StaffEnrollmentDto dto = (StaffEnrollmentDto) action.getRequestBody()
						.get(RequestConstant.SAVE_STAFF_ENROLLMENT);

				List<StaffEnrollmentDto> list = new ArrayList<StaffEnrollmentDto>();

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<SystemFeedbackDTO> postResponseDTO = client.target(API_LINK).path("staffenrollments")
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (postResponseDTO != null) {
					feedback = postResponseDTO.getData();
				}

				SystemResponseDTO<List<StaffEnrollmentDto>> getResponseDTO = client.target(API_LINK)
						.path("staffenrollments").request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<List<StaffEnrollmentDto>>>() {
						});

				list = getResponseDTO.getData();
				System.out.println("GET DTO " + getResponseDTO);

				client.close();
				return new RequestResult(feedback, list, null);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.UPDATE_STAFF_ENROLLMENT)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				StaffEnrollmentDto dto = (StaffEnrollmentDto) action.getRequestBody()
						.get(RequestConstant.UPDATE_STAFF_ENROLLMENT);

				List<StaffEnrollmentDto> list = new ArrayList<StaffEnrollmentDto>();

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<SystemFeedbackDTO> updateResponseDTO = client.target(API_LINK)
						.path("staffenrollments").path(dto.getId()).request(MediaType.APPLICATION_JSON).headers(headers)
						.put(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (updateResponseDTO != null) {
					feedback = updateResponseDTO.getData();

					SystemResponseDTO<List<StaffEnrollmentDto>> getResponseDTO = client.target(API_LINK)
							.path("staffenrollments").request(MediaType.APPLICATION_JSON).headers(headers)
							.get(new GenericType<SystemResponseDTO<List<StaffEnrollmentDto>>>() {
							});

					list = getResponseDTO.getData();
					System.out.println("GET DTO " + getResponseDTO);
				}

				client.close();
				return new RequestResult(feedback, list, null);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.DELETE_STAFF_ENROLLMENT)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				String id = (String) action.getRequestBody().get(RequestConstant.DELETE_STAFF_ENROLLMENT);

				List<StaffEnrollmentDto> list = new ArrayList<StaffEnrollmentDto>();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				Client client = ClientBuilder.newClient();

				SystemResponseDTO<SystemFeedbackDTO> deleteResponseDTO = client.target(API_LINK)
						.path("staffenrollments").path(id).request(MediaType.APPLICATION_JSON).headers(headers)
						.delete(new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
						});

				if (deleteResponseDTO != null) {
					feedback = deleteResponseDTO.getData();

					SystemResponseDTO<List<StaffEnrollmentDto>> getResponseDTO = client.target(API_LINK)
							.path("staffenrollments").request(MediaType.APPLICATION_JSON).headers(headers)
							.get(new GenericType<SystemResponseDTO<List<StaffEnrollmentDto>>>() {
							});

					list = getResponseDTO.getData();
					System.out.println("GET DTO " + getResponseDTO);
				}

				client.close();
				return new RequestResult(feedback, list, null);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.GET_STAFF_ENROLLMENT)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<StaffEnrollmentDto> list = new ArrayList<StaffEnrollmentDto>();

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<List<StaffEnrollmentDto>> responseDto = client.target(API_LINK)
						.path("staffenrollments").request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<List<StaffEnrollmentDto>>>() {
						});

				list = responseDto.getData();

				System.out.println("RESPONSE " + responseDto);
				System.out.println("RES DATA " + responseDto.getData());
				feedback.setResponse(responseDto.isStatus());
				feedback.setMessage(responseDto.getMessage());

				client.close();
				return new RequestResult(feedback, list, null);
			} else if (action.getRequest().equalsIgnoreCase(
					RequestConstant.GET_SCHOOL_STAFF_ENROLLMENTS_IN_ACADEMIC_YEAR_ACADEMIC_TERM_DISTRICT_SCHOOL)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<StaffEnrollmentDto> list = new ArrayList<StaffEnrollmentDto>();
				String academicYearId = (String) action.getRequestBody()
						.get(FilterStaffHeadCountWindow.ACADEMIC_YEAR_ID);
				String academicTermId = (String) action.getRequestBody()
						.get(FilterStaffHeadCountWindow.ACADEMIC_TERM_ID);
				String districtId = (String) action.getRequestBody().get(FilterStaffHeadCountWindow.DISTRICT_ID);
				String schoolId = (String) action.getRequestBody().get(FilterStaffHeadCountWindow.SCHOOL_ID);

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<List<StaffEnrollmentDto>> responseDto = client.target(API_LINK).path("academicyears")
						.path(academicYearId).path("academicterms").path(academicTermId).path("districts")
						.path(districtId).path("schools").path(schoolId).path("staffenrollments")
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<List<StaffEnrollmentDto>>>() {
						});

				list = responseDto.getData();

				System.out.println("RESPONSE " + responseDto);
				System.out.println("RES DATA " + responseDto.getData());
				feedback.setResponse(responseDto.isStatus());
				feedback.setMessage(responseDto.getMessage());

				client.close();
				return new RequestResult(feedback, list, null);
			}

			// learner emrollment
			///////////
			else if (action.getRequest().equalsIgnoreCase(RequestConstant.SAVE_LEARNER_ENROLLMENT)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				LearnerEnrollmentDTO dto = (LearnerEnrollmentDTO) action.getRequestBody()
						.get(RequestConstant.SAVE_LEARNER_ENROLLMENT);

				List<LearnerEnrollmentDTO> list = new ArrayList<LearnerEnrollmentDTO>();

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<SystemFeedbackDTO> postResponseDTO = client.target(API_LINK)
						.path("learnerenrollments").request(MediaType.APPLICATION_JSON).headers(headers)
						.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (postResponseDTO != null) {
					feedback = postResponseDTO.getData();
				}

				SystemResponseDTO<List<LearnerEnrollmentDTO>> getResponseDTO = client.target(API_LINK)
						.path("learnerenrollments").request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<List<LearnerEnrollmentDTO>>>() {
						});

				list = getResponseDTO.getData();
				System.out.println("GET DTO " + getResponseDTO);

				client.close();
				return new RequestResult(feedback, list, null);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.UPDATE_LEARNER_ENROLLMENT)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				LearnerEnrollmentDTO dto = (LearnerEnrollmentDTO) action.getRequestBody()
						.get(RequestConstant.UPDATE_LEARNER_ENROLLMENT);

				List<LearnerEnrollmentDTO> list = new ArrayList<LearnerEnrollmentDTO>();

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<SystemFeedbackDTO> updateResponseDTO = client.target(API_LINK)
						.path("learnerenrollments").path(dto.getId()).request(MediaType.APPLICATION_JSON)
						.headers(headers).put(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (updateResponseDTO != null) {
					feedback = updateResponseDTO.getData();

					SystemResponseDTO<List<LearnerEnrollmentDTO>> getResponseDTO = client.target(API_LINK)
							.path("learnerenrollments").request(MediaType.APPLICATION_JSON).headers(headers)
							.get(new GenericType<SystemResponseDTO<List<LearnerEnrollmentDTO>>>() {
							});

					list = getResponseDTO.getData();
					System.out.println("GET DTO " + getResponseDTO);
				}

				client.close();
				return new RequestResult(feedback, list, null);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.DELETE_LEARNER_ENROLLMENT)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				String id = (String) action.getRequestBody().get(RequestConstant.DELETE_LEARNER_ENROLLMENT);

				List<LearnerEnrollmentDTO> list = new ArrayList<LearnerEnrollmentDTO>();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				Client client = ClientBuilder.newClient();

				SystemResponseDTO<SystemFeedbackDTO> deleteResponseDTO = client.target(API_LINK)
						.path("learnerenrollments").path(id).request(MediaType.APPLICATION_JSON).headers(headers)
						.delete(new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
						});

				if (deleteResponseDTO != null) {
					feedback = deleteResponseDTO.getData();

					SystemResponseDTO<List<LearnerEnrollmentDTO>> getResponseDTO = client.target(API_LINK)
							.path("learnerenrollments").request(MediaType.APPLICATION_JSON).headers(headers)
							.get(new GenericType<SystemResponseDTO<List<LearnerEnrollmentDTO>>>() {
							});

					list = getResponseDTO.getData();
					System.out.println("GET DTO " + getResponseDTO);
				}

				client.close();
				return new RequestResult(feedback, list, null);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.GET_LEARNER_ENROLLMENT)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<LearnerEnrollmentDTO> list = new ArrayList<LearnerEnrollmentDTO>();

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<List<LearnerEnrollmentDTO>> responseDto = client.target(API_LINK)
						.path("learnerenrollments").request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<List<LearnerEnrollmentDTO>>>() {
						});

				list = responseDto.getData();

				System.out.println("RESPONSE " + responseDto);
				System.out.println("RES DATA " + responseDto.getData());
				feedback.setResponse(responseDto.isStatus());
				feedback.setMessage(responseDto.getMessage());

				client.close();
				return new RequestResult(feedback, list, null);
			}
			/// academicyears/{academicYearId}/academicterms/{academicTermId}/districts/{districtId}/schools/{schoolId}/learnerenrollments
			else if (action.getRequest().equalsIgnoreCase(
					RequestConstant.GET_LEARNER_ENROLLMENTS_IN_ACADEMIC_YEAR_ACADEMIC_TERM_DISTRICT_SCHOOL)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<LearnerEnrollmentDTO> list = new ArrayList<LearnerEnrollmentDTO>();
				String academicYearId = (String) action.getRequestBody()
						.get(FilterLearnerHeadCountWindow.ACADEMIC_YEAR_ID);
				String academicTermId = (String) action.getRequestBody()
						.get(FilterLearnerHeadCountWindow.ACADEMIC_TERM_ID);
				String districtId = (String) action.getRequestBody().get(FilterLearnerHeadCountWindow.DISTRICT_ID);
				String schooolId = (String) action.getRequestBody().get(FilterLearnerHeadCountWindow.SCHOOL_ID);

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<List<LearnerEnrollmentDTO>> responseDto = client.target(API_LINK)
						.path("academicyears").path(academicYearId).path("academicterms").path(academicTermId)
						.path("districts").path(districtId).path("schools").path(schooolId).path("learnerenrollments")
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<List<LearnerEnrollmentDTO>>>() {
						});

				list = responseDto.getData();

				System.out.println("RESPONSE " + responseDto);
				System.out.println("RES DATA " + responseDto.getData());
				feedback.setResponse(responseDto.isStatus());
				feedback.setMessage(responseDto.getMessage());

				client.close();
				return new RequestResult(feedback, list, null);
			}
			/////////////// CLOCK IN
			else if (action.getRequest().equalsIgnoreCase(RequestConstant.SAVE_CLOCK_IN)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				ClockInDTO dto = (ClockInDTO) action.getRequestBody().get(RequestConstant.SAVE_CLOCK_IN);

				List<ClockInDTO> list = new ArrayList<ClockInDTO>();

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<SystemFeedbackDTO> postResponseDTO = client.target(API_LINK).path("clockins")
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (postResponseDTO != null) {
					feedback = postResponseDTO.getData();
				}

				SystemResponseDTO<List<ClockInDTO>> getResponseDTO = client.target(API_LINK).path("clockins")
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<List<ClockInDTO>>>() {
						});

				list = getResponseDTO.getData();
				System.out.println("GET DTO " + getResponseDTO);

				client.close();
				return new RequestResult(feedback, list, null);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.UPDATE_CLOCK_IN)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				ClockInDTO dto = (ClockInDTO) action.getRequestBody().get(RequestConstant.UPDATE_CLOCK_IN);

				List<ClockInDTO> list = new ArrayList<ClockInDTO>();

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<SystemFeedbackDTO> updateResponseDTO = client.target(API_LINK).path("clockins")
						.path(dto.getId()).request(MediaType.APPLICATION_JSON).headers(headers)
						.put(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (updateResponseDTO != null) {
					feedback = updateResponseDTO.getData();

					SystemResponseDTO<List<ClockInDTO>> getResponseDTO = client.target(API_LINK).path("clockins")
							.request(MediaType.APPLICATION_JSON).headers(headers)
							.get(new GenericType<SystemResponseDTO<List<ClockInDTO>>>() {
							});

					list = getResponseDTO.getData();
					System.out.println("GET DTO " + getResponseDTO);
				}

				client.close();
				return new RequestResult(feedback, list, null);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.DELETE_CLOCK_IN)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				String id = (String) action.getRequestBody().get(RequestConstant.DELETE_CLOCK_IN);

				List<ClockInDTO> list = new ArrayList<ClockInDTO>();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				Client client = ClientBuilder.newClient();

				SystemResponseDTO<SystemFeedbackDTO> deleteResponseDTO = client.target(API_LINK).path("clockins")
						.path(id).request(MediaType.APPLICATION_JSON).headers(headers)
						.delete(new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
						});

				if (deleteResponseDTO != null) {
					feedback = deleteResponseDTO.getData();

					SystemResponseDTO<List<ClockInDTO>> getResponseDTO = client.target(API_LINK).path("clockins")
							.request(MediaType.APPLICATION_JSON).headers(headers)
							.get(new GenericType<SystemResponseDTO<List<ClockInDTO>>>() {
							});

					list = getResponseDTO.getData();
					System.out.println("GET DTO " + getResponseDTO);
				}

				client.close();
				return new RequestResult(feedback, list, null);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.GET_CLOCK_IN)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<ClockInDTO> list = new ArrayList<ClockInDTO>();

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<List<ClockInDTO>> responseDto = client.target(API_LINK).path("clockins")
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<List<ClockInDTO>>>() {
						});

				list = responseDto.getData();

				System.out.println("RESPONSE " + responseDto);
				System.out.println("RES DATA " + responseDto.getData());
				feedback.setResponse(responseDto.isStatus());
				feedback.setMessage(responseDto.getMessage());

				client.close();
				return new RequestResult(feedback, list, null);
			} else if (action.getRequest()
					.equalsIgnoreCase(RequestConstant.GET_CLOCKINS_IN_ACADEMIC_YEAR_ACADEMIC_TERM_DISTRICT_SCHOOL)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<ClockInDTO> list = new ArrayList<ClockInDTO>();
				String academicYearId = (String) action.getRequestBody().get(FilterClockInWindow.ACADEMIC_YEAR_ID);
				String academicTermId = (String) action.getRequestBody().get(FilterClockInWindow.ACADEMIC_TERM_ID);
				String districtId = (String) action.getRequestBody().get(FilterClockInWindow.DISTRICT_ID);
				String schoolId = (String) action.getRequestBody().get(FilterClockInWindow.SCHOOL_ID);
				String date = (String) action.getRequestBody().get(FilterClockInWindow.CLOCKIN_DATE);
				/// academicyears/{academicYearId}/academicterms/{academicTermId}/districts/{districtId}/schools/{schoolId}/clockins
				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<List<ClockInDTO>> responseDto = client.target(API_LINK).path("academicyears")
						.path(academicYearId).path("academicterms").path(academicTermId).path("districts")
						.path(districtId).path("schools").path(schoolId).path("clockins").queryParam("date", date)
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<List<ClockInDTO>>>() {
						});

				list = responseDto.getData();

				System.out.println("RESPONSE " + responseDto);
				System.out.println("RES DATA " + responseDto.getData());
				feedback.setResponse(responseDto.isStatus());
				feedback.setMessage(responseDto.getMessage());

				client.close();
				return new RequestResult(feedback, list, null);
			}
			///////////// CLOCKOUT
			else if (action.getRequest().equalsIgnoreCase(RequestConstant.SAVE_CLOCK_OUT)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				ClockOutDTO dto = (ClockOutDTO) action.getRequestBody().get(RequestConstant.SAVE_CLOCK_OUT);

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<SystemFeedbackDTO> postResponseDTO = client.target(API_LINK).path("clockouts")
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (postResponseDTO != null) {
					feedback = postResponseDTO.getData();
				}

				List<ClockInDTO> list = new ArrayList<ClockInDTO>();
				// SystemResponseDTO<List<ClockInDTO>> responseDto =
				// client.target(API_LINK).path("clockins")
				// .request(MediaType.APPLICATION_JSON)
				// .headers(headers)
				// .get(new GenericType<SystemResponseDTO<List<ClockInDTO>>>() {
				// });

				// list = responseDto.getData();

				System.out.println("RESPONSE " + feedback);
				System.out.println("RES DATA " + feedback.getMessage());
				// feedback.setResponse(feedback.isResponse());
				// feedback.setMessage(responseDto.getMessage());

				client.close();
				return new RequestResult(feedback, list, null);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.UPDATE_CLOCK_OUT)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				ClockOutDTO dto = (ClockOutDTO) action.getRequestBody().get(RequestConstant.UPDATE_CLOCK_OUT);

				List<ClockOutDTO> list = new ArrayList<ClockOutDTO>();

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<SystemFeedbackDTO> updateResponseDTO = client.target(API_LINK).path("clockouts")
						.path(dto.getId()).request(MediaType.APPLICATION_JSON).headers(headers)
						.put(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (updateResponseDTO != null) {
					feedback = updateResponseDTO.getData();

					SystemResponseDTO<List<ClockOutDTO>> getResponseDTO = client.target(API_LINK).path("clockouts")
							.request(MediaType.APPLICATION_JSON).headers(headers)
							.get(new GenericType<SystemResponseDTO<List<ClockOutDTO>>>() {
							});

					list = getResponseDTO.getData();
					System.out.println("GET DTO " + getResponseDTO);
				}

				client.close();
				return new RequestResult(feedback, list, null);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.DELETE_CLOCK_OUT)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				String id = (String) action.getRequestBody().get(RequestConstant.DELETE_CLOCK_OUT);

				List<ClockOutDTO> list = new ArrayList<ClockOutDTO>();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				Client client = ClientBuilder.newClient();

				SystemResponseDTO<SystemFeedbackDTO> deleteResponseDTO = client.target(API_LINK).path("clockouts")
						.path(id).request(MediaType.APPLICATION_JSON).headers(headers)
						.delete(new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
						});

				if (deleteResponseDTO != null) {
					feedback = deleteResponseDTO.getData();

					SystemResponseDTO<List<ClockOutDTO>> getResponseDTO = client.target(API_LINK).path("clockouts")
							.request(MediaType.APPLICATION_JSON).headers(headers)
							.get(new GenericType<SystemResponseDTO<List<ClockOutDTO>>>() {
							});

					list = getResponseDTO.getData();
					System.out.println("GET DTO " + getResponseDTO);
				}

				client.close();
				return new RequestResult(feedback, list, null);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.GET_CLOCK_OUT)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<ClockOutDTO> list = new ArrayList<ClockOutDTO>();

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<List<ClockOutDTO>> responseDto = client.target(API_LINK).path("clockouts")
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<List<ClockOutDTO>>>() {
						});

				list = responseDto.getData();

				System.out.println("RESPONSE " + responseDto);
				System.out.println("RES DATA " + responseDto.getData());
				feedback.setResponse(responseDto.isStatus());
				feedback.setMessage(responseDto.getMessage());

				client.close();
				return new RequestResult(feedback, list, null);
			} else if (action.getRequest()
					.equalsIgnoreCase(RequestConstant.GET_CLOCK_OUTS_IN_ACADEMIC_YEAR_ACADEMIC_TERM_DISTRICT_SCHOOL)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<ClockOutDTO> list = new ArrayList<ClockOutDTO>();
				String academicYearId = (String) action.getRequestBody().get(FilterClockOutWindow.ACADEMIC_YEAR_ID);
				String academicTermId = (String) action.getRequestBody().get(FilterClockOutWindow.ACADEMIC_TERM_ID);
				String districtId = (String) action.getRequestBody().get(FilterClockOutWindow.DISTRICT_ID);
				String schoolId = (String) action.getRequestBody().get(FilterClockOutWindow.SCHOOL_ID);
				String date = (String) action.getRequestBody().get(FilterClockOutWindow.CLOCK_OUT_DATE);
				/// academicyears/{academicYearId}/academicterms/{academicTermId}/districts/{districtId}/schools/{schoolId}/clockins
				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<List<ClockOutDTO>> responseDto = client.target(API_LINK).path("academicyears")
						.path(academicYearId).path("academicterms").path(academicTermId).path("districts")
						.path(districtId).path("schools").path(schoolId).path("clockouts").queryParam("date", date)
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<List<ClockOutDTO>>>() {
						});

				list = responseDto.getData();

				System.out.println("RESPONSE " + responseDto);
				System.out.println("RES DATA " + responseDto.getData());
				feedback.setResponse(responseDto.isStatus());
				feedback.setMessage(responseDto.getMessage());

				client.close();
				return new RequestResult(feedback, list, null);
			}

			/////////// learner
			/////////// Alttendance//////////////////////////////////////////////////////////////////

			else if (action.getRequest().equalsIgnoreCase(RequestConstant.SAVE_LEARNER_ATTENDANCE)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				LearnerAttendanceDTO dto = (LearnerAttendanceDTO) action.getRequestBody()
						.get(RequestConstant.SAVE_LEARNER_ATTENDANCE);

				List<LearnerAttendanceDTO> list = new ArrayList<LearnerAttendanceDTO>();

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<SystemFeedbackDTO> postResponseDTO = client.target(API_LINK)
						.path("learnerattendances").request(MediaType.APPLICATION_JSON).headers(headers)
						.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (postResponseDTO != null) {
					feedback = postResponseDTO.getData();
				}

				SystemResponseDTO<List<LearnerAttendanceDTO>> getResponseDTO = client.target(API_LINK)
						.path("learnerattendances").request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<List<LearnerAttendanceDTO>>>() {
						});

				list = getResponseDTO.getData();
				System.out.println("GET DTO " + getResponseDTO);

				client.close();
				return new RequestResult(feedback, list, null);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.UPDATE_LEARNER_ATTENDANCE)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				LearnerAttendanceDTO dto = (LearnerAttendanceDTO) action.getRequestBody()
						.get(RequestConstant.UPDATE_LEARNER_ATTENDANCE);

				List<LearnerAttendanceDTO> list = new ArrayList<LearnerAttendanceDTO>();

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<SystemFeedbackDTO> updateResponseDTO = client.target(API_LINK)
						.path("learnerattendances").path(dto.getId()).request(MediaType.APPLICATION_JSON)
						.headers(headers).put(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (updateResponseDTO != null) {
					feedback = updateResponseDTO.getData();

					SystemResponseDTO<List<LearnerAttendanceDTO>> getResponseDTO = client.target(API_LINK)
							.path("learnerattendances").request(MediaType.APPLICATION_JSON).headers(headers)
							.get(new GenericType<SystemResponseDTO<List<LearnerAttendanceDTO>>>() {
							});

					list = getResponseDTO.getData();
					System.out.println("GET DTO " + getResponseDTO);
				}

				client.close();
				return new RequestResult(feedback, list, null);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.DELETE_LEARNER_ATTENDANCE)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				String id = (String) action.getRequestBody().get(RequestConstant.DELETE_LEARNER_ATTENDANCE);

				List<LearnerAttendanceDTO> list = new ArrayList<LearnerAttendanceDTO>();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				Client client = ClientBuilder.newClient();

				SystemResponseDTO<SystemFeedbackDTO> deleteResponseDTO = client.target(API_LINK)
						.path("learnerattendances").path(id).request(MediaType.APPLICATION_JSON).headers(headers)
						.delete(new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
						});

				if (deleteResponseDTO != null) {
					feedback = deleteResponseDTO.getData();

					SystemResponseDTO<List<LearnerAttendanceDTO>> getResponseDTO = client.target(API_LINK)
							.path("learnerattendances").request(MediaType.APPLICATION_JSON).headers(headers)
							.get(new GenericType<SystemResponseDTO<List<LearnerAttendanceDTO>>>() {
							});

					list = getResponseDTO.getData();
					System.out.println("GET DTO " + getResponseDTO);
				}

				client.close();
				return new RequestResult(feedback, list, null);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.GET_LEARNER_ATTENDANCE)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<LearnerAttendanceDTO> list = new ArrayList<LearnerAttendanceDTO>();

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<List<LearnerAttendanceDTO>> responseDto = client.target(API_LINK)
						.path("learnerattendances").request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<List<LearnerAttendanceDTO>>>() {
						});

				list = responseDto.getData();

				System.out.println("RESPONSE " + responseDto);
				System.out.println("RES DATA " + responseDto.getData());
				feedback.setResponse(responseDto.isStatus());
				feedback.setMessage(responseDto.getMessage());

				client.close();
				return new RequestResult(feedback, list, null);
			} else if (action.getRequest().equalsIgnoreCase(
					RequestConstant.GET_LEARNER_ATTENDANCE_IN_ACADEMIC_YEAR_ACADEMIC_TERM_DISTRICT_SCHOOL)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<LearnerAttendanceDTO> list = new ArrayList<LearnerAttendanceDTO>();
				String academicYearId = (String) action.getRequestBody()
						.get(FilterLearnerAttendanceWindow.ACADEMIC_YEAR_ID);
				String academicTermId = (String) action.getRequestBody()
						.get(FilterLearnerAttendanceWindow.ACADEMIC_TERM_ID);
				String districtId = (String) action.getRequestBody().get(FilterLearnerAttendanceWindow.DISTRICT_ID);
				String schoolId = (String) action.getRequestBody().get(FilterLearnerAttendanceWindow.SCHOOL_ID);
				String date = (String) action.getRequestBody().get(FilterLearnerAttendanceWindow.ATTENDANCE_DATE);
				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<List<LearnerAttendanceDTO>> responseDto = client.target(API_LINK)
						.path("academicyears").path(academicYearId).path("academicterms").path(academicTermId)
						.path("districts").path(districtId).path("schools").path(schoolId).path("learnerattendances")
						.queryParam("date", date).request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<List<LearnerAttendanceDTO>>>() {
						});

				list = responseDto.getData();

				System.out.println("RESPONSE " + responseDto);
				System.out.println("RES DATA " + responseDto.getData());
				feedback.setResponse(responseDto.isStatus());
				feedback.setMessage(responseDto.getMessage());

				client.close();
				return new RequestResult(feedback, list, null);
			}

			/////////////////// TIME TABLE
			else if (action.getRequest().equalsIgnoreCase(RequestConstant.SAVE_TIME_TABLE)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				TimeTableDTO dto = (TimeTableDTO) action.getRequestBody().get(RequestConstant.SAVE_TIME_TABLE);

				List<TimeTableDTO> list = new ArrayList<TimeTableDTO>();

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<SystemFeedbackDTO> postResponseDTO = client.target(API_LINK).path("timetables")
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (postResponseDTO != null) {
					feedback = postResponseDTO.getData();
				}

				SystemResponseDTO<List<TimeTableDTO>> getResponseDTO = client.target(API_LINK).path("timetables")
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<List<TimeTableDTO>>>() {
						});

				list = getResponseDTO.getData();
				System.out.println("GET DTO " + getResponseDTO);

				client.close();
				return new RequestResult(feedback, list, null);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.GET_TIME_TABLES)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<TimeTableDTO> list = new ArrayList<TimeTableDTO>();

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<List<TimeTableDTO>> responseDto = client.target(API_LINK).path("timetables")
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<List<TimeTableDTO>>>() {
						});

				list = responseDto.getData();

				System.out.println("RESPONSE " + responseDto);
				System.out.println("RES DATA " + responseDto.getData());
				feedback.setResponse(responseDto.isStatus());
				feedback.setMessage(responseDto.getMessage());

				client.close();
				return new RequestResult(feedback, list, null);
				
			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.MIGRATE_DATA)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);

				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				DataMigrationUtility.getInstance().migrateData(headers);

				feedback.setResponse(true);
				feedback.setMessage("Data Migration successful");

				return new RequestResult(feedback);
			}

		} catch (ForbiddenException exception) {
			exception.printStackTrace();
			SystemErrorDTO error = new SystemErrorDTO();
			error.setErrorCode(exception.getResponse().getStatusInfo().getStatusCode());
			error.setMessage("Session timed out. Please login in again");
			// error.setStatus("" + exception.getResponse().getStatus());

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
