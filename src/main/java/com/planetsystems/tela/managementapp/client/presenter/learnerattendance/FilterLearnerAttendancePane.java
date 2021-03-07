package com.planetsystems.tela.managementapp.client.presenter.learnerattendance;

import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.HLayout;

public class FilterLearnerAttendancePane extends HLayout {

	private ComboBox districtCombo;
	private ComboBox schoolCombo;
	private ComboBox academicYearCombo;
	private ComboBox academicTermCombo;
	private DateItem clockinDateItem;
	
	private IButton filterButton;
	
	public static final String DISTRICT_ID = "DISTRICT_ID";
	public static final String SCHOOL_ID = "SCHOOL_ID";
	public static final String ACADEMIC_YEAR_ID = "ACADEMIC_YEAR_ID";
	public static final String ACADEMIC_TERM_ID = "ACADEMIC_TERM_ID";
	
	public FilterLearnerAttendancePane() {
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
		form.setNumCols(4);
		form.setColWidths("50" , "150" , "50" , "150");
		filterButton = new IButton("Filter");
		filterButton.setLayoutAlign(VerticalAlignment.CENTER);
		filterButton.disable();
		disableEnableFilterButton(academicTermCombo , schoolCombo , filterButton);
	
		HLayout layout = new HLayout();
		layout.setAutoHeight();
		layout.setWidth100();
		layout.addMembers(form , filterButton);
		
		
		this.addMember(layout);
		this.setAutoHeight();
		this.setWidth100();
	
	}
	
	
	private void disableEnableFilterButton(final ComboBox academicTermCombo, final ComboBox schoolCombo,final IButton filterButton) {;
	academicTermCombo.addChangedHandler(new ChangedHandler() {

		@Override
		public void onChanged(ChangedEvent event) {

			if (academicTermCombo.getValueAsString() != null && schoolCombo.getValueAsString() != null) {
                filterButton.setDisabled(false);
			}else {
				 filterButton.setDisabled(true);	
			}
		}
	});

	schoolCombo.addChangedHandler(new ChangedHandler() {

		@Override
		public void onChanged(ChangedEvent event) {
			if (academicTermCombo.getValueAsString() != null && schoolCombo.getValueAsString() != null) {
                filterButton.setDisabled(false);
			}else {
				filterButton.setDisabled(true);
			}
		}
	});

}
	

	public ComboBox getDistrictCombo() {
		return districtCombo;
	}

	public ComboBox getSchoolCombo() {
		return schoolCombo;
	}
	
	
	

}
