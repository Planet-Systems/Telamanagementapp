package com.planetsystems.tela.managementapp.client.presenter.reports.schoolperformance;

import com.smartgwt.client.widgets.layout.VLayout;

public class TeacherClockInSummaryPane extends VLayout {
	private TeacherClockInSummaryListgrid listgrid;

	public TeacherClockInSummaryPane() {
		super();

		listgrid = new TeacherClockInSummaryListgrid();

		VLayout layout = new VLayout();
		layout.addMember(listgrid);
		this.addMember(layout);
	}

	public TeacherClockInSummaryListgrid getListgrid() {
		return listgrid;
	}
	
	

}
