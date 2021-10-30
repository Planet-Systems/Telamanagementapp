package com.planetsystems.tela.managementapp.client.presenter.loginaudit;

import java.util.List;

import com.planetsystems.tela.dto.LoginAuditDTO;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class LoginAuditListgrid extends SuperListGrid {

	public static String USER = "user";
	public static String DATE = "date";

	public LoginAuditListgrid() {

		super();
		ListGridField user = new ListGridField(USER, "User");

		ListGridField date = new ListGridField(DATE, "Login time");

		this.setFields(user, date);
		this.setSortField(DATE);
		this.setSortDirection(SortDirection.DESCENDING);

	}

	public ListGridRecord addRowData(LoginAuditDTO dto) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute(USER, dto.getSystemUser());
		record.setAttribute(DATE, dto.getLoginTime());

		return record;
	}

	public void addRecordsToGrid(List<LoginAuditDTO> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;
		for (LoginAuditDTO item : list) {
			records[row] = addRowData(item);
			row++;
		}
		this.setData(records);
	}

}
