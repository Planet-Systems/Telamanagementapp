package com.planetsystems.tela.managementapp.client.presenter.timetable;

import java.util.List;

import com.planetsystems.tela.dto.LearnerEnrollmentDTO;
import com.planetsystems.tela.dto.TimeTableLessonDTO;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class LessonListGrid extends SuperListGrid {

	public static final String ID = "id";

	public static final String DAY = "day";

	public static final String CLASS = "class";
	public static final String CLASS_ID = "classId";

	public static final String SUBJECT = "subject";
	public static final String SUBJECT_ID = "subjectId";

	public static final String STAFF = "staff";
	public static final String STAFF_ID = "staffId";

	public static final String START_TIME = "startTime";
	public static final String END_TIME = "endTime";

//   private String day;
//   private SchoolClassDTO schoolClassDTO;
//   private SubjectDTO subjectDTO;
//   private String startTime;
//   private String endTime;
//   private SchoolStaffDTO schoolStaffDTO

	public LessonListGrid() {

		ListGridField idField = new ListGridField();
		idField.setHidden(true);

		ListGridField dayField = new ListGridField(DAY, "Day");

		ListGridField classField = new ListGridField(CLASS, "Class");
		ListGridField classIdField = new ListGridField(CLASS_ID, "ClassId");
		classIdField.setHidden(true);

		ListGridField subjectField = new ListGridField(SUBJECT, "Subject");
		ListGridField subjectIdField = new ListGridField(SUBJECT_ID, "Subject Id");
		subjectIdField.setHidden(true);

		ListGridField startTimeField = new ListGridField(START_TIME, "Start Time");
		ListGridField endTimeField = new ListGridField(END_TIME, "End Time");

		ListGridField staffField = new ListGridField(STAFF, "Staff");
		ListGridField staffIdField = new ListGridField(STAFF_ID, "Staff Id");
		staffIdField.setHidden(true);

		this.setFields(dayField, classField, subjectField, startTimeField, endTimeField, staffField);
	}

	public ListGridRecord addRowData(TimeTableLessonDTO timeTableLessonDTO) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute(ID, timeTableLessonDTO.getId());
		record.setAttribute(LessonListGrid.DAY, timeTableLessonDTO.getDay());

		record.setAttribute(LessonListGrid.START_TIME, timeTableLessonDTO.getStartTime());
		record.setAttribute(LessonListGrid.END_TIME, timeTableLessonDTO.getEndTime());

		if (timeTableLessonDTO.getSchoolClassDTO() != null) {
			record.setAttribute(LessonListGrid.CLASS_ID, timeTableLessonDTO.getSchoolClassDTO().getId());
			record.setAttribute(LessonListGrid.CLASS, timeTableLessonDTO.getSchoolClassDTO().getName());
		}

		if (timeTableLessonDTO.getSchoolStaffDTO() != null) {
			record.setAttribute(LessonListGrid.STAFF_ID, timeTableLessonDTO.getSchoolStaffDTO().getId());
			String fullName = timeTableLessonDTO.getSchoolStaffDTO().getGeneralUserDetailDTO().getFirstName() + " "
					+ timeTableLessonDTO.getSchoolStaffDTO().getGeneralUserDetailDTO().getLastName();
			record.setAttribute(LessonListGrid.STAFF, fullName);
		}

		if (timeTableLessonDTO.getSubjectDTO() != null) {
			record.setAttribute(LessonListGrid.SUBJECT_ID, timeTableLessonDTO.getSubjectDTO().getId());
			record.setAttribute(LessonListGrid.SUBJECT, timeTableLessonDTO.getSubjectDTO().getName());
		}

		return record;
	}

	public void addRecordsToGrid(List<TimeTableLessonDTO> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;
		for (TimeTableLessonDTO item : list) {
			records[row] = addRowData(item);
			row++;
		}
		this.setData(records);
	}

	public void addRecordToGrid(TimeTableLessonDTO dto) {
		this.addData(addRowData(dto));
	}

}
