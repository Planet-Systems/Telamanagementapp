package com.planetsystems.tela.managementapp.client.presenter.staffattendance.clockin;

import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;

public class FilterClockInPane extends HLayout {

	private ComboBox districtCombo;
	private ComboBox schoolCombo;
	private ComboBox academicYearCombo;
	private ComboBox academicTermCombo;
	//private TextItem todayDateField;
	private DateItem toDateItem;

	
//	public static final String DISTRICT_ID = "DISTRICT_ID";
//	public static final String SCHOOL_ID = "SCHOOL_ID";
//	public static final String ACADEMIC_YEAR_ID = "ACADEMIC_YEAR_ID";
//	public static final String ACADEMIC_TERM_ID = "ACADEMIC_TERM_ID";
	
	public FilterClockInPane() {
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
		
		//todayDateField = new TextItem();
		//todayDateField.setTitle("Today");
		//todayDateField.disable();
		
		toDateItem = new DateItem();
		toDateItem.setTitle("Date");

		DynamicForm form = new DynamicForm();
		form.setFields(academicYearCombo,academicTermCombo  ,districtCombo , schoolCombo , toDateItem);
		form.setWrapItemTitles(false);
		form.setCellPadding(10);
		form.setNumCols(2);
		form.setColWidths("150" , "250" , "150" , "250" , "150" , "250" , "150" , "250" , "150" , "250" , "150" , "250");
	 
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


	public DateItem getToDateItem() {
		return toDateItem;
	}


	/*public TextItem getTodayDateField() {
		return todayDateField;
	}
*/
	
	

	

}
