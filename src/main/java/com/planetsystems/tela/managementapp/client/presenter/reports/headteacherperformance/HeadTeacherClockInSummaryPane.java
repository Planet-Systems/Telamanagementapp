package com.planetsystems.tela.managementapp.client.presenter.reports.headteacherperformance;

import com.planetsystems.tela.managementapp.client.widget.PerformanceScale;
import com.smartgwt.client.widgets.layout.VLayout;

public class HeadTeacherClockInSummaryPane extends VLayout {
	
	private HeadTeacherClockInSummaryListgrid listgrid;
	
	private PerformanceScale performanceScale;

	public HeadTeacherClockInSummaryPane() {
		super();

		listgrid = new HeadTeacherClockInSummaryListgrid();
		performanceScale=new PerformanceScale();

		VLayout layout = new VLayout();
		layout.addMember(listgrid);
		layout.addMember(performanceScale);
		this.addMember(layout);
	}

	public HeadTeacherClockInSummaryListgrid getListgrid() {
		return listgrid;
	}
	
	

}
