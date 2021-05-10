package com.planetsystems.tela.managementapp.client.presenter.timeattendancesupervision;

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
import com.planetsystems.tela.dto.SchoolDTO;
import com.planetsystems.tela.dto.SchoolStaffDTO;
import com.planetsystems.tela.dto.SystemFeedbackDTO;
import com.planetsystems.tela.dto.TimeAttendanceSupervisionDTO;
import com.planetsystems.tela.managementapp.client.gin.SessionManager;
import com.planetsystems.tela.managementapp.client.place.NameTokens;
import com.planetsystems.tela.managementapp.client.presenter.comboutils.ComboUtil;
import com.planetsystems.tela.managementapp.client.presenter.main.MainPresenter;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkDataUtil;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkResult;
import com.planetsystems.tela.managementapp.client.presenter.staffattendance.clockin.ClockInListGrid;
import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.planetsystems.tela.managementapp.client.widget.MenuButton;
import com.planetsystems.tela.managementapp.client.widget.SwizimaLoader;
import com.planetsystems.tela.managementapp.shared.DatePattern;
import com.planetsystems.tela.managementapp.shared.RequestAction;
import com.planetsystems.tela.managementapp.shared.RequestConstant;
import com.planetsystems.tela.managementapp.shared.RequestDelimeters;
import com.planetsystems.tela.managementapp.shared.RequestResult;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;

public class TimeAttendanceSupervisionPresenter
		extends Presenter<TimeAttendanceSupervisionPresenter.MyView, TimeAttendanceSupervisionPresenter.MyProxy> {
	interface MyView extends View {
		ControlsPane getControlsPane();

		ClockInPane getClockInPane();

		ClockOutPane getClockOutPane();

		TabSet getTabSet();

		AbsentPane getAbsentPane();
	}

	@SuppressWarnings("deprecation")
	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_TimeAttendanceSupervision = new Type<RevealContentHandler<?>>();

	@Inject
	private DispatchAsync dispatcher;

	DateTimeFormat dateTimeFormat = DateTimeFormat
			.getFormat(DatePattern.DAY_MONTH_YEAR_HOUR_MINUTE_SECONDS.getPattern());

	DateTimeFormat dateFormat = DateTimeFormat.getFormat(DatePattern.DAY_MONTH_YEAR.getPattern());
	DateTimeFormat timeFormat = DateTimeFormat.getFormat(DatePattern.HOUR_MINUTE_SECONDS.getPattern());

	@Inject
	PlaceManager placeManager;

	@NameToken(NameTokens.TimeAttendanceSupervision)
	@ProxyCodeSplit
	interface MyProxy extends ProxyPlace<TimeAttendanceSupervisionPresenter> {
	}

	@Inject
	TimeAttendanceSupervisionPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
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

				if (selectedTab.equalsIgnoreCase(TimeAttendanceSupervisionView.CLOCKED_IN)) {
					MenuButton newButton = new MenuButton("New");
					MenuButton edit = new MenuButton("Edit");
					MenuButton delete = new MenuButton("Delete");

					List<MenuButton> buttons = new ArrayList<>();
					buttons.add(newButton);
					buttons.add(edit);
					buttons.add(delete);

					getView().getControlsPane().addMenuButtons(buttons);
					loadAcademicYearCombo(getView().getClockInPane(), null);
					loadAcademicTermCombo(getView().getClockInPane(), null);
					loadDistrictCombo(getView().getClockInPane(), null);
					loadSchoolCombo(getView().getClockInPane(), null);
					disableEnableLoadButton(getView().getClockInPane());
					superviseStaff(getView().getClockInPane());
					getAllSchoolStaffClockInByAcademicTermSchoolDate(getView().getClockInPane());

				} else if (selectedTab.equalsIgnoreCase(TimeAttendanceSupervisionView.CLOCKED_OUT)) {
					MenuButton newButton = new MenuButton("New");
					MenuButton edit = new MenuButton("Edit");
					MenuButton delete = new MenuButton("Delete");
					MenuButton filter = new MenuButton("View");

					List<MenuButton> buttons = new ArrayList<>();
					buttons.add(newButton);
					buttons.add(edit);
					buttons.add(filter);

					getView().getControlsPane().addMenuButtons(buttons);
					loadAcademicYearCombo(getView().getClockOutPane(), null);
					loadAcademicTermCombo(getView().getClockOutPane(), null);
					loadDistrictCombo(getView().getClockOutPane(), null);
					loadSchoolCombo(getView().getClockOutPane(), null);
					disableEnableLoadButton(getView().getClockOutPane());
					getAllSchoolStaffClockOutByAcademicTermSchoolDate(getView().getClockOutPane());
					superviseStaff(getView().getClockOutPane());

				} else if (selectedTab.equalsIgnoreCase(TimeAttendanceSupervisionView.ABSENT)) {
					MenuButton newButton = new MenuButton("New");
					MenuButton edit = new MenuButton("Edit");
					MenuButton delete = new MenuButton("Delete");
					MenuButton filter = new MenuButton("View");

					List<MenuButton> buttons = new ArrayList<>();
					buttons.add(newButton);
					buttons.add(edit);
					buttons.add(filter);

					getView().getControlsPane().addMenuButtons(buttons);
					getView().getControlsPane().addMenuButtons(buttons);
					loadAcademicYearCombo(getView().getAbsentPane(), null);
					loadAcademicTermCombo(getView().getAbsentPane(), null);
					loadDistrictCombo(getView().getAbsentPane(), null);
					loadSchoolCombo(getView().getAbsentPane(), null);
					disableEnableLoadButton(getView().getAbsentPane());
					getAllAbsentSchoolStaffByAcademicTermSchoolDate(getView().getAbsentPane());
					superviseStaff(getView().getAbsentPane());
				} else {
					List<MenuButton> buttons = new ArrayList<>();
					getView().getControlsPane().addMenuButtons(buttons);
				}

			}
		});
	}

	//////////////////////// clockin combos

	private void loadSchoolCombo(final ClockInPane clockInPane, final String defaultValue) {
		clockInPane.getFilterYearTermDistrictSchoolDate().getDistrictCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				ComboUtil.loadSchoolComboByDistrict(
						clockInPane.getFilterYearTermDistrictSchoolDate().getDistrictCombo(),
						clockInPane.getFilterYearTermDistrictSchoolDate().getSchoolCombo(), dispatcher, placeManager,
						defaultValue);
			}
		});
	}

	private void loadDistrictCombo(ClockInPane clockInPane, String defaultValue) {
		ComboUtil.loadDistrictCombo(clockInPane.getFilterYearTermDistrictSchoolDate().getDistrictCombo(), dispatcher,
				placeManager, defaultValue);
	}

	private void loadAcademicTermCombo(final ClockInPane clockInPane, final String defaultValue) {
		clockInPane.getFilterYearTermDistrictSchoolDate().getAcademicYearCombo()
				.addChangedHandler(new ChangedHandler() {

					@Override
					public void onChanged(ChangedEvent event) {
						ComboUtil.loadAcademicTermComboByAcademicYear(
								clockInPane.getFilterYearTermDistrictSchoolDate().getAcademicYearCombo(),
								clockInPane.getFilterYearTermDistrictSchoolDate().getAcademicTermCombo(), dispatcher,
								placeManager, defaultValue);
					}
				});
	}

	private void loadAcademicYearCombo(ClockInPane clockInPane, String defaultValue) {
		ComboUtil.loadAcademicYearCombo(clockInPane.getFilterYearTermDistrictSchoolDate().getAcademicYearCombo(),
				dispatcher, placeManager, defaultValue);

	}

	/// clock out combos
	private void loadSchoolCombo(final ClockOutPane clockOutPane, final String defaultValue) {
		clockOutPane.getFilterYearTermDistrictSchoolDate().getDistrictCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				ComboUtil.loadSchoolComboByDistrict(
						clockOutPane.getFilterYearTermDistrictSchoolDate().getDistrictCombo(),
						clockOutPane.getFilterYearTermDistrictSchoolDate().getSchoolCombo(), dispatcher, placeManager,
						defaultValue);
			}
		});
	}

	private void loadDistrictCombo(ClockOutPane clockOutPane, String defaultValue) {
		ComboUtil.loadDistrictCombo(clockOutPane.getFilterYearTermDistrictSchoolDate().getDistrictCombo(), dispatcher,
				placeManager, defaultValue);

	}

	private void loadAcademicTermCombo(final ClockOutPane clockOutPane, final String defaultValue) {
		clockOutPane.getFilterYearTermDistrictSchoolDate().getAcademicYearCombo()
				.addChangedHandler(new ChangedHandler() {

					@Override
					public void onChanged(ChangedEvent event) {
						ComboUtil.loadAcademicTermComboByAcademicYear(
								clockOutPane.getFilterYearTermDistrictSchoolDate().getAcademicYearCombo(),
								clockOutPane.getFilterYearTermDistrictSchoolDate().getAcademicTermCombo(), dispatcher,
								placeManager, defaultValue);
					}
				});
	}

	private void loadAcademicYearCombo(ClockOutPane clockOutPane, String defaultValue) {
		ComboUtil.loadAcademicYearCombo(clockOutPane.getFilterYearTermDistrictSchoolDate().getAcademicYearCombo(),
				dispatcher, placeManager, defaultValue);
	}

	private void getAllSchoolStaffClockInByAcademicTermSchoolDate(final ClockInPane clockInPane) {
		clockInPane.getLoadButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(RequestDelimeters.ACADEMIC_TERM_ID,
						clockInPane.getFilterYearTermDistrictSchoolDate().getAcademicTermCombo().getValueAsString());
				map.put(RequestDelimeters.SCHOOL_ID,
						clockInPane.getFilterYearTermDistrictSchoolDate().getSchoolCombo().getValueAsString());
				map.put(RequestDelimeters.CLOCKIN_DATE, dateFormat.format(new Date()));
				map.put(NetworkDataUtil.ACTION, RequestConstant.GET_CLOCK_IN_By_ACADEMIC_TERM_SCHOOL_DATE);

				NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

					@Override
					public void onNetworkResult(RequestResult result) {
						if (result.getClockInDTOs().isEmpty()) {
							clockInPane.getSuperviseButton().disable();
						} else {
							clockInPane.getSuperviseButton().enable();
						}
						clockInPane.getClockInListGrid().addRecordsToGrid(result.getClockInDTOs());

					}
				});

			}
		});

	}

	///////// load adbsent combos
	private void loadSchoolCombo(final AbsentPane absentPane, final String defaultValue) {
		absentPane.getFilterYearTermDistrictSchoolDate().getDistrictCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				ComboUtil.loadSchoolComboByDistrict(absentPane.getFilterYearTermDistrictSchoolDate().getDistrictCombo(),
						absentPane.getFilterYearTermDistrictSchoolDate().getSchoolCombo(), dispatcher, placeManager,
						defaultValue);
			}
		});
	}

	private void loadDistrictCombo(AbsentPane absentPane, String defaultValue) {
		ComboUtil.loadDistrictCombo(absentPane.getFilterYearTermDistrictSchoolDate().getDistrictCombo(), dispatcher,
				placeManager, defaultValue);
	}

	private void loadAcademicTermCombo(final AbsentPane absentPane, final String defaultValue) {
		absentPane.getFilterYearTermDistrictSchoolDate().getAcademicYearCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				ComboUtil.loadAcademicTermComboByAcademicYear(
						absentPane.getFilterYearTermDistrictSchoolDate().getAcademicYearCombo(),
						absentPane.getFilterYearTermDistrictSchoolDate().getAcademicTermCombo(), dispatcher,
						placeManager, defaultValue);
			}
		});
	}

	private void loadAcademicYearCombo(AbsentPane absentPane, String defaultValue) {
		ComboUtil.loadAcademicYearCombo(absentPane.getFilterYearTermDistrictSchoolDate().getAcademicYearCombo(),
				dispatcher, placeManager, defaultValue);
	}

	private void getAllSchoolStaffClockOutByAcademicTermSchoolDate(final ClockOutPane clockOutPane) {
		clockOutPane.getLoadButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(RequestDelimeters.ACADEMIC_TERM_ID,
						clockOutPane.getFilterYearTermDistrictSchoolDate().getAcademicTermCombo().getValueAsString());
				map.put(RequestDelimeters.SCHOOL_ID,
						clockOutPane.getFilterYearTermDistrictSchoolDate().getSchoolCombo().getValueAsString());
				map.put(RequestDelimeters.CLOCK_OUT_DATE, dateFormat.format(new Date()));
				map.put(NetworkDataUtil.ACTION, RequestConstant.GET_CLOCK_OUT_BY_TERM_SCHOOL_DATE);

				NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

					@Override
					public void onNetworkResult(RequestResult result) {
						if (result.getClockOutDTOs().isEmpty()) {
							clockOutPane.getSuperviseButton().disable();
						} else {
							clockOutPane.getSuperviseButton().enable();
						}

						clockOutPane.getClockOutListGrid().addRecordsToGrid(result.getClockOutDTOs());
					}
				});

			}
		});

	}

	private void getAllAbsentSchoolStaffByAcademicTermSchoolDate(final AbsentPane absentPane) {
		absentPane.getLoadButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(RequestDelimeters.ACADEMIC_TERM_ID,
						absentPane.getFilterYearTermDistrictSchoolDate().getAcademicTermCombo().getValueAsString());
				map.put(RequestDelimeters.SCHOOL_ID,
						absentPane.getFilterYearTermDistrictSchoolDate().getSchoolCombo().getValueAsString());
				map.put(RequestDelimeters.ABSENT_DATE, dateFormat.format(new Date()));
				map.put(NetworkDataUtil.ACTION, RequestConstant.GET_ABSENT_SCHOOL_STAFF_BY_TERM_SCHOOL_DATE);

				NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

					@Override
					public void onNetworkResult(RequestResult result) {
						if (result.getSchoolStaffDTOs().isEmpty()) {
							absentPane.getSuperviseButton().disable();
						} else {
							absentPane.getSuperviseButton().enable();
						}

						absentPane.getAbsentListGrid().addRecordsToGrid(result.getSchoolStaffDTOs());
					}
				});
			}
		});

	}

	private void disableEnableLoadButton(final ClockInPane clockInPane) {

		final IButton button = clockInPane.getLoadButton();
		final ComboBox termBox = clockInPane.getFilterYearTermDistrictSchoolDate().getAcademicTermCombo();
		final ComboBox schoolBox = clockInPane.getFilterYearTermDistrictSchoolDate().getSchoolCombo();
		final TextItem dayItem = clockInPane.getFilterYearTermDistrictSchoolDate().getDayField();

		termBox.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {

				if (termBox.getValueAsString() != null && schoolBox.getValueAsString() != null
						&& dayItem.getValueAsString() != null) {
					button.setDisabled(false);
				} else {
					button.setDisabled(true);
				}
			}
		});

		schoolBox.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {

				if (termBox.getValueAsString() != null && schoolBox.getValueAsString() != null
						&& dayItem.getValueAsString() != null) {
					button.setDisabled(false);
				} else {
					button.setDisabled(true);
				}
			}
		});
	}

	private void disableEnableLoadButton(final ClockOutPane clockOutPane) {

		final IButton button = clockOutPane.getLoadButton();
		final ComboBox termBox = clockOutPane.getFilterYearTermDistrictSchoolDate().getAcademicTermCombo();
		final ComboBox schoolBox = clockOutPane.getFilterYearTermDistrictSchoolDate().getSchoolCombo();
		final TextItem dayItem = clockOutPane.getFilterYearTermDistrictSchoolDate().getDayField();

		termBox.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {

				if (termBox.getValueAsString() != null && schoolBox.getValueAsString() != null
						&& dayItem.getValueAsString() != null) {
					button.setDisabled(false);
				} else {
					button.setDisabled(true);
				}
			}
		});

		schoolBox.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {

				if (termBox.getValueAsString() != null && schoolBox.getValueAsString() != null
						&& dayItem.getValueAsString() != null) {
					button.setDisabled(false);
				} else {
					button.setDisabled(true);
				}
			}
		});
	}

	private void disableEnableLoadButton(final AbsentPane absentPane) {

		final IButton button = absentPane.getLoadButton();
		final ComboBox termBox = absentPane.getFilterYearTermDistrictSchoolDate().getAcademicTermCombo();
		final ComboBox schoolBox = absentPane.getFilterYearTermDistrictSchoolDate().getSchoolCombo();
		final TextItem dayItem = absentPane.getFilterYearTermDistrictSchoolDate().getDayField();

		termBox.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {

				if (termBox.getValueAsString() != null && schoolBox.getValueAsString() != null
						&& dayItem.getValueAsString() != null) {
					button.setDisabled(false);
				} else {
					button.setDisabled(true);
				}
			}
		});

		schoolBox.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {

				if (termBox.getValueAsString() != null && schoolBox.getValueAsString() != null
						&& dayItem.getValueAsString() != null) {
					button.setDisabled(false);
				} else {
					button.setDisabled(true);
				}
			}
		});
	}

	////////////////// SUPER VISING
	private void superviseStaff(final ClockInPane clockInPane) {

		clockInPane.getSuperviseButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (clockInPane.getClockInListGrid().anySelected()) {
					ListGridRecord record = clockInPane.getClockInListGrid().getSelectedRecord();
					TimeAttendanceSupervisionDTO timeAttendanceSupervisionDTO = new TimeAttendanceSupervisionDTO();
					timeAttendanceSupervisionDTO.setAttendanceStatus("present");
					timeAttendanceSupervisionDTO.setComment("NO COMMENT");
//					timeAttendanceSupervisionDTO.setId(id);
					timeAttendanceSupervisionDTO.setCreatedDateTime(dateTimeFormat.format(new Date()));
					timeAttendanceSupervisionDTO.setSupervisionDate(dateFormat.format(new Date()));
					timeAttendanceSupervisionDTO.setSupervisionTime(timeFormat.format(new Date()));

					SchoolStaffDTO schoolStaffDTO = new SchoolStaffDTO(
							record.getAttribute(ClockInListGrid.SCHOOL_STAFF_ID));
					timeAttendanceSupervisionDTO.setSchoolStaffDTO(schoolStaffDTO);

					SchoolDTO schoolDTO = new SchoolDTO(record.getAttribute(ClockInListGrid.SCHOOL_ID));
					timeAttendanceSupervisionDTO.setSchoolDTO(schoolDTO);

					// timeAttendanceSupervisionDTO.setSupervisor(supervisor);
//					supervisionDTO.setSupervisor(supervisor); extracted from server token

					LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					map.put(RequestConstant.SAVE_TIME_ATTENDANCE_SUPERVISION, timeAttendanceSupervisionDTO);
					map.put(NetworkDataUtil.ACTION, RequestConstant.SAVE_TIME_ATTENDANCE_SUPERVISION);

					NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

						@Override
						public void onNetworkResult(RequestResult result) {
							SystemFeedbackDTO feedbackDTO = result.getSystemFeedbackDTO();
							if (feedbackDTO.isResponse()) {
								SC.say(feedbackDTO.getMessage());
							} else {
								SC.warn("Not Successful \n ERROR:", result.getSystemFeedbackDTO().getMessage());
							}
						}
					});

				} else {
					SC.say("Please Select Staff to superise");
				}
			}
		});

	}

	private void superviseStaff(final ClockOutPane clockOutPane) {

		clockOutPane.getSuperviseButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (clockOutPane.getClockOutListGrid().anySelected()) {
					ListGridRecord record = clockOutPane.getClockOutListGrid().getSelectedRecord();
					TimeAttendanceSupervisionDTO timeAttendanceSupervisionDTO = new TimeAttendanceSupervisionDTO();
					timeAttendanceSupervisionDTO.setAttendanceStatus("present");
					timeAttendanceSupervisionDTO.setComment("NO COMMENT");
//					timeAttendanceSupervisionDTO.setId(id);
					timeAttendanceSupervisionDTO.setCreatedDateTime(dateTimeFormat.format(new Date()));
					timeAttendanceSupervisionDTO.setSupervisionDate(dateFormat.format(new Date()));
					timeAttendanceSupervisionDTO.setSupervisionTime(timeFormat.format(new Date()));

					SchoolStaffDTO schoolStaffDTO = new SchoolStaffDTO(
							record.getAttribute(ClockInListGrid.SCHOOL_STAFF_ID));
					timeAttendanceSupervisionDTO.setSchoolStaffDTO(schoolStaffDTO);

					SchoolDTO schoolDTO = new SchoolDTO(record.getAttribute(ClockInListGrid.SCHOOL_ID));
					timeAttendanceSupervisionDTO.setSchoolDTO(schoolDTO);

					// timeAttendanceSupervisionDTO.setSupervisor(supervisor);
//					supervisionDTO.setSupervisor(supervisor); extracted from server token

					LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					map.put(RequestConstant.SAVE_TIME_ATTENDANCE_SUPERVISION, timeAttendanceSupervisionDTO);
					map.put(NetworkDataUtil.ACTION, RequestConstant.SAVE_TIME_ATTENDANCE_SUPERVISION);
					NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

						@Override
						public void onNetworkResult(RequestResult result) {
							SystemFeedbackDTO feedbackDTO = result.getSystemFeedbackDTO();
							if (feedbackDTO.isResponse()) {
								SC.say(feedbackDTO.getMessage());
							} else {
								SC.warn("Not Successful \n ERROR:", result.getSystemFeedbackDTO().getMessage());
							}
						}
					});
				} else {
					SC.say("Please Select Staff to superise");
				}
			}
		});

	}

	private void superviseStaff(final AbsentPane absentPane) {

		absentPane.getSuperviseButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (absentPane.getAbsentListGrid().anySelected()) {
					ListGridRecord record = absentPane.getAbsentListGrid().getSelectedRecord();
					TimeAttendanceSupervisionDTO timeAttendanceSupervisionDTO = new TimeAttendanceSupervisionDTO();
					timeAttendanceSupervisionDTO.setAttendanceStatus("present");
					timeAttendanceSupervisionDTO.setComment("NO COMMENT");
//					timeAttendanceSupervisionDTO.setId(id);
					timeAttendanceSupervisionDTO.setCreatedDateTime(dateTimeFormat.format(new Date()));
					timeAttendanceSupervisionDTO.setSupervisionDate(dateFormat.format(new Date()));
					timeAttendanceSupervisionDTO.setSupervisionTime(timeFormat.format(new Date()));

					SchoolStaffDTO schoolStaffDTO = new SchoolStaffDTO(
							record.getAttribute(AbsentListGrid.SCHOOL_STAFF_ID));
					timeAttendanceSupervisionDTO.setSchoolStaffDTO(schoolStaffDTO);

					SchoolDTO schoolDTO = new SchoolDTO(record.getAttribute(AbsentListGrid.SCHOOL_ID));
					timeAttendanceSupervisionDTO.setSchoolDTO(schoolDTO);

					// timeAttendanceSupervisionDTO.setSupervisor(supervisor);
//					supervisionDTO.setSupervisor(supervisor); extracted from server token

					LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					map.put(RequestConstant.SAVE_TIME_ATTENDANCE_SUPERVISION, timeAttendanceSupervisionDTO);
					SC.showPrompt("", "", new SwizimaLoader());

					dispatcher.execute(new RequestAction(RequestConstant.SAVE_TIME_ATTENDANCE_SUPERVISION, map),
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
										if (feedbackDTO != null) {
											if (feedbackDTO.isResponse()) {
												SC.say(feedbackDTO.getMessage());
											} else {
												SC.warn("Not Successful \n ERROR:",
														result.getSystemFeedbackDTO().getMessage());
											}
										}
									} else {
										SC.warn("ERROR", "Unknow error");
									}

								}

							});
				} else {
					SC.say("Please Select Staff to superise");
				}
			}
		});

	}

}