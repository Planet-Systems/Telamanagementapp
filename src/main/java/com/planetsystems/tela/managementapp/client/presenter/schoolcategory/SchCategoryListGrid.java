package com.planetsystems.tela.managementapp.client.presenter.schoolcategory;

import java.util.List;

import com.planetsystems.tela.dto.SchoolCategoryDTO;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class SchCategoryListGrid extends SuperListGrid {
	public static String ID = "id";
	public static String CODE = "code";
	public static String NAME = "name";

/*
 *    private String code;
    private String name;
 */
		

	public SchCategoryListGrid() { 
		super();
		
		ListGridField idField = new ListGridField(ID, "Id");
		idField.setHidden(true);

		ListGridField codeField = new ListGridField(CODE, "Code");
		ListGridField nameField = new ListGridField(NAME, "Name");
		
		

		this.setFields(idField , codeField , nameField );

	}

	public ListGridRecord addRowData(SchoolCategoryDTO schoolCategoryDTO) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute(ID, schoolCategoryDTO.getId());
		record.setAttribute(CODE, schoolCategoryDTO.getCode());
		record.setAttribute(NAME, schoolCategoryDTO.getName());
		
		return record;
	}

	public void addRecordsToGrid(List<SchoolCategoryDTO> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;
		for (SchoolCategoryDTO item : list) {
			records[row] = addRowData(item);
			row++;
		}
		this.setData(records);
	}
}
