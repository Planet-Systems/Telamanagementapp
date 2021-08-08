package com.planetsystems.tela.managementapp.client.presenter.staffdailytimetable;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.i18n.client.DateTimeFormat;
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
import com.planetsystems.tela.dto.StaffDailyTimeTableDTO;
import com.planetsystems.tela.dto.StaffDailyTimeTableLessonDTO;
import com.planetsystems.tela.dto.SubjectDTO;
import com.planetsystems.tela.managementapp.client.gin.SessionManager;
import com.planetsystems.tela.managementapp.client.place.NameTokens;
import com.planetsystems.tela.managementapp.client.presenter.comboutils.ComboUtil;
import com.planetsystems.tela.managementapp.client.presenter.main.MainPresenter;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkDataUtil;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkResult;
import com.planetsystems.tela.managementapp.client.presenter.timetable.LessonListGrid;
import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.planetsystems.tela.managementapp.client.widget.MenuButton;
import com.planetsystems.tela.managementapp.shared.DatePattern;
import com.planetsystems.tela.managementapp.shared.RequestConstant;
import com.planetsystems.tela.managementapp.shared.RequestDelimeters;
import com.planetsystems.tela.managementapp.shared.RequestResult;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;

public class StaffDailyTimtablePresenter
		extends Presenter<StaffDailyTimtablePresenter.MyView, StaffDailyTimtablePresenter.MyProxy> {
	interface MyView extends View {
		public StaffDailyTimetablePane getStaffDailyTimetablePane();

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
	DateTimeFormat dayFormat = DateTimeFormat.getFormat(DatePattern.DAY.getPattern());
	DateTimeFormat timeFormat = DateTimeFormat.getFormat(DatePattern.HOUR_MINUTE_SECONDS.getPattern());

	@NameToken(NameTokens.StaffDailyTask)
	@ProxyStandard
	interface MyProxy extends ProxyPlace<StaffDailyTimtablePresenter> {
	}

	@Inject
	StaffDailyTimtablePresenter(EventBus eventBus, MyView view, MyProxy proxy) {
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

				if (selectedTab.equalsIgnoreCase(StaffDailyTimetableView.STAFF_DAILY_TIMETABLE)) {
					// close second tab
					getView().getTabSet().removeTab(1);

					MenuButton newButton = new MenuButton("New");
					MenuButton view = new MenuButton("view");

					List<MenuButton> buttons = new ArrayList<>();
					buttons.add(newButton);
					buttons.add(view);
					getView().getControlsPane().addMenuButtons("Task Attendance Supervision",buttons);
					showCreateTab(newButton);
					viewStaffDailyTimetableLessonTab(view);
					
					final String defaultValue = null;

					ComboUtil.loadAcademicYearCombo(getView().getStaffDailyTimetablePane().getAcademicYearCombo(),
							dispatcher, placeManager, defaultValue);
					
					getView().getStaffDailyTimetablePane().getAcademicYearCombo()
					.addChangedHandler(new ChangedHandler() {

						@Override
						public void onChanged(ChangedEvent event) {
							ComboUtil.loadAcademicTermComboByAcademicYear(
									getView().getStaffDailyTimetablePane().getAcademicYearCombo(),
									getView().getStaffDailyTimetablePane().getAcademicTermCombo(), dispatcher,
									placeManager, defaultValue);
						}
					});
					
					
					

					ComboUtil.loadRegionCombo(getView().getStaffDailyTimetablePane().getRegionCombo() , dispatcher, placeManager, defaultValue);
					getView().getStaffDailyTimetablePane().getRegionCombo().addChangedHandler(new ChangedHandler() {
						
						@Override
						public void onChanged(ChangedEvent event) {
							ComboUtil.loadDistrictComboByRegion(getView().getStaffDailyTimetablePane().getRegionCombo() , 
									getView().getStaffDailyTimetablePane().getDistrictCombo(), dispatcher, placeManager, defaultValue);
						}
					});
				

					getView().getStaffDailyTimetablePane().getDistrictCombo().addChangedHandler(new ChangedHandler() {

						@Override
						public void onChanged(ChangedEvent event) {
							ComboUtil.loadSchoolComboByDistrict(
									getView().getStaffDailyTimetablePane().getDistrictCombo(),
									getView().getStaffDailyTimetablePane().getSchoolCombo(), dispatcher, placeManager,
									null);
						}
					});

					

					disableEnableStaffDailyAttendancePaneLoadLessonButton();
					getStaffDailyTimetablesByAcademicYearTermDistrictSchoolDate();

				} else {
					List<MenuButton> buttons = new ArrayList<>();
					getView().getControlsPane().addMenuButtons(buttons);
				}

			}

		});
	}

	private void getStaffDailyTimetablesByAcademicYearTermDistrictSchoolDate() {
		getView().getStaffDailyTimetablePane().getLoadAttendanceButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				final String academicYearId = getView().getStaffDailyTimetablePane().getAcademicYearCombo()
						.getValueAsString();
				final String academicTermId = getView().getStaffDailyTimetablePane().getAcademicTermCombo()
						.getValueAsString();
				final String districtId = getView().getStaffDailyTimetablePane().getDistrictCombo().getValueAsString();
				final String schoolId = getView().getStaffDailyTimetablePane().getSchoolCombo().getValueAsString();

				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(RequestDelimeters.ACADEMIC_YEAR_ID, academicYearId);
				map.put(RequestDelimeters.ACADEMIC_TERM_ID, academicTermId);
				map.put(RequestDelimeters.DISTRICT_ID, districtId);
				map.put(RequestDelimeters.SCHOOL_ID, schoolId);
				map.put(RequestDelimeters.LESSON_DATE, dateFormat.format(getView().getStaffDailyTimetablePane().getLessonDayDateItem().getValueAsDate()));
				if (SessionManager.getInstance().getLoggedInUserGroup().equalsIgnoreCase(SessionManager.ADMIN)) 
				   map.put(NetworkDataUtil.ACTION, RequestConstant.GET_STAFF_DAILY_TIMETABLE_ACADEMIC_YEAR_TERM_DISTRICT_SCHOOL_DATE);
					else
				map.put(NetworkDataUtil.ACTION,
						RequestConstant.GET_STAFF_DAILY_TIMETABLES_BY_SYSTEM_USER_PROFILE_SCHOOLS_ACADEMIC_YEAR_TERM_DISTRICT_SCHOOL_DATE);

				
				NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

					@Override
					public void onNetworkResult(RequestResult result) {
						getView().getStaffDailyTimetablePane().getStaffDailyAttendanceListGrid()
								.addRecordsToGrid(result.getStaffDailyTimeTableDTOs());
					}
				});
			}
		});

	}

	/////////////////////////// SATFF DAILY ATTENDANCES

	private void disableEnableStaffDailyAttendancePaneLoadLessonButton() {

		final IButton button = getView().getStaffDailyTimetablePane().getLoadAttendanceButton();
		final ComboBox termBox = getView().getStaffDailyTimetablePane().getAcademicTermCombo();
		final ComboBox schoolBox = getView().getStaffDailyTimetablePane().getSchoolCombo();
		//final TextItem dayItem = getView().getStaffDailyTimetablePane().getDayField();
        final DateItem lessonDay = getView().getStaffDailyTimetablePane().getLessonDayDateItem();
		
		termBox.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {

				if (termBox.getValueAsString() != null && schoolBox.getValueAsString() != null
						&& lessonDay.getValueAsDate() != null) {
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
						&& lessonDay.getValueAsDate() != null) {
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

				final CreateStaffDailyTimetableLessonPane createStaffDailyTimetableLessonPane = new CreateStaffDailyTimetableLessonPane();

				Tab tab = new Tab();
				tab.setTitle(StaffDailyTimetableView.ADD_STAFF_DAILY_TIMETABLE_LESSON);
				tab.setCanClose(true);
				tab.setPane(createStaffDailyTimetableLessonPane);
				getView().getTabSet().addTab(tab);
				getView().getTabSet().selectTab(tab);

				disableEnableCreateStaffDailyTaskPaneLoadLessonButton(createStaffDailyTimetableLessonPane);

				ComboUtil.loadAcademicYearCombo(createStaffDailyTimetableLessonPane.getAcademicYearCombo(), dispatcher,
						placeManager, null);

				ComboUtil.loadDistrictCombo(createStaffDailyTimetableLessonPane.getDistrictCombo(), dispatcher, placeManager,
						null);

				createStaffDailyTimetableLessonPane.getDistrictCombo().addChangedHandler(new ChangedHandler() {

					@Override
					public void onChanged(ChangedEvent event) {
						ComboUtil.loadSchoolComboByDistrict(createStaffDailyTimetableLessonPane.getDistrictCombo(),
								createStaffDailyTimetableLessonPane.getSchoolCombo(), dispatcher, placeManager, null);
					}
				});

				createStaffDailyTimetableLessonPane.getAcademicYearCombo().addChangedHandler(new ChangedHandler() {

					@Override
					public void onChanged(ChangedEvent event) {
						ComboUtil.loadAcademicTermComboByAcademicYear(createStaffDailyTimetableLessonPane.getAcademicYearCombo(),
								createStaffDailyTimetableLessonPane.getAcademicTermCombo(), dispatcher, placeManager, null);
					}
				});

				createStaffDailyTimetableLessonPane.getSchoolCombo().addChangedHandler(new ChangedHandler() {

					@Override
					public void onChanged(ChangedEvent event) {
						ComboUtil.loadSchoolStaffComboBySchool(createStaffDailyTimetableLessonPane.getSchoolCombo(),
								createStaffDailyTimetableLessonPane.getSchoolStaffCombo(), dispatcher, placeManager, null);
					}
				});

				getTimeTableLessonsForStaffAcademicYearTermDistrictSchoolDay(createStaffDailyTimetableLessonPane);
				saveStaffDailyTimetableLesson(createStaffDailyTimetableLessonPane);

				closeCreateStaffDailyTaskTab(createStaffDailyTimetableLessonPane);

			}

		});

	}

	private void closeCreateStaffDailyTaskTab(CreateStaffDailyTimetableLessonPane createStaffDailyTimetableLessonPane) {
		createStaffDailyTimetableLessonPane.getCloseTabButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				getView().getTabSet().removeTab(1);
			}
		});

	}

	///////////////////////////

	private void getTimeTableLessonsForStaffAcademicYearTermDistrictSchoolDay(
			final CreateStaffDailyTimetableLessonPane createStaffDailyTimetableLessonPane) {
		createStaffDailyTimetableLessonPane.getLoadLessonButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				if (checkIfNoFieldCreateStaffDailyTaskPaneFieldIsEmpty(createStaffDailyTimetableLessonPane)) {
					String academicYearId = createStaffDailyTimetableLessonPane.getAcademicYearCombo().getValueAsString();
					String academicTermId = createStaffDailyTimetableLessonPane.getAcademicTermCombo().getValueAsString();
					String districtId = createStaffDailyTimetableLessonPane.getDistrictCombo().getValueAsString();
					String schoolId = createStaffDailyTimetableLessonPane.getSchoolCombo().getValueAsString();
					String schoolStaffId = createStaffDailyTimetableLessonPane.getSchoolStaffCombo().getValueAsString();

					LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					map.put(RequestDelimeters.ACADEMIC_YEAR_ID, academicYearId);
					map.put(RequestDelimeters.ACADEMIC_TERM_ID, academicTermId);
					map.put(RequestDelimeters.DISTRICT_ID, districtId);
					map.put(RequestDelimeters.SCHOOL_ID, schoolId);
					map.put(RequestDelimeters.SCHOOL_STAFF_ID, schoolStaffId);
					map.put(RequestDelimeters.LESSON_DAY, dayFormat.format(new Date()));
					map.put(NetworkDataUtil.ACTION,
							RequestConstant.GET_TIME_TABLE_LESSONS_FOR_STAFF_ACADEMIC_YEAR_TERM_DISTRICT_SCHOOL_DAY);

					NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

						@Override
						public void onNetworkResult(RequestResult result) {
							createStaffDailyTimetableLessonPane.getLessonListGrid().addRecordsToGrid(result.getTableLessonDTOs());

							if (result.getTableLessonDTOs().isEmpty())
								createStaffDailyTimetableLessonPane.getSaveButton().disable();
							else
								createStaffDailyTimetableLessonPane.getSaveButton().enable();
						}
					});

				} else {
					SC.say("Please Fill all the fields");
				}
			}
		});
	}

	private boolean checkIfNoFieldCreateStaffDailyTaskPaneFieldIsEmpty(
			final CreateStaffDailyTimetableLessonPane createStaffDailyTimetableLessonPane) {
		boolean flag = true;
		if (createStaffDailyTimetableLessonPane.getDistrictCombo().getValueAsString() == null)
			flag = false;

		if (createStaffDailyTimetableLessonPane.getSchoolCombo().getValueAsString() == null)
			flag = false;

		if (createStaffDailyTimetableLessonPane.getAcademicYearCombo().getValueAsString() == null)
			flag = false;

		if (createStaffDailyTimetableLessonPane.getAcademicTermCombo().getValueAsString() == null)
			flag = false;

		if (createStaffDailyTimetableLessonPane.getSchoolStaffCombo().getValueAsString() == null)
			flag = false;

		if (createStaffDailyTimetableLessonPane.getDayField().getValueAsString() == null)
			flag = false;

		return flag;
	}

	private void disableEnableCreateStaffDailyTaskPaneLoadLessonButton(
			CreateStaffDailyTimetableLessonPane createStaffDailyTimetableLessonPane) {
		;
		final IButton button = createStaffDailyTimetableLessonPane.getLoadLessonButton();
		final ComboBox termBox = createStaffDailyTimetableLessonPane.getAcademicTermCombo();
		final ComboBox schoolBox = createStaffDailyTimetableLessonPane.getSchoolCombo();
		final ComboBox staffBox = createStaffDailyTimetableLessonPane.getSchoolStaffCombo();
		final TextItem dayItem = createStaffDailyTimetableLessonPane.getDayField();

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

	private void saveStaffDailyTimetableLesson(final CreateStaffDailyTimetableLessonPane createStaffDailyTimetableLessonPane) {
		createStaffDailyTimetableLessonPane.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				if (createStaffDailyTimetableLessonPane.getLessonListGrid().anySelected()) {
					final ComboBox termBox = createStaffDailyTimetableLessonPane.getAcademicTermCombo();
					final ComboBox schoolBox = createStaffDailyTimetableLessonPane.getSchoolCombo();
					final ComboBox staffBox = createStaffDailyTimetableLessonPane.getSchoolStaffCombo();
					final TextItem dayItem = createStaffDailyTimetableLessonPane.getDayField();

					StaffDailyTimeTableDTO staffDailyTimeTableDTO = new StaffDailyTimeTableDTO();
					staffDailyTimeTableDTO.setAcademicTermDTO(new AcademicTermDTO(termBox.getValueAsString()));

					staffDailyTimeTableDTO.setCreatedDateTime(dateTimeFormat.format(new Date()));

					staffDailyTimeTableDTO.setLessonDate(dateFormat.format(new Date()));

					staffDailyTimeTableDTO.setSchoolStaffDTO(new SchoolStaffDTO(staffBox.getValueAsString()));

					ListGridRecord[] records = createStaffDailyTimetableLessonPane.getLessonListGrid().getSelectedRecords();
					List<StaffDailyTimeTableLessonDTO> staffDailyTimeTableLessonDTOs = new ArrayList<StaffDailyTimeTableLessonDTO>();

					for (int i = 0; i < records.length; i++) {
						ListGridRecord record = records[i];

						StaffDailyTimeTableLessonDTO lessonDTO = new StaffDailyTimeTableLessonDTO();
						lessonDTO.setCreatedDateTime(dateTimeFormat.format(new Date()));
						lessonDTO.setUpdatedDateTime(dateTimeFormat.format(new Date()));
						lessonDTO.setLessonDate(dateFormat.format(new Date()));
						lessonDTO.setEndTime(record.getAttributeAsString(LessonListGrid.END_TIME));
						lessonDTO.setStartTime(record.getAttributeAsString(LessonListGrid.START_TIME));
						lessonDTO.setSchoolClassDTO(
								new SchoolClassDTO(record.getAttributeAsString(LessonListGrid.CLASS_ID)));
						lessonDTO.setSubjectDTO(new SubjectDTO(record.getAttributeAsString(LessonListGrid.SUBJECT_ID)));

						staffDailyTimeTableLessonDTOs.add(lessonDTO);

					}
					staffDailyTimeTableDTO.setStaffDailyTimeTableLessonDTOS(staffDailyTimeTableLessonDTOs);

					LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					map.put(RequestConstant.SAVE_STAFF_DAILY_TIMETABLE_LESSONS, staffDailyTimeTableDTO);
					map.put(NetworkDataUtil.ACTION, RequestConstant.SAVE_STAFF_DAILY_TIMETABLE_LESSONS);

					NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

						@Override
						public void onNetworkResult(RequestResult result) {
							SC.say("Success" , result.getSystemFeedbackDTO().getMessage());
						}
					});

				} else {
					SC.say("Please select lessons");
				}

			}
		});

	}

	/////////////////////////// VIEW TAB

	private void viewStaffDailyTimetableLessonTab(MenuButton view) {
		view.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (getView().getStaffDailyTimetablePane().getStaffDailyAttendanceListGrid().anySelected()) {
					ViewStaffDailyTimetableLessonPane viewStaffDailyTimetableLessonPane = new ViewStaffDailyTimetableLessonPane();

					ListGridRecord record = getView().getStaffDailyTimetablePane().getStaffDailyAttendanceListGrid()
							.getSelectedRecord();
					String academicYear = record.getAttribute(StaffDailyTimetableListGrid.ACADEMIC_YEAR);
					String academicYearId = record.getAttribute(StaffDailyTimetableListGrid.ACADEMIC_YEAR_ID);

					String academicTerm = record.getAttribute(StaffDailyTimetableListGrid.ACADEMIC_TERM);
					String academicTermId = record.getAttribute(StaffDailyTimetableListGrid.ACADEMIC_TERM_ID);

					String schoolStaff = record.getAttribute(StaffDailyTimetableListGrid.SCHOOL_STAFF);
					String schoolStaffId = record.getAttribute(StaffDailyTimetableListGrid.SCHOOL_STAFF_ID);

					String district = getView().getStaffDailyTimetablePane().getDistrictCombo().getDisplayValue();
					String districtId = getView().getStaffDailyTimetablePane().getDistrictCombo().getValueAsString();

					String school = getView().getStaffDailyTimetablePane().getSchoolCombo().getDisplayValue();
					String schoolId = getView().getStaffDailyTimetablePane().getSchoolCombo().getValueAsString();

					viewStaffDailyTimetableLessonPane.getAcademicYearField().setValue(academicYear);
					viewStaffDailyTimetableLessonPane.getAcademicTermField().setValue(academicTerm);
					viewStaffDailyTimetableLessonPane.getDistrictField().setValue(district);
					viewStaffDailyTimetableLessonPane.getSchoolField().setValue(school);
					viewStaffDailyTimetableLessonPane.getSchoolStaffField().setValue(schoolStaff);

					Tab tab = new Tab();
					tab.setTitle(StaffDailyTimetableView.VIEW_STAFF_DAILY_TIMETABLE_LESSON);
					tab.setCanClose(true);
					tab.setPane(viewStaffDailyTimetableLessonPane);
					getView().getTabSet().addTab(tab);
					getView().getTabSet().selectTab(tab);

					getStaffDailyTimetableLessonsByStaffDailyTimetableStaffDate(viewStaffDailyTimetableLessonPane,
							record  ,  schoolId);

					closeViewStaffDailyAttendanceTaskTab(viewStaffDailyTimetableLessonPane);
				} else {
					SC.say("Please select attendance");
				}

			}
		});
	}

	private void getStaffDailyTimetableLessonsByStaffDailyTimetableStaffDate(
			final ViewStaffDailyTimetableLessonPane viewStaffDailyTimetableLessonPane, final ListGridRecord record , String schoolId) {

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestDelimeters.SCHOOL_STAFF_ID, record.getAttribute(StaffDailyTimetableListGrid.SCHOOL_STAFF_ID));
		map.put(RequestDelimeters.STAFF_DAILY_TIMETALE_ID,
				record.getAttribute(StaffDailyTimetableListGrid.ID));
		map.put(RequestDelimeters.SCHOOL_ID , schoolId);
		map.put(RequestDelimeters.LESSON_DATE, dateFormat.format(new Date()));
		
		//SC.say("Prams "+map.get(RequestDelimeters.SCHOOL_STAFF_ID)+" \n"+map.get(RequestDelimeters.STAFF_DAILY_TIMETALE_ID)+" "+map.get(RequestDelimeters.LESSON_DATE));
		
		if (SessionManager.getInstance().getLoggedInUserGroup().equalsIgnoreCase(SessionManager.ADMIN)) 
			map.put(NetworkDataUtil.ACTION , RequestConstant.GET_STAFF_DAILY_TIMETABLE_LESSONS_BY_SCHOOL_STAFF_DATE);
			else
		map.put(NetworkDataUtil.ACTION,
				RequestConstant.GET_STAFF_DAILY_TIMETABLE_LESSONS_BY_SYSTEM_USER_PROFILE_SCHOOLS_DAILY_TIMETABLE_SCHOOL_STAFF_DATE);
		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {
				viewStaffDailyTimetableLessonPane.getDailyTaskListGrid()
						.addRecordsToGrid(result.getStaffDailyTimeTableLessonDTOs());
			}
		});
	}

	private void closeViewStaffDailyAttendanceTaskTab(
			ViewStaffDailyTimetableLessonPane viewStaffDailyTimetableLessonPane) {
		viewStaffDailyTimetableLessonPane.getCloseTabButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				getView().getTabSet().removeTab(1);
			}
		});

	}

}