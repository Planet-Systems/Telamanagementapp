package com.planetsystems.tela.managementapp.client.presenter.dailyattendancedashoard;

import java.util.List;

import com.planetsystems.tela.dto.dashboard.DailyDistrictAttendanceEnrollmentSummaryDTO;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class DailyDistrictAttendanceEnrollmentSummaryListgrid extends SuperListGrid {

	public static String ID = "id";
	public static String SCHOOL = "School";
	public static String HEAD_TEACHER_STATUS = "headteacherStatus";
	public static String ENROLED_TEACHERS = "enrolledTeachers";
	public static String CLOCKIN_TEACHERS = "clockedInteachers";
	public static String ENROLED_LEARNERS = "enroledLearners";
	public static String CLOCKIN_LEARNERS = "clockedLearners";

	public DailyDistrictAttendanceEnrollmentSummaryListgrid() {
		super();

		ListGridField id = new ListGridField(ID, "Id");
		id.setHidden(true);

		ListGridField school = new ListGridField(SCHOOL, "School");

		ListGridField headteacherStatus = new ListGridField(HEAD_TEACHER_STATUS, "HeadTeacher Clockin Status");

		ListGridField enrolledTeachers = new ListGridField(ENROLED_TEACHERS, "No. of Teachers Enrolled");
		ListGridField clockedInteachers = new ListGridField(CLOCKIN_TEACHERS, "No. of Teachers Clocked-In");

		ListGridField enroledLearners = new ListGridField(ENROLED_LEARNERS, "No. of Learners Enrolled");
		ListGridField clockedLearners = new ListGridField(CLOCKIN_LEARNERS, "No. of Learner Attendance Recorded");

		this.setFields(id, school, headteacherStatus, enrolledTeachers, clockedInteachers, enroledLearners,
				clockedLearners);
		this.setWrapHeaderTitles(true);
		this.setHeaderHeight(50);
	}

	public ListGridRecord addRowData(DailyDistrictAttendanceEnrollmentSummaryDTO dto) {
		ListGridRecord record = new ListGridRecord();

		record.setAttribute(ID, dto.getSchoolId());

		record.setAttribute(SCHOOL, dto.getSchool());

		if (dto.isHeadteacherStatus()) {
			record.setAttribute(HEAD_TEACHER_STATUS, "Present");
		} else {
			record.setAttribute(HEAD_TEACHER_STATUS, "Absent");
		}

		record.setAttribute(ENROLED_TEACHERS, dto.getEnrolledTeachers());
		record.setAttribute(CLOCKIN_TEACHERS, dto.getClockedInteachers());

		record.setAttribute(ENROLED_LEARNERS, dto.getEnroledLearners());
		record.setAttribute(CLOCKIN_LEARNERS, dto.getClockedLearners());

		return record;
	}

	public void addRecordsToGrid(List<DailyDistrictAttendanceEnrollmentSummaryDTO> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;
		for (DailyDistrictAttendanceEnrollmentSummaryDTO item : list) {
			records[row] = addRowData(item);
			row++;
		}
		this.setData(records);
	}

}
