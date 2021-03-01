package com.planetsystems.tela.managementapp.client.presenter.academicyear;

import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.planetsystems.tela.managementapp.client.widget.TextField;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class AcademicTermWindow extends Window {

	private TextField termCodeField;
	private TextField termNameField;
	private DateItem startDateItem;
	private DateItem endDateItem;
	private ComboBox yearComboBox;

	private IButton saveButton;
	private IButton cancelButton;

	public AcademicTermWindow() {
		super();
		termCodeField = new TextField();
		termCodeField.setTitle("Code");

		termNameField = new TextField();
		termNameField.setTitle("Academic term");
		
		startDateItem = new DateItem();
		startDateItem.setTitle("Start date");
		
		endDateItem = new DateItem();
		endDateItem.setTitle("End date");

		saveButton = new IButton("Save");
		cancelButton = new IButton("Cancel");
		cancelButton.setBaseStyle("cancel-button");
		
		yearComboBox = new ComboBox();
		yearComboBox.setTitle("AcademicYear");

		DynamicForm form = new DynamicForm();
		form.setFields(yearComboBox , termCodeField, termNameField , startDateItem , endDateItem);
		form.setWrapItemTitles(false);
		form.setMargin(10);
		
		form.setColWidths("150","250");
		form.setCellPadding(10);
		

		HLayout buttonLayout = new HLayout();
		buttonLayout.setMembers(cancelButton , saveButton);
		buttonLayout.setAutoHeight();
		buttonLayout.setAutoWidth();
		buttonLayout.setMargin(5);
		buttonLayout.setMembersMargin(4);
		
		buttonLayout.setLayoutAlign(Alignment.CENTER);

		VLayout layout = new VLayout();
		layout.addMember(form);
		layout.addMember(buttonLayout);

		layout.setMargin(10);
		this.addItem(layout);
		this.setWidth("40%");
		this.setHeight("60%");
		this.setAutoCenter(true);
		this.setTitle("Academic Term");
		this.setIsModal(true);
		this.setShowModalMask(true);
		cancel(this);
	}
	
	private void cancel(final Window window) {
		cancelButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				window.close();
			}
		});
	}

	public TextField getTermCodeField() {
		return termCodeField;
	}

	public TextField getTermNameField() {
		return termNameField;
	}

	public DateItem getStartDateItem() {
		return startDateItem;
	}

	public DateItem getEndDateItem() {
		return endDateItem;
	}

	public ComboBox getYearComboBox() {
		return yearComboBox;
	}

	public IButton getSaveButton() {
		return saveButton;
	}

	public IButton getCancelButton() {
		return cancelButton;
	}


	
	

}
