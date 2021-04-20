package com.planetsystems.tela.managementapp.client.presenter.schoolcategory.schoolcategory;

import com.planetsystems.tela.managementapp.client.widget.TextField;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class SchoolCategoryWindow extends Window {

	private TextField categoryCode;
	private TextField categoryName;

	private IButton saveButton;
	private IButton cancelButton;

	public SchoolCategoryWindow() {
		super();
		categoryCode = new TextField();
		categoryCode.setTitle("Code");
		categoryCode.setHint("Category");
		categoryCode.setShowHintInField(true);

		categoryName = new TextField();
		categoryName.setTitle("Category");
		categoryName.setHint("Category");
		categoryName.setShowHintInField(true);

		saveButton = new IButton("Save");

		cancelButton = new IButton("Cancel");
		cancelButton.setBaseStyle("cancel-button");

		DynamicForm form = new DynamicForm();
		form.setFields(categoryCode, categoryName);
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
		this.setHeight("40%");
		this.setAutoCenter(true);
		this.setTitle("School category");
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
	
	public TextField getCategoryCode() {
		return categoryCode;
	}

	public TextField getCategoryName() {
		return categoryName;
	}

	public IButton getSaveButton() {
		return saveButton;
	}

}
