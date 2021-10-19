package com.planetsystems.tela.managementapp.client.presenter.reports.nationalperformace;

import com.planetsystems.tela.managementapp.client.widget.PerformanceScale;
import com.smartgwt.client.widgets.layout.VLayout;

public class NationalEndOfMonthTimeAttendancePane extends VLayout {

	private NationalEndOfMonthTimeAttendanceListgrid listgrid;
	private PerformanceScale performanceScale;

	public NationalEndOfMonthTimeAttendancePane() {
		super();

		listgrid = new NationalEndOfMonthTimeAttendanceListgrid();
		performanceScale=new PerformanceScale();

		VLayout layout = new VLayout();
		layout.addMember(listgrid);
		layout.addMember(performanceScale);
		this.addMember(layout);
	}

	public NationalEndOfMonthTimeAttendanceListgrid getListgrid() {
		return listgrid;
	}

}
