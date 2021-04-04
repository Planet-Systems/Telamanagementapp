package com.planetsystems.tela.managementapp.client.presenter.systemuser;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.planetsystems.tela.dto.GeneralUserDetailDTO;
import com.planetsystems.tela.dto.NavigationMenuDTO;
import com.planetsystems.tela.dto.SubMenuItemDTO;
import com.planetsystems.tela.dto.SystemFeedbackDTO;
import com.planetsystems.tela.dto.SystemMenuDTO;
import com.planetsystems.tela.dto.SystemUserDTO;
import com.planetsystems.tela.dto.SystemUserGroupDTO;
import com.planetsystems.tela.dto.SystemUserGroupSystemMenuDTO;
import com.planetsystems.tela.dto.SystemUserProfileDTO;
import com.planetsystems.tela.managementapp.client.gin.SessionManager;
import com.planetsystems.tela.managementapp.client.place.NameTokens;
import com.planetsystems.tela.managementapp.client.presenter.main.MainPresenter;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkDataUtil;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkResult;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.planetsystems.tela.managementapp.client.widget.MenuButton;
import com.planetsystems.tela.managementapp.client.widget.SwizimaLoader;
import com.planetsystems.tela.managementapp.shared.DatePattern;
import com.planetsystems.tela.managementapp.shared.RequestAction;
import com.planetsystems.tela.managementapp.shared.RequestConstant;
import com.planetsystems.tela.managementapp.shared.RequestResult;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;

public class SystemUserPresenter extends Presenter<SystemUserPresenter.MyView, SystemUserPresenter.MyProxy> {
	interface MyView extends View {
		public ControlsPane getControlsPane();

		public SystemUserPane getSystemUserPane();

		public SystemMenuPane getSystemMenuPane();

		public UserGroupPane getUserGroupPane();

		public TabSet getTabSet();

	}

	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_SystemUser = new Type<RevealContentHandler<?>>();

	DateTimeFormat dateTimeFormat = DateTimeFormat
			.getFormat(DatePattern.DAY_MONTH_YEAR_HOUR_MINUTE_SECONDS.getPattern());
	DateTimeFormat dateFormat = DateTimeFormat.getFormat(DatePattern.DAY_MONTH_YEAR.getPattern());

	@Inject
	private DispatchAsync dispatcher;

	@Inject
	private PlaceManager placeManager;

	@NameToken(NameTokens.SystemUser)
	@ProxyCodeSplit
	interface MyProxy extends ProxyPlace<SystemUserPresenter> {
	}

	@Inject
	SystemUserPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
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

				if (selectedTab.equalsIgnoreCase("System user groups")) {
					MenuButton newButton = new MenuButton("New");
					MenuButton edit = new MenuButton("Edit");
					MenuButton delete = new MenuButton("Delete");
					MenuButton permissions = new MenuButton("Permissions");

					List<MenuButton> buttons = new ArrayList<>();
					buttons.add(newButton);
					buttons.add(edit);
					buttons.add(delete);
					buttons.add(permissions);

					getView().getControlsPane().addMenuButtons("System user groups", buttons);

					addUserGroup(newButton);
					editUserGroup(edit);
					deletUserUserGroup(delete);
					loadUserGroups();
					loaSystemUserMenu(permissions);

				} else if (selectedTab.equalsIgnoreCase("System user details")) {

					MenuButton newButton = new MenuButton("New");
					MenuButton edit = new MenuButton("Edit");
					MenuButton delete = new MenuButton("Delete");
					MenuButton details = new MenuButton("Details");

					List<MenuButton> buttons = new ArrayList<>();
					buttons.add(newButton);
					buttons.add(edit);
					buttons.add(delete);
					buttons.add(details);

					getView().getControlsPane().addMenuButtons("System user details", buttons);

					onNewButtonCLicked(newButton);

					// onUpdateButtonCLicked(edit);
					// onDeleteButtonCLicked(delete);
					// onDetailsButtonCLicked(details);
					loadUserDetails();
				} else if (selectedTab.equalsIgnoreCase("System Menu Setup")) {

					MenuButton newButton = new MenuButton("New");
					MenuButton delete = new MenuButton("Delete");

					List<MenuButton> buttons = new ArrayList<>();
					buttons.add(newButton);
					buttons.add(delete);

					getView().getControlsPane().addMenuButtons("System Menu Setup", buttons);
					loadSystemMenus();
					addSystemMenu(newButton);
					deleteSystemMenu(delete);
				}

			}
		});
	}

	// Systems user groups setup

	private void addUserGroup(final MenuButton button) {
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				UserGroupWindow window = new UserGroupWindow();
				onSaveUserGroup(window);
				window.show();

			}
		});

	}

	private void editUserGroup(final MenuButton button) {

		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				UserGroupWindow window = new UserGroupWindow();
				onUpdateUserGroup(window);
				loadRecordToEdit(window);
				window.show();

			}
		});

	}

	private void deletUserUserGroup(final MenuButton button) {
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				onDeleteUserGroup();

			}
		});
	}

	private void onSaveUserGroup(final UserGroupWindow window) {
		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				SystemUserGroupDTO userGroup = new SystemUserGroupDTO();

				userGroup.setCode(window.getRoleCode().getValueAsString());

				userGroup.setDescription(window.getDescription().getValueAsString());
				userGroup.setName(window.getRoleName().getValueAsString());
				userGroup.setDefaultGroup(window.getDefaultRole().getValueAsBoolean());
				userGroup.setReceiveAlerts(window.getReceiveAlerts().getValueAsBoolean());

				userGroup.setAdministrativeRole(window.getAdministrativeRole().getValueAsBoolean());

				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(RequestConstant.SAVE_USER_GROUP, userGroup);
				map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());

				SC.showPrompt("", "", new SwizimaLoader());

				dispatcher.execute(new RequestAction(RequestConstant.SAVE_USER_GROUP, map),
						new AsyncCallback<RequestResult>() {
							public void onFailure(Throwable caught) {
								System.out.println(caught.getMessage());
								SC.clearPrompt();
								SC.say("ERROR", caught.getMessage());
							}

							public void onSuccess(RequestResult result) {

								SC.clearPrompt();

								SessionManager.getInstance().manageSession(result, placeManager);
								if (result != null) {
									if (result.getSystemFeedbackDTO().isResponse()) {

										SC.say("Message", result.getSystemFeedbackDTO().getMessage());

										getView().getUserGroupPane().getListgrid()
												.addRecordsToGrid(result.getSystemUserGroupDTOs());

									} else {
										SC.warn("ERROR", result.getSystemFeedbackDTO().getMessage());
									}

								} else {
									SC.say("ERROR", "Unknow error");
								}

							}
						});

			}
		});
	}

	private void loadUserGroups() {

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());

		dispatcher.execute(new RequestAction(RequestConstant.GET_USER_GROUP, map), new AsyncCallback<RequestResult>() {
			public void onFailure(Throwable caught) {
				System.out.println(caught.getMessage());

				SC.clearPrompt();
				SC.say("ERROR", caught.getMessage());
			}

			public void onSuccess(RequestResult result) {

				SC.clearPrompt();

				SessionManager.getInstance().manageSession(result, placeManager);
				if (result != null) {

					getView().getUserGroupPane().getListgrid().addRecordsToGrid(result.getSystemUserGroupDTOs());

				} else {
					SC.say("ERROR", "Unknow error");
				}

			}
		});
	}

	private void onUpdateUserGroup(final UserGroupWindow window) {

		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				String id = getView().getUserGroupPane().getListgrid().getSelectedRecord()
						.getAttribute(UserGroupListgrid.ID);

				SystemUserGroupDTO userGroup = new SystemUserGroupDTO();
				userGroup.setId(id);

				userGroup.setCode(window.getRoleCode().getValueAsString());

				userGroup.setDescription(window.getDescription().getValueAsString());
				userGroup.setName(window.getRoleName().getValueAsString());
				userGroup.setDefaultGroup(window.getDefaultRole().getValueAsBoolean());
				userGroup.setReceiveAlerts(window.getReceiveAlerts().getValueAsBoolean());

				userGroup.setAdministrativeRole(window.getAdministrativeRole().getValueAsBoolean());

				SC.showPrompt("", "", new SwizimaLoader());

				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(RequestConstant.UPDATE_USER_GROUP, userGroup);
				map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());

				dispatcher.execute(new RequestAction(RequestConstant.UPDATE_USER_GROUP, map),
						new AsyncCallback<RequestResult>() {
							public void onFailure(Throwable caught) {
								System.out.println(caught.getMessage());
								SC.clearPrompt();
								SC.warn("ERROR", caught.getMessage());
							}

							public void onSuccess(RequestResult result) {

								SC.clearPrompt();

								SessionManager.getInstance().manageSession(result, placeManager);
								if (result != null) {
									if (result.getSystemFeedbackDTO().isResponse()) {

										SC.say("Message", result.getSystemFeedbackDTO().getMessage(),
												new BooleanCallback() {

													@Override
													public void execute(Boolean value) {
														if (value) {
															window.close();
														}

													}
												});

										getView().getUserGroupPane().getListgrid()
												.addRecordsToGrid(result.getSystemUserGroupDTOs());

									} else {
										SC.warn("ERROR", result.getSystemFeedbackDTO().getMessage());
									}

								} else {
									SC.say("ERROR", "Unknow error");
								}

							}
						});

			}
		});

	}

	private void onDeleteUserGroup() {

		if (getView().getUserGroupPane().getListgrid().anySelected()) {

			SC.ask("Confrim", "Are you sure you want to delete the selected record?", new BooleanCallback() {

				@Override
				public void execute(Boolean value) {
					if (value) {

						String id = getView().getUserGroupPane().getListgrid().getSelectedRecord()
								.getAttribute(UserGroupListgrid.ID);

						SystemUserGroupDTO userGroup = new SystemUserGroupDTO();
						userGroup.setId(id);

						SC.showPrompt("", "", new SwizimaLoader());

						LinkedHashMap<String, Object> map = new LinkedHashMap<>();
						map.put(RequestConstant.DELETE_USER_GROUP, userGroup);
						map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());

						dispatcher.execute(new RequestAction(RequestConstant.DELETE_USER_GROUP, map),
								new AsyncCallback<RequestResult>() {
									public void onFailure(Throwable caught) {
										System.out.println(caught.getMessage());
										SC.clearPrompt();
										SC.warn("ERROR", caught.getMessage());
									}

									public void onSuccess(RequestResult result) {

										SC.clearPrompt();

										SessionManager.getInstance().manageSession(result, placeManager);
										if (result != null) {
											if (result.getSystemFeedbackDTO().isResponse()) {

												SC.say("Message", result.getSystemFeedbackDTO().getMessage());

												getView().getUserGroupPane().getListgrid()
														.addRecordsToGrid(result.getSystemUserGroupDTOs());

											} else {
												SC.warn("ERROR", result.getSystemFeedbackDTO().getMessage());
											}

										} else {
											SC.say("ERROR", "Unknow error");
										}

									}
								});

					}

				}
			});

		} else {
			SC.warn("ERROR", "Please select record to delete");
		}

	}

	private void loadRecordToEdit(final UserGroupWindow window) {
		ListGridRecord record = getView().getUserGroupPane().getListgrid().getSelectedRecord();

		window.getRoleCode().setValue(record.getAttribute(UserGroupListgrid.CODE));
		window.getDescription().setValue(record.getAttribute(UserGroupListgrid.Description));
		window.getRoleName().setValue(record.getAttribute(UserGroupListgrid.Role));

		if (record.getAttribute(UserGroupListgrid.DefaultRole) != null) {
			String defualtRole = record.getAttribute(UserGroupListgrid.DefaultRole);
			if (defualtRole.equalsIgnoreCase("true")) {
				window.getDefaultRole().setValue(true);
			} else {
				window.getDefaultRole().setValue(false);
			}

		} else {
			window.getDefaultRole().setValue(false);
		}

		if (record.getAttribute(UserGroupListgrid.ReceiveNotifications) != null) {

			String receiveAlerts = record.getAttribute(UserGroupListgrid.ReceiveNotifications);

			if (receiveAlerts.equalsIgnoreCase("true")) {
				window.getReceiveAlerts().setValue(true);
			} else {
				window.getReceiveAlerts().setValue(false);
			}

		}
	}

	// Systems user profile setup
	private void onNewButtonCLicked(MenuButton newButton) {
		newButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				SystemUserWindow window = new SystemUserWindow();
				loadEnabledRadioGroupItem(window);
				loadGenderComboBox(window);
				loadUserGroupsCombo(window);
				window.show();
				saveSystemUser(window);
			}

		});

	}

	private void loadUserGroupsCombo(final SystemUserWindow window) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());

		dispatcher.execute(new RequestAction(RequestConstant.GET_USER_GROUP, map), new AsyncCallback<RequestResult>() {
			public void onFailure(Throwable caught) {
				System.out.println(caught.getMessage());

				SC.clearPrompt();
				SC.say("ERROR", caught.getMessage());
			}

			public void onSuccess(RequestResult result) {

				SC.clearPrompt();

				SessionManager.getInstance().manageSession(result, placeManager);
				if (result != null) {

					LinkedHashMap<String, String> hashMap = new LinkedHashMap<>();
					for (SystemUserGroupDTO dto : result.getSystemUserGroupDTOs()) {
						hashMap.put(dto.getId(), dto.getName());
					}

					window.getSystemUserGroup().setValueMap(hashMap);

				} else {
					SC.say("ERROR", "Unknow error");
				}

			}
		});
	}

	public void loadEnabledRadioGroupItem(SystemUserWindow window) {
		Map<Boolean, String> valueMap = new LinkedHashMap<Boolean, String>();
		valueMap.put(true, "Yes");
		valueMap.put(false, "No");

		window.getEnabledRadioGroupItem().setValueMap(valueMap);
	}

	public void loadGenderComboBox(SystemUserWindow window) {
		Map<String, String> valueMap = new LinkedHashMap<String, String>();
		valueMap.put("female", "Female");
		valueMap.put("male", "Male");

		window.getGenderComboBox().setValueMap(valueMap);
	}

	private void saveSystemUser(final SystemUserWindow window) {
		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				SystemUserProfileDTO dto = new SystemUserProfileDTO();
				dto.setCreatedDateTime(dateTimeFormat.format(new Date()));

				SystemUserDTO systemUserDTO = new SystemUserDTO();
				systemUserDTO.setEnabled(Boolean.valueOf(window.getEnabledRadioGroupItem().getValueAsString()));
				systemUserDTO.setUserName(window.getEmailField().getValueAsString());
				systemUserDTO.setCreatedDateTime(dateTimeFormat.format(new Date()));
				
				 // dto.setPassword(window.getPasswordField().getValueAsString());
			

				GeneralUserDetailDTO generalUserDetailDTO = new GeneralUserDetailDTO();
				generalUserDetailDTO.setFirstName(window.getFirstNameField().getValueAsString());
				generalUserDetailDTO.setLastName(window.getLastNameField().getValueAsString());
				generalUserDetailDTO.setEmail(window.getEmailField().getValueAsString());
				generalUserDetailDTO.setDob(dateFormat.format(window.getDobItem().getValueAsDate()));
				generalUserDetailDTO.setGender(window.getGenderComboBox().getValueAsString());
				generalUserDetailDTO.setNameAbbrev(window.getNameAbbrevField().getValueAsString());
				generalUserDetailDTO.setNationalId(window.getNationalIdField().getValueAsString());
				generalUserDetailDTO.setPhoneNumber(window.getPhoneNumberField().getValueAsString());

				SystemUserGroupDTO systemUserGroupDTO = new SystemUserGroupDTO();
				systemUserGroupDTO.setId(window.getSystemUserGroup().getValueAsString());

				dto.setSystemUserDTO(systemUserDTO);
				dto.setSystemUserGroupDTO(systemUserGroupDTO);
				dto.setGeneralUserDetailDTO(generalUserDetailDTO);

				if (checkIfNoSystemUserWindowFieldIsEmpty(window)) {

					LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					map.put(RequestConstant.SAVE_SYSTEM_USER, dto);
					map.put(NetworkDataUtil.ACTION , RequestConstant.SAVE_SYSTEM_USER);
					map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
					SC.showPrompt("", "", new SwizimaLoader());

						NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

							@Override
							public void onNetworkResult(RequestResult result) {
								clearSystemUserWindowFields(window);
								getAllSystemUserProfiles();
							}
						});
						
						
					
//					dispatcher.execute(new RequestAction(RequestConstant.SAVE_SYSTEM_USER, map),
//							new AsyncCallback<RequestResult>() {
//
//								public void onFailure(Throwable caught) {
//
//									SC.clearPrompt();
//									System.out.println(caught.getMessage());
//									SC.say("ERROR", caught.getMessage());
//								}
//
//								public void onSuccess(RequestResult result) {
//									SC.clearPrompt();
//
//									clearSystemUserWindowFields(window);
//
//									SessionManager.getInstance().manageSession(result, placeManager);
//
//									if (result != null) {
//										SystemFeedbackDTO feedback = result.getSystemFeedbackDTO();
//
//										if (feedback.isResponse()) {
//
//											SC.say("Success", feedback.getMessage());
//
//											getView().getSystemUserPane().getSystemUserListGrid()
//													.addRecordsToGrid(result.getSystemUserProfileDTOs());
//
//										} else {
//											SC.warn("ERROR", feedback.getMessage());
//										}
//
//									} else {
//										SC.warn("ERROR", "Unknow error");
//									}
//
//								}
//
//							});
				} else {
					SC.say("Please Fill all fields");
				}

		

			

//			  GWT.log("USER "+dto);
//			  if(confirmPassword(window)) {
//				  if(checkIfNoSystemUserWindowFieldIsEmpty(window)) {
//					  LinkedHashMap<String, Object> map = new LinkedHashMap<>();
//						map.put(RequestConstant.SAVE_SYSTEM_USER, dto);
//						map.put(NetworkDataUtil.ACTION , RequestConstant.SAVE_SYSTEM_USER);
//
//						NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {
//
//							@Override
//							public void onNetworkResult(RequestResult result) {
//								clearSystemUserWindowFields(window);
//								getAllSystemUsers();
//							}
//						});
//				  }else {
//					  SC.say("Please Fill all fields");
//				  }
//
//			  }else {
//				  SC.say("Passwords don't match");
//			  }

			}
		});
	}
	
	private void getAllSystemUserProfiles() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(NetworkDataUtil.ACTION ,RequestConstant.GET_ALL_SYSTEM_USERS);

		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {
				getView().getSystemUserPane().getSystemUserListGrid().addRecordsToGrid(result.getSystemUserProfileDTOs());
			}
		});
		
	}

	private void clearSystemUserWindowFields(SystemUserWindow window) {
		window.getFirstNameField().clearValue();
		window.getLastNameField().clearValue();
		window.getPhoneNumberField().clearValue();
		window.getEmailField().clearValue();
		window.getDobItem().clearValue();
		window.getNationalIdField().clearValue();
		window.getGenderComboBox().clearValue();
		window.getNameAbbrevField().clearValue();
		window.getEnabledRadioGroupItem().clearValue();
	}

	protected boolean checkIfNoSystemUserWindowFieldIsEmpty(SystemUserWindow window) {
		boolean flag = true;

		if (window.getFirstNameField().getValueAsString() == null)
			flag = false;

		if (window.getLastNameField().getValueAsString() == null)
			flag = false;

		if (window.getPhoneNumberField().getValueAsString() == null)
			flag = false;

		if (window.getEmailField().getValueAsString() == null)
			flag = false;

		if (window.getDobItem().getValueAsDate() == null)
			flag = false;

		if (window.getNationalIdField().getValueAsString() == null)
			flag = false;

		if (window.getGenderComboBox().getValueAsString() == null)
			flag = false;

		return flag;
	}

	private void loadUserDetails() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.GET_ALL_SYSTEM_USERS, null);
		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
		SC.showPrompt("", "", new SwizimaLoader());

		dispatcher.execute(new RequestAction(RequestConstant.GET_ALL_SYSTEM_USERS, map),
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

							getView().getSystemUserPane().getSystemUserListGrid()
									.addRecordsToGrid(result.getSystemUserProfileDTOs());

						} else {
							SC.warn("ERROR", "Unknow error");
						}

					}

				});

	}

	// Systems menu setup

	private void addSystemMenu(final MenuButton button) {
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				SystemMenuWindow window = new SystemMenuWindow();

				loadMenus(window);
				loadMenuItems(window);
				saveSystemMenuSetup(window);

				window.show();
			}
		});
	}

	private void loadMenus(final SystemMenuWindow window) {
		LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();

		for (NavigationMenuDTO dto : NavigationMenuDTO.values()) {
			valueMap.put(dto.getNavigationMenu(), dto.getNavigationMenu());

		}

		window.getNavigationMenu().setValueMap(valueMap);
	}

	private void loadMenuItems(final SystemMenuWindow window) {
		LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();

		for (SubMenuItemDTO dto : SubMenuItemDTO.values()) {
			valueMap.put(dto.getSystemMenuItem(), dto.getSystemMenuItem());

		}

		window.getNavigationMenuItem().setValueMap(valueMap);
	}

	private void saveSystemMenuSetup(final SystemMenuWindow window) {
		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				List<SystemMenuDTO> dtos = new ArrayList<>();

				String navigationMenu = window.getNavigationMenu().getValueAsString();

				for (String systemMenuItem : window.getNavigationMenuItem().getValues()) {
					SystemMenuDTO dto = new SystemMenuDTO();
					dto.setNavigationMenu(navigationMenu);
					dto.setSubMenuItem(systemMenuItem);

					dtos.add(dto);
				}

				if (!dtos.isEmpty()) {

					LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					map.put(RequestConstant.SAVE_SystemMENU, dtos);
					map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());

					SC.showPrompt("", "", new SwizimaLoader());

					dispatcher.execute(new RequestAction(RequestConstant.SAVE_SystemMENU, map),
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

										if (result.getSystemFeedbackDTO() != null) {
											if (result.getSystemFeedbackDTO().isResponse()) {
												SC.say("Sucess", result.getSystemFeedbackDTO().getMessage());
												getView().getSystemMenuPane().getListgrid()
														.addRecordsToGrid(result.getSystemMenuDTOs());
											} else {
												SC.say("ERROR", result.getSystemFeedbackDTO().getMessage());
											}
										} else {
											SC.say("ERROR", "Feedback is null");
										}

									} else {
										SC.say("ERROR", "Unknow error");
									}

								}
							});
				}

			}
		});
	}

	private void loadSystemMenus() {

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());

		SC.showPrompt("", "", new SwizimaLoader());

		dispatcher.execute(new RequestAction(RequestConstant.GET_SystemMENU, map), new AsyncCallback<RequestResult>() {
			public void onFailure(Throwable caught) {
				System.out.println(caught.getMessage());
				SC.say("ERROR", caught.getMessage());
				SC.clearPrompt();
			}

			public void onSuccess(RequestResult result) {

				SC.clearPrompt();

				SessionManager.getInstance().manageSession(result, placeManager);
				if (result != null) {

					getView().getSystemMenuPane().getListgrid().addRecordsToGrid(result.getSystemMenuDTOs());

				} else {
					SC.say("ERROR", "Unknow error");
				}

			}
		});

	}

	private void deleteSystemMenu(final MenuButton button) {
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (getView().getSystemMenuPane().getListgrid().anySelected()) {

					SC.ask("Confirm", "Are you sure you want to delete the selected record?", new BooleanCallback() {

						@Override
						public void execute(Boolean value) {
							if (value) {
								List<SystemMenuDTO> dtos = new ArrayList<>();

								for (ListGridRecord record : getView().getSystemMenuPane().getListgrid()
										.getSelectedRecords()) {
									SystemMenuDTO dto = new SystemMenuDTO();
									dto.setId(record.getAttribute(SystemMenuListgrid.ID));

									dtos.add(dto);
								}

								if (!dtos.isEmpty()) {

									LinkedHashMap<String, Object> map = new LinkedHashMap<>();
									map.put(RequestConstant.DELETE_SystemMENU, dtos);
									map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());

									SC.showPrompt("", "", new SwizimaLoader());

									dispatcher.execute(new RequestAction(RequestConstant.DELETE_SystemMENU, map),
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

														if (result.getSystemFeedbackDTO() != null) {
															if (result.getSystemFeedbackDTO().isResponse()) {
																SC.say("Sucess",
																		result.getSystemFeedbackDTO().getMessage());
																getView().getSystemMenuPane().getListgrid()
																		.addRecordsToGrid(result.getSystemMenuDTOs());
															} else {
																SC.say("ERROR",
																		result.getSystemFeedbackDTO().getMessage());
															}
														} else {
															SC.say("ERROR", "Feedback is null");
														}

													} else {
														SC.say("ERROR", "Unknow error");
													}

												}
											});
								}
							}

						}
					});

				} else {
					SC.warn("ERROR", "Please select record to delete");
				}

			}
		});
	}

	private void loaSystemUserMenu(final MenuButton button) {
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				SystemUserGroupSystemMenuWindow window = new SystemUserGroupSystemMenuWindow();
				loadSystemUserMenu(window);
				saveSystemUserMenu(window);
				window.show();
			}
		});

	}

	private void loadSystemUserMenu(final SystemUserGroupSystemMenuWindow window) {

		if (getView().getUserGroupPane().getListgrid().anySelected()) {

			String id = getView().getUserGroupPane().getListgrid().getSelectedRecord()
					.getAttribute(UserGroupListgrid.ID);

			SystemUserGroupDTO userGroup = new SystemUserGroupDTO();
			userGroup.setId(id);

			LinkedHashMap<String, Object> map = new LinkedHashMap<>();
			map.put(RequestConstant.GET_USER_GROUP_SystemMENU, userGroup);
			map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());

			dispatcher.execute(new RequestAction(RequestConstant.GET_USER_GROUP_SystemMENU, map),
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

								window.getListgrid().addRecordsToGrid(result.getSystemUserGroupSystemMenuDTOs());
								window.getListgrid().selectActiveRecords();

							} else {
								SC.say("ERROR", "Unknow error");
							}

						}
					});
		} else {
			SC.warn("ERROR", "Please select record to view permissions");
		}

		
	}

	private void saveSystemUserMenu(final SystemUserGroupSystemMenuWindow window) {
		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				List<SystemUserGroupSystemMenuDTO> dtos = new ArrayList<>();

				String id = getView().getUserGroupPane().getListgrid().getSelectedRecord()
						.getAttribute(UserGroupListgrid.ID);

				SystemUserGroupDTO userGroup = new SystemUserGroupDTO();
				userGroup.setId(id);

				for (ListGridRecord record : window.getListgrid().getRecords()) {

					boolean selected = window.getListgrid().isSelected(record);

					if (selected) {

						SystemUserGroupSystemMenuDTO dto = new SystemUserGroupSystemMenuDTO();

						if (record.getAttribute(SystemUserGroupSystemMenuListgrid.ID) != null) {
							dto.setId(record.getAttribute(SystemUserGroupSystemMenuListgrid.ID));
						}

						SystemMenuDTO systemMenu = new SystemMenuDTO();
						systemMenu.setId(record.getAttribute(SystemUserGroupSystemMenuListgrid.SystemMenuId));

						dto.setSystemMenuDTO(systemMenu);
						dto.setSystemUserGroupDTO(userGroup);
						dto.setDisabled(false);

						dtos.add(dto);

					} else {

						if (record.getAttribute(SystemUserGroupSystemMenuListgrid.ID) != null) {

							SystemUserGroupSystemMenuDTO dto = new SystemUserGroupSystemMenuDTO();

							if (record.getAttribute(SystemUserGroupSystemMenuListgrid.ID) != null) {
								dto.setId(record.getAttribute(SystemUserGroupSystemMenuListgrid.ID));
							}

							SystemMenuDTO systemMenuDTO = new SystemMenuDTO();
							systemMenuDTO.setId(record.getAttribute(SystemUserGroupSystemMenuListgrid.SystemMenuId));

							dto.setSystemMenuDTO(systemMenuDTO);
							dto.setSystemUserGroupDTO(userGroup);
							dto.setDisabled(true);

							dtos.add(dto);
						}
					}
				}

				if (!dtos.isEmpty()) {

					LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					map.put(RequestConstant.SAVE_USER_GROUP_SystemMENU, dtos);
					map.put(RequestConstant.GET_USER_GROUP, userGroup);
					map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());

					SC.showPrompt("", "", new SwizimaLoader());

					dispatcher.execute(new RequestAction(RequestConstant.SAVE_USER_GROUP_SystemMENU, map),
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

										if (result.getSystemFeedbackDTO() != null) {
											if (result.getSystemFeedbackDTO().isResponse()) {
												SC.say("SUCCESS", result.getSystemFeedbackDTO().getMessage(),
														new BooleanCallback() {

															@Override
															public void execute(Boolean value) {

																if (value) {
																	window.close();
																}

															}
														});
											} else {
												SC.say("ERROR", result.getSystemFeedbackDTO().getMessage());
											}
										}
									} else {
										SC.say("ERROR", "Unknow error");
									}

								}
							});
				} else {
					SC.warn("ERROR", "Please select at least one permission and try again.");
				}

			}
		});
	}

}