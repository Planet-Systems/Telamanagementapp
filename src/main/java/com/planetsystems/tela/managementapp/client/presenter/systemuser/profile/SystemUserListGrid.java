package com.planetsystems.tela.managementapp.client.presenter.systemuser.profile;

import java.util.List;

import com.planetsystems.tela.dto.SystemUserProfileDTO;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceDateField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class SystemUserListGrid extends SuperListGrid {
	public static final String ID = "ID";
	public static final String USERNAME = "USERNAME";
	public static final String ENABLED = "ENABLED";
	public static final String CONFIG_ROLE = "CONFIGROLE";
	public static final String FIRST_NAME = "FIRST_NAME";
	public static final String LAST_NAME = "LAST_NAME";
	public static final String PHONE_NUMBER = "PHONE_NUMBER";
	public static final String EMAIL = "EMAIL";
	public static final String DOB = "DOB";
	public static final String NATIONAL_ID = "NATIONAL_ID";
	public static final String GENDER = "GENDER";
	public static final String NAME_ABBREV = "NAME_ABBREV";

	public static final String USER_GROUP = "USER_GROUP";
	public static final String USER_GROUP_ID = "USER_GROUP_ID";

	public static final String STATUS = "STATUS";

	SystemUserDataSource dataSource;

	public SystemUserListGrid() {
		super();

		dataSource = SystemUserDataSource.getInstance();

		ListGridField idField = new ListGridField(ID, "Id");
		idField.setHidden(true);

		ListGridField usernameField = new ListGridField(USERNAME, "Username");
		ListGridField firstNameField = new ListGridField(FIRST_NAME, "First Name");
		ListGridField lastNameField = new ListGridField(LAST_NAME, "Last Name");

		ListGridField enabledField = new ListGridField(ENABLED, "Enabled");
		enabledField.setHidden(true);

		ListGridField configRoleField = new ListGridField(CONFIG_ROLE, "Role");
		configRoleField.setHidden(true);

		ListGridField phoneNumberField = new ListGridField(PHONE_NUMBER, "Phone Number");

		ListGridField emailField = new ListGridField(EMAIL, "Email");

		ListGridField dobField = new ListGridField(DOB, "D.O.B");
		dobField.setHidden(true);

		ListGridField nationalIdField = new ListGridField(NATIONAL_ID, "NationalId");

		ListGridField genderField = new ListGridField(GENDER, "Gender");

		ListGridField nameAbbrevField = new ListGridField(NAME_ABBREV, "Name Abbreviation");
		nameAbbrevField.setHidden(true);

		ListGridField userGroupFieldId = new ListGridField(USER_GROUP_ID, "User Group Id");
		userGroupFieldId.setHidden(true);

		ListGridField userGroupField = new ListGridField(USER_GROUP, "User Group");

		ListGridField status = new ListGridField(STATUS, "Status");
		ListGridField enabled = new ListGridField(ENABLED, "Enbled?");

		this.setFields(idField, usernameField, firstNameField, lastNameField, emailField, userGroupFieldId,
				userGroupField, phoneNumberField, nationalIdField, dobField, genderField, enabledField, nameAbbrevField,
				configRoleField, status, enabled);

		this.setDataSource(dataSource);
	}

	public ListGridRecord addRowData(SystemUserProfileDTO dto) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute(ID, dto.getId());

		if (dto.getSystemUserDTO() != null) {
			record.setAttribute(USERNAME, dto.getSystemUserDTO().getUserName());
			record.setAttribute(CONFIG_ROLE, dto.getSystemUserDTO().getConfigRole());
		}

		String enabled = dto.getSystemUserDTO().isEnabled() ? "Yes" : "NO";
		record.setAttribute(ENABLED, enabled);

		if (dto.getGeneralUserDetailDTO() != null) {
			record.setAttribute(FIRST_NAME, dto.getGeneralUserDetailDTO().getFirstName());
			record.setAttribute(LAST_NAME, dto.getGeneralUserDetailDTO().getLastName());
			record.setAttribute(EMAIL, dto.getGeneralUserDetailDTO().getEmail());
			record.setAttribute(NAME_ABBREV, dto.getGeneralUserDetailDTO().getNameAbbrev());
			record.setAttribute(DOB, dto.getGeneralUserDetailDTO().getDob());
			record.setAttribute(NATIONAL_ID, dto.getGeneralUserDetailDTO().getNationalId());
			record.setAttribute(GENDER, dto.getGeneralUserDetailDTO().getGender());
			record.setAttribute(PHONE_NUMBER, dto.getGeneralUserDetailDTO().getPhoneNumber());
		}

		if (dto.getSystemUserGroupDTO() != null) {
			record.setAttribute(USER_GROUP_ID, dto.getSystemUserGroupDTO().getId());
			record.setAttribute(USER_GROUP, dto.getSystemUserGroupDTO().getName());
		}

		record.setAttribute(STATUS, dto.getStatus());
		record.setAttribute(enabled, dto.getSystemUserDTO().isEnabled());

		return record;
	}

	public void addRecordsToGrid(List<SystemUserProfileDTO> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;
		for (SystemUserProfileDTO item : list) {
			records[row] = addRowData(item);
			row++;
		}
		this.setData(records);
		dataSource.setTestData(records);
	}

	public static class SystemUserDataSource extends DataSource {

		private static SystemUserDataSource instance = null;

		public static SystemUserDataSource getInstance() {
			if (instance == null) {
				instance = new SystemUserDataSource("SystemUserDataSource");
			}
			return instance;
		}

		public SystemUserDataSource(String id) {
			setID(id);

			DataSourceTextField idField = new DataSourceTextField(ID, "Id");
			idField.setHidden(true);
			idField.setPrimaryKey(true);

			DataSourceTextField usernameField = new DataSourceTextField(USERNAME, "Username");
			DataSourceTextField firstNameField = new DataSourceTextField(FIRST_NAME, "First Name");
			DataSourceTextField lastNameField = new DataSourceTextField(LAST_NAME, "Last Name");

			DataSourceTextField enabledField = new DataSourceTextField(ENABLED, "Enabled");

			DataSourceTextField configRoleField = new DataSourceTextField(CONFIG_ROLE, "Role");

			DataSourceTextField phoneNumberField = new DataSourceTextField(PHONE_NUMBER, "Phone Number");

			DataSourceTextField emailField = new DataSourceTextField(EMAIL, "Email");

			DataSourceDateField dobField = new DataSourceDateField(DOB, "D.O.B");

			DataSourceTextField nationalIdField = new DataSourceTextField(NATIONAL_ID, "NationalId");

			DataSourceTextField genderField = new DataSourceTextField(GENDER, "Gender");

			DataSourceTextField nameAbbrevField = new DataSourceTextField(NAME_ABBREV, "Name Abbreviation");

			this.setFields(idField, usernameField, firstNameField, lastNameField, emailField, phoneNumberField,
					nationalIdField, dobField, genderField, enabledField, nameAbbrevField, configRoleField);

			setClientOnly(true);

		}
	}

}
