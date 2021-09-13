package com.planetsystems.tela.managementapp.client.presenter.reports.districtperformacereport;

import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.Point;
import org.moxieapps.gwt.highcharts.client.Series;
import org.moxieapps.gwt.highcharts.client.ToolTip;
import org.moxieapps.gwt.highcharts.client.ToolTipData;
import org.moxieapps.gwt.highcharts.client.ToolTipFormatter;
import org.moxieapps.gwt.highcharts.client.labels.DataLabelsData;
import org.moxieapps.gwt.highcharts.client.labels.DataLabelsFormatter;
import org.moxieapps.gwt.highcharts.client.labels.PieDataLabels;
import org.moxieapps.gwt.highcharts.client.plotOptions.PiePlotOptions;

import com.google.gwt.core.shared.GWT;
import com.planetsystems.tela.dto.dashboard.DashboardSummaryDTO;
import com.planetsystems.tela.managementapp.client.presenter.dashboard.DashboarTestWindow;
import com.planetsystems.tela.managementapp.shared.UtilityManager;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DistrictDashboard {

	private static DistrictDashboard instance = new DistrictDashboard();

	private DistrictDashboard() {

	}

	public static DistrictDashboard getInstance() {
		return instance;
	}

	public void generateDashboard(VLayout dashboardPane, final DashboardSummaryDTO dto) {

		//long totalSchools = 100;
		//long totalTeachers = 150;
		//long totalLearners = 200;

		 long totalSchools = dto.getShools();
		 long totalTeachers = dto.getTeachers();
		 long totalLearners = dto.getLearners();

		VLayout col1 = new VLayout();
		col1.addMember(getCard("No. of Public Primary Schools", UtilityManager.getInstance().formatCash(totalSchools),
				"#3ac47d"));
		col1.addMember(getCard("No. of Teachers on Payroll", UtilityManager.getInstance().formatCash(totalTeachers),
				"#f7b924"));
		col1.addMember(getCard("No. of Learners", UtilityManager.getInstance().formatCash(totalLearners), "#6495ED"));
		col1.setAutoHeight();
		col1.setWidth("20%");
		col1.setPadding(5);
		col1.setBorder("1px solid #CDCFCC");

		VLayout chart1 = new VLayout();
		// chart1.addMember(learnerCountChart(dto.getBoys(), dto.getGirls(),
		// dto.getYear() + "-" + dto.getTerm()));
		chart1.addMember(learnerCountChart(40, 30, "2020" + "-" + "Term 2"));

		VLayout chart2 = new VLayout();
		// chart2.addMember(tearchersCountChart(dto.getMaleTeachers(),
		// dto.getFemaleTeachers(),dto.getYear() + "-" + dto.getTerm()));

		chart2.addMember(tearchersCountChart(90, 10, "2020" + "-" + "Term 2"));

		HLayout col2 = new HLayout();
		col2.addMember(chart1);
		col2.addMember(chart2);
		col2.setMargin(10);

		HLayout row1 = new HLayout();
		row1.addMember(col1);
		row1.addMember(col2);

		LearnerEnrollmentSummaryListgrid listgrid = new LearnerEnrollmentSummaryListgrid();
		listgrid.setHeight(300);
		listgrid.addRecordsToGrid(dto.getLearnsEnrolledByGender());

		TeacherEnrollmentSummaryListgrid teacherEnrollmentSummaryListgrid = new TeacherEnrollmentSummaryListgrid();
		teacherEnrollmentSummaryListgrid.setHeight(300);
		teacherEnrollmentSummaryListgrid.addRecordsToGrid(dto.getStaffEnrolledByGender());

		VLayout col3 = new VLayout();
		col3.addMember(listgrid);

		VLayout col4 = new VLayout();
		col4.addMember(teacherEnrollmentSummaryListgrid);

		HLayout row2 = new HLayout();
		row2.addMember(col3);
		row2.addMember(col4);

		VLayout layout2 = new VLayout();
		layout2.addMember(row1);
		layout2.addMember(row2);
		layout2.setMembersMargin(5);

		 GWT.log("loading district dashboard"); 
		 
		/*DashboarTestWindow testWindow = new DashboarTestWindow();
		testWindow.getLayout().setMembers(layout2); testWindow.show(); */ 
		  

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
				+ "<h4 style='font-weight: bold; font-size: 1.0rem;font-color:#ADA4AC'>" + cardTitle + "</h4>"
				+ "<h4 style='color:" + color + " !important;'> <span style='font-size: 0.9rem; font-weight: bold;'>"
				+ value + "</span></4></div>");
		label.setCanSelectText(true);

		VLayout layout = new VLayout();
		layout.addMember(label);
		layout.setAutoHeight();

		return layout;
	}

	public Chart learnerCountChart(long boys, long girls, String period) {

		final Chart chart = new Chart().setType(Series.Type.PIE).setMargin(50, 0, 0, 0)
				.setChartTitleText("Learner Enrollment by Sex").setChartSubtitleText("Year: " + period)
				.setPlotBackgroundColor("none").setPlotBorderWidth(0).setPlotShadow(false)
				.setToolTip(new ToolTip().setFormatter(new ToolTipFormatter() {
					public String format(ToolTipData toolTipData) {
						return "<b>" + toolTipData.getSeriesName() + "</b><br/>" + toolTipData.getPointName() + ": "
								+ toolTipData.getYAsDouble() + " %";
					}
				}));

		chart.addSeries(chart.createSeries().setName(period)
				.setPlotOptions(
						new PiePlotOptions().setCenter(.5, .5).setInnerSize(.2).setPieDataLabels(new PieDataLabels()
								.setEnabled(true).setColor("#000000").setFormatter(new DataLabelsFormatter() {
									public String format(DataLabelsData dataLabelsData) {
										return "<b>" + dataLabelsData.getPointName() + "</b>: "
												+ dataLabelsData.getYAsDouble();
									}
								}).setConnectorColor("#000000")))
				.setPoints(new Point[] { new Point("Boys", boys).setColor("#DAF7A6"),
						new Point("Girls", girls).setColor("#FFC300") }));

		chart.setBorderWidth(1);
		chart.setBorderColor("#CDCFCC");
		chart.setHeight(340);
		chart.setWidth(500);
		return chart;
	}

	public Chart tearchersCountChart(long male, long female, String period) {

		final Chart chart = new Chart().setType(Series.Type.PIE).setMargin(50, 0, 0, 0)
				.setChartTitleText("Number of Teachers by Gender").setChartSubtitleText("Year: " + period)
				.setPlotBackgroundColor("none").setPlotBorderWidth(0).setPlotShadow(false)
				.setToolTip(new ToolTip().setFormatter(new ToolTipFormatter() {
					public String format(ToolTipData toolTipData) {
						return "<b>" + toolTipData.getSeriesName() + "</b><br/>" + toolTipData.getPointName() + ": "
								+ toolTipData.getYAsDouble() + " %";
					}
				}));

		chart.addSeries(chart.createSeries().setName(period)
				.setPlotOptions(
						new PiePlotOptions().setCenter(.5, .5).setInnerSize(.2).setPieDataLabels(new PieDataLabels()
								.setEnabled(true).setColor("#000000").setFormatter(new DataLabelsFormatter() {
									public String format(DataLabelsData dataLabelsData) {
										return "<b>" + dataLabelsData.getPointName() + "</b>: "
												+ dataLabelsData.getYAsDouble();
									}
								}).setConnectorColor("#000000")))
				.setPoints(new Point[] { new Point("Male", male).setColor("#FF5733"),
						new Point("Female", female).setColor("#C70039") }));

		chart.setBorderWidth(1);
		chart.setBorderColor("#CDCFCC");
		chart.setHeight(340);
		chart.setWidth(500);
		return chart;
	}

}
