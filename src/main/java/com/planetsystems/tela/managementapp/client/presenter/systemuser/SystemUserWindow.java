package com.planetsystems.tela.managementapp.client.presenter.systemuser;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.planetsystems.tela.managementapp.shared.DatePattern;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.validator.MatchesFieldValidator;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class SystemUserWindow extends Window {

	private TextItem firstNameField;
	private TextItem lastNameField;
	private TextItem phoneNumberField;
	private TextItem emailField;
	private DateItem dobItem;
	private TextItem nationalIdField;
	private ComboBox genderComboBox;
	private TextItem nameAbbrevField;
	private RadioGroupItem enabledRadioGroupItem;

	private ComboBox systemUserGroup;

	DateTimeFormat dateFormat = DateTimeFormat.getFormat(DatePattern.DAY_MONTH_YEAR.getPattern());

	private IButton saveButton;
	private IButton cancelButton;

	public SystemUserWindow() {

		enabledRadioGroupItem = new RadioGroupItem();
		enabledRadioGroupItem.setTitle("Is User Enabled");
		enabledRadioGroupItem.setColSpan("*");
		enabledRadioGroupItem.setDefaultValue(true);
		enabledRadioGroupItem.setVertical(false);

		MatchesFieldValidator validator = new MatchesFieldValidator();
		validator.setOtherField("confirmPassword");
		validator.setErrorMessage("Passwords do not match");

		firstNameField = new TextItem();
		firstNameField.setTitle("First Name");
		firstNameField.setHint("First Name");
		firstNameField.setShowHintInField(true);

		lastNameField = new TextItem();
		lastNameField.setTitle("Last Name");
		lastNameField.setHint("Last Name");
		lastNameField.setShowHintInField(true);

		phoneNumberField = new TextItem();
		phoneNumberField.setTitle("Phone Number");
		phoneNumberField.setMask("#### ###-###");
		phoneNumberField.setHint("#### ###-###");
		phoneNumberField.setShowHintInField(true);

		emailField = new TextItem();
		emailField.setTitle("Email");
		emailField.setHint("Email");
		emailField.setShowHintInField(true);

		dobItem = new DateItem();
		dobItem.setTitle("DOB");
		dobItem.setStartDate(dateFormat.parse("01/01/1996"));

		nationalIdField = new TextItem();
		nationalIdField.setTitle("National Id");
		nationalIdField.setHint("NIN");
		nationalIdField.setShowHintInField(true);

		nameAbbrevField = new TextItem();
		nameAbbrevField.setTitle("Name Abbreviation");
		nameAbbrevField.setHint("Name Abbreviation");
		nameAbbrevField.setShowHintInField(true);
		nameAbbrevField.hide();

		genderComboBox = new ComboBox();
		genderComboBox.setTitle("Gender");
		genderComboBox.setHint("Gender");
		genderComboBox.setShowHintInField(true);

		systemUserGroup = new ComboBox();
		systemUserGroup.setTitle("User Group");

		DynamicForm dynamicForm = new DynamicForm();
		dynamicForm.setFields(firstNameField, lastNameField, emailField, phoneNumberField, nationalIdField,
				genderComboBox, dobItem, systemUserGroup, enabledRadioGroupItem, nameAbbrevField);
		dynamicForm.setNumCols(2);
		dynamicForm.setWrapItemTitles(false);
		dynamicForm.setMargin(10);
		dynamicForm.setCellPadding(8);
		dynamicForm.setColWidths("120", "280");

		saveButton = new IButton("Save");
		cancelButton = new IButton("Close");

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
		this.setHeight("70%");
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

	public ComboBox getGenderComboBox() {
		return genderComboBox;
	}

	public TextItem getNameAbbrevField() {
		return nameAbbrevField;
	}

	public RadioGroupItem getEnabledRadioGroupItem() {
		return enabledRadioGroupItem;
	}

	public IButton getSaveButton() {
		return saveButton;
	}

	public ComboBox getSystemUserGroup() {
		return systemUserGroup;
	}

}
