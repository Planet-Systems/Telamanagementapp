package com.planetsystems.tela.managementapp.client.presenter.systemuser.manage;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class SystemUserProfilePermissionWindow extends Window {
	private IButton cancelButton;
	private SystemUserSchoolPane systemUserSchoolPane;

	public SystemUserProfilePermissionWindow() {
		super();

		systemUserSchoolPane = new SystemUserSchoolPane();

		cancelButton = new IButton("Close");
		cancelButton.setLayoutAlign(Alignment.CENTER);

		HLayout buttonLayout = new HLayout();
		buttonLayout.setMembers(cancelButton);
		buttonLayout.setAutoHeight();
		buttonLayout.setAutoWidth();
		buttonLayout.setMargin(5);
		buttonLayout.setMembersMargin(4);
		buttonLayout.setLayoutAlign(Alignment.CENTER);

		VLayout layout = new VLayout();
		layout.setWidth100();
		layout.setHeight100();
		layout.addMember(systemUserSchoolPane);
		layout.addMember(buttonLayout); 
		layout.setMargin(5);

		this.addItem(layout);
		this.setWidth("50%");
		this.setHeight("50%");
		this.setAutoCenter(true);
		this.setTitle("User Adminstrative Units");
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

}
