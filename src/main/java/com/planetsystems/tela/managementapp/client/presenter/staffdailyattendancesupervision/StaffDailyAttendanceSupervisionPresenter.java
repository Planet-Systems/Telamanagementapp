package com.planetsystems.tela.managementapp.client.presenter.staffdailyattendancesupervision;

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
import com.planetsystems.tela.dto.FilterDTO;
import com.planetsystems.tela.dto.SchoolDTO;
import com.planetsystems.tela.dto.SchoolStaffDTO;
import com.planetsystems.tela.dto.StaffDailyAttendanceSupervisionDTO;
import com.planetsystems.tela.dto.StaffDailyAttendanceTaskSupervisionDTO;
import com.planetsystems.tela.dto.StaffDailyTimeTableDTO;
import com.planetsystems.tela.managementapp.client.gin.SessionManager;
import com.planetsystems.tela.managementapp.client.place.NameTokens;
import com.planetsystems.tela.managementapp.client.presenter.comboutils.ComboUtil;
import com.planetsystems.tela.managementapp.client.presenter.main.MainPresenter;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkDataUtil;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkResult;
import com.planetsystems.tela.managementapp.client.presenter.staffdailytimetable.StaffDailyTimetableLessonListGrid;
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

public class StaffDailyAttendanceSupervisionPresenter extends
		Presenter<StaffDailyAttendanceSupervisionPresenter.MyView, StaffDailyAttendanceSupervisionPresenter.MyProxy> {
	interface MyView extends View {
		ControlsPane getControlsPane();

		StaffDailyAttendanceSupervisionPane getStaffDailyAttendanceSupervisionPane();

		TabSet getTabSet();
	}

	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_StaffDailyAttendanceSupervision = new Type<RevealContentHandler<?>>();

	@Inject
	private DispatchAsync dispatcher;

	@Inject
	private PlaceManager placeManager;

	DateTimeFormat dateTimeFormat = DateTimeFormat
			.getFormat(DatePattern.DAY_MONTH_YEAR_HOUR_MINUTE_SECONDS.getPattern());
	DateTimeFormat dateFormat = DateTimeFormat.getFormat(DatePattern.DAY_MONTH_YEAR.getPattern());
	DateTimeFormat timeFormat = DateTimeFormat.getFormat(DatePattern.HOUR_MINUTE_SECONDS.getPattern());
	DateTimeFormat dayFormat = DateTimeFormat.getFormat(DatePattern.DAY.getPattern());
	DateTimeFormat dayDateFormat = DateTimeFormat.getFormat(DatePattern.DAY_DATE.getPattern());

	@NameToken(NameTokens.StaffDailyAttendanceSuperVision)
	@ProxyCodeSplit
	interface MyProxy extends ProxyPlace<StaffDailyAttendanceSupervisionPresenter> {
	}

	@Inject
	StaffDailyAttendanceSupervisionPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
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

				if (selectedTab
						.equalsIgnoreCase(StaffDailyAttendanceSupervisionView.STAFF_DAILY_ATTENDANCE_SUPERVISION)) {
					// close second tab
					getView().getTabSet().removeTab(1);

					MenuButton newButton = new MenuButton("New");
					MenuButton view = new MenuButton("View Teacher Lessons");

					List<MenuButton> buttons = new ArrayList<>();
					//buttons.add(newButton);
					buttons.add(view);
					getView().getControlsPane().addMenuButtons("Time Attendance Supervision",buttons);
					showCreateTab(newButton);
					viewAttendanceTaskSupervisionTab(view);

					ComboUtil.loadAcademicYearCombo(
							getView().getStaffDailyAttendanceSupervisionPane().getAcademicYearCombo(), dispatcher,
							placeManager, null);

					ComboUtil.loadDistrictCombo(getView().getStaffDailyAttendanceSupervisionPane().getDistrictCombo(),
							dispatcher, placeManager, null);

					getView().getStaffDailyAttendanceSupervisionPane().getDistrictCombo()
							.addChangedHandler(new ChangedHandler() {

								@Override
								public void onChanged(ChangedEvent event) {
									ComboUtil.loadSchoolComboByDistrict(
											getView().getStaffDailyAttendanceSupervisionPane().getDistrictCombo(),
											getView().getStaffDailyAttendanceSupervisionPane().getSchoolCombo(),
											dispatcher, placeManager, null);
								}
							});

					getView().getStaffDailyAttendanceSupervisionPane().getAcademicYearCombo()
							.addChangedHandler(new ChangedHandler() {

								@Override
								public void onChanged(ChangedEvent event) {
									ComboUtil.loadAcademicTermComboByAcademicYear(
											getView().getStaffDailyAttendanceSupervisionPane().getAcademicYearCombo(),
											getView().getStaffDailyAttendanceSupervisionPane().getAcademicTermCombo(),
											dispatcher, placeManager, null);
								}
							});

					disableEnableStaffDailyAttendanceSupervisionPaneLoadLessonButton();
					getStaffDailyAttendanceSuperVisions();

				} else {
					List<MenuButton> buttons = new ArrayList<>();
					getView().getControlsPane().addMenuButtons(buttons);
				}

			}
		});
	}

	private void getStaffDailyAttendanceSuperVisions() {
		getView().getStaffDailyAttendanceSupervisionPane().getLoadSuperVisionButton()
				.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						final String schoolId = getView().getStaffDailyAttendanceSupervisionPane().getSchoolCombo()
								.getValueAsString();
						final Date date = getView().getStaffDailyAttendanceSupervisionPane().getDateField().getValueAsDate();

						LinkedHashMap<String, Object> map = new LinkedHashMap<>();
						map.put(RequestDelimeters.SCHOOL_ID, schoolId);
						map.put(RequestDelimeters.SUPERVISION_DATE, dateFormat.format(new Date()));
						
						FilterDTO filterDTO = new FilterDTO();
						if(schoolId != null)
						filterDTO.setSchoolDTO(new SchoolDTO(schoolId));
						
						filterDTO.setDate(dateFormat.format(date));
						map.put(RequestDelimeters.FILTER_DATA, filterDTO);
						

						if (SessionManager.getInstance().getLoggedInUserGroup().equalsIgnoreCase(SessionManager.ADMIN))
							map.put(NetworkDataUtil.ACTION,
									RequestConstant.FILTER_STAFF_DAILY_SUPERVISIONS);
						else
							map.put(NetworkDataUtil.ACTION,
									RequestConstant.GET_STAFF_DAILY_SUPERVISIONS_BY_SYSTEM_USER_PROFILE_SCHOOLS_SCHOOL_DATE);

						NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

							@Override
							public void onNetworkResult(RequestResult result) {
								getView().getStaffDailyAttendanceSupervisionPane()
										.getStaffDailyAttendanceSupervisionListGrid()
										.addRecordsToGrid(result.getStaffDailyAttendanceSupervisionDTOs());
							}
						});

					}
				});

	}

	private void showCreateTab(MenuButton newButton) {
		newButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				final CreateStaffDailyAttendanceTaskSupervisionPane createSupervisionTaskPane = new CreateStaffDailyAttendanceTaskSupervisionPane();

				Tab tab = new Tab();
				tab.setTitle(StaffDailyAttendanceSupervisionView.ADD_STAFF_DAILY_ATTENDANCE_SUPERVISION_TASK);
				tab.setCanClose(true);
				tab.setPane(createSupervisionTaskPane);
				getView().getTabSet().addTab(tab);
				getView().getTabSet().selectTab(tab);

				disableEnableCreateStaffDailySupervisionTaskPaneLoadLessonButton(createSupervisionTaskPane);

				ComboUtil.loadAcademicYearCombo(createSupervisionTaskPane.getAcademicYearCombo(), dispatcher,
						placeManager, null);

				ComboUtil.loadDistrictCombo(createSupervisionTaskPane.getDistrictCombo(), dispatcher, placeManager,
						null);

				createSupervisionTaskPane.getDistrictCombo().addChangedHandler(new ChangedHandler() {

					@Override
					public void onChanged(ChangedEvent event) {
						ComboUtil.loadSchoolComboByDistrict(createSupervisionTaskPane.getDistrictCombo(),
								createSupervisionTaskPane.getSchoolCombo(), dispatcher, placeManager, null);
					}
				});

				createSupervisionTaskPane.getAcademicYearCombo().addChangedHandler(new ChangedHandler() {

					@Override
					public void onChanged(ChangedEvent event) {
						ComboUtil.loadAcademicTermComboByAcademicYear(createSupervisionTaskPane.getAcademicYearCombo(),
								createSupervisionTaskPane.getAcademicTermCombo(), dispatcher, placeManager, null);
					}
				});

				createSupervisionTaskPane.getSchoolCombo().addChangedHandler(new ChangedHandler() {

					@Override
					public void onChanged(ChangedEvent event) {
						ComboUtil.loadSchoolStaffComboBySchool(createSupervisionTaskPane.getSchoolCombo(),
								createSupervisionTaskPane.getSchoolStaffCombo(), dispatcher, placeManager, null);
					}
				});

				getStaffDailyTimetableLessonsForStaffSchoolStaffDate(createSupervisionTaskPane);
				displayCommentWindow(createSupervisionTaskPane);
				closeCreateStaffDailyTaskTab(createSupervisionTaskPane);

			}

			private void displayCommentWindow(
					final CreateStaffDailyAttendanceTaskSupervisionPane createSupervisionTaskPane) {
				createSupervisionTaskPane.getCommentButton().addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						if (createSupervisionTaskPane.getDailyAttendanceTaskListGrid().anySelected()) {
							CommentWindow window = new CommentWindow();
							window.show();
							saveStaffDailyAttendanceTaskSupervision(createSupervisionTaskPane, window);
						} else {
							SC.say("Select tasks , To comfirm");
						}
					}

				});
			}

		});

	}

	private void getStaffDailyTimetableLessonsForStaffSchoolStaffDate(
			final CreateStaffDailyAttendanceTaskSupervisionPane createSupervisionTaskPane) {
		createSupervisionTaskPane.getLoadTasksButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				final String schoolId = createSupervisionTaskPane.getSchoolCombo().getValueAsString();
				final String schoolStaffId = createSupervisionTaskPane.getSchoolStaffCombo().getValueAsString();

				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(RequestDelimeters.SCHOOL_ID, schoolId);
				map.put(RequestDelimeters.SCHOOL_STAFF_ID, schoolStaffId);
				map.put(RequestDelimeters.LESSON_DATE, dateFormat.format(new Date()));
				map.put(NetworkDataUtil.ACTION, RequestConstant.GET_STAFF_DAILY_TIMETABLE_LESSONS_BY_SCHOOL_STAFF_DATE);

				NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

					@Override
					public void onNetworkResult(RequestResult result) {
						createSupervisionTaskPane.getDailyAttendanceTaskListGrid()
								.addRecordsToGrid(result.getStaffDailyTimeTableLessonDTOs());

						if (result.getStaffDailyTimeTableLessonDTOs().isEmpty())
							createSupervisionTaskPane.getCommentButton().disable();
						else
							createSupervisionTaskPane.getCommentButton().enable();

					}
				});

			}
		});

	}

	private void saveStaffDailyAttendanceTaskSupervision(
			final CreateStaffDailyAttendanceTaskSupervisionPane createSupervisionTaskPane, final CommentWindow window) {

		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				StaffDailyAttendanceSupervisionDTO supervisionDTO = new StaffDailyAttendanceSupervisionDTO();
				String comment = window.getCommenTextAreaItem().getValueAsString();
				SchoolStaffDTO schoolStaffDTO = new SchoolStaffDTO(
						createSupervisionTaskPane.getSchoolStaffCombo().getValueAsString());

				supervisionDTO.setComment(comment);
				supervisionDTO.setSchoolStaffDTO(schoolStaffDTO);
				supervisionDTO.setSupervisionTime(timeFormat.format(new Date()));
				supervisionDTO.setSupervisionDate(dateFormat.format(new Date()));
				supervisionDTO.setCreatedDateTime(dateTimeFormat.format(new Date()));

				List<StaffDailyAttendanceTaskSupervisionDTO> taskSupervisionDTOs = new ArrayList<StaffDailyAttendanceTaskSupervisionDTO>();
				ListGridRecord[] records = createSupervisionTaskPane.getDailyAttendanceTaskListGrid()
						.getSelectedRecords();
				for (int i = 0; i < records.length; i++) {
					ListGridRecord record = records[i];

					StaffDailyAttendanceTaskSupervisionDTO task = new StaffDailyAttendanceTaskSupervisionDTO();
					task.setCreatedDateTime(dateTimeFormat.format(new Date()));

					StaffDailyTimeTableDTO staffDailyTimeTableDTO = new StaffDailyTimeTableDTO();
					staffDailyTimeTableDTO.setId(record.getAttribute(StaffDailyTimetableLessonListGrid.STAFF_DAILY_TIME_TABLE_ID));
					
					//task.setStaffDailyTimeTableDTO(staffDailyTimeTableDTO);

					task.setTeachingStatus("present");
					task.setTeachingTimeStatus("In Time");
					// task.setStatus(status);

					taskSupervisionDTOs.add(task);
				}
				supervisionDTO.setStaffDailyAttendanceTaskSupervisionDTOS(taskSupervisionDTOs);

				GWT.log("DATA Supervision " + supervisionDTO);

				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(RequestConstant.SAVE_STAFF_DAILY_TASK_SUPERVISIONS, supervisionDTO);
				map.put(NetworkDataUtil.ACTION, RequestConstant.SAVE_STAFF_DAILY_TASK_SUPERVISIONS);

				NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

					@Override
					public void onNetworkResult(RequestResult result) {
						window.close();
						SC.say("SUCCESS", result.getSystemFeedbackDTO().getMessage());
						getView().getTabSet().removeTab(1);
						getView().getTabSet().setSelectedTab(0);
					}
				});

			}
		});

	}

	private void closeCreateStaffDailyTaskTab(CreateStaffDailyAttendanceTaskSupervisionPane createSupervisionTaskPane) {
		createSupervisionTaskPane.getCloseTabButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				getView().getTabSet().removeTab(1);
			}
		});

	}

	private void disableEnableCreateStaffDailySupervisionTaskPaneLoadLessonButton(
			CreateStaffDailyAttendanceTaskSupervisionPane createStaffDailySupervisionTaskPane) {
		;
		final IButton button = createStaffDailySupervisionTaskPane.getLoadTasksButton();
		final ComboBox termBox = createStaffDailySupervisionTaskPane.getAcademicTermCombo();
		final ComboBox schoolBox = createStaffDailySupervisionTaskPane.getSchoolCombo();
		final ComboBox staffBox = createStaffDailySupervisionTaskPane.getSchoolStaffCombo();
		final TextItem dayItem = createStaffDailySupervisionTaskPane.getDayField();

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

	private void disableEnableStaffDailyAttendanceSupervisionPaneLoadLessonButton() {

		final IButton button = getView().getStaffDailyAttendanceSupervisionPane().getLoadSuperVisionButton();
		final ComboBox termBox = getView().getStaffDailyAttendanceSupervisionPane().getAcademicTermCombo();
		final ComboBox schoolBox = getView().getStaffDailyAttendanceSupervisionPane().getSchoolCombo();
		final DateItem dateItem = getView().getStaffDailyAttendanceSupervisionPane().getDateField();

		termBox.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {

				if (termBox.getValueAsString() != null && schoolBox.getValueAsString() != null
						&& dateItem.getValueAsDate() != null) {
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
						&& dateItem.getValueAsDate() != null) {
					button.setDisabled(false);
				} else {
					button.setDisabled(true);
				}
			}
		});
	}

	private void viewAttendanceTaskSupervisionTab(MenuButton view) {
		view.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (getView().getStaffDailyAttendanceSupervisionPane().getStaffDailyAttendanceSupervisionListGrid()
						.anySelected()) {
					ViewStaffDailyAttendanceTaskSupervisionPane viewStaffDailyAttendanceSupervisionTaskPane = new ViewStaffDailyAttendanceTaskSupervisionPane();

					ListGridRecord record = getView().getStaffDailyAttendanceSupervisionPane()
							.getStaffDailyAttendanceSupervisionListGrid().getSelectedRecord();

					String academicYear = getView().getStaffDailyAttendanceSupervisionPane().getAcademicYearCombo()
							.getDisplayValue();
					String academicTerm = getView().getStaffDailyAttendanceSupervisionPane().getAcademicTermCombo()
							.getDisplayValue();

					String schoolStaff = record.getAttribute(StaffDailyAttendanceSupervisionListGrid.SCHOOL_STAFF);

					String district = getView().getStaffDailyAttendanceSupervisionPane().getDistrictCombo()
							.getDisplayValue();

					String school = getView().getStaffDailyAttendanceSupervisionPane().getSchoolCombo()
							.getDisplayValue();
					
					Date date = getView().getStaffDailyAttendanceSupervisionPane().getDateField().getValueAsDate();

					viewStaffDailyAttendanceSupervisionTaskPane.getAcademicYearField().setValue(academicYear);
					viewStaffDailyAttendanceSupervisionTaskPane.getAcademicTermField().setValue(academicTerm);
					viewStaffDailyAttendanceSupervisionTaskPane.getDistrictField().setValue(district);
					viewStaffDailyAttendanceSupervisionTaskPane.getSchoolField().setValue(school);
					viewStaffDailyAttendanceSupervisionTaskPane.getSchoolStaffField().setValue(schoolStaff);
					viewStaffDailyAttendanceSupervisionTaskPane.getDayField().setValue(dayDateFormat.format(date));

					Tab tab = new Tab();
					tab.setTitle(StaffDailyAttendanceSupervisionView.VIEW_STAFF_DAILY_ATTENDANCE_SUPERVISION_TASK);
					tab.setCanClose(true);
					tab.setPane(viewStaffDailyAttendanceSupervisionTaskPane);
					getView().getTabSet().addTab(tab);
					getView().getTabSet().selectTab(tab);

					getStaffDailySupervisionTasksByStaffDateStaffDailySupervisionAttendance(
							viewStaffDailyAttendanceSupervisionTaskPane, record);
					closeViewStaffDailyAttendanceSupervisionTaskTab(viewStaffDailyAttendanceSupervisionTaskPane);
				} else {
					SC.say("Please select attendance");
				}

			}

		});
	}

	private void getStaffDailySupervisionTasksByStaffDateStaffDailySupervisionAttendance(
			final ViewStaffDailyAttendanceTaskSupervisionPane supervisionPane, final ListGridRecord record) {

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
//		map.put(RequestDelimeters.SCHOOL_STAFF_ID,
//				record.getAttribute(StaffDailyAttendanceSupervisionListGrid.SCHOOL_STAFF_ID));
//		map.put(RequestDelimeters.STAFF_DAILY_ATTENDANCE_SUPERVISION_ID,
//				record.getAttribute(StaffDailyAttendanceSupervisionListGrid.ID));
//		map.put(RequestDelimeters.SUPERVISION_DATE, dateFormat.format(new Date()));
//		
//		map.put(NetworkDataUtil.ACTION,
//				RequestConstant.GET_STAFF_DAILY_ATTENDANCE_TASK_SUPERVISIONS_BY_SYSTEM_USER_PROFILE_SCHOOLS_STAFF_DATE_DAILY_ATTENDANCE_SUPERVISION);
		
		Date date = getView().getStaffDailyAttendanceSupervisionPane().getDateField().getValueAsDate();

	
		
		
		FilterDTO filterDTO = new FilterDTO();
		filterDTO.setSchoolStaffDTO(new SchoolStaffDTO(record.getAttribute(StaffDailyAttendanceSupervisionListGrid.SCHOOL_STAFF_ID)));
		filterDTO.setStaffDailyAttendanceSupervisionDTO(new StaffDailyAttendanceSupervisionDTO(record.getAttribute(StaffDailyAttendanceSupervisionListGrid.ID)));
		filterDTO.setDate(dateFormat.format(date));
		GWT.log("DATE "+filterDTO.getDate());
		
		map.put(NetworkDataUtil.ACTION , RequestConstant.FILTER_STAFF_DAILY_ATTENDANCE_TASK_SUPERVISIONS);
		map.put(RequestDelimeters.FILTER_DATA , filterDTO);
		
		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {
				supervisionPane.getStaffDailyAttendanceTaskSupervisionListGrid()
						.addRecordsToGrid(result.getStaffDailyAttendanceTaskSupervisionDTOs());
			}
		});
	}

	private void closeViewStaffDailyAttendanceSupervisionTaskTab(
			ViewStaffDailyAttendanceTaskSupervisionPane viewStaffDailyAttendanceSupervisionTaskPane) {
		viewStaffDailyAttendanceSupervisionTaskPane.getCloseTabButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				getView().getTabSet().removeTab(1);
			}
		});

	}

}