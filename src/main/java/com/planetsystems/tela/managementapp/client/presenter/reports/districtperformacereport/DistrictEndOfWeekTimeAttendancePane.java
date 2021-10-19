package com.planetsystems.tela.managementapp.client.presenter.reports.districtperformacereport;

import com.planetsystems.tela.managementapp.client.widget.PerformanceScale;
import com.smartgwt.client.widgets.layout.VLayout;

public class DistrictEndOfWeekTimeAttendancePane extends VLayout {
	private DistrictEndOfWeekTimeAttendanceListgrid listgrid;
	private PerformanceScale performanceScale;

	public DistrictEndOfWeekTimeAttendancePane() {
		super();

		listgrid = new DistrictEndOfWeekTimeAttendanceListgrid();
		performanceScale=new PerformanceScale();

		VLayout layout = new VLayout();
		layout.addMember(listgrid);
		layout.addMember(performanceScale);
		this.addMember(layout);
	}

	public DistrictEndOfWeekTimeAttendanceListgrid getListgrid() {
		return listgrid;
	}

}
