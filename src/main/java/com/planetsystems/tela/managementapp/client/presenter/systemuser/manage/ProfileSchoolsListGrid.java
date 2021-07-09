package com.planetsystems.tela.managementapp.client.presenter.systemuser.manage;

import java.util.List;

import com.planetsystems.tela.dto.SchoolDTO;
import com.planetsystems.tela.dto.SystemUserProfileSchoolDTO;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class ProfileSchoolsListGrid extends SuperListGrid {
	public static String ID = "id";
	public static String DISTRICT = "district";
	public static String DISTRICT_ID = "districtId";
	public static String SCHOOL = "school";
	public static String SCHOOL_ID = "schoolId";
	public static String REGION_ID = "region_id";
	public static String REGION = "region";
	
	private SchoolDataSource dataSource;


	public ProfileSchoolsListGrid() { 
		super();
		
		dataSource = SchoolDataSource.getInstance();
		
		ListGridField idField = new ListGridField(ID, "Id");
		idField.setHidden(true);
		
		ListGridField schoolNameField = new ListGridField(SCHOOL, "Name");
		ListGridField schoolIdField = new ListGridField(SCHOOL_ID, "Id");
		schoolIdField.setHidden(true);

		
		ListGridField districtField = new ListGridField(DISTRICT, "District");
		ListGridField districtIdField = new ListGridField(DISTRICT_ID, "District Id");
		districtIdField.setHidden(true);
		
		ListGridField regionField = new ListGridField(REGION, "Region");
		ListGridField regionIdField = new ListGridField(REGION_ID, "Region Id");
		regionIdField.setHidden(true);

		this.setFields(idField  , districtIdField , regionIdField , schoolIdField , districtField , regionField , schoolNameField);
		this.setDataSource(dataSource);

	}

	public ListGridRecord addRowData(SystemUserProfileSchoolDTO dto) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute(ID, dto.getId());
		
		if(dto.getSchoolDTO() != null) {
			record.setAttribute(SCHOOL, dto.getSchoolDTO().getName());
			record.setAttribute(SCHOOL_ID, dto.getSchoolDTO().getId());
			
			if(dto.getSchoolDTO().getDistrictDTO() != null) {
				record.setAttribute(DISTRICT, dto.getSchoolDTO().getDistrictDTO().getName());
				record.setAttribute(DISTRICT_ID, dto.getSchoolDTO().getDistrictDTO().getId());
				
				if (dto.getSchoolDTO().getDistrictDTO().getRegion() != null) {
					record.setAttribute(REGION , dto.getSchoolDTO().getDistrictDTO().getRegion().getName());
					record.setAttribute(REGION_ID , dto.getSchoolDTO().getDistrictDTO().getRegion().getId());
						
				}
			}
		}
		
		return record;
	}

	public void addRecordsToGrid(List<SystemUserProfileSchoolDTO> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;
		for (SystemUserProfileSchoolDTO item : list) {
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
			
			DataSourceTextField nameField = new DataSourceTextField(SCHOOL, "Name");
			
			DataSourceTextField districtField = new DataSourceTextField(DISTRICT, "District");
			DataSourceTextField districtIdField = new DataSourceTextField(DISTRICT_ID, "District Id");
			districtIdField.setHidden(true);
			
			DataSourceTextField regionField = new DataSourceTextField(REGION, "Region");
			DataSourceTextField regionIdField = new DataSourceTextField(REGION_ID, "Region Id");
			regionIdField.setHidden(true);

			this.setFields(idField , districtIdField , regionIdField , nameField , districtField , regionField);
			setClientOnly(true);

		}
	}
	
	
}
