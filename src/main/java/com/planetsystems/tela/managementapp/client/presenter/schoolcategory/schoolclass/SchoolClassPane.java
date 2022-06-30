package com.planetsystems.tela.managementapp.client.presenter.schoolcategory.schoolclass;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.VLayout;

public class SchoolClassPane extends VLayout {

private SchoolClassListGrid listGrid;
	
	public SchoolClassPane() {
		super();
		Label header = new Label();
		
		header.setStyleName("crm-ContextArea-Header-Label");
		header.setContents("Classes & Streams");
		header.setWidth("100%");
		header.setAutoHeight();
		header.setMargin(10);
		header.setAlign(Alignment.LEFT);

		listGrid = new SchoolClassListGrid();

		VLayout layout = new VLayout();
		layout.addMember(header);
		layout.addMember(listGrid);
		this.addMember(layout);
		
	}

	public SchoolClassListGrid getListGrid() {
		return listGrid;
	}

	
}
