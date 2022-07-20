package com.planetsystems.tela.managementapp.client.presenter.learnerenrollment;

import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class LearnerGuardianWindow extends Window {

	private TextItem firstNameField;
	private TextItem lastNameField;
	private TextItem nationality;
	private TextItem nationalIdField;
	private TextItem phoneNumberField;
	private ComboBox relationshipCombo;

	private IButton saveButton;
	private IButton cancelButton;

	public LearnerGuardianWindow() {
		super();

		firstNameField = new TextItem();
		firstNameField.setTitle("First Name <span style=' color: red; font-weight: bold; font-size: 11px;'>*</span>");
		firstNameField.setHint("First Name");
		firstNameField.setShowHintInField(true);

		lastNameField = new TextItem();
		lastNameField.setTitle("Last Name <span style=' color: red; font-weight: bold; font-size: 11px;'>*</span>");
		lastNameField.setHint("Last Name");
		lastNameField.setShowHintInField(true);

		nationality = new TextItem();
		nationality.setTitle("Nationality");
		nationality.setHint("Nationality");
		nationality.setShowHintInField(true);

		phoneNumberField = new TextItem();
		phoneNumberField
				.setTitle("Phone Number <span style=' color: red; font-weight: bold; font-size: 11px;'>*</span>");
		phoneNumberField.setMask("#### ###-###");
		phoneNumberField.setHint("#### ###-###");
		phoneNumberField.setShowHintInField(true);

		nationalIdField = new TextItem();
		nationalIdField.setTitle("National Id");
		nationalIdField.setHint("NIN");
		nationalIdField.setShowHintInField(true);

		relationshipCombo = new ComboBox();
		relationshipCombo.setTitle("Relationship");
		relationshipCombo.setHint("Select");
		relationshipCombo.setShowHintInField(true);

		DynamicForm dynamicForm = new DynamicForm();
		dynamicForm.setFields(firstNameField, lastNameField, nationality, nationalIdField, phoneNumberField,
				relationshipCombo);
		dynamicForm.setNumCols(2);
		dynamicForm.setWrapItemTitles(true);
		dynamicForm.setMargin(10);
		dynamicForm.setCellPadding(8);
		dynamicForm.setColWidths("80", "220", "80", "220");

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
		layout.addMember(dynamicForm); 
		layout.addMember(buttonLayout);
		layout.setMembersMargin(10);
		layout.setMargin(10);

		layout.setMargin(10);
		this.addItem(layout);
		this.setWidth("30%");
		this.setHeight("50%");
		this.setAutoCenter(true);
		this.setTitle("Parent/Guardian Information");
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

	public TextItem getFirstNameField() {
		return firstNameField;
	}

	public TextItem getLastNameField() {
		return lastNameField;
	}

	public TextItem getNationality() {
		return nationality;
	}

	public TextItem getNationalIdField() {
		return nationalIdField;
	}

	public TextItem getPhoneNumberField() {
		return phoneNumberField;
	}

	public ComboBox getRelationshipCombo() {
		return relationshipCombo;
	}

	public IButton getSaveButton() {
		return saveButton;
	}

	public IButton getCancelButton() {
		return cancelButton;
	}
	
	

}
