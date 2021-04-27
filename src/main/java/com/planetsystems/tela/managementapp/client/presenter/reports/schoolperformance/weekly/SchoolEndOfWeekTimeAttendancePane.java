package com.planetsystems.tela.managementapp.client.presenter.reports.schoolperformance;

import com.smartgwt.client.widgets.layout.VLayout;

public class SchoolEndOfWeekTimeAttendancePane extends VLayout {
	private SchoolEndOfWeekTimeAttendanceListgrid listgrid;

	public SchoolEndOfWeekTimeAttendancePane() {
		super();

		listgrid = new SchoolEndOfWeekTimeAttendanceListgrid();

		VLayout layout = new VLayout();
		layout.addMember(listgrid);
		this.addMember(layout);
	}

	public SchoolEndOfWeekTimeAttendanceListgrid getListgrid() {
		return listgrid;
	}
	
	

}
