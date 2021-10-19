package com.planetsystems.tela.managementapp.client.presenter.timeattendancesupervision;

import com.planetsystems.tela.managementapp.client.presenter.filterpaneutils.FilterYearTermDistrictSchoolDate;
import com.planetsystems.tela.managementapp.client.presenter.staffattendance.clockout.ClockOutListGrid;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class ClockOutPane extends VLayout {
	
	private ClockOutListGrid clockOutListGrid;
	private FilterYearTermDistrictSchoolDate filterYearTermDistrictSchoolDate;
	private IButton loadButton;
	private IButton superviseButton;
	
	public ClockOutPane() {
		super();
		clockOutListGrid = new ClockOutListGrid();
		filterYearTermDistrictSchoolDate = new FilterYearTermDistrictSchoolDate();
		
		
		loadButton = new IButton("Load Clockin staff");
		loadButton.setLayoutAlign(Alignment.RIGHT);
		loadButton.setPadding(10);
		loadButton.disable();
		
		superviseButton = new IButton("Supervise Staff");
		superviseButton.setLayoutAlign(Alignment.RIGHT);
		superviseButton.setPadding(10);
		superviseButton.disable();
		
		HLayout buttonHLayout = new HLayout();
		buttonHLayout.setMembersMargin(10);
		
		buttonHLayout.setLayoutAlign(Alignment.RIGHT);
		buttonHLayout.setMembers(superviseButton , loadButton);
		buttonHLayout.setAutoHeight();
		buttonHLayout.setAutoWidth();
		buttonHLayout.setLayoutRightMargin(10);
	
		this.addMember(filterYearTermDistrictSchoolDate);
		this.addMember(buttonHLayout);
		this.addMember(clockOutListGrid);
		
	}

	public ClockOutListGrid getClockOutListGrid() {
		return clockOutListGrid;
	}

	public FilterYearTermDistrictSchoolDate getFilterYearTermDistrictSchoolDate() {
		return filterYearTermDistrictSchoolDate;
	}

	public IButton getLoadButton() {
		return loadButton;
	}

	public IButton getSuperviseButton() {
		return superviseButton;
	}

	 
	
}
