package com.planetsystems.tela.managementapp.client.presenter.staffenrollment;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.i18n.client.DateTimeFormat;
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
import com.planetsystems.tela.dto.AcademicYearDTO;
import com.planetsystems.tela.dto.DistrictDTO;
import com.planetsystems.tela.dto.FilterDTO;
import com.planetsystems.tela.dto.GeneralUserDetailDTO;
import com.planetsystems.tela.dto.SchoolDTO;
import com.planetsystems.tela.dto.SchoolStaffDTO;
import com.planetsystems.tela.dto.StaffEnrollmentDto;
import com.planetsystems.tela.managementapp.client.gin.SessionManager;
import com.planetsystems.tela.managementapp.client.place.NameTokens;
import com.planetsystems.tela.managementapp.client.presenter.academicyear.term.AcademicTermListGrid;
import com.planetsystems.tela.managementapp.client.presenter.academicyear.term.AcademicTermWindow;
import com.planetsystems.tela.managementapp.client.presenter.comboutils.ComboUtil;
import com.planetsystems.tela.managementapp.client.presenter.main.MainPresenter;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkDataUtil;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkResult;
import com.planetsystems.tela.managementapp.client.presenter.staffenrollment.enrollment.StaffEnrollmentWindow;
import com.planetsystems.tela.managementapp.client.presenter.staffenrollment.staff.FilterStaffHeadCountWindow;
import com.planetsystems.tela.managementapp.client.presenter.staffenrollment.staff.FilterStaffWindow;
import com.planetsystems.tela.managementapp.client.presenter.staffenrollment.staff.SchoolStaffListGrid;
import com.planetsystems.tela.managementapp.client.presenter.staffenrollment.staff.SchoolStaffPane;
import com.planetsystems.tela.managementapp.client.presenter.staffenrollment.staff.SchoolStaffWindow;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.planetsystems.tela.managementapp.client.widget.MenuButton;
import com.planetsystems.tela.managementapp.shared.DatePattern;
import com.planetsystems.tela.managementapp.shared.RequestConstant;
import com.planetsystems.tela.managementapp.shared.RequestDelimeters;
import com.planetsystems.tela.managementapp.shared.RequestResult;
import com.planetsystems.tela.managementapp.shared.requestconstants.StaffEnrollmentRequest;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;

public class StaffEnrollmentPresenter
		extends Presenter<StaffEnrollmentPresenter.MyView, StaffEnrollmentPresenter.MyProxy> {
	interface MyView extends View {
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

	DateTimeFormat dateTimeFormat = DateTimeFormat
			.getFormat(DatePattern.DAY_MONTH_YEAR_HOUR_MINUTE_SECONDS.getPattern());
	DateTimeFormat dateFormat = DateTimeFormat.getFormat(DatePattern.DAY_MONTH_YEAR.getPattern());

	@NameToken(NameTokens.schoolStaff)
	@ProxyCodeSplit
	interface MyProxy extends ProxyPlace<StaffEnrollmentPresenter> {
	}

	@Inject
	StaffEnrollmentPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy, MainPresenter.SLOT_Main);
	}

	@Override
	protected void onBind() {
		super.onBind();
		onTabSelected();
		getAllStaffEnrollments();

	}

	private void onTabSelected() {
		getView().getTabSet().addTabSelectedHandler(new TabSelectedHandler() {

			@Override
			public void onTabSelected(TabSelectedEvent event) {

				String selectedTab = event.getTab().getTitle();

				if (selectedTab.equalsIgnoreCase(StaffEnrollmentView.STAFF_ENROLLMENT)) {
					getAllStaffEnrollments();
					MenuButton newButton = new MenuButton("New");
					MenuButton edit = new MenuButton("Edit");
					MenuButton delete = new MenuButton("Delete");
					MenuButton filter = new MenuButton("Filter");

					List<MenuButton> buttons = new ArrayList<>();
					buttons.add(newButton);
					buttons.add(edit);
					buttons.add(delete);
					buttons.add(filter);

					getView().getControlsPane().addMenuButtons(buttons);
					addStaffEnrollment(newButton);
					selectFilterStaffEnrollmentOption(filter);

				} else if (selectedTab.equalsIgnoreCase(StaffEnrollmentView.TEACHER_LIST)) {
					getAllSchoolStaff();
					MenuButton newButton = new MenuButton("New");
					MenuButton edit = new MenuButton("Edit");
					MenuButton delete = new MenuButton("Delete");
					MenuButton filter = new MenuButton("Filter");

					List<MenuButton> buttons = new ArrayList<>();
					buttons.add(newButton);
					buttons.add(edit);
					buttons.add(delete);
					buttons.add(filter);

					getView().getControlsPane().addMenuButtons(buttons);
					addSchoolStaff(newButton);
					editSchoolStaff(edit);
					selectFilterOption(filter);

				} else {
					List<MenuButton> buttons = new ArrayList<>();
					getView().getControlsPane().addMenuButtons(buttons);
				}

			}


		});
	}

	private void selectFilterOption(final MenuButton filter) {
		final Menu menu = new Menu();
		MenuItem basic = new MenuItem("Base Filter");
		MenuItem advanced = new MenuItem("Advanced Filter");

		menu.setItems(basic, advanced);

		filter.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				menu.showNextTo(filter, "bottom");
			}
		});

		basic.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				getView().getSchoolStaffPane().getSchoolStaffListGrid().setShowFilterEditor(true);
			}
		});

		advanced.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
//	   		SC.say("Advanced Search");
				FilterStaffWindow window = new FilterStaffWindow();
				loadFilterStaffDistrictCombo(window);
				loadFilterStaffSchoolCombo(window);
				window.show();
				filterSchoolStaffsByDistrictSchool(window);
			}
		});

	}

	
	private void editSchoolStaff(MenuButton edit) {
	edit.addClickHandler(new ClickHandler() {
		
		@Override
		public void onClick(ClickEvent event) {
			final SchoolStaffWindow window = new SchoolStaffWindow();
			window.getCodeField().disable();
			window.getSaveButton().setTitle("edit");
			ListGridRecord record = getView().getSchoolStaffPane().getSchoolStaffListGrid().getSelectedRecord();
			loadFieldsToEdit(window , record);

			window.show();
			updateSchoolStaff(window , record);
			
		}
	});
		
	}
	
	public void loadFieldsToEdit(final SchoolStaffWindow window , final ListGridRecord record) {

		window.getFirstNameField().setValue(record.getAttribute(SchoolStaffListGrid.FIRSTNAME));
		window.getLastNameField().setValue(record.getAttribute(SchoolStaffListGrid.LASTNAME));
		window.getEmailField().setValue(record.getAttribute(SchoolStaffListGrid.EMAIL));
		window.getDobItem().setValue(record.getAttribute(SchoolStaffListGrid.DOB));
		window.getCodeField().setValue(record.getAttribute(SchoolStaffListGrid.STAFF_CODE));
		window.getNationalIdField().setValue(record.getAttribute(SchoolStaffListGrid.NATIONAL_ID));
		window.getPhoneNumberField().setValue(record.getAttribute(SchoolStaffListGrid.PHONE_NUMBER));
		window.getNameAbrevField().setValue(record.getAttribute(SchoolStaffListGrid.NAME_ABBREV));
		
		loadDistrictCombo(window, record.getAttribute(SchoolStaffListGrid.DISTRICT_ID));
   		loadSchoolCombo(window, SchoolStaffListGrid.SCHOOL_ID);
		loadGenderCombo(window, record.getAttribute(SchoolStaffListGrid.GENDER));
		loadRegisteredCombo(window, record.getAttribute(SchoolStaffListGrid.REGISTERED));

	}
	
	
	private void updateSchoolStaff(final SchoolStaffWindow window , final ListGridRecord record) {
     window.getSaveButton().addClickHandler(new ClickHandler() {
		
		@Override
		public void onClick(ClickEvent event) {
		  	
			DistrictDTO districtDTO = new DistrictDTO(record.getAttribute(SchoolStaffListGrid.DISTRICT_ID));
			SchoolDTO schoolDTO = new SchoolDTO(record.getAttribute(SchoolStaffListGrid.SCHOOL_ID));
			schoolDTO.setDistrictDTO(districtDTO);
			
			
		  	SchoolStaffDTO schoolStaffDTO = new SchoolStaffDTO();
		  	schoolStaffDTO.setSchoolDTO(schoolDTO);
		  	schoolStaffDTO.setUpdatedDateTime(dateTimeFormat.format(new Date()));
		  	if(window.getRegisteredCombo().getValueAsString().equalsIgnoreCase("Yes")) {
			  	schoolStaffDTO.setRegistered(true);	
		  	}else {
			  	schoolStaffDTO.setRegistered(false);
		  	}
		  	
		  	schoolStaffDTO.setSchoolDTO(new SchoolDTO(window.getSchoolCombo().getID()));
		    
		  	GeneralUserDetailDTO generalUserDetailDTO = new GeneralUserDetailDTO();
		  	generalUserDetailDTO.setDob(dateFormat.format(window.getDobItem().getValueAsDate()));
		  	generalUserDetailDTO.setEmail(window.getEmailField().getValueAsString());
		  	generalUserDetailDTO.setFirstName(window.getFirstNameField().getValueAsString());
		  	generalUserDetailDTO.setGender(window.getGenderCombo().getValueAsString());
		  	generalUserDetailDTO.setLastName(window.getLastNameField().getValueAsString());
		  	generalUserDetailDTO.setNameAbbrev(window.getNameAbrevField().getValueAsString());
		  	generalUserDetailDTO.setPhoneNumber(window.getPhoneNumberField().getValueAsString());
		  	generalUserDetailDTO.setNationalId(window.getNationalIdField().getValueAsString());
		  	
		  	schoolStaffDTO.setGeneralUserDetailDTO(generalUserDetailDTO);
		  			  	
			LinkedHashMap<String, Object> map = new LinkedHashMap<>();
			map.put(StaffEnrollmentRequest.DATA, schoolStaffDTO);
			map.put(StaffEnrollmentRequest.ID, record.getAttribute(SchoolStaffListGrid.ID));
			map.put(NetworkDataUtil.ACTION, StaffEnrollmentRequest.UPDATE);
			NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

				@Override
				public void onNetworkResult(RequestResult result) {
					window.close();
					SC.say("SUCCESS", result.getSystemFeedbackDTO().getMessage());
					getAllSchoolStaff();
				}
			});
		  	
		}
	});
		
	}

	private void selectFilterStaffEnrollmentOption(final MenuButton filter) {
		final Menu menu = new Menu();
		MenuItem basic = new MenuItem("Base Filter");
		MenuItem advanced = new MenuItem("Advanced Filter");

		menu.setItems(basic, advanced);

		filter.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				menu.showNextTo(filter, "bottom");
			}
		});

		basic.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				getView().getStaffEnrollmentPane().getStaffEnrollmentListGrid().setShowFilterEditor(true);
			}
		});

		advanced.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
//	   		SC.say("Advanced Search");
				FilterStaffHeadCountWindow window = new FilterStaffHeadCountWindow();
				loadFilterStaffHeadCountDistrictCombo(window);
				loadFilterStaffHeadCountSchoolCombo(window);
				loadFilterStaffHeadCountAcademicYearCombo(window);
				loadFilterStaffHeadCountAcademicTermCombo(window);
				filterSchoolStaffEnrollmentByAcademicYearAcademicTermDistrictSchool(window);
				window.show();
			}
		});
	}

	//////////////////////

	private void addStaffEnrollment(MenuButton newButton) {
		newButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				StaffEnrollmentWindow window = new StaffEnrollmentWindow();
				setStaffTotal(window);
				
				loadAcademicYearCombo(window, null);
				loadAcademicTermCombo(window, null);
				loadDistrictCombo(window, null);
				
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
				if (checkIfNoStaffEnrollmentWindowFieldIsEmpty(window)) {
					StaffEnrollmentDto dto = new StaffEnrollmentDto();
					dto.setTotalFemale(Long.valueOf(window.getTotalFemaleField().getValueAsString()));
					dto.setTotalMale(Long.valueOf(window.getTotalMaleField().getValueAsString()));
					dto.setCreatedDateTime(dateTimeFormat.format(new Date()));

					AcademicTermDTO academicTermDTO = new AcademicTermDTO(
							window.getAcademicTermCombo().getValueAsString());
					dto.setAcademicTermDTO(academicTermDTO);
					// dto.setId(id);
					SchoolDTO schoolDTO = new SchoolDTO(window.getSchoolCombo().getValueAsString());
					dto.setSchoolDTO(schoolDTO);

					GWT.log("DTO " + dto);
					GWT.log("term " + dto.getAcademicTermDTO().getId());
					GWT.log("DTO " + dto.getSchoolDTO().getId());

					LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					map.put(RequestConstant.SAVE_STAFF_ENROLLMENT, dto);
					map.put(NetworkDataUtil.ACTION, RequestConstant.SAVE_STAFF_ENROLLMENT);
					NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

						@Override
						public void onNetworkResult(RequestResult result) {
							clearStaffEnrollmentWindowFields(window);
							SC.say("SUCCESS", result.getSystemFeedbackDTO().getMessage());
							getAllStaffEnrollments();
						}
					});

				} else {
					SC.warn("Please Fill all fields");
				}

			}
		});

	}

	private boolean checkIfNoStaffEnrollmentWindowFieldIsEmpty(StaffEnrollmentWindow window) {
		boolean flag = true;

		if (window.getAcademicYearCombo().getValueAsString() == null)
			flag = false;

		if (window.getAcademicTermCombo().getValueAsString() == null)
			flag = false;

		if (window.getDistrictCombo().getValueAsString() == null)
			flag = false;

		if (window.getSchoolCombo().getValueAsString() == null)
			flag = false;

		if (window.getTotalFemaleField().getValueAsString() == null)
			flag = false;

		if (window.getTotalMaleField().getValueAsString() == null)
			flag = false;

		if (window.getStaffTotalField().getValueAsString() == null)
			flag = false;

		return flag;
	}

	private void clearStaffEnrollmentWindowFields(StaffEnrollmentWindow window) {

		window.getAcademicYearCombo().clearValue();
		window.getAcademicTermCombo().clearValue();
		window.getDistrictCombo().clearValue();
		window.getSchoolCombo().clearValue();
		window.getTotalFemaleField().clearValue();
		window.getTotalFemaleField().clearValue();
		window.getStaffTotalField().clearValue();
	}

	///////////////////////////////////////// COMBOS

	private void loadAcademicYearCombo(final StaffEnrollmentWindow window, final String defaultValue) {
		ComboUtil.loadAcademicYearCombo(window.getAcademicYearCombo(), dispatcher, placeManager, defaultValue);
	}

	private void loadAcademicTermCombo(final StaffEnrollmentWindow window, final String defaultValue) {
		window.getAcademicYearCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				ComboUtil.loadAcademicTermComboByAcademicYear(window.getAcademicYearCombo(),
						window.getAcademicTermCombo(), dispatcher, placeManager, defaultValue);
			}
		});
	}

	private void loadDistrictCombo(final StaffEnrollmentWindow window, final String defaultValue) {
		ComboUtil.loadDistrictCombo(window.getDistrictCombo(), dispatcher, placeManager, defaultValue);
	}

	private void loadSchoolCombo(final StaffEnrollmentWindow window, final String defaultValue) {
		window.getDistrictCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				ComboUtil.loadSchoolComboByDistrict(window.getDistrictCombo(), window.getSchoolCombo(), dispatcher,
						placeManager, defaultValue);
			}
		});

	}

	/////////////////////////////////////////////////// FILTER SCHOOL STAFF
	/////////////////////////////////////////////////// COMBOS(2)

	// loads district combo in filterstaff pane
	private void loadFilterStaffDistrictCombo(final FilterStaffWindow window) {
		ComboUtil.loadDistrictCombo(window.getFilterStaffsPane().getDistrictCombo(), dispatcher, placeManager, null);
	}

	// loads school combo in filterschoolstaff pane
	private void loadFilterStaffSchoolCombo(final FilterStaffWindow window) {
		window.getFilterStaffsPane().getDistrictCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				ComboUtil.loadSchoolComboByDistrict(window.getFilterStaffsPane().getDistrictCombo(),
						window.getFilterStaffsPane().getSchoolCombo(), dispatcher, placeManager, null);
			}
		});

	}

	////////////////// FILTER STAFF HEAD COUNT COMBOS(4)

	// loads school combo in filterstaffheadcount pane
	private void loadFilterStaffHeadCountSchoolCombo(final FilterStaffHeadCountWindow window) {
		window.getFilterStaffHeadCountPane().getDistrictCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {

				ComboUtil.loadSchoolComboByDistrict(window.getFilterStaffHeadCountPane().getDistrictCombo(),
						window.getFilterStaffHeadCountPane().getSchoolCombo(), dispatcher, placeManager, null);
			}
		});

	}

	// loads district combo in filterstaff head count pane
	private void loadFilterStaffHeadCountDistrictCombo(final FilterStaffHeadCountWindow window) {
		ComboUtil.loadDistrictCombo(window.getFilterStaffHeadCountPane().getDistrictCombo(), dispatcher, placeManager,
				null);
	}

	// loads academic year combo in filter staff head count pane
	private void loadFilterStaffHeadCountAcademicYearCombo(final FilterStaffHeadCountWindow window) {
		ComboUtil.loadAcademicYearCombo(window.getFilterStaffHeadCountPane().getAcademicYearCombo(), dispatcher,
				placeManager, null);
	}

	// loads academic year combo in filter staff head count pane
	private void loadFilterStaffHeadCountAcademicTermCombo(final FilterStaffHeadCountWindow window) {
		window.getFilterStaffHeadCountPane().getAcademicYearCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				ComboUtil.loadAcademicTermComboByAcademicYear(
						window.getFilterStaffHeadCountPane().getAcademicYearCombo(),
						window.getFilterStaffHeadCountPane().getAcademicTermCombo(), dispatcher, placeManager, null);
			}
		});

	}

	/////////////////////////////////////// END COMBOS

	public void setStaffTotal(final StaffEnrollmentWindow window) {
		final int[] totalMale = new int[1];
		final int[] totalFemale = new int[1];
		final int[] total = { 0 };
		window.getTotalFemaleField().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				if (window.getTotalFemaleField().getValueAsString() == null) {
					totalFemale[0] = 0;
				} else {
					totalFemale[0] = Integer.parseInt(window.getTotalFemaleField().getValueAsString());
				}

				if (window.getTotalMaleField().getValueAsString() == null) {
					totalMale[0] = 0;
				}

				total[0] = totalFemale[0] + totalMale[0];
				window.getStaffTotalField().setValue(total[0]);
			}
		});

		window.getTotalMaleField().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				if (window.getTotalMaleField().getValueAsString() == null) {
					totalMale[0] = 0;
				} else {
					totalMale[0] = Integer.parseInt(window.getTotalMaleField().getValueAsString());
				}

				if (window.getTotalFemaleField().getValueAsString() == null) {
					totalFemale[0] = 0;
				}

				total[0] = totalFemale[0] + totalMale[0];
				window.getStaffTotalField().setValue(total[0]);
			}
		});

	}

	private void getAllStaffEnrollments() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		if (SessionManager.getInstance().getLoggedInUserGroup().equalsIgnoreCase(SessionManager.ADMIN))
			map.put(NetworkDataUtil.ACTION, RequestConstant.GET_STAFF_ENROLLMENT);
		else
			map.put(NetworkDataUtil.ACTION, RequestConstant.GET_STAFF_ENROLLMENTS_SYSTEM_USER_PROFILE_SCHOOLS);

		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {
				getView().getStaffEnrollmentPane().getStaffEnrollmentListGrid()
						.addRecordsToGrid(result.getStaffEnrollmentDtos());
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
				loadDistrictCombo(window, null);
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

				if (checkIfNoSchoolStaffWindowFieldIsEmpty(window)) {
					SchoolStaffDTO dto = new SchoolStaffDTO();
					// dto.setId(id);

					dto.setRegistered(Boolean.valueOf(window.getRegisteredCombo().getValueAsString()));
					dto.setCreatedDateTime(dateTimeFormat.format(new Date()));

					dto.setStaffCode(window.getCodeField().getValueAsString());
					// dto.setStatus(status);
					// dto.setStaffType(staffType);

					SchoolDTO schoolDTO = new SchoolDTO(window.getSchoolCombo().getValueAsString());
					dto.setSchoolDTO(schoolDTO);

					GeneralUserDetailDTO generalUserDetailDTO = new GeneralUserDetailDTO();
					generalUserDetailDTO.setFirstName(window.getFirstNameField().getValueAsString());
					generalUserDetailDTO.setLastName(window.getLastNameField().getValueAsString());
					generalUserDetailDTO.setEmail(window.getEmailField().getValueAsString());
					generalUserDetailDTO.setPhoneNumber(window.getPhoneNumberField().getValueAsString());
					generalUserDetailDTO.setGender(window.getGenderCombo().getValueAsString());
					generalUserDetailDTO.setNameAbbrev(window.getNameAbrevField().getValueAsString());
					generalUserDetailDTO.setDob(dateFormat.format(window.getDobItem().getValueAsDate()));
					generalUserDetailDTO.setNationalId(window.getNationalIdField().getValueAsString());

					dto.setGeneralUserDetailDTO(generalUserDetailDTO);

					GWT.log("STAFF " + dto);
					GWT.log("School " + dto.getSchoolDTO().getId());

					LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					map.put(RequestConstant.SAVE_SCHOOL_STAFF, dto);
					map.put(NetworkDataUtil.ACTION, RequestConstant.SAVE_SCHOOL_STAFF);

					NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

						@Override
						public void onNetworkResult(RequestResult result) {
							clearSchoolStaffWindowFields(window);
							SC.say("SUCCESS", result.getSystemFeedbackDTO().getMessage());
							getAllSchoolStaff();
						}
					});

				} else {
					SC.warn("Please Fill the fields");
				}

			}
		});
	}

	protected boolean checkIfNoSchoolStaffWindowFieldIsEmpty(SchoolStaffWindow window) {
		boolean flag = true;

		if (window.getFirstNameField().getValueAsString() == null)
			flag = false;

		if (window.getLastNameField().getValueAsString() == null)
			flag = false;

		if (window.getPhoneNumberField().getValueAsString() == null)
			flag = false;

		if (window.getEmailField().getValueAsString() == null)
			flag = false;

		if (window.getDobItem().getValueAsDate() == null)
			flag = false;

		if (window.getNationalIdField().getValueAsString() == null)
			flag = false;

		if (window.getGenderCombo().getValueAsString() == null)
			flag = false;

		if (window.getNameAbrevField().getValueAsString() == null)
			flag = false;

		if (window.getCodeField().getValueAsString() == null)
			flag = false;

		if (window.getRegisteredCombo().getValueAsString() == null)
			flag = false;

		if (window.getSchoolCombo().getValueAsString() == null)
			flag = false;

		if (window.getDistrictCombo().getValueAsString() == null)
			flag = false;

		return flag;
	}

	private void clearSchoolStaffWindowFields(SchoolStaffWindow window) {

		window.getFirstNameField().clearValue();
		window.getLastNameField().clearValue();
		window.getPhoneNumberField().clearValue();
		window.getEmailField().clearValue();
		window.getDobItem().clearValue();
		window.getNationalIdField().clearValue();
		window.getGenderCombo().clearValue();
		window.getNameAbrevField().clearValue();
		window.getCodeField().clearValue();
		window.getRegisteredCombo().clearValue();
		window.getSchoolCombo().clearValue();
	}

	private void loadGenderCombo(final SchoolStaffWindow window, final String defaultValue) {
		LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();
		valueMap.put("female", "Female");
		valueMap.put("male", "Male");
		window.getGenderCombo().setValueMap(valueMap);
		if (defaultValue != null) {
			window.getGenderCombo().setValue(defaultValue);
		}
	}

	private void loadRegisteredCombo(final SchoolStaffWindow window, final String defaultValue) {
		LinkedHashMap<Boolean, String> valueMap = new LinkedHashMap<>();
		valueMap.put(true, "Yes");
		valueMap.put(false, "No");

		window.getRegisteredCombo().setValueMap(valueMap);
		if (defaultValue != null) {
			window.getRegisteredCombo().setValue(defaultValue);
		}
	}

	private void loadDistrictCombo(final SchoolStaffWindow window, final String defaultValue) {
		ComboUtil.loadDistrictCombo(window.getDistrictCombo(), dispatcher, placeManager, defaultValue);
	}

	private void loadSchoolCombo(final SchoolStaffWindow window, final String defaultValue) {
		window.getDistrictCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				ComboUtil.loadSchoolComboByDistrict(window.getDistrictCombo(), window.getSchoolCombo(), dispatcher,
						placeManager, defaultValue);
			}
		});
	}

	private void getAllSchoolStaff() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		if (SessionManager.getInstance().getLoggedInUserGroup().equalsIgnoreCase(SessionManager.ADMIN))
			map.put(NetworkDataUtil.ACTION ,RequestConstant.GET_SCHOOL_STAFF);
		else
			map.put(NetworkDataUtil.ACTION, RequestConstant.GET_SCHOOL_STAFFS_BY_SYSTEM_USER_PROFILE_SCHOOLS);
		

		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {
				getView().getSchoolStaffPane().getSchoolStaffListGrid().addRecordsToGrid(result.getSchoolStaffDTOs());
			}
		});

	}

	// filter
	private void filterSchoolStaffsByDistrictSchool(final FilterStaffWindow window) {
		window.getFilterButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String districtId = window.getFilterStaffsPane().getDistrictCombo().getValueAsString();
				String schoolId = window.getFilterStaffsPane().getSchoolCombo().getValueAsString();

				FilterDTO dto = new FilterDTO();
				dto.setDistrictDTO(new DistrictDTO(districtId));
				dto.setSchoolDTO(new SchoolDTO(schoolId));

				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(RequestDelimeters.FILTER_SCHOOL_STAFFS, dto);
				map.put(NetworkDataUtil.ACTION, RequestConstant.FILTER_SCHOOL_STAFFS_BY_DISTRICT_SCHOOL);

				NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

					@Override
					public void onNetworkResult(RequestResult result) {
						window.close();
						getView().getSchoolStaffPane().getSchoolStaffListGrid().addRecordsToGrid(result.getSchoolStaffDTOs());
					}
				});

			}
		});
	}

	private void filterSchoolStaffEnrollmentByAcademicYearAcademicTermDistrictSchool(
			final FilterStaffHeadCountWindow window) {
		window.getFilterButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String academicYearId = window.getFilterStaffHeadCountPane().getAcademicYearCombo().getValueAsString();
				String academicTermId = window.getFilterStaffHeadCountPane().getAcademicTermCombo().getValueAsString();
				String districtId = window.getFilterStaffHeadCountPane().getDistrictCombo().getValueAsString();
				String schoolId = window.getFilterStaffHeadCountPane().getSchoolCombo().getValueAsString();

				FilterDTO dto = new FilterDTO();
				dto.setAcademicYearDTO(new AcademicYearDTO(academicYearId));
				dto.setAcademicTermDTO(new AcademicTermDTO(academicTermId));
				dto.setDistrictDTO(new DistrictDTO(districtId));
				dto.setSchoolDTO(new SchoolDTO(schoolId));

				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(RequestDelimeters.FILTER_STAFF_ENROLLMENTS, dto);
				map.put(NetworkDataUtil.ACTION,
						RequestConstant.FILTER_SCHOOL_STAFF_ENROLLMENTS_BY_ACADEMIC_YEAR_ACADEMIC_TERM_DISTRICT_SCHOOL);

				NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

					@Override
					public void onNetworkResult(RequestResult result) {
						window.close();
						getView().getStaffEnrollmentPane().getStaffEnrollmentListGrid()
								.addRecordsToGrid(result.getStaffEnrollmentDtos());
					}
				});

			}
		});
	}

}