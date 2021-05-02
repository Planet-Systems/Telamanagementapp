package com.planetsystems.tela.managementapp.client.presenter.reports.districtperformacereport;

import java.util.List;

import com.planetsystems.tela.dto.dashboard.DataOutPutByGenderDTO;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class LearnerEnrollmentSummaryListgrid extends SuperListGrid {

	public static String ID = "id";
	public static String SCHOOL = "school";
	public static String BOYS = "boys";
	public static String GIRLS = "girls";
	public static String TOTAL = "total";

	public LearnerEnrollmentSummaryListgrid() {
		super();

		ListGridField id = new ListGridField(ID, "Id");
		id.setHidden(true);

		ListGridField school = new ListGridField(SCHOOL, "School");
		ListGridField boys = new ListGridField(BOYS, "Boys");
		ListGridField girls = new ListGridField(GIRLS, "Girls");
		ListGridField total = new ListGridField(TOTAL, "Total");

		this.setFields(id, school, boys, girls, total);
	}

	public ListGridRecord addRowData(DataOutPutByGenderDTO dto) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute(SCHOOL, dto.getKey());
		record.setAttribute(BOYS, dto.getMale());
		record.setAttribute(GIRLS, dto.getFemale());
		record.setAttribute(TOTAL, dto.getMale() + dto.getFemale());

		return record;
	}

	public void addRecordsToGrid(List<DataOutPutByGenderDTO> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;
		for (DataOutPutByGenderDTO item : list) {
			records[row] = addRowData(item);
			row++;
		}
		this.setData(records);
	}

}
