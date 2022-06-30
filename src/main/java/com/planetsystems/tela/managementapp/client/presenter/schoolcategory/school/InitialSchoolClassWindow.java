package com.planetsystems.tela.managementapp.client.presenter.schoolcategory.school;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class InitialSchoolClassWindow extends Window {

	private InitialSchoolClassListGrid listGrid;

	private IButton saveButton;
	private IButton cancelButton;

	public InitialSchoolClassWindow() {
		super();

		Label header = new Label();

		header.setStyleName("crm-ContextArea-Header-Label");
		header.setContents("The following classes will be created for this school.");
		header.setWidth("100%");
		header.setAutoHeight();
		header.setMargin(10);
		header.setAlign(Alignment.LEFT);

		listGrid = new InitialSchoolClassListGrid();
		saveButton = new IButton("Ok");
		cancelButton = new IButton("Cancel");
		cancelButton.setBaseStyle("cancel-button");

		HLayout buttonLayout = new HLayout();
		buttonLayout.setMembers(cancelButton, saveButton);
		buttonLayout.setAutoHeight();
		buttonLayout.setAutoWidth();
		buttonLayout.setMargin(5);
		buttonLayout.setMembersMargin(4);

		buttonLayout.setLayoutAlign(Alignment.CENTER);

		VLayout layout = new VLayout();
		layout.addMember(header);
		layout.addMember(listGrid);
		layout.addMember(buttonLayout);
		layout.setMembersMargin(15);
		layout.setMargin(10);

		layout.setMargin(10);
		this.addItem(layout);
		this.setWidth("50%");
		this.setHeight("50%");
		this.setAutoCenter(true);
		this.setTitle("Initial School Classes");
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

	public InitialSchoolClassListGrid getListGrid() {
		return listGrid;
	}

	public IButton getSaveButton() {
		return saveButton;
	}

}
