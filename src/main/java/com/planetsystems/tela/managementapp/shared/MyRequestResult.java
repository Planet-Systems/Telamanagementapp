package com.planetsystems.tela.managementapp.shared;

import java.util.List;

import com.gwtplatform.dispatch.rpc.shared.Result;
import com.planetsystems.tela.dto.AcademicTermDTO;
import com.planetsystems.tela.dto.AcademicYearDTO;
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
import com.planetsystems.tela.dto.StaffDailyAttendanceSupervisionDTO;
import com.planetsystems.tela.dto.StaffDailyAttendanceTaskSupervisionDTO;
import com.planetsystems.tela.dto.StaffDailyTimeTableDTO;
import com.planetsystems.tela.dto.StaffDailyTimeTableLessonDTO;
import com.planetsystems.tela.dto.StaffEnrollmentDto;
import com.planetsystems.tela.dto.SubjectCategoryDTO;
import com.planetsystems.tela.dto.SubjectDTO;
import com.planetsystems.tela.dto.SystemMenuDTO;
import com.planetsystems.tela.dto.SystemUserGroupDTO;
import com.planetsystems.tela.dto.SystemUserProfileDTO;
import com.planetsystems.tela.dto.SystemUserProfileSchoolDTO;
import com.planetsystems.tela.dto.TimeTableDTO;
import com.planetsystems.tela.dto.TimeTableLessonDTO;
import com.planetsystems.tela.dto.response.SystemErrorDTO;
import com.planetsystems.tela.dto.response.SystemResponseDTO;

public class MyRequestResult implements Result {

	private static final long serialVersionUID = 1L;

	SystemResponseDTO<String> responseText;
	SystemResponseDTO<Integer> responseInteger;
	
	SystemResponseDTO<List<SystemMenuDTO>> SystemMenuResponseList;
	SystemResponseDTO<SystemMenuDTO> SystemMenuResponse;
	
	SystemResponseDTO<SystemUserGroupDTO> systemUserGroupResponse;
	SystemResponseDTO<List<SystemUserGroupDTO>> systemUserGroupResponseList;
	
	SystemResponseDTO<AcademicYearDTO> academicYearResponse;
	SystemResponseDTO<List<AcademicYearDTO>> academicYearResponseList;
	
	SystemResponseDTO<List<AcademicTermDTO>> academicTermResponseList;
	SystemResponseDTO<AcademicTermDTO> academicTermResponse;
	
	SystemResponseDTO<List<RegionDto>> regionResponseList;
	SystemResponseDTO<RegionDto> regionResponse;
	
	SystemResponseDTO<List<DistrictDTO>> districtResponseList;
	SystemResponseDTO<DistrictDTO> districtResponse;
	
	SystemResponseDTO<List<SchoolCategoryDTO>> schoolCategoryResponseList;
	SystemResponseDTO<SchoolCategoryDTO> schoolCategoryResponse;
	
	SystemResponseDTO<List<SchoolDTO>> schoolResponseList; 
	SystemResponseDTO<SchoolDTO> schoolResponse;
	
	SystemResponseDTO<List<SchoolClassDTO>> schoolClassResponseList;
	SystemResponseDTO<SchoolClassDTO> schoolClassResponse;
	
	SystemResponseDTO<List<SubjectCategoryDTO>> subjectCategoryResponseList; 
	SystemResponseDTO<SubjectCategoryDTO> subjectCategoryResponse;
	
	SystemResponseDTO<List<SubjectDTO>> subjectResponseList;
	SystemResponseDTO<SubjectDTO> subjectResponse;
	
	
	SystemResponseDTO<List<LearnerEnrollmentDTO>> learnerEnrollmentResponseList;
	SystemResponseDTO<LearnerEnrollmentDTO> learnerEnrollmentResponse;
	
	SystemResponseDTO<List<StaffEnrollmentDto>> staffEnrollmentResponseList;
	SystemResponseDTO<StaffEnrollmentDto> staffEnrollmentResponse;
	SystemResponseDTO<List<SchoolStaffDTO>> schoolStaffResponseList;
	SystemResponseDTO<SchoolStaffDTO> schoolStaffResponse;
	
	SystemResponseDTO<List<LearnerAttendanceDTO>> learnerAtendanceResponseList;
	SystemResponseDTO<LearnerAttendanceDTO> learnerAttendanceResponse;
	
	SystemResponseDTO<ClockInDTO> clockInResponse;
	SystemResponseDTO<List<ClockInDTO>> clockInResponseList;
	
	SystemResponseDTO<ClockOutDTO> clockOutResponse;
	SystemResponseDTO<List<ClockOutDTO>> clockOutResponseList;
	
	SystemResponseDTO<List<TimeTableDTO>> timeTableResponseList;
	SystemResponseDTO<TimeTableDTO> timeTableResponse;
	
	SystemResponseDTO<List<TimeTableLessonDTO>> timeTableLessonResponseList;
	SystemResponseDTO<TimeTableLessonDTO> timeTableLessonResponse;
	
	SystemResponseDTO<List<StaffDailyTimeTableDTO>> staffDailyTimeTableResponseList;
	SystemResponseDTO<StaffDailyTimeTableDTO> staffDailyTimeTableResponse;
	
	SystemResponseDTO<StaffDailyTimeTableLessonDTO> staffDailyTimeTableLessonResponse;
	SystemResponseDTO<List<StaffDailyTimeTableLessonDTO>> staffDailyTimeTableLessonResponseList;
	
	SystemResponseDTO<StaffDailyAttendanceSupervisionDTO> staffDailyAttendanceSupervisionResponse;
	SystemResponseDTO<List<StaffDailyAttendanceSupervisionDTO>> staffDailyAttendanceSupervisionResponseList;
	
	SystemResponseDTO<StaffDailyAttendanceTaskSupervisionDTO> staffDailyAttendanceTaskSupervisionResponse;
	SystemResponseDTO<List<StaffDailyAttendanceTaskSupervisionDTO>> attendanceTaskSupervisionResponseList;

	SystemResponseDTO<SystemUserProfileDTO>  systemUserProfileResponse;
	SystemResponseDTO<List<SystemUserProfileDTO> > systemUserProfileResponseList;
	
	SystemResponseDTO<SystemUserProfileSchoolDTO> systemUserProfileSchoolResponse;
	SystemResponseDTO<List<SystemUserProfileSchoolDTO>> systemUserProfileSchoolResponseList;
	

	public MyRequestResult() {
		// TODO Auto-generated constructor stub
	}


	public SystemResponseDTO<String> getResponseText() {
		return responseText;
	}


	public void setResponseText(SystemResponseDTO<String> responseText) {
		this.responseText = responseText;
	}


	public SystemResponseDTO<Integer> getResponseInteger() {
		return responseInteger;
	}


	public void setResponseInteger(SystemResponseDTO<Integer> responseInteger) {
		this.responseInteger = responseInteger;
	}


	public SystemResponseDTO<List<SystemMenuDTO>> getSystemMenuResponseList() {
		return SystemMenuResponseList;
	}


	public void setSystemMenuResponseList(SystemResponseDTO<List<SystemMenuDTO>> systemMenuResponseList) {
		SystemMenuResponseList = systemMenuResponseList;
	}


	public SystemResponseDTO<SystemMenuDTO> getSystemMenuResponse() {
		return SystemMenuResponse;
	}


	public void setSystemMenuResponse(SystemResponseDTO<SystemMenuDTO> systemMenuResponse) {
		SystemMenuResponse = systemMenuResponse;
	}


	public SystemResponseDTO<SystemUserGroupDTO> getSystemUserGroupResponse() {
		return systemUserGroupResponse;
	}


	public void setSystemUserGroupResponse(SystemResponseDTO<SystemUserGroupDTO> systemUserGroupResponse) {
		this.systemUserGroupResponse = systemUserGroupResponse;
	}


	public SystemResponseDTO<List<SystemUserGroupDTO>> getSystemUserGroupResponseList() {
		return systemUserGroupResponseList;
	}


	public void setSystemUserGroupResponseList(SystemResponseDTO<List<SystemUserGroupDTO>> systemUserGroupResponseList) {
		this.systemUserGroupResponseList = systemUserGroupResponseList;
	}


	public SystemResponseDTO<AcademicYearDTO> getAcademicYearResponse() {
		return academicYearResponse;
	}


	public void setAcademicYearResponse(SystemResponseDTO<AcademicYearDTO> academicYearResponse) {
		this.academicYearResponse = academicYearResponse;
	}


	public SystemResponseDTO<List<AcademicYearDTO>> getAcademicYearResponseList() {
		return academicYearResponseList;
	}


	public void setAcademicYearResponseList(SystemResponseDTO<List<AcademicYearDTO>> academicYearResponseList) {
		this.academicYearResponseList = academicYearResponseList;
	}


	public SystemResponseDTO<List<AcademicTermDTO>> getAcademicTermResponseList() {
		return academicTermResponseList;
	}


	public void setAcademicTermResponseList(SystemResponseDTO<List<AcademicTermDTO>> academicTermResponseList) {
		this.academicTermResponseList = academicTermResponseList;
	}


	public SystemResponseDTO<AcademicTermDTO> getAcademicTermResponse() {
		return academicTermResponse;
	}


	public void setAcademicTermResponse(SystemResponseDTO<AcademicTermDTO> academicTermResponse) {
		this.academicTermResponse = academicTermResponse;
	}


	public SystemResponseDTO<List<RegionDto>> getRegionResponseList() {
		return regionResponseList;
	}


	public void setRegionResponseList(SystemResponseDTO<List<RegionDto>> regionResponseList) {
		this.regionResponseList = regionResponseList;
	}


	public SystemResponseDTO<RegionDto> getRegionResponse() {
		return regionResponse;
	}


	public void setRegionResponse(SystemResponseDTO<RegionDto> regionResponse) {
		this.regionResponse = regionResponse;
	}


	public SystemResponseDTO<List<DistrictDTO>> getDistrictResponseList() {
		return districtResponseList;
	}


	public void setDistrictResponseList(SystemResponseDTO<List<DistrictDTO>> districtResponseList) {
		this.districtResponseList = districtResponseList;
	}


	public SystemResponseDTO<DistrictDTO> getDistrictResponse() {
		return districtResponse;
	}


	public void setDistrictResponse(SystemResponseDTO<DistrictDTO> districtResponse) {
		this.districtResponse = districtResponse;
	}


	public SystemResponseDTO<List<SchoolCategoryDTO>> getSchoolCategoryResponseList() {
		return schoolCategoryResponseList;
	}


	public void setSchoolCategoryResponseList(SystemResponseDTO<List<SchoolCategoryDTO>> schoolCategoryResponseList) {
		this.schoolCategoryResponseList = schoolCategoryResponseList;
	}


	public SystemResponseDTO<SchoolCategoryDTO> getSchoolCategoryResponse() {
		return schoolCategoryResponse;
	}


	public void setSchoolCategoryResponse(SystemResponseDTO<SchoolCategoryDTO> schoolCategoryResponse) {
		this.schoolCategoryResponse = schoolCategoryResponse;
	}


	public SystemResponseDTO<List<SchoolDTO>> getSchoolResponseList() {
		return schoolResponseList;
	}


	public void setSchoolResponseList(SystemResponseDTO<List<SchoolDTO>> schoolResponseList) {
		this.schoolResponseList = schoolResponseList;
	}


	public SystemResponseDTO<SchoolDTO> getSchoolResponse() {
		return schoolResponse;
	}


	public void setSchoolResponse(SystemResponseDTO<SchoolDTO> schoolResponse) {
		this.schoolResponse = schoolResponse;
	}


	public SystemResponseDTO<List<SchoolClassDTO>> getSchoolClassResponseList() {
		return schoolClassResponseList;
	}


	public void setSchoolClassResponseList(SystemResponseDTO<List<SchoolClassDTO>> schoolClassResponseList) {
		this.schoolClassResponseList = schoolClassResponseList;
	}


	public SystemResponseDTO<SchoolClassDTO> getSchoolClassResponse() {
		return schoolClassResponse;
	}


	public void setSchoolClassResponse(SystemResponseDTO<SchoolClassDTO> schoolClassResponse) {
		this.schoolClassResponse = schoolClassResponse;
	}


	public SystemResponseDTO<List<SubjectCategoryDTO>> getSubjectCategoryResponseList() {
		return subjectCategoryResponseList;
	}


	public void setSubjectCategoryResponseList(SystemResponseDTO<List<SubjectCategoryDTO>> subjectCategoryResponseList) {
		this.subjectCategoryResponseList = subjectCategoryResponseList;
	}


	public SystemResponseDTO<SubjectCategoryDTO> getSubjectCategoryResponse() {
		return subjectCategoryResponse;
	}


	public void setSubjectCategoryResponse(SystemResponseDTO<SubjectCategoryDTO> subjectCategoryResponse) {
		this.subjectCategoryResponse = subjectCategoryResponse;
	}


	public SystemResponseDTO<List<SubjectDTO>> getSubjectResponseList() {
		return subjectResponseList;
	}


	public void setSubjectResponseList(SystemResponseDTO<List<SubjectDTO>> subjectResponseList) {
		this.subjectResponseList = subjectResponseList;
	}


	public SystemResponseDTO<SubjectDTO> getSubjectResponse() {
		return subjectResponse;
	}


	public void setSubjectResponse(SystemResponseDTO<SubjectDTO> subjectResponse) {
		this.subjectResponse = subjectResponse;
	}


	public SystemResponseDTO<List<LearnerEnrollmentDTO>> getLearnerEnrollmentResponseList() {
		return learnerEnrollmentResponseList;
	}


	public void setLearnerEnrollmentResponseList(
			SystemResponseDTO<List<LearnerEnrollmentDTO>> learnerEnrollmentResponseList) {
		this.learnerEnrollmentResponseList = learnerEnrollmentResponseList;
	}


	public SystemResponseDTO<LearnerEnrollmentDTO> getLearnerEnrollmentResponse() {
		return learnerEnrollmentResponse;
	}


	public void setLearnerEnrollmentResponse(SystemResponseDTO<LearnerEnrollmentDTO> learnerEnrollmentResponse) {
		this.learnerEnrollmentResponse = learnerEnrollmentResponse;
	}


	public SystemResponseDTO<List<StaffEnrollmentDto>> getStaffEnrollmentResponseList() {
		return staffEnrollmentResponseList;
	}


	public void setStaffEnrollmentResponseList(SystemResponseDTO<List<StaffEnrollmentDto>> staffEnrollmentResponseList) {
		this.staffEnrollmentResponseList = staffEnrollmentResponseList;
	}


	public SystemResponseDTO<StaffEnrollmentDto> getStaffEnrollmentResponse() {
		return staffEnrollmentResponse;
	}


	public void setStaffEnrollmentResponse(SystemResponseDTO<StaffEnrollmentDto> staffEnrollmentResponse) {
		this.staffEnrollmentResponse = staffEnrollmentResponse;
	}


	public SystemResponseDTO<List<SchoolStaffDTO>> getSchoolStaffResponseList() {
		return schoolStaffResponseList;
	}


	public void setSchoolStaffResponseList(SystemResponseDTO<List<SchoolStaffDTO>> schoolStaffResponseList) {
		this.schoolStaffResponseList = schoolStaffResponseList;
	}


	public SystemResponseDTO<SchoolStaffDTO> getSchoolStaffResponse() {
		return schoolStaffResponse;
	}


	public void setSchoolStaffResponse(SystemResponseDTO<SchoolStaffDTO> schoolStaffResponse) {
		this.schoolStaffResponse = schoolStaffResponse;
	}


	public SystemResponseDTO<List<LearnerAttendanceDTO>> getLearnerAtendanceResponseList() {
		return learnerAtendanceResponseList;
	}


	public void setLearnerAtendanceResponseList(
			SystemResponseDTO<List<LearnerAttendanceDTO>> learnerAtendanceResponseList) {
		this.learnerAtendanceResponseList = learnerAtendanceResponseList;
	}


	public SystemResponseDTO<LearnerAttendanceDTO> getLearnerAttendanceResponse() {
		return learnerAttendanceResponse;
	}


	public void setLearnerAttendanceResponse(SystemResponseDTO<LearnerAttendanceDTO> learnerAttendanceResponse) {
		this.learnerAttendanceResponse = learnerAttendanceResponse;
	}


	public SystemResponseDTO<ClockInDTO> getClockInResponse() {
		return clockInResponse;
	}


	public void setClockInResponse(SystemResponseDTO<ClockInDTO> clockInResponse) {
		this.clockInResponse = clockInResponse;
	}


	public SystemResponseDTO<List<ClockInDTO>> getClockInResponseList() {
		return clockInResponseList;
	}


	public void setClockInResponseList(SystemResponseDTO<List<ClockInDTO>> clockInResponseList) {
		this.clockInResponseList = clockInResponseList;
	}


	public SystemResponseDTO<ClockOutDTO> getClockOutResponse() {
		return clockOutResponse;
	}


	public void setClockOutResponse(SystemResponseDTO<ClockOutDTO> clockOutResponse) {
		this.clockOutResponse = clockOutResponse;
	}


	public SystemResponseDTO<List<ClockOutDTO>> getClockOutResponseList() {
		return clockOutResponseList;
	}


	public void setClockOutResponseList(SystemResponseDTO<List<ClockOutDTO>> clockOutResponseList) {
		this.clockOutResponseList = clockOutResponseList;
	}


	public SystemResponseDTO<List<TimeTableDTO>> getTimeTableResponseList() {
		return timeTableResponseList;
	}


	public void setTimeTableResponseList(SystemResponseDTO<List<TimeTableDTO>> timeTableResponseList) {
		this.timeTableResponseList = timeTableResponseList;
	}


	public SystemResponseDTO<TimeTableDTO> getTimeTableResponse() {
		return timeTableResponse;
	}


	public void setTimeTableResponse(SystemResponseDTO<TimeTableDTO> timeTableResponse) {
		this.timeTableResponse = timeTableResponse;
	}


	public SystemResponseDTO<List<TimeTableLessonDTO>> getTimeTableLessonResponseList() {
		return timeTableLessonResponseList;
	}


	public void setTimeTableLessonResponseList(SystemResponseDTO<List<TimeTableLessonDTO>> timeTableLessonResponseList) {
		this.timeTableLessonResponseList = timeTableLessonResponseList;
	}




	public SystemResponseDTO<TimeTableLessonDTO> getTimeTableLessonResponse() {
		return timeTableLessonResponse;
	}


	public void setTimeTableLessonResponse(SystemResponseDTO<TimeTableLessonDTO> timeTableLessonResponse) {
		this.timeTableLessonResponse = timeTableLessonResponse;
	}


	public SystemResponseDTO<List<StaffDailyTimeTableDTO>> getStaffDailyTimeTableResponseList() {
		return staffDailyTimeTableResponseList;
	}


	public void setStaffDailyTimeTableResponseList(
			SystemResponseDTO<List<StaffDailyTimeTableDTO>> staffDailyTimeTableResponseList) {
		this.staffDailyTimeTableResponseList = staffDailyTimeTableResponseList;
	}


	public SystemResponseDTO<StaffDailyTimeTableDTO> getStaffDailyTimeTableResponse() {
		return staffDailyTimeTableResponse;
	}


	public void setStaffDailyTimeTableResponse(SystemResponseDTO<StaffDailyTimeTableDTO> staffDailyTimeTableResponse) {
		this.staffDailyTimeTableResponse = staffDailyTimeTableResponse;
	}


	public SystemResponseDTO<StaffDailyTimeTableLessonDTO> getStaffDailyTimeTableLessonResponse() {
		return staffDailyTimeTableLessonResponse;
	}


	public void setStaffDailyTimeTableLessonResponse(
			SystemResponseDTO<StaffDailyTimeTableLessonDTO> staffDailyTimeTableLessonResponse) {
		this.staffDailyTimeTableLessonResponse = staffDailyTimeTableLessonResponse;
	}


	public SystemResponseDTO<List<StaffDailyTimeTableLessonDTO>> getStaffDailyTimeTableLessonResponseList() {
		return staffDailyTimeTableLessonResponseList;
	}


	public void setStaffDailyTimeTableLessonResponseList(
			SystemResponseDTO<List<StaffDailyTimeTableLessonDTO>> staffDailyTimeTableLessonResponseList) {
		this.staffDailyTimeTableLessonResponseList = staffDailyTimeTableLessonResponseList;
	}


	public SystemResponseDTO<StaffDailyAttendanceSupervisionDTO> getStaffDailyAttendanceSupervisionResponse() {
		return staffDailyAttendanceSupervisionResponse;
	}


	public void setStaffDailyAttendanceSupervisionResponse(
			SystemResponseDTO<StaffDailyAttendanceSupervisionDTO> staffDailyAttendanceSupervisionResponse) {
		this.staffDailyAttendanceSupervisionResponse = staffDailyAttendanceSupervisionResponse;
	}


	public SystemResponseDTO<List<StaffDailyAttendanceSupervisionDTO>> getStaffDailyAttendanceSupervisionResponseList() {
		return staffDailyAttendanceSupervisionResponseList;
	}


	public void setStaffDailyAttendanceSupervisionResponseList(
			SystemResponseDTO<List<StaffDailyAttendanceSupervisionDTO>> staffDailyAttendanceSupervisionResponseList) {
		this.staffDailyAttendanceSupervisionResponseList = staffDailyAttendanceSupervisionResponseList;
	}


	public SystemResponseDTO<StaffDailyAttendanceTaskSupervisionDTO> getStaffDailyAttendanceTaskSupervisionResponse() {
		return staffDailyAttendanceTaskSupervisionResponse;
	}


	public void setStaffDailyAttendanceTaskSupervisionResponse(
			SystemResponseDTO<StaffDailyAttendanceTaskSupervisionDTO> staffDailyAttendanceTaskSupervisionResponse) {
		this.staffDailyAttendanceTaskSupervisionResponse = staffDailyAttendanceTaskSupervisionResponse;
	}


	public SystemResponseDTO<List<StaffDailyAttendanceTaskSupervisionDTO>> getAttendanceTaskSupervisionResponseList() {
		return attendanceTaskSupervisionResponseList;
	}


	public void setAttendanceTaskSupervisionResponseList(
			SystemResponseDTO<List<StaffDailyAttendanceTaskSupervisionDTO>> attendanceTaskSupervisionResponseList) {
		this.attendanceTaskSupervisionResponseList = attendanceTaskSupervisionResponseList;
	}


	public SystemResponseDTO<SystemUserProfileDTO> getSystemUserProfileResponse() {
		return systemUserProfileResponse;
	}


	public void setSystemUserProfileResponse(SystemResponseDTO<SystemUserProfileDTO> systemUserProfileResponse) {
		this.systemUserProfileResponse = systemUserProfileResponse;
	}


	public SystemResponseDTO<List<SystemUserProfileDTO>> getSystemUserProfileResponseList() {
		return systemUserProfileResponseList;
	}


	public void setSystemUserProfileResponseList(
			SystemResponseDTO<List<SystemUserProfileDTO>> systemUserProfileResponseList) {
		this.systemUserProfileResponseList = systemUserProfileResponseList;
	}


	public SystemResponseDTO<SystemUserProfileSchoolDTO> getSystemUserProfileSchoolResponse() {
		return systemUserProfileSchoolResponse;
	}


	public void setSystemUserProfileSchoolResponse(
			SystemResponseDTO<SystemUserProfileSchoolDTO> systemUserProfileSchoolResponse) {
		this.systemUserProfileSchoolResponse = systemUserProfileSchoolResponse;
	}


	public SystemResponseDTO<List<SystemUserProfileSchoolDTO>> getSystemUserProfileSchoolResponseList() {
		return systemUserProfileSchoolResponseList;
	}


	public void setSystemUserProfileSchoolResponseList(
			SystemResponseDTO<List<SystemUserProfileSchoolDTO>> systemUserProfileSchoolResponseList) {
		this.systemUserProfileSchoolResponseList = systemUserProfileSchoolResponseList;
	}
	
	
	
	
	

	
}
