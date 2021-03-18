package com.planetsystems.tela.managementapp.client.presenter.timetable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
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
import com.planetsystems.tela.dto.GeneralUserDetailDTO;
import com.planetsystems.tela.dto.SchoolClassDTO;
import com.planetsystems.tela.dto.SchoolDTO;
import com.planetsystems.tela.dto.SchoolStaffDTO;
import com.planetsystems.tela.dto.SubjectDTO;
import com.planetsystems.tela.dto.SystemFeedbackDTO;
import com.planetsystems.tela.dto.TimeTableDTO;
import com.planetsystems.tela.dto.TimeTableLessonDTO;
import com.planetsystems.tela.managementapp.client.gin.SessionManager;
import com.planetsystems.tela.managementapp.client.place.NameTokens;
import com.planetsystems.tela.managementapp.client.presenter.comboutils.ComboUtil;
import com.planetsystems.tela.managementapp.client.presenter.main.MainPresenter;
import com.planetsystems.tela.managementapp.client.presenter.region.RegionPane;
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
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;

public class TimeTablePresenter extends Presenter<TimeTablePresenter.MyView, TimeTablePresenter.MyProxy> {
	interface MyView extends View {
		ControlsPane getControlsPane();

		TimetablePane getTimetablePane();

		TabSet getTimeTableTabset();

		VLayout getBodyLayout();
	}

	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_TimeTable = new Type<RevealContentHandler<?>>();

	@Inject
	private DispatchAsync dispatcher;

	@Inject
	private PlaceManager placeManager;

	DateTimeFormat dateTimeFormat = DateTimeFormat
			.getFormat(DatePattern.DAY_MONTH_YEAR_HOUR_MINUTE_SECONDS.getPattern());
	DateTimeFormat dateFormat = DateTimeFormat.getFormat(DatePattern.DAY_MONTH_YEAR.getPattern());
	DateTimeFormat timeFormat = DateTimeFormat.getFormat(DatePattern.HOUR_MINUTE_SECONDS.getPattern());

	private ComboUtil comboUtil;
	
	@NameToken(NameTokens.timeTable)
	@ProxyCodeSplit
	interface MyProxy extends ProxyPlace<TimeTablePresenter> {
	}

	@Inject
	TimeTablePresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy, MainPresenter.SLOT_Main);
		this.comboUtil = new ComboUtil();

	}

	@Override
	protected void onBind() {
		super.onBind();
		onTabSelected();
		getAllTimeTables();
	}

	private void onTabSelected() {
		getView().getTimeTableTabset().addTabSelectedHandler(new TabSelectedHandler() {

			@Override
			public void onTabSelected(TabSelectedEvent event) {

				String selectedTab = event.getTab().getTitle();

				if (selectedTab.equalsIgnoreCase(TimeTableView.TIME_TABLE_TAB)) {

					MenuButton newButton = new MenuButton("New");
					MenuButton view = new MenuButton("View");
					MenuButton delete = new MenuButton("Delete");
					MenuButton fiter = new MenuButton("Filter");

					List<MenuButton> buttons = new ArrayList<>();
					buttons.add(newButton);
					buttons.add(view);
					buttons.add(delete);
					buttons.add(fiter);

					getView().getControlsPane().addMenuButtons(buttons);
					addTimeTablePane(newButton);
					viewTimeTable(view);

				} else if (selectedTab.equalsIgnoreCase(TimeTableView.TIME_TABLE_LESSON_TAB)) {

				} else {
					List<MenuButton> buttons = new ArrayList<>();
					getView().getControlsPane().addMenuButtons(buttons);
				}

			}

		});
	}

	private void viewTimeTable(MenuButton view) {
		view.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (getView().getTimetablePane().getTimeTableListGrid().anySelected()) {
					ListGridRecord record = getView().getTimetablePane().getTimeTableListGrid().getSelectedRecord();
					
					ViewTimeTableLessonsPane viewTimeTableLessonsPane = new ViewTimeTableLessonsPane();
					viewTimeTableLessonsPane.getSchool().setContents(record.getAttribute(TimeTableListGrid.SCHOOL));
					viewTimeTableLessonsPane.getDistrict().setContents(record.getAttribute(TimeTableListGrid.DISTRICT));
					
					viewTimeTableLessonsPane.getAcademicTerm().setContents(record.getAttribute(TimeTableListGrid.ACADEMIC_TERM));
					viewTimeTableLessonsPane.getAcademicYear().setContents(record.getAttribute(TimeTableListGrid.ACADEMIC_YEAR));
					
	               getView().getBodyLayout().setMembers(viewTimeTableLessonsPane);
	               getTimeTableLessonsByTimeTable(viewTimeTableLessonsPane , record.getAttribute(TimeTableListGrid.ID));
				} else {
					SC.warn("Selected TimeTable to view");
				}

			}

		});

	}

	private void getTimeTableLessonsByTimeTable(final ViewTimeTableLessonsPane viewTimeTableLessonsPane , String timeTableId) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestDelimeters.TIME_TABLE_ID, timeTableId);
		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());

		SC.showPrompt("", "", new SwizimaLoader());

		dispatcher.execute(new RequestAction(RequestConstant.GET_TIME_TABLE_LESSONS_BY_TIME_TABLE, map), new AsyncCallback<RequestResult>() {
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
						viewTimeTableLessonsPane.getLessonListGrid().addRecordsToGrid(result.getTableLessonDTOs());
					} else {
						SC.warn("INFO", feedbackDTO.getMessage());
					}

				} else {
					SC.warn("ERROR", "Unknow error");
				}

			}
		});
	}
	
	
	private void addTimeTablePane(MenuButton newButton) {
		newButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// add create Lesson tab
				final CreateTimeTablePane createTimeTablePane = new CreateTimeTablePane();
				final Tab createLessonTab = new Tab("create lesson");
				createLessonTab.setCanClose(true);
				loadAcademicYearCombo(createTimeTablePane, null);
				loadAcademicTermsInAcademicYearCombo(createTimeTablePane, null);
				loadDistrictCombo(createTimeTablePane, null);
				loadSchoolsInDistrictCombo(createTimeTablePane, null);
				createLessonTab.setPane(createTimeTablePane);
				activateAddLessonButton(createTimeTablePane);

				getView().getTimeTableTabset().addTab(createLessonTab);
				getView().getTimeTableTabset().selectTab(createLessonTab);

				displayTimeTableLessonWindow(createTimeTablePane);

				createTimeTablePane.getCancelButton().addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						getView().getTimeTableTabset().removeTab(createLessonTab);
					}
				});

				createTimeTablePane.getSaveButton().addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						TimeTableDTO timeTableDTO = new TimeTableDTO();
						timeTableDTO.setCreatedDateTime(dateTimeFormat.format(new Date()));

						String schoolId = createTimeTablePane.getSchoolComboBox().getValueAsString();
						SchoolDTO schoolDTO = new SchoolDTO(schoolId);
						timeTableDTO.setSchoolDTO(schoolDTO);

						String academicId = createTimeTablePane.getAcademicTermComboBox().getValueAsString();
						AcademicTermDTO academicTermDTO = new AcademicTermDTO(academicId);
						timeTableDTO.setAcademicTermDTO(academicTermDTO);

						ListGridRecord[] records = createTimeTablePane.getLessonListGrid().getRecords(); // new
																											// ListGridRecord[list.size()];
						List<TimeTableLessonDTO> tableLessonDTOs = new ArrayList<TimeTableLessonDTO>();

						for (int i = 0; i < records.length; i++) {
							ListGridRecord record = records[i];
							TimeTableLessonDTO lessonDTO = new TimeTableLessonDTO();
							lessonDTO.setDay(record.getAttribute(LessonListGrid.DAY));
							lessonDTO.setStartTime(record.getAttribute(LessonListGrid.START_TIME));
							lessonDTO.setEndTime(record.getAttribute(LessonListGrid.END_TIME));

							SchoolClassDTO schoolClassDTO = new SchoolClassDTO(
									record.getAttribute(LessonListGrid.CLASS_ID));
							lessonDTO.setSchoolClassDTO(schoolClassDTO);

							SchoolStaffDTO schoolStaffDTO = new SchoolStaffDTO(
									record.getAttribute(LessonListGrid.STAFF_ID));
							lessonDTO.setSchoolStaffDTO(schoolStaffDTO);

							SubjectDTO subjectDTO = new SubjectDTO(record.getAttribute(LessonListGrid.SUBJECT_ID));
							lessonDTO.setSubjectDTO(subjectDTO);

							// (record.getAttribute(LessonListGrid.DAY));
							tableLessonDTOs.add(lessonDTO);
							GWT.log("RECORD " + record);
						}

						timeTableDTO.setTimeTableLessonDTOS(tableLessonDTOs);

						GWT.log("RECORD " + timeTableDTO);

						saveTimeTable(timeTableDTO, createLessonTab, null);

					}

				});

			}

		});
	}

	private void saveTimeTable(final TimeTableDTO timeTableDTO, final Tab lessonTab, final String defaultValue) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.SAVE_TIME_TABLE, timeTableDTO);
		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());

		SC.showPrompt("", "", new SwizimaLoader());

		dispatcher.execute(new RequestAction(RequestConstant.SAVE_TIME_TABLE, map), new AsyncCallback<RequestResult>() {
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
						getView().getTimeTableTabset().removeTab(lessonTab);
						getView().getTimeTableTabset().selectTab(0);
						
						getAllTimeTables();
					} else {
						SC.warn("INFO", feedbackDTO.getMessage());
					}

				} else {
					SC.warn("ERROR", "Unknow error");
				}

			}
		});

	}

	private void displayTimeTableLessonWindow(final CreateTimeTablePane createTimeTablePane) {
		createTimeTablePane.getAddLessonButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				TimeTableLessonWindow window = new TimeTableLessonWindow();
				loadDayCombo(window);

				
				loadClassesInSchoolCombo(window, createTimeTablePane.getSchoolComboBox() , null);
				loadStaffsInSchoolCombo(window, createTimeTablePane.getSchoolComboBox() , null);
				loadSubjectsCombo(window, null);
				window.show();
				addLessonRecord(window, createTimeTablePane);

			}


		
		});

	}

	private void addLessonRecord(final TimeTableLessonWindow window, final CreateTimeTablePane createTimeTablePane) {
		window.getAddRecordButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				TimeTableLessonDTO dto = new TimeTableLessonDTO();
				// dto.setId(id);
				dto.setCreatedDateTime(dateTimeFormat.format(new Date()));
				dto.setDay(window.getDayCombo().getValueAsString());
				dto.setEndTime(window.getEndTime().getEnteredValue());
				dto.setStartTime(window.getStartTime().getEnteredValue());
//				   dto.setStatus(status);

				SchoolClassDTO schoolClassDTO = new SchoolClassDTO(window.getSchoolClassCombo().getValueAsString());
				schoolClassDTO.setName(window.getSchoolClassCombo().getDisplayValue());
				dto.setSchoolClassDTO(schoolClassDTO);

				SchoolStaffDTO schoolStaffDTO = new SchoolStaffDTO(window.getStaffCombo().getValueAsString());
				if (window.getStaffCombo().getDisplayValue() != null) {
					String[] names = window.getStaffCombo().getDisplayValue().split(" ");
					GeneralUserDetailDTO userDetailDTO = new GeneralUserDetailDTO();
					userDetailDTO.setFirstName(names[0]);
					userDetailDTO.setLastName(names[1]);
					schoolStaffDTO.setGeneralUserDetailDTO(userDetailDTO);
				}

				dto.setSchoolStaffDTO(schoolStaffDTO);

				SubjectDTO subjectDTO = new SubjectDTO(window.getSubjectCombo().getValueAsString());
				subjectDTO.setName(window.getSubjectCombo().getDisplayValue());

				dto.setSubjectDTO(subjectDTO);

				GWT.log("DTO " + dto);

				if (checkIfNoTimeTableLessonFieldIsEmpty(window)) {
					createTimeTablePane.getLessonListGrid().addRecordToGrid(dto);
					clearTimeTableLessonWindow(window);
					createTimeTablePane.getSaveButton().enable();
				} else {
					SC.say("Please fill fields");
				}

			}

		});

	}

	private boolean checkIfNoTimeTableLessonFieldIsEmpty(TimeTableLessonWindow window) {
		boolean flag = true;

		if (window.getSchoolClassCombo().getValueAsString() == null)
			flag = false;

		if (window.getSchoolClassCombo().getValueAsString() == null)
			flag = false;
		if (window.getSubjectCombo().getValueAsString() == null)
			flag = false;
		if (window.getStaffCombo().getValueAsString() == null)
			flag = false;
		if (window.getDayCombo().getValueAsString() == null)
			flag = false;
		if (window.getStartTime().getEnteredValue() == null)
			flag = false;
		if (window.getEndTime().getEnteredValue() == null)
			flag = false;

		return flag;
	}

	private void clearTimeTableLessonWindow(TimeTableLessonWindow window) {
		window.getSchoolClassCombo().clearValue();
		window.getSubjectCombo().clearValue();
		window.getStaffCombo().clearValue();
		window.getStartTime().clearValue();
		window.getEndTime().clearValue();
		window.getDayCombo().clearValue();
	}

	private void loadDayCombo(TimeTableLessonWindow window) {
		Map<String, String> daysMap = new HashMap<String, String>();
		daysMap.put("Monday", "Monday");
		daysMap.put("Tuesday", "Tuesday");
		daysMap.put("Wednesday", "Wednesday");
		daysMap.put("Thursday", "Thursday");
		daysMap.put("Friday", "Friday");

		window.getDayCombo().setValueMap(daysMap);
	}

	private void loadDistrictCombo(final CreateTimeTablePane createTimeTablePane, final String defaultValue) {
		comboUtil.loadDistrictCombo(createTimeTablePane.getDistrictComboBox() , dispatcher, placeManager, defaultValue);
	}

	private void loadSchoolsInDistrictCombo(final CreateTimeTablePane createTimeTablePane, final String defaultValue) {

		createTimeTablePane.getDistrictComboBox().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				
				comboUtil.loadSchoolComboByDistrict(createTimeTablePane.getDistrictComboBox(), createTimeTablePane.getSchoolComboBox() , dispatcher, placeManager, defaultValue);
			}
		});

	}

	public void loadAcademicYearCombo(final CreateTimeTablePane createTimeTablePane, final String defaultValue) {
		comboUtil.loadAcademicYearCombo(createTimeTablePane.getAcademicYearComboBox() , dispatcher, placeManager, defaultValue);
	}

	private void loadAcademicTermsInAcademicYearCombo(final CreateTimeTablePane createTimeTablePane,
			final String defaultValue) {
		createTimeTablePane.getAcademicYearComboBox().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				comboUtil.loadAcademicTermComboByAcademicYear(createTimeTablePane.getAcademicYearComboBox(), createTimeTablePane.getAcademicTermComboBox(), dispatcher, placeManager, defaultValue);
			}
		});

	}
	

	private void loadClassesInSchoolCombo(final TimeTableLessonWindow window, final ComboBox schoolComboBox,
			final String defaultValue) {
	comboUtil.loadSchoolClassesComboBySchool(schoolComboBox, window.getSchoolClassCombo(), dispatcher, placeManager, defaultValue);
	}

	private void loadStaffsInSchoolCombo(TimeTableLessonWindow window, ComboBox schoolComboBox, 
			final String defaultValue) {

		comboUtil.loadSchoolStaffComboBySchool(schoolComboBox , window.getStaffCombo(), dispatcher, placeManager , defaultValue);
	}

	private void loadSubjectsCombo(final TimeTableLessonWindow window, final String defaultValue) {
		comboUtil.loadSubjectCombo(window.getSubjectCombo() , dispatcher, placeManager, defaultValue);
	}

	private void getAllTimeTables() {

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.GET_TIME_TABLES, null);
		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
		SC.showPrompt("", "", new SwizimaLoader());

		dispatcher.execute(new RequestAction(RequestConstant.GET_TIME_TABLES, map), new AsyncCallback<RequestResult>() {

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
							// SC.say("SUCCESS", result.getSystemFeedbackDTO().getMessage());
							getView().getTimetablePane().getTimeTableListGrid()
									.addRecordsToGrid(result.getTimeTableDTOs());

						} else {
							SC.warn("Not Successful \n ERROR:", feedbackDTO.getMessage());
						}
					}
				} else {
					SC.warn("ERROR", "Service Down");
					// SC.warn("ERROR", "Unknow error");
				}

			}

		});
	}

	public void activateAddLessonButton(final CreateTimeTablePane createTimeTablePane) {
		final boolean[] flag = new boolean[1];
		flag[0] = true;
		createTimeTablePane.getAcademicTermComboBox().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				if (createTimeTablePane.getAcademicTermComboBox().getValueAsString() == null
						|| createTimeTablePane.getSchoolComboBox().getValueAsString() == null) {
					createTimeTablePane.getAddLessonButton().disable();
				} else {
					createTimeTablePane.getAddLessonButton().enable();
				}
				flag[0] = false;

			}
		});

		createTimeTablePane.getSchoolComboBox().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				if (createTimeTablePane.getSchoolComboBox().getValueAsString() == null
						|| createTimeTablePane.getAcademicTermComboBox().getValueAsString() == null) {
					createTimeTablePane.getAddLessonButton().disable();
				} else {
					createTimeTablePane.getAddLessonButton().enable();
				}
				flag[0] = false;

			}
		});
	}

}