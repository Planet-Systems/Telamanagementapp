package com.planetsystems.tela.managementapp.client.presenter.dailyattendancedashoard;

import com.smartgwt.client.types.Overflow; 
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DailyDashboardPane extends VLayout {

	public DailyDashboardPane() {
		super();
		HLayout buttonLayout = new HLayout();

		buttonLayout.setMembersMargin(5);

		this.addMember(buttonLayout);
		this.setOverflow(Overflow.AUTO);

	}

}
