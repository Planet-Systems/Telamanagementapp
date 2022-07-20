package com.planetsystems.tela.managementapp.client.presenter.academicyear.schoolCalendar;

import java.util.List;

import com.planetsystems.tela.dto.PublicHolidayDTO;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class PublicHolidaysListgrid extends SuperListGrid {

	public static String ID = "id";
	public static String Date = "date";
	public static String Description = "description";

	public PublicHolidaysListgrid() {
		super();

		ListGridField idField = new ListGridField(ID, "Id");
		idField.setHidden(true);

		ListGridField date = new ListGridField(Date, "Date");
		ListGridField description = new ListGridField(Description, "Description");

		this.setFields(idField,description, date);

	}

	public ListGridRecord addRowData(PublicHolidayDTO dto) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute(ID, dto.getId());
		record.setAttribute(Date, dto.getDate());
		record.setAttribute(Description, dto.getDescription());

		return record;
	}

	public void addRecordsToGrid(List<PublicHolidayDTO> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;
		for (PublicHolidayDTO item : list) {
			records[row] = addRowData(item);
			row++;
		}
		this.setData(records);
	}

	public void addRecordsToGrid(PublicHolidayDTO dto) {
		ListGridRecord record = addRowData(dto);
		this.addData(record);
	}

}
