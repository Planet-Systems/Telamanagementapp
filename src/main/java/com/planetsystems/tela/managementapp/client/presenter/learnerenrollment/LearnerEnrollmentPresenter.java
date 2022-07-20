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
import com.planetsystems.tela.dto.LearnerDetailDTO;
import com.planetsystems.tela.dto.LearnerEnrollmentDTO;
import com.planetsystems.tela.dto.LearnerGuardianDTO;
import com.planetsystems.tela.dto.LearnerRegistrationDTO;
import com.planetsystems.tela.dto.SchoolClassDTO;
import com.planetsystems.tela.dto.SchoolDTO;
import com.planetsystems.tela.managementapp.client.gin.SessionManager;
import com.planetsystems.tela.managementapp.client.place.NameTokens;
import com.planetsystems.tela.managementapp.client.presenter.comboutils.ComboUtil;
import com.planetsystems.tela.managementapp.client.presenter.main.MainPresenter;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkDataUtil;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkResult;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.planetsystems.tela.managementapp.client.widget.MenuButton;
import com.planetsystems.tela.managementapp.shared.DatePattern;
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

public class LearnerEnrollmentPresenter
		extends Presenter<LearnerEnrollmentPresenter.MyView, LearnerEnrollmentPresenter.MyProxy> {

	interface MyView extends View {

		public LearnerEnrollementPane getLearnerEnrollementPane();

		public ControlsPane getControlsPane();

		public LeanerDetailsPane getLeanerDetailsPane();

		public TabSet getTabSet();
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

	// DateTimeFormat dateFormat =
	// DateTimeFormat.getFormat(DatePattern.DAY_MONTH_YEAR.getPattern());

	DateTimeFormat dateFormat = DateTimeFormat.getFormat(DatePattern.YEAR_MONTH_DAY.getPattern());

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
		onTabSelected();
	}

	private void onTabSelected() {
		getView().getTabSet().addTabSelectedHandler(new TabSelectedHandler() {

			@Override
			public void onTabSelected(TabSelectedEvent event) {

				String selectedTab = event.getTab().getTitle();

				if (selectedTab.equalsIgnoreCase("Learner Details")) {

					MenuButton newButton = new MenuButton("New");
					MenuButton edit = new MenuButton("Edit");
					MenuButton delete = new MenuButton("Delete");
					MenuButton filter = new MenuButton("Filter");
					MenuButton details = new MenuButton("Details");

					List<MenuButton> buttons = new ArrayList<>();
					buttons.add(newButton);
					buttons.add(edit);
					buttons.add(delete);
					buttons.add(details);
					buttons.add(filter);

					registerLearner(newButton);
					filterLearnerOption(filter);
					learnerDetailsFilter();
					viewLearnerDetails(details);

					getView().getControlsPane().addMenuButtons("Learner Details", buttons);

				} else if (selectedTab.equalsIgnoreCase("Learner Head Count")) {

					MenuButton newButton = new MenuButton("New");
					MenuButton edit = new MenuButton("Edit");
					MenuButton delete = new MenuButton("Delete");
					MenuButton filter = new MenuButton("Filter");

					List<MenuButton> buttons = new ArrayList<>();
					buttons.add(newButton);
					// buttons.add(edit);
					// buttons.add(delete);
					buttons.add(filter);

					learnerEnrolmentFilter();
					addLearnerEnrollment(newButton);
					selectFilterOption(filter);

					getView().getControlsPane().addMenuButtons("Learner Head Count", buttons);

				} else {
					List<MenuButton> buttons = new ArrayList<>();
					getView().getControlsPane().addMenuButtons(buttons);
				}

			}

		});
	}

	public void setControlsPaneMenuButtons() {

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
				// SC.say("Advanced Search");
				learnerEnrolmentFilter();
			}
		});

	}

	private void learnerEnrolmentFilter() {
		FilterLearnerHeadCountWindow window = new FilterLearnerHeadCountWindow();
		loadFilterLearnerHeadCountAcademicYearCombo(window);
		loadFilterLearnerHeadCountAcademicTermCombo(window);
		loadFilterLearnerHeadCountDistrictCombo(window);
		loadFilterLearnerHeadCountSchoolCombo(window);
		filterLearnerEnrollmentByAcademicYearAcademicTermDistrictSchool(window);
		window.show();
	}

	private void learnerDetailsFilter() {
		FilterLearnerDetailsWindow window = new FilterLearnerDetailsWindow();
		loadFilterLearnerDetailsDistrictCombo(window);
		oadFilterLearnerDetailsSchoolCombo(window);
		filterLearnerDetailsBySchool(window);
		window.show();
	}

	///////////////////////////////////////

	private void addLearnerEnrollment(MenuButton newButton) {
		newButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				LearnerEnrollmentWindow window = new LearnerEnrollmentWindow();
				String defaultValue = null;
				setLearnerTotal(window);
				loadAcademicYearCombo(window, defaultValue);
				loadAcademicTermCombo(window, defaultValue);
				loadDistrictCombo(window, defaultValue);
				loadSchoolCombo(window, defaultValue);
				loadSchoolClassCombo(window, defaultValue);

				window.show();

				saveLearnerEnrollment(window);
			}

		});
	}

	private void loadAcademicYearCombo(final LearnerEnrollmentWindow window, final String defaultValue) {
		ComboUtil.loadAcademicYearCombo(window.getAcademicYearCombo(), dispatcher, placeManager, defaultValue);
	}

	private void loadAcademicTermCombo(final LearnerEnrollmentWindow window, final String defaultValue) {
		window.getAcademicYearCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				ComboUtil.loadAcademicTermComboByAcademicYear(window.getAcademicYearCombo(),
						window.getAcademicTermCombo(), dispatcher, placeManager, defaultValue);
			}
		});
	}

	private void loadDistrictCombo(final LearnerEnrollmentWindow window, final String defaultValue) {
		ComboUtil.loadDistrictCombo(window.getDistrictCombo(), dispatcher, placeManager, defaultValue);
	}

	private void loadSchoolCombo(final LearnerEnrollmentWindow window, final String defaultValue) {
		window.getDistrictCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				ComboUtil.loadSchoolComboByDistrict(window.getDistrictCombo(), window.getSchoolCombo(), dispatcher,
						placeManager, defaultValue);
			}
		});

	}

	private void loadSchoolClassCombo(final LearnerEnrollmentWindow window, final String defaultValue) {
		window.getSchoolCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				ComboUtil.loadSchoolClassesComboBySchoolAcademicTerm(window.getAcademicTermCombo(),
						window.getSchoolCombo(), window.getSchoolClassCombo(), dispatcher, placeManager, defaultValue);
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

	private void saveLearnerEnrollment(final LearnerEnrollmentWindow window) {
		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				if (checkIfNoLearnerEnrollmentWindowFieldIsEmpty(window)) {

				} else {
					SC.warn("Please Fill all fields");
				}

				LearnerEnrollmentDTO dto = new LearnerEnrollmentDTO();
				// dto.setId(id);
				dto.setTotalBoys(Long.valueOf(window.getTotalBoysField().getValueAsString()));
				dto.setTotalGirls(Long.valueOf(window.getTotalGirlsField().getValueAsString()));
				dto.setCreatedDateTime(dateTimeFormat.format(new Date()));

				SchoolClassDTO schoolClassDTO = new SchoolClassDTO(window.getSchoolClassCombo().getValueAsString());
				dto.setSchoolClassDTO(schoolClassDTO);

				GWT.log("DTO " + dto);
				GWT.log("ID " + dto.getSchoolClassDTO().getId());

				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(RequestConstant.SAVE_LEARNER_ENROLLMENT, dto);
				map.put(NetworkDataUtil.ACTION, RequestConstant.SAVE_LEARNER_ENROLLMENT);

				NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

					@Override
					public void onNetworkResult(RequestResult result) {
						clearLearnerEnrollmentWindowFields(window);
						window.close();
						SC.say("SUCCESS", result.getSystemFeedbackDTO().getMessage());
						getAllLearnerEnrollments();
					}
				});
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

	private void getAllLearnerEnrollments() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		if (SessionManager.getInstance().getLoggedInUserGroup().equalsIgnoreCase(SessionManager.ADMIN))
			map.put(NetworkDataUtil.ACTION, RequestConstant.GET_LEARNER_ENROLLMENT);
		else
			map.put(NetworkDataUtil.ACTION, RequestConstant.GET_LEARNER_ENROLLMENTS_BY_SYSTEM_USER_PROFILE_SCHOOLS);

		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {
				getView().getLearnerEnrollementPane().getLearnerEnrollmentListGrid()
						.addRecordsToGrid(result.getLearnerEnrollmentDTOs());
			}
		});
	}

	/////////////////////// FILTER LEARNER HEADCOUNT COMBOS(4)

	// loads school combo in filter learner head count pane
	private void loadFilterLearnerHeadCountSchoolCombo(final FilterLearnerHeadCountWindow window) {
		window.getFilterLearnerHeadCountPane().getDistrictCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {

				ComboUtil.loadSchoolComboByDistrict(window.getFilterLearnerHeadCountPane().getDistrictCombo(),
						window.getFilterLearnerHeadCountPane().getSchoolCombo(), dispatcher, placeManager, null);
			}
		});

	}

	// loads district combo in filter learner head count pane
	private void loadFilterLearnerHeadCountDistrictCombo(final FilterLearnerHeadCountWindow window) {
		ComboUtil.loadDistrictCombo(window.getFilterLearnerHeadCountPane().getDistrictCombo(), dispatcher, placeManager,
				null);
	}

	// loads academic year combo in filter learner head count pane
	private void loadFilterLearnerHeadCountAcademicYearCombo(final FilterLearnerHeadCountWindow window) {
		ComboUtil.loadAcademicYearCombo(window.getFilterLearnerHeadCountPane().getAcademicYearCombo(), dispatcher,
				placeManager, null);
	}

	// loads academic year combo in filter learner head count pane
	private void loadFilterLearnerHeadCountAcademicTermCombo(final FilterLearnerHeadCountWindow window) {
		window.getFilterLearnerHeadCountPane().getAcademicYearCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {

				ComboUtil.loadAcademicTermComboByAcademicYear(
						window.getFilterLearnerHeadCountPane().getAcademicYearCombo(),
						window.getFilterLearnerHeadCountPane().getAcademicTermCombo(), dispatcher, placeManager, null);
			}
		});

	}

	// loads district combo in filter learner head count pane
	private void loadFilterLearnerDetailsDistrictCombo(final FilterLearnerDetailsWindow window) {
		ComboUtil.loadDistrictCombo(window.getDistrictCombo(), dispatcher, placeManager, null);
	}

	private void oadFilterLearnerDetailsSchoolCombo(final FilterLearnerDetailsWindow window) {
		window.getDistrictCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {

				ComboUtil.loadSchoolComboByDistrict(window.getDistrictCombo(), window.getSchoolCombo(), dispatcher,
						placeManager, null);
			}
		});

	}

	///////////////////////////////////////// END OF FILTER LEARNERS COMBOS

	private void filterLearnerEnrollmentByAcademicYearAcademicTermDistrictSchool(
			final FilterLearnerHeadCountWindow window) {
		window.getFilterButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String academicYearId = window.getFilterLearnerHeadCountPane().getAcademicYearCombo()
						.getValueAsString();
				String academicTermId = window.getFilterLearnerHeadCountPane().getAcademicTermCombo()
						.getValueAsString();
				String districtId = window.getFilterLearnerHeadCountPane().getDistrictCombo().getValueAsString();
				String schoolId = window.getFilterLearnerHeadCountPane().getSchoolCombo().getValueAsString();

				FilterDTO dto = new FilterDTO();
				dto.setAcademicYearDTO(new AcademicYearDTO(academicYearId));
				dto.setAcademicTermDTO(new AcademicTermDTO(academicTermId));
				dto.setDistrictDTO(new DistrictDTO(districtId));
				dto.setSchoolDTO(new SchoolDTO(schoolId));

				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(RequestDelimeters.FILTER_LEARNER_ENROLLEMENTS, dto);
				map.put(NetworkDataUtil.ACTION,
						RequestConstant.FILTER_LEARNER_ENROLLMENTS_BY_ACADEMIC_YEAR_ACADEMIC_TERM_DISTRICT_SCHOOL);

				NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

					@Override
					public void onNetworkResult(RequestResult result) {
						getView().getLearnerEnrollementPane().getLearnerEnrollmentListGrid()
								.addRecordsToGrid(result.getLearnerEnrollmentDTOs());
					}
				});
			}
		});
	}

	private void filterLearnerDetailsBySchool(final FilterLearnerDetailsWindow window) {
		window.getFilterButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				String districtId = window.getDistrictCombo().getValueAsString();
				String schoolId = window.getSchoolCombo().getValueAsString();

				FilterDTO dto = new FilterDTO();
				dto.setDistrictDTO(new DistrictDTO(districtId));
				dto.setSchoolDTO(new SchoolDTO(schoolId));

				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(RequestDelimeters.FILTER_LEARNER_DETAILS, dto);
				map.put(NetworkDataUtil.ACTION, RequestConstant.FILTER_LEARNER_DETAILS);

				NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

					@Override
					public void onNetworkResult(RequestResult result) {

						getView().getLeanerDetailsPane().getLeanerDetailsListgrid()
								.addRecordsToGrid(result.getLearnerDetailDTOs());
					}
				});
			}
		});
	}

	// learner registration

	private void registerLearner(final MenuButton button) {
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				final LearnerRegistrationWindow window = new LearnerRegistrationWindow();

				ComboUtil.loadDistrictCombo(window.getHomeDistrict(), dispatcher, placeManager, null);
				ComboUtil.loadDistrictCombo(window.getDistrictCombo(), dispatcher, placeManager, null);
				ComboUtil.loadAcademicYearCombo(window.getAcademicYearCombo(), dispatcher, placeManager, null);

				window.getDistrictCombo().addChangedHandler(new ChangedHandler() {

					@Override
					public void onChanged(ChangedEvent event) {

						ComboUtil.loadSchoolComboByDistrict(window.getDistrictCombo(), window.getSchoolCombo(),
								dispatcher, placeManager, null);

					}
				});

				window.getAcademicYearCombo().addChangedHandler(new ChangedHandler() {

					@Override
					public void onChanged(ChangedEvent event) {

						ComboUtil.loadAcademicTermComboByAcademicYear(window.getAcademicYearCombo(),
								window.getAcademicTermCombo(), dispatcher, placeManager, null);

					}
				});

				window.getSchoolCombo().addChangedHandler(new ChangedHandler() {

					@Override
					public void onChanged(ChangedEvent event) {

						ComboUtil.loadSchoolClassesComboBySchoolAcademicTerm(window.getAcademicTermCombo(),
								window.getSchoolCombo(), window.getSchoolClassCombo(), dispatcher, placeManager, null);

					}
				});

				ComboUtil.loadSpecialNeedsCombo(window.getSpecialNeedsCombo());
				ComboUtil.loadOrphanStatusCombo(window.getOrphanStatusCombo());
				ComboUtil.loadGendaCombo(window.getGenderCombo());

				addParent(window);

				registerLearner(window);

				window.show();

			}
		});

	}

	private void addParent(final LearnerRegistrationWindow registrationWindow) {
		registrationWindow.getAddButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				LearnerGuardianWindow window = new LearnerGuardianWindow();
				onSave(registrationWindow, window);
				ComboUtil.loadGuardianRelationshipCombo(window.getRelationshipCombo());
				window.show();

			}
		});
	}

	private void onSave(final LearnerRegistrationWindow registrationWindow, final LearnerGuardianWindow window) {
		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				LearnerGuardianDTO dto = new LearnerGuardianDTO();

				dto.setFirstName(window.getFirstNameField().getValueAsString());
				dto.setGuardianRelationship(window.getRelationshipCombo().getValueAsString());
				dto.setLastName(window.getLastNameField().getValueAsString());
				dto.setNationalId(window.getNationalIdField().getValueAsString());
				dto.setNationality(window.getNationality().getValueAsString());
				dto.setPhoneNumber(window.getPhoneNumberField().getValueAsString());
				registrationWindow.getListgrid().addRecordToGrid(dto);

			}
		});
	}

	private void registerLearner(final LearnerRegistrationWindow registrationWindow) {
		registrationWindow.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				LearnerRegistrationDTO dto = new LearnerRegistrationDTO();

				LearnerDetailDTO learnerDetailDTO = new LearnerDetailDTO();

				learnerDetailDTO.setDob(dateFormat.format(registrationWindow.getDobItem().getValueAsDate()));

				learnerDetailDTO.setFirstName(registrationWindow.getFirstNameField().getValueAsString());
				learnerDetailDTO.setGender(registrationWindow.getGenderCombo().getValueAsString());
				if (registrationWindow.getSpecialNeedsCombo().getValueAsString() != null) {
					if (registrationWindow.getSpecialNeedsCombo().getValueAsString().equalsIgnoreCase("Yes")) {
						learnerDetailDTO.setHasSpecialNeeds(true);
					} else {
						learnerDetailDTO.setHasSpecialNeeds(false);
					}
				}

				DistrictDTO homeDistrict = new DistrictDTO();
				homeDistrict.setId(registrationWindow.getHomeDistrict().getValueAsString());
				learnerDetailDTO.setHomeDistrict(homeDistrict);

				learnerDetailDTO.setLastName(registrationWindow.getLastNameField().getValueAsString());
				learnerDetailDTO.setNationalId(registrationWindow.getNationalIdField().getValueAsString());
				learnerDetailDTO.setNationality(registrationWindow.getNationality().getValueAsString());
				learnerDetailDTO.setOrphanCategory(registrationWindow.getOrphanStatusCombo().getValueAsString());
				learnerDetailDTO.setOtherName(registrationWindow.getOtherNameField().getValueAsString());
				learnerDetailDTO.setPhoneNumber(registrationWindow.getPhoneNumberField().getValueAsString());
				// learnerDetailDTO.setStatus(registrationWindow.getStatus().getValueAsString());
				// learnerDetailDTO.setLearnTelaNo(learnTelaNo);
				dto.setLearnerDetailDTO(learnerDetailDTO);

				dto.setAcademicTermId(registrationWindow.getAcademicTermCombo().getValueAsString());
				dto.setSchoolClassId(registrationWindow.getSchoolClassCombo().getValueAsString());
				dto.setSchoolId(registrationWindow.getSchoolCombo().getValueAsString());

				List<LearnerGuardianDTO> guardians = new ArrayList<>();

				for (ListGridRecord record : registrationWindow.getListgrid().getRecords()) {

					LearnerGuardianDTO guardianDTO = new LearnerGuardianDTO();

					guardianDTO.setFirstName(record.getAttribute(LearnerGuardianListgrid.FIRSTNAME));
					guardianDTO.setGuardianRelationship(record.getAttribute(LearnerGuardianListgrid.RelationShip));
					guardianDTO.setLastName(record.getAttribute(LearnerGuardianListgrid.LASTNAME));
					guardianDTO.setNationalId(record.getAttribute(LearnerGuardianListgrid.NATIONAL_ID));
					guardianDTO.setNationality(record.getAttribute(LearnerGuardianListgrid.NATIONALITY));
					guardianDTO.setPhoneNumber(record.getAttribute(LearnerGuardianListgrid.PHONE_NUMBER));

					guardians.add(guardianDTO);
				}

				dto.setGuardians(guardians);

				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(RequestConstant.SAVE_LEARNER_DETAILS, dto);
				map.put(NetworkDataUtil.ACTION, RequestConstant.SAVE_LEARNER_DETAILS);

				NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

					@Override
					public void onNetworkResult(RequestResult result) {
						if (result.getSystemFeedbackDTO() != null) {
							if (result.getSystemFeedbackDTO().isResponse()) {
								SC.say("SUCCESS", result.getSystemFeedbackDTO().getMessage(), new BooleanCallback() {

									@Override
									public void execute(Boolean value) {

										if (value) {
											// kasoma
										}

									}
								});
							}
						}

					}
				});

			}
		});
	}

	private void filterLearnerOption(final MenuButton filter) {

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
				getView().getLeanerDetailsPane().getLeanerDetailsListgrid().setShowFilterEditor(true);
			}
		});

		advanced.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {

				learnerDetailsFilter();
			}
		});

	}

	private void viewLearnerDetails(final MenuButton button) {
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				if (getView().getLeanerDetailsPane().getLeanerDetailsListgrid().anySelected()) {

					ListGridRecord record = getView().getLeanerDetailsPane().getLeanerDetailsListgrid()
							.getSelectedRecord();

					String learnerId = record.getAttribute(LeanerDetailsListgrid.ID);

					LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					map.put(RequestConstant.GET_LEARNER_DETAILS, learnerId);
					map.put(NetworkDataUtil.ACTION, RequestConstant.GET_LEARNER_DETAILS);

					NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

						@Override
						public void onNetworkResult(final RequestResult result) {

							final LearnerRegistrationDTO dto = result.getLearnerRegistrationDTO();

							final LearnerRegistrationDetailWindow window = new LearnerRegistrationDetailWindow();

							if (dto.getLearnerDetailDTO() != null
									&& dto.getLearnerDetailDTO().getHomeDistrict() != null) {
								
								ComboUtil.loadDistrictCombo(window.getHomeDistrict(), dispatcher, placeManager,dto.getLearnerDetailDTO().getHomeDistrict().getId());
							
							}
							
							 

							ComboUtil.loadDistrictCombo(window.getDistrictCombo(), dispatcher, placeManager, dto.getSchoolDistrictId()); 
							ComboUtil.loadAcademicYearCombo(window.getAcademicYearCombo(), dispatcher, placeManager,dto.getAcademicYearId());
							

							window.getDistrictCombo().addChangedHandler(new ChangedHandler() {

								@Override
								public void onChanged(ChangedEvent event) {

									ComboUtil.loadSchoolComboByDistrict(window.getDistrictCombo(),
											window.getSchoolCombo(), dispatcher, placeManager, dto.getSchoolId());

								}
							});

							window.getAcademicYearCombo().addChangedHandler(new ChangedHandler() {

								@Override
								public void onChanged(ChangedEvent event) {

									ComboUtil.loadAcademicTermComboByAcademicYear(window.getAcademicYearCombo(),
											window.getAcademicTermCombo(), dispatcher, placeManager, dto.getAcademicTermId());

								}
							});

							window.getSchoolCombo().addChangedHandler(new ChangedHandler() {

								@Override
								public void onChanged(ChangedEvent event) {

									ComboUtil.loadSchoolClassesComboBySchoolAcademicTerm(dto.getAcademicTermId(),
											dto.getSchoolId(), window.getSchoolClassCombo(), dispatcher,
											placeManager, dto.getSchoolClassId());
									 

								}
							});
							

							ComboUtil.loadSpecialNeedsCombo(window.getSpecialNeedsCombo());
							ComboUtil.loadOrphanStatusCombo(window.getOrphanStatusCombo());
							ComboUtil.loadGendaCombo(window.getGenderCombo());

							
							if(dto.getLearnerDetailDTO().getDob()!=null) {
								window.getDobItem().setValue(dateFormat.parse(dto.getLearnerDetailDTO().getDob())); 
							}
							
							window.getFirstNameField().setValue(dto.getLearnerDetailDTO().getFirstName()); 
							window.getGenderCombo().setValue(dto.getLearnerDetailDTO().getGender());
							window.getSpecialNeedsCombo().setValue(dto.getLearnerDetailDTO().isHasSpecialNeeds()); 
							
							if(dto.getLearnerDetailDTO().getHomeDistrict()!=null) {
								window.getHomeDistrict().setValue(dto.getLearnerDetailDTO().getHomeDistrict().getId()); 
							}
							 
							
							window.getLastNameField().setValue(dto.getLearnerDetailDTO().getLastName());
							window.getNationalIdField().setValue(dto.getLearnerDetailDTO().getNationalId());
							window.getNationality().setValue(dto.getLearnerDetailDTO().getNationality());
							window.getOrphanStatusCombo().setValue(dto.getLearnerDetailDTO().getOrphanCategory());
							window.getOtherNameField().setValue(dto.getLearnerDetailDTO().getOtherName());
							window.getPhoneNumberField().setValue(dto.getLearnerDetailDTO().getPhoneNumber());
							
							// learnerDetailDTO.setStatus(registrationWindow.getStatus().getValueAsString());
							// learnerDetailDTO.setLearnTelaNo(learnTelaNo);
							 
							window.getListgrid().addRecordsToGrid(dto.getGuardians()); 
							
							window.show();

						}
					});

				} else {

					SC.say("ERROR", "Please select record to view details");
				}

			}
		});

	}

}