package com.planetsystems.tela.managementapp.client.presenter.staffattendance.clockout;

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


public class ClockOutWindow extends Window {
	private ComboBox academicTermCombo;

	private ComboBox schoolStaffCombo;
	private TextAreaItem commentField;
	private TextItem latitudeField;
	private TextItem longitudeField;

	private IButton saveButton;
	private IButton cancelButton;

	public ClockOutWindow() {
		
		commentField = new TextAreaItem();
		commentField.setTitle("Comment");
		commentField.setRowSpan(2);
		
		latitudeField = new TextItem();
		latitudeField.setTitle("Latitude");
		
		longitudeField = new TextItem();
		longitudeField.setTitle("longitude");
		
		academicTermCombo = new ComboBox();
		academicTermCombo.setTitle("AcademicTerm");
		
		schoolStaffCombo = new ComboBox();
		schoolStaffCombo.setTitle("Staff");
       
		
		DynamicForm dynamicForm = new DynamicForm();
		dynamicForm.setFields(academicTermCombo , schoolStaffCombo , latitudeField , longitudeField , commentField);
		dynamicForm.setWrapItemTitles(false);
		dynamicForm.setMargin(10);
		dynamicForm.setCellPadding(7);
		dynamicForm.setColWidths("60", "220");
		
		
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
		
	
		this.setWidth("40%");
		this.setHeight("70%");
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

	public ComboBox getAcademicTermCombo() {
		return academicTermCombo;
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
