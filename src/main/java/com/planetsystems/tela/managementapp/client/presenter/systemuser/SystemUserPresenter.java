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
import com.planetsystems.tela.dto.SystemUserDTO;
import com.planetsystems.tela.dto.SystemUserGroupDTO;
import com.planetsystems.tela.dto.SystemUserGroupSystemMenuDTO;
import com.planetsystems.tela.dto.SystemUserProfileDTO;
import com.planetsystems.tela.dto.enums.SubMenuItemDTO;
import com.planetsystems.tela.managementapp.client.gin.SessionManager;
import com.planetsystems.tela.managementapp.client.place.NameTokens;
import com.planetsystems.tela.managementapp.client.presenter.comboutils.ComboUtil;
import com.planetsystems.tela.managementapp.client.presenter.main.MainPresenter;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkDataUtil;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkResult;
import com.planetsystems.tela.managementapp.client.presenter.schoolcategory.school.SchoolListGrid;
import com.planetsystems.tela.managementapp.client.presenter.systemuser.group.SystemUserGroupSystemMenuListgrid;
import com.planetsystems.tela.managementapp.client.presenter.systemuser.group.SystemUserGroupSystemMenuWindow;
import com.planetsystems.tela.managementapp.client.presenter.systemuser.group.UserGroupListgrid;
import com.planetsystems.tela.managementapp.client.presenter.systemuser.group.UserGroupPane;
import com.planetsystems.tela.managementapp.client.presenter.systemuser.group.SystemUserGroupWindow;
import com.planetsystems.tela.managementapp.client.presenter.systemuser.manage.FilterSchoolsRegionDistrictWindow;
import com.planetsystems.tela.managementapp.client.presenter.systemuser.manage.SelectProfileSchoolWindow;
import com.planetsystems.tela.managementapp.client.presenter.systemuser.manage.SystemUserProfilePermissionWindow;
import com.planetsystems.tela.managementapp.client.presenter.systemuser.menu.SystemMenuListgrid;
import com.planetsystems.tela.managementapp.client.presenter.systemuser.menu.SystemMenuPane;
import com.planetsystems.tela.managementapp.client.presenter.systemuser.menu.SystemMenuWindow;
import com.planetsystems.tela.managementapp.client.presenter.systemuser.profile.SystemUserListGrid;
import com.planetsystems.tela.managementapp.client.presenter.systemuser.profile.SystemUserPane;
import com.planetsystems.tela.managementapp.client.presenter.systemuser.profile.SystemUserWindow;
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
					//editSystemUserGroup(edit);
					//deletUserUserGroup(delete);
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

					// onUpdateButtonCLicked(edit);
					// onDeleteButtonCLicked(delete);
					// onDetailsButtonCLicked(details);
					loadUserDetails();
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
	
	////personal user permissions
	private void addSystemUserPermission(MenuButton permission) {
      permission.addClickHandler(new ClickHandler() {
		
		@Override
		public void onClick(ClickEvent event) {
            if (getView().getSystemUserPane().getSystemUserListGrid().anySelected()) {
            	final ListGridRecord profileRecord = getView().getSystemUserPane().getSystemUserListGrid().getSelectedRecord();
            	final SystemUserProfilePermissionWindow window = new SystemUserProfilePermissionWindow();
            	
            	window.setTitle("Add Permission to "+profileRecord.getAttribute(SystemUserListGrid.EMAIL));
         
            	getSchoolsBySystemUserProfile(window, profileRecord);
            	
            	
    			window.show();	
    			showFilterSchoolsRegionDistrictWindow(window);
    			window.getSystemUserSchoolPane().getUserSchoolButton().addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						getSchoolsBySystemUserProfile(window , profileRecord);
					}
				});
    			
    			window.getSystemUserSchoolPane().getDeleteButton().addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						if (window.getSystemUserSchoolPane().getSchoolDistrictListGrid().anySelected()) {
							SC.confirm("Are Sure to remove this schools ", new BooleanCallback() {
								
								@Override
								public void execute(Boolean value) {
									if (value) {
										ListGridRecord[] records = window.getSystemUserSchoolPane().getSchoolDistrictListGrid().getSelectedRecords();
										List<SchoolDTO> schoolDTOs = new ArrayList<SchoolDTO>();
										for (int i = 0; i < records.length; i++) {
											schoolDTOs.add(new SchoolDTO(records[i].getAttribute(SchoolListGrid.ID)));
										}
										LinkedHashMap<String, Object> map = new LinkedHashMap<>();
										map.put(NetworkDataUtil.ACTION	, RequestConstant.DELETE_SYSTEM_USER_PROFILE_SCHOOLS_PROFILE);
										map.put(RequestDelimeters.SYSTEM_USER_PROFILE_ID, profileRecord.getAttribute(SystemUserListGrid.ID));
										map.put(RequestConstant.DATA , schoolDTOs);
										
										NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {
											public static final String GET_USER_GROUP = "GET_USER_GROUP";
											@Override
											public void onNetworkResult(RequestResult result) {
												getSchoolsBySystemUserProfile(window, profileRecord);
											}
										});
											
									}
								}
							});
	
						}else {
							SC.say("Select Schools to delete from your account");
						}
					}
				});
    		
			}else {
				SC.say("Please a user to add permissions");
			}
			
		}


	});
		
	}

	
	public void getSchoolsBySystemUserProfile(final SystemUserProfilePermissionWindow window , ListGridRecord profileRecord) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestDelimeters.SYSTEM_USER_PROFILE_ID, profileRecord.getAttributeAsObject(SystemUserListGrid.ID));
		if (SessionManager.getInstance().getLoggedInUserGroup().equalsIgnoreCase(SessionManager.ADMIN)) 
			map.put(NetworkDataUtil.ACTION	, RequestConstant.GET_SCHOOLS);
			else 
		map.put(NetworkDataUtil.ACTION	, RequestConstant.GET_SCHOOLS_BY_SYSTEM_USER_PROFILE_SCHOOLS_PROFILE);
		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {
			
			@Override
			public void onNetworkResult(RequestResult result) {
			 window.getSystemUserSchoolPane().getSchoolDistrictListGrid().addRecordsToGrid(result.getSchoolDTOs());		
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
					onUpdateUserGroup(window);
					loadFieldsToEdit(window , groupGridRecord.getSelectedRecord());
					window.show();
				}else {
					SC.say("Please Select group to edit");
				}
	
			}
		});
	}
	
	
	
	private void showFilterSchoolsRegionDistrictWindow(final SystemUserProfilePermissionWindow profilePermissionWindow) {
		profilePermissionWindow.getSystemUserSchoolPane().getAddButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
			 final ListGridRecord profileRecord = getView().getSystemUserPane().getSystemUserListGrid().getSelectedRecord();
				
				final FilterSchoolsRegionDistrictWindow regionDistrictWindow = new FilterSchoolsRegionDistrictWindow();
				final String defaultValue = null;
				ComboUtil.loadRegionCombo(regionDistrictWindow.getFilterRegionDistrict().getRegionCombo() , dispatcher, placeManager, defaultValue);
				regionDistrictWindow.getFilterRegionDistrict().getRegionCombo().addChangedHandler(new ChangedHandler() {
					
					@Override
					public void onChanged(ChangedEvent event) {
						ComboUtil.loadDistrictComboByRegion(regionDistrictWindow.getFilterRegionDistrict().getRegionCombo(), regionDistrictWindow.getFilterRegionDistrict().getDistrictCombo(), dispatcher, placeManager, defaultValue);
					}
				});
				
				regionDistrictWindow.getLoadSchoolsButton().addClickHandler(new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						if (regionDistrictWindow.getFilterRegionDistrict().getDistrictCombo().getValueAsString() != null) {
							final SelectProfileSchoolWindow profileSchoolWindow = new SelectProfileSchoolWindow();
							
							profileSchoolWindow.show();
							LinkedHashMap<String, Object> map = new LinkedHashMap<>();
							map.put(NetworkDataUtil.ACTION	, RequestConstant.GET_NOT_SCHOOLS_BY_SYSTEM_USER_PROFILE_SCHOOLS_PROFILE_DISTRICT);
							map.put(RequestDelimeters.SYSTEM_USER_PROFILE_ID , profileRecord.getAttribute(SystemUserListGrid.ID));
							map.put(RequestDelimeters.DISTRICT_ID, regionDistrictWindow.getFilterRegionDistrict().getDistrictCombo().getValueAsString());
							NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {
								
								@Override
								public void onNetworkResult(RequestResult result) {
									profileSchoolWindow.getSchoolDistrictListGrid().addRecordsToGrid(result.getSchoolDTOs());
									if(!result.getSchoolDTOs().isEmpty())
										profileSchoolWindow.getAddSchoolsButton().enable();
								}
							});
							
							profileSchoolWindow.getAddSchoolsButton().addClickHandler(new ClickHandler() {
								
								@Override
								public void onClick(ClickEvent event) {
									if(profileSchoolWindow.getSchoolDistrictListGrid().anySelected()) {
										
										ListGridRecord[] records = profileSchoolWindow.getSchoolDistrictListGrid().getSelectedRecords();
										List<SchoolDTO> schoolDTOs = new ArrayList<SchoolDTO>();
										for (int i = 0; i < records.length; i++) {
											ListGridRecord schoolRecord = records[i];
										    schoolDTOs.add(new SchoolDTO(schoolRecord.getAttribute(SchoolListGrid.ID)));
										}
										
										LinkedHashMap<String, Object> map = new LinkedHashMap<>();
										map.put(NetworkDataUtil.ACTION	, RequestConstant.SAVE_SYSTEM_USER_PROFILE_SCHOOLS_PROFILE);
										map.put(RequestConstant.DATA , schoolDTOs);
										map.put(RequestDelimeters.SYSTEM_USER_PROFILE_ID , profileRecord.getAttribute(SystemUserListGrid.ID));
										NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {
											
											@Override
											public void onNetworkResult(RequestResult result) {
											 //close windows
												getSchoolsBySystemUserProfile(profilePermissionWindow, profileRecord);
												profilePermissionWindow.close();
												regionDistrictWindow.close();
												profileSchoolWindow.close();
										
											}
										});
									}else {
										SC.say("Select school to add");
									}
								}
							});
							
							
						}else {
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
						  SystemUserGroupDTO systemUserGroupDTO = new SystemUserGroupDTO();
							systemUserGroupDTO.setCode(window.getCodeField().getValueAsString());

							systemUserGroupDTO.setDescription(window.getDescriptionField().getValueAsString());
							systemUserGroupDTO.setName(window.getNameField().getValueAsString());
							systemUserGroupDTO.setDefaultGroup(window.getDefaultRoleBox().getValueAsBoolean());
							systemUserGroupDTO.setReceiveAlerts(window.getReceiveAlertBox().getValueAsBoolean());
							systemUserGroupDTO.setAdministrativeRole(window.getAdministrativeRoleBox().getValueAsBoolean());
						 
						 LinkedHashMap<String, Object> map = new LinkedHashMap<>();
							map.put(SystemUserGroupRequestConstant.DATA, systemUserGroupDTO);
							map.put(NetworkDataUtil.ACTION , SystemUserGroupRequestConstant.SAVE_SYSTEM_USER_GROUP);
							NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {
								
								@Override
								public void onNetworkResult(RequestResult result) {
						          clearSystemGroupWindow(window);
						          SC.say(result.getSystemFeedbackDTO().getMessage());
						          getAllSystemUserGroups();
//									getView().getUserGroupPane().getListgrid()
//									.addRecordsToGrid(result.getSystemUserGroupDTOs());
								}				
							});
					}else {
						SC.say("Please fill all the fields");
					}	
					
					}
				});
				
				//onSaveUserGroup(window);
				window.show();

			}
		});

	}
	
	private boolean checkIfNoSystemUserGroupWindowFieldIsEmpty(SystemUserGroupWindow window) {
	 	if (window.getNameField().getValueAsString() == null) return false;
	 	
	  	if (!window.getReceiveAlertBox().getValueAsBoolean()) return false; 
	  	
	  	if (window.getCodeField().getValueAsString() == null) return false;
	  	
	  	if (window.getDescriptionField().getValueAsString() == null) return false;
	  	
	  	if (!window.getDefaultRoleBox().getValueAsBoolean()) return false;
	  	
	  	if(!window.getAdministrativeRoleBox().getValueAsBoolean()) return false;
	  	
		return true;
	}
	
	private void clearSystemGroupWindow(SystemUserGroupWindow window) {
	  	window.getNameField().clearValue();
	  	window.getReceiveAlertBox().clearValue();
	  	window.getCodeField().clearValue();
	  	window.getDescriptionField().clearValue();
	  	window.getDefaultRoleBox().clearValue();
	  	window.getAdministrativeRoleBox().clearValue();
	}
	
	


	private void deletUserUserGroup(final MenuButton button) {
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				onDeleteUserGroup();

			}
		});
	}

	//@Deprecated
//	private void onSaveUserGroup(final SystemUserGroupWindow window) {
//		window.getSaveButton().addClickHandler(new ClickHandler() {
//
//			@Override
//			public void onClick(ClickEvent event) {
//
//				SystemUserGroupDTO userGroup = new SystemUserGroupDTO();
//
//				userGroup.setCode(window.getRoleCode().getValueAsString());
//
//				userGroup.setDescription(window.getDescription().getValueAsString());
//				userGroup.setName(window.getRoleName().getValueAsString());
//				userGroup.setDefaultGroup(window.getDefaultRole().getValueAsBoolean());
//				userGroup.setReceiveAlerts(window.getReceiveAlerts().getValueAsBoolean());
//
//				userGroup.setAdministrativeRole(window.getAdministrativeRole().getValueAsBoolean());
//
//				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
//				map.put(RequestConstant.SAVE_USER_GROUP, userGroup);
//				map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
//
//				SC.showPrompt("", "", new SwizimaLoader());
//
//				dispatcher.execute(new RequestAction(RequestConstant.SAVE_USER_GROUP, map),
//						new AsyncCallback<RequestResult>() {
//							public void onFailure(Throwable caught) {
//								System.out.println(caught.getMessage());
//								SC.clearPrompt();
//								SC.say("ERROR", caught.getMessage());
//							}
//
//							public void onSuccess(RequestResult result) {
//
//								SC.clearPrompt();
//
//								SessionManager.getInstance().manageSession(result, placeManager);
//								if (result != null) {
//									if (result.getSystemFeedbackDTO().isResponse()) {
//
//										SC.say("Message", result.getSystemFeedbackDTO().getMessage());
//
//										getView().getUserGroupPane().getListgrid()
//												.addRecordsToGrid(result.getSystemUserGroupDTOs());
//
//									} else {
//										SC.warn("ERROR", result.getSystemFeedbackDTO().getMessage());
//									}
//
//								} else {
//									SC.say("ERROR", "Unknow error");
//								}
//
//							}
//						});
//
//			}
//		});
//	}

	private void getAllSystemUserGroups() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(NetworkDataUtil.ACTION , SystemUserGroupRequestConstant.GET_SYSTEM_USER_GROUPS);
		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {
			
			@Override
			public void onNetworkResult(RequestResult result) {
				getView().getUserGroupPane().getListgrid().addRecordsToGrid(result.getSystemUserGroupDTOs());
			}
		});
		
//		dispatcher.execute(new RequestAction(RequestConstant.GET_USER_GROUP, map), new AsyncCallback<RequestResult>() {
//			public void onFailure(Throwable caught) {
//				System.out.println(caught.getMessage());
//
//				SC.clearPrompt();
//				SC.say("ERROR", caught.getMessage());
//			}
//
//			public void onSuccess(RequestResult result) {
//
//				SC.clearPrompt();
//
//				SessionManager.getInstance().manageSession(result, placeManager);
//				if (result != null) {
//
//					getView().getUserGroupPane().getListgrid().addRecordsToGrid(result.getSystemUserGroupDTOs());
//
//				} else {
//					SC.say("ERROR", "Unknow error");
//				}
//
//			}
//		});
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
				userGroup.setDefaultGroup(window.getDefaultRoleBox().getValueAsBoolean());
				userGroup.setReceiveAlerts(window.getReceiveAlertBox().getValueAsBoolean());

				userGroup.setAdministrativeRole(window.getAdministrativeRoleBox().getValueAsBoolean());

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

	private void loadFieldsToEdit(final SystemUserGroupWindow window , ListGridRecord systemUserGroupRecord) {

		window.getCodeField().setValue(systemUserGroupRecord.getAttribute(UserGroupListgrid.CODE));
		window.getDescriptionField().setValue(systemUserGroupRecord.getAttribute(UserGroupListgrid.Description));
		window.getNameField().setValue(systemUserGroupRecord.getAttribute(UserGroupListgrid.Role));

		if (systemUserGroupRecord.getAttribute(UserGroupListgrid.DefaultRole) != null) {
			String defualtRole = systemUserGroupRecord.getAttribute(UserGroupListgrid.DefaultRole);
			if (defualtRole.equalsIgnoreCase("true")) {
				window.getDefaultRoleBox().setValue(true);
			} else {
				window.getDefaultRoleBox().setValue(false);
			}

		} else {
			window.getDefaultRoleBox().setValue(false);
		}

		if (systemUserGroupRecord.getAttribute(UserGroupListgrid.ReceiveNotifications) != null) {

			String receiveAlerts = systemUserGroupRecord.getAttribute(UserGroupListgrid.ReceiveNotifications);

			if (receiveAlerts.equalsIgnoreCase("true")) {
				window.getReceiveAlertBox().setValue(true);
			} else {
				window.getReceiveAlertBox().setValue(false);
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

		window.getGenderCombo().setValueMap(valueMap);
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
				generalUserDetailDTO.setGender(window.getGenderCombo().getValueAsString());
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
		window.getGenderCombo().clearValue();
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

		if (window.getGenderCombo().getValueAsString() == null)
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
				
			    for(SystemMenuDTO systemMenuDTO : SystemMenuDTO.systemMenuDTOList()) {
			    	if(systemMenuDTO.getNavigationMenu().equalsIgnoreCase(navigationMenu))
			    		valueMap.put(systemMenuDTO.getSubMenuItem(),systemMenuDTO.getSubMenuItem());
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
					map.put(SystemMenuRequestConstant.DATA, dtos);
					map.put(NetworkDataUtil.ACTION , SystemMenuRequestConstant.SAVE_SYSTEM_MENU);
					
					NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {
						
						@Override
						public void onNetworkResult(RequestResult result) {
							SC.say(result.getSystemFeedbackDTO().getMessage());
							loadSystemMenus();
							window.close();
						}
					});
					

//					SC.showPrompt("", "", new SwizimaLoader());
//
//					dispatcher.execute(new RequestAction(RequestConstant.SAVE_SystemMENU, map),
//							new AsyncCallback<RequestResult>() {
//								public void onFailure(Throwable caught) {
//									System.out.println(caught.getMessage());
//									SC.say("ERROR", caught.getMessage());
//									SC.clearPrompt();
//								}
//
//								public void onSuccess(RequestResult result) {
//
//									SC.clearPrompt();
//
//									SessionManager.getInstance().manageSession(result, placeManager);
//									if (result != null) {
//
//										if (result.getSystemFeedbackDTO() != null) {
//											if (result.getSystemFeedbackDTO().isResponse()) {
//												SC.say("Sucess", result.getSystemFeedbackDTO().getMessage());
//												getView().getSystemMenuPane().getListgrid()
//														.addRecordsToGrid(result.getSystemMenuDTOs());
//											} else {
//												SC.say("ERROR", result.getSystemFeedbackDTO().getMessage());
//											}
//										} else {
//											SC.say("ERROR", "Feedback is null");
//										}
//
//									} else {
//										SC.say("ERROR", "Unknow error");
//									}
//
//								}
//							});
				}

			}
		});
	}

	private void loadSystemMenus() {

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(NetworkDataUtil.ACTION , SystemMenuRequestConstant.GET_SYSTEM_MENUS);
	    NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {
			
			@Override
			public void onNetworkResult(RequestResult result) {
				GWT.log("RESULT SIZE "+result.getSystemMenuDTOs().size());
				
				getView().getSystemMenuPane().getListgrid().addRecordsToGrid(result.getSystemMenuDTOs());
			}
		});

//		SC.showPrompt("", "", new SwizimaLoader());
//
//		dispatcher.execute(new RequestAction(RequestConstant.GET_SystemMENU, map), new AsyncCallback<RequestResult>() {
//			public void onFailure(Throwable caught) {
//				System.out.println(caught.getMessage());
//				SC.say("ERROR", caught.getMessage());
//				SC.clearPrompt();
//			}
//
//			public void onSuccess(RequestResult result) {
//
//				SC.clearPrompt();
//
//				SessionManager.getInstance().manageSession(result, placeManager);
//				if (result != null) {
//
//					getView().getSystemMenuPane().getListgrid().addRecordsToGrid(result.getSystemMenuDTOs());
//
//				} else {
//					SC.say("ERROR", "Unknow error");
//				}
//
//			}
//		});

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
			map.put(NetworkDataUtil.ACTION, SystemUserGroupSystemMenuRequestConstant.GET_SELECTED_UNSELECTED_USER_GROUP_SYSTEM_MENU);
			NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {
				
				@Override
				public void onNetworkResult(RequestResult result) {
					window.getListgrid().addRecordsToGrid(result.getSystemUserGroupSystemMenuDTOs());
					window.getListgrid().selectActiveRecords();	
				}
			});

//			dispatcher.execute(new RequestAction(SystemUserGroupSystemMenuRequestConstant.GET_SELECTED_UNSELECTED_USER_GROUP_SYSTEM_MENU , map),
//					new AsyncCallback<RequestResult>() {
//						public void onFailure(Throwable caught) {
//							System.out.println(caught.getMessage());
//							SC.say("ERROR", caught.getMessage());
//							SC.clearPrompt();
//						}
//
//						public void onSuccess(RequestResult result) {
//
//							SC.clearPrompt();
//
//							SessionManager.getInstance().manageSession(result, placeManager);
//							if (result != null) {
//
//								window.getListgrid().addRecordsToGrid(result.getSystemUserGroupSystemMenuDTOs());
//								window.getListgrid().selectActiveRecords();
//
//							} else {
//								SC.say("ERROR", "Unknow error");
//							}
//
//						}
//					});
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
                           * This item was not selected when the widget loaded
                           * But not its selected
                           * to be persisted
                           */
						if(record.getAttribute(SystemUserGroupSystemMenuListgrid.ID) == null) {
							SystemUserGroupSystemMenuDTO dto = new SystemUserGroupSystemMenuDTO();
							SystemMenuDTO systemMenuDTO = new SystemMenuDTO();
							systemMenuDTO.setId(record.getAttribute(SystemUserGroupSystemMenuListgrid.SystemMenuId));
							dto.setSystemMenuDTO(systemMenuDTO);
							dto.setSystemUserGroupDTO(userGroup);
							dto.setSelected(true);
							
						dtos.add(dto);
						}	
					}else {
						/**
						 * This item was once selected , but now user has unselected it
						 * @param Id not null
						 * @return
						 * to be deleted
						 */
						if(record.getAttribute(SystemUserGroupSystemMenuListgrid.ID) != null) {
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
					
					
					

//					boolean selected = window.getListgrid().isSelected(record);
//
//					if (selected) {
//
//						SystemUserGroupSystemMenuDTO dto = new SystemUserGroupSystemMenuDTO();
//
//						if (record.getAttribute(SystemUserGroupSystemMenuListgrid.ID) != null) {
//							dto.setId(record.getAttribute(SystemUserGroupSystemMenuListgrid.ID));
//						}
//
//						SystemMenuDTO systemMenu = new SystemMenuDTO();
//						systemMenu.setId(record.getAttribute(SystemUserGroupSystemMenuListgrid.SystemMenuId));
//
//						dto.setSystemMenuDTO(systemMenu);
//						dto.setSystemUserGroupDTO(userGroup);
//						dto.setDisabled(false);
//
//						dtos.add(dto);
//
//					} else {
//
//						if (record.getAttribute(SystemUserGroupSystemMenuListgrid.ID) != null) {
//
//							SystemUserGroupSystemMenuDTO dto = new SystemUserGroupSystemMenuDTO();
//
//							if (record.getAttribute(SystemUserGroupSystemMenuListgrid.ID) != null) {
//								dto.setId(record.getAttribute(SystemUserGroupSystemMenuListgrid.ID));
//							}
//
//							SystemMenuDTO systemMenuDTO = new SystemMenuDTO();
//							systemMenuDTO.setId(record.getAttribute(SystemUserGroupSystemMenuListgrid.SystemMenuId));
//
//							dto.setSystemMenuDTO(systemMenuDTO);
//							dto.setSystemUserGroupDTO(userGroup);
//							dto.setDisabled(true);
//
//							dtos.add(dto);
//						}
//					}
				}

				if (!dtos.isEmpty()) {

					LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					map.put(SystemUserGroupSystemMenuRequestConstant.DATA, dtos);
//					map.put(RequestDelimeters.SYSTEM_USER_GROUP_ID, userGroup);
					map.put(NetworkDataUtil.ACTION, SystemUserGroupSystemMenuRequestConstant.SAVE_USER_GROUP_SYSTEM_MENU);
					NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {
						
						@Override
						public void onNetworkResult(RequestResult result) {
							getSelectedUnSelectedSystemUserGroupMenu(window);
//							window.getListgrid().addRecordsToGrid(result.getSystemUserGroupSystemMenuDTOs());
//							window.getListgrid().selectActiveRecords();	
							window.close();
						}
					});
					
					
//					map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
//
//					SC.showPrompt("", "", new SwizimaLoader());
//
//					dispatcher.execute(new RequestAction(RequestConstant.SAVE_USER_GROUP_SystemMENU, map),
//							new AsyncCallback<RequestResult>() {
//								public void onFailure(Throwable caught) {
//									System.out.println(caught.getMessage());
//									SC.say("ERROR", caught.getMessage());
//									SC.clearPrompt();
//								}
//
//								public void onSuccess(RequestResult result) {
//
//									SC.clearPrompt();
//
//									SessionManager.getInstance().manageSession(result, placeManager);
//									if (result != null) {
//
//										if (result.getSystemFeedbackDTO() != null) {
//											if (result.getSystemFeedbackDTO().isResponse()) {
//												SC.say("SUCCESS", result.getSystemFeedbackDTO().getMessage(),
//														new BooleanCallback() {
//
//															@Override
//															public void execute(Boolean value) {
//
//																if (value) {
//																	window.close();
//																}
//
//															}
//														});
//											} else {
//												SC.say("ERROR", result.getSystemFeedbackDTO().getMessage());
//											}
//										}
//									} else {
//										SC.say("ERROR", "Unknow error");
//									}
//
//								}
//							});
				} else {
					SC.warn("ERROR", "Please select at least one permission and try again.");
				}

			}
		});
	}

}