package com.planetsystems.tela.managementapp.client.presenter.schoolcategory;

import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.planetsystems.tela.managementapp.client.widget.TextField;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class SchoolClassWindow extends Window {

	private TextField classCode;
	private TextField cName;
	private ComboBox school;
	private ComboBox academicTerm;
	

	private IButton saveButton;
	private IButton cancelButton;

	public SchoolClassWindow() {
		super();
		classCode = new TextField();
		classCode.setTitle("Code");

		cName = new TextField();
		cName.setTitle("Class");

		school = new ComboBox();
		school.setTitle("School");
		
		academicTerm = new ComboBox();
		academicTerm.setTitle("Term");

		saveButton = new IButton("Save");
		
		cancelButton = new IButton("Cancel");
		cancelButton.setBaseStyle("cancel-button");

		DynamicForm form = new DynamicForm();
		form.setFields(academicTerm , school ,classCode, cName );
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
		this.setTitle("Classes");
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

	public TextField getClassCode() {
		return classCode;
	}


	public ComboBox getSchool() {
		return school;
	}

	public IButton getSaveButton() {
		return saveButton;
	}

	public TextField getcName() {
		return cName;
	}

	public ComboBox getAcademicTerm() {
		return academicTerm;
	}


	
	

	




}
