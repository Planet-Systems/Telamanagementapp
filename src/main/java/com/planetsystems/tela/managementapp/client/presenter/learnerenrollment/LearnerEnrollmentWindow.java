package com.planetsystems.tela.managementapp.client.presenter.learnerenrollment;

import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class LearnerEnrollmentWindow extends Window {

	private ComboBox schoolClassCombo;
	private ComboBox schoolCombo;
	private ComboBox districtCombo;
	private ComboBox academicYearCombo;
	private ComboBox academicTermCombo;

	private TextItem totalBoysField;
	private TextItem totalGirlsField;
	private TextItem learnerTotalField;

	private IButton saveButton;
	private IButton cancelButton;

	public LearnerEnrollmentWindow() {

		totalBoysField = new TextItem();
		totalBoysField.setTitle("TotalBoys");
		totalBoysField.setHint("TotalBoys");
		totalBoysField.setShowHintInField(true);
		totalBoysField.setKeyPressFilter("[0-9.]");

		totalGirlsField = new TextItem();
		totalGirlsField.setTitle("TotalGirls");
		totalGirlsField.setHint("TotalGirls");
		totalGirlsField.setShowHintInField(true);
		totalGirlsField.setKeyPressFilter("[0-9.]");

		learnerTotalField = new TextItem();
		learnerTotalField.setTitle("Total");
		learnerTotalField.setValue(0);
		learnerTotalField.disable();
		learnerTotalField.setHint("Total: 0");
		learnerTotalField.setShowHintInField(true);

		academicYearCombo = new ComboBox();
		academicYearCombo.setTitle("Academic Year");
		academicYearCombo.setHint("Academic Year");
		academicYearCombo.setShowHintInField(true);
		
		academicTermCombo = new ComboBox();
		academicTermCombo.setTitle("Academic Term");
		academicTermCombo.setHint("Academic Term");
		academicTermCombo.setShowHintInField(true);
		
		districtCombo = new ComboBox();
		districtCombo.setTitle("District");
		districtCombo.setHint("District");
		districtCombo.setShowHintInField(true);
		
		schoolCombo = new ComboBox();
		schoolCombo.setTitle("School");
		schoolCombo.setHint("School");
		schoolCombo.setShowHintInField(true);
		
		schoolClassCombo = new ComboBox();
		schoolClassCombo.setTitle("Class");
		schoolClassCombo.setHint("Class");
		schoolClassCombo.setShowHintInField(true);

		DynamicForm form = new DynamicForm();
		form.setFields(academicYearCombo ,  districtCombo ,academicTermCombo , schoolCombo ,  schoolClassCombo, totalBoysField, totalGirlsField, learnerTotalField);
		form.setWrapItemTitles(false);
		form.setMargin(10);
		form.setColWidths("150", "250" , "150", "250" , "150", "250" , "150", "250"  ,"150", "250");
		form.setCellPadding(10);
		form.setNumCols(4);
		

		saveButton = new IButton("Save");
		cancelButton = new IButton("Cancel");

		HLayout buttonLayout = new HLayout();
		buttonLayout.setMembers(cancelButton, saveButton);
		buttonLayout.setAutoHeight();
		buttonLayout.setAutoWidth();
		buttonLayout.setMargin(5);
		buttonLayout.setMembersMargin(4);

		buttonLayout.setLayoutAlign(Alignment.CENTER);

		VLayout layout = new VLayout();
		layout.addMember(form);
		layout.addMember(buttonLayout);
		layout.setMembersMargin(10);
		layout.setMargin(10);

		this.addItem(layout);
		this.setWidth("70%");
		this.setHeight("60%");
		this.setAutoCenter(true);
		this.setTitle("Learner Enrollment");
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

	public ComboBox getSchoolClassCombo() {
		return schoolClassCombo;
	}

	public ComboBox getSchoolCombo() {
		return schoolCombo;
	}

	public ComboBox getDistrictCombo() {
		return districtCombo;
	}

	public TextItem getTotalBoysField() {
		return totalBoysField;
	}

	public TextItem getTotalGirlsField() {
		return totalGirlsField;
	}

	public TextItem getLearnerTotalField() {
		return learnerTotalField;
	}

	public IButton getSaveButton() {
		return saveButton;
	}

	public IButton getCancelButton() {
		return cancelButton;
	}

	public ComboBox getAcademicYearCombo() {
		return academicYearCombo;
	}

	public ComboBox getAcademicTermCombo() {
		return academicTermCombo;
	}

	
	

}
