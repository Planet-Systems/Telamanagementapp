package com.planetsystems.tela.managementapp.client.presenter.filterpaneutils;

import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.VLayout;

public class FilterYearTermDistrictSchool extends VLayout {
	private ComboBox schoolCombo;
	private ComboBox districtCombo;
	private ComboBox academicYearCombo;
	private ComboBox academicTermCombo;
	DynamicForm form;

	public FilterYearTermDistrictSchool() {
		super();
 
		form = new DynamicForm();
		form.setWrapItemTitles(false);
		form.setMargin(10);
		form.setColWidths("150", "250");
		form.setCellPadding(5);
		form.setNumCols(4);

		academicYearCombo = new ComboBox();
		academicYearCombo.setTitle("Academic Year");
		academicYearCombo.setHint("Select");
		academicYearCombo.setShowHintInField(true);

		academicTermCombo = new ComboBox();
		academicTermCombo.setTitle("Academic Term");
		academicTermCombo.setHint("Select");
		academicTermCombo.setShowHintInField(true);

		districtCombo = new ComboBox();
		districtCombo.setTitle("Local Government");
		districtCombo.setHint("District/City/Munincipality");
		districtCombo.setShowHintInField(true);

		schoolCombo = new ComboBox();
		schoolCombo.setTitle("School");
		schoolCombo.setHint("School");
		schoolCombo.setShowHintInField(true);

		form.setFields(academicYearCombo,  academicTermCombo, districtCombo,schoolCombo);

		this.addMember(form);
		this.setWidth100();
		this.setAutoHeight();
	}

	public ComboBox getSchoolCombo() {
		return schoolCombo;
	}

	public ComboBox getDistrictCombo() {
		return districtCombo;
	}

	public ComboBox getAcademicYearCombo() {
		return academicYearCombo;
	}


	public ComboBox getAcademicTermCombo() {
		return academicTermCombo;
	}

	public DynamicForm getForm() {
		return form;
	}

	
	
}
