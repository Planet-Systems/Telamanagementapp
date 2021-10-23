package com.planetsystems.tela.managementapp.client.presenter.dailyattendancedashoard;

import java.util.Date;
import java.util.LinkedHashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
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
import com.planetsystems.tela.managementapp.client.presenter.dashboard.DashboardPane;
import com.planetsystems.tela.managementapp.client.presenter.dashboard.OverallCountDashboardGenerator;
import com.planetsystems.tela.managementapp.client.presenter.main.MainPresenter;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.planetsystems.tela.managementapp.client.widget.MenuButton;
import com.planetsystems.tela.managementapp.client.widget.SwizimaLoader;
import com.planetsystems.tela.managementapp.shared.DatePattern;
import com.planetsystems.tela.managementapp.shared.RequestAction;
import com.planetsystems.tela.managementapp.shared.RequestConstant;
import com.planetsystems.tela.managementapp.shared.RequestResult;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.planetsystems.tela.dto.dashboard.AttendanceDashboardSummaryDTO;
import com.planetsystems.tela.managementapp.client.gin.SessionManager;
import com.planetsystems.tela.managementapp.client.place.NameTokens;

public class DailyAttendanceDashoardPresenter
		extends Presenter<DailyAttendanceDashoardPresenter.MyView, DailyAttendanceDashoardPresenter.MyProxy> {

	@Inject
	private DispatchAsync dispatcher;

	@Inject
	private PlaceManager placeManager;

	DateTimeFormat dateFormat = DateTimeFormat.getFormat(DatePattern.DAY_MONTH_YEAR.getPattern());

	interface MyView extends View {

		public DailyDashboardPane getDashboardPane();

		public ControlsPane getControlsPane();
	}

	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_DailyAttendanceDashoard = new Type<RevealContentHandler<?>>();

	@NameToken(NameTokens.dailyattendacedashboard)
	@ProxyCodeSplit
	interface MyProxy extends ProxyPlace<DailyAttendanceDashoardPresenter> {
	}

	@Inject
	DailyAttendanceDashoardPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy, MainPresenter.SLOT_Main);

	}

	protected void onBind() {
		super.onBind();
		loadMenuButtons();
	}

	private void loadMenuButtons() {

		MenuButton filterButton = new MenuButton("Filter");
		MenuButton refreshButton = new MenuButton("Refresh");
		getView().getControlsPane().addTitle("Dashboard: Daily Attendace National Overview");
		getView().getControlsPane().addMember(filterButton);
		getView().getControlsPane().addMember(refreshButton);

		refresh(refreshButton);
		filter(filterButton);

		String attendanceDate = dateFormat.format(new Date());
		loadDashboard(attendanceDate);

	}

	private void loadDashboard(String attendanceDate) {

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());
		map.put(RequestConstant.GET_OverallDailyAttendanceDashboard, attendanceDate);

		SC.showPrompt("", "", new SwizimaLoader());

		dispatcher.execute(new RequestAction(RequestConstant.GET_OverallDailyAttendanceDashboard, map),
				new AsyncCallback<RequestResult>() {

					@Override
					public void onFailure(Throwable caught) {
						System.out.println(caught.getMessage());
						SC.warn("ERROR", caught.getMessage());
						GWT.log("ERROR " + caught.getMessage());
						SC.clearPrompt();

					}

					@Override
					public void onSuccess(RequestResult result) {

						SC.clearPrompt();
						SessionManager.getInstance().manageSession(result, placeManager);
						if (result != null) {

							OverallDailyAttendanceDashboardGenerator.getInstance().generateDashboard(
									getView().getDashboardPane(),
									result.getOverallDailyAttendanceEnrollmentSummaryDTO());

						} else {
							SC.warn("ERROR", "Unknow error");
						}

					}
				});

	}

	private void refresh(final MenuButton refreshButton) {
		refreshButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String attendanceDate = dateFormat.format(new Date());
				loadDashboard(attendanceDate);

			}
		});
	}

	private void filter(MenuButton filterButton) {
		filterButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				final FilterWindow window = new FilterWindow();
				loadDataByDate(window);
				window.show();

			}
		});
	}

	private void loadDataByDate(final FilterWindow window) {
		window.getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				String attendanceDate = dateFormat.format(window.getAttendanceDate().getValueAsDate());
				loadDashboard(attendanceDate);
			}
		});
	}

}