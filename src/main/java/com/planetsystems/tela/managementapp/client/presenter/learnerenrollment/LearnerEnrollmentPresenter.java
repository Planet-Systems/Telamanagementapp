package com.planetsystems.tela.managementapp.client.presenter.learnerenrollment;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

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
import com.planetsystems.tela.dto.AcademicTermDTO;
import com.planetsystems.tela.dto.AcademicYearDTO;
import com.planetsystems.tela.dto.DistrictDTO;
import com.planetsystems.tela.dto.FilterDTO;
import com.planetsystems.tela.dto.LearnerEnrollmentDTO;
import com.planetsystems.tela.dto.SchoolClassDTO;
import com.planetsystems.tela.dto.SchoolDTO;
import com.planetsystems.tela.dto.response.SystemResponseDTO;
import com.planetsystems.tela.managementapp.client.gin.SessionManager;
import com.planetsystems.tela.managementapp.client.place.NameTokens;
import com.planetsystems.tela.managementapp.client.presenter.comboutils.ComboUtil;
import com.planetsystems.tela.managementapp.client.presenter.comboutils.ComboUtil2;
import com.planetsystems.tela.managementapp.client.presenter.main.MainPresenter;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkDataUtil;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkDataUtil2;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkResult;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkResult2;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.planetsystems.tela.managementapp.client.widget.MenuButton;
import com.planetsystems.tela.managementapp.shared.DatePattern;
import com.planetsystems.tela.managementapp.shared.MyRequestAction;
import com.planetsystems.tela.managementapp.shared.MyRequestResult;
import com.planetsystems.tela.managementapp.shared.RequestConstant;
import com.planetsystems.tela.managementapp.shared.RequestDelimeters;
import com.planetsystems.tela.managementapp.shared.RequestResult;
import com.planetsystems.tela.managementapp.shared.requestcommands.LearnerEnrollmentCommand;
import com.planetsystems.tela.managementapp.shared.requestcommands.RegionDistrictCommands;
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

public class LearnerEnrollmentPresenter
		extends Presenter<LearnerEnrollmentPresenter.MyView, LearnerEnrollmentPresenter.MyProxy> {
	interface MyView extends View {
		LearnerEnrollementPane getLearnerEnrollementPane();

		ControlsPane getControlsPane();
	}

	@SuppressWarnings("deprecation")
	@ContentSlot

	public static final Type<RevealContentHandler<?>> SLOT_LearnerEnrollment = new Type<RevealContentHandler<?>>();

	@Inject
	private DispatchAsync dispatcher;

	@Inject
	private PlaceManager placeManager;

	DateTimeFormat dateTimeFormat = DateTimeFormat
			.getFormat(DatePattern.DAY_MONTH_YEAR_HOUR_MINUTE_SECONDS.getPattern());
	DateTimeFormat dateFormat = DateTimeFormat.getFormat(DatePattern.DAY_MONTH_YEAR.getPattern());

	@NameToken(NameTokens.learnerEnrollment)
	@ProxyCodeSplit
	interface MyProxy extends ProxyPlace<LearnerEnrollmentPresenter> {
	}

	@Inject
	LearnerEnrollmentPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy, MainPresenter.SLOT_Main);

	}

	@Override
	protected void onBind() {
		super.onBind();
		setControlsPaneMenuButtons();
		getAllLearnerEnrollments2();
	}

	public void setControlsPaneMenuButtons() {
		MenuButton newButton = new MenuButton("New");
		MenuButton edit = new MenuButton("Edit");
		MenuButton delete = new MenuButton("Delete");
		MenuButton filter = new MenuButton("Filter");

		List<MenuButton> buttons = new ArrayList<>();
		buttons.add(newButton);
		buttons.add(edit);
		buttons.add(delete);
		buttons.add(filter);

		getView().getControlsPane().addMenuButtons(buttons);
		addLearnerEnrollment(newButton);
		selectFilterOption(filter);
		deleteLearnerEnrollment(delete);

	}


	private void selectFilterOption(final MenuButton filter) {
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
				getView().getLearnerEnrollementPane().getLearnerEnrollmentListGrid().setShowFilterEditor(true);
			}
		});

		advanced.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
//	   		SC.say("Advanced Search");
				FilterLearnerHeadCountWindow window = new FilterLearnerHeadCountWindow();
				loadFilterLearnerHeadCountAcademicYearCombo2(window);
				loadFilterLearnerHeadCountAcademicTermCombo2(window);
				loadFilterLearnerHeadCountDistrictCombo2(window);
				loadFilterLearnerHeadCountSchoolCombo2(window);
				filterLearnerEnrollmentByAcademicTermSchool(window);
				window.show();
			}
		});

	}

	///////////////////////////////////////

	private void addLearnerEnrollment(MenuButton newButton) {
		newButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				LearnerEnrollmentWindow window = new LearnerEnrollmentWindow();
				String defaultValue = null;
				setLearnerTotal(window);
				loadAcademicYearCombo2(window, defaultValue);
				loadAcademicTermCombo2(window, defaultValue);
				loadDistrictCombo2(window, defaultValue);
				loadSchoolCombo2(window, defaultValue);
				loadSchoolClassCombo2(window, defaultValue);

				window.show();

				saveLearnerEnrollment2(window);
			}

		});
	}

	private void loadAcademicYearCombo2(final LearnerEnrollmentWindow window, final String defaultValue) {
		ComboUtil2.loadAcademicYearCombo(window.getAcademicYearCombo(), dispatcher, placeManager, defaultValue);
	}

	private void loadAcademicTermCombo2(final LearnerEnrollmentWindow window, final String defaultValue) {
		window.getAcademicYearCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				ComboUtil2.loadAcademicTermComboByAcademicYear(window.getAcademicYearCombo(),
						window.getAcademicTermCombo(), dispatcher, placeManager, defaultValue);
			}
		});
	}

	
	private void loadDistrictCombo2(final LearnerEnrollmentWindow window, final String defaultValue) {
		ComboUtil2.loadDistrictCombo(window.getDistrictCombo(), dispatcher, placeManager, defaultValue);
	}

	private void loadSchoolCombo2(final LearnerEnrollmentWindow window, final String defaultValue) {
		window.getDistrictCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				ComboUtil2.loadSchoolComboByDistrict(window.getDistrictCombo(), window.getSchoolCombo(), dispatcher,
						placeManager, defaultValue);
			}
		});

	}
	


	
	private void loadSchoolClassCombo2(final LearnerEnrollmentWindow window, final String defaultValue) {
		window.getSchoolCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				ComboUtil2.loadSchoolClassesComboBySchool(window.getSchoolCombo(),  window.getSchoolClassCombo() , dispatcher, placeManager, defaultValue);

			}
		});
	}
	
	public void setLearnerTotal(final LearnerEnrollmentWindow window) {
		final int[] totalGirls = new int[1];
		final int[] totalBoys = new int[1];
		final int[] total = { 0 };
		window.getTotalBoysField().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {

				if (window.getTotalBoysField().getValueAsString() == null) {
					totalBoys[0] = 0;
				} else {
					totalBoys[0] = Integer.parseInt(window.getTotalBoysField().getValueAsString());
				}

				if (window.getTotalGirlsField().getValueAsString() == null) {
					totalGirls[0] = 0;
				}

				total[0] = totalGirls[0] + totalBoys[0];
				window.getLearnerTotalField().setValue(total[0]);
			}
		});

		window.getTotalGirlsField().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				if (window.getTotalGirlsField().getValueAsString() == null) {
					totalGirls[0] = 0;
				} else {
					totalGirls[0] = Integer.parseInt(window.getTotalGirlsField().getValueAsString());
				}

				if (window.getTotalBoysField().getValueAsString() == null) {
					totalBoys[0] = 0;
				}

				total[0] = totalGirls[0] + totalBoys[0];
				window.getLearnerTotalField().setValue(total[0]);
			}
		});

	}



	protected boolean checkIfNoLearnerEnrollmentWindowFieldIsEmpty(LearnerEnrollmentWindow window) {
		boolean flag = true;

		if (window.getTotalBoysField().getValueAsString() == null)
			flag = false;

		if (window.getTotalGirlsField().getValueAsString() == null)
			flag = false;

		if (window.getDistrictCombo().getValueAsString() == null)
			flag = false;

		if (window.getSchoolCombo().getValueAsString() == null)
			flag = false;

		if (window.getSchoolClassCombo().getValueAsString() == null)
			flag = false;

		return flag;
	}

	private void clearLearnerEnrollmentWindowFields(LearnerEnrollmentWindow window) {

		window.getDistrictCombo().clearValue();
		window.getSchoolCombo().clearValue();
		window.getSchoolClassCombo().clearValue();
		window.getTotalBoysField().clearValue();
		window.getTotalGirlsField().clearValue();
		window.getLearnerTotalField().clearValue();

	}

///////////////////////FILTER LEARNER HEADCOUNT COMBOS(4)

	private void loadFilterLearnerHeadCountSchoolCombo2(final FilterLearnerHeadCountWindow window) {
		window.getFilterLearnerHeadCountPane().getDistrictCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {

				ComboUtil2.loadSchoolComboByDistrict(window.getFilterLearnerHeadCountPane().getDistrictCombo(),
						window.getFilterLearnerHeadCountPane().getSchoolCombo(), dispatcher, placeManager, null);
			}
		});

	}

	
	private void loadFilterLearnerHeadCountDistrictCombo2(final FilterLearnerHeadCountWindow window) {
		ComboUtil2.loadDistrictCombo(window.getFilterLearnerHeadCountPane().getDistrictCombo(), dispatcher, placeManager,null);
	}

	
	private void loadFilterLearnerHeadCountAcademicYearCombo2(final FilterLearnerHeadCountWindow window) {
		ComboUtil2.loadAcademicYearCombo(window.getFilterLearnerHeadCountPane().getAcademicYearCombo(), dispatcher,
				placeManager, null);
	}


	
	private void loadFilterLearnerHeadCountAcademicTermCombo2(final FilterLearnerHeadCountWindow window) {
		window.getFilterLearnerHeadCountPane().getAcademicYearCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {

				ComboUtil2.loadAcademicTermComboByAcademicYear(
						window.getFilterLearnerHeadCountPane().getAcademicYearCombo(),
						window.getFilterLearnerHeadCountPane().getAcademicTermCombo(), dispatcher, placeManager, null);
			}
		});

	}
	

	///////////////////////////////////////// END OF FILTER LEARNERS COMBOS


	////////////////////////////////////////////////// NEW

	private void saveLearnerEnrollment2(final LearnerEnrollmentWindow window) {
		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				if (checkIfNoLearnerEnrollmentWindowFieldIsEmpty(window)) {

					LearnerEnrollmentDTO dto = new LearnerEnrollmentDTO();
					
					// dto.setId(id);
					dto.setTotalBoys(Long.valueOf(window.getTotalBoysField().getValueAsString()));
					dto.setTotalGirls(Long.valueOf(window.getTotalGirlsField().getValueAsString()));
					dto.setCreatedDateTime(dateTimeFormat.format(new Date()));
	                
					SchoolClassDTO classDTO = new SchoolClassDTO(window.getSchoolClassCombo().getValueAsString());
					
					AcademicTermDTO termDTO = new AcademicTermDTO(window.getAcademicTermCombo().getValueAsString());
					termDTO.setAcademicYearDTO(new AcademicYearDTO(window.getAcademicYearCombo().getValueAsString()));
					
					
					SchoolDTO schoolDTO = new SchoolDTO(window.getSchoolCombo().getValueAsString());
					schoolDTO.setDistrictDTO(new DistrictDTO(window.getDistrictCombo().getValueAsString()));
					
					classDTO.setAcademicTermDTO(termDTO);
					classDTO.setSchoolDTO(schoolDTO);
					
					dto.setSchoolClassDTO(classDTO);
					

					GWT.log("DTO " + dto);
					GWT.log("ID " + dto.getSchoolClassDTO().getId());

					LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					map.put(MyRequestAction.DATA, dto);
					map.put(MyRequestAction.COMMAND, LearnerEnrollmentCommand.SAVE_LEARNER_ENROLLMENT);
					
					
					NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

						@Override
						public void onNetworkResult(MyRequestResult result) {
							if (result != null) {
								SystemResponseDTO<LearnerEnrollmentDTO> responseDTO = result.getLearnerEnrollmentResponse();
								if (responseDTO.isStatus()) {
									//clearLearnerEnrollmentWindowFields(window);
									//window.close();
									SC.say("SUCCESS", responseDTO.getMessage());
									getAllLearnerEnrollments2();
								} else {
									SC.say(responseDTO.getMessage());
								}
							}

						}
					});
					
				} else {
					SC.warn("Please Fill all fields");
				}

			}
		});

	}

	private void getAllLearnerEnrollments2() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		if (SessionManager.getInstance().getLoggedInUserGroup().equalsIgnoreCase(SessionManager.ADMIN))
			map.put(MyRequestAction.COMMAND, LearnerEnrollmentCommand.GET_ALL_LEARNER_ENROLLMENTS);
		else
			map.put(MyRequestAction.COMMAND, LearnerEnrollmentCommand.GET_LEARNER_ENROLLMENTS_BY_SYSTEM_USER_PROFILE_SCHOOLS);

		NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

			@Override
			public void onNetworkResult(MyRequestResult result) {
				if (result != null) {
					SystemResponseDTO<List<LearnerEnrollmentDTO>> responseDTO = result.getLearnerEnrollmentResponseList();
					if (responseDTO.isStatus()) {
						if(responseDTO.getData() != null)
						getView().getLearnerEnrollementPane().getLearnerEnrollmentListGrid().addRecordsToGrid(responseDTO.getData());
					} else {
						SC.say(responseDTO.getMessage());
					}
				}
	
			}
		});
	}
	
	
	
	private void filterLearnerEnrollmentByAcademicTermSchool(final FilterLearnerHeadCountWindow window) {
		window.getFilterButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String academicTermId = window.getFilterLearnerHeadCountPane().getAcademicTermCombo()
						.getValueAsString();
				String schoolId = window.getFilterLearnerHeadCountPane().getSchoolCombo().getValueAsString();

				FilterDTO dto = new FilterDTO();
				dto.setAcademicTermDTO(new AcademicTermDTO(academicTermId));
				dto.setSchoolDTO(new SchoolDTO(schoolId));

				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(MyRequestAction.DATA , dto);
				map.put(MyRequestAction.COMMAND , LearnerEnrollmentCommand.GET_LEARNER_ENROLLMENTS_BY_ACADEMIC_TERM_SCHOOL);

				NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

					@Override
					public void onNetworkResult(MyRequestResult result) {
						if (result != null) {
							SystemResponseDTO<List<LearnerEnrollmentDTO>> responseDTO = result.getLearnerEnrollmentResponseList();
							if (responseDTO.isStatus()) {
								if(responseDTO.getData() != null)
								getView().getLearnerEnrollementPane().getLearnerEnrollmentListGrid().addRecordsToGrid(responseDTO.getData());
							} else {
								SC.say(responseDTO.getMessage());
							}
						}
					
					}
				});
			}
		});
	}
	
	
	
	
	private void deleteLearnerEnrollment(MenuButton delete) {
		delete.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (getView().getLearnerEnrollementPane().getLearnerEnrollmentListGrid().anySelected()) {
					SC.ask("Confirm", "Are you sure you want to delete the selected record", new BooleanCallback() {

						@Override
						public void execute(Boolean value) {
							if (value) {
								ListGridRecord record = getView().getLearnerEnrollementPane().getLearnerEnrollmentListGrid().getSelectedRecord();
								LinkedHashMap<String, Object> map = new LinkedHashMap<>();
								map.put(RequestDelimeters.LEARNER_ENROLLMENT_ID, record.getAttributeAsString("id"));
								map.put(MyRequestAction.COMMAND, LearnerEnrollmentCommand.DELETE_LEARNER_ENROLLMENT);

								NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

									@Override
									public void onNetworkResult(MyRequestResult result) {
										
										if (result != null) {
											SystemResponseDTO<String> responseDTO = result.getResponseText();
											if (responseDTO.isStatus()) {
												SC.say("SUCCESS", responseDTO.getMessage());
												getAllLearnerEnrollments2();
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

}