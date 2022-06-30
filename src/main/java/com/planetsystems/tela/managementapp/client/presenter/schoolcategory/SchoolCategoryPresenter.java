package com.planetsystems.tela.managementapp.client.presenter.schoolcategory;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.http.client.URL;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
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
import com.planetsystems.tela.dto.AcademicYearDTO;
import com.planetsystems.tela.dto.DistrictDTO;
import com.planetsystems.tela.dto.FilterDTO;
import com.planetsystems.tela.dto.GeneralUserDetailDTO;
import com.planetsystems.tela.dto.RegionDto;
import com.planetsystems.tela.dto.SchoolCategoryDTO;
import com.planetsystems.tela.dto.SchoolClassDTO;
import com.planetsystems.tela.dto.SchoolDTO;
import com.planetsystems.tela.dto.SchoolStaffDTO;
import com.planetsystems.tela.dto.enums.SchoolGenderCategory;
import com.planetsystems.tela.dto.enums.SchoolLevel;
import com.planetsystems.tela.dto.enums.SchoolOwnership;
import com.planetsystems.tela.dto.enums.SchoolType;
import com.planetsystems.tela.managementapp.client.event.HighlightActiveLinkEvent;
import com.planetsystems.tela.managementapp.client.gin.SessionManager;
import com.planetsystems.tela.managementapp.client.place.NameTokens;
import com.planetsystems.tela.managementapp.client.presenter.comboutils.ComboUtil;
import com.planetsystems.tela.managementapp.client.presenter.main.MainPresenter;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkDataUtil;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkResult;
import com.planetsystems.tela.managementapp.client.presenter.schoolcategory.school.AddSchoolWindow;
import com.planetsystems.tela.managementapp.client.presenter.schoolcategory.school.FilterSchoolWindow;
import com.planetsystems.tela.managementapp.client.presenter.schoolcategory.school.InitialSchoolClassListGrid;
import com.planetsystems.tela.managementapp.client.presenter.schoolcategory.school.InitialSchoolClassWindow;
import com.planetsystems.tela.managementapp.client.presenter.schoolcategory.school.InitialSchoolStaffWindow;
import com.planetsystems.tela.managementapp.client.presenter.schoolcategory.school.SchoolImportWindow;
import com.planetsystems.tela.managementapp.client.presenter.schoolcategory.school.SchoolListGrid;
import com.planetsystems.tela.managementapp.client.presenter.schoolcategory.school.SchoolPane;
import com.planetsystems.tela.managementapp.client.presenter.schoolcategory.schoolcategory.SchCategoryPane;
import com.planetsystems.tela.managementapp.client.presenter.schoolcategory.schoolcategory.SchoolCategoryListGrid;
import com.planetsystems.tela.managementapp.client.presenter.schoolcategory.schoolcategory.SchoolCategoryWindow;
import com.planetsystems.tela.managementapp.client.presenter.schoolcategory.schoolclass.FilterSchoolClassWindow;
import com.planetsystems.tela.managementapp.client.presenter.schoolcategory.schoolclass.SchoolClassListGrid;
import com.planetsystems.tela.managementapp.client.presenter.schoolcategory.schoolclass.SchoolClassPane;
import com.planetsystems.tela.managementapp.client.presenter.schoolcategory.schoolclass.SchoolClassWindow;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.planetsystems.tela.managementapp.client.widget.MenuButton;
import com.planetsystems.tela.managementapp.client.widget.SwizimaLoader;
import com.planetsystems.tela.managementapp.shared.DatePattern;
import com.planetsystems.tela.managementapp.shared.RequestAction;
import com.planetsystems.tela.managementapp.shared.RequestConstant;
import com.planetsystems.tela.managementapp.shared.RequestDelimeters;
import com.planetsystems.tela.managementapp.shared.RequestResult;
import com.planetsystems.tela.managementapp.shared.UtilityManager;
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

public class SchoolCategoryPresenter
		extends Presenter<SchoolCategoryPresenter.MyView, SchoolCategoryPresenter.MyProxy> {
	interface MyView extends View {
		ControlsPane getControlsPane();

		TabSet getTabSet();

		SchCategoryPane getSchoolCategoryPane();

		SchoolPane getSchoolPane();

		SchoolClassPane getSchoolClassPane();
	}

	@SuppressWarnings("deprecation")
	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_SchoolCategory = new Type<RevealContentHandler<?>>();

	@Inject
	private DispatchAsync dispatcher;

	DateTimeFormat dateTimeFormat = DateTimeFormat
			.getFormat(DatePattern.DAY_MONTH_YEAR_HOUR_MINUTE_SECONDS.getPattern());

	DateTimeFormat dateFormat = DateTimeFormat.getFormat(DatePattern.DAY_MONTH_YEAR.getPattern());

	@Inject
	PlaceManager placeManager;

	@NameToken(NameTokens.schoolClassCategory)
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

				// SC.say("selectedTab:: "+selectedTab);

				if (selectedTab.equalsIgnoreCase(SchoolCategoryView.SCHOOL_CATEGORY_TAB_TITLE)) {
					MenuButton newButton = new MenuButton("New");
					MenuButton edit = new MenuButton("Edit");
					MenuButton delete = new MenuButton("Delete");

					List<MenuButton> buttons = new ArrayList<>();
					buttons.add(newButton);
					buttons.add(edit);
					buttons.add(delete);
					// buttons.add(fiter);

					addSchoolCategory(newButton);
					deleteSchoolCategory(delete);
					editSchoolCategory(edit);

					getAllSchoolCategories();
					getView().getControlsPane().addMenuButtons("School Registration & Setup", buttons);

				} else if (selectedTab.equalsIgnoreCase(SchoolCategoryView.SCHOOL_TAB_TITLE)) {
					MenuButton newButton = new MenuButton("New");
					MenuButton edit = new MenuButton("Edit");
					MenuButton delete = new MenuButton("Delete");
					MenuButton filter = new MenuButton("Filter");
					MenuButton importbutton = new MenuButton("Import");
					MenuButton initiateClasses = new MenuButton("Setup Defualt Classes");

					List<MenuButton> buttons = new ArrayList<>();
					buttons.add(newButton);
					buttons.add(edit);
					buttons.add(delete);
					buttons.add(importbutton);
					buttons.add(initiateClasses);
					buttons.add(filter);

					addSchool(newButton);
					deleteSchool(delete);
					editSchool(edit);
					getAllSchools();
					selectFilterSchoolOption(filter);
					setupSchoolInitialClasses(initiateClasses);

					importSchools(importbutton);

					getView().getControlsPane().addMenuButtons("School Registration & Setup", buttons);

				} else if (selectedTab.equalsIgnoreCase(SchoolCategoryView.SCHOOL_CLASSES_TAB_TITLE)) {

					MenuButton newButton = new MenuButton("New");
					MenuButton edit = new MenuButton("Edit");
					MenuButton delete = new MenuButton("Delete");
					MenuButton filter = new MenuButton("Filter");

					List<MenuButton> buttons = new ArrayList<>();
					buttons.add(newButton);
					buttons.add(edit);
					buttons.add(delete);
					buttons.add(filter);

					addSchoolClass(newButton);
					deleteSchoolClass(delete);
					editSchoolClass(edit);
					selectFilterSchoolClassOption(filter);

					getView().getControlsPane().addMenuButtons("School Registration & Setup", buttons);

					getAllSchoolClasses();

				} else {
					List<MenuButton> buttons = new ArrayList<>();
					getView().getControlsPane().addMenuButtons(buttons);
				}

			}

		});
	}

	//////////// menu filter buttons

	private void selectFilterSchoolOption(final MenuButton filter) {
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
				getView().getSchoolPane().getListGrid().setShowFilterEditor(true);
			}
		});

		advanced.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				// SC.say("Advanced Search");
				FilterSchoolWindow window = new FilterSchoolWindow();
				loadFilterRegionCombo(window);
				loadFilterDistrictCombo(window);
				loadFilterSchoolCategoryCombo(window);

				window.show();
				filterSchoolsBYDistrictSchoolCategory(window);
			}
		});

	}

	private void selectFilterSchoolClassOption(final MenuButton filter) {
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
				getView().getSchoolClassPane().getListGrid().setShowFilterEditor(true);
			}
		});

		advanced.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				// SC.say("Advanced Search");
				FilterSchoolClassWindow window = new FilterSchoolClassWindow();
				loadFilterAcademicYearCombo(window);
				loadFilterDistrictCombo(window);
				loadFilterAcademicTermCombo(window);
				loadFilterSchoolCombo(window);
				window.show();
				filterSchoolClassesByAcademicTermSchool(window);
			}
		});

	}

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

				if (checkIfNoSchoolCategoryWindowFieldIsEmpty(window)) {
					SchoolCategoryDTO dto = new SchoolCategoryDTO();
					dto.setCode(window.getCategoryCode().getValueAsString());
					dto.setName(window.getCategoryName().getValueAsString());
					dto.setCreatedDateTime(dateTimeFormat.format(new Date()));

					LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					map.put(RequestConstant.SAVE_SCHOOL_CATEGORY, dto);
					map.put(NetworkDataUtil.ACTION, RequestConstant.SAVE_SCHOOL_CATEGORY);
					NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

						@Override
						public void onNetworkResult(RequestResult result) {
							clearSchoolCategoryWindowFields(window);
							SC.say("SUCCESS", result.getSystemFeedbackDTO().getMessage());
							getAllSchoolCategories();
						}
					});

				} else {
					SC.warn("Please fill all the fields");
				}

			}

		});

	}

	private boolean checkIfNoSchoolCategoryWindowFieldIsEmpty(SchoolCategoryWindow window) {
		boolean flag = true;

		if (window.getCategoryCode().getValueAsString() == null)
			flag = false;

		if (window.getCategoryName().getValueAsString() == null)
			flag = false;

		return flag;
	}

	private void editSchoolCategory(MenuButton button) {
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (getView().getSchoolCategoryPane().getListGrid().anySelected()) {
					SchoolCategoryWindow window = new SchoolCategoryWindow();
					window.getSaveButton().setTitle("Save");
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
		ListGridRecord record = getView().getSchoolCategoryPane().getListGrid().getSelectedRecord();
		window.getCategoryCode().setValue(record.getAttribute(SchoolCategoryListGrid.CODE));
		window.getCategoryName().setValue(record.getAttribute(SchoolCategoryListGrid.NAME));
	}

	private void updateSchCategory(final SchoolCategoryWindow window) {
		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				ListGridRecord record = getView().getSchoolCategoryPane().getListGrid().getSelectedRecord();

				SchoolCategoryDTO dto = new SchoolCategoryDTO();
				dto.setId(record.getAttribute(SchoolCategoryListGrid.ID));
				dto.setCode(window.getCategoryCode().getValueAsString());
				dto.setName(window.getCategoryName().getValueAsString());
				dto.setUpdatedDateTime(dateTimeFormat.format(new Date()));

				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(RequestConstant.UPDATE_SCHOOL_CATEGORY, dto);
				map.put(NetworkDataUtil.ACTION, RequestConstant.UPDATE_SCHOOL_CATEGORY);
				NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

					@Override
					public void onNetworkResult(RequestResult result) {
						clearSchoolCategoryWindowFields(window);
						window.close();
						SC.say("SUCCESS", result.getSystemFeedbackDTO().getMessage());
						getAllSchoolCategories();
					}
				});
			}
		});

	}

	private void deleteSchoolCategory(MenuButton button) {
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (getView().getSchoolCategoryPane().getListGrid().anySelected()) {
					SC.ask("Confirm", "Are you sure you want to delete the selected record", new BooleanCallback() {

						@Override
						public void execute(Boolean value) {
							if (value) {
								ListGridRecord record = getView().getSchoolCategoryPane().getListGrid()
										.getSelectedRecord();

								LinkedHashMap<String, Object> map = new LinkedHashMap<>();
								map.put(RequestDelimeters.SCHOOL_CATEGORY_ID, record.getAttributeAsString("id"));
								map.put(NetworkDataUtil.ACTION, RequestConstant.DELETE_SCHOOL_CATEGORY);

								NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

									@Override
									public void onNetworkResult(RequestResult result) {
										SC.say("SUCCESS", result.getSystemFeedbackDTO().getMessage());
										getAllSchoolCategories();
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

		map.put(NetworkDataUtil.ACTION, RequestConstant.GET_SCHOOL_CATEGORY);

		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {
				getView().getSchoolCategoryPane().getListGrid().addRecordsToGrid(result.getSchoolCategoryDTOs());
			}
		});
	}

	////////////////////////////////////////////////////////// SCHOOL////////////////////////////////////////////////////////////////////

	private void addSchool(MenuButton menuButton) {
		menuButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				AddSchoolWindow window = new AddSchoolWindow();

				loadRegionCombo(window, null);
				loadSchoolCategoryCombo(window, null);
				loadDistrictComboByRegion(window, null);

				loadSchoolGenderCategory(window);
				loadSchoolOwnership(window);
				loadSchoolType(window);
				loadSchoolLevels(window);
				loadSchoolLicenseStatus(window);

				saveSchool(window);

				window.show();

			}

		});
	}

	// add school combos

	private void loadRegionCombo(AddSchoolWindow window, String defaultValue) {
		ComboUtil.loadRegionCombo(window.getRegionCombo(), dispatcher, placeManager, defaultValue);
	}

	private void loadDistrictComboByRegion(final AddSchoolWindow window, final String defaultValue) {
		window.getRegionCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				ComboUtil.loadDistrictComboByRegion(window.getRegionCombo(), window.getDistrictCombo(), dispatcher,
						placeManager, defaultValue);
			}
		});
	}

	private void loadSchoolCategoryCombo(AddSchoolWindow window, String defaultValue) {
		ComboUtil.loadSchoolCategoryCombo(window.getSchoolCategoryCombo(), dispatcher, placeManager, defaultValue);
	}

	private void saveSchool(final AddSchoolWindow window) {
		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				if (checkIfNoSchoolWindowFieldIsEmpty(window)) {

					SchoolDTO dto = new SchoolDTO();
					dto.setCode(window.getSchoolCode().getValueAsString());
					dto.setLatitude(window.getLatitude().getValueAsString());
					dto.setLongitude(window.getLongtitude().getValueAsString());
					dto.setDeviceNumber(window.getDeviceNumber().getValueAsString());
					dto.setName(window.getSchoolName().getValueAsString());
					dto.setCreatedDateTime(dateTimeFormat.format(new Date()));

					dto.setSchoolLevel(window.getSchoolLevel().getValueAsString());
					dto.setSchoolType(window.getSchoolType().getValueAsString());
					dto.setSchoolGenderCategory(window.getSchoolGenderCategory().getValueAsString());
					dto.setSchoolOwnership(window.getSchoolOwnership().getValueAsString());
					dto.setLicensed(window.getLicensed().getValueAsString());

					SchoolCategoryDTO schoolCategoryDTO = new SchoolCategoryDTO();
					schoolCategoryDTO.setId(window.getSchoolCategoryCombo().getValueAsString());
					dto.setSchoolCategoryDTO(schoolCategoryDTO);

					DistrictDTO districtDTO = new DistrictDTO();
					districtDTO.setId(window.getDistrictCombo().getValueAsString());
					dto.setDistrictDTO(districtDTO);

					LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					map.put(RequestConstant.SAVE_SCHOOL, dto);
					map.put(NetworkDataUtil.ACTION, RequestConstant.SAVE_SCHOOL);

					NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

						@Override
						public void onNetworkResult(final RequestResult result) {

							if (result.getSystemFeedbackDTO().isResponse()) {

								SC.say(result.getSystemFeedbackDTO().getMessage(), new BooleanCallback() {

									@Override
									public void execute(Boolean value) {
										window.close();

										GWT.log("School ID: " + result.getSystemFeedbackDTO().getId());

										requesttForHTDetails(result.getSystemFeedbackDTO().getId(),
												SchoolLevel.getSchoolLevel(window.getSchoolLevel().getValueAsString()));

										getAllSchools();

									}
								});

							}

						}
					});

				} else {
					SC.warn("Please fill all the fields");
				}

			}

			private boolean checkIfNoSchoolWindowFieldIsEmpty(AddSchoolWindow window) {
				boolean flag = true;

				if (window.getSchoolName().getValueAsString() == null)
					flag = false;

				if (window.getSchoolCategoryCombo().getValueAsString() == null)
					flag = false;

				if (window.getDistrictCombo().getValueAsString() == null)
					flag = false;

				if (window.getRegionCombo().getValueAsString() == null)
					flag = false;

				if (window.getSchoolCode().getValueAsString() == null)
					flag = false;

				// if (window.getLatitude().getValueAsString() == null)
				// flag = false;
				//
				// if (window.getLongtitude().getValueAsString() == null)
				// flag = false;

				// if (window.getDeviceNumber().getValueAsString() == null)
				// flag = false;

				return flag;
			}
		});
	}

	private void editSchool(MenuButton button) {
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (getView().getSchoolPane().getListGrid().anySelected()) {
					AddSchoolWindow window = new AddSchoolWindow();
					window.getSaveButton().setTitle("Save");

					loadRegionCombo(window, null);
					loadSchoolCategoryCombo(window, null);
					loadDistrictComboByRegion(window, null);
					loadFieldsToEdit(window);

					loadSchoolGenderCategory(window);
					loadSchoolOwnership(window);
					loadSchoolType(window);
					loadSchoolLevels(window);
					loadSchoolLicenseStatus(window);

					updateSchool(window);

					window.show();

				} else {
					SC.say("Please select record to update");
				}
			}

		});
	}

	private void loadFieldsToEdit(AddSchoolWindow window) {
		ListGridRecord record = getView().getSchoolPane().getListGrid().getSelectedRecord();

		window.getSchoolCategoryCombo().setValue(record.getAttribute(SchoolListGrid.CATEGORY));
		window.getSchoolCode().setValue(record.getAttribute(SchoolListGrid.CODE));
		window.getDistrictCombo().setValue(record.getAttribute(SchoolListGrid.DISTRICT_ID));
		window.getSchoolName().setValue(record.getAttribute(SchoolListGrid.NAME));
		window.getLatitude().setValue(record.getAttribute(SchoolListGrid.LATITUDE));
		window.getLongtitude().setValue(record.getAttribute(SchoolListGrid.LONGITUDE));
		window.getDeviceNumber().setValue(record.getAttribute(SchoolListGrid.DEVICE_NUMBER));

		loadDistrictComboByRegion(window, record.getAttribute(SchoolListGrid.DISTRICT_ID));
		loadSchoolCategoryCombo(window, record.getAttribute(SchoolListGrid.CATEGORY_ID));

		window.getSchoolGenderCategory().setValue(record.getAttribute(SchoolListGrid.SchoolGenderCategory));
		window.getSchoolLevel().setValue(record.getAttribute(SchoolListGrid.SchoolLevel));
		window.getSchoolOwnership().setValue(record.getAttribute(SchoolListGrid.SchoolOwnership));
		window.getSchoolType().setValue(record.getAttribute(SchoolListGrid.SchoolType));
		window.getLicensed().setValue(record.getAttribute(SchoolListGrid.Licensed));

	}

	private void updateSchool(final AddSchoolWindow window) {
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

				dto.setSchoolLevel(window.getSchoolLevel().getValueAsString());
				dto.setSchoolType(window.getSchoolType().getValueAsString());
				dto.setSchoolGenderCategory(window.getSchoolGenderCategory().getValueAsString());
				dto.setSchoolOwnership(window.getSchoolOwnership().getValueAsString());
				dto.setLicensed(window.getLicensed().getValueAsString());

				SchoolCategoryDTO schoolCategoryDTO = new SchoolCategoryDTO();
				schoolCategoryDTO.setId(window.getSchoolCategoryCombo().getValueAsString());
				dto.setSchoolCategoryDTO(schoolCategoryDTO);

				DistrictDTO districtDTO = new DistrictDTO();
				districtDTO.setId(window.getDistrictCombo().getValueAsString());
				dto.setDistrictDTO(districtDTO);

				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(RequestConstant.UPDATE_SCHOOL, dto);
				map.put(NetworkDataUtil.ACTION, RequestConstant.UPDATE_SCHOOL);

				NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

					@Override
					public void onNetworkResult(RequestResult result) {
						clearSchoolWindowFields(window);
						window.close();
						getAllSchools();
					}
				});
			}
		});
	}

	private void setupSchoolInitialClasses(MenuButton button) {
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (getView().getSchoolPane().getListGrid().anySelected()) {

					String schoolId = getView().getSchoolPane().getListGrid().getSelectedRecord().getAttribute(SchoolListGrid.ID);

					SchoolLevel level = SchoolLevel.getSchoolLevel(
							getView().getSchoolPane().getListGrid().getSelectedRecord().getAttribute(SchoolListGrid.SchoolLevel));

					initiateSchoolClasses(schoolId, level);

				} else {
					SC.say("Please select record to setup default classes");
				}
			}

		});
	}

	////////////////////// Filter School combos

	private void loadFilterRegionCombo(final FilterSchoolWindow window) {

		ComboUtil.loadRegionCombo(window.getFilterRegionDistrictSchoolCategory().getRegionCombo(), dispatcher,
				placeManager, null);
	}

	private void loadFilterDistrictCombo(final FilterSchoolWindow window) {
		window.getFilterRegionDistrictSchoolCategory().getRegionCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				ComboUtil.loadDistrictComboByRegion(window.getFilterRegionDistrictSchoolCategory().getRegionCombo(),
						window.getFilterRegionDistrictSchoolCategory().getDistrictCombo(), dispatcher, placeManager,
						null);
			}
		});

	}

	private void loadFilterSchoolCategoryCombo(final FilterSchoolWindow window) {
		ComboUtil.loadSchoolCategoryCombo(window.getFilterRegionDistrictSchoolCategory().getSchoolCategoryCombo(),
				dispatcher, placeManager, null);
	}

	////////////////// END OF FILTER SCHOOL COMBOS

	private void clearSchoolWindowFields(AddSchoolWindow window) {

		window.getSchoolCode().clearValue();
		window.getSchoolName().clearValue();
		window.getLatitude().clearValue();
		window.getLongtitude().clearValue();
		window.getDeviceNumber().clearValue();
		window.getSchoolCategoryCombo().clearValue();
		// window.getRegionCombo().clearValue();
		// window.getDistrictCombo().clearValue();
	}

	private void deleteSchool(final MenuButton button) {
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (getView().getSchoolPane().getListGrid().anySelected()) {
					SC.ask("Confirm", "Are you sure you want to delete the selected record", new BooleanCallback() {

						@Override
						public void execute(Boolean value) {
							if (value) {

								ListGridRecord record = getView().getSchoolPane().getListGrid().getSelectedRecord();
								LinkedHashMap<String, Object> map = new LinkedHashMap<>();
								map.put(RequestDelimeters.SCHOOL_ID, record.getAttributeAsString("id"));
								map.put(NetworkDataUtil.ACTION, RequestConstant.DELETE_SCHOOL);

								NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

									@Override
									public void onNetworkResult(RequestResult result) {
										SC.say("SUCCESS", result.getSystemFeedbackDTO().getMessage());
										getAllSchools();
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

		map.put(NetworkDataUtil.ACTION, RequestConstant.GET_SCHOOLS);

		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {
				getView().getSchoolPane().getListGrid().addRecordsToGrid(result.getSchoolDTOs());
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

				loadAcademicYearCombo(window, null);
				loadAcademicTermCombo(window, null);
				loadDistrictCombo(window, null);
				loadSchoolCombo(window, null);
				loadSchoolClass(window, null);

				saveSchoolClass(window);

				window.show();
			}
		});
	}

	private void saveSchoolClass(final SchoolClassWindow window) {
		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				if (checkIfNoSchoolClassWindowFieldIsEmpty(window)) {

					SchoolClassDTO dto = new SchoolClassDTO();

					dto.setCode(window.getCodeField().getValueAsString());
					dto.setName(window.getClassNameField().getValueAsString());
					dto.setCreatedDateTime(dateTimeFormat.format(new Date()));

					dto.setHasStreams(window.getHasStreams().getValueAsBoolean());
					dto.setClassLevel(window.getClassLevel().getValueAsBoolean());

					if (!window.getClassLevel().getValueAsBoolean()) {

						SchoolClassDTO parentSchoolClass = new SchoolClassDTO();
						parentSchoolClass.setId(window.getParentSchoolClass().getValueAsString());

						dto.setParentSchoolClass(parentSchoolClass);
					}

					SchoolDTO schoolDTO = new SchoolDTO();
					schoolDTO.setId(window.getSchoolCombo().getValueAsString());
					dto.setSchoolDTO(schoolDTO);

					AcademicTermDTO academicTermDTO = new AcademicTermDTO();
					academicTermDTO.setId(window.getAcademicTermCombo().getValueAsString());
					dto.setAcademicTermDTO(academicTermDTO);

					// GWT.log("getClassLevel:: "+window.getClassLevel().getValueAsBoolean());

					LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					map.put(RequestConstant.SAVE_SCHOOL_CLASS, dto);
					map.put(NetworkDataUtil.ACTION, RequestConstant.SAVE_SCHOOL_CLASS);
					NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

						@Override
						public void onNetworkResult(RequestResult result) {

							SC.say("SUCCESS", result.getSystemFeedbackDTO().getMessage(), new BooleanCallback() {

								@Override
								public void execute(Boolean value) {

									if (value) {

										filterSchoolClassesByAcademicTermSchool(
												window.getAcademicYearCombo().getValueAsString(),
												window.getAcademicTermCombo().getValueAsString(),
												window.getDistrictCombo().getValueAsString(),
												window.getSchoolCombo().getValueAsString());

										clearSchoolClassWindowFields(window);

									}

								}
							});

						}
					});

				} else {
					SC.warn("Please fill all fields");
				}

			}

		});

	}

	private boolean checkIfNoSchoolClassWindowFieldIsEmpty(SchoolClassWindow window) {
		boolean flag = true;

		if (window.getCodeField().getValueAsString() == null)
			flag = false;

		if (window.getClassNameField().getValueAsString() == null)
			flag = false;

		if (window.getSchoolCombo().getValueAsString() == null)
			flag = false;

		if (window.getDistrictCombo().getValueAsString() == null)
			flag = false;

		if (window.getAcademicTermCombo().getValueAsString() == null)
			flag = false;

		if (window.getAcademicYearCombo().getValueAsString() == null)
			flag = false;

		return flag;
	}

	private void editSchoolClass(MenuButton button) {
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				if (getView().getSchoolClassPane().getListGrid().anySelected()) {

					SchoolClassWindow window = new SchoolClassWindow();

					window.getSaveButton().setTitle("Save");
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
				dto.setCode(window.getCodeField().getValueAsString());
				dto.setName(window.getClassNameField().getValueAsString());

				dto.setHasStreams(window.getHasStreams().getValueAsBoolean());
				dto.setClassLevel(window.getClassLevel().getValueAsBoolean());

				if (!window.getClassLevel().getValueAsBoolean()) {
					SchoolClassDTO parentSchoolClass = new SchoolClassDTO();
					parentSchoolClass.setId(window.getParentSchoolClass().getValueAsString());
					dto.setParentSchoolClass(parentSchoolClass);
				}

				SchoolDTO schoolDTO = new SchoolDTO();
				schoolDTO.setId(window.getSchoolCombo().getValueAsString());
				dto.setSchoolDTO(schoolDTO);

				AcademicTermDTO academicTermDTO = new AcademicTermDTO();
				academicTermDTO.setId(window.getAcademicTermCombo().getValueAsString());
				dto.setAcademicTermDTO(academicTermDTO);

				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(RequestConstant.UPDATE_SCHOOL_CLASS, dto);
				map.put(NetworkDataUtil.ACTION, RequestConstant.UPDATE_SCHOOL_CLASS);

				NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

					@Override
					public void onNetworkResult(RequestResult result) {

						SC.say("SUCCESS", result.getSystemFeedbackDTO().getMessage(), new BooleanCallback() {

							@Override
							public void execute(Boolean value) {

								if (value) {

									filterSchoolClassesByAcademicTermSchool(
											window.getAcademicYearCombo().getValueAsString(),
											window.getAcademicTermCombo().getValueAsString(),
											window.getDistrictCombo().getValueAsString(),
											window.getSchoolCombo().getValueAsString());

									clearSchoolClassWindowFields(window);
									window.close();

								}

							}
						});

					}
				});
			}
		});

	}

	private void loadFieldsToEdit(SchoolClassWindow window) {

		ListGridRecord record = getView().getSchoolClassPane().getListGrid().getSelectedRecord();

		window.getCodeField().setValue(record.getAttribute(SchoolClassListGrid.CODE));
		window.getClassNameField().setValue(record.getAttribute(SchoolClassListGrid.NAME));
		window.getSchoolCombo().setValue(record.getAttribute(SchoolClassListGrid.SCHOOL));
		window.getSchoolCombo().setValue(record.getAttribute(SchoolClassListGrid.DISTRICT));
		window.getAcademicTermCombo().setValue(record.getAttribute(SchoolClassListGrid.ACADEMIC_TERM_ID));

		window.getHasStreams().setValue(record.getAttributeAsBoolean(SchoolClassListGrid.HasStreams));
		window.getClassLevel().setValue(record.getAttributeAsBoolean(SchoolClassListGrid.ClassLevel));
		window.getParentSchoolClass().setValue(record.getAttribute(SchoolClassListGrid.SchoolClassId));

		loadSchoolCombo(window, record.getAttribute(SchoolClassListGrid.SCHOOL_ID));
		loadDistrictCombo(window, record.getAttribute(SchoolClassListGrid.DISTRICT_ID));
		loadAcademicYearCombo(window, record.getAttribute(SchoolClassListGrid.ACADEMIC_YEAR_ID));

		loadAcademicTermCombo(window, record.getAttribute(SchoolClassListGrid.ACADEMIC_TERM_ID));

		loadSchoolClass(window, record.getAttribute(SchoolClassListGrid.SchoolClassId));

	}

	//////////////////////////////// FILTER SCHOOL CLASS COMBOS

	private void loadFilterAcademicYearCombo(final FilterSchoolClassWindow window) {
		ComboUtil.loadAcademicYearCombo(window.getFilterYearTermDistrictSchool().getAcademicYearCombo(), dispatcher,
				placeManager, null);
	}

	private void loadFilterAcademicTermCombo(final FilterSchoolClassWindow window) {
		window.getFilterYearTermDistrictSchool().getAcademicYearCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				ComboUtil.loadAcademicTermComboByAcademicYear(
						window.getFilterYearTermDistrictSchool().getAcademicYearCombo(),
						window.getFilterYearTermDistrictSchool().getAcademicTermCombo(), dispatcher, placeManager,
						null);
			}
		});
	}

	private void loadFilterDistrictCombo(final FilterSchoolClassWindow window) {
		ComboUtil.loadDistrictCombo(window.getFilterYearTermDistrictSchool().getDistrictCombo(), dispatcher,
				placeManager, null);
	}

	private void loadFilterSchoolCombo(final FilterSchoolClassWindow window) {
		window.getFilterYearTermDistrictSchool().getDistrictCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				ComboUtil.loadSchoolComboByDistrict(window.getFilterYearTermDistrictSchool().getDistrictCombo(),
						window.getFilterYearTermDistrictSchool().getSchoolCombo(), dispatcher, placeManager, null);

			}
		});

	}

	private void loadSchoolClass(final SchoolClassWindow window, final String defaultValue) {
		window.getSchoolCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {

				ComboUtil.loadSchoolClassesComboBySchoolAcademicTermByLevel(window.getAcademicTermCombo(),
						window.getSchoolCombo(), window.getParentSchoolClass(), dispatcher, placeManager, defaultValue);

			}
		});

	}

	/////////////////////// END OF FILTER SCHOOL CLASS COMBOS

	private void loadAcademicYearCombo(final SchoolClassWindow window, final String defaultValue) {

		ComboUtil.loadAcademicYearCombo(window.getAcademicYearCombo(), dispatcher, placeManager, defaultValue);

	}

	private void loadAcademicTermCombo(final SchoolClassWindow window, final String defaultValue) {
		window.getAcademicYearCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				ComboUtil.loadAcademicTermComboByAcademicYear(window.getAcademicYearCombo(),
						window.getAcademicTermCombo(), dispatcher, placeManager, defaultValue);
			}
		});
	}

	private void loadDistrictCombo(final SchoolClassWindow window, final String defaultValue) {
		ComboUtil.loadDistrictCombo(window.getDistrictCombo(), dispatcher, placeManager, defaultValue);
	}

	private void loadSchoolCombo(final SchoolClassWindow window, final String defaultValue) {
		window.getDistrictCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				ComboUtil.loadSchoolComboByDistrict(window.getDistrictCombo(), window.getSchoolCombo(), dispatcher,
						placeManager, defaultValue);
			}
		});
	}

	private void clearSchoolClassWindowFields(SchoolClassWindow window) {

		// window.getAcademicTermCombo().clearValue();
		// window.getAcademicYearCombo().clearValue();
		// window.getDistrictCombo().clearValue();
		// window.getSchoolCombo().clearValue();

		window.getCodeField().clearValue();
		window.getClassNameField().clearValue();
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
								LinkedHashMap<String, Object> map = new LinkedHashMap<>();
								map.put(RequestDelimeters.SCHOOL_CLASS_ID, record.getAttributeAsString("id"));
								map.put(NetworkDataUtil.ACTION, RequestConstant.DELETE_SCHOOL_CLASS);

								NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

									@Override
									public void onNetworkResult(RequestResult result) {
										SC.say("SUCCESS", result.getSystemFeedbackDTO().getMessage());
										getAllSchoolClasses();
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
		map.put(NetworkDataUtil.ACTION, RequestConstant.GET_SCHOOL_CLASS);

		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {
				getView().getSchoolClassPane().getListGrid().addRecordsToGrid(result.getSchoolClassDTOs());
			}
		});

	}

	//////////////////////////////////// filter

	private void filterSchoolsBYDistrictSchoolCategory(final FilterSchoolWindow window) {
		window.getFilterButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String schoolCategoryId = window.getFilterRegionDistrictSchoolCategory().getSchoolCategoryCombo()
						.getValueAsString();
				String districtId = window.getFilterRegionDistrictSchoolCategory().getDistrictCombo()
						.getValueAsString();
				String regionId = window.getFilterRegionDistrictSchoolCategory().getRegionCombo().getValueAsString();
				FilterDTO dto = new FilterDTO();
				dto.setSchoolCategoryDTO(new SchoolCategoryDTO(schoolCategoryId));
				dto.setDistrictDTO(new DistrictDTO(districtId));
				dto.setRegionDto(new RegionDto(regionId));

				LinkedHashMap<String, Object> map = new LinkedHashMap<>();

				// map.put(RequestDelimeters.DISTRICT_ID, districtId);
				// map.put(RequestDelimeters.REGION_ID, regionId);
				map.put(RequestDelimeters.FILTER_SCHOOL, dto);
				map.put(NetworkDataUtil.ACTION, RequestConstant.FILTER_SCHOOLS_BY_SCHOOL_CATEGORY_REGION_DISTRICT);
				NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

					@Override
					public void onNetworkResult(RequestResult result) {
						getView().getSchoolPane().getListGrid().addRecordsToGrid(result.getSchoolDTOs());
					}
				});

			}
		});
	}

	private void filterSchoolClassesByAcademicTermSchool(final FilterSchoolClassWindow window) {
		window.getFilterButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String academicYearId = window.getFilterYearTermDistrictSchool().getAcademicYearCombo()
						.getValueAsString();
				String academicTermId = window.getFilterYearTermDistrictSchool().getAcademicTermCombo()
						.getValueAsString();
				String districtId = window.getFilterYearTermDistrictSchool().getDistrictCombo().getValueAsString();
				String schoolId = window.getFilterYearTermDistrictSchool().getSchoolCombo().getValueAsString();

				FilterDTO dto = new FilterDTO();
				dto.setAcademicYearDTO(new AcademicYearDTO(academicYearId));
				dto.setAcademicTermDTO(new AcademicTermDTO(academicTermId));
				dto.setDistrictDTO(new DistrictDTO(districtId));
				dto.setSchoolDTO(new SchoolDTO(schoolId));

				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(RequestDelimeters.FILTER_SCHOOL_CLASS, dto);
				map.put(NetworkDataUtil.ACTION,
						RequestConstant.FILTER_SCHOOL_CLASS_BY_ACADEMIC_YEAR_TERM_DISTRICT_SCHOOL);

				NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

					@Override
					public void onNetworkResult(RequestResult result) {
						getView().getSchoolClassPane().getListGrid().addRecordsToGrid(result.getSchoolClassDTOs());
					}
				});

			}
		});
	}

	private void importSchools(final MenuButton importbutton) {
		importbutton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				fileUpload();
			}
		});
	}

	private void fileUpload() {

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());

		SC.showPrompt("", "", new SwizimaLoader());

		dispatcher.execute(new RequestAction(RequestConstant.GET_FILE_UPLOAD_LINK, map),
				new AsyncCallback<RequestResult>() {
					public void onFailure(Throwable caught) {
						System.out.println(caught.getMessage());
						SC.say("ERROR", caught.getMessage());
						SC.clearPrompt();
					}

					public void onSuccess(RequestResult result) {

						SC.clearPrompt();

						if (result != null) {

							if (result.getSystemFeedbackDTO() != null) {

								// SC.say("GET_FILE_UPLOAD_LINK:: "+result.getSystemFeedbackDTO().getMessage());

								SchoolImportWindow window = new SchoolImportWindow();
								loadRegionCombo(window, null);
								loadDistrictComboByRegion(window, null);
								uploadFile(window, result.getSystemFeedbackDTO().getMessage());
								window.show();

							}

						} else {
							SC.say("ERROR", "Unknow error");
						}

					}
				});
	}

	private void loadRegionCombo(final SchoolImportWindow window, String defaultValue) {
		ComboUtil.loadRegionCombo(window.getRegion(), dispatcher, placeManager, defaultValue);
	}

	private void loadDistrictComboByRegion(final SchoolImportWindow window, final String defaultValue) {
		window.getRegion().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				ComboUtil.loadDistrictComboByRegion(window.getRegion(), window.getDistrict(), dispatcher, placeManager,
						defaultValue);
			}
		});
	}

	private void uploadFile(final SchoolImportWindow window, final String link) {

		window.getUploadButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {

				final String fileName = window.getUpload().getFilename();

				String fileExtension = UtilityManager.getInstance().getFileExtension(fileName);
				final String districtId = window.getDistrict().getValueAsString();
				final String regionId = window.getRegion().getValueAsString();

				if (fileExtension.equalsIgnoreCase("xlsx")) {

					SC.showPrompt("", "", new SwizimaLoader());

					final StringBuilder url = new StringBuilder();

					url.append(link + "importSchools").append("?");

					String arg0Name = URL.encodeQueryString("fileName");
					url.append(arg0Name);
					url.append("=");
					String arg0Value = URL.encodeQueryString(fileName);
					url.append(arg0Value);
					url.append("&" + "districtId" + "=" + districtId);

					window.getUploadForm().setAction(url.toString());
					window.getUploadForm().submit();

				} else {
					SC.warn("ERROR", "Only xlsx files allowed");
				}

				window.getUploadForm().addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {

					public void onSubmitComplete(SubmitCompleteEvent event) {

						SC.clearPrompt();

						// String serverResponse = event.getResults();

						SC.say("Sucess", "Upload Successful", new BooleanCallback() {

							@Override
							public void execute(Boolean value) {
								if (value) {
									window.close();

									loadSchools(regionId, districtId);
								}

							}
						});

						GWT.log("serverResponse::::: " + event.getResults());

					}

				});

			}

		});

	}

	private void loadSchools(String regionId, String districtId) {

		FilterDTO dto = new FilterDTO();
		dto.setDistrictDTO(new DistrictDTO(districtId));
		dto.setRegionDto(new RegionDto(regionId));

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();

		map.put(RequestDelimeters.FILTER_SCHOOL, dto);
		map.put(NetworkDataUtil.ACTION, RequestConstant.FILTER_SCHOOLS_BY_SCHOOL_CATEGORY_REGION_DISTRICT);
		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {

				getView().getSchoolPane().getListGrid().addRecordsToGrid(result.getSchoolDTOs());
			}
		});

	}

	private void loadSchoolLevels(final AddSchoolWindow window) {
		LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();

		for (SchoolLevel level : SchoolLevel.values()) {
			valueMap.put(level.getLevel(), level.getLevel());
		}

		window.getSchoolLevel().setValueMap(valueMap);
	}

	private void loadSchoolType(final AddSchoolWindow window) {
		LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();

		for (SchoolType type : SchoolType.values()) {
			valueMap.put(type.getType(), type.getType());
		}

		window.getSchoolType().setValueMap(valueMap);
	}

	private void loadSchoolOwnership(final AddSchoolWindow window) {
		LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();

		for (SchoolOwnership ownership : SchoolOwnership.values()) {
			valueMap.put(ownership.getSchoolOwnership(), ownership.getSchoolOwnership());
		}

		window.getSchoolOwnership().setValueMap(valueMap);
	}

	private void loadSchoolGenderCategory(final AddSchoolWindow window) {
		LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();

		for (SchoolGenderCategory category : SchoolGenderCategory.values()) {
			valueMap.put(category.getCategory(), category.getCategory());
		}

		window.getSchoolGenderCategory().setValueMap(valueMap);
	}

	private void loadSchoolLicenseStatus(final AddSchoolWindow window) {
		LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();

		valueMap.put("Yes", "Yes");
		valueMap.put("No", "No");

		window.getLicensed().setValueMap(valueMap);
	}

	private void requesttForHTDetails(final String schoolId, final SchoolLevel level) {
		final InitialSchoolStaffWindow window = new InitialSchoolStaffWindow();
		loadGenderCombo(window, null);
		loadRegisteredCombo(window, null);
		loadRoleCombo(window);
		saveSchoolStaff(window, schoolId, level);

		window.getCancelButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				SC.ask("Confirm", "Are you sure you dont want to create head teacher details?", new BooleanCallback() {

					@Override
					public void execute(Boolean value) {
						if (value) {
							window.close();
							initiateSchoolClasses(schoolId, level);
						}

					}
				});

			}
		});

		window.show();
	}

	private void loadGenderCombo(final InitialSchoolStaffWindow window, final String defaultValue) {
		LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();
		valueMap.put("female", "Female");
		valueMap.put("male", "Male");
		window.getGenderCombo().setValueMap(valueMap);
		if (defaultValue != null) {
			window.getGenderCombo().setValue(defaultValue);
		}
	}

	private void loadRegisteredCombo(final InitialSchoolStaffWindow window, final String defaultValue) {
		LinkedHashMap<Boolean, String> valueMap = new LinkedHashMap<>();
		valueMap.put(true, "Yes");
		valueMap.put(false, "No");

		window.getRegisteredCombo().setValueMap(valueMap);
		if (defaultValue != null) {
			window.getRegisteredCombo().setValue(defaultValue);
		}
	}

	private void loadRoleCombo(final InitialSchoolStaffWindow window) {

		LinkedHashMap<String, String> valueMap = new LinkedHashMap<>();
		valueMap.put("Teacher", "Teacher");
		valueMap.put("Head teacher", "Head teacher");
		valueMap.put("Deputy head teacher", "Deputy head teacher");
		valueMap.put("Smc", "Smc");
		window.getRoleCombo().setValueMap(valueMap);

	}

	private void saveSchoolStaff(final InitialSchoolStaffWindow window, final String schoolId,
			final SchoolLevel level) {
		window.getSaveButton().addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				if (checkIfNoSchoolStaffWindowFieldIsEmpty(window)) {
					SchoolStaffDTO dto = new SchoolStaffDTO();
					// dto.setId(id);

					dto.setRegistered(Boolean.valueOf(window.getRegisteredCombo().getValueAsString()));
					dto.setCreatedDateTime(dateTimeFormat.format(new Date()));

					dto.setStaffCode(window.getCodeField().getValueAsString());
					// dto.setStatus(status);
					dto.setStaffType(window.getRoleCombo().getValueAsString());

					SchoolDTO schoolDTO = new SchoolDTO(schoolId);
					dto.setSchoolDTO(schoolDTO);

					GeneralUserDetailDTO generalUserDetailDTO = new GeneralUserDetailDTO();
					generalUserDetailDTO.setFirstName(window.getFirstNameField().getValueAsString());
					generalUserDetailDTO.setLastName(window.getLastNameField().getValueAsString());
					generalUserDetailDTO.setEmail(window.getEmailField().getValueAsString());
					generalUserDetailDTO.setPhoneNumber(window.getPhoneNumberField().getValueAsString());
					generalUserDetailDTO.setGender(window.getGenderCombo().getValueAsString());
					generalUserDetailDTO.setNameAbbrev(window.getNameAbrevField().getValueAsString());
					generalUserDetailDTO.setDob(dateFormat.format(window.getDobItem().getValueAsDate()));
					generalUserDetailDTO.setNationalId(window.getNationalIdField().getValueAsString());

					dto.setGeneralUserDetailDTO(generalUserDetailDTO);

					GWT.log("STAFF " + dto);
					GWT.log("School " + dto.getSchoolDTO().getId());

					LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					map.put(RequestConstant.SAVE_SCHOOL_STAFF, dto);
					map.put(NetworkDataUtil.ACTION, RequestConstant.SAVE_SCHOOL_STAFF);

					NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

						@Override
						public void onNetworkResult(RequestResult result) {

							if (result.getSystemFeedbackDTO() != null) {

								if (result.getSystemFeedbackDTO().isResponse()) {

									SC.say("SUCCESS", result.getSystemFeedbackDTO().getMessage(),
											new BooleanCallback() {

												@Override
												public void execute(Boolean value) {

													window.close();
													initiateSchoolClasses(schoolId, level);
												}
											});

								} else {

									SC.ask("Confirm",
											result.getSystemFeedbackDTO().getMessage() + "Do you want to try again?",
											new BooleanCallback() {

												@Override
												public void execute(Boolean value) {

													if (!value) {
														window.close();
														initiateSchoolClasses(schoolId, level);
													}

												}
											});
								}

							} else {

								SC.warn("Unknown Server error", new BooleanCallback() {

									@Override
									public void execute(Boolean value) {

										window.close();
										initiateSchoolClasses(schoolId, level);
									}
								});

							}

						}
					});

				} else {
					SC.warn("Please Fill the fields");
				}

			}
		});
	}

	protected boolean checkIfNoSchoolStaffWindowFieldIsEmpty(InitialSchoolStaffWindow window) {
		boolean flag = true;

		if (window.getFirstNameField().getValueAsString() == null)
			flag = false;

		if (window.getLastNameField().getValueAsString() == null)
			flag = false;

		if (window.getDobItem().getValueAsDate() == null)
			flag = false;

		if (window.getGenderCombo().getValueAsString() == null)
			flag = false;

		if (window.getRegisteredCombo().getValueAsString() == null)
			flag = false;

		return flag;
	}

	private void filterSchoolClassesByAcademicTermSchool(final String academicYearId, final String academicTermId,
			final String districtId, final String schoolId) {

		FilterDTO dto = new FilterDTO();
		dto.setAcademicYearDTO(new AcademicYearDTO(academicYearId));
		dto.setAcademicTermDTO(new AcademicTermDTO(academicTermId));
		dto.setDistrictDTO(new DistrictDTO(districtId));
		dto.setSchoolDTO(new SchoolDTO(schoolId));

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestDelimeters.FILTER_SCHOOL_CLASS, dto);
		map.put(NetworkDataUtil.ACTION, RequestConstant.FILTER_SCHOOL_CLASS_BY_ACADEMIC_YEAR_TERM_DISTRICT_SCHOOL);

		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {
				getView().getSchoolClassPane().getListGrid().addRecordsToGrid(result.getSchoolClassDTOs());
			}
		});

	}

	private void initiateSchoolClasses(final String schoolId, SchoolLevel level) {

		InitialSchoolClassWindow window = new InitialSchoolClassWindow();

		List<SchoolClassDTO> list = loadInitiaSchoolClasses(level);
		window.getListGrid().addRecordsToGrid(list);
		saveInitialisedClases(schoolId, window);
		window.show();

	}

	private void saveInitialisedClases(final String schoolId, final InitialSchoolClassWindow window) {
		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				List<SchoolClassDTO> dtos = new ArrayList<>();

				for (ListGridRecord record : window.getListGrid().getRecords()) {

					SchoolClassDTO dto = new SchoolClassDTO();

					dto.setCode(record.getAttribute(InitialSchoolClassListGrid.CODE));
					dto.setName(record.getAttribute(InitialSchoolClassListGrid.NAME));
					dto.setCreatedDateTime(dateTimeFormat.format(new Date()));

					dto.setHasStreams(false);
					dto.setClassLevel(true);

					SchoolDTO schoolDTO = new SchoolDTO();
					schoolDTO.setId(schoolId);
					dto.setSchoolDTO(schoolDTO);

					dtos.add(dto);

				}

				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(RequestConstant.SAVE_SCHOOL_INITIAL_CLASSESS, dtos);
				map.put(NetworkDataUtil.ACTION, RequestConstant.SAVE_SCHOOL_INITIAL_CLASSESS);
				NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

					@Override
					public void onNetworkResult(RequestResult result) {

						if (result.getSystemFeedbackDTO() != null) {

							if (result.getSystemFeedbackDTO().isResponse()) {

								SC.say("SUCCESS", result.getSystemFeedbackDTO().getMessage(), new BooleanCallback() {

									@Override
									public void execute(Boolean value) {

										if (value) {

											window.close();
										}

									}
								});
							} else {
								SC.warn("ERROR", result.getSystemFeedbackDTO().getMessage());
							}
						} else {

							SC.warn("ERROR", "Unknown Server error");
						}

					}
				});

			}
		});
	}

	private List<SchoolClassDTO> loadInitiaSchoolClasses(SchoolLevel level) {

		List<SchoolClassDTO> list = new ArrayList<>();

		if (level != null) {

			if (level.getLevel().equalsIgnoreCase(SchoolLevel.PRIMARY.getLevel())) {

				for (int i = 1; i <= 7; i++) {

					String name = "P." + i;
					SchoolClassDTO dto = new SchoolClassDTO();
					dto.setCode(name);
					dto.setName(name);

					list.add(dto);

				}

			} else if (level.getLevel().equalsIgnoreCase(SchoolLevel.SECONDARY.getLevel())) {

				for (int i = 1; i <= 6; i++) {

					String name = "S." + i;
					SchoolClassDTO dto = new SchoolClassDTO();
					dto.setCode(name);
					dto.setName(name);
					list.add(dto);

				}
			} else if (level.getLevel().equalsIgnoreCase(SchoolLevel.CAI.getLevel())) {

				for (int i = 1; i <= 2; i++) {

					String name = "Year " + i;
					SchoolClassDTO dto = new SchoolClassDTO();
					dto.setCode(name);
					dto.setName(name);
					list.add(dto);

				}
			}
		}

		return list;

	}

}