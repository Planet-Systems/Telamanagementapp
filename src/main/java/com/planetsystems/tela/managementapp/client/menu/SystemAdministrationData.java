/**
 * 
 */
package com.planetsystems.tela.managementapp.client.menu;

import java.util.List;

import com.planetsystems.tela.dto.enums.SubMenuItem;
import com.planetsystems.tela.managementapp.client.place.NameTokens;
import com.smartgwt.client.widgets.grid.ListGridRecord;

/**
 * @author Planet Developer 001
 * 
 */
public class SystemAdministrationData {
 
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
//				createRecord("", "application_form", NameTokens.assessmentperiod),
//				createRecord("", "application_form", NameTokens.locations),
//				createRecord("", "application_form", NameTokens.schoolClassCategory),
//				createRecord("", "application_form", NameTokens.subjectCategory),
				
				createRecord("", "application_form", SubMenuItem.ASSESSMENT_PERIOD.getSystemMenuItem()),
				createRecord("", "application_form", SubMenuItem.LOCATION.getSystemMenuItem()),
				createRecord("", "application_form", SubMenuItem.SCHOOL_CLASS_CATEGORY.getSystemMenuItem()),
				createRecord("", "application_form", SubMenuItem.SUBJECTS.getSystemMenuItem()),
				
		};

	}

	public static ListGridRecord[] getNewRecords(List<String> list) {

		ListGridRecord[] records = new ListGridRecord[list.size()];
		int count = 0;
		for (String record : list) {
			records[count] = createRecord("", "application_form", record);
			count++;
		}

		return records;
	}

}
