package com.planetsystems.tela.managementapp.client.presenter.subjectcategory;

import java.util.List;

import com.planetsystems.tela.dto.SubjectCategoryDTO;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class SubCategoryListGrid extends SuperListGrid {

	public static String ID = "id";
	public static String CODE = "code";
	public static String NAME = "name";

/*
 *    private String code;
    private String name;
 */
		

	public SubCategoryListGrid() { 
		super();
		
		ListGridField idField = new ListGridField(ID, "Id");
		idField.setHidden(true);

		ListGridField codeField = new ListGridField(CODE, "Code");
		ListGridField nameField = new ListGridField(NAME, "Name");
		
		

		this.setFields(idField , codeField , nameField );

	}

	public ListGridRecord addRowData(SubjectCategoryDTO subjectCategoryDTO) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute(ID, subjectCategoryDTO.getId());
		record.setAttribute(CODE, subjectCategoryDTO.getCode());
		record.setAttribute(NAME, subjectCategoryDTO.getName());
		
		return record;
	}

	public void addRecordsToGrid(List<SubjectCategoryDTO> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;
		for (SubjectCategoryDTO item : list) {
			records[row] = addRowData(item);
			row++;
		}
		this.setData(records);
	}
	
}
