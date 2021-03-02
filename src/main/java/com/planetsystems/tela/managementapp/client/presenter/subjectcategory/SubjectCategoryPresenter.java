package com.planetsystems.tela.managementapp.client.presenter.subjectcategory;

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
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.planetsystems.tela.dto.AcademicTermDTO;
import com.planetsystems.tela.dto.DistrictDTO;
import com.planetsystems.tela.dto.RegionDto;
import com.planetsystems.tela.dto.SchoolClassDTO;
import com.planetsystems.tela.dto.SchoolDTO;
import com.planetsystems.tela.dto.SubjectCategoryDTO;
import com.planetsystems.tela.dto.SubjectDTO;
import com.planetsystems.tela.dto.SystemFeedbackDTO;
import com.planetsystems.tela.managementapp.client.event.HighlightActiveLinkEvent;
import com.planetsystems.tela.managementapp.client.gin.SessionManager;
import com.planetsystems.tela.managementapp.client.place.NameTokens;
import com.planetsystems.tela.managementapp.client.presenter.main.MainPresenter;
import com.planetsystems.tela.managementapp.client.presenter.region.DistrictListGrid;
import com.planetsystems.tela.managementapp.client.presenter.region.DistrictWindow;
import com.planetsystems.tela.managementapp.client.presenter.schoolcategory.SchoolClassListGrid;
import com.planetsystems.tela.managementapp.client.presenter.schoolcategory.SchoolClassWindow;
import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.planetsystems.tela.managementapp.client.widget.MenuButton;
import com.planetsystems.tela.managementapp.client.widget.SwizimaLoader;
import com.planetsystems.tela.managementapp.client.widget.TextField;
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

public class SubjectCategoryPresenter
		extends Presenter<SubjectCategoryPresenter.MyView, SubjectCategoryPresenter.MyProxy> {
	interface MyView extends View {
		ControlsPane getControlsPane();

		TabSet getTabSet();

		SubCategoryPane getSubCategoryPane();

		SubjectPane getSubjectPane();
	}

	@SuppressWarnings("deprecation")
	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_SubjectCategory = new Type<RevealContentHandler<?>>();

	@Inject
	private DispatchAsync dispatcher;

	@Inject
	private PlaceManager placeManager;
	
	DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat(DatePattern.DAY_MONTH_YEAR_HOUR_MINUTE_SECONDS.getPattern());
	DateTimeFormat dateFormat = DateTimeFormat.getFormat(DatePattern.DAY_MONTH_YEAR.getPattern());
	
	//private String token = Cookies.getCookie(RequestConstant.AUTH_TOKEN);

	@NameToken(NameTokens.subjectCategory)
	@ProxyCodeSplit
	interface MyProxy extends ProxyPlace<SubjectCategoryPresenter> {
	}

	private EventBus eventBus;

	@Inject
	SubjectCategoryPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy, MainPresenter.SLOT_Main);
		this.eventBus = eventBus;

	}

	@Override
	protected void onBind() {
		super.onBind();
		onTabSelected();
		getAllSubjectCategories();
		getAllSubjects();
	}

	@Override
	protected void onReset() {
		super.onReset();
		HighlightActiveLinkEvent highlightActiveLinkEvent = new HighlightActiveLinkEvent(
				placeManager.getCurrentPlaceRequest().getNameToken());
		SubjectCategoryPresenter.this.eventBus.fireEvent(highlightActiveLinkEvent);
	}

	private void onTabSelected() {
		getView().getTabSet().addTabSelectedHandler(new TabSelectedHandler() {

			@Override
			public void onTabSelected(TabSelectedEvent event) {

				String selectedTab = event.getTab().getTitle();

				if (selectedTab.equalsIgnoreCase(SubjectCategoryView.SUB_CATEGORY_TAB_TITLE)) {

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
					addSubjectCategory(newButton);
					deleteSubjectCategory(delete);
					editSubjectCategory(edit);

				} else if (selectedTab.equalsIgnoreCase(SubjectCategoryView.SUBJECT_TAB_TITLE)) {

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
					addSubject(newButton);
					deleteSubject(delete);
					editSubject(edit);

				} else {
					List<MenuButton> buttons = new ArrayList<>();
					getView().getControlsPane().addMenuButtons(buttons);
				}

			}
		});
	}

	//////////////////////////////////////////////////////////// SUBJECT
	//////////////////////////////////////////////////////////// CATEGORY///////////////////////////////////////////////////////////

	private void addSubjectCategory(MenuButton menuButton) {
		menuButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				SubjectCategoryWindow window = new SubjectCategoryWindow();
				window.show();
				saveSubjectCategory(window);

			}
		});
	}

	private void clearSubjectCategoryWindowFields(SubjectCategoryWindow window) {
		window.getCategoryCode().clearValue();
		window.getCategoryName().clearValue();
	}

	private void saveSubjectCategory(final SubjectCategoryWindow window) {
		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				
				if(checkIfNoSubjectCategoryWindowFieldIsEmpty(window)) {
					SubjectCategoryDTO dto = new SubjectCategoryDTO();
					dto.setName(window.getCategoryName().getValueAsString());
					dto.setCode(window.getCategoryCode().getValueAsString());
					dto.setCreatedDateTime(dateTimeFormat.format(new Date()));

					LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					map.put(RequestConstant.SAVE_SUBJECT_CATEGORY, dto);
					map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
					SC.showPrompt("", "", new SwizimaLoader());

					dispatcher.execute(new RequestAction(RequestConstant.SAVE_SUBJECT_CATEGORY , map),
							new AsyncCallback<RequestResult>() {

								public void onFailure(Throwable caught) {

									SC.clearPrompt();
									System.out.println(caught.getMessage());
									SC.say("ERROR", caught.getMessage());
								}

								public void onSuccess(RequestResult result) {
									SC.clearPrompt();
									clearSubjectCategoryWindowFields(window);
									SessionManager.getInstance().manageSession(result, placeManager);
									if (result != null) {
										SystemFeedbackDTO feedbackDTO = result.getSystemFeedbackDTO();

										if (feedbackDTO.isResponse()) {
											SC.say("SUCCESS", feedbackDTO.getMessage());

											getView().getSubCategoryPane().getListGrid()
													.addRecordsToGrid(result.getSubjectCategoryDTOs());
										} else {
											SC.warn("ERROR", result.getSystemFeedbackDTO().getMessage());
										}

									} else {
										SC.warn("ERROR", "Service Down");
										// SC.warn("ERROR", "Unknow error");
									}

								}
							});

				}else {
					SC.say("Please fill all fields");
				}

			}

			
		});
	}

	private boolean checkIfNoSubjectCategoryWindowFieldIsEmpty(SubjectCategoryWindow window) {
		boolean flag = true;
		
		if(window.getCategoryCode().getValueAsString() == null) flag = false;
		
		if(window.getCategoryName().getValueAsString() == null) flag = false;
		
		return flag;
	}
	
	private void editSubjectCategory(MenuButton button) {
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (getView().getSubCategoryPane().getListGrid().anySelected()) {
					SubjectCategoryWindow window = new SubjectCategoryWindow();
					window.getSaveButton().setTitle("Update");
					loadFieldsToEdit(window);
					updateSubjectCategory(window);
					window.show();
				} else {
					SC.say("INFO: please select record to update");
				}
			}
		});

	}

	private void updateSubjectCategory(final SubjectCategoryWindow window) {
		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				ListGridRecord record = getView().getSubCategoryPane().getListGrid().getSelectedRecord();

				SubjectCategoryDTO dto = new SubjectCategoryDTO();
				dto.setName(window.getCategoryName().getValueAsString());
				dto.setCode(window.getCategoryCode().getValueAsString());
				dto.setId(record.getAttribute(SubCategoryListGrid.ID));
				dto.setUpdatedDateTime(dateTimeFormat.format(new Date()));

				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(RequestConstant.UPDATE_SUBJECT_CATEGORY, dto);
				map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
				SC.showPrompt("", "", new SwizimaLoader());

			//	GWT.log("dto " + dto);

				dispatcher.execute(new RequestAction(RequestConstant.UPDATE_SUBJECT_CATEGORY , map),
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
											SC.say("SUCCESS", result.getSystemFeedbackDTO().getMessage());
											clearSubjectCategoryWindowFields(window); 
											window.close();
											
											getView().getSubCategoryPane().getListGrid()
													.addRecordsToGrid(result.getSubjectCategoryDTOs());

										} else {
											SC.warn("ERROR", feedbackDTO.getMessage());
										}
									}
								} else {
									SC.warn("ERROR", "Service Down");
									//SC.warn("ERROR", "Unknow error");
								}

							}
						});

			}
		});

	}

	private void loadFieldsToEdit(SubjectCategoryWindow window) {
		ListGridRecord record = getView().getSubCategoryPane().getListGrid().getSelectedRecord();

		window.getCategoryCode().setValue(record.getAttribute(SchoolClassListGrid.CODE));
		window.getCategoryName().setValue(record.getAttribute(SchoolClassListGrid.NAME));
	}

	private void deleteSubjectCategory(MenuButton button) {
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (getView().getSubCategoryPane().getListGrid().anySelected()) {
					SC.ask("Confirm", "Are you sure you want to delete the selected record", new BooleanCallback() {

						@Override
						public void execute(Boolean value) {
							if (value) {
								ListGridRecord record = getView().getSubCategoryPane().getListGrid()
										.getSelectedRecord();
								// SC.say("Id "+record.getAttributeAsString("id")+" Name
								// "+record.getAttributeAsString("name"));

								LinkedHashMap<String, Object> map = new LinkedHashMap<>();
								map.put(RequestConstant.DELETE_SUBJECT_CATEGORY, record.getAttributeAsString("id"));
								map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
								// map.put(RequestConstant.LOGIN_TOKEN, loginToken);
								GWT.log("DELETE " + map);

								SC.showPrompt("", "", new SwizimaLoader());

								dispatcher.execute(new RequestAction(RequestConstant.DELETE_SUBJECT_CATEGORY , map),
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

															setSubjectCategoryGridData(result.getSubjectCategoryDTOs());

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

	private void getAllSubjectCategories() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.GET_SUBJECT_CATEGORY, null);
		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
		SC.showPrompt("", "", new SwizimaLoader());

		dispatcher.execute(new RequestAction(RequestConstant.GET_SUBJECT_CATEGORY , map),
				new AsyncCallback<RequestResult>() {

					@Override
					public void onFailure(Throwable caught) {
						SC.clearPrompt();
						System.out.println(caught.getMessage());
						SC.warn("ERROR", caught.getMessage());
						GWT.log("ERROR " + caught.getMessage());
					}

					@Override
					public void onSuccess(RequestResult result) {

						SC.clearPrompt();
						SessionManager.getInstance().manageSession(result, placeManager);
						if (result != null) {

							if (result.getSystemFeedbackDTO() != null) {
								if (result.getSystemFeedbackDTO().isResponse()) {
									// SC.say("SUCCESS", result.getSystemFeedbackDTO().getMessage());
									setSubjectCategoryGridData(result.getSubjectCategoryDTOs());

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

	private void setSubjectCategoryGridData(List<SubjectCategoryDTO> subjectCategoryDTOs) {
		getView().getSubCategoryPane().getListGrid().addRecordsToGrid(subjectCategoryDTOs);
	}

	///////////////////////////////////////// SUBJECT/////////////////////////////////////////////////////////////////////////////////

	private void addSubject(MenuButton menuButton) {
		menuButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				SubjectWindow window = new SubjectWindow();
				window.show();
				loadSubjectCategoryCombo(window, null);
				saveSubject(window);

			}
		});
	}

	private void loadSubjectCategoryCombo(final SubjectWindow window, final String defaultValue) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.GET_SUBJECT_CATEGORY, null);
		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
		SC.showPrompt("", "", new SwizimaLoader());

		dispatcher.execute(new RequestAction(RequestConstant.GET_SUBJECT_CATEGORY , map),
				new AsyncCallback<RequestResult>() {

					@Override
					public void onFailure(Throwable caught) {
						SC.clearPrompt();
						System.out.println(caught.getMessage());
						SC.warn("ERROR", caught.getMessage());
						GWT.log("ERROR " + caught.getMessage());
					}

					@Override
					public void onSuccess(RequestResult result) {

						SC.clearPrompt();
						SessionManager.getInstance().manageSession(result, placeManager);
						if (result != null) {

							if (result.getSystemFeedbackDTO() != null) {
								LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();

								for (SubjectCategoryDTO subjectCategoryDTO : result.getSubjectCategoryDTOs()) {
									valueMap.put(subjectCategoryDTO.getId(), subjectCategoryDTO.getName());
								}
								window.getSubjectCategory().setValueMap(valueMap);
								if (defaultValue != null) {
									window.getSubjectCategory().setValue(defaultValue);
								}
							}
						} else {
							SC.warn("ERROR", "Unknow error");
						}

					}
				});
	}

	public void clearSubjectWindowFields(SubjectWindow window) {
		window.getSubjectCode().clearValue();
		window.getSubjectName().clearValue();
		window.getSubjectCategory().clearValue();
	}

	private void saveSubject(final SubjectWindow window) {
		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				
				if(checkIfNoSubjectWindowFieldIsEmpty(window)) {
					SubjectDTO dto = new SubjectDTO();
					dto.setName(window.getSubjectName().getValueAsString());
					dto.setCode(window.getSubjectCode().getValueAsString());
					dto.setCreatedDateTime(dateTimeFormat.format(new Date()));

					SubjectCategoryDTO subjectCategory = new SubjectCategoryDTO();
					subjectCategory.setId(window.getSubjectCategory().getValueAsString());
					dto.setSubjectCategory(subjectCategory);

					LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					map.put(RequestConstant.SAVE_SUBJECT, dto);
					map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
					SC.showPrompt("", "", new SwizimaLoader());

					dispatcher.execute(new RequestAction(RequestConstant.SAVE_SUBJECT , map),
							new AsyncCallback<RequestResult>() {

								public void onFailure(Throwable caught) {

									SC.clearPrompt();
									System.out.println(caught.getMessage());
									SC.say("ERROR", caught.getMessage());
								}

								public void onSuccess(RequestResult result) {
									SC.clearPrompt();
									clearSubjectWindowFields(window);
									SessionManager.getInstance().manageSession(result, placeManager);
									if (result != null) {

										SystemFeedbackDTO feedbackDTO = result.getSystemFeedbackDTO();
										if (feedbackDTO.isResponse()) {
											SC.say("SUCCESS", feedbackDTO.getMessage());

											getView().getSubjectPane().getListGrid().addRecordsToGrid(result.getSubjectDTOs());
											
										} else {
											SC.warn("ERROR", feedbackDTO.getMessage());
										}

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
	
	private boolean checkIfNoSubjectWindowFieldIsEmpty(SubjectWindow window) {
		boolean flag = true;
		if(window.getSubjectCategory().getValueAsString() == null) flag = false;
		
		if(window.getSubjectName().getValueAsString() == null) flag = false;
		
		if(window.getSubjectCode().getValueAsString() == null) flag = false;
		
		return flag;
	}

	public void editSubject(MenuButton button) {
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (getView().getSubjectPane().getListGrid().anySelected()) {
					SubjectWindow window = new SubjectWindow();
					window.getSaveButton().setTitle("Update");
					loadFieldsToEdit(window);
					updateSubject(window);
					window.show();
				} else {
					SC.say("INFO: Select a record to update");
				}
			}
		});

	}

	private void loadFieldsToEdit(SubjectWindow window) {
		ListGridRecord record = getView().getSubjectPane().getListGrid().getSelectedRecord();

		window.getSubjectCategory().setValue(record.getAttribute(SubjectListGrid.SUBJECT_CATEGORY));
		window.getSubjectCode().setValue(record.getAttribute(SubjectListGrid.CODE));
		window.getSubjectName().setValue(record.getAttribute(SubjectListGrid.NAME));

		loadSubjectCategoryCombo(window, record.getAttribute(SubjectListGrid.SUBJECT_CATEGORY_ID));
	}

	private void updateSubject(final SubjectWindow window) {
		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				ListGridRecord record = getView().getSubjectPane().getListGrid().getSelectedRecord();

				SubjectDTO dto = new SubjectDTO();
				dto.setId(record.getAttribute(SubjectListGrid.ID));
				dto.setName(window.getSubjectName().getValueAsString());
				dto.setCode(window.getSubjectCode().getValueAsString());
				dto.setUpdatedDateTime(dateTimeFormat.format(new Date()));

				SubjectCategoryDTO subjectCategory = new SubjectCategoryDTO();
				subjectCategory.setId(window.getSubjectCategory().getValueAsString());
				dto.setSubjectCategory(subjectCategory);

				//GWT.log("dto "+dto.getId()+" cat "+subjectCategory.getId());
				
				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(RequestConstant.UPDATE_SUBJECT, dto);
				map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
				
				SC.showPrompt("", "", new SwizimaLoader());

				dispatcher.execute(new RequestAction(RequestConstant.UPDATE_SUBJECT , map),
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
										SystemFeedbackDTO feedbackDTO = result.getSystemFeedbackDTO();
										if (feedbackDTO.isResponse()) {
											SC.say("SUCCESS", feedbackDTO.getMessage());
											clearSubjectWindowFields(window);
											window.clear();
											getView().getSubjectPane().getListGrid().addRecordsToGrid(result.getSubjectDTOs());

										} else {
											SC.warn("ERROR", feedbackDTO.getMessage());
										}
									}
								} else {
									SC.warn("ERROR", "Unknow error");
								}

							}
						});

			}
		});
	}

	private void deleteSubject(MenuButton button) {
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (getView().getSubjectPane().getListGrid().anySelected()) {
					SC.ask("Confirm", "Are you sure you want to delete the selected record", new BooleanCallback() {

						@Override
						public void execute(Boolean value) {
							if (value) {
								ListGridRecord record = getView().getSubjectPane().getListGrid().getSelectedRecord();
								// SC.say("Id "+record.getAttributeAsString("id")+" Name
								// "+record.getAttributeAsString("name"));

								LinkedHashMap<String, Object> map = new LinkedHashMap<>();
								map.put(RequestConstant.DELETE_SUBJECT, record.getAttributeAsString("id"));
								map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
								// map.put(RequestConstant.LOGIN_TOKEN, loginToken);
								GWT.log("DELETE " + map);

								SC.showPrompt("", "", new SwizimaLoader());

								dispatcher.execute(new RequestAction(RequestConstant.DELETE_SUBJECT , map),
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
														SystemFeedbackDTO feedbackDTO = result.getSystemFeedbackDTO();
														if (feedbackDTO.isResponse()) {
															SC.say("SUCCESS",
																	result.getSystemFeedbackDTO().getMessage());

															getView().getSubjectPane().getListGrid().addRecordsToGrid(result.getSubjectDTOs());

														} else {
															SC.warn("ERROR",
																	feedbackDTO.getMessage());
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

	private void getAllSubjects() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.GET_SUBJECT, null);
		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
		SC.showPrompt("", "", new SwizimaLoader());

		dispatcher.execute(new RequestAction(RequestConstant.GET_SUBJECT , map), new AsyncCallback<RequestResult>() {

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
							getView().getSubjectPane().getListGrid().addRecordsToGrid(result.getSubjectDTOs());

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


}