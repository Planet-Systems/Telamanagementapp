package com.planetsystems.tela.managementapp.client.presenter.region;

import java.util.List;

import com.planetsystems.tela.dto.DistrictDTO;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;



public class DistrictListGrid extends SuperListGrid {
	public static String ID = "id";
	public static String CODE = "code";
	public static String NAME = "name";
	public static String REGION = "region";
	public static String REGION_ID = "region_id";
	public static String ROLLEDOUT = "rolledOut";
	public static String ROLLEDOUT_STATUS = "rolledOutStatus";
    
	private DistrictDataSource dataSource;

	public DistrictListGrid() {
		super();
		dataSource = DistrictDataSource.getInstance();

		ListGridField idField = new ListGridField(ID, "Id");
		idField.setHidden(true);

		ListGridField codeField = new ListGridField(CODE, "Code");
		ListGridField nameField = new ListGridField(NAME, "Name");
		ListGridField regionField = new ListGridField(REGION, "Region");
		ListGridField regionIdField = new ListGridField(REGION_ID, "Region Id");
		regionIdField.setHidden(true);

		ListGridField rolledOutField = new ListGridField(ROLLEDOUT, "Rolled Out");
		ListGridField rolledOutStatusField = new ListGridField(ROLLEDOUT_STATUS, "Rolled Out Status");

		this.setFields(idField, regionIdField, regionField, rolledOutStatusField, codeField, nameField, rolledOutField);
        this.setDataSource(dataSource); 
	}

	public ListGridRecord addRowData(DistrictDTO districtDTO) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute(ID, districtDTO.getId());
		record.setAttribute(CODE, districtDTO.getCode());
		record.setAttribute(NAME, districtDTO.getName());
		if (districtDTO.getRegion() != null) {
			record.setAttribute(REGION, districtDTO.getRegion().getName());
			record.setAttribute(REGION_ID, districtDTO.getRegion().getId());
		}
		String roll = districtDTO.isRolledOut() ? "Yes" : "No";
		record.setAttribute(ROLLEDOUT, roll);
		record.setAttribute(ROLLEDOUT_STATUS, districtDTO.isRolledOut());

		return record;
	}

	public void addRecordsToGrid(List<DistrictDTO> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;
		for (DistrictDTO item : list) {
			records[row] = addRowData(item);
			row++;
		}
		this.setData(records);
	}

	public static class DistrictDataSource extends DataSource {

		private static DistrictDataSource instance = null;

		public static DistrictDataSource getInstance() {
			if (instance == null) {
				instance = new DistrictDataSource("DistrictDataSource");
			}
			return instance;
		}

		public DistrictDataSource(String id) {
			setID(id);
			
			DataSourceTextField idField = new DataSourceTextField(ID, "Id");
			idField.setHidden(true);
			idField.setPrimaryKey(true);
			
			DataSourceTextField codeField = new DataSourceTextField(CODE, "Code");
			DataSourceTextField nameField = new DataSourceTextField(NAME, "Name");
			DataSourceTextField regionField = new DataSourceTextField(REGION, "Region");
			DataSourceTextField regionIdField = new DataSourceTextField(REGION_ID, "Region Id");
			regionIdField.setHidden(true);

			DataSourceTextField rolledOutField = new DataSourceTextField(ROLLEDOUT, "Rolled Out");
			DataSourceTextField rolledOutStatusField = new DataSourceTextField(ROLLEDOUT_STATUS, "Rolled Out Status");

			this.setFields(idField, regionIdField, regionField, rolledOutStatusField, codeField, nameField, rolledOutField);
			
			setClientOnly(true);

		}
	}
}