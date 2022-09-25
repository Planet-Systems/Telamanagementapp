package com.planetsystems.tela.managementapp.client.presenter.staffdailyattendancesupervision;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.planetsystems.tela.managementapp.client.presenter.staffdailytimetable.StaffDailyTimetableLessonListGrid;
import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.planetsystems.tela.managementapp.shared.DatePattern;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class CreateStaffDailyAttendanceTaskSupervisionPane extends VLayout {
	private ComboBox schoolCombo;
	private ComboBox districtCombo;
	private ComboBox academicYearCombo;
	private ComboBox schoolStaffCombo;
	private TextItem dayField;
	private ComboBox academicTermCombo;
	private IButton loadTasksButton;
	private IButton commentButton;
	private IButton closeTabButton;


	DateTimeFormat dayFormat = DateTimeFormat.getFormat(DatePattern.DAY_DATE.getPattern());
	private StaffDailyTimetableLessonListGrid dailyAttendanceTaskListGrid;

	public CreateStaffDailyAttendanceTaskSupervisionPane() {
		super();
		Label header = new Label();
		dailyAttendanceTaskListGrid = new StaffDailyTimetableLessonListGrid();
		dailyAttendanceTaskListGrid.setSelectionType(SelectionStyle.SIMPLE);
		
		loadTasksButton = new IButton("Load Tasks");
		loadTasksButton.setLayoutAlign(Alignment.RIGHT);
		loadTasksButton.setPadding(10);
		loadTasksButton.disable();


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
		form.setMargin(10);
		form.setColWidths("150", "250");
		form.setCellPadding(5);
		form.setNumCols(4);

		academicYearCombo = new ComboBox();
		academicYearCombo.setTitle("Academic Year");
		academicYearCombo.setHint("Select");
		academicYearCombo.setShowHintInField(true);

		academicTermCombo = new ComboBox();
		academicTermCombo.setTitle("Academic Term");
		academicTermCombo.setHint("Select");
		academicTermCombo.setShowHintInField(true);

		districtCombo = new ComboBox();
		districtCombo.setTitle("Local Government");
		districtCombo.setHint("Select");
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

		commentButton = new IButton("Comment");
		commentButton.setLayoutAlign(Alignment.CENTER);
		commentButton.setPadding(10);
     	commentButton.disable();

		
		closeTabButton = new IButton("Close");
		
		HLayout buttonLayout = new HLayout();
		buttonLayout.setMembers(closeTabButton, commentButton);
		buttonLayout.setAutoHeight();
		buttonLayout.setAutoWidth();
		buttonLayout.setMargin(5);
		buttonLayout.setMembersMargin(4);
		
		buttonLayout.setLayoutAlign(Alignment.CENTER);
		

		VLayout layout = new VLayout();
		layout.addMember(form);
		layout.addMember(loadTasksButton);
		layout.addMember(dailyAttendanceTaskListGrid);
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

	
	
	public IButton getLoadTasksButton() {
		return loadTasksButton;
	}

	

	public IButton getCommentButton() {
		return commentButton;
	}

	public StaffDailyTimetableLessonListGrid getDailyAttendanceTaskListGrid() {
		return dailyAttendanceTaskListGrid;
	}

	public IButton getCloseTabButton() {
		return closeTabButton;
	}

	


}
