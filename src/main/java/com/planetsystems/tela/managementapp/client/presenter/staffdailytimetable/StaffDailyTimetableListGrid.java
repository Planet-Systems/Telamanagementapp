package com.planetsystems.tela.managementapp.client.presenter.staffdailytimetable;

import java.util.List;

import com.planetsystems.tela.dto.StaffDailyTimeTableDTO;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class StaffDailyTimetableListGrid extends SuperListGrid {
	public static final String ID = "id";

	public static final String ACADEMIC_YEAR = "ACADEMIC_YEAR";
	public static final String ACADEMIC_YEAR_ID = "ACADEMIC_YEAR_ID";
	
	public static final String ACADEMIC_TERM = "ACADEMIC_TERM";
	public static final String ACADEMIC_TERM_ID = "ACADEMIC_TERM_ID";
	
	public static final String SCHOOL_STAFF = "SCHOOL_STAFF";
	public static final String SCHOOL_STAFF_ID = "SCHOOL_STAFF_ID";
	
	public static final String LESSON_DATE = "LESSON_DATE";

	StaffDailyAttendanceDataSource dataSource;
	
	public StaffDailyTimetableListGrid() {
		super();
		
		dataSource = StaffDailyAttendanceDataSource.getInstance();
		
		ListGridField idField = new ListGridField(ID , "ID");
		idField.setHidden(true);


		ListGridField academicYearField = new ListGridField(ACADEMIC_YEAR, "Academic Year");
		academicYearField.setHidden(true);
		
		ListGridField academicYearIdField = new ListGridField(ACADEMIC_YEAR_ID, "Academic Year Id");
		academicYearIdField.setHidden(true);
		
		ListGridField academicTermField = new ListGridField(ACADEMIC_TERM, "Academic Term");
		academicTermField.setHidden(true);
		
		ListGridField academicTermIdField = new ListGridField(ACADEMIC_TERM_ID, "Academic Term Id");
		academicTermIdField.setHidden(true);

		ListGridField schoolStaffField = new ListGridField(SCHOOL_STAFF, "Teacher");
		ListGridField schoolStaffIdField = new ListGridField(SCHOOL_STAFF_ID, "School Staff Id");
		schoolStaffIdField.setHidden(true);

		ListGridField lessonDateField = new ListGridField(LESSON_DATE, "Lesson Day");

	
        this.setDataSource(dataSource);
		this.setFields(idField , academicYearIdField , academicTermIdField ,schoolStaffIdField , academicYearField , academicTermField , schoolStaffField , lessonDateField);
	}

	public ListGridRecord addRowData(StaffDailyTimeTableDTO staffDailyTimeTableDTO) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute(ID, staffDailyTimeTableDTO.getId());
		record.setAttribute(LESSON_DATE, staffDailyTimeTableDTO.getLessonDate());

		if (staffDailyTimeTableDTO.getAcademicTermDTO() != null) {
			record.setAttribute(ACADEMIC_TERM_ID, staffDailyTimeTableDTO.getAcademicTermDTO().getId());
			record.setAttribute(ACADEMIC_TERM, staffDailyTimeTableDTO.getAcademicTermDTO().getTerm());
			
			if(staffDailyTimeTableDTO.getAcademicTermDTO().getAcademicYearDTO() != null) {
				record.setAttribute(ACADEMIC_YEAR_ID, staffDailyTimeTableDTO.getAcademicTermDTO().getAcademicYearDTO().getId());
				record.setAttribute(ACADEMIC_YEAR, staffDailyTimeTableDTO.getAcademicTermDTO().getAcademicYearDTO().getName());	
			}
		}


		if (staffDailyTimeTableDTO.getSchoolStaffDTO() != null) {
			record.setAttribute(SCHOOL_STAFF_ID, staffDailyTimeTableDTO.getSchoolStaffDTO().getId());
			if(staffDailyTimeTableDTO.getSchoolStaffDTO().getGeneralUserDetailDTO() != null) {
                String fullName = staffDailyTimeTableDTO.getSchoolStaffDTO().getGeneralUserDetailDTO().getFirstName()+" "
			+staffDailyTimeTableDTO.getSchoolStaffDTO().getGeneralUserDetailDTO().getLastName();
                
				record.setAttribute(SCHOOL_STAFF, fullName);
			
			}
			
		}

		return record;
	}

	public void addRecordsToGrid(List<StaffDailyTimeTableDTO> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;
		for (StaffDailyTimeTableDTO item : list) {
			records[row] = addRowData(item);
			row++;
		}
		this.setData(records);
		dataSource.setTestData(records);
	}

	public void addRecordToGrid(StaffDailyTimeTableDTO dto) {
		this.addData(addRowData(dto));
	}
	
	
	
	public static class StaffDailyAttendanceDataSource extends DataSource {

		private static StaffDailyAttendanceDataSource instance = null;

		public static StaffDailyAttendanceDataSource getInstance() {
			if (instance == null) {
				instance = new StaffDailyAttendanceDataSource("StaffDailyAttendanceDataSource");
			}
			return instance;
		}

		public StaffDailyAttendanceDataSource(String id) {
			setID(id);
			
			DataSourceTextField idField = new DataSourceTextField(ID, "Id");
			idField.setHidden(true);
			idField.setPrimaryKey(true);
			
			DataSourceTextField academicYearField = new DataSourceTextField(ACADEMIC_YEAR, "Academic Year");
			DataSourceTextField academicYearIdField = new DataSourceTextField(ACADEMIC_YEAR_ID, "Academic Year Id");
			academicYearIdField.setHidden(true);
			
			DataSourceTextField academicTermField = new DataSourceTextField(ACADEMIC_TERM, "Academic Term");
			DataSourceTextField academicTermIdField = new DataSourceTextField(ACADEMIC_TERM_ID, "Academic Term Id");
			academicTermIdField.setHidden(true);

			DataSourceTextField schoolStaffField = new DataSourceTextField(SCHOOL_STAFF, "School Staff");
			DataSourceTextField schoolStaffIdField = new DataSourceTextField(SCHOOL_STAFF_ID, "School Staff Id");
			schoolStaffIdField.setHidden(true);

			DataSourceTextField lessonDateField = new DataSourceTextField(LESSON_DATE, "Lesson Date");

		

			this.setFields(idField , academicYearIdField , academicTermIdField ,schoolStaffIdField , academicYearField , academicTermField , schoolStaffField , lessonDateField);

			setClientOnly(true);

		}
	}
	
	
}
