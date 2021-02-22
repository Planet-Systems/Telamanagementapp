package com.planetsystems.tela.managementapp.client.presenter.subjectcategory;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.VLayout;

public class SubCategoryPane extends VLayout {


private SubCategoryListGrid listGrid;
	
	public SubCategoryPane() {
		super();
		Label header = new Label();
		
		header.setStyleName("crm-ContextArea-Header-Label");
		header.setContents("Subject Categories");
		header.setWidth("100%");
		header.setAutoHeight();
		header.setMargin(10);
		header.setAlign(Alignment.LEFT);

		listGrid = new SubCategoryListGrid();

		VLayout layout = new VLayout();
		layout.addMember(header);
		layout.addMember(listGrid);
		this.addMember(layout);
		
	}

	public SubCategoryListGrid getListGrid() {
		return listGrid;
	}
}
