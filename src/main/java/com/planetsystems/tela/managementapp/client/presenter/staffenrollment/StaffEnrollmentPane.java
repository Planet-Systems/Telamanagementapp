package com.planetsystems.tela.managementapp.client.presenter.staffenrollment;

import com.planetsystems.tela.managementapp.client.presenter.staffenrollment.enrollment.StaffEnrollmentListGrid;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.VLayout;

public class StaffEnrollmentPane extends VLayout {

	private StaffEnrollmentListGrid staffEnrollmentListGrid;
	
	public StaffEnrollmentPane() {
		super();
		Label header = new Label();
		
		header.setStyleName("crm-ContextArea-Header-Label");
		header.setContents("Staff Head Count");
		header.setWidth("100%");
		header.setAutoHeight();
		header.setMargin(10);
		header.setAlign(Alignment.LEFT);

		staffEnrollmentListGrid = new StaffEnrollmentListGrid();

		VLayout layout = new VLayout();
		//layout.addMember(header);
		layout.addMember(staffEnrollmentListGrid);
		this.addMember(layout);
		
	}

	public StaffEnrollmentListGrid getStaffEnrollmentListGrid() {
		return staffEnrollmentListGrid;
	}


	


	

	
}
