package com.planetsystems.tela.managementapp.client.presenter.reports.districtperformacereport;

import com.planetsystems.tela.managementapp.client.widget.PerformanceScale;
import com.smartgwt.client.widgets.layout.VLayout;

public class DistrictEndOfMonthTimeAttendancePane extends VLayout {

	private DistrictEndOfMonthTimeAttendanceListgrid listgrid;
	private PerformanceScale performanceScale;

	public DistrictEndOfMonthTimeAttendancePane() {
		super();

		listgrid = new DistrictEndOfMonthTimeAttendanceListgrid();
		performanceScale=new PerformanceScale();

		VLayout layout = new VLayout();
		layout.addMember(listgrid);
		layout.addMember(performanceScale);
		this.addMember(layout);
	}

	public DistrictEndOfMonthTimeAttendanceListgrid getListgrid() {
		return listgrid;
	}

}
