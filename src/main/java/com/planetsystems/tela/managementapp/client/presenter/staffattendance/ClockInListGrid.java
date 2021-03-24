package com.planetsystems.tela.managementapp.client.presenter.staffattendance;

import java.util.List;

import com.planetsystems.tela.dto.ClockInDTO;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class ClockInListGrid extends SuperListGrid {
	public static String ID = "id";
	public static String CLOCKED_IN_DATE = "clockInDate";
	public static String CLOCKED_IN_TIME = "clockInTime";
	
	public static String COMMENT = "comment";
	public static String LATITUDE = "latitude";
	public static String LONGTITUDE = "longitude";
	public static String STATUS = "status";
	
	public static String SCHOOL_STAFF ="schoolStaff";
	public static String SCHOOL_STAFF_ID ="schoolStaffId";
	
	public static String ACADEMIC_TERM = "academicTerm";
	public static String ACADEMIC_TERM_ID = "academicTermId";
	
	public static String ACADEMIC_YEAR = "academicYear";
	public static String ACADEMIC_YEAR_ID = "academicYearId";
	
	public static String SCHOOL="school";
	public static String SCHOOL_ID="schoolId";
	

	
	
	
	
	

/*
private AcademicTermDTO academicTerm;

    private SchoolStaffDTO schoolStaff;

    private Date clockInDate;
    private String comment;
    private String latitude;
    private String longitude;
    private String status;
 */

		

	public ClockInListGrid() { 
		super();
		
		ListGridField idField = new ListGridField(ID, "Id");
		idField.setHidden(true);

		ListGridField schoolStaffField = new ListGridField(SCHOOL_STAFF, "Staff");
		ListGridField schoolStaffIdField = new ListGridField(SCHOOL_STAFF_ID, "Staff Id");
		schoolStaffIdField.setHidden(true);
		
		ListGridField academicTermField = new ListGridField(ACADEMIC_TERM, "Academic Term");
		ListGridField academicTermIdField = new ListGridField(ACADEMIC_TERM_ID, "Academic Term Id");
		academicTermIdField.setHidden(true);
		
		ListGridField academicYearField = new ListGridField(ACADEMIC_YEAR, "Academic Year");
		ListGridField academicYearIdField = new ListGridField(ACADEMIC_YEAR_ID, "Academic Year Id");
		academicYearIdField.setHidden(true);
		
		ListGridField schoolField = new ListGridField(SCHOOL, "School");
		ListGridField schoolIdField = new ListGridField(SCHOOL_ID, "School Id");
		schoolIdField.setHidden(true);
		
		
		ListGridField clockInDateField = new ListGridField(CLOCKED_IN_DATE , "clock In Date");
		ListGridField clockInTimeField = new ListGridField(CLOCKED_IN_TIME , "clock In Time");
	
		ListGridField latitudeField = new ListGridField(LATITUDE, "latitude");
		ListGridField longitudeField = new ListGridField(LONGTITUDE, "longitude");
		ListGridField statusField = new ListGridField(STATUS, "status");
		ListGridField commentField = new ListGridField(COMMENT, "comment");

			
		

		this.setFields(idField , schoolIdField,schoolStaffIdField , academicTermIdField,academicYearIdField,academicYearField , academicTermField , schoolField  ,schoolStaffField , clockInDateField , clockInTimeField , commentField , statusField , latitudeField , longitudeField);

	}

	public ListGridRecord addRowData(ClockInDTO clockInDTO) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute(ID, clockInDTO.getId());
		record.setAttribute(LONGTITUDE, clockInDTO.getLongitude());
		record.setAttribute(LATITUDE, clockInDTO.getLatitude());
		record.setAttribute(STATUS, clockInDTO.getStatus());
		record.setAttribute(COMMENT, clockInDTO.getComment());

		record.setAttribute(CLOCKED_IN_DATE, clockInDTO.getClockInDate());
		record.setAttribute(CLOCKED_IN_TIME, clockInDTO.getClockInTime());
		
		if(clockInDTO.getAcademicTermDTO() != null) {
			record.setAttribute(ACADEMIC_TERM, clockInDTO.getAcademicTermDTO().getTerm());
			record.setAttribute(ACADEMIC_TERM_ID, clockInDTO.getAcademicTermDTO().getId());	
			
			if(clockInDTO.getAcademicTermDTO().getAcademicYearDTO() != null) {
				record.setAttribute(ACADEMIC_YEAR, clockInDTO.getAcademicTermDTO().getAcademicYearDTO().getName());
				record.setAttribute(ACADEMIC_YEAR_ID, clockInDTO.getAcademicTermDTO().getAcademicYearDTO().getId());	
			}
		}
		

		if(clockInDTO.getSchoolStaffDTO() != null) {
			String fullname = clockInDTO.getSchoolStaffDTO().getGeneralUserDetailDTO().getFirstName()+" "+clockInDTO.getSchoolStaffDTO().getGeneralUserDetailDTO().getLastName();
			record.setAttribute(SCHOOL_STAFF, fullname);
			record.setAttribute(SCHOOL_STAFF_ID, clockInDTO.getSchoolStaffDTO().getId());
		}
		
		if (clockInDTO.getSchoolDTO() != null) {
			record.setAttribute(SCHOOL, clockInDTO.getSchoolDTO().getName());
			record.setAttribute(SCHOOL_ID, clockInDTO.getSchoolDTO().getId());
		}
		
		return record;
	}

	public void addRecordsToGrid(List<ClockInDTO> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;
		for (ClockInDTO item : list) {
			records[row] = addRowData(item);
			row++;
		}
		this.setData(records);
	}
}
