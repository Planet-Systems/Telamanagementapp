package com.planetsystems.tela.managementapp.client.presenter.reports.nationalperformace;

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
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.planetsystems.tela.dto.reports.DistrictReportFilterDTO;
import com.planetsystems.tela.dto.reports.NationalReportFilterDTO;
import com.planetsystems.tela.managementapp.client.place.NameTokens;

public class NationalPerformacePresenter
		extends Presenter<NationalPerformacePresenter.MyView, NationalPerformacePresenter.MyProxy> {

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
	public static final Type<RevealContentHandler<?>> SLOT_NationalPerformace = new Type<RevealContentHandler<?>>();

	@NameToken(NameTokens.nationalperformace)
	@ProxyCodeSplit
	interface MyProxy extends ProxyPlace<NationalPerformacePresenter> {
	}

	@Inject
	NationalPerformacePresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy, MainPresenter.SLOT_Main);

	}

	protected void onBind() {
		super.onBind();
		loadMenuButtons();
	}

	private void loadMenuButtons() {
		MenuButton filter = new MenuButton("View");
		MenuButton refresh = new MenuButton("Refresh");
		MenuButton export = new MenuButton("Export");

		List<MenuButton> buttons = new ArrayList<>();
		buttons.add(filter);
		buttons.add(refresh);
		buttons.add(export);

		getView().getControlsPane().addMenuButtons("National Performance", buttons);

		showFilter(filter);

	}

	private void showFilter(final MenuButton button) {
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				final Menu menu = new Menu();

				MenuItem dashboard = new MenuItem("Dashboard");
				MenuItem item1 = new MenuItem("End of Week Time Attendance Reports");
				MenuItem item2 = new MenuItem("End of Month Time Attendance Reports");
				MenuItem item3 = new MenuItem("End of Term Time Attendance Reports");
				
				MenuItem item4 = new MenuItem("Number of Teachers Report"); 
				MenuItem item5 = new MenuItem("End of Week Learner Attendance Report");
				MenuItem item6 = new MenuItem("End of Month Learner Attendance Report");
				MenuItem item7 = new MenuItem("End of Term Learner Attendance Report");
				MenuItem item8 = new MenuItem("Learner Enrollment Report"); 

				menu.setItems(dashboard, item1, item2, item3,item4,item5,item6,item7,item8);

				menu.showNextTo(button, "bottom");

				item1.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {

						final ReportFilterWindow window = new ReportFilterWindow();
						loadAcademicYearCombo(window, null);
						loadAcademicTermCombo(window, null);

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
						loadAcademicYearCombo(window, null);
						loadAcademicTermCombo(window, null);

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

	private void loadEndOfWeekTimeAttendance(final ReportFilterWindow window) {

		final NationalReportFilterDTO dto = new NationalReportFilterDTO();

		dto.setTerm(window.getPeriod().getValueAsString());
		dto.setFromDate(dateFormat.format(window.getFromDate().getValueAsDate()));
		dto.setToDate(dateFormat.format(window.getToDate().getValueAsDate()));

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.NationalEndOfWeekTimeAttendance, dto);

		map.put(NetworkDataUtil.ACTION, RequestConstant.NationalEndOfWeekTimeAttendance);
		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {

				NationalEndOfWeekTimeAttendancePane pane = new NationalEndOfWeekTimeAttendancePane();

				MenuButton filter = new MenuButton("Filter");
				MenuButton refresh = new MenuButton("Refresh");
				MenuButton export = new MenuButton("Export");

				List<MenuButton> buttons = new ArrayList<>();
				buttons.add(filter);
				buttons.add(refresh);
				buttons.add(export);

				getView().getControlsPane().addMenuButtons("National End of Week Time Attendance Report", buttons);

				showFilter(filter);

				pane.getListgrid().addRecordsToGrid(result.getNationalEndOfWeekTimeAttendanceDTOs());

				PreviewEndOfWeekTimeAttendance(export, dto);

				getView().getContentPane().setMembers(pane);
			}
		});

	}

	private void loadEndOfMonthTimeAttendance(final ReportFilterWindow window) {

		final NationalReportFilterDTO dto = new NationalReportFilterDTO();

		dto.setTerm(window.getPeriod().getValueAsString());
		dto.setFromDate(dateFormat.format(window.getFromDate().getValueAsDate()));
		dto.setToDate(dateFormat.format(window.getToDate().getValueAsDate()));

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.NationalEndOfMonthTimeAttendance, dto);

		map.put(NetworkDataUtil.ACTION, RequestConstant.NationalEndOfMonthTimeAttendance);
		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {

				NationalEndOfMonthTimeAttendancePane pane = new NationalEndOfMonthTimeAttendancePane();

				MenuButton filter = new MenuButton("Filter");
				MenuButton refresh = new MenuButton("Refresh");
				MenuButton export = new MenuButton("Export");

				List<MenuButton> buttons = new ArrayList<>();
				buttons.add(filter);
				buttons.add(refresh);
				buttons.add(export);

				getView().getControlsPane().addMenuButtons("National End of Month Time Attendance Report", buttons);

				showFilter(filter);

				pane.getListgrid().addRecordsToGrid(result.getNationalEndOfMonthTimeAttendanceDTOs());

				PreviewEndOfMonthTimeAttendance(export, dto);

				getView().getContentPane().setMembers(pane);
			}
		});

	}

	private void loadEndOfTermTimeAttendance(final ReportFilterWindow window) {

		final NationalReportFilterDTO dto = new NationalReportFilterDTO();

		dto.setTerm(window.getPeriod().getValueAsString());
		dto.setFromDate(dateFormat.format(window.getFromDate().getValueAsDate()));
		dto.setToDate(dateFormat.format(window.getToDate().getValueAsDate()));

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.NationalEndOfTermTimeAttendance, dto);

		map.put(NetworkDataUtil.ACTION, RequestConstant.NationalEndOfTermTimeAttendance);
		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {

				NationalEndOfTermTimeAttendancePane pane = new NationalEndOfTermTimeAttendancePane();

				MenuButton filter = new MenuButton("Filter");
				MenuButton refresh = new MenuButton("Refresh");
				MenuButton export = new MenuButton("Export");

				List<MenuButton> buttons = new ArrayList<>();
				buttons.add(filter);
				buttons.add(refresh);
				buttons.add(export);

				getView().getControlsPane().addMenuButtons("National End of Term Time Attendance Report", buttons);

				showFilter(filter);

				pane.getListgrid().addRecordsToGrid(result.getNationalEndOfTermTimeAttendanceDTOs());

				PreviewEndOfTermTimeAttendance(export, dto);

				getView().getContentPane().setMembers(pane);
			}
		});

	}

	private void PreviewEndOfWeekTimeAttendance(MenuButton export, final NationalReportFilterDTO dto) {

		export.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(RequestConstant.NationalEndOfWeekTimeAttendanceReport, dto);

				map.put(NetworkDataUtil.ACTION, RequestConstant.NationalEndOfWeekTimeAttendanceReport);
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

	private void PreviewEndOfMonthTimeAttendance(MenuButton export, final NationalReportFilterDTO dto) {

		export.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(RequestConstant.NationalEndOfMonthTimeAttendanceReport, dto);

				map.put(NetworkDataUtil.ACTION, RequestConstant.NationalEndOfMonthTimeAttendanceReport);
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

	private void PreviewEndOfTermTimeAttendance(MenuButton export, final NationalReportFilterDTO dto) {

		export.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put(RequestConstant.NationalEndOfTermTimeAttendanceReport, dto);

				map.put(NetworkDataUtil.ACTION, RequestConstant.NationalEndOfTermTimeAttendanceReport);
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