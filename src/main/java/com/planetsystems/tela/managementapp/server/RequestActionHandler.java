package com.planetsystems.tela.managementapp.server;

import java.util.ArrayList;
import java.util.Date;
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
import com.planetsystems.tela.dto.StaffDailyAttendanceDTO;
import com.planetsystems.tela.dto.StaffDailyAttendanceSupervisionDTO;
import com.planetsystems.tela.dto.StaffDailyAttendanceTaskDTO;
import com.planetsystems.tela.dto.StaffDailyAttendanceTaskSupervisionDTO;
import com.planetsystems.tela.dto.StaffEnrollmentDto;
import com.planetsystems.tela.dto.SubjectCategoryDTO;
import com.planetsystems.tela.dto.SubjectDTO;
import com.planetsystems.tela.dto.SystemErrorDTO;
import com.planetsystems.tela.dto.SystemFeedbackDTO;
import com.planetsystems.tela.dto.SystemResponseDTO;
import com.planetsystems.tela.dto.SystemUserDTO;
import com.planetsystems.tela.dto.TimeAttendanceSupervisionDTO;
import com.planetsystems.tela.dto.TimeTableDTO;
import com.planetsystems.tela.dto.TimeTableLessonDTO;
import com.planetsystems.tela.dto.TokenFeedbackDTO;
import com.planetsystems.tela.managementapp.client.presenter.learnerattendance.FilterLearnerAttendanceWindow;
import com.planetsystems.tela.managementapp.client.presenter.learnerenrollment.FilterLearnerHeadCountWindow;
import com.planetsystems.tela.managementapp.client.presenter.schoolcategory.FilterSchoolClassPane;
import com.planetsystems.tela.managementapp.client.presenter.schoolcategory.FilterSchoolsPane;
import com.planetsystems.tela.managementapp.client.presenter.staffattendance.FilterClockInWindow;
import com.planetsystems.tela.managementapp.client.presenter.staffattendance.FilterClockOutWindow;
import com.planetsystems.tela.managementapp.client.presenter.staffdailyattendancesupervision.StaffDailyAttendanceSupervisionListGrid;
import com.planetsystems.tela.managementapp.client.presenter.staffdailytask.CreateStaffDailyTaskPane;
import com.planetsystems.tela.managementapp.client.presenter.staffdailytask.StaffDailyAttendancePane;
import com.planetsystems.tela.managementapp.client.presenter.staffenrollment.FilterStaffHeadCountWindow;
import com.planetsystems.tela.managementapp.client.presenter.staffenrollment.FilterStaffsPane;
import com.planetsystems.tela.managementapp.shared.RequestAction;
import com.planetsystems.tela.managementapp.shared.RequestConstant;
import com.planetsystems.tela.managementapp.shared.RequestDelimeters;
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
					System.out.println("LOGIN RESPONSE "+loginResponseDTO.getData());
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

				String id = (String) action.getRequestBody().get(RequestDelimeters.ACADEMIC_YEAR_ID);
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
				String id = (String) action.getRequestBody().get(RequestDelimeters.ACADEMIC_TERM_ID);
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
				String id = (String) action.getRequestBody().get(RequestDelimeters.ACADEMIC_YEAR_ID);
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
				String id = (String) action.getRequestBody().get(RequestDelimeters.REGION_ID);
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
				String id = (String) action.getRequestBody().get(RequestDelimeters.DISTRICT_ID);

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
				String id = (String) action.getRequestBody().get(RequestDelimeters.REGION_ID);

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
				String id = (String) action.getRequestBody().get(RequestDelimeters.SCHOOL_CATEGORY_ID);
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
				String id = (String) action.getRequestBody().get(RequestDelimeters.SCHOOL_ID);

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
				String id = (String) action.getRequestBody().get(RequestDelimeters.DISTRICT_ID);
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
				String districtId = (String) action.getRequestBody().get(RequestDelimeters.DISTRICT_ID);
				String schoolCategoryId = (String) action.getRequestBody().get(RequestDelimeters.SCHOOL_CATEGORY_ID);
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
				String id = (String) action.getRequestBody().get(RequestDelimeters.SCHOOL_CLASS_ID);

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
				String id = (String) action.getRequestBody().get(RequestDelimeters.SCHOOL_ID);

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
				String schoolId = (String) action.getRequestBody().get(RequestDelimeters.SCHOOL_ID);
				String academicTermId = (String) action.getRequestBody().get(RequestDelimeters.ACADEMIC_TERM_ID);

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
				String id = (String) action.getRequestBody().get(RequestDelimeters.SUBJECT_CATEGORY_ID);

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
				String id = (String) action.getRequestBody().get(RequestDelimeters.SUBJECT_ID);

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
				String id = (String) action.getRequestBody().get(RequestDelimeters.SUBJECT_CATEGORY_ID);

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
				String id = (String) action.getRequestBody().get(RequestDelimeters.SCHOOL_STAFF_ID);

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
				String id = (String) action.getRequestBody().get(RequestDelimeters.SCHOOL_ID);

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
				String districtId = (String) action.getRequestBody().get(RequestDelimeters.DISTRICT_ID);
				String schoolId = (String) action.getRequestBody().get(RequestDelimeters.SCHOOL_ID);

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
			
			else if (action.getRequest().equalsIgnoreCase(RequestConstant.GET_ABSENT_SCHOOL_STAFF_BY_TERM_SCHOOL_DATE)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<SchoolStaffDTO> list = new ArrayList<SchoolStaffDTO>();
				String academicTermId = (String) action.getRequestBody().get(RequestDelimeters.ACADEMIC_TERM_ID);
				String schoolId = (String) action.getRequestBody().get(RequestDelimeters.SCHOOL_ID);
				String absentDate = (String) action.getRequestBody().get(RequestDelimeters.ABSENT_DATE);

				Client client = ClientBuilder.newClient();
				// /academicterms/{academicTermId}/schools/{schoolId}/absents
				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<List<SchoolStaffDTO>> responseDto = client.target(API_LINK)
						.path("academicterms")
						.path(academicTermId)
						.path("schools")
						.path(schoolId)
						.path("absents")
						.queryParam("absentDate", absentDate)
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
				String id = (String) action.getRequestBody().get(RequestDelimeters.STAFF_ENROLLMENT_ID);

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
						.get(RequestDelimeters.ACADEMIC_YEAR_ID);
				String academicTermId = (String) action.getRequestBody()
						.get(RequestDelimeters.ACADEMIC_TERM_ID);
				String districtId = (String) action.getRequestBody().get(RequestDelimeters.DISTRICT_ID);
				String schoolId = (String) action.getRequestBody().get(RequestDelimeters.SCHOOL_ID);

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
				String id = (String) action.getRequestBody().get(RequestDelimeters.LEARNER_ENROLLMENT_ID);

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
						.get(RequestDelimeters.ACADEMIC_YEAR_ID);
				String academicTermId = (String) action.getRequestBody()
						.get(RequestDelimeters.ACADEMIC_TERM_ID);
				String districtId = (String) action.getRequestBody().get(RequestDelimeters.DISTRICT_ID);
				String schooolId = (String) action.getRequestBody().get(RequestDelimeters.SCHOOL_ID);

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
				String id = (String) action.getRequestBody().get(RequestDelimeters.CLOCK_IN_ID);

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
				String academicYearId = (String) action.getRequestBody().get(RequestDelimeters.ACADEMIC_YEAR_ID);
				String academicTermId = (String) action.getRequestBody().get(RequestDelimeters.ACADEMIC_TERM_ID);
				String districtId = (String) action.getRequestBody().get(RequestDelimeters.DISTRICT_ID);
				String schoolId = (String) action.getRequestBody().get(RequestDelimeters.SCHOOL_ID);
				String date = (String) action.getRequestBody().get(RequestDelimeters.CLOCKIN_DATE);
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
			else if (action.getRequest()
					.equalsIgnoreCase(RequestConstant.GET_CLOCK_IN_By_ACADEMIC_TERM_SCHOOL_DATE)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<ClockInDTO> list = new ArrayList<ClockInDTO>();
				String academicTermId = (String) action.getRequestBody().get(RequestDelimeters.ACADEMIC_TERM_ID);
				String schoolId = (String) action.getRequestBody().get(RequestDelimeters.SCHOOL_ID);
				String date = (String) action.getRequestBody().get(RequestDelimeters.CLOCKIN_DATE);
				/// /academicterms/{academicTermId}/schools/{schoolId}/clockins"
				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<List<ClockInDTO>> responseDto = client.target(API_LINK)
						.path("academicterms")
						.path(academicTermId)
						.path("schools")
						.path(schoolId)
						.path("clockins")
						.queryParam("clockInDate", date)
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
			
				System.out.println("RESPONSE " + feedback);
				System.out.println("RES DATA " + feedback.getMessage());
			
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
				String id = (String) action.getRequestBody().get(RequestDelimeters.CLOCK_OUT_ID);

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
				String academicYearId = (String) action.getRequestBody().get(RequestDelimeters.ACADEMIC_YEAR_ID);
				String academicTermId = (String) action.getRequestBody().get(RequestDelimeters.ACADEMIC_TERM_ID);
				String districtId = (String) action.getRequestBody().get(RequestDelimeters.DISTRICT_ID);
				String schoolId = (String) action.getRequestBody().get(RequestDelimeters.SCHOOL_ID);
				String date = (String) action.getRequestBody().get(RequestDelimeters.CLOCK_OUT_DATE);
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

			else if (action.getRequest()
					.equalsIgnoreCase(RequestConstant.GET_CLOCK_OUT_BY_TERM_SCHOOL_DATE)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<ClockOutDTO> list = new ArrayList<ClockOutDTO>();
				String academicTermId = (String) action.getRequestBody().get(RequestDelimeters.ACADEMIC_TERM_ID);
				String schoolId = (String) action.getRequestBody().get(RequestDelimeters.SCHOOL_ID);
				String date = (String) action.getRequestBody().get(RequestDelimeters.CLOCK_OUT_DATE);
				/// /academicterms/{academicTermId}/schools/{schoolId}/clockouts
				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<List<ClockOutDTO>> responseDto = client.target(API_LINK)
						.path("academicterms")
						.path(academicTermId)
						.path("schools")
						.path(schoolId)
						.path("clockouts")
						.queryParam("clockOutDate", date)
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
				String id = (String) action.getRequestBody().get(RequestDelimeters.LEARNER_ATTENDANCE_ID);

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
						.get(RequestDelimeters.ACADEMIC_YEAR_ID);
				String academicTermId = (String) action.getRequestBody()
						.get(RequestDelimeters.ACADEMIC_TERM_ID);
				String districtId = (String) action.getRequestBody().get(RequestDelimeters.DISTRICT_ID);
				String schoolId = (String) action.getRequestBody().get(RequestDelimeters.SCHOOL_ID);
				String date = (String) action.getRequestBody().get(RequestDelimeters.ATTENDANCE_DATE);
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
					System.out.print("FEEDBACK " + feedback);
				}

				client.close();
				return new RequestResult(feedback);

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
			} ///////////////////////////////////////////////////////////////////

			else if (action.getRequest().equalsIgnoreCase(RequestConstant.GET_TIME_TABLE_LESSONS_BY_TIME_TABLE)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<TimeTableLessonDTO> list = new ArrayList<TimeTableLessonDTO>();
				String timeTableId = (String) action.getRequestBody()
						.get(RequestDelimeters.TIME_TABLE_ID);

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);
///timetables/{id}/timetablelessons

				SystemResponseDTO<List<TimeTableLessonDTO>> responseDto = client.target(API_LINK).path("timetables")
						.path(timeTableId).path("timetablelessons").request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<List<TimeTableLessonDTO>>>() {
						});

				if(responseDto.getData() != null)
				list = responseDto.getData();


				System.out.println("RESPONSE " + responseDto);
				System.out.println("RES DATA " + responseDto.getData());
				feedback.setResponse(responseDto.isStatus());
				feedback.setMessage(responseDto.getMessage());

				client.close();
				return new RequestResult(feedback, list, null);
			}
			else if (action.getRequest().equalsIgnoreCase(RequestConstant.GET_TIME_TABLE_LESSONS_FOR_STAFF_ACADEMIC_YEAR_TERM_DISTRICT_SCHOOL_DATE)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<TimeTableLessonDTO> list = new ArrayList<TimeTableLessonDTO>();

				String academicYearId = (String) action.getRequestBody().get(RequestDelimeters.ACADEMIC_YEAR_ID);
				String academicTermId = (String) action.getRequestBody().get(RequestDelimeters.ACADEMIC_TERM_ID);
				String districtId =(String) action.getRequestBody().get(RequestDelimeters.DISTRICT_ID);
				String schoolId = (String) action.getRequestBody().get(RequestDelimeters.SCHOOL_ID);
				String schoolStaffId = (String) action.getRequestBody().get(RequestDelimeters.SCHOOL_STAFF_ID);
				String attendanceDate = (String) action.getRequestBody().get(RequestDelimeters.ATTENDANCE_DATE);

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);
				/*
				 * http://localhost:8070/academicyears/1/academicterms/2c9180866ff5c74b01700ab38a410111/districts/8a0080826522e0f601652ef26af8001b/schools/8a008082648d961401648dadbf0f0003/schoolstaffs/2c91808377b47c7a0177b9674a350416/days/Tuesday/timetablelessons
				 */

				SystemResponseDTO<List<TimeTableLessonDTO>> responseDto = client.target(API_LINK)
						.path("academicyears")
						.path(academicYearId)
						.path("academicterms")
						.path(academicTermId)
						.path("districts")
						.path(districtId)
						.path("schools")
						.path(schoolId)
						.path("schoolstaffs")
						.path(schoolStaffId)
						.path("timetablelessons")
						.queryParam("lessonDate" , attendanceDate)
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<List<TimeTableLessonDTO>>>() {
						});

				if(responseDto.getData() != null)
				list = responseDto.getData();


				System.out.println("RESPONSE " + responseDto);
				System.out.println("RES DATA " + responseDto.getData());
				feedback.setResponse(responseDto.isStatus());
				feedback.setMessage(responseDto.getMessage());

				client.close();
				return new RequestResult(feedback, list, null);
			}

			///////////////////////////////////

			else if (action.getRequest().equalsIgnoreCase(RequestConstant.SAVE_SYSTEM_USER)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				SystemUserDTO dto = (SystemUserDTO) action.getRequestBody().get(RequestConstant.SAVE_SYSTEM_USER);

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<SystemFeedbackDTO> postResponseDTO = client.target(API_LINK).path("systemusers")
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (postResponseDTO != null) {
					feedback = postResponseDTO.getData();
				}

				client.close();
				return new RequestResult(feedback);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.GET_ALL_SYSTEM_USERS)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<SystemUserDTO> list = new ArrayList<SystemUserDTO>();

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<List<SystemUserDTO>> responseDto = client.target(API_LINK).path("systemusers")
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<List<SystemUserDTO>>>() {
						});

				list = responseDto.getData();

				System.out.println("RESPONSE " + responseDto);
				System.out.println("RES DATA " + responseDto.getData());
				feedback.setResponse(responseDto.isStatus());
				feedback.setMessage(responseDto.getMessage());

				client.close();
				return new RequestResult(feedback, list, null);

			} 
			else if (action.getRequest().equalsIgnoreCase(RequestConstant.SAVE_STAFF_DAILY_ATTENDANCE_TASKS)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				StaffDailyAttendanceDTO dto = (StaffDailyAttendanceDTO) action.getRequestBody().get(RequestConstant.SAVE_STAFF_DAILY_ATTENDANCE_TASKS);

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<SystemFeedbackDTO> postResponseDTO = client.target(API_LINK).path("staffdailyattendances")
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (postResponseDTO != null) {
					feedback = postResponseDTO.getData();
				}

				client.close();
				return new RequestResult(feedback);

			}else if (action.getRequest().equalsIgnoreCase(RequestConstant.GET_STAFF_DAILY_ATTENDANCE_ACADEMIC_YEAR_TERM_DUSTRICT_SCHOOL_DATE)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<StaffDailyAttendanceDTO> list = new ArrayList<StaffDailyAttendanceDTO>();

				String academicYearId = (String) action.getRequestBody().get(RequestDelimeters.ACADEMIC_YEAR_ID);
				String academicTermId = (String) action.getRequestBody().get(RequestDelimeters.ACADEMIC_TERM_ID);
				String districtId =(String) action.getRequestBody().get(RequestDelimeters.DISTRICT_ID);
				String schoolId = (String) action.getRequestBody().get(RequestDelimeters.SCHOOL_ID);
				String attendanceDate = (String) action.getRequestBody().get(RequestDelimeters.ATTENDANCE_DATE);

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				/*
				 * http://localhost:8070/academicyears/1/academicterms/2c9180866ff5c74b01700ab38a410111/districts/8a0080826522e0f601652ef26af8001b
				 * /schools/8a008082648d961401648dadbf0f0003/staffdailyattendances?attendanceDate=17/03/2021
				 */

				SystemResponseDTO<List<StaffDailyAttendanceDTO>> responseDto = client.target(API_LINK)
						.path("academicyears")
						.path(academicYearId)
						.path("academicterms")
						.path(academicTermId)
						.path("districts")
						.path(districtId)
						.path("schools")
						.path(schoolId)
						.path("staffdailyattendances")
						.queryParam("attendanceDate", attendanceDate)
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<List<StaffDailyAttendanceDTO>>>() {
						});

				if(responseDto.getData() != null)
				list = responseDto.getData();


				System.out.println("RESPONSE " + responseDto);
				System.out.println("RES DATA " + responseDto.getData());
				feedback.setResponse(responseDto.isStatus());
				feedback.setMessage(responseDto.getMessage());

				client.close();
				return new RequestResult(feedback, list, null);
			}

			else if (action.getRequest().equalsIgnoreCase(RequestConstant.GET_STAFF_DAILY_ATTENDANCE_TASK_SCHOOL_STAFF_DATE)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<StaffDailyAttendanceTaskDTO> list = new ArrayList<StaffDailyAttendanceTaskDTO>();

				String schoolId = (String) action.getRequestBody().get(RequestDelimeters.SCHOOL_ID);
				String schoolStaffId = (String) action.getRequestBody().get(RequestDelimeters.SCHOOL_STAFF_ID);
				String attendanceDate = (String) action.getRequestBody().get(RequestDelimeters.ATTENDANCE_DATE);

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				/*
				 * /schools/{school}/schoolstaffs/{staff}/staffdailyattendances
				 */

				SystemResponseDTO<List<StaffDailyAttendanceTaskDTO>> responseDto = client.target(API_LINK)
						.path("schools")
						.path(schoolId)
						.path("schoolstaffs")
						.path(schoolStaffId)
						.path("staffdailyattendances")
						.queryParam("attendanceDate", attendanceDate)
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<List<StaffDailyAttendanceTaskDTO>>>() {
						});

				if(responseDto.getData() != null)
				list = responseDto.getData();


				System.out.println("RESPONSE " + responseDto);
				System.out.println("RES DATA " + responseDto.getData());
				feedback.setResponse(responseDto.isStatus());
				feedback.setMessage(responseDto.getMessage());

				client.close();
				return new RequestResult(feedback, list, null);
			}
			
			else if (action.getRequest().equalsIgnoreCase(RequestConstant.GET_STAFF_DAILY_ATTENDANCE_TASKS_FOR_STAFF_DATE_DAILY_ATTENDANCE)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<StaffDailyAttendanceTaskDTO> list = new ArrayList<StaffDailyAttendanceTaskDTO>();

				String staffDailyAttendanceId = (String) action.getRequestBody().get(RequestDelimeters.STAFF_DAILY_ATTENDANCE_LESSON_ID);
				String schoolStaffId = (String) action.getRequestBody().get(RequestDelimeters.SCHOOL_STAFF_ID);
				String attendanceDate = (String) action.getRequestBody().get(RequestDelimeters.ATTENDANCE_DATE);

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				/*
				 * /staffdailyattendances/{staffDailyAttendanceId}/schoolstaffs/{staff}/staffdailyattendancestasks
				 */

				SystemResponseDTO<List<StaffDailyAttendanceTaskDTO>> responseDto = client.target(API_LINK)
						.path("staffdailyattendances")
						.path(staffDailyAttendanceId)
						.path("schoolstaffs")
						.path(schoolStaffId)
						.path("staffdailyattendancestasks")
						.queryParam("attendanceDate", attendanceDate)
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<List<StaffDailyAttendanceTaskDTO>>>() {
						});

				if(responseDto.getData() != null)
				list = responseDto.getData();


				System.out.println("RESPONSE " + responseDto);
				System.out.println("RES DATA " + responseDto.getData());
				feedback.setResponse(responseDto.isStatus());
				feedback.setMessage(responseDto.getMessage());

				client.close();
				return new RequestResult(feedback, list, null);
			}

			/////////////
			else if (action.getRequest().equalsIgnoreCase(RequestConstant.SAVE_STAFF_DAILY_TASK_SUPERVISIONS)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				StaffDailyAttendanceSupervisionDTO dto = (StaffDailyAttendanceSupervisionDTO) action.getRequestBody().get(RequestConstant.SAVE_STAFF_DAILY_TASK_SUPERVISIONS);

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<SystemFeedbackDTO> postResponseDTO = client.target(API_LINK).path("staffDailyAttendanceSupervisions")
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (postResponseDTO != null) {
					feedback = postResponseDTO.getData();
				}

				client.close();
				return new RequestResult(feedback);

			}
			else if (action.getRequest().equalsIgnoreCase(RequestConstant.GET_STAFF_DAILY_SUPERVISIONS_IN_SCHOOL_DATE)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<StaffDailyAttendanceSupervisionDTO> list = new ArrayList<StaffDailyAttendanceSupervisionDTO>();

		
				String schoolId = (String) action.getRequestBody().get(RequestDelimeters.SCHOOL_ID);
				String supervisionDate = (String) action.getRequestBody().get(RequestDelimeters.SUPERVISION_DATE);

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);
//http://localhost:8070/staffDailyAttendanceSupervisions/schools/8a008082648d961401648dadbf0f0003?supervisionDate=18/03/2021
				SystemResponseDTO<List<StaffDailyAttendanceSupervisionDTO>> responseDto = client.target(API_LINK)
						.path("staffDailyAttendanceSupervisions")
						.path("schools")
						.path(schoolId)
						.queryParam("supervisionDate", supervisionDate)
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<List<StaffDailyAttendanceSupervisionDTO>>>() {
						});

				if(responseDto.getData() != null)
				list = responseDto.getData();


				System.out.println("SUP RESPONSE " + responseDto);
				System.out.println("RES DATA " + responseDto.getData());
				feedback.setResponse(responseDto.isStatus());
				feedback.setMessage(responseDto.getMessage());

				client.close();
				return new RequestResult(feedback, list, null);
			}
			else if (action.getRequest().equalsIgnoreCase(RequestConstant.GET_STAFF_DAILY_ATTENDANCE_TASK_SUPERVISIONS_FOR_STAFF_DATE_DAILY_ATTENDANCE_SUPERVISION)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<StaffDailyAttendanceTaskSupervisionDTO> list = new ArrayList<StaffDailyAttendanceTaskSupervisionDTO>();

				String schoolStaffId = (String) action.getRequestBody().get(RequestDelimeters.SCHOOL_STAFF_ID);
				String supervisionDate = (String) action.getRequestBody().get(RequestDelimeters.SUPERVISION_DATE);
				String supervisionId = (String) action.getRequestBody().get(RequestDelimeters.STAFF_DAILY_ATTENDANCE_SUPERVISION_ID);

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);
//http://localhost:8070/staffDailyAttendanceSupervisions/{}/schoolstaffs/8a008082648d961401648dadbf0f0003/staffDailyAttendancetaskSupervisions?supervisionDate=18/03/2021
				SystemResponseDTO<List<StaffDailyAttendanceTaskSupervisionDTO>> responseDto = client.target(API_LINK)
						.path("staffDailyAttendanceSupervisions")
						.path(supervisionId)
						.path("schoolstaffs")
						.path(schoolStaffId)
						.path("staffDailyAttendancetaskSupervisions")
						.queryParam("supervisionDate", supervisionDate)
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<List<StaffDailyAttendanceTaskSupervisionDTO>>>() {
						});

				if(responseDto.getData() != null)
				list = responseDto.getData();


				System.out.println("SUP RESPONSE " + responseDto);
				System.out.println("RES DATA " + responseDto.getData());
				feedback.setResponse(responseDto.isStatus());
				feedback.setMessage(responseDto.getMessage());

				client.close();
				return new RequestResult(feedback, list, null);
			}
			else if (action.getRequest().equalsIgnoreCase(RequestConstant.SAVE_TIME_ATTENDANCE_SUPERVISION)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				TimeAttendanceSupervisionDTO dto = (TimeAttendanceSupervisionDTO) action.getRequestBody().get(RequestConstant.SAVE_TIME_ATTENDANCE_SUPERVISION);

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<SystemFeedbackDTO> postResponseDTO = client.target(API_LINK)
						.path("timeAttendanceSupervisions")
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});
				

				if (postResponseDTO != null) {
					feedback = postResponseDTO.getData();
				}
				
				System.out.println("Handler Time  "+dto);

				client.close();
				return new RequestResult(feedback);

			}
			else if (action.getRequest().equalsIgnoreCase(RequestConstant.MIGRATE_DATA)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);

				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				DataMigrationUtility.getInstance().migrateData(headers);

				feedback.setResponse(true);
				feedback.setMessage("Data Migration successful");

				return new RequestResult(feedback);
			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.MIGRATE_DATA_ATTENDACE)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);

				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				DataMigrationUtility.getInstance().migrateTeacherAttendance(headers);

				feedback.setResponse(true);
				feedback.setMessage("Data Migration successful");

				return new RequestResult(feedback);
			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.MIGRATE_DATA_TIMETABLES)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);

				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				DataMigrationUtility.getInstance().migrateTimeTables(headers);

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
