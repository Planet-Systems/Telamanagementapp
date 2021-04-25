package com.planetsystems.tela.managementapp.client.presenter.reports.schoolperformance;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.planetsystems.tela.managementapp.client.presenter.dashboard.OverallAttendanceImpactDashboardGenerator;
import com.planetsystems.tela.managementapp.client.presenter.dashboard.OverallImpactDashboardGenerator;
import com.planetsystems.tela.managementapp.client.presenter.main.MainPresenter;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.planetsystems.tela.managementapp.client.widget.MenuButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.planetsystems.tela.managementapp.client.place.NameTokens;

public class SchoolPerformaceReportPresenter
		extends Presenter<SchoolPerformaceReportPresenter.MyView, SchoolPerformaceReportPresenter.MyProxy> {

	interface MyView extends View {

		public ControlsPane getControlsPane();

		public VLayout getContentPane();
	}

	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_SchoolPerformaceReport = new Type<RevealContentHandler<?>>();

	@NameToken(NameTokens.schoolperformance)
	@ProxyCodeSplit
	interface MyProxy extends ProxyPlace<SchoolPerformaceReportPresenter> {
	}

	@Inject
	SchoolPerformaceReportPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy, MainPresenter.SLOT_Main);

	}

	protected void onBind() {
		super.onBind();
		loadMenuButtons();
		loadTeacherClockInSummary();
	}

	private void loadMenuButtons() {
		MenuButton filter = new MenuButton("Filter");
		MenuButton refresh = new MenuButton("Refresh");
		MenuButton export = new MenuButton("Export");

		List<MenuButton> buttons = new ArrayList<>();
		buttons.add(filter);
		buttons.add(refresh);
		buttons.add(export);

		getView().getControlsPane().addMenuButtons("School Performance", buttons);

		showFilter(filter);

	}

	private void showFilter(final MenuButton button) {
		button.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				final Menu menu = new Menu();

				MenuItem dashboard = new MenuItem("Dashboard");
				MenuItem item1 = new MenuItem("Teacher Clockin/Clockout Summary");
				MenuItem item2 = new MenuItem("End of Week Time Attendance Reports");
				MenuItem item3 = new MenuItem("End of Month Time Attendance Reports");
				MenuItem item4 = new MenuItem("End of Term Time Attendance Reports");
				MenuItem item5 = new MenuItem("Time On Task Reports");

				menu.setItems(dashboard,item1, item2, item3, item4, item5);

				menu.showNextTo(button, "bottom");

				item1.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {

						loadTeacherClockInSummary();

					}
				});

				item2.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {

						loadSchoolEndOfWeekTimeAttendance();

					}
				});

				item3.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {

						loadSchoolEndOfMonthTimeAttendance();

					}
				});

				item4.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {

						SchoolEndOfTermTimeAttendance();

					}
				});

				item5.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

					@Override
					public void onClick(MenuItemClickEvent event) {

						loadSchoolTimeOnTaskSummary();

					}
				});

			}
		});
	}

	private void loadTeacherClockInSummary() {
		TeacherClockInSummaryPane pane = new TeacherClockInSummaryPane();

		MenuButton filter = new MenuButton("Filter");
		MenuButton refresh = new MenuButton("Refresh");
		MenuButton export = new MenuButton("Export");

		List<MenuButton> buttons = new ArrayList<>();
		buttons.add(filter);
		buttons.add(refresh);
		buttons.add(export);

		getView().getControlsPane().addMenuButtons("Teacher Clockin/Clockout Summary", buttons);

		showFilter(filter);

		getView().getContentPane().setMembers(pane);

	}

	private void loadSchoolEndOfWeekTimeAttendance() {
		SchoolEndOfWeekTimeAttendancePane pane = new SchoolEndOfWeekTimeAttendancePane();

		MenuButton filter = new MenuButton("Filter");
		MenuButton refresh = new MenuButton("Refresh");
		MenuButton export = new MenuButton("Export");

		List<MenuButton> buttons = new ArrayList<>();
		buttons.add(filter);
		buttons.add(refresh);
		buttons.add(export);

		getView().getControlsPane().addMenuButtons("School End of Week Time Attendance Report", buttons);

		showFilter(filter);
		getView().getContentPane().setMembers(pane);
	}

	private void loadSchoolEndOfMonthTimeAttendance() {
		SchoolEndOfMonthTimeAttendancePane pane = new SchoolEndOfMonthTimeAttendancePane();

		MenuButton filter = new MenuButton("Filter");
		MenuButton refresh = new MenuButton("Refresh");
		MenuButton export = new MenuButton("Export");

		List<MenuButton> buttons = new ArrayList<>();
		buttons.add(filter);
		buttons.add(refresh);
		buttons.add(export);

		getView().getControlsPane().addMenuButtons("School End of Month Time Attendance Report", buttons);

		showFilter(filter);
		getView().getContentPane().setMembers(pane);

	}

	private void SchoolEndOfTermTimeAttendance() {
		SchoolEndOfTermTimeAttendancePane pane = new SchoolEndOfTermTimeAttendancePane();

		MenuButton filter = new MenuButton("Filter");
		MenuButton refresh = new MenuButton("Refresh");
		MenuButton export = new MenuButton("Export");

		List<MenuButton> buttons = new ArrayList<>();
		buttons.add(filter);
		buttons.add(refresh);
		buttons.add(export);

		getView().getControlsPane().addMenuButtons("School End of Term Time Attendance Report", buttons);

		showFilter(filter);
		getView().getContentPane().setMembers(pane);
	}

	private void loadSchoolTimeOnTaskSummary() {
		SchoolTimeOnTaskSummaryPane pane = new SchoolTimeOnTaskSummaryPane();

		MenuButton filter = new MenuButton("Filter");
		MenuButton refresh = new MenuButton("Refresh");
		MenuButton export = new MenuButton("Export");

		List<MenuButton> buttons = new ArrayList<>();
		buttons.add(filter);
		buttons.add(refresh);
		buttons.add(export);

		getView().getControlsPane().addMenuButtons("School Time On Task Reports", buttons);

		showFilter(filter);
		getView().getContentPane().setMembers(pane);
	}

}