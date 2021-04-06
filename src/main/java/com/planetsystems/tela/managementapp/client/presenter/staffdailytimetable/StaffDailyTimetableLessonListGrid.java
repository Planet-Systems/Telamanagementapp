package com.planetsystems.tela.managementapp.client.presenter.staffdailytimetable;

import java.util.List;

import com.planetsystems.tela.dto.StaffDailyTimeTableLessonDTO;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class StaffDailyTimetableLessonListGrid extends SuperListGrid {
	public static final String ID = "id";
     
	public static final String STAFF_DAILY_TIME_TABLE_ID = "staffDailyTimeTableId";

	public static final String CLASS = "class";
	public static final String CLASS_ID = "classId";
	
	public static final String SUBJECT = "subject";
	public static final String SUBJECT_ID = "subjectId";
	
	public static final String START_TIME = "startTime";
	public static final String END_TIME = "endTime";
	
	public static final String ATTENDANCE_STATUS = "attendanceStatus";
	public static final String LESSON_DAY = "lessonDay";
	
	StaffDailyTimeTableLessonDTODataSource dataSource;
	
	
	public StaffDailyTimetableLessonListGrid() {
		super();
		
		
		dataSource = StaffDailyTimeTableLessonDTODataSource.getInstance();
		
		ListGridField idField = new ListGridField(ID , "Id");
		idField.setHidden(true);
		
		ListGridField staffDailyAttendanceIdField = new ListGridField(STAFF_DAILY_TIME_TABLE_ID, "Id");
		staffDailyAttendanceIdField.setHidden(true);

		ListGridField attendanceDateField = new ListGridField(LESSON_DAY, "LessonDay");
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

		this.setFields(staffDailyAttendanceIdField, attendanceDateField, classField, subjectField, startTimeField,
				endTimeField, attendanceStatusField);

		this.setDataSource(dataSource);
	}

	public ListGridRecord addRowData(StaffDailyTimeTableLessonDTO staffDailyTimeTableLessonDTO) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute(ID, staffDailyTimeTableLessonDTO.getId());
		record.setAttribute(LESSON_DAY , staffDailyTimeTableLessonDTO.getLessonDay());
		record.setAttribute(ATTENDANCE_STATUS , staffDailyTimeTableLessonDTO.getAttendanceStatus());

		record.setAttribute(START_TIME, staffDailyTimeTableLessonDTO.getStartTime());
		record.setAttribute(END_TIME, staffDailyTimeTableLessonDTO.getEndTime());

		if (staffDailyTimeTableLessonDTO.getSchoolClassDTO() != null) {
			record.setAttribute(CLASS_ID, staffDailyTimeTableLessonDTO.getSchoolClassDTO().getId());
			record.setAttribute(CLASS, staffDailyTimeTableLessonDTO.getSchoolClassDTO().getName());
		}


		if (staffDailyTimeTableLessonDTO.getSubjectDTO() != null) {
			record.setAttribute(SUBJECT_ID, staffDailyTimeTableLessonDTO.getSubjectDTO().getId());
			record.setAttribute(SUBJECT, staffDailyTimeTableLessonDTO.getSubjectDTO().getName());
		}
		
		if (staffDailyTimeTableLessonDTO.getStaffDailyTimeTableDTO() != null) {
			record.setAttribute(STAFF_DAILY_TIME_TABLE_ID , staffDailyTimeTableLessonDTO.getStaffDailyTimeTableDTO().getId());
		}

		return record;
	}

	public void addRecordsToGrid(List<StaffDailyTimeTableLessonDTO> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;
		for (StaffDailyTimeTableLessonDTO item : list) {
			records[row] = addRowData(item);
			row++;
		}
		this.setData(records);
		dataSource.setTestData(records);
	}

	public void addRecordToGrid(StaffDailyTimeTableLessonDTO dto) {
		this.addData(addRowData(dto));
	}
	
	
	
	public static class StaffDailyTimeTableLessonDTODataSource extends DataSource {

		private static StaffDailyTimeTableLessonDTODataSource instance = null;

		public static StaffDailyTimeTableLessonDTODataSource getInstance() {
			if (instance == null) {
				instance = new StaffDailyTimeTableLessonDTODataSource("StaffDailyTimeTableLessonDTODataSource");
			}
			return instance;
		}

		public StaffDailyTimeTableLessonDTODataSource(String id) {
			setID(id);
			
			DataSourceTextField idField = new DataSourceTextField(ID, "Id");
			idField.setHidden(true);
			idField.setPrimaryKey(true);
			
			DataSourceTextField staffDailyAttendanceIdField = new DataSourceTextField(STAFF_DAILY_TIME_TABLE_ID , "Id");
			staffDailyAttendanceIdField.setHidden(true);

			DataSourceTextField attendanceDateField = new DataSourceTextField(LESSON_DAY, "LessonDate");
			attendanceDateField.setHidden(true);

			DataSourceTextField classField = new DataSourceTextField(CLASS, "Class");
			DataSourceTextField classIdField = new DataSourceTextField(CLASS_ID, "ClassId");
			classIdField.setHidden(true);

			DataSourceTextField subjectField = new DataSourceTextField(SUBJECT, "Subject");
			DataSourceTextField subjectIdField = new DataSourceTextField(SUBJECT_ID, "Subject Id");
			subjectIdField.setHidden(true);

			DataSourceTextField startTimeField = new DataSourceTextField(START_TIME, "Start Time");
			DataSourceTextField endTimeField = new DataSourceTextField(END_TIME, "End Time");

			DataSourceTextField attendanceStatusField = new DataSourceTextField(ATTENDANCE_STATUS, "AttendanceStatus");
		

			this.setFields(staffDailyAttendanceIdField , attendanceDateField, classField, subjectField, startTimeField, endTimeField, attendanceStatusField);
		
			setClientOnly(true);

		}
	}
	
	
	
}
