package com.planetsystems.tela.managementapp.client.presenter.schoolcategory;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.i18n.client.DateTimeFormat;
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
import com.planetsystems.tela.dto.RegionDto;
import com.planetsystems.tela.dto.SchoolCategoryDTO;
import com.planetsystems.tela.dto.SchoolClassDTO;
import com.planetsystems.tela.dto.SchoolDTO;
import com.planetsystems.tela.dto.response.SystemResponseDTO;
import com.planetsystems.tela.managementapp.client.event.HighlightActiveLinkEvent;
import com.planetsystems.tela.managementapp.client.gin.SessionManager;
import com.planetsystems.tela.managementapp.client.place.NameTokens;
import com.planetsystems.tela.managementapp.client.presenter.comboutils.ComboUtil2;
import com.planetsystems.tela.managementapp.client.presenter.main.MainPresenter;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkDataUtil2;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkResult2;
import com.planetsystems.tela.managementapp.client.presenter.schoolcategory.school.AddSchoolWindow;
import com.planetsystems.tela.managementapp.client.presenter.schoolcategory.school.FilterSchoolWindow;
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
import com.planetsystems.tela.managementapp.shared.DatePattern;
import com.planetsystems.tela.managementapp.shared.MyRequestAction;
import com.planetsystems.tela.managementapp.shared.MyRequestResult;
import com.planetsystems.tela.managementapp.shared.RequestDelimeters;
import com.planetsystems.tela.managementapp.shared.requestcommands.SchoolCategoryClassCommand;
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
		getAllSchoolCategories2();
		getAllSchoolClasses2();
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

				if (selectedTab.equalsIgnoreCase(SchoolCategoryView.SCHOOL_CATEGORY_TAB_TITLE)) {
					MenuButton newButton = new MenuButton("New");
					MenuButton edit = new MenuButton("Edit");
					MenuButton delete = new MenuButton("Delete");

					List<MenuButton> buttons = new ArrayList<>();
					buttons.add(newButton);
					buttons.add(edit);
					buttons.add(delete);

					getView().getControlsPane().addMenuButtons(buttons);

					addSchoolCategory(newButton);
					deleteSchoolCategory2(delete);
					editSchoolCategory(edit);

				} else if (selectedTab.equalsIgnoreCase(SchoolCategoryView.SCHOOL_TAB_TITLE)) {
					MenuButton newButton = new MenuButton("New");
					MenuButton edit = new MenuButton("Edit");
					MenuButton delete = new MenuButton("Delete");
					MenuButton filter = new MenuButton("Filter");

					List<MenuButton> buttons = new ArrayList<>();
					buttons.add(newButton);
					buttons.add(edit);
					buttons.add(filter);

					getView().getControlsPane().addMenuButtons(buttons);

					addSchool(newButton);
					deleteSchool2(delete);
					editSchool(edit);
					getAllSchools2();
					selectFilterSchoolOption(filter);

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

					getView().getControlsPane().addMenuButtons(buttons);

					addSchoolClass(newButton);
					deleteSchoolClass2(delete);
					editSchoolClass(edit);
					selectFilterSchoolClassOption(filter);

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
				FilterSchoolWindow window = new FilterSchoolWindow();
				loadFilterDistrictCombos(window);

				window.show();
				filterSchools(window);
			}

		});

	}

	private void loadFilterDistrictCombos(final FilterSchoolWindow window) {

		ComboUtil2.loadRegionCombo(window.getFilterRegionDistrictSchoolCategory().getRegionCombo(), dispatcher,
				placeManager, null);

		window.getFilterRegionDistrictSchoolCategory().getRegionCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				ComboUtil2.loadDistrictComboByRegion(window.getFilterRegionDistrictSchoolCategory().getRegionCombo(),
						window.getFilterRegionDistrictSchoolCategory().getDistrictCombo(), dispatcher, placeManager,
						null);
			}
		});

		ComboUtil2.loadSchoolCategoryCombo(window.getFilterRegionDistrictSchoolCategory().getSchoolCategoryCombo(),
				dispatcher, placeManager, null);

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
				FilterSchoolClassWindow window = new FilterSchoolClassWindow();
				loadFilterSchoolClassesCombos(window, null);
				window.show();
				filterSchoolClasses(window);
			}

		});

	}

	private void loadFilterSchoolClassesCombos(final FilterSchoolClassWindow window, final String defaultValue) {
		ComboUtil2.loadAcademicYearCombo(window.getFilterYearTermDistrictSchool().getAcademicYearCombo(), dispatcher,
				placeManager, defaultValue);

		window.getFilterYearTermDistrictSchool().getAcademicYearCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				ComboUtil2.loadAcademicTermComboByAcademicYear(
						window.getFilterYearTermDistrictSchool().getAcademicYearCombo(),
						window.getFilterYearTermDistrictSchool().getAcademicTermCombo(), dispatcher, placeManager,
						defaultValue);
			}
		});

		ComboUtil2.loadDistrictCombo(window.getFilterYearTermDistrictSchool().getDistrictCombo(), dispatcher,
				placeManager, defaultValue);

		window.getFilterYearTermDistrictSchool().getDistrictCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				ComboUtil2.loadSchoolComboByDistrict(window.getFilterYearTermDistrictSchool().getDistrictCombo(),
						window.getFilterYearTermDistrictSchool().getSchoolCombo(), dispatcher, placeManager,
						defaultValue);

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
				saveSchoolCategory2(window);
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
					window.getSaveButton().setTitle("Update");
					ListGridRecord record = getView().getSchoolCategoryPane().getListGrid().getSelectedRecord();
					window.getCategoryCode().setValue(record.getAttribute(SchoolCategoryListGrid.CODE));
					window.getCategoryName().setValue(record.getAttribute(SchoolCategoryListGrid.NAME));
					updateSchCategory2(window);
					window.show();

				} else {
					SC.say("Please select a record to edit");
				}
			}

		});

	}

	////////////////////////////////////////////////////////// SCHOOL////////////////////////////////////////////////////////////////////

	private void addSchool(MenuButton menuButton) {
		menuButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				AddSchoolWindow window = new AddSchoolWindow();
				window.show();
				loadAddSchoolCombo(window, null, null, null);
				saveSchool2(window);
			}

		});
	}

	private void loadAddSchoolCombo(final AddSchoolWindow window, final String region, final String district,
			final String schoolCategory) {

		ComboUtil2.loadRegionCombo(window.getRegionCombo(), dispatcher, placeManager, region);

		window.getRegionCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				ComboUtil2.loadDistrictComboByRegion(window.getRegionCombo(), window.getDistrictCombo(), dispatcher,
						placeManager, district);
			}
		});

		ComboUtil2.loadSchoolCategoryCombo(window.getSchoolCategoryCombo(), dispatcher, placeManager, schoolCategory);

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

//		if (window.getLatitude().getValueAsString() == null)
//			flag = false;
//
//		if (window.getLongtitude().getValueAsString() == null)
//			flag = false;

//		if (window.getDeviceNumber().getValueAsString() == null)
//			flag = false;

		return flag;
	}

	private void editSchool(MenuButton button) {
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (getView().getSchoolPane().getListGrid().anySelected()) {
					AddSchoolWindow window = new AddSchoolWindow();
					window.getSaveButton().setTitle("Update");
					loadFieldsToEdit(window);
					updateSchool2(window);
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
		window.getDistrictCombo().setValue(record.getAttribute(SchoolListGrid.DISTRICT));
		window.getSchoolName().setValue(record.getAttribute(SchoolListGrid.NAME));
		window.getLatitude().setValue(record.getAttribute(SchoolListGrid.LATITUDE));
		window.getLongtitude().setValue(record.getAttribute(SchoolListGrid.LONGITUDE));
		window.getDeviceNumber().setValue(record.getAttribute(SchoolListGrid.DEVICE_NUMBER));

		loadAddSchoolCombo(window, SchoolListGrid.REGION_ID, record.getAttribute(SchoolListGrid.DISTRICT_ID),
				record.getAttribute(SchoolListGrid.CATEGORY_ID));
	}

	private void clearSchoolWindowFields(AddSchoolWindow window) {

		window.getSchoolCode().clearValue();
		window.getSchoolName().clearValue();
		window.getLatitude().clearValue();
		window.getLongtitude().clearValue();
		window.getDeviceNumber().clearValue();
		window.getSchoolCategoryCombo().clearValue();
		window.getRegionCombo().clearValue();
		window.getDistrictCombo().clearValue();
	}

//	//////////////////////////////////////////////// SCHOOL
//	//////////////////////////////////////////////// CLASS///////////////////////////////////////////////////////////////////////
//
	private void addSchoolClass(MenuButton menuButton) {
		menuButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				SchoolClassWindow window = new SchoolClassWindow();
				loadAddSchoolClassCombo(window, null, null, null, null);
				window.show();
				saveSchoolClass2(window);
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
					window.getSaveButton().setTitle("Update");
					loadFieldsToEdit(window);
					updateSchoolClass2(window);
					window.show();
				} else {
					SC.say("INFO: please select record to update");
				}
			}
		});

	}

	private void loadFieldsToEdit(SchoolClassWindow window) {
		ListGridRecord record = getView().getSchoolClassPane().getListGrid().getSelectedRecord();
		window.getCodeField().setValue(record.getAttribute(SchoolClassListGrid.CODE));
		window.getClassNameField().setValue(record.getAttribute(SchoolClassListGrid.NAME));
		loadAddSchoolClassCombo(window, record.getAttribute(SchoolClassListGrid.ACADEMIC_YEAR),
				record.getAttribute(SchoolClassListGrid.ACADEMIC_TERM),
				record.getAttribute(SchoolClassListGrid.DISTRICT_ID),
				record.getAttribute(SchoolClassListGrid.SCHOOL_ID));

	}

	private void loadAddSchoolClassCombo(final SchoolClassWindow window, final String year, final String term,
			final String district, final String school) {

		ComboUtil2.loadAcademicYearCombo(window.getAcademicYearCombo(), dispatcher, placeManager, year);

		window.getAcademicYearCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				ComboUtil2.loadAcademicTermComboByAcademicYear(window.getAcademicYearCombo(),
						window.getAcademicTermCombo(), dispatcher, placeManager, term);
			}
		});

		ComboUtil2.loadDistrictCombo(window.getDistrictCombo(), dispatcher, placeManager, district);

		window.getDistrictCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				ComboUtil2.loadSchoolComboByDistrict(window.getDistrictCombo(), window.getSchoolCombo(), dispatcher,
						placeManager, school);
			}
		});

	}

	private void clearSchoolClassWindowFields(SchoolClassWindow window) {
		window.getCodeField().clearValue();
		window.getClassNameField().clearValue();
		window.getSchoolCombo().clearValue();
		window.getDistrictCombo().clearValue();
		window.getAcademicTermCombo().clearValue();
		window.getAcademicYearCombo().clearValue();
	}

	//////////////////////////////////// filter

	private void filterSchools(final FilterSchoolWindow window) {
		window.getFilterButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				FilterDTO dto = new FilterDTO();
				String category = window.getFilterRegionDistrictSchoolCategory().getSchoolCategoryCombo().getValueAsString();
				if(category != null)
				dto.setSchoolCategoryDTO(new SchoolCategoryDTO(category));
				
				String district = window.getFilterRegionDistrictSchoolCategory().getDistrictCombo().getValueAsString();
				if(district != null)
				dto.setDistrictDTO(new DistrictDTO(district));
		
				String region = window.getFilterRegionDistrictSchoolCategory().getRegionCombo().getValueAsString();
				if(region != null)
				dto.setRegionDto(new RegionDto(region));

				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(MyRequestAction.DATA, dto);
				map.put(MyRequestAction.COMMAND, SchoolCategoryClassCommand.FILTER_SCHOOLS);
				getSchoolResponseList(map);
			}
		});
	}

	// sets grid
	private void getSchoolResponseList(LinkedHashMap<String, Object> requestPayLoadMap) {
		NetworkDataUtil2.callNetwork2(dispatcher, placeManager, requestPayLoadMap, new NetworkResult2() {

			@Override
			public void onNetworkResult(MyRequestResult result) {
				if (result != null) {
					SystemResponseDTO<List<SchoolDTO>> responseDTO = result.getSchoolResponseList();
					if (responseDTO.isStatus()) {
						if (responseDTO.getData() != null)
							getView().getSchoolPane().getListGrid().addRecordsToGrid(responseDTO.getData());
					} else {
						SC.say(responseDTO.getMessage());
					}
				}

			}
		});
	}

	private void filterSchoolClasses(final FilterSchoolClassWindow window) {
		window.getFilterButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				FilterDTO dto = new FilterDTO();
				String year = window.getFilterYearTermDistrictSchool().getAcademicYearCombo().getValueAsString();
				if(year != null)
				dto.setAcademicYearDTO(new AcademicYearDTO(year));
				
				String term = window.getFilterYearTermDistrictSchool().getAcademicTermCombo().getValueAsString();
				if(term != null)
				dto.setAcademicTermDTO(new AcademicTermDTO(term));
				
				String district = window.getFilterYearTermDistrictSchool().getDistrictCombo().getValueAsString();
				if(district != null)
				dto.setDistrictDTO(new DistrictDTO(district));
				
				String school = window.getFilterYearTermDistrictSchool().getSchoolCombo().getValueAsString();
				if(school != null)
				dto.setSchoolDTO(new SchoolDTO(school));

				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(MyRequestAction.DATA, dto);
				map.put(MyRequestAction.COMMAND, SchoolCategoryClassCommand.FILTER_SCHOOL_CLASS);
				getSchoolClassesResponseList(map);
			}
		});
	}

	// sets grid
	public void getSchoolClassesResponseList(LinkedHashMap<String, Object> requestPayloadMap) {
		NetworkDataUtil2.callNetwork2(dispatcher, placeManager, requestPayloadMap, new NetworkResult2() {

			@Override
			public void onNetworkResult(MyRequestResult result) {

				if (result != null) {
					SystemResponseDTO<List<SchoolClassDTO>> responseDTO = result.getSchoolClassResponseList();
					if (responseDTO.isStatus()) {
						if (responseDTO.getData() != null)
							getView().getSchoolClassPane().getListGrid().addRecordsToGrid(responseDTO.getData());
					} else {
						SC.say(responseDTO.getMessage());
					}
				}

			}
		});

	}

	//////////////////////////////////////////////////////////////////////// NEW

	private void saveSchoolCategory2(final SchoolCategoryWindow window) {
		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				if (checkIfNoSchoolCategoryWindowFieldIsEmpty(window)) {
					SchoolCategoryDTO dto = new SchoolCategoryDTO();
					dto.setCode(window.getCategoryCode().getValueAsString());
					dto.setName(window.getCategoryName().getValueAsString());
					dto.setCreatedDateTime(dateTimeFormat.format(new Date()));

					LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					map.put(MyRequestAction.DATA, dto);
					map.put(MyRequestAction.COMMAND, SchoolCategoryClassCommand.SAVE_SCHOOL_CATEGORY);

					getSchoolCategoryResponseList(map);
				} else {
					SC.warn("Please fill all the fields");
				}

			}

		});

	}

	private void getAllSchoolCategories2() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		if (SessionManager.getInstance().getLoggedInUserGroup().equalsIgnoreCase(SessionManager.ADMIN))
			map.put(MyRequestAction.COMMAND, SchoolCategoryClassCommand.GET_ALL_SCHOOL_CATEGORYS);
		else
			map.put(MyRequestAction.COMMAND,
					SchoolCategoryClassCommand.GET_SCHOOL_CATEGORIES_BY_SYSTEM_USER_PROFILE_SCHOOLS);

		getSchoolCategoryResponseList(map);
	}

	private void deleteSchoolCategory2(MenuButton button) {
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
								map.put(MyRequestAction.COMMAND, SchoolCategoryClassCommand.DELETE_SCHOOL_CATEGORY);

								NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

									@Override
									public void onNetworkResult(MyRequestResult result) {

										if (result != null) {
											SystemResponseDTO<String> responseDTO = result.getResponseText();
											if (responseDTO.isStatus()) {
												SC.say("SUCCESS", responseDTO.getMessage());
												getAllSchoolCategories2();
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

	private void updateSchCategory2(final SchoolCategoryWindow window) {
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
				map.put(MyRequestAction.DATA, dto);
				map.put(MyRequestAction.COMMAND, SchoolCategoryClassCommand.UPDATE_SCHOOL_CATEGORY);
				getSchoolCategoryResponseList(map);
			}
		});

	}

	// set grid
	private void getSchoolCategoryResponseList(LinkedHashMap<String, Object> map) {
		NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

			@Override
			public void onNetworkResult(MyRequestResult result) {
				if (result != null) {
					SystemResponseDTO<SchoolCategoryDTO> responseDTO = result.getSchoolCategoryResponse();
					if (responseDTO.isStatus()) {
						// clearSchoolCategoryWindowFields(window);
						// window.close();
						SC.say("SUCCESS", responseDTO.getMessage());
						getAllSchoolCategories2();
					} else {
						SC.say(responseDTO.getMessage());
					}
				}

			}
		});
	}

	//////////////////// SCHOOL

	private void saveSchool2(final AddSchoolWindow window) {
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

					SC.say(dto.getName());

					SchoolCategoryDTO schoolCategoryDTO = new SchoolCategoryDTO();
					schoolCategoryDTO.setId(window.getSchoolCategoryCombo().getValueAsString());
					dto.setSchoolCategoryDTO(schoolCategoryDTO);

					DistrictDTO districtDTO = new DistrictDTO();
					districtDTO.setId(window.getDistrictCombo().getValueAsString());
					dto.setDistrictDTO(districtDTO);

					LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					map.put(MyRequestAction.DATA, dto);
					map.put(MyRequestAction.COMMAND, SchoolCategoryClassCommand.SAVE_SCHOOL);

					getSchoolResponseList(map);
				} else {
					SC.warn("Please fill all the fields");
				}

			}
		});
	}

	private void getAllSchools2() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		if (SessionManager.getInstance().getLoggedInUserGroup().equalsIgnoreCase(SessionManager.ADMIN))
			map.put(MyRequestAction.COMMAND, SchoolCategoryClassCommand.FILTER_SCHOOLS);
		else
			map.put(MyRequestAction.COMMAND, SchoolCategoryClassCommand.GET_SCHOOLS_BY_SYSTEM_USER_PROFILE_SCHOOLS);

		map.put(MyRequestAction.DATA, new FilterDTO());
		getSchoolResponseList(map);
	}

	private void deleteSchool2(MenuButton button) {
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
								map.put(MyRequestAction.COMMAND, SchoolCategoryClassCommand.DELETE_SCHOOL);

								NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

									@Override
									public void onNetworkResult(MyRequestResult result) {
										if (result != null) {
											SystemResponseDTO<String> responseDTO = result.getResponseText();
											if (responseDTO.isStatus()) {
												SC.say("SUCCESS", responseDTO.getMessage());
												getAllSchools2();
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

	private void updateSchool2(final AddSchoolWindow window) {
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
				schoolCategoryDTO.setId(window.getSchoolCategoryCombo().getValueAsString());
				dto.setSchoolCategoryDTO(schoolCategoryDTO);

				DistrictDTO districtDTO = new DistrictDTO();
				districtDTO.setId(window.getDistrictCombo().getValueAsString());
				dto.setDistrictDTO(districtDTO);

				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(MyRequestAction.DATA, dto);
				map.put(MyRequestAction.COMMAND, SchoolCategoryClassCommand.UPDATE_SCHOOL);

				getSchoolResponseList(map);
			}
		});
	}

	///////// SCHOOL CLASS

	private void saveSchoolClass2(final SchoolClassWindow window) {
		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				if (checkIfNoSchoolClassWindowFieldIsEmpty(window)) {
					SchoolClassDTO dto = new SchoolClassDTO();
					dto.setCode(window.getCodeField().getValueAsString());
					dto.setName(window.getClassNameField().getValueAsString());
					dto.setCreatedDateTime(dateTimeFormat.format(new Date()));

					SchoolDTO schoolDTO = new SchoolDTO();
					schoolDTO.setId(window.getSchoolCombo().getValueAsString());
					dto.setSchoolDTO(schoolDTO);

					AcademicTermDTO academicTermDTO = new AcademicTermDTO();
					academicTermDTO.setId(window.getAcademicTermCombo().getValueAsString());
					dto.setAcademicTermDTO(academicTermDTO);

					LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					map.put(MyRequestAction.DATA, dto);
					map.put(MyRequestAction.COMMAND, SchoolCategoryClassCommand.SAVE_SCHOOL_CLASS);

					getSchoolClassesResponseList(map);
				} else {
					SC.warn("Please fill all fields");
				}

			}

		});

	}

	private void getAllSchoolClasses2() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(MyRequestAction.DATA, new FilterDTO());
		if (SessionManager.getInstance().getLoggedInUserGroup().equalsIgnoreCase(SessionManager.ADMIN))
			map.put(MyRequestAction.COMMAND, SchoolCategoryClassCommand.FILTER_SCHOOL_CLASS);
		else
			map.put(MyRequestAction.COMMAND,
					SchoolCategoryClassCommand.GET_SCHOOL_CLASSES_BY_SYSTEM_USER_PROFILE_SCHOOLS);

		getSchoolClassesResponseList(map);
	}

	private void deleteSchoolClass2(MenuButton button) {
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
								map.put(MyRequestAction.COMMAND, SchoolCategoryClassCommand.DELETE_SCHOOL_CLASS);

								NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

									@Override
									public void onNetworkResult(MyRequestResult result) {
										if (result != null) {
											SystemResponseDTO<String> responseDTO = result.getResponseText();
											if (responseDTO.isStatus()) {
												SC.say("SUCCESS", responseDTO.getMessage());
												getAllSchoolClasses2();
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

	private void updateSchoolClass2(final SchoolClassWindow window) {
		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				ListGridRecord record = getView().getSchoolClassPane().getListGrid().getSelectedRecord();

				SchoolClassDTO dto = new SchoolClassDTO();
				dto.setId(record.getAttribute(SchoolClassListGrid.ID));
				dto.setCode(window.getCodeField().getValueAsString());
				dto.setName(window.getClassNameField().getValueAsString());

				dto.setSchoolDTO(new SchoolDTO(window.getSchoolCombo().getValueAsString()));
				dto.setAcademicTermDTO(new AcademicTermDTO(window.getAcademicTermCombo().getValueAsString()));

				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(MyRequestAction.DATA, dto);
				map.put(MyRequestAction.COMMAND, SchoolCategoryClassCommand.UPDATE_SCHOOL_CLASS);

				getSchoolClassResponse(map);

			}
		});

	}

	private void getSchoolClassResponse(LinkedHashMap<String, Object> map) {
		NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

			@Override
			public void onNetworkResult(MyRequestResult result) {
				if (result != null) {
					SystemResponseDTO<SchoolClassDTO> responseDTO = result.getSchoolClassResponse();
					if (responseDTO.isStatus()) {
						// clearSchoolClassWindowFields(window);
						// window.close();
						getAllSchoolClasses2();
					} else {
						SC.say(responseDTO.getMessage());
					}
				}
			}
		});
	}

}