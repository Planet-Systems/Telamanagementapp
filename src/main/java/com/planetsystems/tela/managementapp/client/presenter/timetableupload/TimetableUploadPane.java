package com.planetsystems.tela.managementapp.client.presenter.timetableupload;

import java.util.List;

import com.planetsystems.tela.dto.SchoolClassDTO;
import com.planetsystems.tela.dto.SchoolStaffDTO;
import com.planetsystems.tela.dto.SubjectDTO;
import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class TimetableUploadPane extends VLayout {
 
	private ComboBox district;
	private ComboBox region;
	private ComboBox school;
	private ComboBox academicYear;
	private ComboBox academicTerm;

	private IButton saveButton;
	private IButton submitButton;
	private IButton backButton;
	
	private VLayout timetableLayout;

	public TimetableUploadPane() {
		super();

		academicTerm = new ComboBox();
		academicTerm.setTitle("Academic Year");
		academicTerm.setHint("Select");
		academicTerm.setShowHint(true);
		academicTerm.setShowHintInField(true);

		academicYear = new ComboBox();
		academicYear.setTitle("Academic Term");
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
		filterForm.setNumCols(4);
		filterForm.setColWidths(150, 250, 150, 250, 150, 250, 150, 250);
		filterForm.setCellPadding(10);
		filterForm.setFields(academicYear, academicTerm, region, district, school);

		timetableLayout = new VLayout();
		
		//timetableUploadListgrid = new TimetableUploadListgrid(  days,   clazes,   subjects,staffList);

		saveButton = new IButton("Save as Draft");
		submitButton = new IButton("Submit");
		backButton = new IButton("Back");

		HLayout buttonLayout = new HLayout();
		buttonLayout.setMembers(backButton, saveButton, submitButton);
		buttonLayout.setAutoHeight();
		buttonLayout.setWidth100();
		buttonLayout.setMargin(5);
		buttonLayout.setMembersMargin(4);
		buttonLayout.setAlign(Alignment.CENTER);

		this.addMember(filterForm);
		this.addMember(timetableLayout);
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



	public VLayout getTimetableLayout() {
		return timetableLayout;
	}

}
