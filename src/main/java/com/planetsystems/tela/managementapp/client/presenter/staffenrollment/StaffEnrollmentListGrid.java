package com.planetsystems.tela.managementapp.client.presenter.staffenrollment;

import java.util.List;

import com.planetsystems.tela.dto.StaffEnrollmentDto;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class StaffEnrollmentListGrid extends SuperListGrid {
	public static String ID = "id";
	public static String SCHOOL="school";
	public static String SCHOOL_ID="schoolId";
	
	public static String ACADEMIC_TERM = "academicTerm";
	public static String ACADEMIC_TERM_ID = "academicTermId";
	
	public static String ACADEMIC_YEAR = "academicYear";
	public static String ACADEMIC_YEAR_ID = "academicYearId";
	
	public static String TOTAL_MALE = "TotalMale";
	public static String TOTAL_FEMALE = "TotalFemale";
	public static String TOTAL = "Total";
	
	public static String STATUS = "status";
	
	

/*
 * private SchoolDTO schoolDTO;

    private AcademicTermDTO academicTermDTO;


    private long  totalMale;

    private long totalFemale;

    private String status;
 */

		

	public StaffEnrollmentListGrid() { 
		super();
		
		ListGridField idField = new ListGridField(ID, "Id");
		idField.setHidden(true);

		ListGridField schoolField = new ListGridField(SCHOOL, "School");
		ListGridField schoolIdField = new ListGridField(SCHOOL_ID, "School Id");
		schoolIdField.setHidden(true);
		
		ListGridField academicTermField = new ListGridField(ACADEMIC_TERM, "Academic Term");
		ListGridField academicTermIdField = new ListGridField(ACADEMIC_TERM_ID, "Academic Term Id");
		academicTermIdField.setHidden(true);
		
		ListGridField academicYearField = new ListGridField(ACADEMIC_YEAR, "Academic Year");
		ListGridField academicYearIdField = new ListGridField(ACADEMIC_YEAR_ID, "Academic Year Id");
		academicYearIdField.setHidden(true);
		
		ListGridField totalFemaleField = new ListGridField(TOTAL_FEMALE, "Females");
		ListGridField totalMaleField = new ListGridField(TOTAL_MALE, "Males");
		ListGridField totalField = new ListGridField(TOTAL, "Total");
		ListGridField statusField = new ListGridField(STATUS, "staus");
		
		

		this.setFields(idField , academicTermIdField , schoolIdField , schoolField , academicTermField  ,totalMaleField , totalFemaleField , totalField , statusField);

	}

	public ListGridRecord addRowData(StaffEnrollmentDto staffEnrollmentDto) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute(ID, staffEnrollmentDto.getId());
		record.setAttribute(TOTAL_MALE, staffEnrollmentDto.getTotalMale());
		record.setAttribute(TOTAL_FEMALE, staffEnrollmentDto.getTotalFemale());
		long total = staffEnrollmentDto.getTotalMale() + staffEnrollmentDto.getTotalFemale();
		record.setAttribute(TOTAL, String.valueOf(total));
		
		record.setAttribute(STATUS, staffEnrollmentDto.getStatus());
		
		
		if(staffEnrollmentDto.getAcademicTermDTO() != null) {
			record.setAttribute(ACADEMIC_TERM, staffEnrollmentDto.getAcademicTermDTO().getTerm());
			record.setAttribute(ACADEMIC_TERM_ID, staffEnrollmentDto.getAcademicTermDTO().getId());	
			
			if(staffEnrollmentDto.getAcademicTermDTO().getAcademicYearDTO() != null) {
				record.setAttribute(ACADEMIC_YEAR, staffEnrollmentDto.getAcademicTermDTO().getAcademicYearDTO().getName());
				record.setAttribute(ACADEMIC_YEAR_ID, staffEnrollmentDto.getAcademicTermDTO().getAcademicYearDTO().getId());			
			}
			
		}
		

		if(staffEnrollmentDto.getSchoolDTO() != null) {
			record.setAttribute(SCHOOL, staffEnrollmentDto.getSchoolDTO().getName());
			record.setAttribute(SCHOOL_ID, staffEnrollmentDto.getSchoolDTO().getId());
		}
		
		return record;
	}

	public void addRecordsToGrid(List<StaffEnrollmentDto> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;
		for (StaffEnrollmentDto item : list) {
			records[row] = addRowData(item);
			row++;
		}
		this.setData(records);
	}
}
