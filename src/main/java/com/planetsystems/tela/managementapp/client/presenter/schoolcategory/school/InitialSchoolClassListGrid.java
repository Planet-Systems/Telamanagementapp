package com.planetsystems.tela.managementapp.client.presenter.schoolcategory.school;

import java.util.List;

import com.planetsystems.tela.dto.SchoolClassDTO; 
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class InitialSchoolClassListGrid extends SuperListGrid {

	public static String CODE = "code";
	public static String NAME = "name";

	public InitialSchoolClassListGrid() {
		super();

		ListGridField codeField = new ListGridField(CODE, "Class Code");
		ListGridField nameField = new ListGridField(NAME, "Class Name");

		this.setFields(codeField, nameField);
		this.setSelectionType(SelectionStyle.NONE);

	}

	public ListGridRecord addRowData(SchoolClassDTO schoolClassDTO) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute(CODE, schoolClassDTO.getCode());
		record.setAttribute(NAME, schoolClassDTO.getName());

		return record;
	}

	public void addRecordsToGrid(List<SchoolClassDTO> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;
		for (SchoolClassDTO item : list) {
			records[row] = addRowData(item);
			row++;
		}
		this.setData(records);
	}

}
