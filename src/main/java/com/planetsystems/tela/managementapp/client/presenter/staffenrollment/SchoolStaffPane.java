/**
 * 
 */
package com.planetsystems.tela.managementapp.client.presenter.staffenrollment;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * @author wanfadger
 *
 */
public class SchoolStaffPane extends VLayout {
	private SchoolStaffListGrid staffListGrid;

	public SchoolStaffPane() {
		super();
		
	Label header = new Label();
		
		header.setStyleName("crm-ContextArea-Header-Label");
		header.setContents("Teachers");
		header.setWidth("100%");
		header.setAutoHeight();
		header.setMargin(10);
		header.setAlign(Alignment.LEFT);
     
		staffListGrid = new SchoolStaffListGrid();
		
		this.addMember(header);
		this.addMember(staffListGrid);
		
	}

	public SchoolStaffListGrid getStaffListGrid() {
		return staffListGrid;
	}
	
	
	
	

}
