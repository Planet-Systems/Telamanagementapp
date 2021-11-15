package com.planetsystems.tela.managementapp.client.presenter.systemuser.group;

import java.util.List;

import com.planetsystems.tela.dto.SystemUserGroupDTO;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class UserGroupListgrid extends SuperListGrid {

	public static String ID = "id";
	public static String CODE = "code";
	public static String Role = "employeeRole";
	public static String Description = "description";

	public static String DefaultRole = "defaultRole";

	public static String ReceiveNotifications = "receiveNotifications";
	public static String ManageData = "ManageData";
	
	public static String Level = "Level";

	public UserGroupListgrid() {

		super();
		ListGridField id = new ListGridField(ID, "Id");
		id.setHidden(true);

		ListGridField code = new ListGridField(CODE, "Code");
		ListGridField name = new ListGridField(Role, "Level name");
		ListGridField description = new ListGridField(Description, "Description");

		ListGridField defaultRole = new ListGridField(DefaultRole, "Is Default");

		ListGridField receiveNotifications = new ListGridField(ReceiveNotifications, "Receive Notifications");

		ListGridField manageData = new ListGridField(ManageData, "Manage Data");
		
		ListGridField level = new ListGridField(Level, "Level");

		this.setFields(id, code, name, description, defaultRole, receiveNotifications, manageData,level);

	}

	public ListGridRecord addRowData(SystemUserGroupDTO dto) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute(ID, dto.getId());
		record.setAttribute(CODE, dto.getCode());
		record.setAttribute(Role, dto.getName());
		record.setAttribute(Description, dto.getDescription());
		record.setAttribute(DefaultRole, dto.isDefaultGroup());
		record.setAttribute(ReceiveNotifications, dto.isReceiveAlerts());
		record.setAttribute(ManageData, dto.isAdministrativeRole());
		record.setAttribute(Level, dto.getUserLevel());
		return record;
	}

	public void addRecordsToGrid(List<SystemUserGroupDTO> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;
		for (SystemUserGroupDTO item : list) {
			records[row] = addRowData(item);
			row++;
		}
		this.setData(records);
	}
}
