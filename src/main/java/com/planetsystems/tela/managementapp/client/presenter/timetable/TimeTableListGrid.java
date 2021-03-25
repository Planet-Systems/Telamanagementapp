package com.planetsystems.tela.managementapp.client.presenter.timetable;

import java.util.List;

import com.planetsystems.tela.dto.TimeTableDTO;
import com.planetsystems.tela.managementapp.client.presenter.region.RegionListGrid.RegionDataSource;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class TimeTableListGrid extends SuperListGrid {

	public static final String ID = "id";

	   
	   public static final String SCHOOL = "school";
	   public static final String SCHOOL_ID = "schoolId";
	   
	   public static final String DISTRICT = "district";
	   public static final String DISTRICT_ID = "districtId";
	   
	   
	   public static final String ACADEMIC_TERM = "academicTerm";
	   public static final String ACADEMIC_TERM_ID = "academicTermId";
	   
	   public static final String ACADEMIC_YEAR = "academicYear";
	   public static final String ACADEMIC_YEAR_ID = "academicYearId";
	 
	   TimeTableDataSource dataSource;
	   
	 
	   
	/*
	 * 
	 *  private SchoolDTO schoolDTO;


    private AcademicTermDTO academicTermDTO;


    private List<TimeTableLessonDTO> timeTableLessonDTOS;
	 * 
	 */
	   
	//   private String day;
	//   private SchoolClassDTO schoolClassDTO;
	//   private SubjectDTO subjectDTO;
	//   private String startTime;
	//   private String endTime;
	//   private SchoolStaffDTO schoolStaffDTO
	   
		
		public TimeTableListGrid() {
			
			dataSource = TimeTableDataSource.getInstance();
			
			ListGridField idField = new ListGridField(ID , "Id");
			idField.setHidden(true);
			
		    
			ListGridField schoolField = new ListGridField(SCHOOL, "School");
			ListGridField schoolIdField = new ListGridField(SCHOOL_ID, "School Id");
			schoolIdField.setHidden(true);
			
			ListGridField districtField = new ListGridField(DISTRICT, "District");
			districtField.setHidden(true);
			ListGridField districtIdField = new ListGridField(DISTRICT_ID, "District Id");
			districtIdField.setHidden(true);
			
			ListGridField academicTermField = new ListGridField(ACADEMIC_TERM , "Academic Term");
			ListGridField academicTermIdField = new ListGridField(ACADEMIC_TERM_ID , "Academic Term Id");
			academicTermIdField.setHidden(true);
			
			ListGridField academicYearField = new ListGridField(ACADEMIC_YEAR , "Academic Year");
			ListGridField academicYearIdField = new ListGridField(ACADEMIC_YEAR_ID , "Academic Year Id");
			academicYearIdField.setHidden(true);

			
			this.setFields(idField,academicTermIdField , academicYearIdField , districtIdField , districtField , schoolIdField , academicYearField , academicTermField , schoolField);
		
		   this.setDataSource(dataSource);
		}
		
		public ListGridRecord addRowData(TimeTableDTO timeTableDTO) {
			  ListGridRecord record = new ListGridRecord();
			  record.setAttribute(ID , timeTableDTO.getId());
			  
			   if(timeTableDTO.getAcademicTermDTO() != null) {
				   record.setAttribute(ACADEMIC_TERM_ID , timeTableDTO.getAcademicTermDTO().getId());
				   record.setAttribute(ACADEMIC_TERM ,timeTableDTO.getAcademicTermDTO().getTerm());   
				   
				   if(timeTableDTO.getAcademicTermDTO().getAcademicYearDTO() != null) {
					   record.setAttribute(ACADEMIC_YEAR_ID , timeTableDTO.getAcademicTermDTO().getAcademicYearDTO().getId());
					   record.setAttribute(ACADEMIC_YEAR ,timeTableDTO.getAcademicTermDTO().getAcademicYearDTO().getName());    
				   }
				   
			   }
			
			   
			   if(timeTableDTO.getSchoolDTO() != null) {
				   record.setAttribute(SCHOOL_ID, timeTableDTO.getSchoolDTO().getId());
				   record.setAttribute(SCHOOL , timeTableDTO.getSchoolDTO().getName());		
				   
				   if(timeTableDTO.getSchoolDTO().getDistrictDTO() != null) {
					   record.setAttribute(DISTRICT_ID, timeTableDTO.getSchoolDTO().getDistrictDTO().getId());
					   record.setAttribute(DISTRICT , timeTableDTO.getSchoolDTO().getDistrictDTO().getName());	 
				   }
			   }

			return record;
		}

		public void addRecordsToGrid(List<TimeTableDTO> list) {
			ListGridRecord[] records = new ListGridRecord[list.size()];
			int row = 0;
			for (TimeTableDTO item : list) {
				records[row] = addRowData(item);
				row++;
			}
			this.setData(records);
			dataSource.setTestData(records);
		}
		
		
		public void addRecordToGrid(TimeTableDTO dto) {
			this.addData(addRowData(dto));
		}
		
		
		public static class TimeTableDataSource extends DataSource {

			private static TimeTableDataSource instance = null;

			public static TimeTableDataSource getInstance() {
				if (instance == null) {
					instance = new TimeTableDataSource("TimeTableDataSource");
				}
				return instance;
			}

			public TimeTableDataSource(String id) {
				setID(id);
				
				DataSourceTextField idField = new DataSourceTextField(ID, "Id");
				idField.setHidden(true);
				idField.setPrimaryKey(true);
				
				DataSourceTextField schoolField = new DataSourceTextField(SCHOOL, "School");
				DataSourceTextField schoolIdField = new DataSourceTextField(SCHOOL_ID, "School Id");
				schoolIdField.setHidden(true);
				
				DataSourceTextField districtField = new DataSourceTextField(DISTRICT, "District");
				districtField.setHidden(true);
				DataSourceTextField districtIdField = new DataSourceTextField(DISTRICT_ID, "District Id");
				districtIdField.setHidden(true);
				
				DataSourceTextField academicTermField = new DataSourceTextField(ACADEMIC_TERM , "Academic Term");
				DataSourceTextField academicTermIdField = new DataSourceTextField(ACADEMIC_TERM_ID , "Academic Term Id");
				academicTermIdField.setHidden(true);
				
				DataSourceTextField academicYearField = new DataSourceTextField(ACADEMIC_YEAR , "Academic Year");
				DataSourceTextField academicYearIdField = new DataSourceTextField(ACADEMIC_YEAR_ID , "Academic Year Id");
				academicYearIdField.setHidden(true);

				
				this.setFields(idField,academicTermIdField , academicYearIdField , districtIdField , districtField , schoolIdField , academicYearField , academicTermField , schoolField);
			
				setClientOnly(true);

			}
		}
	
}
