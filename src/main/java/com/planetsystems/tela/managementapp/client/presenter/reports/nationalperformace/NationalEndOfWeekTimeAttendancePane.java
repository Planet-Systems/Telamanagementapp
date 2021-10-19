package com.planetsystems.tela.managementapp.client.presenter.reports.nationalperformace;

import com.planetsystems.tela.managementapp.client.widget.PerformanceScale;
import com.smartgwt.client.widgets.layout.VLayout;

public class NationalEndOfWeekTimeAttendancePane extends VLayout {

	private NationalEndOfWeekTimeAttendanceListgrid listgrid;
	private PerformanceScale performanceScale;

	public NationalEndOfWeekTimeAttendancePane() {
		super();

		listgrid = new NationalEndOfWeekTimeAttendanceListgrid();
		performanceScale=new PerformanceScale();

		VLayout layout = new VLayout();
		layout.addMember(listgrid);
		layout.addMember(performanceScale);
		this.addMember(layout);
	}

	public NationalEndOfWeekTimeAttendanceListgrid getListgrid() {
		return listgrid;
	}

}
