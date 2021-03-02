package com.planetsystems.tela.managementapp.client.presenter.learnerenrollment;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.VLayout;

public class LearnerEnrollementPane extends VLayout{

	private LearnerEnrollmentListGrid learnerEnrollmentListGrid;

	public LearnerEnrollementPane() {
		super();
	Label header = new Label();
		
		header.setStyleName("crm-ContextArea-Header-Label");
		header.setContents("Head Count");
		header.setWidth("100%");
		header.setAutoHeight();
		header.setMargin(10);
		header.setAlign(Alignment.LEFT);
		
		learnerEnrollmentListGrid = new LearnerEnrollmentListGrid();


		this.addMember(header);
		this.addMember(learnerEnrollmentListGrid);		
	}

	public LearnerEnrollmentListGrid getLearnerEnrollmentListGrid() {
		return learnerEnrollmentListGrid;
	}
	
	
	
	
}
