package com.planetsystems.tela.managementapp.client.presenter.staffenrollment;

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

public class StaffEnrollmentWindow extends Window {


	private ComboBox academicYearCombo;
	private ComboBox academicTermCombo;
	private ComboBox districtCombo;
	private ComboBox schoolCombo;

	private TextItem totalMaleField;
	private TextItem totalFemaleField;
	private TextItem staffTotalField;
	// private TextItem statusField; set from server side

	private IButton saveButton;
	private IButton cancelButton;

	public StaffEnrollmentWindow() {
		
		totalMaleField = new TextItem();
		totalMaleField.setTitle("Number Of Male");
		totalMaleField.setHint("Numeric only<br>[0-9.]");          
		totalMaleField.setKeyPressFilter("[0-9.]");
		totalMaleField.setShowHintInField(true);

		
		totalFemaleField = new TextItem();
		totalFemaleField.setTitle("Number Of Female");
		totalFemaleField.setHint("Numeric only<br>[0-9.]");          
		totalFemaleField.setKeyPressFilter("[0-9.]"); 
		totalFemaleField.setShowHintInField(true);
		
		
		academicYearCombo = new ComboBox();
		academicYearCombo.setTitle("Academic Year");
		academicYearCombo.setHint("Academic Year");
		academicYearCombo.setShowHintInField(true);
		
		academicTermCombo = new ComboBox();
		academicTermCombo.setTitle("Academic Term");
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
		
		staffTotalField = new TextItem();
		staffTotalField.setTitle("Total");
		staffTotalField.setValue(0);
		staffTotalField.disable();
		staffTotalField.setHint("Total");
		staffTotalField.setShowHintInField(true);
		
       
		
		DynamicForm dynamicForm = new DynamicForm();
		dynamicForm.setFields(academicYearCombo , academicTermCombo ,districtCombo , schoolCombo , totalMaleField , totalFemaleField , staffTotalField);
		dynamicForm.setWrapItemTitles(false);
		dynamicForm.setMargin(10);
		dynamicForm.setColWidths("100","250");
		dynamicForm.setCellPadding(10);
		
		
		
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
		this.setWidth("45%");
		this.setHeight("75%");
		this.setAutoCenter(true);
		this.setTitle("Staff Enrollment");
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

	public TextItem getTotalMaleField() {
		return totalMaleField;
	}

	public TextItem getTotalFemaleField() {
		return totalFemaleField;
	}

	public TextItem getStaffTotalField() {
		return staffTotalField;
	}

	public IButton getSaveButton() {
		return saveButton;
	}

	public IButton getCancelButton() {
		return cancelButton;
	}

	
	
	

}
