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
import com.planetsystems.tela.dto.response.SystemResponseDTO;
import com.planetsystems.tela.managementapp.client.event.HighlightActiveLinkEvent;
import com.planetsystems.tela.managementapp.client.place.NameTokens;
import com.planetsystems.tela.managementapp.client.presenter.academicyear.term.AcademicTermListGrid;
import com.planetsystems.tela.managementapp.client.presenter.academicyear.term.AcademicTermPane;
import com.planetsystems.tela.managementapp.client.presenter.academicyear.term.AcademicTermWindow;
import com.planetsystems.tela.managementapp.client.presenter.academicyear.term.FilterAcademicTermWindow;
import com.planetsystems.tela.managementapp.client.presenter.academicyear.year.AcademicYearListGrid;
import com.planetsystems.tela.managementapp.client.presenter.academicyear.year.AcademicYearPane;
import com.planetsystems.tela.managementapp.client.presenter.academicyear.year.AcademicYearWindow;
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
import com.planetsystems.tela.managementapp.shared.requestcommands.AcademicYearTermCommand;
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
		getAllAcademicYears2();
		getAllAcademicTerms2();
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

					getView().getControlsPane().addMenuButtons("Academic Years", buttons);

					addAcademicYear(newAcademicYear);
					deleteAcademicYear2(deleteAcademicYearButton);
					editAcademicYear(editAcademicYearButton);

				} else if (selectedTab.equalsIgnoreCase(AcademicYearView.ACADEMIC_TERM_TAB_TITLE)) {

					MenuButton newButton = new MenuButton("New");

					MenuButton edit = new MenuButton("Edit");
					MenuButton delete = new MenuButton("Delete");
					MenuButton activate = new MenuButton("Activate");
					MenuButton deactivate = new MenuButton("Deactivate");
					MenuButton filter = new MenuButton("View");

					List<MenuButton> buttons = new ArrayList<>();
					buttons.add(newButton);
					buttons.add(edit);
					buttons.add(activate);
					buttons.add(deactivate);
					buttons.add(filter);
					getView().getControlsPane().addMenuButtons("Academic Terms", buttons);

					addAcademicTerm(newButton);
					editAcademicTerm(edit);
					deleteAcademicTerm2(delete);
					selectFilterOption(filter);
					activateAcademicTerm2(activate);
					deactivateAcademicTerm2(deactivate);

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
		ComboUtil2.loadAcademicYearCombo(window.getFilterAcademicTermsPane().getAcademicYearCombo(), dispatcher,
				placeManager, null);
	}

///////////////////////////////////////////////////////ACADEMIC YEAR/////////////////////////////////////////////////////////	

	private void addAcademicYear(MenuButton button) {
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				AcademicYearWindow window = new AcademicYearWindow();
				window.getSaveButton().setTitle("Close");
				saveAcademicYear2(window);
				window.show();
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
					updateAcademicYear2(window);
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

	

	private void clearAcademicYearWindowFields(AcademicYearWindow window) {
		window.getYearCode().clearValue();
		window.getYearName().clearValue();
		window.getStartDate().clearValue();
		window.getEndDate().clearValue();
	}


	///////////////////////////////////////////////////////////// ACADEMIC
	///////////////////////////////////////////////////////////// TERM////////////////////////////////////////////////////////////////////

	
	private void addAcademicTerm(MenuButton button) {
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				AcademicTermWindow window = new AcademicTermWindow();
				ComboUtil2.loadAcademicYearCombo(window.getYearComboBox(), dispatcher, placeManager, null);
				saveAcademicTerm2(window);
				window.show();
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
					window.getSaveButton().setTitle("Close");
					window.getSaveButton().setTitle("Update");
					loadFieldsToEdit(window);
					updateAcademicTerm2(window);
					window.show();
				} else {
					SC.warn("ERROR", "Please select record to edit");
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

		ComboUtil2.loadAcademicYearCombo(window.getYearComboBox(), dispatcher, placeManager, record.getAttribute(AcademicTermListGrid.YEAR_ID));
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

	/////////////////////////// new
	public void saveAcademicYear2(final AcademicYearWindow window) {
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
					map.put(MyRequestAction.DATA, dto);
					map.put(MyRequestAction.COMMAND, AcademicYearTermCommand.SAVE_YEAR);

					
					NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

						@Override
						public void onNetworkResult(MyRequestResult result) {
							if (result != null) {
								SystemResponseDTO<AcademicYearDTO> responseDTO = result.getAcademicYearResponse();
								if (responseDTO.isStatus()) {
									//clearAcademicYearWindowFields(window);
									getAllAcademicYears2();
									
								} else {
									SC.say(responseDTO.getMessage());
								}
							}
							
						}
					});
				} else {
					SC.say("Fill all fields");
				}

			}

		});
	}

	public void getAllAcademicYears2() {

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(MyRequestAction.COMMAND, AcademicYearTermCommand.GET_ALL_YEARS);

		NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {
			@Override
			public void onNetworkResult(MyRequestResult result) {
				if (result != null) {
					SystemResponseDTO<List<AcademicYearDTO>> responseDTO = result.getAcademicYearResponseList();
					getView().getAcademicYearPane().getListGrid().addRecordsToGrid(responseDTO.getData());
				}

			}
		});
	}

	private void deleteAcademicYear2(MenuButton button) {
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
								map.put(MyRequestAction.COMMAND, AcademicYearTermCommand.DELETE_YEAR);

								NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

									@Override
									public void onNetworkResult(MyRequestResult result) {
										if (result != null) {
											SystemResponseDTO<String> responseDTO = result.getResponseText();
											SC.say("SUCCESS", responseDTO.getMessage());
											getAllAcademicYears2();
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

	private void updateAcademicYear2(final AcademicYearWindow window) {
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
				map.put(MyRequestAction.DATA, dto);
				map.put(MyRequestAction.COMMAND, AcademicYearTermCommand.UPDATE_YEAR);

				NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

					@Override
					public void onNetworkResult(MyRequestResult result) {
						if (result != null) {
							SystemResponseDTO<AcademicYearDTO> responseDTO = result.getAcademicYearResponse();
							if (responseDTO.isStatus()) {
								clearAcademicYearWindowFields(window);
								window.show();
								getAllAcademicYears2();
							} else {
								SC.say("INFO", responseDTO.getMessage());
							}
						}
					}
				});
			}
		});

	}

	//////////////// new term
	public void saveAcademicTerm2(final AcademicTermWindow window) {
		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				if (checkIfNoAcademicTermWindowFieldIsEmpty(window)) {
					AcademicTermDTO dto = new AcademicTermDTO();
					dto.setCode(window.getTermCodeField().getValueAsString());
					dto.setStartDate(dateFormat.format(window.getStartDateItem().getValueAsDate()));
					dto.setEndDate(dateFormat.format(window.getEndDateItem().getValueAsDate()));
					dto.setTerm(window.getTermNameField().getValueAsString());
					dto.setCreatedDateTime(dateTimeFormat.format(new Date()));

					AcademicYearDTO yearDto = new AcademicYearDTO();
					yearDto.setId(window.getYearComboBox().getValueAsString());

					dto.setAcademicYearDTO(yearDto);
					GWT.log("ID" + yearDto.getId());

					LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					map.put(MyRequestAction.DATA, dto);
					map.put(MyRequestAction.COMMAND, AcademicYearTermCommand.SAVE_TERM);

					NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

						@Override
						public void onNetworkResult(MyRequestResult result) {
							if (result != null) {
								SystemResponseDTO<AcademicTermDTO> responseDTO = result.getAcademicTermResponse();
								if (responseDTO.isStatus()) {
									//clearAcademicTermWindowFields(window);
									SC.say("INFO", responseDTO.getMessage());
									getAllAcademicTerms2();
								} else {
									SC.say("INFO", responseDTO.getMessage());
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

	public void getAllAcademicTerms2() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(MyRequestAction.COMMAND, AcademicYearTermCommand.GET_ALL_TERMS);

		NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {
			@Override
			public void onNetworkResult(MyRequestResult result) {
				if (result != null) {
					SystemResponseDTO<List<AcademicTermDTO>> responseDTO = result.getAcademicTermResponseList();
					if (responseDTO.isStatus()) {
						if (responseDTO.getData() != null) {
							getView().getAcademicTermPane().getListGrid().addRecordsToGrid(responseDTO.getData());
						}
					} else {
						SC.say(responseDTO.getMessage());
					}
				}

			}
		});
	}

	private void deleteAcademicTerm2(MenuButton button) {
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
								map.put(MyRequestAction.COMMAND, AcademicYearTermCommand.DELETE_TERM);

								NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

									@Override
									public void onNetworkResult(MyRequestResult result) {
										if (result != null) {
											SystemResponseDTO<String> responseDTO = result.getResponseText();
											if (responseDTO.isStatus()) {
												SC.say("INFO", responseDTO.getMessage());
											} else {
												SC.say("INFO", responseDTO.getMessage());
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

	public void updateAcademicTerm2(final AcademicTermWindow window) {
		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				ListGridRecord record = getView().getAcademicTermPane().getListGrid().getSelectedRecord();

				AcademicTermDTO dto = new AcademicTermDTO();

				dto.setId(record.getAttribute(AcademicTermListGrid.ID));
				dto.setCode(window.getTermCodeField().getValueAsString());
				dto.setStartDate(dateFormat.format(window.getStartDateItem().getValueAsDate()));
				dto.setEndDate(dateFormat.format(window.getEndDateItem().getValueAsDate()));
				dto.setTerm(window.getTermNameField().getValueAsString());
				dto.setUpdatedDateTime(dateTimeFormat.format(new Date()));

				AcademicYearDTO yearDTO = new AcademicYearDTO();
				yearDTO.setId(window.getYearComboBox().getValueAsString());

				dto.setAcademicYearDTO(yearDTO);
				GWT.log("ID" + yearDTO.getId());

				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(MyRequestAction.DATA, dto);
				map.put(MyRequestAction.COMMAND, AcademicYearTermCommand.UPDATE_TERM);
				NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

					@Override
					public void onNetworkResult(MyRequestResult result) {
						if (result != null) {
							SystemResponseDTO<AcademicTermDTO> responseDTO = result.getAcademicTermResponse();
							if(responseDTO.isStatus()) {
								window.close();
								clearAcademicTermWindowFields(window);
								SC.say("SUCCESS", responseDTO.getMessage());
								getAllAcademicTerms2();
							}else {
								SC.say("INFO", responseDTO.getMessage());
							}
							
						}
					}
				});
			}
		});
	}
	
	
	private void activateAcademicTerm2(MenuButton button) {
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (getView().getAcademicTermPane().getListGrid().anySelected()) {
					SC.ask("Confirm", "Are you sure you want to activate the selected term", new BooleanCallback() {

						@Override
						public void execute(Boolean value) {
							if (value) {
								ListGridRecord record = getView().getAcademicTermPane().getListGrid()
										.getSelectedRecord();
								LinkedHashMap<String, Object> map = new LinkedHashMap<>();
								map.put(RequestDelimeters.ACADEMIC_TERM_ID, record.getAttributeAsString("id"));
								map.put(MyRequestAction.COMMAND, AcademicYearTermCommand.ACTIVATE_TERM);

								NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

									@Override
									public void onNetworkResult(MyRequestResult result) {
										if(result != null) {
											SystemResponseDTO<AcademicTermDTO> responseDTO = result.getAcademicTermResponse();
											
											if(responseDTO.isStatus()) {
												SC.say("INFO", responseDTO.getMessage());
												getAllAcademicTerms2();
											}else {
												SC.say("INFO", responseDTO.getMessage());	
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
	
	
	
	private void deactivateAcademicTerm2(MenuButton button) {
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (getView().getAcademicTermPane().getListGrid().anySelected()) {
					SC.ask("Confirm", "Are you sure you want to deactivate the selected term", new BooleanCallback() {

						@Override
						public void execute(Boolean value) {
							if (value) {
								ListGridRecord record = getView().getAcademicTermPane().getListGrid()
										.getSelectedRecord();
								LinkedHashMap<String, Object> map = new LinkedHashMap<>();
								map.put(RequestDelimeters.ACADEMIC_TERM_ID, record.getAttributeAsString("id"));
								map.put(MyRequestAction.COMMAND, AcademicYearTermCommand.DEACTIVATE_TERM);

								NetworkDataUtil2.callNetwork2(dispatcher, placeManager, map, new NetworkResult2() {

									@Override
									public void onNetworkResult(MyRequestResult result) {
										if(result != null) {
											SystemResponseDTO<AcademicTermDTO> responseDTO = result.getAcademicTermResponse();
											
											if(responseDTO.isStatus()) {
												SC.say("SUCCESS", responseDTO.getMessage());
												getAllAcademicTerms2();
											}else {
												SC.say("INFO", responseDTO.getMessage());	
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
	
	
	public void loadFieldsToUpdate(final AcademicTermWindow window) {

		ListGridRecord record = getView().getAcademicTermPane().getListGrid().getSelectedRecord();

		window.getTermCodeField().setValue(record.getAttribute(AcademicTermListGrid.CODE));
		window.getTermNameField().setValue(record.getAttribute(AcademicTermListGrid.NAME));
		window.getStartDateItem().setValue(record.getAttribute(AcademicTermListGrid.START_DATE));
		window.getEndDateItem().setValue(record.getAttribute(AcademicTermListGrid.END_DATE));

		ComboUtil.loadAcademicYearCombo(window.getYearComboBox(), dispatcher, placeManager, record.getAttribute(AcademicTermListGrid.YEAR_ID));
	}

	
	
	

}
