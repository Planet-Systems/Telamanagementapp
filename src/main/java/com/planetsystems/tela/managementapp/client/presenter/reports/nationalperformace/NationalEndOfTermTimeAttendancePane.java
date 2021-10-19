package com.planetsystems.tela.managementapp.client.presenter.reports.nationalperformace;

import com.planetsystems.tela.managementapp.client.widget.PerformanceScale;
import com.smartgwt.client.widgets.layout.VLayout;

public class NationalEndOfTermTimeAttendancePane extends VLayout {

	private NationalEndOfTermTimeAttendanceListgrid listgrid;
	private PerformanceScale performanceScale;

	public NationalEndOfTermTimeAttendancePane() {
		super();

		listgrid = new NationalEndOfTermTimeAttendanceListgrid();
		performanceScale=new PerformanceScale();

		VLayout layout = new VLayout();
		layout.addMember(listgrid);
		layout.addMember(performanceScale);
		this.addMember(layout);
	}

	public NationalEndOfTermTimeAttendanceListgrid getListgrid() {
		return listgrid;
	}

}
