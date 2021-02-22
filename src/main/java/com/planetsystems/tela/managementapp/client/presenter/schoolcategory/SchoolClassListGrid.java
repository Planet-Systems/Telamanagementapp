package com.planetsystems.tela.managementapp.client.presenter.schoolcategory;

import java.util.List;

import com.planetsystems.tela.dto.SchoolClassDTO;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class SchoolClassListGrid extends SuperListGrid {
	public static String ID = "id";
	public static String CODE = "code";
	public static String NAME = "name";
	public static String SCHOOL = "school";
	public static String SCHOOL_ID = "school id";
	
	public static String ACADEMIC_TERM = "Academic Term";
	public static String ACADEMIC_TERM_ID = "Academic Term ID";
	
//	private String code;
//    private String name;

		

	public SchoolClassListGrid() { 
		super();
		
		ListGridField idField = new ListGridField(ID, "Id");
		idField.setHidden(true);

		ListGridField codeField = new ListGridField(CODE, "Code");
		ListGridField nameField = new ListGridField(NAME, "Name");
		ListGridField schoolField = new ListGridField(SCHOOL, "School");
		ListGridField schoolIdField = new ListGridField(SCHOOL_ID, "School Id");
		
		ListGridField academicTermField = new ListGridField(ACADEMIC_TERM , "Academic Term");
		ListGridField academicTermIdField = new ListGridField(ACADEMIC_TERM_ID, "Academic Term id");
		academicTermIdField.setHidden(true);
		

		this.setFields(idField , academicTermIdField , codeField , nameField , academicTermField , schoolField);

	}

	public ListGridRecord addRowData(SchoolClassDTO schoolClassDTO) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute(ID, schoolClassDTO.getId());
		record.setAttribute(CODE, schoolClassDTO.getCode());
		record.setAttribute(NAME, schoolClassDTO.getName());

		if(schoolClassDTO.getSchool() != null) {
			record.setAttribute(SCHOOL, schoolClassDTO.getSchool().getName());
			record.setAttribute(SCHOOL_ID, schoolClassDTO.getSchool().getId());
		}
		
		if(schoolClassDTO.getAcademicTerm() != null) {
			record.setAttribute(ACADEMIC_TERM, schoolClassDTO.getAcademicTerm().getTerm());
			record.setAttribute(ACADEMIC_TERM_ID, schoolClassDTO.getAcademicTerm().getId());
		}
		
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