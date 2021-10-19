package com.planetsystems.tela.managementapp.client.presenter.staffattendance.clockout;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.VLayout;

public class ClockOutPane extends VLayout {

private ClockOutListGrid clockOutListGrid;
private TextItem districtField;
private TextItem schoolField;
private TextItem academicYearField;
private TextItem academicTermField;
private TextItem dateField;
private DynamicForm filterParams;
	
	public ClockOutPane() {
		super();
		Label header = new Label();
		
		header.setStyleName("crm-ContextArea-Header-Label");
		header.setContents("ClockOut");
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

		clockOutListGrid = new ClockOutListGrid();

		VLayout layout = new VLayout();
		layout.addMember(filterParams);
		layout.addMember(clockOutListGrid);
		this.addMember(layout);
		
	}

	public ClockOutListGrid getClockOutListGrid() {
		return clockOutListGrid;
	}

	public TextItem getDistrictField() {
		return districtField;
	}

	public TextItem getSchoolField() {
		return schoolField;
	}

	public TextItem getAcademicYearField() {
		return academicYearField;
	}

	public TextItem getAcademicTermField() {
		return academicTermField;
	}

	public TextItem getDateField() {
		return dateField;
	}

	public DynamicForm getFilterParams() {
		return filterParams;
	}
	
	

	
	
}
