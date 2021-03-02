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
	/*
	 * 
	 * private SchoolClassDTO schoolClassDTO; private long totalBoys; private long
	 * totalGirls; private String status;
	 */

	private ComboBox schoolClassComboBox;

	private TextItem totalBoysField;
	private TextItem totalGirlsField;
	private TextItem learnerTotalField;
	// private TextItem statusField; set from server side

	private IButton saveButton;
	private IButton cancelButton;

	public LearnerEnrollmentWindow() {

		totalBoysField = new TextItem();
		totalBoysField.setTitle("TotalBoys");
		totalBoysField.setHint("TotalBoys");
		totalBoysField.setShowHintInField(true);

		totalGirlsField = new TextItem();
		totalGirlsField.setTitle("TotalGirls");
		totalGirlsField.setHint("TotalGirls");
		totalGirlsField.setShowHintInField(true);

		learnerTotalField = new TextItem();
		learnerTotalField.setTitle("Total");
		learnerTotalField.setValue(0);
		learnerTotalField.disable();
		learnerTotalField.setHint("Total: 0");
		learnerTotalField.setShowHintInField(true);

		schoolClassComboBox = new ComboBox();
		schoolClassComboBox.setTitle("Class");
		schoolClassComboBox.setHint("Class");
		schoolClassComboBox.setShowHintInField(true);

		DynamicForm dynamicForm = new DynamicForm();
		dynamicForm.setFields(schoolClassComboBox, totalBoysField, totalGirlsField, learnerTotalField);
		dynamicForm.setWrapItemTitles(false);
		dynamicForm.setMargin(10);
		dynamicForm.setColWidths("150", "250");
		dynamicForm.setCellPadding(10);

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
		layout.addMember(dynamicForm);
		layout.addMember(buttonLayout);
		layout.setMembersMargin(10);
		layout.setMargin(10);

		this.addMember(layout);

		this.setWidth("40%");
		this.setHeight("50%");
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

	public ComboBox getSchoolClassComboBox() {
		return schoolClassComboBox;
	}

	public TextItem getTotalBoysField() {
		return totalBoysField;
	}

	public TextItem getTotalGirlsField() {
		return totalGirlsField;
	}

	public IButton getSaveButton() {
		return saveButton;
	}

	public TextItem getLearnerTotalField() {
		return learnerTotalField;
	}

}
