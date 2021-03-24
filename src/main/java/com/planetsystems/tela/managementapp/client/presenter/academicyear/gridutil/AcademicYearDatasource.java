package com.planetsystems.tela.managementapp.client.presenter.academicyear.gridutil;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceDateField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.grid.ListGridField;

public class AcademicYearDatasource extends DataSource {
	
	public static String ID = "id";
	public static String CODE = "code";
	public static String NAME = "name";
	public static String STATUS = "status";
	public static String ACTIVATION_STATUS = "activationStatus";
	public static String END_DATE = "endDate";
	public static String START_DATE = "startDate";
	

	 private static AcademicYearDatasource instance = null;  
	  
	    public static AcademicYearDatasource getInstance() {  
	        if (instance == null) {  
	            instance = new AcademicYearDatasource("academicYearDS");  
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
			//ListGridField activationStatusField= new ListGridField(ACTIVATION_STATUS, "Activation status");
			
			

			this.setFields(idField, codeField , nameField  ,startDateField , endDateField ,statusField);

						
			setClientOnly(true);
		} 

	
}
