package com.planetsystems.tela.managementapp.client.menu;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class SchoolStatisticsDataSource extends DataSource {

	private static SchoolStatisticsDataSource instance = null;

	public static SchoolStatisticsDataSource getInstance(ListGridRecord[] data) {

		if (instance == null) {
			instance = new SchoolStatisticsDataSource("SchoolStatisticsDataSource", data);
		}
		return instance;
	}

	public SchoolStatisticsDataSource(String id, ListGridRecord[] data) {

		DataSourceTextField pk = new DataSourceTextField("pk", "Primary Key");
		DataSourceTextField icon = new DataSourceTextField("icon", "ICON");
		DataSourceTextField name = new DataSourceTextField("name", "Name");
		setFields(pk, icon, name);

		setTestData(data);
		setClientOnly(true);
	}

}
