package com.planetsystems.tela.managementapp.client.presenter.subjectcategory;

import java.util.List;

import com.planetsystems.tela.dto.SubjectCategoryDTO;
import com.planetsystems.tela.managementapp.client.presenter.region.RegionListGrid.RegionDataSource;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class SubCategoryListGrid extends SuperListGrid {

	public static String ID = "id";
	public static String CODE = "code";
	public static String NAME = "name";

/*
 *    private String code;
    private String name;
 */
	SubjectCategoryDataSource dataSource;

	public SubCategoryListGrid() { 
		super();
		
		dataSource = SubjectCategoryDataSource.getInstance();
		
		ListGridField idField = new ListGridField(ID, "Id");
		idField.setHidden(true);

		ListGridField codeField = new ListGridField(CODE, "Code");
		ListGridField nameField = new ListGridField(NAME, "Name");
		
		

		this.setFields(idField , codeField , nameField );
		
		this.setDataSource(dataSource);

	}

	public ListGridRecord addRowData(SubjectCategoryDTO subjectCategoryDTO) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute(ID, subjectCategoryDTO.getId());
		record.setAttribute(CODE, subjectCategoryDTO.getCode());
		record.setAttribute(NAME, subjectCategoryDTO.getName());
		
		return record;
	}

	public void addRecordsToGrid(List<SubjectCategoryDTO> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;
		for (SubjectCategoryDTO item : list) {
			records[row] = addRowData(item);
			row++;
		}
		this.setData(records);
		dataSource.setTestData(records);
	}
	
	
	public static class SubjectCategoryDataSource extends DataSource {

		private static SubjectCategoryDataSource instance = null;

		public static SubjectCategoryDataSource getInstance() {
			if (instance == null) {
				instance = new SubjectCategoryDataSource("RegionDataSource");
			}
			return instance;
		}

		public SubjectCategoryDataSource(String id) {
			setID(id);
			
			DataSourceTextField idField = new DataSourceTextField(ID, "Id");
			idField.setHidden(true);
			idField.setPrimaryKey(true);

			DataSourceTextField codeField = new DataSourceTextField(CODE, "Code");
			DataSourceTextField nameField = new DataSourceTextField(NAME, "Name");
			
			

			this.setFields(idField , codeField , nameField );
			setClientOnly(true);

		}
	}
	
}
