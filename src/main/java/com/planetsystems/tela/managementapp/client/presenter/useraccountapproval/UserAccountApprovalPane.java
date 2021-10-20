package com.planetsystems.tela.managementapp.client.presenter.useraccountapproval;
 
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.VLayout;

public class UserAccountApprovalPane extends VLayout {

	private UserAccountApprovalListgrid listgrid;

	public UserAccountApprovalPane() {
		super();
		Label header = new Label();

		header.setStyleName("crm-ContextArea-Header-Label");
		header.setContents("Users");
		header.setWidth("100%");
		header.setAutoHeight();
		header.setMargin(10);
		header.setAlign(Alignment.LEFT);

		listgrid = new UserAccountApprovalListgrid();

		VLayout layout = new VLayout();
		layout.addMember(listgrid);
		this.addMember(layout);

	}

	public UserAccountApprovalListgrid getListgrid() {
		return listgrid;
	}

}
