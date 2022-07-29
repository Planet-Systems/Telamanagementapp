package com.planetsystems.tela.managementapp.client.presenter.timetableupload;

import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class TimetableUploadHomePane extends VLayout {

	private ComboBox district;
	private ComboBox region;
	private ComboBox school;
	private ComboBox academicYear;
	private ComboBox academicTerm;

	private IButton saveButton;
	private IButton submitButton;
	private IButton backButton;

	public TimetableUploadHomePane() {
		super();

		academicTerm = new ComboBox();
		academicTerm.setTitle("Academic Term");
		academicTerm.setHint("Select");
		academicTerm.setShowHint(true);
		academicTerm.setShowHintInField(true);

		academicYear = new ComboBox();
		academicYear.setTitle("Academic Year");
		academicYear.setHint("Select");
		academicYear.setShowHint(true);
		academicYear.setShowHintInField(true);

		region = new ComboBox();
		region.setTitle("Region");
		region.setHint("Region");
		region.setShowHint(true);
		region.setShowHintInField(true);

		district = new ComboBox();
		district.setTitle("Local Government");
		district.setHint("Select");
		district.setShowHint(true);
		district.setShowHintInField(true);

		school = new ComboBox();
		school.setTitle("School");
		school.setHint("Select");
		school.setShowHint(true);
		school.setShowHintInField(true);

		DynamicForm filterForm = new DynamicForm();
		filterForm.setNumCols(2);
		filterForm.setColWidths(250, 350);
		filterForm.setCellPadding(10);
		filterForm.setFields(academicYear, academicTerm, region, district, school);

		saveButton = new IButton("Next");
		submitButton = new IButton("Submit");
		submitButton.hide();
		backButton = new IButton("Back");

		HLayout buttonLayout = new HLayout();
		buttonLayout.setMembers(backButton, saveButton, submitButton);
		buttonLayout.setAutoHeight();
		buttonLayout.setWidth100();
		buttonLayout.setMargin(5);
		buttonLayout.setMembersMargin(4);
		buttonLayout.setAlign(Alignment.CENTER);

		this.addMember(filterForm);
		this.addMember(buttonLayout);

	}

	public ComboBox getDistrict() {
		return district;
	}

	public ComboBox getRegion() {
		return region;
	}

	public ComboBox getSchool() {
		return school;
	}

	public ComboBox getAcademicYear() {
		return academicYear;
	}

	public ComboBox getAcademicTerm() {
		return academicTerm;
	}

	public IButton getSaveButton() {
		return saveButton;
	}

	public IButton getSubmitButton() {
		return submitButton;
	}

	public IButton getBackButton() {
		return backButton;
	}

	 

}
