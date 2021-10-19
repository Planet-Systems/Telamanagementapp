package com.planetsystems.tela.managementapp.client.presenter.staffdailyattendancesupervision;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class CommentWindow extends Window{
    private TextAreaItem commenTextAreaItem;
    private IButton saveButton;
	private IButton closeButton;
	
	public CommentWindow() {
		super();
		
		saveButton = new IButton("Save");
		
		closeButton = new IButton("Close");
		
		commenTextAreaItem = new TextAreaItem();
		commenTextAreaItem.setColSpan(2);
		commenTextAreaItem.setTitle("Comment");
		
		DynamicForm form = new DynamicForm();
		form.setFields(commenTextAreaItem);
		form.setWrapItemTitles(false);
		form.setMargin(10);
		form.setColWidths("150","250");
		form.setCellPadding(10);
		
		HLayout buttonLayout = new HLayout();
		buttonLayout.setMembers(closeButton, saveButton);
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
		this.setTitle("Comment");
		this.setIsModal(true);
		this.setShowModalMask(true);
		cancel(this);
	}

	private void cancel(final Window window) {
		closeButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				window.close();
			}
		});
	}

	public TextAreaItem getCommenTextAreaItem() {
		return commenTextAreaItem;
	}

	public IButton getSaveButton() {
		return saveButton;
	}

	

	
	
}
