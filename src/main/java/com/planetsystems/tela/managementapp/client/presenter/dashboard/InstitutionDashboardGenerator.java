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

import com.planetsystems.tela.dto.dashboard.DashboardSummaryDTO;
import com.planetsystems.tela.dto.dashboard.DataOutPutByGenderDTO;
import com.planetsystems.tela.dto.dashboard.DataOutPutDTO;
import com.planetsystems.tela.managementapp.shared.UtilityManager;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class InstitutionDashboardGenerator {
	 
	private static InstitutionDashboardGenerator instance = new InstitutionDashboardGenerator();

	private InstitutionDashboardGenerator() {

	}

	public static InstitutionDashboardGenerator getInstance() {
		return instance;
	}

	public void generateDashboard(DashboardPane dashboardPane, final DashboardSummaryDTO dto) {

		long institutionLearners = dto.getInstitutionLearners();
		long institutionLearnersBoys = dto.getInstitutionLearnersBoys();
		long institutionLearnersGirls = dto.getInstitutionLearnersGirls();

		long institutionTeachers = dto.getInstitutionTeachers();
		long institutionTeachersOnPayRoll = dto.getInstitutionTeachersOnPayRoll();
		long institutionTeachersNotOnPayRoll = dto.getInstitutionTeachersNotOnPayRoll();

		long institutionTeachersMale = dto.getInstitutionTeachersMale();
		long institutionTeachersFemale = dto.getInstitutionTeachersFemale();

		long institutionTeachersWithSpecialNeedslearners = dto.getInstitutionTeachersWithSpecialNeedslearners();
		long institutionTeachersWithoutSpecialNeedslearners = dto.getInstitutionTeachersWithoutSpecialNeedslearners();

		List<DataOutPutDTO> institutionstaffEnrolledByRegion = dto.getInstitutionstaffEnrolledByRegion();

		List<DataOutPutByGenderDTO> institutionStaffEnrolledByGenderByRegion = dto
				.getInstitutionStaffEnrolledByGenderByRegion();

		List<DataOutPutDTO> institutionlearnsEnrolledByRegion = dto.getInstitutionlearnsEnrolledByRegion();

		List<DataOutPutByGenderDTO> institutionlearnersEnrolledByGenderRegion = dto
				.getInstitutionlearnersEnrolledByGenderRegion();

		HLayout row1Summary = new HLayout();

		row1Summary.addMember(getCard("Teacher/instructors Enrolled", UtilityManager.getInstance().formatCash(institutionTeachers),
				"#ffffff", "#7FBA00"));

		row1Summary.addMember(getCard("Students Enrolled", UtilityManager.getInstance().formatCash(institutionLearners),
				"#ffffff", "#00A4EF"));

		row1Summary.addMember(getCard("Teacher/instructors on Payroll",
				UtilityManager.getInstance().formatCash(institutionTeachersOnPayRoll), "#ffffff", "#FFB900"));
		row1Summary.addMember(getCard("Teacher/instructors Not on Payroll",
				UtilityManager.getInstance().formatCash(institutionTeachersNotOnPayRoll), "#ffffff", "#737373"));

		row1Summary.setAutoHeight();
		// row1Summary.setWidth("20%");
		row1Summary.setPadding(5);
		row1Summary.setBorder("0px solid #CDCFCC");

		VLayout chart1 = new VLayout();
		chart1.addMember(tearchersByGenderChart(institutionTeachersMale, institutionTeachersFemale,
				dto.getYear() + "-" + dto.getTerm()));

		VLayout chart2 = new VLayout();
		chart2.addMember(learnerByGenderCountChart(institutionLearnersBoys, institutionLearnersGirls,
				dto.getYear() + "-" + dto.getTerm()));

		VLayout chart3 = new VLayout();
		chart3.addMember(staffBySpecialNeedsCountChart(institutionTeachersWithSpecialNeedslearners,
				institutionTeachersWithoutSpecialNeedslearners, dto.getYear() + "-" + dto.getTerm()));

		VLayout chart4 = new VLayout();
		chart4.addMember(teachersPerRegionChart(institutionstaffEnrolledByRegion));

		VLayout chart5 = new VLayout();
		chart5.addMember(teachersByRegionByGenderStackedChart(institutionStaffEnrolledByGenderByRegion));

		VLayout chart6 = new VLayout();
		chart6.addMember(learnersPerRegionChart(institutionlearnsEnrolledByRegion));

		VLayout chart7 = new VLayout();
		chart7.addMember(learnersByRegionByGenderStackedChart(institutionlearnersEnrolledByGenderRegion));

		HLayout row2Summary = new HLayout();
		row2Summary.addMember(chart1);
		row2Summary.addMember(chart2);
		row2Summary.addMember(chart3);
		row2Summary.setMargin(10);

		VLayout row1 = new VLayout();
		row1.addMember(row1Summary);
		row1.addMember(row2Summary);

		VLayout row2 = new VLayout();
		row2.addMember(chart4);
		// row2.addMember(chart4);

		VLayout row3 = new VLayout();
		row3.addMember(chart5);
		// row3.addMember(chart6);

		VLayout row4 = new VLayout();
		row4.addMember(chart6);

		VLayout row5 = new VLayout();
		row5.addMember(chart7);

		VLayout layout2 = new VLayout();
		layout2.addMember(row1);
		layout2.addMember(row2);
		layout2.addMember(row3);

		layout2.addMember(row4);
		layout2.addMember(row5);
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

	private Chart tearchersByGenderChart(long male, long female, String period) {

		final Chart chart = new Chart().setType(Series.Type.PIE).setChartTitleText("Number of Teacher/instructors by Gender")
				.setChartSubtitleText("Year: " + period).setPlotBackgroundColor((String) null).setPlotBorderWidth(null)
				.setPlotShadow(false)
				.setPiePlotOptions(new PiePlotOptions().setCenter(.5, .5).setInnerSize(.2).setAllowPointSelect(true)
						.setCursor(PlotOptions.Cursor.POINTER).setPieDataLabels(new PieDataLabels().setEnabled(true)
								.setDistance(-50).setFormatter(new DataLabelsFormatter() {
									public String format(DataLabelsData dataLabelsData) {
										return UtilityManager.getInstance().formatCash(dataLabelsData.getYAsDouble());
									}
								}))
						.setShowInLegend(true))
				.setToolTip(new ToolTip().setFormatter(new ToolTipFormatter() {
					public String format(ToolTipData toolTipData) {
						return "<b>" + toolTipData.getPointName() + "</b>: "
								+ UtilityManager.getInstance().formatCash(toolTipData.getYAsDouble());
					}
				}));

		chart.addSeries(chart.createSeries().setName("Staff").setPoints(new Point[] {
				new Point("Male", male).setColor("#FFB900"), new Point("Female", female).setColor("#7FBA00") }));

		// #FFB900,#7FBA00

		chart.setBorderWidth(1);
		chart.setBorderColor("#CDCFCC");
		chart.setHeight(360);
		chart.setWidth(450);

		return chart;

	}

	private Chart learnerByGenderCountChart(long boys, long girls, String period) {

		final Chart chart = new Chart().setType(Series.Type.PIE).setChartTitleText("Student Enrollment by Gender")
				.setChartSubtitleText("Year: " + period).setPlotBackgroundColor((String) null).setPlotBorderWidth(null)
				.setPlotShadow(false)
				.setPiePlotOptions(new PiePlotOptions().setCenter(.5, .5).setInnerSize(.2).setAllowPointSelect(true)
						.setCursor(PlotOptions.Cursor.POINTER).setPieDataLabels(new PieDataLabels().setEnabled(true)
								.setDistance(-50).setFormatter(new DataLabelsFormatter() {
									public String format(DataLabelsData dataLabelsData) {
										return UtilityManager.getInstance().formatCash(dataLabelsData.getYAsDouble());
									}
								}))
						.setShowInLegend(true))
				.setToolTip(new ToolTip().setFormatter(new ToolTipFormatter() {
					public String format(ToolTipData toolTipData) {
						return "<b>" + toolTipData.getPointName() + "</b>: "
								+ UtilityManager.getInstance().formatCash(toolTipData.getYAsDouble());
					}
				}));

		chart.addSeries(chart.createSeries().setName("Student").setPoints(new Point[] {
				new Point("Male", boys).setColor("#00A4EF"), new Point("Female", girls).setColor("#737373") }));

		// #00A4EF,#737373

		chart.setBorderWidth(1);
		chart.setBorderColor("#CDCFCC");
		chart.setHeight(360);
		chart.setWidth(450);

		return chart;

	}

	public Chart staffBySpecialNeedsCountChart(long onPayroll, long notOnPayroll, String period) {

		final Chart chart = new Chart().setType(Series.Type.PIE)
				.setChartTitleText("Number of Teacher/instructors by Special Needs Status").setChartSubtitleText("Year: " + period)
				.setPlotBackgroundColor((String) null).setPlotBorderWidth(null).setPlotShadow(false)
				.setPiePlotOptions(new PiePlotOptions().setCenter(.5, .5).setInnerSize(.2).setAllowPointSelect(true)
						.setCursor(PlotOptions.Cursor.POINTER).setPieDataLabels(new PieDataLabels().setEnabled(true)
								.setDistance(-50).setFormatter(new DataLabelsFormatter() {
									public String format(DataLabelsData dataLabelsData) {
										return UtilityManager.getInstance().formatCash(dataLabelsData.getYAsDouble());
									}
								}))
						.setShowInLegend(true))
				.setToolTip(new ToolTip().setFormatter(new ToolTipFormatter() {
					public String format(ToolTipData toolTipData) {
						return "<b>" + toolTipData.getPointName() + "</b>: "
								+ UtilityManager.getInstance().formatCash(toolTipData.getYAsDouble());
					}
				}));

		chart.addSeries(chart.createSeries().setName("Teacher/instructors")
				.setPoints(new Point[] { new Point("Teacher/instructors with Special Needs", onPayroll).setColor("#FFB900"),
						new Point("Teacher/instructors without Special Needs", notOnPayroll).setColor("#00A4EF") }));

		// "#FFB900","#00A4EF"

		chart.setBorderWidth(1);
		chart.setBorderColor("#CDCFCC");
		chart.setHeight(360);
		chart.setWidth(450);

		return chart;
	}

	private Chart teachersPerRegionChart(List<DataOutPutDTO> staffEnrolled) {
		Chart lineChart = new Chart().setType(Series.Type.COLUMN)
				.setChartTitle(new ChartTitle().setText("Number of Teacher/instructors By Sub-Region"))
				.setChartSubtitle(new ChartSubtitle().setText("Numbers")).setToolTip(new ToolTip().setEnabled(false));

		lineChart.getYAxis().setAxisTitleText("Number of Teacher/instructors");

		lineChart.setBarPlotOptions(
				new BarPlotOptions().setEnableMouseTracking(true).setDataLabels(new DataLabels().setEnabled(true)));

		String[] lables = new String[staffEnrolled.size()];

		Number[] teachers = new Number[staffEnrolled.size()];

		int counter = 0;
		for (DataOutPutDTO dto : staffEnrolled) {
			lables[counter] = dto.getKey();
			teachers[counter] = dto.getValue();
			counter++;
		}

		lineChart.getXAxis().setCategories(lables);
		lineChart.addSeries(lineChart.createSeries().setName("Number of Teacher/instructors").setPoints(teachers));
		lineChart.setColors("#00A4EF", "#7FBA00");

		lineChart.setBorderWidth(1);
		lineChart.setBorderColor("#CDCFCC");
		lineChart.setHeight(600);

		return lineChart;
	}

	private Chart teachersByRegionByGenderStackedChart(List<DataOutPutByGenderDTO> list) {

		final Chart chart = new Chart().setType(Series.Type.COLUMN)
				.setChartTitleText("Number of Teacher/instructors by Sub-Region by Gender")
				.setSeriesPlotOptions(new SeriesPlotOptions().setStacking(PlotOptions.Stacking.NORMAL))
				.setLegend(new Legend().setBackgroundColor("#FFFFFF").setReversed(true))
				.setToolTip(new ToolTip().setFormatter(new ToolTipFormatter() {
					public String format(ToolTipData toolTipData) {
						return toolTipData.getSeriesName() + ": " + toolTipData.getYAsLong() + " "
								+ toolTipData.getXAsString();
					}
				})).setColors("#00A4EF", "#7FBA00");

		int counter = 0;

		String[] lables = new String[list.size()];
		Number[] male = new Number[list.size()];
		Number[] female = new Number[list.size()];

		for (DataOutPutByGenderDTO dto : list) {

			lables[counter] = dto.getKey();
			male[counter] = dto.getMale();
			female[counter] = dto.getFemale();
			counter++;
		}

		chart.getXAxis().setCategories(lables);
		chart.addSeries(chart.createSeries().setName("Male").setPoints(male));
		chart.addSeries(chart.createSeries().setName("Female").setPoints(female));

		chart.setBarPlotOptions(
				new BarPlotOptions().setEnableMouseTracking(true).setDataLabels(new DataLabels().setEnabled(true)));

		chart.getYAxis().setMin(0).setAxisTitleText("Number of Teacher/instructors");
		chart.setBorderWidth(1);
		chart.setBorderColor("#CDCFCC");
		chart.setHeight(600);
		return chart;
	}

	private Chart learnersPerRegionChart(List<DataOutPutDTO> learnersEnrolled) {
		Chart lineChart = new Chart().setType(Series.Type.COLUMN)
				.setChartTitle(new ChartTitle().setText("Student Enrollment by Sub-Region"))
				.setChartSubtitle(new ChartSubtitle().setText("Numbers")).setToolTip(new ToolTip().setEnabled(true));

		lineChart.getYAxis().setAxisTitleText("Number of Students");

		lineChart.setColumnPlotOptions(
				new ColumnPlotOptions().setEnableMouseTracking(true).setDataLabels(new DataLabels().setEnabled(true)));

		String[] lables = new String[learnersEnrolled.size()];

		Number[] learners = new Number[learnersEnrolled.size()];

		int counter = 0;
		for (DataOutPutDTO dto : learnersEnrolled) {
			lables[counter] = dto.getKey();
			learners[counter] = dto.getValue();
			counter++;
		}

		lineChart.getXAxis().setCategories(lables);
		lineChart.addSeries(lineChart.createSeries().setName("Number of Students").setPoints(learners));
		lineChart.setColors("#00A4EF", "#7FBA00");

		lineChart.setBorderWidth(1);
		lineChart.setBorderColor("#CDCFCC");
		lineChart.setHeight(600);

		return lineChart;
	}

	private Chart learnersByRegionByGenderStackedChart(List<DataOutPutByGenderDTO> list) {

		final Chart chart = new Chart().setType(Series.Type.COLUMN)
				.setChartTitleText("Student Enrollment by Sub-Region by Gender")
				.setSeriesPlotOptions(new SeriesPlotOptions().setStacking(PlotOptions.Stacking.NORMAL))
				.setLegend(new Legend().setBackgroundColor("#FFFFFF").setReversed(true))
				.setToolTip(new ToolTip().setFormatter(new ToolTipFormatter() {
					public String format(ToolTipData toolTipData) {
						return toolTipData.getSeriesName() + ": " + toolTipData.getYAsLong() + " "
								+ toolTipData.getXAsString();
					}
				})).setColors("#00A4EF", "#7FBA00");

		String[] lables = new String[list.size()];
		Number[] boys = new Number[list.size()];
		Number[] girls = new Number[list.size()];

		int counter = 0;

		for (DataOutPutByGenderDTO dto : list) {

			lables[counter] = dto.getKey();
			boys[counter] = dto.getMale();
			girls[counter] = dto.getFemale();
			counter++;
		}

		chart.getXAxis().setCategories(lables);
		chart.addSeries(chart.createSeries().setName("Male").setPoints(boys));
		chart.addSeries(chart.createSeries().setName("Female").setPoints(girls));

		chart.setColumnPlotOptions(
				new ColumnPlotOptions().setEnableMouseTracking(true).setDataLabels(new DataLabels().setEnabled(true)));

		chart.getYAxis().setMin(0).setAxisTitleText("Number of Students");
		chart.setBorderWidth(1);
		chart.setBorderColor("#CDCFCC");
		chart.setHeight(600);
		return chart;
	}



}
