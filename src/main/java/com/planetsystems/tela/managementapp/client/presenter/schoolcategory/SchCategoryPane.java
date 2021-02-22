package com.planetsystems.tela.managementapp.client.presenter.schoolcategory;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.VLayout;

public class SchCategoryPane extends VLayout {

private SchCategoryListGrid listGrid;
	
	public SchCategoryPane() {
		super();
		Label header = new Label();
		
		header.setStyleName("crm-ContextArea-Header-Label");
		header.setContents("Categories");
		header.setWidth("100%");
		header.setAutoHeight();
		header.setMargin(10);
		header.setAlign(Alignment.LEFT);

		listGrid = new SchCategoryListGrid();

		VLayout layout = new VLayout();
		layout.addMember(header);
		layout.addMember(listGrid);
		this.addMember(layout);
		
	}

	public SchCategoryListGrid getListGrid() {
		return listGrid;
	}

	
}
