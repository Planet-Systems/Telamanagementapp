package com.planetsystems.tela.managementapp.client.presenter.region;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.VLayout;

public class DistrictPane extends VLayout {

private DistrictListGrid listGrid;
	
	public DistrictPane() {
		super();
		Label header = new Label();
		
		header.setStyleName("crm-ContextArea-Header-Label");
		header.setContents("Districts");
		header.setWidth("100%");
		header.setAutoHeight();
		header.setMargin(10);
		header.setAlign(Alignment.LEFT);

		listGrid = new DistrictListGrid();

		VLayout layout = new VLayout();
		layout.addMember(header);
		layout.addMember(listGrid);
		this.addMember(layout);
		
	}

	public DistrictListGrid getListGrid() {
		return listGrid;
	}

	
}
