package com.planetsystems.tela.managementapp.shared;

import java.util.List;

import com.gwtplatform.dispatch.rpc.shared.Result;
import com.planetsystems.tela.dto.AcademicTermDTO;
import com.planetsystems.tela.dto.AcademicYearDTO;
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
import com.planetsystems.tela.dto.SystemMenuDTO;
import com.planetsystems.tela.dto.SystemUserGroupDTO;
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

	
	

	
	
	
	
	
	
	
}
