package com.planetsystems.tela.managementapp.client.presenter.schoolcategory.schoolclass;

import java.util.List;

import com.planetsystems.tela.dto.SchoolClassDTO;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class SchoolClassListGrid extends SuperListGrid {
	public static String ID = "id";
	public static String CODE = "code";
	public static String NAME = "name";

	public static String SCHOOL = "school";
	public static String SCHOOL_ID = "school id";

	public static String DISTRICT = "district";
	public static String DISTRICT_ID = "district id";

	public static String ACADEMIC_TERM = "Academic Term";
	public static String ACADEMIC_TERM_ID = "Academic Term ID";

	public static String ACADEMIC_YEAR = "Academic Year";
	public static String ACADEMIC_YEAR_ID = "Academic Year ID";

	public static String HasStreams = "hasStreams";
	public static String ClassLevel = "classLevel";
	public static String SchoolClassId = "SchoolClassId";
	public static String SchoolClassCode = "SchoolClassCode";

	private SchoolClassDataSource dataSource;

	public SchoolClassListGrid() {
		super();

		dataSource = SchoolClassDataSource.getInstance();

		ListGridField idField = new ListGridField(ID, "Id");
		idField.setHidden(true);

		ListGridField codeField = new ListGridField(CODE, "Code");
		ListGridField nameField = new ListGridField(NAME, "Name");

		ListGridField schoolField = new ListGridField(SCHOOL, "School");
		ListGridField schoolIdField = new ListGridField(SCHOOL_ID, "School Id");
		schoolIdField.setHidden(true);

		ListGridField districtField = new ListGridField(DISTRICT, "District");
		ListGridField districtIdField = new ListGridField(DISTRICT_ID, "District Id");
		districtIdField.setHidden(true);

		ListGridField academicTermField = new ListGridField(ACADEMIC_TERM, "Academic Term");
		ListGridField academicTermIdField = new ListGridField(ACADEMIC_TERM_ID, "Academic Term id");
		academicTermIdField.setHidden(true);

		ListGridField academicYearField = new ListGridField(ACADEMIC_YEAR, "Academic Year");
		ListGridField academicYearIdField = new ListGridField(ACADEMIC_YEAR_ID, "Academic Year id");
		academicYearIdField.setHidden(true);

		ListGridField hasStreams = new ListGridField(HasStreams, "Has Streams");

		ListGridField classLevel = new ListGridField(ClassLevel, "Is Parent Class");

		ListGridField schoolClassId = new ListGridField(SchoolClassId, "ParentId");
		schoolClassId.setHidden(true);

		ListGridField schoolClassCode = new ListGridField(SchoolClassCode, "Parent");

		this.setFields(idField, academicYearIdField, academicTermIdField, districtIdField, schoolIdField,
				academicYearField, academicTermField, districtField, schoolField, codeField, nameField, hasStreams,
				classLevel, schoolClassId, schoolClassCode);
		this.setDataSource(dataSource);
	}

	public ListGridRecord addRowData(SchoolClassDTO schoolClassDTO) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute(ID, schoolClassDTO.getId());
		record.setAttribute(CODE, schoolClassDTO.getCode());
		record.setAttribute(NAME, schoolClassDTO.getName());

		if (schoolClassDTO.getSchoolDTO() != null) {
			record.setAttribute(SCHOOL, schoolClassDTO.getSchoolDTO().getName());
			record.setAttribute(SCHOOL_ID, schoolClassDTO.getSchoolDTO().getId());

			if (schoolClassDTO.getSchoolDTO().getDistrictDTO() != null) {
				record.setAttribute(DISTRICT, schoolClassDTO.getSchoolDTO().getDistrictDTO().getName());
				record.setAttribute(DISTRICT_ID, schoolClassDTO.getSchoolDTO().getDistrictDTO().getId());
			}

		}

		if (schoolClassDTO.getAcademicTermDTO() != null) {
			record.setAttribute(ACADEMIC_TERM, schoolClassDTO.getAcademicTermDTO().getTerm());
			record.setAttribute(ACADEMIC_TERM_ID, schoolClassDTO.getAcademicTermDTO().getId());
			if (schoolClassDTO.getAcademicTermDTO().getAcademicYearDTO() != null) {
				record.setAttribute(ACADEMIC_YEAR, schoolClassDTO.getAcademicTermDTO().getAcademicYearDTO().getName());
				record.setAttribute(ACADEMIC_YEAR_ID, schoolClassDTO.getAcademicTermDTO().getAcademicYearDTO().getId());
			}
		}

		record.setAttribute(HasStreams, schoolClassDTO.isHasStreams());
		record.setAttribute(ClassLevel, schoolClassDTO.isClassLevel());

		if (!schoolClassDTO.isClassLevel()) {
			if (schoolClassDTO.getParentSchoolClass() != null) {
				record.setAttribute(SchoolClassId, schoolClassDTO.getParentSchoolClass().getId());
				record.setAttribute(SchoolClassCode, schoolClassDTO.getParentSchoolClass().getCode());
			} else {
				record.setAttribute(SchoolClassCode, "NA");
			}
		}else {
			record.setAttribute(SchoolClassCode, "NA");
		}

		return record;
	}

	public void addRecordsToGrid(List<SchoolClassDTO> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;
		for (SchoolClassDTO item : list) {
			records[row] = addRowData(item);
			row++;
		}
		this.setData(records);
		dataSource.setTestData(records);
	}

	public static class SchoolClassDataSource extends DataSource {

		private static SchoolClassDataSource instance = null;

		public static SchoolClassDataSource getInstance() {
			if (instance == null) {
				instance = new SchoolClassDataSource("SchoolClassDataSource");
			}
			return instance;
		}

		public SchoolClassDataSource(String id) {
			setID(id);

			DataSourceTextField idField = new DataSourceTextField(ID, "Id");
			idField.setHidden(true);
			idField.setPrimaryKey(true);

			DataSourceTextField codeField = new DataSourceTextField(CODE, "Code");
			DataSourceTextField nameField = new DataSourceTextField(NAME, "Name");
			DataSourceTextField schoolField = new DataSourceTextField(SCHOOL, "School");
			DataSourceTextField schoolIdField = new DataSourceTextField(SCHOOL_ID, "School Id");
			schoolIdField.setHidden(true);

			DataSourceTextField academicTermField = new DataSourceTextField(ACADEMIC_TERM, "Academic Term");
			DataSourceTextField academicTermIdField = new DataSourceTextField(ACADEMIC_TERM_ID, "Academic Term id");
			academicTermIdField.setHidden(true);

			this.setFields(idField, academicTermIdField, schoolIdField, codeField, nameField, academicTermField,
					schoolField);
			setClientOnly(true);

		}
	}

}
