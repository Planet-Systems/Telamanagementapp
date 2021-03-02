package com.planetsystems.tela.managementapp.client.presenter.learnerenrollment;

import java.util.List;

import com.planetsystems.tela.dto.LearnerEnrollmentDTO;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class LearnerEnrollmentListGrid extends SuperListGrid  {
	public static String ID = "id";

	public static String TOTAL_BOYS = "totalBoys";
	public static String TOTAL_GIRLS = "totalGirls";
	public static String STATUS = "status";
	
	public static String SCHOOL_CLASS = "schoolClass";
	public static String SCHOOL_CLASS_ID = "schoolClassId";
	
	
	
	
/*
 *    private SchoolClassDTO schoolClassDTO;
    private long totalBoys;
    private long totalGirls;
    private String status;
  
 */

		

	public LearnerEnrollmentListGrid() { 
		super();
		
		ListGridField idField = new ListGridField(ID, "Id");
		idField.setHidden(true);

		ListGridField schoolCLassField = new ListGridField(SCHOOL_CLASS, "schoolClass");
		ListGridField schoolCLassIdField = new ListGridField(SCHOOL_CLASS_ID, "schoolClassId");
		schoolCLassIdField.setHidden(true);
		
		
		ListGridField totalBoysField = new ListGridField(TOTAL_BOYS, "totalBoys");
		ListGridField totalGirlsField = new ListGridField(TOTAL_GIRLS, "totalGirls");
		ListGridField statusField = new ListGridField(STATUS, "status");
		
		

		this.setFields(idField , schoolCLassIdField , schoolCLassField , totalBoysField , totalGirlsField  ,statusField );

	}

	public ListGridRecord addRowData(LearnerEnrollmentDTO learnerEnrollmentDTO) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute(ID, learnerEnrollmentDTO.getId());
		record.setAttribute(TOTAL_BOYS, learnerEnrollmentDTO.getTotalBoys());
		record.setAttribute(TOTAL_GIRLS, learnerEnrollmentDTO.getTotalGirls());
		record.setAttribute(STATUS, learnerEnrollmentDTO.getStatus());
		
		
		if(learnerEnrollmentDTO.getSchoolClassDTO() != null) {
			record.setAttribute(SCHOOL_CLASS, learnerEnrollmentDTO.getSchoolClassDTO().getName());
			record.setAttribute(SCHOOL_CLASS_ID, learnerEnrollmentDTO.getSchoolClassDTO().getId());	
		}
		
		
		return record;
	}

	public void addRecordsToGrid(List<LearnerEnrollmentDTO> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;
		for (LearnerEnrollmentDTO item : list) {
			records[row] = addRowData(item);
			row++;
		}
		this.setData(records);
	}
}
