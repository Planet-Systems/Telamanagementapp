/**
 * 
 */
package com.planetsystems.tela.managementapp.client.menu;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

/**
 * @author Planet Developer 001
 * 
 */
public class SystemUserDataSource extends DataSource {

	private static SystemUserDataSource instance = null;

	public static SystemUserDataSource getInstance(ListGridRecord[] data) {

		if (instance == null) {
			instance = new SystemUserDataSource("localSystemUserDataSource",data);
		}
		return instance;
	}

	public SystemUserDataSource(String id,ListGridRecord[] data) {

		DataSourceTextField pk = new DataSourceTextField("pk", "Primary Key");
		DataSourceTextField icon = new DataSourceTextField("icon", "ICON");
		DataSourceTextField name = new DataSourceTextField("name", "Name");
		setFields(pk, icon, name);

		setTestData(data);
		setClientOnly(true);
	}
}
