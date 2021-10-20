package com.planetsystems.tela.managementapp.client.presenter.useraccountapproval;

import java.util.List;

import com.planetsystems.tela.dto.SystemUserProfileDTO;
import com.planetsystems.tela.dto.UserAccountRequestDTO;
import com.planetsystems.tela.managementapp.client.presenter.systemuser.profile.SystemUserListGrid.SystemUserDataSource;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class UserAccountApprovalListgrid extends SuperListGrid {
	public static final String ID = "ID";
	public static final String FIRST_NAME = "FIRST_NAME";
	public static final String LAST_NAME = "LAST_NAME";
	public static final String PHONE_NUMBER = "PHONE_NUMBER";
	public static final String EMAIL = "EMAIL";
	public static final String DOB = "DOB";
	public static final String NATIONAL_ID = "NATIONAL_ID";
	public static final String GENDER = "GENDER";

	public static final String USER_GROUP = "USER_GROUP";
	public static final String USER_GROUP_ID = "USER_GROUP_ID";

	public static final String COMMENT = "COMMENT";

	public UserAccountApprovalListgrid() {
		super();

		ListGridField idField = new ListGridField(ID, "Id");
		idField.setHidden(true);

		ListGridField firstNameField = new ListGridField(FIRST_NAME, "First Name");
		ListGridField lastNameField = new ListGridField(LAST_NAME, "Last Name");

		ListGridField phoneNumberField = new ListGridField(PHONE_NUMBER, "Phone Number");

		ListGridField emailField = new ListGridField(EMAIL, "Email");

		ListGridField dobField = new ListGridField(DOB, "D.O.B");

		ListGridField nationalIdField = new ListGridField(NATIONAL_ID, "NationalId");

		ListGridField genderField = new ListGridField(GENDER, "Gender");

		ListGridField comment = new ListGridField(COMMENT, "Reason");

		ListGridField role = new ListGridField(USER_GROUP, "Role");

		this.setFields(idField, firstNameField, lastNameField, emailField, phoneNumberField, nationalIdField, dobField,
				genderField, role, comment);

	}

	public ListGridRecord addRowData(UserAccountRequestDTO dto) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute(ID, dto.getId());

		record.setAttribute(FIRST_NAME, dto.getFirstName());
		record.setAttribute(LAST_NAME, dto.getLastName());
		record.setAttribute(EMAIL, dto.getEmail());
		record.setAttribute(DOB, dto.getDob());
		record.setAttribute(NATIONAL_ID, dto.getNationalId());
		record.setAttribute(GENDER, dto.getGender());
		record.setAttribute(PHONE_NUMBER, dto.getPhoneNumber());
		record.setAttribute(COMMENT, dto.getComment());
		if (dto.getSystemUserGroup() != null) {
			record.setAttribute(USER_GROUP, dto.getSystemUserGroup().getName());
		}

		return record;
	}

	public void addRecordsToGrid(List<UserAccountRequestDTO> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;
		for (UserAccountRequestDTO item : list) {
			records[row] = addRowData(item);
			row++;
		}
		this.setData(records);
	}

}
