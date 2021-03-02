package com.planetsystems.tela.managementapp.client.presenter.staffenrollment;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.planetsystems.tela.dto.AcademicTermDTO;
import com.planetsystems.tela.dto.GeneralUserDetailDTO;
import com.planetsystems.tela.dto.SchoolDTO;
import com.planetsystems.tela.dto.SchoolStaffDTO;
import com.planetsystems.tela.dto.StaffEnrollmentDto;
import com.planetsystems.tela.dto.SystemFeedbackDTO;
import com.planetsystems.tela.managementapp.client.gin.SessionManager;
import com.planetsystems.tela.managementapp.client.place.NameTokens;
import com.planetsystems.tela.managementapp.client.presenter.main.MainPresenter;
import com.planetsystems.tela.managementapp.client.presenter.schoolstaff.SchoolStaffWindow;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.planetsystems.tela.managementapp.client.widget.MenuButton;
import com.planetsystems.tela.managementapp.client.widget.SwizimaLoader;
import com.planetsystems.tela.managementapp.shared.DatePattern;
import com.planetsystems.tela.managementapp.shared.RequestAction;
import com.planetsystems.tela.managementapp.shared.RequestConstant;
import com.planetsystems.tela.managementapp.shared.RequestResult;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;
public class StaffEnrollmentPresenter extends Presenter<StaffEnrollmentPresenter.MyView, StaffEnrollmentPresenter.MyProxy>  {
    interface MyView extends View  {
    	public ControlsPane getControlsPane();

    	public TabSet getTabSet();

    	public StaffEnrollmentPane getStaffEnrollmentPane();

    	public SchoolStaffPane getSchoolStaffPane();
    
    }
    @ContentSlot
    public static final Type<RevealContentHandler<?>> SLOT_Enrollment = new Type<RevealContentHandler<?>>();
    
    @Inject
    private DispatchAsync dispatcher;
    
    @Inject
    private PlaceManager placeManager;
    
	DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat(DatePattern.DAY_MONTH_YEAR_HOUR_MINUTE_SECONDS.getPattern());
	DateTimeFormat dateFormat = DateTimeFormat.getFormat(DatePattern.DAY_MONTH_YEAR.getPattern());

    @NameToken(NameTokens.enrollment)
    @ProxyCodeSplit
    interface MyProxy extends ProxyPlace<StaffEnrollmentPresenter> {
    }

    @Inject
    StaffEnrollmentPresenter(
            EventBus eventBus,
            MyView view, 
            MyProxy proxy) {
        super(eventBus, view, proxy, MainPresenter.SLOT_Main);
        
    }
    
    @Override
    protected void onBind() {
    	super.onBind();
     onTabSelected();
     getAllStaffEnrollments();
     getAllSchoolStaff();
     
    }
    private void onTabSelected() {
		getView().getTabSet().addTabSelectedHandler(new TabSelectedHandler() {

			@Override
			public void onTabSelected(TabSelectedEvent event) {

				String selectedTab = event.getTab().getTitle();

				if (selectedTab.equalsIgnoreCase(StaffEnrollmentView.STAFF_ENROLLMENT)) {
					MenuButton newButton = new MenuButton("New");
					MenuButton edit = new MenuButton("Edit");
					MenuButton delete = new MenuButton("Delete");
					MenuButton fiter = new MenuButton("Filter");

					List<MenuButton> buttons = new ArrayList<>();
					buttons.add(newButton);
					buttons.add(edit);
					buttons.add(delete);
					buttons.add(fiter);

					getView().getControlsPane().addMenuButtons(buttons);
					addStaffEnrollment(newButton);
//					deleteRegion(delete);
//					editRegion(edit);

				} else if (selectedTab.equalsIgnoreCase(StaffEnrollmentView.TEACHER_LIST)){
		
					MenuButton newButton = new MenuButton("New");
					MenuButton edit = new MenuButton("Edit");
					MenuButton delete = new MenuButton("Delete");
					MenuButton fiter = new MenuButton("Filter");

					List<MenuButton> buttons = new ArrayList<>();
					buttons.add(newButton);
					buttons.add(edit);
					buttons.add(delete);
					buttons.add(fiter);

					getView().getControlsPane().addMenuButtons(buttons);
					addSchoolStaff(newButton);

				} else {
					List<MenuButton> buttons = new ArrayList<>();
					getView().getControlsPane().addMenuButtons(buttons);
				}

			}

		});
	}
    

	private void addStaffEnrollment(MenuButton newButton) {
	newButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				  StaffEnrollmentWindow window = new StaffEnrollmentWindow();
				  loadAcademicTermCombo(window, null);
				  setStaffTotal(window);
				  loadSchoolCombo(window, null);
				  window.show();
				  
				  saveStaffEnrollment(window);
				  
			}

			
		});
		
	}
	
	private void saveStaffEnrollment(final StaffEnrollmentWindow window) {
      window.getSaveButton().addClickHandler(new ClickHandler() {
		
		@Override
		public void onClick(ClickEvent event) {
			if(checkIfNoStaffEnrollmentWindowFieldIsEmpty(window)) {
				StaffEnrollmentDto dto = new StaffEnrollmentDto();
				   dto.setTotalFemale(Long.valueOf(window.getTotalFemaleField().getValueAsString()));
				   dto.setTotalMale(Long.valueOf(window.getTotalMaleField().getValueAsString()));
				   dto.setCreatedDateTime(dateTimeFormat.format(new Date()));
				   
				   AcademicTermDTO academicTermDTO = new AcademicTermDTO(window.getAcademicTermComboBox().getValueAsString());
				   dto.setAcademicTermDTO(academicTermDTO);
				   //dto.setId(id);
				   SchoolDTO schoolDTO = new SchoolDTO(window.getSchoolComboBox().getValueAsString());
				   dto.setSchoolDTO(schoolDTO);
				   
				   GWT.log("DTO "+dto);
				   GWT.log("term "+dto.getAcademicTermDTO().getId());
				   GWT.log("DTO "+dto.getSchoolDTO().getId());
				   
				   LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					map.put(RequestConstant.SAVE_STAFF_ENROLLMENT, dto);
					map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
					SC.showPrompt("", "", new SwizimaLoader());

					dispatcher.execute(new RequestAction(RequestConstant.SAVE_STAFF_ENROLLMENT, map),
							new AsyncCallback<RequestResult>() {

								public void onFailure(Throwable caught) {

									SC.clearPrompt();
									System.out.println(caught.getMessage());
									SC.say("ERROR", caught.getMessage());
								}

								public void onSuccess(RequestResult result) {
									SC.clearPrompt();
									
									clearStaffEnrollmentWindowFields(window);
									window.close();

									SessionManager.getInstance().manageSession(result, placeManager);
									
									if (result != null) {
										SystemFeedbackDTO feedback = result.getSystemFeedbackDTO();

										if (feedback.isResponse()) {
											SC.say("SUCCESS", feedback.getMessage());
										} else {
											SC.warn("INFO", feedback.getMessage());
										}

										getView().getStaffEnrollmentPane().getStaffEnrollmentListGrid().addRecordsToGrid(result.getStaffEnrollmentDtos());

									} else {
										SC.warn("ERROR", "Unknow error");
									}

								}

							});
				   
			}else {
				SC.warn("Please Fill all fields");
			}

		}
	});
		
	}
	
	private boolean checkIfNoStaffEnrollmentWindowFieldIsEmpty(StaffEnrollmentWindow window) {
		boolean flag = true;

		if(window.getAcademicTermComboBox().getValueAsString() == null) flag = false;
		
		if(window.getSchoolComboBox().getValueAsString() == null) flag = false;
		
		if(window.getTotalFemaleField().getValueAsString() == null) flag = false;
		
		if(window.getTotalMaleField().getValueAsString() == null) flag = false;
		
		if(window.getStaffTotalField().getValueAsString() == null) flag = false;
		
		return flag;
	}
	

	private void clearStaffEnrollmentWindowFields(StaffEnrollmentWindow window) {
 
		window.getAcademicTermComboBox().clearValue();
		window.getTotalFemaleField().clearValue();
		window.getTotalFemaleField().clearValue();
		window.getStaffTotalField().clearValue();
	}
	
	
	/////////////////////////////////////////COMBOS
	
	private void loadAcademicTermCombo(final StaffEnrollmentWindow window, final String defaultValue) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.GET_ACADEMIC_TERM, null);
		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
		SC.showPrompt("", "", new SwizimaLoader());

		dispatcher.execute(new RequestAction( RequestConstant.GET_ACADEMIC_TERM , map),
				new AsyncCallback<RequestResult>() {

					@Override
					public void onFailure(Throwable caught) {
						System.out.println(caught.getMessage());
						SC.warn("ERROR", caught.getMessage());
						GWT.log("ERROR " + caught.getMessage());
						SC.clearPrompt();

					}

					@Override
					public void onSuccess(RequestResult result) {

						SC.clearPrompt();
						SessionManager.getInstance().manageSession(result, placeManager);
						if (result != null) {

							if (result.getSystemFeedbackDTO() != null) {
								LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();

								for (AcademicTermDTO academicTermDTO : result.getAcademicTermDTOs()) {
									valueMap.put(academicTermDTO.getId(), academicTermDTO.getTerm());
								}
								window.getAcademicTermComboBox().setValueMap(valueMap);

								if (defaultValue != null) {
									window.getAcademicTermComboBox().setValue(defaultValue);
								}

							}
						} else {
							SC.warn("ERROR", "Unknow error");
						}

					}
				});
	}

	
	private void loadSchoolCombo(final StaffEnrollmentWindow window, final String defaultValue) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.GET_SCHOOL, null);
		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
		SC.showPrompt("", "", new SwizimaLoader());

		dispatcher.execute(new RequestAction(RequestConstant.GET_SCHOOL , map), new AsyncCallback<RequestResult>() {

			@Override
			public void onFailure(Throwable caught) {
				System.out.println(caught.getMessage());
				SC.warn("ERROR", caught.getMessage());
				GWT.log("ERROR " + caught.getMessage());
				SC.clearPrompt();

			}

			@Override
			public void onSuccess(RequestResult result) {

				SC.clearPrompt();
				SessionManager.getInstance().manageSession(result, placeManager);
				if (result != null) {

					if (result.getSystemFeedbackDTO() != null) {
						LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();

						for (SchoolDTO schoolDTO : result.getSchoolDTOs()) {
							valueMap.put(schoolDTO.getId(), schoolDTO.getName());
						}
						window.getSchoolComboBox().setValueMap(valueMap);
						if (defaultValue != null) {
							window.getSchoolComboBox().setValue(defaultValue);
						}
					}
				} else {
					SC.warn("ERROR", "Unknow error");
				}

			}
		});
	}

	
	///////////////////////////////////////END COMBOS
	
	
public void	setStaffTotal(final StaffEnrollmentWindow window){
	final int[] totalMale = new int[1];
	final int[] totalFemale = new int[1];
	final int[] total = {0};
	window.getTotalFemaleField().addChangedHandler(new ChangedHandler() {
		
		@Override
		public void onChanged(ChangedEvent event) {
			if(window.getTotalFemaleField().getValueAsString() == null) {
				totalFemale[0] = 0;
			}else {
				totalFemale[0] = Integer.parseInt(window.getTotalFemaleField().getValueAsString());
			}
			
			
			if(window.getTotalMaleField().getValueAsString() == null) {
				totalMale[0] = 0;
			}

				total[0] = totalFemale[0] + totalMale[0];
				 window.getStaffTotalField().setValue(total[0]);
		}
	});
	
window.getTotalMaleField().addChangedHandler(new ChangedHandler() {
		
		@Override
		public void onChanged(ChangedEvent event) {
			if(window.getTotalMaleField().getValueAsString() == null) {
				totalMale[0] = 0;
			}else {
				totalMale[0] = Integer.parseInt(window.getTotalMaleField().getValueAsString());	
			}
				
			
			if(window.getTotalFemaleField().getValueAsString() == null) {
				totalFemale[0] = 0;
			}
			

				total[0] = totalFemale[0] + totalMale[0];
				 window.getStaffTotalField().setValue(total[0]);
		}
	});

	  	
	
	}






private void getAllStaffEnrollments() {
	LinkedHashMap<String, Object> map = new LinkedHashMap<>();
	map.put(RequestConstant.GET_STAFF_ENROLLMENT, null);
	map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
	SC.showPrompt("", "", new SwizimaLoader());

	dispatcher.execute(new RequestAction(RequestConstant.GET_STAFF_ENROLLMENT , map),
			new AsyncCallback<RequestResult>() {

				@Override
				public void onFailure(Throwable caught) {
					System.out.println(caught.getMessage());
					SC.warn("ERROR", caught.getMessage());
					GWT.log("ERROR " + caught.getMessage());
					SC.clearPrompt();

				}

				@Override
				public void onSuccess(RequestResult result) {

					SC.clearPrompt();
					SessionManager.getInstance().manageSession(result, placeManager);
					if (result != null) {
                        SystemFeedbackDTO feedbackDTO = result.getSystemFeedbackDTO();
						if ( feedbackDTO != null) {
							if (feedbackDTO.isResponse()) {
								// SC.say("SUCCESS", result.getSystemFeedbackDTO().getMessage());
								getView().getStaffEnrollmentPane().getStaffEnrollmentListGrid()
								.addRecordsToGrid(result.getStaffEnrollmentDtos());
							} else {
								SC.warn("Not Successful \n ERROR:", feedbackDTO.getMessage());
							}
						}
					} else {
						SC.warn("ERROR", "Unknow error");
					}

				}

			});

}

/////////////////////////////////////////////////////////school Staff
private void addSchoolStaff(MenuButton newButton) {
	newButton.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
		
		@Override
		public void onClick(ClickEvent event) {
			SchoolStaffWindow window = new SchoolStaffWindow();
			loadGenderCombo(window, null);
			loadRegisteredCombo(window, null);
			loadSchoolCombo(window, null);
			saveSchoolStaff(window);
			window.show();
			
		}

	});
	
}

private void saveSchoolStaff(final SchoolStaffWindow window) {
	window.getSaveButton().addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
		
		@Override
		public void onClick(ClickEvent event) {
			
			if(checkIfNoSchoolStaffWindowFieldIsEmpty(window)){
				 SchoolStaffDTO dto = new SchoolStaffDTO();
				 //  dto.setId(id);
				   
				   dto.setRegistered(Boolean.valueOf(window.getRegisteredComboBox().getValueAsString()));
				   dto.setCreatedDateTime(dateTimeFormat.format(new Date()));
				 
				   dto.setStaffCode(window.getStaffCode().getValueAsString());
				 //  dto.setStatus(status);
				  // dto.setStaffType(staffType);
			
				   SchoolDTO schoolDTO = new SchoolDTO(window.getSchoolComboBox().getValueAsString());
				   dto.setSchoolDTO(schoolDTO);
					
				   GeneralUserDetailDTO generalUserDetailDTO = new GeneralUserDetailDTO();
				   generalUserDetailDTO.setFirstName(window.getFirstNameField().getValueAsString());
				   generalUserDetailDTO.setLastName(window.getLastNameField().getValueAsString());
				   generalUserDetailDTO.setEmail(window.getEmailField().getValueAsString());
				   generalUserDetailDTO.setPhoneNumber(window.getPhoneNumberField().getValueAsString());
				   generalUserDetailDTO.setGender(window.getGenderComboBox().getValueAsString());
				   generalUserDetailDTO.setNameAbbrev(window.getNameAbrevField().getValueAsString());
				   generalUserDetailDTO.setDob(dateFormat.format(window.getDobItem().getValueAsDate()));
				   generalUserDetailDTO.setNationalId(window.getNationalIdField().getValueAsString());
				   
				   dto.setGeneralUserDetailDTO(generalUserDetailDTO);
				   
				   GWT.log("STAFF "+dto);
				   GWT.log("School "+dto.getSchoolDTO().getId());
				   
					LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					map.put(RequestConstant.SAVE_SCHOOL_STAFF, dto);
					map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
					SC.showPrompt("", "", new SwizimaLoader());

					dispatcher.execute(new RequestAction(RequestConstant.SAVE_SCHOOL_STAFF, map),
							new AsyncCallback<RequestResult>() {

								public void onFailure(Throwable caught) {

									SC.clearPrompt();
									System.out.println(caught.getMessage());
									SC.say("ERROR", caught.getMessage());
								}

								public void onSuccess(RequestResult result) {
									SC.clearPrompt();
									
									clearSchoolStaffWindowFields(window);

									SessionManager.getInstance().manageSession(result, placeManager);
									
									if (result != null) {
										SystemFeedbackDTO feedback = result.getSystemFeedbackDTO();

										if (feedback.isResponse()) {
											SC.say("SUCCESS", feedback.getMessage());
										} else {
											SC.warn("INFO", feedback.getMessage());
										}

										getView().getSchoolStaffPane().getStaffListGrid().addRecordsToGrid(result.getSchoolStaffDTOs());

									} else {
										SC.warn("ERROR", "Unknow error");
									}

								}

							});
			}else {
				SC.warn("Please Fill the fields");
			}
			
		}
	});
	}

	protected boolean checkIfNoSchoolStaffWindowFieldIsEmpty(SchoolStaffWindow window) {
		boolean flag = true;
		
		if(window.getFirstNameField().getValueAsString() == null) flag = false;
		
		if(window.getLastNameField().getValueAsString() == null) flag = false;
		
		if(window.getPhoneNumberField().getValueAsString() == null) flag = false;
		
		if(window.getEmailField().getValueAsString() == null) flag = false;
		
		if(window.getDobItem().getValueAsDate() == null) flag = false;
		
		if(window.getNationalIdField().getValueAsString() == null) flag = false;
		
		if(window.getGenderComboBox().getValueAsString() == null) flag = false;
		
		if(window.getNameAbrevField().getValueAsString() == null) flag = false;
		
		if(window.getStaffCode().getValueAsString() == null) flag = false;
		
		if(window.getRegisteredComboBox().getValueAsString() == null) flag = false;
		
		if(window.getSchoolComboBox().getValueAsString() == null) flag = false;
		
		
		return flag;
	}

	private void clearSchoolStaffWindowFields(SchoolStaffWindow window) {
		
		window.getFirstNameField().clearValue();
		window.getLastNameField().clearValue();
		window.getPhoneNumberField().clearValue();
		window.getEmailField().clearValue();
		window.getDobItem().clearValue();
		window.getNationalIdField().clearValue();
		window.getGenderComboBox().clearValue();
		window.getNameAbrevField().clearValue();
		window.getStaffCode().clearValue();
		window.getRegisteredComboBox().clearValue();
		window.getSchoolComboBox().clearValue();
	}
	
	private void loadGenderCombo(final SchoolStaffWindow window , final String defaultValue) {
		LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();
		valueMap.put("female", "Female");
		valueMap.put("male", "Male");
		
		window.getGenderComboBox().setValueMap(valueMap);
	}
	
	private void loadRegisteredCombo(final SchoolStaffWindow window , final String defaultValue) {
		LinkedHashMap<Boolean, String> valueMap = new LinkedHashMap<>();
		valueMap.put(true, "Yes");
		valueMap.put(false, "No");
		
		window.getRegisteredComboBox().setValueMap(valueMap);
	}
	
	
	
	private void loadSchoolCombo(final SchoolStaffWindow window, final String defaultValue) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.GET_SCHOOL, null);
		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
		SC.showPrompt("", "", new SwizimaLoader());

		dispatcher.execute(new RequestAction(RequestConstant.GET_SCHOOL , map), new AsyncCallback<RequestResult>() {

			@Override
			public void onFailure(Throwable caught) {
				System.out.println(caught.getMessage());
				SC.warn("ERROR", caught.getMessage());
				GWT.log("ERROR " + caught.getMessage());
				SC.clearPrompt();

			}

			@Override
			public void onSuccess(RequestResult result) {

				SC.clearPrompt();
				SessionManager.getInstance().manageSession(result, placeManager);
				if (result != null) {

					if (result.getSystemFeedbackDTO() != null) {
						LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();

						for (SchoolDTO schoolDTO : result.getSchoolDTOs()) {
							valueMap.put(schoolDTO.getId(), schoolDTO.getName());
						}
						window.getSchoolComboBox().setValueMap(valueMap);
						if (defaultValue != null) {
							window.getSchoolComboBox().setValue(defaultValue);
						}
					}
				} else {
					SC.warn("ERROR", "Unknow error");
				}

			}
		});
	}

	private void getAllSchoolStaff() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.GET_SCHOOL_STAFF, null);
		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
		SC.showPrompt("", "", new SwizimaLoader());

		dispatcher.execute(new RequestAction(RequestConstant.GET_SCHOOL_STAFF , map),
				new AsyncCallback<RequestResult>() {

					@Override
					public void onFailure(Throwable caught) {
						System.out.println(caught.getMessage());
						SC.warn("ERROR", caught.getMessage());
						GWT.log("ERROR " + caught.getMessage());
						SC.clearPrompt();

					}

					@Override
					public void onSuccess(RequestResult result) {

						SC.clearPrompt();
						SessionManager.getInstance().manageSession(result, placeManager);
						if (result != null) {
                            SystemFeedbackDTO feedbackDTO = result.getSystemFeedbackDTO();
							if ( feedbackDTO != null) {
								if (result.getSystemFeedbackDTO().isResponse()) {
									// SC.say("SUCCESS", result.getSystemFeedbackDTO().getMessage());
									getView().getSchoolStaffPane().getStaffListGrid().addRecordsToGrid(result.getSchoolStaffDTOs());
								} else {
									SC.warn("Not Successful \n ERROR:", result.getSystemFeedbackDTO().getMessage());
								}
							}
						} else {
							SC.warn("ERROR", "Unknow error");
						}

					}

				});

	}

	
}