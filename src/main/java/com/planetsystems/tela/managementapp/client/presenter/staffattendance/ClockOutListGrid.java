package com.planetsystems.tela.managementapp.client.presenter.staffattendance;

import java.util.List;

import com.planetsystems.tela.dto.ClockInDTO;
import com.planetsystems.tela.dto.ClockOutDTO;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class ClockOutListGrid extends SuperListGrid {
	public static String ID = "id";
	public static String CLOCKED_IN_DATE = "clockInDate";
	public static String CLOCKED_IN_ID  = "clockInID";
	
	public static String CLOCKED_OUT_TIME  = "clockOutTime";
	public static String CLOCKED_IN_TIME  = "clockInTime";
	

	public static String COMMENT = "comment";
	public static String LATITUDE = "latitude";
	public static String LONGTITUDE = "longitude";
	public static String STATUS = "status";

	public static String SCHOOL_STAFF = "schoolStaff";
	public static String SCHOOL_STAFF_ID = "schoolStaffId";

	public static String ACADEMIC_TERM = "academicTerm";
	public static String ACADEMIC_TERM_ID = "academicTermId";
	
	public static String SCHOOL="school";
	public static String SCHOOL_ID="schoolId";

	public static String ACADEMIC_YEAR = "academicYear";
	public static String ACADEMIC_YEAR_ID = "academicYearId";

	/*
	 * private String comment; private ClockInDTO clockInDTO;
	 */

	public ClockOutListGrid() {
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
		ListGridField schoolIdField = new ListGridField(SCHOOL_ID, "SchoolId");
		schoolIdField.setHidden(true);
		
		ListGridField latitudeField = new ListGridField(LATITUDE, "latitude");
		ListGridField longitudeField = new ListGridField(LONGTITUDE, "longitude");
		ListGridField statusField = new ListGridField(STATUS, "status");
		ListGridField commentField = new ListGridField(COMMENT, "comment");
		
		ListGridField clockInIdField = new ListGridField(CLOCKED_IN_ID, "clock In Id");
		clockInIdField.setHidden(true);
		
		ListGridField clockInTimeField = new ListGridField(CLOCKED_IN_TIME, "clocked In Time");
		ListGridField clockOutTimeField = new ListGridField(CLOCKED_OUT_TIME, "clocked Out Time");
		ListGridField clockInDateField = new ListGridField(CLOCKED_IN_DATE, "clocked In Date");

		this.setFields(idField, schoolStaffIdField,schoolIdField, academicTermIdField,academicYearIdField,clockInIdField ,academicYearField , academicTermField,schoolField, schoolStaffField , clockInDateField,clockInTimeField , clockOutTimeField ,commentField ,statusField,
				latitudeField, longitudeField);

	}

	public ListGridRecord addRowData(ClockOutDTO clockOutDTO) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute(ID, clockOutDTO.getId());
		record.setAttribute(COMMENT, clockOutDTO.getComment());
		record.setAttribute(STATUS, clockOutDTO.getStatus());
		record.setAttribute(CLOCKED_OUT_TIME, clockOutDTO.getClockOutTime());
		
		if(clockOutDTO.getClockInDTO() != null) {

			record.setAttribute(LONGTITUDE, clockOutDTO.getClockInDTO().getLongitude());
			record.setAttribute(LATITUDE, clockOutDTO.getClockInDTO().getLatitude());
			record.setAttribute(CLOCKED_IN_DATE, clockOutDTO.getClockInDTO().getClockInDate());
			record.setAttribute(CLOCKED_IN_TIME, clockOutDTO.getClockInDTO().getClockInTime());
			record.setAttribute(CLOCKED_IN_ID, clockOutDTO.getClockInDTO().getId());
		
			if(clockOutDTO.getClockInDTO().getAcademicTermDTO() != null) {
				record.setAttribute(ACADEMIC_TERM, clockOutDTO.getClockInDTO().getAcademicTermDTO().getTerm());
				record.setAttribute(ACADEMIC_TERM_ID, clockOutDTO.getClockInDTO().getAcademicTermDTO().getId());
				
				if(clockOutDTO.getClockInDTO().getAcademicTermDTO().getAcademicYearDTO() != null) {
					record.setAttribute(ACADEMIC_YEAR, clockOutDTO.getClockInDTO().getAcademicTermDTO().getAcademicYearDTO().getName());
					record.setAttribute(ACADEMIC_YEAR_ID, clockOutDTO.getClockInDTO().getAcademicTermDTO().getAcademicYearDTO().getId());
				}
			}
			
			if(clockOutDTO.getClockInDTO().getSchoolStaffDTO().getGeneralUserDetailDTO() != null) {
				String fullname = clockOutDTO.getClockInDTO().getSchoolStaffDTO().getGeneralUserDetailDTO().getFirstName() + " "
						+ clockOutDTO.getClockInDTO().getSchoolStaffDTO().getGeneralUserDetailDTO().getLastName();
				record.setAttribute(SCHOOL_STAFF, fullname);
				record.setAttribute(SCHOOL_STAFF_ID, clockOutDTO.getClockInDTO().getSchoolStaffDTO().getId());
				
				if(clockOutDTO.getClockInDTO().getSchoolStaffDTO().getSchoolDTO() != null) {
					record.setAttribute(SCHOOL, clockOutDTO.getClockInDTO().getSchoolStaffDTO().getSchoolDTO().getName());
					record.setAttribute(SCHOOL_ID, clockOutDTO.getClockInDTO().getSchoolStaffDTO().getSchoolDTO().getId());
				}
			}

		}

		return record;
	}

	public void addRecordsToGrid(List<ClockOutDTO> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;
		for (ClockOutDTO item : list) {
			records[row] = addRowData(item);
			row++;
		}
		this.setData(records);
	}
}