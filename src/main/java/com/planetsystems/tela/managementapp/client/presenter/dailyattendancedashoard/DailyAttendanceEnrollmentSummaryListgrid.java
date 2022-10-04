package com.planetsystems.tela.managementapp.client.presenter.dailyattendancedashoard;

import java.util.List;

import com.planetsystems.tela.dto.dashboard.DailyAttendanceEnrollmentSummaryDTO;
import com.planetsystems.tela.dto.dashboard.DataOutPutByGenderDTO;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class DailyAttendanceEnrollmentSummaryListgrid  extends SuperListGrid {

	public static String ID = "id";
	public static String DISTRICT = "district";
	public static String ENROLED_SCHOOLS = "enroledSchools";
	public static String CLOCKED_SCHOOLS = "clockedSchools";
	public static String ENROLED_TEACHERS = "enrolledTeachers";
	public static String CLOCKIN_TEACHERS = "clockedInteachers";
	public static String ENROLED_LEARNERS = "enroledLearners";
	public static String CLOCKIN_LEARNERS = "clockedLearners";
	 

	public DailyAttendanceEnrollmentSummaryListgrid() {
		super();

		ListGridField id = new ListGridField(ID, "Id");
		id.setHidden(true); 
		
		ListGridField district = new ListGridField(DISTRICT, "District");
		 

		ListGridField enroledSchools = new ListGridField(ENROLED_SCHOOLS, "No. of Schools Enrolled");
		ListGridField clockedSchools = new ListGridField(CLOCKED_SCHOOLS, "No. of Schools Clocked-In");
		
		ListGridField enrolledTeachers = new ListGridField(ENROLED_TEACHERS, "No. of Teachers Enrolled");
		ListGridField clockedInteachers = new ListGridField(CLOCKIN_TEACHERS, "No. of Teachers Clocked-In");
		
		ListGridField enroledLearners = new ListGridField(ENROLED_LEARNERS, "No. of Learners Enrolled");
		ListGridField clockedLearners = new ListGridField(CLOCKIN_LEARNERS, "No. of Learner Attendance Recorded");
		 

		this.setFields(id,district, enroledSchools, clockedSchools, enrolledTeachers, clockedInteachers,enroledLearners,clockedLearners);
		this.setWrapHeaderTitles(true);
		this.setHeaderHeight(50);
		this.setSelectionType(SelectionStyle.NONE);
	}

	public ListGridRecord addRowData(DailyAttendanceEnrollmentSummaryDTO dto) {
		ListGridRecord record = new ListGridRecord();
		
		record.setAttribute(ID, dto.getDistrictId());
		
		record.setAttribute(DISTRICT, dto.getDistrict());
		
		record.setAttribute(ENROLED_SCHOOLS, dto.getEnroledSchools());
		record.setAttribute(CLOCKED_SCHOOLS, dto.getClockedSchools());
		
		record.setAttribute(ENROLED_TEACHERS, dto.getEnrolledTeachers());
		record.setAttribute(CLOCKIN_TEACHERS, dto.getClockedInteachers()); 
		
		record.setAttribute(ENROLED_LEARNERS, dto.getEnroledLearners());
		record.setAttribute(CLOCKIN_LEARNERS, dto.getClockedLearners());

		return record;
	}

	public void addRecordsToGrid(List<DailyAttendanceEnrollmentSummaryDTO> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;
		for (DailyAttendanceEnrollmentSummaryDTO item : list) {
			records[row] = addRowData(item);
			row++;
		}
		this.setData(records);
	}

}
