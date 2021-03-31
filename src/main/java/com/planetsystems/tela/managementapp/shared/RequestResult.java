package com.planetsystems.tela.managementapp.shared;

import java.util.List;

import com.gwtplatform.dispatch.rpc.shared.Result;
import com.planetsystems.tela.dto.AbsentDTO;
import com.planetsystems.tela.dto.AcademicTermDTO;
import com.planetsystems.tela.dto.AcademicYearDTO;
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
import com.planetsystems.tela.dto.StaffDailyAttendanceDTO;
import com.planetsystems.tela.dto.StaffDailyAttendanceSupervisionDTO;
import com.planetsystems.tela.dto.StaffDailyAttendanceTaskDTO;
import com.planetsystems.tela.dto.StaffDailyAttendanceTaskSupervisionDTO;
import com.planetsystems.tela.dto.StaffEnrollmentDto;
import com.planetsystems.tela.dto.SubjectCategoryDTO;
import com.planetsystems.tela.dto.SubjectDTO;
import com.planetsystems.tela.dto.SystemErrorDTO;
import com.planetsystems.tela.dto.SystemFeedbackDTO;
import com.planetsystems.tela.dto.SystemUserDTO;
import com.planetsystems.tela.dto.TimeAttendanceSupervisionDTO;
import com.planetsystems.tela.dto.TimeTableDTO;
import com.planetsystems.tela.dto.TimeTableLessonDTO;
import com.planetsystems.tela.dto.TokenFeedbackDTO;

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
    private List<StaffDailyAttendanceDTO> staffDailyAttendanceDTOs;
    private List<StaffDailyAttendanceTaskDTO> staffDailyAttendanceTaskDTOs;
    private List<StaffDailyAttendanceSupervisionDTO> staffDailyAttendanceSupervisionDTOs;
    private List<StaffDailyAttendanceTaskSupervisionDTO> staffDailyAttendanceTaskSupervisionDTOs;
    private List<TimeAttendanceSupervisionDTO> timeAttendanceSupervisionDTOs;



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RequestResult(SystemFeedbackDTO systemFeedbackDTO) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
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

	public RequestResult(SystemFeedbackDTO systemFeedbackDTO, List<TimeTableDTO> timeTableDTOs , TimeTableDTO tableDTO) {

		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.timeTableDTOs = timeTableDTOs;
	}



	public RequestResult(SystemFeedbackDTO systemFeedbackDTO, List<SystemUserDTO> systemUserDTOs , SystemUserDTO dto) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.systemUserDTOs = systemUserDTOs;
	}
	

	public RequestResult(SystemFeedbackDTO systemFeedbackDTO, List<TimeTableLessonDTO> tableLessonDTOs  ,TimeTableLessonDTO dto) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.tableLessonDTOs = tableLessonDTOs;
	}


	
	public RequestResult(SystemFeedbackDTO systemFeedbackDTO, List<StaffDailyAttendanceDTO> staffDailyAttendanceDTOs , StaffDailyAttendanceDTO dailyAttendanceDTO) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.staffDailyAttendanceDTOs = staffDailyAttendanceDTOs;
	}
	

	public RequestResult(SystemFeedbackDTO systemFeedbackDTO,
			List<StaffDailyAttendanceSupervisionDTO> staffDailyAttendanceSupervisionDTOs , StaffDailyAttendanceSupervisionDTO dto) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.staffDailyAttendanceSupervisionDTOs = staffDailyAttendanceSupervisionDTOs;
	}
		

	public RequestResult(SystemFeedbackDTO systemFeedbackDTO,
			List<StaffDailyAttendanceTaskSupervisionDTO> staffDailyAttendanceTaskSupervisionDTOs , StaffDailyAttendanceTaskSupervisionDTO dto) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.staffDailyAttendanceTaskSupervisionDTOs = staffDailyAttendanceTaskSupervisionDTOs;
	}

	
	public RequestResult(SystemFeedbackDTO systemFeedbackDTO,
			List<StaffDailyAttendanceTaskDTO> staffDailyAttendanceTaskDTOs , StaffDailyAttendanceTaskDTO dto) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.staffDailyAttendanceTaskDTOs = staffDailyAttendanceTaskDTOs;
	}
	

	public RequestResult(SystemFeedbackDTO systemFeedbackDTO,
			List<TimeAttendanceSupervisionDTO> timeAttendanceSupervisionDTOs , TimeAttendanceSupervisionDTO dto) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.timeAttendanceSupervisionDTOs = timeAttendanceSupervisionDTOs;
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


	
	public List<StaffDailyAttendanceDTO> getStaffDailyAttendanceDTOs() {
		return staffDailyAttendanceDTOs;
	}
	
	
 
	
	
	public List<StaffDailyAttendanceTaskDTO> getStaffDailyAttendanceTaskDTOs() {
		return staffDailyAttendanceTaskDTOs;
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


	@Override
	public String toString() {
		return "RequestResult [systemErrorDTO=" + systemErrorDTO + "FEEDDTO " + systemFeedbackDTO + " ]";
	}

}
