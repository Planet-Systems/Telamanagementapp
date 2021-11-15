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
import com.planetsystems.tela.dto.SystemUserGroupDTO;
import com.planetsystems.tela.managementapp.client.gin.SessionManager;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkDataUtil;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkResult;
import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.planetsystems.tela.managementapp.shared.RequestConstant;
import com.planetsystems.tela.managementapp.shared.RequestDelimeters;
import com.planetsystems.tela.managementapp.shared.RequestResult;
import com.planetsystems.tela.managementapp.shared.requestconstants.SystemUserGroupRequestConstant;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.form.fields.MultiComboBoxItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

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

					
					if(academicYearComboBox.getValueAsString() != null) {
						academicYearComboBox.clearValue();
						academicYearComboBox.setValueMap(valueMap);
					}else {
						academicYearComboBox.setValueMap(valueMap);
					}

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
 
				
				if(academicTermCombo.getValueAsString() != null) {
					academicTermCombo.clearValue();
					academicTermCombo.setValueMap(valueMap);
				}else {
					academicTermCombo.setValueMap(valueMap);
				}
 
				if (defaultValue != null) {
					academicTermCombo.setValue(defaultValue);
				}else {
					academicTermCombo.clearValue();
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
				if(academicTermCombo.getValueAsString() != null) {
					academicTermCombo.clearValue();
					academicTermCombo.setValueMap(valueMap);
				}else {
					academicTermCombo.setValueMap(valueMap);
				}
				
				
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
			map.put(NetworkDataUtil.ACTION, RequestConstant.GET_REGION);
		  
		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {
				//LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();
				
				
				DataSource records = new DataSource();
				DataSourceTextField idField = new DataSourceTextField("id", "Id"); 
				DataSourceTextField nameField = new DataSourceTextField("name", "Region");
				records.setFields(idField, nameField);
				records.setClientOnly(true);

				for (RegionDto regionDto : result.getRegionDtos()) { 
					Record record = new Record();
					record.setAttribute("id", regionDto.getId()); 
					record.setAttribute("name", regionDto.getName()); 
					records.addData(record);

				}

				ListGrid pickListProperties = new ListGrid();
				pickListProperties.setShowFilterEditor(true);
				pickListProperties.setFilterOnKeypress(true);
				pickListProperties.setWidth(520);
				pickListProperties.getWrapCells();
				pickListProperties.setWrapCells(true);
				pickListProperties.setFixedRecordHeights(false);

				ListGridField id = new ListGridField("id", "Id");
				id.setHidden(true); 
				ListGridField name = new ListGridField("name", "Region");

				comboBox.setOptionDataSource(records);

				comboBox.setValueField("id");
				comboBox.setDisplayField("name");
				comboBox.setPickListFields(id, name);
				comboBox.setPickListProperties(pickListProperties);
				comboBox.setPickListWidth(300);
				
				

				/*for (RegionDto regionDto : result.getRegionDtos()) {
					valueMap.put(regionDto.getId(), regionDto.getName());
				}*/
				if(comboBox.getValueAsString() != null) {
					comboBox.clearValue();
					//comboBox.setValueMap(valueMap);
				}else {
					//comboBox.setValueMap(valueMap);
				}

				if (defaultValue != null&&defaultValue.length()!=0) {
					comboBox.setValue(defaultValue);
					ChangedEvent event1 = new ChangedEvent(comboBox.getJsObj());
					comboBox.fireEvent(event1);
				}else {
					
					comboBox.setValue(result.getRegionDtos().get(0).getId()); 
					ChangedEvent event1 = new ChangedEvent(comboBox.getJsObj());
					comboBox.fireEvent(event1);
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
 
			map.put(NetworkDataUtil.ACTION, RequestConstant.GET_DISTRICT);
		  
		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {
				
				 
				DataSource records = new DataSource();
				DataSourceTextField idField = new DataSourceTextField("id", "Id"); 
				DataSourceTextField nameField = new DataSourceTextField("name", "District");
				records.setFields(idField, nameField);
				records.setClientOnly(true);

				for (DistrictDTO districtDTO : result.getDistrictDTOs()) { 
					Record record = new Record();
					record.setAttribute("id", districtDTO.getId()); 
					record.setAttribute("name", districtDTO.getName()); 
					records.addData(record);

				}

				ListGrid pickListProperties = new ListGrid();
				pickListProperties.setShowFilterEditor(true);
				pickListProperties.setFilterOnKeypress(true);
				pickListProperties.setWidth(520);
				pickListProperties.getWrapCells();
				pickListProperties.setWrapCells(true);
				pickListProperties.setFixedRecordHeights(false);

				ListGridField id = new ListGridField("id", "Id");
				id.setHidden(true); 
				ListGridField name = new ListGridField("name", "District");

				districtCombo.setOptionDataSource(records);

				districtCombo.setValueField("id");
				districtCombo.setDisplayField("name");
				districtCombo.setPickListFields(id, name);
				districtCombo.setPickListProperties(pickListProperties);
				districtCombo.setPickListWidth(300);
 
				 
				//LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();
				  

				/*for (DistrictDTO districtDTO : result.getDistrictDTOs()) {
					valueMap.put(districtDTO.getId(), districtDTO.getName());
				}*/
				
				if(districtCombo.getValueAsString() != null) {
					districtCombo.clearValue();
					//districtCombo.setValueMap(valueMap);
				}else {
					//districtCombo.setValueMap(valueMap);	
				}

				if (defaultValue != null&&defaultValue.length()!=0) {
					districtCombo.setValue(defaultValue); 
					ChangedEvent event1 = new ChangedEvent(districtCombo.getJsObj());
					districtCombo.fireEvent(event1);
				}else {
					districtCombo.setValue(result.getDistrictDTOs().get(0).getId());  
					ChangedEvent event1 = new ChangedEvent(districtCombo.getJsObj());
					districtCombo.fireEvent(event1);
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
				//LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();
				
				
				DataSource records = new DataSource();
				DataSourceTextField idField = new DataSourceTextField("id", "Id"); 
				DataSourceTextField nameField = new DataSourceTextField("name", "District");
				records.setFields(idField, nameField);
				records.setClientOnly(true);

				for (DistrictDTO districtDTO : result.getDistrictDTOs()) { 
					Record record = new Record();
					record.setAttribute("id", districtDTO.getId()); 
					record.setAttribute("name", districtDTO.getName()); 
					records.addData(record);

				}

				ListGrid pickListProperties = new ListGrid();
				pickListProperties.setShowFilterEditor(true);
				pickListProperties.setFilterOnKeypress(true);
				pickListProperties.setWidth(520);
				pickListProperties.getWrapCells();
				pickListProperties.setWrapCells(true);
				pickListProperties.setFixedRecordHeights(false);

				ListGridField id = new ListGridField("id", "Id");
				id.setHidden(true); 
				ListGridField name = new ListGridField("name", "District");

				districtCombo.setOptionDataSource(records);

				districtCombo.setValueField("id");
				districtCombo.setDisplayField("name");
				districtCombo.setPickListFields(id, name);
				districtCombo.setPickListProperties(pickListProperties);
				districtCombo.setPickListWidth(300);
				
				
				

				/*for (DistrictDTO districtDTO : result.getDistrictDTOs()) {
					valueMap.put(districtDTO.getId(), districtDTO.getName());
				}*/
				
				if(districtCombo.getValueAsString() != null) {
					districtCombo.clearValue();
					//districtCombo.setValueMap(valueMap);
				}else {
					//districtCombo.setValueMap(valueMap);
				}
				
				if (defaultValue != null&&defaultValue.length()!=0) {
					districtCombo.setValue(defaultValue);
					ChangedEvent event1 = new ChangedEvent(districtCombo.getJsObj());
					districtCombo.fireEvent(event1);
				}else {
					districtCombo.setValue(result.getDistrictDTOs().get(0).getId()); 
					ChangedEvent event1 = new ChangedEvent(districtCombo.getJsObj());
					districtCombo.fireEvent(event1);
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
		map.put(NetworkDataUtil.ACTION, RequestConstant.GET_SCHOOLS);

		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {
				//LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();

				
				DataSource records = new DataSource();
				DataSourceTextField idField = new DataSourceTextField("id", "Id"); 
				DataSourceTextField nameField = new DataSourceTextField("name", "School");
				records.setFields(idField, nameField);
				records.setClientOnly(true);

				for (SchoolDTO schoolDTO : result.getSchoolDTOs()) { 
					Record record = new Record();
					record.setAttribute("id", schoolDTO.getId()); 
					record.setAttribute("name", schoolDTO.getName()); 
					records.addData(record);

				}

				ListGrid pickListProperties = new ListGrid();
				pickListProperties.setShowFilterEditor(true);
				pickListProperties.setFilterOnKeypress(true);
				pickListProperties.setWidth(520);
				pickListProperties.getWrapCells();
				pickListProperties.setWrapCells(true);
				pickListProperties.setFixedRecordHeights(false);

				ListGridField id = new ListGridField("id", "Id");
				id.setHidden(true); 
				ListGridField name = new ListGridField("name", "School");

				schoolCombo.setOptionDataSource(records);

				schoolCombo.setValueField("id");
				schoolCombo.setDisplayField("name");
				schoolCombo.setPickListFields(id, name);
				schoolCombo.setPickListProperties(pickListProperties);
				schoolCombo.setPickListWidth(400);
				
				/*for (SchoolDTO schoolDTO : result.getSchoolDTOs()) {
					valueMap.put(schoolDTO.getId(), schoolDTO.getName());
				}*/
				
				if(schoolCombo.getValueAsString() != null) {
					schoolCombo.clearValue();
					//schoolCombo.setValueMap(valueMap);
				}else {
					//schoolCombo.setValueMap(valueMap);
				}
				
				if (defaultValue != null) {
					schoolCombo.setValue(defaultValue);
					ChangedEvent event1 = new ChangedEvent(schoolCombo.getJsObj());
					schoolCombo.fireEvent(event1);
				}
			}
		});

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
				//LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();
				
				
				DataSource records = new DataSource();
				DataSourceTextField idField = new DataSourceTextField("id", "Id"); 
				DataSourceTextField nameField = new DataSourceTextField("name", "School");
				records.setFields(idField, nameField);
				records.setClientOnly(true);

				for (SchoolDTO schoolDTO : result.getSchoolDTOs()) { 
					Record record = new Record();
					record.setAttribute("id", schoolDTO.getId()); 
					record.setAttribute("name", schoolDTO.getName()); 
					records.addData(record);

				}

				ListGrid pickListProperties = new ListGrid();
				pickListProperties.setShowFilterEditor(true);
				pickListProperties.setFilterOnKeypress(true);
				pickListProperties.setWidth(520);
				pickListProperties.getWrapCells();
				pickListProperties.setWrapCells(true);
				pickListProperties.setFixedRecordHeights(false);

				ListGridField id = new ListGridField("id", "Id");
				id.setHidden(true); 
				ListGridField name = new ListGridField("name", "School");

				schoolCombo.setOptionDataSource(records);

				schoolCombo.setValueField("id");
				schoolCombo.setDisplayField("name");
				schoolCombo.setPickListFields(id, name);
				schoolCombo.setPickListProperties(pickListProperties);
				schoolCombo.setPickListWidth(400);
				

				/*for (SchoolDTO schoolDTO : result.getSchoolDTOs()) {
					valueMap.put(schoolDTO.getId(), schoolDTO.getName());
				}*/
				
				if(schoolCombo.getValueAsString() != null) {
					schoolCombo.clearValue();
					//schoolCombo.setValueMap(valueMap);
				}else {
					//schoolCombo.setValueMap(valueMap);
				}


				if (defaultValue != null) {
					schoolCombo.setValue(defaultValue);
					ChangedEvent event1 = new ChangedEvent(schoolCombo.getJsObj());
					schoolCombo.fireEvent(event1);
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
				//LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();
				
				
				
				DataSource records = new DataSource();
				DataSourceTextField idField = new DataSourceTextField("id", "Id"); 
				DataSourceTextField nameField = new DataSourceTextField("name", "Teacher Name");
				records.setFields(idField, nameField);
				records.setClientOnly(true);

				for (SchoolStaffDTO schoolStaffDTO : result.getSchoolStaffDTOs()) { 
					
					String fullName = schoolStaffDTO.getGeneralUserDetailDTO().getFirstName() + " "
							+ schoolStaffDTO.getGeneralUserDetailDTO().getLastName();
					
					Record record = new Record();
					record.setAttribute("id", schoolStaffDTO.getId()); 
					record.setAttribute("name", fullName); 
					records.addData(record);

				}

				ListGrid pickListProperties = new ListGrid();
				pickListProperties.setShowFilterEditor(true);
				pickListProperties.setFilterOnKeypress(true);
				pickListProperties.setWidth(520);
				pickListProperties.getWrapCells();
				pickListProperties.setWrapCells(true);
				pickListProperties.setFixedRecordHeights(false);

				ListGridField id = new ListGridField("id", "Id");
				id.setHidden(true); 
				ListGridField name = new ListGridField("name", "Teacher Name");

				schoolStaffCombo.setOptionDataSource(records);

				schoolStaffCombo.setValueField("id");
				schoolStaffCombo.setDisplayField("name");
				schoolStaffCombo.setPickListFields(id, name);
				schoolStaffCombo.setPickListProperties(pickListProperties);
				schoolStaffCombo.setPickListWidth(400);

				/*for (SchoolStaffDTO schoolStaffDTO : result.getSchoolStaffDTOs()) {
					String fullName = schoolStaffDTO.getGeneralUserDetailDTO().getFirstName() + " "
							+ schoolStaffDTO.getGeneralUserDetailDTO().getLastName();
					valueMap.put(schoolStaffDTO.getId(), fullName);
				}*/
				
				if(schoolStaffCombo.getValueAsString() != null) {
					schoolStaffCombo.clearValue();
					//schoolStaffCombo.setValueMap(valueMap);
				}else {
					//schoolStaffCombo.setValueMap(valueMap);
				}


				if (defaultValue != null) {
					schoolStaffCombo.setValue(defaultValue); 
					ChangedEvent event1 = new ChangedEvent(schoolStaffCombo.getJsObj());
					schoolStaffCombo.fireEvent(event1);
				}
			}
		});
	}
	
	public static void loadHeadteacherComboBySchool(final ComboBox schoolCombo, final ComboBox schoolStaffCombo,
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
					if(schoolStaffDTO.getStaffType().equalsIgnoreCase("Head teacher")) {
						String fullName = schoolStaffDTO.getGeneralUserDetailDTO().getFirstName() + " "
								+ schoolStaffDTO.getGeneralUserDetailDTO().getLastName();
						valueMap.put(schoolStaffDTO.getId(), fullName);
					}
					
				}
				
				if(schoolStaffCombo.getValueAsString() != null) {
					schoolStaffCombo.clearValue();
					schoolStaffCombo.setValueMap(valueMap);
				}else {
					schoolStaffCombo.setValueMap(valueMap);
				}


				if (defaultValue != null) {
					schoolStaffCombo.setValue(defaultValue);
					ChangedEvent event1 = new ChangedEvent(schoolStaffCombo.getJsObj());
					schoolStaffCombo.fireEvent(event1);
				}
			}
		});
	}
		
		
		public static void loadSchoolStaffMultiComboBySchool(final ComboBox schoolCombo, final MultiComboBoxItem schoolStaffCombo,
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
					
					if(schoolStaffCombo.getValues() != null) {
						schoolStaffCombo.clearValue();
						schoolStaffCombo.setValueMap(valueMap);
					}else {
						schoolStaffCombo.setValueMap(valueMap);
					}

					if (defaultValue != null) {
						schoolStaffCombo.setValue(defaultValue);
						ChangedEvent event1 = new ChangedEvent(schoolStaffCombo.getJsObj());
						schoolStaffCombo.fireEvent(event1);
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

	public static void loadSchoolStaffCombo(final ComboBox schoolStaffCombo, final DispatchAsync dispatcher,
			final PlaceManager placeManager, final String defaultValue) {

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.GET_SCHOOL_STAFF, null);
		map.put(NetworkDataUtil.ACTION, RequestConstant.GET_SCHOOL_STAFF);
		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {
				//LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();
				
				
				DataSource records = new DataSource();
				DataSourceTextField idField = new DataSourceTextField("id", "Id"); 
				DataSourceTextField nameField = new DataSourceTextField("name", "Teacher Name");
				records.setFields(idField, nameField);
				records.setClientOnly(true);

				for (SchoolStaffDTO schoolStaffDTO : result.getSchoolStaffDTOs()) { 
					
					String fullName = schoolStaffDTO.getGeneralUserDetailDTO().getFirstName() + " "
							+ schoolStaffDTO.getGeneralUserDetailDTO().getLastName();
					
					Record record = new Record();
					record.setAttribute("id", schoolStaffDTO.getId()); 
					record.setAttribute("name", fullName); 
					records.addData(record);

				}

				ListGrid pickListProperties = new ListGrid();
				pickListProperties.setShowFilterEditor(true);
				pickListProperties.setFilterOnKeypress(true);
				pickListProperties.setWidth(520);
				pickListProperties.getWrapCells();
				pickListProperties.setWrapCells(true);
				pickListProperties.setFixedRecordHeights(false);

				ListGridField id = new ListGridField("id", "Id");
				id.setHidden(true); 
				ListGridField name = new ListGridField("name", "Teacher Name");

				schoolStaffCombo.setOptionDataSource(records);

				schoolStaffCombo.setValueField("id");
				schoolStaffCombo.setDisplayField("name");
				schoolStaffCombo.setPickListFields(id, name);
				schoolStaffCombo.setPickListProperties(pickListProperties);
				schoolStaffCombo.setPickListWidth(400);
				

				/*for (SchoolStaffDTO schoolStaffDTO : result.getSchoolStaffDTOs()) {
					String fullName = schoolStaffDTO.getGeneralUserDetailDTO().getFirstName()
							+ schoolStaffDTO.getGeneralUserDetailDTO().getLastName();
					valueMap.put(schoolStaffDTO.getId(), fullName);
				}*/
				
				if(schoolStaffCombo.getValueAsString() != null) {
					schoolStaffCombo.clearValue();
					//SchoolStaffCombo.setValueMap(valueMap);
				}else {
					//SchoolStaffCombo.setValueMap(valueMap);
				}


				if (defaultValue != null) {
					schoolStaffCombo.setValue(defaultValue);
					ChangedEvent event1 = new ChangedEvent(schoolStaffCombo.getJsObj());
					schoolStaffCombo.fireEvent(event1);
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

				if(schoolCategoryCombo.getValueAsString() != null) {
					schoolCategoryCombo.clearValue();
					schoolCategoryCombo.setValueMap(valueMap);
				}else {
					schoolCategoryCombo.setValueMap(valueMap);
				}

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

				
				if(subjectCategoryCombo.getValueAsString() != null) {
					subjectCategoryCombo.clearValue();
					subjectCategoryCombo.setValueMap(valueMap);
				}else {
					subjectCategoryCombo.setValueMap(valueMap);
				}

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
				
				if(schoolClassCombo.getValueAsString() != null) {
					schoolClassCombo.clearValue();
					schoolClassCombo.setValueMap(valueMap);
				}else {
					schoolClassCombo.setValueMap(valueMap);	
				}
				
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
				
				if(schoolClassComboBox.getValueAsString() != null) {
					schoolClassComboBox.clearValue();
					schoolClassComboBox.setValueMap(valueMap);
				}else {
					schoolClassComboBox.setValueMap(valueMap);
				}


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
				
				if(schoolClassCombo.getValueAsString() != null) {
					schoolClassCombo.clearValue();
					schoolClassCombo.setValueMap(valueMap);
				}else {
					schoolClassCombo.setValueMap(valueMap);
				}
				

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
				
				if(subjectCombo.getValueAsString() != null) {
					subjectCombo.clearValue();
					subjectCombo.setValueMap(valueMap);
				}else {
					subjectCombo.setValueMap(valueMap);
				}


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

	public static void loadSystemUserGroupCombo(final ComboBox systemUserGroupCombo, DispatchAsync dispatcher,
			PlaceManager placeManager, final String defaultValue) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(NetworkDataUtil.ACTION , SystemUserGroupRequestConstant.GET_SYSTEM_USER_GROUPS);
		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {
			
			@Override
			public void onNetworkResult(RequestResult result) {
				LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();
				for (SystemUserGroupDTO dto : result.getSystemUserGroupDTOs()) {
					valueMap.put(dto.getId(), dto.getName());
				}

				
				if(systemUserGroupCombo.getValueAsString() != null) {
					systemUserGroupCombo.clearValue();
					systemUserGroupCombo.setValueMap(valueMap);
				}else {
					systemUserGroupCombo.setValueMap(valueMap);
				}
				
				if (defaultValue != null) {
					systemUserGroupCombo.setValue(defaultValue);
				}
			}
		});
		
	}

}
