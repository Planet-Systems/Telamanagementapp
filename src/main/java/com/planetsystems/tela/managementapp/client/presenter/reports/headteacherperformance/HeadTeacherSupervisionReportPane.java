package com.planetsystems.tela.managementapp.client.presenter.reports.headteacherperformance;

import com.planetsystems.tela.managementapp.client.widget.PerformanceScale;
import com.smartgwt.client.widgets.layout.VLayout;

public class HeadTeacherSupervisionReportPane extends VLayout {

	private HeadTeacherSupervisionReportListgrid listgrid;

	private PerformanceScale performanceScale;

	public HeadTeacherSupervisionReportPane() {
		super();

		listgrid = new HeadTeacherSupervisionReportListgrid();
		performanceScale = new PerformanceScale();

		VLayout layout = new VLayout();
		layout.addMember(listgrid);
		layout.addMember(performanceScale);
		this.addMember(layout);
	}

	public HeadTeacherSupervisionReportListgrid getListgrid() {
		return listgrid;
	}

	public PerformanceScale getPerformanceScale() {
		return performanceScale;
	}
	
	

}
