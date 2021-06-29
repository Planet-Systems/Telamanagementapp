package com.planetsystems.tela.managementapp.server;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.planetsystems.tela.dto.AcademicTermDTO;
import com.planetsystems.tela.dto.AcademicYearDTO;
import com.planetsystems.tela.dto.ClockInDTO;
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
import com.planetsystems.tela.dto.AppDTO.StaffAttendanceImportDTO;
import com.planetsystems.tela.dto.response.SystemFeedbackDTO;

public class DataMigrationUtility {

	private InputStream input = null;
	private Properties prop = new Properties();

	private static DataMigrationUtility instance = new DataMigrationUtility();

	private DataMigrationUtility() {

	}

	public static DataMigrationUtility getInstance() {
		return instance;
	}

	public String getApLink() {

		String apiLink = null;
		try {

			String filename = "properties.properties";

			input = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
			if (input == null) {
				System.out.println("Sorry, unable to find " + filename);
				return null;
			}

			// load a properties file from class path, inside static method

			prop.load(input);

			apiLink = prop.getProperty("api");

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return apiLink;
	}

	public String getDataApILink() {

		String apiLink = null;
		try {

			String filename = "properties.properties";

			input = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
			if (input == null) {
				System.out.println("Sorry, unable to find " + filename);
				return null;
			}

			// load a properties file from class path, inside static method

			prop.load(input);

			apiLink = prop.getProperty("dataMigrationAPI");

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return apiLink;
	}

	public Client getClientBuilder() {
		return ClientBuilder.newClient();
	}

	public void migrateData(MultivaluedMap<String, Object> headers) {
		try {

			Client client = ClientBuilder.newClient();

			List<AcademicYearDTO> academicYears = client.target(getDataApILink()).path("academicYears")
					.request(MediaType.APPLICATION_JSON).headers(headers).get(new GenericType<List<AcademicYearDTO>>() {
					});

			SystemFeedbackDTO feedback1 = client.target(getApLink()).path("import").path("academicYears")
					.request(MediaType.APPLICATION_JSON).headers(headers)
					.post(Entity.entity(academicYears, MediaType.APPLICATION_JSON), SystemFeedbackDTO.class);

			List<AcademicTermDTO> academicTerms = client.target(getDataApILink()).path("academicTerms")
					.request(MediaType.APPLICATION_JSON).headers(headers).get(new GenericType<List<AcademicTermDTO>>() {
					});

			SystemFeedbackDTO feedback2 = client.target(getApLink()).path("import").path("academicTerms")
					.request(MediaType.APPLICATION_JSON).headers(headers)
					.post(Entity.entity(academicTerms, MediaType.APPLICATION_JSON), SystemFeedbackDTO.class);

			List<RegionDto> regions = client.target(getDataApILink()).path("regions")
					.request(MediaType.APPLICATION_JSON).headers(headers).get(new GenericType<List<RegionDto>>() {
					});

			SystemFeedbackDTO feedback3 = client.target(getApLink()).path("import").path("regions")
					.request(MediaType.APPLICATION_JSON).headers(headers)
					.post(Entity.entity(regions, MediaType.APPLICATION_JSON), SystemFeedbackDTO.class);

			List<DistrictDTO> districts = client.target(getDataApILink()).path("districts")
					.request(MediaType.APPLICATION_JSON).headers(headers).get(new GenericType<List<DistrictDTO>>() {
					});

			SystemFeedbackDTO feedback4 = client.target(getApLink()).path("import").path("districts")
					.request(MediaType.APPLICATION_JSON).headers(headers)
					.post(Entity.entity(districts, MediaType.APPLICATION_JSON), SystemFeedbackDTO.class);

			List<SchoolCategoryDTO> schoolCategories = client.target(getDataApILink()).path("schoolCategories")
					.request(MediaType.APPLICATION_JSON).headers(headers)
					.get(new GenericType<List<SchoolCategoryDTO>>() {
					});

			SystemFeedbackDTO feedback5 = client.target(getApLink()).path("import").path("schoolCategories")
					.request(MediaType.APPLICATION_JSON).headers(headers)
					.post(Entity.entity(schoolCategories, MediaType.APPLICATION_JSON), SystemFeedbackDTO.class);

			List<SchoolDTO> schools = client.target(getDataApILink()).path("schools")
					.request(MediaType.APPLICATION_JSON).headers(headers).get(new GenericType<List<SchoolDTO>>() {
					});

			SystemFeedbackDTO feedback6 = client.target(getApLink()).path("import").path("schools")
					.request(MediaType.APPLICATION_JSON).headers(headers)
					.post(Entity.entity(schools, MediaType.APPLICATION_JSON), SystemFeedbackDTO.class);

			List<SchoolClassDTO> schoolClasses = client.target(getDataApILink()).path("schoolClasses")
					.request(MediaType.APPLICATION_JSON).headers(headers).get(new GenericType<List<SchoolClassDTO>>() {
					});

			SystemFeedbackDTO feedback7 = client.target(getApLink()).path("import").path("schoolClasses")
					.request(MediaType.APPLICATION_JSON).headers(headers)
					.post(Entity.entity(schoolClasses, MediaType.APPLICATION_JSON), SystemFeedbackDTO.class);

			List<SubjectCategoryDTO> subjetCategories = client.target(getDataApILink()).path("subjetCategories")
					.request(MediaType.APPLICATION_JSON).headers(headers)
					.get(new GenericType<List<SubjectCategoryDTO>>() {
					});

			SystemFeedbackDTO feedback8 = client.target(getApLink()).path("import").path("subjetCategories")
					.request(MediaType.APPLICATION_JSON).headers(headers)
					.post(Entity.entity(subjetCategories, MediaType.APPLICATION_JSON), SystemFeedbackDTO.class);

			List<SubjectDTO> subjects = client.target(getDataApILink()).path("subjects")
					.request(MediaType.APPLICATION_JSON).headers(headers).get(new GenericType<List<SubjectDTO>>() {
					});

			SystemFeedbackDTO feedback9 = client.target(getApLink()).path("import").path("subjects")
					.request(MediaType.APPLICATION_JSON).headers(headers)
					.post(Entity.entity(subjects, MediaType.APPLICATION_JSON), SystemFeedbackDTO.class);

			List<SchoolStaffDTO> schoolStaff = client.target(getDataApILink()).path("schoolStaff")
					.request(MediaType.APPLICATION_JSON).headers(headers).get(new GenericType<List<SchoolStaffDTO>>() {
					});

			SystemFeedbackDTO feedback10 = client.target(getApLink()).path("import").path("schoolStaff")
					.request(MediaType.APPLICATION_JSON).headers(headers)
					.post(Entity.entity(schoolStaff, MediaType.APPLICATION_JSON), SystemFeedbackDTO.class);

			List<StaffEnrollmentDto> staffEnrollment = client.target(getDataApILink()).path("staffEnrollment")
					.request(MediaType.APPLICATION_JSON).headers(headers)
					.get(new GenericType<List<StaffEnrollmentDto>>() {
					});

			SystemFeedbackDTO feedback11 = client.target(getApLink()).path("import").path("staffEnrollment")
					.request(MediaType.APPLICATION_JSON).headers(headers)
					.post(Entity.entity(staffEnrollment, MediaType.APPLICATION_JSON), SystemFeedbackDTO.class);

			List<LearnerEnrollmentDTO> learnerEnrollment = client.target(getDataApILink()).path("learnerEnrollment")
					.request(MediaType.APPLICATION_JSON).headers(headers)
					.get(new GenericType<List<LearnerEnrollmentDTO>>() {
					});

			SystemFeedbackDTO feedback12 = client.target(getApLink()).path("import").path("learnerEnrollment")
					.request(MediaType.APPLICATION_JSON).headers(headers)
					.post(Entity.entity(learnerEnrollment, MediaType.APPLICATION_JSON), SystemFeedbackDTO.class);

			List<LearnerAttendanceDTO> learnerAttendance = client.target(getDataApILink()).path("learnerAttendance")
					.request(MediaType.APPLICATION_JSON).headers(headers)
					.get(new GenericType<List<LearnerAttendanceDTO>>() {
					});

			SystemFeedbackDTO feedback13 = client.target(getApLink()).path("import").path("learnerAttendance")
					.request(MediaType.APPLICATION_JSON).headers(headers)
					.post(Entity.entity(learnerAttendance, MediaType.APPLICATION_JSON), SystemFeedbackDTO.class);

			System.out.println("feedback1:: " + feedback1.getMessage());
			System.out.println("feedback2:: " + feedback2.getMessage());
			System.out.println("feedback3:: " + feedback3.getMessage());

			System.out.println("feedback4:: " + feedback4.getMessage());
			System.out.println("feedback5:: " + feedback5.getMessage());
			System.out.println("feedback6:: " + feedback6.getMessage());
			System.out.println("feedback7:: " + feedback7.getMessage());
			System.out.println("feedback8:: " + feedback8.getMessage());
			System.out.println("feedback9:: " + feedback9.getMessage());
			System.out.println("feedback10:: " + feedback10.getMessage());
			System.out.println("feedback11:: " + feedback11.getMessage());
			System.out.println("feedback12:: " + feedback12.getMessage());
			System.out.println("feedback13:: " + feedback13.getMessage());

			client.close();

		} catch (

		Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public void migrateTeacherAttendance(MultivaluedMap<String, Object> headers) {
		try {

			Client client = ClientBuilder.newClient();

			List<AcademicTermDTO> academicTerms = client.target(getDataApILink()).path("academicTerms")
					.request(MediaType.APPLICATION_JSON).headers(headers).get(new GenericType<List<AcademicTermDTO>>() {
					});

			if (academicTerms != null) {

				for (AcademicTermDTO dto : academicTerms) {

					System.out.println("AcademicTermDTO:: " + dto.getTerm());

					List<StaffAttendanceImportDTO> clockins = client.target(getDataApILink()).path("clockins").path(dto.getId())
							.request(MediaType.APPLICATION_JSON).headers(headers)
							.get(new GenericType<List<StaffAttendanceImportDTO>>() {
							});

					if (clockins != null) {
						if (!clockins.isEmpty()) {

							System.out.println("clockins AcademicTermDTO:: " + clockins.size());

							SystemFeedbackDTO feedback14 = client.target(getApLink()).path("import").path("clockins")
									.request(MediaType.APPLICATION_JSON).headers(headers)
									.post(Entity.entity(clockins, MediaType.APPLICATION_JSON), SystemFeedbackDTO.class);

							System.out.println("feedback14:: " + feedback14.getMessage());
						}
					}

				}
			}

			client.close();

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public void migrateTimeTables(MultivaluedMap<String, Object> headers) {
		try {

			Client client = ClientBuilder.newClient();

			List<AcademicTermDTO> academicTerms = client.target(getDataApILink()).path("timetables")
					.request(MediaType.APPLICATION_JSON).headers(headers).get(new GenericType<List<AcademicTermDTO>>() {
					});

			if (academicTerms != null) {

				for (AcademicTermDTO dto : academicTerms) {

					List<ClockInDTO> clockins = client.target(getDataApILink()).path("timetables").path(dto.getId())
							.request(MediaType.APPLICATION_JSON).headers(headers)
							.get(new GenericType<List<ClockInDTO>>() {
							});

					if (clockins != null) {
						if (!clockins.isEmpty()) {

							SystemFeedbackDTO feedback14 = client.target(getApLink()).path("import").path("timetables")
									.request(MediaType.APPLICATION_JSON).headers(headers)
									.post(Entity.entity(clockins, MediaType.APPLICATION_JSON), SystemFeedbackDTO.class);

							System.out.println("feedback14:: " + feedback14.getMessage());
						}
					}

				}
			}

			client.close();

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public void migrateSubjects(MultivaluedMap<String, Object> headers) {

		Client client = ClientBuilder.newClient();

		List<SubjectCategoryDTO> subjetCategories = client.target(getDataApILink()).path("subjetCategories")
				.request(MediaType.APPLICATION_JSON).headers(headers).get(new GenericType<List<SubjectCategoryDTO>>() {
				});

		SystemFeedbackDTO feedback8 = client.target(getApLink()).path("import").path("subjetCategories")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.post(Entity.entity(subjetCategories, MediaType.APPLICATION_JSON), SystemFeedbackDTO.class);

		List<SubjectDTO> subjects = client.target(getDataApILink()).path("subjects").request(MediaType.APPLICATION_JSON)
				.headers(headers).get(new GenericType<List<SubjectDTO>>() {
				});

		SystemFeedbackDTO feedback9 = client.target(getApLink()).path("import").path("subjects")
				.request(MediaType.APPLICATION_JSON).headers(headers)
				.post(Entity.entity(subjects, MediaType.APPLICATION_JSON), SystemFeedbackDTO.class);

		System.out.println("feedback12:: " + feedback8.getMessage());
		System.out.println("feedback13:: " + feedback9.getMessage());

		client.close();
	}

}
