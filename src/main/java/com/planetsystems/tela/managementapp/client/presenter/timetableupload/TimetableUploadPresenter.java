package com.planetsystems.tela.managementapp.client.presenter.timetableupload;

import java.util.ArrayList;
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
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.planetsystems.tela.dto.SchoolClassDTO;
import com.planetsystems.tela.dto.SchoolStaffDTO;
import com.planetsystems.tela.dto.SubjectDTO;
import com.planetsystems.tela.dto.TimeTableLessonDTO;
import com.planetsystems.tela.dto.enums.LessonDay;
import com.planetsystems.tela.managementapp.client.place.NameTokens;
import com.planetsystems.tela.managementapp.client.presenter.comboutils.ComboUtil;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkDataUtil;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkResult;
import com.planetsystems.tela.managementapp.shared.DatePattern;
import com.planetsystems.tela.managementapp.shared.RequestConstant;
import com.planetsystems.tela.managementapp.shared.RequestDelimeters;
import com.planetsystems.tela.managementapp.shared.RequestResult;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;

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

	interface MyView extends View {

		public TimetableUploadPane getTimetableUploadPane();
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
		loadTimetableTemplate();
	}

	private void loadTimetableTemplate() {

		loadRegionCombo(getView().getTimetableUploadPane(), null);
		loadDistrictCombo(getView().getTimetableUploadPane(), null);
		loadSchoolsInDistrictCombo(getView().getTimetableUploadPane(), null);
		loadAcademicYearCombo(getView().getTimetableUploadPane(), null);
		loadAcademicTermsInAcademicYearCombo(getView().getTimetableUploadPane(), null);

		getView().getTimetableUploadPane().getSchool().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {

				String schoolId = getView().getTimetableUploadPane().getSchool().getValueAsString();
				String academicTermId = getView().getTimetableUploadPane().getAcademicTerm().getValueAsString();

				loadTimetableLessonsData(schoolId, academicTermId);

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

	private void loadTimetableLessonsData(final String schoolId, final String academicTermId) {

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

				List<TimeTableLessonDTO> list = new ArrayList<>();

				for (int i = 0; i <= 30; i++) {
					TimeTableLessonDTO dto = new TimeTableLessonDTO();
					list.add(dto);
				}

				timetableUploadListgrid.addRecordsToGrid(list);

				getView().getTimetableUploadPane().getTimetableLayout().setMembers(timetableUploadListgrid);

			}
		});

	}
	
	

}