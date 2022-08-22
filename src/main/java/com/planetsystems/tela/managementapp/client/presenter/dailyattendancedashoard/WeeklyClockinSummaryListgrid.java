package com.planetsystems.tela.managementapp.client.presenter.dailyattendancedashoard;

import java.util.List;

import com.planetsystems.tela.dto.reports.WeeklyClockinSummaryDTO;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class WeeklyClockinSummaryListgrid extends SuperListGrid {

	public static String ID = "id";
	public static String DISTRICT = "district";

	public static String SchoolsEnrolled = "schoolsEnrolled";
	public static String StaffEnrolled = "staffEnrolled";

	public static String MondaySchoolsClockedIn = "mondaySchoolsClockedIn";
	public static String MondayStaffClockedIn = "mondayStaffClockedIn";

	public static String TuesdaySchoolsClockedIn = "tuesdaySchoolsClockedIn";
	public static String TuesdayStaffClockedIn = "tuesdayStaffClockedIn";

	public static String WednesdaySchoolsClockedIn = "wednesdaySchoolsClockedIn";
	public static String WednesdayStaffClockedIn = "wednesdayStaffClockedIn";

	public static String ThurdadySchoolsClockedIn = "thurdadySchoolsClockedIn";
	public static String ThurdadyStaffClockedIn = "thurdadyStaffClockedIn";

	public static String FridaySchoolsClockedIn = "fridaySchoolsClockedIn";
	public static String FridayStaffClockedIn = "fridayStaffClockedIn";

	public WeeklyClockinSummaryListgrid() {
		super();

		ListGridField id = new ListGridField(ID, "Id");
		id.setHidden(true);

		ListGridField district = new ListGridField(DISTRICT, "District");

		ListGridField schoolsEnrolled = new ListGridField(SchoolsEnrolled, "No. of Schools Enrolled");
		ListGridField staffEnrolled = new ListGridField(StaffEnrolled, "No. of Staff Enrolled");

		ListGridField mondaySchoolsClockedIn = new ListGridField(MondaySchoolsClockedIn, "Monday Schools Clockins");
		ListGridField mondayStaffClockedIn = new ListGridField(MondayStaffClockedIn, "Monday Staff Clockin");

		ListGridField tuesdaySchoolsClockedIn = new ListGridField(TuesdaySchoolsClockedIn, "Tuesday Schools Clockins");
		ListGridField tuesdayStaffClockedIn = new ListGridField(TuesdayStaffClockedIn, "Tuesday Staff Clockin");

		ListGridField wednesdaySchoolsClockedIn = new ListGridField(WednesdaySchoolsClockedIn,
				"Wednesday Schools Clockins");
		ListGridField wednesdayStaffClockedIn = new ListGridField(WednesdayStaffClockedIn, "Wednesday Staff Clockin");

		ListGridField thurdadySchoolsClockedIn = new ListGridField(ThurdadySchoolsClockedIn,
				"Thursday Schools Clockins");
		ListGridField thurdadyStaffClockedIn = new ListGridField(ThurdadyStaffClockedIn, "Thursday Staff Clockin");

		ListGridField fridaySchoolsClockedIn = new ListGridField(FridaySchoolsClockedIn, "Friday Schools Clockins");
		ListGridField fridayStaffClockedIn = new ListGridField(FridayStaffClockedIn, "Friday Staff Clockin");

		this.setFields(id, district, schoolsEnrolled, staffEnrolled, mondaySchoolsClockedIn, mondayStaffClockedIn,
				tuesdaySchoolsClockedIn, tuesdayStaffClockedIn, wednesdaySchoolsClockedIn, wednesdayStaffClockedIn,
				thurdadySchoolsClockedIn, thurdadyStaffClockedIn, fridaySchoolsClockedIn, fridayStaffClockedIn);

		this.setWrapHeaderTitles(true);
		this.setHeaderHeight(50);
	}

	public ListGridRecord addRowData(WeeklyClockinSummaryDTO dto) {
		ListGridRecord record = new ListGridRecord();

		record.setAttribute(ID, dto.getDistrictId());

		record.setAttribute(DISTRICT, dto.getDistrict());

		record.setAttribute(SchoolsEnrolled, dto.getSchoolsEnrolled());
		record.setAttribute(StaffEnrolled, dto.getStaffEnrolled());

		record.setAttribute(MondaySchoolsClockedIn, dto.getMondaySchoolsClockedIn());
		record.setAttribute(MondayStaffClockedIn, dto.getMondayStaffClockedIn());

		record.setAttribute(TuesdaySchoolsClockedIn, dto.getTuesdaySchoolsClockedIn());
		record.setAttribute(TuesdayStaffClockedIn, dto.getTuesdayStaffClockedIn());

		record.setAttribute(WednesdaySchoolsClockedIn, dto.getWednesdaySchoolsClockedIn());
		record.setAttribute(WednesdayStaffClockedIn, dto.getWednesdayStaffClockedIn());

		record.setAttribute(ThurdadySchoolsClockedIn, dto.getThurdadySchoolsClockedIn());
		record.setAttribute(ThurdadyStaffClockedIn, dto.getThurdadyStaffClockedIn());

		record.setAttribute(FridaySchoolsClockedIn, dto.getFridaySchoolsClockedIn());
		record.setAttribute(FridayStaffClockedIn, dto.getFridayStaffClockedIn());

		return record;
	}

	public void addRecordsToGrid(List<WeeklyClockinSummaryDTO> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;
		for (WeeklyClockinSummaryDTO item : list) {
			records[row] = addRowData(item);
			row++;
		}
		this.setData(records);
	}

}
