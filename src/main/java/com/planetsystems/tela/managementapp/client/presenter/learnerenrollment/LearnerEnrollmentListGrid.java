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
	public static String TOTAL = "total";
	public static String STATUS = "status";
	
	public static String SCHOOL="school";
	public static String SCHOOL_ID="schoolId";
	
	public static String SCHOOL_CLASS = "schoolClass";
	public static String SCHOOL_CLASS_ID = "schoolClassId";
	
	public static String ACADEMIC_TERM = "academicTerm";
	public static String ACADEMIC_TERM_ID = "academicTermId";
	
	public static String ACADEMIC_YEAR = "academicYear";
	public static String ACADEMIC_YEAR_ID = "academicYearId";
	
	
	
	
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

		ListGridField schoolCLassField = new ListGridField(SCHOOL_CLASS, "School Class");
		ListGridField schoolCLassIdField = new ListGridField(SCHOOL_CLASS_ID, "School Class Id");
		schoolCLassIdField.setHidden(true);
		
		
		ListGridField totalBoysField = new ListGridField(TOTAL_BOYS, "Boys");
		ListGridField totalGirlsField = new ListGridField(TOTAL_GIRLS, "Girls");
		ListGridField totalField = new ListGridField(TOTAL, "Total");
		ListGridField statusField = new ListGridField(STATUS, "status");
		
		ListGridField academicTermField = new ListGridField(ACADEMIC_TERM, "Academic Term");
		ListGridField academicTermIdField = new ListGridField(ACADEMIC_TERM_ID, "Academic Term Id");
		academicTermIdField.setHidden(true);
		
		ListGridField academicYearField = new ListGridField(ACADEMIC_YEAR, "Academic Year");
		ListGridField academicYearIdField = new ListGridField(ACADEMIC_YEAR_ID, "Academic Year Id");
		academicYearIdField.setHidden(true);
		
		ListGridField schoolField = new ListGridField(SCHOOL, "School");
		ListGridField schoolIdField = new ListGridField(SCHOOL_ID, "SchoolId");
		schoolIdField.setHidden(true);
		

		this.setFields(idField , schoolCLassIdField ,academicTermIdField , academicYearIdField , schoolIdField,academicYearField , academicTermField,schoolField, schoolCLassField , totalBoysField , totalGirlsField , totalField ,statusField );

	}
	
	

	public ListGridRecord addRowData(LearnerEnrollmentDTO learnerEnrollmentDTO) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute(ID, learnerEnrollmentDTO.getId());
		
		record.setAttribute(TOTAL_BOYS, learnerEnrollmentDTO.getTotalBoys());
		record.setAttribute(TOTAL_GIRLS, learnerEnrollmentDTO.getTotalGirls());
		long total = learnerEnrollmentDTO.getTotalGirls()+learnerEnrollmentDTO.getTotalBoys();
		record.setAttribute(TOTAL, String.valueOf(total));
		record.setAttribute(STATUS, learnerEnrollmentDTO.getStatus());
		
		
		if(learnerEnrollmentDTO.getSchoolClassDTO() != null) {
			record.setAttribute(SCHOOL_CLASS, learnerEnrollmentDTO.getSchoolClassDTO().getName());
			record.setAttribute(SCHOOL_CLASS_ID, learnerEnrollmentDTO.getSchoolClassDTO().getId());	
		}
		
		if(learnerEnrollmentDTO.getSchoolClassDTO().getSchoolDTO() != null) {
			record.setAttribute(SCHOOL, learnerEnrollmentDTO.getSchoolClassDTO().getSchoolDTO().getName());
			record.setAttribute(SCHOOL_ID, learnerEnrollmentDTO.getSchoolClassDTO().getSchoolDTO().getId());	
		}
		
		if(learnerEnrollmentDTO.getSchoolClassDTO().getAcademicTermDTO() != null) {
			record.setAttribute(ACADEMIC_TERM, learnerEnrollmentDTO.getSchoolClassDTO().getAcademicTermDTO().getTerm());
			record.setAttribute(ACADEMIC_TERM_ID, learnerEnrollmentDTO.getSchoolClassDTO().getAcademicTermDTO().getId());	
			
			if(learnerEnrollmentDTO.getSchoolClassDTO().getAcademicTermDTO().getAcademicYearDTO() != null) {
				record.setAttribute(ACADEMIC_YEAR, learnerEnrollmentDTO.getSchoolClassDTO().getAcademicTermDTO().getAcademicYearDTO().getName());
				record.setAttribute(ACADEMIC_YEAR_ID, learnerEnrollmentDTO.getSchoolClassDTO().getAcademicTermDTO().getAcademicYearDTO().getId());	
			}
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
	
	
	
	public void addRecordToGrid(LearnerEnrollmentDTO dto) {
		this.addData(addRowData(dto));
	}
}
