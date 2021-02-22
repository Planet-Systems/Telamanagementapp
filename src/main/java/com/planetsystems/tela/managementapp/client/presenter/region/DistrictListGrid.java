package com.planetsystems.tela.managementapp.client.presenter.region;

import java.util.List;

import com.google.gwt.user.client.ui.Tree;
import com.planetsystems.tela.dto.DistrictDTO;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
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

//private RegionDto region;
//private String name;
//private String code;
//private boolean rolledOut;

		

	public DistrictListGrid() { 
		super();
		
		ListGridField idField = new ListGridField(ID, "Id");
		idField.setHidden(true);

		ListGridField codeField = new ListGridField(CODE, "Code");
		ListGridField nameField = new ListGridField(NAME, "Name");
		ListGridField regionField = new ListGridField(REGION, "region");
		ListGridField regionIdField = new ListGridField(REGION_ID, "region_id");
		regionIdField.setHidden(true);
		
		ListGridField rolledOutField = new ListGridField(ROLLEDOUT, "rolledOut");
		ListGridField rolledOutStatusField = new ListGridField(ROLLEDOUT_STATUS, "rolledOutStatus");
		
		

		this.setFields(idField , regionIdField , regionField , rolledOutStatusField , codeField , nameField , rolledOutField);

	}

	public ListGridRecord addRowData(DistrictDTO districtDTO) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute(ID, districtDTO.getId());
		record.setAttribute(CODE, districtDTO.getCode());
		record.setAttribute(NAME, districtDTO.getName());
		if(districtDTO.getRegion() != null) {
			record.setAttribute(REGION, districtDTO.getRegion().getName());
			record.setAttribute(REGION_ID, districtDTO.getRegion().getId());
		}
		String roll = districtDTO.isRolledOut() ? "Yes" : "No" ;
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
}