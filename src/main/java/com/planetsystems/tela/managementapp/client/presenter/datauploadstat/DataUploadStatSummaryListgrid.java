package com.planetsystems.tela.managementapp.client.presenter.datauploadstat;

import java.util.List;
 
import com.planetsystems.tela.dto.reports.DataUploadStatSummaryDTO;
import com.planetsystems.tela.managementapp.client.presenter.datauploadstat.DataUploadStatListgrid.DataUploadStatDataSource;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class DataUploadStatSummaryListgrid extends SuperListGrid {

	public static String ID = "id";
	public static String Region = "region";
	public static String DistrictId = "districtId";
	public static String District = "district";
	public static String PrimarySchoolsEnrolled = "primarySchoolsEnrolled";
	public static String SecondarySchoolsEnrolled = "secondarySchoolsEnrolled";
	public static String CAISchoolsEnrolled = "CAISchoolsEnrolled";
	public static String PrimarySchoolsStaffUpload = "primarySchoolsStaffUpload";
	public static String SecondarySchoolsStaffUpload = "SecondarySchoolsStaffUpload";
	public static String CAISchoolsStaffUpload = "CAISchoolsStaffUpload";

	DataUploadStatDataSource dataSource;

	public DataUploadStatSummaryListgrid() {
		super();

		ListGridField id = new ListGridField(ID, "Id");
		id.setHidden(true);

		ListGridField region = new ListGridField(Region, "Region");
		ListGridField districtId = new ListGridField(DistrictId, "district Id");
		districtId.setHidden(true); 
		ListGridField district = new ListGridField(District, "Local Government");
		ListGridField primarySchoolsEnrolled = new ListGridField(PrimarySchoolsEnrolled,
				"No. Primary Schools Enrolled");
		ListGridField secondarySchoolsEnrolled = new ListGridField(SecondarySchoolsEnrolled,
				"No. Secondary Schools Enrolled"); 
		ListGridField cAISchoolsEnrolled = new ListGridField(CAISchoolsEnrolled, "No. CAI Schools Enrolled");
		
		
		ListGridField primarySchoolsStaffUpload = new ListGridField(PrimarySchoolsStaffUpload,
				"No. Primary School Submissions");
		ListGridField secondarySchoolsStaffUpload = new ListGridField(SecondarySchoolsStaffUpload,
				"No. Secondary School Submissions");
		ListGridField cAISchoolsStaffUpload = new ListGridField(CAISchoolsStaffUpload,
				"No. CAI School Submissions");

		this.setFields(region, districtId, district, primarySchoolsEnrolled, secondarySchoolsEnrolled,
				cAISchoolsEnrolled, primarySchoolsStaffUpload, secondarySchoolsStaffUpload, cAISchoolsStaffUpload);
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

	public ListGridRecord addRowData(DataUploadStatSummaryDTO dto) {
		ListGridRecord record = new ListGridRecord();

		record.setAttribute(ID, dto.getDistrictId());

		record.setAttribute(Region, dto.getRegion());
		record.setAttribute(District, dto.getDistrict());
		record.setAttribute(DistrictId, dto.getDistrictId());
		record.setAttribute(PrimarySchoolsEnrolled, dto.getPrimarySchoolsEnrolled());
		record.setAttribute(SecondarySchoolsEnrolled, dto.getSecondarySchoolsEnrolled());
		record.setAttribute(CAISchoolsEnrolled, dto.getCAISchoolsEnrolled());
		record.setAttribute(PrimarySchoolsStaffUpload, dto.getPrimarySchoolsStaffUpload());
		record.setAttribute(SecondarySchoolsStaffUpload, dto.getSecondarySchoolsStaffUpload());
		record.setAttribute(CAISchoolsStaffUpload, dto.getCAISchoolsStaffUpload());

		return record;
	}

	public void addRecordsToGrid(List<DataUploadStatSummaryDTO> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;
		for (DataUploadStatSummaryDTO item : list) {
			records[row] = addRowData(item);
			row++;
		}
		this.setData(records);
		dataSource.setTestData(records);
	}

	public static class DataUploadStatSummaryDataSource extends DataSource {

		private static DataUploadStatSummaryDataSource instance = null;

		public static DataUploadStatSummaryDataSource getInstance() {
			if (instance == null) {
				instance = new DataUploadStatSummaryDataSource("DataUploadStatSummaryDataSource");
			}
			return instance;
		}

		public DataUploadStatSummaryDataSource(String id) {
			setID(id);

			DataSourceTextField idField = new DataSourceTextField(ID, "Id");
			idField.setPrimaryKey(true);

			DataSourceTextField region = new DataSourceTextField(Region, "Region");
			DataSourceTextField districtId = new DataSourceTextField(DistrictId, "district Id");
			DataSourceTextField district = new DataSourceTextField(District, "Local Government");
			DataSourceTextField primarySchoolsEnrolled = new DataSourceTextField(PrimarySchoolsEnrolled,
					"No. Primary Schools Enrolled");
			DataSourceTextField secondarySchoolsEnrolled = new DataSourceTextField(SecondarySchoolsEnrolled,
					"No. Secondary Schools Enrolled");
			DataSourceTextField cAISchoolsEnrolled = new DataSourceTextField(CAISchoolsEnrolled,
					"No. CAI Schools Enrolled");
			DataSourceTextField primarySchoolsStaffUpload = new DataSourceTextField(PrimarySchoolsStaffUpload,
					"No. Primary Schools Staff Uploaded");
			DataSourceTextField secondarySchoolsStaffUpload = new DataSourceTextField(SecondarySchoolsStaffUpload,
					"No. Secondary Schools Staff Uploaded");
			DataSourceTextField cAISchoolsStaffUpload = new DataSourceTextField(CAISchoolsStaffUpload,
					"No. CAI Schools Staff Uploaded");

			this.setFields(region, districtId, district, primarySchoolsEnrolled, secondarySchoolsEnrolled,
					cAISchoolsEnrolled, primarySchoolsStaffUpload, secondarySchoolsStaffUpload, cAISchoolsStaffUpload);

			setClientOnly(true);
		}
	}

}
