package com.planetsystems.tela.managementapp.shared;

import java.util.List;

import com.gwtplatform.dispatch.rpc.shared.Result;
import com.planetsystems.tela.dto.AcademicTermDTO;
import com.planetsystems.tela.dto.AcademicYearDTO;
import com.planetsystems.tela.dto.ClockInDTO;
import com.planetsystems.tela.dto.ClockOutDTO;
import com.planetsystems.tela.dto.DistrictDTO;
import com.planetsystems.tela.dto.FilterDTO;
import com.planetsystems.tela.dto.LearnerAttendanceDTO;
import com.planetsystems.tela.dto.LearnerEnrollmentDTO;
import com.planetsystems.tela.dto.RegionDto;
import com.planetsystems.tela.dto.SMCSupervisionDTO;
import com.planetsystems.tela.dto.SchoolCategoryDTO;
import com.planetsystems.tela.dto.SchoolClassDTO;
import com.planetsystems.tela.dto.SchoolDTO;
import com.planetsystems.tela.dto.SchoolStaffDTO;
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
import com.planetsystems.tela.dto.SystemUserDTO;
import com.planetsystems.tela.dto.SystemUserGroupDTO;
import com.planetsystems.tela.dto.SystemUserGroupSystemMenuDTO;
import com.planetsystems.tela.dto.SystemUserProfileDTO;
import com.planetsystems.tela.dto.SystemUserProfileSchoolDTO;
import com.planetsystems.tela.dto.TimeAttendanceSupervisionDTO;
import com.planetsystems.tela.dto.TimeTableDTO;
import com.planetsystems.tela.dto.TimeTableLessonDTO;
import com.planetsystems.tela.dto.TokenFeedbackDTO;
import com.planetsystems.tela.dto.dashboard.AttendanceDashboardSummaryDTO;
import com.planetsystems.tela.dto.dashboard.DashboardSummaryDTO;
import com.planetsystems.tela.dto.dashboard.DataOutPutByGenderDTO;
import com.planetsystems.tela.dto.dashboard.DataOutPutDTO;
import com.planetsystems.tela.dto.reports.SchoolEndOfMonthTimeAttendanceDTO;
import com.planetsystems.tela.dto.reports.SchoolEndOfTermTimeAttendanceDTO;
import com.planetsystems.tela.dto.reports.SchoolEndOfWeekTimeAttendanceDTO;
import com.planetsystems.tela.dto.reports.SchoolTimeOnTaskSummaryDTO;
import com.planetsystems.tela.dto.reports.TeacherClockInSummaryDTO;
import com.planetsystems.tela.dto.reports.DistrictEndOfMonthTimeAttendanceDTO;
import com.planetsystems.tela.dto.reports.DistrictEndOfTermTimeAttendanceDTO;
import com.planetsystems.tela.dto.reports.DistrictEndOfWeekTimeAttendanceDTO;
import com.planetsystems.tela.dto.reports.DistrictReportFilterDTO;
import com.planetsystems.tela.dto.reports.HeadTeacherSupervisionCriteria;
import com.planetsystems.tela.dto.reports.HeadTeacherSupervisionReportDTO;
import com.planetsystems.tela.dto.reports.NationalEndOfMonthTimeAttendanceDTO;
import com.planetsystems.tela.dto.reports.NationalEndOfTermTimeAttendanceDTO;
import com.planetsystems.tela.dto.reports.NationalEndOfWeekTimeAttendanceDTO;
import com.planetsystems.tela.dto.reports.NationalReportFilterDTO;
import com.planetsystems.tela.dto.reports.SMCSupervisionCriteria;
import com.planetsystems.tela.dto.reports.SMCSupervisionReportDTO;

public class RequestResult implements Result {

	private SystemFeedbackDTO systemFeedbackDTO;
	private TokenFeedbackDTO tokenFeedbackDTO;
	private FilterDTO filterDto;
	private SystemErrorDTO systemErrorDTO;
	private List<AcademicYearDTO> academicYearDTOs;
	private List<AcademicTermDTO> academicTermDTOs;
	private List<RegionDto> regionDtos;
	private List<DistrictDTO> districtDTOs;
	private List<SchoolCategoryDTO> schoolCategoryDTOs;
	private List<SchoolClassDTO> schoolClassDTOs;
	private List<SchoolDTO> schoolDTOs;
	private List<SubjectCategoryDTO> subjectCategoryDTOs;
	private List<SubjectDTO> subjectDTOs;
	private List<SchoolStaffDTO> schoolStaffDTOs;
	private List<StaffEnrollmentDto> staffEnrollmentDtos;
	private List<LearnerEnrollmentDTO> learnerEnrollmentDTOs;

	private List<ClockInDTO> clockInDTOs;
	private List<ClockOutDTO> clockOutDTOs;
	private List<LearnerAttendanceDTO> learnerAttendanceDTOs;
	private List<TimeTableDTO> timeTableDTOs;
	private List<TimeTableLessonDTO> tableLessonDTOs;
	private List<SystemUserDTO> systemUserDTOs;
	private List<StaffDailyTimeTableDTO> staffDailyTimeTableDTOs;
	private List<StaffDailyTimeTableLessonDTO> staffDailyTimeTableLessonDTOs;
	private List<StaffDailyAttendanceSupervisionDTO> staffDailyAttendanceSupervisionDTOs;
	private List<StaffDailyAttendanceTaskSupervisionDTO> staffDailyAttendanceTaskSupervisionDTOs;
	private List<TimeAttendanceSupervisionDTO> timeAttendanceSupervisionDTOs;
	private List<SystemMenuDTO> systemMenuDTOs;

	private List<SystemUserGroupSystemMenuDTO> systemUserGroupSystemMenuDTOs;

	private List<SystemUserGroupDTO> systemUserGroupDTOs;
	private SystemUserGroupDTO systemUserGroupDTO;

	private List<SystemUserProfileDTO> systemUserProfileDTOs;

	private List<SystemUserProfileSchoolDTO> systemUserProfileSchoolDTOs;

	private DashboardSummaryDTO dashboardSummaryDTO;

	private List<DataOutPutDTO> staffEnrolledByRegion;
	private List<DataOutPutDTO> learnsEnrolledRegion;

	private List<DataOutPutByGenderDTO> staffEnrolledByGender;
	private List<DataOutPutByGenderDTO> learnsEnrolledByGender;

	private List<TeacherClockInSummaryDTO> teacherClockInSummaryDTOs;
	List<SchoolEndOfWeekTimeAttendanceDTO> schoolEndOfWeekTimeAttendanceDTOs;
	List<SchoolEndOfMonthTimeAttendanceDTO> schoolEndOfMonthTimeAttendanceDTOs;
	List<SchoolEndOfTermTimeAttendanceDTO> schoolEndOfTermTimeAttendanceDTOs;
	List<SchoolTimeOnTaskSummaryDTO> schoolTimeOnTaskSummaryDTOs;

	private AttendanceDashboardSummaryDTO attendanceDashboardSummaryDTO;

	private SystemUserProfileDTO systemUserProfileDTO;

	private List<DistrictEndOfWeekTimeAttendanceDTO> districtEndOfWeekTimeAttendanceDTOs;
	private List<DistrictEndOfMonthTimeAttendanceDTO> districtEndOfMonthTimeAttendanceDTOs;
	private List<DistrictEndOfTermTimeAttendanceDTO> districtEndOfTermTimeAttendanceDTOs;

	private List<NationalEndOfWeekTimeAttendanceDTO> nationalEndOfWeekTimeAttendanceDTOs;
	private List<NationalEndOfMonthTimeAttendanceDTO> nationalEndOfMonthTimeAttendanceDTOs;
	private List<NationalEndOfTermTimeAttendanceDTO> nationalEndOfTermTimeAttendanceDTOs;

	private DistrictReportFilterDTO districtReportFilterDTO;

	private NationalReportFilterDTO nationalReportFilterDTO;

	private List<HeadTeacherSupervisionCriteria> headTeacherSupervisionCriterias;

	private HeadTeacherSupervisionReportDTO headTeacherSupervisionReportDTO;

	private List<SMCSupervisionDTO> smcSupervisionDTOs;

	private SMCSupervisionReportDTO smcSupervisionReportDTO;

	private List<SMCSupervisionCriteria> smcSupervisionCriterias;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RequestResult(SystemFeedbackDTO systemFeedbackDTO, List<SMCSupervisionCriteria> smcSupervisionCriterias,
			SMCSupervisionCriteria dto) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.smcSupervisionCriterias = smcSupervisionCriterias;
	}

	public RequestResult(SystemFeedbackDTO systemFeedbackDTO, SMCSupervisionReportDTO smcSupervisionReportDTO) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.smcSupervisionReportDTO = smcSupervisionReportDTO;
	}

	public RequestResult(SystemFeedbackDTO systemFeedbackDTO, List<SMCSupervisionDTO> smcSupervisionDTOs,
			SMCSupervisionDTO dto) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.smcSupervisionDTOs = smcSupervisionDTOs;
	}

	public RequestResult(SystemFeedbackDTO systemFeedbackDTO,
			HeadTeacherSupervisionReportDTO headTeacherSupervisionReportDTO) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.headTeacherSupervisionReportDTO = headTeacherSupervisionReportDTO;
	}

	public RequestResult(SystemFeedbackDTO systemFeedbackDTO,
			List<HeadTeacherSupervisionCriteria> headTeacherSupervisionCriterias, HeadTeacherSupervisionCriteria dto) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.headTeacherSupervisionCriterias = headTeacherSupervisionCriterias;
	}

	public RequestResult(SystemFeedbackDTO systemFeedbackDTO,
			List<NationalEndOfWeekTimeAttendanceDTO> nationalEndOfWeekTimeAttendanceDTOs,
			NationalEndOfWeekTimeAttendanceDTO dto) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.nationalEndOfWeekTimeAttendanceDTOs = nationalEndOfWeekTimeAttendanceDTOs;
	}

	public RequestResult(SystemFeedbackDTO systemFeedbackDTO,
			List<NationalEndOfMonthTimeAttendanceDTO> nationalEndOfMonthTimeAttendanceDTOs,
			NationalEndOfMonthTimeAttendanceDTO dto) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.nationalEndOfMonthTimeAttendanceDTOs = nationalEndOfMonthTimeAttendanceDTOs;
	}

	public RequestResult(SystemFeedbackDTO systemFeedbackDTO,
			List<NationalEndOfTermTimeAttendanceDTO> nationalEndOfTermTimeAttendanceDTOs,
			NationalEndOfTermTimeAttendanceDTO dto) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.nationalEndOfTermTimeAttendanceDTOs = nationalEndOfTermTimeAttendanceDTOs;
	}

	public RequestResult(DistrictReportFilterDTO districtReportFilterDTO) {
		super();
		this.districtReportFilterDTO = districtReportFilterDTO;
	}

	public RequestResult(SystemFeedbackDTO systemFeedbackDTO,
			List<DistrictEndOfTermTimeAttendanceDTO> districtEndOfTermTimeAttendanceDTOs,
			DistrictEndOfTermTimeAttendanceDTO dto) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.districtEndOfTermTimeAttendanceDTOs = districtEndOfTermTimeAttendanceDTOs;
	}

	public RequestResult(SystemFeedbackDTO systemFeedbackDTO,
			List<DistrictEndOfMonthTimeAttendanceDTO> districtEndOfMonthTimeAttendanceDTOs,
			DistrictEndOfMonthTimeAttendanceDTO dto) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.districtEndOfMonthTimeAttendanceDTOs = districtEndOfMonthTimeAttendanceDTOs;
	}

	public RequestResult(SystemFeedbackDTO systemFeedbackDTO,
			List<DistrictEndOfWeekTimeAttendanceDTO> districtEndOfWeekTimeAttendanceDTOs,
			DistrictEndOfWeekTimeAttendanceDTO dto) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.districtEndOfWeekTimeAttendanceDTOs = districtEndOfWeekTimeAttendanceDTOs;
	}

	public RequestResult(AttendanceDashboardSummaryDTO attendanceDashboardSummaryDTO) {
		super();
		this.attendanceDashboardSummaryDTO = attendanceDashboardSummaryDTO;
	}

	public RequestResult(List<DataOutPutDTO> staffEnrolledByRegion, List<DataOutPutDTO> learnsEnrolledRegion,
			List<DataOutPutByGenderDTO> staffEnrolledByGender, List<DataOutPutByGenderDTO> learnsEnrolledByGender,
			DataOutPutDTO dto) {
		super();
		this.staffEnrolledByRegion = staffEnrolledByRegion;
		this.learnsEnrolledRegion = learnsEnrolledRegion;
		this.staffEnrolledByGender = staffEnrolledByGender;
		this.learnsEnrolledByGender = learnsEnrolledByGender;
	}

	public RequestResult(SystemFeedbackDTO systemFeedbackDTO) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
	}

	public RequestResult(SystemFeedbackDTO systemFeedbackDTO, DashboardSummaryDTO dashboardSummaryDTO) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.dashboardSummaryDTO = dashboardSummaryDTO;
	}

	public RequestResult(DashboardSummaryDTO dashboardSummaryDTO) {
		super();
		this.dashboardSummaryDTO = dashboardSummaryDTO;
	}

	public RequestResult(SystemFeedbackDTO systemFeedbackDTO, List<SystemUserProfileDTO> systemUserProfileDTOs,
			SystemUserProfileDTO dto) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.systemUserProfileDTOs = systemUserProfileDTOs;
	}

	public RequestResult(SystemFeedbackDTO systemFeedbackDTO, List<SystemUserGroupDTO> systemUserGroupDTOs,
			SystemUserGroupDTO dto) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.systemUserGroupDTOs = systemUserGroupDTOs;
	}

	public RequestResult(SystemFeedbackDTO systemFeedbackDTO,
			List<SystemUserGroupSystemMenuDTO> systemUserGroupSystemMenuDTOs, SystemUserGroupSystemMenuDTO dto) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.systemUserGroupSystemMenuDTOs = systemUserGroupSystemMenuDTOs;
	}

	public RequestResult(SystemFeedbackDTO systemFeedbackDTO, List<SystemMenuDTO> systemMenuDTOs, SystemMenuDTO dto) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.systemMenuDTOs = systemMenuDTOs;
	}

	public RequestResult(SystemFeedbackDTO systemFeedbackDTO, List<AcademicYearDTO> academicYearDTOs,
			AcademicYearDTO dto) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.academicYearDTOs = academicYearDTOs;
	}

	public RequestResult(SystemFeedbackDTO systemFeedbackDTO, List<AcademicTermDTO> academicTermDTOs,
			AcademicTermDTO dto) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.academicTermDTOs = academicTermDTOs;
	}

	public RequestResult(SystemFeedbackDTO systemFeedbackDTO, List<RegionDto> regionDtos, RegionDto dto) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.regionDtos = regionDtos;
	}

	public RequestResult(SystemFeedbackDTO systemFeedbackDTO, List<DistrictDTO> districtDTOs, DistrictDTO dto) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.districtDTOs = districtDTOs;
	}

	public RequestResult(SystemFeedbackDTO systemFeedbackDTO, List<SchoolCategoryDTO> schoolCategoryDTOs,
			SchoolCategoryDTO dto) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.schoolCategoryDTOs = schoolCategoryDTOs;
	}

	public RequestResult(SystemFeedbackDTO systemFeedbackDTO, List<SchoolDTO> schoolDTOs, SchoolDTO dto) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.schoolDTOs = schoolDTOs;
	}

	public RequestResult(SystemFeedbackDTO systemFeedbackDTO, List<SchoolClassDTO> schoolClassDTOs,
			SchoolClassDTO dto) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.schoolClassDTOs = schoolClassDTOs;
	}

	public RequestResult(SystemFeedbackDTO systemFeedbackDTO, List<SubjectCategoryDTO> subjectCategoryDTOs,
			SubjectCategoryDTO dto) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.subjectCategoryDTOs = subjectCategoryDTOs;
	}

	public RequestResult(SystemFeedbackDTO systemFeedbackDTO, List<SubjectDTO> subjectDTOs, SubjectDTO dto) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.subjectDTOs = subjectDTOs;
	}

	public RequestResult(SystemFeedbackDTO systemFeedbackDTO, List<SchoolStaffDTO> schoolStaffDTOs,
			SchoolStaffDTO dto) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.schoolStaffDTOs = schoolStaffDTOs;
	}

	public RequestResult(SystemFeedbackDTO systemFeedbackDTO, List<StaffEnrollmentDto> staffEnrollmentDtos,
			StaffEnrollmentDto staffEnrollmentDto) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.staffEnrollmentDtos = staffEnrollmentDtos;
	}

	public RequestResult(SystemFeedbackDTO systemFeedbackDTO, List<ClockInDTO> clockInDTOs, ClockInDTO clockInDTO) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.clockInDTOs = clockInDTOs;
	}

	public RequestResult(SystemFeedbackDTO systemFeedbackDTO, List<ClockOutDTO> clockOutDTOs, ClockOutDTO clockOutDTO) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.clockOutDTOs = clockOutDTOs;
	}

	public RequestResult(SystemFeedbackDTO systemFeedbackDTO, List<LearnerEnrollmentDTO> learnerEnrollmentDTOs,
			LearnerEnrollmentDTO learnerEnrollmentDTO) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.learnerEnrollmentDTOs = learnerEnrollmentDTOs;
	}

	public RequestResult(SystemFeedbackDTO systemFeedbackDTO, List<LearnerAttendanceDTO> learnerAttendanceDTOs,
			LearnerAttendanceDTO learnerAttendanceDTO) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.learnerAttendanceDTOs = learnerAttendanceDTOs;
	}

	public RequestResult(SystemFeedbackDTO systemFeedbackDTO, List<TimeTableDTO> timeTableDTOs, TimeTableDTO tableDTO) {

		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.timeTableDTOs = timeTableDTOs;
	}

	public RequestResult(SystemFeedbackDTO systemFeedbackDTO, List<SystemUserDTO> systemUserDTOs, SystemUserDTO dto) {

		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.systemUserDTOs = systemUserDTOs;
	}

	public RequestResult(SystemFeedbackDTO systemFeedbackDTO, List<TimeTableLessonDTO> tableLessonDTOs,
			TimeTableLessonDTO dto) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.tableLessonDTOs = tableLessonDTOs;
	}

	public RequestResult(SystemFeedbackDTO systemFeedbackDTO, List<StaffDailyTimeTableDTO> staffDailyTimeTableDTOs,
			StaffDailyTimeTableDTO staffDailyTimeTableDTO) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.staffDailyTimeTableDTOs = staffDailyTimeTableDTOs;
	}

	public RequestResult(SystemFeedbackDTO systemFeedbackDTO,
			List<StaffDailyAttendanceSupervisionDTO> staffDailyAttendanceSupervisionDTOs,
			StaffDailyAttendanceSupervisionDTO dto) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.staffDailyAttendanceSupervisionDTOs = staffDailyAttendanceSupervisionDTOs;
	}

	public RequestResult(SystemFeedbackDTO systemFeedbackDTO,
			List<StaffDailyAttendanceTaskSupervisionDTO> staffDailyAttendanceTaskSupervisionDTOs,
			StaffDailyAttendanceTaskSupervisionDTO dto) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.staffDailyAttendanceTaskSupervisionDTOs = staffDailyAttendanceTaskSupervisionDTOs;
	}

	public RequestResult(SystemFeedbackDTO systemFeedbackDTO,
			List<StaffDailyTimeTableLessonDTO> staffDailyTimeTableLessonDTOs, StaffDailyTimeTableLessonDTO dto) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.staffDailyTimeTableLessonDTOs = staffDailyTimeTableLessonDTOs;
	}

	public RequestResult(SystemFeedbackDTO systemFeedbackDTO,
			List<TimeAttendanceSupervisionDTO> timeAttendanceSupervisionDTOs, TimeAttendanceSupervisionDTO dto) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.timeAttendanceSupervisionDTOs = timeAttendanceSupervisionDTOs;
	}

	public RequestResult(SystemFeedbackDTO systemFeedbackDTO, SystemUserGroupDTO systemUserGroupDTO) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.systemUserGroupDTO = systemUserGroupDTO;
	}

	public RequestResult(SystemFeedbackDTO systemFeedbackDTO,
			List<SystemUserProfileSchoolDTO> systemUserProfileSchoolDTOs) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.systemUserProfileSchoolDTOs = systemUserProfileSchoolDTOs;
	}

	public RequestResult(SystemFeedbackDTO systemFeedbackDTO,
			List<SchoolTimeOnTaskSummaryDTO> schoolTimeOnTaskSummaryDTOs, SchoolTimeOnTaskSummaryDTO dto) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.schoolTimeOnTaskSummaryDTOs = schoolTimeOnTaskSummaryDTOs;
	}

	public RequestResult() {
		super();
	}

	public RequestResult(TokenFeedbackDTO tokenFeedbackDTO) {
		super();
		this.tokenFeedbackDTO = tokenFeedbackDTO;
	}

	public RequestResult(SystemErrorDTO systemErrorDTO) {
		super();
		this.systemErrorDTO = systemErrorDTO;
	}

	public RequestResult(FilterDTO filterDto) {
		super();
		this.filterDto = filterDto;
	}

	public RequestResult(SystemFeedbackDTO feedback, List<TeacherClockInSummaryDTO> teacherClockInSummaryDTOs,
			TeacherClockInSummaryDTO dto) {
		this.systemFeedbackDTO = feedback;
		this.teacherClockInSummaryDTOs = teacherClockInSummaryDTOs;
	}

	public RequestResult(SystemFeedbackDTO feedback, List<SchoolEndOfWeekTimeAttendanceDTO> list,
			SchoolEndOfWeekTimeAttendanceDTO dto) {
		this.systemFeedbackDTO = feedback;
		this.schoolEndOfWeekTimeAttendanceDTOs = list;
	}

	public RequestResult(SystemFeedbackDTO feedback, List<SchoolEndOfMonthTimeAttendanceDTO> list,
			SchoolEndOfMonthTimeAttendanceDTO dto) {
		this.systemFeedbackDTO = feedback;
		this.schoolEndOfMonthTimeAttendanceDTOs = list;
	}

	public RequestResult(SystemFeedbackDTO feedback, SystemUserProfileDTO systemUserProfileDTO) {
		this.systemFeedbackDTO = feedback;
		this.systemUserProfileDTO = systemUserProfileDTO;
	}

	public RequestResult(SystemFeedbackDTO systemFeedbackDTO,
			List<SchoolEndOfTermTimeAttendanceDTO> schoolEndOfTermTimeAttendanceDTOs,
			SchoolEndOfTermTimeAttendanceDTO dto) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.schoolEndOfTermTimeAttendanceDTOs = schoolEndOfTermTimeAttendanceDTOs;
	}

	public SystemUserProfileDTO getSystemUserProfileDTO() {
		return systemUserProfileDTO;
	}

	public SystemFeedbackDTO getSystemFeedbackDTO() {
		return systemFeedbackDTO;
	}

	public List<AcademicYearDTO> getAcademicYearDTOs() {
		return academicYearDTOs;
	}

	public List<AcademicTermDTO> getAcademicTermDTOs() {
		return academicTermDTOs;
	}

	public List<RegionDto> getRegionDtos() {
		return regionDtos;
	}

	public List<DistrictDTO> getDistrictDTOs() {
		return districtDTOs;
	}

	public List<SchoolCategoryDTO> getSchoolCategoryDTOs() {
		return schoolCategoryDTOs;
	}

	public List<SchoolClassDTO> getSchoolClassDTOs() {
		return schoolClassDTOs;
	}

	public List<SchoolDTO> getSchoolDTOs() {
		return schoolDTOs;
	}

	public List<SubjectCategoryDTO> getSubjectCategoryDTOs() {
		return subjectCategoryDTOs;
	}

	public List<SubjectDTO> getSubjectDTOs() {
		return subjectDTOs;
	}

	public TokenFeedbackDTO getTokenFeedbackDTO() {
		return tokenFeedbackDTO;
	}

	public SystemErrorDTO getSystemErrorDTO() {
		return systemErrorDTO;
	}

	public List<SchoolStaffDTO> getSchoolStaffDTOs() {
		return schoolStaffDTOs;
	}

	public List<StaffEnrollmentDto> getStaffEnrollmentDtos() {
		return staffEnrollmentDtos;
	}

	public List<LearnerEnrollmentDTO> getLearnerEnrollmentDTOs() {
		return learnerEnrollmentDTOs;
	}

	public List<ClockInDTO> getClockInDTOs() {
		return clockInDTOs;
	}

	public List<ClockOutDTO> getClockOutDTOs() {
		return clockOutDTOs;
	}

	public List<LearnerAttendanceDTO> getLearnerAttendanceDTOs() {
		return learnerAttendanceDTOs;
	}

	public List<TimeTableDTO> getTimeTableDTOs() {
		return timeTableDTOs;
	}

	public List<SystemUserDTO> getSystemUserDTOs() {
		return systemUserDTOs;
	}

	public List<TimeTableLessonDTO> getTableLessonDTOs() {
		return tableLessonDTOs;
	}

	public List<SystemMenuDTO> getSystemMenuDTOs() {
		return systemMenuDTOs;
	}

	public List<SystemUserGroupSystemMenuDTO> getSystemUserGroupSystemMenuDTOs() {
		return systemUserGroupSystemMenuDTOs;
	}

	public List<SystemUserGroupDTO> getSystemUserGroupDTOs() {
		return systemUserGroupDTOs;
	}

	public List<SystemUserProfileDTO> getSystemUserProfileDTOs() {
		return systemUserProfileDTOs;
	}

	public List<StaffDailyTimeTableDTO> getStaffDailyTimeTableDTOs() {
		return staffDailyTimeTableDTOs;
	}

	public List<StaffDailyTimeTableLessonDTO> getStaffDailyTimeTableLessonDTOs() {
		return staffDailyTimeTableLessonDTOs;
	}

	public List<StaffDailyAttendanceSupervisionDTO> getStaffDailyAttendanceSupervisionDTOs() {
		return staffDailyAttendanceSupervisionDTOs;
	}

	public List<StaffDailyAttendanceTaskSupervisionDTO> getStaffDailyAttendanceTaskSupervisionDTOs() {
		return staffDailyAttendanceTaskSupervisionDTOs;
	}

	public List<TimeAttendanceSupervisionDTO> getTimeAttendanceSupervisionDTOs() {
		return timeAttendanceSupervisionDTOs;
	}

	public FilterDTO getFilterDto() {
		return filterDto;
	}

	public SystemUserGroupDTO getSystemUserGroupDTO() {
		return systemUserGroupDTO;
	}

	public List<SystemUserProfileSchoolDTO> getSystemUserProfileSchoolDTOs() {
		return systemUserProfileSchoolDTOs;
	}

	@Override
	public String toString() {
		return "RequestResult [systemErrorDTO=" + systemErrorDTO + "FEEDDTO " + systemFeedbackDTO + " ]";
	}

	public DashboardSummaryDTO getDashboardSummaryDTO() {
		return dashboardSummaryDTO;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<DataOutPutDTO> getStaffEnrolledByRegion() {
		return staffEnrolledByRegion;
	}

	public List<DataOutPutDTO> getLearnsEnrolledRegion() {
		return learnsEnrolledRegion;
	}

	public List<DataOutPutByGenderDTO> getStaffEnrolledByGender() {
		return staffEnrolledByGender;
	}

	public List<DataOutPutByGenderDTO> getLearnsEnrolledByGender() {
		return learnsEnrolledByGender;
	}

	public AttendanceDashboardSummaryDTO getAttendanceDashboardSummaryDTO() {
		return attendanceDashboardSummaryDTO;
	}

	public List<TeacherClockInSummaryDTO> getTeacherClockInSummaryDTOs() {
		return teacherClockInSummaryDTOs;
	}

	public List<SchoolEndOfWeekTimeAttendanceDTO> getSchoolEndOfWeekTimeAttendanceDTOs() {
		return schoolEndOfWeekTimeAttendanceDTOs;
	}

	public List<SchoolEndOfMonthTimeAttendanceDTO> getSchoolEndOfMonthTimeAttendanceDTOs() {
		return schoolEndOfMonthTimeAttendanceDTOs;
	}

	public List<SchoolTimeOnTaskSummaryDTO> getSchoolTimeOnTaskSummaryDTOs() {
		return schoolTimeOnTaskSummaryDTOs;
	}

	public List<DistrictEndOfWeekTimeAttendanceDTO> getDistrictEndOfWeekTimeAttendanceDTOs() {
		return districtEndOfWeekTimeAttendanceDTOs;
	}

	public DistrictReportFilterDTO getDistrictReportFilterDTO() {
		return districtReportFilterDTO;
	}

	public List<DistrictEndOfMonthTimeAttendanceDTO> getDistrictEndOfMonthTimeAttendanceDTOs() {
		return districtEndOfMonthTimeAttendanceDTOs;
	}

	public List<DistrictEndOfTermTimeAttendanceDTO> getDistrictEndOfTermTimeAttendanceDTOs() {
		return districtEndOfTermTimeAttendanceDTOs;
	}

	public List<NationalEndOfWeekTimeAttendanceDTO> getNationalEndOfWeekTimeAttendanceDTOs() {
		return nationalEndOfWeekTimeAttendanceDTOs;
	}

	public List<NationalEndOfMonthTimeAttendanceDTO> getNationalEndOfMonthTimeAttendanceDTOs() {
		return nationalEndOfMonthTimeAttendanceDTOs;
	}

	public List<NationalEndOfTermTimeAttendanceDTO> getNationalEndOfTermTimeAttendanceDTOs() {
		return nationalEndOfTermTimeAttendanceDTOs;
	}

	public NationalReportFilterDTO getNationalReportFilterDTO() {
		return nationalReportFilterDTO;
	}

	public List<SchoolEndOfTermTimeAttendanceDTO> getSchoolEndOfTermTimeAttendanceDTOs() {
		return schoolEndOfTermTimeAttendanceDTOs;
	}

	public List<HeadTeacherSupervisionCriteria> getHeadTeacherSupervisionCriterias() {
		return headTeacherSupervisionCriterias;
	}

	public HeadTeacherSupervisionReportDTO getHeadTeacherSupervisionReportDTO() {
		return headTeacherSupervisionReportDTO;
	}

	public List<SMCSupervisionDTO> getSmcSupervisionDTOs() {
		return smcSupervisionDTOs;
	}

	public SMCSupervisionReportDTO getSmcSupervisionReportDTO() {
		return smcSupervisionReportDTO;
	}

	public List<SMCSupervisionCriteria> getSmcSupervisionCriterias() {
		return smcSupervisionCriterias;
	}

}
