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
import com.planetsystems.tela.dto.AcademicTermDTO;
import com.planetsystems.tela.dto.AcademicYearDTO;
import com.planetsystems.tela.dto.DistrictDTO;
import com.planetsystems.tela.dto.FilterDTO;
import com.planetsystems.tela.dto.SchoolDTO;
import com.planetsystems.tela.dto.SchoolStaffDTO;
import com.planetsystems.tela.dto.StaffDailyAttendanceSupervisionDTO;
import com.planetsystems.tela.dto.StaffDailyAttendanceTaskSupervisionDTO;
import com.planetsystems.tela.dto.StaffDailyTimeTableDTO;
import com.planetsystems.tela.dto.StaffDailyTimeTableLessonDTO;
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
import com.planetsystems.tela.managementapp.client.presenter.staffdailytimetable.StaffDailyTimetableLessonListGrid;
import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.planetsystems.tela.managementapp.client.widget.MenuButton;
import com.planetsystems.tela.managementapp.shared.DatePattern;
import com.planetsystems.tela.managementapp.shared.MyRequestAction;
import com.planetsystems.tela.managementapp.shared.MyRequestResult;
import com.planetsystems.tela.managementapp.shared.RequestConstant;
import com.planetsystems.tela.managementapp.shared.RequestDelimeters;
import com.planetsystems.tela.managementapp.shared.RequestResult;
import com.planetsystems.tela.managementapp.shared.requestcommands.StaffDailySupervisionCommand;
import com.planetsystems.tela.managementapp.shared.requestcommands.StaffDailySupervisionTaskCommand;
import com.planetsystems.tela.managementapp.shared.requestcommands.StaffDailyTimetableLessonCommands;
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
					MenuButton view = new MenuButton("view");

					List<MenuButton> buttons = new ArrayList<>();
					buttons.add(newButton);
					buttons.add(view);
					getView().getControlsPane().addMenuButtons(buttons);
					showCreateTab(newButton);
					viewAttendanceTaskSupervisionTab(view);

					ComboUtil2.loadAcademicYearCombo(
							getView().getStaffDailyAttendanceSupervisionPane().getAcademicYearCombo(), dispatcher,
							placeManager, null);

					ComboUtil2.loadDistrictCombo(getView().getStaffDailyAttendanceSupervisionPane().getDistrictCombo(),
							dispatcher, placeManager, null);

					getView().getStaffDailyAttendanceSupervisionPane().getDistrictCombo()
							.addChangedHandler(new ChangedHandler() {

								@Override
								public void onChanged(ChangedEvent event) {
									ComboUtil2.loadSchoolComboByDistrict(
											getView().getStaffDailyAttendanceSupervisionPane().getDistrictCombo(),
											getView().getStaffDailyAttendanceSupervisionPane().getSchoolCombo(),
											dispatcher, placeManager, null);
								}
							});

					getView().getStaffDailyAttendanceSupervisionPane().getAcademicYearCombo()
							.addChangedHandler(new ChangedHandler() {

								@Override
								public void onChanged(ChangedEvent event) {
									ComboUtil2.loadAcademicTermComboByAcademicYear(
											getView().getStaffDailyAttendanceSupervisionPane().getAcademicYearCombo(),
											getView().getStaffDailyAttendanceSupervisionPane().getAcademicTermCombo(),
											dispatcher, placeManager, null);
								}
							});

					disableEnableStaffDailyAttendanceSupervisionPaneLoadLessonButton();
					getStaffDailyAttendanceSuperVisions2();

				} else {
					List<MenuButton> buttons = new ArrayList<>();
					getView().getControlsPane().addMenuButtons(buttons);
				}

			}
		});
	}

	@Deprecated
	private void getStaffDailyAttendanceSuperVisions() {
		getView().getStaffDailyAttendanceSupervisionPane().getLoadSuperVisionButton()
				.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						final String schoolId = getView().getStaffDailyAttendanceSupervisionPane().getSchoolCombo()
								.getValueAsString();

						LinkedHashMap<String, Object> map = new LinkedHashMap<>();
						map.put(RequestDelimeters.SCHOOL_ID, schoolId);
						map.put(RequestDelimeters.SUPERVISION_DATE, dateFormat.format(new Date()));

						if (SessionManager.getInstance().getLoggedInUserGroup().equalsIgnoreCase(SessionManager.ADMIN))
							map.put(NetworkDataUtil.ACTION,
									RequestConstant.GET_STAFF_DAILY_SUPERVISIONS_IN_SCHOOL_DATE);
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

				ComboUtil2.loadAcademicYearCombo(createSupervisionTaskPane.getAcademicYearCombo(), dispatcher,
						placeManager, null);

				ComboUtil2.loadDistrictCombo(createSupervisionTaskPane.getDistrictCombo(), dispatcher, placeManager,
						null);

				createSupervisionTaskPane.getDistrictCombo().addChangedHandler(new ChangedHandler() {

					@Override
					public void onChanged(ChangedEvent event) {
						ComboUtil2.loadSchoolComboByDistrict(createSupervisionTaskPane.getDistrictCombo(),
								createSupervisionTaskPane.getSchoolCombo(), dispatcher, placeManager, null);
					}
				});

				createSupervisionTaskPane.getAcademicYearCombo().addChangedHandler(new ChangedHandler() {

					@Override
					public void onChanged(ChangedEvent event) {
						ComboUtil2.loadAcademicTermComboByAcademicYear(createSupervisionTaskPane.getAcademicYearCombo(),
								createSupervisionTaskPane.getAcademicTermCombo(), dispatcher, placeManager, null);
					}
				});

				createSupervisionTaskPane.getSchoolCombo().addChangedHandler(new ChangedHandler() {

					@Override
					public void onChanged(ChangedEvent event) {
						ComboUtil2.loadSchoolStaffComboBySchool(createSupervisionTaskPane.getSchoolCombo(),
								createSupervisionTaskPane.getSchoolStaffCombo(), dispatcher, placeManager, null);
					}
				});

				getStaffDailyTimetableLessonsForStaffSchoolStaffDate2(createSupervisionTaskPane);
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
							saveStaffDailyAttendanceTaskSupervision2(createSupervisionTaskPane, window);
						} else {
							SC.say("Select tasks , To comfirm");
						}
					}

				});
			}

		});

	}

	
	@Deprecated
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

	
	@Deprecated
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
					staffDailyTimeTableDTO
							.setId(record.getAttribute(StaffDailyTimetableLessonListGrid.STAFF_DAILY_TIME_TABLE_ID));
					task.setStaffDailyTimeTableDTO(staffDailyTimeTableDTO);

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
		final TextItem dayItem = getView().getStaffDailyAttendanceSupervisionPane().getDayField();

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

					viewStaffDailyAttendanceSupervisionTaskPane.getAcademicYearField().setValue(academicYear);
					viewStaffDailyAttendanceSupervisionTaskPane.getAcademicTermField().setValue(academicTerm);
					viewStaffDailyAttendanceSupervisionTaskPane.getDistrictField().setValue(district);
					viewStaffDailyAttendanceSupervisionTaskPane.getSchoolField().setValue(school);
					viewStaffDailyAttendanceSupervisionTaskPane.getSchoolStaffField().setValue(schoolStaff);

					Tab tab = new Tab();
					tab.setTitle(StaffDailyAttendanceSupervisionView.VIEW_STAFF_DAILY_ATTENDANCE_SUPERVISION_TASK);
					tab.setCanClose(true);
					tab.setPane(viewStaffDailyAttendanceSupervisionTaskPane);
					getView().getTabSet().addTab(tab);
					getView().getTabSet().selectTab(tab);

					getStaffDailySupervisionTasksByStaffDateStaffDailySupervisionAttendance2(
							viewStaffDailyAttendanceSupervisionTaskPane, record);
					closeViewStaffDailyAttendanceSupervisionTaskTab(viewStaffDailyAttendanceSupervisionTaskPane);
				} else {
					SC.say("Please select attendance");
				}

			}

		});
	}

	@Deprecated
	private void getStaffDailySupervisionTasksByStaffDateStaffDailySupervisionAttendance(
			final ViewStaffDailyAttendanceTaskSupervisionPane supervisionPane, final ListGridRecord record) {

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestDelimeters.SCHOOL_STAFF_ID,
				record.getAttribute(StaffDailyAttendanceSupervisionListGrid.SCHOOL_STAFF_ID));
		map.put(RequestDelimeters.STAFF_DAILY_ATTENDANCE_SUPERVISION_ID,
				record.getAttribute(StaffDailyAttendanceSupervisionListGrid.ID));
		map.put(RequestDelimeters.SUPERVISION_DATE, dateFormat.format(new Date()));
		map.put(NetworkDataUtil.ACTION,
				RequestConstant.GET_STAFF_DAILY_ATTENDANCE_TASK_SUPERVISIONS_BY_SYSTEM_USER_PROFILE_SCHOOLS_STAFF_DATE_DAILY_ATTENDANCE_SUPERVISION);

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
	
	
	/////////////////////////////////////////////////NEW NEW NEW NEW NEW NEW
	
	
	private void getStaffDailyTimetableLessonsForStaffSchoolStaffDate2(
			final CreateStaffDailyAttendanceTaskSupervisionPane createSupervisionTaskPane) {
		createSupervisionTaskPane.getLoadTasksButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				final String schoolId = createSupervisionTaskPane.getSchoolCombo().getValueAsString();
				final String schoolStaffId = createSupervisionTaskPane.getSchoolStaffCombo().getValueAsString();

				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				FilterDTO dto = new FilterDTO();
				dto.setSchoolDTO(new SchoolDTO(schoolId));
				dto.setSchoolStaffDTO(new SchoolStaffDTO(schoolStaffId));
				dto.setDate(dateFormat.format(new Date()));
				

				map.put(MyRequestAction.COMMAND, StaffDailyTimetableLessonCommands.GET_ALL_STAFF_DAILY_TIME_TABLE_LESSONS);
				map.put(MyRequestAction.DATA, dto);

				
				NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

					@Override
					public void onNetworkResult(MyRequestResult result) {
						if (result != null) {
							SystemResponseDTO<List<StaffDailyTimeTableLessonDTO>> responseDTO = result.getStaffDailyTimeTableLessonResponseList();
							if (responseDTO.isStatus()) {
								if(responseDTO.getData() !=null) {
									createSupervisionTaskPane.getDailyAttendanceTaskListGrid().addRecordsToGrid(responseDTO.getData());

							if (responseDTO.getData().isEmpty())
								createSupervisionTaskPane.getCommentButton().disable();
							else
								createSupervisionTaskPane.getCommentButton().enable();
								}
								
							
							} else {
								SC.say(responseDTO.getMessage());
							}
						}

					}
				});

			}
		});

	}

	
	
	
	
	private void getStaffDailyAttendanceSuperVisions2() {
		getView().getStaffDailyAttendanceSupervisionPane().getLoadSuperVisionButton()
				.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
				
						LinkedHashMap<String, Object> map = new LinkedHashMap<>();
						FilterDTO dto = new FilterDTO();
						dto.setAcademicYearDTO(new AcademicYearDTO(getView().getStaffDailyAttendanceSupervisionPane().getAcademicYearCombo().getValueAsString()));
						dto.setAcademicTermDTO(new AcademicTermDTO(getView().getStaffDailyAttendanceSupervisionPane().getAcademicTermCombo().getValueAsString()));
						dto.setSchoolDTO(new SchoolDTO(getView().getStaffDailyAttendanceSupervisionPane().getSchoolCombo().getValueAsString()));
						dto.setDistrictDTO(new DistrictDTO(getView().getStaffDailyAttendanceSupervisionPane().getDistrictCombo().getValueAsString()));
						dto.setDate(dateFormat.format(new Date()));
						map.put(MyRequestAction.DATA, dto);
						

						if (SessionManager.getInstance().getLoggedInUserGroup().equalsIgnoreCase(SessionManager.ADMIN))
							map.put(MyRequestAction.COMMAND,
									StaffDailySupervisionCommand.GET_ALL_STAFF_DAILY_SUPERVISIONS);
						else
							map.put(MyRequestAction.COMMAND,
									StaffDailySupervisionCommand.GET_STAFF_DAILY_SUPERVISIONS_BY_SYSTEM_USER_PROFILE_SCHOOLS_SCHOOL_DATE);

						NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

							@Override
							public void onNetworkResult(MyRequestResult result) {
								if (result != null) {
									SystemResponseDTO<List<StaffDailyAttendanceSupervisionDTO>> responseDTO = result.getStaffDailyAttendanceSupervisionResponseList();
									if (responseDTO.isStatus()) {
										if(responseDTO.getData() !=null) {
											getView().getStaffDailyAttendanceSupervisionPane()
											.getStaffDailyAttendanceSupervisionListGrid()
											.addRecordsToGrid(responseDTO.getData());
										
									
									} else {
										SC.say(responseDTO.getMessage());
									}
								}
								
								}}
						});

					}
				});

	}


	
	
	
	private void saveStaffDailyAttendanceTaskSupervision2(
			final CreateStaffDailyAttendanceTaskSupervisionPane createSupervisionTaskPane, final CommentWindow window) {

		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				StaffDailyAttendanceSupervisionDTO supervisionDTO = new StaffDailyAttendanceSupervisionDTO();
				String comment = window.getCommenTextAreaItem().getValueAsString();
				SchoolStaffDTO schoolStaffDTO = new SchoolStaffDTO(
						createSupervisionTaskPane.getSchoolStaffCombo().getValueAsString());
				SchoolDTO schoolDTO = new SchoolDTO(createSupervisionTaskPane.getSchoolCombo().getValueAsString());
				schoolDTO.setDistrictDTO(new DistrictDTO(createSupervisionTaskPane.getDistrictCombo().getValueAsString()));
				schoolStaffDTO.setSchoolDTO(schoolDTO);
			

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
					staffDailyTimeTableDTO
							.setId(record.getAttribute(StaffDailyTimetableLessonListGrid.STAFF_DAILY_TIME_TABLE_ID));
					task.setStaffDailyTimeTableDTO(staffDailyTimeTableDTO);

					task.setTeachingStatus("present");
					task.setTeachingTimeStatus("In Time");
					// task.setStatus(status);

					taskSupervisionDTOs.add(task);
				}
				supervisionDTO.setStaffDailyAttendanceTaskSupervisionDTOS(taskSupervisionDTOs);

				GWT.log("DATA Supervision " + supervisionDTO);

				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(MyRequestAction.DATA, supervisionDTO);
				map.put(MyRequestAction.COMMAND, StaffDailySupervisionCommand.SAVE_STAFF_DAILY_SUPERVISION);

				NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

					@Override
					public void onNetworkResult(MyRequestResult result) {
						if (result != null) {
							SystemResponseDTO<StaffDailyAttendanceSupervisionDTO> responseDTO = result.getStaffDailyAttendanceSupervisionResponse();
							if (responseDTO.isStatus()) {
								window.close();
								SC.say("SUCCESS", responseDTO.getMessage());
								getView().getTabSet().removeTab(1);
								getView().getTabSet().setSelectedTab(0);
		
							} else {
								SC.say(responseDTO.getMessage());
							}
						}
					
					}
				});

			}
		});

	}


	
	private void getStaffDailySupervisionTasksByStaffDateStaffDailySupervisionAttendance2(
			final ViewStaffDailyAttendanceTaskSupervisionPane supervisionPane, final ListGridRecord record) {

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		FilterDTO dto = new FilterDTO();
		dto.setSchoolStaffDTO(new SchoolStaffDTO(record.getAttribute(StaffDailyAttendanceSupervisionListGrid.SCHOOL_STAFF_ID)));
		dto.setStaffDailyAttendanceSupervisionDTO(new StaffDailyAttendanceSupervisionDTO(record.getAttribute(StaffDailyAttendanceSupervisionListGrid.ID)));
		dto.setDate(dateFormat.format(new Date()));

		map.put(MyRequestAction.COMMAND,
				StaffDailySupervisionTaskCommand.GET_ALL_STAFF_DAILY_SUPERVISION_TASKS);

		NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

			@Override
			public void onNetworkResult(MyRequestResult result) {
				if (result != null) {
					SystemResponseDTO<List<StaffDailyAttendanceTaskSupervisionDTO>> responseDTO = result.getAttendanceTaskSupervisionResponseList();
					if (responseDTO.isStatus()) {
						if(responseDTO.getData() !=null) {
							supervisionPane.getStaffDailyAttendanceTaskSupervisionListGrid()
							.addRecordsToGrid(responseDTO.getData());
						}
						
					
					} else {
						SC.say(responseDTO.getMessage());
					}
				}

			}
		});
	}

	
	
	

}