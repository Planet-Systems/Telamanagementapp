package com.planetsystems.tela.managementapp.client.presenter.systemuser.group;
  
import com.google.gwt.user.client.ui.Tree;
import com.planetsystems.tela.managementapp.client.widget.TextField;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class SystemUserGroupWindow extends Window {

	private TextField codeField;
	private TextField nameField;
	private TextField descriptionField;
	private RadioGroupItem defaultRoleRadio;
	private RadioGroupItem receiveAlertRadio;
	
	private RadioGroupItem administrativeRoleRadio;

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

		defaultRoleRadio = new RadioGroupItem();
		defaultRoleRadio.setTitle("Is User Enabled");
		defaultRoleRadio.setDefaultValue(false);
		defaultRoleRadio.setValueMap("false" , "true");
		defaultRoleRadio.setVertical(false);
		
		defaultRoleRadio = new RadioGroupItem();
		defaultRoleRadio.setTitle("Is Default?");
		defaultRoleRadio.setDefaultValue(false);
		defaultRoleRadio.setVertical(false);
		defaultRoleRadio.setValueMap("false" , "true");

		receiveAlertRadio = new RadioGroupItem();
		receiveAlertRadio.setTitle("Activation Alerts?");
		receiveAlertRadio.setDefaultValue(false);
		receiveAlertRadio.setVertical(false);
		receiveAlertRadio.setValueMap("false" , "true");
		
		administrativeRoleRadio = new RadioGroupItem();
		administrativeRoleRadio.setTitle("Manage Data");
		administrativeRoleRadio.setDefaultValue(true);
		administrativeRoleRadio.setVertical(false);
		administrativeRoleRadio.setValueMap("true" , "false");
		
		saveButton = new IButton("Save");
		cancelButton = new IButton("Close");

		DynamicForm form = new DynamicForm();
		form.setFields(codeField, nameField, descriptionField, defaultRoleRadio, receiveAlertRadio, administrativeRoleRadio);
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
		this.setWidth("50%");
		this.setHeight("70%");
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

	public RadioGroupItem getDefaultRoleRadio() {
		return defaultRoleRadio;
	}

	public RadioGroupItem getReceiveAlertRadio() {
		return receiveAlertRadio;
	}

	public RadioGroupItem getAdministrativeRoleRadio() {
		return administrativeRoleRadio;
	}

	public IButton getSaveButton() {
		return saveButton;
	}


	
	


}
