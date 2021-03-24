package com.planetsystems.tela.managementapp.client.presenter.staffdailytask;

import java.util.List;

import com.planetsystems.tela.dto.StaffDailyAttendanceTaskDTO;
import com.planetsystems.tela.dto.TimeTableLessonDTO;
import com.planetsystems.tela.managementapp.client.presenter.timetable.LessonListGrid;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class DailyTaskListGrid extends SuperListGrid {
	public static final String ID = "id";
     
	public static final String STAFF_DAILY_ATTENDANCE_ID = "staffDailyAttendanceId";

	public static final String CLASS = "class";
	public static final String CLASS_ID = "classId";
	
	public static final String SUBJECT = "subject";
	public static final String SUBJECT_ID = "subjectId";
	
	public static final String START_TIME = "startTime";
	public static final String END_TIME = "endTime";
	
	public static final String ATTENDANCE_STATUS = "attendanceStatus";
	public static final String ATTENDANCE_DATE = "attendanceDate";
	
	/*
	 *      private AcademicTermDTO academicTermDTO;

    private SchoolStaffDTO schoolStaffDTO;

    private String comment;

    private String dailyAttendanceDate;


    private StaffDailyAttendanceDTO staffDailyAttendanceDTO; // not set
	 */
	
	public DailyTaskListGrid() {
		super();
		ListGridField idField = new ListGridField(ID , "Id");
		idField.setHidden(true);
		
		ListGridField staffDailyAttendanceIdField = new ListGridField(STAFF_DAILY_ATTENDANCE_ID , "Id");
		staffDailyAttendanceIdField.setHidden(true);

		ListGridField attendanceDateField = new ListGridField(ATTENDANCE_DATE, "AttendanceDate");
		attendanceDateField.setHidden(true);

		ListGridField classField = new ListGridField(CLASS, "Class");
		ListGridField classIdField = new ListGridField(CLASS_ID, "ClassId");
		classIdField.setHidden(true);

		ListGridField subjectField = new ListGridField(SUBJECT, "Subject");
		ListGridField subjectIdField = new ListGridField(SUBJECT_ID, "Subject Id");
		subjectIdField.setHidden(true);

		ListGridField startTimeField = new ListGridField(START_TIME, "Start Time");
		ListGridField endTimeField = new ListGridField(END_TIME, "End Time");

		ListGridField attendanceStatusField = new ListGridField(ATTENDANCE_STATUS, "AttendanceStatus");
	

		this.setFields(staffDailyAttendanceIdField , attendanceDateField, classField, subjectField, startTimeField, endTimeField, attendanceStatusField);
	}

	public ListGridRecord addRowData(StaffDailyAttendanceTaskDTO staffDailyAttendanceTaskDTO) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute(ID, staffDailyAttendanceTaskDTO.getId());
		record.setAttribute(ATTENDANCE_DATE , staffDailyAttendanceTaskDTO.getDailyAttendanceDate());
		record.setAttribute(ATTENDANCE_STATUS , staffDailyAttendanceTaskDTO.getAttendanceStatus());

		record.setAttribute(START_TIME, staffDailyAttendanceTaskDTO.getStartTime());
		record.setAttribute(END_TIME, staffDailyAttendanceTaskDTO.getEndTime());

		if (staffDailyAttendanceTaskDTO.getSchoolClassDTO() != null) {
			record.setAttribute(CLASS_ID, staffDailyAttendanceTaskDTO.getSchoolClassDTO().getId());
			record.setAttribute(CLASS, staffDailyAttendanceTaskDTO.getSchoolClassDTO().getName());
		}


		if (staffDailyAttendanceTaskDTO.getSubjectDTO() != null) {
			record.setAttribute(SUBJECT_ID, staffDailyAttendanceTaskDTO.getSubjectDTO().getId());
			record.setAttribute(SUBJECT, staffDailyAttendanceTaskDTO.getSubjectDTO().getName());
		}
		
		if (staffDailyAttendanceTaskDTO.getStaffDailyAttendanceDTO() != null) {
			record.setAttribute(STAFF_DAILY_ATTENDANCE_ID , staffDailyAttendanceTaskDTO.getStaffDailyAttendanceDTO().getId());
		}

		return record;
	}

	public void addRecordsToGrid(List<StaffDailyAttendanceTaskDTO> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;
		for (StaffDailyAttendanceTaskDTO item : list) {
			records[row] = addRowData(item);
			row++;
		}
		this.setData(records);
	}

	public void addRecordToGrid(StaffDailyAttendanceTaskDTO dto) {
		this.addData(addRowData(dto));
	}
	
}
