package com.planetsystems.tela.managementapp.client.presenter.dailyattendancedashoard;
 
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class FilterWindow extends Window {

	private DateItem attendanceDate;

	private IButton saveButton;
	private IButton cancelButton;

	public FilterWindow() {
		super();

		attendanceDate = new DateItem();
		attendanceDate.setTitle("Attendance Date");

		saveButton = new IButton("Ok");
		cancelButton = new IButton("Cancel");
		cancelButton.setBaseStyle("cancel-button");

		DynamicForm form = new DynamicForm();
		form.setFields(attendanceDate);
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
		this.setTitle("Attendance Filter");
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

	public DateItem getAttendanceDate() {
		return attendanceDate;
	}

	public IButton getSaveButton() {
		return saveButton;
	}

	public IButton getCancelButton() {
		return cancelButton;
	}

}
