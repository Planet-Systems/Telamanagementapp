/**
 * 
 */
package com.planetsystems.tela.managementapp.client.menu;

import java.util.List;

import com.smartgwt.client.widgets.grid.ListGridRecord; 

/**
 * @author Planet Developer 001
 * 
 */
public class SystemAdministrationData { 
	
   public	static	final String ACADEMIC_YEAR = "academicYear";
   public	static	final String LOCATION = "location";
   public	static	final String SCHOOLS = "schools";
   public	static	final String SUBJECTS = "subjects";
  

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
				createRecord("", "application_form" , ACADEMIC_YEAR),
				createRecord("", "application_form" , LOCATION),
				createRecord("", "application_form" , SCHOOLS),
				createRecord("", "application_form" , SUBJECTS),

				
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
