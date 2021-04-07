package com.planetsystems.tela.managementapp.client.presenter.staffattendance;

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

public class ClockInWindow extends Window {


	private ComboBox academicYearCombo;

	private ComboBox academicTermCombo;
	
	private ComboBox districtCombo;
	private ComboBox schoolCombo;

	private ComboBox schoolStaffCombo;

	private TextAreaItem commentField;
	private TextItem latitudeField;
	private TextItem longitudeField;

	private IButton saveButton;
	private IButton cancelButton;

	public ClockInWindow() {
		
		commentField = new TextAreaItem();
		commentField.setTitle("Comment");
		commentField.setRowSpan(2);
		commentField.setHint("Comment");
		commentField.setShowHintInField(true);
		
		latitudeField = new TextItem();
		latitudeField.setTitle("Latitude");
		latitudeField.setHint("Latitude");
		latitudeField.setShowHintInField(true);
		
		longitudeField = new TextItem();
		longitudeField.setTitle("longitude");
		longitudeField.setHint("longitude");
		longitudeField.setShowHintInField(true);
		
		academicYearCombo = new ComboBox();
		academicYearCombo.setTitle("AcademicYear");
		academicYearCombo.setHint("academicYear");
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
		
		schoolStaffCombo = new ComboBox();
		schoolStaffCombo.setTitle("Staff");
		schoolStaffCombo.setHint("Staff");
		schoolStaffCombo.setShowHintInField(true);
       
		
		DynamicForm form = new DynamicForm();
		form.setFields(academicYearCombo ,districtCombo, academicTermCombo  , schoolCombo, latitudeField ,schoolStaffCombo, longitudeField  , commentField);
		form.setWrapItemTitles(false);
		form.setMargin(10);
		form.setCellPadding(7);
		form.setColWidths("150", "250" , "150", "250" , "150", "250" , "150", "250" , "150", "250" , "100", "250");
		form.setNumCols(4);
		
		
		saveButton = new IButton("Save");
		cancelButton = new IButton("Cancel");
	
		HLayout buttonLayout = new HLayout();
		buttonLayout.setMembers(cancelButton , saveButton);
		buttonLayout.setAutoHeight();
		buttonLayout.setMargin(5);
		buttonLayout.setMembersMargin(4);
		buttonLayout.setAutoWidth();
		
		buttonLayout.setLayoutAlign(Alignment.CENTER);
		
		VLayout layout = new VLayout();
		layout.addMember(form);
		layout.addMember(buttonLayout);
		layout.setMembersMargin(10);
		layout.setMargin(10);
		
		
		this.addItem(layout);
		
	
		this.setWidth("70%");
		this.setHeight("80%");
		this.setAutoCenter(true);
		this.setTitle("Clock In");
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

	public ComboBox getDistrictCombo() {
		return districtCombo;
	}

	public ComboBox getSchoolCombo() {
		return schoolCombo;
	}

	public ComboBox getSchoolStaffCombo() {
		return schoolStaffCombo;
	}

	public TextAreaItem getCommentField() {
		return commentField;
	}

	public TextItem getLatitudeField() {
		return latitudeField;
	}

	public TextItem getLongitudeField() {
		return longitudeField;
	}

	public IButton getSaveButton() {
		return saveButton;
	}

	public IButton getCancelButton() {
		return cancelButton;
	}

	
	
}
