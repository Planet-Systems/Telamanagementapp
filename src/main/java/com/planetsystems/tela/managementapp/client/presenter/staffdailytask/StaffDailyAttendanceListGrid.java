package com.planetsystems.tela.managementapp.client.presenter.staffdailytask;

import java.util.List;

import com.planetsystems.tela.dto.StaffDailyAttendanceDTO;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class StaffDailyAttendanceListGrid extends SuperListGrid {
	public static final String ID = "id";

	public static final String ACADEMIC_YEAR = "ACADEMIC_YEAR";
	public static final String ACADEMIC_YEAR_ID = "ACADEMIC_YEAR_ID";
	
	public static final String ACADEMIC_TERM = "ACADEMIC_TERM";
	public static final String ACADEMIC_TERM_ID = "ACADEMIC_TERM_ID";
	
	public static final String SCHOOL_STAFF = "SCHOOL_STAFF";
	public static final String SCHOOL_STAFF_ID = "SCHOOL_STAFF_ID";
	
	public static final String ATTENDANCE_DATE = "ATTENDANCE_DATE";
	
	
	/*
	 *     private AcademicTermDTO academicTermDTO;

    private SchoolStaffDTO schoolStaffDTO;

    private String comment;

    private String dailyAttendanceDate;

	 */
	StaffDailyAttendanceDataSource dataSource;
	
	public StaffDailyAttendanceListGrid() {
		super();
		
		dataSource = StaffDailyAttendanceDataSource.getInstance();
		
		ListGridField idField = new ListGridField(ID , "ID");
		idField.setHidden(true);


		ListGridField academicYearField = new ListGridField(ACADEMIC_YEAR, "Academic Year");
		ListGridField academicYearIdField = new ListGridField(ACADEMIC_YEAR_ID, "Academic Year Id");
		academicYearIdField.setHidden(true);
		
		ListGridField academicTermField = new ListGridField(ACADEMIC_TERM, "Academic Term");
		ListGridField academicTermIdField = new ListGridField(ACADEMIC_TERM_ID, "Academic Term Id");
		academicTermIdField.setHidden(true);

		ListGridField schoolStaffField = new ListGridField(SCHOOL_STAFF, "School Staff");
		ListGridField schoolStaffIdField = new ListGridField(SCHOOL_STAFF_ID, "School Staff Id");
		schoolStaffIdField.setHidden(true);

		ListGridField attendanceDateField = new ListGridField(ATTENDANCE_DATE, "Attendance Date");

	
        this.setDataSource(dataSource);
		this.setFields(idField , academicYearIdField , academicTermIdField ,schoolStaffIdField , academicYearField , academicTermField , schoolStaffField , attendanceDateField);
	}

	public ListGridRecord addRowData(StaffDailyAttendanceDTO staffDailyAttendanceDTO) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute(ID, staffDailyAttendanceDTO.getId());
		record.setAttribute(ATTENDANCE_DATE, staffDailyAttendanceDTO.getDailyAttendanceDate());

		if (staffDailyAttendanceDTO.getAcademicTermDTO() != null) {
			record.setAttribute(ACADEMIC_TERM_ID, staffDailyAttendanceDTO.getAcademicTermDTO().getId());
			record.setAttribute(ACADEMIC_TERM, staffDailyAttendanceDTO.getAcademicTermDTO().getTerm());
			
			if(staffDailyAttendanceDTO.getAcademicTermDTO().getAcademicYearDTO() != null) {
				record.setAttribute(ACADEMIC_YEAR_ID, staffDailyAttendanceDTO.getAcademicTermDTO().getAcademicYearDTO().getId());
				record.setAttribute(ACADEMIC_YEAR, staffDailyAttendanceDTO.getAcademicTermDTO().getAcademicYearDTO().getName());	
			}
		}


		if (staffDailyAttendanceDTO.getSchoolStaffDTO() != null) {
			record.setAttribute(SCHOOL_STAFF_ID, staffDailyAttendanceDTO.getSchoolStaffDTO().getId());
			if(staffDailyAttendanceDTO.getSchoolStaffDTO().getGeneralUserDetailDTO() != null) {
                String fullName = staffDailyAttendanceDTO.getSchoolStaffDTO().getGeneralUserDetailDTO().getFirstName()+" "
			+staffDailyAttendanceDTO.getSchoolStaffDTO().getGeneralUserDetailDTO().getLastName();
                
				record.setAttribute(SCHOOL_STAFF, fullName);
			
			}
			
		}

		return record;
	}

	public void addRecordsToGrid(List<StaffDailyAttendanceDTO> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;
		for (StaffDailyAttendanceDTO item : list) {
			records[row] = addRowData(item);
			row++;
		}
		this.setData(records);
		dataSource.setTestData(records);
	}

	public void addRecordToGrid(StaffDailyAttendanceDTO dto) {
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

			DataSourceTextField attendanceDateField = new DataSourceTextField(ATTENDANCE_DATE, "Attendance Date");

		

			this.setFields(idField , academicYearIdField , academicTermIdField ,schoolStaffIdField , academicYearField , academicTermField , schoolStaffField , attendanceDateField);

			setClientOnly(true);

		}
	}
	
	
}
