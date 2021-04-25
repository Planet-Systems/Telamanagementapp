package com.planetsystems.tela.managementapp.client.presenter.dashboard;

import java.util.LinkedHashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.rpc.AsyncCallback;
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
import com.planetsystems.tela.managementapp.client.gin.SessionManager;
import com.planetsystems.tela.managementapp.client.place.NameTokens;
import com.planetsystems.tela.managementapp.client.presenter.main.MainPresenter;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkDataUtil;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkResult;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.planetsystems.tela.managementapp.client.widget.MenuButton;
import com.planetsystems.tela.managementapp.client.widget.SwizimaLoader;
import com.planetsystems.tela.managementapp.shared.RequestAction;
import com.planetsystems.tela.managementapp.shared.RequestConstant;
import com.planetsystems.tela.managementapp.shared.RequestResult;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

@SuppressWarnings("deprecation")
public class DashboardPresenter extends Presenter<DashboardPresenter.MyView, DashboardPresenter.MyProxy> {

	@Inject
	private DispatchAsync dispatcher;

	@Inject
	private PlaceManager placeManager;

	interface MyView extends View {

		public DashboardPane getDashboardPane();

		public ControlsPane getControlsPane();
	}

	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_Dashboard = new Type<RevealContentHandler<?>>();

	@NameToken(NameTokens.dashboard)
	@ProxyCodeSplit
	interface MyProxy extends ProxyPlace<DashboardPresenter> {
	}

	@Inject
	DashboardPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy, MainPresenter.SLOT_Main);

	}

	@Override
	protected void onBind() {
		// TODO Auto-generated method stub
		super.onBind();
		migrateData();
		migrateAttendanceData();
		migrateTimeTablesData();
		migrateSubjectsData();

		loadMenuButtons();

		loadDashboard();
	}

	private void loadMenuButtons() {

		MenuButton filterButton = new MenuButton("Filter");
		MenuButton refreshButton = new MenuButton("Refresh");
		getView().getControlsPane().addTitle("Dashboard: National Overview");
		getView().getControlsPane().addMember(filterButton);
		getView().getControlsPane().addMember(refreshButton);

		showFilter(filterButton);
		refresh(refreshButton);

	}

	private void migrateData() {
		getView().getDashboardPane().getRefreshButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				SC.ask("Confrim", "Are you sure you want to migrate data", new BooleanCallback() {

					@Override
					public void execute(Boolean value) {

						if (value) {
							LinkedHashMap<String, Object> map = new LinkedHashMap<>();
							map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());

							SC.showPrompt("", "", new SwizimaLoader());

							dispatcher.execute(new RequestAction(RequestConstant.MIGRATE_DATA, map),
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

												if (result.getSystemFeedbackDTO() != null) {

													SC.say(result.getSystemFeedbackDTO().getMessage());

												}
											} else {
												SC.warn("ERROR", "Unknow error");
											}

										}
									});
						}

					}
				});

			}
		});

	}

	private void migrateAttendanceData() {
		getView().getDashboardPane().getImportAttendaceButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				SC.ask("Confrim", "Are you sure you want to migrate Attendance data", new BooleanCallback() {

					@Override
					public void execute(Boolean value) {

						if (value) {
							LinkedHashMap<String, Object> map = new LinkedHashMap<>();
							map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());

							SC.showPrompt("", "", new SwizimaLoader());

							dispatcher.execute(new RequestAction(RequestConstant.MIGRATE_DATA_ATTENDACE, map),
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

												if (result.getSystemFeedbackDTO() != null) {

													SC.say(result.getSystemFeedbackDTO().getMessage());

												}
											} else {
												SC.warn("ERROR", "Unknow error");
											}

										}
									});
						}

					}
				});

			}
		});

	}

	private void migrateTimeTablesData() {
		getView().getDashboardPane().getImportTimeTablesButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				SC.ask("Confrim", "Are you sure you want to migrate Timetable data", new BooleanCallback() {

					@Override
					public void execute(Boolean value) {

						if (value) {
							LinkedHashMap<String, Object> map = new LinkedHashMap<>();
							map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());

							SC.showPrompt("", "", new SwizimaLoader());

							dispatcher.execute(new RequestAction(RequestConstant.MIGRATE_DATA_TIMETABLES, map),
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

												if (result.getSystemFeedbackDTO() != null) {

													SC.say(result.getSystemFeedbackDTO().getMessage());

												}
											} else {
												SC.warn("ERROR", "Unknow error");
											}

										}
									});
						}

					}
				});

			}
		});

	}
	
	private void migrateSubjectsData() {
		getView().getDashboardPane().getImportSubjects().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				SC.ask("Confrim", "Are you sure you want to migrate Subjects data", new BooleanCallback() {

					@Override
					public void execute(Boolean value) {

						if (value) {
							LinkedHashMap<String, Object> map = new LinkedHashMap<>();
							map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());

							SC.showPrompt("", "", new SwizimaLoader());

							dispatcher.execute(new RequestAction(RequestConstant.MIGRATE_DATA_SUBJECTS, map),
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

												if (result.getSystemFeedbackDTO() != null) {

													SC.say(result.getSystemFeedbackDTO().getMessage());

												}
											} else {
												SC.warn("ERROR", "Unknow error");
											}

										}
									});
						}

					}
				});

			}
		});

	}


	private void loadDashboard() {

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());

		SC.showPrompt("", "", new SwizimaLoader());

		dispatcher.execute(new RequestAction(RequestConstant.GET_DEFAULT_ENROLLMENT_DASHBOARD, map),
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

							OverallCountDashboardGenerator.getInstance().generateDashboard(getView().getDashboardPane(),
									result.getDashboardSummaryDTO());

						} else {
							SC.warn("ERROR", "Unknow error");
						}

					}
				});

	}

	private void loadAttendanceDashboard() {

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());

		SC.showPrompt("", "", new SwizimaLoader());

		dispatcher.execute(new RequestAction(RequestConstant.GET_DEFAULT_ATTENDANCE_DASHBOARD, map),
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

							OverallAttendanceDashboardGenerator.getInstance().generateDashboard(
									getView().getDashboardPane(), result.getAttendanceDashboardSummaryDTO());

						} else {
							SC.warn("ERROR", "Unknow error");
						}

					}
				});

	}

	private void showFilter(final MenuButton button) {
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				final Menu menu = new Menu();

				MenuItem item1 = new MenuItem("View Enrollment Dashboard");
				MenuItem item2 = new MenuItem("View Attendance Dashboard");
				MenuItem item3 = new MenuItem("View Enrollment Over Time Dashboard");
				MenuItem item4 = new MenuItem("View Attendance Over Time Dashboard");
				MenuItem item5 = new MenuItem("Advanced Filter");

				menu.setItems(item1, item2, item3, item4, item5);

				menu.showNextTo(button, "bottom");

				item1.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {

						loadDashboard();
					}
				});

				item2.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {

						loadAttendanceDashboard();
					}
				});

				item3.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						OverallImpactDashboardGenerator.getInstance().generateDashboard(getView().getDashboardPane());
					}
				});

				item4.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {

						OverallAttendanceImpactDashboardGenerator.getInstance()
								.generateDashboard(getView().getDashboardPane());
					}
				});

			}
		});
	}

	private void refresh(MenuButton button) {
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				loadDashboard();

			}
		});
	}

}