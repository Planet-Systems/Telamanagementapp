package com.planetsystems.tela.managementapp.client.presenter.dailyattendancedashoard;

import javax.inject.Inject;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.planetsystems.tela.managementapp.client.presenter.dashboard.DashboardPane;
import com.planetsystems.tela.managementapp.client.presenter.dashboard.DashboardPresenter;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.smartgwt.client.widgets.layout.VLayout;

class DailyAttendanceDashoardView extends ViewImpl implements DailyAttendanceDashoardPresenter.MyView {
	private VLayout panel;
	private DailyDashboardPane dashboardPane;
	private static final String DEFAULT_MARGIN = "0px";

	private ControlsPane controlsPane;

	@Inject
	DailyAttendanceDashoardView() {
		panel = new VLayout();
		controlsPane = new ControlsPane();
		dashboardPane = new DailyDashboardPane();

		panel.addMember(controlsPane);
		panel.addMember(dashboardPane);

		panel.setWidth100();
		panel.setHeight100();
		Window.enableScrolling(false);
		Window.setMargin(DEFAULT_MARGIN);
	}

	@Override
	public void setInSlot(Object slot, IsWidget content) {
		if (slot == DailyAttendanceDashoardPresenter.SLOT_DailyAttendanceDashoard) {
			panel.setMembers((VLayout) content.asWidget());
		} else {
			super.setInSlot(slot, content);
		}
	}

	public Widget asWidget() {
		return panel;
	}

	
	public DailyDashboardPane getDashboardPane() {
		return dashboardPane;
	}

	public static String getDefaultMargin() {
		return DEFAULT_MARGIN;
	}

	public ControlsPane getControlsPane() {
		return controlsPane;
	}

}