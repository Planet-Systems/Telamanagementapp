package com.planetsystems.tela.managementapp.client.presenter.systemuser;

import com.planetsystems.tela.managementapp.client.presenter.region.RegionListGrid;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.VLayout;

@SuppressWarnings("unused")
public class SystemUserPane extends VLayout {

private SystemUserListGrid systemUserListGrid;
	
	public SystemUserPane() {
		super();
		Label header = new Label();
		
		header.setStyleName("crm-ContextArea-Header-Label");
		header.setContents("Users");
		header.setWidth("100%");
		header.setAutoHeight();
		header.setMargin(10);
		header.setAlign(Alignment.LEFT);

		systemUserListGrid = new SystemUserListGrid();

		VLayout layout = new VLayout();
		//layout.addMember(header);
		layout.addMember(systemUserListGrid);
		this.addMember(layout);
		
	}

	public SystemUserListGrid getSystemUserListGrid() {
		return systemUserListGrid;
	}


	
	
}
