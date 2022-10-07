package com.planetsystems.tela.managementapp.client.presenter.batchupload;

import java.util.List;

import com.planetsystems.tela.dto.TelaLicenseKeyDTO;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class TelaLicenseKeyListgrid extends SuperListGrid {
	public static String ID = "id";

	public static String LicenseKey = "licenseKey";
	public static String Product = "product";
	public static String ProductVersion = "productVersion";
	public static String Validity = "validity";
	public static String Activations = "activations";
	public static String LicenseStatus = "licenseStatus";
	public static String ExpirationDate = "expirationDate";

	private TelaLicenseKeySource dataSource;

	public TelaLicenseKeyListgrid() {
		super();

		dataSource = TelaLicenseKeySource.getInstance();

		ListGridField idField = new ListGridField(ID, "Id");
		idField.setHidden(true);

		ListGridField licenseKey = new ListGridField(LicenseKey, "License Key");
		licenseKey.setWidth(300);
		
		ListGridField product = new ListGridField(Product, "Product");
		ListGridField productVersion = new ListGridField(ProductVersion, "Product Version");
		ListGridField validity = new ListGridField(Validity, "Validity");
		ListGridField activations = new ListGridField(Activations, "Activations");
		ListGridField licenseStatus = new ListGridField(LicenseStatus, "Status");
		ListGridField expirationDate = new ListGridField(ExpirationDate, "Expiration Date");

		this.setDataSource(dataSource);
		this.setFields(idField, licenseKey, product, productVersion, validity, activations, licenseStatus,
				expirationDate);
		this.setSelectionType(SelectionStyle.SIMPLE);
		this.setWrapHeaderTitles(true);
		
		ListGridField rowNumberFieldProperties=new ListGridField();
		rowNumberFieldProperties.setWidth(40); 
		this.setRowNumberFieldProperties(rowNumberFieldProperties);
	}

	public ListGridRecord addRowData(TelaLicenseKeyDTO dto) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute(ID, dto.getId());
		record.setAttribute(LicenseKey, dto.getLicenseKey());
		record.setAttribute(Product, dto.getProduct());
		record.setAttribute(ProductVersion, dto.getProductVersion());
		record.setAttribute(Validity, dto.getValidity());
		record.setAttribute(Activations, dto.getActivations());
		record.setAttribute(LicenseStatus, dto.getLicenseStatus());
		record.setAttribute(ExpirationDate, dto.getExpirationDate());

		return record;
	}

	public void addRecordsToGrid(List<TelaLicenseKeyDTO> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;
		for (TelaLicenseKeyDTO item : list) {
			records[row] = addRowData(item);
			row++;
		}
		this.setData(records);
		dataSource.setTestData(records);
	}

	public static class TelaLicenseKeySource extends DataSource {

		private static TelaLicenseKeySource instance = null;

		public static TelaLicenseKeySource getInstance() {
			if (instance == null) {
				instance = new TelaLicenseKeySource("TelaLicenseKeySource");
			}
			return instance;
		}

		public TelaLicenseKeySource(String id) {
			setID(id);

			DataSourceTextField idField = new DataSourceTextField(ID, "Id");
			idField.setHidden(true);
			idField.setPrimaryKey(true);

			DataSourceTextField licenseKey = new DataSourceTextField(LicenseKey, "License Key");
			DataSourceTextField product = new DataSourceTextField(Product, "Product");
			DataSourceTextField productVersion = new DataSourceTextField(ProductVersion, "Product Version");
			DataSourceTextField validity = new DataSourceTextField(Validity, "Validity");
			DataSourceTextField activations = new DataSourceTextField(Activations, "Activations");
			DataSourceTextField licenseStatus = new DataSourceTextField(LicenseStatus, "Status");
			DataSourceTextField expirationDate = new DataSourceTextField(ExpirationDate, "Expiration Date");

			this.setFields(idField, licenseKey, product, productVersion, validity, activations, licenseStatus,
					expirationDate);
			setClientOnly(true);

		}
	}

}
