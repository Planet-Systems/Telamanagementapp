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

import com.fasterxml.jackson.databind.ObjectMapper;
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
import com.planetsystems.tela.dto.SystemMenuDTO;
import com.planetsystems.tela.dto.SystemUserGroupDTO;
import com.planetsystems.tela.dto.SystemUserGroupSystemMenuDTO;
import com.planetsystems.tela.dto.SystemUserProfileDTO;
import com.planetsystems.tela.dto.TimeAttendanceSupervisionDTO;
import com.planetsystems.tela.dto.TimeTableDTO;
import com.planetsystems.tela.dto.TimeTableLessonDTO;
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
import com.planetsystems.tela.dto.response.SystemErrorDTO;
import com.planetsystems.tela.dto.response.SystemFeedbackDTO;
import com.planetsystems.tela.dto.response.SystemResponseDTO;
import com.planetsystems.tela.dto.response.TokenFeedbackDTO;
import com.planetsystems.tela.managementapp.shared.RequestAction;
import com.planetsystems.tela.managementapp.shared.RequestConstant;
import com.planetsystems.tela.managementapp.shared.RequestDelimeters;
import com.planetsystems.tela.managementapp.shared.RequestResult;
import com.planetsystems.tela.managementapp.shared.requestcommands.ReportsRequestConstant;
import com.planetsystems.tela.managementapp.shared.requestcommands.SmsRequest;
import com.planetsystems.tela.managementapp.shared.requestcommands.StaffEnrollmentRequest;
import com.planetsystems.tela.managementapp.shared.requestcommands.SystemMenuCommands;
import com.planetsystems.tela.managementapp.shared.requestcommands.SystemUserGroupRequestCommand;
import com.planetsystems.tela.managementapp.shared.requestcommands.SystemUserGroupSystemMenuCommand;
import com.planetsystems.tela.managementapp.shared.requestcommands.SystemUserProfileRequestConstant;

public class RequestActionHandler implements ActionHandler<RequestAction, RequestResult> {

	final String API_LINK = APIGateWay.getInstance().getApLink();
	final String REPORT_GEN_API = APIGateWay.getInstance().getReportGeneratorLink();
	
	ObjectMapper mapper = new ObjectMapper();

	@Inject
	public RequestActionHandler() {
		super();
	}

	@Override
	public RequestResult execute(RequestAction action, ExecutionContext context) throws ActionException {

		try {
 if (action.getRequest()
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
 
		
 else if (action.getRequest().equalsIgnoreCase(RequestConstant.GET_DEFAULT_ENROLLMENT_DASHBOARD)
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
  else if (action.getRequest().equalsIgnoreCase(ReportsRequestConstant.TeacherClockInSummaryREPORT)) {

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
