package com.planetsystems.tela.managementapp.client.presenter.comboutils;

import java.util.LinkedHashMap;
import java.util.List;

import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.planetsystems.tela.dto.AcademicTermDTO;
import com.planetsystems.tela.dto.AcademicYearDTO;
import com.planetsystems.tela.dto.DistrictDTO;
import com.planetsystems.tela.dto.RegionDto;
import com.planetsystems.tela.dto.SchoolCategoryDTO;
import com.planetsystems.tela.dto.SchoolClassDTO;
import com.planetsystems.tela.dto.SchoolDTO;
import com.planetsystems.tela.dto.SchoolStaffDTO;
import com.planetsystems.tela.dto.SubjectCategoryDTO;
import com.planetsystems.tela.dto.SubjectDTO;
import com.planetsystems.tela.dto.SystemUserGroupDTO;
import com.planetsystems.tela.dto.response.SystemResponseDTO;
import com.planetsystems.tela.managementapp.client.gin.SessionManager;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkDataUtil;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkDataUtil2;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkResult;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkResult2;
import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.planetsystems.tela.managementapp.shared.MyRequestAction;
import com.planetsystems.tela.managementapp.shared.MyRequestResult;
import com.planetsystems.tela.managementapp.shared.RequestConstant;
import com.planetsystems.tela.managementapp.shared.RequestDelimeters;
import com.planetsystems.tela.managementapp.shared.RequestResult;
import com.planetsystems.tela.managementapp.shared.requestcommands.AcademicYearTermCommand;
import com.planetsystems.tela.managementapp.shared.requestcommands.RegionDistrictCommands;
import com.planetsystems.tela.managementapp.shared.requestcommands.SchoolCategoryClassCommand;
import com.planetsystems.tela.managementapp.shared.requestcommands.SubjectCategoryCommand;
import com.planetsystems.tela.managementapp.shared.requestcommands.SystemUserGroupRequestCommand;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.form.fields.MultiComboBoxItem;

public class ComboUtil2 {

	private String token = SessionManager.getInstance().getLoginToken();
	
	////////////////////////// ACADEMIC YEAR COMBOS

	public static void loadAcademicYearCombo(final ComboBox academicYearComboBox, final DispatchAsync dispatcher,
			final PlaceManager placeManager, final String defaultValue) {

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(MyRequestAction.COMMAND, AcademicYearTermCommand.GET_ALL_YEARS);
		map.put(MyRequestAction.TOKEN, SessionManager.getInstance().getLoginToken());

		NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

			@Override
			public void onNetworkResult(MyRequestResult result) {
				if (result != null) {
					SystemResponseDTO<List<AcademicYearDTO>> responseDTO = result.getAcademicYearResponseList();
					if (responseDTO.isStatus()) {
						if (responseDTO.getData() != null) {
							if(responseDTO.getData() != null) {
								LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();

								for (AcademicYearDTO academicYearDTO : responseDTO.getData()) {
									valueMap.put(academicYearDTO.getId(), academicYearDTO.getName());
								}

								academicYearComboBox.setValueMap(valueMap);

								if (defaultValue != null) {
									academicYearComboBox.setValue(defaultValue);
								}
							}
						
						}
					} else {
						SC.say("Unknow Error !!");
					}
				}
			}
		});

	}

	// ACADEMIC TERM COMBO
	public static void loadAcademicTermComboByAcademicYear(final ComboBox academicYearCombo,
			final ComboBox academicTermCombo, final DispatchAsync dispatcher, final PlaceManager placeManager,
			final String defaultValue) {

		String academicYearId = academicYearCombo.getValueAsString();
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestDelimeters.ACADEMIC_YEAR_ID, academicYearId);
		map.put(MyRequestAction.COMMAND, AcademicYearTermCommand.GET_ALL_TERMS_BY_YEAR);
		map.put(MyRequestAction.TOKEN, SessionManager.getInstance().getLoginToken());

		NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

			@Override
			public void onNetworkResult(MyRequestResult result) {

				if (result != null) {
					SystemResponseDTO<List<AcademicTermDTO>> responseDTO = result.getAcademicTermResponseList();
					if (responseDTO.isStatus()) {
						if(responseDTO.getData() != null) {
							LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();

							for (AcademicTermDTO academicTermDTO : responseDTO.getData()) {
								valueMap.put(academicTermDTO.getId(), academicTermDTO.getTerm());
							}
							academicTermCombo.setValueMap(valueMap);
							if (defaultValue != null) {
								academicTermCombo.setValue(defaultValue);
							}
						}
					
					} else {
						SC.say(responseDTO.getMessage());
					}
				}

			}
		});
	}

//	  if(result != null) { SystemResponseDTO<List<AcademicTermDTO>> responseDTO =
//	  result.getAcademicTermResponseList(); if(responseDTO.isStatus()) {
//	  
//	  }else { SC.say(responseDTO.getMessage()); } }

	public static void loadAcademicTermCombo(final ComboBox academicTermCombo, final DispatchAsync dispatcher,
			final PlaceManager placeManager, final String defaultValue) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(MyRequestAction.COMMAND, AcademicYearTermCommand.GET_ALL_TERMS);
		map.put(MyRequestAction.TOKEN, SessionManager.getInstance().getLoginToken());

		NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

			@Override
			public void onNetworkResult(MyRequestResult result) {

				if (result != null) {
					SystemResponseDTO<List<AcademicTermDTO>> responseDTO = result.getAcademicTermResponseList();
					if (responseDTO.isStatus()) {
						if(responseDTO.getData() !=null) {
							LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();

							for (AcademicTermDTO academicTermDTO : responseDTO.getData()) {
								valueMap.put(academicTermDTO.getId(), academicTermDTO.getTerm());
							}
							academicTermCombo.setValueMap(valueMap);
							if (defaultValue != null) {
								academicTermCombo.setValue(defaultValue);
							}
						}
						
					
					} else {
						SC.say(responseDTO.getMessage());
					}
				}

			}
		});

	}

	////////////////////////////// REGION COMBOS

	public static void loadRegionCombo(final ComboBox comboBox, final DispatchAsync dispatcher,
			final PlaceManager placeManager, final String defaultValue) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(MyRequestAction.TOKEN, SessionManager.getInstance().getLoginToken());
		if (SessionManager.getInstance().getLoggedInUserGroup().equalsIgnoreCase("Admin"))
			map.put(MyRequestAction.COMMAND, RegionDistrictCommands.GET_ALL_REGIONS);
		else
			map.put(MyRequestAction.COMMAND, RegionDistrictCommands.GET_REGIONS_BY_SYSTEM_USER_PROFILE_SCHOOLS);

		NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

			@Override
			public void onNetworkResult(MyRequestResult result) {
				if (result != null) {
					SystemResponseDTO<List<RegionDto>> responseDTO = result.getRegionResponseList();
					if (responseDTO.isStatus()) {

						if(responseDTO.getData() != null) {
							LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();
							for (RegionDto regionDto : responseDTO.getData()) {
								valueMap.put(regionDto.getId(), regionDto.getName());
							}
							comboBox.setValueMap(valueMap);
							
							if (defaultValue != null) {
								comboBox.setValue(defaultValue);
							}
						}
					} else {
						SC.say(responseDTO.getMessage());
					}
				}

			}
		});
	}

	//////////////////////////////////// DISTRICT COMBOS

	public static void loadDistrictCombo(final ComboBox districtCombo, final DispatchAsync dispatcher,
			final PlaceManager placeManager, final String defaultValue) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(MyRequestAction.TOKEN, SessionManager.getInstance().getLoginToken());

		if (SessionManager.getInstance().getLoggedInUserGroup().equalsIgnoreCase("Admin"))
			map.put(MyRequestAction.COMMAND, RegionDistrictCommands.GET_ALL_DISTRICTS);
		else
			map.put(MyRequestAction.COMMAND, RegionDistrictCommands.GET_DISTRICTS_BY_SYSTEM_USER_PROFILE_SCHOOLS);

		NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

			@Override
			public void onNetworkResult(MyRequestResult result) {
				if (result != null) {
					SystemResponseDTO<List<DistrictDTO>> responseDTO = result.getDistrictResponseList();
					if (responseDTO.isStatus()) {
						if(responseDTO.getData() != null) {
							LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();

							for (DistrictDTO districtDTO : responseDTO.getData()) {
								valueMap.put(districtDTO.getId(), districtDTO.getName());
							}
							districtCombo.setValueMap(valueMap);
							if (defaultValue != null) {
								districtCombo.setValue(defaultValue);
							}
						}
					} else {
						SC.say(responseDTO.getMessage());
					}
				}
				
			}
		});

	}

	public static void loadDistrictComboByRegion(final ComboBox regionCombo, final ComboBox districtCombo,
			final DispatchAsync dispatcher, final PlaceManager placeManager, final String defaultValue) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestDelimeters.REGION_ID, regionCombo.getValueAsString());
		map.put(MyRequestAction.TOKEN, SessionManager.getInstance().getLoginToken());

		if (SessionManager.getInstance().getLoggedInUserGroup().equalsIgnoreCase(SessionManager.ADMIN))
			map.put(MyRequestAction.COMMAND, RegionDistrictCommands.GET_ALL_DISTRICTS_BY_REGION);
		else
			map.put(MyRequestAction.COMMAND, RegionDistrictCommands.GET_DISTRICTS_BY_SYSTEM_USER_PROFILE_SCHOOLS);

		NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

			@Override
			public void onNetworkResult(MyRequestResult result) {
				if (result != null) {
					SystemResponseDTO<List<DistrictDTO>> responseDTO = result.getDistrictResponseList();
					if (responseDTO.isStatus()) {
						if(responseDTO.getData() != null) {
							LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();

							for (DistrictDTO districtDTO : responseDTO.getData()) {
								valueMap.put(districtDTO.getId(), districtDTO.getName());
							}
							districtCombo.setValueMap(valueMap);
							if (defaultValue != null) {
								districtCombo.setValue(defaultValue);
							}
						}
					} else {
						SC.say(responseDTO.getMessage());
					}
				}
			}
		});
	}

	///////////////////////////// SCHOOL COMBOS

	public static void loadSchoolCombo(final ComboBox schoolCombo, final DispatchAsync dispatcher,
			final PlaceManager placeManager, final String defaultValue) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(MyRequestAction.COMMAND, SchoolCategoryClassCommand.GET_ALL_SCHOOLS);
		map.put(MyRequestAction.TOKEN, SessionManager.getInstance().getLoginToken());

		NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

			@Override
			public void onNetworkResult(MyRequestResult result) {
				if (result != null) {
					SystemResponseDTO<List<SchoolDTO>> responseDTO = result.getSchoolResponseList();
					if (responseDTO.isStatus()) {
                        if(responseDTO.getData() != null) {
                        	LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();

            				for (SchoolDTO schoolDTO : responseDTO.getData()) {
            					valueMap.put(schoolDTO.getId(), schoolDTO.getName());
            				}
            				schoolCombo.setValueMap(valueMap);
            				if (defaultValue != null) {
            					schoolCombo.setValue(defaultValue);
            				}
                        }
					} else {
						SC.say(responseDTO.getMessage());
					}
				}

			}
		});
	}

	public static void loadSchoolComboByDistrict(final ComboBox districtCombo, final ComboBox schoolCombo,
			final DispatchAsync dispatcher, final PlaceManager placeManager, final String defaultValue) {
		String districtId = districtCombo.getValueAsString();
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestDelimeters.DISTRICT_ID, districtId);
		map.put(MyRequestAction.COMMAND, SchoolCategoryClassCommand.GET_ALL_SCHOOLS_BY_SCHOOL_DISTRICT);
		map.put(MyRequestAction.TOKEN, SessionManager.getInstance().getLoginToken());

		NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

			@Override
			public void onNetworkResult(MyRequestResult result) {
				if (result != null) {
					SystemResponseDTO<List<SchoolDTO>> responseDTO = result.getSchoolResponseList();
					if (responseDTO.isStatus()) {
                        if(responseDTO.getData() != null) {
                        	LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();

            				for (SchoolDTO schoolDTO : responseDTO.getData()) {
            					valueMap.put(schoolDTO.getId(), schoolDTO.getName());
            				}
            				schoolCombo.setValueMap(valueMap);
            				if (defaultValue != null) {
            					schoolCombo.setValue(defaultValue);
            				}
                        }
					} else {
						SC.say(responseDTO.getMessage());
					}
				}
			}
		});

	}

	//////////////////// SCHOOL STAFF COMBOS
	public static void loadSchoolStaffComboBySchool(final ComboBox schoolCombo, final ComboBox schoolStaffCombo,
			final DispatchAsync dispatcher, final PlaceManager placeManager, final String defaultValue) {

		String schoolId = schoolCombo.getValueAsString();
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestDelimeters.SCHOOL_ID, schoolId);
		map.put(NetworkDataUtil.ACTION, RequestConstant.GET_STAFFS_IN_SCHOOL);

		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {
				LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();

				for (SchoolStaffDTO schoolStaffDTO : result.getSchoolStaffDTOs()) {
					String fullName = schoolStaffDTO.getGeneralUserDetailDTO().getFirstName() + " "
							+ schoolStaffDTO.getGeneralUserDetailDTO().getLastName();
					valueMap.put(schoolStaffDTO.getId(), fullName);
				}
				schoolStaffCombo.setValueMap(valueMap);

				if (defaultValue != null) {
					schoolStaffCombo.setValue(defaultValue);
				}
			}
		});
	}

	public static void loadSchoolStaffMultiComboBySchool(final ComboBox schoolCombo,
			final MultiComboBoxItem schoolStaffCombo, final DispatchAsync dispatcher, final PlaceManager placeManager,
			final String defaultValue) {

		String schoolId = schoolCombo.getValueAsString();
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestDelimeters.SCHOOL_ID, schoolId);
		map.put(NetworkDataUtil.ACTION, RequestConstant.GET_STAFFS_IN_SCHOOL);

		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {
				LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();

				for (SchoolStaffDTO schoolStaffDTO : result.getSchoolStaffDTOs()) {
					String fullName = schoolStaffDTO.getGeneralUserDetailDTO().getFirstName() + " "
							+ schoolStaffDTO.getGeneralUserDetailDTO().getLastName();
					valueMap.put(schoolStaffDTO.getId(), fullName);
				}
				schoolStaffCombo.setValueMap(valueMap);

				if (defaultValue != null) {
					schoolStaffCombo.setValue(defaultValue);
				}
			}
		});
	}

	public static void loadSchoolStaffCombo(final ComboBox SchoolStaffCombo, final DispatchAsync dispatcher,
			final PlaceManager placeManager, final String defaultValue) {

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.GET_SCHOOL_STAFF, null);
		map.put(NetworkDataUtil.ACTION, RequestConstant.GET_SCHOOL_STAFF);
		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {
				LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();

				for (SchoolStaffDTO schoolStaffDTO : result.getSchoolStaffDTOs()) {
					String fullName = schoolStaffDTO.getGeneralUserDetailDTO().getFirstName()
							+ schoolStaffDTO.getGeneralUserDetailDTO().getLastName();
					valueMap.put(schoolStaffDTO.getId(), fullName);
				}
				SchoolStaffCombo.setValueMap(valueMap);

				if (defaultValue != null) {
					SchoolStaffCombo.setValue(defaultValue);
				}
			}
		});
	}

	/////////////////////// SCHOOL CATEGORY COMBOS

	public static void loadSchoolCategoryCombo(final ComboBox schoolCategoryCombo, final DispatchAsync dispatcher,
			final PlaceManager placeManager, final String defaultValue) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(MyRequestAction.COMMAND, SchoolCategoryClassCommand.GET_ALL_SCHOOL_CATEGORYS);
		map.put(MyRequestAction.TOKEN,	SessionManager.getInstance().getLoginToken());

		NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

			@Override
			public void onNetworkResult(MyRequestResult result) {
				
				if (result != null) {
					SystemResponseDTO<List<SchoolCategoryDTO>> responseDTO = result.getSchoolCategoryResponseList();
					if (responseDTO.isStatus()) {
                        if(responseDTO.getData() != null) {
            				LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();

            				for (SchoolCategoryDTO schoolCategoryDTO : responseDTO.getData()) {
            					valueMap.put(schoolCategoryDTO.getId(), schoolCategoryDTO.getName());
            				}

            				schoolCategoryCombo.setValueMap(valueMap);

            				if (defaultValue != null) {
            					schoolCategoryCombo.setValue(defaultValue);
            				}
                        }
					} else {
						SC.say(responseDTO.getMessage());
					}
				}
			}
		});
	}

	///////////////////////////// SUBJECT CATEGORY

	public static void loadSubjectCategoryCombo(final ComboBox subjectCategoryCombo, final DispatchAsync dispatcher,
			final PlaceManager placeManager, final String defaultValue) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(MyRequestAction.TOKEN, SessionManager.getInstance().getLoginToken());
		map.put(MyRequestAction.COMMAND, SubjectCategoryCommand.GET_ALL_SUBJECT_CATEGORYS);

		NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

			@Override
			public void onNetworkResult(MyRequestResult result) {
				if (result != null) {
					SystemResponseDTO<List<SubjectCategoryDTO>> responseDTO = result.getSubjectCategoryResponseList();
					if (responseDTO.isStatus()) {
                        if(responseDTO.getData() != null) {
                        	LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();

            				for (SubjectCategoryDTO subjectCategoryDTO : responseDTO.getData()) {
            					valueMap.put(subjectCategoryDTO.getId(), subjectCategoryDTO.getName());
            				}
            				subjectCategoryCombo.setValueMap(valueMap);
            				if (defaultValue != null) {
            					subjectCategoryCombo.setValue(defaultValue);
            				}
                        }
					} else {
						SC.say(responseDTO.getMessage());
					}
				}
		
			}
		});
	}

	////////////////////////////////////////// SCHOOL CLASS COMBOS

	public static void loadSchoolClassCombo(final ComboBox schoolClassCombo, final DispatchAsync dispatcher,
			final PlaceManager placeManager, final String defaultValue) {

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(MyRequestAction.TOKEN, SessionManager.getInstance().getLoginToken());
		map.put(MyRequestAction.COMMAND, SchoolCategoryClassCommand.GET_ALL_SCHOOL_CLASSES);

		NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

			@Override
			public void onNetworkResult(MyRequestResult result) {
				
				if (result != null) {
					SystemResponseDTO<List<SchoolClassDTO>> responseDTO = result.getSchoolClassResponseList();
					if (responseDTO.isStatus()) {
                        if(responseDTO.getData() != null) {
                        	LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();

            				for (SchoolClassDTO schoolClassDTO : responseDTO.getData()) {
            					valueMap.put(schoolClassDTO.getId(), schoolClassDTO.getName());
            				}
            				schoolClassCombo.setValueMap(valueMap);
            				if (defaultValue != null) {
            					schoolClassCombo.setValue(defaultValue);
            				}
                        }
					} else {
						SC.say(responseDTO.getMessage());
					}
				}
		
			}
		});
	}

	public static void loadSchoolClassesComboBySchool(final ComboBox schoolCombo, final ComboBox schoolClassComboBox,
			final DispatchAsync dispatcher, final PlaceManager placeManager, final String defaultValue) {

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestDelimeters.SCHOOL_ID, schoolCombo.getValueAsString());
		map.put(MyRequestAction.TOKEN, SessionManager.getInstance().getLoginToken());
		map.put(MyRequestAction.COMMAND, SchoolCategoryClassCommand.GET_ALL_SCHOOL_CLASSES_BY_SCHOOL);

		NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

			@Override
			public void onNetworkResult(MyRequestResult result) {
				
				if (result != null) {
					SystemResponseDTO<List<SchoolClassDTO>> responseDTO = result.getSchoolClassResponseList();
					if (responseDTO.isStatus()) {
                        if(responseDTO.getData() != null) {
                        	LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();

            				for (SchoolClassDTO schoolClassDTO : responseDTO.getData()) {
            					valueMap.put(schoolClassDTO.getId(), schoolClassDTO.getName());
            				}
            				schoolClassComboBox.setValueMap(valueMap);

            				if (defaultValue != null) {
            					schoolClassComboBox.setValue(defaultValue);
            				}
                        }
					} else {
						SC.say(responseDTO.getMessage());
					}
				}
		
			}
		});
	}

	@Deprecated
	public static void loadSchoolClassesComboBySchoolAcademicTerm(final ComboBox academicTermCombo,
			final ComboBox schoolCombo, final ComboBox schoolClassCombo, final DispatchAsync dispatcher,
			final PlaceManager placeManager, final String defaultValue) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestDelimeters.SCHOOL_ID, schoolCombo.getValueAsString());
		map.put(RequestDelimeters.ACADEMIC_TERM_ID, academicTermCombo.getValueAsString());
		map.put(NetworkDataUtil.ACTION, RequestConstant.GET_SCHOOL_CLASSES_IN_SCHOOL_ACADEMIC_TERM);

		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {
				LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();

				for (SchoolClassDTO schoolClassDTO : result.getSchoolClassDTOs()) {
					valueMap.put(schoolClassDTO.getId(), schoolClassDTO.getName());
				}
				schoolClassCombo.setValueMap(valueMap);

				if (defaultValue != null) {
					schoolClassCombo.setValue(defaultValue);
				}
			}
		});

	}

////////////////////////////////////SUBJECT COMBOS
	public static void loadSubjectCombo(final ComboBox subjectCombo, final DispatchAsync dispatcher,
			final PlaceManager placeManager, final String defaultValue) {

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(MyRequestAction.TOKEN, SessionManager.getInstance().getLoginToken());
		map.put(MyRequestAction.COMMAND, SubjectCategoryCommand.GET_ALL_SUBJECTS);
		
		NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

			@Override
			public void onNetworkResult(MyRequestResult result) {
				if (result != null) {
					SystemResponseDTO<List<SubjectDTO>> responseDTO = result.getSubjectResponseList();
					if (responseDTO.isStatus()) {
                        if(responseDTO.getData() != null) {
                        	LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();

            				for (SubjectDTO subjectDTO : responseDTO.getData()) {
            					valueMap.put(subjectDTO.getId(), subjectDTO.getName());
            				}
            				subjectCombo.setValueMap(valueMap);

            				if (defaultValue != null) {
            					subjectCombo.setValue(defaultValue);
            				}
                        }
					} else {
						SC.say(responseDTO.getMessage());
					}
				}
	
			}
		});
	}
	
	

	public static void loadSystemUserGroupCombo(final ComboBox systemUserGroupCombo, DispatchAsync dispatcher,
			PlaceManager placeManager, final String defaultValue) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(NetworkDataUtil.ACTION, SystemUserGroupRequestCommand.GET_SYSTEM_USER_GROUPS);
		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {
				LinkedHashMap<String, String> hashMap = new LinkedHashMap<>();
				for (SystemUserGroupDTO dto : result.getSystemUserGroupDTOs()) {
					hashMap.put(dto.getId(), dto.getName());
				}
				systemUserGroupCombo.setValueMap(hashMap);
				if (defaultValue != null) {
					systemUserGroupCombo.setValue(defaultValue);
				}
			}
		});

	}

}
