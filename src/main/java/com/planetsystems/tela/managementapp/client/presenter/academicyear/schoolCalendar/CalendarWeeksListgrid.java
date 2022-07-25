package com.planetsystems.tela.managementapp.client.presenter.academicyear.schoolCalendar;

import java.util.List;

import com.planetsystems.tela.dto.SchoolCalendarWeekDTO;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class CalendarWeeksListgrid extends SuperListGrid {

	public static String ID = "id";

	public static String CalendarMonth = "calendarMonth";
	public static String CalendarWeek = "calendarWeek";
	public static String StartDate = "startDate";
	public static String EndDate = "endDate";
	public static String ExpectedHours = "expectedHours";

	public CalendarWeeksListgrid() {
		super();

		ListGridField idField = new ListGridField(ID, "Id");
		idField.setHidden(true);

		ListGridField calendarMonth = new ListGridField(CalendarMonth, "Month");
		ListGridField calendarWeek = new ListGridField(CalendarWeek, "WeeK");
		ListGridField startDate = new ListGridField(StartDate, "Start Date");
		ListGridField endDate = new ListGridField(EndDate, "End Date");
		ListGridField expectedHours = new ListGridField(ExpectedHours, "Expected Weekly Hours");

		this.setFields(idField, calendarMonth, calendarWeek, startDate, endDate, expectedHours);

	}

	public ListGridRecord addRowData(SchoolCalendarWeekDTO dto) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute(ID, dto.getId());

		record.setAttribute(CalendarMonth, dto.getCalendarMonth());
		record.setAttribute(CalendarWeek, dto.getCalendarWeek());
		record.setAttribute(StartDate, dto.getStartDate());
		record.setAttribute(EndDate, dto.getEndDate());
		record.setAttribute(ExpectedHours, dto.getExpectedHours());

		return record;
	}

	public void addRecordsToGrid(List<SchoolCalendarWeekDTO> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;
		for (SchoolCalendarWeekDTO item : list) {
			records[row] = addRowData(item);
			row++;
		}
		this.setData(records);
	}

	public void addRecordsToGrid(SchoolCalendarWeekDTO dto) {
		ListGridRecord record = addRowData(dto);
		this.addData(record);
	}

}
