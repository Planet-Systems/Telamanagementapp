package com.planetsystems.tela.managementapp.client.presenter.schoolcategory;

import java.util.List;

import com.planetsystems.tela.dto.SchoolCategoryDTO;
import com.planetsystems.tela.managementapp.client.presenter.region.RegionListGrid.RegionDataSource;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class SchCategoryListGrid extends SuperListGrid {
	public static String ID = "id";
	public static String CODE = "code";
	public static String NAME = "name";
	
	private SchoolCategoryDataSource dataSource;

/*
 *    private String code;
    private String name;
 */
		

	public SchCategoryListGrid() { 
		super();
		
		dataSource = SchoolCategoryDataSource.getInstance();
		
		ListGridField idField = new ListGridField(ID, "Id");
		idField.setHidden(true);

		ListGridField codeField = new ListGridField(CODE, "Code");
		ListGridField nameField = new ListGridField(NAME, "Name");
		
		

		this.setFields(idField , codeField , nameField );
		this.setDataSource(dataSource);

	}

	public ListGridRecord addRowData(SchoolCategoryDTO schoolCategoryDTO) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute(ID, schoolCategoryDTO.getId());
		record.setAttribute(CODE, schoolCategoryDTO.getCode());
		record.setAttribute(NAME, schoolCategoryDTO.getName());
		
		return record;
	}

	public void addRecordsToGrid(List<SchoolCategoryDTO> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;
		for (SchoolCategoryDTO item : list) {
			records[row] = addRowData(item);
			row++;
		}
		this.setData(records);
		dataSource.setTestData(records);
	}
	
	
	public static class SchoolCategoryDataSource extends DataSource {

		private static SchoolCategoryDataSource instance = null;

		public static SchoolCategoryDataSource getInstance() {
			if (instance == null) {
				instance = new SchoolCategoryDataSource("SchoolCategoryDataSource");
			}
			return instance;
		}

		public SchoolCategoryDataSource(String id) {
			setID(id);
			
			DataSourceTextField idField = new DataSourceTextField(ID, "Id");
			idField.setHidden(true);
			idField.setPrimaryKey(true);
			
			
			DataSourceTextField codeField = new DataSourceTextField(CODE, "Code");
			DataSourceTextField nameField = new DataSourceTextField(NAME, "Name");
			
			this.setFields(idField , codeField , nameField );

			this.setFields(idField, codeField , nameField);
			setClientOnly(true);

		}
	}
}
