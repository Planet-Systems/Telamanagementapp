package com.planetsystems.tela.managementapp.client.presenter.systemuser;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.planetsystems.tela.managementapp.client.widget.TextField;
import com.planetsystems.tela.managementapp.shared.DatePattern;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.validator.MatchesFieldValidator;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class SystemUserWindow extends Window {
	private TextItem userNameField;
	private PasswordItem passwordField;
	private PasswordItem confirmPasswordField;
	private TextItem firstNameField;
	private TextItem lastNameField;
	private TextItem phoneNumberField;
	private TextItem emailField;
	private DateItem dobItem;
	private TextItem nationalIdField;
	private ComboBox genderComboBox;
	private TextItem nameAbbrevField;
	private RadioGroupItem enabledRadioGroupItem;

	DateTimeFormat dateFormat = DateTimeFormat.getFormat(DatePattern.DAY_MONTH_YEAR.getPattern());
	/*
	 * private String userName; private String password; private boolean enabled =
	 * true; private String configRole;
	 */

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

		passwordField = new PasswordItem();
		passwordField.setTitle("Password");
		passwordField.setName("password");
		passwordField.setHint("password");
		passwordField.setShowHintInField(true);

		confirmPasswordField = new PasswordItem();
		confirmPasswordField.setName("confirmPassword");
		confirmPasswordField.setTitle("Confirm Password");
		confirmPasswordField.setHint("confirm password");
		confirmPasswordField.setShowHintInField(true);
		confirmPasswordField.setRequired(true);

		userNameField = new TextItem();
		userNameField.setTitle("Username");
		userNameField.setHint("username");
		userNameField.setShowHintInField(true);

		firstNameField = new TextItem();
		firstNameField.setTitle("FirstName");
		firstNameField.setHint("firstName");
		firstNameField.setShowHintInField(true);

		lastNameField = new TextItem();
		lastNameField.setTitle("LastName");
		lastNameField.setHint("LastName");
		lastNameField.setShowHintInField(true);

		phoneNumberField = new TextItem();
		phoneNumberField.setTitle("PhoneNumber");
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
		nationalIdField.setTitle("NationalId");
		nationalIdField.setHint("NIN");
		nationalIdField.setShowHintInField(true);

		nameAbbrevField = new TextItem();
		nameAbbrevField.setTitle("NameAbbreviation");
		nameAbbrevField.setHint("NameAbbreviation");
		nameAbbrevField.setShowHintInField(true);

		genderComboBox = new ComboBox();
		genderComboBox.setTitle("Gender");
		genderComboBox.setHint("Gender");
		genderComboBox.setShowHintInField(true);

		DynamicForm dynamicForm = new DynamicForm();
		dynamicForm.setFields(firstNameField, lastNameField, userNameField , nameAbbrevField, emailField, nationalIdField,
				phoneNumberField, genderComboBox, enabledRadioGroupItem , dobItem  , passwordField , confirmPasswordField);
		dynamicForm.setNumCols(4);
		dynamicForm.setWrapItemTitles(false);
		dynamicForm.setMargin(10);
		dynamicForm.setCellPadding(8);
		dynamicForm.setColWidths("80", "220", "80", "220");

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
		this.setWidth("65%");
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

	public TextItem getUserNameField() {
		return userNameField;
	}

	public PasswordItem getPasswordField() {
		return passwordField;
	}

	public PasswordItem getConfirmPasswordField() {
		return confirmPasswordField;
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

	
	
}
