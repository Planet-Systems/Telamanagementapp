package com.planetsystems.tela.managementapp.client.presenter.staffenrollment.enrollment;

import java.util.List;

import com.planetsystems.tela.dto.StaffEnrollmentDto;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
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


	StaffEnrollmentDataSource dataSource;

	public StaffEnrollmentListGrid() { 
		super();
		
		dataSource = StaffEnrollmentDataSource.getInstance();
		
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

		this.setDataSource(dataSource);
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
		dataSource.setTestData(records);
	}
	
	
	
	
	public static class StaffEnrollmentDataSource extends DataSource {

		private static StaffEnrollmentDataSource instance = null;

		public static StaffEnrollmentDataSource getInstance() {
			if (instance == null) {
				instance = new StaffEnrollmentDataSource("StaffEnrollmentDataSource");
			}
			return instance;
		}

		public StaffEnrollmentDataSource(String id) {
			setID(id);
			
			DataSourceTextField idField = new DataSourceTextField(ID, "Id");
			idField.setHidden(true);
			idField.setPrimaryKey(true);
			
			DataSourceTextField schoolField = new DataSourceTextField(SCHOOL, "School");
			DataSourceTextField schoolIdField = new DataSourceTextField(SCHOOL_ID, "School Id");
			schoolIdField.setHidden(true);
			
			DataSourceTextField academicTermField = new DataSourceTextField(ACADEMIC_TERM, "Academic Term");
			DataSourceTextField academicTermIdField = new DataSourceTextField(ACADEMIC_TERM_ID, "Academic Term Id");
			academicTermIdField.setHidden(true);
			
			DataSourceTextField academicYearField = new DataSourceTextField(ACADEMIC_YEAR, "Academic Year");
			DataSourceTextField academicYearIdField = new DataSourceTextField(ACADEMIC_YEAR_ID, "Academic Year Id");
			academicYearIdField.setHidden(true);
			
			DataSourceIntegerField totalFemaleField = new DataSourceIntegerField(TOTAL_FEMALE, "Females");
			DataSourceIntegerField totalMaleField = new DataSourceIntegerField(TOTAL_MALE, "Males");
			DataSourceIntegerField totalField = new DataSourceIntegerField(TOTAL, "Total");
			DataSourceTextField statusField = new 			DataSourceTextField(STATUS, "staus");
			
			

			this.setFields(idField , academicTermIdField , schoolIdField , schoolField , academicTermField  ,totalMaleField , totalFemaleField , totalField , statusField);

			setClientOnly(true);

		}
	}
	
	
}
