package com.planetsystems.tela.managementapp.client.presenter.systemuser.group;

import java.util.List;

import com.planetsystems.tela.dto.SystemUserGroupSystemMenuDTO;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class SystemUserGroupSystemMenuListgrid extends SuperListGrid {

	public static String ID = "id";

	public static String SystemUserGroupId = "systemUserGroupId";
	public static String SystemUserGroup = "systemUserGroup";
	public static String SystemMenuId = "systemMenuId";
	public static String SystemMenu = "systemMenu";
	public static String SystemMenuItem = "systemMenuItem";
	public static String Status = "status";

	public SystemUserGroupSystemMenuListgrid() {
		super();

		ListGridField id = new ListGridField(ID, "id");
		id.setHidden(true);

		ListGridField systemUserGroupId = new ListGridField(SystemUserGroupId, "SystemUserGroup Id");
		systemUserGroupId.setHidden(true);

		ListGridField systemUserGroup = new ListGridField(SystemUserGroup, "SystemUserGroup");
		systemUserGroup.setHidden(true);

		ListGridField systemMenuId = new ListGridField(SystemMenuId, "SystemMenuId");
		systemMenuId.setHidden(true);

		ListGridField systemMenu = new ListGridField(SystemMenu, "System Menu");
		ListGridField systemMenuItem = new ListGridField(SystemMenuItem, "System Menu Item");

		ListGridField status = new ListGridField(Status, "Status");

		status.setHidden(true);
		this.setSelectionType(SelectionStyle.SIMPLE);
		this.setFields(id, systemUserGroupId, systemUserGroup, systemMenuId, systemMenu, systemMenuItem, status);
		
		this.setGroupByField(SystemMenu);

	}

	public ListGridRecord addRowData(SystemUserGroupSystemMenuDTO dto) {
		
		ListGridRecord record = new ListGridRecord();
		record.setAttribute(ID, dto.getId());
		
		if (dto.getSystemUserGroupDTO() != null) {
			record.setAttribute(SystemUserGroupId, dto.getSystemUserGroupDTO().getId());
			record.setAttribute(SystemUserGroup, dto.getSystemUserGroupDTO().getName());
		}

		if (dto.getSystemMenuDTO() != null) {
			record.setAttribute(SystemMenuId, dto.getSystemMenuDTO().getId());
			record.setAttribute(SystemMenu, dto.getSystemMenuDTO().getNavigationMenu());
			record.setAttribute(SystemMenuItem, dto.getSystemMenuDTO().getSubMenuItem());
		}

		record.setAttribute(Status, dto.isDisabled());

		return record;
	}

	public void addRecordsToGrid(List<SystemUserGroupSystemMenuDTO> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;
		for (SystemUserGroupSystemMenuDTO item : list) {
			records[row] = addRowData(item);

			row++;
		}
		this.setData(records);
	}

	public void selectActiveRecords() {
		for (ListGridRecord record : this.getRecords()) {
			if (record.getAttributeAsBoolean(Status)) {
				this.selectRecord(record);
			}
		}

		this.redraw();
	}

}
