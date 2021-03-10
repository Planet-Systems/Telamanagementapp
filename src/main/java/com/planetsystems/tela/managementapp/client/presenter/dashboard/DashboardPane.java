package com.planetsystems.tela.managementapp.client.presenter.dashboard;

import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.layout.VLayout;

public class DashboardPane extends VLayout{
 
	private IButton refreshButton;
	
	public DashboardPane() {
		super();
		VLayout buttonLayout = new VLayout();
		
		refreshButton = new IButton();
		refreshButton.setTitle("Refresh");
		buttonLayout.addMember(refreshButton);
		
		
		this.setMembers(buttonLayout);
		
	}

	public IButton getRefreshButton() {
		return refreshButton;
	}


	
	
}
