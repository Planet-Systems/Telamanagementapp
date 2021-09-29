package com.planetsystems.tela.managementapp.client.presenter.staffdailytimetable;


import com.google.gwt.i18n.client.DateTimeFormat;
import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.planetsystems.tela.managementapp.shared.DatePattern;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class StaffDailyTimetablePane extends VLayout {
	private ComboBox schoolCombo;
	private ComboBox districtCombo;
	private ComboBox academicYearCombo;
	private ComboBox regionCombo;
//	private TextItem dayField;
	private DateItem lessonDayDateItem;
	private ComboBox academicTermCombo;
	private IButton loadAttendanceButton;

	public static final String ACADEMIC_YEAR_ID = "ACADEMIC_YEAR_ID";
	public static final String ACADEMIC_TERM_ID = "ACADEMIC_TERM_ID";
	public static final String DISTRICT_ID = "DISTRICT_ID";
	public static final String SCHOOL_ID = "SCHOOL_ID";
	public static final String ATTENDANCE_DATE = "ATTENDANCE_DATE";

	DateTimeFormat dayFormat = DateTimeFormat.getFormat(DatePattern.DAY_DATE.getPattern());

	private StaffDailyTimetableListGrid staffDailyTimetableListGrid;

	public StaffDailyTimetablePane() {
		super();
		Label header = new Label();
		staffDailyTimetableListGrid = new StaffDailyTimetableListGrid();

		
		loadAttendanceButton = new IButton("Load daily timetable");
		loadAttendanceButton.setLayoutAlign(Alignment.RIGHT);
		loadAttendanceButton.setPadding(10);
		loadAttendanceButton.disable();


		header.setStyleName("crm-ContextArea-Header-Label");
		header.setStyleName("crm-ContextArea-Header-Label");
		header.setContents("School Staff Daily Task Attendance");
		header.setPadding(10);
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

		Label dayLabel = new Label("Date: ");
		dayLabel.setAutoHeight();
		dayLabel.setAutoWidth();
		dayLabel.setPadding(2);

		DynamicForm form = new DynamicForm();
		form.setWrapItemTitles(false);
		form.setMargin(10);
		form.setColWidths("150", "250");
		form.setCellPadding(5);
		form.setNumCols(4);

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

//		dayField = new TextItem("Day");
//		dayField.setValue(dayFormat.format(new Date()));
//		dayField.disable();
        
		lessonDayDateItem = new DateItem();
		lessonDayDateItem.setTitle("Supervision Date");
		//lessonDayDateItem.disable();
		
		form.setFields(academicYearCombo, regionCombo , academicTermCombo , districtCombo , lessonDayDateItem , schoolCombo);

		VLayout layout = new VLayout();
		layout.addMember(form);
		layout.addMember(loadAttendanceButton);
		layout.addMember(staffDailyTimetableListGrid);
		this.addMember(layout);

	}

	public ComboBox getSchoolCombo() {
		return schoolCombo;
	}

	public ComboBox getDistrictCombo() {
		return districtCombo;
	}

	public ComboBox getAcademicYearCombo() {
		return academicYearCombo;
	}


	public ComboBox getRegionCombo() {
		return regionCombo;
	}

	

	public DateItem getLessonDayDateItem() {
		return lessonDayDateItem;
	}

	public DateTimeFormat getDayFormat() {
		return dayFormat;
	}

	public StaffDailyTimetableListGrid getStaffDailyTimetableListGrid() {
		return staffDailyTimetableListGrid;
	}

	public ComboBox getAcademicTermCombo() {
		return academicTermCombo;
	}


	public IButton getLoadAttendanceButton() {
		return loadAttendanceButton;
	}


	public StaffDailyTimetableListGrid getStaffDailyAttendanceListGrid() {
		return staffDailyTimetableListGrid;
	}

}
