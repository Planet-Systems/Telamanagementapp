package com.planetsystems.tela.managementapp.client.presenter.academicyear;

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
import com.planetsystems.tela.managementapp.client.event.HighlightActiveLinkEvent;
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
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;

public class AcademicYearPresenter extends Presenter<AcademicYearPresenter.MyView, AcademicYearPresenter.MyProxy> {

	interface MyView extends View {

		public TabSet getTabSet();

		public ControlsPane getControlsPane();

		public AcademicYearPane getAcademicYearPane();

		public AcademicTermPane getAcademicTermPane();

		public VLayout getVlayout();

	}

	DateTimeFormat dateTimeFormat = DateTimeFormat
			.getFormat(DatePattern.DAY_MONTH_YEAR_HOUR_MINUTE_SECONDS.getPattern());
	DateTimeFormat dateFormat = DateTimeFormat.getFormat(DatePattern.DAY_MONTH_YEAR.getPattern());

	private DispatchAsync dispatcher;

	@Inject
	private PlaceManager placeManager;

	@SuppressWarnings("deprecation")
	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_AcademicYear = new Type<RevealContentHandler<?>>();

	@NameToken(NameTokens.assessmentperiod)
	@ProxyCodeSplit
	interface MyProxy extends ProxyPlace<AcademicYearPresenter> {
	}

	private EventBus eventBus;

	@Inject
	AcademicYearPresenter(EventBus eventBus, MyView view, MyProxy proxy, final DispatchAsync dispatcher) {
		super(eventBus, view, proxy, MainPresenter.SLOT_Main);
		this.eventBus = eventBus;
		this.dispatcher = dispatcher;
	}

	@Override
	protected void onBind() {
		super.onBind();
		onTabSelected();
		getAllAcademicYears();
		getAllAcademicTerms();
	}

	@Override
	protected void onReset() {
		super.onReset();
		HighlightActiveLinkEvent highlightActiveLinkEvent = new HighlightActiveLinkEvent(
				placeManager.getCurrentPlaceRequest().getNameToken());
		AcademicYearPresenter.this.eventBus.fireEvent(highlightActiveLinkEvent);
	}

	private void onTabSelected() {
		getView().getTabSet().addTabSelectedHandler(new TabSelectedHandler() {

			@Override
			public void onTabSelected(TabSelectedEvent event) {

				String selectedTab = event.getTab().getTitle();

				if (selectedTab.equalsIgnoreCase(AcademicYearView.ACADEMIC_YEAR_TAB_TITLE)) {
					getView().getAcademicTermPane().getListGrid().setShowFilterEditor(false);
					
					MenuButton editAcademicYearButton = new MenuButton("Edit");
					MenuButton deleteAcademicYearButton = new MenuButton("Delete");
					MenuButton newAcademicYear = new MenuButton("New");

					List<MenuButton> buttons = new ArrayList<>();
					buttons.add(newAcademicYear);
					buttons.add(editAcademicYearButton);
					buttons.add(deleteAcademicYearButton);

					getView().getControlsPane().addMenuButtons(buttons);

					addAcademicYear(newAcademicYear);
					deleteAcademicYear(deleteAcademicYearButton);
					editAcademicYear(editAcademicYearButton);

				} else if (selectedTab.equalsIgnoreCase(AcademicYearView.ACADEMIC_TERM_TAB_TITLE)) {

					MenuButton newButton = new MenuButton("New");

					MenuButton edit = new MenuButton("Edit");
					MenuButton delete = new MenuButton("Delete");
					MenuButton filter = new MenuButton("Filter");

					List<MenuButton> buttons = new ArrayList<>();
					buttons.add(newButton);
					buttons.add(edit);
					buttons.add(filter);
					getView().getControlsPane().addMenuButtons(buttons);

					addAcademicTerm(newButton);
					editAcademicTerm(edit);
					deleteAcademicTerm(delete);
					selectFilterOption(filter);

				} else {
					MenuButton newButton = new MenuButton("New");

					MenuButton edit = new MenuButton("Edit");
					MenuButton delete = new MenuButton("Delete");
					MenuButton filter = new MenuButton("Filter");

					List<MenuButton> buttons = new ArrayList<>();
					buttons.add(newButton);
					buttons.add(edit);
					buttons.add(filter);
					getView().getControlsPane().addMenuButtons(buttons);

				}

			}

		});
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
				getView().getAcademicTermPane().getListGrid().setShowFilterEditor(true);
			}
		});

		advanced.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
//   		SC.say("Advanced Search");
				FilterAcademicTermWindow window = new FilterAcademicTermWindow();
				loadFilterAcademicYearCombo(window);
				window.show();
				filterAcademicTermsByAcademicYear(window);
			}

		});

	}

	// filter window
	private void loadFilterAcademicYearCombo(final FilterAcademicTermWindow window) {
		ComboUtil.loadAcademicYearCombo(window.getFilterAcademicTermsPane().getAcademicYearCombo(), dispatcher,
				placeManager, null);
	}

///////////////////////////////////////////////////////ACADEMIC YEAR/////////////////////////////////////////////////////////	

	private void addAcademicYear(MenuButton button) {
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				AcademicYearWindow window = new AcademicYearWindow();
				saveAcademicYear(window);
				window.show();
			}
		});
	}

	public void saveAcademicYear(final AcademicYearWindow window) {
		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				AcademicYearDTO dto = new AcademicYearDTO();

				if (checkIfNoAcademicYearWindowFieldIsEmpty(window)) {
					dto.setName(window.getYearName().getValueAsString());
					dto.setCode(window.getYearCode().getValueAsString());
					dto.setStartDate(dateFormat.format(window.getStartDate().getValueAsDate()));
					dto.setEndDate(dateFormat.format(window.getEndDate().getValueAsDate()));
					dto.setCreatedDateTime(dateTimeFormat.format(new Date()));

					LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					map.put(RequestConstant.SAVE_ACADEMIC_YEAR, dto);
					map.put(NetworkDataUtil.ACTION, RequestConstant.SAVE_ACADEMIC_YEAR);

					NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

						@Override
						public void onNetworkResult(RequestResult result) {
							clearAcademicYearWindowFields(window);
							getAllAcademicYears();
						}
					});
				} else {
					SC.say("Fill all fields");
				}

			}

		});
	}

	private boolean checkIfNoAcademicYearWindowFieldIsEmpty(AcademicYearWindow window) {
		boolean flag = true;

		if (window.getYearName().getValueAsString() == null)
			flag = false;

		if (window.getYearCode().getValueAsString() == null)
			flag = false;

		if (window.getStartDate().getValueAsDate() == null)
			flag = false;

		if (window.getEndDate().getValueAsDate() == null)
			flag = false;

		return flag;
	}

	private void editAcademicYear(MenuButton button) {
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				if (getView().getAcademicYearPane().getListGrid().anySelected()) {
					AcademicYearWindow window = new AcademicYearWindow();
					window.getSaveButton().setTitle("Update");
					loadFieldsToEdit(window);
					updateAcademicYear(window);
					window.show();
				} else {
					SC.warn("Please check record to update");
				}

			}
		});

	}

	private void loadFieldsToEdit(AcademicYearWindow window) {
		ListGridRecord record = getView().getAcademicYearPane().getListGrid().getSelectedRecord();
		window.getYearCode().setValue(record.getAttribute(AcademicYearListGrid.CODE));
		window.getYearName().setValue(record.getAttribute(AcademicYearListGrid.NAME));
		window.getStartDate().setValue(record.getAttribute(AcademicYearListGrid.START_DATE));
		window.getEndDate().setValue(record.getAttribute(AcademicYearListGrid.END_DATE));

	}

	private void updateAcademicYear(final AcademicYearWindow window) {
		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				AcademicYearDTO dto = new AcademicYearDTO();
				ListGridRecord record = getView().getAcademicYearPane().getListGrid().getSelectedRecord();
				dto.setId(record.getAttribute(AcademicYearListGrid.ID));
				dto.setName(window.getYearName().getValueAsString());
				dto.setCode(window.getYearCode().getValueAsString());
				dto.setStartDate(dateFormat.format(window.getStartDate().getValueAsDate()));
				dto.setEndDate(dateFormat.format(window.getEndDate().getValueAsDate()));
				dto.setUpdatedDateTime(dateTimeFormat.format(new Date()));

				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(RequestConstant.UPDATE_ACADEMIC_YEAR, dto);
				map.put(NetworkDataUtil.ACTION, RequestConstant.UPDATE_ACADEMIC_YEAR);

				NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

					@Override
					public void onNetworkResult(RequestResult result) {
						clearAcademicYearWindowFields(window);
						window.show();
						SC.say("SUCCESS", result.getSystemFeedbackDTO().getMessage());
						getAllAcademicYears();
					}
				});
			}
		});

	}

	private void deleteAcademicYear(MenuButton button) {
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (getView().getAcademicYearPane().getListGrid().anySelected()) {
					SC.ask("Confirm", "Are you sure you want to delete the selected record", new BooleanCallback() {

						@Override
						public void execute(Boolean value) {
							if (value) {
								ListGridRecord record = getView().getAcademicYearPane().getListGrid()
										.getSelectedRecord();

								String id = record.getAttributeAsString(AcademicYearListGrid.ID);
								LinkedHashMap<String, Object> map = new LinkedHashMap<>();
								map.put(RequestDelimeters.ACADEMIC_YEAR_ID, id);
								map.put(NetworkDataUtil.ACTION, RequestConstant.DELETE_ACADEMIC_YEAR);
								NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

									@Override
									public void onNetworkResult(RequestResult result) {
										SC.say("SUCCESS", result.getSystemFeedbackDTO().getMessage());
										getAllAcademicYears();
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

	public void getAllAcademicYears() {

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.GET_ACADEMIC_YEAR, null);
		map.put(NetworkDataUtil.ACTION, RequestConstant.GET_ACADEMIC_YEAR);

		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {
			@Override
			public void onNetworkResult(RequestResult result) {
				getView().getAcademicYearPane().getListGrid().addRecordsToGrid(result.getAcademicYearDTOs());
			}
		});
	}

	private void clearAcademicYearWindowFields(AcademicYearWindow window) {
		window.getYearCode().clearValue();
		window.getYearName().clearValue();
		window.getStartDate().clearValue();
		window.getEndDate().clearValue();
	}

	// add academic term window
	public void loadAcademicYearCombo(final AcademicTermWindow window, final String defaultValue) {
		ComboUtil.loadAcademicYearCombo(window.getYearComboBox(), dispatcher, placeManager, defaultValue);
	}

	///////////////////////////////////////////////////////////// ACADEMIC
	///////////////////////////////////////////////////////////// TERM////////////////////////////////////////////////////////////////////

	private void addAcademicTerm(MenuButton button) {
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				AcademicTermWindow window = new AcademicTermWindow();
				loadAcademicYearCombo(window, null);
				saveAcademicTerm(window);
				window.show();
			}
		});
	}

	public void saveAcademicTerm(final AcademicTermWindow window) {
		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				if (checkIfNoAcademicTermWindowFieldIsEmpty(window)) {
					AcademicTermDTO academicTermDTO = new AcademicTermDTO();
					academicTermDTO.setCode(window.getTermCodeField().getValueAsString());
					academicTermDTO.setStartDate(dateFormat.format(window.getStartDateItem().getValueAsDate()));
					academicTermDTO.setEndDate(dateFormat.format(window.getEndDateItem().getValueAsDate()));
					academicTermDTO.setTerm(window.getTermNameField().getValueAsString());
					academicTermDTO.setCreatedDateTime(dateTimeFormat.format(new Date()));

					AcademicYearDTO academicYearDTO = new AcademicYearDTO();
					academicYearDTO.setId(window.getYearComboBox().getValueAsString());

					academicTermDTO.setAcademicYearDTO(academicYearDTO);
					GWT.log("ID" + academicYearDTO.getId());

					LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					map.put(RequestConstant.SAVE_ACADEMIC_TERM, academicTermDTO);
					map.put(NetworkDataUtil.ACTION, RequestConstant.SAVE_ACADEMIC_TERM);

					NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

						@Override
						public void onNetworkResult(RequestResult result) {
							clearAcademicTermWindowFields(window);
							SC.say("SUCCESS", result.getSystemFeedbackDTO().getMessage());
							getAllAcademicTerms();
						}
					});
				} else {
					SC.warn("Please fill all fields");
				}

			}
		});
	}

	protected boolean checkIfNoAcademicTermWindowFieldIsEmpty(AcademicTermWindow window) {
		boolean flag = true;

		if (window.getTermCodeField().getValueAsString() == null)
			flag = false;

		if (window.getTermNameField().getValueAsString() == null)
			flag = false;

		if (window.getStartDateItem().getValueAsDate() == null)
			flag = false;

		if (window.getEndDateItem().getValueAsDate() == null)
			flag = false;

		if (window.getYearComboBox().getValueAsString() == null)
			flag = false;

		return flag;
	}

	private void clearAcademicTermWindowFields(AcademicTermWindow window) {
		window.getTermCodeField().clearValue();
		window.getTermNameField().clearValue();
		window.getStartDateItem().clearValue();
		window.getEndDateItem().clearValue();
		window.getYearComboBox().clearValue();

	}

	public void editAcademicTerm(MenuButton button) {
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (getView().getAcademicTermPane().getListGrid().anySelected()) {
					AcademicTermWindow window = new AcademicTermWindow();
					window.getSaveButton().setTitle("Update");
					loadFieldsToEdit(window);
					updateAcademicTerm(window);
					window.show();
				} else {
					SC.warn("ERROR", "Please select record to edit");
				}

			}
		});
	}

	public void updateAcademicTerm(final AcademicTermWindow window) {
		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				ListGridRecord record = getView().getAcademicTermPane().getListGrid().getSelectedRecord();

				AcademicTermDTO academicTermDTO = new AcademicTermDTO();

				academicTermDTO.setId(record.getAttribute(AcademicTermListGrid.ID));
				academicTermDTO.setCode(window.getTermCodeField().getValueAsString());
				academicTermDTO.setStartDate(dateFormat.format(window.getStartDateItem().getValueAsDate()));
				academicTermDTO.setEndDate(dateFormat.format(window.getEndDateItem().getValueAsDate()));
				academicTermDTO.setTerm(window.getTermNameField().getValueAsString());
				academicTermDTO.setUpdatedDateTime(dateTimeFormat.format(new Date()));

				AcademicYearDTO academicYearDTO = new AcademicYearDTO();
				academicYearDTO.setId(window.getYearComboBox().getValueAsString());

				academicTermDTO.setAcademicYearDTO(academicYearDTO);
				GWT.log("ID" + academicYearDTO.getId());

				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(RequestConstant.UPDATE_ACADEMIC_TERM, academicTermDTO);
				map.put(NetworkDataUtil.ACTION, RequestConstant.UPDATE_ACADEMIC_TERM);
				NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

					@Override
					public void onNetworkResult(RequestResult result) {
						clearAcademicTermWindowFields(window);
						window.close();
						SC.say("SUCCESS", result.getSystemFeedbackDTO().getMessage());
						getAllAcademicTerms();
					}
				});
			}
		});
	}

	private void deleteAcademicTerm(MenuButton button) {
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (getView().getAcademicTermPane().getListGrid().anySelected()) {
					SC.ask("Confirm", "Are you sure you want to delete the selected record", new BooleanCallback() {

						@Override
						public void execute(Boolean value) {
							if (value) {
								ListGridRecord record = getView().getAcademicTermPane().getListGrid()
										.getSelectedRecord();
								LinkedHashMap<String, Object> map = new LinkedHashMap<>();
								map.put(RequestDelimeters.ACADEMIC_TERM_ID, record.getAttributeAsString("id"));
								map.put(NetworkDataUtil.ACTION, RequestConstant.DELETE_ACADEMIC_TERM);

								NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

									@Override
									public void onNetworkResult(RequestResult result) {
										SC.say("SUCCESS", result.getSystemFeedbackDTO().getMessage());
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

	public void loadFieldsToEdit(final AcademicTermWindow window) {

		ListGridRecord record = getView().getAcademicTermPane().getListGrid().getSelectedRecord();

		window.getTermCodeField().setValue(record.getAttribute(AcademicTermListGrid.CODE));
		window.getTermNameField().setValue(record.getAttribute(AcademicTermListGrid.NAME));
		window.getStartDateItem().setValue(record.getAttribute(AcademicTermListGrid.START_DATE));
		window.getEndDateItem().setValue(record.getAttribute(AcademicTermListGrid.END_DATE));

		loadAcademicYearCombo(window, record.getAttribute(AcademicTermListGrid.YEAR_ID));

	}

	public void getAllAcademicTerms() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.GET_ACADEMIC_TERM, null);
		map.put(NetworkDataUtil.ACTION, RequestConstant.GET_ACADEMIC_TERM);

		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {
			@Override
			public void onNetworkResult(RequestResult result) {
				getView().getAcademicTermPane().getListGrid().addRecordsToGrid(result.getAcademicTermDTOs());
			}
		});
	}

	public void filterAcademicTermsByAcademicYear(final FilterAcademicTermWindow window) {
		window.getFilterAcademicTermsPane().getAcademicYearCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				String id = window.getFilterAcademicTermsPane().getAcademicYearCombo().getValueAsString();
				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(RequestDelimeters.ACADEMIC_YEAR_ID, id);
				map.put(NetworkDataUtil.ACTION, RequestConstant.FILTER_ACADEMIC_TERMS_BY_ACADEMIC_YEAR);

				NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

					@Override
					public void onNetworkResult(RequestResult result) {
						window.close();
						getView().getAcademicTermPane().getListGrid().addRecordsToGrid(result.getAcademicTermDTOs());
					}
				});
			}
		});

	}

}
