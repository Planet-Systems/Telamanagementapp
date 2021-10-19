package com.planetsystems.tela.managementapp.client.presenter.smcsupervision;

import javax.inject.Inject;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.smartgwt.client.widgets.layout.VLayout;

class SMCSupervisionView extends ViewImpl implements SMCSupervisionPresenter.MyView {
	private static final String DEFAULT_MARGIN = "0px";
	private VLayout panel;
	private SMCSupervisionPane smcSupervisionPane;

	private ControlsPane controlsPane;

	@Inject
	SMCSupervisionView() {
		panel = new VLayout();
		smcSupervisionPane = new SMCSupervisionPane();
		controlsPane = new ControlsPane();

		panel.addMember(controlsPane);
		panel.addMember(smcSupervisionPane);
		panel.setWidth100();
		panel.setHeight("90%");
		Window.enableScrolling(false);
		Window.setMargin(DEFAULT_MARGIN);

	}

	public Widget asWidget() {
		return panel;
	}

	public SMCSupervisionPane getSmcSupervisionPane() {
		return smcSupervisionPane;
	}

	public ControlsPane getControlsPane() {
		return controlsPane;
	}

}