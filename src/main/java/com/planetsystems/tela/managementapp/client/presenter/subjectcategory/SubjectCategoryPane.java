package com.planetsystems.tela.managementapp.client.presenter.subjectcategory;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.VLayout;

public class SubjectCategoryPane extends VLayout {


private SubjectCategoryListGrid listGrid;
	
	public SubjectCategoryPane() {
		super();
		Label header = new Label();
		
		header.setStyleName("crm-ContextArea-Header-Label");
		header.setContents("Subject Categories");
		header.setWidth("100%");
		header.setAutoHeight();
		header.setMargin(10);
		header.setAlign(Alignment.LEFT);

		listGrid = new SubjectCategoryListGrid();

		VLayout layout = new VLayout();
		layout.addMember(header);
		layout.addMember(listGrid);
		this.addMember(layout);
		
	}

	public SubjectCategoryListGrid getListGrid() {
		return listGrid;
	}
}
