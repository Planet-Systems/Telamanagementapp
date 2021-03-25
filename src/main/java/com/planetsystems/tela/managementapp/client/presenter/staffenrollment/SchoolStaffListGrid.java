package com.planetsystems.tela.managementapp.client.presenter.staffenrollment;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Tree;
import com.planetsystems.tela.dto.DistrictDTO;
import com.planetsystems.tela.dto.SchoolStaffDTO;
import com.planetsystems.tela.managementapp.client.presenter.region.RegionListGrid.RegionDataSource;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceDateField;
import com.smartgwt.client.data.fields.DataSourceDateTimeField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class SchoolStaffListGrid extends SuperListGrid {
	public static String ID = "id";
	public static String STAFF_CODE = "staffCode";
	public static String REGISTERED = "registered";
	public static String SCHOOL = "school";
	public static String SCHOOL_ID = "schoolId";

	public static String FIRSTNAME = "firstName";
	public static String LASTNAME = "lastName";
	public static String PHONE_NUMBER = "phoneNumber";
	public static String EMAIL = "email";
	public static String DOB = "dob";
	public static String NATIONAL_ID = "nationalId";
	public static String GENDER = "gender";
	public static String NAME_ABBREV = "nameAbbrev";

	/*
	 * private String firstName; private String lastName; private String
	 * phoneNumber; private String email; private String dob; private String
	 * nationalId; private String gender; private String nameAbrev;
	 */

	/*
	 * private String staffCode; private String staffType; private boolean
	 * registered;
	 * 
	 * 
	 * private GeneralUserDetailDTO generalUserDetailDTO; private SchoolDTO
	 * schoolDTO;
	 */

	SchoolStaffDataSource dataSource;

	public SchoolStaffListGrid() {
		super();

		dataSource = SchoolStaffDataSource.getInstance();

		ListGridField idField = new ListGridField(ID, "Id");
		idField.setHidden(true);

		ListGridField staffCodeField = new ListGridField(STAFF_CODE, "Pin Code");
		ListGridField registeredField = new ListGridField(REGISTERED, "Registerd");

		ListGridField schoolField = new ListGridField(SCHOOL, "School");
		ListGridField schoolIdField = new ListGridField(SCHOOL_ID, "School Id");
		schoolIdField.setHidden(true);

		ListGridField firstNameField = new ListGridField(FIRSTNAME, "First Name");
		ListGridField lastNameField = new ListGridField(LASTNAME, "Last Name");
		ListGridField phoneNumberField = new ListGridField(PHONE_NUMBER, "Phone Number");
		ListGridField emailField = new ListGridField(EMAIL, "Email");
		ListGridField dobField = new ListGridField(DOB, "D.O.B");
		ListGridField nationalIdField = new ListGridField(NATIONAL_ID, "N.I.N");
		ListGridField genderField = new ListGridField(GENDER, "Gender");
		ListGridField nameAbrevField = new ListGridField(NAME_ABBREV, "Name Abbreviation");

		this.setFields(idField, schoolIdField, staffCodeField, firstNameField, lastNameField, genderField,
				phoneNumberField, emailField, nationalIdField, dobField, nameAbrevField, schoolField, registeredField);
		this.setDataSource(dataSource);
	}

	public ListGridRecord addRowData(SchoolStaffDTO schoolStaffDTO) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute(ID, schoolStaffDTO.getId());
		record.setAttribute(STAFF_CODE, schoolStaffDTO.getStaffCode());
		String registered = schoolStaffDTO.isRegistered() ? "Yes" : "NO";
		record.setAttribute(REGISTERED, registered);

		if (schoolStaffDTO.getSchoolDTO() != null) {
			record.setAttribute(SCHOOL_ID, schoolStaffDTO.getSchoolDTO().getId());
			record.setAttribute(SCHOOL, schoolStaffDTO.getSchoolDTO().getName());
		}

		if (schoolStaffDTO.getGeneralUserDetailDTO() != null) {
			GWT.log("DETAILS " + schoolStaffDTO.getGeneralUserDetailDTO());
			record.setAttribute(FIRSTNAME, schoolStaffDTO.getGeneralUserDetailDTO().getFirstName());
			record.setAttribute(LASTNAME, schoolStaffDTO.getGeneralUserDetailDTO().getLastName());
			record.setAttribute(EMAIL, schoolStaffDTO.getGeneralUserDetailDTO().getEmail());
			record.setAttribute(NAME_ABBREV, schoolStaffDTO.getGeneralUserDetailDTO().getNameAbbrev());
			record.setAttribute(DOB, schoolStaffDTO.getGeneralUserDetailDTO().getDob());
			record.setAttribute(NATIONAL_ID, schoolStaffDTO.getGeneralUserDetailDTO().getNationalId());
			record.setAttribute(GENDER, schoolStaffDTO.getGeneralUserDetailDTO().getGender());
			record.setAttribute(PHONE_NUMBER, schoolStaffDTO.getGeneralUserDetailDTO().getPhoneNumber());
		}

		return record;
	}

	public void addRecordsToGrid(List<SchoolStaffDTO> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;
		for (SchoolStaffDTO item : list) {
			records[row] = addRowData(item);
			row++;
		}
		this.setData(records);
		dataSource.setTestData(records);
	}

	
	
	public static class SchoolStaffDataSource extends DataSource {

		private static SchoolStaffDataSource instance = null;

		public static SchoolStaffDataSource getInstance() {
			if (instance == null) {
				instance = new SchoolStaffDataSource("SchoolStaffDataSource");
			}
			return instance;
		}

		public SchoolStaffDataSource(String id) {
			setID(id);

			DataSourceTextField idField = new DataSourceTextField(ID, "Id");
			idField.setHidden(true);
			idField.setPrimaryKey(true);

			DataSourceTextField staffCodeField = new DataSourceTextField(STAFF_CODE, "Pin Code");
			DataSourceTextField registeredField = new DataSourceTextField(REGISTERED, "Registerd");

			DataSourceTextField schoolField = new DataSourceTextField(SCHOOL, "School");
			DataSourceTextField schoolIdField = new DataSourceTextField(SCHOOL_ID, "School Id");
			schoolIdField.setHidden(true);

			DataSourceTextField firstNameField = new DataSourceTextField(FIRSTNAME, "First Name");
			DataSourceTextField lastNameField = new DataSourceTextField(LASTNAME, "Last Name");
			DataSourceTextField phoneNumberField = new DataSourceTextField(PHONE_NUMBER, "Phone Number");
			DataSourceTextField emailField = new DataSourceTextField(EMAIL, "Email");
			DataSourceDateField dobField = new DataSourceDateField(DOB, "D.O.B");
			DataSourceTextField nationalIdField = new DataSourceTextField(NATIONAL_ID, "N.I.N");
			DataSourceTextField genderField = new DataSourceTextField(GENDER, "Gender");
			DataSourceTextField nameAbrevField = new DataSourceTextField(NAME_ABBREV, "Name Abbreviation");

			this.setFields(idField, schoolIdField, staffCodeField, firstNameField, lastNameField, genderField,
					phoneNumberField, emailField, nationalIdField, dobField, nameAbrevField, schoolField, registeredField);
			setClientOnly(true);

		}
	}

}
