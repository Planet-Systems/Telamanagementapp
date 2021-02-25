package com.planetsystems.tela.managementapp.client.presenter.staffattendance;

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
import com.planetsystems.tela.dto.ClockInDTO;
import com.planetsystems.tela.dto.LearnerAttendanceDTO;
import com.planetsystems.tela.dto.SchoolClassDTO;
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
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;

public class StaffAttendancePresenter extends Presenter<StaffAttendancePresenter.MyView, StaffAttendancePresenter.MyProxy> {
	interface MyView extends View {
		public ControlsPane getControlsPane();

		public TabSet getTabSet();

		public ClockOutPane getClockOutPane();

		public ClockInPane getClockInPane();
	}

	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_attendance = new Type<RevealContentHandler<?>>();
	
	@Inject
	private PlaceManager placeManager;

	@Inject
	private DispatchAsync dispatcher;
	
	@NameToken(NameTokens.staffAttendance)
	@ProxyCodeSplit
	interface MyProxy extends ProxyPlace<StaffAttendancePresenter> {
	}

	@Inject
	StaffAttendancePresenter(EventBus eventBus, MyView view, MyProxy proxy) {
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

				if (selectedTab.equalsIgnoreCase(StaffAttendanceView.CLOCKIN_TAB_TITLE)) {

					MenuButton clockInButton = new MenuButton("ClockIn");
					MenuButton edit = new MenuButton("Edit");
					MenuButton delete = new MenuButton("Delete");
					MenuButton clockOut = new MenuButton("ClockOut");
					MenuButton fiter = new MenuButton("Filter");

					List<MenuButton> buttons = new ArrayList<>();
					buttons.add(clockInButton);
					//buttons.add(edit);
					//buttons.add(delete);
					buttons.add(fiter);
					buttons.add(clockOut);

					getView().getControlsPane().addMenuButtons(buttons);
					
					addClockIn(clockInButton);
		

				} else if (selectedTab.equalsIgnoreCase(StaffAttendanceView.CLOCKOUT_TAB_TITLE)) {

					//MenuButton newButton = new MenuButton("New");
					MenuButton edit = new MenuButton("Edit");
					MenuButton delete = new MenuButton("Delete");
					MenuButton fiter = new MenuButton("Filter");

					List<MenuButton> buttons = new ArrayList<>();
				///	buttons.add(newButton);
					//buttons.add(edit);
					// buttons.add(delete);
					buttons.add(fiter);

					getView().getControlsPane().addMenuButtons(buttons);


				} 
				else {
					List<MenuButton> buttons = new ArrayList<>();
					getView().getControlsPane().addMenuButtons(buttons);
				}

			}


		});
	}
	
	
	private void addClockIn(MenuButton clockInButton) {
		clockInButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
			
				  ClockInWindow window	= new ClockInWindow();
				  window.show();
				  
				  loadAcademicTermCombo(window , null);
				  loadSchoolStaffCombo(window , null);
				  
				  saveClockin(window);
			}
		});

	}

	
	protected void saveClockin(final ClockInWindow window) {
	    window.getSaveButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				ClockInDTO dto = new ClockInDTO();
				//dto.setClockInDate(clockInDate);
				dto.setComment(window.getCommentField().getValueAsString());
				//dto.setId(id);
				dto.setLatitude(window.getLatitudeField().getValueAsString());
				dto.setLongitude(window.getLongitudeField().getValueAsString());
				
				
				AcademicTermDTO academicTermDTO = new AcademicTermDTO(window.getAcademicTermComboBox().getValueAsString());
				dto.setAcademicTermDTO(academicTermDTO);
				
//				SchoolStaffDTO schoolStaffDTO = new SchoolStaffDTO();
//				schoolStaffDTO.setId(window.getSchoolStaffComboBox().getValueAsString());
//				dto.setSchoolStaffDTO(schoolStaffDTO);
//				
				GWT.log("DTO "+dto);
				GWT.log("Term "+dto.getAcademicTermDTO().getId());
				
				
				

			}
		});
		
	}


	private void loadAcademicTermCombo(final ClockInWindow window, final String defaultValue) {
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

	
	
	private void loadSchoolStaffCombo(final ClockInWindow window, final String defaultValue) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.GET_SCHOOL_STAFF, null);
		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
//		SC.showPrompt("", "", new SwizimaLoader());
//
//		dispatcher.execute(new RequestAction( RequestConstant.GET_SCHOOL_STAFF , map),
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
//									String fullName = schoolStaffDTO.getGeneralUserDetailDTO().getFirstName() + schoolStaffDTO.getGeneralUserDetailDTO().getLastName(); 
//									valueMap.put(schoolStaffDTO.getId(), fullName);
//								}
//								window.getSchoolStaffComboBox().setValueMap(valueMap);
//
//								if (defaultValue != null) {
//									window.getSchoolStaffComboBox().setValue(defaultValue);
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

	
	
	
	
	
	


	
	
	
}