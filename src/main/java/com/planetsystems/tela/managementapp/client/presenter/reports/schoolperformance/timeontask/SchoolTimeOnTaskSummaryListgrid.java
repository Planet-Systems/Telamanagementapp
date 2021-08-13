package com.planetsystems.tela.managementapp.client.presenter.reports.schoolperformance.timeontask;

import java.util.List;

import com.planetsystems.tela.dto.reports.SchoolTimeOnTaskSummaryDTO;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class SchoolTimeOnTaskSummaryListgrid extends SuperListGrid {

	public static String ID = "id";
	public static String TaskDay = "taskDay";
	public static String DeploymentUnit = "unit";
	public static String Task = "task";
	public static String Staff = "employee";

	public static String StartTime = "clockin";
	public static String EndTime = "clockout";

	public static String AbsentismStatus = "absentismStatus";
	public static String AbsentismComment = "absentismComment";

	public static String Supervisor = "supervisor";
	public static String PrensetismStatus = "prensetismStatus";
	public static String PrensetismComment = "prensetismComment";

	public static String DATE = "date";

	public SchoolTimeOnTaskSummaryListgrid() {
		super();
		ListGridField id = new ListGridField(ID, "Id");
		id.setHidden(true);

		ListGridField taskDay = new ListGridField(TaskDay, "Day");

		ListGridField unit = new ListGridField(DeploymentUnit, "Class");

		ListGridField task = new ListGridField(Task, "Subject");

		ListGridField startTime = new ListGridField(StartTime, "Start Time");
		startTime.setHidden(true);

		ListGridField endTime = new ListGridField(EndTime, "End Time");
		endTime.setHidden(true);

		ListGridField employee = new ListGridField(Staff, "Teacher");

		ListGridField absentismStatus = new ListGridField(AbsentismStatus, "Teacher Status");
		absentismStatus.setHidden(true);

		ListGridField absentismComment = new ListGridField(AbsentismComment, "Teacher Comment");
		absentismComment.setHidden(true);

		ListGridField supervisor = new ListGridField(Supervisor, "Supervised By");
		ListGridField prensetismStatus = new ListGridField(PrensetismStatus, "Taught /Not Taught");
		ListGridField prensetismComment = new ListGridField(PrensetismComment, "In-Time/ Out-Of-Time");

		ListGridField date = new ListGridField(DATE, "Date (mm/dd/yyyy)");

		this.setFields(id, date, taskDay, employee, unit, task, startTime, endTime, absentismStatus, absentismComment,
				prensetismStatus, prensetismComment, supervisor);

		this.setSortField(DATE);
		this.setSortDirection(SortDirection.DESCENDING);

		this.setWrapHeaderTitles(true);
		this.setHeaderHeight(50);
	}

	public ListGridRecord addRowData(SchoolTimeOnTaskSummaryDTO dto) {
		ListGridRecord record = new ListGridRecord();

		if (dto.getTaskDay() != null) {
			record.setAttribute(TaskDay, dto.getTaskDay());
		}

		if (dto.getSubject() != null) {

			record.setAttribute(DeploymentUnit, dto.getSchoolClass());

			if (dto.getSubject() != null) {
				record.setAttribute(Task, dto.getSubject());
			}

		}

		record.setAttribute(StartTime, dto.getStartTime());

		record.setAttribute(EndTime, dto.getEndTime());

		record.setAttribute(Staff, dto.getStaff());

		if (dto.getAbsentismStatus() != null) {
			record.setAttribute(AbsentismStatus, dto.getAbsentismStatus());
			record.setAttribute(AbsentismComment, dto.getAbsentismComment());
		}

		if (dto.getSupervisor() != null) {
			record.setAttribute(Supervisor, dto.getSupervisor());
		}

		if (dto.getPrensetismStatus() != null) {
			record.setAttribute(PrensetismStatus, dto.getPrensetismStatus());
			record.setAttribute(PrensetismComment, dto.getPrensetismComment());
		}
		
		record.setAttribute(DATE, dto.getTaskDate());

		return record;
	}

	public void addRecordsToGrid(List<SchoolTimeOnTaskSummaryDTO> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;
		for (SchoolTimeOnTaskSummaryDTO item : list) {
			records[row] = addRowData(item);
			row++;
		}
		this.setData(records);
	}

}
