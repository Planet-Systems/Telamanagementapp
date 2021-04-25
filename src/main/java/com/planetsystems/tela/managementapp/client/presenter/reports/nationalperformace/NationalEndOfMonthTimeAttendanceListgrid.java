package com.planetsystems.tela.managementapp.client.presenter.reports.nationalperformace;

import java.util.List;

import com.planetsystems.tela.dto.reports.NationalEndOfMonthTimeAttendanceDTO;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.planetsystems.tela.managementapp.shared.UtilityManager;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class NationalEndOfMonthTimeAttendanceListgrid extends SuperListGrid {

	public static String DISTRICT = "district";
	public static String WEEK_1 = "week1";
	public static String WEEK_2 = "week2";
	public static String WEEK_3 = "week3";
	public static String WEEK_4 = "week4";
	public static String TOTAL = "total";

	public static String EXPECTED = "expected";
	public static String BALANCE = "balance";
	public static String PERCENT = "percent";

	public NationalEndOfMonthTimeAttendanceListgrid() {
		super();
		ListGridField district = new ListGridField(DISTRICT, "District");
		ListGridField week1 = new ListGridField(WEEK_1, "Week 1");
		ListGridField week2 = new ListGridField(WEEK_2, "Week 2");
		ListGridField week3 = new ListGridField(WEEK_3, "Week 3");
		ListGridField week4 = new ListGridField(WEEK_4, "Week 4");
		ListGridField total = new ListGridField(TOTAL, "Total");

		ListGridField expected = new ListGridField(EXPECTED, "Expected");
		ListGridField balance = new ListGridField(BALANCE, "Balance");
		ListGridField percent = new ListGridField(PERCENT, "Percent (%)");

		this.setFields(district, week1, week2, week3, week4, total, expected, balance, percent);

	}

	public ListGridRecord addRowData(NationalEndOfMonthTimeAttendanceDTO dto) {
		ListGridRecord record = new ListGridRecord();

		record.setAttribute(DISTRICT, dto.getDistrict());

		if (dto.getWeek1() != null) {
			record.setAttribute(WEEK_1, dto.getWeek1());
		} else {
			record.setAttribute(WEEK_1, "__");
		}

		if (dto.getWeek2() != null) {
			record.setAttribute(WEEK_2, dto.getWeek2());
		} else {
			record.setAttribute(WEEK_2, "__");
		}

		if (dto.getWeek3() != null) {
			record.setAttribute(WEEK_3, dto.getWeek3());
		} else {
			record.setAttribute(WEEK_3, "__");
		}

		if (dto.getWeek4() != null) {
			record.setAttribute(WEEK_4, dto.getWeek4());
		} else {
			record.setAttribute(WEEK_4, "__");
		}

		record.setAttribute(TOTAL, dto.getTotalTime());

		record.setAttribute(EXPECTED, dto.getExpectedHours());
		record.setAttribute(BALANCE, dto.getBalance());
		// record.setAttribute(PERCENT, timeSheet.getPercentage() + "%");
		record.setAttribute(PERCENT, UtilityManager.getInstance().formatCash(dto.getPercentage()) + "%");

		return record;
	}

	public void addRecordsToGrid(List<NationalEndOfMonthTimeAttendanceDTO> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;
		for (NationalEndOfMonthTimeAttendanceDTO item : list) {
			records[row] = addRowData(item);
			row++;
		}
		this.setData(records);
	}

	@Override
	protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) {
		if (getFieldName(colNum).equals(PERCENT)) {

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
