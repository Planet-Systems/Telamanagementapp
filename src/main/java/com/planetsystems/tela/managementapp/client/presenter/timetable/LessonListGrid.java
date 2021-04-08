package com.planetsystems.tela.managementapp.client.presenter.timetable;

import java.util.List;

import com.planetsystems.tela.dto.TimeTableLessonDTO;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class LessonListGrid extends SuperListGrid {

	public static final String ID = "id";

	public static final String LESSON_DAY = "lessonDay";

	public static final String CLASS = "class";
	public static final String CLASS_ID = "classId";

	public static final String SUBJECT = "subject";
	public static final String SUBJECT_ID = "subjectId";

	public static final String STAFF = "staff";
	public static final String STAFF_ID = "staffId";

	public static final String START_TIME = "startTime";
	public static final String END_TIME = "endTime";

	  LessonDataSource dataSource;

	public LessonListGrid() {

		dataSource = LessonDataSource.getInstance();
		
		ListGridField idField = new ListGridField(ID , "ID");
		idField.setHidden(true);

		ListGridField lessonDayField = new ListGridField(LESSON_DAY, "Lesson Day");

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

		this.setFields(idField , lessonDayField, classIdField ,classField, subjectIdField ,subjectField, startTimeField, endTimeField, staffIdField ,staffField);
		// this.setDataSource(dataSource);
	}

	public ListGridRecord addRowData(TimeTableLessonDTO timeTableLessonDTO) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute(ID, timeTableLessonDTO.getId());
		record.setAttribute(LESSON_DAY, timeTableLessonDTO.getLessonDay());

		record.setAttribute(START_TIME, timeTableLessonDTO.getStartTime());
		record.setAttribute(END_TIME, timeTableLessonDTO.getEndTime());

		if (timeTableLessonDTO.getSchoolClassDTO() != null) {
			record.setAttribute(CLASS_ID, timeTableLessonDTO.getSchoolClassDTO().getId());
			record.setAttribute(CLASS, timeTableLessonDTO.getSchoolClassDTO().getName());
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
		//dataSource.setTestData(records);
	}

	public void addRecordToGrid(TimeTableLessonDTO dto) {
		this.addData(addRowData(dto));
	}
	
	
	public static class LessonDataSource extends DataSource {

		private static LessonDataSource instance = null;

		public static LessonDataSource getInstance() {
			if (instance == null) {
				instance = new LessonDataSource("LessonDataSource");
			}
			return instance;
		}

		public LessonDataSource(String id) {
			setID(id);
			
			DataSourceTextField idField = new DataSourceTextField(ID, "Id");
			idField.setHidden(true);
			idField.setPrimaryKey(true);
			
			DataSourceTextField lessonDayField = new DataSourceTextField(LESSON_DAY, "Lesson Day");

			DataSourceTextField classField = new DataSourceTextField(CLASS, "Class");
			DataSourceTextField classIdField = new DataSourceTextField(CLASS_ID, "ClassId");
			classIdField.setHidden(true);

			DataSourceTextField subjectField = new DataSourceTextField(SUBJECT, "Subject");
			DataSourceTextField subjectIdField = new DataSourceTextField(SUBJECT_ID, "Subject Id");
			subjectIdField.setHidden(true);

			DataSourceTextField startTimeField = new DataSourceTextField(START_TIME, "Start Time");
			DataSourceTextField endTimeField = new DataSourceTextField(END_TIME, "End Time");

			DataSourceTextField staffField = new DataSourceTextField(STAFF, "Staff");
			DataSourceTextField staffIdField = new DataSourceTextField(STAFF_ID, "Staff Id");
			staffIdField.setHidden(true);
			
			this.setFields(idField , lessonDayField, classIdField ,classField, subjectIdField ,subjectField, startTimeField, endTimeField, staffIdField ,staffField);
			setClientOnly(true);

		}
	}
	

}
