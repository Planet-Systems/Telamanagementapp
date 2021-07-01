package com.planetsystems.tela.managementapp.client.presenter.main;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.gwtplatform.mvp.shared.proxy.PlaceRequest;
import com.planetsystems.tela.dto.AuthenticationDTO;
import com.planetsystems.tela.dto.SystemMenuDTO;
import com.planetsystems.tela.dto.SystemUserGroupSystemMenuDTO;
import com.planetsystems.tela.dto.enums.NavigationMenu;
import com.planetsystems.tela.dto.enums.SubMenuItem;
import com.planetsystems.tela.dto.response.SystemResponseDTO;
import com.planetsystems.tela.managementapp.client.gin.SessionManager;
import com.planetsystems.tela.managementapp.client.menu.CurriculumCoverageData;
import com.planetsystems.tela.managementapp.client.menu.CurriculumCoverageDataSource;
import com.planetsystems.tela.managementapp.client.menu.IncentivesData;
import com.planetsystems.tela.managementapp.client.menu.IncentivesDataSource;
import com.planetsystems.tela.managementapp.client.menu.ReportsData;
import com.planetsystems.tela.managementapp.client.menu.ReportsDataSource;
import com.planetsystems.tela.managementapp.client.menu.SystemAdministrationData;
import com.planetsystems.tela.managementapp.client.menu.SystemAdministrationDataSource;
import com.planetsystems.tela.managementapp.client.menu.SystemAttendanceData;
import com.planetsystems.tela.managementapp.client.menu.SystemAttendanceDataSource;
import com.planetsystems.tela.managementapp.client.menu.SystemEnrollmentData;
import com.planetsystems.tela.managementapp.client.menu.SystemEnrollmentDataSource;
import com.planetsystems.tela.managementapp.client.menu.SystemTimeTableData;
import com.planetsystems.tela.managementapp.client.menu.SystemTimeTableDataSource;
import com.planetsystems.tela.managementapp.client.menu.SystemUserData;
import com.planetsystems.tela.managementapp.client.menu.SystemUserDataSource;
import com.planetsystems.tela.managementapp.client.menu.UtilityManagerData;
import com.planetsystems.tela.managementapp.client.menu.UtilityManagerDataSource;
import com.planetsystems.tela.managementapp.client.place.NameTokens;
import com.planetsystems.tela.managementapp.client.presenter.login.changepassword.ChangePasswordWindow;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkDataUtil;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkDataUtil2;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkResult;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkResult2;
import com.planetsystems.tela.managementapp.client.widget.MainStatusBar;
import com.planetsystems.tela.managementapp.client.widget.Masthead;
import com.planetsystems.tela.managementapp.client.widget.NavigationPane;
import com.planetsystems.tela.managementapp.shared.MyRequestAction;
import com.planetsystems.tela.managementapp.shared.MyRequestResult;
import com.planetsystems.tela.managementapp.shared.RequestAction;
import com.planetsystems.tela.managementapp.shared.RequestConstant;
import com.planetsystems.tela.managementapp.shared.RequestResult;
import com.planetsystems.tela.managementapp.shared.requestcommands.AuthRequestCommand;
import com.planetsystems.tela.managementapp.shared.requestcommands.SystemUserGroupSystemMenuCommand;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.VStack;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.MenuItemSeparator;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

@SuppressWarnings("deprecation")
public class MainPresenter extends Presenter<MainPresenter.MyView, MainPresenter.MyProxy> {
	interface MyView extends View {
		public Masthead getMastHead();

		public NavigationPane getNavigationPane();

		public MainStatusBar getMainStatusBar();
	}

	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_Main = new Type<RevealContentHandler<?>>();

	@Inject
	private PlaceManager placeManager;

	@Inject
	private DispatchAsync dispatcher;

	@ProxyStandard
	interface MyProxy extends Proxy<MainPresenter> {
	}

	@Inject
	MainPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy, RevealType.Root);
	}

	@Override
	protected void onBind() {
		super.onBind();
		manageUserProfile(Cookies.getCookie(RequestConstant.USERNAME), "");
		// loadMenu();
		// loadSystemUserMenu();
		loadLoggedInSystemUserMenu();
	}

	@Override
	protected void onReset() {
		super.onReset();
	}

	private class NavigationPaneClickHandler implements RecordClickHandler {
		public void onRecordClick(RecordClickEvent event) {

			Record record = event.getRecord();
			String name = record.getAttributeAsString("name");

			PlaceRequest placeRequest = new PlaceRequest.Builder().nameToken(name).build();

			placeManager.revealPlace(placeRequest);

		}

	}

	private void loadMenu() {
		getView().getNavigationPane().addSection(RequestConstant.SYSTEM_CONFIGURATION,
				SystemAdministrationDataSource.getInstance(SystemAdministrationData.getNewRecords()));

		getView().getNavigationPane().addRecordClickHandler(RequestConstant.SYSTEM_CONFIGURATION,
				new NavigationPaneClickHandler());

		getView().getNavigationPane().addSection(RequestConstant.SYSTEM_ENROLLMENT,
				SystemEnrollmentDataSource.getInstance(SystemEnrollmentData.getNewRecords()));

		getView().getNavigationPane().addRecordClickHandler(RequestConstant.SYSTEM_ENROLLMENT,
				new NavigationPaneClickHandler());

		getView().getNavigationPane().addSection(RequestConstant.SYSTEM_ATTENDANCE,
				SystemAttendanceDataSource.getInstance(SystemAttendanceData.getNewRecords()));

		getView().getNavigationPane().addRecordClickHandler(RequestConstant.SYSTEM_ATTENDANCE,
				new NavigationPaneClickHandler());

		getView().getNavigationPane().addSection(RequestConstant.SYSTEM_TIME_TABLES,
				SystemTimeTableDataSource.getInstance(SystemTimeTableData.getNewRecords()));

		getView().getNavigationPane().addRecordClickHandler(RequestConstant.SYSTEM_TIME_TABLES,
				new NavigationPaneClickHandler());

		getView().getNavigationPane().addSection(RequestConstant.SYSTEM_USERS,
				SystemUserDataSource.getInstance(SystemUserData.getNewRecords()));

		getView().getNavigationPane().addRecordClickHandler(RequestConstant.SYSTEM_USERS,
				new NavigationPaneClickHandler());

	}

	private void manageUserProfile(final String userName, final String role) {

		getView().getMastHead().getUserProfile().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				Label banner2 = new Label();
				banner2.setContents("<p style='margin-left:10px; text-align:center;'>" + userName + "</p>"
						+ "<p style='margin-left:10px; text-align:center;'>" + role + "</p>");
				banner2.setAutoHeight();
				banner2.setAlign(Alignment.LEFT);
				banner2.setWidth100();

				VStack layout = new VStack();
				layout.addMember(banner2);
				layout.setMembersMargin(0);
				layout.setAutoHeight();

				final Menu menu = new Menu();

				MenuItem item1 = new MenuItem(userName);
				MenuItem item4 = new MenuItem("Profile");
				MenuItem item2 = new MenuItem("Change Password");

				MenuItem item3 = new MenuItem("Logout");

				item2.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {
						final ChangePasswordWindow window = new ChangePasswordWindow();
						window.getChangeButton().addClickHandler(new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {

								if (checkIfFieldsAreFilled(window)) {
									String old = window.getOldPasswordField().getValueAsString();
									String newP = window.getNewPasswordField().getValueAsString();
									String comfirm = window.getComfirmPasswoField().getValueAsString();

									if (newP.equals(comfirm)) {
										AuthenticationDTO dto = new AuthenticationDTO();
										dto.setOldPassword(old);
										dto.setPassword(newP);

										LinkedHashMap<String, Object> map = new LinkedHashMap<>();
										map.put(MyRequestAction.DATA, dto);
										map.put(MyRequestAction.COMMAND, AuthRequestCommand.CHANGE_PASSWORD);

										NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map,
												new NetworkResult2() {

													@Override
													public void onNetworkResult(MyRequestResult result) {

														SystemResponseDTO<String> response = result.getResponseText();
														GWT.log("CHANGE PWD " + response);
														if (!response.isStatus()) {
															window.close();
															SC.say(response.getMessage());

														} else {
															window.close();
															SC.say(response.getMessage());
															SessionManager.getInstance().logOut(placeManager);
														}

													}
												});

									} else {
										SC.say("Passwords donot match");
									}
								} else {
									SC.say("fill all the fields");
								}

							}

						});

						window.show();
					}
				});

				item3.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					public void onClick(MenuItemClickEvent event) {

						SessionManager.getInstance().logOut(placeManager);

					}
				});

				item4.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {

						PlaceRequest placeRequest = new PlaceRequest.Builder().nameToken(NameTokens.ProfileDetail)
								.build();

						placeManager.revealPlace(placeRequest);
					}
				});

				MenuItemSeparator separator = new MenuItemSeparator();
				menu.setItems(item1, item4, item2, separator, item3);

				menu.showNextTo(getView().getMastHead().getUserProfile(), "bottom");

			}
		});

	}

	private boolean checkIfFieldsAreFilled(ChangePasswordWindow window) {
		boolean flag = true;
		if (window.getOldPasswordField().getValueAsString() == null)
			flag = false;

		if (window.getNewPasswordField().getValueAsString() == null)
			flag = false;

		if (window.getComfirmPasswoField().getValueAsString() == null)
			flag = false;

		return flag;
	}

	private void loadLoggedInSystemUserMenu() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(MyRequestAction.COMMAND, SystemUserGroupSystemMenuCommand.LOGGED_USER_SYSTEM_MENU);

		NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

			@Override
			public void onNetworkResult(MyRequestResult result) {
				if (result != null) {
					SystemResponseDTO<List<SystemMenuDTO>> responseDTO = result.getSystemMenuResponseList();
					/*
					 * These are root menus list
					 */
					List<String> systemConfig = new ArrayList<String>();
					List<String> enrollment = new ArrayList<String>();
					List<String> attendance = new ArrayList<String>();
					List<String> timetable = new ArrayList<String>();
					List<String> systemusers = new ArrayList<String>();
					List<String> supervisions = new ArrayList<String>();
					List<String> generateReports = new ArrayList<String>();
					List<String> curriculumCoverage = new ArrayList<String>();
					List<String> incentives = new ArrayList<String>();
					List<String> utilityManager = new ArrayList<String>();

					if (responseDTO.getData().isEmpty()) {
						SC.say("You dont have any menu");
					} else {
						// Create root system menus

						for (SystemMenuDTO systemMenuDTO : responseDTO.getData()) {

							if (systemMenuDTO.getNavigationMenu()
									.equalsIgnoreCase(NavigationMenu.SYSTEM_CONFIGURATION.getNavigationMenu()))
								systemConfig.add(systemMenuDTO.getSubMenuItem());

							else if (systemMenuDTO.getNavigationMenu()
									.equalsIgnoreCase(NavigationMenu.ENROLLMENT.getNavigationMenu()))
								enrollment.add(systemMenuDTO.getSubMenuItem());

							else if (systemMenuDTO.getNavigationMenu()
									.equalsIgnoreCase(NavigationMenu.ATTENDANCE.getNavigationMenu()))
								attendance.add(systemMenuDTO.getSubMenuItem());

							else if (systemMenuDTO.getNavigationMenu()
									.equalsIgnoreCase(NavigationMenu.SYSTEM_USERS.getNavigationMenu()))
								systemusers.add(systemMenuDTO.getSubMenuItem());

							else if (systemMenuDTO.getNavigationMenu()
									.equalsIgnoreCase(NavigationMenu.TIMETABLE.getNavigationMenu()))
								timetable.add(systemMenuDTO.getSubMenuItem());

							else if (systemMenuDTO.getNavigationMenu()
									.equalsIgnoreCase(NavigationMenu.SUPERVISION.getNavigationMenu()))
								supervisions.add(systemMenuDTO.getSubMenuItem());

							else if (systemMenuDTO.getNavigationMenu()
									.equalsIgnoreCase(NavigationMenu.GENERATE_REPORTS.getNavigationMenu()))
								generateReports.add(systemMenuDTO.getSubMenuItem());

							else if (systemMenuDTO.getNavigationMenu()
									.equalsIgnoreCase(NavigationMenu.CURRICULUM_COVERAGE.getNavigationMenu()))
								curriculumCoverage.add(systemMenuDTO.getSubMenuItem());

							else if (systemMenuDTO.getNavigationMenu()
									.equalsIgnoreCase(NavigationMenu.INCENTIVES.getNavigationMenu()))
								incentives.add(systemMenuDTO.getSubMenuItem());

							else if (systemMenuDTO.getNavigationMenu()
									.equalsIgnoreCase(NavigationMenu.UTILITY_MANAGER.getNavigationMenu()))
								utilityManager.add(systemMenuDTO.getSubMenuItem());

						}

						/// Adding sub menus
						if (!systemConfig.isEmpty()) {
							getView().getNavigationPane().addSection(
									NavigationMenu.SYSTEM_CONFIGURATION.getNavigationMenu(),
									SystemAdministrationDataSource
											.getInstance(SystemAdministrationData.getNewRecords(systemConfig)));
							getView().getNavigationPane().addRecordClickHandler(
									NavigationMenu.SYSTEM_CONFIGURATION.getNavigationMenu(),
									new NavigationPaneClickHandler());
						}

						if (!enrollment.isEmpty()) {

							getView().getNavigationPane().addSection(NavigationMenu.ENROLLMENT.getNavigationMenu(),
									SystemEnrollmentDataSource
											.getInstance(SystemEnrollmentData.getNewRecords(enrollment)));

							getView().getNavigationPane().addRecordClickHandler(
									NavigationMenu.ENROLLMENT.getNavigationMenu(), new NavigationPaneClickHandler());
						}

						if (!attendance.isEmpty()) {

							getView().getNavigationPane().addSection(NavigationMenu.ATTENDANCE.getNavigationMenu(),
									SystemAttendanceDataSource
											.getInstance(SystemAttendanceData.getNewRecords(attendance)));

							getView().getNavigationPane().addRecordClickHandler(
									NavigationMenu.ATTENDANCE.getNavigationMenu(), new NavigationPaneClickHandler());

						}

						if (!timetable.isEmpty()) {

							getView().getNavigationPane().addSection(NavigationMenu.TIMETABLE.getNavigationMenu(),
									SystemTimeTableDataSource
											.getInstance(SystemTimeTableData.getNewRecords(timetable)));

							getView().getNavigationPane().addRecordClickHandler(
									NavigationMenu.TIMETABLE.getNavigationMenu(), new NavigationPaneClickHandler());

						}

						if (!systemusers.isEmpty()) {
							getView().getNavigationPane().addSection(NavigationMenu.SYSTEM_USERS.getNavigationMenu(),
									SystemUserDataSource.getInstance(SystemUserData.getNewRecords(systemusers)));

							getView().getNavigationPane().addRecordClickHandler(
									NavigationMenu.SYSTEM_USERS.getNavigationMenu(), new NavigationPaneClickHandler());
						}

						if (!generateReports.isEmpty()) {
							getView().getNavigationPane().addSection(
									NavigationMenu.GENERATE_REPORTS.getNavigationMenu(),
									ReportsDataSource.getInstance(ReportsData.getNewRecords(generateReports)));

							getView().getNavigationPane().addRecordClickHandler(
									NavigationMenu.GENERATE_REPORTS.getNavigationMenu(),
									new NavigationPaneClickHandler());
						}

						// Need to customize it
						getView().getNavigationPane().addSection(NavigationMenu.CURRICULUM_COVERAGE.getNavigationMenu(),
								CurriculumCoverageDataSource.getInstance(CurriculumCoverageData.getNewRecords()));
						getView().getNavigationPane().addRecordClickHandler(
								NavigationMenu.CURRICULUM_COVERAGE.getNavigationMenu(),
								new NavigationPaneClickHandler());

						getView().getNavigationPane().addSection(NavigationMenu.INCENTIVES.getNavigationMenu(),
								IncentivesDataSource.getInstance(IncentivesData.getNewRecords()));
						getView().getNavigationPane().addRecordClickHandler(
								NavigationMenu.INCENTIVES.getNavigationMenu(), new NavigationPaneClickHandler());

						getView().getNavigationPane().addSection(NavigationMenu.UTILITY_MANAGER.getNavigationMenu(),
								UtilityManagerDataSource.getInstance(UtilityManagerData.getNewRecords()));
						getView().getNavigationPane().addRecordClickHandler(
								NavigationMenu.UTILITY_MANAGER.getNavigationMenu(), new NavigationPaneClickHandler());

					}
				}
			}
		});
	}

}