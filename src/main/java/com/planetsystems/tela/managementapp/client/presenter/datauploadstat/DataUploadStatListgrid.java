package com.planetsystems.tela.managementapp.client.presenter.datauploadstat;

import java.util.List;

import com.planetsystems.tela.dto.reports.DataUploadStatDTO; 
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class DataUploadStatListgrid extends SuperListGrid {

	public static String ID = "id";
	public static String Region = "region";
	public static String District = "district";
	public static String School = "school";
	public static String SchoolTelephoneNo = "schoolTelephoneNo";
	public static String Level = "level";
	public static String SchoolId = "schoolId";
	public static String RolloutPhase = "rolloutPhase";
	public static String UploadedStaff = "uploadedStaff";
	public static String HeadTeacher = "headTeacher";
	public static String Staff = "staff";
	public static String Timetable = "timetable";
	
	DataUploadStatDataSource dataSource;

	public DataUploadStatListgrid() {
		super();

		 
		ListGridField id = new ListGridField(ID, "Id");
		id.setHidden(true);

		ListGridField region = new ListGridField(Region, "Sub-Region");
		ListGridField district = new ListGridField(District, "Local Government");
		ListGridField school = new ListGridField(School, "School");
		ListGridField schoolTelephoneNo = new ListGridField(SchoolTelephoneNo, "School Telephone No.");
		ListGridField level = new ListGridField(Level, "Level");
		ListGridField schoolId = new ListGridField(SchoolId, "school Id");
		schoolId.setHidden(true);

		ListGridField rolloutPhase = new ListGridField(RolloutPhase, "Rollout Phase");
		ListGridField uploadedStaff = new ListGridField(UploadedStaff, "Staff Uploaded?");
		ListGridField headTeacher = new ListGridField(HeadTeacher, "Head Teacher Details Captured?");
		ListGridField staff = new ListGridField(Staff, "No. Staff Uploaded");
		ListGridField timetable = new ListGridField(Timetable, "Timetable Uploaded?");

		this.setFields(region, district, school, schoolTelephoneNo, level, schoolId, rolloutPhase, uploadedStaff,
				headTeacher, staff, timetable);
		this.setWrapHeaderTitles(true);
		this.setHeaderHeight(50);

		this.setSelectionType(SelectionStyle.SIMPLE);
		this.setWrapHeaderTitles(true);

		ListGridField rowNumberFieldProperties = new ListGridField();
		rowNumberFieldProperties.setWidth(40);
		this.setRowNumberFieldProperties(rowNumberFieldProperties);
		
		dataSource = DataUploadStatDataSource.getInstance();
		this.setDataSource(dataSource);
	}

	public ListGridRecord addRowData(DataUploadStatDTO dto) {
		ListGridRecord record = new ListGridRecord();

		record.setAttribute(ID, dto.getSchoolId());
		
		record.setAttribute(Region, dto.getRegion());
		record.setAttribute(District, dto.getDistrict());
		record.setAttribute(School, dto.getSchool());
		record.setAttribute(SchoolTelephoneNo, dto.getSchoolTelephoneNo());
		record.setAttribute(Level, dto.getLevel());
		record.setAttribute(SchoolId, dto.getSchoolId());
		record.setAttribute(RolloutPhase, dto.getRolloutPhase());
		record.setAttribute(Staff, dto.getStaff());

		if (dto.isUploadedStaff()) {
			record.setAttribute(UploadedStaff, "Yes");
		} else {
			record.setAttribute(UploadedStaff, "No");
		}

		if (dto.isHeadTeacher()) {
			record.setAttribute(HeadTeacher, "Yes");
		} else {
			record.setAttribute(HeadTeacher, "No");
		}

		if (dto.isTimetable()) {
			record.setAttribute(Timetable, "Yes");
		} else {
			record.setAttribute(Timetable, "No");
		}

		return record;
	}

	public void addRecordsToGrid(List<DataUploadStatDTO> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;
		for (DataUploadStatDTO item : list) {
			records[row] = addRowData(item);
			row++;
		}
		this.setData(records);
		dataSource.setTestData(records);
	}

	public static class DataUploadStatDataSource extends DataSource {

		private static DataUploadStatDataSource instance = null;

		public static DataUploadStatDataSource getInstance() {
			if (instance == null) {
				instance = new DataUploadStatDataSource("DataUploadStatDataSource");
			}
			return instance;
		}

		public DataUploadStatDataSource(String id) {
			setID(id);

			DataSourceTextField idField = new DataSourceTextField(ID, "Id"); 
			idField.setPrimaryKey(true);

			DataSourceTextField region = new DataSourceTextField(Region, "Sub-Region");
			DataSourceTextField district = new DataSourceTextField(District, "Local Government");
			DataSourceTextField school = new DataSourceTextField(School, "School");
			DataSourceTextField schoolTelephoneNo = new DataSourceTextField(SchoolTelephoneNo, "School Telephone No.");
			DataSourceTextField level = new DataSourceTextField(Level, "Level");
			DataSourceTextField schoolId = new DataSourceTextField(SchoolId, "school Id");
			  
			DataSourceTextField rolloutPhase = new DataSourceTextField(RolloutPhase, "Rollout Phase");
			DataSourceTextField uploadedStaff = new DataSourceTextField(UploadedStaff, "Staff Uploaded?");
			DataSourceTextField headTeacher = new DataSourceTextField(HeadTeacher, "Head Teacher Details Captured?");
			DataSourceTextField staff = new DataSourceTextField(Staff, "No. Staff Uploaded");
			DataSourceTextField timetable = new DataSourceTextField(Timetable, "Timetable Uploaded?");

			this.setFields(region, district, school, schoolTelephoneNo, level, schoolId, rolloutPhase, uploadedStaff,
					headTeacher, staff, timetable);
			setClientOnly(true);
		}
	}

}
