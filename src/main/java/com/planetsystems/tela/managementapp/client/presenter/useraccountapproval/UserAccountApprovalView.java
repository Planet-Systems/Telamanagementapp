package com.planetsystems.tela.managementapp.client.presenter.useraccountapproval;

import javax.inject.Inject;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.smartgwt.client.widgets.layout.VLayout;

class UserAccountApprovalView extends ViewImpl implements UserAccountApprovalPresenter.MyView {
	private static final String DEFAULT_MARGIN = "0px";

	private VLayout panel;
	private ControlsPane controlsPane;
	private UserAccountApprovalPane userAccountApprovalPane;

	@Inject
	UserAccountApprovalView() {
		panel = new VLayout();
		controlsPane = new ControlsPane();
		userAccountApprovalPane = new UserAccountApprovalPane();

		panel.addMember(controlsPane);
		panel.addMember(userAccountApprovalPane);

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

	public UserAccountApprovalPane getUserAccountApprovalPane() {
		return userAccountApprovalPane;
	}
	
	
}