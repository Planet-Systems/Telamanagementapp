package com.planetsystems.tela.managementapp.client.presenter.timeattendancesupervision;

import com.planetsystems.tela.managementapp.client.presenter.filterpaneutils.FilterYearTermDistrictSchoolDate;
import com.planetsystems.tela.managementapp.client.presenter.staffattendance.clockin.ClockInListGrid;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class ClockInPane extends VLayout {
	private IButton loadButton;
	private IButton superviseButton;

	private ClockInListGrid clockInListGrid;
	private FilterYearTermDistrictSchoolDate filterYearTermDistrictSchoolDate;

	public ClockInPane() {
		super();
		clockInListGrid = new ClockInListGrid();
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

		VLayout layout = new VLayout();
		layout.addMember(filterYearTermDistrictSchoolDate);
		layout.addMember(buttonHLayout);
		layout.addMember(clockInListGrid);
		this.addMember(layout);

	}

	public IButton getLoadButton() {
		return loadButton;
	}


	public ClockInListGrid getClockInListGrid() {
		return clockInListGrid;
	}

	public FilterYearTermDistrictSchoolDate getFilterYearTermDistrictSchoolDate() {
		return filterYearTermDistrictSchoolDate;
	}

	public IButton getSuperviseButton() {
		return superviseButton;
	}
	
	

}
