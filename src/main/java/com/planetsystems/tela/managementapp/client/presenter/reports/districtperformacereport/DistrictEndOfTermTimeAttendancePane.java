package com.planetsystems.tela.managementapp.client.presenter.reports.districtperformacereport;

import com.planetsystems.tela.managementapp.client.widget.PerformanceScale;
import com.smartgwt.client.widgets.layout.VLayout;

public class DistrictEndOfTermTimeAttendancePane extends VLayout {

	private DistrictEndOfTermTimeAttendanceListgrid listgrid;
	private PerformanceScale performanceScale;

	public DistrictEndOfTermTimeAttendancePane() {
		super();

		listgrid = new DistrictEndOfTermTimeAttendanceListgrid();
		performanceScale=new PerformanceScale();

		VLayout layout = new VLayout();
		layout.addMember(listgrid);
		layout.addMember(performanceScale);
		this.addMember(layout);
	}

	public DistrictEndOfTermTimeAttendanceListgrid getListgrid() {
		return listgrid;
	}

}
