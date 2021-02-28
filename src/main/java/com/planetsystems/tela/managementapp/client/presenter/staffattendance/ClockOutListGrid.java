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

	/*
	 * private String comment; private ClockInDTO clockInDTO;
	 */

	public ClockOutListGrid() {
		super();

		ListGridField idField = new ListGridField(ID, "Id");
		idField.setHidden(true);

		ListGridField schoolStaffField = new ListGridField(SCHOOL_STAFF, "Staff");
		ListGridField schoolStaffIdField = new ListGridField(SCHOOL_STAFF_ID, "StaffId");
		schoolStaffIdField.setHidden(true);

		ListGridField academicField = new ListGridField(ACADEMIC_TERM, "AcademicTerm");
		ListGridField academicIdField = new ListGridField(ACADEMIC_TERM_ID, "AcademicTermId");
		academicIdField.setHidden(true);

		ListGridField latitudeField = new ListGridField(LATITUDE, "latitude");
		ListGridField longitudeField = new ListGridField(LONGTITUDE, "logitude");
		ListGridField statusField = new ListGridField(STATUS, "status");
		ListGridField commentField = new ListGridField(COMMENT, "comment");
		
		ListGridField clockInIdField = new ListGridField(CLOCKED_IN_ID, "clockInId");
		clockInIdField.setHidden(true);
		
		ListGridField clockInTimeField = new ListGridField(CLOCKED_IN_TIME, "clockedInTime");
		ListGridField clockOutTimeField = new ListGridField(CLOCKED_OUT_TIME, "clockedOutTime");
		ListGridField clockInDateField = new ListGridField(CLOCKED_IN_DATE, "clockedInDate");

		this.setFields(idField, schoolStaffIdField, academicIdField,clockInIdField , schoolStaffField, academicField, commentField , clockInDateField,clockInTimeField , clockOutTimeField ,statusField,
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
			}
			
			if(clockOutDTO.getClockInDTO().getSchoolStaffDTO().getGeneralUserDetailDTO() != null) {
				String fullname = clockOutDTO.getClockInDTO().getSchoolStaffDTO().getGeneralUserDetailDTO().getFirstName() + " "
						+ clockOutDTO.getClockInDTO().getSchoolStaffDTO().getGeneralUserDetailDTO().getLastName();
				record.setAttribute(SCHOOL_STAFF, fullname);
				record.setAttribute(SCHOOL_STAFF_ID, clockOutDTO.getClockInDTO().getSchoolStaffDTO().getId());
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
