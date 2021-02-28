package com.planetsystems.tela.managementapp.client.presenter.staffattendance;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.VLayout;

public class ClockInPane extends VLayout {

private ClockInListGrid clockInListGrid;
	
	public ClockInPane() {
		super();
		Label header = new Label();
		
		header.setStyleName("crm-ContextArea-Header-Label");
		header.setContents("ClockIn");
		header.setWidth("100%");
		header.setAutoHeight();
		header.setMargin(10);
		header.setAlign(Alignment.LEFT);

		clockInListGrid = new ClockInListGrid();

		VLayout layout = new VLayout();
		layout.addMember(header);
		layout.addMember(clockInListGrid);
		this.addMember(layout);
		
	}

	public ClockInListGrid getClockInListGrid() {
		return clockInListGrid;
	}

	
	
	
}
