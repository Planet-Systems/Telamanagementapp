package com.planetsystems.tela.managementapp.client.presenter.reports.districtperformacereport;

import java.util.List;

import com.planetsystems.tela.dto.reports.DistrictEndOfWeekTimeAttendanceDTO;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.planetsystems.tela.managementapp.shared.UtilityManager;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class DistrictEndOfWeekTimeAttendanceListgrid extends SuperListGrid {

	public static String SCHOOL = "school";
	public static String MON = "mon";
	public static String TUE = "tue";
	public static String WED = "wed";
	public static String THUR = "thur";
	public static String FRI = "fri";
	public static String SAT = "sat";
	public static String SUN = "sun";
	public static String TOTAL = "total";

	public static String EXPECTED = "expected";
	public static String BALANCE = "balance";
	public static String PERCENT = "percent";

	public DistrictEndOfWeekTimeAttendanceListgrid() {
		super();
		ListGridField employee = new ListGridField(SCHOOL, "School");
		ListGridField mon = new ListGridField(MON, "Mon");
		ListGridField tue = new ListGridField(TUE, "Tue");
		ListGridField wed = new ListGridField(WED, "Wed");
		ListGridField thur = new ListGridField(THUR, "Thur");
		ListGridField fri = new ListGridField(FRI, "Fri");
		ListGridField sat = new ListGridField(SAT, "Sat");
		sat.setHidden(true);
		ListGridField sun = new ListGridField(SUN, "Sun");
		sun.setHidden(true);
		ListGridField total = new ListGridField(TOTAL, "Total");

		ListGridField expected = new ListGridField(EXPECTED, "Expected");
		ListGridField balance = new ListGridField(BALANCE, "Variance");
		ListGridField percent = new ListGridField(PERCENT, "Percent(%)");

		this.setFields(employee, mon, tue, wed, thur, fri, sat, sun, total, expected, balance, percent);

	}

	public ListGridRecord addRowData(DistrictEndOfWeekTimeAttendanceDTO dto) {
		ListGridRecord record = new ListGridRecord();

		record.setAttribute(SCHOOL, dto.getSchool());

		if (dto.getMon() != null) {
			record.setAttribute(MON, dto.getMon());
		} else {
			record.setAttribute(MON, "__");
		}

		if (dto.getTue() != null) {
			record.setAttribute(TUE, dto.getTue());
		} else {
			record.setAttribute(TUE, "__");
		}

		if (dto.getWed() != null) {
			record.setAttribute(WED, dto.getWed());
		} else {
			record.setAttribute(WED, "__");
		}

		if (dto.getThur() != null) {
			record.setAttribute(THUR, dto.getThur());
		} else {
			record.setAttribute(THUR, "__");
		}

		if (dto.getFri() != null) {
			record.setAttribute(FRI, dto.getFri());
		} else {
			record.setAttribute(FRI, "__");
		}

		if (dto.getSat() != null) {
			record.setAttribute(SAT, dto.getSat());
		} else {
			record.setAttribute(SAT, "__");
		}

		if (dto.getSun() != null) {
			record.setAttribute(SUN, dto.getSun());
		} else {
			record.setAttribute(SUN, "__");
		}

		record.setAttribute(TOTAL, dto.getTotalWorkHours());
		record.setAttribute(EXPECTED, dto.getExpectedHours());
		record.setAttribute(BALANCE, dto.getBalance());
		// record.setAttribute(PERCENT, timeSheet.getPercentage() + "%");

		record.setAttribute(PERCENT, UtilityManager.getInstance().formatCash(dto.getPercentage()) + "%");

		// record.setAttribute(PERCENT, timeSheet.getPercentage());

		return record;
	}

	public void addRecordsToGrid(List<DistrictEndOfWeekTimeAttendanceDTO> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;

		for (DistrictEndOfWeekTimeAttendanceDTO item : list) {

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
