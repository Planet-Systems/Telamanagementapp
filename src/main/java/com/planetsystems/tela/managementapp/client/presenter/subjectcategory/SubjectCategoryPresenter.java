package com.planetsystems.tela.managementapp.client.presenter.subjectcategory;

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
import com.planetsystems.tela.dto.FilterDTO;
import com.planetsystems.tela.dto.SubjectCategoryDTO;
import com.planetsystems.tela.dto.SubjectDTO;
import com.planetsystems.tela.dto.response.SystemResponseDTO;
import com.planetsystems.tela.managementapp.client.event.HighlightActiveLinkEvent;
import com.planetsystems.tela.managementapp.client.place.NameTokens;
import com.planetsystems.tela.managementapp.client.presenter.comboutils.ComboUtil2;
import com.planetsystems.tela.managementapp.client.presenter.main.MainPresenter;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkDataUtil2;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkResult2;
import com.planetsystems.tela.managementapp.client.presenter.schoolcategory.schoolclass.SchoolClassListGrid;
import com.planetsystems.tela.managementapp.client.presenter.subjectcategory.category.SubjectCategoryListGrid;
import com.planetsystems.tela.managementapp.client.presenter.subjectcategory.category.SubjectCategoryPane;
import com.planetsystems.tela.managementapp.client.presenter.subjectcategory.category.SubjectCategoryWindow;
import com.planetsystems.tela.managementapp.client.presenter.subjectcategory.subject.FilterSubjectWindow;
import com.planetsystems.tela.managementapp.client.presenter.subjectcategory.subject.SubjectListGrid;
import com.planetsystems.tela.managementapp.client.presenter.subjectcategory.subject.SubjectPane;
import com.planetsystems.tela.managementapp.client.presenter.subjectcategory.subject.SubjectWindow;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.planetsystems.tela.managementapp.client.widget.MenuButton;
import com.planetsystems.tela.managementapp.shared.DatePattern;
import com.planetsystems.tela.managementapp.shared.MyRequestAction;
import com.planetsystems.tela.managementapp.shared.MyRequestResult;
import com.planetsystems.tela.managementapp.shared.RequestDelimeters;
import com.planetsystems.tela.managementapp.shared.requestcommands.SubjectCategoryCommand;
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

public class SubjectCategoryPresenter
		extends Presenter<SubjectCategoryPresenter.MyView, SubjectCategoryPresenter.MyProxy> {
	interface MyView extends View {
		ControlsPane getControlsPane();

		TabSet getTabSet();

		SubjectCategoryPane getSubCategoryPane();

		SubjectPane getSubjectPane();
	}

	@SuppressWarnings("deprecation")
	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_SubjectCategory = new Type<RevealContentHandler<?>>();

	@Inject
	private DispatchAsync dispatcher;

	@Inject
	private PlaceManager placeManager;

	DateTimeFormat dateTimeFormat = DateTimeFormat
			.getFormat(DatePattern.DAY_MONTH_YEAR_HOUR_MINUTE_SECONDS.getPattern());
	DateTimeFormat dateFormat = DateTimeFormat.getFormat(DatePattern.DAY_MONTH_YEAR.getPattern());

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
		getAllSubjectCategories2();
		getAllSubjects2();
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

					getView().getControlsPane().addMenuButtons(buttons);
					addSubjectCategory(newButton);
					deleteSubjectCategory2(delete);
					editSubjectCategory(edit);

				} else if (selectedTab.equalsIgnoreCase(SubjectCategoryView.SUBJECT_TAB_TITLE)) {

					MenuButton newButton = new MenuButton("New");
					MenuButton edit = new MenuButton("Edit");
					MenuButton delete = new MenuButton("Delete");
					MenuButton filter = new MenuButton("Filter");

					List<MenuButton> buttons = new ArrayList<>();
					buttons.add(newButton);
					buttons.add(edit);
					buttons.add(filter);

					getView().getControlsPane().addMenuButtons(buttons);
					addSubject(newButton);
					deleteSubject2(delete);
					editSubject(edit);
					selectFilterSubjectOption(filter);
				} else {
					List<MenuButton> buttons = new ArrayList<>();
					getView().getControlsPane().addMenuButtons(buttons);
				}

			}
		});
	}

	private void selectFilterSubjectOption(final MenuButton filter) {
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
				getView().getSubjectPane().getListGrid().setShowFilterEditor(true);
			}
		});

		advanced.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				FilterSubjectWindow window = new FilterSubjectWindow();
				ComboUtil2.loadSubjectCategoryCombo(window.getFilterSubjectsPane().getCategoryCombo(), dispatcher, placeManager,null);
				window.show();
				filterSubjectsSubjectCategory(window);
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
				saveSubjectCategory2(window);

			}
		});
	}

	private void clearSubjectCategoryWindowFields(SubjectCategoryWindow window) {
		window.getCodeField().clearValue();
		window.getNameField().clearValue();
	}


	private boolean checkIfNoSubjectCategoryWindowFieldIsEmpty(SubjectCategoryWindow window) {
		boolean flag = true;

		if (window.getCodeField().getValueAsString() == null)
			flag = false;

		if (window.getNameField().getValueAsString() == null)
			flag = false;

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
					updateSubjectCategory2(window);
					window.show();
				} else {
					SC.say("INFO: please select record to update");
				}
			}
		});

	}


	private void loadFieldsToEdit(SubjectCategoryWindow window) {
		ListGridRecord record = getView().getSubCategoryPane().getListGrid().getSelectedRecord();

		window.getCodeField().setValue(record.getAttribute(SchoolClassListGrid.CODE));
		window.getNameField().setValue(record.getAttribute(SchoolClassListGrid.NAME));
	}

	///////////////////////////////////////// SUBJECT/////////////////////////////////////////////////////////////////////////////////

	private void addSubject(MenuButton menuButton) {
		menuButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				SubjectWindow window = new SubjectWindow();
				window.show();
				loadSubjectCategoryCombo2(window, null);
				saveSubject2(window);

			}
		});
	}

	private void loadSubjectCategoryCombo2(final SubjectWindow window, final String defaultValue) {
		ComboUtil2.loadSubjectCategoryCombo(window.getSubjectCategoryCombo(), dispatcher, placeManager, defaultValue);
	}

	

	public void clearSubjectWindowFields(SubjectWindow window) {
		window.getCodeField().clearValue();
		window.getNameField().clearValue();
		window.getSubjectCategoryCombo().clearValue();
	}

	private boolean checkIfNoSubjectWindowFieldIsEmpty(SubjectWindow window) {
		boolean flag = true;
		if (window.getSubjectCategoryCombo().getValueAsString() == null)
			flag = false;

		if (window.getNameField().getValueAsString() == null)
			flag = false;

		if (window.getCodeField().getValueAsString() == null)
			flag = false;

		return flag;
	}

	public void editSubject(MenuButton button) {
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (getView().getSubjectPane().getListGrid().anySelected()) {
					SubjectWindow window = new SubjectWindow();
					window.getSaveButton().setTitle("Update");
					loadFieldsToEdit2(window);
					updateSubject2(window);
					window.show();
				} else {
					SC.say("INFO: Select a record to update");
				}
			}
		});

	}

	
	private void loadFieldsToEdit2(SubjectWindow window) {
		ListGridRecord record = getView().getSubjectPane().getListGrid().getSelectedRecord();

		window.getSubjectCategoryCombo().setValue(record.getAttribute(SubjectListGrid.SUBJECT_CATEGORY));
		window.getCodeField().setValue(record.getAttribute(SubjectListGrid.CODE));
		window.getNameField().setValue(record.getAttribute(SubjectListGrid.NAME));

		loadSubjectCategoryCombo2(window, record.getAttribute(SubjectListGrid.SUBJECT_CATEGORY_ID));
	}

	


	// filter
	private void filterSubjectsSubjectCategory(final FilterSubjectWindow window) {
		window.getFilterSubjectsPane().getCategoryCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				String subjectCategoryId = window.getFilterSubjectsPane().getCategoryCombo().getValueAsString();
				FilterDTO dto = new FilterDTO();
				if(subjectCategoryId != null)
				dto.setSubjectCategoryDTO(new SubjectCategoryDTO(subjectCategoryId));

				map.put(MyRequestAction.DATA, dto);
				map.put(MyRequestAction.COMMAND, SubjectCategoryCommand.FILTER_SUBJECTS);
				window.close();

				subjectResponseList(map);
			}
		});

	}
	
	
	
	//////////////////////////////////////////////////////////////////NEW
	
	private void saveSubjectCategory2(final SubjectCategoryWindow window) {
		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				if (checkIfNoSubjectCategoryWindowFieldIsEmpty(window)) {
					SubjectCategoryDTO dto = new SubjectCategoryDTO();
					dto.setName(window.getNameField().getValueAsString());
					dto.setCode(window.getCodeField().getValueAsString());
					dto.setCreatedDateTime(dateTimeFormat.format(new Date()));

					LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					map.put(MyRequestAction.DATA, dto);
					map.put(MyRequestAction.COMMAND, SubjectCategoryCommand.SAVE_SUBJECT_CATEGORY);

					categoryResponseList(map);
				} else {
					SC.say("Please fill all fields");
				}

			}

		});
	}
	
	
	
	private void getAllSubjectCategories2() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(MyRequestAction.COMMAND, SubjectCategoryCommand.GET_ALL_SUBJECT_CATEGORYS);
		categoryResponseList(map);
	}
	
	private void categoryResponseList(LinkedHashMap<String, Object> map) {
		NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

			@Override
			public void onNetworkResult(MyRequestResult result) {
				if (result != null) {
					SystemResponseDTO<List<SubjectCategoryDTO>> responseDTO = result.getSubjectCategoryResponseList();
					if (responseDTO.isStatus()) {
						getView().getSubCategoryPane().getListGrid().addRecordsToGrid(responseDTO.getData());
					} else {
						SC.say(responseDTO.getMessage());
					}

				}

			}
		});
	}
	
	
	private void deleteSubjectCategory2(MenuButton button) {
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
								LinkedHashMap<String, Object> map = new LinkedHashMap<>();
								map.put(RequestDelimeters.SUBJECT_CATEGORY_ID, record.getAttributeAsString("id"));
								map.put(MyRequestAction.COMMAND, SubjectCategoryCommand.DELETE_SUBJECT_CATEGORY);

								NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

									@Override
									public void onNetworkResult(MyRequestResult result) {
										if (result != null) {
											SystemResponseDTO<String> responseDTO = result.getResponseText();
											if (responseDTO.isStatus()) {
												SC.say(responseDTO.getMessage());
												getAllSubjectCategories2();
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

	
	
	
	private void updateSubjectCategory2(final SubjectCategoryWindow window) {
		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				ListGridRecord record = getView().getSubCategoryPane().getListGrid().getSelectedRecord();

				SubjectCategoryDTO dto = new SubjectCategoryDTO();
				dto.setName(window.getNameField().getValueAsString());
				dto.setCode(window.getCodeField().getValueAsString());
				dto.setId(record.getAttribute(SubjectCategoryListGrid.ID));
				dto.setUpdatedDateTime(dateTimeFormat.format(new Date()));

				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(MyRequestAction.DATA, dto);
				map.put(MyRequestAction.COMMAND, SubjectCategoryCommand.UPDATE_SUBJECT_CATEGORY);
				
				categoryResponseList(map);
			}
		});

	}

	
	private void saveSubject2(final SubjectWindow window) {
		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				if (checkIfNoSubjectWindowFieldIsEmpty(window)) {
					SubjectDTO dto = new SubjectDTO();
					dto.setName(window.getNameField().getValueAsString());
					dto.setCode(window.getCodeField().getValueAsString());
					dto.setCreatedDateTime(dateTimeFormat.format(new Date()));
					dto.setSubjectCategoryDTO(new SubjectCategoryDTO(window.getSubjectCategoryCombo().getValueAsString()));
					
					GWT.log("DTO "+dto.getSubjectCategoryDTO().getId());

					LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					map.put(MyRequestAction.DATA, dto);
					map.put(MyRequestAction.COMMAND, SubjectCategoryCommand.SAVE_SUBJECT);
					subjectResponseList(map);
				} else {
					SC.warn("Please fill all fields");
				}

			}

		});
	}
	
	
	private void getAllSubjects2() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(MyRequestAction.COMMAND, SubjectCategoryCommand.FILTER_SUBJECTS);
		map.put(MyRequestAction.DATA, new FilterDTO());
		subjectResponseList(map);
	}
	
	//SET GRID
	private void subjectResponseList(LinkedHashMap<String, Object> map) {
		NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

			@Override
			public void onNetworkResult(MyRequestResult result) {
				if (result != null) {
					SystemResponseDTO<List<SubjectDTO>> responseDTO = result.getSubjectResponseList();
					if (responseDTO.isStatus()) {
						if(responseDTO.getData() != null)
						getView().getSubjectPane().getListGrid().addRecordsToGrid(responseDTO.getData());
					} else {
						SC.say(responseDTO.getMessage());
					}

				}
				
				
			}
		});
	}
	
	
	private void deleteSubject2(MenuButton button) {
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
								map.put(RequestDelimeters.SUBJECT_ID, record.getAttributeAsString("id"));
								map.put(MyRequestAction.COMMAND, SubjectCategoryCommand.DELETE_SUBJECT);

								NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

									@Override
									public void onNetworkResult(MyRequestResult result) {
										if (result != null) {
											SystemResponseDTO<String> responseDTO = result.getResponseText();
											if (responseDTO.isStatus()) {
												SC.say("SUCCESS", responseDTO.getMessage());
												getAllSubjects2();
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
	
	
	
	private void updateSubject2(final SubjectWindow window) {
		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				ListGridRecord record = getView().getSubjectPane().getListGrid().getSelectedRecord();

				SubjectDTO dto = new SubjectDTO();
				dto.setId(record.getAttribute(SubjectListGrid.ID));
				dto.setName(window.getNameField().getValueAsString());
				dto.setCode(window.getCodeField().getValueAsString());
				dto.setUpdatedDateTime(dateTimeFormat.format(new Date()));

				SubjectCategoryDTO subjectCategory = new SubjectCategoryDTO();
				subjectCategory.setId(window.getSubjectCategoryCombo().getValueAsString());
				dto.setSubjectCategoryDTO(subjectCategory);


				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(MyRequestAction.DATA, dto);
				map.put(MyRequestAction.COMMAND, SubjectCategoryCommand.UPDATE_SUBJECT);

				subjectResponseList(map);
			}
		});
	}

	

}