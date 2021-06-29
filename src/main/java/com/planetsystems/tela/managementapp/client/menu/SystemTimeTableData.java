
package com.planetsystems.tela.managementapp.client.menu;

import java.util.List;

import com.planetsystems.tela.dto.enums.SubMenuItem;
import com.planetsystems.tela.managementapp.client.place.NameTokens;
import com.smartgwt.client.widgets.grid.ListGridRecord; 

public class SystemTimeTableData {  
  

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
//				createRecord("", "application_form" , NameTokens.timeTable)
				createRecord("", "application_form" , SubMenuItem.TIMETABLE.getSystemMenuItem())
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
