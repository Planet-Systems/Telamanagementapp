/**
 * 
 */
package com.planetsystems.tela.managementapp.client.menu;

import java.util.List;

import com.smartgwt.client.widgets.grid.ListGridRecord; 


public class SystemEnrollmentData { 

	   public	static	final String STAFF = "Staff";
   public	static	final String ENROLLMENT = "Enrollment";
   //public	static	final String LEARNER_ENROLLMENT = "learnerEnrollment";
//   public static final String STAFF_ATTENDANCE = "ataffAttendance";
  // public	static	final String STAFF_CLOCK_IN = "clockIn";
  // public	static	final String STAFF_CLOCK_OUT = "clockOut";
   public	static	final String STAFF_ATTENDANCE = "Staff Attendance";
   public	static	final String LEARNER_ATTENDANCE = "Learner Attendance";
   
  
  

	private static ListGridRecord[] records;

	public static ListGridRecord[] getRecords() {
		if (records == null) {
			records = getNewRecords();
		}
		return records;

	}

	public static ListGridRecord createRecord(String pk, String icon, String name) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute("pk", pk);
		record.setAttribute("icon", icon);
		record.setAttribute("name", name);
		return record;
	}

	public static ListGridRecord[] getNewRecords() {
//createRecord("", "application_form",NameTokens.DASHBOARD),
		return new ListGridRecord[] {
				
//				createRecord("", "application_form",NameTokens.ACADEMIC_YEAR),
//				createRecord("", "application_form",NameTokens.region),
//				createRecord("", "application_form",NameTokens.schoolcategory),
//				createRecord("", "application_form",NameTokens.schoolclass),
//				createRecord("", "application_form",NameTokens.subjectcategory),
				createRecord("", "application_form" , STAFF),
				createRecord("", "application_form" , ENROLLMENT),
				createRecord("", "application_form" , STAFF_ATTENDANCE),
				createRecord("", "application_form" , LEARNER_ATTENDANCE),

				
		};

	}
	
	public static ListGridRecord[] getNewRecords(List<String> list) {

		ListGridRecord[] records = new ListGridRecord[list.size()];
		int count = 0;
		for(String record:list){
			records[count] = createRecord("", "application_form", record);
			count++;
		}

		return records;
	} 
	 
}
