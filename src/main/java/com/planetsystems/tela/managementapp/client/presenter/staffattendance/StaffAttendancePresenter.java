package com.planetsystems.tela.managementapp.client.presenter.staffattendance;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

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
import com.planetsystems.tela.dto.AcademicTermDTO;
import com.planetsystems.tela.dto.AcademicYearDTO;
import com.planetsystems.tela.dto.ClockInDTO;
import com.planetsystems.tela.dto.ClockOutDTO;
import com.planetsystems.tela.dto.DistrictDTO;
import com.planetsystems.tela.dto.SchoolDTO;
import com.planetsystems.tela.dto.SchoolStaffDTO;
import com.planetsystems.tela.dto.SystemFeedbackDTO;
import com.planetsystems.tela.managementapp.client.gin.SessionManager;
import com.planetsystems.tela.managementapp.client.place.NameTokens;
import com.planetsystems.tela.managementapp.client.presenter.learnerenrollment.FilterLearnerHeadCountWindow;
import com.planetsystems.tela.managementapp.client.presenter.main.MainPresenter;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.planetsystems.tela.managementapp.client.widget.MenuButton;
import com.planetsystems.tela.managementapp.client.widget.SwizimaLoader;
import com.planetsystems.tela.managementapp.shared.DatePattern;
import com.planetsystems.tela.managementapp.shared.RequestAction;
import com.planetsystems.tela.managementapp.shared.RequestConstant;
import com.planetsystems.tela.managementapp.shared.RequestResult;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.HoverEvent;
import com.smartgwt.client.widgets.events.HoverHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;

public class StaffAttendancePresenter
		extends Presenter<StaffAttendancePresenter.MyView, StaffAttendancePresenter.MyProxy> {
	interface MyView extends View {
		public ControlsPane getControlsPane();

		public TabSet getTabSet();

		public ClockOutPane getClockOutPane();

		public ClockInPane getClockInPane();
	}

	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_attendance = new Type<RevealContentHandler<?>>();

	@Inject
	private PlaceManager placeManager;

	@Inject
	private DispatchAsync dispatcher;

	DateTimeFormat dateTimeFormat = DateTimeFormat
			.getFormat(DatePattern.DAY_MONTH_YEAR_HOUR_MINUTE_SECONDS.getPattern());
	DateTimeFormat dateFormat = DateTimeFormat.getFormat(DatePattern.DAY_MONTH_YEAR.getPattern());

	@NameToken(NameTokens.staffAttendance)
	@ProxyCodeSplit
	interface MyProxy extends ProxyPlace<StaffAttendancePresenter> {
	}

	@Inject
	StaffAttendancePresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy, MainPresenter.SLOT_Main);
	}

	@Override
	protected void onBind() {
		super.onBind();
		onTabSelected();
		getAllStaffClockIn();
	}

	private void onTabSelected() {
		getView().getTabSet().addTabSelectedHandler(new TabSelectedHandler() {

			@Override
			public void onTabSelected(TabSelectedEvent event) {

				String selectedTab = event.getTab().getTitle();

				if (selectedTab.equalsIgnoreCase(StaffAttendanceView.CLOCKIN_TAB_TITLE)) {
					MenuButton clockInButton = new MenuButton("ClockIn");
					MenuButton edit = new MenuButton("Edit");
					MenuButton delete = new MenuButton("Delete");
					MenuButton clockOut = new MenuButton("ClockOut");
					MenuButton filter = new MenuButton("Filter");

					List<MenuButton> buttons = new ArrayList<>();
					buttons.add(clockInButton);
					// buttons.add(edit);
					// buttons.add(delete);
					buttons.add(filter);
					buttons.add(clockOut);

					getView().getControlsPane().addMenuButtons(buttons);

					addClockIn(clockInButton);
					clockOut(clockOut);
					selectFilterOption(filter);	

				} else if (selectedTab.equalsIgnoreCase(StaffAttendanceView.CLOCKOUT_TAB_TITLE)) {
					getAllStaffClockOut();
					// MenuButton newButton = new MenuButton("New");
					MenuButton edit = new MenuButton("Edit");
					MenuButton delete = new MenuButton("Delete");
					MenuButton filter = new MenuButton("Filter");

					List<MenuButton> buttons = new ArrayList<>();
					/// buttons.add(newButton);
					// buttons.add(edit);
					// buttons.add(delete);
					buttons.add(filter);

					getView().getControlsPane().addMenuButtons(buttons);
					selectFilterClockOutOption(filter);	

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
//	   		SC.say("Advanced Search");
	   		FilterClockInWindow window = new FilterClockInWindow();
	   		loadFilterClockInAcademicYearCombo(window);
			loadFilterClockInAcademicTermCombo(window);
			loadFilterClockInDistrictCombo(window);
			loadFilterClockInSchoolCombo(window);
	   		window.show();
	   		filterClockInsByAcademicYearAcademicTermDistrictSchool(window);
	   		disableEnableFilterButton(window);
	   		}		
	   	});
	       
		}


	
	
	
	private void disableEnableFilterButton(final FilterClockInWindow window) {;
	window.getFilterClockInPane().getAcademicTermCombo().addChangedHandler(new ChangedHandler() {

		@Override
		public void onChanged(ChangedEvent event) {

			if (window.getFilterClockInPane().getAcademicTermCombo().getValueAsString() != null && window.getFilterClockInPane().getSchoolCombo().getValueAsString() != null) {
				window.getFilterButton().setDisabled(false);
			}else {
				window.getFilterButton().setDisabled(true);	
			}
		}
	});

	window.getFilterClockInPane().getSchoolCombo().addChangedHandler(new ChangedHandler() {

		@Override
		public void onChanged(ChangedEvent event) {
			if (window.getFilterClockInPane().getAcademicTermCombo().getValueAsString() != null && window.getFilterClockInPane().getSchoolCombo().getValueAsString() != null) {
                window.getFilterButton().setDisabled(false);
			}else {
				window.getFilterButton().setDisabled(true);
			}
		}
	});

}
	
	
	
	private void selectFilterClockOutOption(final MenuButton filter) {
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
//	   		SC.say("Advanced Search");
	   		FilterClockOutWindow window = new FilterClockOutWindow();
	   		loadFilterClockOutAcademicYearCombo(window);
			loadFilterClockOutAcademicTermCombo(window);
			loadFilterClockOutDistrictCombo(window);
			loadFilterClockOutSchoolCombo(window);
	   		window.show();
	   		filterClockOutsByAcademicYearAcademicTermDistrictSchool(window);
	   		disableEnableFilterButton(window);
	   		}		
	   	});
	       
		}
	
	
	private void disableEnableFilterButton(final FilterClockOutWindow window) {;
	window.getFilterClockOutPane().getAcademicTermCombo().addChangedHandler(new ChangedHandler() {

		@Override
		public void onChanged(ChangedEvent event) {

			if (window.getFilterClockOutPane().getAcademicTermCombo().getValueAsString() != null && window.getFilterClockOutPane().getSchoolCombo().getValueAsString() != null) {
				window.getFilterButton().setDisabled(false);
			}else {
				 window.getFilterButton().setDisabled(true);	
			}
		}
	});

	window.getFilterClockOutPane().getSchoolCombo().addChangedHandler(new ChangedHandler() {

		@Override
		public void onChanged(ChangedEvent event) {
			if (window.getFilterClockOutPane().getAcademicTermCombo().getValueAsString() != null && window.getFilterClockOutPane().getSchoolCombo().getValueAsString() != null) {
				window.getFilterButton().setDisabled(false);
			}else {
				window.getFilterButton().setDisabled(true);
			}
		}
	});

}

	
	
	
	///////////////////////////////////////
	private void addClockIn(MenuButton clockInButton) {
		clockInButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				ClockInWindow window = new ClockInWindow();
				window.show();

				loadAcademicTermCombo(window, null);
				loadSchoolStaffCombo(window, null);

				saveClockin(window);
			}
		});

	}

	protected void saveClockin(final ClockInWindow window) {
		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				if (checkIfNoClockInWindowFieldIsEmpty(window)) {
					ClockInDTO dto = new ClockInDTO();
					// dto.setClockInDate(clockInDate);
					dto.setComment(window.getCommentField().getValueAsString());
					// dto.setId(id);
					dto.setLatitude(window.getLatitudeField().getValueAsString());
					dto.setLongitude(window.getLongitudeField().getValueAsString());
					dto.setCreatedDateTime(dateTimeFormat.format(new Date()));

					AcademicTermDTO academicTermDTO = new AcademicTermDTO(
							window.getAcademicTermComboBox().getValueAsString());
					dto.setAcademicTermDTO(academicTermDTO);

					SchoolStaffDTO schoolStaffDTO = new SchoolStaffDTO();
					schoolStaffDTO.setId(window.getSchoolStaffComboBox().getValueAsString());
					dto.setSchoolStaffDTO(schoolStaffDTO);

					GWT.log("DTO " + dto);
					GWT.log("Term " + dto.getAcademicTermDTO().getId());
					GWT.log("Staff " + dto.getSchoolStaffDTO().getId());

					LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					map.put(RequestConstant.SAVE_CLOCK_IN, dto);
					map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
					SC.showPrompt("", "", new SwizimaLoader());

					dispatcher.execute(new RequestAction(RequestConstant.SAVE_CLOCK_IN, map),
							new AsyncCallback<RequestResult>() {

								public void onFailure(Throwable caught) {

									SC.clearPrompt();
									System.out.println(caught.getMessage());
									SC.say("ERROR", caught.getMessage());
								}

								public void onSuccess(RequestResult result) {
									SC.clearPrompt();

									clearClockInWindowFields(window);
									window.close();

									SessionManager.getInstance().manageSession(result, placeManager);

									if (result != null) {
										SystemFeedbackDTO feedback = result.getSystemFeedbackDTO();

										if (feedback.isResponse()) {
											SC.say("SUCCESS", feedback.getMessage());
										} else {
											SC.warn("INFO", feedback.getMessage());
										}

										getView().getClockInPane().getClockInListGrid()
												.addRecordsToGrid(result.getClockInDTOs());

									} else {
										SC.warn("ERROR", "Unknow error");
									}

								}

							});

				} else {
					SC.say("Please fill all the fields");
				}

			}

		});

	}

	private boolean checkIfNoClockInWindowFieldIsEmpty(ClockInWindow window) {
		boolean flag = true;

		if (window.getAcademicTermComboBox().getValueAsString() == null)
			flag = false;

		if (window.getSchoolStaffComboBox().getValueAsString() == null)
			flag = false;

		// if(window.getCommentField().getValueAsString() == null) flag = false;

		if (window.getLatitudeField().getValueAsString() == null)
			flag = false;

		if (window.getLongitudeField().getValueAsString() == null)
			flag = false;

		return flag;
	}

	private void clearClockInWindowFields(ClockInWindow window) {
		window.getAcademicTermComboBox().clearValue();
		window.getSchoolStaffComboBox().clearValue();
		window.getCommentField().clearValue();
		window.getLatitudeField().clearValue();
		window.getLongitudeField().clearValue();
	}

	private void loadAcademicTermCombo(final ClockInWindow window, final String defaultValue) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.GET_ACADEMIC_TERM, null);
		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
		SC.showPrompt("", "", new SwizimaLoader());

		dispatcher.execute(new RequestAction(RequestConstant.GET_ACADEMIC_TERM, map),
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
								window.getAcademicTermComboBox().setValueMap(valueMap);

								if (defaultValue != null) {
									window.getAcademicTermComboBox().setValue(defaultValue);
								}

							}
						} else {
							SC.warn("ERROR", "Unknow error");
						}

					}
				});
	}

	private void loadSchoolStaffCombo(final ClockInWindow window, final String defaultValue) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.GET_SCHOOL_STAFF, null);
		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
//		SC.showPrompt("", "", new SwizimaLoader());

		dispatcher.execute(new RequestAction(RequestConstant.GET_SCHOOL_STAFF, map),
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

								for (SchoolStaffDTO schoolStaffDTO : result.getSchoolStaffDTOs()) {
									String fullName = schoolStaffDTO.getGeneralUserDetailDTO().getFirstName()
											+ schoolStaffDTO.getGeneralUserDetailDTO().getLastName();
									valueMap.put(schoolStaffDTO.getId(), fullName);
								}
								window.getSchoolStaffComboBox().setValueMap(valueMap);

								if (defaultValue != null) {
									window.getSchoolStaffComboBox().setValue(defaultValue);
								}

							}
						} else {
							SC.warn("ERROR", "Unknow error");
						}

					}
				});
	}

	private void getAllStaffClockIn() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.GET_CLOCK_IN, null);
		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
		SC.showPrompt("", "", new SwizimaLoader());

		dispatcher.execute(new RequestAction(RequestConstant.GET_CLOCK_IN, map), new AsyncCallback<RequestResult>() {

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
						if (feedbackDTO.isResponse()) {
							// SC.say("SUCCESS", result.getSystemFeedbackDTO().getMessage());
							getView().getClockInPane().getClockInListGrid().addRecordsToGrid(result.getClockInDTOs());
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

	//////////////////////// clockout

	private void clockOut(MenuButton clockOutButton) {

		clockOutButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (getView().getClockInPane().getClockInListGrid().anySelected()) {
					ListGridRecord record = getView().getClockInPane().getClockInListGrid().getSelectedRecord();
					ClockOutDTO dto = new ClockOutDTO();

					ClockInDTO clockInDTO = new ClockInDTO();
					clockInDTO.setId(record.getAttribute(ClockInListGrid.ID));

					dto.setClockInDTO(clockInDTO);
					dto.setComment(record.getAttribute(ClockInListGrid.COMMENT));
					dto.setCreatedDateTime(dateTimeFormat.format(new Date()));
//            	 SC.say("Hello");
					GWT.log("DTO " + dto);
					GWT.log("DTO id  " + dto.getClockInDTO().getId());

					GWT.log("date " + dto.getCreatedDateTime());

					LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					map.put(RequestConstant.SAVE_CLOCK_OUT, dto);
					map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
					SC.showPrompt("", "", new SwizimaLoader());

					dispatcher.execute(new RequestAction(RequestConstant.SAVE_CLOCK_OUT, map),
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
										SystemFeedbackDTO feedbackDTO = result.getSystemFeedbackDTO();
										if (feedbackDTO != null) {
											if (feedbackDTO.isResponse()) {
												SC.say("SUCCESS", result.getSystemFeedbackDTO().getMessage());
//          									getView().getClockInPane().getClockInListGrid().addRecordsToGrid(result.getClockInDTOs());
												getAllStaffClockIn();
											} else {
												SC.warn("Not Successful \n ERROR:",
														result.getSystemFeedbackDTO().getMessage());
											}
										}
									} else {
										SC.warn("ERROR", "Unknow error");
									}

								}

							});

				} else {
					SC.say("Please Select A record to clockout");
				}
			}
		});

	}

	private void getAllStaffClockOut() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.GET_CLOCK_OUT, null);
		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
		SC.showPrompt("", "", new SwizimaLoader());

		dispatcher.execute(new RequestAction(RequestConstant.GET_CLOCK_OUT, map), new AsyncCallback<RequestResult>() {

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
						if (feedbackDTO.isResponse()) {
							// SC.say("SUCCESS", result.getSystemFeedbackDTO().getMessage());
							getView().getClockOutPane().getClockOutListGrid()
									.addRecordsToGrid(result.getClockOutDTOs());
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

///////////////////////FILTER CLOCKIN COMBOS(4)

//loads school combo in filter learner head count pane
	private void loadFilterClockInSchoolCombo(final FilterClockInWindow window) {
		window.getFilterClockInPane().getDistrictCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				String districtId = window.getFilterClockInPane().getDistrictCombo().getValueAsString();
				map.put(RequestConstant.GET_SCHOOLS_IN_DISTRICT, districtId);
				map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
				SC.showPrompt("", "", new SwizimaLoader());

				dispatcher.execute(new RequestAction(RequestConstant.GET_SCHOOLS_IN_DISTRICT, map),
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

										for (SchoolDTO schoolDTO : result.getSchoolDTOs()) {
											valueMap.put(schoolDTO.getId(), schoolDTO.getName());
										}
										window.getFilterClockInPane().getSchoolCombo().setValueMap(valueMap);
									}
								} else {
									SC.warn("ERROR", "Unknow error");
								}

							}
						});
			}
		});

	}

//loads district combo in filter learner head count pane	
	private void loadFilterClockInDistrictCombo(final FilterClockInWindow window) {
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
						LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();

						for (DistrictDTO districtDTO : result.getDistrictDTOs()) {
							valueMap.put(districtDTO.getId(), districtDTO.getName());
						}
						window.getFilterClockInPane().getDistrictCombo().setValueMap(valueMap);
					}
				} else {
					SC.warn("ERROR", "Unknow error");
				}

			}
		});
	}

//loads academic year combo in filter learner head count pane	
	private void loadFilterClockInAcademicYearCombo(final FilterClockInWindow window) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.GET_ACADEMIC_YEAR, null);
		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
		SC.showPrompt("", "", new SwizimaLoader());

		dispatcher.execute(new RequestAction(RequestConstant.GET_ACADEMIC_YEAR, map),
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

								for (AcademicYearDTO academicYearDTO : result.getAcademicYearDTOs()) {
									valueMap.put(academicYearDTO.getId(), academicYearDTO.getName());
								}
								window.getFilterClockInPane().getAcademicYearCombo().setValueMap(valueMap);
							}
						} else {
							SC.warn("ERROR", "Unknow error");
						}

					}
				});
	}

//loads academic year combo in filter learner head count pane	
	private void loadFilterClockInAcademicTermCombo(final FilterClockInWindow window) {
		window.getFilterClockInPane().getAcademicYearCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				String academicYearId = window.getFilterClockInPane().getAcademicYearCombo().getValueAsString();
				map.put(RequestConstant.GET_ACADEMIC_TERMS_IN_ACADEMIC_YEAR, academicYearId);
				map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
				SC.showPrompt("", "", new SwizimaLoader());

				dispatcher.execute(new RequestAction(RequestConstant.GET_ACADEMIC_TERMS_IN_ACADEMIC_YEAR, map),
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
										window.getFilterClockInPane().getAcademicTermCombo().setValueMap(valueMap);
									}
								} else {
									SC.warn("ERROR", "Unknow error");
								}

							}
						});
			}
		});

	}

/////////////////////////////////////////END OF FILTER CLOCKIN COMBOS

///////////////////////FILTER CLOCKOUT COMBOS(4)

//loads school combo in filter learner head count pane
	private void loadFilterClockOutSchoolCombo(final FilterClockOutWindow window) {
		window.getFilterClockOutPane().getDistrictCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				String districtId = window.getFilterClockOutPane().getDistrictCombo().getValueAsString();
				map.put(RequestConstant.GET_SCHOOLS_IN_DISTRICT, districtId);
				map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
				SC.showPrompt("", "", new SwizimaLoader());

				dispatcher.execute(new RequestAction(RequestConstant.GET_SCHOOLS_IN_DISTRICT, map),
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

										for (SchoolDTO schoolDTO : result.getSchoolDTOs()) {
											valueMap.put(schoolDTO.getId(), schoolDTO.getName());
										}
										window.getFilterClockOutPane().getSchoolCombo().setValueMap(valueMap);
									}
								} else {
									SC.warn("ERROR", "Unknow error");
								}

							}
						});
			}
		});

	}

//loads district combo in filter learner head count pane	
	private void loadFilterClockOutDistrictCombo(final FilterClockOutWindow window) {
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
						LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();

						for (DistrictDTO districtDTO : result.getDistrictDTOs()) {
							valueMap.put(districtDTO.getId(), districtDTO.getName());
						}
						window.getFilterClockOutPane().getDistrictCombo().setValueMap(valueMap);
					}
				} else {
					SC.warn("ERROR", "Unknow error");
				}

			}
		});
	}

//loads academic year combo in filter learner head count pane	
	private void loadFilterClockOutAcademicYearCombo(final FilterClockOutWindow window) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.GET_ACADEMIC_YEAR, null);
		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
		SC.showPrompt("", "", new SwizimaLoader());

		dispatcher.execute(new RequestAction(RequestConstant.GET_ACADEMIC_YEAR, map),
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

								for (AcademicYearDTO academicYearDTO : result.getAcademicYearDTOs()) {
									valueMap.put(academicYearDTO.getId(), academicYearDTO.getName());
								}
								window.getFilterClockOutPane().getAcademicYearCombo().setValueMap(valueMap);
							}
						} else {
							SC.warn("ERROR", "Unknow error");
						}

					}
				});
	}

//loads academic year combo in filter learner head count pane	
	private void loadFilterClockOutAcademicTermCombo(final FilterClockOutWindow window) {
		window.getFilterClockOutPane().getAcademicYearCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				String academicYearId = window.getFilterClockOutPane().getAcademicYearCombo().getValueAsString();
				map.put(RequestConstant.GET_ACADEMIC_TERMS_IN_ACADEMIC_YEAR, academicYearId);
				map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
				SC.showPrompt("", "", new SwizimaLoader());

				dispatcher.execute(new RequestAction(RequestConstant.GET_ACADEMIC_TERMS_IN_ACADEMIC_YEAR, map),
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
										window.getFilterClockOutPane().getAcademicTermCombo().setValueMap(valueMap);
									}
								} else {
									SC.warn("ERROR", "Unknow error");
								}

							}
						});
			}
		});

	}

/////////////////////////////////////////END OF FILTER CLOCKOUT COMBOS
	
	
	//filter
	private void filterClockInsByAcademicYearAcademicTermDistrictSchool(final FilterClockInWindow window) {
		window.getFilterButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				String academicYearId = window.getFilterClockInPane().getAcademicYearCombo().getValueAsString();
				String academicTermId = window.getFilterClockInPane().getAcademicTermCombo().getValueAsString();
				String districtId = window.getFilterClockInPane().getDistrictCombo().getValueAsString();
				String schoolId = window.getFilterClockInPane().getSchoolCombo().getValueAsString();
				String date = dateFormat.format(window.getFilterClockInPane().getClockinDateItem().getValueAsDate());
				
				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(FilterClockInWindow.ACADEMIC_YEAR_ID, academicYearId);
				map.put(FilterClockInWindow.ACADEMIC_TERM_ID, academicTermId);
				map.put(FilterClockInWindow.DISTRICT_ID, districtId);
				map.put(FilterClockInWindow.SCHOOL_ID, schoolId);
				map.put(FilterClockInWindow.CLOCKIN_DATE, date);
				
				map.put(RequestConstant.GET_CLOCKINS_IN_ACADEMIC_YEAR_ACADEMIC_TERM_DISTRICT_SCHOOL, map);
				map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
				SC.showPrompt("", "", new SwizimaLoader());

				dispatcher.execute(new RequestAction(RequestConstant.GET_CLOCKINS_IN_ACADEMIC_YEAR_ACADEMIC_TERM_DISTRICT_SCHOOL , map),
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
		                            SystemFeedbackDTO feedbackDTO = result.getSystemFeedbackDTO();
									if ( feedbackDTO != null) {
										window.close();
										if (result.getSystemFeedbackDTO().isResponse()) {
											// SC.say("SUCCESS", result.getSystemFeedbackDTO().getMessage());
											getView().getClockInPane().getClockInListGrid().addRecordsToGrid(result.getClockInDTOs());
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
		});
	}

	
	private void filterClockOutsByAcademicYearAcademicTermDistrictSchool(final FilterClockOutWindow window) {
		window.getFilterButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				String academicYearId = window.getFilterClockOutPane().getAcademicYearCombo().getValueAsString();
				String academicTermId = window.getFilterClockOutPane().getAcademicTermCombo().getValueAsString();
				String districtId = window.getFilterClockOutPane().getDistrictCombo().getValueAsString();
				String schoolId = window.getFilterClockOutPane().getSchoolCombo().getValueAsString();
				String date = dateFormat.format(window.getFilterClockOutPane().getClockinDateItem().getValueAsDate());
				
				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(FilterClockOutWindow.ACADEMIC_YEAR_ID, academicYearId);
				map.put(FilterClockOutWindow.ACADEMIC_TERM_ID, academicTermId);
				map.put(FilterClockOutWindow.DISTRICT_ID, districtId);
				map.put(FilterClockOutWindow.SCHOOL_ID, schoolId);
				map.put(FilterClockOutWindow.CLOCK_OUT_DATE, date);
				
				map.put(RequestConstant.GET_CLOCK_OUTS_IN_ACADEMIC_YEAR_ACADEMIC_TERM_DISTRICT_SCHOOL, map);
				map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
				SC.showPrompt("", "", new SwizimaLoader());

				dispatcher.execute(new RequestAction(RequestConstant.GET_CLOCK_OUTS_IN_ACADEMIC_YEAR_ACADEMIC_TERM_DISTRICT_SCHOOL , map),
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
		                            SystemFeedbackDTO feedbackDTO = result.getSystemFeedbackDTO();
									if ( feedbackDTO != null) {
										window.close();
										if (result.getSystemFeedbackDTO().isResponse()) {
											// SC.say("SUCCESS", result.getSystemFeedbackDTO().getMessage());
											getView().getClockOutPane().getClockOutListGrid().addRecordsToGrid(result.getClockOutDTOs());
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
		});
	}


}