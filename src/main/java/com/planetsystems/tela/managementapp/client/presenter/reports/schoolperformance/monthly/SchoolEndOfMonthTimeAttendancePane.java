package com.planetsystems.tela.managementapp.client.presenter.reports.schoolperformance.monthly;

import com.planetsystems.tela.managementapp.client.widget.PerformanceScale;
import com.smartgwt.client.widgets.layout.VLayout;

public class SchoolEndOfMonthTimeAttendancePane extends VLayout {

	private SchoolEndOfMonthTimeAttendanceListgrid listgrid;
	 
	private PerformanceScale performanceScale;

	public SchoolEndOfMonthTimeAttendancePane() {
		super();

		listgrid = new SchoolEndOfMonthTimeAttendanceListgrid();
		performanceScale=new PerformanceScale();

		VLayout layout = new VLayout();
		layout.addMember(listgrid);
		layout.addMember(performanceScale);
		this.addMember(layout);
	}

	public SchoolEndOfMonthTimeAttendanceListgrid getListgrid() {
		return listgrid;
	}

	 

}
