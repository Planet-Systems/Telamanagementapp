package com.planetsystems.tela.managementapp.client.presenter.academicyear;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.VLayout;

public class AcademicYearDashboardPane extends VLayout {

	
	public AcademicYearDashboardPane() {
		super();

		
		Label header = new Label();
		
		header.setStyleName("crm-ContextArea-Header-Label");
		header.setContents("Academic Year $ Term Graphs");
		header.setWidth("100%");
		header.setAutoHeight();
		header.setMargin(10);
		header.setAlign(Alignment.LEFT);
		
		

		VLayout layout = new VLayout();
		layout.addMember(header);
		
		this.addMember(layout);
		
	}

}
