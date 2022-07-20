package com.planetsystems.tela.managementapp.client.presenter.academicyear.schoolCalendar;

import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.planetsystems.tela.managementapp.client.widget.TextField;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

public class SchoolCalendarWindow extends Window {

	private ComboBox academicYear;
	private ComboBox academicTerm;
	private TextField expectedDailyHours;
	private TextField expectedWeeklyHours;
	private TextField expectedMonthlyHours;
	private TextField expectedTermlyHours;
	private TextField description;

	private CalendarWeeksListgrid schoolCalendarWeeksListgrid;

	private PublicHolidaysListgrid publicHolidayListgrid;

	private IButton saveButton;
	private IButton cancelButton;

	private IButton addWeekButton;
	private IButton addPublicDayButton;

	// addWeekButton,addPublicDayButton

	public SchoolCalendarWindow() {
		super();

		academicYear = new ComboBox();
		academicYear.setTitle("Academic Year");
		academicYear.setHint("Select");
		academicYear.setShowHintInField(true);

		academicTerm = new ComboBox();
		academicTerm.setTitle("Academic Term");
		academicTerm.setHint("Select");
		academicTerm.setShowHintInField(true);

		expectedDailyHours = new TextField();
		expectedDailyHours.setTitle("Expected Daily Hours");
		expectedDailyHours.setHint("Expected Daily Hours");
		expectedDailyHours.setShowHintInField(true);

		expectedWeeklyHours = new TextField();
		expectedWeeklyHours.setTitle("Expected Weekly Hours");
		expectedWeeklyHours.setHint("Expected Weekly Hours");
		expectedWeeklyHours.setShowHintInField(true);

		expectedMonthlyHours = new TextField();
		expectedMonthlyHours.setTitle("Expected Monthly Hours");
		expectedMonthlyHours.setHint("Expected Monthly Hours");
		expectedMonthlyHours.setShowHintInField(true);

		expectedTermlyHours = new TextField();
		expectedTermlyHours.setTitle("Expected Termly Hours");
		expectedTermlyHours.setHint("Expected Termly Hours");
		expectedTermlyHours.setShowHintInField(true);

		description = new TextField();
		description.setTitle("Description");
		description.setHint("Description");
		description.setShowHintInField(true);

		schoolCalendarWeeksListgrid = new CalendarWeeksListgrid();
		schoolCalendarWeeksListgrid.setHeight(280);

		publicHolidayListgrid = new PublicHolidaysListgrid();
		publicHolidayListgrid.setHeight(280);

		saveButton = new IButton("Save");
		cancelButton = new IButton("Cancel");
		cancelButton.setBaseStyle("cancel-button");

		addWeekButton = new IButton("Add");
		addPublicDayButton = new IButton("Add");

		DynamicForm form = new DynamicForm();
		form.setFields(academicYear, academicTerm, expectedDailyHours, expectedWeeklyHours, expectedMonthlyHours,
				expectedTermlyHours,description);
		form.setWrapItemTitles(true);
		form.setMargin(10);
		form.setAutoFocus(true);
		form.setNumCols(4);
		form.setColWidths("150", "250", "150", "250", "150", "250", "150", "250", "150", "250");
		form.setCellPadding(10);

		HLayout buttonLayout = new HLayout();
		buttonLayout.setMembers(cancelButton, saveButton);
		buttonLayout.setAutoHeight();
		buttonLayout.setAutoWidth();
		buttonLayout.setMargin(5);
		buttonLayout.setMembersMargin(4);
		buttonLayout.setLayoutAlign(Alignment.CENTER);

		HLayout buttonLayout1 = new HLayout();
		buttonLayout1.setMembers(addWeekButton);
		buttonLayout1.setAutoHeight();
		buttonLayout1.setAutoWidth();
		buttonLayout1.setMargin(5);
		buttonLayout1.setMembersMargin(4);
		buttonLayout1.setLayoutAlign(Alignment.RIGHT);

		HLayout buttonLayout2 = new HLayout();
		buttonLayout2.setMembers(addPublicDayButton);
		buttonLayout2.setAutoHeight();
		buttonLayout2.setAutoWidth();
		buttonLayout2.setMargin(5);
		buttonLayout2.setMembersMargin(4);
		buttonLayout2.setLayoutAlign(Alignment.RIGHT);

		VLayout schoolCalendarWeeksLayout = new VLayout();
		schoolCalendarWeeksLayout.setMembers(buttonLayout1, schoolCalendarWeeksListgrid);
		schoolCalendarWeeksLayout.setAutoHeight();
		schoolCalendarWeeksLayout.setWidth100();
		schoolCalendarWeeksLayout.setMargin(5);
		schoolCalendarWeeksLayout.setMembersMargin(4);

		VLayout publicHolidayLayout = new VLayout();
		publicHolidayLayout.setMembers(buttonLayout2, publicHolidayListgrid);
		publicHolidayLayout.setAutoHeight();
		publicHolidayLayout.setWidth100();
		publicHolidayLayout.setMargin(5);
		publicHolidayLayout.setMembersMargin(4);

		TabSet tabSet = new TabSet();

		Tab tab1 = new Tab();
		tab1.setPane(schoolCalendarWeeksLayout);
		tab1.setTitle("School Calendar Weeks");

		Tab tab2 = new Tab();
		tab2.setPane(publicHolidayLayout);
		tab2.setTitle("Public Holidays");

		tabSet.addTab(tab1);
		tabSet.addTab(tab2);
		tabSet.setMargin(0);
		tabSet.setPadding(0);
		tabSet.setHeight(350);
		tabSet.setWidth100();

		VLayout layout = new VLayout();
		layout.addMember(form);
		layout.addMember(tabSet);
		layout.addMember(buttonLayout);

		layout.setMargin(10);
		this.addItem(layout);
		this.setWidth("60%");
		this.setHeight("80%");
		this.setAutoCenter(true);
		this.setTitle("School Calendar");
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

	public ComboBox getAcademicYear() {
		return academicYear;
	}

	public ComboBox getAcademicTerm() {
		return academicTerm;
	}

	public TextField getExpectedDailyHours() {
		return expectedDailyHours;
	}

	public TextField getExpectedWeeklyHours() {
		return expectedWeeklyHours;
	}

	public TextField getExpectedMonthlyHours() {
		return expectedMonthlyHours;
	}

	public TextField getExpectedTermlyHours() {
		return expectedTermlyHours;
	}

	public TextField getDescription() {
		return description;
	}

	public CalendarWeeksListgrid getSchoolCalendarWeeksListgrid() {
		return schoolCalendarWeeksListgrid;
	}

	public PublicHolidaysListgrid getPublicHolidayListgrid() {
		return publicHolidayListgrid;
	}

	public IButton getSaveButton() {
		return saveButton;
	}

	public IButton getCancelButton() {
		return cancelButton;
	}

	public IButton getAddWeekButton() {
		return addWeekButton;
	}

	public IButton getAddPublicDayButton() {
		return addPublicDayButton;
	}

}
