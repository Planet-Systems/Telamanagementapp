package com.planetsystems.tela.managementapp.client.presenter.reports.headteacherperformance;

import java.util.List;

import com.planetsystems.tela.dto.reports.TeacherClockInSummaryDTO;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.planetsystems.tela.managementapp.shared.UtilityManager;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.SelectionType;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class HeadTeacherClockInSummaryListgrid extends SuperListGrid {

	public static String STAFF = "staff";

	public static String DAY = "day";
	public static String DATE = "date";
	public static String CLOCKIN = "clockin";
	public static String CLOCKOUT = "clockout";

	public static String TOTAL = "total";
	public static String EXPECTED = "expected";
	public static String BALANCE = "balance";
	public static String PERCENT = "percent";

	public HeadTeacherClockInSummaryListgrid() {
		super();
		ListGridField staff = new ListGridField(STAFF, "HeadTeacher");

		
		
//		staff.setHidden(true);

		ListGridField day = new ListGridField(DAY, "Day");
		ListGridField date = new ListGridField(DATE, "Date");
		ListGridField clockin = new ListGridField(CLOCKIN, "Clockin");
		ListGridField clockout = new ListGridField(CLOCKOUT, "Clockout");

		ListGridField total = new ListGridField(TOTAL, "Total Hours of HeadTeachers' on Site");

		ListGridField expected = new ListGridField(EXPECTED, "Total Number of Hours Expected on Site");
		ListGridField balance = new ListGridField(BALANCE, "Variance");
		ListGridField percent = new ListGridField(PERCENT, "Percentage(%)");

		this.setFields(staff, date, day, clockin, clockout, total, expected, balance, percent);

		this.setWrapHeaderTitles(true);
		this.setHeaderHeight(50);

	}

	public ListGridRecord addRowData(TeacherClockInSummaryDTO dto) {
		ListGridRecord record = new ListGridRecord();

		record.setAttribute(STAFF, dto.getStaff());

		if (dto.getTaskDay() != null) {
			record.setAttribute(DAY, dto.getTaskDay());
		} else {
			record.setAttribute(DAY, "__");
		}

		record.setAttribute(DATE, dto.getClockInDate());

		record.setAttribute(CLOCKIN, dto.getClockInTime());

		record.setAttribute(CLOCKOUT, dto.getClockOutTime());

		record.setAttribute(TOTAL, dto.getTotalWorkHours());
		record.setAttribute(EXPECTED, dto.getExpectedHours());
		record.setAttribute(BALANCE, dto.getBalance());
		record.setAttribute(PERCENT, UtilityManager.getInstance().formatCash(dto.getPercentage()) + "%");

		return record;
	}

	public void addRecordsToGrid(List<TeacherClockInSummaryDTO> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;
		for (TeacherClockInSummaryDTO item : list) {
			records[row] = addRowData(item);
			row++;
		}
		this.setData(records);
	}

	@Override
	protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) {
		if (getFieldName(colNum).equals(PERCENT)) {

			// float percent = Float.parseFloat(record.getAttribute(PERCENT));

			String percentage = (record.getAttribute(PERCENT)).replace("%", "");

			float percent = Float.parseFloat(percentage);

			if (percent < 75) {
				// red
				return "font-weight:bold; color:#C9C5C5; font-size:14;background-color:#c20215";
			}

			else if (percent >= 75 && percent < 90) {
				// yellow
				return "font-weight:bold; color:#000000; font-size:14; background-color:#fcb143";
			} else if (percent >= 90) {
				// green
				return "font-weight:bold; color:#C9C5C5; font-size:14;background-color:#5eb96d";

			} else {
				return "font-weight:bold; color:#C9C5C5; font-size:14;background-color:#c20215";
			}

		}

		else {
			return super.getCellCSSText(record, rowNum, colNum);
		}

	}
}
