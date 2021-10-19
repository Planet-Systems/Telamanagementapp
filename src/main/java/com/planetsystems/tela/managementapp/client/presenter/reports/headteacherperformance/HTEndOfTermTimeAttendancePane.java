package com.planetsystems.tela.managementapp.client.presenter.reports.headteacherperformance;

import com.planetsystems.tela.managementapp.client.widget.PerformanceScale;
import com.smartgwt.client.widgets.layout.VLayout;

public class HTEndOfTermTimeAttendancePane extends VLayout {

	private HTEndOfTermTimeAttendanceListgrid listgrid;
	private PerformanceScale performanceScale;

	public HTEndOfTermTimeAttendancePane() {
		super();

		listgrid = new HTEndOfTermTimeAttendanceListgrid();
		performanceScale=new PerformanceScale();

		VLayout layout = new VLayout();
		layout.addMember(listgrid);
		layout.addMember(performanceScale);
		
		this.addMember(layout);
	}

	public HTEndOfTermTimeAttendanceListgrid getListgrid() {
		return listgrid;
	}

}
