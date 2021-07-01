package com.planetsystems.tela.managementapp.shared;

import java.awt.List;

import com.gwtplatform.dispatch.rpc.shared.Result;
import com.planetsystems.tela.dto.SystemUserGroupDTO;
import com.planetsystems.tela.dto.response.SystemErrorDTO;
import com.planetsystems.tela.dto.response.SystemResponseDTO;

public class MyRequestResult implements Result {

	private static final long serialVersionUID = 1L;

	SystemResponseDTO<String> responseText;
	SystemResponseDTO<Integer> responseInteger;
	SystemResponseDTO<SystemUserGroupDTO> systemUserGroupResponseDTO;
//	SystemResponseDTO<List> responseList;



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



	public SystemResponseDTO<SystemUserGroupDTO> getSystemUserGroupResponseDTO() {
		return systemUserGroupResponseDTO;
	}



	public void setSystemUserGroupResponseDTO(SystemResponseDTO<SystemUserGroupDTO> systemUserGroupResponseDTO) {
		this.systemUserGroupResponseDTO = systemUserGroupResponseDTO;
	}
	
	

}
