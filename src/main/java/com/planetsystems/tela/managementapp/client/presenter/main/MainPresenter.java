package com.planetsystems.tela.managementapp.client.presenter.main;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.gargoylesoftware.htmlunit.javascript.host.Window;
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
import com.planetsystems.tela.dto.NavigationMenuDTO;
import com.planetsystems.tela.dto.SystemFeedbackDTO;
import com.planetsystems.tela.dto.SystemMenuDTO;
import com.planetsystems.tela.dto.SystemUserGroupSystemMenuDTO;
import com.planetsystems.tela.managementapp.client.gin.SessionManager;
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
import com.planetsystems.tela.managementapp.client.place.NameTokens;
import com.planetsystems.tela.managementapp.client.presenter.academicyear.year.AcademicYearWindow;
import com.planetsystems.tela.managementapp.client.presenter.login.changepassword.ChangePasswordWindow;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkDataUtil;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkResult;
import com.planetsystems.tela.managementapp.client.widget.MainStatusBar;
import com.planetsystems.tela.managementapp.client.widget.Masthead;
import com.planetsystems.tela.managementapp.client.widget.NavigationPane;
import com.planetsystems.tela.managementapp.shared.RequestAction;
import com.planetsystems.tela.managementapp.shared.RequestConstant;
import com.planetsystems.tela.managementapp.shared.RequestResult;
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
		//loadSystemUserMenu();
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
	
							if(checkIfFieldsAreFilled(window)) {
								String old = window.getOldPasswordField().getValueAsString();
								String newP =  window.getNewPasswordField().getValueAsString();
								String comfirm = window.getComfirmPasswoField().getValueAsString();
								
								if (newP.equals(comfirm)) {
									AuthenticationDTO dto = new AuthenticationDTO();
									dto.setOldPassword(old);
									dto.setPassword(newP);
									dto.setUserName(userName);
									
									LinkedHashMap<String, Object> map = new LinkedHashMap<>();
									map.put(RequestConstant.DATA , dto);
									map.put(NetworkDataUtil.ACTION, RequestConstant.CHANGE_PASSWORD);
									

									NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {
										
										@Override
										public void onNetworkResult(RequestResult result) {
											SC.say(result.getSystemFeedbackDTO().getMessage());
											SessionManager.getInstance().logOut(placeManager);
											window.close();
										}
									});
								}else {
									SC.say("Passwords donot match");
								}
							}else {
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

						PlaceRequest placeRequest = new PlaceRequest.Builder().nameToken(NameTokens.ProfileDetail).build();

						placeManager.revealPlace(placeRequest);
					}
				});

				MenuItemSeparator separator = new MenuItemSeparator();
				menu.setItems(item1, item4 , item2, separator, item3);

				menu.showNextTo(getView().getMastHead().getUserProfile(), "bottom");

			}
		});

	}
	
	
	private boolean checkIfFieldsAreFilled(ChangePasswordWindow window) {
		boolean flag = true;
		if(window.getOldPasswordField().getValueAsString() == null) flag = false;
		
		if(window.getNewPasswordField().getValueAsString() == null) flag = false;
		
		if(window.getComfirmPasswoField().getValueAsString() == null) flag = false;
								
		return flag;
	}
	

	
	private void loadLoggedInSystemUserMenu() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(NetworkDataUtil.ACTION ,RequestConstant.GET_LOGED_IN_USER_SYSTEM_MENUS);
		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {
			
			@Override
			public void onNetworkResult(RequestResult result) {
				//grouping menus , wish it was j8+ i would write better
				List<String> systemConfig = new ArrayList<String>();
				List<String> enrollemnt = new ArrayList<String>();
				List<String> attendance = new ArrayList<String>();
				List<String> timetable = new ArrayList<String>();
				List<String> systemusers = new ArrayList<String>();
				List<String> generatereports = new ArrayList<String>();
				
				if (result.getSystemMenuDTOs().isEmpty()) {
					SC.say("You don have any menu");
				}else {
			
				List<SystemMenuDTO> systemMenuDTOs = result.getSystemMenuDTOs();
	
				for (SystemMenuDTO systemMenuDTO : systemMenuDTOs) {
					if(systemMenuDTO.getNavigationMenu().equalsIgnoreCase(NavigationMenuDTO.SYSTEM_CONFIGURATION.getNavigationMenu()))
						systemConfig.add(systemMenuDTO.getSubMenuItem());
					
					else if (systemMenuDTO.getNavigationMenu().equalsIgnoreCase(NavigationMenuDTO.ENROLLMENT.getNavigationMenu()))
						enrollemnt.add(systemMenuDTO.getSubMenuItem());
					
					else if (systemMenuDTO.getNavigationMenu().equalsIgnoreCase(NavigationMenuDTO.ATTENDANCE.getNavigationMenu()))
						attendance.add(systemMenuDTO.getSubMenuItem());
					
					else if (systemMenuDTO.getNavigationMenu().equalsIgnoreCase(NavigationMenuDTO.SYSTEM_USERS.getNavigationMenu()))
						systemusers.add(systemMenuDTO.getSubMenuItem());
					
					else if (systemMenuDTO.getNavigationMenu().equalsIgnoreCase(NavigationMenuDTO.TIMETABLE.getNavigationMenu()))
						timetable.add(systemMenuDTO.getSubMenuItem());
					
					else if (systemMenuDTO.getNavigationMenu().equalsIgnoreCase(NavigationMenuDTO.GENERATE_REPORTS.getNavigationMenu()))
						generatereports.add(systemMenuDTO.getSubMenuItem());
						
				}
				
				
				if (!systemConfig.isEmpty()) {
					getView().getNavigationPane().addSection(RequestConstant.SYSTEM_CONFIGURATION,
							SystemAdministrationDataSource
									.getInstance(SystemAdministrationData.getNewRecords(systemConfig)));
					getView().getNavigationPane().addRecordClickHandler(
							RequestConstant.SYSTEM_CONFIGURATION, new NavigationPaneClickHandler());
				}
				
				
				if (!enrollemnt.isEmpty()) {

					getView().getNavigationPane().addSection(RequestConstant.SYSTEM_ENROLLMENT,
							SystemEnrollmentDataSource.getInstance(SystemEnrollmentData.getNewRecords(enrollemnt)));

					getView().getNavigationPane().addRecordClickHandler(RequestConstant.SYSTEM_ENROLLMENT,
							new NavigationPaneClickHandler());
				}
				
				
				if (!attendance.isEmpty()) {

					getView().getNavigationPane().addSection(RequestConstant.SYSTEM_ATTENDANCE,
							SystemAttendanceDataSource.getInstance(SystemAttendanceData.getNewRecords(attendance)));

					getView().getNavigationPane().addRecordClickHandler(RequestConstant.SYSTEM_ATTENDANCE,
							new NavigationPaneClickHandler());

				}
				
				
				if (!timetable.isEmpty()) {

					getView().getNavigationPane().addSection(RequestConstant.SYSTEM_TIME_TABLES,
							SystemTimeTableDataSource.getInstance(SystemTimeTableData.getNewRecords(timetable)));

					getView().getNavigationPane().addRecordClickHandler(RequestConstant.SYSTEM_TIME_TABLES,
							new NavigationPaneClickHandler());

				}
				
				
				if (!systemusers.isEmpty()) {
					getView().getNavigationPane().addSection(RequestConstant.SYSTEM_USERS,
							SystemUserDataSource.getInstance(SystemUserData.getNewRecords(systemusers)));

					getView().getNavigationPane().addRecordClickHandler(RequestConstant.SYSTEM_USERS,
							new NavigationPaneClickHandler());
				}

				if (!generatereports.isEmpty()) {
					getView().getNavigationPane().addSection(RequestConstant.SYSTEM_REPORTS,
							ReportsDataSource.getInstance(ReportsData.getNewRecords(generatereports)));

					getView().getNavigationPane().addRecordClickHandler(RequestConstant.SYSTEM_REPORTS,
							new NavigationPaneClickHandler());
				}
					
				
				}}
		});
	}


	@Deprecated
	private void loadSystemUserMenu() {

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());

		dispatcher.execute(new RequestAction(RequestConstant.GET_LOGED_IN_USER_SYSTEM_MENUS, map),
				new AsyncCallback<RequestResult>() {
					public void onFailure(Throwable caught) {
						System.out.println(caught.getMessage());
						SC.say("ERROR", caught.getMessage());
						SC.clearPrompt();
					}

					public void onSuccess(RequestResult result) {

						SC.clearPrompt();

						SessionManager.getInstance().manageSession(result, placeManager);
						if (result != null) {

							List<String> systemConfig = new ArrayList<String>();
							List<String> enrollemnt = new ArrayList<String>();
							List<String> attendance = new ArrayList<String>();
							List<String> timetable = new ArrayList<String>();
							List<String> systemusers = new ArrayList<String>();
							List<String> generatereports = new ArrayList<String>();

							List<SystemUserGroupSystemMenuDTO> list = result.getSystemUserGroupSystemMenuDTOs();

							for (SystemUserGroupSystemMenuDTO dto : list) {
								if (dto.getSystemMenuDTO() != null) {
									if (dto.getSystemMenuDTO().getNavigationMenu() != null) {
										if (dto.getSystemMenuDTO().getNavigationMenu().equalsIgnoreCase(
												NavigationMenuDTO.SYSTEM_CONFIGURATION.getNavigationMenu())) {

											systemConfig.add(dto.getSystemMenuDTO().getSubMenuItem());

										} else if (dto.getSystemMenuDTO().getNavigationMenu()
												.equalsIgnoreCase(NavigationMenuDTO.ENROLLMENT.getNavigationMenu())) {

											enrollemnt.add(dto.getSystemMenuDTO().getSubMenuItem());

										} else if (dto.getSystemMenuDTO().getNavigationMenu()
												.equalsIgnoreCase(NavigationMenuDTO.ATTENDANCE.getNavigationMenu())) {

											attendance.add(dto.getSystemMenuDTO().getSubMenuItem());

										} else if (dto.getSystemMenuDTO().getNavigationMenu()
												.equalsIgnoreCase(NavigationMenuDTO.TIMETABLE.getNavigationMenu())) {

											timetable.add(dto.getSystemMenuDTO().getSubMenuItem());

										} else if (dto.getSystemMenuDTO().getNavigationMenu()
												.equalsIgnoreCase(NavigationMenuDTO.SYSTEM_USERS.getNavigationMenu())) {

											systemusers.add(dto.getSystemMenuDTO().getSubMenuItem());

										} else if (dto.getSystemMenuDTO().getNavigationMenu().equalsIgnoreCase(
												NavigationMenuDTO.GENERATE_REPORTS.getNavigationMenu())) {

											generatereports.add(dto.getSystemMenuDTO().getSubMenuItem());
										}
									}
								}
							}

							if (!systemConfig.isEmpty()) {
								getView().getNavigationPane().addSection(RequestConstant.SYSTEM_CONFIGURATION,
										SystemAdministrationDataSource
												.getInstance(SystemAdministrationData.getNewRecords(systemConfig)));
								getView().getNavigationPane().addRecordClickHandler(
										RequestConstant.SYSTEM_CONFIGURATION, new NavigationPaneClickHandler());
							}

							if (!enrollemnt.isEmpty()) {

								getView().getNavigationPane().addSection(RequestConstant.SYSTEM_ENROLLMENT,
										SystemEnrollmentDataSource.getInstance(SystemEnrollmentData.getNewRecords(enrollemnt)));

								getView().getNavigationPane().addRecordClickHandler(RequestConstant.SYSTEM_ENROLLMENT,
										new NavigationPaneClickHandler());
							}

							if (!attendance.isEmpty()) {

								getView().getNavigationPane().addSection(RequestConstant.SYSTEM_ATTENDANCE,
										SystemAttendanceDataSource.getInstance(SystemAttendanceData.getNewRecords(attendance)));

								getView().getNavigationPane().addRecordClickHandler(RequestConstant.SYSTEM_ATTENDANCE,
										new NavigationPaneClickHandler());

							}

							if (!timetable.isEmpty()) {

								getView().getNavigationPane().addSection(RequestConstant.SYSTEM_TIME_TABLES,
										SystemTimeTableDataSource.getInstance(SystemTimeTableData.getNewRecords(timetable)));

								getView().getNavigationPane().addRecordClickHandler(RequestConstant.SYSTEM_TIME_TABLES,
										new NavigationPaneClickHandler());

							}

							if (!systemusers.isEmpty()) {
								getView().getNavigationPane().addSection(RequestConstant.SYSTEM_USERS,
										SystemUserDataSource.getInstance(SystemUserData.getNewRecords(systemusers)));

								getView().getNavigationPane().addRecordClickHandler(RequestConstant.SYSTEM_USERS,
										new NavigationPaneClickHandler());
							}

							if (!generatereports.isEmpty()) {

								getView().getNavigationPane().addSection(RequestConstant.SYSTEM_REPORTS,
										ReportsDataSource.getInstance(ReportsData.getNewRecords(generatereports)));

								getView().getNavigationPane().addRecordClickHandler(RequestConstant.SYSTEM_REPORTS,
										new NavigationPaneClickHandler());
							}

							/*if (systemConfig.isEmpty() && enrollemnt.isEmpty() && attendance.isEmpty()
									&& timetable.isEmpty() && generatereports.isEmpty()) {

								PlaceRequest placeRequest = new PlaceRequest.Builder().nameToken(NameTokens.dashboard)
										.build();

								placeManager.revealPlace(placeRequest);

							} else {

								placeManager.revealDefaultPlace();

							}*/

						} else {
							SC.say("ERROR", "Unknow error");
						}

					}
				});

	}

}