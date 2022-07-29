package com.planetsystems.tela.managementapp.client.presenter.timetableupload;

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
import com.planetsystems.tela.dto.SchoolClassDTO;
import com.planetsystems.tela.dto.SchoolDTO;
import com.planetsystems.tela.dto.SchoolStaffDTO;
import com.planetsystems.tela.dto.SubjectDTO;
import com.planetsystems.tela.dto.TimeTableDTO;
import com.planetsystems.tela.dto.TimeTableLessonDTO;
import com.planetsystems.tela.dto.enums.LessonDay;
import com.planetsystems.tela.managementapp.client.gin.SessionManager;
import com.planetsystems.tela.managementapp.client.place.NameTokens;
import com.planetsystems.tela.managementapp.client.presenter.comboutils.ComboUtil;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkDataUtil;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkResult;
import com.planetsystems.tela.managementapp.shared.DatePattern;
import com.planetsystems.tela.managementapp.shared.RequestConstant;
import com.planetsystems.tela.managementapp.shared.RequestDelimeters;
import com.planetsystems.tela.managementapp.shared.RequestResult;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.VLayout;

public class TimetableUploadPresenter
		extends Presenter<TimetableUploadPresenter.MyView, TimetableUploadPresenter.MyProxy> {

	@Inject
	private DispatchAsync dispatcher;

	@Inject
	private PlaceManager placeManager;

	DateTimeFormat dateTimeFormat = DateTimeFormat
			.getFormat(DatePattern.DAY_MONTH_YEAR_HOUR_MINUTE_SECONDS.getPattern());
	DateTimeFormat dateFormat = DateTimeFormat.getFormat(DatePattern.DAY_MONTH_YEAR.getPattern());

	DateTimeFormat timeFormat = DateTimeFormat.getFormat(DatePattern.HOUR_MINUTE_SECONDS.getPattern());

	DateTimeFormat timeFormat2 = DateTimeFormat.getFormat("HH:mm ss");

	interface MyView extends View {

		// public TimetableUploadPane getTimetableUploadPane();

		public TimetableUploadHomePane getTimetableUploadHomePane();

		public VLayout getMainLayout();
	}

	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_TimetableUpload = new Type<RevealContentHandler<?>>();

	@NameToken(NameTokens.timetableupload)
	@ProxyCodeSplit
	interface MyProxy extends ProxyPlace<TimetableUploadPresenter> {
	}

	@Inject
	TimetableUploadPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy, RevealType.RootLayout);

	}

	protected void onBind() {
		super.onBind();

		loadTimetableUploadHomePage(getView().getTimetableUploadHomePane());

	}

	private void loadTimetableUploadHomePage(final TimetableUploadHomePane pane) {

		ComboUtil.loadRegionCombo(pane.getRegion(), dispatcher, placeManager, null);

		pane.getRegion().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {

				ComboUtil.loadDistrictComboByRegion(pane.getRegion(), pane.getDistrict(), dispatcher, placeManager,
						null);
			}
		});

		pane.getDistrict().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {

				ComboUtil.loadSchoolComboByDistrict(pane.getDistrict(), pane.getSchool(), dispatcher, placeManager,
						null);
			}
		});

		ComboUtil.loadAcademicYearCombo(pane.getAcademicYear(), dispatcher, placeManager, null);

		pane.getAcademicYear().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				ComboUtil.loadAcademicTermComboByAcademicYear(pane.getAcademicYear(), pane.getAcademicTerm(),
						dispatcher, placeManager, null);
			}
		});

		pane.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				String regionId = pane.getRegion().getValueAsString();
				String districtId = pane.getDistrict().getValueAsString();
				String schoolId = pane.getSchool().getValueAsString();
				String termId = pane.getAcademicTerm().getValueAsString();
				String yearId = pane.getAcademicYear().getValueAsString();

				TimetableUploadPane timetableUploadPane = new TimetableUploadPane();
				loadTimetableTemplate(timetableUploadPane, regionId, districtId, schoolId, termId, yearId);
				accessLogin(timetableUploadPane);

				getView().getMainLayout().setMembers(timetableUploadPane);
			}
		});

		getView().getMainLayout().setMembers(pane);

	}

	private void loadTimetableTemplate(final TimetableUploadPane timetableUploadPane, final String regionId,
			final String districtId, final String schoolId, final String termId, final String yearId) {

		loadAcademicYearCombo(timetableUploadPane, yearId);
		loadAcademicTermsInAcademicYearCombo(timetableUploadPane, termId);
		loadRegionCombo(timetableUploadPane, regionId);
		loadDistrictCombo(timetableUploadPane, districtId);
		loadSchoolsInDistrictCombo(timetableUploadPane, schoolId);

		timetableUploadPane.getSchool().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {

				// String schoolId = timetableUploadPane.getSchool().getValueAsString();
				// String academicTermId =
				// timetableUploadPane.getAcademicTerm().getValueAsString();

				loadTimetableLessonsData(timetableUploadPane, schoolId, termId);

			}
		});

	}

	private void loadRegionCombo(final TimetableUploadPane pane, final String defaultValue) {

		ComboUtil.loadRegionCombo(pane.getRegion(), dispatcher, placeManager, defaultValue);
	}

	private void loadDistrictCombo(final TimetableUploadPane pane, final String defaultValue) {
		pane.getRegion().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {

				ComboUtil.loadDistrictComboByRegion(pane.getRegion(), pane.getDistrict(), dispatcher, placeManager,
						defaultValue);
			}
		});

	}

	private void loadSchoolsInDistrictCombo(final TimetableUploadPane pane, final String defaultValue) {

		pane.getDistrict().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {

				ComboUtil.loadSchoolComboByDistrict(pane.getDistrict(), pane.getSchool(), dispatcher, placeManager,
						defaultValue);
			}
		});

	}

	public void loadAcademicYearCombo(final TimetableUploadPane pane, final String defaultValue) {
		ComboUtil.loadAcademicYearCombo(pane.getAcademicYear(), dispatcher, placeManager, defaultValue);
	}

	private void loadAcademicTermsInAcademicYearCombo(final TimetableUploadPane pane, final String defaultValue) {
		pane.getAcademicYear().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				ComboUtil.loadAcademicTermComboByAcademicYear(pane.getAcademicYear(), pane.getAcademicTerm(),
						dispatcher, placeManager, defaultValue);
			}
		});

	}

	private void loadTimetableLessonsData(final TimetableUploadPane timetableUploadPane, final String schoolId,
			final String academicTermId) {

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestDelimeters.SCHOOL_ID, schoolId);
		map.put(RequestDelimeters.ACADEMIC_TERM_ID, academicTermId);

		map.put(NetworkDataUtil.ACTION, RequestConstant.LOAD_TIMETABLE_LESSONS_DATA);

		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {

				GWT.log("LOAD_TIMETABLE_LESSONS_DATA");

				List<SchoolClassDTO> clazes = new ArrayList<>();
				for (SchoolClassDTO dto : result.getSchoolClassDTOs()) {
					clazes.add(dto);
				}

				List<SubjectDTO> subjects = new ArrayList<>();
				for (SubjectDTO dto : result.getSubjectDTOs()) {
					subjects.add(dto);
				}

				List<SchoolStaffDTO> staffList = new ArrayList<>();
				for (SchoolStaffDTO dto : result.getSchoolStaffDTOs()) {
					staffList.add(dto);
				}

				List<String> days = new ArrayList<>();
				for (LessonDay lessonDay : LessonDay.values()) {
					days.add(lessonDay.getDay());
				}

				TimetableUploadListgrid timetableUploadListgrid = new TimetableUploadListgrid(days, clazes, subjects,
						staffList);

				List<TimeTableLessonDTO> existingLessons = result.getTableLessonDTOs();

				List<TimeTableLessonDTO> list = new ArrayList<>();

				for (TimeTableLessonDTO dto : existingLessons) {
					list.add(dto);
				}

				for (int i = 0; i <= 200; i++) {
					TimeTableLessonDTO dto = new TimeTableLessonDTO();
					list.add(dto);
				}

				timetableUploadListgrid.addRecordsToGrid(list);

				submitTimetable(timetableUploadPane, timetableUploadListgrid);
				timetableUploadPane.getTimetableLayout().setMembers(timetableUploadListgrid);

			}
		});

	}

	private void submitTimetable(final TimetableUploadPane timetableUploadPane,
			final TimetableUploadListgrid timetableUploadListgrid) {
		timetableUploadPane.getSaveButton().addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {

				TimeTableDTO timeTableDTO = new TimeTableDTO();
				timeTableDTO.setCreatedDateTime(dateTimeFormat.format(new Date()));

				String schoolId = timetableUploadPane.getSchool().getValueAsString();
				SchoolDTO schoolDTO = new SchoolDTO(schoolId);
				timeTableDTO.setSchoolDTO(schoolDTO);

				String academicId = timetableUploadPane.getAcademicTerm().getValueAsString();
				AcademicTermDTO academicTermDTO = new AcademicTermDTO(academicId);

				timeTableDTO.setAcademicTermDTO(academicTermDTO);

				ListGridRecord[] records = timetableUploadListgrid.getRecords();

				List<TimeTableLessonDTO> tableLessonDTOs = new ArrayList<TimeTableLessonDTO>();

				for (ListGridRecord record : records) {

					if (record.getAttribute(TimetableUploadListgrid.LESSON_DAY) != null) {

						TimeTableLessonDTO lessonDTO = new TimeTableLessonDTO();

						if (record.getAttribute(TimetableUploadListgrid.ID) != null) {
							lessonDTO.setId(record.getAttribute(TimetableUploadListgrid.ID));
						}

						String startTime = timeFormat2
								.format(record.getAttributeAsDate(TimetableUploadListgrid.START_TIME));
						String endTime = timeFormat2
								.format(record.getAttributeAsDate(TimetableUploadListgrid.END_TIME));

						lessonDTO.setLessonDay(record.getAttribute(TimetableUploadListgrid.LESSON_DAY));
						lessonDTO.setStartTime(startTime.replace(" 00", ""));
						lessonDTO.setEndTime(endTime.replace(" 00", ""));

						SchoolClassDTO schoolClassDTO = new SchoolClassDTO(
								record.getAttribute(TimetableUploadListgrid.CLASS_ID));
						lessonDTO.setSchoolClassDTO(schoolClassDTO);

						SchoolStaffDTO schoolStaffDTO = new SchoolStaffDTO(
								record.getAttribute(TimetableUploadListgrid.STAFF_ID));

						lessonDTO.setSchoolStaffDTO(schoolStaffDTO);

						SubjectDTO subjectDTO = new SubjectDTO(record.getAttribute(TimetableUploadListgrid.SUBJECT_ID));
						lessonDTO.setSubjectDTO(subjectDTO);

						tableLessonDTOs.add(lessonDTO);
						GWT.log("RECORD " + record);
					}

				}

				timeTableDTO.setTimeTableLessonDTOS(tableLessonDTOs);

				saveTimeTable(timeTableDTO, timetableUploadListgrid);

			}
		});
	}

	private void saveTimeTable(final TimeTableDTO timeTableDTO, final TimetableUploadListgrid timetableUploadListgrid) {

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.SUBMIT_TIME_TABLE, timeTableDTO);
		map.put(NetworkDataUtil.ACTION, RequestConstant.SUBMIT_TIME_TABLE);

		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {

				if (result != null) {
					if (result.getSystemFeedbackDTO() != null) {
						if (result.getSystemFeedbackDTO().isResponse()) {
							SC.say("Success", "Your timetable has been saved successfully", new BooleanCallback() {

								@Override
								public void execute(Boolean value) {

									final String schoolId = timeTableDTO.getSchoolDTO().getId();
									final String academicTermId = timeTableDTO.getAcademicTermDTO().getId();

									loadLessons(schoolId, academicTermId, timetableUploadListgrid);

								}
							});
						} else {
							SC.warn("ERROR", result.getSystemFeedbackDTO().getMessage());
						}
					}
				}

			}
		});
	}

	private void loadLessons(final String schoolId, final String academicTermId,
			final TimetableUploadListgrid timetableUploadListgrid) {

		GWT.log("loadLessons: loadLessons loadLessons");

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestDelimeters.SCHOOL_ID, schoolId);
		map.put(RequestDelimeters.ACADEMIC_TERM_ID, academicTermId);

		map.put(NetworkDataUtil.ACTION, RequestConstant.LOAD_TIMETABLE_LESSONS);

		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {

				List<TimeTableLessonDTO> list = new ArrayList<>();

				for (TimeTableLessonDTO dto : result.getTableLessonDTOs()) {
					list.add(dto);
				}

				for (int i = 0; i <= 200; i++) {
					TimeTableLessonDTO dto = new TimeTableLessonDTO();
					list.add(dto);
				}

				timetableUploadListgrid.addRecordsToGrid(list);

			}
		});
	}

	private void accessLogin(final TimetableUploadPane timetableUploadPane) {
		timetableUploadPane.getBackButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				TimetableUploadHomePane pane = new TimetableUploadHomePane();
				loadTimetableUploadHomePage(pane);

				// SessionManager.getInstance().accessLogin(placeManager);

			}
		});
	}

}