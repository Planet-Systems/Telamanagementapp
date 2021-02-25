package com.planetsystems.tela.managementapp.client.presenter.enrollment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.GwtEvent.Type;
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
import com.planetsystems.tela.dto.LearnerEnrollementDTO;
import com.planetsystems.tela.dto.SchoolClassDTO;
import com.planetsystems.tela.dto.SchoolDTO;
import com.planetsystems.tela.dto.StaffEnrollementDto;
import com.planetsystems.tela.managementapp.client.gin.SessionManager;
import com.planetsystems.tela.managementapp.client.place.NameTokens;
import com.planetsystems.tela.managementapp.client.presenter.main.MainPresenter;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.planetsystems.tela.managementapp.client.widget.MenuButton;
import com.planetsystems.tela.managementapp.client.widget.SwizimaLoader;
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
public class EnrollmentPresenter extends Presenter<EnrollmentPresenter.MyView, EnrollmentPresenter.MyProxy>  {
    interface MyView extends View  {
    	public ControlsPane getControlsPane();

    	public TabSet getTabSet();

    	public StaffEnrollmentPane getStaffEnrollmentPane();

    	public LearnerEnrollmentPane getLearnerEnrollmentPane();
    
    }
    @ContentSlot
    public static final Type<RevealContentHandler<?>> SLOT_Enrollment = new Type<RevealContentHandler<?>>();
    
    @Inject
    private DispatchAsync dispatcher;
    
    @Inject
    private PlaceManager placeManager;

    @NameToken(NameTokens.enrollment)
    @ProxyCodeSplit
    interface MyProxy extends ProxyPlace<EnrollmentPresenter> {
    }

    @Inject
    EnrollmentPresenter(
            EventBus eventBus,
            MyView view, 
            MyProxy proxy) {
        super(eventBus, view, proxy, MainPresenter.SLOT_Main);
        
    }
    
    @Override
    protected void onBind() {
    	super.onBind();
     onTabSelected();
    }
    private void onTabSelected() {
		getView().getTabSet().addTabSelectedHandler(new TabSelectedHandler() {

			@Override
			public void onTabSelected(TabSelectedEvent event) {

				String selectedTab = event.getTab().getTitle();

				if (selectedTab.equalsIgnoreCase(EnrollmentView.STAFF_ENROLLMENT)) {
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

				} else if (selectedTab.equalsIgnoreCase(EnrollmentView.LEARNERS_ENROLLMENT)){
		
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
					addLearnerEnrollment(newButton);
//					addDistrict(newButton);
//					deleteDistrict(delete);
//					editDistrict(edit);

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
		   StaffEnrollementDto dto = new StaffEnrollementDto();
		   dto.setTotalFemale(Long.valueOf(window.getTotalFemaleField().getValueAsString()));
		   dto.setTotalMale(Long.valueOf(window.getTotalMaleField().getValueAsString()));
		   
		   AcademicTermDTO academicTermDTO = new AcademicTermDTO(window.getAcademicTermComboBox().getValueAsString());
		   dto.setAcademicTermDTO(academicTermDTO);
		   //dto.setId(id);
		   SchoolDTO schoolDTO = new SchoolDTO(window.getSchoolComboBox().getValueAsString());
		   dto.setSchoolDTO(schoolDTO);
		   
		   GWT.log("DTO "+dto);
		   GWT.log("term "+dto.getAcademicTermDTO().getId());
		   GWT.log("DTO "+dto.getSchoolDTO().getId());
		   
		}
	});
		
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

	private void loadSchoolClassCombo(final LearnerEnrollmentWindow window, final String defaultValue) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.GET_SCHOOL_CLASS, null);
		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
		SC.showPrompt("", "", new SwizimaLoader());

		dispatcher.execute(new RequestAction(RequestConstant.GET_SCHOOL_CLASS , map), new AsyncCallback<RequestResult>() {

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

						for (SchoolClassDTO schoolClassDTO : result.getSchoolClassDTOs()) {
							valueMap.put(schoolClassDTO.getId(), schoolClassDTO.getName());
						}
						window.getSchoolClassComboBox().setValueMap(valueMap);
						if (defaultValue != null) {
							window.getSchoolClassComboBox().setValue(defaultValue);
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




public void	setLearnerTotal(final LearnerEnrollmentWindow window){
	final int[] totalGirls = new int[1];
	final int[] totalBoys = new int[1];
	final int[] total = {0};
	window.getTotalBoysField().addChangedHandler(new ChangedHandler() {
		
		@Override
		public void onChanged(ChangedEvent event) {
			
			if(window.getTotalBoysField().getValueAsString() == null) {
				totalBoys[0] = 0;
			}else {
				totalBoys[0] = Integer.parseInt(window.getTotalBoysField().getValueAsString());
			}
		
			if(window.getTotalGirlsField().getValueAsString() == null) {
				totalGirls[0] = 0;
			}
			
				total[0] = totalGirls[0] + totalBoys[0];
				 window.getLearnerTotalField().setValue(total[0]);
		}
	});
	
	
window.getTotalGirlsField().addChangedHandler(new ChangedHandler() {
		
		@Override
		public void onChanged(ChangedEvent event) {
			if(window.getTotalGirlsField().getValueAsString() == null) {
				totalGirls[0] = 0;
			}else {
				totalGirls[0] = Integer.parseInt(window.getTotalGirlsField().getValueAsString());	
			}
			
			if(window.getTotalBoysField().getValueAsString() == null) {
				totalBoys[0] = 0;
			}

				total[0] = totalGirls[0] + totalBoys[0];
				 window.getLearnerTotalField().setValue(total[0]);
		}
	});

	  	
	
	}
	
	///////////////////////////LEARNERS
	
	
	
	private void addLearnerEnrollment(MenuButton newButton) {
	newButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				  LearnerEnrollmentWindow window = new LearnerEnrollmentWindow();
				  setLearnerTotal(window);
				  loadSchoolClassCombo(window, null);
				  window.show();
				  
				  saveLearnerEnrollment(window);
			}

		});
	}

	private void saveLearnerEnrollment(final LearnerEnrollmentWindow window) {
	  window.getSaveButton().addClickHandler(new ClickHandler() {
		
		@Override
		public void onClick(ClickEvent event) {
			LearnerEnrollementDTO dto = new LearnerEnrollementDTO();
			//dto.setId(id);
			dto.setTotalBoys(Long.valueOf(window.getTotalBoysField().getValueAsString()));
			dto.setTotalGirls(Long.valueOf(window.getTotalGirlsField().getValueAsString()));
			
			SchoolClassDTO schoolClassDTO = new SchoolClassDTO(window.getSchoolClassComboBox().getValueAsString());
			dto.setSchoolClassDTO(schoolClassDTO);
			
			GWT.log("DTO "+dto);
			GWT.log("ID "+dto.getSchoolClassDTO().getId());
		}
	});
		
		
	}
	
}