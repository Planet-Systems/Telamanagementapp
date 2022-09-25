package com.planetsystems.tela.managementapp.client.presenter.learnerattendance;

import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.layout.HLayout;

public class FilterLearnerAttendancePane extends HLayout {

	private ComboBox districtCombo;
	private ComboBox schoolCombo;
	private ComboBox academicYearCombo;
	private ComboBox academicTermCombo;
	private DateItem attendanceDateItem;

	
	public FilterLearnerAttendancePane() {
		super();

		districtCombo = new ComboBox();
		districtCombo.setTitle("Local Government");
		districtCombo.setHint("Select");
		districtCombo.setShowHintInField(true);
		
		
		schoolCombo = new ComboBox();
		schoolCombo.setTitle("School");
		schoolCombo.setHint("Select");
		schoolCombo.setShowHintInField(true);
		
		academicYearCombo = new ComboBox();
		academicYearCombo.setTitle("Academic Year");
		academicYearCombo.setHint("Select");
		academicYearCombo.setShowHintInField(true);
		
		academicTermCombo = new ComboBox();
		academicTermCombo.setTitle("Academic Term");
		academicTermCombo.setHint("Select");
		academicTermCombo.setShowHintInField(true);
		
		attendanceDateItem = new DateItem();
		attendanceDateItem.setTitle("Date");

		DynamicForm form = new DynamicForm();
		form.setFields(academicYearCombo  ,districtCombo ,academicTermCombo, schoolCombo , attendanceDateItem);
		form.setWrapItemTitles(false);
		form.setMargin(10);
		form.setCellPadding(10);
		form.setNumCols(2);
		form.setColWidths("100" , "300");

		
		this.addMember(form);
		this.setAutoHeight();
		this.setWidth100();
	
	}

	public ComboBox getDistrictCombo() {
		return districtCombo;
	}

	public ComboBox getSchoolCombo() {
		return schoolCombo;
	}

	public ComboBox getAcademicYearCombo() {
		return academicYearCombo;
	}

	public ComboBox getAcademicTermCombo() {
		return academicTermCombo;
	}

	public DateItem getAttendanceDateItem() {
		return attendanceDateItem;
	}


	
	
	
	

}
