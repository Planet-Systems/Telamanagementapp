package com.planetsystems.tela.managementapp.client.presenter.reports.nationalperformace;

import javax.inject.Inject;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.smartgwt.client.widgets.layout.VLayout;

class NationalPerformaceView extends ViewImpl implements NationalPerformacePresenter.MyView {
	private static final String DEFAULT_MARGIN = "0px";
	private VLayout panel;

	private ControlsPane controlsPane;

	private VLayout contentPane;

	@Inject
	NationalPerformaceView() {
		panel = new VLayout();
		controlsPane = new ControlsPane();
		contentPane = new VLayout();

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