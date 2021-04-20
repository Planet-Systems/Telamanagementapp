package com.planetsystems.tela.managementapp.client.presenter.staffattendance.clockout;

import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.layout.HLayout;

public class FilterClockOutPane extends HLayout {

	private ComboBox districtCombo;
	private ComboBox schoolCombo;
	private ComboBox academicYearCombo;
	private ComboBox academicTermCombo;
	private DateItem clockinDateItem;

	
	public static final String DISTRICT_ID = "DISTRICT_ID";
	public static final String SCHOOL_ID = "SCHOOL_ID";
	public static final String ACADEMIC_YEAR_ID = "ACADEMIC_YEAR_ID";
	public static final String ACADEMIC_TERM_ID = "ACADEMIC_TERM_ID";
	
	public FilterClockOutPane() {
		super();

		districtCombo = new ComboBox();
		districtCombo.setTitle("District");
		districtCombo.setHint("Select A District");
		districtCombo.setShowHintInField(true);
		
		
		schoolCombo = new ComboBox();
		schoolCombo.setTitle("School");
		schoolCombo.setHint("Select A School");
		schoolCombo.setShowHintInField(true);
		
		academicYearCombo = new ComboBox();
		academicYearCombo.setTitle("Academic year");
		academicYearCombo.setHint("Select A Year");
		academicYearCombo.setShowHintInField(true);
		
		academicTermCombo = new ComboBox();
		academicTermCombo.setTitle("AcademicTerm");
		academicTermCombo.setHint("Select A  Term");
		academicTermCombo.setShowHintInField(true);
		
		clockinDateItem = new DateItem();
		clockinDateItem.setTitle("Date");

		DynamicForm form = new DynamicForm();
		form.setFields(academicYearCombo  ,districtCombo ,academicTermCombo, schoolCombo , clockinDateItem);
		form.setWrapItemTitles(false);
		form.setMargin(10);
		form.setCellPadding(10);
		form.setNumCols(2);
		form.setColWidths("80" , "250");
	
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


	public DateItem getClockinDateItem() {
		return clockinDateItem;
	}


}
