package com.planetsystems.tela.managementapp.client.presenter.subjectcategory;

import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.planetsystems.tela.managementapp.client.widget.TextField;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class SubjectWindow extends Window {

	private TextField subjectCode;
	private TextField subjectName;
	private ComboBox subjectCategory;

	private IButton saveButton;
	private IButton cancelButton;

	public SubjectWindow() {
		super();
		subjectCode = new TextField();
		subjectCode.setTitle("Code");

		subjectName = new TextField();
		subjectName.setTitle("Subject");

		subjectCategory = new ComboBox();
		subjectCategory.setTitle("Category");

		saveButton = new IButton("Save");
		
		cancelButton = new IButton("Cancel");
		cancelButton.setBaseStyle("cancel-button");

		DynamicForm form = new DynamicForm();
		form.setFields(subjectCategory , subjectCode, subjectName);
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
		this.setHeight("35%");
		this.setAutoCenter(true);
		this.setTitle("Subject");
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

	public TextField getSubjectCode() {
		return subjectCode;
	}

	public TextField getSubjectName() {
		return subjectName;
	}

	public ComboBox getSubjectCategory() {
		return subjectCategory;
	}

	public IButton getSaveButton() {
		return saveButton;
	}


	
	
}
