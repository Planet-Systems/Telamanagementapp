package com.planetsystems.tela.managementapp.client.presenter.schoolcategory.school;

import java.util.Date;

import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class InitialSchoolStaffWindow extends Window {

	private TextItem firstNameField;
	private TextItem lastNameField;
	private TextItem phoneNumberField;
	private TextItem emailField;
	private DateItem dobItem;
	private TextItem nationalIdField;
	private ComboBox genderCombo;
	private TextItem nameAbrevField;
	private TextItem codeField;
	private ComboBox registeredCombo;

	private ComboBox roleCombo;

	private IButton saveButton;
	private IButton cancelButton;

	public InitialSchoolStaffWindow() {
		super();
		firstNameField = new TextItem();
		firstNameField.setTitle("Given Name <span style=' color: red; font-weight: bold; font-size: 11px;'>*</span>");
		firstNameField.setHint("Given Name");
		firstNameField.setShowHintInField(true);

		lastNameField = new TextItem();
		lastNameField.setTitle("Surname <span style=' color: red; font-weight: bold; font-size: 11px;'>*</span>");
		lastNameField.setHint("Surname");
		lastNameField.setShowHintInField(true);

		phoneNumberField = new TextItem();
		phoneNumberField
				.setTitle("Phone Number <span style=' color: red; font-weight: bold; font-size: 11px;'>*</span>");
		phoneNumberField.setMask("#### ###-###");
		phoneNumberField.setHint("#### ###-###");
		phoneNumberField.setShowHintInField(true);

		emailField = new TextItem();
		emailField.setTitle("Email <span style=' color: red; font-weight: bold; font-size: 11px;'>*</span>");
		emailField.setHint("Email");
		emailField.setShowHintInField(true);

		dobItem = new DateItem();
		dobItem.setTitle("DOB");
		dobItem.setUseTextField(false);
		dobItem.setWidth("*");
		dobItem.setStartDate(new Date(10, 0, 1));

		nationalIdField = new TextItem();
		nationalIdField.setTitle("National Id");
		nationalIdField.setHint("NIN");
		nationalIdField.setShowHintInField(true);

		nameAbrevField = new TextItem();
		nameAbrevField.setTitle("Name Abbreviation");
		nameAbrevField.setHint("Name Abbreviation");
		nameAbrevField.setShowHintInField(true);

		codeField = new TextItem();
		codeField.setTitle("Teacher PIN");
		codeField.setHint("Teacher PIN");
		codeField.setShowHintInField(true);
		codeField.setHidden(true);

		genderCombo = new ComboBox();
		genderCombo.setTitle("Gender <span style=' color: red; font-weight: bold; font-size: 11px;'>*</span>");
		genderCombo.setHint("Gender");
		genderCombo.setShowHintInField(true);

		registeredCombo = new ComboBox();
		registeredCombo
				.setTitle("Payroll Status <span style=' color: red; font-weight: bold; font-size: 11px;'>*</span>");
		registeredCombo.setHint("Payroll Status");
		registeredCombo.setShowHintInField(true);

		roleCombo = new ComboBox();
		roleCombo.setTitle("Role <span style=' color: red; font-weight: bold; font-size: 11px;'>*</span>");
		roleCombo.setHint("Role");
		roleCombo.setShowHintInField(true);

		DynamicForm dynamicForm = new DynamicForm();
		dynamicForm.setFields(lastNameField, firstNameField, nameAbrevField, emailField, nationalIdField,
				phoneNumberField, codeField, genderCombo, dobItem, registeredCombo, roleCombo);
		dynamicForm.setNumCols(4);
		dynamicForm.setWrapItemTitles(false);
		dynamicForm.setMargin(10);
		dynamicForm.setCellPadding(8);
		dynamicForm.setColWidths("80", "220", "80", "220");

		saveButton = new IButton("Save");
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
		this.setWidth("50%");
		this.setHeight("50%");
		this.setAutoCenter(true);
		this.setTitle("Principal/Head Teacher Details");
		this.setIsModal(true);
		this.setShowModalMask(true);
		this.setShowCloseButton(false);
		this.setShowMinimizeButton(false);
		this.setShowMaximizeButton(false);

		// closeWindow(this);

	}

	public TextItem getFirstNameField() {
		return firstNameField;
	}

	public TextItem getLastNameField() {
		return lastNameField;
	}

	public TextItem getPhoneNumberField() {
		return phoneNumberField;
	}

	public TextItem getEmailField() {
		return emailField;
	}

	public DateItem getDobItem() {
		return dobItem;
	}

	public TextItem getNationalIdField() {
		return nationalIdField;
	}

	public ComboBox getGenderCombo() {
		return genderCombo;
	}

	public TextItem getNameAbrevField() {
		return nameAbrevField;
	}

	public TextItem getCodeField() {
		return codeField;
	}

	public ComboBox getRegisteredCombo() {
		return registeredCombo;
	}

	public ComboBox getRoleCombo() {
		return roleCombo;
	}

	public IButton getSaveButton() {
		return saveButton;
	}

	public IButton getCancelButton() {
		return cancelButton;
	}

}
