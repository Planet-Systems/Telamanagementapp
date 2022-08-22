package com.planetsystems.tela.managementapp.client.presenter.dailyattendancedashoard;

import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class WeeklyFilterWindow extends Window {

	private DateItem fromDate;
	private DateItem toDate;

	private IButton saveButton;
	private IButton cancelButton;

	public WeeklyFilterWindow() {
		super();

		fromDate = new DateItem();
		fromDate.setTitle("From");

		toDate = new DateItem();
		toDate.setTitle("To");

		saveButton = new IButton("Ok");
		cancelButton = new IButton("Cancel");
		cancelButton.setBaseStyle("cancel-button");

		DynamicForm form = new DynamicForm();
		form.setFields(fromDate, toDate);
		form.setWrapItemTitles(true);
		form.setMargin(10);
		form.setColWidths(150, 200);

		HLayout buttonLayout = new HLayout();
		buttonLayout.setMembers(cancelButton, saveButton);
		buttonLayout.setAutoHeight();
		buttonLayout.setWidth100();
		buttonLayout.setMargin(10);
		buttonLayout.setMembersMargin(10);

		VLayout layout = new VLayout();
		layout.addMember(form);
		layout.addMember(buttonLayout);

		layout.setMargin(10);
		this.addItem(layout);
		this.setWidth("30%");
		this.setHeight("30%");
		this.setAutoCenter(true);
		this.setTitle("Weekly Attendance Filter");
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

	public DateItem getFromDate() {
		return fromDate;
	}

	public DateItem getToDate() {
		return toDate;
	}

	public IButton getSaveButton() {
		return saveButton;
	}

}
