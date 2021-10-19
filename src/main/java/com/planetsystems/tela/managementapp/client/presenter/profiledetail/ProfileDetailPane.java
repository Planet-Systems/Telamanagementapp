package com.planetsystems.tela.managementapp.client.presenter.profiledetail;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.planetsystems.tela.managementapp.shared.DatePattern;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.RadioGroupItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.validator.MatchesFieldValidator;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class ProfileDetailPane extends VLayout {
	private TextItem firstNameField;
	private TextItem lastNameField;
	private TextItem phoneNumberField;
	private TextItem emailField;
	private DateItem dobItem;
	private TextItem nationalIdField;
	private ComboBox genderCombo;
	private TextItem nameAbbrevField;
	
	private IButton saveButton;
	private IButton editButton;
	VLayout buttonLayout;
	
	DateTimeFormat dateFormat = DateTimeFormat.getFormat(DatePattern.DAY_MONTH_YEAR.getPattern());
	
	public ProfileDetailPane() {
		super();

		Img profileImage = new Img("des_logo.jpg");
		profileImage.setHeight(130);
		profileImage.setWidth(130);
		profileImage.setLayoutAlign(VerticalAlignment.CENTER);

		MatchesFieldValidator validator = new MatchesFieldValidator();
		validator.setOtherField("confirmPassword");
		validator.setErrorMessage("Passwords do not match");

		firstNameField = new TextItem();
		firstNameField.setTitle("First Name");
		firstNameField.setHint("First Name");
		firstNameField.setShowHintInField(true);
		firstNameField.disable();


		lastNameField = new TextItem();
		lastNameField.setTitle("Last Name");
		lastNameField.setHint("Last Name");
		lastNameField.setShowHintInField(true);
		lastNameField.disable();

		phoneNumberField = new TextItem();
		phoneNumberField.setTitle("Phone Number");
		phoneNumberField.setMask("#### ###-###");
		phoneNumberField.setHint("#### ###-###");
		phoneNumberField.setShowHintInField(true);
		phoneNumberField.disable();

		emailField = new TextItem();
		emailField.setTitle("Email");
		emailField.setHint("Email");
		emailField.setShowHintInField(true);
		emailField.disable();

		dobItem = new DateItem();
		dobItem.setTitle("DOB");
		dobItem.setStartDate(dateFormat.parse("01/01/1996"));
		dobItem.disable();

		nationalIdField = new TextItem();
		nationalIdField.setTitle("National Id");
		nationalIdField.setHint("NIN");
		nationalIdField.setShowHintInField(true);
		nationalIdField.disable();

		nameAbbrevField = new TextItem();
		nameAbbrevField.setTitle("Name Abbreviation");
		nameAbbrevField.setHint("Name Abbreviation");
		nameAbbrevField.setShowHintInField(true);
		nameAbbrevField.hide();

		genderCombo = new ComboBox();
		genderCombo.setTitle("Gender");
		genderCombo.setHint("Gender");
		genderCombo.setShowHintInField(true);
		genderCombo.disable();


		//nameAbbrevField
		DynamicForm form = new DynamicForm();
		form.setFields(firstNameField, lastNameField, emailField, phoneNumberField, nationalIdField,genderCombo, dobItem);
		form.setWrapItemTitles(false);
		form.setCellPadding(8);
		form.setNumCols(4);
		form.setColWidths("150", "250"  , "150", "250" , "150", "250" , "150", "250" , "150", "250" , "150", "250" , "150", "250");

		saveButton = new IButton("Save");
		saveButton.setWidth("100%");
		saveButton.setHeight("25");
		
		
		editButton = new IButton("Edit");
		editButton.setWidth("100%");
		editButton.setHeight("25");
		
		buttonLayout = new VLayout();
		buttonLayout.setMembers(editButton);
		buttonLayout.setHeight("26");
		buttonLayout.setWidth("15%");
		buttonLayout.setMembersMargin(4);
//        buttonLayout.setBorder("1px solid red");
		buttonLayout.setLayoutAlign(Alignment.CENTER);

		
		
		VLayout layout = new VLayout();
		layout.setWidth("80%");
		layout.setBackgroundColor("#A9A9A9");
		layout.setMembersMargin(10);
		layout.setLayoutTopMargin(8);
//		layout.setBorder("1px solid green");
		layout.setLayoutAlign(Alignment.CENTER);
		layout.setShadowDepth(5);
		layout.setShadowColor("blue");
		
		layout.addMember(profileImage);
		layout.addMember(form);
		layout.addMember(buttonLayout);

		this.addMember(layout);
		
		
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

	public TextItem getNameAbbrevField() {
		return nameAbbrevField;
	}

	public IButton getSaveButton() {
		return saveButton;
	}

	public IButton getEditButton() {
		return editButton;
	}

	public VLayout getButtonLayout() {
		return buttonLayout;
	}

	public DateTimeFormat getDateFormat() {
		return dateFormat;
	}

	
	
	
}
