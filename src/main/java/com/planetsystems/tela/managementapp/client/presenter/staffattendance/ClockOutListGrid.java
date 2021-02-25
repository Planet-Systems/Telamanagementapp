package com.planetsystems.tela.managementapp.client.presenter.staffattendance;

import java.util.List;

import com.planetsystems.tela.dto.ClockInDTO;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class ClockOutListGrid extends SuperListGrid {
	public static String ID = "id";
	public static String CLOCKED_IN_DATE = "clockInDate";
	public static String COMMENT = "comment";
	public static String LATITUDE = "latitude";
	public static String LONGTITUDE = "longitude";
	public static String STATUS = "status";
	
	public static String SCHOOL_STAFF ="schoolStaff";
	public static String SCHOOL_STAFF_ID ="schoolStaffId";
	
	public static String ACADEMIC_TERM = "academicTerm";
	public static String ACADEMIC_TERM_ID = "academicTermId";
	
	
	
	

/*
private AcademicTermDTO academicTerm;

    private SchoolStaffDTO schoolStaff;

    private Date clockInDate;
    private String comment;
    private String latitude;
    private String longitude;
    private String status;
 */

		

	public ClockOutListGrid() { 
		super();
		
		ListGridField idField = new ListGridField(ID, "Id");
		idField.setHidden(true);

		ListGridField schoolField = new ListGridField(SCHOOL_STAFF, "School");
		ListGridField schoolIdField = new ListGridField(SCHOOL_STAFF_ID, "SchoolId");
		schoolIdField.setHidden(true);
		
		ListGridField academicField = new ListGridField(ACADEMIC_TERM, "AcademicTerm");
		ListGridField academicIdField = new ListGridField(ACADEMIC_TERM_ID, "AcademicTermId");
		academicIdField.setHidden(true);
	
		ListGridField latitudeField = new ListGridField(LATITUDE, "latitude");
		ListGridField longitudeField = new ListGridField(LONGTITUDE, "logitude");
		ListGridField statusField = new ListGridField(STATUS, "status");
		ListGridField commentField = new ListGridField(COMMENT, "comment");

			
		

		this.setFields(idField , schoolIdField , academicIdField , schoolField , academicField , commentField , statusField , latitudeField , longitudeField);

	}

	public ListGridRecord addRowData(ClockInDTO clockInDTO) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute(ID, clockInDTO.getId());
		record.setAttribute(LONGTITUDE, clockInDTO.getLongitude());
		record.setAttribute(LATITUDE, clockInDTO.getLatitude());
		record.setAttribute(STATUS, clockInDTO.getStatus());
		
		
		if(clockInDTO.getAcademicTermDTO() != null) {
			record.setAttribute(ACADEMIC_TERM, clockInDTO.getAcademicTermDTO().getTerm());
			record.setAttribute(ACADEMIC_TERM_ID, clockInDTO.getAcademicTermDTO().getId());	
		}
		

		if(clockInDTO.getSchoolStaffDTO() != null) {
			String fullname = clockInDTO.getSchoolStaffDTO().getGeneralUserDetailDTO().getFirstName()+" "+clockInDTO.getSchoolStaffDTO().getGeneralUserDetailDTO().getLastName();
			record.setAttribute(SCHOOL_STAFF, fullname);
			record.setAttribute(SCHOOL_STAFF_ID, clockInDTO.getSchoolStaffDTO().getId());
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
