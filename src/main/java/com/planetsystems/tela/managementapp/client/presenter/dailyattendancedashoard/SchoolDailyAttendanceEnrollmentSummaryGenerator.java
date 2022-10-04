package com.planetsystems.tela.managementapp.client.presenter.dailyattendancedashoard;

import com.planetsystems.tela.dto.dashboard.SchoolDailyAttendanceEnrollmentSummaryDTO;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class SchoolDailyAttendanceEnrollmentSummaryGenerator {

	private static SchoolDailyAttendanceEnrollmentSummaryGenerator instance = new SchoolDailyAttendanceEnrollmentSummaryGenerator();

	private SchoolDailyAttendanceEnrollmentSummaryGenerator() {

	}

	public static SchoolDailyAttendanceEnrollmentSummaryGenerator getInstance() {
		return instance;
	}

	public void generateDashboard(VLayout dashboardPane, SchoolDailyAttendanceEnrollmentSummaryDTO dto) {

		boolean headteacherststaut = dto.isHeadteacherClockInStatus();
		long enrolledTeachers = dto.getEnrolledTeachers();
		long clockedInteachers = dto.getClockedInteachers();
		long enroledLearners = dto.getEnroledLearners();
		long clockedLearners = dto.getClockedLearners();

		HLayout row1Summary = new HLayout();
		row1Summary.addMember(getCard("Teachers Enrolled", enrolledTeachers + "", "#ffffff", "#7FBA00"));
		row1Summary.addMember(getCard("Teachers Clocked in", clockedInteachers + "", "#ffffff", "#00A4EF"));
		row1Summary.addMember(getCard("Learners Enrolled", enroledLearners + "", "#ffffff", "#FFB900"));
		row1Summary.addMember(getCard("Learners Present", clockedLearners + "", "#ffffff", "#737373"));

		row1Summary.setAutoHeight();
		// row1Summary.setWidth("20%");
		row1Summary.setPadding(5);
		row1Summary.setBorder("0px solid #CDCFCC");
		row1Summary.setMargin(10);

		VLayout row2Summary = new VLayout();
		DailySchoolAttendanceEnrollmentSummaryListgrid summaryListgrid = new DailySchoolAttendanceEnrollmentSummaryListgrid();
		summaryListgrid.addRecordsToGrid(dto.getDailySchoolAttendanceEnrollmentSummaryDTOs());
		summaryListgrid.setHeight(470);
		row2Summary.addMember(summaryListgrid);

		VLayout row1 = new VLayout();
		row1.addMember(row1Summary);
		row1.addMember(row2Summary);

		VLayout layout2 = new VLayout();
		layout2.addMember(row1);
		layout2.setMembersMargin(5);

		/*
		 * DashboarTestWindow testWindow = new DashboarTestWindow();
		 * testWindow.getLayout().setMembers(layout2); testWindow.show();
		 */

		dashboardPane.setMembers(layout2);

	}

	private VLayout getCard(String cardTitle, String value, String color, String bgColor) {
		Label label = new Label();
		label.setPadding(2);
		label.setAlign(Alignment.CENTER);
		label.setValign(VerticalAlignment.CENTER);
		label.setWrap(true);
		label.setContents("<div style='box-sizing: border-box; box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);"
				+ "background-color:" + bgColor + ";padding: 2px;font-family: \"Lucida Grande\","
				+ " \"Lucida Sans Unicode\", Arial, Helvetica, sans-serif; width:300px;'>"
				+ "<p style='font-weight: bold; font-size: 1.8rem;color:#ffffff !important'>" + value + "</p>"
				+ "<p style='color:" + color + " !important;'> <span style='font-size: 0.9rem; font-weight: bold;'>"
				+ cardTitle + "</span></p></div>");
		label.setCanSelectText(true);

		VLayout layout = new VLayout();
		layout.addMember(label);
		layout.setAutoHeight();

		return layout;
	}

}
