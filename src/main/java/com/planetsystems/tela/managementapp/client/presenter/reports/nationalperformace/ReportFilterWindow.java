package com.planetsystems.tela.managementapp.client.presenter.reports.nationalperformace;

import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class ReportFilterWindow extends Window {

	private ComboBox year;
	private ComboBox period; 
	private DateItem fromDate;
	private DateItem toDate;

	private IButton saveButton;
	private IButton cancelButton;

	public ReportFilterWindow() {
		super();

		year = new ComboBox();
		year.setTitle("Year");

		period = new ComboBox();
		period.setTitle("Period");
 
		fromDate = new DateItem();
		fromDate.setTitle("From");

		toDate = new DateItem();
		toDate.setTitle("To");

		saveButton = new IButton("Ok");
		cancelButton = new IButton("Cancel");
		cancelButton.setBaseStyle("cancel-button");

		DynamicForm form = new DynamicForm();
		form.setFields(year, period, fromDate, toDate);
		form.setWrapItemTitles(false);
		form.setMargin(10);
		form.setColWidths(100, 250);

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
		this.setHeight("40%");
		this.setAutoCenter(true);
		this.setTitle("Report Filter");
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

	public ComboBox getYear() {
		return year;
	}

	public ComboBox getPeriod() {
		return period;
	}

	public IButton getSaveButton() {
		return saveButton;
	}

	public IButton getCancelButton() {
		return cancelButton;
	}
 

	public DateItem getFromDate() {
		return fromDate;
	}

	public DateItem getToDate() {
		return toDate;
	}

}
