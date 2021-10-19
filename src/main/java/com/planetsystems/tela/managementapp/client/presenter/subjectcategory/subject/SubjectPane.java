package com.planetsystems.tela.managementapp.client.presenter.subjectcategory.subject;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.VLayout;

public class SubjectPane extends VLayout {

private SubjectListGrid listGrid;
	
	public SubjectPane() {
		super();
		Label header = new Label();
		
		header.setStyleName("crm-ContextArea-Header-Label");
		header.setContents("Subjects");
		header.setWidth("100%");
		header.setAutoHeight();
		header.setMargin(10);
		header.setAlign(Alignment.LEFT);

		listGrid = new SubjectListGrid();

		VLayout layout = new VLayout();
		layout.addMember(header);
		layout.addMember(listGrid);
		this.addMember(layout);
		
	}

	public SubjectListGrid getListGrid() {
		return listGrid;
	}

	
}
