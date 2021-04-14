package com.planetsystems.tela.managementapp.client.presenter.comboutils;

import java.util.LinkedHashMap;

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
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkDataUtil;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkResult;
import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.planetsystems.tela.managementapp.shared.RequestConstant;
import com.planetsystems.tela.managementapp.shared.RequestDelimeters;
import com.planetsystems.tela.managementapp.shared.RequestResult;

public class ComboUtil {

	////////////////////////// ACADEMIC YEAR COMBOS

	public static void loadAcademicYearCombo(final ComboBox academicYearComboBox, final DispatchAsync dispatcher,
			final PlaceManager placeManager, final String defaultValue) {

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(NetworkDataUtil.ACTION, RequestConstant.GET_ACADEMIC_YEAR);

		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {
				if (result != null) {
					LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();

					for (AcademicYearDTO academicYearDTO : result.getAcademicYearDTOs()) {
						valueMap.put(academicYearDTO.getId(), academicYearDTO.getName());
					}

					academicYearComboBox.setValueMap(valueMap);

					if (defaultValue != null) {
						academicYearComboBox.setValue(defaultValue);
					}
				}
			}
		});

//		SC.showPrompt("", "", new SwizimaLoader());
//
//		dispatcher.execute(new RequestAction(RequestConstant.GET_ACADEMIC_YEAR, map),
//				new AsyncCallback<RequestResult>() {
//					public void onFailure(Throwable caught) {
//						System.out.println(caught.getMessage());
//						SC.warn("ERROR", caught.getMessage());
//						GWT.log("ERROR " + caught.getMessage());
//						SC.clearPrompt();
//					}
//
//					public void onSuccess(RequestResult result) {
//						SC.clearPrompt();
//						SessionManager.getInstance().manageSession(result, placeManager);
//
//						if (result != null) {
//							GWT.log("PRESENTER LIST " + result.getAcademicYearDTOs());
//							LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();
//
//							for (AcademicYearDTO academicYearDTO : result.getAcademicYearDTOs()) {
//								valueMap.put(academicYearDTO.getId(), academicYearDTO.getName());
//							}
//
//							academicYearComboBox.setValueMap(valueMap);
//
//							if (defaultValue != null) {
//								academicYearComboBox.setValue(defaultValue);
//							}
//
//						} else {
//							SC.warn("ERROR", "Service Down");
//						}
//
//					}
//				});
	}

	// ACADEMIC TERM COMBO
	public static void loadAcademicTermComboByAcademicYear(final ComboBox academicYearCombo,
			final ComboBox academicTermCombo, final DispatchAsync dispatcher, final PlaceManager placeManager,
			final String defaultValue) {

		String academicYearId = academicYearCombo.getValueAsString();
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestDelimeters.ACADEMIC_YEAR_ID, academicYearId);
		map.put(NetworkDataUtil.ACTION, RequestConstant.GET_ACADEMIC_TERMS_IN_ACADEMIC_YEAR);

		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {
				LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();

				for (AcademicTermDTO academicTermDTO : result.getAcademicTermDTOs()) {
					valueMap.put(academicTermDTO.getId(), academicTermDTO.getTerm());
				}
				academicTermCombo.setValueMap(valueMap);
				if (defaultValue != null) {
					academicTermCombo.setValue(defaultValue);
				}
			}
		});

//		SC.showPrompt("", "", new SwizimaLoader());
//
//		dispatcher.execute(new RequestAction(RequestConstant.GET_ACADEMIC_TERMS_IN_ACADEMIC_YEAR, map),
//				new AsyncCallback<RequestResult>() {
//					public void onFailure(Throwable caught) {
//						System.out.println(caught.getMessage());
//						SC.warn("ERROR", caught.getMessage());
//						GWT.log("ERROR " + caught.getMessage());
//						SC.clearPrompt();
//					}
//
//					public void onSuccess(RequestResult result) {
//						SC.clearPrompt();
//						SessionManager.getInstance().manageSession(result, placeManager);
//
//						if (result != null) {
//
//							LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();
//
//							for (AcademicTermDTO academicTermDTO : result.getAcademicTermDTOs()) {
//								valueMap.put(academicTermDTO.getId(), academicTermDTO.getTerm());
//							}
//							academicTermCombo.setValueMap(valueMap);
//							if (defaultValue != null) {
//								academicTermCombo.setValue(defaultValue);
//							}
//
//						} else {
//							SC.warn("ERROR", "Unknow error");
//						}
//
//					}
//				});

	}

	public static void loadAcademicTermCombo(final ComboBox academicTermCombo, final DispatchAsync dispatcher,
			final PlaceManager placeManager, final String defaultValue) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.GET_ACADEMIC_TERM, null);
		map.put(NetworkDataUtil.ACTION, RequestConstant.GET_ACADEMIC_TERM);

		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {
				LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();

				for (AcademicTermDTO academicTermDTO : result.getAcademicTermDTOs()) {
					valueMap.put(academicTermDTO.getId(), academicTermDTO.getTerm());
				}
				academicTermCombo.setValueMap(valueMap);
				if (defaultValue != null) {
					academicTermCombo.setValue(defaultValue);
				}
			}
		});

//		SC.showPrompt("", "", new SwizimaLoader());
//
//		dispatcher.execute(new RequestAction(RequestConstant.GET_ACADEMIC_TERM, map),
//				new AsyncCallback<RequestResult>() {
//
//					@Override
//					public void onFailure(Throwable caught) {
//						System.out.println(caught.getMessage());
//						SC.warn("ERROR", caught.getMessage());
//						GWT.log("ERROR " + caught.getMessage());
//						SC.clearPrompt();
//
//					}
//
//					@Override
//					public void onSuccess(RequestResult result) {
//
//						SC.clearPrompt();
//						SessionManager.getInstance().manageSession(result, placeManager);
//						if (result != null) {
//
//							if (result.getSystemFeedbackDTO() != null) {
//								LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();
//
//								for (AcademicTermDTO academicTermDTO : result.getAcademicTermDTOs()) {
//									valueMap.put(academicTermDTO.getId(), academicTermDTO.getTerm());
//								}
//								academicTermCombo.setValueMap(valueMap);
//								if (defaultValue != null) {
//									academicTermCombo.setValue(defaultValue);
//								}
//							}
//						} else {
//							SC.warn("ERROR", "Unknow error");
//						}
//
//					}
//				});

	}

	////////////////////////////// REGION COMBOS

	public static void loadRegionCombo(final ComboBox comboBox, final DispatchAsync dispatcher,
			final PlaceManager placeManager, final String defaultValue) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.GET_REGION, null);
		map.put(NetworkDataUtil.ACTION, RequestConstant.GET_REGION);

		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {
				LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();

				for (RegionDto regionDto : result.getRegionDtos()) {
					valueMap.put(regionDto.getId(), regionDto.getName());
				}
				comboBox.setValueMap(valueMap);

				if (defaultValue != null) {
					comboBox.setValue(defaultValue);
				}
			}
		});
//		
//		SC.showPrompt("", "", new SwizimaLoader());
//
//		dispatcher.execute(new RequestAction(RequestConstant.GET_REGION, map), new AsyncCallback<RequestResult>() {
//
//			@Override
//			public void onFailure(Throwable caught) {
//				System.out.println(caught.getMessage());
//				SC.warn("ERROR", caught.getMessage());
//				GWT.log("ERROR " + caught.getMessage());
//				SC.clearPrompt();
//
//			}
//
//			@Override
//			public void onSuccess(RequestResult result) {
//
//				SC.clearPrompt();
//				SessionManager.getInstance().manageSession(result, placeManager);
//
//				if (result != null) {
//
//					SystemFeedbackDTO feedbackDTO = result.getSystemFeedbackDTO();
//					if (feedbackDTO != null) {
//						if (result.getSystemFeedbackDTO().isResponse()) {
//							LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();
//
//							for (RegionDto regionDto : result.getRegionDtos()) {
//								valueMap.put(regionDto.getId(), regionDto.getName());
//							}
//							comboBox.setValueMap(valueMap);
//
//							if (defaultValue != null) {
//								comboBox.setValue(defaultValue);
//							}
//
//						} else {
//							SC.warn("Not Successful \n ERROR:", result.getSystemFeedbackDTO().getMessage());
//						}
//					}
//				} else {
//					SC.warn("ERROR", "Service Down");
//					// SC.warn("ERROR", "Unknown error");
//				}
//
//			}
//		});
	}

	//////////////////////////////////// DISTRICT COMBOS

	public static void loadDistrictCombo(final ComboBox districtCombo, final DispatchAsync dispatcher,
			final PlaceManager placeManager, final String defaultValue) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.GET_DISTRICT, null);
		map.put(NetworkDataUtil.ACTION, RequestConstant.GET_DISTRICT);

		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {
				LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();

				for (DistrictDTO districtDTO : result.getDistrictDTOs()) {
					valueMap.put(districtDTO.getId(), districtDTO.getName());
				}
				districtCombo.setValueMap(valueMap);
				if (defaultValue != null) {
					districtCombo.setValue(defaultValue);
				}
			}
		});

//		SC.showPrompt("", "", new SwizimaLoader());
//		dispatcher.execute(new RequestAction(RequestConstant.GET_DISTRICT, map), new AsyncCallback<RequestResult>() {
//
//			@Override
//			public void onFailure(Throwable caught) {
//				System.out.println(caught.getMessage());
//				SC.warn("ERROR", caught.getMessage());
//				GWT
//
//						.log("ERROR " + caught.getMessage());
//				SC.clearPrompt();
//
//			}
//
//			@Override
//			public void onSuccess(RequestResult result) {
//
//				SC.clearPrompt();
//				SessionManager.getInstance().manageSession(result, placeManager);
//				if (result != null) {
//
//					if (result.getSystemFeedbackDTO() != null) {
//						LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();
//
//						for (DistrictDTO districtDTO : result.getDistrictDTOs()) {
//							valueMap.put(districtDTO.getId(), districtDTO.getName());
//						}
//						districtCombo.setValueMap(valueMap);
//						if (defaultValue != null) {
//							districtCombo.setValue(defaultValue);
//						}
//					}
//				} else {
//					SC.warn("ERROR", "Unknow error");
//				}
//
//			}
//		});
	}

	public static void loadDistrictComboByRegion(final ComboBox regionCombo, final ComboBox districtCombo,
			final DispatchAsync dispatcher, final PlaceManager placeManager, final String defaultValue) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestDelimeters.REGION_ID, regionCombo.getValueAsString());
		map.put(NetworkDataUtil.ACTION, RequestConstant.GET_DISTRICTS_IN_REGION);
		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {
				LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();

				for (DistrictDTO districtDTO : result.getDistrictDTOs()) {
					valueMap.put(districtDTO.getId(), districtDTO.getName());
				}
				districtCombo.setValueMap(valueMap);
				if (defaultValue != null) {
					districtCombo.setValue(defaultValue);
				}
			}
		});

//		SC.showPrompt("", "", new SwizimaLoader());
//		dispatcher.execute(new RequestAction(RequestConstant.GET_DISTRICTS_IN_REGION, map),
//				new AsyncCallback<RequestResult>() {
//
//					@Override
//					public void onFailure(Throwable caught) {
//						System.out.println(caught.getMessage());
//						SC.warn("ERROR", caught.getMessage());
//						GWT
//
//								.log("ERROR " + caught.getMessage());
//						SC.clearPrompt();
//
//					}
//
//					@Override
//					public void onSuccess(RequestResult result) {
//
//						SC.clearPrompt();
//						SessionManager.getInstance().manageSession(result, placeManager);
//						if (result != null) {
//
//							if (result.getSystemFeedbackDTO() != null) {
//								LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();
//
//								for (DistrictDTO districtDTO : result.getDistrictDTOs()) {
//									valueMap.put(districtDTO.getId(), districtDTO.getName());
//								}
//								districtCombo.setValueMap(valueMap);
//								if (defaultValue != null) {
//									districtCombo.setValue(defaultValue);
//								}
//							}
//						} else {
//							SC.warn("ERROR", "Unknow error");
//						}
//
//					}
//				});
	}

	///////////////////////////// SCHOOL COMBOS

	public static void loadSchoolCombo(final ComboBox schoolCombo, final DispatchAsync dispatcher,
			final PlaceManager placeManager, final String defaultValue) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.GET_SCHOOLS, null);
		map.put(NetworkDataUtil.ACTION, RequestConstant.GET_SCHOOLS);

//		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
//		SC.showPrompt("", "", new SwizimaLoader());
//
//		dispatcher.execute(new RequestAction(RequestConstant.GET_SCHOOLS, map), new AsyncCallback<RequestResult>() {
//
//			@Override
//			public void onFailure(Throwable caught) {
//				System.out.println(caught.getMessage());
//				SC.warn("ERROR", caught.getMessage());
//				GWT.log("ERROR " + caught.getMessage());
//				SC.clearPrompt();
//
//			}
//
//			@Override
//			public void onSuccess(RequestResult result) {
//
//				SC.clearPrompt();
//				SessionManager.getInstance().manageSession(result, placeManager);
//				if (result != null) {
//
//					if (result.getSystemFeedbackDTO() != null) {
//						LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();
//
//						for (SchoolDTO schoolDTO : result.getSchoolDTOs()) {
//							valueMap.put(schoolDTO.getId(), schoolDTO.getName());
//						}
//						schoolCombo.setValueMap(valueMap);
//						if (defaultValue != null) {
//							schoolCombo.setValue(defaultValue);
//						}
//					}
//				} else {
//					SC.warn("ERROR", "Unknow error");
//				}
//
//			}
//		});
	}

	public static void loadSchoolComboByDistrict(final ComboBox districtCombo, final ComboBox schoolCombo,
			final DispatchAsync dispatcher, final PlaceManager placeManager, final String defaultValue) {
		String districtId = districtCombo.getValueAsString();
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestDelimeters.DISTRICT_ID, districtId);
		map.put(NetworkDataUtil.ACTION, RequestConstant.GET_SCHOOLS_IN_DISTRICT);

		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {
				LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();

				for (SchoolDTO schoolDTO : result.getSchoolDTOs()) {
					valueMap.put(schoolDTO.getId(), schoolDTO.getName());
				}

				schoolCombo.setValueMap(valueMap);

				if (defaultValue != null) {
					schoolCombo.setValue(defaultValue);
				}
			}
		});

//		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
//
//		SC.showPrompt("", "", new SwizimaLoader());
//
//		dispatcher.execute(new RequestAction(RequestConstant.GET_SCHOOLS_IN_DISTRICT, map),
//				new AsyncCallback<RequestResult>() {
//					public void onFailure(Throwable caught) {
//						System.out.println(caught.getMessage());
//						SC.warn("ERROR", caught.getMessage());
//						GWT.log("ERROR " + caught.getMessage());
//						SC.clearPrompt();
//					}
//
//					public void onSuccess(RequestResult result) {
//						SC.clearPrompt();
//						SessionManager.getInstance().manageSession(result, placeManager);
//
//						if (result != null) {
//
//							LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();
//
//							for (SchoolDTO schoolDTO : result.getSchoolDTOs()) {
//								valueMap.put(schoolDTO.getId(), schoolDTO.getName());
//							}
//
//							schoolCombo.setValueMap(valueMap);
//
//							if (defaultValue != null) {
//								schoolCombo.setValue(defaultValue);
//							}
//
//						} else {
//							SC.warn("ERROR", "Unknow error");
//						}
//
//					}
//				});

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

//		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
//
//		SC.showPrompt("", "", new SwizimaLoader());
//
//		dispatcher.execute(new RequestAction(RequestConstant.GET_STAFFS_IN_SCHOOL, map),
//				new AsyncCallback<RequestResult>() {
//					public void onFailure(Throwable caught) {
//						System.out.println(caught.getMessage());
//						SC.warn("ERROR", caught.getMessage());
//						GWT.log("ERROR " + caught.getMessage());
//						SC.clearPrompt();
//					}
//
//					public void onSuccess(RequestResult result) {
//						SC.clearPrompt();
//						SessionManager.getInstance().manageSession(result, placeManager);
//
//						if (result != null) {
//
//							LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();
//
//							for (SchoolStaffDTO schoolStaffDTO : result.getSchoolStaffDTOs()) {
//								String fullName = schoolStaffDTO.getGeneralUserDetailDTO().getFirstName() + " "
//										+ schoolStaffDTO.getGeneralUserDetailDTO().getLastName();
//								valueMap.put(schoolStaffDTO.getId(), fullName);
//							}
//							schoolStaffCombo.setValueMap(valueMap);
//
//							if (defaultValue != null) {
//								schoolStaffCombo.setValue(defaultValue);
//							}
//
//						} else {
//							SC.warn("ERROR", "Unknow error");
//						}
//
//					}
//				});

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

//		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
////		SC.showPrompt("", "", new SwizimaLoader());
//
//		dispatcher.execute(new RequestAction(RequestConstant.GET_SCHOOL_STAFF, map),
//				new AsyncCallback<RequestResult>() {
//
//					@Override
//					public void onFailure(Throwable caught) {
//						System.out.println(caught.getMessage());
//						SC.warn("ERROR", caught.getMessage());
//						GWT.log("ERROR " + caught.getMessage());
//						SC.clearPrompt();
//
//					}
//
//					@Override
//					public void onSuccess(RequestResult result) {
//
//						SC.clearPrompt();
//						SessionManager.getInstance().manageSession(result, placeManager);
//						if (result != null) {
//
//							if (result.getSystemFeedbackDTO() != null) {
//								LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();
//
//								for (SchoolStaffDTO schoolStaffDTO : result.getSchoolStaffDTOs()) {
//									String fullName = schoolStaffDTO.getGeneralUserDetailDTO().getFirstName()
//											+ schoolStaffDTO.getGeneralUserDetailDTO().getLastName();
//									valueMap.put(schoolStaffDTO.getId(), fullName);
//								}
//								SchoolStaffCombo.setValueMap(valueMap);
//
//								if (defaultValue != null) {
//									SchoolStaffCombo.setValue(defaultValue);
//								}
//
//							}
//						} else {
//							SC.warn("ERROR", "Unknow error");
//						}
//
//					}
//				});
	}

	/////////////////////// SCHOOL CATEGORY COMBOS

	public static void loadSchoolCategoryCombo(final ComboBox schoolCategoryCombo, final DispatchAsync dispatcher,
			final PlaceManager placeManager, final String defaultValue) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.GET_SCHOOL_CATEGORY, null);
		map.put(NetworkDataUtil.ACTION, RequestConstant.GET_SCHOOL_CATEGORY);

		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {

				LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();

				for (SchoolCategoryDTO schoolCategoryDTO : result.getSchoolCategoryDTOs()) {
					valueMap.put(schoolCategoryDTO.getId(), schoolCategoryDTO.getName());
				}

				schoolCategoryCombo.setValueMap(valueMap);

				if (defaultValue != null) {
					schoolCategoryCombo.setValue(defaultValue);
				}
			}
		});

//		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
//		SC.showPrompt("", "", new SwizimaLoader());
//
//		dispatcher.execute(new RequestAction(RequestConstant.GET_SCHOOL_CATEGORY, map),
//				new AsyncCallback<RequestResult>() {
//
//					@Override
//					public void onFailure(Throwable caught) {
//						System.out.println(caught.getMessage());
//						SC.warn("ERROR", caught.getMessage());
//						GWT.log("ERROR " + caught.getMessage());
//						SC.clearPrompt();
//
//					}
//
//					@Override
//					public void onSuccess(RequestResult result) {
//
//						SC.clearPrompt();
//						SessionManager.getInstance().manageSession(result, placeManager);
//						if (result != null) {
//
//							if (result.getSystemFeedbackDTO() != null) {
//								if (result.getSystemFeedbackDTO().isResponse()) {
//
//									LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();
//
//									for (SchoolCategoryDTO schoolCategoryDTO : result.getSchoolCategoryDTOs()) {
//										valueMap.put(schoolCategoryDTO.getId(), schoolCategoryDTO.getName());
//									}
//
//									schoolCategoryCombo.setValueMap(valueMap);
//
//									if (defaultValue != null) {
//										schoolCategoryCombo.setValue(defaultValue);
//									}
//
//								} else {
//									SC.warn("Not Successful \n ERROR:", result.getSystemFeedbackDTO().getMessage());
//								}
//							}
//						} else {
//							SC.warn("ERROR", "Service Down");
//							// SC.warn("ERROR", "Unknown error");
//						}
//
//					}
//				});
	}

	///////////////////////////// SUBJECT CATEGORY

	public static void loadSubjectCategoryCombo(final ComboBox subjectCategoryCombo, final DispatchAsync dispatcher,
			final PlaceManager placeManager, final String defaultValue) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.GET_SUBJECT_CATEGORY, null);
		map.put(NetworkDataUtil.ACTION, RequestConstant.GET_SUBJECT_CATEGORY);

		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {
				LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();

				for (SubjectCategoryDTO subjectCategoryDTO : result.getSubjectCategoryDTOs()) {
					valueMap.put(subjectCategoryDTO.getId(), subjectCategoryDTO.getName());
				}
				subjectCategoryCombo.setValueMap(valueMap);
				if (defaultValue != null) {
					subjectCategoryCombo.setValue(defaultValue);
				}
			}
		});

//		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
//		SC.showPrompt("", "", new SwizimaLoader());
//
//		dispatcher.execute(new RequestAction(RequestConstant.GET_SUBJECT_CATEGORY, map),
//				new AsyncCallback<RequestResult>() {
//
//					@Override
//					public void onFailure(Throwable caught) {
//						SC.clearPrompt();
//						System.out.println(caught.getMessage());
//						SC.warn("ERROR", caught.getMessage());
//						GWT.log("ERROR " + caught.getMessage());
//					}
//
//					@Override
//					public void onSuccess(RequestResult result) {
//
//						SC.clearPrompt();
//						SessionManager.getInstance().manageSession(result, placeManager);
//						if (result != null) {
//
//							if (result.getSystemFeedbackDTO() != null) {
//								LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();
//
//								for (SubjectCategoryDTO subjectCategoryDTO : result.getSubjectCategoryDTOs()) {
//									valueMap.put(subjectCategoryDTO.getId(), subjectCategoryDTO.getName());
//								}
//								subjectCategoryCombo.setValueMap(valueMap);
//								if (defaultValue != null) {
//									subjectCategoryCombo.setValue(defaultValue);
//								}
//							}
//						} else {
//							SC.warn("ERROR", "Unknow error");
//						}
//
//					}
//				});
	}

	////////////////////////////////////////// SCHOOL CLASS COMBOS

	public static void loadSchoolClassCombo(final ComboBox schoolClassCombo, final DispatchAsync dispatcher,
			final PlaceManager placeManager, final String defaultValue) {

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.GET_SCHOOL_CLASS, null);
		map.put(NetworkDataUtil.ACTION, RequestConstant.GET_SCHOOL_CLASS);

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

//		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
//		SC.showPrompt("", "", new SwizimaLoader());
//
//		dispatcher.execute(new RequestAction(RequestConstant.GET_SCHOOL_CLASS, map),
//				new AsyncCallback<RequestResult>() {
//
//					@Override
//					public void onFailure(Throwable caught) {
//						System.out.println(caught.getMessage());
//						SC.warn("ERROR", caught.getMessage());
//						GWT.log("ERROR " + caught.getMessage());
//						SC.clearPrompt();
//
//					}
//
//					@Override
//					public void onSuccess(RequestResult result) {
//
//						SC.clearPrompt();
//						SessionManager.getInstance().manageSession(result, placeManager);
//						if (result != null) {
//
//							if (result.getSystemFeedbackDTO() != null) {
//								LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();
//
//								for (SchoolClassDTO schoolClassDTO : result.getSchoolClassDTOs()) {
//									valueMap.put(schoolClassDTO.getId(), schoolClassDTO.getName());
//								}
//								schoolClassCombo.setValueMap(valueMap);
//								if (defaultValue != null) {
//									schoolClassCombo.setValue(defaultValue);
//								}
//							}
//						} else {//
////					}
////					});
//							//
//							SC.warn("ERROR", "Unknow error");
//						}
//
//					}
//				});
	}

	public static void loadSchoolClassesComboBySchool(final ComboBox schoolCombo, final ComboBox schoolClassComboBox,
			final DispatchAsync dispatcher, final PlaceManager placeManager, final String defaultValue) {

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestDelimeters.SCHOOL_ID, schoolCombo.getValueAsString());
		map.put(NetworkDataUtil.ACTION, RequestConstant.GET_SCHOOL_CLASSES_IN_SCHOOL);

		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {
				LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();

				for (SchoolClassDTO schoolClassDTO : result.getSchoolClassDTOs()) {
					valueMap.put(schoolClassDTO.getId(), schoolClassDTO.getName());
				}
				schoolClassComboBox.setValueMap(valueMap);

				if (defaultValue != null) {
					schoolClassComboBox.setValue(defaultValue);
				}
			}
		});

//		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
//
//		SC.showPrompt("", "", new SwizimaLoader());
//
//		dispatcher.execute(new RequestAction(RequestConstant.GET_SCHOOL_CLASSES_IN_SCHOOL, map),
//				new AsyncCallback<RequestResult>() {
//					public void onFailure(Throwable caught) {
//						System.out.println(caught.getMessage());
//						SC.warn("ERROR", caught.getMessage());
//						GWT.log("ERROR " + caught.getMessage());
//						SC.clearPrompt();
//					}
//
//					public void onSuccess(RequestResult result) {
//						SC.clearPrompt();
//						SessionManager.getInstance().manageSession(result, placeManager);
//
//						if (result != null) {
//
//							LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();
//
//							for (SchoolClassDTO schoolClassDTO : result.getSchoolClassDTOs()) {
//								valueMap.put(schoolClassDTO.getId(), schoolClassDTO.getName());
//							}
//							schoolClassComboBox.setValueMap(valueMap);
//
//							if (defaultValue != null) {
//								schoolClassComboBox.setValue(defaultValue);
//							}
//
//						} else {
//							SC.warn("ERROR", "Unknow error");
//						}
//
//					}
//
//				});

	}

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

//		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
//
//		SC.showPrompt("", "", new SwizimaLoader());
//
//		dispatcher.execute(new RequestAction(RequestConstant.GET_SCHOOL_CLASSES_IN_SCHOOL_ACADEMIC_TERM, map),
//				new AsyncCallback<RequestResult>() {
//					public void onFailure(Throwable caught) {
//						System.out.println(caught.getMessage());
//						SC.warn("ERROR", caught.getMessage());
//						GWT.log("ERROR " + caught.getMessage());
//						SC.clearPrompt();
//					}
//
//					public void onSuccess(RequestResult result) {
//						SC.clearPrompt();
//						SessionManager.getInstance().manageSession(result, placeManager);
//
//						if (result != null) {
//
//							LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();
//
//							for (SchoolClassDTO schoolClassDTO : result.getSchoolClassDTOs()) {
//								valueMap.put(schoolClassDTO.getId(), schoolClassDTO.getName());
//							}
//							schoolClassCombo.setValueMap(valueMap);
//
//							if (defaultValue != null) {
//								schoolClassCombo.setValue(defaultValue);
//							}
//
//						} else {
//							SC.warn("ERROR", "Unknow error");
//						}
//
//					}
//
//				});

	}

////////////////////////////////////SUBJECT COMBOS
	public static void loadSubjectCombo(final ComboBox subjectCombo, final DispatchAsync dispatcher,
			final PlaceManager placeManager, final String defaultValue) {

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.GET_SUBJECT, null);
		map.put(NetworkDataUtil.ACTION, RequestConstant.GET_SUBJECT);
		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {
				LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();

				for (SubjectDTO subjectDTO : result.getSubjectDTOs()) {
					valueMap.put(subjectDTO.getId(), subjectDTO.getName());
				}
				subjectCombo.setValueMap(valueMap);

				if (defaultValue != null) {
					subjectCombo.setValue(defaultValue);
				}
			}
		});

//		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
//
//		SC.showPrompt("", "", new SwizimaLoader());
//
//		dispatcher.execute(new RequestAction(RequestConstant.GET_SUBJECT, map), new AsyncCallback<RequestResult>() {
//			public void onFailure(Throwable caught) {
//				System.out.println(caught.getMessage());
//				SC.warn("ERROR", caught.getMessage());
//				GWT.log("ERROR " + caught.getMessage());
//				SC.clearPrompt();
//			}
//
//			public void onSuccess(RequestResult result) {
//				SC.clearPrompt();
//				SessionManager.getInstance().manageSession(result, placeManager);
//
//				if (result != null) {
//
//					LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();
//
//					for (SubjectDTO subjectDTO : result.getSubjectDTOs()) {
//						valueMap.put(subjectDTO.getId(), subjectDTO.getName());
//					}
//					subjectCombo.setValueMap(valueMap);
//
//					if (defaultValue != null) {
//						subjectCombo.setValue(defaultValue);
//					}
//
//				} else {
//					SC.warn("ERROR", "Unknow error");
//				}
//
//			}
//
//		});
	}

}
