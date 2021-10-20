package com.planetsystems.tela.managementapp.client.menu;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class ApprovalsDataSource extends DataSource {

	private static ApprovalsDataSource instance = null;

	public static ApprovalsDataSource getInstance(ListGridRecord[] data) {

		if (instance == null) {
			instance = new ApprovalsDataSource("ApprovalsDataSource", data);
		}
		return instance;
	}

	public ApprovalsDataSource(String id, ListGridRecord[] data) {

		DataSourceTextField pk = new DataSourceTextField("pk", "Primary Key");
		DataSourceTextField icon = new DataSourceTextField("icon", "ICON");
		DataSourceTextField name = new DataSourceTextField("name", "Name");
		setFields(pk, icon, name);

		setTestData(data);
		setClientOnly(true);
	}

}
