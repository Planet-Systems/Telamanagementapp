package com.planetsystems.tela.managementapp.client.presenter.reports.schoolperformance.daily;

import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class FilterClockInSummaryWindow extends Window  {

	private IButton fetchButton;
	private IButton cancelButton;
	
	private ComboBox academicYearCombo;
	private ComboBox academicTermCombo;
	private ComboBox regionCombo;
	private ComboBox districtCombo;
	private ComboBox schoolCombo;
	private ComboBox schoolStaffCombo;
	private DateItem fromDateItem;
	private DateItem toDateItem;

	public FilterClockInSummaryWindow() {
		super();

		fetchButton = new IButton("Fetch");

		cancelButton = new IButton("Close");
		cancelButton.setBaseStyle("cancel-button");

		HLayout buttonLayout = new HLayout();
		buttonLayout.setMembers(cancelButton, fetchButton);
		buttonLayout.setAutoHeight();
		buttonLayout.setAutoWidth();
		buttonLayout.setMargin(5);
		buttonLayout.setMembersMargin(4);
		buttonLayout.setLayoutAlign(Alignment.CENTER);

		DynamicForm form = new DynamicForm();
		academicYearCombo = new ComboBox();
		academicYearCombo.setTitle("AcademicYear");
		academicYearCombo.setHint("Year");
		academicYearCombo.setShowHintInField(true);

		academicTermCombo = new ComboBox();
		academicTermCombo.setTitle("AcademicTerm");
		academicTermCombo.setHint("AcademicTerm");
		academicTermCombo.setShowHintInField(true);

		regionCombo = new ComboBox();
		regionCombo.setTitle("Region");
		regionCombo.setHint("region");
		regionCombo.setShowHintInField(true);
		
		districtCombo = new ComboBox();
		districtCombo.setTitle("District");
		districtCombo.setHint("District");
		districtCombo.setShowHintInField(true);

		schoolCombo = new ComboBox();
		schoolCombo.setTitle("School");
		schoolCombo.setHint("School");
		schoolCombo.setShowHintInField(true);
		
		schoolStaffCombo = new ComboBox();
		schoolStaffCombo.setTitle("Staff");
		schoolStaffCombo.setHint("staff");
		schoolStaffCombo.setShowHintInField(true);
		
		fromDateItem = new DateItem();
		fromDateItem.setTitle("From");
		
		
		toDateItem = new DateItem();
		toDateItem.setTitle("To");
		
				
				
		//toDateItem
		form.setFields(academicYearCombo, regionCombo , academicTermCombo , districtCombo , schoolStaffCombo , schoolCombo , fromDateItem  );
		form.setWrapItemTitles(false);
		form.setMargin(10);
		//, "150", "250"
		form.setColWidths("150", "250" , "150", "250" , "150", "250" , "150", "250" , "150", "250" , "150", "250" ,"150", "250" );
		form.setCellPadding(10);
		form.setNumCols(4);

		VLayout layout = new VLayout();
		layout.addMember(form);
		layout.addMember(buttonLayout);

		layout.setMargin(10);
		this.addItem(layout);
		this.setWidth("70%");
		this.setHeight("60%");
		this.setAutoCenter(true);
		this.setTitle("Teacher ClockIn Summary");
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

	public IButton getFetchButton() {
		return fetchButton;
	}

	public ComboBox getAcademicYearCombo() {
		return academicYearCombo;
	}

	public ComboBox getAcademicTermCombo() {
		return academicTermCombo;
	}

	public ComboBox getRegionCombo() {
		return regionCombo;
	}

	public ComboBox getDistrictCombo() {
		return districtCombo;
	}

	public ComboBox getSchoolCombo() {
		return schoolCombo;
	}

	public ComboBox getSchoolStaffCombo() {
		return schoolStaffCombo;
	}

	public DateItem getFromDateItem() {
		return fromDateItem;
	}

	public DateItem getToDateItem() {
		return toDateItem;
	}
	
	

}
