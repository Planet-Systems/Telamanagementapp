package com.planetsystems.tela.managementapp.client.presenter.schoolcategory.school;

import java.util.List;

import com.planetsystems.tela.dto.SchoolDTO;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.SelectionStyle;
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
	
	public static String SchoolLevel="SchoolLevel";
	public static String SchoolOwnership="SchoolOwnership";
	public static String SchoolType="SchoolType";
	public static String SchoolGenderCategory="SchoolGenderCategory";
	public static String Licensed="Licensed";
	
	public static String Rollout="rollout";
	public static String TelaSchoolNumber="telaSchoolNumber";
	
	private SchoolDataSource dataSource;
	
	public static String REGION = "region";
	public static String REGION_ID = "regionId";
	
	public static String TelaSchoolUID="telaSchoolUID";
	public static String EMISNumber="EMISNumber";


	public SchoolListGrid() { 
		super();
		
		dataSource = SchoolDataSource.getInstance();
		
		ListGridField idField = new ListGridField(ID, "Id");
		idField.setHidden(true);

		ListGridField codeField = new ListGridField(CODE, "Code");
		ListGridField nameField = new ListGridField(NAME, "Name");
		ListGridField deviceNumberField = new ListGridField(DEVICE_NUMBER, "Tela Phone Number");
		
		ListGridField attendanceTrackedField = new ListGridField(ATTENDANCETRACKED, "Attendance Tracked"); 
		attendanceTrackedField.setHidden(true);
		
		ListGridField locationField = new ListGridField(LOCATION, "Location");
		locationField.setHidden(true);
		
		ListGridField latitudeField = new ListGridField(LATITUDE, "Latitude");
		ListGridField longitudeField = new ListGridField(LONGITUDE, "Longitude");
		latitudeField.setHidden(true);
		longitudeField.setHidden(true);
		
		ListGridField categoryField = new ListGridField(CATEGORY, "School Foundation Body");
		categoryField.setHidden(true);
		
		ListGridField categoryIdField = new ListGridField(CATEGORY_ID, "School Foundation Body Id");
		categoryIdField.setHidden(true);
		
		
		ListGridField districtField = new ListGridField(DISTRICT, "Local Government");
		ListGridField districtIdField = new ListGridField(DISTRICT_ID, "District Id");
		districtIdField.setHidden(true);
		
		ListGridField schoolLevel = new ListGridField(SchoolLevel, "Level");
		ListGridField schoolOwnership = new ListGridField(SchoolOwnership, "Ownership");
		ListGridField schoolType = new ListGridField(SchoolType, "Type");
		ListGridField schoolGenderCategory = new ListGridField(SchoolGenderCategory, "Gender Category");
		ListGridField licensed = new ListGridField(Licensed, "license Status");
		
		ListGridField rollout = new ListGridField(Rollout, "Rollout Phase");
		
		ListGridField telaSchoolNumber= new ListGridField(TelaSchoolNumber, "Tela School Number");
		telaSchoolNumber.setHidden(true);
		
		ListGridField regionField = new ListGridField(REGION, "Sub-Region");
		regionField.setHidden(true);
		
		ListGridField regionIdField = new ListGridField(REGION_ID, "Sub-Region Id");
		regionIdField.setHidden(true);
		
		ListGridField telaSchoolUID= new ListGridField(TelaSchoolUID, "Tela School Number");
		ListGridField emisNumber= new ListGridField(EMISNumber, "EMIS Number");
		codeField.setHidden(true); 
		emisNumber.setHidden(true); 
		  
		this.setFields(idField ,categoryIdField , districtIdField , telaSchoolUID,deviceNumberField, codeField , nameField , schoolLevel,schoolOwnership,schoolType,schoolGenderCategory,licensed,categoryField, districtField , attendanceTrackedField , locationField , 
				regionField,regionIdField,latitudeField , latitudeField,rollout,telaSchoolNumber,emisNumber);
		this.setDataSource(dataSource);
		this.setSelectionType(SelectionStyle.SIMPLE);
		this.setWrapHeaderTitles(true);
		
		ListGridField rowNumberFieldProperties=new ListGridField();
		rowNumberFieldProperties.setWidth(40); 
		this.setRowNumberFieldProperties(rowNumberFieldProperties);

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
			
			if(schoolDTO.getDistrictDTO().getRegion()!=null) {
				record.setAttribute(REGION, schoolDTO.getDistrictDTO().getRegion().getName());
				record.setAttribute(REGION_ID, schoolDTO.getDistrictDTO().getRegion().getId());
			}
		}
		
		record.setAttribute(DEVICE_NUMBER, schoolDTO.getDeviceNumber());
		
		if(schoolDTO.getSchoolCategoryDTO() != null) {
			record.setAttribute(CATEGORY, schoolDTO.getSchoolCategoryDTO().getName());
			record.setAttribute(CATEGORY_ID, schoolDTO.getSchoolCategoryDTO().getId());
					
		}
	
		String att = schoolDTO.isAttendanceTracked() ? "Yes" : "No" ;
		record.setAttribute(ATTENDANCETRACKED, att);
		
		record.setAttribute(SchoolLevel, schoolDTO.getSchoolLevel());
		record.setAttribute(SchoolOwnership, schoolDTO.getSchoolOwnership());
		record.setAttribute(SchoolType, schoolDTO.getSchoolType());
		record.setAttribute(SchoolGenderCategory, schoolDTO.getSchoolGenderCategory());
		record.setAttribute(Licensed, schoolDTO.getLicensed());
		
		record.setAttribute(Rollout, schoolDTO.getRolloutPhase());
		
		record.setAttribute(TelaSchoolNumber, schoolDTO.getTelaSchoolNumber());
		
		record.setAttribute(TelaSchoolUID, schoolDTO.getTelaSchoolUID());
		record.setAttribute(EMISNumber, schoolDTO.getEmisNumber());
		
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
			
			
			DataSourceTextField schoolLevel = new DataSourceTextField(SchoolLevel, "Level");
			DataSourceTextField schoolOwnership = new DataSourceTextField(SchoolOwnership, "Ownership");
			DataSourceTextField schoolType = new DataSourceTextField(SchoolType, "Type");
			DataSourceTextField schoolGenderCategory = new DataSourceTextField(SchoolGenderCategory, "Gender Category");
			DataSourceTextField licensed = new DataSourceTextField(Licensed, "license Status");
			
			DataSourceTextField rollout = new DataSourceTextField(Rollout, "Rollout Phase");
			
			DataSourceTextField telaSchoolNumber = new DataSourceTextField(TelaSchoolNumber, "Tela School Number");
			
			DataSourceTextField regionField = new DataSourceTextField(REGION, "Sub-Region");
			 
			
			DataSourceTextField regionIdField = new DataSourceTextField(REGION_ID, "Sub-Region Id");
			
			DataSourceTextField telaSchoolUID= new DataSourceTextField(TelaSchoolUID, "Tela School Number");
			DataSourceTextField emisNumber= new DataSourceTextField(EMISNumber, "EMIS Number");
			  
			this.setFields(idField ,categoryIdField , districtIdField , telaSchoolUID,deviceNumberField, codeField , nameField , schoolLevel,schoolOwnership,schoolType,schoolGenderCategory,licensed,categoryField, districtField , attendanceTrackedField , locationField , 
					regionField,regionIdField,latitudeField , latitudeField,rollout,telaSchoolNumber,emisNumber);
			
			setClientOnly(true);

		}
	}
	
	
}
