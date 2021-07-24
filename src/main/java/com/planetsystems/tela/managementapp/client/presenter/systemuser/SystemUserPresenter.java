package com.planetsystems.tela.managementapp.client.presenter.systemuser;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import com.planetsystems.tela.dto.GeneralUserDetailDTO;
import com.planetsystems.tela.dto.SchoolDTO;
import com.planetsystems.tela.dto.SystemMenuDTO;
import com.planetsystems.tela.dto.SystemUserDTO;
import com.planetsystems.tela.dto.SystemUserGroupDTO;
import com.planetsystems.tela.dto.SystemUserGroupSystemMenuDTO;
import com.planetsystems.tela.dto.SystemUserProfileDTO;
import com.planetsystems.tela.dto.SystemUserProfileSchoolDTO;
import com.planetsystems.tela.dto.enums.NavigationMenuDTO;
import com.planetsystems.tela.dto.response.SystemResponseDTO;
import com.planetsystems.tela.managementapp.client.gin.SessionManager;
import com.planetsystems.tela.managementapp.client.place.NameTokens;
import com.planetsystems.tela.managementapp.client.presenter.comboutils.ComboUtil2;
import com.planetsystems.tela.managementapp.client.presenter.main.MainPresenter;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkDataUtil;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkDataUtil2;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkResult;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkResult2;
import com.planetsystems.tela.managementapp.client.presenter.systemuser.group.SystemUserGroupSystemMenuListgrid;
import com.planetsystems.tela.managementapp.client.presenter.systemuser.group.SystemUserGroupSystemMenuWindow;
import com.planetsystems.tela.managementapp.client.presenter.systemuser.group.SystemUserGroupWindow;
import com.planetsystems.tela.managementapp.client.presenter.systemuser.group.UserGroupListgrid;
import com.planetsystems.tela.managementapp.client.presenter.systemuser.group.UserGroupPane;
import com.planetsystems.tela.managementapp.client.presenter.systemuser.manage.FilterSchoolsRegionDistrictWindow;
import com.planetsystems.tela.managementapp.client.presenter.systemuser.manage.ProfileSchoolsListGrid;
import com.planetsystems.tela.managementapp.client.presenter.systemuser.manage.SelectProfileSchoolListGrid;
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
import com.planetsystems.tela.managementapp.shared.MyRequestAction;
import com.planetsystems.tela.managementapp.shared.MyRequestResult;
import com.planetsystems.tela.managementapp.shared.RequestConstant;
import com.planetsystems.tela.managementapp.shared.RequestDelimeters;
import com.planetsystems.tela.managementapp.shared.RequestResult;
import com.planetsystems.tela.managementapp.shared.requestcommands.SystemMenuCommands;
import com.planetsystems.tela.managementapp.shared.requestcommands.SystemUserCommand;
import com.planetsystems.tela.managementapp.shared.requestcommands.SystemUserGroupCommand;
import com.planetsystems.tela.managementapp.shared.requestcommands.SystemUserGroupSystemMenuCommand;
import com.planetsystems.tela.managementapp.shared.requestcommands.SystemUserProfileSchoolCommand;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
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
					MenuButton delete = new MenuButton("Delete");
					MenuButton details = new MenuButton("Details");
					MenuButton permission = new MenuButton("Manage User");

					List<MenuButton> buttons = new ArrayList<>();
					buttons.add(newButton);
					buttons.add(edit);
					buttons.add(delete);
					buttons.add(details);
					buttons.add(permission);

					addSystemUserPermission(permission);

					getView().getControlsPane().addMenuButtons("System user details", buttons);

					onNewButtonCLicked(newButton);

					update(edit);
					 delete(delete);
					// onDetailsButtonCLicked(details);
				
					getAllSystemUserProfiles();
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

	//// personal user permissions
	private void addSystemUserPermission(MenuButton permission) {
		permission.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (getView().getSystemUserPane().getSystemUserListGrid().anySelected()) {
					final ListGridRecord profileRecord = getView().getSystemUserPane().getSystemUserListGrid()
							.getSelectedRecord();
					final SystemUserProfilePermissionWindow window = new SystemUserProfilePermissionWindow();
		

					window.setTitle("Add Permission to " + profileRecord.getAttribute(SystemUserListGrid.EMAIL));
					

					getProfileSchoolsBySystemUserProfile(window, profileRecord);

					window.show();
					showFilterSchoolsRegionDistrictWindow(window);
					
					
					window.getSystemUserSchoolPane().getUserSchoolButton().addClickHandler(new ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							getProfileSchoolsBySystemUserProfile(window, profileRecord);
						}
					});

					
					
					window.getSystemUserSchoolPane().getDeleteButton().addClickHandler(new ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							if (window.getSystemUserSchoolPane().getProfileSchoolsListGrid().anySelected()) {
								SC.confirm("Are Sure to remove this schools ", new BooleanCallback() {

									@Override
									public void execute(Boolean value) {
										if (value) {
											ListGridRecord[] records = window.getSystemUserSchoolPane()
													.getProfileSchoolsListGrid().getSelectedRecords();
											List<SystemUserProfileSchoolDTO> dtos = new ArrayList<>();
											
											for (int i = 0; i < records.length; i++) {
												GWT.log("REQUESTS ID "+ records[i].getAttribute(ProfileSchoolsListGrid.ID));
												dtos.add(new SystemUserProfileSchoolDTO(records[i].getAttribute(ProfileSchoolsListGrid.ID)));
											}
											
											LinkedHashMap<String, Object> map = new LinkedHashMap<>();
											map.put(MyRequestAction.COMMAND , SystemUserProfileSchoolCommand.DELETE_SYSTEM_USER_PROFILE_SCHOOLS);
											
											map.put(MyRequestAction.DATA, dtos);
											
											

											NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map,	new NetworkResult2() {

														@Override
														public void onNetworkResult(MyRequestResult result) {
															if (result != null) {
																SystemResponseDTO<String> responseDTO = result.getResponseText();
																if (responseDTO.isStatus()) {
																	getProfileSchoolsBySystemUserProfile(window, profileRecord);
																} else {
																	SC.say(responseDTO.getMessage());
																}
															}
														
														}
													});

										}
									}
								});

							} else {
								SC.say("Select Schools to delete from your account");
							}
						}
					});

				} else {
					SC.say("Please a user to add permissions");
				}

			}

		});

	}

	public void getProfileSchoolsBySystemUserProfile(final SystemUserProfilePermissionWindow window,
			ListGridRecord profileRecord) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestDelimeters.SYSTEM_USER_PROFILE_ID, profileRecord.getAttributeAsObject(SystemUserListGrid.ID));
		map.put(MyRequestAction.COMMAND, SystemUserProfileSchoolCommand.GET_SYSTEM_USER_PROFILE_SCHOOL_SCHOOLS_BY_ID);

		NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

			@Override
			public void onNetworkResult(MyRequestResult result) {
				if (result != null) {
					SystemResponseDTO<List<SystemUserProfileSchoolDTO>> responseDTO = result.getSystemUserProfileSchoolResponseList();
					if (responseDTO.isStatus()) {
						if(responseDTO.getData() != null)
							window.getSystemUserSchoolPane().getProfileSchoolsListGrid().addRecordsToGrid(responseDTO.getData());
					} else {
						SC.say(responseDTO.getMessage());
					}
				}
				
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
				ComboUtil2.loadRegionCombo(regionDistrictWindow.getFilterRegionDistrict().getRegionCombo(), dispatcher,
						placeManager, defaultValue);
				
				regionDistrictWindow.getFilterRegionDistrict().getRegionCombo().addChangedHandler(new ChangedHandler() {

					@Override
					public void onChanged(ChangedEvent event) {
						ComboUtil2.loadDistrictComboByRegion(
								regionDistrictWindow.getFilterRegionDistrict().getRegionCombo(),
								regionDistrictWindow.getFilterRegionDistrict().getDistrictCombo(), dispatcher,
								placeManager, defaultValue);
					}
				});

				regionDistrictWindow.getLoadSchoolsButton().addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						if (regionDistrictWindow.getFilterRegionDistrict().getDistrictCombo()
								.getValueAsString() != null) {
							final SelectProfileSchoolWindow profileSchoolWindow = new SelectProfileSchoolWindow();

							profileSchoolWindow.show();
							LinkedHashMap<String, Object> map = new LinkedHashMap<>();
							
							map.put(MyRequestAction.COMMAND ,
									SystemUserProfileSchoolCommand.GET_NOT_PROFILE_SCHOOLS_BY_PROFILE_DISTRICT);
							map.put(RequestDelimeters.SYSTEM_USER_PROFILE_ID,
									profileRecord.getAttribute(SystemUserListGrid.ID));
							map.put(RequestDelimeters.DISTRICT_ID, regionDistrictWindow.getFilterRegionDistrict()
									.getDistrictCombo().getValueAsString());
							
							NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

								@Override
								public void onNetworkResult(MyRequestResult result) {
									
									if (result != null) {
										SystemResponseDTO<List<SchoolDTO>> responseDTO = result.getSchoolResponseList();
										if (responseDTO.isStatus()) {
//										
									if (responseDTO.getData() != null) {
										profileSchoolWindow.getSelectProfileSchoolListGrid().addRecordsToGrid(responseDTO.getData());
										profileSchoolWindow.getAddSchoolsButton().enable();
									}
										
										} else {
											SC.say(responseDTO.getMessage());
										}
									}
		
								}
							});

							//saving system user profile schools

							profileSchoolWindow.getAddSchoolsButton().addClickHandler(new ClickHandler() {

								@Override
								public void onClick(ClickEvent event) {
									if (profileSchoolWindow.getSelectProfileSchoolListGrid().anySelected()) {

										ListGridRecord[] records = profileSchoolWindow.getSelectProfileSchoolListGrid()
												.getSelectedRecords();
										List<SystemUserProfileSchoolDTO> dtos = new ArrayList<>();
										
										for (int i = 0; i < records.length; i++) {
											SystemUserProfileSchoolDTO dto = new SystemUserProfileSchoolDTO();
											dto.setSchoolDTO(new SchoolDTO(records[i].getAttribute(SelectProfileSchoolListGrid.SCHOOL_ID)));
											dto.setSystemUserProfileDTO(new SystemUserProfileDTO(profileRecord.getAttribute(SystemUserListGrid.ID)));
											
											dtos.add(dto);
										}
										LinkedHashMap<String, Object> map = new LinkedHashMap<>();
										map.put(MyRequestAction.COMMAND , SystemUserProfileSchoolCommand.SAVE_SYSTEM_USER_PROFILE_SCHOOLS);
										map.put(MyRequestAction.DATA, dtos);
										
									
										NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

											@Override
											public void onNetworkResult(MyRequestResult result) {
												if (result != null) {
													SystemResponseDTO<String> responseDTO = result.getResponseText();
													if (responseDTO.isStatus()) {
															// close windows
															//getProfileSchoolsBySystemUserProfile(profilePermissionWindow, profileRecord);
															getAllSystemUserProfiles();
														    profilePermissionWindow.close();
															regionDistrictWindow.close();
															profileSchoolWindow.close();
													} else {
														SC.say(responseDTO.getMessage());
													}
												}
												
												

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

				window.getSaveButton().addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						if (checkIfNoSystemUserGroupWindowFieldIsEmpty(window)) {
							SystemUserGroupDTO dto = new SystemUserGroupDTO();
							dto.setCode(window.getCodeField().getValueAsString());

							dto.setDescription(window.getDescriptionField().getValueAsString());
							dto.setName(window.getNameField().getValueAsString());


							dto.setDefaultGroup(Boolean.parseBoolean(window.getDefaultRoleRadio().getValueAsString()));


							dto.setReceiveAlerts(Boolean.parseBoolean(window.getReceiveAlertRadio().getValueAsString()));

							dto.setAdministrativeRole(Boolean.parseBoolean(window.getAdministrativeRoleRadio().getValueAsString()));

							LinkedHashMap<String, Object> map = new LinkedHashMap<>();
							map.put(MyRequestAction.DATA, dto);
							map.put(MyRequestAction.COMMAND, SystemUserGroupCommand.SAVE_SYSTEM_USER_GROUP);
						
							userGroupResponse(map);
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
	
	private void userGroupResponse(LinkedHashMap<String, Object> map) {
		NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

			@Override
			public void onNetworkResult(MyRequestResult result) {
				if (result != null) {
					SystemResponseDTO<SystemUserGroupDTO> responseDTO = result.getSystemUserGroupResponse();
					if (responseDTO.isStatus()) {
						//clearSystemGroupWindow(window);
						SC.say(responseDTO.getMessage());
						getAllSystemUserGroups();

					} else {
						SC.say(responseDTO.getMessage());
					}
				}
				
			}
		});
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
				if (getView().getUserGroupPane().getListgrid().anySelected()) {
					SC.ask("Confirm", "Are you sure you want to delete the selected record", new BooleanCallback() {

						@Override
						public void execute(Boolean value) {
							if (value) {
								ListGridRecord record = getView().getUserGroupPane().getListgrid().getSelectedRecord();
								LinkedHashMap<String, Object> map = new LinkedHashMap<>();
								map.put(RequestDelimeters.SYSTEM_USER_GROUP_ID, record.getAttributeAsString("id"));
								map.put(MyRequestAction.COMMAND, SystemUserGroupCommand.DELETE_SYSTEM_USER_GROUP);

								NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

									@Override
									public void onNetworkResult(MyRequestResult result) {
										
										if (result != null) {
											SystemResponseDTO<String> responseDTO = result.getResponseText();
											if (responseDTO.isStatus()) {
												SC.say("SUCCESS", responseDTO.getMessage());
												getAllSystemUserGroups();
											} else {
												SC.say(responseDTO.getMessage());
											}
										}
			
									}
								});
							}
						}
					});
				} else {
					SC.warn("Please check atleast one record");
				}
			}
		});
	}


	private void getAllSystemUserGroups() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(MyRequestAction.COMMAND, SystemUserGroupCommand.GET_ALL_SYSTEM_USER_GROUPS);
		
		NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

			@Override
			public void onNetworkResult(MyRequestResult result) {
				if (result != null) {
					SystemResponseDTO<List<SystemUserGroupDTO>> responseDTO = result.getSystemUserGroupResponseList();
					if (responseDTO.isStatus()) {
						if(responseDTO.getData() !=null) {
							getView().getUserGroupPane().getListgrid().addRecordsToGrid(responseDTO.getData());
						}
					
					} else {
						SC.say(responseDTO.getMessage());
					}
				}
				
			}
		});
	}

	private void onUpdateUserGroup(final SystemUserGroupWindow window) {

		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				String id = getView().getUserGroupPane().getListgrid().getSelectedRecord()
						.getAttribute(UserGroupListgrid.ID);

				SystemUserGroupDTO dto = new SystemUserGroupDTO();
				dto.setId(id);

				dto.setCode(window.getCodeField().getValueAsString());

				dto.setDescription(window.getDescriptionField().getValueAsString());
				dto.setName(window.getNameField().getValueAsString());
				dto.setDefaultGroup(Boolean.parseBoolean(window.getDefaultRoleRadio().getValueAsString()));
				dto.setReceiveAlerts(Boolean.parseBoolean(window.getReceiveAlertRadio().getValueAsString()));

				dto.setAdministrativeRole(Boolean.parseBoolean(window.getAdministrativeRoleRadio().getValueAsString()));

				SC.showPrompt("", "", new SwizimaLoader());

				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(MyRequestAction.DATA, dto);
				map.put(MyRequestAction.COMMAND, SystemUserGroupCommand.UPDATE_SYSTEM_USER_GROUP);
	
				userGroupResponse(map);
//
//				NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {
//
//					@Override
//					public void onNetworkResult(MyRequestResult result) {
//						if (result != null) {
//							SystemResponseDTO<SystemUserGroupDTO> responseDTO = result.getSystemUserGroupResponse();
//							if (responseDTO.isStatus()) {
//							//	clearSystemGroupWindow(window);
//							//	window.close();
//								SC.say("SUCCESS", responseDTO.getMessage());
//								getAllSystemUserGroups();
//							} else {
//								SC.say(responseDTO.getMessage());
//							}
//						}
//			
//						
//					}
//				});
				


			}
		});

	}
	
//	
//@Deprecated
//	private void onDeleteUserGroup() {
//
//		if (getView().getUserGroupPane().getListgrid().anySelected()) {
//
//			SC.ask("Confrim", "Are you sure you want to delete the selected record?", new BooleanCallback() {
//
//				@Override
//				public void execute(Boolean value) {
//					if (value) {
//
//						String id = getView().getUserGroupPane().getListgrid().getSelectedRecord()
//								.getAttribute(UserGroupListgrid.ID);
//
//						SystemUserGroupDTO userGroup = new SystemUserGroupDTO();
//						userGroup.setId(id);
//
//						SC.showPrompt("", "", new SwizimaLoader());
//
//						LinkedHashMap<String, Object> map = new LinkedHashMap<>();
//						map.put(RequestConstant.DELETE_USER_GROUP, userGroup);
//						map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
//
//						dispatcher.execute(new RequestAction(RequestConstant.DELETE_USER_GROUP, map),
//								new AsyncCallback<RequestResult>() {
//									public void onFailure(Throwable caught) {
//										System.out.println(caught.getMessage());
//										SC.clearPrompt();
//										SC.warn("ERROR", caught.getMessage());
//									}
//
//									public void onSuccess(RequestResult result) {
//
//										SC.clearPrompt();
//
//										SessionManager.getInstance().manageSession(result, placeManager);
//										if (result != null) {
//											if (result.getSystemFeedbackDTO().isResponse()) {
//
//												SC.say("Message", result.getSystemFeedbackDTO().getMessage());
//
//												getView().getUserGroupPane().getListgrid()
//														.addRecordsToGrid(result.getSystemUserGroupDTOs());
//
//											} else {
//												SC.warn("ERROR", result.getSystemFeedbackDTO().getMessage());
//											}
//
//										} else {
//											SC.say("ERROR", "Unknow error");
//										}
//
//									}
//								});
//
//					}
//
//				}
//			});
//
//		} else {
//			SC.warn("ERROR", "Please select record to delete");
//		}
//
//	}

	private void loadFieldsToEdit(final SystemUserGroupWindow window, ListGridRecord systemUserGroupRecord) {

		window.getCodeField().setValue(systemUserGroupRecord.getAttribute(UserGroupListgrid.CODE));
		window.getDescriptionField().setValue(systemUserGroupRecord.getAttribute(UserGroupListgrid.Description));
		window.getNameField().setValue(systemUserGroupRecord.getAttribute(UserGroupListgrid.Role));

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
				ComboUtil2.loadSystemUserGroupCombo(window.getSystemUserGroupCombo(), dispatcher, placeManager, null);
				window.show();
				saveSystemUserProfile(window);
			}

		});

	}
	


	public void loadEnabledRadioGroupItem(SystemUserProfileWindow window) {
		Map<Boolean, String> valueMap = new LinkedHashMap<Boolean, String>();
		valueMap.put(true, "Yes");
		valueMap.put(false, "No");

		window.getEnabledRadioGroupItem().setValueMap(valueMap);
	}

//	@Deprecated
//	public void loadGenderComboBox(SystemUserProfileWindow window) {
//		Map<String, String> valueMap = new LinkedHashMap<String, String>();
//		valueMap.put("female", "Female");
//		valueMap.put("male", "Male");
//
//		//window.getGenderCombo().setValueMap(valueMap);
//	}

	private void saveSystemUserProfile(final SystemUserProfileWindow window) {
		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (checkIfNoSystemUserWindowFieldIsEmpty(window)) {

					SystemUserProfileDTO dto = new SystemUserProfileDTO();
					dto.setCreatedDateTime(dateTimeFormat.format(new Date()));
					SystemUserDTO systemUserDTO = new SystemUserDTO();
					systemUserDTO.setEnabled(Boolean.parseBoolean(window.getEnabledRadioGroupItem().getValueAsString()));
					systemUserDTO.setUserName(window.getEmailField().getValueAsString());
					systemUserDTO.setCreatedDateTime(dateTimeFormat.format(new Date()));

					// dto.setPassword(window.getPasswordField().getValueAsString());

					GeneralUserDetailDTO generalUserDetailDTO = new GeneralUserDetailDTO();
//					generalUserDetailDTO.setFirstName(window.getFirstNameField().getValueAsString());
//					generalUserDetailDTO.setLastName(window.getLastNameField().getValueAsString());
					generalUserDetailDTO.setEmail(window.getEmailField().getValueAsString());
//					generalUserDetailDTO.setDob(dateFormat.format(window.getDobItem().getValueAsDate()));
//					generalUserDetailDTO.setGender(window.getGenderCombo().getValueAsString());
//					generalUserDetailDTO.setNameAbbrev(window.getNameAbbrevField().getValueAsString());
//					generalUserDetailDTO.setNationalId(window.getNationalIdField().getValueAsString());
//					generalUserDetailDTO.setPhoneNumber(window.getPhoneNumberField().getValueAsString());

					SystemUserGroupDTO systemUserGroupDTO = new SystemUserGroupDTO();
					systemUserGroupDTO.setId(window.getSystemUserGroupCombo().getValueAsString());


					dto.setSystemUserDTO(systemUserDTO);
					dto.setSystemUserGroupDTO(systemUserGroupDTO);
					dto.setGeneralUserDetailDTO(generalUserDetailDTO);

						LinkedHashMap<String, Object> map = new LinkedHashMap<>();
						map.put(MyRequestAction.COMMAND, SystemUserCommand.SAVE_SYSTEM_USER);
						map.put(MyRequestAction.DATA, dto);

						NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

							@Override
							public void onNetworkResult(MyRequestResult result) {
								if (result != null) {
									SystemResponseDTO<SystemUserProfileDTO> responseDTO = result.getSystemUserProfileResponse();
									if (responseDTO.isStatus()) {
										//clearSystemUserWindowFields(window);
										//window.close();
										getAllSystemUserProfiles();
									} else {
										SC.say(responseDTO.getMessage());
									}
								}
							
							}
						});


				}else {
					SC.say("Fill all the fields");
				}

			}
		});
	}

//	@Deprecated
//	private boolean checkIfProfileFieldsNotEmpty(SystemUserProfileWindow window) {
//		boolean status = true;
//
////		if (window.getFirstNameField().getValueAsString() == null)
////			status = false;
//
////		if (window.getLastNameField().getValueAsString() == null)
////			status = false;
//
////		if (window.getPhoneNumberField().getValueAsString() == null)
////			status = false;
//
//		if (window.getEmailField().getValueAsString() == null)
//			status = false;
//
////		if (window.getDobItem().getValueAsDate() == null)
////			status = false;
//
////		if (window.getNationalIdField().getValueAsString() == null)
////			status = false;
////
////		if (window.getGenderCombo().getValueAsString() == null)
////			status = false;
//
//		if (window.getSystemUserGroupCombo().getValueAsString() == null)
//			status = false;
//
//		// if(window.getNameAbbrevField().getValueAsString() == null) status = false;
//
//		return status;
//	}
	
	

	private void getAllSystemUserProfiles() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(MyRequestAction.COMMAND , SystemUserCommand.GET_ALL_SYSTEM_USERS);

		NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

			@Override
			public void onNetworkResult(MyRequestResult result) {
				if (result != null) {
					SystemResponseDTO<List<SystemUserProfileDTO>> responseDTO = result.getSystemUserProfileResponseList();
					if (responseDTO.isStatus()) {
					   if(responseDTO.getData() != null)
						   getView().getSystemUserPane().getSystemUserListGrid().addRecordsToGrid(responseDTO.getData());
					} else {
						SC.say(responseDTO.getMessage());
					}
				}
				
				
			}
		});
	}

	private void clearSystemUserWindowFields(SystemUserProfileWindow window) {
//		window.getFirstNameField().clearValue();
//		window.getLastNameField().clearValue();
//		window.getPhoneNumberField().clearValue();
		window.getEmailField().clearValue();
//		window.getDobItem().clearValue();
//		window.getNationalIdField().clearValue();
//		window.getGenderCombo().clearValue();
//		window.getNameAbbrevField().clearValue();
		window.getEnabledRadioGroupItem().clearValue();
	}

	protected boolean checkIfNoSystemUserWindowFieldIsEmpty(SystemUserProfileWindow window) {
		boolean flag = true;

//		if (window.getFirstNameField().getValueAsString() == null)
//			flag = false;
//
//		if (window.getLastNameField().getValueAsString() == null)
//			flag = false;
//
//		if (window.getPhoneNumberField().getValueAsString() == null)
//			flag = false;

		if (window.getEmailField().getValueAsString() == null)
			flag = false;

//		if (window.getDobItem().getValueAsDate() == null)
//			flag = false;
//
//		if (window.getNationalIdField().getValueAsString() == null)
//			flag = false;
//
//		if (window.getGenderCombo().getValueAsString() == null)
//			flag = false;

		return flag;
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

//		for (SubMenuItemDTO dto : SubMenuItemDTO.values()) {
//			valueMap.put(dto.getSystemMenuItem(), dto.getSystemMenuItem());
//
//		}

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
					map.put(MyRequestAction.DATA, dtos);
					map.put(MyRequestAction.COMMAND, SystemMenuCommands.SAVE_SYSTEM_MENU);

					NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

						@Override
						public void onNetworkResult(MyRequestResult result) {
							if (result != null) {
								SystemResponseDTO<String> responseDTO = result.getResponseText();
								if (responseDTO.isStatus()) {
									SC.say(responseDTO.getMessage());
									loadSystemMenus();
									//window.close();
								} else {
									SC.say(responseDTO.getMessage());
								}

							}
						
						}
					});
				}

			}
		});
	}

	private void loadSystemMenus() {

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(MyRequestAction.COMMAND, SystemMenuCommands.GET_SYSTEM_MENUS);
		NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

			@Override
			public void onNetworkResult(MyRequestResult result) {
				if (result != null) {
					SystemResponseDTO<List<SystemMenuDTO>> responseDTO = result.getSystemMenuResponseList();
					if (responseDTO.isStatus()) {
						if(responseDTO.getData() != null)
							getView().getSystemMenuPane().getListgrid().addRecordsToGrid(responseDTO.getData());
					} else {
						SC.say(responseDTO.getMessage());
					}
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
									map.put(MyRequestAction.DATA, dtos);
									map.put(MyRequestAction.COMMAND, SystemMenuCommands.DELETE_SYSTEM_MENUS);

									SC.showPrompt("", "", new SwizimaLoader());

									NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {
										
										@Override
										public void onNetworkResult(MyRequestResult result) {
											SC.clearPrompt();
											if (result != null) {
												SystemResponseDTO<String> responseDTO = result.getResponseText();
												if (responseDTO.isStatus()) {
													SC.say(responseDTO.getMessage());
													loadSystemMenus();	
												} else {
													SC.say(responseDTO.getMessage());
												}
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
					SystemUserGroupSystemMenuCommand.GET_SELECTED_UNSELECTED_USER_GROUP_SYSTEM_MENU);
			NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

				@Override
				public void onNetworkResult(RequestResult result) {
					window.getListgrid().addRecordsToGrid(result.getSystemUserGroupSystemMenuDTOs());
					window.getListgrid().selectActiveRecords();
				}
			});

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
						 * @param Id not null
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
				}

				if (!dtos.isEmpty()) {

					LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					map.put(SystemUserGroupSystemMenuCommand.DATA, dtos);
//					map.put(RequestDelimeters.SYSTEM_USER_GROUP_ID, userGroup);
					map.put(NetworkDataUtil.ACTION,
							SystemUserGroupSystemMenuCommand.SAVE_USER_GROUP_SYSTEM_MENU);
					NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

						@Override
						public void onNetworkResult(RequestResult result) {
							getSelectedUnSelectedSystemUserGroupMenu(window);
//							window.getListgrid().addRecordsToGrid(result.getSystemUserGroupSystemMenuDTOs());
//							window.getListgrid().selectActiveRecords();	
							window.close();
						}
					});

				} else {
					SC.warn("ERROR", "Please select at least one permission and try again.");
				}

			}
		});
	}
	
	
	
	
	private void delete(MenuButton deleteBtn) {
		deleteBtn.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (getView().getSystemUserPane().getSystemUserListGrid().anySelected()) {
					SC.ask("Confirm", "Are you sure you want to delete the selected record", new BooleanCallback() {

						@Override
						public void execute(Boolean value) {
							if (value) {
								ListGridRecord record = getView().getSystemUserPane().getSystemUserListGrid().getSelectedRecord();
								LinkedHashMap<String, Object> map = new LinkedHashMap<>();
								map.put(RequestDelimeters.SYSTEM_USER_PROFILE_ID, record.getAttributeAsString(SystemUserListGrid.ID));
								map.put(MyRequestAction.COMMAND, SystemUserCommand.DELETE_SYSTEM_USER);

								NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

									@Override
									public void onNetworkResult(MyRequestResult result) {
										
										if (result != null) {
											SystemResponseDTO<String> responseDTO = result.getResponseText();
											if (responseDTO.isStatus()) {
												SC.say("SUCCESS", responseDTO.getMessage());
												getAllSystemUserProfiles();
											} else {
												SC.say(responseDTO.getMessage());
											}
										}
			
									}
								});
							}
						}
					});
				} else {
					SC.warn("Please check atleast one record");
				}
			}
		});
		
	}
	
	
	private void update(MenuButton edit) {
		edit.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				editProfile();
			}

		});
	}
	
	
	
	private void editProfile() {
		final SystemUserProfileWindow window = new SystemUserProfileWindow();
		loadEnabledRadioGroupItem(window);
	//	loadGenderComboBox(window);
//		getView().getSystemUserPane().getSystemUserListGrid().getAttribute(SystemUserListGrid.)
		
		window.getEmailField().setValue(getView().getSystemUserPane().getSystemUserListGrid().getAttribute(SystemUserListGrid.EMAIL));
		
		ComboUtil2.loadSystemUserGroupCombo(window.getSystemUserGroupCombo(), dispatcher, placeManager, null);
		window.show();
		
		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (checkIfNoSystemUserWindowFieldIsEmpty(window)) {

					SystemUserProfileDTO dto = new SystemUserProfileDTO();
					dto.setCreatedDateTime(dateTimeFormat.format(new Date()));
					SystemUserDTO systemUserDTO = new SystemUserDTO();
					systemUserDTO.setEnabled(Boolean.parseBoolean(window.getEnabledRadioGroupItem().getValueAsString()));
					systemUserDTO.setUserName(window.getEmailField().getValueAsString());
					systemUserDTO.setCreatedDateTime(dateTimeFormat.format(new Date()));

					// dto.setPassword(window.getPasswordField().getValueAsString());

					GeneralUserDetailDTO generalUserDetailDTO = new GeneralUserDetailDTO();
//					generalUserDetailDTO.setFirstName(window.getFirstNameField().getValueAsString());
//					generalUserDetailDTO.setLastName(window.getLastNameField().getValueAsString());
					generalUserDetailDTO.setEmail(window.getEmailField().getValueAsString());
//					generalUserDetailDTO.setDob(dateFormat.format(window.getDobItem().getValueAsDate()));
//					generalUserDetailDTO.setGender(window.getGenderCombo().getValueAsString());
//					generalUserDetailDTO.setNameAbbrev(window.getNameAbbrevField().getValueAsString());
//					generalUserDetailDTO.setNationalId(window.getNationalIdField().getValueAsString());
//					generalUserDetailDTO.setPhoneNumber(window.getPhoneNumberField().getValueAsString());

					SystemUserGroupDTO systemUserGroupDTO = new SystemUserGroupDTO();
					systemUserGroupDTO.setId(window.getSystemUserGroupCombo().getValueAsString());


					dto.setSystemUserDTO(systemUserDTO);
					dto.setSystemUserGroupDTO(systemUserGroupDTO);
					dto.setGeneralUserDetailDTO(generalUserDetailDTO);

						LinkedHashMap<String, Object> map = new LinkedHashMap<>();
						map.put(MyRequestAction.COMMAND, SystemUserCommand.UPDATE_SYSTEM_USER);
						map.put(MyRequestAction.DATA, dto);
						
						GWT.log("PROFILE "+dto);

						NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

							@Override
							public void onNetworkResult(MyRequestResult result) {
								if (result != null) {
									SystemResponseDTO<SystemUserProfileDTO> responseDTO = result.getSystemUserProfileResponse();
									if (responseDTO.isStatus()) {
										//clearSystemUserWindowFields(window);
										//window.close();
										SC.say("SUCCESS", responseDTO.getMessage());
										getAllSystemUserProfiles();
									} else {
										SC.say(responseDTO.getMessage());
									}
								}
					
								
							}
						});
						
						
				}else {
					SC.say("Fill all the fields");
				}

			}
		});
		
	}

}