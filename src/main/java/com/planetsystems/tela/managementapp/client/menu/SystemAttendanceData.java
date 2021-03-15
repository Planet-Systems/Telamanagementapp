/**
 * 
 */
package com.planetsystems.tela.managementapp.client.menu;

import java.util.List;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class SystemAttendanceData {

	public static final String STAFF = "Staff";
	public static final String LEARNER = "Learner";
	public	static	final String HEAD_TEACHER_SUPERVISION = "Head Teacher Supervision";
	public	static	final String STAFF_DAILY_TASKS = "Staff Daily Task";

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
				createRecord("", "application_form", STAFF),
				createRecord("", "application_form", LEARNER),
				createRecord("", "application_form", STAFF_DAILY_TASKS),
				createRecord("", "application_form", HEAD_TEACHER_SUPERVISION),		
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
