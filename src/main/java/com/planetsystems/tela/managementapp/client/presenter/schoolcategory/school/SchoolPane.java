package com.planetsystems.tela.managementapp.client.presenter.schoolcategory.school;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.VLayout;

public class SchoolPane extends VLayout {

private SchoolListGrid listGrid;
	
	public SchoolPane() {
		super();
		Label header = new Label();
		
		header.setStyleName("crm-ContextArea-Header-Label");
		header.setContents("Schools");
		header.setWidth("100%");
		header.setAutoHeight();
		header.setMargin(10);
		header.setAlign(Alignment.LEFT);

		listGrid = new SchoolListGrid();

		VLayout layout = new VLayout();
		layout.addMember(header);
		layout.addMember(listGrid);
		this.addMember(layout);
		
	}

	public SchoolListGrid getListGrid() {
		return listGrid;
	}

	
}
