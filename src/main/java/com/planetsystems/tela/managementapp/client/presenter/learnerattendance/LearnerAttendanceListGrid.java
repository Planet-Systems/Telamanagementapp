package com.planetsystems.tela.managementapp.client.presenter.learnerattendance;

import java.util.List;

import org.apache.bcel.generic.I2F;

import com.planetsystems.tela.dto.LearnerAttendanceDTO;
import com.planetsystems.tela.dto.SchoolDTO;
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
	
	public static String ACADEMIC_YEAR = "academicYear";
	public static String ACADEMIC_YEAR_ID = "academicYearId";
	
	public static String SCHOOL="school";
	public static String SCHOOL_ID="schoolId";
	
	public static String ATTENDANCE_DATE = "attendanceDate";
	public static String GIRLS_PRESENT = "girlsPresent";
	public static String BOYS_PRESENT = "boysPresent";
	public static String TOTAL_PRESENT = "totalPresent";
	
	public static String GIRLS_ABSENT = "girlsAbsent";
	public static String BOYS_ABSENT = "boysAbsent";
	public static String TOTAL_ABSENT = "totalAbsent";
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

		ListGridField schoolClassField = new ListGridField(SCHOOL_CLASS , "School Class");
		ListGridField schoolClassIdField = new ListGridField(SCHOOL_CLASS_ID , "School ClassId");
		schoolClassIdField.setHidden(true);
		
		ListGridField academicTermField = new ListGridField(ACADEMIC_TERM , "Academic Term");
		ListGridField academicTermIdField = new ListGridField(ACADEMIC_TERM_ID , "Academic Term Id");
		academicTermIdField.setHidden(true);
		
		ListGridField academicYearField = new ListGridField(ACADEMIC_YEAR, "Academic Year");
		ListGridField academicYearIdField = new ListGridField(ACADEMIC_YEAR_ID, "Academic Year Id");
		academicYearIdField.setHidden(true);
		
		ListGridField schoolField = new ListGridField(SCHOOL, "School");
		ListGridField schoolIdField = new ListGridField(SCHOOL_ID, "School Id");
		schoolIdField.setHidden(true);
		
		ListGridField schoolStaffField = new ListGridField(SCHOOL_STAFF , "School Staff");
		ListGridField schoolStaffIdField = new ListGridField(SCHOOL_STAFF_ID , "School Staff Id");
		schoolStaffIdField.setHidden(true);
		
		ListGridField attendanceDateField = new ListGridField(ATTENDANCE_DATE, "Attendance Date");
		ListGridField girlsAbsentField = new ListGridField(GIRLS_ABSENT, "Girls Absent");
		ListGridField boysAbsentField = new ListGridField(BOYS_ABSENT, "Boys Absent");
		ListGridField totalAbsentField = new ListGridField(TOTAL_ABSENT, "Total Absent");
		
		
		ListGridField girlsPresentField = new ListGridField(GIRLS_PRESENT, "Girls Present");
		ListGridField boysPresentField = new ListGridField(BOYS_PRESENT, "Boys Present");
		ListGridField totalPresentField = new ListGridField(TOTAL_PRESENT, "Total Present");
		
		ListGridField commentField = new ListGridField(COMMENT, "Comment");
	    
		   
		     	
		

		this.setFields(idField , schoolClassIdField , academicTermIdField  , schoolStaffIdField , academicTermField , schoolClassField , schoolStaffField , attendanceDateField ,boysPresentField , girlsPresentField ,totalPresentField, boysAbsentField ,girlsAbsentField , totalAbsentField  , commentField  );

	}

	public ListGridRecord addRowData(LearnerAttendanceDTO learnerAttendanceDTO) {
		ListGridRecord record = new ListGridRecord();

		record.setAttribute(ID, learnerAttendanceDTO.getId());
		record.setAttribute(ATTENDANCE_DATE , learnerAttendanceDTO.getAttendanceDate());
		record.setAttribute(GIRLS_ABSENT, learnerAttendanceDTO.getGirlsAbsent());
		record.setAttribute(GIRLS_PRESENT, learnerAttendanceDTO.getGirlsPresent());
		record.setAttribute(BOYS_ABSENT, learnerAttendanceDTO.getBoysAbsent());
		record.setAttribute(BOYS_PRESENT, learnerAttendanceDTO.getBoysPresent());
		
		long totalAbsent = learnerAttendanceDTO.getBoysAbsent() + learnerAttendanceDTO.getGirlsAbsent();
		long totalPresent = learnerAttendanceDTO.getGirlsPresent() + learnerAttendanceDTO.getBoysPresent();
		
		record.setAttribute(TOTAL_ABSENT, String.valueOf(totalAbsent));
		record.setAttribute(TOTAL_PRESENT, String.valueOf(totalPresent));
		record.setAttribute(COMMENT, learnerAttendanceDTO.getComment());
		
		if(learnerAttendanceDTO.getSchoolClassDTO() != null) {
			record.setAttribute(SCHOOL_CLASS, learnerAttendanceDTO.getSchoolClassDTO().getName());
			record.setAttribute(SCHOOL_CLASS_ID, learnerAttendanceDTO.getSchoolClassDTO().getId());
			
			if(learnerAttendanceDTO.getSchoolClassDTO().getSchoolDTO() != null){
				record.setAttribute(SCHOOL, learnerAttendanceDTO.getSchoolClassDTO().getSchoolDTO().getName());
				record.setAttribute(SCHOOL_ID, learnerAttendanceDTO.getSchoolClassDTO().getSchoolDTO().getId());
				
			}
		}
		
		
		
		if(learnerAttendanceDTO.getAcademicTermDTO() != null) {
			record.setAttribute(ACADEMIC_TERM, learnerAttendanceDTO.getAcademicTermDTO().getTerm());
			record.setAttribute(ACADEMIC_TERM_ID, learnerAttendanceDTO.getAcademicTermDTO().getId());
			
			if(learnerAttendanceDTO.getAcademicTermDTO().getAcademicYearDTO() != null) {
				record.setAttribute(ACADEMIC_YEAR, learnerAttendanceDTO.getAcademicTermDTO().getAcademicYearDTO().getName());
				record.setAttribute(ACADEMIC_YEAR_ID, learnerAttendanceDTO.getAcademicTermDTO().getAcademicYearDTO().getId());
					
			}
		}
		
		
		if(learnerAttendanceDTO.getSchoolStaffDTO() != null) {
			if(learnerAttendanceDTO.getSchoolStaffDTO().getGeneralUserDetailDTO() != null) {

				String fullName = learnerAttendanceDTO.getSchoolStaffDTO().getGeneralUserDetailDTO().getFirstName()
						+" "+learnerAttendanceDTO.getSchoolStaffDTO().getGeneralUserDetailDTO().getLastName();
				record.setAttribute(SCHOOL_STAFF, fullName);
			}
			record.setAttribute(SCHOOL_STAFF_ID, learnerAttendanceDTO.getSchoolStaffDTO().getId());
			
			
		}
		
		
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