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
import com.planetsystems.tela.dto.SchoolDTO;
import com.planetsystems.tela.dto.SystemMenuDTO;
import com.planetsystems.tela.dto.SystemUserAdministrationUnitDTO;
import com.planetsystems.tela.dto.SystemUserDTO;
import com.planetsystems.tela.dto.SystemUserGroupDTO;
import com.planetsystems.tela.dto.SystemUserGroupSystemMenuDTO;
import com.planetsystems.tela.dto.SystemUserProfileDTO;
import com.planetsystems.tela.dto.enums.AdministrationLevel;
import com.planetsystems.tela.managementapp.client.gin.SessionManager;
import com.planetsystems.tela.managementapp.client.place.NameTokens;
import com.planetsystems.tela.managementapp.client.presenter.comboutils.ComboUtil;
import com.planetsystems.tela.managementapp.client.presenter.main.MainPresenter;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkDataUtil;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkResult;
import com.planetsystems.tela.managementapp.client.presenter.schoolcategory.school.FilterSchoolWindow;
import com.planetsystems.tela.managementapp.client.presenter.schoolcategory.school.SchoolListGrid;
import com.planetsystems.tela.managementapp.client.presenter.systemuser.group.SystemUserGroupSystemMenuListgrid;
import com.planetsystems.tela.managementapp.client.presenter.systemuser.group.SystemUserGroupSystemMenuWindow;
import com.planetsystems.tela.managementapp.client.presenter.systemuser.group.SystemUserGroupWindow;
import com.planetsystems.tela.managementapp.client.presenter.systemuser.group.UserGroupListgrid;
import com.planetsystems.tela.managementapp.client.presenter.systemuser.group.UserGroupPane;
import com.planetsystems.tela.managementapp.client.presenter.systemuser.manage.FilterSchoolsRegionDistrictWindow;
import com.planetsystems.tela.managementapp.client.presenter.systemuser.manage.SelectProfileSchoolWindow;
import com.planetsystems.tela.managementapp.client.presenter.systemuser.manage.SystemUserProfilePermissionWindow;
import com.planetsystems.tela.managementapp.client.presenter.systemuser.menu.SystemMenuListgrid;
import com.planetsystems.tela.managementapp.client.presenter.systemuser.menu.SystemMenuPane;
import com.planetsystems.tela.managementapp.client.presenter.systemuser.menu.SystemMenuWindow;
import com.planetsystems.tela.managementapp.client.presenter.systemuser.profile.SystemUserListGrid;
import com.planetsystems.tela.managementapp.client.presenter.systemuser.profile.SystemUserPane;
import com.planetsystems.tela.managementapp.client.presenter.systemuser.profile.SystemUserProfileWindow;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.planetsystems.tela.managementapp.client.widget.MenuButton;
import com.planetsystems.tela.managementapp.client.widget.SwizimaLoader;
import com.planetsystems.tela.managementapp.shared.DatePattern;
import com.planetsystems.tela.managementapp.shared.RequestAction;
import com.planetsystems.tela.managementapp.shared.RequestConstant;
import com.planetsystems.tela.managementapp.shared.RequestDelimeters;
import com.planetsystems.tela.managementapp.shared.RequestResult;
import com.planetsystems.tela.managementapp.shared.requestconstants.SystemMenuRequestConstant;
import com.planetsystems.tela.managementapp.shared.requestconstants.SystemUserGroupRequestConstant;
import com.planetsystems.tela.managementapp.shared.requestconstants.SystemUserGroupSystemMenuRequestConstant;
import com.planetsystems.tela.managementapp.shared.requestconstants.SystemUserProfileRequestConstant;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
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

				if (selectedTab.equalsIgnoreCase(SystemUserView.SYSTEM_USER_GROUPS)) {
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

					saveSystemUserGroup(newButton);
					editSystemUserGroup(edit);
					deletUserUserGroup(delete);
					getAllSystemUserGroups();

					loadSystemUserMenu(permissions);

				} else if (selectedTab.equalsIgnoreCase(SystemUserView.SYSTEM_USER_PROFILES)) {

					MenuButton newButton = new MenuButton("New");
					MenuButton edit = new MenuButton("Edit");
					MenuButton activate = new MenuButton("Activate");
					MenuButton delete = new MenuButton("Deactivate");
					MenuButton passwordReset = new MenuButton("Reset Password");
					MenuButton details = new MenuButton("Details");
					MenuButton permission = new MenuButton("Manage User");
					MenuButton filter = new MenuButton("Filter");

					List<MenuButton> buttons = new ArrayList<>();
					buttons.add(newButton);
					buttons.add(edit);
					buttons.add(activate);
					buttons.add(delete);
					buttons.add(passwordReset);
					buttons.add(details);
					buttons.add(permission);
					buttons.add(filter);

					addSystemUserPermission(permission);

					getView().getControlsPane().addMenuButtons("System User Details", buttons);

					onNewButtonCLicked(newButton);

					onEditButtonCLicked(edit);
					// onDetailsButtonCLicked(details);
					onPasswordResetButtonCLicked(passwordReset);
					onDeactivateButtonCLicked(delete);
					onActivateButtonCLicked(activate); 
					getSystemUserProfiles();
					
					selectFilterUserOption(filter);
					
				} else if (selectedTab.equalsIgnoreCase(SystemUserView.SYSTEM_MENU_SETUP)) {

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
	
	
	private void selectFilterUserOption(final MenuButton filter) {
		final Menu menu = new Menu();
		MenuItem basic = new MenuItem("Base Filter");
		MenuItem advanced = new MenuItem("Advanced Filter");

		menu.setItems(basic, advanced);

		filter.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				menu.showNextTo(filter, "bottom");
			}
		});

		basic.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				getView().getSystemUserPane().getSystemUserListGrid().setShowFilterEditor(true);
			}
		}); 

	}

	 
	private void editSystemUserGroup(MenuButton edit) {
		edit.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				ListGrid groupGridRecord = getView().getUserGroupPane().getListgrid();
				if (groupGridRecord.anySelected()) {
					SystemUserGroupWindow window = new SystemUserGroupWindow();

					loadFieldsToEdit(window, groupGridRecord.getSelectedRecord());
					onUpdateUserGroup(window);
					window.show();
				} else {
					SC.say("Please Select group to edit");
				}

			}
		});
	}

	private void showFilterSchoolsRegionDistrictWindow(
			final SystemUserProfilePermissionWindow profilePermissionWindow) {
		profilePermissionWindow.getSystemUserSchoolPane().getAddButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				final ListGridRecord profileRecord = getView().getSystemUserPane().getSystemUserListGrid()
						.getSelectedRecord();

				final FilterSchoolsRegionDistrictWindow regionDistrictWindow = new FilterSchoolsRegionDistrictWindow();
				final String defaultValue = null;

				ComboUtil.loadRegionCombo(regionDistrictWindow.getFilterRegionDistrict().getRegionCombo(), dispatcher,
						placeManager, defaultValue);

				regionDistrictWindow.getFilterRegionDistrict().getRegionCombo().addChangedHandler(new ChangedHandler() {

					@Override
					public void onChanged(ChangedEvent event) {
						ComboUtil.loadDistrictComboByRegion(
								regionDistrictWindow.getFilterRegionDistrict().getRegionCombo(),
								regionDistrictWindow.getFilterRegionDistrict().getDistrictCombo(), dispatcher,
								placeManager, defaultValue);
					}
				});

				regionDistrictWindow.getSaveButton().addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {

						if (regionDistrictWindow.getFilterRegionDistrict().getDistrictCombo()
								.getValueAsString() != null) {
							final SelectProfileSchoolWindow profileSchoolWindow = new SelectProfileSchoolWindow();

							profileSchoolWindow.show();
							LinkedHashMap<String, Object> map = new LinkedHashMap<>();
							map.put(NetworkDataUtil.ACTION,
									RequestConstant.GET_NOT_SCHOOLS_BY_SYSTEM_USER_PROFILE_SCHOOLS_PROFILE_DISTRICT);
							map.put(RequestDelimeters.SYSTEM_USER_PROFILE_ID,
									profileRecord.getAttribute(SystemUserListGrid.ID));
							map.put(RequestDelimeters.DISTRICT_ID, regionDistrictWindow.getFilterRegionDistrict()
									.getDistrictCombo().getValueAsString());
							NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

								@Override
								public void onNetworkResult(RequestResult result) {
									profileSchoolWindow.getSchoolDistrictListGrid()
											.addRecordsToGrid(result.getSchoolDTOs());
									if (!result.getSchoolDTOs().isEmpty())
										profileSchoolWindow.getAddSchoolsButton().enable();
								}
							});

							// saving system user profile schools

							profileSchoolWindow.getAddSchoolsButton().addClickHandler(new ClickHandler() {

								@Override
								public void onClick(ClickEvent event) {
									if (profileSchoolWindow.getSchoolDistrictListGrid().anySelected()) {

										ListGridRecord[] records = profileSchoolWindow.getSchoolDistrictListGrid()
												.getSelectedRecords();
										List<SchoolDTO> schoolDTOs = new ArrayList<SchoolDTO>();
										for (int i = 0; i < records.length; i++) {
											ListGridRecord schoolRecord = records[i];
											schoolDTOs.add(new SchoolDTO(schoolRecord.getAttribute(SchoolListGrid.ID)));
										}

										LinkedHashMap<String, Object> map = new LinkedHashMap<>();
										map.put(NetworkDataUtil.ACTION,
												RequestConstant.SAVE_SYSTEM_USER_PROFILE_SCHOOLS_PROFILE);
										map.put(RequestConstant.REQUEST_DATA, schoolDTOs);
										map.put(RequestDelimeters.SYSTEM_USER_PROFILE_ID,
												profileRecord.getAttribute(SystemUserListGrid.ID));
										NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

											@Override
											public void onNetworkResult(RequestResult result) {
												// close windows
												// getSchoolsBySystemUserProfile(profilePermissionWindow,
												// profileRecord);
												profilePermissionWindow.close();
												regionDistrictWindow.close();
												profileSchoolWindow.close();

											}
										});
									} else {
										SC.say("Select school to add");
									}
								}
							});

						} else {
							SC.say("Please select a district");
						}

					}
				});

				regionDistrictWindow.show();
			}
		});
	}

	// Systems user groups setup

	private void saveSystemUserGroup(final MenuButton button) {

		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				final SystemUserGroupWindow window = new SystemUserGroupWindow();

				loadUserLevels(window);

				window.getSaveButton().addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {

						if (checkIfNoSystemUserGroupWindowFieldIsEmpty(window)) {

							SystemUserGroupDTO systemUserGroupDTO = new SystemUserGroupDTO();
							systemUserGroupDTO.setCode(window.getCodeField().getValueAsString());

							systemUserGroupDTO.setDescription(window.getDescriptionField().getValueAsString());
							systemUserGroupDTO.setName(window.getNameField().getValueAsString());

							systemUserGroupDTO.setDefaultGroup(
									Boolean.parseBoolean(window.getDefaultRoleRadio().getValueAsString()));

							systemUserGroupDTO.setReceiveAlerts(
									Boolean.parseBoolean(window.getReceiveAlertRadio().getValueAsString()));

							systemUserGroupDTO.setAdministrativeRole(
									Boolean.parseBoolean(window.getAdministrativeRoleRadio().getValueAsString()));

							systemUserGroupDTO.setUserLevel(window.getUserLevelField().getValueAsString());

							LinkedHashMap<String, Object> map = new LinkedHashMap<>();
							map.put(SystemUserGroupRequestConstant.DATA, systemUserGroupDTO);
							map.put(NetworkDataUtil.ACTION, SystemUserGroupRequestConstant.SAVE_SYSTEM_USER_GROUP);
							NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

								@Override
								public void onNetworkResult(RequestResult result) {
									clearSystemGroupWindow(window);
									SC.say(result.getSystemFeedbackDTO().getMessage());
									getAllSystemUserGroups();
								}
							});
						} else {
							SC.say("Please fill all the fields");
						}

					}
				});

				// onSaveUserGroup(window);
				window.show();

			}
		});

	}

	private void loadUserLevels(final SystemUserGroupWindow window) {

		//

		LinkedHashMap<String, String> hashMap = new LinkedHashMap<>();

		for (AdministrationLevel level : AdministrationLevel.values()) {
			hashMap.put(level.getAdministrationLevel(), level.getAdministrationLevel());
		}
		window.getUserLevelField().setValueMap(hashMap);
	}

	private boolean checkIfNoSystemUserGroupWindowFieldIsEmpty(SystemUserGroupWindow window) {
		boolean status = true;
		if (window.getNameField().getValueAsString() == null)
			status = false;

		if (window.getReceiveAlertRadio().getValueAsString() == null)
			status = false;

		if (window.getCodeField().getValueAsString() == null)
			status = false;

		if (window.getDescriptionField().getValueAsString() == null)
			status = false;

		if (window.getDefaultRoleRadio().getValueAsString() == null)
			status = false;

		if (window.getAdministrativeRoleRadio().getValueAsString() == null)
			status = false;

		return status;
	}

	private void clearSystemGroupWindow(SystemUserGroupWindow window) {
		window.getNameField().clearValue();
		window.getReceiveAlertRadio().clearValue();
		window.getCodeField().clearValue();
		window.getDescriptionField().clearValue();
		window.getDefaultRoleRadio().clearValue();
		window.getAdministrativeRoleRadio().clearValue();
	}

	private void deletUserUserGroup(final MenuButton button) {
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				onDeleteUserGroup();

			}
		});
	}

	// @Deprecated
	// private void onSaveUserGroup(final SystemUserGroupWindow window) {
	// window.getSaveButton().addClickHandler(new ClickHandler() {
	//
	// @Override
	// public void onClick(ClickEvent event) {
	//
	// SystemUserGroupDTO userGroup = new SystemUserGroupDTO();
	//
	// userGroup.setCode(window.getRoleCode().getValueAsString());
	//
	// userGroup.setDescription(window.getDescription().getValueAsString());
	// userGroup.setName(window.getRoleName().getValueAsString());
	// userGroup.setDefaultGroup(window.getDefaultRole().getValueAsBoolean());
	// userGroup.setReceiveAlerts(window.getReceiveAlerts().getValueAsBoolean());
	//
	// userGroup.setAdministrativeRole(window.getAdministrativeRole().getValueAsBoolean());
	//
	// LinkedHashMap<String, Object> map = new LinkedHashMap<>();
	// map.put(RequestConstant.SAVE_USER_GROUP, userGroup);
	// map.put(RequestConstant.LOGIN_TOKEN,
	// SessionManager.getInstance().getLoginToken());
	//
	// SC.showPrompt("", "", new SwizimaLoader());
	//
	// dispatcher.execute(new RequestAction(RequestConstant.SAVE_USER_GROUP, map),
	// new AsyncCallback<RequestResult>() {
	// public void onFailure(Throwable caught) {
	// System.out.println(caught.getMessage());
	// SC.clearPrompt();
	// SC.say("ERROR", caught.getMessage());
	// }
	//
	// public void onSuccess(RequestResult result) {
	//
	// SC.clearPrompt();
	//
	// SessionManager.getInstance().manageSession(result, placeManager);
	// if (result != null) {
	// if (result.getSystemFeedbackDTO().isResponse()) {
	//
	// SC.say("Message", result.getSystemFeedbackDTO().getMessage());
	//
	// getView().getUserGroupPane().getListgrid()
	// .addRecordsToGrid(result.getSystemUserGroupDTOs());
	//
	// } else {
	// SC.warn("ERROR", result.getSystemFeedbackDTO().getMessage());
	// }
	//
	// } else {
	// SC.say("ERROR", "Unknow error");
	// }
	//
	// }
	// });
	//
	// }
	// });
	// }

	private void getAllSystemUserGroups() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(NetworkDataUtil.ACTION, SystemUserGroupRequestConstant.GET_SYSTEM_USER_GROUPS);
		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {
				getView().getUserGroupPane().getListgrid().addRecordsToGrid(result.getSystemUserGroupDTOs());
			}
		});

		// dispatcher.execute(new RequestAction(RequestConstant.GET_USER_GROUP, map),
		// new AsyncCallback<RequestResult>() {
		// public void onFailure(Throwable caught) {
		// System.out.println(caught.getMessage());
		//
		// SC.clearPrompt();
		// SC.say("ERROR", caught.getMessage());
		// }
		//
		// public void onSuccess(RequestResult result) {
		//
		// SC.clearPrompt();
		//
		// SessionManager.getInstance().manageSession(result, placeManager);
		// if (result != null) {
		//
		// getView().getUserGroupPane().getListgrid().addRecordsToGrid(result.getSystemUserGroupDTOs());
		//
		// } else {
		// SC.say("ERROR", "Unknow error");
		// }
		//
		// }
		// });
	}

	private void onUpdateUserGroup(final SystemUserGroupWindow window) {

		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				String id = getView().getUserGroupPane().getListgrid().getSelectedRecord()
						.getAttribute(UserGroupListgrid.ID);

				SystemUserGroupDTO userGroup = new SystemUserGroupDTO();
				userGroup.setId(id);

				userGroup.setCode(window.getCodeField().getValueAsString());

				userGroup.setDescription(window.getDescriptionField().getValueAsString());
				userGroup.setName(window.getNameField().getValueAsString());
				userGroup.setDefaultGroup(Boolean.parseBoolean(window.getDefaultRoleRadio().getValueAsString()));
				userGroup.setReceiveAlerts(Boolean.parseBoolean(window.getReceiveAlertRadio().getValueAsString()));

				userGroup.setAdministrativeRole(
						Boolean.parseBoolean(window.getAdministrativeRoleRadio().getValueAsString()));

				userGroup.setUserLevel(window.getUserLevelField().getValueAsString());

				SC.showPrompt("", "", new SwizimaLoader());

				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(SystemUserGroupRequestConstant.DATA, userGroup);
				map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());

				dispatcher.execute(new RequestAction(SystemUserGroupRequestConstant.UPDATE_SYSTEM_USER_GROUP, map),
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

	private void loadFieldsToEdit(final SystemUserGroupWindow window, ListGridRecord systemUserGroupRecord) {

		loadUserLevels(window);

		window.getCodeField().setValue(systemUserGroupRecord.getAttribute(UserGroupListgrid.CODE));
		window.getDescriptionField().setValue(systemUserGroupRecord.getAttribute(UserGroupListgrid.Description));
		window.getNameField().setValue(systemUserGroupRecord.getAttribute(UserGroupListgrid.Role));
		window.getUserLevelField().setValue(systemUserGroupRecord.getAttribute(UserGroupListgrid.Level));

		if (systemUserGroupRecord.getAttribute(UserGroupListgrid.DefaultRole) != null) {
			String defualtRole = systemUserGroupRecord.getAttribute(UserGroupListgrid.DefaultRole);
			if (defualtRole.equalsIgnoreCase("true")) {
				window.getDefaultRoleRadio().setValue(true);
			} else {
				window.getDefaultRoleRadio().setValue(false);
			}

		} else {
			window.getDefaultRoleRadio().setValue(false);
		}

		if (systemUserGroupRecord.getAttribute(UserGroupListgrid.ReceiveNotifications) != null) {

			String receiveAlerts = systemUserGroupRecord.getAttribute(UserGroupListgrid.ReceiveNotifications);

			if (receiveAlerts.equalsIgnoreCase("true")) {
				window.getReceiveAlertRadio().setValue(true);
			} else {
				window.getReceiveAlertRadio().setValue(false);
			}

		}
	}

	// Systems user profile setup
	private void onNewButtonCLicked(MenuButton newButton) {
		newButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				SystemUserProfileWindow window = new SystemUserProfileWindow();
				loadEnabledRadioGroupItem(window);
				loadGenderComboBox(window);
				loadUserGroupsCombo(window);
				window.show();
				saveSystemUserProfile(window);
			}

		});

	}

	private void onEditButtonCLicked(MenuButton button) {
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (getView().getSystemUserPane().getSystemUserListGrid().anySelected()) {
					SystemUserProfileWindow window = new SystemUserProfileWindow();
					loadEnabledRadioGroupItem(window);
					loadGenderComboBox(window);
					loadUserGroupsCombo(window);
					loadRecordToEdit(window);
					updateSystemUserProfile(window);
					window.show();

				} else {
					SC.warn("ERROR", "Please select record to edit");
				}

			}

		});

	}

	private void onDeactivateButtonCLicked(MenuButton button) {
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (getView().getSystemUserPane().getSystemUserListGrid().anySelected()) {

					SC.ask("Confirm", "Are you sure you want to deactivate the selected system user?",
							new BooleanCallback() {

								@Override
								public void execute(Boolean value) {

									if (value) {
										deactivate();
									}
								}
							});

				} else {
					SC.warn("ERROR", "Please select record to edit");
				}

			}

		});

	}

	private void onActivateButtonCLicked(MenuButton button) {
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (getView().getSystemUserPane().getSystemUserListGrid().anySelected()) {

					SC.ask("Confirm", "Are you sure you want to Activate the selected system user?",
							new BooleanCallback() {

								@Override
								public void execute(Boolean value) {

									if (value) {
										activate();
									}
								}
							});

				} else {
					SC.warn("ERROR", "Please select record to edit");
				}

			}

		});

	}

	private void onPasswordResetButtonCLicked(MenuButton button) {
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (getView().getSystemUserPane().getSystemUserListGrid().anySelected()) {

					SC.ask("Confirm", "Are you sure you want to deactivate the selected system user?",
							new BooleanCallback() {

								@Override
								public void execute(Boolean value) {

									if (value) {
										resetPassword();
									}
								}
							});

				} else {
					SC.warn("ERROR", "Please select record to edit");
				}

			}

		});

	}

	private void loadRecordToEdit(final SystemUserProfileWindow window) {

		ListGridRecord record = getView().getSystemUserPane().getSystemUserListGrid().getSelectedRecord();

		window.getFirstNameField().setValue(record.getAttribute(SystemUserListGrid.FIRST_NAME));
		window.getLastNameField().setValue(record.getAttribute(SystemUserListGrid.LAST_NAME));
		window.getPhoneNumberField().setValue(record.getAttribute(SystemUserListGrid.PHONE_NUMBER));
		window.getEmailField().setValue(record.getAttribute(SystemUserListGrid.EMAIL));
		window.getGenderCombo().setValue(record.getAttribute(SystemUserListGrid.GENDER));
		window.getSystemUserGroupCombo().setValue(record.getAttribute(SystemUserListGrid.USER_GROUP_ID));

	}

	private void loadUserGroupsCombo(final SystemUserProfileWindow window) {

		// ComboUtil.loadSystemUserGroupCombo(window.getSystemUserGroupCombo() ,
		// dispatcher, placeManager, null);

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(NetworkDataUtil.ACTION, SystemUserGroupRequestConstant.GET_SYSTEM_USER_GROUPS);
		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {
				LinkedHashMap<String, String> hashMap = new LinkedHashMap<>();
				for (SystemUserGroupDTO dto : result.getSystemUserGroupDTOs()) {
					hashMap.put(dto.getId(), dto.getName());
				}
				window.getSystemUserGroupCombo().setValueMap(hashMap);
			}
		});

		// dispatcher.execute(new RequestAction(RequestConstant.GET_USER_GROUP, map),
		// new AsyncCallback<RequestResult>() {
		// public void onFailure(Throwable caught) {
		// System.out.println(caught.getMessage());
		//
		// SC.clearPrompt();
		// SC.say("ERROR", caught.getMessage());
		// }
		//
		// public void onSuccess(RequestResult result) {
		//
		// SC.clearPrompt();
		//
		// SessionManager.getInstance().manageSession(result, placeManager);
		// if (result != null) {
		//
		// LinkedHashMap<String, String> hashMap = new LinkedHashMap<>();
		// for (SystemUserGroupDTO dto : result.getSystemUserGroupDTOs()) {
		// hashMap.put(dto.getId(), dto.getName());
		// }
		//
		// window.getSystemUserGroupCombo().setValueMap(hashMap);
		//
		// } else {
		// SC.say("ERROR", "Unknow error");
		// }
		//
		// }
		// });
	}

	public void loadEnabledRadioGroupItem(SystemUserProfileWindow window) {
		Map<Boolean, String> valueMap = new LinkedHashMap<Boolean, String>();
		valueMap.put(true, "Yes");
		valueMap.put(false, "No");

		window.getEnabledRadioGroupItem().setValueMap(valueMap);
	}

	@Deprecated
	public void loadGenderComboBox(SystemUserProfileWindow window) {
		Map<String, String> valueMap = new LinkedHashMap<String, String>();
		valueMap.put("Female", "Female");
		valueMap.put("Male", "Male");
		valueMap.put("Others", "Others");

		window.getGenderCombo().setValueMap(valueMap);
	}

	private void saveSystemUserProfile(final SystemUserProfileWindow window) {
		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (checkIfNoSystemUserWindowFieldIsEmpty(window)) {

					SystemUserProfileDTO dto = new SystemUserProfileDTO();
					dto.setCreatedDateTime(dateTimeFormat.format(new Date()));
					SystemUserDTO systemUserDTO = new SystemUserDTO();
					systemUserDTO
							.setEnabled(Boolean.parseBoolean(window.getEnabledRadioGroupItem().getValueAsString()));
					systemUserDTO.setUserName(window.getEmailField().getValueAsString());
					systemUserDTO.setCreatedDateTime(dateTimeFormat.format(new Date()));

					// dto.setPassword(window.getPasswordField().getValueAsString());

					GeneralUserDetailDTO generalUserDetailDTO = new GeneralUserDetailDTO();
					generalUserDetailDTO.setFirstName(window.getFirstNameField().getValueAsString());
					generalUserDetailDTO.setLastName(window.getLastNameField().getValueAsString());
					generalUserDetailDTO.setEmail(window.getEmailField().getValueAsString());
					// generalUserDetailDTO.setDob(dateFormat.format(window.getDobItem().getValueAsDate()));
					generalUserDetailDTO.setGender(window.getGenderCombo().getValueAsString());
					// generalUserDetailDTO.setNameAbbrev(window.getNameAbbrevField().getValueAsString());
					// generalUserDetailDTO.setNationalId(window.getNationalIdField().getValueAsString());
					generalUserDetailDTO.setPhoneNumber(window.getPhoneNumberField().getValueAsString());

					SystemUserGroupDTO systemUserGroupDTO = new SystemUserGroupDTO();
					systemUserGroupDTO.setId(window.getSystemUserGroupCombo().getValueAsString());

					dto.setSystemUserDTO(systemUserDTO);
					dto.setSystemUserGroupDTO(systemUserGroupDTO);
					dto.setGeneralUserDetailDTO(generalUserDetailDTO);

					LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					map.put(NetworkDataUtil.ACTION, SystemUserProfileRequestConstant.SAVE_SYSTEM_USER_PROFILE);
					map.put(SystemUserProfileRequestConstant.DATA, dto);

					NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

						@Override
						public void onNetworkResult(RequestResult result) {

							if (result.getSystemFeedbackDTO() != null) {
								if (result.getSystemFeedbackDTO().isResponse()) {
									SC.say("Success", result.getSystemFeedbackDTO().getMessage(),
											new BooleanCallback() {

												@Override
												public void execute(Boolean value) {
													if (value) {
														clearSystemUserWindowFields(window);
														getAllSystemUserProfiles();
													}

												}
											});
								}
							}

						}
					});

				} else {
					SC.say("Fill all the fields");
				}

			}
		});
	}

	private void updateSystemUserProfile(final SystemUserProfileWindow window) {
		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				if (checkIfNoSystemUserWindowFieldIsEmpty(window)) {

					SystemUserProfileDTO dto = new SystemUserProfileDTO();
					dto.setId(getView().getSystemUserPane().getSystemUserListGrid().getSelectedRecord()
							.getAttribute(SystemUserListGrid.ID));

					dto.setCreatedDateTime(dateTimeFormat.format(new Date()));
					SystemUserDTO systemUserDTO = new SystemUserDTO();
					systemUserDTO
							.setEnabled(Boolean.parseBoolean(window.getEnabledRadioGroupItem().getValueAsString()));
					systemUserDTO.setUserName(window.getEmailField().getValueAsString());
					systemUserDTO.setCreatedDateTime(dateTimeFormat.format(new Date()));

					// dto.setPassword(window.getPasswordField().getValueAsString());

					GeneralUserDetailDTO generalUserDetailDTO = new GeneralUserDetailDTO();
					generalUserDetailDTO.setFirstName(window.getFirstNameField().getValueAsString());
					generalUserDetailDTO.setLastName(window.getLastNameField().getValueAsString());
					generalUserDetailDTO.setEmail(window.getEmailField().getValueAsString());
					// generalUserDetailDTO.setDob(dateFormat.format(window.getDobItem().getValueAsDate()));
					generalUserDetailDTO.setGender(window.getGenderCombo().getValueAsString());
					// generalUserDetailDTO.setNameAbbrev(window.getNameAbbrevField().getValueAsString());
					// generalUserDetailDTO.setNationalId(window.getNationalIdField().getValueAsString());
					generalUserDetailDTO.setPhoneNumber(window.getPhoneNumberField().getValueAsString());

					SystemUserGroupDTO systemUserGroupDTO = new SystemUserGroupDTO();
					systemUserGroupDTO.setId(window.getSystemUserGroupCombo().getValueAsString());

					dto.setSystemUserDTO(systemUserDTO);
					dto.setSystemUserGroupDTO(systemUserGroupDTO);
					dto.setGeneralUserDetailDTO(generalUserDetailDTO);

					LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					map.put(NetworkDataUtil.ACTION, SystemUserProfileRequestConstant.UPDATE_SYSTEM_USER_PROFILE);
					map.put(SystemUserProfileRequestConstant.DATA, dto);

					NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

						@Override
						public void onNetworkResult(RequestResult result) {

							if (result.getSystemFeedbackDTO() != null) {
								if (result.getSystemFeedbackDTO().isResponse()) {
									SC.say("Success", result.getSystemFeedbackDTO().getMessage(),
											new BooleanCallback() {

												@Override
												public void execute(Boolean value) {
													if (value) {
														window.close();
														getAllSystemUserProfiles();
													}

												}
											});
								}
							}

						}
					});

				} else {
					SC.say("Fill all the fields");
				}

			}
		});
	}

	private void deactivate() {

		SystemUserProfileDTO dto = new SystemUserProfileDTO();
		dto.setId(getView().getSystemUserPane().getSystemUserListGrid().getSelectedRecord()
				.getAttribute(SystemUserListGrid.ID));

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(NetworkDataUtil.ACTION, SystemUserProfileRequestConstant.DEACTIVATE_SYSTEM_USER_PROFILE);
		map.put(SystemUserProfileRequestConstant.DATA, dto);

		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {

				if (result.getSystemFeedbackDTO() != null) {
					if (result.getSystemFeedbackDTO().isResponse()) {
						SC.say("Success", result.getSystemFeedbackDTO().getMessage(), new BooleanCallback() {

							@Override
							public void execute(Boolean value) {
								if (value) {
									getAllSystemUserProfiles();
								}

							}
						});
					}
				}

			}
		});

	}

	private void activate() {

		SystemUserProfileDTO dto = new SystemUserProfileDTO();
		dto.setId(getView().getSystemUserPane().getSystemUserListGrid().getSelectedRecord()
				.getAttribute(SystemUserListGrid.ID));

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(NetworkDataUtil.ACTION, SystemUserProfileRequestConstant.ACTIVATE_SYSTEM_USER_PROFILE);
		map.put(SystemUserProfileRequestConstant.DATA, dto);

		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {

				if (result.getSystemFeedbackDTO() != null) {
					if (result.getSystemFeedbackDTO().isResponse()) {
						SC.say("Success", result.getSystemFeedbackDTO().getMessage(), new BooleanCallback() {

							@Override
							public void execute(Boolean value) {
								if (value) {
									getAllSystemUserProfiles();
								}

							}
						});
					}
				}

			}
		});

	}

	private void resetPassword() {

		SystemUserProfileDTO dto = new SystemUserProfileDTO();
		dto.setId(getView().getSystemUserPane().getSystemUserListGrid().getSelectedRecord()
				.getAttribute(SystemUserListGrid.ID));

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(NetworkDataUtil.ACTION, SystemUserProfileRequestConstant.RESET_PASSWORD_SYSTEM_USER_PROFILE);
		map.put(SystemUserProfileRequestConstant.DATA, dto);

		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {

				if (result.getSystemFeedbackDTO() != null) {
					if (result.getSystemFeedbackDTO().isResponse()) {
						SC.say("Success", result.getSystemFeedbackDTO().getMessage(), new BooleanCallback() {

							@Override
							public void execute(Boolean value) {
								if (value) {
									getAllSystemUserProfiles();
								}

							}
						});
					}
				}

			}
		});

	}

	@Deprecated
	private boolean checkIfProfileFieldsNotEmpty(SystemUserProfileWindow window) {
		boolean status = true;

		// if (window.getFirstNameField().getValueAsString() == null)
		// status = false;

		// if (window.getLastNameField().getValueAsString() == null)
		// status = false;

		// if (window.getPhoneNumberField().getValueAsString() == null)
		// status = false;

		if (window.getEmailField().getValueAsString() == null)
			status = false;

		// if (window.getDobItem().getValueAsDate() == null)
		// status = false;

		// if (window.getNationalIdField().getValueAsString() == null)
		// status = false;
		//
		// if (window.getGenderCombo().getValueAsString() == null)
		// status = false;

		if (window.getSystemUserGroupCombo().getValueAsString() == null)
			status = false;

		// if(window.getNameAbbrevField().getValueAsString() == null) status = false;

		return status;
	}

	private void getAllSystemUserProfiles() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(NetworkDataUtil.ACTION, SystemUserProfileRequestConstant.GET_SYSTEM_USER_PROFILES);

		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {
				getView().getSystemUserPane().getSystemUserListGrid()
						.addRecordsToGrid(result.getSystemUserProfileDTOs());
			}
		});

	}

	private void clearSystemUserWindowFields(SystemUserProfileWindow window) {
		// window.getFirstNameField().clearValue();
		// window.getLastNameField().clearValue();
		// window.getPhoneNumberField().clearValue();
		window.getEmailField().clearValue();
		// window.getDobItem().clearValue();
		// window.getNationalIdField().clearValue();
		// window.getGenderCombo().clearValue();
		// window.getNameAbbrevField().clearValue();
		window.getEnabledRadioGroupItem().clearValue();
	}

	protected boolean checkIfNoSystemUserWindowFieldIsEmpty(SystemUserProfileWindow window) {
		boolean flag = true;

		// if (window.getFirstNameField().getValueAsString() == null)
		// flag = false;
		//
		// if (window.getLastNameField().getValueAsString() == null)
		// flag = false;
		//
		// if (window.getPhoneNumberField().getValueAsString() == null)
		// flag = false;

		if (window.getEmailField().getValueAsString() == null)
			flag = false;

		// if (window.getDobItem().getValueAsDate() == null)
		// flag = false;
		//
		// if (window.getNationalIdField().getValueAsString() == null)
		// flag = false;
		//
		// if (window.getGenderCombo().getValueAsString() == null)
		// flag = false;

		return flag;
	}

	private void getSystemUserProfiles() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(NetworkDataUtil.ACTION, SystemUserProfileRequestConstant.GET_SYSTEM_USER_PROFILES);
		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {
				getView().getSystemUserPane().getSystemUserListGrid()
						.addRecordsToGrid(result.getSystemUserProfileDTOs());
			}
		});

		 

	}

	// Systems menu setup

	private void addSystemMenu(final MenuButton button) {
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				SystemMenuWindow window = new SystemMenuWindow();

				loadNavigationMenuCombo(window);
				loadSubMenuItemsByNavigationMenu(window);
				saveSystemMenu(window);

				window.show();
			}
		});
	}

	private void loadNavigationMenuCombo(final SystemMenuWindow window) {
		LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();

		for (NavigationMenuDTO dto : NavigationMenuDTO.values()) {
			valueMap.put(dto.getNavigationMenu(), dto.getNavigationMenu());

		}

		window.getNavigationMenuCombo().setValueMap(valueMap);
	}

	private void loadSubMenuItemsByNavigationMenu(final SystemMenuWindow window) {
		window.getNavigationMenuCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();
				String navigationMenu = window.getNavigationMenuCombo().getValueAsString();

				for (SystemMenuDTO systemMenuDTO : SystemMenuDTO.systemMenuDTOList()) {
					if (systemMenuDTO.getNavigationMenu().equalsIgnoreCase(navigationMenu))
						valueMap.put(systemMenuDTO.getSubMenuItem(), systemMenuDTO.getSubMenuItem());
				}
				window.getSubMenuItemCombo().setValueMap(valueMap);
			}
		});

		// for (SubMenuItemDTO dto : SubMenuItemDTO.values()) {
		// valueMap.put(dto.getSystemMenuItem(), dto.getSystemMenuItem());
		//
		// }

	}

	private void saveSystemMenu(final SystemMenuWindow window) {
		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				List<SystemMenuDTO> dtos = new ArrayList<>();

				String navigationMenu = window.getNavigationMenuCombo().getValueAsString();

				for (String systemMenuItem : window.getSubMenuItemCombo().getValues()) {
					SystemMenuDTO dto = new SystemMenuDTO();
					dto.setNavigationMenu(navigationMenu);
					dto.setSubMenuItem(systemMenuItem);
					dtos.add(dto);
				}

				if (!dtos.isEmpty()) {

					LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					map.put(SystemMenuRequestConstant.DATA, dtos);
					map.put(NetworkDataUtil.ACTION, SystemMenuRequestConstant.SAVE_SYSTEM_MENU);

					NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

						@Override
						public void onNetworkResult(RequestResult result) {
							SC.say(result.getSystemFeedbackDTO().getMessage());
							loadSystemMenus();
							window.close();
						}
					});

					// SC.showPrompt("", "", new SwizimaLoader());
					//
					// dispatcher.execute(new RequestAction(RequestConstant.SAVE_SystemMENU, map),
					// new AsyncCallback<RequestResult>() {
					// public void onFailure(Throwable caught) {
					// System.out.println(caught.getMessage());
					// SC.say("ERROR", caught.getMessage());
					// SC.clearPrompt();
					// }
					//
					// public void onSuccess(RequestResult result) {
					//
					// SC.clearPrompt();
					//
					// SessionManager.getInstance().manageSession(result, placeManager);
					// if (result != null) {
					//
					// if (result.getSystemFeedbackDTO() != null) {
					// if (result.getSystemFeedbackDTO().isResponse()) {
					// SC.say("Sucess", result.getSystemFeedbackDTO().getMessage());
					// getView().getSystemMenuPane().getListgrid()
					// .addRecordsToGrid(result.getSystemMenuDTOs());
					// } else {
					// SC.say("ERROR", result.getSystemFeedbackDTO().getMessage());
					// }
					// } else {
					// SC.say("ERROR", "Feedback is null");
					// }
					//
					// } else {
					// SC.say("ERROR", "Unknow error");
					// }
					//
					// }
					// });
				}

			}
		});
	}

	private void loadSystemMenus() {

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(NetworkDataUtil.ACTION, SystemMenuRequestConstant.GET_SYSTEM_MENUS);
		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {
				GWT.log("RESULT SIZE " + result.getSystemMenuDTOs().size());

				getView().getSystemMenuPane().getListgrid().addRecordsToGrid(result.getSystemMenuDTOs());
			}
		});

		// SC.showPrompt("", "", new SwizimaLoader());
		//
		// dispatcher.execute(new RequestAction(RequestConstant.GET_SystemMENU, map),
		// new AsyncCallback<RequestResult>() {
		// public void onFailure(Throwable caught) {
		// System.out.println(caught.getMessage());
		// SC.say("ERROR", caught.getMessage());
		// SC.clearPrompt();
		// }
		//
		// public void onSuccess(RequestResult result) {
		//
		// SC.clearPrompt();
		//
		// SessionManager.getInstance().manageSession(result, placeManager);
		// if (result != null) {
		//
		// getView().getSystemMenuPane().getListgrid().addRecordsToGrid(result.getSystemMenuDTOs());
		//
		// } else {
		// SC.say("ERROR", "Unknow error");
		// }
		//
		// }
		// });

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

	private void loadSystemUserMenu(final MenuButton button) {
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				SystemUserGroupSystemMenuWindow window = new SystemUserGroupSystemMenuWindow();
				getSelectedUnSelectedSystemUserGroupMenu(window);
				saveSystemUserGroupSystemMenu(window);
				window.show();
			}
		});

	}

	private void getSelectedUnSelectedSystemUserGroupMenu(final SystemUserGroupSystemMenuWindow window) {

		if (getView().getUserGroupPane().getListgrid().anySelected()) {

			String id = getView().getUserGroupPane().getListgrid().getSelectedRecord()
					.getAttribute(UserGroupListgrid.ID);

			SystemUserGroupDTO userGroup = new SystemUserGroupDTO();
			userGroup.setId(id);

			LinkedHashMap<String, Object> map = new LinkedHashMap<>();
			map.put(RequestDelimeters.SYSTEM_USER_GROUP_ID, userGroup.getId());
			map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
			map.put(NetworkDataUtil.ACTION,
					SystemUserGroupSystemMenuRequestConstant.GET_SELECTED_UNSELECTED_USER_GROUP_SYSTEM_MENU);
			NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

				@Override
				public void onNetworkResult(RequestResult result) {
					window.getListgrid().addRecordsToGrid(result.getSystemUserGroupSystemMenuDTOs());
					window.getListgrid().selectActiveRecords();
				}
			});

			// dispatcher.execute(new
			// RequestAction(SystemUserGroupSystemMenuRequestConstant.GET_SELECTED_UNSELECTED_USER_GROUP_SYSTEM_MENU
			// , map),
			// new AsyncCallback<RequestResult>() {
			// public void onFailure(Throwable caught) {
			// System.out.println(caught.getMessage());
			// SC.say("ERROR", caught.getMessage());
			// SC.clearPrompt();
			// }
			//
			// public void onSuccess(RequestResult result) {
			//
			// SC.clearPrompt();
			//
			// SessionManager.getInstance().manageSession(result, placeManager);
			// if (result != null) {
			//
			// window.getListgrid().addRecordsToGrid(result.getSystemUserGroupSystemMenuDTOs());
			// window.getListgrid().selectActiveRecords();
			//
			// } else {
			// SC.say("ERROR", "Unknow error");
			// }
			//
			// }
			// });
		} else {
			SC.warn("ERROR", "Please select a group");
		}

	}

	private void saveSystemUserGroupSystemMenu(final SystemUserGroupSystemMenuWindow window) {
		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				List<SystemUserGroupSystemMenuDTO> dtos = new ArrayList<>();

				String groupId = getView().getUserGroupPane().getListgrid().getSelectedRecord()
						.getAttribute(UserGroupListgrid.ID);

				SystemUserGroupDTO userGroup = new SystemUserGroupDTO();
				userGroup.setId(groupId);

				for (ListGridRecord record : window.getListgrid().getRecords()) {
					if (window.getListgrid().isSelected(record)) {

						/**
						 * This item was not selected when the widget loaded But not its selected to be
						 * persisted
						 */
						if (record.getAttribute(SystemUserGroupSystemMenuListgrid.ID) == null) {
							SystemUserGroupSystemMenuDTO dto = new SystemUserGroupSystemMenuDTO();
							SystemMenuDTO systemMenuDTO = new SystemMenuDTO();
							systemMenuDTO.setId(record.getAttribute(SystemUserGroupSystemMenuListgrid.SystemMenuId));
							dto.setSystemMenuDTO(systemMenuDTO);
							dto.setSystemUserGroupDTO(userGroup);
							dto.setSelected(true);

							dtos.add(dto);
						}
					} else {
						/**
						 * This item was once selected , but now user has unselected it
						 *
						 * @param Id
						 *            not null
						 * @return to be deleted
						 */
						if (record.getAttribute(SystemUserGroupSystemMenuListgrid.ID) != null) {
							SystemUserGroupSystemMenuDTO dto = new SystemUserGroupSystemMenuDTO();
							dto.setId(record.getAttribute(SystemUserGroupSystemMenuListgrid.ID));
							SystemMenuDTO systemMenuDTO = new SystemMenuDTO();
							systemMenuDTO.setId(record.getAttribute(SystemUserGroupSystemMenuListgrid.SystemMenuId));
							dto.setSystemMenuDTO(systemMenuDTO);
							dto.setSystemUserGroupDTO(userGroup);
							dto.setSelected(false);

							dtos.add(dto);
						}
					}

					// boolean selected = window.getListgrid().isSelected(record);
					//
					// if (selected) {
					//
					// SystemUserGroupSystemMenuDTO dto = new SystemUserGroupSystemMenuDTO();
					//
					// if (record.getAttribute(SystemUserGroupSystemMenuListgrid.ID) != null) {
					// dto.setId(record.getAttribute(SystemUserGroupSystemMenuListgrid.ID));
					// }
					//
					// SystemMenuDTO systemMenu = new SystemMenuDTO();
					// systemMenu.setId(record.getAttribute(SystemUserGroupSystemMenuListgrid.SystemMenuId));
					//
					// dto.setSystemMenuDTO(systemMenu);
					// dto.setSystemUserGroupDTO(userGroup);
					// dto.setDisabled(false);
					//
					// dtos.add(dto);
					//
					// } else {
					//
					// if (record.getAttribute(SystemUserGroupSystemMenuListgrid.ID) != null) {
					//
					// SystemUserGroupSystemMenuDTO dto = new SystemUserGroupSystemMenuDTO();
					//
					// if (record.getAttribute(SystemUserGroupSystemMenuListgrid.ID) != null) {
					// dto.setId(record.getAttribute(SystemUserGroupSystemMenuListgrid.ID));
					// }
					//
					// SystemMenuDTO systemMenuDTO = new SystemMenuDTO();
					// systemMenuDTO.setId(record.getAttribute(SystemUserGroupSystemMenuListgrid.SystemMenuId));
					//
					// dto.setSystemMenuDTO(systemMenuDTO);
					// dto.setSystemUserGroupDTO(userGroup);
					// dto.setDisabled(true);
					//
					// dtos.add(dto);
					// }
					// }
				}

				if (!dtos.isEmpty()) {

					LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					map.put(SystemUserGroupSystemMenuRequestConstant.DATA, dtos);
					// map.put(RequestDelimeters.SYSTEM_USER_GROUP_ID, userGroup);
					map.put(NetworkDataUtil.ACTION,
							SystemUserGroupSystemMenuRequestConstant.SAVE_USER_GROUP_SYSTEM_MENU);
					NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

						@Override
						public void onNetworkResult(RequestResult result) {
							getSelectedUnSelectedSystemUserGroupMenu(window);
							// window.getListgrid().addRecordsToGrid(result.getSystemUserGroupSystemMenuDTOs());
							// window.getListgrid().selectActiveRecords();
							window.close();
						}
					});

					// map.put(RequestConstant.LOGIN_TOKEN,
					// SessionManager.getInstance().getLoginToken());
					//
					// SC.showPrompt("", "", new SwizimaLoader());
					//
					// dispatcher.execute(new
					// RequestAction(RequestConstant.SAVE_USER_GROUP_SystemMENU, map),
					// new AsyncCallback<RequestResult>() {
					// public void onFailure(Throwable caught) {
					// System.out.println(caught.getMessage());
					// SC.say("ERROR", caught.getMessage());
					// SC.clearPrompt();
					// }
					//
					// public void onSuccess(RequestResult result) {
					//
					// SC.clearPrompt();
					//
					// SessionManager.getInstance().manageSession(result, placeManager);
					// if (result != null) {
					//
					// if (result.getSystemFeedbackDTO() != null) {
					// if (result.getSystemFeedbackDTO().isResponse()) {
					// SC.say("SUCCESS", result.getSystemFeedbackDTO().getMessage(),
					// new BooleanCallback() {
					//
					// @Override
					// public void execute(Boolean value) {
					//
					// if (value) {
					// window.close();
					// }
					//
					// }
					// });
					// } else {
					// SC.say("ERROR", result.getSystemFeedbackDTO().getMessage());
					// }
					// }
					// } else {
					// SC.say("ERROR", "Unknow error");
					// }
					//
					// }
					// });
				} else {
					SC.warn("ERROR", "Please select at least one permission and try again.");
				}

			}
		});
	}

	//// Individual user permissions
	private void addSystemUserPermission(MenuButton permission) {
		permission.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				if (getView().getSystemUserPane().getSystemUserListGrid().anySelected()) {

					final ListGridRecord profileRecord = getView().getSystemUserPane().getSystemUserListGrid()
							.getSelectedRecord();

					String role = profileRecord.getAttribute(SystemUserListGrid.USER_GROUP_LEVEL);

					final SystemUserProfilePermissionWindow window = new SystemUserProfilePermissionWindow();

					loadUserAdministrativeUnits(window, profileRecord.getAttribute(SystemUserListGrid.ID));

					addAdministrationUnit(window, role);

					window.show();

				} else {
					SC.say("Please select user");
				}

			}

		});

	}

	private void loadUserAdministrativeUnits(final SystemUserProfilePermissionWindow window, final String userId) {

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.GET_USER_ADMIN_UNITS, userId);
		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());

		SC.showPrompt("", "", new SwizimaLoader());

		dispatcher.execute(new RequestAction(RequestConstant.GET_USER_ADMIN_UNITS, map),
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

							window.getSystemUserSchoolPane().getAdministrativeUnitListGrid().setDispatcher(dispatcher);
							
							window.getSystemUserSchoolPane().getAdministrativeUnitListGrid()
									.addRecordsToGrid(result.getSystemUserAdministrationUnitDTOs());

						} else {
							SC.say("ERROR", "Unknow error");
						}

					}
				});

	}

	private void addAdministrationUnit(final SystemUserProfilePermissionWindow window, final String role) {
		window.getSystemUserSchoolPane().getAddButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				if (role.equalsIgnoreCase("district")||role.equalsIgnoreCase("Municiple")) {

					final FilterSchoolsRegionDistrictWindow districtWindow = new FilterSchoolsRegionDistrictWindow();

					ComboUtil.loadRegionCombo(districtWindow.getFilterRegionDistrict().getRegionCombo(), dispatcher,
							placeManager, "");

					districtWindow.getFilterRegionDistrict().getRegionCombo().addChangedHandler(new ChangedHandler() {

						@Override
						public void onChanged(ChangedEvent event) {

							ComboUtil.loadDistrictComboByRegion(
									districtWindow.getFilterRegionDistrict().getRegionCombo(),
									districtWindow.getFilterRegionDistrict().getDistrictCombo(), dispatcher,
									placeManager, "");
						}
					});

					onSaveAdministrationUnit(window,districtWindow);

					districtWindow.show();

				} else if (role.equalsIgnoreCase("school")) {

				}

			}
		});
	}

	private void onSaveAdministrationUnit(final SystemUserProfilePermissionWindow window,final FilterSchoolsRegionDistrictWindow districtWindow) {
		districtWindow.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				final ListGridRecord record = getView().getSystemUserPane().getSystemUserListGrid().getSelectedRecord();

				SystemUserAdministrationUnitDTO dto = new SystemUserAdministrationUnitDTO();

				dto.setAdministrationLevel(AdministrationLevel.DISTRICT.getAdministrationLevel());
				dto.setAdminstrationUnitId(
						districtWindow.getFilterRegionDistrict().getDistrictCombo().getValueAsString());

				SystemUserProfileDTO systemUserProfile = new SystemUserProfileDTO();
				systemUserProfile.setId(record.getAttribute(SystemUserListGrid.ID));

				dto.setSystemUserProfile(systemUserProfile);

				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(RequestConstant.SAVE_USER_ADMIN_UNITS, dto);
				map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());

				SC.showPrompt("", "", new SwizimaLoader());

				dispatcher.execute(new RequestAction(RequestConstant.SAVE_USER_ADMIN_UNITS, map),
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

											SC.say("Sucess", result.getSystemFeedbackDTO().getMessage(),new BooleanCallback() {
												
												@Override
												public void execute(Boolean value) {
													 if(value) {
														 window.getSystemUserSchoolPane().getAdministrativeUnitListGrid().setDispatcher(dispatcher);
													 }
													
												}
											});
											  

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
		});
	}

}