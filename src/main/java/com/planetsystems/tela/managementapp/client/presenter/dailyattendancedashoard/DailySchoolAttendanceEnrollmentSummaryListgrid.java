package com.planetsystems.tela.managementapp.client.presenter.dailyattendancedashoard;

import java.util.List;

import com.planetsystems.tela.dto.dashboard.DailySchoolAttendanceEnrollmentSummaryDTO;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class DailySchoolAttendanceEnrollmentSummaryListgrid extends SuperListGrid {

	public static String StaffId = "staffId";
	public static String Staff = "staff";
	public static String Role = "role";
	public static String ClockInTime = "clock-InTime";

	public DailySchoolAttendanceEnrollmentSummaryListgrid() {
		super();

		ListGridField staffId = new ListGridField(StaffId, "Id");
		staffId.setHidden(true);

		ListGridField staff = new ListGridField(Staff, "Staff Name");

		ListGridField role = new ListGridField(Role, "Position");

		ListGridField clockInTime = new ListGridField(ClockInTime, "Clock-In Time");

		this.setFields(staffId, staff, role, clockInTime);
		this.setWrapHeaderTitles(true);
		//this.setHeaderHeight(50);
		this.setSelectionType(SelectionStyle.NONE);
	}

	public ListGridRecord addRowData(DailySchoolAttendanceEnrollmentSummaryDTO dto) {
		ListGridRecord record = new ListGridRecord();

		record.setAttribute(StaffId, dto.getStaffId());

		record.setAttribute(Staff, dto.getStaff());

		record.setAttribute(Role, dto.getRole());

		record.setAttribute(ClockInTime, dto.getClockInTime());

		return record;
	}

	public void addRecordsToGrid(List<DailySchoolAttendanceEnrollmentSummaryDTO> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;
		for (DailySchoolAttendanceEnrollmentSummaryDTO item : list) {
			records[row] = addRowData(item);
			row++;
		}
		this.setData(records);
	}

}
