package com.planetsystems.tela.managementapp.client.presenter.learnerattendance.listgridutil;

import java.util.List;

import com.google.gwt.user.client.ui.Tree;
import com.planetsystems.tela.dto.AcademicTermDTO;
import com.planetsystems.tela.managementapp.client.presenter.academicyear.gridutil.AcademicTermDataSource;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class AcademicTermListGrid extends SuperListGrid {
	
	public static String ID = "id";
	public static String CODE = "code";
	public static String NAME = "name";
	public static String YEAR = "year";
	public static String YEAR_ID = "yearId";
	public static String STATUS = "status";
	public static String ACTIVATION_STATUS = "activationStatus";
	public static String END_DATE = "endDate";
	public static String START_DATE = "startDate";

//    private String code;
//    private String term;
//    private String activationStatus;
//    private String startDate;
//    private String endDate;
//
//    private AcademicYearDTO academicYear;
		
	AcademicTermDataSource dataSource;
	public AcademicTermListGrid() { 
		super();
		
		 dataSource = AcademicTermDataSource.getInstance();

		ListGridField idField = new ListGridField(ID, "Id");
		idField.setHidden(true);
		//idField.setPrimaryKey(true);

		ListGridField codeField = new ListGridField(CODE, "Code");
		ListGridField yearField = new ListGridField(YEAR, "Academic year");
		ListGridField yearIdField = new ListGridField(YEAR_ID, "Academic year id");
		yearIdField.setHidden(true);
		
		ListGridField nameField = new ListGridField(NAME, "Academic Term");
		ListGridField statusField= new ListGridField(STATUS, "Status");
		ListGridField startDateField = new ListGridField(START_DATE, "Start date(DD/MM/YYYY)");
		ListGridField endDateField= new ListGridField(END_DATE, "End date(DD/MM/YYYY)");
		//ListGridField activationStatusField= new ListGridField(ACTIVATION_STATUS, "Activation status");
		
		this.setDataSource(dataSource);

		this.setFields(idField, yearIdField , yearField ,codeField , nameField ,statusField ,startDateField , endDateField );

	}

	public ListGridRecord addRowData(AcademicTermDTO academicTermDTO) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute(ID, academicTermDTO.getId());
		record.setAttribute(CODE, academicTermDTO.getCode());
		
		record.setAttribute(NAME, academicTermDTO.getTerm());
		if(academicTermDTO.getAcademicYearDTO() != null) {
			record.setAttribute(YEAR, academicTermDTO.getAcademicYearDTO().getName());
			record.setAttribute(YEAR_ID, academicTermDTO.getAcademicYearDTO().getId());
		}
		record.setAttribute(START_DATE, academicTermDTO.getStartDate());
		record.setAttribute(END_DATE, academicTermDTO.getEndDate() );
		
		if(academicTermDTO.getActivationStatus()!=null) {
			record.setAttribute(STATUS, academicTermDTO.getActivationStatus());
		}
		
		return record;
	}

	public void addRecordsToGrid(List<AcademicTermDTO> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;
		for (AcademicTermDTO item : list) {
			records[row] = addRowData(item);
			row++;
		}
		this.setData(records);
		dataSource.setTestData(records);
	}

}
