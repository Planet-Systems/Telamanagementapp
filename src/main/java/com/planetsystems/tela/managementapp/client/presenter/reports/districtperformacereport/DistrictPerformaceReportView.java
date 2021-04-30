package com.planetsystems.tela.managementapp.client.presenter.reports.districtperformacereport;

import javax.inject.Inject;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.layout.VLayout;

class DistrictPerformaceReportView extends ViewImpl implements DistrictPerformaceReportPresenter.MyView {

	private static final String DEFAULT_MARGIN = "0px";
	private VLayout panel;

	private ControlsPane controlsPane;

	private VLayout contentPane;

	@Inject
	DistrictPerformaceReportView() {
		panel = new VLayout();
		controlsPane = new ControlsPane();
		contentPane = new VLayout();
		contentPane.setOverflow(Overflow.AUTO);

		panel.addMember(controlsPane);
		panel.addMember(contentPane);
		panel.setWidth100();
		panel.setHeight("90%");
		Window.enableScrolling(false);
		Window.setMargin(DEFAULT_MARGIN);
	}

	public Widget asWidget() {
		return panel;
	}

	public ControlsPane getControlsPane() {
		return controlsPane;
	}

	public VLayout getContentPane() {
		return contentPane;
	}
	
	

}