package com.planetsystems.tela.managementapp.client.presenter.learnerattendance.listgridutil;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class LearnerAttendanceDatasource extends DataSource {

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
	
	private static LearnerAttendanceDatasource instance = null; 
	
	public static LearnerAttendanceDatasource getInstance() {  
        if (instance == null) {  
            instance = new LearnerAttendanceDatasource("LearnerAttendanceDatasource");  
        }  
        return instance;  
    }


	public LearnerAttendanceDatasource(String id) {
		setID(id);
		
		DataSourceTextField idField = new DataSourceTextField(ID , "Id");
		idField.setHidden(true);

		DataSourceTextField schoolClassField = new DataSourceTextField(SCHOOL_CLASS , "School Class");
		DataSourceTextField schoolClassIdField = new DataSourceTextField(SCHOOL_CLASS_ID , "School ClassId");
		schoolClassIdField.setHidden(true);
		
		DataSourceTextField academicTermField = new DataSourceTextField(ACADEMIC_TERM , "Academic Term");
		DataSourceTextField academicTermIdField = new DataSourceTextField(ACADEMIC_TERM_ID , "Academic Term Id");
		academicTermIdField.setHidden(true);
		
		DataSourceTextField academicYearField = new DataSourceTextField(ACADEMIC_YEAR, "Academic Year");
		DataSourceTextField academicYearIdField = new DataSourceTextField(ACADEMIC_YEAR_ID, "Academic Year Id");
		academicYearIdField.setHidden(true);
		
		DataSourceTextField schoolField = new DataSourceTextField(SCHOOL, "School");
		DataSourceTextField schoolIdField = new DataSourceTextField(SCHOOL_ID, "School Id");
		schoolIdField.setHidden(true);
		
		DataSourceTextField schoolStaffField = new DataSourceTextField(SCHOOL_STAFF , "School Staff");
		DataSourceTextField schoolStaffIdField = new DataSourceTextField(SCHOOL_STAFF_ID , "School Staff Id");
		schoolStaffIdField.setHidden(true);
		
		DataSourceTextField attendanceDateField = new DataSourceTextField(ATTENDANCE_DATE, "Attendance Date");
		DataSourceTextField girlsAbsentField = new DataSourceTextField(GIRLS_ABSENT, "Girls Absent");
		DataSourceTextField boysAbsentField = new DataSourceTextField(BOYS_ABSENT, "Boys Absent");
		DataSourceTextField totalAbsentField = new DataSourceTextField(TOTAL_ABSENT, "Total Absent");
		
		
		DataSourceTextField girlsPresentField = new DataSourceTextField(GIRLS_PRESENT, "Girls Present");
		DataSourceTextField boysPresentField = new DataSourceTextField(BOYS_PRESENT, "Boys Present");
		DataSourceTextField totalPresentField = new DataSourceTextField(TOTAL_PRESENT, "Total Present");
		
		DataSourceTextField commentField = new DataSourceTextField(COMMENT, "Comment");
	    


		this.setFields(idField , schoolClassIdField , academicTermIdField  , schoolStaffIdField , academicTermField , schoolClassField , schoolStaffField , attendanceDateField ,boysPresentField , girlsPresentField ,totalPresentField, boysAbsentField ,girlsAbsentField , totalAbsentField  , commentField  );
		setClientOnly(true);
	}
	
	
	
	
}
