package com.planetsystems.tela.managementapp.client.presenter.staffdailytask;

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
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.planetsystems.tela.dto.AcademicTermDTO;
import com.planetsystems.tela.dto.SchoolClassDTO;
import com.planetsystems.tela.dto.SchoolStaffDTO;
import com.planetsystems.tela.dto.StaffDailyAttendanceDTO;
import com.planetsystems.tela.dto.StaffDailyAttendanceTaskDTO;
import com.planetsystems.tela.dto.SubjectDTO;
import com.planetsystems.tela.dto.SystemFeedbackDTO;
import com.planetsystems.tela.managementapp.client.gin.SessionManager;
import com.planetsystems.tela.managementapp.client.place.NameTokens;
import com.planetsystems.tela.managementapp.client.presenter.comboutils.ComboUtil;
import com.planetsystems.tela.managementapp.client.presenter.main.MainPresenter;
import com.planetsystems.tela.managementapp.client.presenter.timetable.LessonListGrid;
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
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;

public class StaffDailyTaskPresenter
		extends Presenter<StaffDailyTaskPresenter.MyView, StaffDailyTaskPresenter.MyProxy> {
	interface MyView extends View {
		public StaffDailyAttendancePane getStaffDailyAttendancePane();

		public ControlsPane getControlsPane();

		public TabSet getTabSet();
	}

	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_StaffDailyTask = new Type<RevealContentHandler<?>>();

	@Inject
	private DispatchAsync dispatcher;

	@Inject
	private PlaceManager placeManager;

	DateTimeFormat dateTimeFormat = DateTimeFormat
			.getFormat(DatePattern.DAY_MONTH_YEAR_HOUR_MINUTE_SECONDS.getPattern());
	DateTimeFormat dateFormat = DateTimeFormat.getFormat(DatePattern.DAY_MONTH_YEAR.getPattern());
	DateTimeFormat timeFormat = DateTimeFormat.getFormat(DatePattern.HOUR_MINUTE_SECONDS.getPattern());

	ComboUtil comboUtil;

	@NameToken(NameTokens.StaffDailyTask)
	@ProxyStandard
	interface MyProxy extends ProxyPlace<StaffDailyTaskPresenter> {
	}

	@Inject
	StaffDailyTaskPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy, MainPresenter.SLOT_Main);

		comboUtil = new ComboUtil();

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

				if (selectedTab.equalsIgnoreCase(StaffDailyTaskView.STAFF_DAILY_ATTENDANCES)) {
					// close second tab
					getView().getTabSet().removeTab(1);

					MenuButton newButton = new MenuButton("New");
					MenuButton view = new MenuButton("view");

					List<MenuButton> buttons = new ArrayList<>();
					buttons.add(newButton);
					buttons.add(view);
					getView().getControlsPane().addMenuButtons(buttons);
					showCreateTab(newButton);
					viewAttendanceTaskTab(view);

					comboUtil.loadAcademicYearCombo(getView().getStaffDailyAttendancePane().getAcademicYearCombo(),
							dispatcher, placeManager , null);

					comboUtil.loadDistrictCombo(getView().getStaffDailyAttendancePane().getDistrictCombo(), dispatcher,
							placeManager , null);

					comboUtil.loadSchoolComboByDistrict(getView().getStaffDailyAttendancePane().getDistrictCombo(),
							getView().getStaffDailyAttendancePane().getSchoolCombo(), dispatcher, placeManager , null);

					comboUtil.loadAcademicTermComboByAcademicYear(
							getView().getStaffDailyAttendancePane().getAcademicYearCombo(),
							getView().getStaffDailyAttendancePane().getAcademicTermCombo(), dispatcher, placeManager , null);

					disableEnableStaffDailyAttendancePaneLoadLessonButton();
					getStaffDailyAttendances();

				} else {
					List<MenuButton> buttons = new ArrayList<>();
					getView().getControlsPane().addMenuButtons(buttons);
				}

			}

		});
	}

	private void getStaffDailyAttendances() {
		getView().getStaffDailyAttendancePane().getLoadAttendanceButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				final String academicYearId = getView().getStaffDailyAttendancePane().getAcademicYearCombo().getValueAsString();
				final String academicTermId = getView().getStaffDailyAttendancePane().getAcademicTermCombo().getValueAsString();
				final String districtId = getView().getStaffDailyAttendancePane().getDistrictCombo().getValueAsString();
				final String schoolId = getView().getStaffDailyAttendancePane().getSchoolCombo().getValueAsString();

				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(RequestDelimeters.ACADEMIC_YEAR_ID, academicYearId);
				map.put(RequestDelimeters.ACADEMIC_TERM_ID, academicTermId);
				map.put(RequestDelimeters.DISTRICT_ID, districtId);
				map.put(RequestDelimeters.SCHOOL_ID, schoolId);
				map.put(RequestDelimeters.ATTENDANCE_DATE, dateFormat.format(new Date()));

				map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
				
				
				SC.showPrompt("", "", new SwizimaLoader());
				
						dispatcher.execute(new RequestAction(RequestConstant.GET_STAFF_DAILY_ATTENDANCE_ACADEMIC_YEAR_TERM_DUSTRICT_SCHOOL_DATE, map),
								new AsyncCallback<RequestResult>() {
									public void onFailure(Throwable caught) {
										System.out.println(caught.getMessage());
										SC.warn("ERROR", caught.getMessage());
										GWT.log("ERROR " + caught.getMessage());
										SC.clearPrompt();
									}
				
									public void onSuccess(RequestResult result) {
										SC.clearPrompt();
										SessionManager.getInstance().manageSession(result, placeManager);
				
										if (result != null) {
				
											SystemFeedbackDTO feedbackDTO = result.getSystemFeedbackDTO();
											if (feedbackDTO.isResponse()) {
												getView().getStaffDailyAttendancePane().getStaffDailyAttendanceListGrid()
												.addRecordsToGrid(result.getStaffDailyAttendanceDTOs());
											}
				
										} else {
											SC.warn("ERROR", "Unknow error");
										}
				
									}
								});
			}
		});
		
	}
	
	/////////////////////////// SATFF DAILY ATTENDANCES

	private void disableEnableStaffDailyAttendancePaneLoadLessonButton() {
	
		final IButton button = getView().getStaffDailyAttendancePane().getLoadAttendanceButton();
		final ComboBox termBox = getView().getStaffDailyAttendancePane().getAcademicTermCombo();
		final ComboBox schoolBox = getView().getStaffDailyAttendancePane().getSchoolCombo();
		final TextItem dayItem = getView().getStaffDailyAttendancePane().getDayField();

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

	private void showCreateTab(MenuButton newButton) {
		newButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				CreateStaffDailyTaskPane createStaffDailyTaskPane = new CreateStaffDailyTaskPane();

				Tab tab = new Tab();
				tab.setTitle(StaffDailyTaskView.ADD_STAFF_DAILY_ATTENDANCE_TASK);
				tab.setCanClose(true);
				tab.setPane(createStaffDailyTaskPane);
				getView().getTabSet().addTab(tab);
				getView().getTabSet().selectTab(tab);

				disableEnableCreateStaffDailyTaskPaneLoadLessonButton(createStaffDailyTaskPane);

				comboUtil.loadAcademicYearCombo(createStaffDailyTaskPane.getAcademicYearCombo(), dispatcher,
						placeManager , null);

				comboUtil.loadDistrictCombo(createStaffDailyTaskPane.getDistrictCombo(), dispatcher, placeManager , null);

				comboUtil.loadSchoolComboByDistrict(createStaffDailyTaskPane.getDistrictCombo(),
						createStaffDailyTaskPane.getSchoolCombo(), dispatcher, placeManager , null);

				comboUtil.loadAcademicTermComboByAcademicYear(createStaffDailyTaskPane.getAcademicYearCombo(),
						createStaffDailyTaskPane.getAcademicTermCombo(), dispatcher, placeManager , null);

				comboUtil.loadSchoolStaffComboBySchool(createStaffDailyTaskPane.getSchoolCombo(),
						createStaffDailyTaskPane.getSchoolStaffCombo(), dispatcher, placeManager , null);

				getTimeTableLessonsForStaffAcademicYearTermDistrictSchoolDay(createStaffDailyTaskPane);
				saveStaffDailyAttendanceTask(createStaffDailyTaskPane);

				closeCreateStaffDailyTaskTab(createStaffDailyTaskPane);

			}

		});

	}

	private void closeCreateStaffDailyTaskTab(CreateStaffDailyTaskPane createStaffDailyTaskPane) {
		createStaffDailyTaskPane.getCloseTabButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				getView().getTabSet().removeTab(1);
			}
		});

	}

	///////////////////////////

	private void getTimeTableLessonsForStaffAcademicYearTermDistrictSchoolDay(
			final CreateStaffDailyTaskPane createStaffDailyTaskPane) {
		createStaffDailyTaskPane.getLoadLessonButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				if (checkIfNoFieldCreateStaffDailyTaskPaneFieldIsEmpty(createStaffDailyTaskPane)) {
					String academicYearId = createStaffDailyTaskPane.getAcademicYearCombo().getValueAsString();
					String academicTermId = createStaffDailyTaskPane.getAcademicTermCombo().getValueAsString();
					String districtId = createStaffDailyTaskPane.getDistrictCombo().getValueAsString();
					String schoolId = createStaffDailyTaskPane.getSchoolCombo().getValueAsString();
					String schoolStaffId = createStaffDailyTaskPane.getSchoolStaffCombo().getValueAsString();
					String day = createStaffDailyTaskPane.getDayField().getValueAsString();

					LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					map.put(RequestDelimeters.ACADEMIC_YEAR_ID, academicYearId);
					map.put(RequestDelimeters.ACADEMIC_TERM_ID, academicTermId);
					map.put(RequestDelimeters.DISTRICT_ID, districtId);
					map.put(RequestDelimeters.SCHOOL_ID, schoolId);
					map.put(RequestDelimeters.SCHOOL_STAFF_ID, schoolStaffId);
					map.put(RequestDelimeters.DAY, day);

					map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());

					GWT.log("YEAR TOKEN " + SessionManager.getInstance().getLoginToken());
					GWT.log("YEAR TOKEN " + map.get(RequestConstant.LOGIN_TOKEN));
					SC.showPrompt("", "", new SwizimaLoader());

					dispatcher.execute(new RequestAction(
							RequestConstant.GET_TIME_TABLE_LESSONS_FOR_STAFF_ACADEMIC_YEAR_TERM_DISTRICT_SCHOOL_DAY,
							map), new AsyncCallback<RequestResult>() {
								public void onFailure(Throwable caught) {
									System.out.println(caught.getMessage());
									SC.warn("ERROR", caught.getMessage());
									GWT.log("ERROR " + caught.getMessage());
									SC.clearPrompt();
								}

								public void onSuccess(RequestResult result) {
									SC.clearPrompt();
									SessionManager.getInstance().manageSession(result, placeManager);

									GWT.log("PRESENTER LIST " + result.getTableLessonDTOs());
									if (result != null) {

										createStaffDailyTaskPane.getLessonListGrid()
												.addRecordsToGrid(result.getTableLessonDTOs());

										if (result.getTableLessonDTOs().isEmpty())
											createStaffDailyTaskPane.getSaveButton().disable();
										else
											createStaffDailyTaskPane.getSaveButton().enable();

									} else {
										SC.warn("ERROR", "Service Down");
									}

								}
							});
				} else {
					SC.say("Please Fill all the fields");
				}
			}
		});
	}
	
	////////////////
	private void getTimeTableLessonsForStaffAcademicYearTermDistrictSchoolDay(final ViewStaffDailyAttendanceTaskPane viewStaffDailyAttendanceTaskPane ,
			final String academicYearId ,final String academicTermId ,final String districtId ,final String schoolId ,final String schoolStaffId
			) {

					String day = viewStaffDailyAttendanceTaskPane.getDayField().getValueAsString();

					LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					map.put(RequestDelimeters.ACADEMIC_YEAR_ID, academicYearId);
					map.put(RequestDelimeters.ACADEMIC_TERM_ID, academicTermId);
					map.put(RequestDelimeters.DISTRICT_ID, districtId);
					map.put(RequestDelimeters.SCHOOL_ID, schoolId);
					map.put(RequestDelimeters.SCHOOL_STAFF_ID, schoolStaffId);
					map.put(RequestDelimeters.DAY, day);

					map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());

					GWT.log("YEAR TOKEN " + SessionManager.getInstance().getLoginToken());
					GWT.log("YEAR TOKEN " + map.get(RequestConstant.LOGIN_TOKEN));
					SC.showPrompt("", "", new SwizimaLoader());

					dispatcher.execute(new RequestAction(
							RequestConstant.GET_TIME_TABLE_LESSONS_FOR_STAFF_ACADEMIC_YEAR_TERM_DISTRICT_SCHOOL_DAY,
							map), new AsyncCallback<RequestResult>() {
								public void onFailure(Throwable caught) {
									System.out.println(caught.getMessage());
									SC.warn("ERROR", caught.getMessage());
									GWT.log("ERROR " + caught.getMessage());
									SC.clearPrompt();
								}

								public void onSuccess(RequestResult result) {
									SC.clearPrompt();
									SessionManager.getInstance().manageSession(result, placeManager);

									GWT.log("PRESENTER LIST " + result.getTableLessonDTOs());
									if (result != null) {

										viewStaffDailyAttendanceTaskPane.getLessonListGrid()
												.addRecordsToGrid(result.getTableLessonDTOs());

									} else {
										SC.warn("ERROR", "Service Down");
									}

								}
							});

	}

	private boolean checkIfNoFieldCreateStaffDailyTaskPaneFieldIsEmpty(
			final CreateStaffDailyTaskPane createStaffDailyTaskPane) {
		boolean flag = true;
		if (createStaffDailyTaskPane.getDistrictCombo().getValueAsString() == null)
			flag = false;

		if (createStaffDailyTaskPane.getSchoolCombo().getValueAsString() == null)
			flag = false;

		if (createStaffDailyTaskPane.getAcademicYearCombo().getValueAsString() == null)
			flag = false;

		if (createStaffDailyTaskPane.getAcademicTermCombo().getValueAsString() == null)
			flag = false;

		if (createStaffDailyTaskPane.getSchoolStaffCombo().getValueAsString() == null)
			flag = false;

		if (createStaffDailyTaskPane.getDayField().getValueAsString() == null)
			flag = false;

		return flag;
	}


	private void disableEnableCreateStaffDailyTaskPaneLoadLessonButton(
			CreateStaffDailyTaskPane createStaffDailyTaskPane) {
		;
		final IButton button = createStaffDailyTaskPane.getLoadLessonButton();
		final ComboBox termBox = createStaffDailyTaskPane.getAcademicTermCombo();
		final ComboBox schoolBox = createStaffDailyTaskPane.getSchoolCombo();
		final ComboBox staffBox = createStaffDailyTaskPane.getSchoolStaffCombo();
		final TextItem dayItem = createStaffDailyTaskPane.getDayField();

		termBox.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {

				if (termBox.getValueAsString() != null && schoolBox.getValueAsString() != null
						&& staffBox.getValueAsString() != null && dayItem.getValueAsString() != null) {
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
						&& staffBox.getValueAsString() != null && dayItem.getValueAsString() != null) {
					button.setDisabled(false);
				} else {
					button.setDisabled(true);
				}
			}
		});

		staffBox.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {

				if (termBox.getValueAsString() != null && schoolBox.getValueAsString() != null
						&& staffBox.getValueAsString() != null && dayItem.getValueAsString() != null) {
					button.setDisabled(false);
				} else {
					button.setDisabled(true);
				}
			}
		});

	}

	private void saveStaffDailyAttendanceTask(final CreateStaffDailyTaskPane createStaffDailyTaskPane) {
		createStaffDailyTaskPane.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				if (createStaffDailyTaskPane.getLessonListGrid().anySelected()) {
					final ComboBox termBox = createStaffDailyTaskPane.getAcademicTermCombo();
					final ComboBox schoolBox = createStaffDailyTaskPane.getSchoolCombo();
					final ComboBox staffBox = createStaffDailyTaskPane.getSchoolStaffCombo();
					final TextItem dayItem = createStaffDailyTaskPane.getDayField();

					StaffDailyAttendanceDTO staffDailyAttendanceDTO = new StaffDailyAttendanceDTO();
					staffDailyAttendanceDTO.setAcademicTermDTO(new AcademicTermDTO(termBox.getValueAsString()));

					staffDailyAttendanceDTO.setCreatedDateTime(dateTimeFormat.format(new Date()));
					
					staffDailyAttendanceDTO.setDailyAttendanceDate(dateFormat.format(new Date()));

					staffDailyAttendanceDTO.setSchoolStaffDTO(new SchoolStaffDTO(staffBox.getValueAsString()));

					ListGridRecord[] records = createStaffDailyTaskPane.getLessonListGrid().getSelectedRecords();
					List<StaffDailyAttendanceTaskDTO> staffDailyAttendanceTaskDTOS = new ArrayList<StaffDailyAttendanceTaskDTO>();

					for (int i = 0; i < records.length; i++) {
						ListGridRecord record = records[i];

						StaffDailyAttendanceTaskDTO taskDTO = new StaffDailyAttendanceTaskDTO();
						taskDTO.setAttendanceStatus("present");
						taskDTO.setCreatedDateTime(dateTimeFormat.format(new Date()));
						taskDTO.setUpdatedDateTime(dateTimeFormat.format(new Date()));
						taskDTO.setDay(dayItem.getValueAsString());
						taskDTO.setEndTime(record.getAttributeAsString(LessonListGrid.END_TIME));
						taskDTO.setStartTime(record.getAttributeAsString(LessonListGrid.START_TIME));
						taskDTO.setSchoolClassDTO(
								new SchoolClassDTO(record.getAttributeAsString(LessonListGrid.CLASS_ID)));
						taskDTO.setSubjectDTO(new SubjectDTO(record.getAttributeAsString(LessonListGrid.SUBJECT_ID)));

						staffDailyAttendanceTaskDTOS.add(taskDTO);

					}
					staffDailyAttendanceDTO.setStaffDailyAttendanceTaskDTOS(staffDailyAttendanceTaskDTOS);

					LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					map.put(RequestConstant.SAVE_STAFF_DAILY_ATTENDANCE_TASKS, staffDailyAttendanceDTO);
					map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());

					SC.showPrompt("", "", new SwizimaLoader());

					dispatcher.execute(new RequestAction(RequestConstant.SAVE_STAFF_DAILY_ATTENDANCE_TASKS, map),
							new AsyncCallback<RequestResult>() {
								public void onFailure(Throwable caught) {
									System.out.println(caught.getMessage());
									SC.warn("ERROR", caught.getMessage());
									GWT.log("ERROR " + caught.getMessage());
									SC.clearPrompt();
								}

								public void onSuccess(RequestResult result) {
									SC.clearPrompt();
									SessionManager.getInstance().manageSession(result, placeManager);

									if (result != null) {

										SystemFeedbackDTO feedbackDTO = result.getSystemFeedbackDTO();
										if (feedbackDTO.isResponse()) {
											SC.say(feedbackDTO.getMessage());
										} else {
											SC.say(feedbackDTO.getMessage());
										}

									} else {
										SC.warn("ERROR", "Unknow error");
									}

								}
							});

				} else {
					SC.say("Please select lessons");
				}

			}
		});

	}

	/////////////////////////// VIEW TAB

	private void viewAttendanceTaskTab(MenuButton view) {
		view.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (getView().getStaffDailyAttendancePane().getStaffDailyAttendanceListGrid().anySelected()) {
					ViewStaffDailyAttendanceTaskPane viewStaffDailyAttendanceTaskPane = new ViewStaffDailyAttendanceTaskPane();
					
					ListGridRecord record = getView().getStaffDailyAttendancePane().getStaffDailyAttendanceListGrid().getSelectedRecord();
					String academicYear = record.getAttribute(StaffDailyAttendanceListGrid.ACADEMIC_YEAR);
					String academicYearId = record.getAttribute(StaffDailyAttendanceListGrid.ACADEMIC_YEAR_ID);
					
					String academicTerm = record.getAttribute(StaffDailyAttendanceListGrid.ACADEMIC_TERM);
					String academicTermId = record.getAttribute(StaffDailyAttendanceListGrid.ACADEMIC_TERM_ID);
					
					String schoolStaff = record.getAttribute(StaffDailyAttendanceListGrid.SCHOOL_STAFF);
					String schoolStaffId = record.getAttribute(StaffDailyAttendanceListGrid.SCHOOL_STAFF_ID);
					
					String district = getView().getStaffDailyAttendancePane().getDistrictCombo().getDisplayValue();
					String districtId = getView().getStaffDailyAttendancePane().getDistrictCombo().getValueAsString();
					
					String school = getView().getStaffDailyAttendancePane().getSchoolCombo().getDisplayValue();
					String schoolId = getView().getStaffDailyAttendancePane().getSchoolCombo().getValueAsString();
					
					
					
					viewStaffDailyAttendanceTaskPane.getAcademicYearField().setValue(academicYear);					
					viewStaffDailyAttendanceTaskPane.getAcademicTermField().setValue(academicTerm);
					viewStaffDailyAttendanceTaskPane.getDistrictField().setValue(district);
					viewStaffDailyAttendanceTaskPane.getSchoolField().setValue(school);
					viewStaffDailyAttendanceTaskPane.getSchoolStaffField().setValue(schoolStaff);
					
					Tab tab = new Tab();
					tab.setTitle(StaffDailyTaskView.VIEW_STAFF_DAILY_ATTENDANCE_TASK);
					tab.setCanClose(true);
					tab.setPane(viewStaffDailyAttendanceTaskPane);
					getView().getTabSet().addTab(tab);
					getView().getTabSet().selectTab(tab);

					getTimeTableLessonsForStaffAcademicYearTermDistrictSchoolDay(viewStaffDailyAttendanceTaskPane , academicYearId , academicTermId , districtId , schoolId , schoolStaffId);
				
				closeViewStaffDailyAttendanceTaskTab(viewStaffDailyAttendanceTaskPane);
				}else {
					SC.say("Please select attendance");
				}
			
			}

		});
	}

	private void closeViewStaffDailyAttendanceTaskTab(
			ViewStaffDailyAttendanceTaskPane viewStaffDailyAttendanceTaskPane) {
		viewStaffDailyAttendanceTaskPane.getCloseTabButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				getView().getTabSet().removeTab(1);
			}
		});

		
	}

}