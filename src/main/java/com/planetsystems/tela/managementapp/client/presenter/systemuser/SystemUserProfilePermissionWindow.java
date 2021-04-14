package com.planetsystems.tela.managementapp.client.presenter.systemuser;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

public class SystemUserProfilePermissionWindow extends Window {
	private IButton cancelButton;
	private IButton saveButton;
	private SystemUserSchoolPane systemUserSchoolPane;
	
	public SystemUserProfilePermissionWindow() {
		super();
		
		systemUserSchoolPane = new SystemUserSchoolPane();
		
		final TabSet tabSet = new TabSet();
		Tab tab1 = new Tab("Select User Schools");
		tab1.setPane(systemUserSchoolPane);
		
		Tab tab2 = new Tab("Other");
		
		tabSet.addTab(tab1);
		tabSet.addTab(tab2);
	    
		 cancelButton = new IButton("Close");
	        cancelButton.setLayoutAlign(Alignment.CENTER);
	        
	        saveButton = new IButton("Save");
	        saveButton.setLayoutAlign(Alignment.CENTER);
	        saveButton.disable();
		
		HLayout buttonLayout = new HLayout();
		buttonLayout.setMembers(cancelButton, saveButton);
		buttonLayout.setAutoHeight();
		buttonLayout.setAutoWidth();
		buttonLayout.setMargin(5);
		buttonLayout.setMembersMargin(4);
		buttonLayout.setLayoutAlign(Alignment.CENTER);

	        VLayout layout = new VLayout();
	        layout.setWidth100();
	        layout.setHeight100();
	        layout.addMembers(tabSet , buttonLayout);

	
		this.addItem(layout);
		this.setWidth("100%");
		this.setHeight("80%");
		this.setAutoCenter(true);
		this.setTitle("Create User");
		this.setIsModal(true);
		this.setShowModalMask(true);
		closeWindow(this);
	}
	
	private void closeWindow(final Window window) {
		cancelButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				window.close();
			}
		});
	}

	public IButton getCancelButton() {
		return cancelButton;
	}

	public void setCancelButton(IButton cancelButton) {
		this.cancelButton = cancelButton;
	}

	public SystemUserSchoolPane getSystemUserSchoolPane() {
		return systemUserSchoolPane;
	}

	public void setSystemUserSchoolPane(SystemUserSchoolPane systemUserSchoolPane) {
		this.systemUserSchoolPane = systemUserSchoolPane;
	}

	public IButton getSaveButton() {
		return saveButton;
	}

	
	
	
	
}
