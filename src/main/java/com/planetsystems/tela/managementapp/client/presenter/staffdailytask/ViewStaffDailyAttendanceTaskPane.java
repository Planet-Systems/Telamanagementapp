package com.planetsystems.tela.managementapp.client.presenter.staffdailytask;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.planetsystems.tela.managementapp.client.presenter.timetable.LessonListGrid;
import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.planetsystems.tela.managementapp.shared.DatePattern;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class ViewStaffDailyAttendanceTaskPane extends VLayout {
	private TextItem schoolField;
	private TextItem districtField;
	private TextItem academicYearField;
	private ComboBox schoolStaffCombo;
	private TextItem dayField;
	private TextItem academicTermField;
	private IButton loadLessonButton;
	private IButton saveButton;

	public static final String ACADEMIC_YEAR_ID = "ACADEMIC_YEAR_ID";
	public static final String ACADEMIC_TERM_ID = "ACADEMIC_TERM_ID";
	public static final String DISTRICT_ID = "DISTRICT_ID";
	public static final String SCHOOL_ID = "SCHOOL_ID";
	public static final String SCHOOL_STAFF_ID = "SCHOOL_STAFF_ID";
	public static final String DAY = "DAY";

	DateTimeFormat dayFormat = DateTimeFormat.getFormat(DatePattern.DAY.getPattern());
	DateTimeFormat yearFormat = DateTimeFormat.getFormat(DatePattern.YEAR.getPattern());

	private LessonListGrid lessonListGrid;

	public ViewStaffDailyAttendanceTaskPane() {
		super();
		Label header = new Label();
		lessonListGrid = new LessonListGrid();
		lessonListGrid.setSelectionType(SelectionStyle.SIMPLE);
		
		loadLessonButton = new IButton("Load Lesson");
		loadLessonButton.setLayoutAlign(Alignment.RIGHT);
		loadLessonButton.setPadding(10);
		loadLessonButton.disable();


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
//		    formLayout.setBorder("1px solid red");
		formLayout.setLayoutAlign(Alignment.CENTER);

		HLayout schoolDistrictLayout = new HLayout();
		schoolDistrictLayout.setPadding(5);
		schoolDistrictLayout.setAutoHeight();
		schoolDistrictLayout.setWidth("50%");
//	    schoolDistrictLayout.setBorder("1px solid red");
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
		academicYearField.setTitle("AcademicYear");
		academicYearField.setHint("Year");
		academicYearField.setShowHintInField(true);
		academicYearField.disable();

		academicTermField = new TextItem();
		academicTermField.setTitle("AcademicTerm");
		academicTermField.setHint("AcademicTerm");
		academicTermField.setShowHintInField(true);
		academicTermField.disable();

		districtField = new TextItem();
		districtField.setTitle("District");
		districtField.setHint("District");
		districtField.setShowHintInField(true);
		districtField.disable();

		schoolField = new TextItem();
		schoolField.setTitle("School");
		schoolField.setHint("School");
		schoolField.disable();
		schoolField.setShowHintInField(true);

		schoolStaffCombo = new ComboBox();
		schoolStaffCombo.setTitle("Staff");
		schoolStaffCombo.setHint("Staff");
		schoolStaffCombo.setShowHintInField(true);

		dayField = new TextItem("Day");
		dayField.setValue(dayFormat.format(new Date()));
		dayField.disable();

		form.setFields(academicYearField, districtField, academicTermField, schoolField, dayField, schoolStaffCombo);

		saveButton = new IButton("Save");
		saveButton.setLayoutAlign(Alignment.CENTER);
		saveButton.setPadding(10);
		saveButton.disable();

		VLayout layout = new VLayout();
		layout.addMember(header);
		layout.addMember(form);
		layout.addMember(loadLessonButton);
		layout.addMember(lessonListGrid);
		layout.addMember(saveButton);
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

	public ComboBox getSchoolStaffCombo() {
		return schoolStaffCombo;
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

	public DateTimeFormat getDayFormat() {
		return dayFormat;
	}

	public DateTimeFormat getYearFormat() {
		return yearFormat;
	}

	public LessonListGrid getLessonListGrid() {
		return lessonListGrid;
	}

	
	
	
	


}
