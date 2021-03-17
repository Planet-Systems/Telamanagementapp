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

public class CreateStaffDailyTaskPane extends VLayout {
	private ComboBox schoolCombo;
	private ComboBox districtCombo;
	private ComboBox academicYearCombo;
	private ComboBox schoolStaffCombo;
	private TextItem dayField;
	private ComboBox academicTermCombo;
	private IButton loadLessonButton;
	private IButton saveButton;
	private IButton closeTabButton;

	public static final String ACADEMIC_YEAR_ID = "ACADEMIC_YEAR_ID";
	public static final String ACADEMIC_TERM_ID = "ACADEMIC_TERM_ID";
	public static final String DISTRICT_ID = "DISTRICT_ID";
	public static final String SCHOOL_ID = "SCHOOL_ID";
	public static final String SCHOOL_STAFF_ID = "SCHOOL_STAFF_ID";
	public static final String DAY = "DAY";

	DateTimeFormat dayFormat = DateTimeFormat.getFormat(DatePattern.DAY.getPattern());
	DateTimeFormat yearFormat = DateTimeFormat.getFormat(DatePattern.YEAR.getPattern());

	private LessonListGrid lessonListGrid;

	public CreateStaffDailyTaskPane() {
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
		header.setContents("Create Staff Daily Tasks");
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
		schoolStaffCombo.setHint("Staff");
		schoolStaffCombo.setShowHintInField(true);

		dayField = new TextItem("Day");
		dayField.setValue(dayFormat.format(new Date()));
		dayField.disable();

		form.setFields(academicYearCombo, districtCombo, academicTermCombo, schoolCombo, dayField, schoolStaffCombo);

		saveButton = new IButton("Save");
		saveButton.setLayoutAlign(Alignment.CENTER);
		saveButton.setPadding(10);
		saveButton.disable();

		
		closeTabButton = new IButton("Close");
		
		HLayout buttonLayout = new HLayout();
		buttonLayout.setMembers(closeTabButton, saveButton);
		buttonLayout.setAutoHeight();
		buttonLayout.setAutoWidth();
		buttonLayout.setMargin(5);
		buttonLayout.setMembersMargin(4);
		
		buttonLayout.setLayoutAlign(Alignment.CENTER);
		

		VLayout layout = new VLayout();
		//layout.addMember(header);
		layout.addMember(form);
		layout.addMember(loadLessonButton);
		layout.addMember(lessonListGrid);
		layout.addMember(buttonLayout);
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

	public ComboBox getSchoolStaffCombo() {
		return schoolStaffCombo;
	}

	public TextItem getDayField() {
		return dayField;
	}

	public ComboBox getAcademicTermCombo() {
		return academicTermCombo;
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

	public IButton getCloseTabButton() {
		return closeTabButton;
	}

	


}
