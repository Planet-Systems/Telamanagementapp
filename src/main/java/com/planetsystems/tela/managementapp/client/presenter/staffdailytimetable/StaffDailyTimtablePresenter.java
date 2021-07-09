package com.planetsystems.tela.managementapp.client.presenter.staffdailytimetable;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import com.gargoylesoftware.htmlunit.javascript.host.Window;
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
import com.planetsystems.tela.dto.AcademicYearDTO;
import com.planetsystems.tela.dto.DistrictDTO;
import com.planetsystems.tela.dto.FilterDTO;
import com.planetsystems.tela.dto.SchoolClassDTO;
import com.planetsystems.tela.dto.SchoolDTO;
import com.planetsystems.tela.dto.SchoolStaffDTO;
import com.planetsystems.tela.dto.StaffDailyTimeTableDTO;
import com.planetsystems.tela.dto.StaffDailyTimeTableLessonDTO;
import com.planetsystems.tela.dto.SubjectDTO;
import com.planetsystems.tela.dto.TimeTableDTO;
import com.planetsystems.tela.dto.TimeTableLessonDTO;
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
import com.planetsystems.tela.managementapp.client.presenter.timetable.LessonListGrid;
import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.planetsystems.tela.managementapp.client.widget.MenuButton;
import com.planetsystems.tela.managementapp.shared.DatePattern;
import com.planetsystems.tela.managementapp.shared.MyRequestAction;
import com.planetsystems.tela.managementapp.shared.MyRequestResult;
import com.planetsystems.tela.managementapp.shared.RequestConstant;
import com.planetsystems.tela.managementapp.shared.RequestDelimeters;
import com.planetsystems.tela.managementapp.shared.RequestResult;
import com.planetsystems.tela.managementapp.shared.requestcommands.SchoolStaffEnrollmentCommand;
import com.planetsystems.tela.managementapp.shared.requestcommands.StaffDailyTimetableCommand;
import com.planetsystems.tela.managementapp.shared.requestcommands.StaffDailyTimetableLessonCommands;
import com.planetsystems.tela.managementapp.shared.requestcommands.TimetableLessonCommands;
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

	@NameToken(NameTokens.StaffDailyTimetableLessons)
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
					getView().getControlsPane().addMenuButtons(buttons);
					showCreateTab(newButton);
					viewStaffDailyTimetableLessonTab(view);
					
					final String defaultValue = null;

					ComboUtil2.loadAcademicYearCombo(getView().getStaffDailyTimetablePane().getAcademicYearCombo(),
							dispatcher, placeManager, defaultValue);
					
					getView().getStaffDailyTimetablePane().getAcademicYearCombo()
					.addChangedHandler(new ChangedHandler() {

						@Override
						public void onChanged(ChangedEvent event) {
							ComboUtil2.loadAcademicTermComboByAcademicYear(
									getView().getStaffDailyTimetablePane().getAcademicYearCombo(),
									getView().getStaffDailyTimetablePane().getAcademicTermCombo(), dispatcher,
									placeManager, defaultValue);
						}
					});
					
					
					

					ComboUtil2.loadRegionCombo(getView().getStaffDailyTimetablePane().getRegionCombo() , dispatcher, placeManager, defaultValue);
					getView().getStaffDailyTimetablePane().getRegionCombo().addChangedHandler(new ChangedHandler() {
						
						@Override
						public void onChanged(ChangedEvent event) {
							ComboUtil2.loadDistrictComboByRegion(getView().getStaffDailyTimetablePane().getRegionCombo() , 
									getView().getStaffDailyTimetablePane().getDistrictCombo(), dispatcher, placeManager, defaultValue);
						}
					});
				

					getView().getStaffDailyTimetablePane().getDistrictCombo().addChangedHandler(new ChangedHandler() {

						@Override
						public void onChanged(ChangedEvent event) {
							ComboUtil2.loadSchoolComboByDistrict(
									getView().getStaffDailyTimetablePane().getDistrictCombo(),
									getView().getStaffDailyTimetablePane().getSchoolCombo(), dispatcher, placeManager,
									null);
						}
					});

					

					disableEnableStaffDailyAttendancePaneLoadLessonButton();
					getStaffDailyTimetablesByAcademicYearTermDistrictSchoolDate2();

				} else {
					List<MenuButton> buttons = new ArrayList<>();
					getView().getControlsPane().addMenuButtons(buttons);
				}

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

				ComboUtil2.loadAcademicYearCombo(createStaffDailyTimetableLessonPane.getAcademicYearCombo(), dispatcher,
						placeManager, null);

				ComboUtil2.loadDistrictCombo(createStaffDailyTimetableLessonPane.getDistrictCombo(), dispatcher, placeManager,
						null);

				createStaffDailyTimetableLessonPane.getDistrictCombo().addChangedHandler(new ChangedHandler() {

					@Override
					public void onChanged(ChangedEvent event) {
						ComboUtil2.loadSchoolComboByDistrict(createStaffDailyTimetableLessonPane.getDistrictCombo(),
								createStaffDailyTimetableLessonPane.getSchoolCombo(), dispatcher, placeManager, null);
					}
				});

				createStaffDailyTimetableLessonPane.getAcademicYearCombo().addChangedHandler(new ChangedHandler() {

					@Override
					public void onChanged(ChangedEvent event) {
						ComboUtil2.loadAcademicTermComboByAcademicYear(createStaffDailyTimetableLessonPane.getAcademicYearCombo(),
								createStaffDailyTimetableLessonPane.getAcademicTermCombo(), dispatcher, placeManager, null);
					}
				});

				createStaffDailyTimetableLessonPane.getSchoolCombo().addChangedHandler(new ChangedHandler() {

					@Override
					public void onChanged(ChangedEvent event) {
						ComboUtil2.loadSchoolStaffComboBySchool(createStaffDailyTimetableLessonPane.getSchoolCombo(),
								createStaffDailyTimetableLessonPane.getSchoolStaffCombo(), dispatcher, placeManager, null);
					}

				
				});

				getTimeTableLessonsForStaffAcademicYearTermDistrictSchoolDay2(createStaffDailyTimetableLessonPane);
				saveStaffDailyTimetableLesson2(createStaffDailyTimetableLessonPane);

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
							record);

					closeViewStaffDailyAttendanceTaskTab(viewStaffDailyTimetableLessonPane);
				} else {
					SC.say("Please select attendance");
				}

			}
		});
	}

	private void getStaffDailyTimetableLessonsByStaffDailyTimetableStaffDate(
			final ViewStaffDailyTimetableLessonPane viewStaffDailyTimetableLessonPane, final ListGridRecord record) {

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		FilterDTO dto = new FilterDTO();
		dto.setSchoolStaffDTO(new SchoolStaffDTO(record.getAttribute(StaffDailyTimetableListGrid.SCHOOL_STAFF_ID)));
		dto.setStaffDailyTimeTableDTO(new StaffDailyTimeTableDTO(record.getAttribute(StaffDailyTimetableListGrid.ID)));
		dto.setDate(dateFormat.format(new Date()));
		
		map.put(MyRequestAction.DATA, dto);

		
		if (SessionManager.getInstance().getLoggedInUserGroup().equalsIgnoreCase(SessionManager.ADMIN)) 
			map.put(MyRequestAction.COMMAND , StaffDailyTimetableLessonCommands.GET_ALL_STAFF_DAILY_TIME_TABLE_LESSONS);
			else
		map.put(MyRequestAction.COMMAND , StaffDailyTimetableLessonCommands.GET_STAFF_DAILY_TIME_TABLES_LESSONS_BY_SYSTEM_USER_PROFILE_SCHOOLS);
		
		NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

			@Override
			public void onNetworkResult(MyRequestResult result) {
				if (result != null) {
					SystemResponseDTO<List<StaffDailyTimeTableLessonDTO>> responseDTO = result.getStaffDailyTimeTableLessonResponseList();
					if (responseDTO.isStatus()) {
						if(responseDTO.getData() != null) {
							viewStaffDailyTimetableLessonPane.getDailyTaskListGrid().addRecordsToGrid(responseDTO.getData());
						}
					} else {
						SC.say(responseDTO.getMessage());
					}
				}
				
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
	
	
	
	//////////////////////////////////////////////////////////NEW NEW NEW NEW
	
	private void getStaffDailyTimetablesByAcademicYearTermDistrictSchoolDate2() {
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
				FilterDTO dto = new FilterDTO();
				dto.setAcademicYearDTO(new AcademicYearDTO(academicYearId));
				dto.setAcademicTermDTO(new AcademicTermDTO(academicTermId));
				dto.setDistrictDTO(new DistrictDTO(districtId));
				dto.setSchoolDTO(new SchoolDTO(schoolId));
				dto.setDate(dateFormat.format(getView().getStaffDailyTimetablePane().getLessonDayDateItem().getValueAsDate()));
				map.put(MyRequestAction.DATA, dto);
				
				

				if (SessionManager.getInstance().getLoggedInUserGroup().equalsIgnoreCase(SessionManager.ADMIN)) 
				   map.put(MyRequestAction.COMMAND, StaffDailyTimetableCommand.GET_ALL_STAFF_DAILY_TIME_TABLES);
					else
				map.put(MyRequestAction.COMMAND,
						StaffDailyTimetableCommand.GET_STAFF_DAILY_TIME_TABLES_BY_SYSTEM_USER_PROFILE_SCHOOLS);

				
				NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

					@Override
					public void onNetworkResult(MyRequestResult result) {
						if (result != null) {
							SystemResponseDTO<List<StaffDailyTimeTableDTO>> responseDTO = result.getStaffDailyTimeTableResponseList();
							if (responseDTO.isStatus()) {
								if(responseDTO.getData() !=null) {
									getView().getStaffDailyTimetablePane().getStaffDailyAttendanceListGrid()
									.addRecordsToGrid(responseDTO.getData());
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
	
	
	
	private void getTimeTableLessonsForStaffAcademicYearTermDistrictSchoolDay2(
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
					FilterDTO dto = new FilterDTO();
					dto.setAcademicYearDTO(new AcademicYearDTO(academicYearId));
					dto.setAcademicTermDTO(new AcademicTermDTO(academicTermId));
					dto.setDistrictDTO(new DistrictDTO(districtId));
					dto.setSchoolDTO(new SchoolDTO(schoolId));
					dto.setSchoolStaffDTO(new SchoolStaffDTO(schoolStaffId));
					dto.setDay(dayFormat.format(new Date()));
					
					map.put(MyRequestAction.COMMAND , TimetableLessonCommands.GET_ALL_TIMETABLE_LESSONS);
					map.put(MyRequestAction.DATA , dto);

					NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

						@Override
						public void onNetworkResult(MyRequestResult result) {
							if (result != null) {
								SystemResponseDTO<List<TimeTableLessonDTO>> responseDTO = result.getTimeTableLessonResponseList();
								if (responseDTO.isStatus()) {
									if(responseDTO.getData() != null) {
										createStaffDailyTimetableLessonPane.getLessonListGrid().addRecordsToGrid(responseDTO.getData());

										if (responseDTO.getData().isEmpty())
											createStaffDailyTimetableLessonPane.getSaveButton().disable();
										else
											createStaffDailyTimetableLessonPane.getSaveButton().enable();
									}
								} else {
									SC.say(responseDTO.getMessage());
								}
							}
					
						}
					});

				} else {
					SC.say("Please Fill all the fields");
				}
			}
		});
	}
	
	
	
	private void saveStaffDailyTimetableLesson2(final CreateStaffDailyTimetableLessonPane createStaffDailyTimetableLessonPane) {
		createStaffDailyTimetableLessonPane.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				if (createStaffDailyTimetableLessonPane.getLessonListGrid().anySelected()) {
					final ComboBox termBox = createStaffDailyTimetableLessonPane.getAcademicTermCombo();
					final ComboBox schoolBox = createStaffDailyTimetableLessonPane.getSchoolCombo();
					final ComboBox districtBox = createStaffDailyTimetableLessonPane.getDistrictCombo();
					final ComboBox staffBox = createStaffDailyTimetableLessonPane.getSchoolStaffCombo();
					final TextItem dayItem = createStaffDailyTimetableLessonPane.getDayField();

					StaffDailyTimeTableDTO staffDailyTimeTableDTO = new StaffDailyTimeTableDTO();
					staffDailyTimeTableDTO.setAcademicTermDTO(new AcademicTermDTO(termBox.getValueAsString()));

					staffDailyTimeTableDTO.setCreatedDateTime(dateTimeFormat.format(new Date()));

					staffDailyTimeTableDTO.setLessonDate(dateFormat.format(new Date()));

					SchoolDTO schoolDto = new SchoolDTO(schoolBox.getValueAsString());
					schoolDto.setDistrictDTO(new DistrictDTO(districtBox.getValueAsString()));
					
					SchoolStaffDTO staffDto = new SchoolStaffDTO(staffBox.getValueAsString());
					staffDto.setSchoolDTO(schoolDto);
					
					staffDailyTimeTableDTO.setSchoolStaffDTO(staffDto);
					

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
					map.put(MyRequestAction.COMMAND, StaffDailyTimetableCommand.SAVE_STAFF_DAILY_TIME_TABLE);
					map.put(MyRequestAction.DATA, staffDailyTimeTableDTO);
					

					NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

						@Override
						public void onNetworkResult(MyRequestResult result) {
							if (result != null) {
								SystemResponseDTO<StaffDailyTimeTableDTO> responseDTO = result.getStaffDailyTimeTableResponse();
								if (responseDTO.isStatus()) {
									SC.say("Success" , responseDTO.getMessage());
								} else {
									SC.say(responseDTO.getMessage());
								}
							}
							
							
							
						}
					});

				} else {
					SC.say("Please select lessons");
				}

			}
		});

	}

	
	

}