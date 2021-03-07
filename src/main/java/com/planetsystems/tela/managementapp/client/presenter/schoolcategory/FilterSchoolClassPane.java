package com.planetsystems.tela.managementapp.client.presenter.schoolcategory;

import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.HLayout;

public class FilterSchoolClassPane extends HLayout {

	private ComboBox academicTermCombo;
	private ComboBox schoolCombo;
	
	public static final String ACADEMIC_TERM_ID = "ACADEMIC_TERM_ID";
	public static final String SCHOOL_ID = "SCHOOL_ID";

	public FilterSchoolClassPane() {
		super();

		academicTermCombo = new ComboBox();
		academicTermCombo.setTitle("Academic Term");
		academicTermCombo.setHint("Select A Term");
		academicTermCombo.setShowHintInField(true);

		schoolCombo = new ComboBox();
		schoolCombo.setTitle("School");
		schoolCombo.setHint("Select A School");
		schoolCombo.setShowHintInField(true);

		DynamicForm form = new DynamicForm();
		form.setFields(academicTermCombo, schoolCombo);
		form.setWrapItemTitles(false);
		form.setMargin(10);
		form.setCellPadding(10);
		form.setNumCols(2);
		form.setColWidths("80", "250");

		this.addMember(form);
		this.setAutoHeight();
		this.setWidth100();
		// this.setBorder("1px solid green");
	}

	public ComboBox getAcademicTermCombo() {
		return academicTermCombo;
	}

	public ComboBox getSchoolCombo() {
		return schoolCombo;
	}


}
