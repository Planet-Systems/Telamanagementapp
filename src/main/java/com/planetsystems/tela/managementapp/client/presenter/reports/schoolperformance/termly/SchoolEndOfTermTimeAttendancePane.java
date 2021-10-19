package com.planetsystems.tela.managementapp.client.presenter.reports.schoolperformance.termly;

import com.planetsystems.tela.managementapp.client.widget.PerformanceScale;
import com.smartgwt.client.widgets.layout.VLayout;

public class SchoolEndOfTermTimeAttendancePane extends VLayout {

	private SchoolEndOfTermTimeAttendanceListgrid listgrid;
	private PerformanceScale performanceScale;

	public SchoolEndOfTermTimeAttendancePane() {
		super();

		listgrid = new SchoolEndOfTermTimeAttendanceListgrid();
		performanceScale=new PerformanceScale();

		VLayout layout = new VLayout();
		layout.addMember(listgrid);
		layout.addMember(performanceScale);
		
		this.addMember(layout);
	}

	public SchoolEndOfTermTimeAttendanceListgrid getListgrid() {
		return listgrid;
	}

}
