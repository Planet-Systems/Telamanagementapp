package com.planetsystems.tela.managementapp.client.presenter.enrollment;

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
	/*
	 *   private SchoolDTO schoolDTO;

    private AcademicTermDTO academicTermDTO;


    private long  totalMale;

    private long totalFemale;

    private String status;
	 */
	private ComboBox academicTermComboBox;

	private ComboBox schoolComboBox;

	private TextItem totalMaleField;
	private TextItem totalFemaleField;
	private TextItem staffTotalField;
	// private TextItem statusField; set from server side

	private IButton saveButton;
	private IButton cancelButton;

	public StaffEnrollmentWindow() {
		
		totalMaleField = new TextItem();
		totalMaleField.setTitle("Number Of Male");

		
		totalFemaleField = new TextItem();
		totalFemaleField.setTitle("Number Of Female");
		
		academicTermComboBox = new ComboBox();
		academicTermComboBox.setTitle("AcademicTerm");
		
		schoolComboBox = new ComboBox();
		schoolComboBox.setTitle("School");
		
		staffTotalField = new TextItem();
		staffTotalField.setTitle("Total");
		staffTotalField.setValue(0);
		staffTotalField.disable();
		
       
		
		DynamicForm dynamicForm = new DynamicForm();
		dynamicForm.setFields(academicTermComboBox , schoolComboBox , totalMaleField , totalFemaleField , staffTotalField);
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
		
		
		this.addMember(layout);
		
	
		this.setWidth("40%");
		this.setHeight("60%");
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

	public ComboBox getAcademicTermComboBox() {
		return academicTermComboBox;
	}

	public ComboBox getSchoolComboBox() {
		return schoolComboBox;
	}

	public TextItem getTotalMaleField() {
		return totalMaleField;
	}

	public TextItem getTotalFemaleField() {
		return totalFemaleField;
	}

	public IButton getSaveButton() {
		return saveButton;
	}

	public IButton getCancelButton() {
		return cancelButton;
	}

	public TextItem getStaffTotalField() {
		return staffTotalField;
	}
	
	
	
	
	
	

}
