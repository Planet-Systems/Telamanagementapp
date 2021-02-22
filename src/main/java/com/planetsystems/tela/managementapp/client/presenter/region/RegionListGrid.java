package com.planetsystems.tela.managementapp.client.presenter.region;

import java.util.List;

import com.planetsystems.tela.dto.RegionDto;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class RegionListGrid extends SuperListGrid {
	public static String ID = "id";
	public static String CODE = "code";
	public static String NAME = "name";
;
	
//	private String code;
//    private String name;

		

	public RegionListGrid() { 
		super();
		
		ListGridField idField = new ListGridField(ID, "Id");
		idField.setHidden(true);

		ListGridField codeField = new ListGridField(CODE, "Code");
		ListGridField nameField = new ListGridField(NAME, "Name");
		
		

		this.setFields(idField, codeField , nameField);

	}

	public ListGridRecord addRowData(RegionDto regionDto) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute(ID, regionDto.getId());
		record.setAttribute(CODE, regionDto.getCode());
		record.setAttribute(NAME, regionDto.getName());
		
		return record;
	}

	public void addRecordsToGrid(List<RegionDto> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;
		for (RegionDto item : list) {
			records[row] = addRowData(item);
			row++;
		}
		this.setData(records);
	}
}
