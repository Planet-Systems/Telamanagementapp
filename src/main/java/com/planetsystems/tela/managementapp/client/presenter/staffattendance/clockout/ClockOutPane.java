package com.planetsystems.tela.managementapp.client.presenter.staffattendance.clockout;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.VLayout;

public class ClockOutPane extends VLayout {

private ClockOutListGrid clockOutListGrid;
	
	public ClockOutPane() {
		super();
		Label header = new Label();
		
		header.setStyleName("crm-ContextArea-Header-Label");
		header.setContents("ClockOut");
		header.setWidth("100%");
		header.setAutoHeight();
		header.setMargin(10);
		header.setAlign(Alignment.LEFT);

		clockOutListGrid = new ClockOutListGrid();

		VLayout layout = new VLayout();
		layout.addMember(header);
		layout.addMember(clockOutListGrid);
		this.addMember(layout);
		
	}

	public ClockOutListGrid getClockOutListGrid() {
		return clockOutListGrid;
	}

	
	
}
