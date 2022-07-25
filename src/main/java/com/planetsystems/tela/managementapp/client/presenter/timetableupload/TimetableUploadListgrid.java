package com.planetsystems.tela.managementapp.client.presenter.timetableupload;

import java.util.LinkedHashMap;
import java.util.List;

import com.planetsystems.tela.dto.SchoolClassDTO;
import com.planetsystems.tela.dto.SchoolStaffDTO;
import com.planetsystems.tela.dto.SubjectDTO;
import com.planetsystems.tela.dto.TimeTableLessonDTO;
import com.planetsystems.tela.managementapp.client.presenter.timetable.LessonListGrid;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TimeItem;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class TimetableUploadListgrid extends SuperListGrid {

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

	public TimetableUploadListgrid(List<String> days, List<SchoolClassDTO> clazes, List<SubjectDTO> subjects,
			List<SchoolStaffDTO> staffList) {

		ListGridField idField = new ListGridField(ID, "ID");
		idField.setHidden(true);

		ListGridField lessonDayField = new ListGridField(LESSON_DAY, "Lesson Day");

		LinkedHashMap<String, String> lessonDayFieldValueMap = new LinkedHashMap<>();
		for (String day : days) {
			lessonDayFieldValueMap.put(day, day);
		}
		lessonDayField.setValueMap(lessonDayFieldValueMap);

		ListGridField classField = new ListGridField(CLASS, "Class");
		SelectItem classFieldSelectItem = new SelectItem();
		LinkedHashMap<String, String> classFieldValueMap = new LinkedHashMap<>();
		for (SchoolClassDTO dto : clazes) {
			classFieldValueMap.put(dto.getId(), dto.getName());
		}
		classField.setValueMap(classFieldValueMap);
		classField.setEditorType(classFieldSelectItem);

		ListGridField classIdField = new ListGridField(CLASS_ID, "ClassId");
		classIdField.setHidden(true);

		ListGridField subjectField = new ListGridField(SUBJECT, "Subject");
		
		SelectItem subjectFieldSelectItem = new SelectItem(); 
		LinkedHashMap<String, String> subjectFieldValueMap = new LinkedHashMap<>();
		for (SubjectDTO dto : subjects) {
			subjectFieldValueMap.put(dto.getId(), dto.getName());
		}
		subjectField.setValueMap(subjectFieldValueMap);
		subjectField.setEditorType(subjectFieldSelectItem);
		

		
		ListGridField subjectIdField = new ListGridField(SUBJECT_ID, "Subject Id");
		subjectIdField.setHidden(true);

		
		ListGridField startTimeField = new ListGridField(START_TIME, "Start Time");
		startTimeField.setType(ListGridFieldType.TIME);
		TimeItem startTime=new TimeItem();
		startTimeField.setEditorType(startTime);
		
		
		ListGridField endTimeField = new ListGridField(END_TIME, "End Time");
		endTimeField.setType(ListGridFieldType.TIME);
		TimeItem endTime=new TimeItem();
		endTimeField.setEditorType(endTime);

		ListGridField staffField = new ListGridField(STAFF, "Staff");
		
		SelectItem staffFieldSelectItem = new SelectItem(); 
		LinkedHashMap<String, String> staffFieldValueMap = new LinkedHashMap<>();
		for (SchoolStaffDTO dto : staffList) {
			staffFieldValueMap.put(dto.getId(), dto.getGeneralUserDetailDTO().getFirstName()+" "+dto.getGeneralUserDetailDTO().getLastName());
		}
		staffField.setValueMap(staffFieldValueMap);
		staffField.setEditorType(staffFieldSelectItem);
		
		ListGridField staffIdField = new ListGridField(STAFF_ID, "Staff Id");
		staffIdField.setHidden(true);

		this.setFields(idField, lessonDayField, classIdField, classField, subjectIdField, subjectField, startTimeField,
				endTimeField, staffIdField, staffField);

		this.setAutoFetchData(true);
		this.setCanEdit(true);
		this.setEditEvent(ListGridEditEvent.CLICK);
		//this.setEditByCell(true);

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
		// dataSource.setTestData(records);
	}

	public void addRecordToGrid(TimeTableLessonDTO dto) {
		this.addData(addRowData(dto));
	}

}
