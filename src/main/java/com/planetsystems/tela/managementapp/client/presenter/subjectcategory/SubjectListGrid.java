package com.planetsystems.tela.managementapp.client.presenter.subjectcategory;

import java.util.List;

import com.planetsystems.tela.dto.SubjectDTO;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class SubjectListGrid extends SuperListGrid {
	public static String ID = "id";
	public static String CODE = "code";
	public static String NAME = "name";
	public static String SUBJECT_CATEGORY = "subjectCategory";
	public static String SUBJECT_CATEGORY_ID = "subjectCategoryId";

	/*
	 * private String code; private String name; private SubjectCategoryDTO
	 * subjectCategory;
	 * 
	 */

	public SubjectListGrid() {
		super();

		ListGridField idField = new ListGridField(ID, "Id");
		idField.setHidden(true);

		ListGridField codeField = new ListGridField(CODE, "Code");
		ListGridField nameField = new ListGridField(NAME, "Name");
		ListGridField subCategoryField = new ListGridField(SUBJECT_CATEGORY, "Subject Category");
		ListGridField subCategoryIdField = new ListGridField(SUBJECT_CATEGORY_ID, "Subject Category Id");
		subCategoryIdField.setHidden(true);

		this.setFields(idField, subCategoryIdField, subCategoryField, codeField, nameField);

	}

	public ListGridRecord addRowData(SubjectDTO subjectDTO) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute(ID, subjectDTO.getId());
		record.setAttribute(CODE, subjectDTO.getCode());
		record.setAttribute(NAME, subjectDTO.getName());
		
		if (subjectDTO.getSubjectCategory() != null) {
			record.setAttribute(SUBJECT_CATEGORY, subjectDTO.getSubjectCategory().getName());
			record.setAttribute(SUBJECT_CATEGORY_ID, subjectDTO.getSubjectCategory().getId());
		}

		return record;
	}

	public void addRecordsToGrid(List<SubjectDTO> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;
		for (SubjectDTO item : list) {
			records[row] = addRowData(item);
			row++;
		}
		this.setData(records);
	}
}
