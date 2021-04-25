package com.planetsystems.tela.managementapp.client.presenter.dashboard;

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DashboardPane extends VLayout{
 
	private IButton refreshButton;
	private IButton importAttendaceButton;
	private IButton importTimeTablesButton;
	private IButton importSubjects;
	public DashboardPane() {
		super();
		HLayout buttonLayout = new HLayout();
		
		refreshButton = new IButton();
		refreshButton.setTitle("Import Initial Data");
		
		importAttendaceButton=new IButton();
		importAttendaceButton.setTitle("Import Attendace Data");
		
		importTimeTablesButton=new IButton();
		importTimeTablesButton.setTitle("Import Timetable Data");
		
		importSubjects=new IButton();
		importSubjects.setTitle("Import Subjects Data");
		
		
		buttonLayout.addMember(refreshButton);
		buttonLayout.addMember(importAttendaceButton);
		buttonLayout.addMember(importTimeTablesButton);
		buttonLayout.addMember(importSubjects);
		buttonLayout.setMembersMargin(5); 
		
		
		this.setMembers(buttonLayout);
		this.setOverflow(Overflow.AUTO);
		
	}

	public IButton getRefreshButton() {
		return refreshButton;
	}

	public IButton getImportAttendaceButton() {
		return importAttendaceButton;
	}

	public IButton getImportTimeTablesButton() {
		return importTimeTablesButton;
	}

	public IButton getImportSubjects() {
		return importSubjects;
	}


	
	
}
