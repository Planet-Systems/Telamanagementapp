package com.planetsystems.tela.managementapp.client.presenter.reports.schoolperformance;

import com.smartgwt.client.widgets.layout.VLayout;

public class SchoolTimeOnTaskSummaryPane extends VLayout {

	private SchoolTimeOnTaskSummaryListgrid listgrid;

	public SchoolTimeOnTaskSummaryPane() {
		super();

		listgrid = new SchoolTimeOnTaskSummaryListgrid();

		VLayout layout = new VLayout();
		layout.addMember(listgrid);
		this.addMember(layout);
	}

}
