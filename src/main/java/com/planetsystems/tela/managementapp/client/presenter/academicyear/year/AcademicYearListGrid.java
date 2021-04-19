package com.planetsystems.tela.managementapp.client.presenter.academicyear.year;

import java.util.List;

import com.planetsystems.tela.dto.AcademicYearDTO;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceDateField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class AcademicYearListGrid extends SuperListGrid {
	public static String ID = "id";
	public static String CODE = "code";
	public static String NAME = "name";
	public static String STATUS = "status";
	public static String ACTIVATION_STATUS = "activationStatus";
	public static String END_DATE = "endDate";
	public static String START_DATE = "startDate";
	

	private AcademicYearDatasource datasource;
		
	public AcademicYearListGrid() { 
		super();
		
		datasource = AcademicYearDatasource.getInstance();
		
		ListGridField idField = new ListGridField(ID, "Id");
		idField.setHidden(true);

		ListGridField codeField = new ListGridField(CODE, "Code");
		ListGridField nameField = new ListGridField(NAME, "Academic year");
		ListGridField statusField= new ListGridField(STATUS, "Status");
		ListGridField startDateField = new ListGridField(START_DATE, "Start date");
		ListGridField endDateField= new ListGridField(END_DATE, "End date");
		
		
        this.setDataSource(datasource);
		this.setFields(idField, codeField , nameField  ,startDateField , endDateField ,statusField);

	}

	public ListGridRecord addRowData(AcademicYearDTO academicYearDTO) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute(ID, academicYearDTO.getId());
		record.setAttribute(CODE, academicYearDTO.getCode());
		
		record.setAttribute(NAME, academicYearDTO.getName());
		record.setAttribute(START_DATE, academicYearDTO.getStartDate());
		record.setAttribute(END_DATE, academicYearDTO.getEndDate() );
		
			record.setAttribute(STATUS, academicYearDTO.getActivationStatus());
		
		
		return record;
	}

	public void addRecordsToGrid(List<AcademicYearDTO> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;
		for (AcademicYearDTO item : list) {
			records[row] = addRowData(item);
			row++;
		}
		this.setData(records);
		datasource.setTestData(records);
	}
	
	
	public static class AcademicYearDatasource extends DataSource {

		 private static AcademicYearDatasource instance = null;  
		  
		    public static AcademicYearDatasource getInstance() {  
		        if (instance == null) {  
		            instance = new AcademicYearDatasource("AcademicYearDatasource");  
		        }  
		        return instance;  
		    }

			public AcademicYearDatasource(String id) {
				setID(id);

				DataSourceTextField idField = new DataSourceTextField(ID, "Id");
				idField.setHidden(true);
				idField.setPrimaryKey(true);

				DataSourceTextField codeField = new DataSourceTextField(CODE, "Code");
				DataSourceTextField nameField = new DataSourceTextField(NAME, "Academic year");
				DataSourceTextField statusField= new DataSourceTextField(STATUS, "Status");
				DataSourceDateField startDateField = new DataSourceDateField(START_DATE, "Start date");
				DataSourceDateField endDateField= new DataSourceDateField(END_DATE, "End date");

				this.setFields(idField, codeField , nameField  ,startDateField , endDateField ,statusField);
				
				setClientOnly(true);
			} 

		
	}
}
