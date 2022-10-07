package com.planetsystems.tela.managementapp.client.presenter.batchupload;

import java.util.List;

import com.planetsystems.tela.dto.TelaSchoolLicenseDTO;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class TelaSchoolLicenseListgrid extends SuperListGrid {
	public static String ID = "id";

	public static String LicenseKey = "licenseKey";
	public static String School = "school";
	public static String District = "district";
	public static String SchoolTelaNumber = "schoolTelaNumber";

	private TelaSchoolLicenseSource dataSource;

	public TelaSchoolLicenseListgrid() {
		super();

		dataSource = TelaSchoolLicenseSource.getInstance();

		ListGridField idField = new ListGridField(ID, "Id");
		idField.setHidden(true);

		ListGridField licenseKey = new ListGridField(LicenseKey, "License Key");
		ListGridField school = new ListGridField(School, "School");
		ListGridField district = new ListGridField(District, "District");
		
		ListGridField schoolTelaNumber = new ListGridField(SchoolTelaNumber, "Tela Number");
		 

		this.setDataSource(dataSource);
		this.setFields(idField, district, school,schoolTelaNumber, licenseKey);
		this.setSelectionType(SelectionStyle.SIMPLE);
		this.setWrapHeaderTitles(true);
		
		ListGridField rowNumberFieldProperties=new ListGridField();
		rowNumberFieldProperties.setWidth(40); 
		this.setRowNumberFieldProperties(rowNumberFieldProperties);
	}

	public ListGridRecord addRowData(TelaSchoolLicenseDTO dto) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute(ID, dto.getId());

		if (dto.getTelaLicenseKey() != null) {
			record.setAttribute(LicenseKey, dto.getTelaLicenseKey().getLicenseKey());
		}

		if (dto.getSchool() != null) {
			record.setAttribute(School, dto.getSchool().getName());
			record.setAttribute(SchoolTelaNumber, dto.getSchool().getTelaSchoolUID());

			
			if (dto.getSchool().getDistrictDTO() != null) {
				record.setAttribute(District, dto.getSchool().getDistrictDTO().getName());
			}
			
		}

		return record;
	}

	public void addRecordsToGrid(List<TelaSchoolLicenseDTO> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;
		for (TelaSchoolLicenseDTO item : list) {
			records[row] = addRowData(item);
			row++;
		}
		this.setData(records);
		dataSource.setTestData(records);
	}

	public static class TelaSchoolLicenseSource extends DataSource {

		private static TelaSchoolLicenseSource instance = null;

		public static TelaSchoolLicenseSource getInstance() {
			if (instance == null) {
				instance = new TelaSchoolLicenseSource("TelaSchoolLicenseSource");
			}
			return instance;
		}

		public TelaSchoolLicenseSource(String id) {
			setID(id);

			DataSourceTextField idField = new DataSourceTextField(ID, "Id");
			idField.setHidden(true);
			idField.setPrimaryKey(true);

			DataSourceTextField licenseKey = new DataSourceTextField(LicenseKey, "License Key");
			DataSourceTextField school = new DataSourceTextField(School, "School");
			DataSourceTextField district = new DataSourceTextField(District, "District");
			DataSourceTextField schoolTelaNumber = new DataSourceTextField(SchoolTelaNumber, "Tela Number");

			this.setFields(idField, district, school, schoolTelaNumber,licenseKey);
			setClientOnly(true);

		}
	}

}
