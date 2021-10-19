package com.planetsystems.tela.managementapp.client.presenter.staffattendance.clockin;

import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class ClockInPane extends VLayout {

private ClockInListGrid clockInListGrid;

private TextItem districtField;
private TextItem schoolField;
private TextItem academicYearField;
private TextItem academicTermField;
private TextItem dateField;
private DynamicForm filterParams;

	public ClockInPane() {
		super();
		Label header = new Label();
		
		header.setStyleName("crm-ContextArea-Header-Label");
		header.setContents("ClockIn");
		header.setWidth("100%");
		header.setAutoHeight();
		header.setMargin(10);
		header.setAlign(Alignment.LEFT);
		
		districtField = new TextItem();
		districtField.setTitle("District");
		districtField.setShowHintInField(true);
		districtField.disable();
		
		
		schoolField = new TextItem();
		schoolField.setTitle("School");
		schoolField.setShowHintInField(true);
		schoolField.disable();
		
		academicYearField = new TextItem();
		academicYearField.setTitle("Academic year");
		academicYearField.setShowHintInField(true);
		academicYearField.disable();
		
		academicTermField = new TextItem();
		academicTermField.setTitle("AcademicTerm");
		academicTermField.setShowHintInField(true);
		academicTermField.disable();
		
		dateField = new TextItem();
		dateField.setTitle("Today");
		dateField.disable();
		
		
		filterParams = new DynamicForm();
		filterParams.setFields(academicYearField,academicTermField  ,districtField , schoolField , dateField );
		filterParams.setWrapItemTitles(false);
		filterParams.setCellPadding(10);
		filterParams.setNumCols(4);
		filterParams.setColWidths("150" , "250" , "150" , "250" , "150" , "250" , "150" , "250" , "150" , "250" , "150" , "250");
		filterParams.hide();


		clockInListGrid = new ClockInListGrid();

		VLayout layout = new VLayout();
		layout.addMember(filterParams);
		layout.addMember(clockInListGrid);
		this.addMember(layout);
		
	}

	public ClockInListGrid getClockInListGrid() {
		return clockInListGrid;
	}

	public TextItem getDistrictField() {
		return districtField;
	}

	public void setDistrictField(TextItem districtField) {
		this.districtField = districtField;
	}

	public TextItem getSchoolField() {
		return schoolField;
	}

	public void setSchoolField(TextItem schoolField) {
		this.schoolField = schoolField;
	}

	public TextItem getAcademicYearField() {
		return academicYearField;
	}

	public void setAcademicYearField(TextItem academicYearField) {
		this.academicYearField = academicYearField;
	}

	public TextItem getAcademicTermField() {
		return academicTermField;
	}

	public void setAcademicTermField(TextItem academicTermField) {
		this.academicTermField = academicTermField;
	}

	public TextItem getDateField() {
		return dateField;
	}

	public void setDateField(TextItem dateField) {
		this.dateField = dateField;
	}

	public void setClockInListGrid(ClockInListGrid clockInListGrid) {
		this.clockInListGrid = clockInListGrid;
	}

	public DynamicForm getFilterParams() {
		return filterParams;
	}

	public void setFilterParams(DynamicForm filterParams) {
		this.filterParams = filterParams;
	}

	
	
	
	
	
}
