package com.planetsystems.tela.managementapp.client.presenter.learnerenrollment;

import java.util.List;

import com.planetsystems.tela.dto.LearnerDetailDTO;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class LeanerDetailsListgrid extends SuperListGrid {

	public static String ID = "id";
	public static String FIRSTNAME = "firstName";
	public static String LASTNAME = "lastName";
	public static String OTHERNAME = "otherName";
	public static String GENDER = "gender";
	public static String NATIONALITY = "nationality";
	public static String DOB = "dob";
	public static String HOME_DISTRICT_ID = "districtId";
	public static String HOME_DISTRICT = "district";

	public static String NATIONAL_ID = "nationalId";
	public static String PHONE_NUMBER = "phoneNumber";

	public static String LearnTelaNo = "learnTelaNo";
	public static String HasSpecialNeeds = "hasSpecialNeeds";
	public static String OrphanCategory = "orphanCategory";

	LearnerDetailsDataSource dataSource;

	public LeanerDetailsListgrid() {
		super();

		dataSource = LearnerDetailsDataSource.getInstance();

		ListGridField idField = new ListGridField(ID, "Id");
		idField.setHidden(true);

		ListGridField firstNameField = new ListGridField(FIRSTNAME, "First Name");
		ListGridField lastNameField = new ListGridField(LASTNAME, "Last Name");
		ListGridField otherNameField = new ListGridField(OTHERNAME, "Other Name");
		ListGridField genderField = new ListGridField(GENDER, "Gender");
		ListGridField nationality = new ListGridField(NATIONALITY, "Nationality");
		ListGridField dobField = new ListGridField(DOB, "D.O.B");
		ListGridField districtField = new ListGridField(HOME_DISTRICT, "Home District");
		ListGridField districtIdField = new ListGridField(HOME_DISTRICT_ID, "Home District Id");
		districtIdField.setHidden(true);
		ListGridField nationalIdField = new ListGridField(NATIONAL_ID, "N.I.N");
		ListGridField phoneNumberField = new ListGridField(PHONE_NUMBER, "Phone Number");
		ListGridField learnTelaNo = new ListGridField(LearnTelaNo, "Tela No.");
		ListGridField hasSpecialNeeds = new ListGridField(HasSpecialNeeds, "Has Special Needs?");
		ListGridField orphanCategory = new ListGridField(OrphanCategory, "Orphan Category");

		this.setFields(idField, learnTelaNo, firstNameField, lastNameField, otherNameField, genderField, nationality,
				dobField, districtField, districtIdField, nationalIdField, phoneNumberField, hasSpecialNeeds,
				orphanCategory);
		this.setDataSource(dataSource);
		this.setWrapHeaderTitles(true);
		this.setSelectionType(SelectionStyle.SIMPLE);
	}

	public ListGridRecord addRowData(LearnerDetailDTO dto) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute(ID, dto.getId());

		record.setAttribute(FIRSTNAME, dto.getFirstName());
		record.setAttribute(LASTNAME, dto.getLastName());
		record.setAttribute(OTHERNAME, dto.getOtherName());
		record.setAttribute(GENDER, dto.getGender());
		record.setAttribute(NATIONALITY, dto.getNationality());
		record.setAttribute(DOB, dto.getDob());
		record.setAttribute(HOME_DISTRICT, dto.getHomeDistrict().getName());
		record.setAttribute(HOME_DISTRICT_ID, dto.getHomeDistrict().getId());

		record.setAttribute(NATIONAL_ID, dto.getNationalId());
		record.setAttribute(PHONE_NUMBER, dto.getPhoneNumber());
		record.setAttribute(LearnTelaNo, dto.getLearnTelaNo());
		record.setAttribute(HasSpecialNeeds, dto.isHasSpecialNeeds());
		record.setAttribute(OrphanCategory, dto.getOrphanCategory());

		return record;
	}

	public void addRecordsToGrid(List<LearnerDetailDTO> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;
		for (LearnerDetailDTO item : list) {
			records[row] = addRowData(item);
			row++;
		}
		this.setData(records);
		dataSource.setTestData(records);
	}

	public static class LearnerDetailsDataSource extends DataSource {

		private static LearnerDetailsDataSource instance = null;

		public static LearnerDetailsDataSource getInstance() {
			if (instance == null) {
				instance = new LearnerDetailsDataSource("LearnerDetailsDataSource");
			}
			return instance;
		}

		public LearnerDetailsDataSource(String id) {
			setID(id);

			DataSourceTextField idField = new DataSourceTextField(ID, "Id");
			idField.setHidden(true);
			idField.setPrimaryKey(true);

			DataSourceTextField firstNameField = new DataSourceTextField(FIRSTNAME, "First Name");
			DataSourceTextField lastNameField = new DataSourceTextField(LASTNAME, "Last Name");
			DataSourceTextField otherNameField = new DataSourceTextField(OTHERNAME, "Other Name");
			DataSourceTextField genderField = new DataSourceTextField(GENDER, "Gender");
			DataSourceTextField nationality = new DataSourceTextField(NATIONALITY, "Nationality");
			DataSourceTextField dobField = new DataSourceTextField(DOB, "D.O.B");
			DataSourceTextField districtField = new DataSourceTextField(HOME_DISTRICT, "Home District");
			DataSourceTextField districtIdField = new DataSourceTextField(HOME_DISTRICT_ID, "Home District Id");

			DataSourceTextField nationalIdField = new DataSourceTextField(NATIONAL_ID, "N.I.N");
			DataSourceTextField phoneNumberField = new DataSourceTextField(PHONE_NUMBER, "Phone Number");
			DataSourceTextField learnTelaNo = new DataSourceTextField(LearnTelaNo, "Tela No.");
			DataSourceTextField hasSpecialNeeds = new DataSourceTextField(HasSpecialNeeds, "Has Special Needs?");
			DataSourceTextField orphanCategory = new DataSourceTextField(OrphanCategory, "Orphan Category");

			this.setFields(idField, learnTelaNo, firstNameField, lastNameField, otherNameField, genderField,
					nationality, dobField, districtField, districtIdField, nationalIdField, phoneNumberField,
					hasSpecialNeeds, orphanCategory);
			setClientOnly(true);

		}
	}

}
