package com.planetsystems.tela.managementapp.client.presenter.academicyear.schoolCalendar;

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

public class CalendarWeekWindow extends Window {

	private ComboBox calendarMonth;
	private ComboBox calendarWeek;
	private DateItem startDate;
	private DateItem endDate;
	private TextField expectedHours;

	private IButton saveButton;
	private IButton cancelButton;

	public CalendarWeekWindow() {
		super();

		calendarMonth = new ComboBox();
		calendarMonth.setTitle("Month");
		calendarMonth.setHint("Month");
		calendarMonth.setShowHintInField(true);

		calendarWeek = new ComboBox();
		calendarWeek.setTitle("Week");
		calendarWeek.setHint("Week");
		calendarWeek.setShowHintInField(true);

		startDate = new DateItem();
		startDate.setTitle("Start Date");

		endDate = new DateItem();
		endDate.setTitle("End Date");

		expectedHours = new TextField();
		expectedHours.setTitle("Expected Hours");
		expectedHours.setHint("Expected Hours");
		expectedHours.setShowHintInField(true);

		saveButton = new IButton("Ok");
		cancelButton = new IButton("Cancel");
		cancelButton.setBaseStyle("cancel-button");

		DynamicForm form = new DynamicForm();
		form.setFields(calendarMonth, calendarWeek, startDate, endDate, expectedHours);
		form.setWrapItemTitles(true);
		form.setMargin(10);
		form.setAutoFocus(true);

		form.setColWidths("150", "250", "150", "250", "150", "250", "150", "250", "150", "250");
		form.setCellPadding(10);

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
		this.setWidth("35%");
		this.setHeight("50%");
		this.setAutoCenter(true);
		this.setTitle("Calendar Week");
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

	public ComboBox getCalendarMonth() {
		return calendarMonth;
	}

	public ComboBox getCalendarWeek() {
		return calendarWeek;
	}

	public DateItem getStartDate() {
		return startDate;
	}

	public DateItem getEndDate() {
		return endDate;
	}

	public TextField getExpectedHours() {
		return expectedHours;
	}

	public IButton getSaveButton() {
		return saveButton;
	}

	public IButton getCancelButton() {
		return cancelButton;
	}

}
