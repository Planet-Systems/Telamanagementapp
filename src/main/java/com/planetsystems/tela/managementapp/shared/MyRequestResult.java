package com.planetsystems.tela.managementapp.shared;

import java.util.List;

import com.gwtplatform.dispatch.rpc.shared.Result;
import com.planetsystems.tela.dto.AcademicYearDTO;
import com.planetsystems.tela.dto.SystemMenuDTO;
import com.planetsystems.tela.dto.SystemUserGroupDTO;
import com.planetsystems.tela.dto.response.SystemErrorDTO;
import com.planetsystems.tela.dto.response.SystemResponseDTO;

public class MyRequestResult implements Result {

	private static final long serialVersionUID = 1L;

	SystemResponseDTO<String> responseText;
	SystemResponseDTO<Integer> responseInteger;
	SystemResponseDTO<SystemUserGroupDTO> systemUserGroupResponse;
	SystemResponseDTO<List<SystemMenuDTO>> SystemMenuResponseList;
	SystemResponseDTO<AcademicYearDTO> academicYearResponse;
	SystemResponseDTO<List<AcademicYearDTO>> academicYearResponseList;
	
	



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





	public SystemResponseDTO<SystemUserGroupDTO> getSystemUserGroupResponse() {
		return systemUserGroupResponse;
	}





	public void setSystemUserGroupResponse(SystemResponseDTO<SystemUserGroupDTO> systemUserGroupResponse) {
		this.systemUserGroupResponse = systemUserGroupResponse;
	}





	public SystemResponseDTO<List<SystemMenuDTO>> getSystemMenuResponseList() {
		return SystemMenuResponseList;
	}





	public void setSystemMenuResponseList(SystemResponseDTO<List<SystemMenuDTO>> systemMenuResponseList) {
		SystemMenuResponseList = systemMenuResponseList;
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

	
	

	
	

}
