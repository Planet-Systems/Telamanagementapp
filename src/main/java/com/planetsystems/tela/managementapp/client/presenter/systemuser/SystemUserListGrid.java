package com.planetsystems.tela.managementapp.client.presenter.systemuser;

import java.util.List;
 
import com.planetsystems.tela.dto.SystemUserProfileDTO;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
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

	public SystemUserListGrid() {
		super();

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

		ListGridField nationalIdField = new ListGridField(NATIONAL_ID, "NationalId");

		ListGridField genderField = new ListGridField(GENDER, "Gender");

		ListGridField nameAbbrevField = new ListGridField(NAME_ABBREV, "Name Abbreviation");

		this.setFields(idField, usernameField, firstNameField, lastNameField, emailField, phoneNumberField,
				nationalIdField, dobField, genderField, enabledField, nameAbbrevField, configRoleField);

	}

	public ListGridRecord addRowData(SystemUserProfileDTO dto) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute(ID, dto.getId());

		if (dto.getSystemUser() != null) {
			record.setAttribute(USERNAME, dto.getSystemUser().getUserName());
			record.setAttribute(CONFIG_ROLE, dto.getSystemUser().getConfigRole());
		}

		String enabled = dto.getSystemUser().isEnabled() ? "Yes" : "NO";
		record.setAttribute(ENABLED, enabled);

		if (dto.getGeneralUserDetail() != null) {
			record.setAttribute(FIRST_NAME, dto.getGeneralUserDetail().getFirstName());
			record.setAttribute(LAST_NAME, dto.getGeneralUserDetail().getLastName());
			record.setAttribute(EMAIL, dto.getGeneralUserDetail().getEmail());
			record.setAttribute(NAME_ABBREV, dto.getGeneralUserDetail().getNameAbbrev());
			record.setAttribute(DOB, dto.getGeneralUserDetail().getDob());
			record.setAttribute(NATIONAL_ID, dto.getGeneralUserDetail().getNationalId());
			record.setAttribute(GENDER, dto.getGeneralUserDetail().getGender());
			record.setAttribute(PHONE_NUMBER, dto.getGeneralUserDetail().getPhoneNumber());
		}

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
	}
}
