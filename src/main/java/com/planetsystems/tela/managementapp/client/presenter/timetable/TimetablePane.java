package com.planetsystems.tela.managementapp.client.presenter.timetable;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.VLayout;

public class TimetablePane extends VLayout {
	private TimeTableListGrid timeTableListGrid;
	public TimetablePane() {
		super();
		Label header = new Label();
		
		header.setStyleName("crm-ContextArea-Header-Label");
		header.setContents("TimeTables");
		header.setWidth("100%");
		header.setAutoHeight();
		header.setMargin(10);
		header.setAlign(Alignment.LEFT);
		
		timeTableListGrid = new TimeTableListGrid();
		
	   
		VLayout layout = new VLayout();
		layout.addMember(header);
		layout.addMember(timeTableListGrid);
		this.addMember(layout);	
	}
	

	public TimeTableListGrid getTimeTableListGrid() {
		return timeTableListGrid;
	}
	
	
	
	

	
	
	
}
