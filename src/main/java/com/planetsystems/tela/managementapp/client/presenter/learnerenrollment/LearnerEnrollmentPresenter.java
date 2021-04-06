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
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
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
		getAllLearnerEnrollments();
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
				loadFilterLearnerHeadCountAcademicYearCombo(window);
				loadFilterLearnerHeadCountAcademicTermCombo(window);
				loadFilterLearnerHeadCountDistrictCombo(window);
				loadFilterLearnerHeadCountSchoolCombo(window);
				filterLearnerEnrollmentByAcademicYearAcademicTermDistrictSchool(window);
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
		ComboUtil.loadAcademicYearCombo(window.getAcademicYearCombo() , dispatcher, placeManager, defaultValue);
	}
	
	private void loadAcademicTermCombo(final LearnerEnrollmentWindow window, final String defaultValue) {
		window.getAcademicYearCombo().addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				ComboUtil.loadAcademicTermComboByAcademicYear(window.getAcademicYearCombo() , window.getAcademicTermCombo() , dispatcher, placeManager, defaultValue);	
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
				ComboUtil.loadSchoolComboByDistrict(window.getDistrictCombo() ,window.getSchoolCombo(), dispatcher, placeManager, defaultValue);	
			}
		});
		
	}
	private void loadSchoolClassCombo(final LearnerEnrollmentWindow window, final String defaultValue) {
		window.getSchoolCombo().addChangedHandler(new ChangedHandler() {
			
			@Override
			public void onChanged(ChangedEvent event) {
				ComboUtil.loadSchoolClassesComboBySchoolAcademicTerm(window.getAcademicTermCombo() , window.getSchoolCombo() , window.getSchoolClassCombo() , dispatcher, placeManager , defaultValue);		
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
		map.put(RequestConstant.GET_LEARNER_ENROLLMENT, null);
		map.put(NetworkDataUtil.ACTION, RequestConstant.GET_LEARNER_ENROLLMENT);

		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {
				getView().getLearnerEnrollementPane().getLearnerEnrollmentListGrid()
						.addRecordsToGrid(result.getLearnerEnrollmentDTOs());
			}
		});
	}

///////////////////////FILTER LEARNER HEADCOUNT COMBOS(4)

//loads school combo in filter learner head count pane
	private void loadFilterLearnerHeadCountSchoolCombo(final FilterLearnerHeadCountWindow window) {
		window.getFilterLearnerHeadCountPane().getDistrictCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {

				ComboUtil.loadSchoolComboByDistrict(window.getFilterLearnerHeadCountPane().getDistrictCombo(),
						window.getFilterLearnerHeadCountPane().getSchoolCombo(), dispatcher, placeManager, null);
			}
		});

	}

//loads district combo in filter learner head count pane	
	private void loadFilterLearnerHeadCountDistrictCombo(final FilterLearnerHeadCountWindow window) {
		ComboUtil.loadDistrictCombo(window.getFilterLearnerHeadCountPane().getDistrictCombo(), dispatcher, placeManager,
				null);
	}

//loads academic year combo in filter learner head count pane	
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

}