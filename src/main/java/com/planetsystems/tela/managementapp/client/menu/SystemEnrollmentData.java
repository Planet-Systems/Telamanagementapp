/**
 * 
 */
package com.planetsystems.tela.managementapp.client.menu;

import java.util.List;

import com.smartgwt.client.widgets.grid.ListGridRecord; 


public class SystemEnrollmentData { 

	  // public	static	final String STAFF = "Staff";
   public	static	final String STAFF_ENROLLMENT = "Staff";
   //public	static	final String LEARNER_ENROLLMENT = "learnerEnrollment";
//   public static final String STAFF_ATTENDANCE = "ataffAttendance";
  // public	static	final String STAFF_CLOCK_IN = "clockIn";
  // public	static	final String STAFF_CLOCK_OUT = "clockOut";
//   public	static	final String STAFF_ATTENDANCE = "Staff Attendance";
   public	static	final String LEARNER_ENROLLMENT = "Learner";
   
  
  

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
		return new ListGridRecord[] {
				createRecord("", "application_form" , STAFF_ENROLLMENT),
				createRecord("", "application_form" , LEARNER_ENROLLMENT),

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