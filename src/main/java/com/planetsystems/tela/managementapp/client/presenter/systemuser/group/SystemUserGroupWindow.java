package com.planetsystems.tela.managementapp.client.presenter.systemuser.group;
  
import com.planetsystems.tela.managementapp.client.widget.TextField;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class SystemUserGroupWindow extends Window {

	private TextField codeField;
	private TextField nameField;
	private TextField descriptionField;
	private CheckboxItem defaultRoleBox;
	private CheckboxItem receiveAlertBox;
	
	private CheckboxItem administrativeRoleBox;

	private IButton saveButton;
	private IButton cancelButton;

	public SystemUserGroupWindow() {
		super();
		codeField = new TextField();
		codeField.setTitle("code");

		nameField = new TextField();
		nameField.setTitle("Name");

		descriptionField = new TextField();
		descriptionField.setTitle("Description");

		defaultRoleBox = new CheckboxItem();
		defaultRoleBox.setTitle("Is Default?");

		receiveAlertBox = new CheckboxItem();
		receiveAlertBox.setTitle("Receive Project activation Alerts?");
		
		administrativeRoleBox=new CheckboxItem();
		administrativeRoleBox.setTitle("Manage Data");
		
		saveButton = new IButton("Save");
		cancelButton = new IButton("Close");

		DynamicForm form = new DynamicForm();
		form.setFields(codeField, nameField, descriptionField, defaultRoleBox, receiveAlertBox, administrativeRoleBox);
		form.setWrapItemTitles(false);
		form.setCellPadding(8);
		form.setColWidths("150", "250" , "150", "250" , "150", "250" , "150", "250" , "150", "250" , "150", "250");


		HLayout buttonLayout = new HLayout();
		buttonLayout.setMembers(cancelButton , saveButton);
		buttonLayout.setLayoutAlign(Alignment.CENTER);
		buttonLayout.setAlign(Alignment.CENTER);
		buttonLayout.setWidth("20%");
		buttonLayout.setBackgroundColor("1px solid green");
		buttonLayout.setMembersMargin(5);
		buttonLayout.setAutoHeight();
		buttonLayout.setMargin(5);
		

		VLayout layout = new VLayout();
		layout.addMember(form);
		layout.addMember(buttonLayout);

		layout.setMargin(10);
		this.addItem(layout);
		this.setWidth("40%");
		this.setHeight("60%");
		this.setAutoCenter(true);
		this.setTitle("User Groups");
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

	public TextField getCodeField() {
		return codeField;
	}

	public void setCodeField(TextField codeField) {
		this.codeField = codeField;
	}

	public TextField getNameField() {
		return nameField;
	}

	public void setNameField(TextField nameField) {
		this.nameField = nameField;
	}

	public TextField getDescriptionField() {
		return descriptionField;
	}

	public void setDescriptionField(TextField descriptionField) {
		this.descriptionField = descriptionField;
	}

	public CheckboxItem getDefaultRoleBox() {
		return defaultRoleBox;
	}

	public void setDefaultRoleBox(CheckboxItem defaultRoleBox) {
		this.defaultRoleBox = defaultRoleBox;
	}

	public CheckboxItem getReceiveAlertBox() {
		return receiveAlertBox;
	}

	public void setReceiveAlertBox(CheckboxItem receiveAlertBox) {
		this.receiveAlertBox = receiveAlertBox;
	}

	public CheckboxItem getAdministrativeRoleBox() {
		return administrativeRoleBox;
	}

	public void setAdministrativeRoleBox(CheckboxItem administrativeRoleBox) {
		this.administrativeRoleBox = administrativeRoleBox;
	}

	public IButton getSaveButton() {
		return saveButton;
	}

	public void setSaveButton(IButton saveButton) {
		this.saveButton = saveButton;
	}

	


}
