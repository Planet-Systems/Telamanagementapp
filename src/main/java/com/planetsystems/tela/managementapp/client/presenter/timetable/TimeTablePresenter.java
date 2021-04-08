package com.planetsystems.tela.managementapp.client.presenter.timetable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import com.planetsystems.tela.dto.GeneralUserDetailDTO;
import com.planetsystems.tela.dto.SchoolClassDTO;
import com.planetsystems.tela.dto.SchoolDTO;
import com.planetsystems.tela.dto.SchoolStaffDTO;
import com.planetsystems.tela.dto.SubjectDTO;
import com.planetsystems.tela.dto.TimeTableDTO;
import com.planetsystems.tela.dto.TimeTableLessonDTO;
import com.planetsystems.tela.managementapp.client.enums.LessonDay;
import com.planetsystems.tela.managementapp.client.place.NameTokens;
import com.planetsystems.tela.managementapp.client.presenter.comboutils.ComboUtil;
import com.planetsystems.tela.managementapp.client.presenter.main.MainPresenter;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkDataUtil;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkResult;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.planetsystems.tela.managementapp.client.widget.MenuButton;
import com.planetsystems.tela.managementapp.shared.DatePattern;
import com.planetsystems.tela.managementapp.shared.RequestConstant;
import com.planetsystems.tela.managementapp.shared.RequestDelimeters;
import com.planetsystems.tela.managementapp.shared.RequestResult;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;

public class TimeTablePresenter extends Presenter<TimeTablePresenter.MyView, TimeTablePresenter.MyProxy> {
	interface MyView extends View {
		ControlsPane getControlsPane();

		TimetablePane getTimetablePane();

		TabSet getTabSet();
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

	@NameToken(NameTokens.timeTable)
	@ProxyCodeSplit
	interface MyProxy extends ProxyPlace<TimeTablePresenter> {
	}

	@Inject
	TimeTablePresenter(EventBus eventBus, MyView view, MyProxy proxy) {
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

				if (selectedTab.equalsIgnoreCase(TimeTableView.TIME_TABLES_TAB)) {
					// close second tab
					getView().getTabSet().removeTab(1);

					MenuButton newButton = new MenuButton("New");
					MenuButton view = new MenuButton("View Lessons");
					MenuButton delete = new MenuButton("Delete");
					MenuButton fiter = new MenuButton("Filter");

					List<MenuButton> buttons = new ArrayList<>();
					buttons.add(newButton);
					buttons.add(view);
					buttons.add(delete);
					buttons.add(fiter);

					getView().getControlsPane().addMenuButtons(buttons);
					addTimeTablePane(newButton);
					viewTimeTableLessons(view);

					getAllTimeTables();

				} else {
					List<MenuButton> buttons = new ArrayList<>();
					getView().getControlsPane().addMenuButtons(buttons);
				}

			}

		});
	}

	private void viewTimeTableLessons(MenuButton view) {
		view.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (getView().getTimetablePane().getTimeTableListGrid().anySelected()) {					
					
					Tab tab = new Tab();
					tab.setTitle("Time Table Lessons");
					getView().getTabSet().removeTab(1);// remove before putting
					final ListGridRecord record = getView().getTimetablePane().getTimeTableListGrid().getSelectedRecord();
					final String timeTableId = record.getAttribute(TimeTableListGrid.ID);

					final ViewTimeTableLessonsPane viewTimeTableLessonsPane = new ViewTimeTableLessonsPane();
					viewTimeTableLessonsPane.getLessonListGrid().setShowFilterEditor(true);
					//set controls button
					MenuButton add = new MenuButton("Add");
					add.addClickHandler(new ClickHandler() {
						
						@Override
						public void onClick(ClickEvent event) {
							saveTimeTableLessonByTimeTable(viewTimeTableLessonsPane , record , null);
						}
					});
					MenuButton edit = new MenuButton("edit");
					MenuButton delete = new MenuButton("delete");
					delete.addClickHandler(new ClickHandler() {
						
						@Override
						public void onClick(ClickEvent event) {
							if(viewTimeTableLessonsPane.getLessonListGrid().anySelected()) {
								deleteTimeTableLesson(viewTimeTableLessonsPane , record , null);
							}else {
								SC.say("Select lesson to delete");
							}
						}

						
					});
					
					
                    viewTimeTableLessonsPane.getControlsPane().addMenuButtons(Arrays.asList(add , edit , delete));
                    

					
					
					viewTimeTableLessonsPane.getSchool().setContents(record.getAttribute(TimeTableListGrid.SCHOOL));
					viewTimeTableLessonsPane.getDistrict().setContents(record.getAttribute(TimeTableListGrid.DISTRICT));

					viewTimeTableLessonsPane.getAcademicTerm()
							.setContents(record.getAttribute(TimeTableListGrid.ACADEMIC_TERM));
					viewTimeTableLessonsPane.getAcademicYear()
							.setContents(record.getAttribute(TimeTableListGrid.ACADEMIC_YEAR));

					tab.setPane(viewTimeTableLessonsPane);

					getView().getTabSet().addTab(tab);
					getView().getTabSet().selectTab(tab);

					getTimeTableLessonsByTimeTable(viewTimeTableLessonsPane, timeTableId);

					viewTimeTableLessonsPane.getCloseTabButton().addClickHandler(new ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							getView().getTabSet().removeTab(1);
						}
					});

				} else {
					SC.warn("Selected TimeTable to view");
				}

			}


		});

	}
	
	
	private void deleteTimeTableLesson(final ViewTimeTableLessonsPane viewTimeTableLessonsPane, final ListGridRecord timeTableRecord , final String defaultValue) {
		final ListGridRecord lessonRecord = viewTimeTableLessonsPane.getLessonListGrid().getSelectedRecord();
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put(RequestDelimeters.TIME_TABLE_LESSON_ID, lessonRecord.getAttribute(LessonListGrid.ID));
		map.put(NetworkDataUtil.ACTION, RequestConstant.DELETE_TIME_TABLE_LESSON);
		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {
			
			@Override
			public void onNetworkResult(RequestResult result) {
				SC.say(result.getSystemFeedbackDTO().getMessage());
				getTimeTableLessonsByTimeTable(viewTimeTableLessonsPane , timeTableRecord.getAttribute(TimeTableListGrid.ID) );
			}
		});
		
	}
	
	private void saveTimeTableLessonByTimeTable(final ViewTimeTableLessonsPane viewTimeTableLessonsPane , final ListGridRecord timeTableRecord , final String defaultValue) {
		final TimeTableLessonWindow window = new TimeTableLessonWindow();
		 window.getAddRecordButton().setTitle("Save");
		 loadLessonDayCombo(window);

			LinkedHashMap<String, Object> map = new LinkedHashMap<>();
			map.put(RequestDelimeters.SCHOOL_ID, timeTableRecord.getAttribute(TimeTableListGrid.SCHOOL_ID));
			map.put(RequestDelimeters.ACADEMIC_TERM_ID, timeTableRecord.getAttribute(TimeTableListGrid.ACADEMIC_TERM_ID));
			map.put(NetworkDataUtil.ACTION, RequestConstant.GET_SCHOOL_CLASSES_IN_SCHOOL_ACADEMIC_TERM);
			
            NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {		
				@Override
				public void onNetworkResult(RequestResult result) {
					LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();

					for (SchoolClassDTO schoolClassDTO : result.getSchoolClassDTOs()) {
						valueMap.put(schoolClassDTO.getId(), schoolClassDTO.getName());
					}
					window.getSchoolClassCombo().setValueMap(valueMap);

					if (defaultValue != null) {
						window.getSchoolClassCombo().setValue(defaultValue);
					}
				}
			});
			
        	LinkedHashMap<String, Object> staffMap = new LinkedHashMap<>();
        	staffMap.put(RequestDelimeters.SCHOOL_ID, timeTableRecord.getAttribute(TimeTableListGrid.SCHOOL_ID));
        	staffMap.put(NetworkDataUtil.ACTION, RequestConstant.GET_STAFFS_IN_SCHOOL);
			
            NetworkDataUtil.callNetwork(dispatcher, placeManager, staffMap , new NetworkResult() {		
				@Override
				public void onNetworkResult(RequestResult result) {
					LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();

					for (SchoolStaffDTO schoolStaffDTO : result.getSchoolStaffDTOs()) {
						
						String fullName = schoolStaffDTO.getGeneralUserDetailDTO().getFirstName() + " "
								+ schoolStaffDTO.getGeneralUserDetailDTO().getLastName();
						valueMap.put(schoolStaffDTO.getId(), fullName);
					}
					window.getSchoolStaffCombo().setValueMap(valueMap);

					if (defaultValue != null) {
						window.getSchoolStaffCombo().setValueMap(valueMap);
					}
				}
			});
			ComboUtil.loadSubjectCombo(window.getSubjectCombo(), dispatcher, placeManager, defaultValue);
		 window.show();
		 
		 ///lesson dto
	     window.getAddRecordButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				 TimeTableLessonDTO lessonDTO = new TimeTableLessonDTO();
				 lessonDTO.setEndTime(timeFormat.format(window.getEndTime().getValueAsDate()));
				 lessonDTO.setStartTime(timeFormat.format(window.getStartTime().getValueAsDate()));
				 lessonDTO.setCreatedDateTime(dateTimeFormat.format(new Date()));
				 lessonDTO.setSchoolClassDTO(new SchoolClassDTO(window.getSchoolClassCombo().getValueAsString()));
				 lessonDTO.setSchoolStaffDTO(new SchoolStaffDTO(window.getSchoolStaffCombo().getValueAsString()));
				 lessonDTO.setLessonDay(window.getLessonDayCombo().getValueAsString());
				 lessonDTO.setSubjectDTO(new SubjectDTO(window.getSubjectCombo().getValueAsString()));
				 
				 LinkedHashMap<String, Object> saveMap = new LinkedHashMap<>();
				 saveMap.put(RequestDelimeters.TIME_TABLE_ID, timeTableRecord.getAttribute(TimeTableListGrid.ID));
				 saveMap.put(RequestConstant.SAVE_TIME_TABLE_LESSON_BY_TIME_TABLE , lessonDTO);
				 saveMap.put(NetworkDataUtil.ACTION, RequestConstant.SAVE_TIME_TABLE_LESSON_BY_TIME_TABLE);
				 NetworkDataUtil.callNetwork(dispatcher, placeManager, saveMap , new NetworkResult() {
					
					@Override
					public void onNetworkResult(RequestResult result) {
						SC.say(result.getSystemFeedbackDTO().getMessage());
						clearTimeTableLessonWindow(window);
						getTimeTableLessonsByTimeTable(viewTimeTableLessonsPane, timeTableRecord.getAttribute(TimeTableListGrid.ID));
					}
				});
			}
		});

	}

	private void getTimeTableLessonsByTimeTable(final ViewTimeTableLessonsPane viewTimeTableLessonsPane,
			String timeTableId) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestDelimeters.TIME_TABLE_ID, timeTableId);
		map.put(NetworkDataUtil.ACTION, RequestConstant.GET_TIME_TABLE_LESSONS_BY_TIME_TABLE);

		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {
				viewTimeTableLessonsPane.getLessonListGrid().addRecordsToGrid(result.getTableLessonDTOs());
				
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

				getView().getTabSet().addTab(createLessonTab);
				getView().getTabSet().selectTab(createLessonTab);

				displayTimeTableLessonWindow(createTimeTablePane);

				createTimeTablePane.getCancelButton().addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						getView().getTabSet().removeTab(createLessonTab);
					}
				});

				createTimeTablePane.getSaveButton().addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {

						if (createTimeTablePane.getLessonListGrid().anySelected()) {

							TimeTableDTO timeTableDTO = new TimeTableDTO();
							timeTableDTO.setCreatedDateTime(dateTimeFormat.format(new Date()));

							String schoolId = createTimeTablePane.getSchoolCombo().getValueAsString();
							SchoolDTO schoolDTO = new SchoolDTO(schoolId);
							timeTableDTO.setSchoolDTO(schoolDTO);

							String academicId = createTimeTablePane.getAcademicTermCombo().getValueAsString();
							AcademicTermDTO academicTermDTO = new AcademicTermDTO(academicId);
							
							timeTableDTO.setAcademicTermDTO(academicTermDTO);

							ListGridRecord[] records = createTimeTablePane.getLessonListGrid().getSelectedRecords(); // new
							// ListGridRecord[list.size()];
							List<TimeTableLessonDTO> tableLessonDTOs = new ArrayList<TimeTableLessonDTO>();

							for (int i = 0; i < records.length; i++) {
								ListGridRecord record = records[i];
								TimeTableLessonDTO lessonDTO = new TimeTableLessonDTO();
								lessonDTO.setLessonDay(record.getAttribute(LessonListGrid.LESSON_DAY));
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

							//GWT.log("RECORD " + timeTableDTO);

						
							GWT.log("TIME TABLE "+timeTableDTO);
							GWT.log("term "+timeTableDTO.getAcademicTermDTO().getId());
							GWT.log("school "+timeTableDTO.getSchoolDTO().getId());
							GWT.log("subject "+timeTableDTO.getTimeTableLessonDTOS().get(0).getSubjectDTO().getId());
							GWT.log("class  "+timeTableDTO.getTimeTableLessonDTOS().get(0).getSchoolClassDTO().getId());
							GWT.log("staff  "+timeTableDTO.getTimeTableLessonDTOS().get(0).getSchoolStaffDTO().getId());
//						        System.out.println("timetable  "+timeTableDTO.getTimeTableLessonDTOS().get(0).getTimeTableDTO());
							saveTimeTable(timeTableDTO, null);

						} else {
							SC.say("Please Select lessons To Comfirm");
						}

					}

				});

			}

		});
	}

	private void saveTimeTable(final TimeTableDTO timeTableDTO, final String defaultValue) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.SAVE_TIME_TABLE, timeTableDTO);
		map.put(NetworkDataUtil.ACTION, RequestConstant.SAVE_TIME_TABLE);
		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {
			
			@Override
			public void onNetworkResult(RequestResult result) {
				getView().getTabSet().removeTab(1);
				getView().getTabSet().selectTab(0);
				getAllTimeTables();
			}
		});
	}

	private void displayTimeTableLessonWindow(final CreateTimeTablePane createTimeTablePane) {
		createTimeTablePane.getAddLessonButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				TimeTableLessonWindow window = new TimeTableLessonWindow();
				loadLessonDayCombo(window);
				String defaultValue = null;
				ComboUtil.loadSchoolClassesComboBySchoolAcademicTerm(createTimeTablePane.getAcademicTermCombo() ,createTimeTablePane.getSchoolCombo() , window.getSchoolClassCombo(), dispatcher, placeManager,
						defaultValue);
				
				ComboUtil.loadSchoolStaffComboBySchool(createTimeTablePane.getSchoolCombo(), window.getSchoolStaffCombo(), dispatcher, placeManager, defaultValue);
		
				ComboUtil.loadSubjectCombo(window.getSubjectCombo(), dispatcher, placeManager, defaultValue);
				
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
				dto.setLessonDay(window.getLessonDayCombo().getValueAsString());
				dto.setEndTime(window.getEndTime().getEnteredValue());
				dto.setStartTime(window.getStartTime().getEnteredValue());
//				   dto.setStatus(status);

				SchoolClassDTO schoolClassDTO = new SchoolClassDTO(window.getSchoolClassCombo().getValueAsString());
				schoolClassDTO.setName(window.getSchoolClassCombo().getDisplayValue());
				dto.setSchoolClassDTO(schoolClassDTO);

				SchoolStaffDTO schoolStaffDTO = new SchoolStaffDTO(window.getSchoolStaffCombo().getValueAsString());
				if (window.getSchoolStaffCombo().getDisplayValue() != null) {
					String[] names = window.getSchoolStaffCombo().getDisplayValue().split(" ");
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
				

				int id = 0;
				if (checkIfNoTimeTableLessonFieldIsEmpty(window)) {
					String id2 = String.valueOf(++id);
					dto.setId(id2);
					SC.say("ID "+id2);
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
		if (window.getSchoolStaffCombo().getValueAsString() == null)
			flag = false;
		if (window.getLessonDayCombo().getValueAsString() == null)
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
		window.getSchoolStaffCombo().clearValue();
		window.getStartTime().clearValue();
		window.getEndTime().clearValue();
		window.getLessonDayCombo().clearValue();
	}


	private void loadLessonDayCombo(TimeTableLessonWindow window) {
		Map<String, String> daysMap = new HashMap<String, String>();
	    for (LessonDay lessonDay : LessonDay.values()) {
			daysMap.put(lessonDay.getDay() , lessonDay.getDay());
		}

		window.getLessonDayCombo().setValueMap(daysMap);
	}

	private void loadDistrictCombo(final CreateTimeTablePane createTimeTablePane, final String defaultValue) {
		ComboUtil.loadDistrictCombo(createTimeTablePane.getDistrictCombo(), dispatcher, placeManager, defaultValue);
	}

	private void loadSchoolsInDistrictCombo(final CreateTimeTablePane createTimeTablePane, final String defaultValue) {

		createTimeTablePane.getDistrictCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {

				ComboUtil.loadSchoolComboByDistrict(createTimeTablePane.getDistrictCombo(),
						createTimeTablePane.getSchoolCombo(), dispatcher, placeManager, defaultValue);
			}
		});

	}

	public void loadAcademicYearCombo(final CreateTimeTablePane createTimeTablePane, final String defaultValue) {
		ComboUtil.loadAcademicYearCombo(createTimeTablePane.getAcademicYearCombo(), dispatcher, placeManager,
				defaultValue);
	}

	private void loadAcademicTermsInAcademicYearCombo(final CreateTimeTablePane createTimeTablePane,
			final String defaultValue) {
		createTimeTablePane.getAcademicYearCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				ComboUtil.loadAcademicTermComboByAcademicYear(createTimeTablePane.getAcademicYearCombo(),
						createTimeTablePane.getAcademicTermCombo(), dispatcher, placeManager, defaultValue);
			}
		});

	}


	private void getAllTimeTables() {

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.GET_TIME_TABLES, null);
		map.put(NetworkDataUtil.ACTION, RequestConstant.GET_TIME_TABLES);

		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {
				getView().getTimetablePane().getTimeTableListGrid().addRecordsToGrid(result.getTimeTableDTOs());
			}
		});
	}

	public void activateAddLessonButton(final CreateTimeTablePane createTimeTablePane) {
		final boolean[] flag = new boolean[1];
		flag[0] = true;
		createTimeTablePane.getAcademicTermCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				if (createTimeTablePane.getAcademicTermCombo().getValueAsString() == null
						|| createTimeTablePane.getSchoolCombo().getValueAsString() == null) {
					createTimeTablePane.getAddLessonButton().disable();
				} else {
					createTimeTablePane.getAddLessonButton().enable();
				}
				flag[0] = false;

			}
		});

		createTimeTablePane.getSchoolCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				if (createTimeTablePane.getSchoolCombo().getValueAsString() == null
						|| createTimeTablePane.getAcademicTermCombo().getValueAsString() == null) {
					createTimeTablePane.getAddLessonButton().disable();
				} else {
					createTimeTablePane.getAddLessonButton().enable();
				}
				flag[0] = false;

			}
		});
	}

}