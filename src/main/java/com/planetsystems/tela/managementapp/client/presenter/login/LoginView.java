package com.planetsystems.tela.managementapp.client.presenter.login;

import javax.inject.Inject;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.layout.VLayout;

class LoginView extends ViewImpl implements LoginPresenter.MyView {

	private VLayout panel;
	private LoginPane loginPane;
	private static final String DEFAULT_MARGIN = "0px";

    @Inject
    LoginView( ) {
    	panel = new VLayout();
		loginPane = new LoginPane();
		
		
		VLayout centerLayout = new VLayout();
		centerLayout.setLayoutAlign(Alignment.CENTER);
		centerLayout.setWidth("50%");
		centerLayout.setHeight("70%");
		centerLayout.setMargin(50);
		centerLayout.addMember(loginPane);

		panel.addMember(centerLayout);
		panel.setWidth100();
		panel.setHeight("90%");
		Window.enableScrolling(false);
		Window.setMargin(DEFAULT_MARGIN);
    }
    
    
	public Widget asWidget() {
		return panel;
	}

	public LoginPane getLoginPane() {
		return loginPane;
	}
    
    
}