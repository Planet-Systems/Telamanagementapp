package com.planetsystems.tela.managementapp.client.presenter.systemuser.group;

import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class SystemUserGroupSystemMenuWindow extends Window {

	private SystemUserGroupSystemMenuListgrid listgrid;

	private IButton saveButton;
	private IButton cancelButton;

	public SystemUserGroupSystemMenuWindow() {
		super();

		listgrid = new SystemUserGroupSystemMenuListgrid();

		saveButton = new IButton("Save");
		cancelButton = new IButton("Cancel");
		cancelButton.setBaseStyle("cancel-button");

		HLayout buttonLayout = new HLayout();
		buttonLayout.setMembers(cancelButton,saveButton);
		buttonLayout.setAutoHeight();
		buttonLayout.setWidth100();
		buttonLayout.setMargin(5);
		buttonLayout.setMembersMargin(4);

		VLayout layout = new VLayout();
		layout.addMember(listgrid);

		layout.addMember(buttonLayout);

		layout.setMargin(10);
		this.addItem(layout);
		this.setWidth("55%");
		this.setHeight("70%");
		this.setAutoCenter(true);
		this.setTitle("User group");
		this.setIsModal(true);
		this.setShowModalMask(true);
		onCancelButtonClicked(this);
	}

	private void onCancelButtonClicked(final Window window) {
		cancelButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				window.close();

			}
		});
	}

	public SystemUserGroupSystemMenuListgrid getListgrid() {
		return listgrid;
	}

	public IButton getSaveButton() {
		return saveButton;
	}

}
