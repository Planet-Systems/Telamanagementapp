package com.planetsystems.tela.managementapp.client.presenter.loginaudit;

import javax.inject.Inject;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.smartgwt.client.widgets.layout.VLayout;

class LoginAuditView extends ViewImpl implements LoginAuditPresenter.MyView {
	private static final String DEFAULT_MARGIN = "0px";
	private VLayout panel;
	private LoginAuditPane loginAuditPane;
	private ControlsPane controlsPane;

	@Inject
	LoginAuditView() {
		panel = new VLayout();
		loginAuditPane = new LoginAuditPane();
		controlsPane = new ControlsPane();

		panel.addMember(controlsPane);
		panel.addMember(loginAuditPane);
		panel.setWidth100();
		panel.setHeight("90%");
		Window.enableScrolling(false);
		Window.setMargin(DEFAULT_MARGIN);

	}

	public Widget asWidget() {
		return panel;
	}

	public LoginAuditPane getLoginAuditPane() {
		return loginAuditPane;
	}

	public ControlsPane getControlsPane() {
		return controlsPane;
	}
	
	

}