package com.planetsystems.tela.managementapp.client.presenter.academicyear.schoolCalendar;

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

public class PublicHolidayWindow extends Window {

	private TextField description;
	private DateItem publicHolidayDate;

	private IButton saveButton;
	private IButton cancelButton;

	public PublicHolidayWindow() {
		super();

		description = new TextField();
		description.setTitle("Description");
		description.setHint("Description");
		description.setShowHintInField(true);

		publicHolidayDate = new DateItem();
		publicHolidayDate.setTitle("Date");

		saveButton = new IButton("Ok");
		cancelButton = new IButton("Cancel");
		cancelButton.setBaseStyle("cancel-button");

		DynamicForm form = new DynamicForm();
		form.setFields(description, publicHolidayDate);
		form.setWrapItemTitles(false);
		form.setMargin(10);
		form.setColWidths("150", "250");
		form.setCellPadding(10);
		form.setAutoFocus(true);

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

		layout.setMargin(10);
		this.addItem(layout);
		this.setWidth("40%");
		this.setHeight("40%");
		this.setAutoCenter(true);
		this.setTitle("Public Holiday");
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

	public TextField getDescription() {
		return description;
	}

	public DateItem getPublicHolidayDate() {
		return publicHolidayDate;
	}

	public IButton getSaveButton() {
		return saveButton;
	}

	public IButton getCancelButton() {
		return cancelButton;
	}

}
