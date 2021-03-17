package com.planetsystems.tela.managementapp.client.presenter.staffdailytask;

import java.util.ArrayList;
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
import com.planetsystems.tela.dto.SchoolDTO;
import com.planetsystems.tela.dto.SchoolStaffDTO;
import com.planetsystems.tela.dto.TimeTableLessonDTO;
import com.planetsystems.tela.managementapp.client.gin.SessionManager;
import com.planetsystems.tela.managementapp.client.place.NameTokens;
import com.planetsystems.tela.managementapp.client.presenter.main.MainPresenter;
import com.planetsystems.tela.managementapp.client.presenter.region.DistrictWindow;
import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.planetsystems.tela.managementapp.client.widget.MenuButton;
import com.planetsystems.tela.managementapp.client.widget.SwizimaLoader;
import com.planetsystems.tela.managementapp.shared.DatePattern;
import com.planetsystems.tela.managementapp.shared.RequestAction;
import com.planetsystems.tela.managementapp.shared.RequestConstant;
import com.planetsystems.tela.managementapp.shared.RequestResult;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;

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
		setControlsPaneButtons();
		loadAcademicTermYearCombo();
		loadDistrictCombo();
		loadSchoolsInDistrictCombo();
		loadAcademicTermsInAcademicYearCombo();
		loadStaffsInSchoolCombo();
	}

	private void setControlsPaneButtons() {
		MenuButton fetch = new MenuButton("Fetch");
		List<MenuButton> buttons = new ArrayList<>();
		buttons.add(fetch);

		// /academicyears/{year}/academicterms/{term}/districts/{district}/schools/{school}/"
		// +
		// "schoolstaffs/{staffId}/day/{day}/timetablelessons
		getView().getStaffDailyTaskPane().getControlsPane().addMenuButtons(buttons);
		getTimeTableLessonsForStaffAcademicYearTermDistrictSchoolDay(fetch);
	}

	private void getTimeTableLessonsForStaffAcademicYearTermDistrictSchoolDay(MenuButton fetch) {
		fetch.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				// if (checkIfNoFieldIsEmpty()) {
				SC.say("Hello uganda");

				String academicYearId = getView().getStaffDailyTaskPane().getAcademicYearCombo().getValueAsString();

				String academicTermId = getView().getStaffDailyTaskPane().getAcademicTermCombo().getValueAsString();

				String districtId = getView().getStaffDailyTaskPane().getDistrictCombo().getValueAsString();

				String schoolId = getView().getStaffDailyTaskPane().getSchoolCombo().getValueAsString();

				String schoolStaffId = getView().getStaffDailyTaskPane().getSchoolStaffCombo().getValueAsString();

				String day = getView().getStaffDailyTaskPane().getDay().getValueAsString();

				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(StaffDailyTaskPane.ACADEMIC_YEAR_ID, academicYearId);
				map.put(StaffDailyTaskPane.ACADEMIC_TERM_ID, academicTermId);
				map.put(StaffDailyTaskPane.DISTRICT_ID, districtId);
				map.put(StaffDailyTaskPane.SCHOOL_ID, schoolId);
				map.put(StaffDailyTaskPane.SCHOOL_STAFF_ID, schoolStaffId);
				map.put(StaffDailyTaskPane.DAY, day);

				map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());

				GWT.log("YEAR TOKEN " + map.get(RequestConstant.LOGIN_TOKEN));

				GWT.log("ACADEMIC_YEAR_ID: " + academicYearId);
				GWT.log("ACADEMIC_TERM_ID: " + academicTermId);
				GWT.log("DISTRICT_ID" + districtId);
				GWT.log("SCHOOL_ID" + schoolId);
				// GWT.log("SCHOOL_STAFF_ID" + schoolStaffId);
				// GWT.log("DAY" + day);

				SC.showPrompt("", "", new SwizimaLoader());

				dispatcher.execute(new RequestAction(
						RequestConstant.GET_TIME_TABLE_LESSONS_FOR_STAFF_ACADEMIC_YEAR_TERM_DISTRICT_SCHOOL_DAY, map),
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

									GWT.log("PRESENTER LIST " + result.getTableLessonDTOs());

									getView().getStaffDailyTaskPane().getLessonListGrid()
											.addRecordsToGrid(result.getTableLessonDTOs());

								} else {
									SC.warn("ERROR", "Service Down");
								}

							}
						});
				// }else {
				// SC.say("Please Fill all the fields");
				// }
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

		if (getView().getStaffDailyTaskPane().getDay().getValueAsString() == null)
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

}