package com.planetsystems.tela.managementapp.client.presenter.dailyattendancedashoard;

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
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.planetsystems.tela.managementapp.client.presenter.dashboard.DashboardPane;
import com.planetsystems.tela.managementapp.client.presenter.dashboard.PrimarySchoolDashboardGenerator;
import com.planetsystems.tela.managementapp.client.presenter.main.MainPresenter;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.planetsystems.tela.managementapp.client.widget.MenuButton;
import com.planetsystems.tela.managementapp.client.widget.SwizimaLoader;
import com.planetsystems.tela.managementapp.shared.DatePattern;
import com.planetsystems.tela.managementapp.shared.RequestAction;
import com.planetsystems.tela.managementapp.shared.RequestConstant;
import com.planetsystems.tela.managementapp.shared.RequestResult;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.planetsystems.tela.dto.dashboard.AttendanceDashboardSummaryDTO;
import com.planetsystems.tela.dto.reports.NationalReportFilterDTO;
import com.planetsystems.tela.managementapp.client.gin.SessionManager;
import com.planetsystems.tela.managementapp.client.place.NameTokens;

public class DailyAttendanceDashoardPresenter
		extends Presenter<DailyAttendanceDashoardPresenter.MyView, DailyAttendanceDashoardPresenter.MyProxy> {

	@Inject
	private DispatchAsync dispatcher;

	@Inject
	private PlaceManager placeManager;

	DateTimeFormat dateFormat = DateTimeFormat.getFormat(DatePattern.DAY_MONTH_YEAR.getPattern());

	interface MyView extends View {

		public DailyDashboardPane getDashboardPane();

		public ControlsPane getControlsPane();
	}

	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_DailyAttendanceDashoard = new Type<RevealContentHandler<?>>();

	@NameToken(NameTokens.dailyattendacedashboard)
	@ProxyCodeSplit
	interface MyProxy extends ProxyPlace<DailyAttendanceDashoardPresenter> {
	}

	@Inject
	DailyAttendanceDashoardPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy, MainPresenter.SLOT_Main);

	}

	protected void onBind() {
		super.onBind();
		loadMenuButtons();
	}

	private void loadMenuButtons() {

		String attendanceDate = dateFormat.format(new Date());

		MenuButton filterButton = new MenuButton("Filter");
		MenuButton refreshButton = new MenuButton("Refresh");

		List<MenuButton> list = new ArrayList<>();
		list.add(filterButton);
		list.add(refreshButton);

		getView().getControlsPane().addMenuButtons("Dashboard: Daily Attendance National Overview-" + attendanceDate,
				list);

		// getView().getControlsPane().addTitle("Dashboard: Daily Attendance National
		// Overview");
		// getView().getControlsPane().addMember(filterButton);
		// getView().getControlsPane().addMember(refreshButton);

		refresh(refreshButton);
		filter(filterButton);

		loadDashboard(attendanceDate);

	}

	private void loadDashboard(final String attendanceDate) {

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
		map.put(RequestConstant.GET_OverallDailyAttendanceDashboard, attendanceDate);

		SC.showPrompt("", "", new SwizimaLoader());

		dispatcher.execute(new RequestAction(RequestConstant.GET_OverallDailyAttendanceDashboard, map),
				new AsyncCallback<RequestResult>() {

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

							OverallDailyAttendanceDashboardGenerator.getInstance().generateDashboard(
									getView().getDashboardPane(),
									result.getOverallDailyAttendanceEnrollmentSummaryDTO());

							OverallDailyAttendanceDashboardGenerator.getInstance().summaryListgrid
									.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {

										@Override
										public void onRecordDoubleClick(RecordDoubleClickEvent event) {

											String districtId = event.getRecord()
													.getAttribute(DailyAttendanceEnrollmentSummaryListgrid.ID);

											String district = event.getRecord()
													.getAttribute(DailyAttendanceEnrollmentSummaryListgrid.DISTRICT);

											MenuButton filterButton = new MenuButton("Filter");
											MenuButton refreshButton = new MenuButton("Back");

											List<MenuButton> list = new ArrayList<>();
											list.add(filterButton);
											list.add(refreshButton);

											getView().getControlsPane()
													.addMenuButtons("Dashboard: Daily Attendance for " + district
															+ " District -" + attendanceDate, list);

											refreshButton.addClickHandler(new ClickHandler() {

												@Override
												public void onClick(ClickEvent event) {

													loadMenuButtons();

												}
											});

											DistrictDashboardfilter(filterButton, districtId);

											loadDashboard(attendanceDate, districtId);

										}
									});

						} else {
							SC.warn("ERROR", "Unknow error");
						}

					}
				});

	}

	private void refresh(final MenuButton refreshButton) {
		refreshButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String attendanceDate = dateFormat.format(new Date());
				loadDashboard(attendanceDate);

			}
		});
	}

	private void filter(final MenuButton filterButton) {

		filterButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				final Menu menu = new Menu();

				MenuItem item1 = new MenuItem("Daily View");
				MenuItem item2 = new MenuItem("Weekly View");

				menu.setItems(item1, item2);

				menu.showNextTo(filterButton, "bottom");

				item1.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {

						final FilterWindow window = new FilterWindow();
						loadDataByDate(window);
						window.show();

					}
				});

				item2.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {

						final WeeklyFilterWindow window = new WeeklyFilterWindow();
						window.getSaveButton().addClickHandler(new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {

								String fromDate = dateFormat.format(window.getFromDate().getValueAsDate());
								String toDate = dateFormat.format(window.getToDate().getValueAsDate());

								loadWeeklyDashboard(fromDate, toDate);

							}
						});
						window.show();

					}
				});

			}
		});
	}

	private void loadDataByDate(final FilterWindow window) {
		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				String attendanceDate = dateFormat.format(window.getAttendanceDate().getValueAsDate());
				loadDashboard(attendanceDate);
			}
		});
	}

	private void loadDashboard(final String attendanceDate, final String districtId) {

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
		map.put(RequestConstant.ATTENDANCE_DATE, attendanceDate);
		map.put(RequestConstant.DISTRICT_ID, districtId);

		SC.showPrompt("", "", new SwizimaLoader());

		dispatcher.execute(new RequestAction(RequestConstant.GET_DistrictDailyAttendanceDashboard, map),
				new AsyncCallback<RequestResult>() {

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

							DistrictDailyAttendanceDashboardGenerator.getInstance().generateDashboard(
									getView().getDashboardPane(),
									result.getDistrictDailyAttendanceEnrollmentSummaryDTO());

							DistrictDailyAttendanceDashboardGenerator.getInstance().summaryListgrid
									.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {

										@Override
										public void onRecordDoubleClick(RecordDoubleClickEvent event) {

											String schoolId = event.getRecord()
													.getAttribute(DailyDistrictAttendanceEnrollmentSummaryListgrid.ID);

											String school = event.getRecord().getAttribute(
													DailyDistrictAttendanceEnrollmentSummaryListgrid.SCHOOL);

											MenuButton filterButton = new MenuButton("Filter");
											MenuButton refreshButton = new MenuButton("Back");

											List<MenuButton> list = new ArrayList<>();
											list.add(filterButton);
											list.add(refreshButton);

											getView().getControlsPane().addMenuButtons(
													"Dashboard: Daily Attendance for " + school + " -" + attendanceDate,
													list);

											refreshButton.addClickHandler(new ClickHandler() {

												@Override
												public void onClick(ClickEvent event) {

													loadMenuButtons();

												}
											});

											loadSchoolDashboard(attendanceDate, schoolId);
											SchoolDashboardfilter(filterButton, schoolId);

										}
									});

						} else {
							SC.warn("ERROR", "Unknow error");
						}

					}
				});

	}

	private void DistrictDashboardfilter(final MenuButton filterButton, final String districtId) {
		filterButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				final FilterWindow window = new FilterWindow();
				loadDataDistrictDashboardByDate(window, districtId);
				window.show();

			}
		});
	}

	private void loadDataDistrictDashboardByDate(final FilterWindow window, final String districtId) {
		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				String attendanceDate = dateFormat.format(window.getAttendanceDate().getValueAsDate());

				loadDashboard(attendanceDate, districtId);

			}
		});
	}

	private void loadSchoolDashboard(String attendanceDate, String schoolId) {

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
		map.put(RequestConstant.ATTENDANCE_DATE, attendanceDate);
		map.put(RequestConstant.SCHOOL_ID, schoolId);

		SC.showPrompt("", "", new SwizimaLoader());

		dispatcher.execute(new RequestAction(RequestConstant.GET_SchoolDailyAttendanceDashboard, map),
				new AsyncCallback<RequestResult>() {

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

							SchoolDailyAttendanceEnrollmentSummaryGenerator.getInstance().generateDashboard(
									getView().getDashboardPane(),
									result.getSchoolDailyAttendanceEnrollmentSummaryDTO());

						} else {
							SC.warn("ERROR", "Unknow error");
						}

					}
				});

	}

	private void SchoolDashboardfilter(final MenuButton filterButton, final String schoolId) {
		filterButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				final FilterWindow window = new FilterWindow();
				loadDataSchoolDashboardByDate(window, schoolId);
				window.show();

			}
		});
	}

	private void loadDataSchoolDashboardByDate(final FilterWindow window, final String schoolId) {
		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				String attendanceDate = dateFormat.format(window.getAttendanceDate().getValueAsDate());

				loadSchoolDashboard(attendanceDate, schoolId);

			}
		});
	}

	private void loadWeeklyDashboard(final String fromDate, final String toDate) {

		NationalReportFilterDTO dto = new NationalReportFilterDTO();
		dto.setFromDate(fromDate);
		dto.setToDate(toDate);

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
		map.put(RequestConstant.GET_WeeklyDailyAttendanceDashboard, dto);

		SC.showPrompt("", "", new SwizimaLoader());

		dispatcher.execute(new RequestAction(RequestConstant.GET_WeeklyDailyAttendanceDashboard, map),
				new AsyncCallback<RequestResult>() {

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

							WeeklyClockinSummaryPane pane = new WeeklyClockinSummaryPane();
							pane.getListgrid().addRecordsToGrid(result.getWeeklyClockinSummaryDTOs());

							getView().getDashboardPane().setMembers(pane);

						} else {
							SC.warn("ERROR", "Unknow error");
						}

					}
				});

	}

}