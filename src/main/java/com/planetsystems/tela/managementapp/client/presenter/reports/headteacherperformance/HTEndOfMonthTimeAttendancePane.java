package com.planetsystems.tela.managementapp.client.presenter.reports.headteacherperformance;

import com.planetsystems.tela.managementapp.client.widget.PerformanceScale;
import com.smartgwt.client.widgets.layout.VLayout;

public class HTEndOfMonthTimeAttendancePane extends VLayout {

	private HTEndOfMonthTimeAttendanceListgrid listgrid;
	 
	private PerformanceScale performanceScale;

	public HTEndOfMonthTimeAttendancePane() {
		super();

		listgrid = new HTEndOfMonthTimeAttendanceListgrid();
		performanceScale=new PerformanceScale();

		VLayout layout = new VLayout();
		layout.addMember(listgrid);
		layout.addMember(performanceScale);
		this.addMember(layout);
	}

	public HTEndOfMonthTimeAttendanceListgrid getListgrid() {
		return listgrid;
	}

	 

}
