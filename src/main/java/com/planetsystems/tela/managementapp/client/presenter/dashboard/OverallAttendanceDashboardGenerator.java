package com.planetsystems.tela.managementapp.client.presenter.dashboard;

import java.util.List;

import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.ChartSubtitle;
import org.moxieapps.gwt.highcharts.client.ChartTitle;
import org.moxieapps.gwt.highcharts.client.Legend;
import org.moxieapps.gwt.highcharts.client.Point;
import org.moxieapps.gwt.highcharts.client.Series;
import org.moxieapps.gwt.highcharts.client.ToolTip;
import org.moxieapps.gwt.highcharts.client.ToolTipData;
import org.moxieapps.gwt.highcharts.client.ToolTipFormatter;
import org.moxieapps.gwt.highcharts.client.labels.DataLabels;
import org.moxieapps.gwt.highcharts.client.labels.DataLabelsData;
import org.moxieapps.gwt.highcharts.client.labels.DataLabelsFormatter;
import org.moxieapps.gwt.highcharts.client.labels.PieDataLabels;
import org.moxieapps.gwt.highcharts.client.plotOptions.BarPlotOptions;
import org.moxieapps.gwt.highcharts.client.plotOptions.ColumnPlotOptions;
import org.moxieapps.gwt.highcharts.client.plotOptions.PiePlotOptions;
import org.moxieapps.gwt.highcharts.client.plotOptions.PlotOptions;
import org.moxieapps.gwt.highcharts.client.plotOptions.SeriesPlotOptions;

import com.planetsystems.tela.dto.dashboard.AttendanceDashboardSummaryDTO;
import com.planetsystems.tela.dto.dashboard.DataOutPutByGenderDTO;
import com.planetsystems.tela.dto.dashboard.DataOutPutDTO;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class OverallAttendanceDashboardGenerator {

	private static OverallAttendanceDashboardGenerator instance = new OverallAttendanceDashboardGenerator();

	private OverallAttendanceDashboardGenerator() {

	}

	public static OverallAttendanceDashboardGenerator getInstance() {
		return instance;
	}

	public void generateDashboard(VLayout dashboardPane, AttendanceDashboardSummaryDTO dto) {

		double totalTeachers = dto.getTeacher();
		double totalLearners = dto.getLearner();

		VLayout col1 = new VLayout();
		col1.addMember(getCard("Overall Teacher Attendance Rate", totalTeachers + "%", "#f7b924"));
		col1.addMember(getCard("Overall Learner Attendance Rate", totalLearners + "%", "#6495ED"));
		col1.setAutoHeight();
		col1.setWidth("20%");
		col1.setPadding(5);
		col1.setBorder("1px solid #CDCFCC");
		col1.setMargin(5);

		VLayout chart1 = new VLayout();
		chart1.addMember(learnerAttendanceChart(dto.getBoys(), dto.getGirls(), dto.getPeriod()));

		VLayout chart2 = new VLayout();
		chart2.addMember(tearchersAttendanceChart(dto.getMale(), dto.getFemale(), dto.getPeriod()));

		VLayout chart3 = new VLayout();
		chart3.addMember(teachersPerRegionChart());

		VLayout chart4 = new VLayout();
		chart4.addMember(teachersByRegionByGenderStackedChart());

		VLayout chart5 = new VLayout();
		chart5.addMember(learnersPerRegionChart(dto.getLearnAttendace()));

		VLayout chart6 = new VLayout();
		chart6.addMember(learnersByRegionByGenderStackedChart(dto.getLearnAttendanceByGender()));

		VLayout chart7 = new VLayout();
		chart7.addMember(teachersEnrollmentVsAttendanceByRegion());

		VLayout chart8 = new VLayout();
		chart8.addMember(learnerEnrollmentVsAttendanceByRegion());

		HLayout col2 = new HLayout();
		col2.addMember(chart1);
		col2.addMember(chart2);
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

		/*DashboarTestWindow testWindow = new DashboarTestWindow();
		testWindow.getLayout().setMembers(layout2);
		testWindow.show();*/ 

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

	public Chart learnerAttendanceChart(double boys, double girls, String period) {

		final Chart chart = new Chart().setType(Series.Type.PIE).setMargin(50, 0, 0, 0)
				.setChartTitleText("Learner Attendance Rate by Sex").setChartSubtitleText(period)
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
		chart.setHeight(300);
		chart.setWidth(500);
		return chart;
	}

	public Chart tearchersAttendanceChart(double male, double female, String period) {

		final Chart chart = new Chart().setType(Series.Type.PIE).setMargin(50, 0, 0, 0)
				.setChartTitleText("Teacher Attendance Rate by Gender").setChartSubtitleText(period)
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
		chart.setHeight(300);
		chart.setWidth(500);
		return chart;
	}

	private Chart teachersPerRegionChart() {
		Chart lineChart = new Chart().setType(Series.Type.BAR)
				.setChartTitle(new ChartTitle().setText("Teacher Attendance Rate Per Region"))
				.setChartSubtitle(new ChartSubtitle().setText("Numbers")).setToolTip(new ToolTip().setEnabled(false));

		lineChart.getYAxis().setAxisTitleText("% of Teachers");

		/*
		 * lineChart.setColumnPlotOptions( new
		 * ColumnPlotOptions().setEnableMouseTracking(true).setDataLabels(new
		 * DataLabels().setEnabled(true)));
		 */

		lineChart.setBarPlotOptions(
				new BarPlotOptions().setEnableMouseTracking(true).setDataLabels(new DataLabels().setEnabled(true)));

		String[] lables = { "Buganda North", "Lango", "Bukedi", "Ankole", "Busoga", "Karamoja", "Kigezi", "Toro",
				"Teso", "Acholi", "Elgon", "West Nile", "Bunyoro", "Buganda South", "KAMPALA" };
		Number[] teachers = { 10, 30, 40, 30, 20, 37, 28, 54, 100, 37, 64, 36, 50, 47, 41 };
		// Number[] female = {20,35,21,40,30,39,46,50,87,80,41,36,50,30,41};

		/*
		 * int counter = 0; for (KeyValueSummaryDTO dto : list) { lables[counter] =
		 * dto.getName(); points[counter] = dto.getValue(); counter++; }
		 */

		lineChart.getXAxis().setCategories(lables);
		lineChart.addSeries(lineChart.createSeries().setName("% of Teachers").setPoints(teachers));
		lineChart.setColors("#40E0D0", "#6495ED");

		lineChart.setBorderWidth(1);
		lineChart.setBorderColor("#CDCFCC");
		lineChart.setHeight(500);

		return lineChart;
	}

	private Chart teachersByRegionByGenderStackedChart() {

		final Chart chart = new Chart().setType(Series.Type.BAR)
				.setChartTitleText("Teacher Attendance Rate by Region by Gender")
				.setSeriesPlotOptions(new SeriesPlotOptions().setStacking(PlotOptions.Stacking.NORMAL))
				.setLegend(new Legend().setBackgroundColor("#FFFFFF").setReversed(true))
				.setToolTip(new ToolTip().setFormatter(new ToolTipFormatter() {
					public String format(ToolTipData toolTipData) {
						return toolTipData.getSeriesName() + ": " + toolTipData.getYAsLong() + " "
								+ toolTipData.getXAsString();
					}
				})).setColors("#FFC300", "#DAF7A6");

		String[] lables = { "Buganda North", "Lango", "Bukedi", "Ankole", "Busoga", "Karamoja", "Kigezi", "Toro",
				"Teso", "Acholi", "Elgon", "West Nile", "Bunyoro", "Buganda South", "KAMPALA" };
		Number[] male = { 10, 30, 40, 30, 20, 37, 28, 54, 100, 37, 64, 36, 50, 47, 41 };
		Number[] female = { 20, 35, 21, 40, 30, 39, 46, 50, 87, 80, 41, 36, 50, 30, 41 };

		/*
		 * int counter = 0; int validPointCount = 0; int expiredPointCount = 0;
		 * 
		 * for (StackChartSummary summary : list) {
		 * 
		 * lables[counter] = summary.getLable();
		 * 
		 * if (summary.getPoints() != null) {
		 * 
		 * for (KeyValueSummaryDTO point : summary.getPoints()) {
		 * 
		 * if (point.getName().equalsIgnoreCase("Valid Permit")) {
		 * 
		 * validPoints[validPointCount] = point.getValue(); validPointCount++;
		 * 
		 * } else if (point.getName().equalsIgnoreCase("Expired Permit")) {
		 * 
		 * expiredPoints[expiredPointCount] = point.getValue(); expiredPointCount++;
		 * 
		 * }
		 * 
		 * } }
		 * 
		 * counter++; }
		 */

		chart.getXAxis().setCategories(lables);
		chart.addSeries(chart.createSeries().setName("Male").setPoints(male));
		chart.addSeries(chart.createSeries().setName("Female").setPoints(female));

		chart.setBarPlotOptions(
				new BarPlotOptions().setEnableMouseTracking(true).setDataLabels(new DataLabels().setEnabled(true)));

		/*
		 * chart.setColumnPlotOptions( new
		 * ColumnPlotOptions().setEnableMouseTracking(true).setDataLabels(new
		 * DataLabels().setEnabled(true)));
		 */

		chart.getYAxis().setMin(0).setAxisTitleText("% of Teachers");
		chart.setBorderWidth(1);
		chart.setBorderColor("#CDCFCC");
		chart.setHeight(500);
		return chart;
	}

	private Chart learnersPerRegionChart(List<DataOutPutDTO> learnerAttendance) {
		Chart lineChart = new Chart().setType(Series.Type.COLUMN)
				.setChartTitle(new ChartTitle().setText("Learner Attendance Rate by Region"))
				.setChartSubtitle(new ChartSubtitle().setText("%")).setToolTip(new ToolTip().setEnabled(true));

		lineChart.getYAxis().setAxisTitleText("% of Learners Attendance");

		lineChart.setColumnPlotOptions(
				new ColumnPlotOptions().setEnableMouseTracking(true).setDataLabels(new DataLabels().setEnabled(true)));

		/*
		 * String[] lables = { "Buganda North", "Lango", "Bukedi", "Ankole", "Busoga",
		 * "Karamoja", "Kigezi", "Toro", "Teso", "Acholi", "Elgon", "West Nile",
		 * "Bunyoro", "Buganda South", "KAMPALA" }; Number[] learners = { 10, 30, 40,
		 * 30, 20, 37, 28, 54, 100, 37, 64, 36, 50, 47, 41 };
		 */
		/*
		 * int counter = 0; for (KeyValueSummaryDTO dto : list) { lables[counter] =
		 * dto.getName(); points[counter] = dto.getValue(); counter++; }
		 * 
		 * 
		 */

		String[] lables = new String[learnerAttendance.size()];

		Number[] learners = new Number[learnerAttendance.size()];

		int counter = 0;
		for (DataOutPutDTO dto : learnerAttendance) {
			lables[counter] = dto.getKey();
			learners[counter] = dto.getValue();
			counter++;
		}

		lineChart.getXAxis().setCategories(lables);
		lineChart.addSeries(lineChart.createSeries().setName("% of Learners Attendance").setPoints(learners));
		lineChart.setColors("#40E0D0", "#6495ED");

		lineChart.setBorderWidth(1);
		lineChart.setBorderColor("#CDCFCC");
		lineChart.setHeight(500);

		return lineChart;
	}

	private Chart learnersByRegionByGenderStackedChart(List<DataOutPutByGenderDTO> list) {

		final Chart chart = new Chart().setType(Series.Type.COLUMN)
				.setChartTitleText("Learner Attendance Rate by Region by Gender")
				.setChartSubtitle(new ChartSubtitle().setText("%"))
				.setSeriesPlotOptions(new SeriesPlotOptions().setStacking(PlotOptions.Stacking.NORMAL))
				.setLegend(new Legend().setBackgroundColor("#FFFFFF").setReversed(true))
				.setToolTip(new ToolTip().setFormatter(new ToolTipFormatter() {
					public String format(ToolTipData toolTipData) {
						return toolTipData.getSeriesName() + ": " + toolTipData.getYAsLong() + " "
								+ toolTipData.getXAsString();
					}
				})).setColors("#FFC300", "#DAF7A6");

		/*String[] lables = { "Buganda North", "Lango", "Bukedi", "Ankole", "Busoga", "Karamoja", "Kigezi", "Toro",
				"Teso", "Acholi", "Elgon", "West Nile", "Bunyoro", "Buganda South", "KAMPALA" };
		Number[] boys = { 10, 30, 40, 30, 20, 37, 28, 54, 100, 37, 64, 36, 50, 47, 41 };
		Number[] girls = { 20, 35, 21, 40, 30, 39, 46, 50, 87, 80, 41, 36, 50, 30, 41 };*/

		String[] lables =new String[list.size()];
		Number[] boys=new Number[list.size()];
		Number[] girls=new Number[list.size()];
		int counter = 0; 

		for (DataOutPutByGenderDTO dto : list) {

			lables[counter] = dto.getKey();
			boys[counter]=dto.getMale();
			girls[counter]=dto.getFemale();
			counter++;
		} 
		 

		chart.getXAxis().setCategories(lables);
		chart.addSeries(chart.createSeries().setName("Boys").setPoints(boys));
		chart.addSeries(chart.createSeries().setName("Girls").setPoints(girls));

		/*
		 * chart.setBarPlotOptions( new
		 * BarPlotOptions().setEnableMouseTracking(true).setDataLabels(new
		 * DataLabels().setEnabled(true)));
		 */

		chart.setColumnPlotOptions(
				new ColumnPlotOptions().setEnableMouseTracking(true).setDataLabels(new DataLabels().setEnabled(true)));

		chart.getYAxis().setMin(0).setAxisTitleText("% of Learners Attendance");
		chart.setBorderWidth(1);
		chart.setBorderColor("#CDCFCC");
		chart.setHeight(500);
		return chart;
	}

	private Chart teachersEnrollmentVsAttendanceByRegion() {

		final Chart chart = new Chart().setType(Series.Type.LINE)
				.setChartTitleText("Number of Teachers Vs Attendance by Region")
				.setSeriesPlotOptions(new SeriesPlotOptions().setStacking(PlotOptions.Stacking.NORMAL))
				.setLegend(new Legend().setBackgroundColor("#FFFFFF").setReversed(true))
				.setToolTip(new ToolTip().setFormatter(new ToolTipFormatter() {
					public String format(ToolTipData toolTipData) {
						return toolTipData.getSeriesName() + ": " + toolTipData.getYAsLong() + " "
								+ toolTipData.getXAsString();
					}
				})).setColors("#FFC300", "#DAF7A6");

		String[] lables = { "Buganda North", "Lango", "Bukedi", "Ankole", "Busoga", "Karamoja", "Kigezi", "Toro",
				"Teso", "Acholi", "Elgon", "West Nile", "Bunyoro", "Buganda South", "KAMPALA" };
		Number[] enrollment = { 10, 30, 40, 30, 20, 37, 28, 54, 100, 37, 64, 36, 50, 47, 41 };
		Number[] attendance = { 20, 35, 21, 40, 30, 39, 46, 50, 87, 80, 41, 36, 50, 30, 41 };

		/*
		 * int counter = 0; int validPointCount = 0; int expiredPointCount = 0;
		 * 
		 * for (StackChartSummary summary : list) {
		 * 
		 * lables[counter] = summary.getLable();
		 * 
		 * if (summary.getPoints() != null) {
		 * 
		 * for (KeyValueSummaryDTO point : summary.getPoints()) {
		 * 
		 * if (point.getName().equalsIgnoreCase("Valid Permit")) {
		 * 
		 * validPoints[validPointCount] = point.getValue(); validPointCount++;
		 * 
		 * } else if (point.getName().equalsIgnoreCase("Expired Permit")) {
		 * 
		 * expiredPoints[expiredPointCount] = point.getValue(); expiredPointCount++;
		 * 
		 * }
		 * 
		 * } }
		 * 
		 * counter++; }
		 */

		chart.getXAxis().setCategories(lables);
		chart.addSeries(chart.createSeries().setName("Enrollment").setPoints(enrollment));
		chart.addSeries(chart.createSeries().setName("Attendance").setPoints(attendance));

		chart.setBarPlotOptions(
				new BarPlotOptions().setEnableMouseTracking(true).setDataLabels(new DataLabels().setEnabled(true)));

		/*
		 * chart.setColumnPlotOptions( new
		 * ColumnPlotOptions().setEnableMouseTracking(true).setDataLabels(new
		 * DataLabels().setEnabled(true)));
		 */

		chart.getYAxis().setMin(0).setAxisTitleText("Number of Teachers");
		chart.setBorderWidth(1);
		chart.setBorderColor("#CDCFCC");
		chart.setHeight(500);
		return chart;
	}

	private Chart learnerEnrollmentVsAttendanceByRegion() {

		final Chart chart = new Chart().setType(Series.Type.LINE)
				.setChartTitleText("Learner Enrollment Vs Attendance by Region")
				.setSeriesPlotOptions(new SeriesPlotOptions().setStacking(PlotOptions.Stacking.NORMAL))
				.setLegend(new Legend().setBackgroundColor("#FFFFFF").setReversed(true))
				.setToolTip(new ToolTip().setFormatter(new ToolTipFormatter() {
					public String format(ToolTipData toolTipData) {
						return toolTipData.getSeriesName() + ": " + toolTipData.getYAsLong() + " "
								+ toolTipData.getXAsString();
					}
				})).setColors("#FFC300", "#DAF7A6");

		String[] lables = { "Buganda North", "Lango", "Bukedi", "Ankole", "Busoga", "Karamoja", "Kigezi", "Toro",
				"Teso", "Acholi", "Elgon", "West Nile", "Bunyoro", "Buganda South", "KAMPALA" };
		Number[] enrollment = { 10, 30, 40, 30, 20, 37, 28, 54, 100, 37, 64, 36, 50, 47, 41 };
		Number[] attendance = { 20, 35, 21, 40, 30, 39, 46, 50, 87, 80, 41, 36, 50, 30, 41 };

		/*
		 * int counter = 0; int validPointCount = 0; int expiredPointCount = 0;
		 * 
		 * for (StackChartSummary summary : list) {
		 * 
		 * lables[counter] = summary.getLable();
		 * 
		 * if (summary.getPoints() != null) {
		 * 
		 * for (KeyValueSummaryDTO point : summary.getPoints()) {
		 * 
		 * if (point.getName().equalsIgnoreCase("Valid Permit")) {
		 * 
		 * validPoints[validPointCount] = point.getValue(); validPointCount++;
		 * 
		 * } else if (point.getName().equalsIgnoreCase("Expired Permit")) {
		 * 
		 * expiredPoints[expiredPointCount] = point.getValue(); expiredPointCount++;
		 * 
		 * }
		 * 
		 * } }
		 * 
		 * counter++; }
		 */

		chart.getXAxis().setCategories(lables);
		chart.addSeries(chart.createSeries().setName("Enrollment").setPoints(enrollment));
		chart.addSeries(chart.createSeries().setName("Attendance").setPoints(attendance));

		chart.setBarPlotOptions(
				new BarPlotOptions().setEnableMouseTracking(true).setDataLabels(new DataLabels().setEnabled(true)));

		/*
		 * chart.setColumnPlotOptions( new
		 * ColumnPlotOptions().setEnableMouseTracking(true).setDataLabels(new
		 * DataLabels().setEnabled(true)));
		 */

		chart.getYAxis().setMin(0).setAxisTitleText("Number of Learners");
		chart.setBorderWidth(1);
		chart.setBorderColor("#CDCFCC");
		chart.setHeight(500);
		return chart;
	}

}
