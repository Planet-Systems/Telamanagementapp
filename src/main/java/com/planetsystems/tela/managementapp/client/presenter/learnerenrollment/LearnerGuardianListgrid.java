package com.planetsystems.tela.managementapp.client.presenter.learnerenrollment;

import java.util.List;

import com.planetsystems.tela.dto.LearnerGuardianDTO;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class LearnerGuardianListgrid extends SuperListGrid {
	public static String ID = "id";
	public static String FIRSTNAME = "firstName";
	public static String LASTNAME = "lastName";
	public static String NATIONALITY = "nationality";
	public static String NATIONAL_ID = "nationalId";
	public static String PHONE_NUMBER = "phoneNumber";
	public static String EMAIL = "email";
	public static String RelationShip = "relationShip";

	public LearnerGuardianListgrid() {
		super();

		ListGridField idField = new ListGridField(ID, "Id");
		idField.setHidden(true);

		ListGridField firstNameField = new ListGridField(FIRSTNAME, "First Name");
		ListGridField lastNameField = new ListGridField(LASTNAME, "Last Name");
		ListGridField nationality = new ListGridField(NATIONALITY, "N.I.N");
		ListGridField nationalIdField = new ListGridField(NATIONAL_ID, "N.I.N");
		ListGridField phoneNumberField = new ListGridField(PHONE_NUMBER, "Phone Number");
		ListGridField emailField = new ListGridField(EMAIL, "Email");
		ListGridField relationShip = new ListGridField(RelationShip, "RelationShip");

		this.setFields(idField, firstNameField, lastNameField, nationality, nationalIdField, phoneNumberField,
				emailField, relationShip);

		this.setWrapHeaderTitles(true);
		this.setSelectionType(SelectionStyle.SIMPLE);
	}

	public ListGridRecord addRowData(LearnerGuardianDTO dto) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute(ID, dto.getId());
		record.setAttribute(FIRSTNAME, dto.getFirstName());
		record.setAttribute(LASTNAME, dto.getLastName());
		record.setAttribute(NATIONALITY, dto.getNationality());
		record.setAttribute(NATIONAL_ID, dto.getNationalId());
		record.setAttribute(PHONE_NUMBER, dto.getPhoneNumber());
		record.setAttribute(RelationShip, dto.getGuardianRelationship());

		return record;
	}

	public void addRecordsToGrid(List<LearnerGuardianDTO> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;
		for (LearnerGuardianDTO item : list) {
			records[row] = addRowData(item);
			row++;
		}
		this.setData(records);

	}

	public void addRecordToGrid(LearnerGuardianDTO item) {
		ListGridRecord record = addRowData(item);
		this.addData(record);

	}

}
