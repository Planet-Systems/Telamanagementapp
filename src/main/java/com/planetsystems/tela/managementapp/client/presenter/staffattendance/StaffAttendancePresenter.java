package com.planetsystems.tela.managementapp.client.presenter.staffattendance;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
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
import com.planetsystems.tela.dto.ClockInDTO;
import com.planetsystems.tela.dto.ClockOutDTO;
import com.planetsystems.tela.dto.DistrictDTO;
import com.planetsystems.tela.dto.FilterDTO;
import com.planetsystems.tela.dto.SchoolDTO;
import com.planetsystems.tela.dto.SchoolStaffDTO;
import com.planetsystems.tela.dto.response.SystemResponseDTO;
import com.planetsystems.tela.managementapp.client.gin.SessionManager;
import com.planetsystems.tela.managementapp.client.place.NameTokens;
import com.planetsystems.tela.managementapp.client.presenter.comboutils.ComboUtil;
import com.planetsystems.tela.managementapp.client.presenter.comboutils.ComboUtil2;
import com.planetsystems.tela.managementapp.client.presenter.main.MainPresenter;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkDataUtil;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkDataUtil2;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkResult;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkResult2;
import com.planetsystems.tela.managementapp.client.presenter.staffattendance.clockin.ClockInListGrid;
import com.planetsystems.tela.managementapp.client.presenter.staffattendance.clockin.ClockInPane;
import com.planetsystems.tela.managementapp.client.presenter.staffattendance.clockin.ClockInWindow;
import com.planetsystems.tela.managementapp.client.presenter.staffattendance.clockin.FilterClockInWindow;
import com.planetsystems.tela.managementapp.client.presenter.staffattendance.clockout.ClockOutPane;
import com.planetsystems.tela.managementapp.client.presenter.staffattendance.clockout.FilterClockOutWindow;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.planetsystems.tela.managementapp.client.widget.MenuButton;
import com.planetsystems.tela.managementapp.shared.DatePattern;
import com.planetsystems.tela.managementapp.shared.MyRequestAction;
import com.planetsystems.tela.managementapp.shared.MyRequestResult;
import com.planetsystems.tela.managementapp.shared.RequestConstant;
import com.planetsystems.tela.managementapp.shared.RequestDelimeters;
import com.planetsystems.tela.managementapp.shared.RequestResult;
import com.planetsystems.tela.managementapp.shared.requestcommands.ClockInOutCommand;
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

public class StaffAttendancePresenter
		extends Presenter<StaffAttendancePresenter.MyView, StaffAttendancePresenter.MyProxy> {
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

	DateTimeFormat dateTimeFormat = DateTimeFormat
			.getFormat(DatePattern.DAY_MONTH_YEAR_HOUR_MINUTE_SECONDS.getPattern());
	DateTimeFormat dateFormat = DateTimeFormat.getFormat(DatePattern.DAY_MONTH_YEAR.getPattern());
	DateTimeFormat timeFormat = DateTimeFormat.getFormat(PredefinedFormat.HOUR24_MINUTE_SECOND);

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
					MenuButton clockOut = new MenuButton("ClockOut");
					MenuButton filter = new MenuButton("Filter");

					List<MenuButton> buttons = new ArrayList<>();
					buttons.add(clockInButton);
					buttons.add(filter);
					buttons.add(clockOut);

					getView().getControlsPane().addMenuButtons(buttons);

					getAllStaffClockIn2();
					addClockIn(clockInButton);
					clockOut2(clockOut);
					selectFilterOption(filter);

				} else if (selectedTab.equalsIgnoreCase(StaffAttendanceView.CLOCKOUT_TAB_TITLE)) {
					getAllStaffClockOut2();
					MenuButton filter = new MenuButton("Filter");

					List<MenuButton> buttons = new ArrayList<>();

					buttons.add(filter);

					getView().getControlsPane().addMenuButtons(buttons);
					selectFilterClockOutOption(filter);

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
				getView().getClockInPane().getClockInListGrid().setShowFilterEditor(true);
			}
		});

		advanced.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				FilterClockInWindow window = new FilterClockInWindow();
				loadFilterClockInCombos(window, null);
				window.show();
				filterClockIns(window);
			}

		});

	}

	private void selectFilterClockOutOption(final MenuButton filter) {
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
				getView().getClockOutPane().getClockOutListGrid().setShowFilterEditor(true);
			}
		});

		advanced.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				FilterClockOutWindow window = new FilterClockOutWindow();
				loadFilterClockOutCombos(window, null);
				window.show();
				filterClockOuts(window);
			}

		});

	}

	///////////////////////////////////////////
	private void addClockIn(MenuButton clockInButton) {
		clockInButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				ClockInWindow window = new ClockInWindow();
				window.show();
				loadAddClockOutCombos(window, null, null, null, null, null);

				saveClockIn(window);
			}

		});

	}

	private boolean checkIfNoClockInWindowFieldIsEmpty(ClockInWindow window) {
		boolean flag = true;

		if (window.getAcademicYearCombo().getValueAsString() == null)
			flag = false;

		if (window.getAcademicTermCombo().getValueAsString() == null)
			flag = false;

		if (window.getDistrictCombo().getValueAsString() == null)
			flag = false;

		if (window.getSchoolCombo().getValueAsString() == null)
			flag = false;

		if (window.getSchoolStaffCombo().getValueAsString() == null)
			flag = false;

		// if(window.getCommentField().getValueAsString() == null) flag = false;

		if (window.getLatitudeField().getValueAsString() == null)
			flag = false;

		if (window.getLongitudeField().getValueAsString() == null)
			flag = false;

		return flag;
	}

	private void clearClockInWindowFields(ClockInWindow window) {
		window.getAcademicYearCombo().clearValue();
		window.getAcademicTermCombo().clearValue();
		window.getDistrictCombo().clearValue();
		window.getSchoolCombo().clearValue();
		window.getSchoolStaffCombo().clearValue();
		window.getCommentField().clearValue();
		window.getLatitudeField().clearValue();
		window.getLongitudeField().clearValue();
	}

	private void loadAddClockOutCombos(final ClockInWindow window, final String year, final String term,
			final String district, final String school, final String staff) {
		ComboUtil2.loadAcademicYearCombo(window.getAcademicYearCombo(), dispatcher, placeManager, year);

		window.getAcademicYearCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				ComboUtil2.loadAcademicTermComboByAcademicYear(window.getAcademicYearCombo(),
						window.getAcademicTermCombo(), dispatcher, placeManager, term);
			}
		});

		ComboUtil2.loadDistrictCombo(window.getDistrictCombo(), dispatcher, placeManager, district);

		window.getDistrictCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				ComboUtil2.loadSchoolComboByDistrict(window.getDistrictCombo(), window.getSchoolCombo(), dispatcher,
						placeManager, school);
			}
		});

		window.getSchoolCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				ComboUtil2.loadSchoolStaffComboBySchool(window.getSchoolCombo(), window.getSchoolStaffCombo(),
						dispatcher, placeManager, staff);
			}
		});

	}

///////////////////////FILTER CLOCKIN COMBOS(4)

	private void loadFilterClockInCombos(final FilterClockInWindow window, final String defaultValue) {
		window.getFilterClockInPane().getDistrictCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				ComboUtil2.loadSchoolComboByDistrict(window.getFilterClockInPane().getDistrictCombo(),
						window.getFilterClockInPane().getSchoolCombo(), dispatcher, placeManager, defaultValue);
			}
		});

		ComboUtil2.loadDistrictCombo(window.getFilterClockInPane().getDistrictCombo(), dispatcher, placeManager,
				defaultValue);

		ComboUtil2.loadAcademicYearCombo(window.getFilterClockInPane().getAcademicYearCombo(), dispatcher, placeManager,
				defaultValue);

		window.getFilterClockInPane().getAcademicYearCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {

				ComboUtil2.loadAcademicTermComboByAcademicYear(window.getFilterClockInPane().getAcademicYearCombo(),
						window.getFilterClockInPane().getAcademicTermCombo(), dispatcher, placeManager, defaultValue);
			}
		});

	}

/////////////////////////////////////////END OF FILTER CLOCKIN COMBOS

///////////////////////FILTER CLOCKOUT COMBOS(4)

	private void loadFilterClockOutCombos(final FilterClockOutWindow window, final String defaultValue) {
				window.getFilterClockOutPane().getDistrictCombo().addChangedHandler(new ChangedHandler() {

					@Override
					public void onChanged(ChangedEvent event) {
						ComboUtil2.loadSchoolComboByDistrict(window.getFilterClockOutPane().getDistrictCombo(),
								window.getFilterClockOutPane().getSchoolCombo(), dispatcher, placeManager, defaultValue);
					}
				});


				ComboUtil2.loadDistrictCombo(window.getFilterClockOutPane().getDistrictCombo(), dispatcher, placeManager, defaultValue);

				ComboUtil2.loadAcademicYearCombo(window.getFilterClockOutPane().getAcademicYearCombo(), dispatcher, placeManager, defaultValue);

				window.getFilterClockOutPane().getAcademicYearCombo().addChangedHandler(new ChangedHandler() {

					@Override
					public void onChanged(ChangedEvent event) {

						ComboUtil2.loadAcademicTermComboByAcademicYear(window.getFilterClockOutPane().getAcademicYearCombo(),
								window.getFilterClockOutPane().getAcademicTermCombo(), dispatcher, placeManager, defaultValue);
					}
				});
	}

/////////////////////////////////////////END OF FILTER CLOCKOUT COMBOS

	// filter
	private void filterClockIns(final FilterClockInWindow window) {
		window.getFilterButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String academicYearId = window.getFilterClockInPane().getAcademicYearCombo().getValueAsString();
				String academicTermId = window.getFilterClockInPane().getAcademicTermCombo().getValueAsString();
				String districtId = window.getFilterClockInPane().getDistrictCombo().getValueAsString();
				String schoolId = window.getFilterClockInPane().getSchoolCombo().getValueAsString();
				String fromDate = dateFormat.format(window.getFilterClockInPane().getFromDateItem().getValueAsDate());
				String toDate = dateFormat.format(window.getFilterClockInPane().getToDateItem().getValueAsDate());

				FilterDTO dto = new FilterDTO();
				if (academicYearId != null)
					dto.setAcademicYearDTO(new AcademicYearDTO(academicYearId));
				if (academicTermId != null)
					dto.setAcademicTermDTO(new AcademicTermDTO(academicTermId));
				if (districtId != null)
					dto.setDistrictDTO(new DistrictDTO(districtId));
				if (schoolId != null)
					dto.setSchoolDTO(new SchoolDTO(schoolId));
				if (fromDate != null)
					dto.setFromDate(fromDate);
				if (toDate != null)
					dto.setToDate(toDate);

				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(MyRequestAction.DATA, dto);
				map.put(MyRequestAction.COMMAND, ClockInOutCommand.FILTER_CLOCKINS);

				clockInResponseList(map);
			}
		});
	}

	private void filterClockOuts(final FilterClockOutWindow window) {
		window.getFilterButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String academicYearId = window.getFilterClockOutPane().getAcademicYearCombo().getValueAsString();
				String academicTermId = window.getFilterClockOutPane().getAcademicTermCombo().getValueAsString();
				String districtId = window.getFilterClockOutPane().getDistrictCombo().getValueAsString();
				String schoolId = window.getFilterClockOutPane().getSchoolCombo().getValueAsString();
				String date = dateFormat.format(window.getFilterClockOutPane().getClockinDateItem().getValueAsDate());

				FilterDTO dto = new FilterDTO();
				if(academicYearId != null)
				dto.setAcademicYearDTO(new AcademicYearDTO(academicYearId));
				if(academicTermId != null)
				dto.setAcademicTermDTO(new AcademicTermDTO(academicTermId));
				if(districtId != null)
				dto.setDistrictDTO(new DistrictDTO(districtId));
				if(schoolId != null)
				dto.setSchoolDTO(new SchoolDTO(schoolId));
				if(date != null)
				dto.setDate(date);

				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(MyRequestAction.DATA, dto);
				map.put(MyRequestAction.COMMAND , ClockInOutCommand.FILTER_CLOCK_OUTS);
				
				clockOutResponseList(map);
			}
		});
	}

	////////////////////////////////////////////////////// NEW

	protected void saveClockIn(final ClockInWindow window) {
		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				if (checkIfNoClockInWindowFieldIsEmpty(window)) {
					ClockInDTO dto = new ClockInDTO();
					dto.setComment(window.getCommentField().getValueAsString());
					dto.setLatitude(window.getLatitudeField().getValueAsString());
					dto.setLongitude(window.getLongitudeField().getValueAsString());
					dto.setCreatedDateTime(dateTimeFormat.format(new Date()));
					dto.setClockInDate(dateFormat.format(new Date()));
					dto.setClockInTime(timeFormat.format(new Date()));

					AcademicTermDTO academicTermDTO = new AcademicTermDTO(
							window.getAcademicTermCombo().getValueAsString());
					dto.setAcademicTermDTO(academicTermDTO);

					SchoolDTO schoolDTO = new SchoolDTO(window.getSchoolCombo().getValueAsString());
					dto.setSchoolDTO(schoolDTO);

					SchoolStaffDTO schoolStaffDTO = new SchoolStaffDTO();
					schoolStaffDTO.setId(window.getSchoolStaffCombo().getValueAsString());
					dto.setSchoolStaffDTO(schoolStaffDTO);

					LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					map.put(MyRequestAction.DATA, dto);
					map.put(MyRequestAction.COMMAND, ClockInOutCommand.SAVE_CLOCK_IN);

					NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

						@Override
						public void onNetworkResult(MyRequestResult result) {
							if (result != null) {
								SystemResponseDTO<ClockInDTO> responseDTO = result.getClockInResponse();
								if (responseDTO.isStatus()) {
									// clearClockInWindowFields(window);
									SC.say("SUCCESS", responseDTO.getMessage());
									getAllStaffClockIn2();
								} else {
									SC.say(responseDTO.getMessage());
								}
							}

						}
					});

				} else {
					SC.say("Please fill all the fields");
				}

			}

		});

	}

	private void getAllStaffClockIn2() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(MyRequestAction.DATA, new FilterDTO());
		if (SessionManager.getInstance().getLoggedInUserGroup().equalsIgnoreCase(SessionManager.ADMIN))
			map.put(MyRequestAction.COMMAND, ClockInOutCommand.FILTER_CLOCKINS);
		else
			map.put(MyRequestAction.COMMAND, ClockInOutCommand.GET_CLOCK_INS_BY_SYSTEM_USER_PROFILE_SCHOOLS);

		clockInResponseList(map);
	}

	// set grid
	private void clockInResponseList(LinkedHashMap<String, Object> map) {
		NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

			@Override
			public void onNetworkResult(MyRequestResult result) {
				if (result != null) {
					SystemResponseDTO<List<ClockInDTO>> responseDTO = result.getClockInResponseList();
					if (responseDTO.isStatus()) {
						if (responseDTO.getData() != null)
							getView().getClockInPane().getClockInListGrid().addRecordsToGrid(responseDTO.getData());
					} else {
						SC.say(responseDTO.getMessage());
					}
				}

			}
		});
	}

	private void clockOut2(MenuButton clockOutButton) {

		clockOutButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (getView().getClockInPane().getClockInListGrid().anySelected()) {
					ListGridRecord record = getView().getClockInPane().getClockInListGrid().getSelectedRecord();
					ClockOutDTO dto = new ClockOutDTO();

					ClockInDTO clockInDTO = new ClockInDTO();
					clockInDTO.setId(record.getAttribute(ClockInListGrid.ID));

					dto.setClockInDTO(clockInDTO);
					dto.setComment(record.getAttribute(ClockInListGrid.COMMENT));
					dto.setCreatedDateTime(dateTimeFormat.format(new Date()));
	
					LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					map.put(MyRequestAction.DATA, dto);
					map.put(MyRequestAction.COMMAND, ClockInOutCommand.SAVE_CLOCK_OUT);

					NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

						@Override
						public void onNetworkResult(MyRequestResult result) {
							if (result != null) {
								SystemResponseDTO<ClockOutDTO> responseDTO = result.getClockOutResponse();
								if (responseDTO.isStatus()) {
									SC.say("SUCCESS", responseDTO.getMessage());
									getAllStaffClockIn2();
								} else {
									SC.say(responseDTO.getMessage());
								}
							}

						}
					});

				} else {
					SC.say("Please Select A record to clockout");
				}
			}
		});

	}

	private void getAllStaffClockOut2() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(MyRequestAction.DATA, new FilterDTO());
		if (SessionManager.getInstance().getLoggedInUserGroup().equalsIgnoreCase(SessionManager.ADMIN))
			map.put(MyRequestAction.COMMAND, ClockInOutCommand.FILTER_CLOCK_OUTS);
		else
			map.put(MyRequestAction.COMMAND, ClockInOutCommand.GET_CLOCK_OUTS_BY_SYSTEM_USER_PROFILE_SCHOOLS);

		clockOutResponseList(map);
	}

	private void clockOutResponseList(LinkedHashMap<String, Object> map) {
		NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

			@Override
			public void onNetworkResult(MyRequestResult result) {
				if (result != null) {
					SystemResponseDTO<List<ClockOutDTO>> responseDTO = result.getClockOutResponseList();
					if (responseDTO.isStatus()) {
						if (responseDTO.getData() != null)
							getView().getClockOutPane().getClockOutListGrid().addRecordsToGrid(responseDTO.getData());
					} else {
						SC.say(responseDTO.getMessage());
					}
				}

			}
		});
	}

}