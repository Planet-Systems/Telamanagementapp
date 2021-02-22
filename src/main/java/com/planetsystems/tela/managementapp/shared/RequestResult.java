package com.planetsystems.tela.managementapp.shared;

import java.util.List;

import com.gwtplatform.dispatch.rpc.shared.Result;
import com.planetsystems.tela.dto.AcademicTermDTO;
import com.planetsystems.tela.dto.AcademicYearDTO;
import com.planetsystems.tela.dto.DistrictDTO;
import com.planetsystems.tela.dto.RegionDto;
import com.planetsystems.tela.dto.SchoolCategoryDTO;
import com.planetsystems.tela.dto.SchoolClassDTO;
import com.planetsystems.tela.dto.SchoolDTO;
import com.planetsystems.tela.dto.SubjectCategoryDTO;
import com.planetsystems.tela.dto.SubjectDTO;
import com.planetsystems.tela.dto.SystemErrorDTO;
import com.planetsystems.tela.dto.SystemFeedbackDTO;
import com.planetsystems.tela.dto.TokenFeedbackDTO;

public class RequestResult implements Result  {
	
	private SystemFeedbackDTO systemFeedbackDTO;
	private TokenFeedbackDTO tokenFeedbackDTO;
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


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	

	public RequestResult(SystemFeedbackDTO systemFeedbackDTO, List<AcademicYearDTO> academicYearDTOs , AcademicYearDTO dto) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.academicYearDTOs = academicYearDTOs;
	}
	
	
	public RequestResult(SystemFeedbackDTO systemFeedbackDTO, List<AcademicTermDTO> academicTermDTOs , AcademicTermDTO dto) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.academicTermDTOs = academicTermDTOs;
	}
	

	public RequestResult(SystemFeedbackDTO systemFeedbackDTO, List<RegionDto> regionDtos , RegionDto dto) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.regionDtos = regionDtos;
	}

	public RequestResult(SystemFeedbackDTO systemFeedbackDTO, List<DistrictDTO> districtDTOs , DistrictDTO dto) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.districtDTOs = districtDTOs;
	}

	public RequestResult(SystemFeedbackDTO systemFeedbackDTO, List<SchoolCategoryDTO> schoolCategoryDTOs , SchoolCategoryDTO dto) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.schoolCategoryDTOs = schoolCategoryDTOs;
	}
	

	public RequestResult(SystemFeedbackDTO systemFeedbackDTO, List<SchoolDTO> schoolDTOs , SchoolDTO dto) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.schoolDTOs = schoolDTOs;
	}


	public RequestResult(SystemFeedbackDTO systemFeedbackDTO, List<SchoolClassDTO> schoolClassDTOs , SchoolClassDTO dto) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.schoolClassDTOs = schoolClassDTOs;
	}
	
	
	public RequestResult(SystemFeedbackDTO systemFeedbackDTO, List<SubjectCategoryDTO> subjectCategoryDTOs , SubjectCategoryDTO dto) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.subjectCategoryDTOs = subjectCategoryDTOs;
	}

	
	

	public RequestResult(SystemFeedbackDTO systemFeedbackDTO, List<SubjectDTO> subjectDTOs , SubjectDTO dto) {
		super();
		this.systemFeedbackDTO = systemFeedbackDTO;
		this.subjectDTOs = subjectDTOs;
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


	
	
	
	
	

	


}