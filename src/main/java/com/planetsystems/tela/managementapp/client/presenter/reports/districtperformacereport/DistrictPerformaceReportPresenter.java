package com.planetsystems.tela.managementapp.client.presenter.reports.districtperformacereport;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
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
import com.planetsystems.tela.dto.dashboard.DashboardSummaryDTO;
import com.planetsystems.tela.dto.reports.DistrictReportFilterDTO;
import com.planetsystems.tela.managementapp.client.place.NameTokens;
import com.planetsystems.tela.managementapp.client.presenter.comboutils.ComboUtil;
import com.planetsystems.tela.managementapp.client.presenter.main.MainPresenter;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkDataUtil;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkResult;
import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.planetsystems.tela.managementapp.client.widget.MenuButton;
import com.planetsystems.tela.managementapp.shared.DatePattern;
import com.planetsystems.tela.managementapp.shared.RequestConstant;
import com.planetsystems.tela.managementapp.shared.RequestResult;
import com.planetsystems.tela.managementapp.shared.UtilityManager;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

public class DistrictPerformaceReportPresenter
		extends Presenter<DistrictPerformaceReportPresenter.MyView, DistrictPerformaceReportPresenter.MyProxy> {

	@Inject
	private DispatchAsync dispatcher;

	@Inject
	PlaceManager placeManager;

	DateTimeFormat dateFormat = DateTimeFormat.getFormat(DatePattern.DAY_MONTH_YEAR.getPattern());

	interface MyView extends View {

		public ControlsPane getControlsPane();

		public VLayout getContentPane();
	}

	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_DistrictPerformaceReport = new Type<RevealContentHandler<?>>();

	@NameToken(NameTokens.districtperformance)
	@ProxyCodeSplit
	interface MyProxy extends ProxyPlace<DistrictPerformaceReportPresenter> {
	}

	@Inject
	DistrictPerformaceReportPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy, MainPresenter.SLOT_Main);

	}

	protected void onBind() {
		super.onBind();
		loadMenuButtons();
		// loadDistrictDashboard();
	}

	private void loadMenuButtons() {
		MenuButton refresh = new MenuButton("Dashboard");
		MenuButton filter = new MenuButton("Filter");

		MenuButton export = new MenuButton("Export");

		List<MenuButton> buttons = new ArrayList<>();
		buttons.add(refresh);
		buttons.add(filter);
		buttons.add(export);

		final ComboBox selectFilter = new ComboBox();
		selectFilter.setTitle("District");
		ComboUtil.loadDistrictCombo(selectFilter, dispatcher, placeManager, "");

		getView().getControlsPane().addMenuButtons("District Performance", selectFilter, buttons);

		showFilter(filter);

		refresh.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				String id = selectFilter.getValueAsString();

				loadDistrictDashboard(id);
			}
		});

		selectFilter.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				String id = selectFilter.getValueAsString();
				if(id!=null&&id.length()!=0) {
					loadDistrictDashboard(id);
				}
 

			}
		});

	}

	private void loadDistrictDashboard(String id) {

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.GET_DISTRICT_SUMMARY_DASHBOARD, id);
		map.put(NetworkDataUtil.ACTION, RequestConstant.GET_DISTRICT_SUMMARY_DASHBOARD);

		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {

				//if (result != null) {

					//if (result.getDashboardSummaryDTO() != null) {
						
						DashboardSummaryDTO dto = result.getDashboardSummaryDTO();

						DistrictDashboard.getInstance().generateDashboard(getView().getContentPane(), dto);
						
					/*}else {
						
					}
*/
				//}

			}
		});

	}

	private void showFilter(final MenuButton button) {
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				final Menu menu = new Menu();

				MenuItem dashboard = new MenuItem("Dashboard");
				MenuItem item1 = new MenuItem("End of Week Teacher Time Attendance Report");
				MenuItem item2 = new MenuItem("End of Month Teacher Time Attendance Report");
				MenuItem item3 = new MenuItem("End of Term Teacher Time Attendance Report");

				//MenuItem item4 = new MenuItem("Number of Teachers Report");
				//MenuItem item5 = new MenuItem("End of Week Learner Attendance Report");
				//MenuItem item6 = new MenuItem("End of Month Learner Attendance Report");
				//MenuItem item7 = new MenuItem("End of Term Learner Attendance Report");
				//MenuItem item8 = new MenuItem("Learner Enrollment Report");

				//menu.setItems(dashboard, item1, item2, item3, item4, item5, item6, item7, item8);
				menu.setItems(dashboard, item1, item2, item3);

				menu.showNextTo(button, "bottom");

				item1.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {

						final ReportFilterWindow window = new ReportFilterWindow();
						loadAcademicYearCombo(window, null);
						loadAcademicTermCombo(window, null);
						loadDistrictCombo(window, null);

						window.getSaveButton().addClickHandler(new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {

								loadEndOfWeekTimeAttendance(window);

							}
						});

						window.show();

					}
				});

				item2.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {

						final ReportFilterWindow window = new ReportFilterWindow();
						// window.getToDate().hide();
						loadAcademicYearCombo(window, null);
						loadAcademicTermCombo(window, null);
						loadDistrictCombo(window, null);

						window.getToDate().hide();
						window.getFromDate().setTitle("Month");

						window.getSaveButton().addClickHandler(new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {

								loadEndOfMonthTimeAttendance(window);

							}
						});

						window.show();

					}
				});

				item3.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {

						final ReportFilterWindow window = new ReportFilterWindow();
						loadAcademicYearCombo(window, null);
						loadAcademicTermCombo(window, null);
						loadDistrictCombo(window, null);
						window.getToDate().hide();
						window.getFromDate().hide();

						window.getSaveButton().addClickHandler(new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {

								loadEndOfTermTimeAttendance(window);

							}
						});

						window.show();

					}
				});

			}
		});
	}

	private void loadAcademicYearCombo(final ReportFilterWindow window, final String defaultValue) {
		ComboUtil.loadAcademicYearCombo(window.getYear(), dispatcher, placeManager, defaultValue);
	}

	private void loadAcademicTermCombo(final ReportFilterWindow window, final String defaultValue) {
		window.getYear().addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				ComboUtil.loadAcademicTermComboByAcademicYear(window.getYear(), window.getPeriod(), dispatcher,
						placeManager, defaultValue);
			}
		});
	}

	private void loadDistrictCombo(final ReportFilterWindow window, final String defaultValue) {
		ComboUtil.loadDistrictCombo(window.getDistrict(), dispatcher, placeManager, defaultValue);
	}

	private void loadEndOfWeekTimeAttendance(final ReportFilterWindow window) {

		final DistrictReportFilterDTO dto = new DistrictReportFilterDTO();

		dto.setDistrict(window.getDistrict().getValueAsString());
		dto.setTerm(window.getPeriod().getValueAsString());
		dto.setFromDate(dateFormat.format(window.getFromDate().getValueAsDate()));
		dto.setToDate(dateFormat.format(window.getToDate().getValueAsDate()));

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.DistrictEndOfWeekTimeAttendance, dto);

		map.put(NetworkDataUtil.ACTION, RequestConstant.DistrictEndOfWeekTimeAttendance);

		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {

				DistrictEndOfWeekTimeAttendancePane pane = new DistrictEndOfWeekTimeAttendancePane();

				MenuButton filter = new MenuButton("Filter");
				MenuButton refresh = new MenuButton("Refresh");
				MenuButton export = new MenuButton("Export");

				List<MenuButton> buttons = new ArrayList<>();
				buttons.add(filter);
				buttons.add(refresh);
				buttons.add(export);

				getView().getControlsPane().addMenuButtons("District End of Week Time Attendance Report", buttons);

				showFilter(filter);

				PreviewEndOfWeekTimeAttendance(export, dto);
				pane.getListgrid().addRecordsToGrid(result.getDistrictEndOfWeekTimeAttendanceDTOs());

				getView().getContentPane().setMembers(pane);
			}
		});

	}

	private void loadEndOfMonthTimeAttendance(final ReportFilterWindow window) {

		final DistrictReportFilterDTO dto = new DistrictReportFilterDTO();

		dto.setDistrict(window.getDistrict().getValueAsString());
		dto.setTerm(window.getPeriod().getValueAsString());
		dto.setFromDate(dateFormat.format(window.getFromDate().getValueAsDate()));
		dto.setToDate(dateFormat.format(window.getToDate().getValueAsDate()));

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.DistrictEndOfMonthTimeAttendance, dto);

		map.put(NetworkDataUtil.ACTION, RequestConstant.DistrictEndOfMonthTimeAttendance);
		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {

				DistrictEndOfMonthTimeAttendancePane pane = new DistrictEndOfMonthTimeAttendancePane();
				MenuButton filter = new MenuButton("Filter");
				MenuButton refresh = new MenuButton("Refresh");
				MenuButton export = new MenuButton("Export");

				List<MenuButton> buttons = new ArrayList<>();
				buttons.add(filter);
				buttons.add(refresh);
				buttons.add(export);

				getView().getControlsPane().addMenuButtons("District End of Month Time Attendance Report", buttons);

				showFilter(filter);

				PreviewEndOfMonthTimeAttendance(export, dto);
				pane.getListgrid().addRecordsToGrid(result.getDistrictEndOfMonthTimeAttendanceDTOs());

				getView().getContentPane().setMembers(pane);

			}
		});

	}

	private void loadEndOfTermTimeAttendance(final ReportFilterWindow window) {

		final DistrictReportFilterDTO dto = new DistrictReportFilterDTO();

		dto.setDistrict(window.getDistrict().getValueAsString());
		dto.setTerm(window.getPeriod().getValueAsString());
		dto.setFromDate(dateFormat.format(window.getFromDate().getValueAsDate()));
		dto.setToDate(dateFormat.format(window.getToDate().getValueAsDate()));

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.DistrictEndOfTermTimeAttendance, dto);

		map.put(NetworkDataUtil.ACTION, RequestConstant.DistrictEndOfTermTimeAttendance);
		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {

				DistrictEndOfTermTimeAttendancePane pane = new DistrictEndOfTermTimeAttendancePane();

				MenuButton filter = new MenuButton("Filter");
				MenuButton refresh = new MenuButton("Refresh");
				MenuButton export = new MenuButton("Export");

				List<MenuButton> buttons = new ArrayList<>();
				buttons.add(filter);
				buttons.add(refresh);
				buttons.add(export);

				getView().getControlsPane().addMenuButtons("District End of Term Time Attendance Report", buttons);

				showFilter(filter);

				pane.getListgrid().addRecordsToGrid(result.getDistrictEndOfTermTimeAttendanceDTOs());

				PreviewEndOfTermTimeAttendance(export, dto);

				getView().getContentPane().setMembers(pane);
			}
		});

	}

	private void PreviewEndOfWeekTimeAttendance(MenuButton export, final DistrictReportFilterDTO dto) {

		export.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(RequestConstant.DistrictEndOfWeekTimeAttendanceReport, dto);

				map.put(NetworkDataUtil.ACTION, RequestConstant.DistrictEndOfWeekTimeAttendanceReport);
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

	private void PreviewEndOfMonthTimeAttendance(MenuButton export, final DistrictReportFilterDTO dto) {

		export.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(RequestConstant.DistrictEndOfMonthTimeAttendanceReport, dto);

				map.put(NetworkDataUtil.ACTION, RequestConstant.DistrictEndOfMonthTimeAttendanceReport);
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

	private void PreviewEndOfTermTimeAttendance(MenuButton export, final DistrictReportFilterDTO dto) {

		export.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(RequestConstant.DistrictEndOfTermTimeAttendanceReport, dto);

				map.put(NetworkDataUtil.ACTION, RequestConstant.DistrictEndOfTermTimeAttendanceReport);
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