package com.planetsystems.tela.managementapp.client.presenter.reports.headteacherperformance;

import com.planetsystems.tela.managementapp.client.widget.PerformanceScale;
import com.smartgwt.client.widgets.layout.VLayout;

public class HTEndOfWeekTimeAttendancePane extends VLayout {
	private HTEndOfWeekTimeAttendanceListgrid listgrid;
	private PerformanceScale performanceScale;

	public HTEndOfWeekTimeAttendancePane() {
		super();

		listgrid = new HTEndOfWeekTimeAttendanceListgrid();
		performanceScale=new PerformanceScale();

		VLayout layout = new VLayout();
		layout.addMember(listgrid);
		layout.addMember(performanceScale);
		
		this.addMember(layout);
	}

	public HTEndOfWeekTimeAttendanceListgrid getListgrid() {
		return listgrid;
	}
	
	

}
