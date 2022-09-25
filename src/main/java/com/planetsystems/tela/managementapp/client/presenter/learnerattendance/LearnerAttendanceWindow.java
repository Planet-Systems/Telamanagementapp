package com.planetsystems.tela.managementapp.client.presenter.learnerattendance;

import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class LearnerAttendanceWindow extends Window {
	
	private ComboBox academicYearCombo;
	private ComboBox academicTermCombo;

	private ComboBox schoolCombo;
	private ComboBox districtCombo;
	private ComboBox schoolClassCombo;
	private ComboBox schoolStaffCombo;

	private TextAreaItem commentField;
	private TextItem girlsPresentField;
	private TextItem boysPresentField;
	private TextItem boysAbsentField;
	private TextItem girlsAbsentField;
	private TextItem totalAbsentField;
	private TextItem totalPresentField;
	

	private IButton saveButton;
	private IButton cancelButton;

	public LearnerAttendanceWindow() {
		
		commentField = new TextAreaItem();
		commentField.setTitle("Comment");
		commentField.setHint("Comment on attendance");
		commentField.setShowHintInField(true);
		commentField.setRowSpan(2);
		
		girlsAbsentField = new TextItem();
		girlsAbsentField.setTitle("GirlsAbsent");
		girlsAbsentField.setHint("GirlsAbsent");
		girlsAbsentField.setShowHintInField(true);
		
		
		boysAbsentField = new TextItem();
		boysAbsentField.setTitle("BoysAbsent");
		boysAbsentField.setHint("BoysAbsent");
		boysAbsentField.setShowHintInField(true);
		
		girlsPresentField = new TextItem();
		girlsPresentField.setTitle("GirlsPresent");
		girlsPresentField.setHint("GirlsPresent");
		girlsPresentField.setShowHintInField(true);
		
		boysPresentField = new TextItem();
		boysPresentField.setTitle("BoysPresent");
		boysPresentField.setHint("BoysPresent");
		boysPresentField.setShowHintInField(true);
		
		totalAbsentField = new TextItem();
		totalAbsentField.setTitle("TotalAbsent");
		totalAbsentField.disable();
		totalAbsentField.setHint("TotalAbsent: 0");
		totalAbsentField.setShowHintInField(true);
		
		
		totalPresentField = new TextItem();
		totalPresentField.setTitle("TotalPresent");
		totalPresentField.disable();
		totalPresentField.setHint("TotalPresent: 0");
		totalPresentField.setShowHintInField(true);
		
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
		districtCombo.setHint("Select");
		districtCombo.setShowHintInField(true);
		
		schoolCombo = new ComboBox();
		schoolCombo.setTitle("School");
		schoolCombo.setHint("school");
		schoolCombo.setShowHintInField(true);
		
		
		
		schoolStaffCombo = new ComboBox();
		schoolStaffCombo.setTitle("Staff");
		schoolStaffCombo.setHint("Staff");
		schoolStaffCombo.setShowHintInField(true);
		
		schoolClassCombo = new ComboBox();
		schoolClassCombo.setTitle("Class");
		schoolClassCombo.setHint("Class");
		schoolClassCombo.setShowHintInField(true);
       
		
		DynamicForm dynamicForm = new DynamicForm();
		dynamicForm.setFields(academicYearCombo ,districtCombo , academicTermCombo , schoolCombo , schoolClassCombo ,schoolStaffCombo , 
				boysPresentField , boysAbsentField , girlsPresentField , girlsAbsentField , commentField , totalAbsentField , totalPresentField);
		dynamicForm.setWrapItemTitles(false);
		dynamicForm.setMargin(10);
		dynamicForm.setCellPadding(7);
		dynamicForm.setColWidths("80", "220" , "80", "220");
		dynamicForm.setNumCols(4);
		
		
		saveButton = new IButton("Save");
		cancelButton = new IButton("Cancel");
	
		HLayout buttonLayout = new HLayout();
		buttonLayout.setMembers(cancelButton , saveButton);
		buttonLayout.setAutoHeight();
		buttonLayout.setAutoWidth();
		buttonLayout.setMargin(5);
		buttonLayout.setMembersMargin(4);
		
		
		buttonLayout.setLayoutAlign(Alignment.CENTER);
		
		VLayout layout = new VLayout();
		layout.addMember(dynamicForm);
		layout.addMember(buttonLayout);
		layout.setMembersMargin(10);
		layout.setMargin(10);
		
		
		this.addItem(layout);
		
	
		this.setWidth("60%");
		this.setHeight("80%");
		this.setAutoCenter(true);
		this.setTitle("Learner Attendance");
		this.setIsModal(true);
		this.setShowModalMask(true);
		closeWindow(this);
	}
	
	private void closeWindow(final Window window) {
		cancelButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				window.close();
			}
		});
	}

	public ComboBox getAcademicYearCombo() {
		return academicYearCombo;
	}

	public ComboBox getAcademicTermCombo() {
		return academicTermCombo;
	}

	public ComboBox getSchoolCombo() {
		return schoolCombo;
	}

	public ComboBox getDistrictCombo() {
		return districtCombo;
	}

	public ComboBox getSchoolClassCombo() {
		return schoolClassCombo;
	}

	public ComboBox getSchoolStaffCombo() {
		return schoolStaffCombo;
	}

	public TextAreaItem getCommentField() {
		return commentField;
	}

	public TextItem getGirlsPresentField() {
		return girlsPresentField;
	}

	public TextItem getBoysPresentField() {
		return boysPresentField;
	}

	public TextItem getBoysAbsentField() {
		return boysAbsentField;
	}

	public TextItem getGirlsAbsentField() {
		return girlsAbsentField;
	}

	public TextItem getTotalAbsentField() {
		return totalAbsentField;
	}

	public TextItem getTotalPresentField() {
		return totalPresentField;
	}

	public IButton getSaveButton() {
		return saveButton;
	}

	public IButton getCancelButton() {
		return cancelButton;
	}

	
	
}
