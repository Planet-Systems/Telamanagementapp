package com.planetsystems.tela.managementapp.client.presenter.reports.smcperformance;

import java.util.List;
 
import com.planetsystems.tela.dto.reports.SMCSupervisionCriteria;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class SMCSupervisionReportListgrid extends SuperListGrid {

	public static String CRITERIA = "criteria"; 
	public static String RESULT = "result";

	public SMCSupervisionReportListgrid() {
		super();
		
		ListGridField criteria = new ListGridField(CRITERIA, "Supervision Area"); 
		ListGridField result = new ListGridField(RESULT, "Result"); 

		this.setFields(criteria, result);

		this.setWrapHeaderTitles(true);
		this.setHeaderHeight(50);

	}

	public ListGridRecord addRowData(SMCSupervisionCriteria dto) {
		ListGridRecord record = new ListGridRecord();

		record.setAttribute(CRITERIA, dto.getCriteria()); 
		record.setAttribute(RESULT, dto.getValue());
  
		return record;
	}

	public void addRecordsToGrid(List<SMCSupervisionCriteria> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;
		for (SMCSupervisionCriteria item : list) {
			records[row] = addRowData(item);
			row++;
		}
		this.setData(records);
	}

	 

}
