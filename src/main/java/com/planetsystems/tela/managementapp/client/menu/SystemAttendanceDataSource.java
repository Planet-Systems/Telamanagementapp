/**
 * 
 */
package com.planetsystems.tela.managementapp.client.menu;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class SystemAttendanceDataSource extends DataSource {

	private static SystemAttendanceDataSource instance = null;

	public static SystemAttendanceDataSource getInstance(ListGridRecord[] data) {

		if (instance == null) {
			instance = new SystemAttendanceDataSource("localSystemAttendanceDataSource",data);
		}
		return instance;
	}

	public SystemAttendanceDataSource(String id,ListGridRecord[] data) {

		DataSourceTextField pk = new DataSourceTextField("pk", "Primary Key");
		DataSourceTextField icon = new DataSourceTextField("icon", "ICON");
		DataSourceTextField name = new DataSourceTextField("name", "Name");
		setFields(pk, icon, name);

		setTestData(data);
		setClientOnly(true);
	}
}
