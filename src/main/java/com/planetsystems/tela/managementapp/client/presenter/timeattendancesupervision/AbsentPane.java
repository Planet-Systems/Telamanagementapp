package com.planetsystems.tela.managementapp.client.presenter.timeattendancesupervision;

import com.planetsystems.tela.managementapp.client.presenter.filterpaneutils.FilterYearTermDistrictSchoolDate;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class AbsentPane extends VLayout{
	private FilterYearTermDistrictSchoolDate filterYearTermDistrictSchoolDate;
	private IButton loadButton;
	private AbsentListGrid absentListGrid;
	private IButton superviseButton;

	public AbsentPane() {
		super();
		absentListGrid = new AbsentListGrid();
		filterYearTermDistrictSchoolDate = new FilterYearTermDistrictSchoolDate();
		
		
		loadButton = new IButton("Load Absent Staff");
		loadButton.setLayoutAlign(Alignment.RIGHT);
		loadButton.setPadding(10);
		loadButton.disable();
		
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
		this.addMember(absentListGrid);
		
	}

	public AbsentListGrid getAbsentListGrid() {
		return absentListGrid;
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
