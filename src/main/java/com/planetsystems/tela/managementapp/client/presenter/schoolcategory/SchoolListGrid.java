package com.planetsystems.tela.managementapp.client.presenter.schoolcategory;

import java.util.List;

import org.apache.bcel.generic.I2F;

import com.planetsystems.tela.dto.SchoolDTO;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
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
	

/*
 *     private String code;
    private String name;
    private String location;
    private SchoolCategoryDTO schoolCategory;

    private String latitude;
    private String longitude;

    private DistrictDTO district;

    private String deviceNumber; // Phone Serial number

    private boolean attendanceTracked = true;

    private String activationStatus;
 */

		

	public SchoolListGrid() { 
		super();
		
		ListGridField idField = new ListGridField(ID, "Id");
		idField.setHidden(true);

		ListGridField codeField = new ListGridField(CODE, "Code");
		ListGridField nameField = new ListGridField(NAME, "Name");
		ListGridField deviceNumberField = new ListGridField(DEVICE_NUMBER, "DeviceNumber");
		ListGridField attendanceTrackedField = new ListGridField(ATTENDANCETRACKED, "AttendanceTracked"); 
		ListGridField locationField = new ListGridField(LOCATION, "Location");
		
		ListGridField latitudeField = new ListGridField(LATITUDE, "Latitude");
		ListGridField longitudeField = new ListGridField(LONGITUDE, "Longitude");
		latitudeField.setHidden(true);
		longitudeField.setHidden(true);
		
		ListGridField categoryField = new ListGridField(CATEGORY, "SchoolCategory");
		ListGridField categoryIdField = new ListGridField(CATEGORY_ID, "SchoolCategoryId");
		categoryIdField.setHidden(true);
		
		
		ListGridField districtField = new ListGridField(DISTRICT, "District");
		ListGridField districtIdField = new ListGridField(DISTRICT_ID, "DistrictId");
		districtIdField.setHidden(true);
		
	

		

		this.setFields(idField ,categoryIdField , districtIdField , deviceNumberField, codeField , nameField , categoryField, districtField , attendanceTrackedField , locationField , 
				latitudeField , latitudeField
				);

	}

	public ListGridRecord addRowData(SchoolDTO schoolDTO) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute(ID, schoolDTO.getId());
		record.setAttribute(CODE, schoolDTO.getCode());
		record.setAttribute(NAME, schoolDTO.getName());
		
		record.setAttribute(LOCATION, schoolDTO.getLatitude() +","+ schoolDTO.getLongitude());
		record.setAttribute(LATITUDE, schoolDTO.getLatitude());
		record.setAttribute(LONGITUDE, schoolDTO.getLongitude());

		if(schoolDTO.getDistrict() != null) {
			record.setAttribute(DISTRICT, schoolDTO.getDistrict().getName());
			record.setAttribute(DISTRICT_ID, schoolDTO.getDistrict().getId());
		}
		
		record.setAttribute(DEVICE_NUMBER, schoolDTO.getDeviceNumber());
		
		if(schoolDTO.getSchoolCategory() != null) {
			record.setAttribute(CATEGORY, schoolDTO.getSchoolCategory().getName());
			record.setAttribute(CATEGORY_ID, schoolDTO.getSchoolCategory().getId());
					
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
	}
}
