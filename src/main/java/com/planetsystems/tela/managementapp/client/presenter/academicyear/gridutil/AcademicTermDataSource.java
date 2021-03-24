package com.planetsystems.tela.managementapp.client.presenter.academicyear.gridutil;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class AcademicTermDataSource  extends DataSource{
	public static String ID = "id";
	public static String CODE = "code";
	public static String NAME = "name";
	public static String YEAR = "year";
	public static String YEAR_ID = "yearId";
	public static String STATUS = "status";
	public static String ACTIVATION_STATUS = "activationStatus";
	public static String END_DATE = "endDate";
	public static String START_DATE = "startDate";
	 private static AcademicTermDataSource instance = null;  
	  
	    public static AcademicTermDataSource getInstance() {  
	        if (instance == null) {  
	            instance = new AcademicTermDataSource("academicDS");  
	        }  
	        return instance;  
	    }

		public AcademicTermDataSource(String id) {
			setID(id);
			
			DataSourceTextField idField = new DataSourceTextField(ID, "Id");
			idField.setHidden(true);
			idField.setPrimaryKey(true);
			
			

			DataSourceTextField codeField = new DataSourceTextField(CODE, "Code");
			DataSourceTextField yearField = new DataSourceTextField(YEAR, "Academic year");
			DataSourceTextField yearIdField = new DataSourceTextField(YEAR_ID, "Academic year id");
			yearIdField.setHidden(true);
			
			DataSourceTextField nameField = new DataSourceTextField(NAME, "Academic Term");
			DataSourceTextField statusField= new DataSourceTextField(STATUS, "Status");
			DataSourceTextField startDateField = new DataSourceTextField(START_DATE, "Start date(DD/MM/YYYY)");
			DataSourceTextField endDateField= new DataSourceTextField(END_DATE, "End date(DD/MM/YYYY)");
			
			this.setFields(idField, yearIdField , yearField ,codeField , nameField ,statusField ,startDateField , endDateField);
			
			setClientOnly(true);
		} 
	    
	    
	  

}
