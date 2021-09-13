package com.planetsystems.tela.managementapp.client.presenter.smcsupervision;

import java.util.ArrayList;
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
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.planetsystems.tela.dto.AcademicTermDTO;
import com.planetsystems.tela.dto.AcademicYearDTO;
import com.planetsystems.tela.dto.DistrictDTO;
import com.planetsystems.tela.dto.FilterDTO;
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
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

public class SMCSupervisionPresenter
		extends Presenter<SMCSupervisionPresenter.MyView, SMCSupervisionPresenter.MyProxy> {

	@Inject
	private PlaceManager placeManager;

	@Inject
	private DispatchAsync dispatcher;
	
	
	DateTimeFormat dateTimeFormat = DateTimeFormat
			.getFormat(DatePattern.DAY_MONTH_YEAR_HOUR_MINUTE_SECONDS.getPattern());
	DateTimeFormat dateFormat = DateTimeFormat.getFormat(DatePattern.DAY_MONTH_YEAR.getPattern());

	interface MyView extends View {

		public SMCSupervisionPane getSmcSupervisionPane();

		public ControlsPane getControlsPane();
	}

	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_SMCSupervision = new Type<RevealContentHandler<?>>();

	@NameToken(NameTokens.smcsupervision)
	@ProxyCodeSplit
	interface MyProxy extends ProxyPlace<SMCSupervisionPresenter> {
	}

	@Inject
	SMCSupervisionPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy, MainPresenter.SLOT_Main);

	}

	protected void onBind() {
		super.onBind();
		setControlsPaneMenuButtons();
	}

	public void setControlsPaneMenuButtons() {
		MenuButton filter = new MenuButton("Filter");

		List<MenuButton> buttons = new ArrayList<>();
		buttons.add(filter);

		getView().getControlsPane().addMenuButtons("SMC Supervision Summary", buttons);
		loadAdvancedFilter(); 
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
				getView().getSmcSupervisionPane().getListGrid().setShowFilterEditor(true);
			}
		});

		advanced.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				
				loadAdvancedFilter();
			}
		});

	}

	private void loadAdvancedFilter() {
		FilterSMCSupervisionWindow window = new FilterSMCSupervisionWindow();
		loadFilterSMCSupervisionAcademicYearCombo(window);
		loadFilterSMCSupervisionAcademicTermCombo(window);
		loadFilterSMCSupervisionDistrictCombo(window);
		loadFilterSMCSupervisionSchoolCombo(window);
		filterSMCSupervisionByAcademicYearAcademicTermDistrictSchool(window);
		window.show();
	}

	//////////////////////// LOAD FILTER ATTENDANCE COMBOS

	// loads school combo in filter learner head count pane
	private void loadFilterSMCSupervisionSchoolCombo(final FilterSMCSupervisionWindow window) {
		window.getFilterSMCSupervision().getDistrictCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				ComboUtil.loadSchoolComboByDistrict(window.getFilterSMCSupervision().getDistrictCombo(),
						window.getFilterSMCSupervision().getSchoolCombo(), dispatcher, placeManager, null);
			}
		});

	}

	// loads district combo in filter learner head count pane
	private void loadFilterSMCSupervisionDistrictCombo(final FilterSMCSupervisionWindow window) {
		ComboUtil.loadDistrictCombo(window.getFilterSMCSupervision().getDistrictCombo(), dispatcher,
				placeManager, null);
	}

	// loads academic year combo in filter learner head count pane
	private void loadFilterSMCSupervisionAcademicYearCombo(final FilterSMCSupervisionWindow window) {
		ComboUtil.loadAcademicYearCombo(window.getFilterSMCSupervision().getAcademicYearCombo(), dispatcher,
				placeManager, null);
	}

	// loads academic year combo in filter learner head count pane
	private void loadFilterSMCSupervisionAcademicTermCombo(final FilterSMCSupervisionWindow window) {
		window.getFilterSMCSupervision().getAcademicYearCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {

				ComboUtil.loadAcademicTermComboByAcademicYear(
						window.getFilterSMCSupervision().getAcademicYearCombo(),
						window.getFilterSMCSupervision().getAcademicTermCombo(), dispatcher, placeManager, null);
			}
		}); 

	}

	////////////////////// END FILTER ATTENDANCE COMBOS

	// filter
	private void filterSMCSupervisionByAcademicYearAcademicTermDistrictSchool(
			final FilterSMCSupervisionWindow window) {
		window.getFilterButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				
				
				String academicYearId = window.getFilterSMCSupervision().getAcademicYearCombo()
						.getValueAsString();
				
				String academicTermId = window.getFilterSMCSupervision().getAcademicTermCombo()
						.getValueAsString();
				
				String districtId = window.getFilterSMCSupervision().getDistrictCombo().getValueAsString();
				
				String schoolId = window.getFilterSMCSupervision().getSchoolCombo().getValueAsString();
				
				/*String date = dateFormat
						.format(window.getFilterSMCSupervision().getAttendanceDateItem().getValueAsDate());*/
				
				FilterDTO dto = new FilterDTO();
				dto.setAcademicYearDTO(new AcademicYearDTO(academicYearId));
				dto.setAcademicTermDTO(new AcademicTermDTO(academicTermId));
				dto.setDistrictDTO(new DistrictDTO(districtId));
				dto.setSchoolDTO(new SchoolDTO(schoolId));
				//dto.setDate(date);

				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(RequestDelimeters.FILTER_DATA, dto);

				map.put(NetworkDataUtil.ACTION,
						RequestConstant.FILTER_SMC_SUPERVISION_BY_ACADEMIC_YEAR_ACADEMIC_TERM_DISTRICT_SCHOOL);

				NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

					@Override
					public void onNetworkResult(RequestResult result) {
						
						
						window.close();
						getView().getSmcSupervisionPane().getListGrid()
								.addRecordsToGrid(result.getSmcSupervisionDTOs());
						
						
					}
				});
			}
		});
	}

}