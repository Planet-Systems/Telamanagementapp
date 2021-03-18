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
import com.planetsystems.tela.dto.AcademicYearDTO;
import com.planetsystems.tela.dto.DistrictDTO;
import com.planetsystems.tela.dto.SchoolClassDTO;
import com.planetsystems.tela.dto.SchoolDTO;
import com.planetsystems.tela.dto.SchoolStaffDTO;
import com.planetsystems.tela.dto.StaffDailyAttendanceDTO;
import com.planetsystems.tela.dto.StaffDailyAttendanceTaskDTO;
import com.planetsystems.tela.dto.SubjectDTO;
import com.planetsystems.tela.dto.SystemFeedbackDTO;
import com.planetsystems.tela.dto.TimeTableLessonDTO;
import com.planetsystems.tela.managementapp.client.gin.SessionManager;
import com.planetsystems.tela.managementapp.client.place.NameTokens;
import com.planetsystems.tela.managementapp.client.presenter.main.MainPresenter;
import com.planetsystems.tela.managementapp.client.presenter.region.DistrictWindow;
import com.planetsystems.tela.managementapp.client.presenter.staffattendance.FilterClockOutWindow;
import com.planetsystems.tela.managementapp.client.presenter.timetable.LessonListGrid;
import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.planetsystems.tela.managementapp.client.widget.MenuButton;
import com.planetsystems.tela.managementapp.client.widget.SwizimaLoader;
import com.planetsystems.tela.managementapp.shared.DatePattern;
import com.planetsystems.tela.managementapp.shared.RequestAction;
import com.planetsystems.tela.managementapp.shared.RequestConstant;
import com.planetsystems.tela.managementapp.shared.RequestResult;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;

import com.smartgwt.client.widgets.grid.ListGridRecord;
import java_cup.internal_error;

public class StaffDailyTaskPresenter
		extends Presenter<StaffDailyTaskPresenter.MyView, StaffDailyTaskPresenter.MyProxy> {
	interface MyView extends View {
		public StaffDailyTaskPane getStaffDailyTaskPane();
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

	@NameToken(NameTokens.StaffDailyTask)
	@ProxyStandard
	interface MyProxy extends ProxyPlace<StaffDailyTaskPresenter> {
	}

	@Inject
	StaffDailyTaskPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy, MainPresenter.SLOT_Main);

	}

	@Override
	protected void onBind() {
		super.onBind();
		disableEnableLoadLessonButton();
		loadAcademicTermYearCombo();
		loadDistrictCombo();
		loadSchoolsInDistrictCombo();
		loadAcademicTermsInAcademicYearCombo();
		loadStaffsInSchoolCombo();
		getTimeTableLessonsForStaffAcademicYearTermDistrictSchoolDay();
		saveStaffDailyAttendanceTask();
	}

	private void getTimeTableLessonsForStaffAcademicYearTermDistrictSchoolDay() {
		getView().getStaffDailyTaskPane().getLoadLessonButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				if (checkIfNoFieldIsEmpty()) {
					String academicYearId = getView().getStaffDailyTaskPane().getAcademicYearCombo().getValueAsString();
					String academicTermId = getView().getStaffDailyTaskPane().getAcademicTermCombo().getValueAsString();
					String districtId = getView().getStaffDailyTaskPane().getDistrictCombo().getValueAsString();
					String schoolId = getView().getStaffDailyTaskPane().getSchoolCombo().getValueAsString();
					String schoolStaffId = getView().getStaffDailyTaskPane().getSchoolStaffCombo().getValueAsString();
					String day = getView().getStaffDailyTaskPane().getDayField().getValueAsString();

					LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					map.put(StaffDailyTaskPane.ACADEMIC_YEAR_ID, academicYearId);
					map.put(StaffDailyTaskPane.ACADEMIC_TERM_ID, academicTermId);
					map.put(StaffDailyTaskPane.DISTRICT_ID, districtId);
					map.put(StaffDailyTaskPane.SCHOOL_ID, schoolId);
					map.put(StaffDailyTaskPane.SCHOOL_STAFF_ID, schoolStaffId);
					map.put(StaffDailyTaskPane.DAY, day);

					map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());

					map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());

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

									if (result != null) {

										getView().getStaffDailyTaskPane().getLessonListGrid()
												.addRecordsToGrid(result.getTableLessonDTOs());

										if (result.getTableLessonDTOs().isEmpty())
											getView().getStaffDailyTaskPane().getSaveButton().disable();
										else
											getView().getStaffDailyTaskPane().getSaveButton().enable();

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

	private boolean checkIfNoFieldIsEmpty() {
		boolean flag = true;
		if (getView().getStaffDailyTaskPane().getDistrictCombo().getValueAsString() == null)
			flag = false;

		if (getView().getStaffDailyTaskPane().getSchoolCombo().getValueAsString() == null)
			flag = false;

		if (getView().getStaffDailyTaskPane().getAcademicYearCombo().getValueAsString() == null)
			flag = false;

		if (getView().getStaffDailyTaskPane().getAcademicTermCombo().getValueAsString() == null)
			flag = false;

		if (getView().getStaffDailyTaskPane().getSchoolStaffCombo().getValueAsString() == null)
			flag = false;

		if (getView().getStaffDailyTaskPane().getDayField().getValueAsString() == null)
			flag = false;

		return flag;
	}

	private void loadAcademicTermYearCombo() {

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.GET_ACADEMIC_YEAR, null);
		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());

		GWT.log("YEAR TOKEN " + SessionManager.getInstance().getLoginToken());
		GWT.log("YEAR TOKEN " + map.get(RequestConstant.LOGIN_TOKEN));

		SC.showPrompt("", "", new SwizimaLoader());

		dispatcher.execute(new RequestAction(RequestConstant.GET_ACADEMIC_YEAR, map),
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

						GWT.log("PRESENTER LIST " + result.getAcademicYearDTOs());
						if (result != null) {
							LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();

							for (AcademicYearDTO academicYearDTO : result.getAcademicYearDTOs()) {
								valueMap.put(academicYearDTO.getId(), academicYearDTO.getName());
							}

							getView().getStaffDailyTaskPane().getAcademicYearCombo().setValueMap(valueMap);

						} else {
							SC.warn("ERROR", "Service Down");
						}

					}
				});

	}

	private void loadAcademicTermsInAcademicYearCombo() {
		getView().getStaffDailyTaskPane().getAcademicYearCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				String academicYearId = getView().getStaffDailyTaskPane().getAcademicYearCombo().getValueAsString();
				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(RequestConstant.GET_ACADEMIC_TERMS_IN_ACADEMIC_YEAR, academicYearId);
				map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());

				SC.showPrompt("", "", new SwizimaLoader());

				dispatcher.execute(new RequestAction(RequestConstant.GET_ACADEMIC_TERMS_IN_ACADEMIC_YEAR, map),
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

									LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();

									for (AcademicTermDTO academicTermDTO : result.getAcademicTermDTOs()) {
										valueMap.put(academicTermDTO.getId(), academicTermDTO.getTerm());
									}
									getView().getStaffDailyTaskPane().getAcademicTermCombo().setValueMap(valueMap);

								} else {
									SC.warn("ERROR", "Unknow error");
								}

							}
						});
			}
		});

	}

	private void loadDistrictCombo() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.GET_DISTRICT, null);
		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
		SC.showPrompt("", "", new SwizimaLoader());
		dispatcher.execute(new RequestAction(RequestConstant.GET_DISTRICT, map), new AsyncCallback<RequestResult>() {

			@Override
			public void onFailure(Throwable caught) {
				System.out.println(caught.getMessage());
				SC.warn("ERROR", caught.getMessage());
				GWT

						.log("ERROR " + caught.getMessage());
				SC.clearPrompt();

			}

			@Override
			public void onSuccess(RequestResult result) {

				SC.clearPrompt();
				SessionManager.getInstance().manageSession(result, placeManager);
				if (result != null) {

					if (result.getSystemFeedbackDTO() != null) {
						LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();

						for (DistrictDTO districtDTO : result.getDistrictDTOs()) {
							valueMap.put(districtDTO.getId(), districtDTO.getName());
						}
						getView().getStaffDailyTaskPane().getDistrictCombo().setValueMap(valueMap);
					}
				} else {
					SC.warn("ERROR", "Unknow error");
				}

			}
		});
	}

	private void loadSchoolsInDistrictCombo() {

		getView().getStaffDailyTaskPane().getDistrictCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				String districtId = getView().getStaffDailyTaskPane().getDistrictCombo().getValueAsString();
				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(RequestConstant.GET_SCHOOLS_IN_DISTRICT, districtId);
				map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());

				SC.showPrompt("", "", new SwizimaLoader());

				dispatcher.execute(new RequestAction(RequestConstant.GET_SCHOOLS_IN_DISTRICT, map),
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

									LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();

									for (SchoolDTO schoolDTO : result.getSchoolDTOs()) {
										valueMap.put(schoolDTO.getId(), schoolDTO.getName());
									}

									getView().getStaffDailyTaskPane().getSchoolCombo().setValueMap(valueMap);

								} else {
									SC.warn("ERROR", "Unknow error");
								}

							}
						});
			}
		});

	}

	private void loadStaffsInSchoolCombo() {
		getView().getStaffDailyTaskPane().getSchoolCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				String schoolId = getView().getStaffDailyTaskPane().getSchoolCombo().getValueAsString();
				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(RequestConstant.GET_STAFFS_IN_SCHOOL, schoolId);
				map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());

				SC.showPrompt("", "", new SwizimaLoader());

				dispatcher.execute(new RequestAction(RequestConstant.GET_STAFFS_IN_SCHOOL, map),
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

									LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();

									for (SchoolStaffDTO schoolStaffDTO : result.getSchoolStaffDTOs()) {
										String fullName = schoolStaffDTO.getGeneralUserDetailDTO().getFirstName() + " "
												+ schoolStaffDTO.getGeneralUserDetailDTO().getLastName();
										valueMap.put(schoolStaffDTO.getId(), fullName);
									}
									getView().getStaffDailyTaskPane().getSchoolStaffCombo().setValueMap(valueMap);

								} else {
									SC.warn("ERROR", "Unknow error");
								}

							}
						});

			}
		});

	}

	private void disableEnableLoadLessonButton() {
		;
		final IButton button = getView().getStaffDailyTaskPane().getLoadLessonButton();
		final ComboBox termBox = getView().getStaffDailyTaskPane().getAcademicTermCombo();
		final ComboBox schoolBox = getView().getStaffDailyTaskPane().getSchoolCombo();
		final ComboBox staffBox = getView().getStaffDailyTaskPane().getSchoolStaffCombo();
		final TextItem dayItem = getView().getStaffDailyTaskPane().getDayField();

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

	private void saveStaffDailyAttendanceTask() {
		getView().getStaffDailyTaskPane().getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				if (getView().getStaffDailyTaskPane().getLessonListGrid().anySelected()) {
					final ComboBox termBox = getView().getStaffDailyTaskPane().getAcademicTermCombo();
					final ComboBox schoolBox = getView().getStaffDailyTaskPane().getSchoolCombo();
					final ComboBox staffBox = getView().getStaffDailyTaskPane().getSchoolStaffCombo();
					final TextItem dayItem = getView().getStaffDailyTaskPane().getDayField();

					StaffDailyAttendanceDTO staffDailyAttendanceDTO = new StaffDailyAttendanceDTO();
					staffDailyAttendanceDTO.setAcademicTermDTO(new AcademicTermDTO(termBox.getValueAsString()));

					// staffDailyAttendanceDTO.setComment(comment);
					staffDailyAttendanceDTO.setCreatedDateTime(dateTimeFormat.format(new Date()));
					// staffDailyAttendanceDTO.setUpdatedDateTime(updatedDateTime);
					// staffDailyAttendanceDTO.setId(id);
					staffDailyAttendanceDTO.setDailyAttendanceDate(dateFormat.format(new Date()));
					// staffDailyAttendanceDTO.setStatus(status);
					staffDailyAttendanceDTO.setSchoolStaffDTO(new SchoolStaffDTO(staffBox.getValueAsString()));

					ListGridRecord[] records = getView().getStaffDailyTaskPane().getLessonListGrid()
							.getSelectedRecords();
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

}