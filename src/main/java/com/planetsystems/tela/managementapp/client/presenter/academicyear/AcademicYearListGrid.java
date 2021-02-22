package com.planetsystems.tela.managementapp.client.presenter.academicyear;

import java.util.List;

import com.planetsystems.tela.dto.AcademicYearDTO;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class AcademicYearListGrid extends SuperListGrid {
	public static String ID = "id";
	public static String CODE = "code";
	public static String NAME = "name";
	public static String STATUS = "status";
	public static String ACTIVATION_STATUS = "activationStatus";
	public static String END_DATE = "endDate";
	public static String START_DATE = "startDate";
	

		
	public AcademicYearListGrid() { 
		super();
		
		ListGridField idField = new ListGridField(ID, "Id");
		idField.setHidden(true);

		ListGridField codeField = new ListGridField(CODE, "Code");
		ListGridField nameField = new ListGridField(NAME, "Academic year");
		ListGridField statusField= new ListGridField(STATUS, "Status");
		ListGridField startDateField = new ListGridField(START_DATE, "Start date");
		ListGridField endDateField= new ListGridField(END_DATE, "End date");
		//ListGridField activationStatusField= new ListGridField(ACTIVATION_STATUS, "Activation status");
		
		

		this.setFields(idField, codeField , nameField  ,startDateField , endDateField ,statusField);

	}

	public ListGridRecord addRowData(AcademicYearDTO academicYearDTO) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute(ID, academicYearDTO.getId());
		record.setAttribute(CODE, academicYearDTO.getCode());
		
		record.setAttribute(NAME, academicYearDTO.getName());
		record.setAttribute(START_DATE, academicYearDTO.getStartDate());
		record.setAttribute(END_DATE, academicYearDTO.getEndDate() );
		
			record.setAttribute(STATUS, academicYearDTO.getActivationStatus());
		
		
		return record;
	}

	public void addRecordsToGrid(List<AcademicYearDTO> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;
		for (AcademicYearDTO item : list) {
			records[row] = addRowData(item);
			row++;
		}
		this.setData(records);
	}
}