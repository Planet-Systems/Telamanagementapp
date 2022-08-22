package com.planetsystems.tela.managementapp.client.presenter.useraccountrequest;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.planetsystems.tela.managementapp.client.widget.TextField;
import com.planetsystems.tela.managementapp.shared.DatePattern;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class SignupPane extends VLayout {

	private TextItem emailField;
	private TextItem firstNameField;
	private TextItem lastNameField;
	private TextItem phoneNumberField;
	private ComboBox genderCombo;
	private TextItem nationalIdField;
	private DateItem dobItem;
	private ComboBox systemUserGroupCombo;
	private TextItem comment;

	DateTimeFormat dateFormat = DateTimeFormat.getFormat(DatePattern.DAY_MONTH_YEAR.getPattern());

	private IButton submitButton;
	private IButton backButton;

	public SignupPane() {
		super();

		Label header2 = new Label();
		String headerContent2 = "<h4 style='font-size:18px;color:#000000; font-weight:bold;margin-left:10px;'>Request for TELA user account</h4>"
				+ "<p style='font-size:14px;color:#000000;margin-left:1px;font-weight:bold;'>Need login Assistance?</p>"
				+ "<p style='font-size:14px;color:#000000;margin-left:1px;'>Users with login credentials! Login</p>"
				+ "<p style='font-size:14px;color:#000000;margin-left:1px;'>New user!! Please Fill in request form below and submit. Your request shall be reviewed and approved.</p>"
				+ "<p style='font-size:14px;color:#000000;margin-left:1px;'>Upon approval, you will receive the username and password on your email.</p>"
				+ "<p style='font-size:14px;color:#000000;margin-left:1px;font-weight:bold;'>In case of further clarifications you may contact 0800 377771, or email: tela@gmail.com support</p>";
		header2.setContents(headerContent2);
		header2.setWidth("100%");
		header2.setAutoHeight();
		header2.setMargin(10);
		header2.setAlign(Alignment.LEFT);

		emailField = new TextField();
		emailField.setTitle(
				"<span style='  font-weight: 500; font-size: 14px; '>Email</span><span style=' color: red; font-weight: 400; font-size: 14px;'>*</span>");
		emailField.setUsePlaceholderForHint(true);
		emailField.setShowHintInField(true);
		emailField.setHint("Email");

		firstNameField = new TextField();
		firstNameField.setTitle(
				"<span style='  font-weight: 500; font-size: 14px; '>First Name</span><span style=' color: red; font-weight: 400; font-size: 14px;'>*</span>");
		firstNameField.setUsePlaceholderForHint(true);
		firstNameField.setShowHintInField(true);
		// firstNameField.setHint("First Name");

		lastNameField = new TextField();
		lastNameField.setTitle(
				"<span style='  font-weight: 500; font-size: 14px; '>Last Name</span><span style=' color: red; font-weight: 400; font-size: 14px;'>*</span>");
		lastNameField.setUsePlaceholderForHint(true);
		lastNameField.setShowHintInField(true);
		// shortName.setHint("Short Name (abbreviation)");

		phoneNumberField = new TextField();
		phoneNumberField.setTitle(
				"<span style='  font-weight: 500; font-size: 14px; '>Telephone Number </span><span style=' color: red; font-weight: 400; font-size: 14px;'>*</span>");
		phoneNumberField.setUsePlaceholderForHint(true);
		phoneNumberField.setShowHintInField(true);
		// officeNumber.setHint("Office Telephone No.");

		genderCombo = new ComboBox();
		genderCombo.setTitle(
				"<span style='  font-weight: 500; font-size: 14px; '>Gender</span><span style=' color: red; font-weight: 400; font-size: 14px;'>*</span>");
		genderCombo.setUsePlaceholderForHint(true);
		genderCombo.setShowHintInField(true);
		// physicalAddress.setHint("Physical Address or Main Office Location");

		nationalIdField = new TextField();
		nationalIdField.setTitle("<span style='  font-weight: 500; font-size: 14px; '>National Id</span>");
		nationalIdField.setUsePlaceholderForHint(true);
		nationalIdField.setShowHintInField(true);
		// postalAddress.setHint("Physical Address or Main Office Location");

		dobItem = new DateItem();
		dobItem.setWidth("*");
		dobItem.setTitle(
				"<span style='  font-weight: 500; font-size: 14px; '>Date of Birth </span><span style=' color: red; font-weight: 400; font-size: 14px;'></span>");
		// category.setUsePlaceholderForHint(true);
		// category.setShowHintInField(true);
		// category.setHint("Category");
		dobItem.setStartDate(new Date(10, 0, 1));

		systemUserGroupCombo = new ComboBox();
		systemUserGroupCombo.setTitle(
				"<span style='  font-weight: 500; font-size: 14px; '>Role</span><span style=' color: red; font-weight: 400; font-size: 14px;'>*</span>");
		systemUserGroupCombo.setUsePlaceholderForHint(true);
		systemUserGroupCombo.setShowHintInField(true);
		// applicantFirstName.setHint("Applicant First Name");

		comment = new TextField();
		comment.setTitle(
				"<span style='  font-weight: 500; font-size: 14px; '>Reason </span><span style=' color: red; font-weight: 400; font-size: 14px;'>*</span>");
		comment.setUsePlaceholderForHint(true);
		comment.setShowHintInField(true);
		// applicantLastName.setHint("Applicant Last Name");

		submitButton = new IButton("Submit");
		backButton = new IButton("Back");

		DynamicForm form = new DynamicForm();
		form.setFields( firstNameField, lastNameField, emailField,phoneNumberField, genderCombo, systemUserGroupCombo,nationalIdField,
				dobItem,  comment);
		form.setWrapItemTitles(true);
		form.setMargin(5);
		form.setNumCols(2);
		form.setColWidths("400", "400");
		form.setCellPadding(15);
		form.setTitleOrientation(TitleOrientation.TOP);

		HLayout buttonLayout = new HLayout();
		buttonLayout.setMembers(backButton, submitButton);
		buttonLayout.setAutoHeight();
		buttonLayout.setWidth100();
		buttonLayout.setMargin(5);
		buttonLayout.setMembersMargin(4);

		VLayout layout = new VLayout();

		layout.addMember(header2);
		layout.addMember(form);

		layout.addMember(buttonLayout);
		layout.setStyleName("crm-signup");
		// layout.setBackgroundColor("#ffffff");

		this.setMembers(layout);
		// this.setBackgroundColor("#000000");
		this.setOverflow(Overflow.AUTO);
		this.setBackgroundColor("#f0f0f0");

	}

	public IButton getSubmitButton() {
		return submitButton;
	}

	public IButton getBackButton() {
		return backButton;
	}

	public TextItem getEmailField() {
		return emailField;
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

	public ComboBox getGenderCombo() {
		return genderCombo;
	}

	public TextItem getNationalIdField() {
		return nationalIdField;
	}

	public DateItem getDobItem() {
		return dobItem;
	}

	public ComboBox getSystemUserGroupCombo() {
		return systemUserGroupCombo;
	}

	public TextItem getComment() {
		return comment;
	}

	public DateTimeFormat getDateFormat() {
		return dateFormat;
	}

}
