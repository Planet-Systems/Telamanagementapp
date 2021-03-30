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
		
//		HLayout formLayout = new HLayout();
//		formLayout.setPadding(5);
//		formLayout.setAutoHeight();
//		formLayout.setWidth("50%");
////		    formLayout.setBorder("1px solid red");
//		formLayout.setLayoutAlign(Alignment.CENTER);
//
//		HLayout schoolDistrictLayout = new HLayout();
//		schoolDistrictLayout.setPadding(5);
//		schoolDistrictLayout.setAutoHeight();
//		schoolDistrictLayout.setWidth("50%");
////	    schoolDistrictLayout.setBorder("1px solid red");
//		schoolDistrictLayout.setLayoutAlign(Alignment.CENTER);

		Label schoolLabel = new Label("School: ");
		schoolLabel.setAutoHeight();
		schoolLabel.setAutoWidth();
		schoolLabel.setPadding(2);

		Label dayLabel = new Label("Day: ");
		dayLabel.setAutoHeight();
		dayLabel.setAutoWidth();
		dayLabel.setPadding(2);

		form = new DynamicForm();
		form.setWrapItemTitles(false);
		form.setMargin(10);
		form.setColWidths("150", "250");
		form.setCellPadding(5);
		form.setNumCols(4);

		academicYearCombo = new ComboBox();
		academicYearCombo.setTitle("AcademicYear");
		academicYearCombo.setHint("Year");
		academicYearCombo.setShowHintInField(true);

		academicTermCombo = new ComboBox();
		academicTermCombo.setTitle("AcademicTerm");
		academicTermCombo.setHint("AcademicTerm");
		academicTermCombo.setShowHintInField(true);

		districtCombo = new ComboBox();
		districtCombo.setTitle("District");
		districtCombo.setHint("District");
		districtCombo.setShowHintInField(true);

		schoolCombo = new ComboBox();
		schoolCombo.setTitle("School");
		schoolCombo.setHint("School");
		schoolCombo.setShowHintInField(true);

		form.setFields(academicYearCombo, districtCombo, academicTermCombo, schoolCombo);

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
