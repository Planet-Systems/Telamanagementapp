package com.planetsystems.tela.managementapp.client.presenter.systemuser;

import java.util.List;

import com.planetsystems.tela.dto.SystemMenuDTO;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class SystemMenuListgrid extends SuperListGrid {

	public static String ID = "id";

	public static String NavigationMenu = "navigationMenu";
	public static String SystemMenuItem = "systemMenuItem";
	public static String Status = "status";

	public SystemMenuListgrid() {
		super();

		ListGridField id = new ListGridField(ID, "id");
		id.setHidden(true);

		ListGridField navigationMenu = new ListGridField(NavigationMenu, "Menu");
		ListGridField systemMenuItem = new ListGridField(SystemMenuItem, "Sub Menu");
		ListGridField status = new ListGridField(Status, "Status");

		this.setSelectionType(SelectionStyle.SIMPLE);
		this.setFields(id, navigationMenu, systemMenuItem, status);
		this.setGroupByField(NavigationMenu);

	}

	public ListGridRecord addRowData(SystemMenuDTO dto) {
		ListGridRecord record = new ListGridRecord();

		record.setAttribute(ID, dto.getId());
		record.setAttribute(NavigationMenu, dto.getNavigationMenu());
		record.setAttribute(SystemMenuItem, dto.getSubMenuItem());
		record.setAttribute(Status, dto.getActivativationSatus());

		return record;
	}

	public void addRecordsToGrid(List<SystemMenuDTO> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;
		for (SystemMenuDTO item : list) {
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
