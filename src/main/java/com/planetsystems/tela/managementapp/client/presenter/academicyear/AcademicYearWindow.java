package com.planetsystems.tela.managementapp.client.presenter.academicyear;

import com.planetsystems.tela.managementapp.client.widget.TextField;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class AcademicYearWindow extends Window {

	private TextField yearCode;
	private TextField yearName;
	private DateItem startDate;
	private DateItem endDate;

	private IButton saveButton;
	private IButton cancelButton;

	public AcademicYearWindow() {
		super();
		
		
		yearCode = new TextField();
		yearCode.setTitle("Code");

		yearName = new TextField();
		yearName.setTitle("Academic year");
		
		startDate = new DateItem();
		startDate.setTitle("Start date");
		
		endDate = new DateItem();
		endDate.setTitle("End date");

		saveButton = new IButton("Save");
		
		cancelButton = new IButton("Cancel");
		cancelButton.setBaseStyle("cancel-button");

		DynamicForm form = new DynamicForm();
		form.setFields(yearCode, yearName , startDate , endDate);
		form.setWrapItemTitles(false);
		form.setMargin(10);

		HLayout buttonLayout = new HLayout();
		buttonLayout.setMembers(cancelButton , saveButton);
		buttonLayout.setAutoHeight();
		buttonLayout.setWidth100();
		buttonLayout.setMargin(5);
		buttonLayout.setMembersMargin(4);

		VLayout layout = new VLayout();
		layout.addMember(form);
		layout.addMember(buttonLayout);

		layout.setMargin(10);
		this.addItem(layout);
		this.setWidth("40%");
		this.setHeight("40%");
		this.setAutoCenter(true);
		this.setTitle("Academic year");
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

	public TextField getYearCode() {
		return yearCode;
	}

	public TextField getYearName() {
		return yearName;
	}

	public IButton getSaveButton() {
		return saveButton;
	}

	public DateItem getStartDate() {
		return startDate;
	}

	public DateItem getEndDate() {
		return endDate;
	}

	public IButton getCancelButton() {
		return cancelButton;
	}


	
	
}