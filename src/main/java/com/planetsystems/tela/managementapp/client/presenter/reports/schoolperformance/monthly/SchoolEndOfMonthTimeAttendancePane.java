package com.planetsystems.tela.managementapp.client.presenter.reports.schoolperformance.monthly;

import com.smartgwt.client.widgets.layout.VLayout;

public class SchoolEndOfMonthTimeAttendancePane extends VLayout {

	private SchoolEndOfMonthTimeAttendanceListgrid listgrid;

	public SchoolEndOfMonthTimeAttendancePane() {
		super();

		listgrid = new SchoolEndOfMonthTimeAttendanceListgrid();

		VLayout layout = new VLayout();
		layout.addMember(listgrid);
		this.addMember(layout);
	}

	public SchoolEndOfMonthTimeAttendanceListgrid getListgrid() {
		return listgrid;
	}

	 

}
