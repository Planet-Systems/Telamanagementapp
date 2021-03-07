package com.planetsystems.tela.managementapp.client.presenter.learnerattendance;

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
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.planetsystems.tela.managementapp.client.presenter.main.MainPresenter;
import com.planetsystems.tela.managementapp.client.presenter.staffattendance.FilterClockInWindow;
import com.planetsystems.tela.managementapp.client.presenter.staffattendance.FilterClockOutWindow;
import com.planetsystems.tela.managementapp.client.presenter.staffenrollment.StaffEnrollmentWindow;
import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.planetsystems.tela.managementapp.client.widget.MenuButton;
import com.planetsystems.tela.managementapp.client.widget.SwizimaLoader;
import com.planetsystems.tela.managementapp.shared.DatePattern;
import com.planetsystems.tela.managementapp.shared.RequestAction;
import com.planetsystems.tela.managementapp.shared.RequestConstant;
import com.planetsystems.tela.managementapp.shared.RequestResult;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.HoverEvent;
import com.smartgwt.client.widgets.events.HoverHandler;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.planetsystems.tela.dto.AcademicTermDTO;
import com.planetsystems.tela.dto.AcademicYearDTO;
import com.planetsystems.tela.dto.DistrictDTO;
import com.planetsystems.tela.dto.LearnerAttendanceDTO;
import com.planetsystems.tela.dto.SchoolClassDTO;
import com.planetsystems.tela.dto.SchoolDTO;
import com.planetsystems.tela.dto.SchoolStaffDTO;
import com.planetsystems.tela.dto.SystemFeedbackDTO;
import com.planetsystems.tela.managementapp.client.gin.SessionManager;
import com.planetsystems.tela.managementapp.client.place.NameTokens;

public class LearnerAttendancePresenter
		extends Presenter<LearnerAttendancePresenter.MyView, LearnerAttendancePresenter.MyProxy> {
	interface MyView extends View {
		public ControlsPane getControlsPane();

		public LearnerAttendancePane getAttendancePane();
	}

	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_LearnerAttendance = new Type<RevealContentHandler<?>>();

	@Inject
	private PlaceManager placeManager;

	@Inject
	private DispatchAsync dispatcher;
	
	DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat(DatePattern.DAY_MONTH_YEAR_HOUR_MINUTE_SECONDS.getPattern());
	DateTimeFormat dateFormat = DateTimeFormat.getFormat(DatePattern.DAY_MONTH_YEAR.getPattern());

	@NameToken(NameTokens.learnerAttendance)
	@ProxyCodeSplit
	interface MyProxy extends ProxyPlace<LearnerAttendancePresenter> {
	}

	@Inject
	LearnerAttendancePresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy, MainPresenter.SLOT_Main);

	}

	@Override
	protected void onBind() {
		super.onBind();
		setControlsPaneMenuButtons();
		getAllLearnerAttendance();
	}

	public void setControlsPaneMenuButtons() {
		MenuButton newButton = new MenuButton("New");
		MenuButton edit = new MenuButton("Edit");
		MenuButton delete = new MenuButton("Delete");
		MenuButton filter = new MenuButton("Filter");
		filter.setCanHover(true);

		List<MenuButton> buttons = new ArrayList<>();
		buttons.add(newButton);
		buttons.add(edit);
		buttons.add(delete);
		buttons.add(filter);

		getView().getControlsPane().addMenuButtons(buttons);
		addLearnerAttendance(newButton);
		selectFilterOption(filter);

	}
	
	
	
	private void selectFilterOption(final MenuButton filter) {
	       final Menu menu = new Menu();
	       MenuItem basic = new MenuItem("Base Filter");
	       MenuItem advanced = new MenuItem("Advanced Filter");
	       
	       menu.setItems(basic , advanced);
	      
	       filter.addHoverHandler(new HoverHandler() {
			@Override
			public void onHover(HoverEvent event) {
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
	   		FilterLearnerAttendanceWindow window = new FilterLearnerAttendanceWindow();
	   		loadFilterLearnerAttendanceAcademicYearCombo(window);
	   		loadFilterLearnerAttendanceAcademicTermCombo(window);
	   		loadFilterLearnerAttendanceDistrictCombo(window);
	   		loadFilterLearnerAttendanceSchoolCombo(window);
	   		window.show();
	   		filterLearnerAttendanceByAcademicYearAcademicTermDistrictSchool(window);
	   		disableEnableFilterButton(window);
	   		}		
	   	});
	       
		}

	
	private void disableEnableFilterButton(final FilterLearnerAttendanceWindow window) {;
	window.getFilterLearnerAttendancePane().getAcademicTermCombo().addChangedHandler(new ChangedHandler() {

		@Override
		public void onChanged(ChangedEvent event) {

			if (window.getFilterLearnerAttendancePane().getAcademicTermCombo().getValueAsString() != null && window.getFilterLearnerAttendancePane().getSchoolCombo().getValueAsString() != null) {
                window.getFilterButton().setDisabled(false);
			}else {
				window.getFilterButton().setDisabled(true);	
			}
		}
	});

	window.getFilterLearnerAttendancePane().getSchoolCombo().addChangedHandler(new ChangedHandler() {

		@Override
		public void onChanged(ChangedEvent event) {
			if (	window.getFilterLearnerAttendancePane().getAcademicTermCombo().getValueAsString() != null && window.getFilterLearnerAttendancePane().getSchoolCombo().getValueAsString() != null) {
				window.getFilterButton().setDisabled(false);
			}else {
				window.getFilterButton().setDisabled(true);
			}
		}
	});

}
	
	
	
	

//////////////////////LEARNER Attendance

	private void addLearnerAttendance(MenuButton newButton) {
		newButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				LearnerAttendanceWindow window = new LearnerAttendanceWindow();
				setTotalAbsentPresent(window);
				loadAcademicTermCombo(window, null);
				loadSchoolClassCombo(window, null);
				loadSchoolStaffCombo(window, null);
				window.show();

				saveLearnerAttendance(window);
			}

		});

	}

	private void saveLearnerAttendance(final LearnerAttendanceWindow window) {
		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				if(checkIfNoLearnerAttendanceWindowFieldIsEmpty(window)) {
					LearnerAttendanceDTO dto = new LearnerAttendanceDTO();

					dto.setBoysAbsent(Long.parseLong(window.getBoysAbsentField().getValueAsString()));
					dto.setBoysPresent(Long.parseLong(window.getBoysPresentField().getValueAsString()));
					dto.setGirlsAbsent(Long.parseLong(window.getGirlsAbsentField().getValueAsString()));
					dto.setGirlsPresent(Long.parseLong(window.getGirlsPresentField().getValueAsString()));
					dto.setComment(window.getCommentField().getValueAsString());
					dto.setCreatedDateTime(dateTimeFormat.format(new Date()));
					dto.setAttendanceDate(dateFormat.format(new Date()));

					AcademicTermDTO academicTermDTO = new AcademicTermDTO(
							window.getAcademicTermComboBox().getValueAsString());
					dto.setAcademicTermDTO(academicTermDTO);

					SchoolClassDTO schoolClassDTO = new SchoolClassDTO(window.getSchoolClassComboBox().getValueAsString());
					dto.setSchoolClassDTO(schoolClassDTO);

					SchoolStaffDTO schoolStaffDTO = new SchoolStaffDTO(window.getSchoolStaffComboBox().getValueAsString());
					dto.setSchoolStaffDTO(schoolStaffDTO);

					LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					map.put(RequestConstant.SAVE_LEARNER_ATTENDANCE, dto);
					map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
					SC.showPrompt("", "", new SwizimaLoader());

					dispatcher.execute(new RequestAction(RequestConstant.SAVE_LEARNER_ATTENDANCE, map),
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
												clearAcademicYearWindowFields(window);
												window.close();
												// SC.say("SUCCESS", result.getSystemFeedbackDTO().getMessage());
												getView().getAttendancePane().getLearnerAttendanceListGrid()
														.addRecordsToGrid(result.getLearnerAttendanceDTOs());
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
				}else {
					SC.warn("Please fill all fields");
				}

			}

		});
	}

	private void clearAcademicYearWindowFields(LearnerAttendanceWindow window) {

		window.getAcademicTermComboBox().clearValue();
		window.getSchoolClassComboBox().clearValue();
		window.getSchoolStaffComboBox().clearValue();
		window.getCommentField().clearValue();
		window.getGirlsAbsentField().clearValue();
		window.getBoysAbsentField().clearValue();
		window.getGirlsPresentField().clearValue();
		window.getBoysPresentField().clearValue();
		window.getTotalAbsentField().clearValue();
		window.getTotalPresentField().clearValue();
		
	}
	
	private boolean checkIfNoLearnerAttendanceWindowFieldIsEmpty(LearnerAttendanceWindow window) {
	    boolean flag = true;

	    if(window.getAcademicTermComboBox().getValueAsString() == null) flag = false;
	    
	    if(window.getSchoolClassComboBox().getValueAsString() == null) flag = false;
	    
	    if(window.getSchoolStaffComboBox().getValueAsString() == null) flag = false;
	    
//	    if(window.getCommentField().getValueAsString() == null) flag = false;
	    
	    if(window.getGirlsAbsentField().getValueAsString() == null) flag = false;
	    
	    if(window.getBoysAbsentField().getValueAsString() == null) flag = false;
	    
	    if(window.getBoysPresentField().getValueAsString() == null) flag = false;
	    
	    if(window.getGirlsPresentField().getValueAsString() == null) flag = false;
	    
	    
		return flag;
	}
	
	private void loadAcademicTermCombo(final LearnerAttendanceWindow window, final String defaultValue) {
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

	private void loadSchoolStaffCombo(final LearnerAttendanceWindow window, final String defaultValue) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.GET_SCHOOL_STAFF, null);
		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
		SC.showPrompt("", "", new SwizimaLoader());

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

	private void loadSchoolClassCombo(final LearnerAttendanceWindow window, final String defaultValue) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.GET_SCHOOL_CLASS, null);
		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
		SC.showPrompt("", "", new SwizimaLoader());

		dispatcher.execute(new RequestAction(RequestConstant.GET_SCHOOL_CLASS, map),
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

								for (SchoolClassDTO schoolClassDTO : result.getSchoolClassDTOs()) {
									valueMap.put(schoolClassDTO.getId(), schoolClassDTO.getName());
								}
								window.getSchoolClassComboBox().setValueMap(valueMap);

								if (defaultValue != null) {
									window.getSchoolClassComboBox().setValue(defaultValue);
								}

							}
						} else {
							SC.warn("ERROR", "Unknow error");
						}

					}
				});
	}

	private void getAllLearnerAttendance() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.GET_LEARNER_ATTENDANCE, null);
		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
		SC.showPrompt("", "", new SwizimaLoader());

		dispatcher.execute(new RequestAction(RequestConstant.GET_LEARNER_ATTENDANCE, map),
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
									// SC.say("SUCCESS", result.getSystemFeedbackDTO().getMessage());
									getView().getAttendancePane().getLearnerAttendanceListGrid()
											.addRecordsToGrid(result.getLearnerAttendanceDTOs());
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

	public void setTotalAbsentPresent(final LearnerAttendanceWindow window) {
		final int[] totalGirlsPresent = new int[1];
		final int[] totalBoysPresent = new int[1];
		final int[] totalGirlsAbsent = new int[1];
		final int[] totalBoysAbsent = new int[1];
		final int[] totalPresent = { 0 };
		final int[] totalAbsent = { 0 };

		window.getGirlsAbsentField().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				if (window.getGirlsAbsentField().getValueAsString() == null) {
					totalGirlsAbsent[0] = 0;
				} else {
					totalGirlsAbsent[0] = Integer.parseInt(window.getGirlsAbsentField().getValueAsString());
				}

				if (window.getBoysAbsentField().getValueAsString() == null) {
					totalBoysAbsent[0] = 0;
				}

				totalAbsent[0] = totalGirlsAbsent[0] + totalBoysAbsent[0];
				window.getTotalAbsentField().setValue(totalAbsent[0]);
			}
		});

		window.getBoysAbsentField().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {

				if (window.getBoysAbsentField().getValueAsString() == null) {
					totalBoysAbsent[0] = 0;
				} else {
					totalBoysAbsent[0] = Integer.parseInt(window.getBoysAbsentField().getValueAsString());
				}

				if (window.getGirlsAbsentField().getValueAsString() == null) {
					totalGirlsAbsent[0] = 0;
				}

				totalAbsent[0] = totalBoysAbsent[0] + totalGirlsAbsent[0];
				window.getTotalAbsentField().setValue(totalAbsent[0]);
			}
		});

		///////////////////////////////////////// present
		window.getGirlsPresentField().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				if (window.getGirlsPresentField().getValueAsString() == null) {
					totalGirlsPresent[0] = 0;
				} else {
					totalGirlsPresent[0] = Integer.parseInt(window.getGirlsPresentField().getValueAsString());
				}

				if (window.getBoysPresentField().getValueAsString() == null) {
					totalBoysPresent[0] = 0;
				}

				totalPresent[0] = totalGirlsPresent[0] + totalBoysPresent[0];
				window.getTotalPresentField().setValue(totalPresent[0]);
			}
		});

		window.getBoysPresentField().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				if (window.getBoysPresentField().getValueAsString() == null) {
					totalBoysPresent[0] = 0;
				} else {
					totalBoysPresent[0] = Integer.parseInt(window.getBoysPresentField().getValueAsString());
				}

				if (window.getGirlsPresentField().getValueAsString() == null) {
					totalGirlsPresent[0] = 0;
				}

				totalPresent[0] = totalGirlsPresent[0] + totalBoysPresent[0];
				window.getTotalPresentField().setValue(totalPresent[0]);
			}
		});

	}
	
	////////////////////////LOAD FILTER ATTENDANCE COMBOS
	
	//loads school combo in filter learner head count pane
		private void loadFilterLearnerAttendanceSchoolCombo(final FilterLearnerAttendanceWindow window) {
			window.getFilterLearnerAttendancePane().getDistrictCombo().addChangedHandler(new ChangedHandler() {

				@Override
				public void onChanged(ChangedEvent event) {
					LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					String districtId = window.getFilterLearnerAttendancePane().getDistrictCombo().getValueAsString();
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
											window.getFilterLearnerAttendancePane().getSchoolCombo().setValueMap(valueMap);
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
		private void loadFilterLearnerAttendanceDistrictCombo(final FilterLearnerAttendanceWindow window) {
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
							window.getFilterLearnerAttendancePane().getDistrictCombo().setValueMap(valueMap);
						}
					} else {
						SC.warn("ERROR", "Unknow error");
					}

				}
			});
		}

	//loads academic year combo in filter learner head count pane	
		private void loadFilterLearnerAttendanceAcademicYearCombo(final FilterLearnerAttendanceWindow window) {
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
									window.getFilterLearnerAttendancePane().getAcademicYearCombo().setValueMap(valueMap);
								}
							} else {
								SC.warn("ERROR", "Unknow error");
							}

						}
					});
		}

	//loads academic year combo in filter learner head count pane	
		private void loadFilterLearnerAttendanceAcademicTermCombo(final FilterLearnerAttendanceWindow window) {
			window.getFilterLearnerAttendancePane().getAcademicYearCombo().addChangedHandler(new ChangedHandler() {

				@Override
				public void onChanged(ChangedEvent event) {
					LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					String academicYearId = window.getFilterLearnerAttendancePane().getAcademicYearCombo().getValueAsString();
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
											window.getFilterLearnerAttendancePane().getAcademicTermCombo().setValueMap(valueMap);
										}
									} else {
										SC.warn("ERROR", "Unknow error");
									}

								}
							});
				}
			});

		}
	
	//////////////////////END FILTER ATTENDANCE COMBOS
	
	//filter
		private void filterLearnerAttendanceByAcademicYearAcademicTermDistrictSchool(final FilterLearnerAttendanceWindow window) {
			window.getFilterButton().addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					String academicYearId = window.getFilterLearnerAttendancePane().getAcademicYearCombo().getValueAsString();
					String academicTermId = window.getFilterLearnerAttendancePane().getAcademicTermCombo().getValueAsString();
					String districtId = window.getFilterLearnerAttendancePane().getDistrictCombo().getValueAsString();
					String schoolId = window.getFilterLearnerAttendancePane().getSchoolCombo().getValueAsString();
					String date = dateFormat.format(window.getFilterLearnerAttendancePane().getAttendanceDateItem().getValueAsDate());
					
					LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					map.put(FilterLearnerAttendanceWindow.ACADEMIC_YEAR_ID, academicYearId);
					map.put(FilterLearnerAttendanceWindow.ACADEMIC_TERM_ID, academicTermId);
					map.put(FilterLearnerAttendanceWindow.DISTRICT_ID, districtId);
					map.put(FilterLearnerAttendanceWindow.SCHOOL_ID, schoolId);
					map.put(FilterLearnerAttendanceWindow.ATTENDANCE_DATE, date);
					
					map.put(RequestConstant.GET_LEARNER_ATTENDANCE_IN_ACADEMIC_YEAR_ACADEMIC_TERM_DISTRICT_SCHOOL, map);
					map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
					SC.showPrompt("", "", new SwizimaLoader());

					dispatcher.execute(new RequestAction(RequestConstant.GET_LEARNER_ATTENDANCE_IN_ACADEMIC_YEAR_ACADEMIC_TERM_DISTRICT_SCHOOL , map),
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
												getView().getAttendancePane().getLearnerAttendanceListGrid().addRecordsToGrid(result.getLearnerAttendanceDTOs());
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