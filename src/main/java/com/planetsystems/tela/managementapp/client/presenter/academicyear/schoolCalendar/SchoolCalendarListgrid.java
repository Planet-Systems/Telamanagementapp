package com.planetsystems.tela.managementapp.client.presenter.academicyear.schoolCalendar;

import java.util.List;

import com.planetsystems.tela.dto.SchoolCalendarDTO;
import com.planetsystems.tela.managementapp.client.presenter.academicyear.year.AcademicYearListGrid.AcademicYearDatasource;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class SchoolCalendarListgrid extends SuperListGrid {

	public static String ID = "id";

	public static String AcademicYearId = "academicYearId";
	public static String AcademicYear = "academicYear";

	public static String AcademicTermId = "academicTermId";
	public static String AcademicTerm = "academicTerm";
	public static String ExpectedDailyHours = "expectedDailyHours";
	public static String ExpectedWeeklyHours = "expectedWeeklyHours";
	public static String ExpectedMonthlyHours = "expectedMonthlyHours";
	public static String ExpectedTermlyHours = "expectedTermlyHours";
	public static String Description = "Description";

	private SchoolCalendarDatasource datasource;

	public SchoolCalendarListgrid() {
		super();

		ListGridField idField = new ListGridField(ID, "Id");
		idField.setHidden(true);

		ListGridField academicYearId = new ListGridField(AcademicYearId, "Academic Year Id");
		academicYearId.setHidden(true);

		ListGridField academicYear = new ListGridField(AcademicYear, "Academic Year");

		ListGridField academicTermId = new ListGridField(AcademicTermId, "Academic Term Id");
		academicTermId.setHidden(true);
		ListGridField academicTerm = new ListGridField(AcademicTerm, "Academic Term");

		ListGridField expectedDailyHours = new ListGridField(ExpectedDailyHours, "Expected Daily Hours");
		ListGridField expectedWeeklyHours = new ListGridField(ExpectedWeeklyHours, "Expected Weekly Hours");
		ListGridField expectedMonthlyHours = new ListGridField(ExpectedMonthlyHours, "Expected Monthly Hours");
		ListGridField expectedTermlyHours = new ListGridField(ExpectedTermlyHours, "Expected Termly Hours");
		ListGridField description = new ListGridField(Description, "Description");

		this.setFields(idField, academicYearId, academicYear, academicTermId, academicTerm, description,
				expectedDailyHours, expectedWeeklyHours, expectedMonthlyHours, expectedTermlyHours);

		datasource = SchoolCalendarDatasource.getInstance();
		this.setDataSource(datasource);
		this.setWrapHeaderTitles(true);
		this.setHeaderHeight(50);

	}

	public ListGridRecord addRowData(SchoolCalendarDTO dto) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute(ID, dto.getId());

		if (dto.getAcademicTerm() != null) {

			record.setAttribute(AcademicTermId, dto.getAcademicTerm().getId());
			record.setAttribute(AcademicTerm, dto.getAcademicTerm().getTerm());

			if (dto.getAcademicTerm().getAcademicYearDTO() != null) {
				record.setAttribute(AcademicYearId, dto.getAcademicTerm().getAcademicYearDTO().getId());
				record.setAttribute(AcademicYear, dto.getAcademicTerm().getAcademicYearDTO().getName());
			}
		}

		record.setAttribute(ExpectedDailyHours, dto.getExpectedDailyHours());
		record.setAttribute(ExpectedWeeklyHours, dto.getExpectedWeeklyHours());
		record.setAttribute(ExpectedMonthlyHours, dto.getExpectedMonthlyHours());
		record.setAttribute(ExpectedTermlyHours, dto.getExpectedTermlyHours());
		record.setAttribute(Description, dto.getDescription());

		return record;
	}

	public void addRecordsToGrid(List<SchoolCalendarDTO> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;
		for (SchoolCalendarDTO item : list) {
			records[row] = addRowData(item);
			row++;
		}
		this.setData(records);
		datasource.setTestData(records);
	}

	public static class SchoolCalendarDatasource extends DataSource {

		private static SchoolCalendarDatasource instance = null;

		public static SchoolCalendarDatasource getInstance() {
			if (instance == null) {
				instance = new SchoolCalendarDatasource("SchoolCalendarDatasource");
			}
			return instance;
		}

		public SchoolCalendarDatasource(String id) {
			setID(id);

			DataSourceTextField idField = new DataSourceTextField(ID, "Id");
			idField.setHidden(true);
			idField.setPrimaryKey(true);

			DataSourceTextField academicTermId = new DataSourceTextField(AcademicTermId, "Academic Term Id");
			DataSourceTextField academicTerm = new DataSourceTextField(AcademicTerm, "Academic Term");
			DataSourceTextField expectedDailyHours = new DataSourceTextField(ExpectedDailyHours,
					"Expected Daily Hours");
			DataSourceTextField expectedWeeklyHours = new DataSourceTextField(ExpectedWeeklyHours,
					"Expected Weekly Hours");
			DataSourceTextField expectedMonthlyHours = new DataSourceTextField(ExpectedMonthlyHours,
					"Expected Monthly Hours");
			DataSourceTextField expectedTermlyHours = new DataSourceTextField(ExpectedTermlyHours,
					"Expected Termly Hours");
			DataSourceTextField description = new DataSourceTextField(Description, "Description");

			this.setFields(idField, academicTermId, academicTerm, description, expectedDailyHours, expectedWeeklyHours,
					expectedMonthlyHours, expectedTermlyHours);

			setClientOnly(true);
		}

	}

}
