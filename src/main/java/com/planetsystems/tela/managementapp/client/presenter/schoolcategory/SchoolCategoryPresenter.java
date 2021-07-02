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
import com.planetsystems.tela.managementapp.client.presenter.comboutils.ComboUtil;
import com.planetsystems.tela.managementapp.client.presenter.filterpaneutils.FilterRegionDistrictSchoolCategory;
import com.planetsystems.tela.managementapp.client.presenter.filterpaneutils.FilterYearTermDistrictSchool;
import com.planetsystems.tela.managementapp.client.presenter.main.MainPresenter;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkDataUtil;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkDataUtil2;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkResult;
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
import com.planetsystems.tela.managementapp.shared.RequestConstant;
import com.planetsystems.tela.managementapp.shared.RequestDelimeters;
import com.planetsystems.tela.managementapp.shared.RequestResult;
import com.planetsystems.tela.managementapp.shared.requestcommands.SchoolCategoryClassCommand;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangeEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangeHandler;
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
					// buttons.add(fiter);

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
					// buttons.add(delete);
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
//	   		SC.say("Advanced Search");
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
//	   		SC.say("Advanced Search");
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

	private void clearSchoolCategoryWindowFields(SchoolCategoryWindow window) {
		window.getCategoryCode().clearValue();
		window.getCategoryName().clearValue();
	}

	@Deprecated
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
					window.getSaveButton().setTitle("Update");
					loadFieldsToEdit(window);
					updateSchCategory2(window);
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

	@Deprecated
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

	@Deprecated
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

	@Deprecated
	private void getAllSchoolCategories() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		if (SessionManager.getInstance().getLoggedInUserGroup().equalsIgnoreCase(SessionManager.ADMIN))
			map.put(NetworkDataUtil.ACTION, RequestConstant.GET_SCHOOL_CATEGORY);
		else
			map.put(NetworkDataUtil.ACTION, RequestConstant.GET_SCHOOL_CATEGORIES_BY_SYSTEM_USER_PROFILE_SCHOOLS);

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
				window.show();
				loadRegionCombo2(window, null);
				loadSchoolCategoryCombo2(window, null);
				loadDistrictComboByRegion2(window, null);
				saveSchool2(window);
			}

		});
	}

	// add school combos

	@Deprecated
	private void loadRegionCombo(AddSchoolWindow window, String defaultValue) {
		ComboUtil.loadRegionCombo(window.getRegionCombo(), dispatcher, placeManager, defaultValue);
	}
	
	private void loadRegionCombo2(AddSchoolWindow window, String defaultValue) {
		ComboUtil.loadRegionCombo(window.getRegionCombo(), dispatcher, placeManager, defaultValue);
	}
	

	@Deprecated
	private void loadDistrictComboByRegion(final AddSchoolWindow window, final String defaultValue) {
		window.getRegionCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				ComboUtil.loadDistrictComboByRegion(window.getRegionCombo(),window.getDistrictCombo(), dispatcher, placeManager, defaultValue);
			}
		});
	}
	
	private void loadDistrictComboByRegion2(final AddSchoolWindow window, final String defaultValue) {
		window.getRegionCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				ComboUtil.loadDistrictComboByRegion(window.getRegionCombo(),window.getDistrictCombo(), dispatcher, placeManager, defaultValue);
			}
		});
	}
	

	@Deprecated
	private void loadSchoolCategoryCombo(AddSchoolWindow window, String defaultValue) {
		ComboUtil.loadSchoolCategoryCombo(window.getSchoolCategoryCombo(), dispatcher, placeManager, defaultValue);
	}
	
	private void loadSchoolCategoryCombo2(AddSchoolWindow window, String defaultValue) {
		ComboUtil.loadSchoolCategoryCombo(window.getSchoolCategoryCombo(), dispatcher, placeManager, defaultValue);
	}

	@Deprecated
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
						public void onNetworkResult(RequestResult result) {
							clearSchoolWindowFields(window);
							SC.say("SUCCESS", result.getSystemFeedbackDTO().getMessage());
							getAllSchools();
						}
					});

				} else {
					SC.warn("Please fill all the fields");
				}

			}
		});
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

		loadDistrictComboByRegion2(window, record.getAttribute(SchoolListGrid.DISTRICT_ID));
		loadSchoolCategoryCombo2(window, record.getAttribute(SchoolListGrid.CATEGORY_ID));

	}

	
	@Deprecated
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
		window.getRegionCombo().clearValue();
		window.getDistrictCombo().clearValue();
	}

	
	@Deprecated
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

	
	@Deprecated
	private void getAllSchools() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		if (SessionManager.getInstance().getLoggedInUserGroup().equalsIgnoreCase(SessionManager.ADMIN))
			map.put(NetworkDataUtil.ACTION, RequestConstant.GET_SCHOOLS);
		else
			map.put(NetworkDataUtil.ACTION, RequestConstant.GET_SCHOOLS_BY_SYSTEM_USER_PROFILE_SCHOOLS);
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
				loadAcademicYearCombo2(window, null);
				loadAcademicTermCombo2(window, null);
				loadDistrictCombo2(window, null);
				loadSchoolCombo2(window, null);
				saveSchoolClass2(window);
				window.show();
			}
		});
	}

	
	@Deprecated
	private void saveSchoolClass(final SchoolClassWindow window) {
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
					map.put(RequestConstant.SAVE_SCHOOL_CLASS, dto);
					map.put(NetworkDataUtil.ACTION, RequestConstant.SAVE_SCHOOL_CLASS);
					NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

						@Override
						public void onNetworkResult(RequestResult result) {
							clearSchoolClassWindowFields(window);
							// SC.say("SUCCESS", result.getSystemFeedbackDTO().getMessage());
							getAllSchoolClasses();
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

	@Deprecated
	private void updateSchoolClass(final SchoolClassWindow window) {
		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				ListGridRecord record = getView().getSchoolClassPane().getListGrid().getSelectedRecord();

				SchoolClassDTO dto = new SchoolClassDTO();
				dto.setId(record.getAttribute(SchoolClassListGrid.ID));
				dto.setCode(window.getCodeField().getValueAsString());
				dto.setName(window.getClassNameField().getValueAsString());

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
						clearSchoolClassWindowFields(window);
						window.close();
						getAllSchoolClasses();
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
		window.getAcademicTermCombo().setValue(record.getAttribute(SchoolClassListGrid.ACADEMIC_TERM));
		window.getAcademicTermCombo().setValue(record.getAttribute(SchoolClassListGrid.ACADEMIC_YEAR));

		loadSchoolCombo2(window, record.getAttribute(SchoolClassListGrid.SCHOOL_ID));
		loadDistrictCombo2(window, record.getAttribute(SchoolClassListGrid.DISTRICT_ID));
		loadAcademicTermCombo2(window, record.getAttribute(SchoolClassListGrid.ACADEMIC_TERM_ID));
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

	/////////////////////// END OF FILTER SCHOOL CLASS COMBOS

	@Deprecated
	private void loadAcademicYearCombo(final SchoolClassWindow window, final String defaultValue) {

		ComboUtil.loadAcademicYearCombo(window.getAcademicYearCombo(), dispatcher, placeManager, defaultValue);
	}
	private void loadAcademicYearCombo2(final SchoolClassWindow window, final String defaultValue) {

		ComboUtil.loadAcademicYearCombo(window.getAcademicYearCombo(), dispatcher, placeManager, defaultValue);
	}

	@Deprecated
	private void loadAcademicTermCombo(final SchoolClassWindow window, final String defaultValue) {
		window.getAcademicYearCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				ComboUtil.loadAcademicTermComboByAcademicYear(window.getAcademicYearCombo(),
						window.getAcademicTermCombo(), dispatcher, placeManager, defaultValue);
			}
		});
	}
	
	private void loadAcademicTermCombo2(final SchoolClassWindow window, final String defaultValue) {
		window.getAcademicYearCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				ComboUtil.loadAcademicTermComboByAcademicYear(window.getAcademicYearCombo(),
						window.getAcademicTermCombo(), dispatcher, placeManager, defaultValue);
			}
		});
	}
	

	@Deprecated
	private void loadDistrictCombo(final SchoolClassWindow window, final String defaultValue) {
		ComboUtil.loadDistrictCombo(window.getDistrictCombo(), dispatcher, placeManager, defaultValue);
	}
	
	private void loadDistrictCombo2(final SchoolClassWindow window, final String defaultValue) {
		ComboUtil.loadDistrictCombo(window.getDistrictCombo(), dispatcher, placeManager, defaultValue);
	}

	@Deprecated
	private void loadSchoolCombo(final SchoolClassWindow window, final String defaultValue) {
		window.getDistrictCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				ComboUtil.loadSchoolComboByDistrict(window.getDistrictCombo(), window.getSchoolCombo(), dispatcher,
						placeManager, defaultValue);
			}
		});
	}
	
	private void loadSchoolCombo2(final SchoolClassWindow window, final String defaultValue) {
		window.getDistrictCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				ComboUtil.loadSchoolComboByDistrict(window.getDistrictCombo(), window.getSchoolCombo(), dispatcher,
						placeManager, defaultValue);
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

	
	@Deprecated
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

	@Deprecated
	private void getAllSchoolClasses() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		if (SessionManager.getInstance().getLoggedInUserGroup().equalsIgnoreCase(SessionManager.ADMIN))
			map.put(NetworkDataUtil.ACTION, RequestConstant.GET_SCHOOL_CLASS);
		else
			map.put(NetworkDataUtil.ACTION, RequestConstant.GET_SCHOOL_CLASSES_BY_SYSTEM_USER_PROFILE_SCHOOLS);

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

//				map.put(RequestDelimeters.DISTRICT_ID, districtId);
//				map.put(RequestDelimeters.REGION_ID, regionId);
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
	
	
	
	
	
	////////////////////////////////////////////////////////////////////////NEW
	
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
					
					NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

						@Override
						public void onNetworkResult(MyRequestResult result) {
							
							if (result != null) {
								SystemResponseDTO<SchoolCategoryDTO> responseDTO = result.getSchoolCategoryResponse();
								if (responseDTO.isStatus()) {
									clearSchoolCategoryWindowFields(window);
									SC.say("SUCCESS", responseDTO.getMessage());
									getAllSchoolCategories2();
								} else {
									SC.say(responseDTO.getMessage());
								}
							}
							

						}
					});

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
			map.put(MyRequestAction.COMMAND , SchoolCategoryClassCommand.GET_SCHOOL_CATEGORIES_BY_SYSTEM_USER_PROFILE_SCHOOLS);

		NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

			@Override
			public void onNetworkResult(MyRequestResult result) {
				if (result != null) {
					SystemResponseDTO<List<SchoolCategoryDTO>> responseDTO = result.getSchoolCategoryResponseList();
					if (responseDTO.isStatus()) {
						if(responseDTO.getData() != null)
							getView().getSchoolCategoryPane().getListGrid().addRecordsToGrid(responseDTO.getData());
					} else {
						SC.say(responseDTO.getMessage());
					}
				}
				
			}
		});
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
				NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

					@Override
					public void onNetworkResult(MyRequestResult result) {
						if (result != null) {
							SystemResponseDTO<SchoolCategoryDTO> responseDTO = result.getSchoolCategoryResponse();
							if (responseDTO.isStatus()) {
								clearSchoolCategoryWindowFields(window);
								window.close();
								SC.say("SUCCESS", responseDTO.getMessage());
								getAllSchoolCategories2();
							} else {
								SC.say(responseDTO.getMessage());
							}
						}
						
						
					}
				});
			}
		});

	}
	
	
	
	////////////////////SCHOOL
	
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

					NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

						@Override
						public void onNetworkResult(MyRequestResult result) {
							if (result != null) {
								SystemResponseDTO<SchoolDTO> responseDTO = result.getSchoolResponse();
								if (responseDTO.isStatus()) {
									clearSchoolWindowFields(window);
									SC.say("SUCCESS", responseDTO.getMessage());
									getAllSchools2();
								} else {
									SC.say(responseDTO.getMessage());
								}
							}
						}
					});

				} else {
					SC.warn("Please fill all the fields");
				}

			}
		});
	}
	
	
	private void getAllSchools2() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		if (SessionManager.getInstance().getLoggedInUserGroup().equalsIgnoreCase(SessionManager.ADMIN))
			map.put(MyRequestAction.COMMAND, SchoolCategoryClassCommand.GET_ALL_SCHOOLS);
		else
			map.put(MyRequestAction.COMMAND, SchoolCategoryClassCommand.GET_SCHOOLS_BY_SYSTEM_USER_PROFILE_SCHOOLS);
		
		NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

			@Override
			public void onNetworkResult(MyRequestResult result) {
				if (result != null) {
					SystemResponseDTO<List<SchoolDTO>> responseDTO = result.getSchoolResponseList();
					if (responseDTO.isStatus()) {
						if(responseDTO.getData() != null)
						getView().getSchoolPane().getListGrid().addRecordsToGrid(responseDTO.getData());;
					} else {
						SC.say(responseDTO.getMessage());
					}
				}
				
				
			}
		});
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

				NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

					@Override
					public void onNetworkResult(MyRequestResult result) {
						if (result != null) {
							SystemResponseDTO<SchoolDTO> responseDTO = result.getSchoolResponse();
							if (responseDTO.isStatus()) {
								clearSchoolWindowFields(window);
								window.close();
								getAllSchools2();
							} else {
								SC.say(responseDTO.getMessage());
							}
						}

					}
				});
			}
		});
	}

	
	
	/////////SCHOOL CLASS
	
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
					NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

						@Override
						public void onNetworkResult(MyRequestResult result) {
							if (result != null) {
								SystemResponseDTO<SchoolClassDTO> responseDTO = result.getSchoolClassResponse();
								if (responseDTO.isStatus()) {
									if(responseDTO.getData() == null)
									SC.say(responseDTO.getMessage());
									clearSchoolClassWindowFields(window);
									getAllSchoolClasses2();
								} else {
									SC.say(responseDTO.getMessage());
								}
							}
						}
					});

				} else {
					SC.warn("Please fill all fields");
				}

			}

		});

	}
	
	
	private void getAllSchoolClasses2() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		if (SessionManager.getInstance().getLoggedInUserGroup().equalsIgnoreCase(SessionManager.ADMIN))
			map.put(MyRequestAction.COMMAND, SchoolCategoryClassCommand.GET_ALL_SCHOOL_CLASSES);
		else
			map.put(MyRequestAction.COMMAND, SchoolCategoryClassCommand.GET_SCHOOL_CLASSES_BY_SYSTEM_USER_PROFILE_SCHOOLS);

		NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

			@Override
			public void onNetworkResult(MyRequestResult result) {
				if (result != null) {
					SystemResponseDTO<List<SchoolClassDTO>> responseDTO = result.getSchoolClassResponseList();
					if (responseDTO.isStatus()) {
						if(responseDTO.getData() != null)
						getView().getSchoolClassPane().getListGrid().addRecordsToGrid(responseDTO.getData());
					} else {
						SC.say(responseDTO.getMessage());
					}
				}
				
				
			}
		});

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

				SchoolDTO schoolDTO = new SchoolDTO();
				schoolDTO.setId(window.getSchoolCombo().getValueAsString());
				dto.setSchoolDTO(schoolDTO);

				AcademicTermDTO academicTermDTO = new AcademicTermDTO();
				academicTermDTO.setId(window.getAcademicTermCombo().getValueAsString());
				dto.setAcademicTermDTO(academicTermDTO);

				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(MyRequestAction.DATA, dto);
				map.put(MyRequestAction.COMMAND, SchoolCategoryClassCommand.UPDATE_SCHOOL_CLASS);

				NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

					@Override
					public void onNetworkResult(MyRequestResult result) {
						if (result != null) {
							SystemResponseDTO<SchoolClassDTO> responseDTO = result.getSchoolClassResponse();
							if (responseDTO.isStatus()) {
								clearSchoolClassWindowFields(window);
								window.close();
								getAllSchoolClasses2();
							} else {
								SC.say(responseDTO.getMessage());
							}
						}
					}
				});
			}
		});

	}

	

}