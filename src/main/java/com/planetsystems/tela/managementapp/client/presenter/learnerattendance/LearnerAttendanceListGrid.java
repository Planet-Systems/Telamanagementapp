package com.planetsystems.tela.managementapp.client.presenter.learnerattendance;

import java.util.List;

import com.planetsystems.tela.dto.LearnerAttendanceDTO;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class LearnerAttendanceListGrid extends SuperListGrid {
	public static String ID = "id";
    
	public static String SCHOOL_CLASS = "schoolClass";
	public static String SCHOOL_CLASS_ID = "schoolClassId";
	
	public static String ACADEMIC_TERM = "academicTerm";
	public static String ACADEMIC_TERM_ID = "academicTermId";
	
	public static String SCHOOL_STAFF = "schoolStaff";
	public static String SCHOOL_STAFF_ID = "schoolStaffId";
	
	public static String ATTENDANCE_DATE = "attendanceDate";
	public static String GIRLS_PRESENT = "girlsPresent";
	public static String BOYS_PRESENT = "boysPresent";
	public static String GIRLS_ABSENT = "girlsAbsent";
	public static String BOYS_ABSENT = "boysAbsent";
	public static String COMMENT = "comment";
	
	
	

    /*
     *  private SchoolClassDTO schoolClassDTO;

    private AcademicTermDTO academicTermDTO;

    private SchoolStaffDTO schoolStaffDTO;

    private String attendanceDate;
    private long girlsPresent;
    private long boysPresent;
    private long boysAbsent;
    private long girlsAbsent;
    private String comment;
     */
    
   

		

	public LearnerAttendanceListGrid() { 
		super();
		
		ListGridField idField = new ListGridField(ID , "Id");
		idField.setHidden(true);

		ListGridField schoolClassField = new ListGridField(SCHOOL_CLASS , "SchoolClass");
		ListGridField schoolClassIdField = new ListGridField(SCHOOL_CLASS_ID , "SchoolClassId");
		schoolClassIdField.setHidden(true);
		
		ListGridField academicTermField = new ListGridField(ACADEMIC_TERM , "AcademicTerm");
		ListGridField academicTermIdField = new ListGridField(ACADEMIC_TERM_ID , "AcademicTermId");
		academicTermIdField.setHidden(true);
		
		ListGridField schoolStaffField = new ListGridField(SCHOOL_STAFF , "SchoolStaff");
		ListGridField schoolStaffIdField = new ListGridField(SCHOOL_STAFF_ID , "SchoolStaffId");
		schoolClassIdField.setHidden(true);
		
		ListGridField attendanceDateField = new ListGridField(ATTENDANCE_DATE, "AttendanceDate");
		ListGridField girlsAbsentField = new ListGridField(GIRLS_ABSENT, "GirlsAbsent");
		ListGridField girlsPresentField = new ListGridField(GIRLS_PRESENT, "GirlsPresent");
		ListGridField boysAbsentField = new ListGridField(BOYS_ABSENT, "BoysAbsent");
		ListGridField boysPresentField = new ListGridField(BOYS_PRESENT, "BoysPresent");
		ListGridField commentField = new ListGridField(COMMENT, "Comment");
	    
		   
		     	
		

		this.setFields(idField , schoolClassIdField , academicTermIdField  , schoolStaffIdField , academicTermField , schoolClassField , schoolStaffField , attendanceDateField , girlsAbsentField , girlsPresentField , boysAbsentField , boysPresentField , commentField  );

	}

	public ListGridRecord addRowData(LearnerAttendanceDTO learnerAttendanceDTO) {
		ListGridRecord record = new ListGridRecord();

		record.setAttribute(ID, learnerAttendanceDTO.getId());
		
		record.setAttribute(SCHOOL_CLASS, learnerAttendanceDTO.getSchoolClassDTO().getName());
		record.setAttribute(SCHOOL_CLASS_ID, learnerAttendanceDTO.getSchoolClassDTO().getId());
		
		if(learnerAttendanceDTO.getAcademicTermDTO() != null) {
			record.setAttribute(ACADEMIC_TERM, learnerAttendanceDTO.getAcademicTermDTO().getTerm());
			record.setAttribute(ACADEMIC_TERM_ID, learnerAttendanceDTO.getAcademicTermDTO().getId());
		}
		
		
		if(learnerAttendanceDTO.getSchoolStaffDTO() != null) {
			if(learnerAttendanceDTO.getSchoolStaffDTO().getGeneralUserDetailDTO() != null) {

				String fullName = learnerAttendanceDTO.getSchoolStaffDTO().getGeneralUserDetailDTO().getFirstName()
						+" "+learnerAttendanceDTO.getSchoolStaffDTO().getGeneralUserDetailDTO().getLastName();
				record.setAttribute(SCHOOL_STAFF, fullName);
			}
			record.setAttribute(SCHOOL_STAFF_ID, learnerAttendanceDTO.getSchoolStaffDTO().getId());
		}
		
		record.setAttribute(ATTENDANCE_DATE , learnerAttendanceDTO.getAttendanceDate());
		record.setAttribute(GIRLS_ABSENT, learnerAttendanceDTO.getGirlsAbsent());
		record.setAttribute(GIRLS_PRESENT, learnerAttendanceDTO.getGirlsPresent());
		record.setAttribute(BOYS_ABSENT, learnerAttendanceDTO.getBoysAbsent());
		record.setAttribute(BOYS_PRESENT, learnerAttendanceDTO.getBoysPresent());
		record.setAttribute(COMMENT, learnerAttendanceDTO.getComment());
		
		return record;
	}

	public void addRecordsToGrid(List<LearnerAttendanceDTO> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;
		for (LearnerAttendanceDTO item : list) {
			records[row] = addRowData(item);
			row++;
		}
		this.setData(records);
	}
}