package com.planetsystems.tela.managementapp.client.presenter.dailyattendancedashoard;

import com.planetsystems.tela.dto.dashboard.DistrictDailyAttendanceEnrollmentSummaryDTO; 
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DistrictDailyAttendanceDashboardGenerator {



	private static DistrictDailyAttendanceDashboardGenerator instance = new DistrictDailyAttendanceDashboardGenerator();

	private DistrictDailyAttendanceDashboardGenerator() {

	}

	public static DistrictDailyAttendanceDashboardGenerator getInstance() {
		return instance;
	}
	
	DailyDistrictAttendanceEnrollmentSummaryListgrid summaryListgrid = new DailyDistrictAttendanceEnrollmentSummaryListgrid();

	public void generateDashboard(VLayout dashboardPane, DistrictDailyAttendanceEnrollmentSummaryDTO dto) {
 
		long enroledSchools = dto.getEnroledSchools();
		long clockedSchools = dto.getClockedSchools();
		long clockedHeadteachers = dto.getClockedHeadteachers();
		long enrolledTeachers = dto.getEnrolledTeachers();
		long clockedInteachers = dto.getClockedInteachers();
		long enroledLearners = dto.getEnroledLearners();
		long clockedLearners = dto.getClockedLearners();

		VLayout col1 = new VLayout();  
		col1.addMember(getCard("Total Schools Clocked in", clockedSchools + "", "#6495ED"));
		col1.addMember(getCard("Total Headteachers Clocked in", clockedHeadteachers + "", "#f7b924"));
		col1.addMember(getCard("Total Teachers Clocked in", clockedInteachers + "", "#6495ED"));
		
		col1.setAutoHeight();
		col1.setWidth("20%");
		col1.setPadding(5);
		col1.setBorder("1px solid #CDCFCC");
		col1.setMargin(10);

		VLayout chart1 = new VLayout();
		
		summaryListgrid.addRecordsToGrid(dto.getDailyDistrictAttendanceEnrollmentSummaryDTOs());
		summaryListgrid.setHeight(470);
		chart1.addMember(summaryListgrid);

		VLayout chart2 = new VLayout();
		// chart2.addMember(tearchersAttendanceChart(dto.getMale(), dto.getFemale(),
		// dto.getPeriod()));

		VLayout chart3 = new VLayout();
		// chart3.addMember(teachersPerRegionChart());

		VLayout chart4 = new VLayout();
		// chart4.addMember(teachersByRegionByGenderStackedChart());

		VLayout chart5 = new VLayout();
		// chart5.addMember(learnersPerRegionChart(dto.getLearnAttendace()));

		VLayout chart6 = new VLayout();
		// chart6.addMember(learnersByRegionByGenderStackedChart(dto.getLearnAttendanceByGender()));

		VLayout chart7 = new VLayout();
		// chart7.addMember(teachersEnrollmentVsAttendanceByRegion());

		VLayout chart8 = new VLayout();
		// chart8.addMember(learnerEnrollmentVsAttendanceByRegion());

		HLayout col2 = new HLayout();
		col2.addMember(chart1);
		// col2.addMember(chart2);
		col2.setMargin(5);

		HLayout row1 = new HLayout();
		row1.addMember(col1);
		row1.addMember(col2);

		HLayout row2 = new HLayout();
		row2.addMember(chart3);
		row2.addMember(chart4);

		HLayout row3 = new HLayout();
		row3.addMember(chart5);
		row3.addMember(chart6);

		HLayout row4 = new HLayout();
		row4.addMember(chart7);
		row4.addMember(chart8);

		VLayout layout2 = new VLayout();
		layout2.addMember(row1);
		layout2.addMember(row2);
		layout2.addMember(row3);
		layout2.addMember(row4);
		layout2.setMembersMargin(5);

		/*
		 * DashboarTestWindow testWindow = new DashboarTestWindow();
		 * testWindow.getLayout().setMembers(layout2); testWindow.show();
		 */

		dashboardPane.setMembers(layout2);

	}

	private VLayout getCard(String cardTitle, String value, String color) {
		Label label = new Label();
		label.setPadding(2);
		label.setAlign(Alignment.CENTER);
		label.setValign(VerticalAlignment.CENTER);
		label.setWrap(true);
		label.setContents("<div style='box-sizing: border-box; box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);"
				+ "background-color: #ffffff;padding: 2px;font-family: \"Lucida Grande\", \"Lucida Sans Unicode\", Arial, Helvetica, sans-serif; width:250px;'>"
				+ "<h4 style='font-weight: bold; font-size: 0.9rem;font-color:#ADA4AC'>" + cardTitle + "</h4>"
				+ "<h4 style='color:" + color + " !important;'> <span style='font-size: 0.8rem; font-weight: bold;'>"
				+ value + "</span></4></div>");
		label.setCanSelectText(true);

		VLayout layout = new VLayout();
		layout.addMember(label);
		layout.setAutoHeight();

		return layout;
	}


}
