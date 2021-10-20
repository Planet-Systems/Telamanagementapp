package com.planetsystems.tela.managementapp.client.presenter.useraccountrequest;

import javax.inject.Inject;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.smartgwt.client.widgets.layout.VLayout;

class UserAccountRequestView extends ViewImpl implements UserAccountRequestPresenter.MyView {

	private static final String DEFAULT_MARGIN = "0px";
	private VLayout panel;

	private SignupPane signupPane;
	private Header header;

	@Inject
	public UserAccountRequestView() {
		super();

		panel = new VLayout();
		header = new Header();
		signupPane = new SignupPane();

		panel.addMember(header);
		panel.addMember(signupPane);
		panel.setWidth100();
		panel.setHeight("99%");
		panel.setBackgroundColor("#f0f0f0");
		Window.enableScrolling(false);
		Window.setMargin(DEFAULT_MARGIN);

	}

	public Widget asWidget() {
		return panel;
	}

	@Override
	public void setInSlot(Object slot, IsWidget content) {
		if (slot == UserAccountRequestPresenter.SLOT_UserAccountRequest) {
			panel.setMembers((VLayout) content.asWidget());
		} else {
			super.setInSlot(slot, content);
		}
	}

	public SignupPane getSignupPane() {
		return signupPane;
	}

	public Header getHeader() {
		return header;
	}
	
	

}