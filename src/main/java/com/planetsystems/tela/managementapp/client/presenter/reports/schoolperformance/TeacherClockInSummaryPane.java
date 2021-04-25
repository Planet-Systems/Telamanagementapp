package com.planetsystems.tela.managementapp.client.presenter.reports.schoolperformance;

import com.planetsystems.tela.managementapp.client.widget.PerformanceScale;
import com.smartgwt.client.widgets.layout.VLayout;

public class TeacherClockInSummaryPane extends VLayout {
	private TeacherClockInSummaryListgrid listgrid;
	private PerformanceScale performanceScale;

	public TeacherClockInSummaryPane() {
		super();

		listgrid = new TeacherClockInSummaryListgrid();
		performanceScale=new PerformanceScale();

		VLayout layout = new VLayout();
		layout.addMember(listgrid);
		layout.addMember(performanceScale);
		this.addMember(layout);
	}

	public TeacherClockInSummaryListgrid getListgrid() {
		return listgrid;
	}
	
	

}
