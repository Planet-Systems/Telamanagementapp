package com.planetsystems.tela.managementapp.client.presenter.timetable;

import java.util.List;

import com.planetsystems.tela.dto.TimeTableDTO;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class TimeTableListGrid extends SuperListGrid {

	public static final String ID = "id";

	   
	   public static final String SCHOOL = "school";
	   public static final String SCHOOL_ID = "schoolId";
	   
	   public static final String ACADEMIC_TERM = "academicTerm";
	   public static final String ACADEMIC_TERM_ID = "academicTermId";
	   
	   public static final String ACADEMIC_YEAR = "academicYear";
	   public static final String ACADEMIC_YEAR_ID = "academicYearId";
	 
	   
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
			
			ListGridField idField = new ListGridField();
			idField.setHidden(true);
			
		    
			ListGridField schoolField = new ListGridField(SCHOOL, "School");
			ListGridField schoolIdField = new ListGridField(SCHOOL_ID, "School Id");
			schoolIdField.setHidden(true);
			
			ListGridField academicTermField = new ListGridField(ACADEMIC_TERM , "Academic Term");
			ListGridField academicTermIdField = new ListGridField(ACADEMIC_TERM_ID , "Academic Term Id");
			academicTermIdField.setHidden(true);
			
			ListGridField academicYearField = new ListGridField(ACADEMIC_YEAR , "Academic Year");
			ListGridField academicYearIdField = new ListGridField(ACADEMIC_YEAR_ID , "Academic Year Id");
			academicTermIdField.setHidden(true);

			
			this.setFields(academicTermIdField , academicYearIdField , schoolIdField , academicYearField , academicTermField , schoolField);
		}
		
		public ListGridRecord addRowData(TimeTableDTO timeTableDTO) {
			  ListGridRecord record = new ListGridRecord();
			  
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
		}
		
		
		public void addRecordToGrid(TimeTableDTO dto) {
			this.addData(addRowData(dto));
		}
	
}
