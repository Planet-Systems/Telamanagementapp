package com.planetsystems.tela.managementapp.client.presenter.reports.schoolperformance;

import com.planetsystems.tela.managementapp.client.widget.PerformanceScale;
import com.smartgwt.client.widgets.layout.VLayout;

public class SchoolEndOfWeekTimeAttendancePane extends VLayout {
	private SchoolEndOfWeekTimeAttendanceListgrid listgrid;
	private PerformanceScale performanceScale;

	public SchoolEndOfWeekTimeAttendancePane() {
		super();

		listgrid = new SchoolEndOfWeekTimeAttendanceListgrid();
		performanceScale=new PerformanceScale();

		VLayout layout = new VLayout();
		layout.addMember(listgrid);
		layout.addMember(performanceScale);
		
		this.addMember(layout);
	}

	public SchoolEndOfWeekTimeAttendanceListgrid getListgrid() {
		return listgrid;
	}
	
	

}
