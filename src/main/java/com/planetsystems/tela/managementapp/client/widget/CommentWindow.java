package com.planetsystems.tela.managementapp.client.widget;

import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class CommentWindow extends Window {

	private TextAreaItem comment;
	private IButton saveButton;
	private IButton cancelButton;

	public CommentWindow() {
		super();

		comment = new TextAreaItem();
		comment.setTitle("General Comment");
		comment.setShowTitle(true);
		comment.setWidth("*");
		comment.setHeight(100);

		saveButton = new IButton("Ok");
		cancelButton = new IButton("Cancel");

		DynamicForm formContainer = new DynamicForm();
		formContainer.setFields(comment);
		formContainer.setMargin(20);
		formContainer.setColWidths("150", "400");

		HLayout layout2 = new HLayout();
		layout2.setMembers(saveButton, cancelButton);
		layout2.setMembersMargin(5);
		layout2.setStyleName("window-button-margin");

		VLayout widgetLayout = new VLayout();
		widgetLayout.addMember(formContainer);
		widgetLayout.addMember(layout2);

		this.addItem(widgetLayout);
		this.setWidth("40%");
		this.setHeight("40%");
		this.setAutoCenter(true);
		this.setTitle("General Comment");
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

	public TextAreaItem getComment() {
		return comment;
	}

	public IButton getSaveButton() {
		return saveButton;
	}

}
