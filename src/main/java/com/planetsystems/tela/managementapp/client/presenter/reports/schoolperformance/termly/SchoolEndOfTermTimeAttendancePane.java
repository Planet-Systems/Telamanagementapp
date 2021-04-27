package com.planetsystems.tela.managementapp.client.presenter.reports.schoolperformance.termly;

import com.smartgwt.client.widgets.layout.VLayout;

public class SchoolEndOfTermTimeAttendancePane extends VLayout {

	private SchoolEndOfTermTimeAttendanceListgrid listgrid;

	public SchoolEndOfTermTimeAttendancePane() {
		super();

		listgrid = new SchoolEndOfTermTimeAttendanceListgrid();

		VLayout layout = new VLayout();
		layout.addMember(listgrid);
		this.addMember(layout);
	}

	public SchoolEndOfTermTimeAttendanceListgrid getListgrid() {
		return listgrid;
	}

}
