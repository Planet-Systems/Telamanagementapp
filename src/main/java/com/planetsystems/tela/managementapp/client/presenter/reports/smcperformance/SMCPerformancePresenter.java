package com.planetsystems.tela.managementapp.client.presenter.reports.smcperformance;

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
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.planetsystems.tela.managementapp.client.presenter.comboutils.ComboUtil;
import com.planetsystems.tela.managementapp.client.presenter.main.MainPresenter;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkDataUtil;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkResult;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.planetsystems.tela.managementapp.client.widget.MenuButton;
import com.planetsystems.tela.managementapp.shared.DatePattern;
import com.planetsystems.tela.managementapp.shared.RequestConstant;
import com.planetsystems.tela.managementapp.shared.RequestResult;
import com.planetsystems.tela.managementapp.shared.UtilityManager;
import com.planetsystems.tela.managementapp.shared.requestconstants.ReportsRequestConstant;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.planetsystems.tela.dto.AcademicTermDTO;
import com.planetsystems.tela.dto.FilterDTO;
import com.planetsystems.tela.dto.SchoolDTO;
import com.planetsystems.tela.managementapp.client.place.NameTokens;

public class SMCPerformancePresenter
		extends Presenter<SMCPerformancePresenter.MyView, SMCPerformancePresenter.MyProxy> {

	@Inject
	PlaceManager placeManager;

	@Inject
	private DispatchAsync dispatcher;

	DateTimeFormat dateTimeFormat = DateTimeFormat
			.getFormat(DatePattern.DAY_MONTH_YEAR_HOUR_MINUTE_SECONDS.getPattern());
	DateTimeFormat dateFormat = DateTimeFormat.getFormat(DatePattern.DAY_MONTH_YEAR.getPattern());

	SMCSupervisionReportPane smcSupervisionReportPane;

	interface MyView extends View {

		public ControlsPane getControlsPane();

		public VLayout getContentPane();
	}

	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_SMCPerformance = new Type<RevealContentHandler<?>>();

	@NameToken(NameTokens.smcperformance)
	@ProxyCodeSplit
	interface MyProxy extends ProxyPlace<SMCPerformancePresenter> {
	}

	@Inject
	SMCPerformancePresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy, MainPresenter.SLOT_Main);

		smcSupervisionReportPane = new SMCSupervisionReportPane();

	}

	protected void onBind() {
		super.onBind();
		loadMenuButtons();
		getView().getContentPane().setMembers(smcSupervisionReportPane);
	}

	final FilterDTO smcSupervisionDTO = new FilterDTO();

	private void loadMenuButtons() {
		MenuButton filter = new MenuButton("View");
		MenuButton refresh = new MenuButton("Refresh");
		MenuButton export = new MenuButton("Export");

		List<MenuButton> buttons = new ArrayList<>();
		buttons.add(filter);
		buttons.add(refresh);
		buttons.add(export);

		getView().getControlsPane().addMenuButtons("SMC Supervision Summary", buttons);

		showFilter(filter);
		loadFilter();

		refreshData(refresh);
		exportSMCSupervisionReport(export);

	}

	private void showFilter(final MenuButton button) {
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				final Menu menu = new Menu();

				MenuItem dashboard = new MenuItem("Dashboard");
				MenuItem supervision = new MenuItem("SMC Supervision Summary");

				menu.setItems(dashboard, supervision);

				menu.showNextTo(button, "bottom");

				supervision.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {

						loadFilter();

					}
				});

			}
		});
	}

	private void loadFilter() {

		final FilterSupervisionSummaryWindow window = new FilterSupervisionSummaryWindow();

		final String defaultValue = null;
		ComboUtil.loadAcademicYearCombo(window.getAcademicYearCombo(), dispatcher, placeManager, defaultValue);
		window.getAcademicYearCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				ComboUtil.loadAcademicTermComboByAcademicYear(window.getAcademicYearCombo(),
						window.getAcademicTermCombo(), dispatcher, placeManager, defaultValue);
			}
		});

		ComboUtil.loadRegionCombo(window.getRegionCombo(), dispatcher, placeManager, defaultValue);
		window.getRegionCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				ComboUtil.loadDistrictComboByRegion(window.getRegionCombo(), window.getDistrictCombo(), dispatcher,
						placeManager, defaultValue);
			}
		});

		window.getDistrictCombo().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				ComboUtil.loadSchoolComboByDistrict(window.getDistrictCombo(), window.getSchoolCombo(), dispatcher,
						placeManager, defaultValue);
			}
		});

		window.getFetchButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				SchoolDTO schoolDTO = new SchoolDTO(window.getSchoolCombo().getValueAsString());
				AcademicTermDTO academicTermDTO = new AcademicTermDTO(window.getAcademicTermCombo().getValueAsString());
				String fromDate = dateFormat.format(window.getSupervisionDateItem().getValueAsDate());

				smcSupervisionDTO.setAcademicTermDTO(academicTermDTO);

				smcSupervisionDTO.setSchoolDTO(schoolDTO);
				smcSupervisionDTO.setFromDate(fromDate);

				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(NetworkDataUtil.ACTION, ReportsRequestConstant.SMCSupervisionReport);
				map.put(ReportsRequestConstant.DATA, smcSupervisionDTO);

				NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

					@Override
					public void onNetworkResult(RequestResult result) {

						smcSupervisionReportPane.getListgrid()
								.addRecordsToGrid(result.getSmcSupervisionReportDTO().getMrow_inputs());

					}
				});

			}
		});

		window.show();

	}

	private void refreshData(final MenuButton refresh) {
		refresh.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(NetworkDataUtil.ACTION, ReportsRequestConstant.SMCSupervisionReport);
				map.put(ReportsRequestConstant.DATA, smcSupervisionDTO);

				NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

					@Override
					public void onNetworkResult(RequestResult result) {

						smcSupervisionReportPane.getListgrid()
								.addRecordsToGrid(result.getSmcSupervisionReportDTO().getMrow_inputs());

					}
				});

			}
		});

	}

	private void exportSMCSupervisionReport(final MenuButton export) {
		export.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(RequestConstant.SMCSupervidsionReportExport, smcSupervisionDTO);

				map.put(NetworkDataUtil.ACTION, RequestConstant.SMCSupervidsionReportExport);
				NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

					@Override
					public void onNetworkResult(RequestResult result) {

						UtilityManager.getInstance().preview(result.getSystemFeedbackDTO().getMessage(),
								"Preview Report");

					}
				});

			}
		});
	}

}