package com.planetsystems.tela.managementapp.client.presenter.learnerenrollment;

import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;

public class LearnerRegistrationDetailWindow extends Window {

	private TextItem firstNameField;
	private TextItem lastNameField;
	private TextItem phoneNumberField;
	private DateItem dobItem;
	private TextItem nationalIdField;
	private ComboBox genderCombo;

	private ComboBox specialNeedsCombo;
	private ComboBox homeDistrict;
	private ComboBox orphanStatusCombo;
	private TextItem otherNameField;
	private TextItem nationality;
	private TextItem telaNoField;

	private ComboBox districtCombo;
	private ComboBox schoolCombo;

	private ComboBox schoolClassCombo;
	private ComboBox academicYearCombo;
	private ComboBox academicTermCombo;

	private LearnerGuardianListgrid listgrid;

	private IButton cancelButton;

	public LearnerRegistrationDetailWindow() {
		super();

		firstNameField = new TextItem();
		firstNameField.setTitle("First Name <span style=' color: red; font-weight: bold; font-size: 11px;'>*</span>");
		firstNameField.setHint("First Name");
		firstNameField.setShowHintInField(true);

		lastNameField = new TextItem();
		lastNameField.setTitle("Last Name <span style=' color: red; font-weight: bold; font-size: 11px;'>*</span>");
		lastNameField.setHint("Last Name");
		lastNameField.setShowHintInField(true);

		otherNameField = new TextItem();
		otherNameField.setTitle("Other Name");
		otherNameField.setHint("Other Name");
		otherNameField.setShowHintInField(true);

		nationality = new TextItem();
		nationality.setTitle("Nationality");
		nationality.setHint("Nationality");
		nationality.setShowHintInField(true);

		telaNoField = new TextItem();
		telaNoField.setTitle("Learner Tela Number");
		telaNoField.setHint("Learner Tela Number");
		telaNoField.setShowHintInField(true);

		phoneNumberField = new TextItem();
		phoneNumberField
				.setTitle("Phone Number <span style=' color: red; font-weight: bold; font-size: 11px;'>*</span>");
		phoneNumberField.setMask("#### ###-###");
		phoneNumberField.setHint("#### ###-###");
		phoneNumberField.setShowHintInField(true);

		specialNeedsCombo = new ComboBox();
		specialNeedsCombo.setTitle("Has Special Needs?");
		specialNeedsCombo.setHint("Select");
		specialNeedsCombo.setShowHintInField(true);

		dobItem = new DateItem();
		dobItem.setTitle("DOB");
		dobItem.setUseTextField(false);
		dobItem.setWidth("*");

		nationalIdField = new TextItem();
		nationalIdField.setTitle("National Id");
		nationalIdField.setHint("NIN");
		nationalIdField.setShowHintInField(true);

		homeDistrict = new ComboBox();
		homeDistrict.setTitle("Home District");
		homeDistrict.setHint("Select");
		homeDistrict.setShowHintInField(true);

		orphanStatusCombo = new ComboBox();
		orphanStatusCombo.setTitle("Orphan Status");
		orphanStatusCombo.setHint("Select");
		orphanStatusCombo.setShowHintInField(true);

		genderCombo = new ComboBox();
		genderCombo.setTitle("Gender <span style=' color: red; font-weight: bold; font-size: 11px;'>*</span>");
		genderCombo.setHint("Gender");
		genderCombo.setShowHintInField(true);

		schoolCombo = new ComboBox();
		schoolCombo.setTitle("School <span style=' color: red; font-weight: bold; font-size: 11px;'>*</span>");
		schoolCombo.setHint("Select");
		schoolCombo.setShowHintInField(true);

		districtCombo = new ComboBox();
		districtCombo
				.setTitle("School District <span style=' color: red; font-weight: bold; font-size: 11px;'>*</span>");
		districtCombo.setHint("Select");
		districtCombo.setShowHintInField(true);

		schoolClassCombo = new ComboBox();
		schoolClassCombo
				.setTitle("Enrolled Class <span style=' color: red; font-weight: bold; font-size: 11px;'>*</span>");
		schoolClassCombo.setHint("Select");
		schoolClassCombo.setShowHintInField(true);

		academicYearCombo = new ComboBox();
		academicYearCombo.setTitle("Academic Year");
		academicYearCombo.setHint("Select");
		academicYearCombo.setShowHintInField(true);

		academicTermCombo = new ComboBox();
		academicTermCombo.setTitle("Academic Term");
		academicTermCombo.setHint("Select");
		academicTermCombo.setShowHintInField(true);

		listgrid = new LearnerGuardianListgrid();
		listgrid.setHeight(220);

		DynamicForm dynamicForm = new DynamicForm();
		dynamicForm.setFields(firstNameField, lastNameField, otherNameField, genderCombo, nationality, nationalIdField,
				dobItem, homeDistrict, orphanStatusCombo, specialNeedsCombo, phoneNumberField, telaNoField,
				academicYearCombo, academicTermCombo, districtCombo, schoolCombo, schoolClassCombo);
		dynamicForm.setNumCols(4);
		dynamicForm.setWrapItemTitles(false);
		dynamicForm.setMargin(10);
		dynamicForm.setCellPadding(8);
		dynamicForm.setColWidths("80", "220", "80", "220");

		cancelButton = new IButton("Cancel");
		cancelButton.setBaseStyle("cancel-button");

		HLayout buttonLayout = new HLayout();
		buttonLayout.setMembers(cancelButton);
		buttonLayout.setAutoHeight();
		buttonLayout.setAutoWidth();
		buttonLayout.setMargin(5);
		buttonLayout.setMembersMargin(4);
		buttonLayout.setLayoutAlign(Alignment.CENTER);

		VLayout guardianLayout = new VLayout();
		guardianLayout.setMembers(listgrid);
		guardianLayout.setAutoHeight();
		guardianLayout.setWidth100();
		guardianLayout.setMargin(5);
		guardianLayout.setMembersMargin(4);

		final SectionStack sectionStack = new SectionStack();
		sectionStack.setVisibilityMode(VisibilityMode.MUTEX);
		sectionStack.setWidth100();
		sectionStack.setHeight(250);

		SectionStackSection section1 = new SectionStackSection("Parents/Guardian Information");
		section1.setExpanded(true);
		section1.addItem(guardianLayout);
		sectionStack.addSection(section1);
		section1.setIcon("images/icons/16/contact.png");

		VLayout layout = new VLayout();
		layout.addMember(dynamicForm);
		layout.addMember(sectionStack);
		layout.addMember(buttonLayout);
		layout.setMembersMargin(10);
		layout.setMargin(10);

		layout.setMargin(10);
		this.addItem(layout);
		this.setWidth("60%");
		this.setHeight("80%");
		this.setAutoCenter(true);
		this.setTitle("Learner Registration Details");
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

	public DateItem getDobItem() {
		return dobItem;
	}

	public TextItem getNationalIdField() {
		return nationalIdField;
	}

	public ComboBox getGenderCombo() {
		return genderCombo;
	}

	public ComboBox getSpecialNeedsCombo() {
		return specialNeedsCombo;
	}

	public ComboBox getHomeDistrict() {
		return homeDistrict;
	}

	public ComboBox getOrphanStatusCombo() {
		return orphanStatusCombo;
	}

	public TextItem getOtherNameField() {
		return otherNameField;
	}

	public TextItem getNationality() {
		return nationality;
	}

	public TextItem getTelaNoField() {
		return telaNoField;
	}

	public ComboBox getDistrictCombo() {
		return districtCombo;
	}

	public ComboBox getSchoolCombo() {
		return schoolCombo;
	}

	public ComboBox getSchoolClassCombo() {
		return schoolClassCombo;
	}

	public ComboBox getAcademicYearCombo() {
		return academicYearCombo;
	}

	public ComboBox getAcademicTermCombo() {
		return academicTermCombo;
	}

	public LearnerGuardianListgrid getListgrid() {
		return listgrid;
	}

	public IButton getCancelButton() {
		return cancelButton;
	}
}
