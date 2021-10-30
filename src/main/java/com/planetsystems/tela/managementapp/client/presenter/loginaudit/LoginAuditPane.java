package com.planetsystems.tela.managementapp.client.presenter.loginaudit;
 
import com.smartgwt.client.widgets.layout.VLayout;

public class LoginAuditPane extends VLayout {

	private LoginAuditListgrid listgrid;

	public LoginAuditPane() {
		super();

		listgrid = new LoginAuditListgrid();

		VLayout layout = new VLayout();
		layout.addMember(listgrid); 
		
		this.addMember(layout);

	}

	public LoginAuditListgrid getListgrid() {
		return listgrid;
	}

}
