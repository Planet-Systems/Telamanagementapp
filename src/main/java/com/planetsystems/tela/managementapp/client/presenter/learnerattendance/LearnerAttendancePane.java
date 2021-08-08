package com.planetsystems.tela.managementapp.client.presenter.learnerattendance;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.VLayout;

public class LearnerAttendancePane extends VLayout {

	private LearnerAttendanceListGrid learnerAttendanceListGrid;

	public LearnerAttendancePane() {
		
	Label header = new Label();
		
		header.setStyleName("Staff");
		header.setContents("Learner Attendance");
		header.setWidth("100%");
		header.setAutoHeight();
		header.setMargin(10);
		header.setAlign(Alignment.LEFT);
     
		learnerAttendanceListGrid = new LearnerAttendanceListGrid();
		
		//this.addMember(header);
		this.addMember(learnerAttendanceListGrid);
		
	}

	public LearnerAttendanceListGrid getLearnerAttendanceListGrid() {
		return learnerAttendanceListGrid;
	}

	
	
	
}
