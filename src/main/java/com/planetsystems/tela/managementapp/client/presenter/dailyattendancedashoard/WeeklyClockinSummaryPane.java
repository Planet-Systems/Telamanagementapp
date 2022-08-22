package com.planetsystems.tela.managementapp.client.presenter.dailyattendancedashoard;

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class WeeklyClockinSummaryPane extends VLayout {

	private WeeklyClockinSummaryListgrid listgrid;
	
	public WeeklyClockinSummaryPane() {
		super();
		
		listgrid=new WeeklyClockinSummaryListgrid();
		
		
		HLayout buttonLayout = new HLayout();

		buttonLayout.setMembersMargin(5);

		this.addMember(buttonLayout);
		this.setOverflow(Overflow.AUTO);

	}

	public WeeklyClockinSummaryListgrid getListgrid() {
		return listgrid;
	}
	
	

}
