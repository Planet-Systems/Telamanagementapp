package com.planetsystems.tela.managementapp.client.presenter.reports.schoolperformance.weekly;

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

public class FilterWeeklyAttendanceWindow extends Window  {

	private IButton saveButton;
	private IButton cancelButton;
	
	private ComboBox academicYearCombo;
	private ComboBox academicTermCombo;
	private ComboBox regionCombo;
	private ComboBox districtCombo;
	private ComboBox schoolCombo;
	private ComboBox monthCombo;
	private ComboBox weekCombo;

	public FilterWeeklyAttendanceWindow() {
		super();

		saveButton = new IButton("Ok");

		cancelButton = new IButton("Cancel");
		cancelButton.setBaseStyle("cancel-button");

		HLayout buttonLayout = new HLayout();
		buttonLayout.setMembers(cancelButton, saveButton);
		buttonLayout.setAutoHeight();
		buttonLayout.setAutoWidth();
		buttonLayout.setMargin(5);
		buttonLayout.setMembersMargin(4);
		buttonLayout.setLayoutAlign(Alignment.CENTER);

		DynamicForm form = new DynamicForm();
		academicYearCombo = new ComboBox();
		academicYearCombo.setTitle("Academic Year");
		academicYearCombo.setHint("Academic Year");
		academicYearCombo.setShowHintInField(true);

		academicTermCombo = new ComboBox();
		academicTermCombo.setTitle("Academic Term");
		academicTermCombo.setHint("Academic Term");
		academicTermCombo.setShowHintInField(true);

		regionCombo = new ComboBox();
		regionCombo.setTitle("Region");
		regionCombo.setHint("Region");
		regionCombo.setShowHintInField(true);
		
		districtCombo = new ComboBox();
		districtCombo.setTitle("District");
		districtCombo.setHint("District");
		districtCombo.setShowHintInField(true);

		schoolCombo = new ComboBox();
		schoolCombo.setTitle("School");
		schoolCombo.setHint("School");
		schoolCombo.setShowHintInField(true);
		
		monthCombo = new ComboBox();
		monthCombo.setTitle("Month");
		monthCombo.setHint("month");
		monthCombo.setShowHintInField(true);
		
		weekCombo = new ComboBox();
		weekCombo.setTitle("Week");
		weekCombo.setHint("Week");
		weekCombo.setShowHintInField(true);
		
		
		
				
				
		
		form.setFields(academicYearCombo , academicTermCombo ,regionCombo, districtCombo , schoolCombo, monthCombo  , weekCombo );
		form.setWrapItemTitles(false);
		form.setColWidths("150", "300"  );
		form.setCellPadding(10);
		form.setNumCols(2);

		VLayout layout = new VLayout();
		layout.addMember(form);
		layout.addMember(buttonLayout);

		layout.setMargin(10);
		this.addItem(layout);
		this.setWidth("40%");
		this.setHeight("60%");
		this.setAutoCenter(true);
		this.setTitle("End of Week ClockIn Summary");
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

	public IButton getSaveButton() {
		return saveButton;
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

	public ComboBox getMonthCombo() {
		return monthCombo;
	}

	public ComboBox getWeekCombo() {
		return weekCombo;
	}

	
	
}
