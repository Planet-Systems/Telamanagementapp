package com.planetsystems.tela.managementapp.client.presenter.staffattendance.clockout;

import java.util.List;

import com.planetsystems.tela.dto.ClockOutDTO;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceDateField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class ClockOutListGrid extends SuperListGrid {
	public static String ID = "id";
	public static String CLOCKED_IN_DATE = "clockInDate";
	public static String CLOCKED_IN_ID  = "clockInID";
	
	public static String CLOCKED_OUT_TIME  = "clockOutTime";
	public static String CLOCKED_IN_TIME  = "clockInTime";
	

	public static String COMMENT = "comment";
	public static String LATITUDE = "latitude";
	public static String LONGTITUDE = "longitude";
	public static String STATUS = "status";

	public static String SCHOOL_STAFF = "schoolStaff";
	public static String SCHOOL_STAFF_ID = "schoolStaffId";

	public static String ACADEMIC_TERM = "academicTerm";
	public static String ACADEMIC_TERM_ID = "academicTermId";
	
	public static String SCHOOL="school";
	public static String SCHOOL_ID="schoolId";

	public static String ACADEMIC_YEAR = "academicYear";
	public static String ACADEMIC_YEAR_ID = "academicYearId";


	private ClockOutDataSource dataSource;
	
	public ClockOutListGrid() {
		super();

		dataSource = ClockOutDataSource.getInstance();
		
		ListGridField idField = new ListGridField(ID, "Id");
		idField.setHidden(true);

		ListGridField schoolStaffField = new ListGridField(SCHOOL_STAFF, "Staff");
		ListGridField schoolStaffIdField = new ListGridField(SCHOOL_STAFF_ID, "Staff Id");
		schoolStaffIdField.setHidden(true);

		ListGridField academicTermField = new ListGridField(ACADEMIC_TERM, "Term");
		ListGridField academicTermIdField = new ListGridField(ACADEMIC_TERM_ID, "Academic Term Id");
		academicTermIdField.setHidden(true);

		ListGridField academicYearField = new ListGridField(ACADEMIC_YEAR, "Year");
		ListGridField academicYearIdField = new ListGridField(ACADEMIC_YEAR_ID, "Academic Year Id");
		academicYearIdField.setHidden(true);
		
		ListGridField schoolField = new ListGridField(SCHOOL, "School");
		ListGridField schoolIdField = new ListGridField(SCHOOL_ID, "SchoolId");
		schoolIdField.setHidden(true);
		
		ListGridField latitudeField = new ListGridField(LATITUDE, "Latitude");
		ListGridField longitudeField = new ListGridField(LONGTITUDE, "Longitude");
		ListGridField statusField = new ListGridField(STATUS, "Status");
		statusField.setHidden(true);
		ListGridField commentField = new ListGridField(COMMENT, "Comment");
		
		ListGridField clockInIdField = new ListGridField(CLOCKED_IN_ID, "clock In Id");
		clockInIdField.setHidden(true);
		
		ListGridField clockInTimeField = new ListGridField(CLOCKED_IN_TIME, "In Time");
		ListGridField clockOutTimeField = new ListGridField(CLOCKED_OUT_TIME, "OutTime");
		ListGridField clockInDateField = new ListGridField(CLOCKED_IN_DATE, "Date");

		this.setFields(idField, schoolStaffIdField,schoolIdField, academicTermIdField,academicYearIdField,clockInIdField ,academicYearField , academicTermField,schoolField, schoolStaffField , clockInDateField,clockInTimeField , clockOutTimeField ,commentField ,statusField,
				latitudeField, longitudeField);

		this.setDataSource(dataSource);
		this.setWrapHeaderTitles(true);
	}

	public ListGridRecord addRowData(ClockOutDTO clockOutDTO) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute(ID, clockOutDTO.getId());
		record.setAttribute(COMMENT, clockOutDTO.getComment());
		record.setAttribute(STATUS, clockOutDTO.getStatus());
		record.setAttribute(CLOCKED_OUT_TIME, clockOutDTO.getClockOutTime());
		
		if(clockOutDTO.getClockInDTO() != null) {

			record.setAttribute(LONGTITUDE, clockOutDTO.getClockInDTO().getLongitude());
			record.setAttribute(LATITUDE, clockOutDTO.getClockInDTO().getLatitude());
			record.setAttribute(CLOCKED_IN_DATE, clockOutDTO.getClockInDTO().getClockInDate());
			record.setAttribute(CLOCKED_IN_TIME, clockOutDTO.getClockInDTO().getClockInTime());
			record.setAttribute(CLOCKED_IN_ID, clockOutDTO.getClockInDTO().getId());
		
			if(clockOutDTO.getClockInDTO().getAcademicTermDTO() != null) {
				record.setAttribute(ACADEMIC_TERM, clockOutDTO.getClockInDTO().getAcademicTermDTO().getTerm());
				record.setAttribute(ACADEMIC_TERM_ID, clockOutDTO.getClockInDTO().getAcademicTermDTO().getId());
				
				if(clockOutDTO.getClockInDTO().getAcademicTermDTO().getAcademicYearDTO() != null) {
					record.setAttribute(ACADEMIC_YEAR, clockOutDTO.getClockInDTO().getAcademicTermDTO().getAcademicYearDTO().getName());
					record.setAttribute(ACADEMIC_YEAR_ID, clockOutDTO.getClockInDTO().getAcademicTermDTO().getAcademicYearDTO().getId());
				}
			}
			
			if(clockOutDTO.getClockInDTO().getSchoolStaffDTO().getGeneralUserDetailDTO() != null) {
				String fullname = clockOutDTO.getClockInDTO().getSchoolStaffDTO().getGeneralUserDetailDTO().getFirstName() + " "
						+ clockOutDTO.getClockInDTO().getSchoolStaffDTO().getGeneralUserDetailDTO().getLastName();
				record.setAttribute(SCHOOL_STAFF, fullname);
				record.setAttribute(SCHOOL_STAFF_ID, clockOutDTO.getClockInDTO().getSchoolStaffDTO().getId());
				
				if(clockOutDTO.getClockInDTO().getSchoolStaffDTO().getSchoolDTO() != null) {
					record.setAttribute(SCHOOL, clockOutDTO.getClockInDTO().getSchoolStaffDTO().getSchoolDTO().getName());
					record.setAttribute(SCHOOL_ID, clockOutDTO.getClockInDTO().getSchoolStaffDTO().getSchoolDTO().getId());
				}
			}

		}

		return record;
	}

	public void addRecordsToGrid(List<ClockOutDTO> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;
		for (ClockOutDTO item : list) {
			records[row] = addRowData(item);
			row++;
		}
		this.setData(records);
		dataSource.setTestData(records);
	}
	
	
	
	
	public static class ClockOutDataSource extends DataSource {

		private static ClockOutDataSource instance = null;

		public static ClockOutDataSource getInstance() {
			if (instance == null) {
				instance = new ClockOutDataSource("ClockOutDataSource");
			}
			return instance;
		}

		public ClockOutDataSource(String id) {
			setID(id);
			
			DataSourceTextField idField = new DataSourceTextField(ID, "Id");
			idField.setHidden(true);
			idField.setPrimaryKey(true);
			
			DataSourceTextField schoolStaffField = new DataSourceTextField(SCHOOL_STAFF, "Staff");
			DataSourceTextField schoolStaffIdField = new DataSourceTextField(SCHOOL_STAFF_ID, "Staff Id");
			schoolStaffIdField.setHidden(true);

			DataSourceTextField academicTermField = new DataSourceTextField(ACADEMIC_TERM, "Academic Term");
			DataSourceTextField academicTermIdField = new DataSourceTextField(ACADEMIC_TERM_ID, "Academic Term Id");
			academicTermIdField.setHidden(true);

			DataSourceTextField academicYearField = new DataSourceTextField(ACADEMIC_YEAR, "Academic Year");
			DataSourceTextField academicYearIdField = new DataSourceTextField(ACADEMIC_YEAR_ID, "Academic Year Id");
			academicYearIdField.setHidden(true);
			
			DataSourceTextField schoolField = new DataSourceTextField(SCHOOL, "School");
			DataSourceTextField schoolIdField = new DataSourceTextField(SCHOOL_ID, "SchoolId");
			schoolIdField.setHidden(true);
			
			DataSourceTextField latitudeField = new DataSourceTextField(LATITUDE, "latitude");
			DataSourceTextField longitudeField = new DataSourceTextField(LONGTITUDE, "longitude");
			DataSourceTextField statusField = new DataSourceTextField(STATUS, "status");
			DataSourceTextField commentField = new DataSourceTextField(COMMENT, "comment");
			
			DataSourceTextField clockInIdField = new DataSourceTextField(CLOCKED_IN_ID, "clock In Id");
			clockInIdField.setHidden(true);
			
			DataSourceTextField clockInTimeField = new DataSourceTextField(CLOCKED_IN_TIME, "clocked In Time");
			DataSourceTextField clockOutTimeField = new DataSourceTextField(CLOCKED_OUT_TIME, "clocked Out Time");
			DataSourceDateField clockInDateField = new DataSourceDateField(CLOCKED_IN_DATE, "clocked In Date");

			this.setFields(idField, schoolStaffIdField,schoolIdField, academicTermIdField,academicYearIdField,clockInIdField ,academicYearField , academicTermField,schoolField, schoolStaffField , clockInDateField,clockInTimeField , clockOutTimeField ,commentField ,statusField,
					latitudeField, longitudeField);

			setClientOnly(true);

		}
	}
	
	
	
}
