package com.planetsystems.tela.managementapp.client.presenter.systemuser;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.VLayout;

public class UserGroupPane extends VLayout {

	private UserGroupListgrid listgrid;

	public UserGroupPane() {

		super();
		Label header = new Label();
		header.setStyleName("crm-ContextArea-Header-Label");
		header.setContents("System user groups");
		header.setWidth("100%");
		header.setAutoHeight();
		header.setMargin(10);
		header.setAlign(Alignment.LEFT);

		listgrid = new UserGroupListgrid();

		VLayout layout = new VLayout();
		//layout.addMember(header);
		layout.addMember(listgrid);
		this.addMember(layout);

	}

	public UserGroupListgrid getListgrid() {
		return listgrid;
	}
	
	

}
