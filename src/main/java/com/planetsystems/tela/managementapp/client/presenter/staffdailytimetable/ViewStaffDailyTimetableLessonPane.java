package com.planetsystems.tela.managementapp.client.presenter.staffdailytimetable;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.planetsystems.tela.managementapp.shared.DatePattern;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class ViewStaffDailyTimetableLessonPane extends VLayout {
	private TextItem schoolField;
	private TextItem districtField;
	private TextItem academicYearField;
	private TextItem schoolStaffField;
	private TextItem dayField;
	private TextItem academicTermField;
	private IButton loadLessonButton;
	private IButton saveButton;

	DateTimeFormat dayFormat = DateTimeFormat.getFormat(DatePattern.DAY_DATE.getPattern());

	private StaffDailyTimetableLessonListGrid staffDailyTimetableLessonListGrid;
	private IButton closeTabButton;

	public ViewStaffDailyTimetableLessonPane() {
		super();
		Label header = new Label();
		staffDailyTimetableLessonListGrid = new StaffDailyTimetableLessonListGrid();
		staffDailyTimetableLessonListGrid.setSelectionType(SelectionStyle.NONE);

		loadLessonButton = new IButton("Load Lesson");
		loadLessonButton.setLayoutAlign(Alignment.RIGHT);
		loadLessonButton.setPadding(10);

		header.setStyleName("crm-ContextArea-Header-Label");
		header.setStyleName("crm-ContextArea-Header-Label");
		header.setContents("View Staff Daily Attendance Tasks");
		header.setPadding(5);
		header.setAutoHeight();
		header.setAutoWidth();
		header.setWrap(false);
		header.setAlign(Alignment.CENTER);
		header.setLayoutAlign(Alignment.CENTER);
		header.setMargin(3);

		HLayout formLayout = new HLayout();
		formLayout.setPadding(5);
		formLayout.setAutoHeight();
		formLayout.setWidth("50%");
		formLayout.setLayoutAlign(Alignment.CENTER);

		HLayout schoolDistrictLayout = new HLayout();
		schoolDistrictLayout.setPadding(5);
		schoolDistrictLayout.setAutoHeight();
		schoolDistrictLayout.setWidth("50%");
		schoolDistrictLayout.setLayoutAlign(Alignment.CENTER);

		Label schoolLabel = new Label("School: ");
		schoolLabel.setAutoHeight();
		schoolLabel.setAutoWidth();
		schoolLabel.setPadding(2);

		Label dayLabel = new Label("Day: ");
		dayLabel.setAutoHeight();
		dayLabel.setAutoWidth();
		dayLabel.setPadding(2);

		DynamicForm form = new DynamicForm();
		form.setWrapItemTitles(false);
		form.setMargin(2);
		form.setColWidths("80", "150");
		form.setCellPadding(5);
		form.setNumCols(4);

		academicYearField = new TextItem();
		academicYearField.setTitle("Academic Year");
		academicYearField.setHint("Select");
		academicYearField.setShowHintInField(true);
		academicYearField.disable();

		academicTermField = new TextItem();
		academicTermField.setTitle("Academic Term");
		academicTermField.setHint("Select");
		academicTermField.setShowHintInField(true);
		academicTermField.disable();

		districtField = new TextItem();
		districtField.setTitle("Local Government");
		districtField.setHint("Select");
		districtField.setShowHintInField(true);
		districtField.disable();

		schoolField = new TextItem();
		schoolField.setTitle("School");
		schoolField.setHint("School");
		schoolField.disable();
		schoolField.setShowHintInField(true);

		schoolStaffField = new TextItem();
		schoolStaffField.setTitle("Staff");
		schoolStaffField.setHint("Staff");
		schoolStaffField.disable();
		schoolStaffField.setShowHintInField(true);

		dayField = new TextItem("Day");
		dayField.setValue(dayFormat.format(new Date()));
		dayField.disable();

		form.setFields(academicYearField, districtField, academicTermField, schoolField, dayField, schoolStaffField);

		saveButton = new IButton("Save");
		saveButton.setLayoutAlign(Alignment.CENTER);
		saveButton.setPadding(10);
		saveButton.disable();

		closeTabButton = new IButton("Close");

		HLayout buttonLayout = new HLayout();
		buttonLayout.setMembers(closeTabButton);
		buttonLayout.setAutoHeight();
		buttonLayout.setAutoWidth();
		buttonLayout.setMargin(5);
		buttonLayout.setMembersMargin(4);

		buttonLayout.setLayoutAlign(Alignment.CENTER);

		VLayout layout = new VLayout();
		layout.addMember(form);
		layout.addMember(staffDailyTimetableLessonListGrid);
		layout.addMember(buttonLayout);
		this.addMember(layout);

	}

	public TextItem getSchoolField() {
		return schoolField;
	}

	public TextItem getDistrictField() {
		return districtField;
	}

	public TextItem getAcademicYearField() {
		return academicYearField;
	}


	
	
	public TextItem getSchoolStaffField() {
		return schoolStaffField;
	}

	public TextItem getDayField() {
		return dayField;
	}

	public TextItem getAcademicTermField() {
		return academicTermField;
	}

	public IButton getLoadLessonButton() {
		return loadLessonButton;
	}

	public IButton getSaveButton() {
		return saveButton;
	}


	public StaffDailyTimetableLessonListGrid getDailyTaskListGrid() {
		return staffDailyTimetableLessonListGrid;
	}

	public IButton getCloseTabButton() {
		return closeTabButton;
	}
	
	

}
