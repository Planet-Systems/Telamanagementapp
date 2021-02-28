package com.planetsystems.tela.managementapp.client.presenter.schoolcategory;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Cookies;
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
import com.planetsystems.tela.dto.DistrictDTO;
import com.planetsystems.tela.dto.SchoolCategoryDTO;
import com.planetsystems.tela.dto.SchoolClassDTO;
import com.planetsystems.tela.dto.SchoolDTO;
import com.planetsystems.tela.dto.SystemFeedbackDTO;
import com.planetsystems.tela.managementapp.client.event.HighlightActiveLinkEvent;
import com.planetsystems.tela.managementapp.client.gin.SessionManager;
import com.planetsystems.tela.managementapp.client.place.NameTokens;
import com.planetsystems.tela.managementapp.client.presenter.main.MainPresenter;
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

public class SchoolCategoryPresenter
		extends Presenter<SchoolCategoryPresenter.MyView, SchoolCategoryPresenter.MyProxy> {
	interface MyView extends View {
		ControlsPane getControlsPane();

		TabSet getTabSet();

		SchCategoryPane getSchCategoryPane();

		SchoolPane getSchoolPane();

		SchoolClassPane getSchoolClassPane();
	}

	@SuppressWarnings("deprecation")
	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_SchoolCategory = new Type<RevealContentHandler<?>>();

	@Inject
	private DispatchAsync dispatcher;

	@Inject
	private DispatchAsync dispatchAsync;
	
	DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat(DatePattern.DAY_MONTH_YEAR_HOUR_MINUTE_SECONDS.getPattern());
	DateTimeFormat dateFormat = DateTimeFormat.getFormat(DatePattern.DAY_MONTH_YEAR.getPattern());
	
	//private String token = Cookies.getCookie(RequestConstant.AUTH_TOKEN);

	@Inject
	PlaceManager placeManager;

	@NameToken(NameTokens.schoolclasscategory)
	@ProxyStandard
	interface MyProxy extends ProxyPlace<SchoolCategoryPresenter> {
	}

	private EventBus eventBus;

	@Inject
	SchoolCategoryPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy, MainPresenter.SLOT_Main);
		this.eventBus = eventBus;
	}

	@Override
	protected void onBind() {
		super.onBind();
		onTabSelected();
		getAllSchoolCategories();
		getAllSchools();
		getAllSchoolClasses();
	}

	@Override
	protected void onReset() {
		super.onReset();
		HighlightActiveLinkEvent highlightActiveLinkEvent = new HighlightActiveLinkEvent(
				placeManager.getCurrentPlaceRequest().getNameToken());
		SchoolCategoryPresenter.this.eventBus.fireEvent(highlightActiveLinkEvent);
	}

	private void onTabSelected() {
		getView().getTabSet().addTabSelectedHandler(new TabSelectedHandler() {

			@Override
			public void onTabSelected(TabSelectedEvent event) {

				String selectedTab = event.getTab().getTitle();

				if (selectedTab.equalsIgnoreCase(SchoolCategoryView.SCH_CATEGORY_TAB_TITLE)) {

					MenuButton newButton = new MenuButton("New");
					MenuButton edit = new MenuButton("Edit");
					MenuButton delete = new MenuButton("Delete");
					MenuButton fiter = new MenuButton("Filter");

					List<MenuButton> buttons = new ArrayList<>();
					buttons.add(newButton);
					buttons.add(edit);
					buttons.add(delete);
					buttons.add(fiter);

					getView().getControlsPane().addMenuButtons(buttons);

					addSchoolCategory(newButton);
					deleteSchoolCategory(delete);
					editSchoolCategory(edit);

				} else if (selectedTab.equalsIgnoreCase(SchoolCategoryView.SCHOOL_TAB_TITLE)) {

					MenuButton newButton = new MenuButton("New");
					MenuButton edit = new MenuButton("Edit");
					MenuButton delete = new MenuButton("Delete");
					MenuButton fiter = new MenuButton("Filter");

					List<MenuButton> buttons = new ArrayList<>();
					buttons.add(newButton);
					buttons.add(edit);
					// buttons.add(delete);
					buttons.add(fiter);

					getView().getControlsPane().addMenuButtons(buttons);

					addSchool(newButton);
					deleteSchool(delete);
					editSchool(edit);

				} else if (selectedTab.equalsIgnoreCase(SchoolCategoryView.SCH_CLASSES_TAB_TITLE)) {
					MenuButton newButton = new MenuButton("New");
					MenuButton edit = new MenuButton("Edit");
					MenuButton delete = new MenuButton("Delete");
					MenuButton fiter = new MenuButton("Filter");

					List<MenuButton> buttons = new ArrayList<>();
					buttons.add(newButton);
					buttons.add(edit);
					// buttons.add(delete);
					buttons.add(fiter);

					getView().getControlsPane().addMenuButtons(buttons);

					addSchoolClass(newButton);
					deleteSchoolClass(delete);
					editSchoolClass(edit);

				} else {
					List<MenuButton> buttons = new ArrayList<>();
					getView().getControlsPane().addMenuButtons(buttons);
				}

			}

		});
	}

	///////////////////////////////////////////////// SCHOOL
	///////////////////////////////////////////////// CATEGORY//////////////////////////////////////////////////////////

	private void addSchoolCategory(MenuButton menuButton) {
		menuButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				SchoolCategoryWindow window = new SchoolCategoryWindow();
				window.show();
				saveSchoolCategory(window);
			}
		});

	}

	private void clearSchoolCategoryWindowFields(SchoolCategoryWindow window) {
		window.getCategoryCode().clearValue();
		window.getCategoryName().clearValue();
	}

	private void saveSchoolCategory(final SchoolCategoryWindow window) {
		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				SchoolCategoryDTO dto = new SchoolCategoryDTO();
				dto.setCode(window.getCategoryCode().getValueAsString());
				dto.setName(window.getCategoryName().getValueAsString());
				dto.setCreatedDateTime(dateTimeFormat.format(new Date()));

				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(RequestConstant.SAVE_SCHOOL_CATEGORY, dto);
				map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
				SC.showPrompt("", "", new SwizimaLoader());

				dispatcher.execute(new RequestAction(RequestConstant.SAVE_SCHOOL_CATEGORY, map),
						new AsyncCallback<RequestResult>() {

							public void onFailure(Throwable caught) {

								SC.clearPrompt();
								System.out.println(caught.getMessage());
								SC.say("ERROR", caught.getMessage());
							}

							public void onSuccess(RequestResult result) {
								SC.clearPrompt();
								clearSchoolCategoryWindowFields(window);
								SessionManager.getInstance().manageSession(result, placeManager);
								if (result != null) {
									SystemFeedbackDTO feedback = result.getSystemFeedbackDTO();

									if (feedback.isResponse()) {
										SC.say("SUCCESS", feedback.getMessage());
									} else {
										SC.warn("INFO", feedback.getMessage());
									}

									getView().getSchCategoryPane().getListGrid()
											.addRecordsToGrid(result.getSchoolCategoryDTOs());

								} else {
									SC.warn("ERROR", "Service Down");
									// SC.warn("ERROR", "Unknow error");
								}
							}

						});

			}
		});

	}

	private void editSchoolCategory(MenuButton button) {
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (getView().getSchCategoryPane().getListGrid().anySelected()) {
					SchoolCategoryWindow window = new SchoolCategoryWindow();
					loadFieldsToEdit(window);
					updateSchCategory(window);
					window.show();

				} else {
					SC.say("Please select a record to edit");
				}
			}

		});

	}

	private void loadFieldsToEdit(SchoolCategoryWindow window) {
		ListGridRecord record = getView().getSchCategoryPane().getListGrid().getSelectedRecord();
		window.getCategoryCode().setValue(record.getAttribute(SchCategoryListGrid.CODE));
		window.getCategoryName().setValue(record.getAttribute(SchCategoryListGrid.NAME));
	}

	private void updateSchCategory(final SchoolCategoryWindow window) {
		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				ListGridRecord record = getView().getSchCategoryPane().getListGrid().getSelectedRecord();
			
				SchoolCategoryDTO dto = new SchoolCategoryDTO();
				dto.setId(record.getAttribute(SchCategoryListGrid.ID));
				dto.setCode(window.getCategoryCode().getValueAsString());
				dto.setName(window.getCategoryName().getValueAsString());
				dto.setUpdatedDateTime(dateTimeFormat.format(new Date()));

				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(RequestConstant.UPDATE_SCHOOL_CATEGORY, dto);
				map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
				SC.showPrompt("", "", new SwizimaLoader());

				dispatcher.execute(new RequestAction(RequestConstant.UPDATE_SCHOOL_CATEGORY, map),
						new AsyncCallback<RequestResult>() {

							public void onFailure(Throwable caught) {

								SC.clearPrompt();
								System.out.println(caught.getMessage());
								SC.say("ERROR", caught.getMessage());
							}

							public void onSuccess(RequestResult result) {
								SC.clearPrompt();
								SessionManager.getInstance().manageSession(result, placeManager);
								if (result != null) {
									SystemFeedbackDTO feedback = result.getSystemFeedbackDTO();

								if (feedback.isResponse()) {
										SC.say("SUCCESS", feedback.getMessage());
										clearSchoolCategoryWindowFields(window);
										window.close();
										
										getView().getSchCategoryPane().getListGrid()
										.addRecordsToGrid(result.getSchoolCategoryDTOs());
									} else  {
										SC.warn("INFO", feedback.getMessage());
									} 

								} else {
									SC.warn("ERROR", "Service Down");
									// SC.warn("ERROR", "Unknown error");
								}

							}

						});
			}
		});

	}

	private void deleteSchoolCategory(MenuButton button) {
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (getView().getSchCategoryPane().getListGrid().anySelected()) {
					SC.ask("Confirm", "Are you sure you want to delete the selected record", new BooleanCallback() {

						@Override
						public void execute(Boolean value) {
							if (value) {
								ListGridRecord record = getView().getSchCategoryPane().getListGrid()
										.getSelectedRecord();
								// SC.say("Id "+record.getAttributeAsString("id")+" Name
								// "+record.getAttributeAsString("name"));

								LinkedHashMap<String, Object> map = new LinkedHashMap<>();
								map.put(RequestConstant.DELETE_SCHOOL_CATEGORY, record.getAttributeAsString("id"));
								map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
								GWT.log("DELETE " + map);

								SC.showPrompt("", "", new SwizimaLoader());

								dispatcher.execute(new RequestAction(RequestConstant.DELETE_SCHOOL_CATEGORY , map),
										new AsyncCallback<RequestResult>() {

											public void onFailure(Throwable caught) {
												SC.clearPrompt();
												System.out.println(caught.getMessage());
												SC.say("ERROR", caught.getMessage());
											}

											public void onSuccess(RequestResult result) {
												SC.clearPrompt();
												SessionManager.getInstance().manageSession(result, placeManager);
												if (result != null) {

													if (result.getSystemFeedbackDTO() != null) {
														if (result.getSystemFeedbackDTO().isResponse()) {
															SC.say("SUCCESS",
																	result.getSystemFeedbackDTO().getMessage());

															getView().getSchCategoryPane().getListGrid()
																	.addRecordsToGrid(result.getSchoolCategoryDTOs());

														} else {
															SC.warn("ERROR",
																	result.getSystemFeedbackDTO().getMessage());
														}
													}
												} else {
													SC.warn("ERROR", "Unknow error");
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

	private void getAllSchoolCategories() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.GET_SCHOOL_CATEGORY, null);
		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
		SC.showPrompt("", "", new SwizimaLoader());

		dispatchAsync.execute(new RequestAction(RequestConstant.GET_SCHOOL_CATEGORY , map),
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
								if (result.getSystemFeedbackDTO().isResponse()) {
									// SC.say("SUCCESS", result.getSystemFeedbackDTO().getMessage());

									getView().getSchCategoryPane().getListGrid()
											.addRecordsToGrid(result.getSchoolCategoryDTOs());

								} else {
									SC.warn("Not Successful \n ERROR:", result.getSystemFeedbackDTO().getMessage());
								}
							}
						} else {
							SC.warn("ERROR", "Service Down");
							// SC.warn("ERROR", "Unknown error");
						}

					}
				});

	}

	////////////////////////////////////////////////////////// SCHOOL////////////////////////////////////////////////////////////////////

	private void addSchool(MenuButton menuButton) {
		menuButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				SchoolWindow window = new SchoolWindow();
				window.show();
				loadCategoryCombo(window, null);
				loadDistrictCombo(window, null);
				saveSchool(window);
			}
		});
	}

	private void saveSchool(final SchoolWindow window) {
		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				SchoolDTO dto = new SchoolDTO();
				dto.setCode(window.getSchoolCode().getValueAsString());
				dto.setLatitude(window.getLatitude().getValueAsString());
				dto.setLongitude(window.getLongtitude().getValueAsString());
				dto.setDeviceNumber(window.getDeviceNumber().getValueAsString());
				dto.setName(window.getSchoolName().getValueAsString());
				dto.setCreatedDateTime(dateTimeFormat.format(new Date()));

				SchoolCategoryDTO schoolCategoryDTO = new SchoolCategoryDTO();
				schoolCategoryDTO.setId(window.getSchoolCategory().getValueAsString());
				dto.setSchoolCategoryDTO(schoolCategoryDTO);

				DistrictDTO districtDTO = new DistrictDTO();
				districtDTO.setId(window.getDistrict().getValueAsString());
				dto.setDistrictDTO(districtDTO);

				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(RequestConstant.SAVE_SCHOOL, dto);
				map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
				SC.showPrompt("", "", new SwizimaLoader());

				dispatcher.execute(new RequestAction(RequestConstant.SAVE_SCHOOL , map),
						new AsyncCallback<RequestResult>() {

							public void onFailure(Throwable caught) {

								SC.clearPrompt();
								System.out.println(caught.getMessage());
								SC.say("ERROR", caught.getMessage());
							}

							public void onSuccess(RequestResult result) {
								SC.clearPrompt();
								clearSchoolWindowFields(window);
								SessionManager.getInstance().manageSession(result, placeManager);
								if (result != null) {
									SystemFeedbackDTO feedback = result.getSystemFeedbackDTO();

									if (feedback.isResponse()) {
										SC.say("SUCCESS", feedback.getMessage());
									} else {
										SC.warn("INFO", feedback.getMessage());
									}

									getView().getSchoolPane().getListGrid().addRecordsToGrid(result.getSchoolDTOs());

								} else {
									SC.warn("ERROR", "Unknow error");
								}

							}
						});

			}
		});
	}

	private void editSchool(MenuButton button) {
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (getView().getSchoolPane().getListGrid().anySelected()) {
					SchoolWindow window = new SchoolWindow();
					loadFieldsToEdit(window);
					updateSchool(window);
					window.show();

				} else {
					SC.say("Please select record to update");
				}
			}

		});
	}

	private void loadFieldsToEdit(SchoolWindow window) {
		ListGridRecord record = getView().getSchoolPane().getListGrid().getSelectedRecord();

		window.getSchoolCategory().setValue(record.getAttribute(SchoolListGrid.CATEGORY));
		window.getSchoolCode().setValue(record.getAttribute(SchoolListGrid.CODE));
		window.getDistrict().setValue(record.getAttribute(SchoolListGrid.DISTRICT));
		window.getSchoolName().setValue(record.getAttribute(SchoolListGrid.NAME));
		window.getLatitude().setValue(record.getAttribute(SchoolListGrid.LATITUDE));
		window.getLongtitude().setValue(record.getAttribute(SchoolListGrid.LONGITUDE));
		window.getDeviceNumber().setValue(record.getAttribute(SchoolListGrid.DEVICE_NUMBER));

		loadDistrictCombo(window, record.getAttribute(SchoolListGrid.DISTRICT_ID));
		loadCategoryCombo(window, record.getAttribute(SchoolListGrid.CATEGORY_ID));

	}

	private void updateSchool(final SchoolWindow window) {
		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				ListGridRecord record = getView().getSchoolPane().getListGrid().getSelectedRecord();

				SchoolDTO dto = new SchoolDTO();
				dto.setId(record.getAttribute(SchoolListGrid.ID));
				dto.setCode(window.getSchoolCode().getValueAsString());
				dto.setLatitude(window.getLatitude().getValueAsString());
				dto.setLongitude(window.getLongtitude().getValueAsString());
				dto.setDeviceNumber(window.getDeviceNumber().getValueAsString());
				dto.setName(window.getSchoolName().getValueAsString());
				dto.setUpdatedDateTime(dateTimeFormat.format(new Date()));

				SchoolCategoryDTO schoolCategoryDTO = new SchoolCategoryDTO();
				schoolCategoryDTO.setId(window.getSchoolCategory().getValueAsString());
				dto.setSchoolCategoryDTO(schoolCategoryDTO);

				DistrictDTO districtDTO = new DistrictDTO();
				districtDTO.setId(window.getDistrict().getValueAsString());
				dto.setDistrictDTO(districtDTO);
				
				//GWT.log("SC "+schoolCategory.getId() +" d "+district.getId()+" id "+dto.getId());

				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(RequestConstant.UPDATE_SCHOOL, dto);
				map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
				SC.showPrompt("", "", new SwizimaLoader());

				dispatcher.execute(new RequestAction(RequestConstant.UPDATE_SCHOOL , map),
						new AsyncCallback<RequestResult>() {

							public void onFailure(Throwable caught) {

								SC.clearPrompt();
								System.out.println(caught.getMessage());
								SC.say("ERROR", caught.getMessage());
							}

							public void onSuccess(RequestResult result) {
								SC.clearPrompt();
								SessionManager.getInstance().manageSession(result, placeManager);					

								if (result != null) {
									SystemFeedbackDTO feedback = result.getSystemFeedbackDTO();

									if (feedback.isResponse()) {
										SC.say("SUCCESS", feedback.getMessage());
										clearSchoolWindowFields(window);
										window.close();
										getView().getSchoolPane().getListGrid().addRecordsToGrid(result.getSchoolDTOs());
										
									} else {
										SC.warn("INFO", feedback.getMessage());
									}

								} else {
									SC.warn("ERROR", "Unknow error");
								}

							}
						});

			}
		});
	}

	private void loadCategoryCombo(final SchoolWindow window, final String defaultValue) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.GET_SCHOOL_CATEGORY, null);
		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
		SC.showPrompt("", "", new SwizimaLoader());
		dispatchAsync.execute(new RequestAction(RequestConstant.GET_SCHOOL_CATEGORY , map),
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
								LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();

								for (SchoolCategoryDTO schoolCategoryDTO : result.getSchoolCategoryDTOs()) {
									valueMap.put(schoolCategoryDTO.getId(), schoolCategoryDTO.getName());
								}
								window.getSchoolCategory().setValueMap(valueMap);

								if (defaultValue != null) {
									window.getSchoolCategory().setValue(defaultValue);
								}

							}
						} else {
							SC.warn("ERROR", "Unknow error");
						}

					}
				});
	}

	private void loadDistrictCombo(final SchoolWindow window, final String defaultValue) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.GET_DISTRICT, null);
		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
		SC.showPrompt("", "", new SwizimaLoader());
		dispatchAsync.execute(new RequestAction(RequestConstant.GET_DISTRICT , map), new AsyncCallback<RequestResult>() {

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
						LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();

						for (DistrictDTO districtDTO : result.getDistrictDTOs()) {
							valueMap.put(districtDTO.getId(), districtDTO.getName());
						}
						window.getDistrict().setValueMap(valueMap);

						if (defaultValue != null) {
							window.getDistrict().setValue(defaultValue);
						}
					}
				} else {
					SC.warn("ERROR", "Unknow error");
				}

			}
		});
	}

	private void clearSchoolWindowFields(SchoolWindow window) {

		window.getSchoolCode().clearValue();
		window.getSchoolName().clearValue();
		window.getLatitude().clearValue();
		window.getLongtitude().clearValue();
		window.getDeviceNumber().clearValue();
		window.getSchoolCategory().clearValue();
	}

	private void deleteSchool(MenuButton button) {
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (getView().getSchoolPane().getListGrid().anySelected()) {
					SC.ask("Confirm", "Are you sure you want to delete the selected record", new BooleanCallback() {

						@Override
						public void execute(Boolean value) {
							if (value) {
								ListGridRecord record = getView().getSchoolPane().getListGrid().getSelectedRecord();
								// SC.say("Id "+record.getAttributeAsString("id")+" Name
								// "+record.getAttributeAsString("name"));

								LinkedHashMap<String, Object> map = new LinkedHashMap<>();
								map.put(RequestConstant.DELETE_SCHOOL, record.getAttributeAsString("id"));
								map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
								// map.put(RequestConstant.LOGIN_TOKEN, loginToken);
								GWT.log("DELETE " + map);

								SC.showPrompt("", "", new SwizimaLoader());

								dispatcher.execute(new RequestAction(RequestConstant.DELETE_SCHOOL , map),
										new AsyncCallback<RequestResult>() {

											public void onFailure(Throwable caught) {
												SC.clearPrompt();
												System.out.println(caught.getMessage());
												SC.say("ERROR", caught.getMessage());
											}

											public void onSuccess(RequestResult result) {
												SC.clearPrompt();
												SessionManager.getInstance().manageSession(result, placeManager);
												if (result != null) {

													if (result.getSystemFeedbackDTO() != null) {
														if (result.getSystemFeedbackDTO().isResponse()) {
															SC.say("SUCCESS",
																	result.getSystemFeedbackDTO().getMessage());

															getView().getSchoolPane().getListGrid()
																	.addRecordsToGrid(result.getSchoolDTOs());

														} else {
															SC.warn("ERROR",
																	result.getSystemFeedbackDTO().getMessage());
														}
													}
												} else {
													SC.warn("ERROR", "Unknow error");
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

	private void getAllSchools() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.GET_SCHOOL, null);
		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
		SC.showPrompt("", "", new SwizimaLoader());

		dispatchAsync.execute(new RequestAction(RequestConstant.GET_SCHOOL , map), new AsyncCallback<RequestResult>() {

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
						if (result.getSystemFeedbackDTO().isResponse()) {
							// SC.say("SUCCESS", result.getSystemFeedbackDTO().getMessage());
							getView().getSchoolPane().getListGrid().addRecordsToGrid(result.getSchoolDTOs());
						} else {
							SC.warn("Not Successful \n ERROR:", result.getSystemFeedbackDTO().getMessage());
						}
					}
				} else {
					SC.warn("ERROR", "Service Down");
					// SC.warn("ERROR", "Unknow error");
				}

			}
		});

	}

	//////////////////////////////////////////////// SCHOOL
	//////////////////////////////////////////////// CLASS///////////////////////////////////////////////////////////////////////

	private void addSchoolClass(MenuButton menuButton) {
		menuButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				SchoolClassWindow window = new SchoolClassWindow();
				loadSchoolCombo(window, null);
				loadAcademicTermCombo(window, null);
				saveSchoolClass(window);
				window.show();
			}
		});
	}

	private void saveSchoolClass(final SchoolClassWindow window) {
		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				SchoolClassDTO dto = new SchoolClassDTO();
				dto.setCode(window.getClassCode().getValueAsString());
				dto.setName(window.getcName().getValueAsString());

				SchoolDTO schoolDTO = new SchoolDTO();
				schoolDTO.setId(window.getSchool().getValueAsString());
				dto.setSchoolDTO(schoolDTO);

				AcademicTermDTO academicTermDTO = new AcademicTermDTO();
				academicTermDTO.setId(window.getAcademicTerm().getValueAsString());
				dto.setAcademicTermDTO(academicTermDTO);

				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(RequestConstant.SAVE_SCHOOL_CLASS, dto);
				map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
				SC.showPrompt("", "", new SwizimaLoader());

				dispatcher.execute(new RequestAction(RequestConstant.SAVE_SCHOOL_CLASS , map),
						new AsyncCallback<RequestResult>() {

							public void onFailure(Throwable caught) {

								SC.clearPrompt();
								System.out.println(caught.getMessage());
								SC.say("ERROR", caught.getMessage());
							}

							public void onSuccess(RequestResult result) {
								SC.clearPrompt();
								clearSchoolClassWindowFields(window);
								SessionManager.getInstance().manageSession(result, placeManager);
								if (result != null) {
									SystemFeedbackDTO feedback = result.getSystemFeedbackDTO();

									 if (feedback.isResponse()) {
										SC.say("SUCCESS", feedback.getMessage());
									} else {
										SC.warn("INFO", feedback.getMessage());
									} 

									getView().getSchoolClassPane().getListGrid()
											.addRecordsToGrid(result.getSchoolClassDTOs());

								} else {
									SC.warn("ERROR", "Unknow error");
								}

							}
						});

			}
		});

	}

	private void editSchoolClass(MenuButton button) {
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (getView().getSchoolClassPane().getListGrid().anySelected()) {
					SchoolClassWindow window = new SchoolClassWindow();
					loadFieldsToEdit(window);
					updateSchoolClass(window);
					window.show();
				} else {
					SC.say("INFO: please select record to update");
				}
			}
		});

	}

	private void updateSchoolClass(final SchoolClassWindow window) {
		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				ListGridRecord record = getView().getSchoolClassPane().getListGrid().getSelectedRecord();

				SchoolClassDTO dto = new SchoolClassDTO();
				dto.setId(record.getAttribute(SchoolClassListGrid.ID));
				dto.setCode(window.getClassCode().getValueAsString());
				dto.setName(window.getcName().getValueAsString());

				SchoolDTO schoolDTO = new SchoolDTO();
				schoolDTO.setId(window.getSchool().getValueAsString());
				dto.setSchoolDTO(schoolDTO);

				AcademicTermDTO academicTermDTO = new AcademicTermDTO();
				academicTermDTO.setId(window.getAcademicTerm().getValueAsString());
				dto.setAcademicTermDTO(academicTermDTO);

				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(RequestConstant.UPDATE_SCHOOL_CLASS, dto);
				map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
				SC.showPrompt("", "", new SwizimaLoader());

				//GWT.log("DTO " +dto.getId()+" term "+academicTermDTO.getId()+" SCHOOL "+school.getId());

				dispatcher.execute(new RequestAction(RequestConstant.UPDATE_SCHOOL_CLASS , map),
						new AsyncCallback<RequestResult>() {

							public void onFailure(Throwable caught) {
								
								SC.clearPrompt();
								System.out.println(caught.getMessage());
								SC.say("ERROR", caught.getMessage());
							}

							public void onSuccess(RequestResult result) {
								SC.clearPrompt();
								SessionManager.getInstance().manageSession(result, placeManager);
								if (result != null) {
									SystemFeedbackDTO feedback = result.getSystemFeedbackDTO();

								if (feedback.isResponse()) {
									SC.say("SUCCESS", feedback.getMessage());
									
									clearSchoolClassWindowFields(window);
									window.close();
									
									getView().getSchoolClassPane().getListGrid()
									.addRecordsToGrid(result.getSchoolClassDTOs());
										
									} else {
										SC.warn("INFO", feedback.getMessage());
									}

								} else {
									SC.warn("ERROR", "Unknow error");
								}
							}
						});

			}
		});

	}

	private void loadFieldsToEdit(SchoolClassWindow window) {
		ListGridRecord record = getView().getSchoolClassPane().getListGrid().getSelectedRecord();
		window.getClassCode().setValue(record.getAttribute(SchoolClassListGrid.CODE));
		window.getcName().setValue(record.getAttribute(SchoolClassListGrid.NAME));
		window.getSchool().setValue(record.getAttribute(SchoolClassListGrid.SCHOOL));
		window.getAcademicTerm().setValue(record.getAttribute(SchoolClassListGrid.ACADEMIC_TERM));

		loadSchoolCombo(window, record.getAttribute(SchoolClassListGrid.SCHOOL_ID));
		loadAcademicTermCombo(window, record.getAttribute(SchoolClassListGrid.ACADEMIC_TERM_ID));
	}

	private void loadSchoolCombo(final SchoolClassWindow window, final String defaultValue) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.GET_SCHOOL, null);
		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
		SC.showPrompt("", "", new SwizimaLoader());

		dispatchAsync.execute(new RequestAction(RequestConstant.GET_SCHOOL , map), new AsyncCallback<RequestResult>() {

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
						LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();

						for (SchoolDTO schoolDTO : result.getSchoolDTOs()) {
							valueMap.put(schoolDTO.getId(), schoolDTO.getName());
						}
						window.getSchool().setValueMap(valueMap);
						if (defaultValue != null) {
							window.getSchool().setValue(defaultValue);
						}
					}
				} else {
					SC.warn("ERROR", "Unknow error");
				}

			}
		});
	}

	private void loadAcademicTermCombo(final SchoolClassWindow window, final String defaultValue) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.GET_ACADEMIC_TERM, null);
		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
		SC.showPrompt("", "", new SwizimaLoader());

		dispatchAsync.execute(new RequestAction( RequestConstant.GET_ACADEMIC_TERM , map),
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
								LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();

								for (AcademicTermDTO academicTermDTO : result.getAcademicTermDTOs()) {
									valueMap.put(academicTermDTO.getId(), academicTermDTO.getTerm());
								}
								window.getAcademicTerm().setValueMap(valueMap);

								if (defaultValue != null) {
									window.getAcademicTerm().setValue(defaultValue);
								}

							}
						} else {
							SC.warn("ERROR", "Unknow error");
						}

					}
				});
	}

	private void clearSchoolClassWindowFields(SchoolClassWindow window) {
		window.getClassCode().clearValue();
		window.getcName().clearValue();
		window.getSchool().clearValue();
		window.getAcademicTerm().clearValue();
	}

	private void deleteSchoolClass(MenuButton button) {
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (getView().getSchoolClassPane().getListGrid().anySelected()) {
					SC.ask("Confirm", "Are you sure you want to delete the selected record", new BooleanCallback() {

						@Override
						public void execute(Boolean value) {
							if (value) {
								ListGridRecord record = getView().getSchoolClassPane().getListGrid()
										.getSelectedRecord();
								// SC.say("Id "+record.getAttributeAsString("id")+" Name
								// "+record.getAttributeAsString("name"));

								LinkedHashMap<String, Object> map = new LinkedHashMap<>();
								map.put(RequestConstant.DELETE_SCHOOL_CLASS, record.getAttributeAsString("id"));
								map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
								// map.put(RequestConstant.LOGIN_TOKEN, loginToken);
								GWT.log("DELETE " + map);

								SC.showPrompt("", "", new SwizimaLoader());

								dispatcher.execute(new RequestAction(RequestConstant.DELETE_SCHOOL_CLASS , map),
										new AsyncCallback<RequestResult>() {

											public void onFailure(Throwable caught) {
												SC.clearPrompt();
												System.out.println(caught.getMessage());
												SC.say("ERROR", caught.getMessage());
											}

											public void onSuccess(RequestResult result) {
												SC.clearPrompt();
												SessionManager.getInstance().manageSession(result, placeManager);
												if (result != null) {

													if (result.getSystemFeedbackDTO() != null) {
														if (result.getSystemFeedbackDTO().isResponse()) {
															SC.say("SUCCESS",
																	result.getSystemFeedbackDTO().getMessage());

															getView().getSchoolClassPane().getListGrid()
																	.addRecordsToGrid(result.getSchoolClassDTOs());

														} else {
															SC.warn("ERROR",
																	result.getSystemFeedbackDTO().getMessage());
														}
													}
												} else {
													SC.warn("ERROR", "Unknow error");
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

	private void getAllSchoolClasses() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.GET_SCHOOL_CLASS, null);
		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
		SC.showPrompt("", "", new SwizimaLoader());

		dispatchAsync.execute(new RequestAction(RequestConstant.GET_SCHOOL_CLASS , map),
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
								if (result.getSystemFeedbackDTO().isResponse()) {
									// SC.say("SUCCESS", result.getSystemFeedbackDTO().getMessage());
									getView().getSchoolClassPane().getListGrid()
											.addRecordsToGrid(result.getSchoolClassDTOs());
								} else {
									SC.warn("Not Successful \n ERROR:", result.getSystemFeedbackDTO().getMessage());
								}
							}
						} else {
							SC.warn("ERROR", "Unknow error");
						}

					}

				});

	}

}