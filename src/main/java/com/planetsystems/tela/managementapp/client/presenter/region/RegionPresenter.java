package com.planetsystems.tela.managementapp.client.presenter.region;

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
import com.planetsystems.tela.dto.DistrictDTO;
import com.planetsystems.tela.dto.RegionDto;
import com.planetsystems.tela.dto.SystemFeedbackDTO;
import com.planetsystems.tela.managementapp.client.event.HighlightActiveLinkEvent;
import com.planetsystems.tela.managementapp.client.gin.SessionManager;
import com.planetsystems.tela.managementapp.client.place.NameTokens;
import com.planetsystems.tela.managementapp.client.presenter.comboutils.ComboUtil;
import com.planetsystems.tela.managementapp.client.presenter.main.MainPresenter;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.planetsystems.tela.managementapp.client.widget.MenuButton;
import com.planetsystems.tela.managementapp.client.widget.SwizimaLoader;
import com.planetsystems.tela.managementapp.shared.DatePattern;
import com.planetsystems.tela.managementapp.shared.RequestAction;
import com.planetsystems.tela.managementapp.shared.RequestConstant;
import com.planetsystems.tela.managementapp.shared.RequestDelimeters;
import com.planetsystems.tela.managementapp.shared.RequestResult;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;

@SuppressWarnings("deprecation")
public class RegionPresenter extends Presenter<RegionPresenter.MyView, RegionPresenter.MyProxy> {
	interface MyView extends View {

		ControlsPane getControlsPane();

		TabSet getTabSet();

		RegionPane getRegionPane();

		DistrictPane getDistrictPane();
		
	}

	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_Region = new Type<RevealContentHandler<?>>();

	@Inject
	private DispatchAsync dispatcher;
	
	DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat(DatePattern.DAY_MONTH_YEAR_HOUR_MINUTE_SECONDS.getPattern());
	DateTimeFormat dateFormat = DateTimeFormat.getFormat(DatePattern.DAY_MONTH_YEAR.getPattern());


	@Inject
	PlaceManager placeManager;

	@NameToken(NameTokens.region)
	@ProxyCodeSplit
	interface MyProxy extends ProxyPlace<RegionPresenter> {
	}

	private EventBus eventBus;

	private ComboUtil comboUtil;
	
	@Inject
	RegionPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy, MainPresenter.SLOT_Main);
		this.eventBus = eventBus;
		comboUtil = new ComboUtil();
	}

	@Override
	protected void onBind() {
		super.onBind();
		onTabSelected();
		getAllRegions();
		getAllDistricts();
	}

	@Override
	protected void onReset() {
		super.onReset();
		HighlightActiveLinkEvent highlightActiveLinkEvent = new HighlightActiveLinkEvent(
				placeManager.getCurrentPlaceRequest().getNameToken());
		RegionPresenter.this.eventBus.fireEvent(highlightActiveLinkEvent);
	}

	private void onTabSelected() {
		getView().getTabSet().addTabSelectedHandler(new TabSelectedHandler() {

			@Override
			public void onTabSelected(TabSelectedEvent event) {

				String selectedTab = event.getTab().getTitle();

				if (selectedTab.equalsIgnoreCase(RegionView.REGION_TAB_TITLE)) {
					MenuButton newButton = new MenuButton("New");
					MenuButton edit = new MenuButton("Edit");
					MenuButton delete = new MenuButton("Delete");

					List<MenuButton> buttons = new ArrayList<>();
					buttons.add(newButton);
					buttons.add(edit);
					buttons.add(delete);

					getView().getControlsPane().addMenuButtons(buttons);
					addRegion(newButton);
					deleteRegion(delete);
					editRegion(edit);

				} else if (selectedTab.equalsIgnoreCase(RegionView.DISTRICT_TAB_TITLE)) {
					MenuButton newButton = new MenuButton("New");
					MenuButton edit = new MenuButton("Edit");
					MenuButton delete = new MenuButton("Delete");
					MenuButton filter = new MenuButton("Filter");

					List<MenuButton> buttons = new ArrayList<>();
					buttons.add(newButton);
					buttons.add(edit);
					buttons.add(filter);

					getView().getControlsPane().addMenuButtons(buttons);
					addDistrict(newButton);
					deleteDistrict(delete);
					editDistrict(edit);
					selectFilterOption(filter);

				} else {
					List<MenuButton> buttons = new ArrayList<>();
					getView().getControlsPane().addMenuButtons(buttons);
				}

			}

			
		});
	}
	
	private void selectFilterOption(final MenuButton filter) {
	       final Menu menu = new Menu();
	       MenuItem basic = new MenuItem("Base Filter");
	       MenuItem advanced = new MenuItem("Advanced Filter");
	       
	       menu.setItems(basic , advanced);
	      
	       filter.addClickHandler(new ClickHandler() {
	   		
	   		@Override
	   		public void onClick(ClickEvent event) {
	   			menu.showNextTo(filter, "bottom");
	   		}
	   	});

	       basic.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			
			@Override
			public void onClick(MenuItemClickEvent event) {
			SC.say("Basic Search");
			}
		});
	       
	       advanced.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
	   		
	   		@Override
	   		public void onClick(MenuItemClickEvent event) {
	   		FilterDistrictWindow window = new FilterDistrictWindow();
	   		loadFilterRegionCombo(window);
	   		window.show();
	        filterDistrictsByRegion(window);
	   		}		
	   	});
	       
		}

	
	//filter region combo
	private void loadFilterRegionCombo(final FilterDistrictWindow window) {
		
		comboUtil.loadRegionCombo(window.getFilterDistrictsPane().getRegionCombo() , dispatcher , placeManager , null);
		
//		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
//		map.put(RequestConstant.GET_REGION, null);
//		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
//		SC.showPrompt("", "", new SwizimaLoader());
//
//		dispatcher.execute(new RequestAction(RequestConstant.GET_REGION, map), new AsyncCallback<RequestResult>() {
//
//			@Override
//			public void onFailure(Throwable caught) {
//				System.out.println(caught.getMessage());
//				SC.warn("ERROR", caught.getMessage());
//				GWT.log("ERROR " + caught.getMessage());
//				SC.clearPrompt();
//
//			}
//
//			@Override
//			public void onSuccess(RequestResult result) {
//
//				SC.clearPrompt();
//				SessionManager.getInstance().manageSession(result, placeManager);
//
//				if (result != null) {
//					
//					SystemFeedbackDTO feedbackDTO = result.getSystemFeedbackDTO();
//					if (feedbackDTO != null) {
//						if (result.getSystemFeedbackDTO().isResponse()) {
//							LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();
//
//							for (RegionDto regionDto : result.getRegionDtos()) {
//								valueMap.put(regionDto.getId(), regionDto.getName());
//							}
//							window.getFilterDistrictsPane().getRegionCombo().setValueMap(valueMap);
//
//						} else {
//							SC.warn("Not Successful \n ERROR:", result.getSystemFeedbackDTO().getMessage());
//						}
//					}
//				} else {
//					SC.warn("ERROR", "Service Down");
//					// SC.warn("ERROR", "Unknow error");
//				}
//
//			}
//		});
	}

//////////////////////////////////////////////////////////////////////////REGION///////////////////////////////////////////////////////////////////

	public void addRegion(MenuButton menuButton) {
		menuButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				RegionWindow window = new RegionWindow();
				window.show();
				saveRegion(window);

			}
		});
	}

	private void saveRegion(final RegionWindow window) {
		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				
				if(checkIfNoRegionWindowFieldIsEmpty(window)) {
					RegionDto dto = new RegionDto();
					dto.setName(window.getNameField().getValueAsString());
					dto.setCode(window.getCodeField().getValueAsString());
					dto.setCreatedDateTime(dateTimeFormat.format(new Date()));

					LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					map.put(RequestConstant.SAVE_REGION, dto);
					map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
					SC.showPrompt("", "", new SwizimaLoader());

					dispatcher.execute(new RequestAction(RequestConstant.SAVE_REGION, map),
							new AsyncCallback<RequestResult>() {

								public void onFailure(Throwable caught) {

									SC.clearPrompt();
									System.out.println(caught.getMessage());
									SC.say("ERROR", caught.getMessage());
								}

								public void onSuccess(RequestResult result) {
									SC.clearPrompt();
									
									clearRegionWindowFields(window);

									SessionManager.getInstance().manageSession(result, placeManager);
									
									if (result != null) {
										SystemFeedbackDTO feedback = result.getSystemFeedbackDTO();

										if (feedback.isResponse()) {
											SC.say("SUCCESS", feedback.getMessage());
										} else {
											SC.warn("INFO", feedback.getMessage());
										}

										getView().getRegionPane().getListGrid().addRecordsToGrid(result.getRegionDtos());

									} else {
										SC.warn("ERROR", "Unknow error");
									}

								}

							});
				}else {
					SC.warn("Please fill all fields");
				}
				
				
			}

			
		});

	}

	
	private boolean checkIfNoRegionWindowFieldIsEmpty(RegionWindow window) {
        boolean flag = true;

        if(window.getCodeField().getValueAsString() == null) flag = false;
        
        if(window.getNameField().getValueAsString() == null) flag = false;
             
		return flag;
	}
	
	private void clearRegionWindowFields(RegionWindow window) {
		window.getCodeField().clearValue();
		window.getNameField().clearValue();
	}

	private void editRegion(MenuButton button) {
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (getView().getRegionPane().getListGrid().anySelected()) {

					RegionWindow window = new RegionWindow();
					window.getSaveButton().setTitle("Update");
					loadFieldsToEdit(window);
					updateRegion(window);
					window.show();

				} else {
					SC.say("Please select a record to update");
				}
			}

		});

	}

	private void updateRegion(final RegionWindow window) {
		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				ListGridRecord record = getView().getRegionPane().getListGrid().getSelectedRecord();

				RegionDto dto = new RegionDto();
				dto.setId(record.getAttribute(RegionListGrid.ID));
				dto.setName(window.getNameField().getValueAsString());
				dto.setCode(window.getCodeField().getValueAsString());
				dto.setUpdatedDateTime(dateTimeFormat.format(new Date()));

				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(RequestConstant.UPDATE_REGION, dto);
				map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());

				// GWT.log("ID "+record.getAttribute(RegionListGrid.ID));
  
				SC.showPrompt("", "", new SwizimaLoader());
                
				dispatcher.execute(new RequestAction(RequestConstant.UPDATE_REGION, map),
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
										clearRegionWindowFields(window);
										window.close();
										SC.say("SUCCESS", feedback.getMessage());

										getView().getRegionPane().getListGrid()
												.addRecordsToGrid(result.getRegionDtos());
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

	private void loadFieldsToEdit(RegionWindow window) {

		ListGridRecord record = getView().getRegionPane().getListGrid().getSelectedRecord();

		window.getCodeField().setValue(record.getAttribute(RegionListGrid.CODE));
		window.getNameField().setValue(record.getAttribute(RegionListGrid.NAME));
	}

	private void deleteRegion(MenuButton button) {
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (getView().getRegionPane().getListGrid().anySelected()) {
					SC.ask("Confirm", "Are you sure you want to delete the selected record", new BooleanCallback() {

						@Override
						public void execute(Boolean value) {
							if (value) {
								ListGridRecord record = getView().getRegionPane().getListGrid().getSelectedRecord();
								// SC.say("Id "+record.getAttributeAsString("id")+" Name
								// "+record.getAttributeAsString("name"));

								LinkedHashMap<String, Object> map = new LinkedHashMap<>();
								map.put(RequestConstant.DELETE_REGION, record.getAttributeAsString("id"));
								map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
								GWT.log("DELETE " + map);

								SC.showPrompt("", "", new SwizimaLoader());

								dispatcher.execute(new RequestAction(RequestConstant.DELETE_REGION, map),
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
													
													SystemFeedbackDTO feedbackDTO = result.getSystemFeedbackDTO();
													if (feedbackDTO != null) {
														if (feedbackDTO.isResponse()) {
															SC.say("SUCCESS",
																	result.getSystemFeedbackDTO().getMessage());

															getView().getRegionPane().getListGrid()
																	.addRecordsToGrid(result.getRegionDtos());

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

	private void getAllRegions() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.GET_REGION, null);
		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
		SC.showPrompt("", "", new SwizimaLoader());

		dispatcher.execute(new RequestAction(RequestConstant.GET_REGION, map), new AsyncCallback<RequestResult>() {

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
					
					SystemFeedbackDTO feedbackDTO = result.getSystemFeedbackDTO();
					if (feedbackDTO != null) {
						if (result.getSystemFeedbackDTO().isResponse()) {
							// SC.say("SUCCESS", result.getSystemFeedbackDTO().getMessage());
							getView().getRegionPane().getListGrid().addRecordsToGrid(result.getRegionDtos());
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

	/////////////////////////////////////////////////////////// DISTRICT////////////////////////////////////////////////////////////////////////

	protected void addDistrict(MenuButton newButton) {
		newButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				DistrictWindow window = new DistrictWindow();
				loadRolledOutCombo(window, null);
				loadRegionCombo(window, null);
				saveDistrict(window);
				window.show();

			}
		});

	}

	private void saveDistrict(final DistrictWindow window) {
		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				
				if(checkIfNoDistrictWindowFieldIsEmpty(window)) {
					DistrictDTO dto = new DistrictDTO();
					dto.setCode(window.getDistrictCode().getValueAsString());
					dto.setName(window.getDistrictName().getValueAsString());
					dto.setCreatedDateTime(dateTimeFormat.format(new Date()));

					dto.setRolledOut(Boolean.parseBoolean(window.getRolledOut().getValueAsString()));

					RegionDto regionDto = new RegionDto();
					regionDto.setId(window.getRegion().getValueAsString());

					dto.setRegion(regionDto);

					GWT.log("Dis " + dto);

					LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					map.put(RequestConstant.SAVE_DISTRICT, dto);
					map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
					SC.showPrompt("", "", new SwizimaLoader());

					dispatcher.execute(new RequestAction(RequestConstant.SAVE_DISTRICT, map),
							new AsyncCallback<RequestResult>() {

								public void onFailure(Throwable caught) {

									SC.clearPrompt();
									System.out.println(caught.getMessage());
									SC.say("ERROR", caught.getMessage());
								}

								public void onSuccess(RequestResult result) {
									SC.clearPrompt();
									clearDistrictWindowFields(window);
									
									SessionManager.getInstance().manageSession(result, placeManager);
									
									if (result != null) {
										SystemFeedbackDTO feedback = result.getSystemFeedbackDTO();

										if (feedback.isResponse()) {
											SC.say("SUCCESS", feedback.getMessage());
										} else {
											SC.warn("INFO", feedback.getMessage());
										}

										getView().getDistrictPane().getListGrid()
												.addRecordsToGrid(result.getDistrictDTOs());

									} else {
										SC.warn("ERROR", "Unknow error");
									}

								}

							});
				}else {
					SC.warn("Please fill all fields");
				}

			}

			
		});
	}
	
	private boolean checkIfNoDistrictWindowFieldIsEmpty(DistrictWindow window) {
		boolean flag = true;
	
		if(window.getDistrictCode().getValueAsString() == null) flag = false;
		
		if(window.getDistrictName().getValueAsString() == null) flag = false;
		
		if(window.getRegion().getValueAsString() == null) flag = false;
		
		if(window.getRolledOut().getValueAsString() == null) flag = false;
		
		return flag;
	}

	public void editDistrict(MenuButton button) {
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (getView().getDistrictPane().getListGrid().anySelected()) {
					DistrictWindow window = new DistrictWindow();
					window.getSaveButton().setTitle("Update");
					loadFieldsToEdit(window);
					updateDistrict(window);
					window.show();
				} else {
					SC.say("INFO: Select a record to update");
				}
			}
		});

	}

	private void loadFieldsToEdit(DistrictWindow window) {
		ListGridRecord record = getView().getDistrictPane().getListGrid().getSelectedRecord();
		window.getDistrictCode().setValue(record.getAttribute(DistrictListGrid.CODE));
		window.getDistrictName().setValue(record.getAttribute(DistrictListGrid.NAME));
		window.getRegion().setValue(record.getAttribute(DistrictListGrid.REGION));
		window.getRolledOut().setValue(record.getAttribute(DistrictListGrid.ROLLEDOUT));

		loadRolledOutCombo(window, record.getAttribute(DistrictListGrid.ROLLEDOUT_STATUS));
		loadRegionCombo(window, record.getAttribute(DistrictListGrid.REGION_ID));

	}

	private void updateDistrict(final DistrictWindow window) {
		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				ListGridRecord record = getView().getDistrictPane().getListGrid().getSelectedRecord();

				DistrictDTO dto = new DistrictDTO();
				dto.setId(record.getAttribute(DistrictListGrid.ID));
				dto.setCode(window.getDistrictCode().getValueAsString());
				dto.setName(window.getDistrictName().getValueAsString());
				dto.setUpdatedDateTime(dateTimeFormat.format(new Date()));

				dto.setRolledOut(Boolean.parseBoolean(window.getRolledOut().getValueAsString()));

				RegionDto regionDto = new RegionDto();
				regionDto.setId(window.getRegion().getValueAsString());

				dto.setRegion(regionDto);

				 GWT.log("reg "+regionDto.getId()+" roll "+dto.isRolledOut());

				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(RequestConstant.UPDATE_DISTRICT, dto);
				map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
				SC.showPrompt("", "", new SwizimaLoader());

				dispatcher.execute(new RequestAction(RequestConstant.UPDATE_DISTRICT, map),
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
										clearDistrictWindowFields(window);
										window.close();
										getView().getDistrictPane().getListGrid()
												.addRecordsToGrid(result.getDistrictDTOs());
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

	public void loadRolledOutCombo(DistrictWindow window, String defaultValue) {
		Map<Boolean, String> map = new LinkedHashMap<Boolean, String>();
		map.put(true, "Yes");
		map.put(false, "No");
		window.getRolledOut().setValueMap(map);

		if (defaultValue != null) {
			window.getRolledOut().setValue(defaultValue);
		}
	}

	//District window
	public void loadRegionCombo(final DistrictWindow window, final String defaultValue) {
		
		comboUtil.loadRegionCombo(window.getRegion(), dispatcher, placeManager, defaultValue);
		
//		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
//		map.put(RequestConstant.GET_REGION, null);
//		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
//		SC.showPrompt("", "", new SwizimaLoader());
//
//		dispatcher.execute(new RequestAction(RequestConstant.GET_REGION, map), new AsyncCallback<RequestResult>() {
//
//			@Override
//			public void onFailure(Throwable caught) {
//				System.out.println(caught.getMessage());
//				SC.warn("ERROR", caught.getMessage());
//				GWT.log("ERROR " + caught.getMessage());
//				SC.clearPrompt();
//			}
//
//			@Override
//			public void onSuccess(RequestResult result) {
//
//				SC.clearPrompt();
//				SessionManager.getInstance().manageSession(result, placeManager);
//
//				if (result != null) {
//
//					if (result.getSystemFeedbackDTO() != null) {
//						LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();
//
//						for (RegionDto regionDto : result.getRegionDtos()) {
//							valueMap.put(regionDto.getId(), regionDto.getName());
//						}
//						window.getRegion().setValueMap(valueMap);
//
//						if (defaultValue != null) {
//							window.getRegion().setValue(defaultValue);
//						}
//					}
//				} else {
//					SC.warn("ERROR", "Unknow error");
//				}
//
//			}
//		});
	}

	private void clearDistrictWindowFields(DistrictWindow window) {
		window.getDistrictName().clearValue();
		window.getDistrictCode().clearValue();
		window.getRolledOut().clearValue();
		window.getRegion().clearValue();
	}

	private void deleteDistrict(MenuButton button) {
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (getView().getRegionPane().getListGrid().anySelected()) {
					SC.ask("Confirm", "Are you sure you want to delete the selected record", new BooleanCallback() {

						@Override
						public void execute(Boolean value) {
							if (value) {
								ListGridRecord record = getView().getRegionPane().getListGrid().getSelectedRecord();
								// SC.say("Id "+record.getAttributeAsString("id")+" Name
								// "+record.getAttributeAsString("name"));

								LinkedHashMap<String, Object> map = new LinkedHashMap<>();
								map.put(RequestDelimeters.DISTRICT_ID, record.getAttributeAsString("id"));
								map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
								GWT.log("DELETE " + map);

								SC.showPrompt("", "", new SwizimaLoader());

								dispatcher.execute(new RequestAction(RequestConstant.DELETE_DISTRICT, map),
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
													SystemFeedbackDTO feedbackDTO = result.getSystemFeedbackDTO();
													if (feedbackDTO != null) {
														if (feedbackDTO.isResponse()) {
															SC.say("SUCCESS",
																	result.getSystemFeedbackDTO().getMessage());

															getView().getDistrictPane().getListGrid()
																	.addRecordsToGrid(result.getDistrictDTOs());

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

	private void getAllDistricts() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.GET_DISTRICT, null);
		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
		SC.showPrompt("", "", new SwizimaLoader());

		dispatcher.execute(new RequestAction(RequestConstant.GET_DISTRICT, map), new AsyncCallback<RequestResult>() {

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
							// SC.say("SUCCESS", result.getSystemFeedbackDTO().getMessage());\
							getView().getDistrictPane().getListGrid().addRecordsToGrid(result.getDistrictDTOs());
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
	
	
	public void filterDistrictsByRegion(final FilterDistrictWindow window) {
		window.getFilterDistrictsPane().getRegionCombo().addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				String id = window.getFilterDistrictsPane().getRegionCombo().getValueAsString();
		
				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
			
					map.put(RequestDelimeters.REGION_ID, id);	

				
				map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
				
				SC.showPrompt("", "", new SwizimaLoader());

				dispatcher.execute(new RequestAction(RequestConstant.GET_DISTRICTS_IN_REGION, map),
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
                                window.close();
                                
								if (result != null) {

									getView().getDistrictPane().getListGrid()
											.addRecordsToGrid(result.getDistrictDTOs());

								} else {
									// SC.warn("ERROR", "Unknown error");
									SC.warn("ERROR", "Service Down");
								}

							}
						});
			}
		});
		
	}

	
	
	
	
	

}