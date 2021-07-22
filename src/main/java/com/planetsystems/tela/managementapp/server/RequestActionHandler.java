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

import com.google.gson.Gson;
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
import com.planetsystems.tela.dto.FilterDTO;
import com.planetsystems.tela.dto.LearnerAttendanceDTO;
import com.planetsystems.tela.dto.LearnerEnrollmentDTO;
import com.planetsystems.tela.dto.RegionDto;
import com.planetsystems.tela.dto.SchoolCategoryDTO;
import com.planetsystems.tela.dto.SchoolClassDTO;
import com.planetsystems.tela.dto.SchoolDTO;
import com.planetsystems.tela.dto.SchoolStaffDTO;
import com.planetsystems.tela.dto.SmsSchoolStaffDTO;
import com.planetsystems.tela.dto.StaffDailyAttendanceSupervisionDTO;
import com.planetsystems.tela.dto.StaffDailyAttendanceTaskSupervisionDTO;
import com.planetsystems.tela.dto.StaffDailyTimeTableDTO;
import com.planetsystems.tela.dto.StaffDailyTimeTableLessonDTO;
import com.planetsystems.tela.dto.StaffEnrollmentDto;
import com.planetsystems.tela.dto.SubjectCategoryDTO;
import com.planetsystems.tela.dto.SubjectDTO;
import com.planetsystems.tela.dto.SystemErrorDTO;
import com.planetsystems.tela.dto.SystemFeedbackDTO;
import com.planetsystems.tela.dto.SystemMenuDTO;
import com.planetsystems.tela.dto.SystemResponseDTO;
import com.planetsystems.tela.dto.SystemUserGroupDTO;
import com.planetsystems.tela.dto.SystemUserGroupSystemMenuDTO;
import com.planetsystems.tela.dto.SystemUserProfileDTO;
import com.planetsystems.tela.dto.TimeAttendanceSupervisionDTO;
import com.planetsystems.tela.dto.TimeTableDTO;
import com.planetsystems.tela.dto.TimeTableLessonDTO;
import com.planetsystems.tela.dto.TokenFeedbackDTO;
import com.planetsystems.tela.dto.dashboard.AttendanceDashboardSummaryDTO;
import com.planetsystems.tela.dto.dashboard.DashboardSummaryDTO;
import com.planetsystems.tela.dto.reports.DistrictEndOfMonthTimeAttendanceDTO;
import com.planetsystems.tela.dto.reports.DistrictEndOfTermTimeAttendanceDTO;
import com.planetsystems.tela.dto.reports.DistrictEndOfWeekTimeAttendanceDTO;
import com.planetsystems.tela.dto.reports.DistrictReportFilterDTO;
import com.planetsystems.tela.dto.reports.NationalEndOfMonthTimeAttendanceDTO;
import com.planetsystems.tela.dto.reports.NationalEndOfTermTimeAttendanceDTO;
import com.planetsystems.tela.dto.reports.NationalEndOfWeekTimeAttendanceDTO;
import com.planetsystems.tela.dto.reports.NationalReportFilterDTO;
import com.planetsystems.tela.dto.reports.ReportPreviewRequestDTO;
import com.planetsystems.tela.dto.reports.SchoolEndOfMonthTimeAttendanceDTO;
import com.planetsystems.tela.dto.reports.SchoolEndOfTermTimeAttendanceDTO;
import com.planetsystems.tela.dto.reports.SchoolEndOfWeekTimeAttendanceDTO;
import com.planetsystems.tela.dto.reports.SchoolTimeOnTaskSummaryDTO;
import com.planetsystems.tela.dto.reports.TeacherClockInSummaryDTO;
import com.planetsystems.tela.dto.reports.outputs.DailyDistrictReport;
import com.planetsystems.tela.dto.reports.outputs.DistrictTermReport;
import com.planetsystems.tela.dto.reports.outputs.DistrictWeeklyReport;
import com.planetsystems.tela.dto.reports.outputs.NationalMonthlyReport;
import com.planetsystems.tela.dto.reports.outputs.NationalTermlyReport;
import com.planetsystems.tela.dto.reports.outputs.NationalWeeklyReport;
import com.planetsystems.tela.managementapp.shared.RequestAction;
import com.planetsystems.tela.managementapp.shared.RequestConstant;
import com.planetsystems.tela.managementapp.shared.RequestDelimeters;
import com.planetsystems.tela.managementapp.shared.RequestResult;
import com.planetsystems.tela.managementapp.shared.requestconstants.ReportsRequestConstant;
import com.planetsystems.tela.managementapp.shared.requestconstants.SmsRequest;
import com.planetsystems.tela.managementapp.shared.requestconstants.StaffEnrollmentRequest;
import com.planetsystems.tela.managementapp.shared.requestconstants.SystemMenuRequestConstant;
import com.planetsystems.tela.managementapp.shared.requestconstants.SystemUserGroupRequestConstant;
import com.planetsystems.tela.managementapp.shared.requestconstants.SystemUserGroupSystemMenuRequestConstant;
import com.planetsystems.tela.managementapp.shared.requestconstants.SystemUserProfileRequestConstant;

public class RequestActionHandler implements ActionHandler<RequestAction, RequestResult> {

	final String API_LINK = APIGateWay.getInstance().getApLink();
	final String REPORT_GEN_API = APIGateWay.getInstance().getReportGeneratorLink();

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
					System.out.println("LOGIN RESPONSE " + loginResponseDTO.getData());
					feedback = loginResponseDTO.getData();
				}

				client.close();
				return new RequestResult(feedback);

				////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			}
			if (action.getRequest().equalsIgnoreCase(RequestConstant.RESET_PASSWORD)) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				AuthenticationDTO dto = (AuthenticationDTO) action.getRequestBody().get(RequestConstant.DATA);
				System.out.print("DTO Email "+dto.getUserName());

				Client client = ClientBuilder.newClient();

				SystemResponseDTO<SystemFeedbackDTO> responseDTO = client.target(API_LINK).path("ResetPassword")
						.request(MediaType.APPLICATION_JSON).post(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				System.out.println("AUTH " + responseDTO);

				if (responseDTO != null) {
					System.out.println("LOGIN RESPONSE " + responseDTO.getData());
					feedback = responseDTO.getData();
				}

				client.close();
				return new RequestResult(feedback);

				////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			}
			if (action.getRequest().equalsIgnoreCase(RequestConstant.CHANGE_PASSWORD)) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				AuthenticationDTO dto = (AuthenticationDTO) action.getRequestBody().get(RequestConstant.DATA);
				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				System.out.print("DTO Email "+dto.getUserName());

				Client client = ClientBuilder.newClient();

				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<SystemFeedbackDTO> responseDTO = client.target(API_LINK).path("ChangePassword")
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});


				System.out.println("AUTH " + responseDTO);

				if (responseDTO != null) {
					System.out.println("LOGIN RESPONSE " + responseDTO.getData());
					feedback = responseDTO.getData();
					feedback.setResponse(responseDTO.isStatus());
				}
				

				client.close();
				return new RequestResult(feedback);

				////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			}
			else if (action.getRequest().equalsIgnoreCase(RequestConstant.SAVE_ACADEMIC_YEAR)) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				AcademicYearDTO dto = (AcademicYearDTO) action.getRequestBody().get(RequestConstant.SAVE_ACADEMIC_YEAR);
				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);

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
				}

				client.close();
				return new RequestResult(feedback);

				//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.UPDATE_ACADEMIC_YEAR)) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				AcademicYearDTO dto = (AcademicYearDTO) action.getRequestBody()
						.get(RequestConstant.UPDATE_ACADEMIC_YEAR);
				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);

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
				}

				client.close();
				return new RequestResult(feedback);

				/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.DELETE_ACADEMIC_YEAR)) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				String id = (String) action.getRequestBody().get(RequestDelimeters.ACADEMIC_YEAR_ID);
				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);

				Client client = ClientBuilder.newClient();

				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<SystemFeedbackDTO> deleteResponseDTO = client.target(API_LINK).path("academicyears")
						.path(id).request(MediaType.APPLICATION_JSON).headers(headers)
						.delete(new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
						});

				if (deleteResponseDTO != null) {
					feedback = deleteResponseDTO.getData();
				}

				client.close();
				return new RequestResult(feedback);

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

				// System.out.println("RESPONSE " + responseDto);
				// System.out.println("RES DATA " + responseDto.getData());

				feedback.setResponse(true);
				feedback.setMessage(responseDto.getMessage());

				client.close();
				return new RequestResult(feedback, list, null);

				/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.SAVE_ACADEMIC_TERM)) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				AcademicTermDTO dto = (AcademicTermDTO) action.getRequestBody().get(RequestConstant.SAVE_ACADEMIC_TERM);
				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);

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

				client.close();
				return new RequestResult(feedback);

				////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.UPDATE_ACADEMIC_TERM)) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				AcademicTermDTO dto = (AcademicTermDTO) action.getRequestBody()
						.get(RequestConstant.UPDATE_ACADEMIC_TERM);
				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);

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
				}

				client.close();
				return new RequestResult(feedback);

				///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.DELETE_ACADEMIC_TERM)) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				String id = (String) action.getRequestBody().get(RequestDelimeters.ACADEMIC_TERM_ID);
				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);

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

				client.close();
				return new RequestResult(feedback);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.ACTIVATE_ACADEMIC_TERM)) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				String id = (String) action.getRequestBody().get(RequestDelimeters.ACADEMIC_TERM_ID);
				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);

				Client client = ClientBuilder.newClient();
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<SystemFeedbackDTO> deleteResponseDTO = client.target(API_LINK).path("academicterms")
						.path("activate").path(id).request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
						});

				if (deleteResponseDTO != null) {
					feedback = deleteResponseDTO.getData();
				}

				client.close();
				return new RequestResult(feedback);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.DEACTIVATE_ACADEMIC_TERM)) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				String id = (String) action.getRequestBody().get(RequestDelimeters.ACADEMIC_TERM_ID);
				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);

				Client client = ClientBuilder.newClient();
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<SystemFeedbackDTO> deleteResponseDTO = client.target(API_LINK).path("academicterms")
						.path("deactivate").path(id).request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
						});

				if (deleteResponseDTO != null) {
					feedback = deleteResponseDTO.getData();
				}

				client.close();
				return new RequestResult(feedback);

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
			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.FILTER_ACADEMIC_TERMS_BY_ACADEMIC_YEAR)) {
				String id = (String) action.getRequestBody().get(RequestDelimeters.ACADEMIC_YEAR_ID);
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<AcademicTermDTO> list = new ArrayList<AcademicTermDTO>();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);

				Client client = ClientBuilder.newClient();
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);
				/// filter/academictermsby/academicyears/{id}
				SystemResponseDTO<List<AcademicTermDTO>> responseDto = client.target(API_LINK).path("filter")
						.path("academictermsby").path("academicyears").path(id).request(MediaType.APPLICATION_JSON)
						.headers(headers).get(new GenericType<SystemResponseDTO<List<AcademicTermDTO>>>() {
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

				client.close();
				return new RequestResult(feedback);

				////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.UPDATE_REGION)) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				RegionDto dto = (RegionDto) action.getRequestBody().get(RequestConstant.UPDATE_REGION);

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				Client client = ClientBuilder.newClient();

				SystemResponseDTO<SystemFeedbackDTO> updateResponseDTO = client.target(API_LINK).path("regions")
						.path(dto.getId()).request(MediaType.APPLICATION_JSON).headers(headers)
						.put(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (updateResponseDTO != null) {
					feedback = updateResponseDTO.getData();
				}

				client.close();
				return new RequestResult(feedback);

				///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.DELETE_REGION)) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				String id = (String) action.getRequestBody().get(RequestDelimeters.REGION_ID);
				System.out.println("DTO " + id);

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
				}

				client.close();
				return new RequestResult(feedback);

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
			} else if (action.getRequest()
					.equalsIgnoreCase(RequestConstant.GET_REGIONS_BY_SYSTEM_USER_PROFILE_SCHOOLS)) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				List<RegionDto> list = new ArrayList<RegionDto>();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				Client client = ClientBuilder.newClient();

				SystemResponseDTO<List<RegionDto>> responseDto = client.target(API_LINK)
						.path("SystemUserProfileSchools").path("Regions").request(MediaType.APPLICATION_JSON)
						.headers(headers).get(new GenericType<SystemResponseDTO<List<RegionDto>>>() {
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

				client.close();
				return new RequestResult(feedback);

				/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.UPDATE_DISTRICT)) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				DistrictDTO dto = (DistrictDTO) action.getRequestBody().get(RequestConstant.UPDATE_DISTRICT);

				System.out.println("DTO " + dto);

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
				}

				client.close();
				return new RequestResult(feedback);

				////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.DELETE_DISTRICT)) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				String id = (String) action.getRequestBody().get(RequestDelimeters.DISTRICT_ID);

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
				}

				client.close();
				return new RequestResult(feedback);

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
			} else if (action.getRequest()
					.equalsIgnoreCase(RequestConstant.GET_DISTRICTS_BY_SYSTEM_USER_PROFILE_SCHOOLS)) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<DistrictDTO> list = new ArrayList<DistrictDTO>();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				Client client = ClientBuilder.newClient();

				SystemResponseDTO<List<DistrictDTO>> responseDto = client.target(API_LINK).path("SystemUserProfile")
						.path("Districts").request(MediaType.APPLICATION_JSON).headers(headers)
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

			else if (action.getRequest()
					.equalsIgnoreCase(RequestConstant.GET_DISTRICTS_BY_SYSTEM_USER_PROFILE_SCHOOLS_REGION)) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<DistrictDTO> list = new ArrayList<DistrictDTO>();
				String regionId = (String) action.getRequestBody().get(RequestDelimeters.REGION_ID);

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				Client client = ClientBuilder.newClient();

				SystemResponseDTO<List<DistrictDTO>> responseDto = client.target(API_LINK)
						.path("SystemUserProfileSchools").path("Regions").path(regionId).path("Districts")
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
			}

			else if (action.getRequest().equalsIgnoreCase(RequestConstant.FILTER_DISTRICTS_BY_REGION)) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<DistrictDTO> list = new ArrayList<DistrictDTO>();

				String id = (String) action.getRequestBody().get(RequestDelimeters.REGION_ID);
				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);

				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				Client client = ClientBuilder.newClient();
				/// filter/districtsby/regions/{id}
				SystemResponseDTO<List<DistrictDTO>> responseDto = client.target(API_LINK).path("filter")
						.path("districtsby").path("regions").path(id).request(MediaType.APPLICATION_JSON)
						.headers(headers).get(new GenericType<SystemResponseDTO<List<DistrictDTO>>>() {
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

				client.close();
				return new RequestResult(feedback);

				////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.UPDATE_SCHOOL_CATEGORY)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				SchoolCategoryDTO dto = (SchoolCategoryDTO) action.getRequestBody()
						.get(RequestConstant.UPDATE_SCHOOL_CATEGORY);

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
				}

				client.close();
				return new RequestResult(feedback);

				/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.DELETE_SCHOOL_CATEGORY)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				String id = (String) action.getRequestBody().get(RequestDelimeters.SCHOOL_CATEGORY_ID);
				System.out.println("DTO " + id);

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
				}

				client.close();
				return new RequestResult(feedback);

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

			else if (action.getRequest()
					.equalsIgnoreCase(RequestConstant.GET_SCHOOL_CATEGORIES_BY_SYSTEM_USER_PROFILE_SCHOOLS)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<SchoolCategoryDTO> list = new ArrayList<SchoolCategoryDTO>();

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<List<SchoolCategoryDTO>> responseDto = client.target(API_LINK)
						.path("SystemUserProfile").path("SchoolCategories").request(MediaType.APPLICATION_JSON)
						.headers(headers).get(new GenericType<SystemResponseDTO<List<SchoolCategoryDTO>>>() {
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

				client.close();
				return new RequestResult(feedback);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.UPDATE_SCHOOL)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				SchoolDTO dto = (SchoolDTO) action.getRequestBody().get(RequestConstant.UPDATE_SCHOOL);

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
				}

				client.close();
				return new RequestResult(feedback);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.DELETE_SCHOOL)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				String id = (String) action.getRequestBody().get(RequestDelimeters.SCHOOL_ID);

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
				}

				client.close();
				return new RequestResult(feedback);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.GET_SCHOOLS)
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

			else if (action.getRequest()
					.equalsIgnoreCase(RequestConstant.GET_SCHOOLS_BY_SYSTEM_USER_PROFILE_SCHOOLS_PROFILE)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<SchoolDTO> list = new ArrayList<SchoolDTO>();
				String profileId = (String) action.getRequestBody().get(RequestDelimeters.SYSTEM_USER_PROFILE_ID);

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<List<SchoolDTO>> responseDto = client.target(API_LINK)
						.path("SystemUserProfileSchools").path(profileId).path("Schools")
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

			else if (action.getRequest()
					.equalsIgnoreCase(RequestConstant.GET_NOT_SCHOOLS_BY_SYSTEM_USER_PROFILE_SCHOOLS_PROFILE_DISTRICT)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<SchoolDTO> list = new ArrayList<SchoolDTO>();
				String profileId = (String) action.getRequestBody().get(RequestDelimeters.SYSTEM_USER_PROFILE_ID);
				String districtId = (String) action.getRequestBody().get(RequestDelimeters.DISTRICT_ID);

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<List<SchoolDTO>> responseDto = client.target(API_LINK)
						.path("NotSystemUserProfileSchools").path(profileId).path("Districts").path(districtId)
						.path("Schools").request(MediaType.APPLICATION_JSON).headers(headers)
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

			else if (action.getRequest().equalsIgnoreCase(RequestConstant.GET_SCHOOLS_BY_SYSTEM_USER_PROFILE_SCHOOLS)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<SchoolDTO> list = new ArrayList<SchoolDTO>();

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<List<SchoolDTO>> responseDto = client.target(API_LINK).path("SystemUserProfile")
						.path("Schools").request(MediaType.APPLICATION_JSON).headers(headers)
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

			else if (action.getRequest()
					.equalsIgnoreCase(RequestConstant.FILTER_SCHOOLS_BY_SCHOOL_CATEGORY_REGION_DISTRICT)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				// String districtId = (String)
				// action.getRequestBody().get(RequestDelimeters.DISTRICT_ID) != null ? (String)
				// action.getRequestBody().get(RequestDelimeters.DISTRICT_ID) : "";
				// String regionId = (String)
				// action.getRequestBody().get(RequestDelimeters.REGION_ID) != null ? (String)
				// action.getRequestBody().get(RequestDelimeters.REGION_ID) : "" ;
				// String schoolCategoryId = (String)
				// action.getRequestBody().get(RequestDelimeters.SCHOOL_CATEGORY_ID) != null ?
				// (String) action.getRequestBody().get(RequestDelimeters.SCHOOL_CATEGORY_ID) :
				// "";
				//
				FilterDTO filterDTO = (FilterDTO) action.getRequestBody().get(RequestDelimeters.FILTER_SCHOOL);
				List<SchoolDTO> list = new ArrayList<SchoolDTO>();

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				/// filter/schoolsby/schoolcategories/{schoolCategoryId}/regions/{region}/districts/{districtId}
				SystemResponseDTO<List<SchoolDTO>> responseDto = client.target(API_LINK).path("filter").path("schools")
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.post(Entity.entity(filterDTO, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<List<SchoolDTO>>>() {
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
				client.close();
				return new RequestResult(feedback);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.UPDATE_SCHOOL_CLASS)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				SchoolClassDTO dto = (SchoolClassDTO) action.getRequestBody().get(RequestConstant.UPDATE_SCHOOL_CLASS);

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
				}

				client.close();
				return new RequestResult(feedback);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.DELETE_SCHOOL_CLASS)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				String id = (String) action.getRequestBody().get(RequestDelimeters.SCHOOL_CLASS_ID);

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				Client client = ClientBuilder.newClient();

				SystemResponseDTO<SystemFeedbackDTO> deleteResponseDTO = client.target(API_LINK).path("SchoolClasses")
						.path(id).request(MediaType.APPLICATION_JSON).headers(headers)
						.delete(new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
						});

				if (deleteResponseDTO != null) {
					feedback = deleteResponseDTO.getData();
				}

				client.close();
				return new RequestResult(feedback);

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

			else if (action.getRequest()
					.equalsIgnoreCase(RequestConstant.GET_SCHOOL_CLASSES_BY_SYSTEM_USER_PROFILE_SCHOOLS)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<SchoolClassDTO> list = new ArrayList<SchoolClassDTO>();

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);
				SystemResponseDTO<List<SchoolClassDTO>> responseDto = client.target(API_LINK).path("SystemUserProfile")
						.path("SchoolClasses").request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<List<SchoolClassDTO>>>() {
						});

				list = responseDto.getData();

				System.out.println("RESPONSE " + responseDto);
				System.out.println("RES DATA " + responseDto.getData());
				feedback.setResponse(true);
				feedback.setMessage(responseDto.getMessage());

				client.close();
				return new RequestResult(feedback, list, null);
			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.GET_SCHOOL_CLASSES_IN_SCHOOL_ACADEMIC_TERM)
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

			else if (action.getRequest()
					.equalsIgnoreCase(RequestConstant.FILTER_SCHOOL_CLASS_BY_ACADEMIC_YEAR_TERM_DISTRICT_SCHOOL)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<SchoolClassDTO> list = new ArrayList<SchoolClassDTO>();
				FilterDTO dto = (FilterDTO) action.getRequestBody().get(RequestDelimeters.FILTER_SCHOOL_CLASS);

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);
				/// filter/schoolclasses
				SystemResponseDTO<List<SchoolClassDTO>> responseDto = client.target(API_LINK).path("filter")
						.path("schoolclasses").request(MediaType.APPLICATION_JSON).headers(headers)
						.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<List<SchoolClassDTO>>>() {
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

				client.close();
				return new RequestResult(feedback);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.UPDATE_SUBJECT_CATEGORY)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				SubjectCategoryDTO dto = (SubjectCategoryDTO) action.getRequestBody()
						.get(RequestConstant.UPDATE_SUBJECT_CATEGORY);

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
				}

				client.close();
				return new RequestResult(feedback);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.DELETE_SUBJECT_CATEGORY)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				String id = (String) action.getRequestBody().get(RequestDelimeters.SUBJECT_CATEGORY_ID);

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
				}

				client.close();
				return new RequestResult(feedback);

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

				client.close();
				return new RequestResult(feedback);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.UPDATE_SUBJECT)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				SubjectDTO dto = (SubjectDTO) action.getRequestBody().get(RequestConstant.UPDATE_SUBJECT);

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
				}

				client.close();
				return new RequestResult(feedback);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.DELETE_SUBJECT)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				String id = (String) action.getRequestBody().get(RequestDelimeters.SUBJECT_ID);

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
				}

				client.close();
				return new RequestResult(feedback);

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
			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.GET_SUBJECTS_IN_SUBJECT_CATEGORY)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<SubjectDTO> list = new ArrayList<SubjectDTO>();
				String id = (String) action.getRequestBody().get(RequestDelimeters.SUBJECT_CATEGORY_ID);

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);
				// subjectcategories/{subjectCategoryID}/subjects
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

			else if (action.getRequest().equalsIgnoreCase(RequestConstant.FILTER_SUBJECTS_BY_SUBJECT_CATEGORY)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<SubjectDTO> list = new ArrayList<SubjectDTO>();
				String id = (String) action.getRequestBody().get(RequestDelimeters.SUBJECT_CATEGORY_ID);

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);
				/// filter/subjectsby/subjectcategories/{subjectCategoryID)
				SystemResponseDTO<List<SubjectDTO>> responseDto = client.target(API_LINK).path("filter")
						.path("subjectsby").path("subjectcategories").path(id).request(MediaType.APPLICATION_JSON)
						.headers(headers).get(new GenericType<SystemResponseDTO<List<SubjectDTO>>>() {
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

				client.close();
				return new RequestResult(feedback);

			} else if (action.getRequest().equalsIgnoreCase(StaffEnrollmentRequest.UPDATE)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				SchoolStaffDTO dto = (SchoolStaffDTO) action.getRequestBody().get(StaffEnrollmentRequest.DATA);
				String id = (String)action.getRequestBody().get(StaffEnrollmentRequest.ID);

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<SystemFeedbackDTO> updateResponseDTO = client.target(API_LINK).path("SchoolStaff")
						.path(id).request(MediaType.APPLICATION_JSON).headers(headers)
						.put(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (updateResponseDTO != null) {
					feedback = updateResponseDTO.getData();
				}

				client.close();
				return new RequestResult(feedback);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.DELETE_SCHOOL_STAFF)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				String id = (String) action.getRequestBody().get(RequestDelimeters.SCHOOL_STAFF_ID);

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
				}

				client.close();
				return new RequestResult(feedback);

			}

			else if (action.getRequest().equalsIgnoreCase(RequestConstant.GET_SCHOOL_STAFF)
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
			}

			else if (action.getRequest()
					.equalsIgnoreCase(RequestConstant.GET_SCHOOL_STAFFS_BY_SYSTEM_USER_PROFILE_SCHOOLS)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<SchoolStaffDTO> list = new ArrayList<SchoolStaffDTO>();

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<List<SchoolStaffDTO>> responseDto = client.target(API_LINK)
						.path("SystemUserProfileSchools").path("SchoolStaffs").request(MediaType.APPLICATION_JSON)
						.headers(headers).get(new GenericType<SystemResponseDTO<List<SchoolStaffDTO>>>() {
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

			else if (action.getRequest().equalsIgnoreCase(RequestConstant.FILTER_SCHOOL_STAFFS_BY_DISTRICT_SCHOOL)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<SchoolStaffDTO> list = new ArrayList<SchoolStaffDTO>();
				FilterDTO dto = (FilterDTO) action.getRequestBody().get(RequestDelimeters.FILTER_SCHOOL_STAFFS);
				// String schoolId = (String)
				// action.getRequestBody().get(RequestDelimeters.SCHOOL_ID);

				Client client = ClientBuilder.newClient();
				// filter/schoolstaffsby/districts/{districtId}/schools/{schoolId}
				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<List<SchoolStaffDTO>> responseDto = client.target(API_LINK).path("filter")
						.path("schoolstaffs").request(MediaType.APPLICATION_JSON).headers(headers)
						.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<List<SchoolStaffDTO>>>() {
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

				SystemResponseDTO<List<SchoolStaffDTO>> responseDto = client.target(API_LINK).path("academicterms")
						.path(academicTermId).path("schools").path(schoolId).path("absents")
						.queryParam("absentDate", absentDate).request(MediaType.APPLICATION_JSON).headers(headers)
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

				client.close();
				return new RequestResult(feedback);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.UPDATE_STAFF_ENROLLMENT)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				StaffEnrollmentDto dto = (StaffEnrollmentDto) action.getRequestBody()
						.get(RequestConstant.UPDATE_STAFF_ENROLLMENT);

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
				}

				client.close();
				return new RequestResult(feedback);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.DELETE_STAFF_ENROLLMENT)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				String id = (String) action.getRequestBody().get(RequestDelimeters.STAFF_ENROLLMENT_ID);

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
				}

				client.close();
				return new RequestResult(feedback);

			} else if (action.getRequest()
					.equalsIgnoreCase(RequestConstant.GET_STAFF_ENROLLMENTS_SYSTEM_USER_PROFILE_SCHOOLS)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<StaffEnrollmentDto> list = new ArrayList<StaffEnrollmentDto>();

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<List<StaffEnrollmentDto>> responseDto = client.target(API_LINK)
						.path("SystemUserProfile").path("StaffEnrollments").request(MediaType.APPLICATION_JSON)
						.headers(headers).get(new GenericType<SystemResponseDTO<List<StaffEnrollmentDto>>>() {
						});

				list = responseDto.getData();

				System.out.println("RESPONSE " + responseDto);
				System.out.println("RES DATA " + responseDto.getData());
				feedback.setResponse(responseDto.isStatus());
				feedback.setMessage(responseDto.getMessage());

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
				String academicYearId = (String) action.getRequestBody().get(RequestDelimeters.ACADEMIC_YEAR_ID);
				String academicTermId = (String) action.getRequestBody().get(RequestDelimeters.ACADEMIC_TERM_ID);
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

			else if (action.getRequest().equalsIgnoreCase(
					RequestConstant.FILTER_SCHOOL_STAFF_ENROLLMENTS_BY_ACADEMIC_YEAR_ACADEMIC_TERM_DISTRICT_SCHOOL)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<StaffEnrollmentDto> list = new ArrayList<StaffEnrollmentDto>();

				FilterDTO dto = (FilterDTO) action.getRequestBody().get(RequestDelimeters.FILTER_STAFF_ENROLLMENTS);

				// String academicTermId = (String) action.getRequestBody()
				// .get(RequestDelimeters.ACADEMIC_TERM_ID);
				// String districtId = (String)
				// action.getRequestBody().get(RequestDelimeters.DISTRICT_ID);
				// String schoolId = (String)
				// action.getRequestBody().get(RequestDelimeters.SCHOOL_ID);

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);
				// filter/staffenrollmentsby/academicyears/{academicYearId}/academicterms/{academicTermId}/districts/{districtId}/schools/{schoolId}
				SystemResponseDTO<List<StaffEnrollmentDto>> responseDto = client.target(API_LINK).path("filter")
						.path("staffenrollments").request(MediaType.APPLICATION_JSON).headers(headers)
						.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<List<StaffEnrollmentDto>>>() {
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

				client.close();
				return new RequestResult(feedback);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.UPDATE_LEARNER_ENROLLMENT)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				LearnerEnrollmentDTO dto = (LearnerEnrollmentDTO) action.getRequestBody()
						.get(RequestConstant.UPDATE_LEARNER_ENROLLMENT);

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
				}

				client.close();
				return new RequestResult(feedback);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.DELETE_LEARNER_ENROLLMENT)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				String id = (String) action.getRequestBody().get(RequestDelimeters.LEARNER_ENROLLMENT_ID);

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
				}

				client.close();
				return new RequestResult(feedback);

			}

			else if (action.getRequest().equalsIgnoreCase(RequestConstant.GET_LEARNER_ENROLLMENT)
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

			else if (action.getRequest()
					.equalsIgnoreCase(RequestConstant.GET_LEARNER_ENROLLMENTS_BY_SYSTEM_USER_PROFILE_SCHOOLS)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<LearnerEnrollmentDTO> list = new ArrayList<LearnerEnrollmentDTO>();

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<List<LearnerEnrollmentDTO>> responseDto = client.target(API_LINK)
						.path("SystemUserProfile").path("LearnerEnrollments").request(MediaType.APPLICATION_JSON)
						.headers(headers).get(new GenericType<SystemResponseDTO<List<LearnerEnrollmentDTO>>>() {
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
				String academicYearId = (String) action.getRequestBody().get(RequestDelimeters.ACADEMIC_YEAR_ID);
				String academicTermId = (String) action.getRequestBody().get(RequestDelimeters.ACADEMIC_TERM_ID);
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

			else if (action.getRequest().equalsIgnoreCase(
					RequestConstant.FILTER_LEARNER_ENROLLMENTS_BY_ACADEMIC_YEAR_ACADEMIC_TERM_DISTRICT_SCHOOL)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<LearnerEnrollmentDTO> list = new ArrayList<LearnerEnrollmentDTO>();

				FilterDTO dto = (FilterDTO) action.getRequestBody().get(RequestDelimeters.FILTER_LEARNER_ENROLLEMENTS);
				//
				// String academicTermId = (String) action.getRequestBody()
				// .get(RequestDelimeters.ACADEMIC_TERM_ID);
				// String districtId = (String)
				// action.getRequestBody().get(RequestDelimeters.DISTRICT_ID);
				// String schooolId = (String)
				// action.getRequestBody().get(RequestDelimeters.SCHOOL_ID);

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);
				/// filter/learnerenrollmentsby/academicyears/{academicYearId}/academicterms/{academicTermId}/districts/{districtId}/schools/{schoolId}
				SystemResponseDTO<List<LearnerEnrollmentDTO>> responseDto = client.target(API_LINK).path("filter")
						.path("learnerenrollments").request(MediaType.APPLICATION_JSON).headers(headers)
						.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<List<LearnerEnrollmentDTO>>>() {
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
				client.close();
				return new RequestResult(feedback);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.UPDATE_CLOCK_IN)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				ClockInDTO dto = (ClockInDTO) action.getRequestBody().get(RequestConstant.UPDATE_CLOCK_IN);

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
				}

				client.close();
				return new RequestResult(feedback);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.DELETE_CLOCK_IN)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				String id = (String) action.getRequestBody().get(RequestDelimeters.CLOCK_IN_ID);

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
				}

				client.close();
				return new RequestResult(feedback);

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
					.equalsIgnoreCase(RequestConstant.GET_CLOCK_INS_BY_SYSTEM_USER_PROFILE_SCHOOLS)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<ClockInDTO> list = new ArrayList<ClockInDTO>();

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<List<ClockInDTO>> responseDto = client.target(API_LINK).path("SystemUserProfile")
						.path("ClockIns").request(MediaType.APPLICATION_JSON).headers(headers)
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
					.equalsIgnoreCase(RequestConstant.FILTER_CLOCKINS_BY_ACADEMIC_YEAR_ACADEMIC_TERM_DISTRICT_SCHOOL)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<ClockInDTO> list = new ArrayList<ClockInDTO>();
				FilterDTO dto = (FilterDTO) action.getRequestBody().get(RequestDelimeters.FILTER_CLOCK_INS);
				// String academicTermId = (String)
				// action.getRequestBody().get(RequestDelimeters.ACADEMIC_TERM_ID);
				// String districtId = (String)
				// action.getRequestBody().get(RequestDelimeters.DISTRICT_ID);
				// String schoolId = (String)
				// action.getRequestBody().get(RequestDelimeters.SCHOOL_ID);
				// String date = (String)
				// action.getRequestBody().get(RequestDelimeters.CLOCKIN_DATE);
				Client client = ClientBuilder.newClient();
				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				/// filter/clockinsby/academicyears/{academicYearId}/academicterms/{academicTermId}/districts/{districtId}/schools/{schoolId}
				SystemResponseDTO<List<ClockInDTO>> responseDto = client.target(API_LINK).path("filter")
						.path("clockins").request(MediaType.APPLICATION_JSON).headers(headers)
						.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<List<ClockInDTO>>>() {
								});

				list = responseDto.getData();

				System.out.println("RESPONSE " + responseDto);
				System.out.println("RES DATA " + responseDto.getData());
				feedback.setResponse(responseDto.isStatus());
				feedback.setMessage(responseDto.getMessage());

				client.close();
				return new RequestResult(feedback, list, null);
			}

			else if (action.getRequest().equalsIgnoreCase(RequestConstant.GET_CLOCK_IN_By_ACADEMIC_TERM_SCHOOL_DATE)
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

				SystemResponseDTO<List<ClockInDTO>> responseDto = client.target(API_LINK).path("academicterms")
						.path(academicTermId).path("schools").path(schoolId).path("clockins")
						.queryParam("clockInDate", date).request(MediaType.APPLICATION_JSON).headers(headers)
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

				System.out.println("RESPONSE " + feedback);
				System.out.println("RES DATA " + feedback.getMessage());

				client.close();
				return new RequestResult(feedback);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.UPDATE_CLOCK_OUT)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				ClockOutDTO dto = (ClockOutDTO) action.getRequestBody().get(RequestConstant.UPDATE_CLOCK_OUT);

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
				}

				client.close();
				return new RequestResult(feedback);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.DELETE_CLOCK_OUT)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				String id = (String) action.getRequestBody().get(RequestDelimeters.CLOCK_OUT_ID);

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
				}

				client.close();
				return new RequestResult(feedback);

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
			}

			else if (action.getRequest().equalsIgnoreCase(RequestConstant.GET_CLOCK_OUTS_BY_SYSTEM_USER_PROFILE_SCHOOLS)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<ClockOutDTO> list = new ArrayList<ClockOutDTO>();

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<List<ClockOutDTO>> responseDto = client.target(API_LINK).path("SystemUserProfile")
						.path("ClockOuts").request(MediaType.APPLICATION_JSON).headers(headers)
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
					.equalsIgnoreCase(RequestConstant.FILTER_CLOCK_OUTS_BY_ACADEMIC_YEAR_ACADEMIC_TERM_DISTRICT_SCHOOL)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<ClockOutDTO> list = new ArrayList<ClockOutDTO>();
				FilterDTO dto = (FilterDTO) action.getRequestBody().get(RequestDelimeters.FILTER_CLOCK_OUTS);
				// String academicTermId = (String)
				// action.getRequestBody().get(RequestDelimeters.ACADEMIC_TERM_ID);
				// String districtId = (String)
				// action.getRequestBody().get(RequestDelimeters.DISTRICT_ID);
				// String schoolId = (String)
				// action.getRequestBody().get(RequestDelimeters.SCHOOL_ID);
				// String date = (String)
				// action.getRequestBody().get(RequestDelimeters.CLOCK_OUT_DATE);
				/// filter/clockoutsby/academicyears/{academicYearId}/academicterms/{academicTermId}/districts/{districtId}/schools/{schoolId}
				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<List<ClockOutDTO>> responseDto = client.target(API_LINK).path("filter")
						.path("clockouts").request(MediaType.APPLICATION_JSON).headers(headers)
						.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<List<ClockOutDTO>>>() {
								});

				list = responseDto.getData();

				System.out.println("RESPONSE " + responseDto);
				System.out.println("RES DATA " + responseDto.getData());
				feedback.setResponse(responseDto.isStatus());
				feedback.setMessage(responseDto.getMessage());

				client.close();
				return new RequestResult(feedback, list, null);
			}

			else if (action.getRequest().equalsIgnoreCase(RequestConstant.GET_CLOCK_OUT_BY_TERM_SCHOOL_DATE)
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

				SystemResponseDTO<List<ClockOutDTO>> responseDto = client.target(API_LINK).path("academicterms")
						.path(academicTermId).path("schools").path(schoolId).path("clockouts")
						.queryParam("clockOutDate", date).request(MediaType.APPLICATION_JSON).headers(headers)
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

				client.close();
				return new RequestResult(feedback);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.UPDATE_LEARNER_ATTENDANCE)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				LearnerAttendanceDTO dto = (LearnerAttendanceDTO) action.getRequestBody()
						.get(RequestConstant.UPDATE_LEARNER_ATTENDANCE);

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
				}

				client.close();
				return new RequestResult(feedback);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.DELETE_LEARNER_ATTENDANCE)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				String id = (String) action.getRequestBody().get(RequestDelimeters.LEARNER_ATTENDANCE_ID);

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
				}

				client.close();
				return new RequestResult(feedback);

			}

			else if (action.getRequest().equalsIgnoreCase(RequestConstant.GET_LEARNER_ATTENDANCE)
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
			}

			else if (action.getRequest()
					.equalsIgnoreCase(RequestConstant.GET_LEARNER_ATTENDANCES_BY_SYSTEM_USER_PROFILE_SCHOOLS)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<LearnerAttendanceDTO> list = new ArrayList<LearnerAttendanceDTO>();

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<List<LearnerAttendanceDTO>> responseDto = client.target(API_LINK)
						.path("SystemUserProfile").path("LearnerAttendances").request(MediaType.APPLICATION_JSON)
						.headers(headers).get(new GenericType<SystemResponseDTO<List<LearnerAttendanceDTO>>>() {
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
				String academicYearId = (String) action.getRequestBody().get(RequestDelimeters.ACADEMIC_YEAR_ID);
				String academicTermId = (String) action.getRequestBody().get(RequestDelimeters.ACADEMIC_TERM_ID);
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

			else if (action.getRequest().equalsIgnoreCase(
					RequestConstant.FILTER_LEARNER_ATTENDANCE_BY_ACADEMIC_YEAR_ACADEMIC_TERM_DISTRICT_SCHOOL)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<LearnerAttendanceDTO> list = new ArrayList<LearnerAttendanceDTO>();
				FilterDTO dto = (FilterDTO) action.getRequestBody().get(RequestDelimeters.FILTER_LEARNER_ATTENDANCES);
				// String academicTermId = (String) action.getRequestBody()
				// .get(RequestDelimeters.ACADEMIC_TERM_ID);
				// String districtId = (String)
				// action.getRequestBody().get(RequestDelimeters.DISTRICT_ID);
				// String schoolId = (String)
				// action.getRequestBody().get(RequestDelimeters.SCHOOL_ID);
				// String date = (String)
				// action.getRequestBody().get(RequestDelimeters.ATTENDANCE_DATE);
				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				/// filter/learnerattendancesby/academicyears/{academicYearId}/academicterms/{academicTermId}/districts/{districtId}/schools/{schoolId}"

				SystemResponseDTO<List<LearnerAttendanceDTO>> responseDto = client.target(API_LINK).path("filter")
						.path("learnerattendances").request(MediaType.APPLICATION_JSON).headers(headers)
						.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<List<LearnerAttendanceDTO>>>() {
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
			}

			else if (action.getRequest()
					.equalsIgnoreCase(RequestConstant.GET_TIME_TABLES_BY_SYSTEM_USER_PROFILE_SCHOOLS)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<TimeTableDTO> list = new ArrayList<TimeTableDTO>();

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<List<TimeTableDTO>> responseDto = client.target(API_LINK).path("SystemUserProfile")
						.path("Timetables").request(MediaType.APPLICATION_JSON).headers(headers)
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
			else if (action.getRequest().equalsIgnoreCase(RequestConstant.SAVE_TIME_TABLE_LESSON_BY_TIME_TABLE)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				TimeTableLessonDTO dto = (TimeTableLessonDTO) action.getRequestBody()
						.get(RequestConstant.SAVE_TIME_TABLE_LESSON_BY_TIME_TABLE);
				String timeTableId = (String) action.getRequestBody().get(RequestDelimeters.TIME_TABLE_ID);

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);
				/// timetables/{id}/timetablelessons
				SystemResponseDTO<SystemFeedbackDTO> postResponseDTO = client.target(API_LINK).path("timetables")
						.path(timeTableId).path("timetablelessons").request(MediaType.APPLICATION_JSON).headers(headers)
						.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (postResponseDTO != null) {
					feedback = postResponseDTO.getData();
					System.out.print("FEEDBACK " + feedback);
				}

				client.close();
				return new RequestResult(feedback);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.DELETE_TIME_TABLE_LESSON)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				String id = (String) action.getRequestBody().get(RequestDelimeters.TIME_TABLE_LESSON_ID);

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				Client client = ClientBuilder.newClient();
				/// timetablelessons/{id}
				SystemResponseDTO<SystemFeedbackDTO> deleteResponseDTO = client.target(API_LINK)
						.path("timetablelessons").path(id).request(MediaType.APPLICATION_JSON).headers(headers)
						.delete(new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
						});

				if (deleteResponseDTO != null) {
					feedback = deleteResponseDTO.getData();
				}

				client.close();
				return new RequestResult(feedback);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.GET_TIME_TABLE_LESSONS_BY_TIME_TABLE)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<TimeTableLessonDTO> list = new ArrayList<TimeTableLessonDTO>();
				String timeTableId = (String) action.getRequestBody().get(RequestDelimeters.TIME_TABLE_ID);

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);
				/// timetables/{id}/timetablelessons

				SystemResponseDTO<List<TimeTableLessonDTO>> responseDto = client.target(API_LINK).path("timetables")
						.path(timeTableId).path("timetablelessons").request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<List<TimeTableLessonDTO>>>() {
						});

				if (responseDto.getData() != null)
					list = responseDto.getData();

				System.out.println("RESPONSE " + responseDto);
				System.out.println("RES DATA " + responseDto.getData());
				feedback.setResponse(responseDto.isStatus());
				feedback.setMessage(responseDto.getMessage());

				client.close();
				return new RequestResult(feedback, list, null);
			} else if (action.getRequest().equalsIgnoreCase(
					RequestConstant.GET_TIME_TABLE_LESSONS_FOR_STAFF_ACADEMIC_YEAR_TERM_DISTRICT_SCHOOL_DAY)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<TimeTableLessonDTO> list = new ArrayList<TimeTableLessonDTO>();

				String academicYearId = (String) action.getRequestBody().get(RequestDelimeters.ACADEMIC_YEAR_ID);
				String academicTermId = (String) action.getRequestBody().get(RequestDelimeters.ACADEMIC_TERM_ID);
				String districtId = (String) action.getRequestBody().get(RequestDelimeters.DISTRICT_ID);
				String schoolId = (String) action.getRequestBody().get(RequestDelimeters.SCHOOL_ID);
				String schoolStaffId = (String) action.getRequestBody().get(RequestDelimeters.SCHOOL_STAFF_ID);
				String lessonDay = (String) action.getRequestBody().get(RequestDelimeters.LESSON_DAY);

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);
				/*
				 * http://localhost:8070/academicyears/1/academicterms/
				 * 2c9180866ff5c74b01700ab38a410111/districts/8a0080826522e0f601652ef26af8001b/
				 * schools/8a008082648d961401648dadbf0f0003/schoolstaffs/
				 * 2c91808377b47c7a0177b9674a350416/days/Tuesday/timetablelessons
				 */

				SystemResponseDTO<List<TimeTableLessonDTO>> responseDto = client.target(API_LINK).path("academicyears")
						.path(academicYearId).path("academicterms").path(academicTermId).path("districts")
						.path(districtId).path("schools").path(schoolId).path("schoolstaffs").path(schoolStaffId)
						.path("days").path(lessonDay).path("timetablelessons").request(MediaType.APPLICATION_JSON)
						.headers(headers).get(new GenericType<SystemResponseDTO<List<TimeTableLessonDTO>>>() {
						});

				if (responseDto.getData() != null)
					list = responseDto.getData();

				if (list != null) {

					System.out.println("list: " + list.size());
				}

				feedback.setResponse(responseDto.isStatus());
				feedback.setMessage(responseDto.getMessage());

				client.close();
				return new RequestResult(feedback, list, null);
			}

			///////////////////////////////////

			else if (action.getRequest().equalsIgnoreCase(SystemUserProfileRequestConstant.SAVE_SYSTEM_USER_PROFILE)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				List<SystemUserProfileDTO> profileDTOs = new ArrayList<SystemUserProfileDTO>();

				SystemUserProfileDTO dto = (SystemUserProfileDTO) action.getRequestBody()
						.get(SystemUserProfileRequestConstant.DATA);

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<SystemFeedbackDTO> postResponseDTO = client.target(API_LINK)
						.path("SystemUserProfiles").request(MediaType.APPLICATION_JSON).headers(headers)
						.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (postResponseDTO != null) {

					feedback = postResponseDTO.getData();

					SystemResponseDTO<List<SystemUserProfileDTO>> responseDto = client.target(API_LINK)
							.path("SystemUserProfiles").request(MediaType.APPLICATION_JSON).headers(headers)
							.get(new GenericType<SystemResponseDTO<List<SystemUserProfileDTO>>>() {
							});

					if (responseDto != null) {
						profileDTOs = responseDto.getData();
						feedback.setResponse(responseDto.isStatus());
						feedback.setMessage(responseDto.getMessage());
					}

				}

				client.close();
				return new RequestResult(feedback, profileDTOs, null);

			} else if (action.getRequest().equalsIgnoreCase(SystemUserProfileRequestConstant.GET_SYSTEM_USER_PROFILES)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				List<SystemUserProfileDTO> systemUserProfileDTOs = new ArrayList<SystemUserProfileDTO>();

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<List<SystemUserProfileDTO>> responseDto = client.target(API_LINK)
						.path("SystemUserProfiles").request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<List<SystemUserProfileDTO>>>() {
						});

				if (responseDto != null) {
					systemUserProfileDTOs = responseDto.getData();
					feedback.setResponse(responseDto.isStatus());
					feedback.setMessage(responseDto.getMessage());
					System.out.println("RESPONSE " + responseDto);
					System.out.println("RES DATA " + responseDto.getData());
				}

				client.close();
				return new RequestResult(feedback, systemUserProfileDTOs, null);

			} else if (action.getRequest().equalsIgnoreCase(SystemUserProfileRequestConstant.GET_SYSTEM_USER_PROFILE)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				SystemUserProfileDTO systemUserProfileDTO = new SystemUserProfileDTO();

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<SystemUserProfileDTO> responseDto = client.target(API_LINK).path("Logged")
						.path("SystemUserProfile").request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<SystemUserProfileDTO>>() {
						});

				if (responseDto != null) {
					systemUserProfileDTO = responseDto.getData();
					feedback.setResponse(responseDto.isStatus());
					feedback.setMessage(responseDto.getMessage());
					System.out.println("RESPONSE " + responseDto);
					System.out.println("RES DATA " + responseDto.getData());
				}

				client.close();
				return new RequestResult(feedback, systemUserProfileDTO);

			} else if (action.getRequest()
					.equalsIgnoreCase(SystemUserProfileRequestConstant.UPDATE_LOGGED_SYSTEM_USER_PROFILE_DETAILS)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				SystemUserProfileDTO dto = (SystemUserProfileDTO) action.getRequestBody()
						.get(SystemUserProfileRequestConstant.DATA);

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<SystemFeedbackDTO> responseDto = client.target(API_LINK).path("Logged")
						.path("SystemUserProfile").request(MediaType.APPLICATION_JSON).headers(headers)
						.put(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (responseDto != null) {
					feedback = responseDto.getData();
					System.out.println("RESPONSE " + responseDto);
					System.out.println("RES DATA " + responseDto.getData());
				}

				client.close();
				return new RequestResult(feedback);

			}

			else if (action.getRequest().equalsIgnoreCase(RequestConstant.SAVE_STAFF_DAILY_TIMETABLE_LESSONS)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				StaffDailyTimeTableDTO dto = (StaffDailyTimeTableDTO) action.getRequestBody()
						.get(RequestConstant.SAVE_STAFF_DAILY_TIMETABLE_LESSONS);

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<SystemFeedbackDTO> postResponseDTO = client.target(API_LINK)
						.path("staffDailyTimeTables").request(MediaType.APPLICATION_JSON).headers(headers)
						.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (postResponseDTO != null) {
					feedback = postResponseDTO.getData();
				}

				client.close();
				return new RequestResult(feedback);

			} else if (action.getRequest()
					.equalsIgnoreCase(RequestConstant.GET_STAFF_DAILY_TIMETABLE_ACADEMIC_YEAR_TERM_DISTRICT_SCHOOL_DATE)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<StaffDailyTimeTableDTO> list = new ArrayList<StaffDailyTimeTableDTO>();

				String academicYearId = (String) action.getRequestBody().get(RequestDelimeters.ACADEMIC_YEAR_ID);
				String academicTermId = (String) action.getRequestBody().get(RequestDelimeters.ACADEMIC_TERM_ID);
				String districtId = (String) action.getRequestBody().get(RequestDelimeters.DISTRICT_ID);
				String schoolId = (String) action.getRequestBody().get(RequestDelimeters.SCHOOL_ID);
				String lessonDate = (String) action.getRequestBody().get(RequestDelimeters.LESSON_DATE);

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<List<StaffDailyTimeTableDTO>> responseDto = client.target(API_LINK)
						.path("academicyears").path(academicYearId).path("academicterms").path(academicTermId)
						.path("districts").path(districtId).path("schools").path(schoolId).path("staffDailyTimeTables")
						.queryParam("date", lessonDate).request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<List<StaffDailyTimeTableDTO>>>() {
						});

				if (responseDto.getData() != null)
					list = responseDto.getData();

				System.out.println("RESPONSE " + responseDto);
				System.out.println("RES DATA " + responseDto.getData());
				feedback.setResponse(responseDto.isStatus());
				feedback.setMessage(responseDto.getMessage());

				client.close();
				return new RequestResult(feedback, list, null);
			}

			else if (action.getRequest().equalsIgnoreCase(
					RequestConstant.GET_STAFF_DAILY_TIMETABLES_BY_SYSTEM_USER_PROFILE_SCHOOLS_ACADEMIC_YEAR_TERM_DISTRICT_SCHOOL_DATE)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<StaffDailyTimeTableDTO> list = new ArrayList<StaffDailyTimeTableDTO>();

				String academicYearId = (String) action.getRequestBody().get(RequestDelimeters.ACADEMIC_YEAR_ID);
				String academicTermId = (String) action.getRequestBody().get(RequestDelimeters.ACADEMIC_TERM_ID);
				String districtId = (String) action.getRequestBody().get(RequestDelimeters.DISTRICT_ID);
				String schoolId = (String) action.getRequestBody().get(RequestDelimeters.SCHOOL_ID);
				String lessonDate = (String) action.getRequestBody().get(RequestDelimeters.LESSON_DATE);

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<List<StaffDailyTimeTableDTO>> responseDto = client.target(API_LINK)
						.path("academicyears").path(academicYearId).path("academicterms").path(academicTermId)
						.path("districts").path(districtId).path("schools").path(schoolId).path("staffDailyTimeTables")
						.queryParam("date", lessonDate).request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<List<StaffDailyTimeTableDTO>>>() {
						});

				if (responseDto.getData() != null)
					list = responseDto.getData();

				System.out.println("RESPONSE " + responseDto);
				System.out.println("RES DATA " + responseDto.getData());
				feedback.setResponse(responseDto.isStatus());
				feedback.setMessage(responseDto.getMessage());

				client.close();
				return new RequestResult(feedback, list, null);
			}

			else if (action.getRequest()
					.equalsIgnoreCase(RequestConstant.GET_STAFF_DAILY_TIMETABLE_LESSONS_BY_SCHOOL_STAFF_DATE)

					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<StaffDailyTimeTableLessonDTO> list = new ArrayList<StaffDailyTimeTableLessonDTO>();

				String schoolId = (String) action.getRequestBody().get(RequestDelimeters.SCHOOL_ID);
				String schoolStaffId = (String) action.getRequestBody().get(RequestDelimeters.SCHOOL_STAFF_ID);
				String lessonDate = (String) action.getRequestBody().get(RequestDelimeters.LESSON_DATE);

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				/*
				 * /schools/{school}/schoolstaffs/{staff}/staffdailyattendances
				 */

				SystemResponseDTO<List<StaffDailyTimeTableLessonDTO>> responseDto = client.target(API_LINK)
						.path("schools").path(schoolId).path("schoolstaffs").path(schoolStaffId)
						.path("staffDailyTimeTableLessons").queryParam("date", lessonDate)
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<List<StaffDailyTimeTableLessonDTO>>>() {
						});

				if (responseDto.getData() != null)
					list = responseDto.getData();

				System.out.println("RESPONSE " + responseDto);
				System.out.println("RES DATA " + responseDto.getData());
				feedback.setResponse(responseDto.isStatus());
				feedback.setMessage(responseDto.getMessage());

				client.close();
				return new RequestResult(feedback, list, null);
			}

			else if (action.getRequest().equalsIgnoreCase(
					RequestConstant.GET_STAFF_DAILY_TIMETABLE_LESSONS_BY_SYSTEM_USER_PROFILE_SCHOOLS_DAILY_TIMETABLE_SCHOOL_STAFF_DATE)

					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<StaffDailyTimeTableLessonDTO> list = new ArrayList<StaffDailyTimeTableLessonDTO>();

				String staffDailyTimetableId = (String) action.getRequestBody()
						.get(RequestDelimeters.STAFF_DAILY_TIMETALE_ID);
				String schoolStaffId = (String) action.getRequestBody().get(RequestDelimeters.SCHOOL_STAFF_ID);
				String lessonDate = (String) action.getRequestBody().get(RequestDelimeters.LESSON_DATE);

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				/*
				 * /staffdailytimetables/{dailytimetable}/schoolstaffs/{staff}/
				 * staffDailyTimeTableLessons
				 */
				SystemResponseDTO<List<StaffDailyTimeTableLessonDTO>> responseDto = client.target(API_LINK)
						.path("SystemUserProfile").path("StaffDailyTimetables").path(staffDailyTimetableId)
						.path("SchoolStaffs").path(schoolStaffId).path("StaffDailyTimeTableLessons")
						.queryParam("date", lessonDate)

						.request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<List<StaffDailyTimeTableLessonDTO>>>() {
						});

				if (responseDto.getData() != null)
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

				StaffDailyAttendanceSupervisionDTO dto = (StaffDailyAttendanceSupervisionDTO) action.getRequestBody()
						.get(RequestConstant.SAVE_STAFF_DAILY_TASK_SUPERVISIONS);

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<SystemFeedbackDTO> postResponseDTO = client.target(API_LINK)
						.path("staffDailyAttendanceSupervisions").request(MediaType.APPLICATION_JSON).headers(headers)
						.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (postResponseDTO != null) {
					feedback = postResponseDTO.getData();
				}

				client.close();
				return new RequestResult(feedback);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.GET_STAFF_DAILY_SUPERVISIONS_IN_SCHOOL_DATE)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<StaffDailyAttendanceSupervisionDTO> list = new ArrayList<StaffDailyAttendanceSupervisionDTO>();

				String schoolId = (String) action.getRequestBody().get(RequestDelimeters.SCHOOL_ID);
				String supervisionDate = (String) action.getRequestBody().get(RequestDelimeters.SUPERVISION_DATE);

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);
				// http://localhost:8070/staffDailyAttendanceSupervisions/schools/8a008082648d961401648dadbf0f0003?supervisionDate=18/03/2021
				SystemResponseDTO<List<StaffDailyAttendanceSupervisionDTO>> responseDto = client.target(API_LINK)

						.path("staffDailyAttendanceSupervisions").path("schools").path(schoolId)
						.queryParam("supervisionDate", supervisionDate).request(MediaType.APPLICATION_JSON)
						.headers(headers)
//=======
//						.path("SystemUserProfile").path("StaffDailyAttendanceSupervisions")
//						.path("Schools")
//						.path(schoolId)
//						.queryParam("supervisionDate", supervisionDate)
//						.request(MediaType.APPLICATION_JSON).headers(headers)
//>>>>>>> test
						.get(new GenericType<SystemResponseDTO<List<StaffDailyAttendanceSupervisionDTO>>>() {
						});

				if (responseDto.getData() != null)
					list = responseDto.getData();

				System.out.println("SUP RESPONSE " + responseDto);
				System.out.println("RES DATA " + responseDto.getData());
				feedback.setResponse(responseDto.isStatus());
				feedback.setMessage(responseDto.getMessage());

				client.close();
				return new RequestResult(feedback, list, null);

			}

			else if (action.getRequest().equalsIgnoreCase(
					RequestConstant.GET_STAFF_DAILY_SUPERVISIONS_BY_SYSTEM_USER_PROFILE_SCHOOLS_SCHOOL_DATE)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<StaffDailyAttendanceSupervisionDTO> list = new ArrayList<StaffDailyAttendanceSupervisionDTO>();

				String schoolId = (String) action.getRequestBody().get(RequestDelimeters.SCHOOL_ID);
				String supervisionDate = (String) action.getRequestBody().get(RequestDelimeters.SUPERVISION_DATE);

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);
				// http://localhost:8070/staffDailyAttendanceSupervisions/schools/8a008082648d961401648dadbf0f0003?supervisionDate=18/03/2021
				SystemResponseDTO<List<StaffDailyAttendanceSupervisionDTO>> responseDto = client.target(API_LINK)
						.path("SystemUserProfile").path("StaffDailyAttendanceSupervisions").path("Schools")
						.path(schoolId).queryParam("supervisionDate", supervisionDate)
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<List<StaffDailyAttendanceSupervisionDTO>>>() {
						});

				if (responseDto.getData() != null)
					list = responseDto.getData();

				System.out.println("SUP RESPONSE " + responseDto);
				System.out.println("RES DATA " + responseDto.getData());
				feedback.setResponse(responseDto.isStatus());
				feedback.setMessage(responseDto.getMessage());

				client.close();
				return new RequestResult(feedback, list, null);
			}

			else if (action.getRequest().equalsIgnoreCase(
					RequestConstant.GET_STAFF_DAILY_ATTENDANCE_TASK_SUPERVISIONS_BY_SYSTEM_USER_PROFILE_SCHOOLS_STAFF_DATE_DAILY_ATTENDANCE_SUPERVISION)

					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<StaffDailyAttendanceTaskSupervisionDTO> list = new ArrayList<StaffDailyAttendanceTaskSupervisionDTO>();

				String schoolStaffId = (String) action.getRequestBody().get(RequestDelimeters.SCHOOL_STAFF_ID);
				String supervisionDate = (String) action.getRequestBody().get(RequestDelimeters.SUPERVISION_DATE);
				String supervisionId = (String) action.getRequestBody()
						.get(RequestDelimeters.STAFF_DAILY_ATTENDANCE_SUPERVISION_ID);

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);
				// http://localhost:8070/staffDailyAttendanceSupervisions/{}/schoolstaffs/8a008082648d961401648dadbf0f0003/staffDailyAttendancetaskSupervisions?supervisionDate=18/03/2021
				SystemResponseDTO<List<StaffDailyAttendanceTaskSupervisionDTO>> responseDto = client.target(API_LINK)
						.path("staffDailyAttendanceSupervisions").path(supervisionId).path("schoolstaffs")
						.path(schoolStaffId).path("staffDailyAttendancetaskSupervisions")
						.queryParam("supervisionDate", supervisionDate).request(MediaType.APPLICATION_JSON)
						.headers(headers)
						.get(new GenericType<SystemResponseDTO<List<StaffDailyAttendanceTaskSupervisionDTO>>>() {
						});

				if (responseDto.getData() != null)
					list = responseDto.getData();

				System.out.println("SUP RESPONSE " + responseDto);
				System.out.println("RES DATA " + responseDto.getData());
				feedback.setResponse(responseDto.isStatus());
				feedback.setMessage(responseDto.getMessage());

				client.close();
				return new RequestResult(feedback, list, null);
			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.SAVE_TIME_ATTENDANCE_SUPERVISION)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				TimeAttendanceSupervisionDTO dto = (TimeAttendanceSupervisionDTO) action.getRequestBody()
						.get(RequestConstant.SAVE_TIME_ATTENDANCE_SUPERVISION);

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<SystemFeedbackDTO> postResponseDTO = client.target(API_LINK)
						.path("timeAttendanceSupervisions").request(MediaType.APPLICATION_JSON).headers(headers)
						.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (postResponseDTO != null) {
					feedback = postResponseDTO.getData();
				}

				System.out.println("Handler Time  " + dto);

				client.close();
				return new RequestResult(feedback);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.GET_DEFAULT_ENROLLMENT_DASHBOARD)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				DashboardSummaryDTO dashboardSummaryDTO = new DashboardSummaryDTO();

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<DashboardSummaryDTO> postResponseDTO = client.target(API_LINK)
						.path("dashboardSummary").path("default").path("enrollment").request(MediaType.APPLICATION_JSON)
						.headers(headers).get(new GenericType<SystemResponseDTO<DashboardSummaryDTO>>() {
						});

				if (postResponseDTO != null) {
					dashboardSummaryDTO = postResponseDTO.getData();
				}

				client.close();

				return new RequestResult(dashboardSummaryDTO);

			}

			else if (action.getRequest().equalsIgnoreCase(RequestConstant.GET_DEFAULT_ATTENDANCE_DASHBOARD)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				AttendanceDashboardSummaryDTO dashboardSummaryDTO = new AttendanceDashboardSummaryDTO();

				Client client = ClientBuilder.newClient();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<AttendanceDashboardSummaryDTO> postResponseDTO = client.target(API_LINK)
						.path("dashboardSummary").path("default").path("attendance").request(MediaType.APPLICATION_JSON)
						.headers(headers).get(new GenericType<SystemResponseDTO<AttendanceDashboardSummaryDTO>>() {
						});

				if (postResponseDTO != null) {

					dashboardSummaryDTO = postResponseDTO.getData();

					System.out.println("DashboardSummaryDTO: " + dashboardSummaryDTO.getTeacher());

				} else {
					System.out.println("DashboardSummaryDTO: is null");
				}

				client.close();

				return new RequestResult(dashboardSummaryDTO);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.SAVE_SYSTEM_USER_PROFILE_SCHOOLS_PROFILE)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				Client client = ClientBuilder.newClient();
				String profileId = (String) action.getRequestBody().get(RequestDelimeters.SYSTEM_USER_PROFILE_ID);

				List<SchoolDTO> dtos = (List<SchoolDTO>) action.getRequestBody().get(RequestConstant.DATA);
				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<SystemFeedbackDTO> postResponseDTO = client.target(API_LINK)
						.path("SystemUserProfileSchools").path(profileId).path("Schools")
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.post(Entity.entity(dtos, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (postResponseDTO != null) {
					feedback = postResponseDTO.getData();
					feedback.setMessage(postResponseDTO.getMessage());
					feedback.setResponse(postResponseDTO.isStatus());
				}

				client.close();
				return new RequestResult(feedback);

			}
			// else if
			// (action.getRequest().equalsIgnoreCase(RequestConstant.DELETE_SYSTEM_USER_PROFILE_SCHOOLS)
			// && action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
			//
			// SystemFeedbackDTO feedback = new SystemFeedbackDTO();
			//
			// List<SchoolDTO> dtos = (List<SchoolDTO>)
			// action.getRequestBody().get(RequestConstant.DATA);
			// System.out.println("Handler profileSchool "+dtos);
			// Client client = ClientBuilder.newClient();
			//
			// String token = (String)
			// action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
			// MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String,
			// Object>();
			// headers.add(HttpHeaders.AUTHORIZATION, token);
			//
			// SystemResponseDTO<SystemFeedbackDTO> deleteResponseDTO =
			// client.target(API_LINK)
			// .path("SystemUserProfileSchools")
			// .request(MediaType.APPLICATION_JSON).headers(headers)
			// .put(Entity.entity(dtos , MediaType.APPLICATION_JSON),new
			// GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {});
			//
			// if (deleteResponseDTO != null) {
			// feedback = deleteResponseDTO.getData();
			// }
			//
			//
			//
			// client.close();
			// return new RequestResult(feedback);
			//
			// }
			//
			else if (action.getRequest().equalsIgnoreCase(RequestConstant.DELETE_SYSTEM_USER_PROFILE_SCHOOLS_PROFILE)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				List<SchoolDTO> dtos = (List<SchoolDTO>) action.getRequestBody().get(RequestConstant.DATA);
				System.out.println("Handler profileSchool  " + dtos);
				Client client = ClientBuilder.newClient();
				String profileId = (String) action.getRequestBody().get(RequestDelimeters.SYSTEM_USER_PROFILE_ID);
				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<SystemFeedbackDTO> deleteResponseDTO = client.target(API_LINK)
						.path("SystemUserProfileSchools").path(profileId).request(MediaType.APPLICATION_JSON)
						.headers(headers).put(Entity.entity(dtos, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (deleteResponseDTO != null) {
					feedback = deleteResponseDTO.getData();
					feedback.setMessage(deleteResponseDTO.getMessage());
					feedback.setResponse(deleteResponseDTO.isStatus());
				}

				client.close();
				return new RequestResult(feedback);

			}

			//
			// =======
			// SystemResponseDTO<SystemFeedbackDTO> postResponseDTO =
			// client.target(API_LINK)
			// .path("SystemUserProfileSchools")
			// .path(profileId)
			// .path("Schools")
			// .request(MediaType.APPLICATION_JSON).headers(headers)
			// .post(Entity.entity(dto, MediaType.APPLICATION_JSON),
			// new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
			// });
			//
			//
			// if (postResponseDTO != null) {
			// feedback = postResponseDTO.getData();
			// }
			//
			//
			//
			// client.close();
			// return new RequestResult(feedback);
			//
			// }
			// else if
			// (action.getRequest().equalsIgnoreCase(RequestConstant.DELETE_SYSTEM_USER_PROFILE_SCHOOLS)
			// && action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
			//
			// SystemFeedbackDTO feedback = new SystemFeedbackDTO();
			//
			// List<SchoolDTO> dtos = (List<SchoolDTO>)
			// action.getRequestBody().get(RequestConstant.DATA);
			// System.out.println("Handler profileSchool "+dtos);
			// Client client = ClientBuilder.newClient();
			//
			// String token = (String)
			// action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
			// MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String,
			// Object>();
			// headers.add(HttpHeaders.AUTHORIZATION, token);
			//
			// SystemResponseDTO<SystemFeedbackDTO> deleteResponseDTO =
			// client.target(API_LINK)
			// .path("SystemUserProfileSchools")
			// .request(MediaType.APPLICATION_JSON).headers(headers)
			// .put(Entity.entity(dtos , MediaType.APPLICATION_JSON),new
			// GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {});
			//
			// if (deleteResponseDTO != null) {
			// feedback = deleteResponseDTO.getData();
			// }
			//
			//
			//
			// client.close();
			// return new RequestResult(feedback);
			//
			// }
			//
			// else if
			// (action.getRequest().equalsIgnoreCase(RequestConstant.DELETE_SYSTEM_USER_PROFILE_SCHOOLS_PROFILE)
			// && action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
			//
			// SystemFeedbackDTO feedback = new SystemFeedbackDTO();
			//
			// List<SchoolDTO> dtos = (List<SchoolDTO>)
			// action.getRequestBody().get(RequestConstant.DATA);
			// System.out.println("Handler profileSchool "+dtos);
			// Client client = ClientBuilder.newClient();
			// String profileId = (String)
			// action.getRequestBody().get(RequestDelimeters.SYSTEM_USER_PROFILE_ID);
			// String token = (String)
			// action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
			// MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String,
			// Object>();
			// headers.add(HttpHeaders.AUTHORIZATION, token);
			//
			// SystemResponseDTO<SystemFeedbackDTO> deleteResponseDTO =
			// client.target(API_LINK)
			// .path("SystemUserProfileSchools").path(profileId)
			// .request(MediaType.APPLICATION_JSON).headers(headers)
			// .put(Entity.entity(dtos , MediaType.APPLICATION_JSON),new
			// GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {});
			//
			// if (deleteResponseDTO != null) {
			// feedback = deleteResponseDTO.getData();
			// }
			//
			//
			//
			// client.close();
			// return new RequestResult(feedback);
			//
			// }
			//
			// >>>>>>> test
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
			}

			else if (action.getRequest().equalsIgnoreCase(RequestConstant.MIGRATE_DATA_ATTENDACE)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);

				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				DataMigrationUtility.getInstance().migrateTeacherAttendance(headers);

				feedback.setResponse(true);
				feedback.setMessage("Data Migration successful");

				return new RequestResult(feedback);
			}
			if (action.getRequest().equalsIgnoreCase(RequestConstant.MIGRATE_DATA_TIMETABLES)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);

				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				DataMigrationUtility.getInstance().migrateTimeTables(headers);

				feedback.setResponse(true);
				feedback.setMessage("Data Migration successful");

				return new RequestResult(feedback);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.MIGRATE_DATA_SUBJECTS)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				System.out.println("MIGRATE_DATA_SUBJECTS");

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);

				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				DataMigrationUtility.getInstance().migrateSubjects(headers);

				feedback.setResponse(true);
				feedback.setMessage("Data Migration successful");

				return new RequestResult(feedback);

			}

			else if (action.getRequest().equalsIgnoreCase(SystemMenuRequestConstant.SAVE_SYSTEM_MENU)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				System.out.println("Entry SAVE_SystemMENU: ");

				@SuppressWarnings("unchecked")
				List<SystemMenuDTO> systemMenuDTOs = (List<SystemMenuDTO>) action.getRequestBody()
						.get(SystemMenuRequestConstant.DATA);

				if (systemMenuDTOs != null) {

					Client client = ClientBuilder.newClient();

					String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
					MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
					headers.add(HttpHeaders.AUTHORIZATION, token);

					SystemResponseDTO<SystemFeedbackDTO> responseDTO = client.target(API_LINK).path("SystemMenus")
							.request(MediaType.APPLICATION_JSON).headers(headers)
							.post(Entity.entity(systemMenuDTOs, MediaType.APPLICATION_JSON),
									new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
									});

					if (responseDTO != null) {
						// SystemResponseDTO<List<SystemMenuDTO>> responseData =
						// client.target(API_LINK).path("active")
						// .path("systemMenu").request(MediaType.APPLICATION_JSON).headers(headers)
						// .get(new GenericType<SystemResponseDTO<List<SystemMenuDTO>>>() {
						// });
						//
						// if (responseData != null) {
						// responselist = responseData.getData();
					}

					feedback = responseDTO.getData();
					client.close();
				}

				return new RequestResult(feedback);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.DELETE_SystemMENU)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				System.out.println("Entry DELETE_SystemMENU: ");

				@SuppressWarnings("unchecked")
				List<SystemMenuDTO> list = (List<SystemMenuDTO>) action.getRequestBody()
						.get(RequestConstant.DELETE_SystemMENU);

				List<SystemMenuDTO> responselist = new ArrayList<SystemMenuDTO>();

				if (list != null) {

					String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
					MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
					headers.add(HttpHeaders.AUTHORIZATION, token);

					Client client = ClientBuilder.newClient();

					SystemResponseDTO<SystemFeedbackDTO> responseDTO = client.target(API_LINK).path("delete")
							.path("systemMenu").request(MediaType.APPLICATION_JSON).headers(headers)
							.post(Entity.entity(list, MediaType.APPLICATION_JSON),
									new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
									});

					if (responseDTO != null) {

						feedback = responseDTO.getData();

						SystemResponseDTO<List<SystemMenuDTO>> responseData = client.target(API_LINK).path("active")
								.path("systemMenu").request(MediaType.APPLICATION_JSON).headers(headers)
								.get(new GenericType<SystemResponseDTO<List<SystemMenuDTO>>>() {
								});

						if (responseData != null) {
							responselist = responseData.getData();
						}

					}

					client.close();
				}

				return new RequestResult(feedback, responselist, null);

			} else if (action.getRequest().equalsIgnoreCase(SystemMenuRequestConstant.GET_SYSTEM_MENUS)
					&& action.getRequestBody().get(RequestConstant.LOGIN_TOKEN) != null) {
				System.out.println("GETTING SYSTEM mENUS ");

				List<SystemMenuDTO> systemMenuDTOs = new ArrayList<SystemMenuDTO>();

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				Client client = ClientBuilder.newClient();

				SystemResponseDTO<List<SystemMenuDTO>> responseDTO = client.target(API_LINK).path("SystemMenus")
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<List<SystemMenuDTO>>>() {
						});

				if (responseDTO != null) {
					systemMenuDTOs = responseDTO.getData();
					// System.out.println("MENUS "+systemMenuDTOs);
					feedback.setResponse(responseDTO.isStatus());
					feedback.setMessage(responseDTO.getMessage());
				}

				client.close();

				return new RequestResult(feedback, systemMenuDTOs, null);

			} else if (action.getRequest()
					.equalsIgnoreCase(SystemUserGroupSystemMenuRequestConstant.SAVE_USER_GROUP_SYSTEM_MENU)) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				System.out.println("Entry SAVE_USER_GROUP_SystemMENU: ");
				//
				// SystemUserGroupDTO userGroup = (SystemUserGroupDTO) action.getRequestBody()
				// .get(RequestConstant.GET_USER_GROUP);

				@SuppressWarnings("unchecked")
				List<SystemUserGroupSystemMenuDTO> dtos = (List<SystemUserGroupSystemMenuDTO>) action.getRequestBody()
						.get(SystemUserGroupSystemMenuRequestConstant.DATA);

				if (dtos != null) {

					String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
					MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
					headers.add(HttpHeaders.AUTHORIZATION, token);

					Client client = ClientBuilder.newClient();

					SystemResponseDTO<SystemFeedbackDTO> responseDTO = client.target(API_LINK)
							.path("SystemUserGroupSystemMenus").request(MediaType.APPLICATION_JSON).headers(headers)
							.post(Entity.entity(dtos, MediaType.APPLICATION_JSON),
									new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
									});

					if (responseDTO != null) {

						feedback = responseDTO.getData();
						//
						// SystemResponseDTO<List<SystemUserGroupSystemMenuDTO>> responseData =
						// client.target(API_LINK)
						// .path("active").path("systemUserGroupSystemMenu").path("userGroup")
						// .path(userGroup.getId()).request(MediaType.APPLICATION_JSON).headers(headers)
						// .get(new GenericType<SystemResponseDTO<List<SystemUserGroupSystemMenuDTO>>>()
						// {
						// });
						//
						// if (responseData != null) {
						// responselist = responseData.getData();
						// }
					}

					client.close();
				}

				return new RequestResult(feedback);

			} else if (action.getRequest().equalsIgnoreCase(
					SystemUserGroupSystemMenuRequestConstant.GET_SELECTED_UNSELECTED_USER_GROUP_SYSTEM_MENU)) {

				System.out.println("GET_USER_GROUP_SystemMENU");

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				List<SystemUserGroupSystemMenuDTO> list = new ArrayList<SystemUserGroupSystemMenuDTO>();
				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				String userGroupId = (String) action.getRequestBody().get(RequestDelimeters.SYSTEM_USER_GROUP_ID);

				System.out.println("GET_USER_GROUP_SystemMENU: " + userGroupId);

				Client client = ClientBuilder.newClient();

				SystemResponseDTO<List<SystemUserGroupSystemMenuDTO>> data = client.target(API_LINK)
						.path("SystemUserGroupSystemMenus").path("SystemUserGroup").path(userGroupId)
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<List<SystemUserGroupSystemMenuDTO>>>() {
						});

				if (data != null) {
					list = data.getData();
					feedback.setMessage(data.getMessage());
					feedback.setResponse(data.isStatus());
				}

				client.close();

				return new RequestResult(feedback, list, null);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.GET_LOGED_IN_USER_SYSTEM_MENUS)) {

				System.out.println("GET_LOGED_IN_USER_SYSTEM_MENUS");

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				
				System.out.println("GET_LOGED_IN_USER_SYSTEM_MENUS TOKEN " + token);
				
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				List<SystemMenuDTO> list = new ArrayList<SystemMenuDTO>();

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				Client client = ClientBuilder.newClient();

				SystemResponseDTO<List<SystemMenuDTO>> data = client.target(API_LINK).path("LoggedUserSystemMenus")
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<List<SystemMenuDTO>>>() {
						});

				if (data != null) {
					if (data.getData() != null) {
						list = data.getData();
						feedback.setMessage(data.getMessage());
						feedback.setResponse(data.isStatus());
						System.out.println("GET_LOGEDIN_USER_SystemMENU list " + list.size());
					} else {
						System.out.println("GET_LOGEDIN_USER_SystemMENU getData is null");
					}

				}else {
					System.out.println("GET_LOGEDIN_USER_SystemMENU list DATA IS NULL");
				}

				client.close();

				return new RequestResult(feedback, list, null);

			}

			else if (action.getRequest().equalsIgnoreCase(RequestConstant.GET_LOGEDIN_USER_SystemMENU)) {

				System.out.println("GET_LOGEDIN_USER_SystemMENU");

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				List<SystemUserGroupSystemMenuDTO> list = new ArrayList<SystemUserGroupSystemMenuDTO>();

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				Client client = ClientBuilder.newClient();

				SystemResponseDTO<List<SystemUserGroupSystemMenuDTO>> data = client.target(API_LINK).path("logedinUser")
						.path("systemUserGroupSystemMenus").request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<List<SystemUserGroupSystemMenuDTO>>>() {
						});

				if (data != null) {
					if (data.getData() != null) {
						list = data.getData();
						System.out.println("GET_LOGEDIN_USER_SystemMENU list " + list.size());
					} else {
						System.out.println("GET_LOGEDIN_USER_SystemMENU getData is null");
					}

				}

				client.close();

				return new RequestResult(feedback, list, null);

			} else if (action.getRequest().equalsIgnoreCase(SystemUserGroupRequestConstant.SAVE_SYSTEM_USER_GROUP)) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				SystemUserGroupDTO dto = (SystemUserGroupDTO) action.getRequestBody()
						.get(SystemUserGroupRequestConstant.DATA);

				// List<SystemUserGroupDTO> list = new ArrayList<SystemUserGroupDTO>();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				Client client = ClientBuilder.newClient();

				SystemResponseDTO<SystemFeedbackDTO> postResponseDTO = client.target(API_LINK).path("SystemUserGroups")
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (postResponseDTO != null) {
					feedback = postResponseDTO.getData();
					feedback.setMessage(postResponseDTO.getMessage());
					feedback.setResponse(postResponseDTO.isStatus());
				}

				// SystemResponseDTO<List<SystemUserGroupDTO>> getResponseDTO =
				// client.target(API_LINK)
				// .path("systemusergroups").request(MediaType.APPLICATION_JSON).headers(headers)
				// .get(new GenericType<SystemResponseDTO<List<SystemUserGroupDTO>>>() {
				// });

				// list = getResponseDTO.getData();
				// System.out.println("GET DTO " + getResponseDTO);

				client.close();
				return new RequestResult(feedback);

				////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			}

			else if (action.getRequest().equalsIgnoreCase(SystemUserGroupRequestConstant.UPDATE_SYSTEM_USER_GROUP)) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				SystemUserGroupDTO dto = (SystemUserGroupDTO) action.getRequestBody()
						.get(SystemUserGroupRequestConstant.DATA);
				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);

				Client client = ClientBuilder.newClient();

				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<SystemFeedbackDTO> updateResponseDTO = client.target(API_LINK)
						.path("SystemUserGroups").path(dto.getId()).request(MediaType.APPLICATION_JSON).headers(headers)
						.put(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (updateResponseDTO != null) {
					feedback = updateResponseDTO.getData();
					// SystemResponseDTO<List<SystemUserGroupDTO>> getResponseDTO =
					// client.target(API_LINK)
					// .path("SystemUserGroups").request(MediaType.APPLICATION_JSON).headers(headers)
					// .get(new GenericType<SystemResponseDTO<List<SystemUserGroupDTO>>>() {
					// });
					//
					// list = getResponseDTO.getData();
					// System.out.println("GET DTO " + getResponseDTO);
				}

				client.close();
				return new RequestResult(feedback);

				/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			} else if (action.getRequest().equalsIgnoreCase(SystemUserGroupRequestConstant.DELETE_SYSTEM_USER_GROUP)) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				SystemUserGroupDTO dto = (SystemUserGroupDTO) action.getRequestBody()
						.get(SystemUserGroupRequestConstant.DATA);

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);

				// List<SystemUserGroupDTO> list = new ArrayList<SystemUserGroupDTO>();

				Client client = ClientBuilder.newClient();

				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<SystemFeedbackDTO> deleteResponseDTO = client.target(API_LINK)
						.path("SystemUserGroups").path(dto.getId()).request(MediaType.APPLICATION_JSON).headers(headers)
						.delete(new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
						});

				if (deleteResponseDTO != null) {
					feedback = deleteResponseDTO.getData();
					//
					// SystemResponseDTO<List<SystemUserGroupDTO>> getResponseDTO =
					// client.target(API_LINK)
					// .path("SystemUserGroups").request(MediaType.APPLICATION_JSON).headers(headers)
					// .get(new GenericType<SystemResponseDTO<List<SystemUserGroupDTO>>>() {
					// });
					//
					// list = getResponseDTO.getData();
					// System.out.println("GET DTO " + getResponseDTO);
				}

				client.close();
				return new RequestResult(feedback);

				/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			} else if (action.getRequest().equalsIgnoreCase(SystemUserGroupRequestConstant.GET_SYSTEM_USER_GROUPS)) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<SystemUserGroupDTO> list = new ArrayList<SystemUserGroupDTO>();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				Client client = ClientBuilder.newClient();

				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<List<SystemUserGroupDTO>> responseDto = client.target(API_LINK)
						.path("SystemUserGroups").request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<List<SystemUserGroupDTO>>>() {
						});

				if (responseDto != null) {
					list = responseDto.getData();
					feedback.setResponse(true);
					feedback.setMessage(responseDto.getMessage());
				}

				// System.out.println("RESPONSE " + responseDto);
				// System.out.println("RES DATA " + responseDto.getData());

				client.close();
				return new RequestResult(feedback, list, null);

				/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			}

			else if (action.getRequest().equalsIgnoreCase(SystemUserGroupRequestConstant.LOGGEDIN_SYSTEM_USER_GROUPS)) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				// List<SystemUserGroupDTO> list = new ArrayList<SystemUserGroupDTO>();
				SystemUserGroupDTO systemUserGroupDTO = new SystemUserGroupDTO();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				Client client = ClientBuilder.newClient();

				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<SystemUserGroupDTO> responseDto = client.target(API_LINK)
						.path("LoggedSystemUserGroup").request(MediaType.APPLICATION_JSON).headers(headers)
						.get(new GenericType<SystemResponseDTO<SystemUserGroupDTO>>() {
						});

				if (responseDto != null) {
					feedback.setMessage(responseDto.getMessage());
					feedback.setResponse(responseDto.isStatus());
					systemUserGroupDTO = responseDto.getData();
					feedback.setId(systemUserGroupDTO.getId());
				}

				client.close();
				return new RequestResult(feedback, systemUserGroupDTO);

				/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			} else if (action.getRequest().equalsIgnoreCase(ReportsRequestConstant.TeacherClockInSummaryREPORT)) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<TeacherClockInSummaryDTO> list = new ArrayList<TeacherClockInSummaryDTO>();

				FilterDTO dto = (FilterDTO) action.getRequestBody().get(ReportsRequestConstant.DATA);

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				Client client = ClientBuilder.newClient();

				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<List<TeacherClockInSummaryDTO>> responseDto = client.target(API_LINK).path("Reports")
						.path("TeacherClockInSummary").request(MediaType.APPLICATION_JSON).headers(headers)
						.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<List<TeacherClockInSummaryDTO>>>() {
								});

				if (responseDto != null) {
					list = responseDto.getData();
					feedback.setResponse(responseDto.isStatus());
					feedback.setMessage(responseDto.getMessage());
					System.out.println("REPORT1  " + responseDto);
					System.out.println("RESport1  " + responseDto.getData());
				} else {
					System.out.println("NULL RESPONSE");
				}

				client.close();
				return new RequestResult(feedback, list, null);

				/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			} else if (action.getRequest()
					.equalsIgnoreCase(ReportsRequestConstant.SchoolEndOfWeekTimeAttendanceReport)) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<SchoolEndOfWeekTimeAttendanceDTO> list = new ArrayList<SchoolEndOfWeekTimeAttendanceDTO>();

				FilterDTO dto = (FilterDTO) action.getRequestBody().get(ReportsRequestConstant.DATA);

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				Client client = ClientBuilder.newClient();

				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<List<SchoolEndOfWeekTimeAttendanceDTO>> responseDto = client.target(API_LINK)
						.path("Reports").path("SchoolEndOfWeekTimeAttendance").request(MediaType.APPLICATION_JSON)
						.headers(headers).post(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<List<SchoolEndOfWeekTimeAttendanceDTO>>>() {
								});

				if (responseDto != null) {
					list = responseDto.getData();
					feedback.setResponse(true);
					feedback.setMessage(responseDto.getMessage());
					System.out.println("REPORT1  " + responseDto);
					System.out.println("RESport1  " + responseDto.getData());
				}

				client.close();
				return new RequestResult(feedback, list, null);

				/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			} else if (action.getRequest()
					.equalsIgnoreCase(ReportsRequestConstant.SchoolEndOfMonthTimeAttendanceREPORT)) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<SchoolEndOfMonthTimeAttendanceDTO> list = new ArrayList<SchoolEndOfMonthTimeAttendanceDTO>();

				FilterDTO dto = (FilterDTO) action.getRequestBody().get(ReportsRequestConstant.DATA);

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				Client client = ClientBuilder.newClient();

				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<List<SchoolEndOfMonthTimeAttendanceDTO>> responseDto = client.target(API_LINK)
						.path("Reports").path("SchoolEndOfMonthTimeAttendance").request(MediaType.APPLICATION_JSON)
						.headers(headers).post(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<List<SchoolEndOfMonthTimeAttendanceDTO>>>() {
								});

				if (responseDto != null) {
					list = responseDto.getData();
					feedback.setResponse(true);
					feedback.setMessage(responseDto.getMessage());
					System.out.println("REPORT1  " + responseDto);
					System.out.println("RESport1  " + responseDto.getData());
				}

				client.close();
				return new RequestResult(feedback, list, null);

				/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			}

			else if (action.getRequest().equalsIgnoreCase(ReportsRequestConstant.SchoolEndOfTermTimeAttendanceReport)) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<SchoolEndOfTermTimeAttendanceDTO> list = new ArrayList<SchoolEndOfTermTimeAttendanceDTO>();

				FilterDTO dto = (FilterDTO) action.getRequestBody().get(ReportsRequestConstant.DATA);

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				Client client = ClientBuilder.newClient();

				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<List<SchoolEndOfTermTimeAttendanceDTO>> responseDto = client.target(API_LINK)
						.path("Reports").path("SchoolEndOfTermTimeAttendance")
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.post(Entity.entity(dto , MediaType.APPLICATION_JSON) ,
								new GenericType<SystemResponseDTO<List<SchoolEndOfTermTimeAttendanceDTO>>>() {
						});

				if (responseDto != null) {
					list = responseDto.getData();
					feedback.setResponse(true);
					feedback.setMessage(responseDto.getMessage());
					System.out.println("REPORT1  " + responseDto);
					System.out.println("RESport1  " + responseDto.getData());
				}


				client.close();
				return new RequestResult(feedback, list, null);

				/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			}

			else if (action.getRequest().equalsIgnoreCase(ReportsRequestConstant.SchoolTimeOnTaskSummary)) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();
				List<SchoolTimeOnTaskSummaryDTO> list = new ArrayList<SchoolTimeOnTaskSummaryDTO>();

				FilterDTO dto = (FilterDTO) action.getRequestBody().get(ReportsRequestConstant.DATA);

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);
				Client client = ClientBuilder.newClient();

				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<List<SchoolTimeOnTaskSummaryDTO>> responseDto = client.target(API_LINK)
						.path("Reports").path("SchoolTimeOnTaskSummary")
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.post(Entity.entity(dto , MediaType.APPLICATION_JSON) ,
								new GenericType<SystemResponseDTO<List<SchoolTimeOnTaskSummaryDTO>>>() {
						});

				if (responseDto != null) {
					list = responseDto.getData();
					feedback.setResponse(true);
					feedback.setMessage(responseDto.getMessage());
					System.out.println("REPORT1  " + responseDto);
					System.out.println("RESport1  " + responseDto.getData());
				}


				client.close();
				return new RequestResult(feedback, list, null);

				/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			}

			else if (action.getRequest().equalsIgnoreCase(RequestConstant.DistrictEndOfWeekTimeAttendance)) {

				Client client = ClientBuilder.newClient();

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);

				DistrictReportFilterDTO filterDTO = (DistrictReportFilterDTO) action.getRequestBody()
						.get(RequestConstant.DistrictEndOfWeekTimeAttendance);

				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				List<DistrictEndOfWeekTimeAttendanceDTO> list = new ArrayList<DistrictEndOfWeekTimeAttendanceDTO>();

				SystemResponseDTO<List<DistrictEndOfWeekTimeAttendanceDTO>> responseDto = client.target(API_LINK)
						.path("districtEndOfWeekTimeAttendance").request(MediaType.APPLICATION_JSON).headers(headers)
						.post(Entity.entity(filterDTO, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<List<DistrictEndOfWeekTimeAttendanceDTO>>>() {
								});

				if (responseDto != null) {
					feedback.setMessage(responseDto.getMessage());
					feedback.setResponse(responseDto.isStatus());

					list = responseDto.getData();

				}

				client.close();
				return new RequestResult(feedback, list, null);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.DistrictEndOfMonthTimeAttendance)) {

				Client client = ClientBuilder.newClient();

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);

				DistrictReportFilterDTO filterDTO = (DistrictReportFilterDTO) action.getRequestBody()
						.get(RequestConstant.DistrictEndOfMonthTimeAttendance);

				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				List<DistrictEndOfMonthTimeAttendanceDTO> list = new ArrayList<DistrictEndOfMonthTimeAttendanceDTO>();

				SystemResponseDTO<List<DistrictEndOfMonthTimeAttendanceDTO>> responseDto = client.target(API_LINK)
						.path("districtEndOfMonthTimeAttendance").request(MediaType.APPLICATION_JSON).headers(headers)
						.post(Entity.entity(filterDTO, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<List<DistrictEndOfMonthTimeAttendanceDTO>>>() {
								});

				if (responseDto != null) {
					feedback.setMessage(responseDto.getMessage());
					feedback.setResponse(responseDto.isStatus());

					list = responseDto.getData();

				}

				client.close();
				return new RequestResult(feedback, list, null);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.DistrictEndOfTermTimeAttendance)) {

				Client client = ClientBuilder.newClient();

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);

				DistrictReportFilterDTO filterDTO = (DistrictReportFilterDTO) action.getRequestBody()
						.get(RequestConstant.DistrictEndOfTermTimeAttendance);

				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				List<DistrictEndOfTermTimeAttendanceDTO> list = new ArrayList<DistrictEndOfTermTimeAttendanceDTO>();

				SystemResponseDTO<List<DistrictEndOfTermTimeAttendanceDTO>> responseDto = client.target(API_LINK)
						.path("districtEndOfTermTimeAttendance").request(MediaType.APPLICATION_JSON).headers(headers)
						.post(Entity.entity(filterDTO, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<List<DistrictEndOfTermTimeAttendanceDTO>>>() {
								});

				if (responseDto != null) {
					feedback.setMessage(responseDto.getMessage());
					feedback.setResponse(responseDto.isStatus());

					list = responseDto.getData();

				}

				client.close();
				return new RequestResult(feedback, list, null);

			}

			else if (action.getRequest().equalsIgnoreCase(RequestConstant.DistrictEndOfWeekTimeAttendanceReport)) {

				System.out.print("DistrictEndOfWeekTimeAttendanceReport");

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				Client client = ClientBuilder.newClient();
				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);

				DistrictReportFilterDTO filterDTO = (DistrictReportFilterDTO) action.getRequestBody()
						.get(RequestConstant.DistrictEndOfWeekTimeAttendanceReport);

				String refId = "kfredrick";

				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				DailyDistrictReport responseDto = client.target(API_LINK).path("districtEndOfWeekTimeAttendanceReport")
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.post(Entity.entity(filterDTO, MediaType.APPLICATION_JSON), DailyDistrictReport.class);

				if (responseDto != null) {

					Gson gson = new Gson();
					String jsonString = gson.toJson(responseDto);

					ReportPreviewRequestDTO dto = new ReportPreviewRequestDTO();
					dto.setInputFileName("District  End of  Week Report -Time attendance");
					dto.setJsonString(jsonString);
					dto.setOutputFileName("District  End of  Week Report -Time attendance");
					dto.setRefId(refId);

					SystemFeedbackDTO systemFeedback = client.target(REPORT_GEN_API).path("preview")
							.request(MediaType.APPLICATION_JSON).headers(headers)
							.post(Entity.entity(dto, MediaType.APPLICATION_JSON), SystemFeedbackDTO.class);

					if (systemFeedback != null) {
						feedback.setResponse(systemFeedback.isResponse());
						feedback.setMessage(systemFeedback.getMessage());
					}

				}

				client.close();

				return new RequestResult(feedback);

			}

			else if (action.getRequest().equalsIgnoreCase(RequestConstant.DistrictEndOfMonthTimeAttendanceReport)) {

				System.out.print("DistrictEndOfMonthTimeAttendanceReport");

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				Client client = ClientBuilder.newClient();
				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);

				DistrictReportFilterDTO filterDTO = (DistrictReportFilterDTO) action.getRequestBody()
						.get(RequestConstant.DistrictEndOfMonthTimeAttendanceReport);

				String refId = "kfredrick";

				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				DistrictWeeklyReport responseDto = client.target(API_LINK)
						.path("districtEndOfMonthTimeAttendanceReport").request(MediaType.APPLICATION_JSON)
						.headers(headers)
						.post(Entity.entity(filterDTO, MediaType.APPLICATION_JSON), DistrictWeeklyReport.class);

				if (responseDto != null) {

					Gson gson = new Gson();
					String jsonString = gson.toJson(responseDto);

					ReportPreviewRequestDTO dto = new ReportPreviewRequestDTO();
					dto.setInputFileName("District  End of  Month Report -003-Time attendance");
					dto.setJsonString(jsonString);
					dto.setOutputFileName("District  End of  Month Report -003-Time attendance");
					dto.setRefId(refId);

					SystemFeedbackDTO systemFeedback = client.target(REPORT_GEN_API).path("preview")
							.request(MediaType.APPLICATION_JSON).headers(headers)
							.post(Entity.entity(dto, MediaType.APPLICATION_JSON), SystemFeedbackDTO.class);

					if (systemFeedback != null) {
						feedback.setResponse(systemFeedback.isResponse());
						feedback.setMessage(systemFeedback.getMessage());
					}

				}

				client.close();

				return new RequestResult(feedback);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.DistrictEndOfTermTimeAttendanceReport)) {

				System.out.print("DistrictEndOfTermTimeAttendanceReport");

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				Client client = ClientBuilder.newClient();
				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);

				DistrictReportFilterDTO filterDTO = (DistrictReportFilterDTO) action.getRequestBody()
						.get(RequestConstant.DistrictEndOfTermTimeAttendanceReport);

				String refId = "kfredrick";

				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				DistrictTermReport responseDto = client.target(API_LINK).path("districtEndOfTermTimeAttendanceReport")
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.post(Entity.entity(filterDTO, MediaType.APPLICATION_JSON), DistrictTermReport.class);

				if (responseDto != null) {

					Gson gson = new Gson();
					String jsonString = gson.toJson(responseDto);

					ReportPreviewRequestDTO dto = new ReportPreviewRequestDTO();
					dto.setInputFileName("District  End of  Term Report -003-Time attendance");
					dto.setJsonString(jsonString);
					dto.setOutputFileName("District  End of  Term Report -003-Time attendance");
					dto.setRefId(refId);

					SystemFeedbackDTO systemFeedback = client.target(REPORT_GEN_API).path("preview")
							.request(MediaType.APPLICATION_JSON).headers(headers)
							.post(Entity.entity(dto, MediaType.APPLICATION_JSON), SystemFeedbackDTO.class);

					if (systemFeedback != null) {
						feedback.setResponse(systemFeedback.isResponse());
						feedback.setMessage(systemFeedback.getMessage());
					}

				}

				client.close();

				return new RequestResult(feedback);

			}

			else if (action.getRequest().equalsIgnoreCase(RequestConstant.NationalEndOfWeekTimeAttendance)) {

				Client client = ClientBuilder.newClient();

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);

				NationalReportFilterDTO filterDTO = (NationalReportFilterDTO) action.getRequestBody()
						.get(RequestConstant.NationalEndOfWeekTimeAttendance);

				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				List<NationalEndOfWeekTimeAttendanceDTO> list = new ArrayList<NationalEndOfWeekTimeAttendanceDTO>();

				SystemResponseDTO<List<NationalEndOfWeekTimeAttendanceDTO>> responseDto = client.target(API_LINK)
						.path("nationalEndOfWeekTimeAttendance").request(MediaType.APPLICATION_JSON).headers(headers)
						.post(Entity.entity(filterDTO, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<List<NationalEndOfWeekTimeAttendanceDTO>>>() {
								});

				if (responseDto != null) {
					feedback.setMessage(responseDto.getMessage());
					feedback.setResponse(responseDto.isStatus());

					list = responseDto.getData();

				}

				client.close();
				return new RequestResult(feedback, list, null);

			}

			else if (action.getRequest().equalsIgnoreCase(RequestConstant.NationalEndOfWeekTimeAttendanceReport)) {

				Client client = ClientBuilder.newClient();

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);

				String refId = "kfredrick";

				NationalReportFilterDTO filterDTO = (NationalReportFilterDTO) action.getRequestBody()
						.get(RequestConstant.NationalEndOfWeekTimeAttendanceReport);

				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				NationalWeeklyReport responseDto = client.target(API_LINK).path("nationalEndOfWeekTimeAttendanceReport")
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.post(Entity.entity(filterDTO, MediaType.APPLICATION_JSON), NationalWeeklyReport.class);

				if (responseDto != null) {

					Gson gson = new Gson();
					String jsonString = gson.toJson(responseDto);

					ReportPreviewRequestDTO dto = new ReportPreviewRequestDTO();
					dto.setInputFileName("National End of  Week Report -Time attendance");
					dto.setJsonString(jsonString);
					dto.setOutputFileName("National End of  Week Report -Time attendance");
					dto.setRefId(refId);

					SystemFeedbackDTO systemFeedback = client.target(REPORT_GEN_API).path("preview")
							.request(MediaType.APPLICATION_JSON).headers(headers)
							.post(Entity.entity(dto, MediaType.APPLICATION_JSON), SystemFeedbackDTO.class);

					if (systemFeedback != null) {
						feedback.setResponse(systemFeedback.isResponse());
						feedback.setMessage(systemFeedback.getMessage());
					}

				}

				client.close();
				return new RequestResult(feedback);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.NationalEndOfMonthTimeAttendance)) {

				Client client = ClientBuilder.newClient();

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);

				NationalReportFilterDTO filterDTO = (NationalReportFilterDTO) action.getRequestBody()
						.get(RequestConstant.NationalEndOfMonthTimeAttendance);

				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				List<NationalEndOfMonthTimeAttendanceDTO> list = new ArrayList<NationalEndOfMonthTimeAttendanceDTO>();

				SystemResponseDTO<List<NationalEndOfMonthTimeAttendanceDTO>> responseDto = client.target(API_LINK)
						.path("nationalEndOfMonthTimeAttendance").request(MediaType.APPLICATION_JSON).headers(headers)
						.post(Entity.entity(filterDTO, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<List<NationalEndOfMonthTimeAttendanceDTO>>>() {
								});

				if (responseDto != null) {
					feedback.setMessage(responseDto.getMessage());
					feedback.setResponse(responseDto.isStatus());

					list = responseDto.getData();

				}

				client.close();
				return new RequestResult(feedback, list, null);

			}

			else if (action.getRequest().equalsIgnoreCase(RequestConstant.NationalEndOfMonthTimeAttendanceReport)) {

				Client client = ClientBuilder.newClient();

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);

				String refId = "kfredrick";

				NationalReportFilterDTO filterDTO = (NationalReportFilterDTO) action.getRequestBody()
						.get(RequestConstant.NationalEndOfMonthTimeAttendanceReport);

				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				NationalMonthlyReport responseDto = client.target(API_LINK)
						.path("nationalEndOfMonthTimeAttendanceReport").request(MediaType.APPLICATION_JSON)
						.headers(headers)
						.post(Entity.entity(filterDTO, MediaType.APPLICATION_JSON), NationalMonthlyReport.class);

				if (responseDto != null) {

					Gson gson = new Gson();
					String jsonString = gson.toJson(responseDto);

					ReportPreviewRequestDTO dto = new ReportPreviewRequestDTO();
					dto.setInputFileName("National End of  Month Report -003-Time attendance");
					dto.setJsonString(jsonString);
					dto.setOutputFileName("National End of  Month Report -003-Time attendance");
					dto.setRefId(refId);

					SystemFeedbackDTO systemFeedback = client.target(REPORT_GEN_API).path("preview")
							.request(MediaType.APPLICATION_JSON).headers(headers)
							.post(Entity.entity(dto, MediaType.APPLICATION_JSON), SystemFeedbackDTO.class);

					if (systemFeedback != null) {
						feedback.setResponse(systemFeedback.isResponse());
						feedback.setMessage(systemFeedback.getMessage());
					}

				}

				client.close();
				return new RequestResult(feedback);

			}

			else if (action.getRequest().equalsIgnoreCase(RequestConstant.NationalEndOfTermTimeAttendance)) {

				Client client = ClientBuilder.newClient();

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);

				NationalReportFilterDTO filterDTO = (NationalReportFilterDTO) action.getRequestBody()
						.get(RequestConstant.NationalEndOfTermTimeAttendance);

				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				List<NationalEndOfTermTimeAttendanceDTO> list = new ArrayList<NationalEndOfTermTimeAttendanceDTO>();

				SystemResponseDTO<List<NationalEndOfTermTimeAttendanceDTO>> responseDto = client.target(API_LINK)
						.path("nationalEndOfTermTimeAttendance").request(MediaType.APPLICATION_JSON).headers(headers)
						.post(Entity.entity(filterDTO, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<List<NationalEndOfTermTimeAttendanceDTO>>>() {
								});

				if (responseDto != null) {
					feedback.setMessage(responseDto.getMessage());
					feedback.setResponse(responseDto.isStatus());

					list = responseDto.getData();

				}

				client.close();
				return new RequestResult(feedback, list, null);

			}else if (action.getRequest().equalsIgnoreCase(SmsRequest.SMS_STAFF)) {

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				Client client = ClientBuilder.newClient();
				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);

				SmsSchoolStaffDTO dto = (SmsSchoolStaffDTO) action.getRequestBody()
						.get(SmsRequest.DATA);

				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				SystemResponseDTO<SystemFeedbackDTO> postResponseDTO = client.target(API_LINK).path("SmsSchoolStaff")
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.post(Entity.entity(dto, MediaType.APPLICATION_JSON),
								new GenericType<SystemResponseDTO<SystemFeedbackDTO>>() {
								});

				if (postResponseDTO != null) {
					feedback.setMessage(postResponseDTO.getMessage());
					feedback.setResponse(postResponseDTO.isStatus());
					//feedback.setId(id);
				}

				client.close();

				return new RequestResult(feedback);

			} else if (action.getRequest().equalsIgnoreCase(RequestConstant.NationalEndOfTermTimeAttendanceReport)) {

				Client client = ClientBuilder.newClient();

				SystemFeedbackDTO feedback = new SystemFeedbackDTO();

				String token = (String) action.getRequestBody().get(RequestConstant.LOGIN_TOKEN);

				String refId = "kfredrick";

				NationalReportFilterDTO filterDTO = (NationalReportFilterDTO) action.getRequestBody()
						.get(RequestConstant.NationalEndOfTermTimeAttendanceReport);

				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
				headers.add(HttpHeaders.AUTHORIZATION, token);

				NationalTermlyReport responseDto = client.target(API_LINK).path("nationalEndOfTermTimeAttendanceReport")
						.request(MediaType.APPLICATION_JSON).headers(headers)
						.post(Entity.entity(filterDTO, MediaType.APPLICATION_JSON), NationalTermlyReport.class);

				if (responseDto != null) {

					Gson gson = new Gson();
					String jsonString = gson.toJson(responseDto);

					ReportPreviewRequestDTO dto = new ReportPreviewRequestDTO();
					dto.setInputFileName("National  End of  Term Report -003-Time attendance");
					dto.setJsonString(jsonString);
					dto.setOutputFileName("National  End of  Term Report -003-Time attendance");
					dto.setRefId(refId);

					SystemFeedbackDTO systemFeedback = client.target(REPORT_GEN_API).path("preview")
							.request(MediaType.APPLICATION_JSON).headers(headers)
							.post(Entity.entity(dto, MediaType.APPLICATION_JSON), SystemFeedbackDTO.class);

					if (systemFeedback != null) {
						feedback.setResponse(systemFeedback.isResponse());
						feedback.setMessage(systemFeedback.getMessage());
					}

				}

				client.close();
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
			SystemErrorDTO error = new SystemErrorDTO();
			error.setErrorCode(8082);
			error.setMessage(exception.getMessage()
					+ "\n There is a problem accessing the processing data processing engine. Please contact Systems Admin.");

			return new RequestResult(error);

		} catch (Exception exception) {
			exception.printStackTrace();
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
