package com.planetsystems.tela.managementapp.client.presenter.schoolcategory;

import java.util.List;

import org.apache.bcel.generic.I2F;

import com.planetsystems.tela.dto.SchoolDTO;
import com.planetsystems.tela.managementapp.client.presenter.region.RegionListGrid.RegionDataSource;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class SchoolListGrid extends SuperListGrid {
	public static String ID = "id";
	public static String CODE = "code";
	public static String NAME = "name";
	public static String CATEGORY = "schoolCategory";
	public static String CATEGORY_ID = "schoolCategoryId";
	public static String DISTRICT = "district";
	public static String DISTRICT_ID = "districtId";
	public static String DEVICE_NUMBER = "deviceNumber";
	public static String ATTENDANCETRACKED = "attendanceTracked";
	public static String LOCATION = "location";
	
	public static String LATITUDE = "latitude";
	public static String LONGITUDE = "longitude";
	
	private SchoolDataSource dataSource;


	public SchoolListGrid() { 
		super();
		
		dataSource = SchoolDataSource.getInstance();
		
		ListGridField idField = new ListGridField(ID, "Id");
		idField.setHidden(true);

		ListGridField codeField = new ListGridField(CODE, "Code");
		ListGridField nameField = new ListGridField(NAME, "Name");
		ListGridField deviceNumberField = new ListGridField(DEVICE_NUMBER, "Device Number");
		ListGridField attendanceTrackedField = new ListGridField(ATTENDANCETRACKED, "Attendance Tracked"); 
		ListGridField locationField = new ListGridField(LOCATION, "Location");
		
		ListGridField latitudeField = new ListGridField(LATITUDE, "Latitude");
		ListGridField longitudeField = new ListGridField(LONGITUDE, "Longitude");
		latitudeField.setHidden(true);
		longitudeField.setHidden(true);
		
		ListGridField categoryField = new ListGridField(CATEGORY, "School Category");
		ListGridField categoryIdField = new ListGridField(CATEGORY_ID, "School Category Id");
		categoryIdField.setHidden(true);
		
		
		ListGridField districtField = new ListGridField(DISTRICT, "District");
		ListGridField districtIdField = new ListGridField(DISTRICT_ID, "District Id");
		districtIdField.setHidden(true);

		this.setFields(idField ,categoryIdField , districtIdField , deviceNumberField, codeField , nameField , categoryField, districtField , attendanceTrackedField , locationField , 
				latitudeField , latitudeField);
		this.setDataSource(dataSource);

	}

	public ListGridRecord addRowData(SchoolDTO schoolDTO) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute(ID, schoolDTO.getId());
		record.setAttribute(CODE, schoolDTO.getCode());
		record.setAttribute(NAME, schoolDTO.getName());
		
		record.setAttribute(LOCATION, schoolDTO.getLatitude() +","+ schoolDTO.getLongitude());
		record.setAttribute(LATITUDE, schoolDTO.getLatitude());
		record.setAttribute(LONGITUDE, schoolDTO.getLongitude());

		if(schoolDTO.getDistrictDTO() != null) {
			record.setAttribute(DISTRICT, schoolDTO.getDistrictDTO().getName());
			record.setAttribute(DISTRICT_ID, schoolDTO.getDistrictDTO().getId());
		}
		
		record.setAttribute(DEVICE_NUMBER, schoolDTO.getDeviceNumber());
		
		if(schoolDTO.getSchoolCategoryDTO() != null) {
			record.setAttribute(CATEGORY, schoolDTO.getSchoolCategoryDTO().getName());
			record.setAttribute(CATEGORY_ID, schoolDTO.getSchoolCategoryDTO().getId());
					
		}
	
		String att = schoolDTO.isAttendanceTracked() ? "Yes" : "No" ;
		record.setAttribute(ATTENDANCETRACKED, att);
		
		return record;
	}

	public void addRecordsToGrid(List<SchoolDTO> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;
		for (SchoolDTO item : list) {
			records[row] = addRowData(item);
			row++;
		}
		this.setData(records);
		dataSource.setTestData(records);
	}
	
	
	
	
	public static class SchoolDataSource extends DataSource {

		private static SchoolDataSource instance = null;

		public static SchoolDataSource getInstance() {
			if (instance == null) {
				instance = new SchoolDataSource("SchoolDataSource");
			}
			return instance;
		}

		public SchoolDataSource(String id) {
			setID(id);
			
			DataSourceTextField idField = new DataSourceTextField(ID, "Id");
			idField.setHidden(true);
			idField.setPrimaryKey(true);
			
			DataSourceTextField codeField = new DataSourceTextField(CODE, "Code");
			DataSourceTextField nameField = new DataSourceTextField(NAME, "Name");
			DataSourceTextField deviceNumberField = new DataSourceTextField(DEVICE_NUMBER, "Device Number");
			DataSourceTextField attendanceTrackedField = new DataSourceTextField(ATTENDANCETRACKED, "Attendance Tracked"); 
			DataSourceTextField locationField = new DataSourceTextField(LOCATION, "Location");
			
			DataSourceTextField latitudeField = new DataSourceTextField(LATITUDE, "Latitude");
			DataSourceTextField longitudeField = new DataSourceTextField(LONGITUDE, "Longitude");
			latitudeField.setHidden(true);
			longitudeField.setHidden(true);
			
			DataSourceTextField categoryField = new DataSourceTextField(CATEGORY, "School Category");
			DataSourceTextField categoryIdField = new DataSourceTextField(CATEGORY_ID, "School Category Id");
			categoryIdField.setHidden(true);
			
			
			DataSourceTextField districtField = new DataSourceTextField(DISTRICT, "District");
			DataSourceTextField districtIdField = new DataSourceTextField(DISTRICT_ID, "District Id");
			districtIdField.setHidden(true);

			this.setFields(idField ,categoryIdField , districtIdField , deviceNumberField, codeField , nameField , categoryField, districtField , attendanceTrackedField , locationField , 
					latitudeField , latitudeField);
			setClientOnly(true);

		}
	}
	
	
}
