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

import com.google.gwt.core.client.GWT;
import com.planetsystems.tela.dto.dashboard.DashboardSummaryDTO;
import com.planetsystems.tela.dto.dashboard.DataOutPutByGenderDTO;
import com.planetsystems.tela.dto.dashboard.DataOutPutDTO;
import com.planetsystems.tela.managementapp.shared.UtilityManager;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class OverallCountDashboardGenerator {

	private static OverallCountDashboardGenerator instance = new OverallCountDashboardGenerator();

	private OverallCountDashboardGenerator() {

	}

	public static OverallCountDashboardGenerator getInstance() {
		return instance;
	}

	public void generateDashboard(DashboardPane dashboardPane,final DashboardSummaryDTO dto) {
		long totalSchools = dto.getShools();
		long totalTeachers = dto.getTeachers();
		long totalLearners = dto.getLearners();

		VLayout col1 = new VLayout();
		col1.addMember(
				getCard("Number of Public Primary Schools", UtilityManager.getInstance().formatCash(totalSchools), "#3ac47d"));
		col1.addMember(
				getCard("Number of Teachers on Payroll", UtilityManager.getInstance().formatCash(totalTeachers), "#f7b924"));
		col1.addMember(
				getCard("Total Number of Learners", UtilityManager.getInstance().formatCash(totalLearners), "#6495ED"));
		col1.setAutoHeight();
		col1.setWidth("20%");
		col1.setPadding(5);
		col1.setBorder("1px solid #CDCFCC");

		VLayout chart1 = new VLayout();
		chart1.addMember(learnerCountChart(dto.getBoys(),dto.getGirls(),dto.getYear()+"-"+dto.getTerm()));

		VLayout chart2 = new VLayout();
		chart2.addMember(tearchersCountChart(dto.getMaleTeachers(),dto.getFemaleTeachers(),dto.getYear()+"-"+dto.getTerm()));
		
		VLayout chart3 = new VLayout();
		GWT.log("getStaffEnrolled:: loading");
		GWT.log("getStaffEnrolled:: "+dto.getStaffEnrolled().size());
		
		chart3.addMember(teachersPerRegionChart(dto.getStaffEnrolled()));
		
		VLayout chart4 = new VLayout();
		chart4.addMember(teachersByRegionByGenderStackedChart(dto.getStaffEnrolledByGender()));
		
		VLayout chart5 = new VLayout();
		chart5.addMember(learnersPerRegionChart(dto.getLearnsEnrolled()));
		
		VLayout chart6 = new VLayout();
		chart6.addMember(learnersByRegionByGenderStackedChart(dto.getLearnsEnrolledByGender()));
		  
		HLayout col2 = new HLayout();
		col2.addMember(chart1);
		col2.addMember(chart2);
		col2.setMargin(10);

		HLayout row1 = new HLayout();
		row1.addMember(col1);
		row1.addMember(col2);
		  
		HLayout row2 = new HLayout();
		row2.addMember(chart3);
		row2.addMember(chart4);
		
		HLayout row3 = new HLayout();
		row3.addMember(chart5);
		row3.addMember(chart6);

		VLayout layout2 = new VLayout();
		layout2.addMember(row1);
		layout2.addMember(row2);
		layout2.addMember(row3);
		layout2.setMembersMargin(5);

		/* DashboarTestWindow testWindow = new DashboarTestWindow();
		testWindow.getLayout().setMembers(layout2);
		testWindow.show();*/  

		//dashboardPane.setMembers(layout2); 

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

	public Chart learnerCountChart(long boys,long girls,String period) {

		final Chart chart = new Chart().setType(Series.Type.PIE).setMargin(50, 0, 0, 0)
				.setChartTitleText("Learner Enrollment by Sex").setChartSubtitleText("Year: "+period)
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

	public Chart tearchersCountChart(long male,long female,String period) {

		final Chart chart = new Chart().setType(Series.Type.PIE).setMargin(50, 0, 0, 0)
				.setChartTitleText("Number of Teachers by Gender").setChartSubtitleText("Year: "+period)
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

	private Chart teachersPerRegionChart(List<DataOutPutDTO> staffEnrolled) {
		Chart lineChart = new Chart().setType(Series.Type.BAR)
				.setChartTitle(new ChartTitle().setText("Number of Teachers Per Region"))
				.setChartSubtitle(new ChartSubtitle().setText("Numbers")).setToolTip(new ToolTip().setEnabled(false));

		lineChart.getYAxis().setAxisTitleText("Number of Teachers");

		/*lineChart.setColumnPlotOptions(
				new ColumnPlotOptions().setEnableMouseTracking(true).setDataLabels(new DataLabels().setEnabled(true)));*/
		
		lineChart.setBarPlotOptions(
				new BarPlotOptions().setEnableMouseTracking(true).setDataLabels(new DataLabels().setEnabled(true))); 

		/*String[] lables = { "Buganda North", "Lango", "Bukedi", "Ankole", "Busoga", "Karamoja", "Kigezi", "Toro",
				"Teso", "Acholi", "Elgon", "West Nile", "Bunyoro", "Buganda South", "KAMPALA" };*/
		/*Number[] teachers = {10,30,40,30,20,37,28,54,100,37,64,36,50,47,41};*/
		//Number[] female = {20,35,21,40,30,39,46,50,87,80,41,36,50,30,41};

		String[] lables=new String[staffEnrolled.size()];
		
		Number[] teachers=new Number[staffEnrolled.size()];
		
		 int counter = 0;
		for (DataOutPutDTO dto : staffEnrolled) {
			lables[counter] = dto.getKey();
			teachers[counter] = dto.getValue();
			counter++;
		} 

		lineChart.getXAxis().setCategories(lables);
		lineChart.addSeries(lineChart.createSeries().setName("Number of Teachers").setPoints(teachers)); 
		lineChart.setColors("#40E0D0","#6495ED");
		 

		lineChart.setBorderWidth(1);
		lineChart.setBorderColor("#CDCFCC");
		lineChart.setHeight(500);

		return lineChart;
	} 
	
	private Chart teachersByRegionByGenderStackedChart(List<DataOutPutByGenderDTO> list) {

		final Chart chart = new Chart().setType(Series.Type.BAR)
				.setChartTitleText("Number of Teachers by Region by Gender")
				.setSeriesPlotOptions(new SeriesPlotOptions().setStacking(PlotOptions.Stacking.NORMAL))
				.setLegend(new Legend().setBackgroundColor("#FFFFFF").setReversed(true))
				.setToolTip(new ToolTip().setFormatter(new ToolTipFormatter() {
					public String format(ToolTipData toolTipData) {
						return toolTipData.getSeriesName() + ": " + toolTipData.getYAsLong() + " "
								+ toolTipData.getXAsString();
					}
				})).setColors("#FFC300", "#DAF7A6");

		  
			/*String[] lables = {"Buganda North", "Lango", "Bukedi", "Ankole", "Busoga", "Karamoja", "Kigezi", "Toro",
					"Teso", "Acholi", "Elgon", "West Nile", "Bunyoro", "Buganda South", "KAMPALA"};
			Number[] male = {10,30,40,30,20,37,28,54,100,37,64,36,50,47,41};
			Number[] female = {20,35,21,40,30,39,46,50,87,80,41,36,50,30,41};*/

			int counter = 0;
			int validPointCount = 0;
			int expiredPointCount = 0;

			String[] lables =new String[list.size()];
			Number[] male =new Number[list.size()];
			Number[] female =new Number[list.size()];
			
			for (DataOutPutByGenderDTO dto : list) {

				lables[counter] = dto.getKey();
				male[counter]=dto.getMale();
				female[counter]=dto.getFemale();
				counter++;
			} 

			chart.getXAxis().setCategories(lables);
			chart.addSeries(chart.createSeries().setName("Male").setPoints(male));
			chart.addSeries(chart.createSeries().setName("Female").setPoints(female));
			
			 chart.setBarPlotOptions(
					new BarPlotOptions().setEnableMouseTracking(true).setDataLabels(new DataLabels().setEnabled(true))); 
			
			/*chart.setColumnPlotOptions(
					new ColumnPlotOptions().setEnableMouseTracking(true).setDataLabels(new DataLabels().setEnabled(true)));*/
		 

		chart.getYAxis().setMin(0).setAxisTitleText("Number of Teachers");
		chart.setBorderWidth(1);
		chart.setBorderColor("#CDCFCC");
		chart.setHeight(500);
		return chart;
	}
	
	
	private Chart learnersPerRegionChart(List<DataOutPutDTO> learnersEnrolled) {
		Chart lineChart = new Chart().setType(Series.Type.COLUMN)
				.setChartTitle(new ChartTitle().setText("Learner Enrollment by Region"))
				.setChartSubtitle(new ChartSubtitle().setText("Numbers")).setToolTip(new ToolTip().setEnabled(true));

		lineChart.getYAxis().setAxisTitleText("Number of Learners");

		lineChart.setColumnPlotOptions(
				new ColumnPlotOptions().setEnableMouseTracking(true).setDataLabels(new DataLabels().setEnabled(true)));

		/*String[] lables = { "Buganda North", "Lango", "Bukedi", "Ankole", "Busoga", "Karamoja", "Kigezi", "Toro",
				"Teso", "Acholi", "Elgon", "West Nile", "Bunyoro", "Buganda South", "KAMPALA" };
		Number[] learners = {10,30,40,30,20,37,28,54,100,37,64,36,50,47,41}; */

		String[] lables=new String[learnersEnrolled.size()];
		
		Number[] learners =new Number[learnersEnrolled.size()];
		
		int counter = 0;
		for (DataOutPutDTO dto : learnersEnrolled) {
			lables[counter] = dto.getKey();
			learners[counter] = dto.getValue();
			counter++;
		} 

		lineChart.getXAxis().setCategories(lables);
		lineChart.addSeries(lineChart.createSeries().setName("Number of Learners").setPoints(learners)); 
		lineChart.setColors("#40E0D0","#6495ED");
		 

		lineChart.setBorderWidth(1);
		lineChart.setBorderColor("#CDCFCC");
		lineChart.setHeight(500);

		return lineChart;
	}
	 
	private Chart learnersByRegionByGenderStackedChart(List<DataOutPutByGenderDTO> list) {

		final Chart chart = new Chart().setType(Series.Type.COLUMN)
				.setChartTitleText("Learner Enrollment by Region by Gender")
				.setSeriesPlotOptions(new SeriesPlotOptions().setStacking(PlotOptions.Stacking.NORMAL))
				.setLegend(new Legend().setBackgroundColor("#FFFFFF").setReversed(true))
				.setToolTip(new ToolTip().setFormatter(new ToolTipFormatter() {
					public String format(ToolTipData toolTipData) {
						return toolTipData.getSeriesName() + ": " + toolTipData.getYAsLong() + " "
								+ toolTipData.getXAsString();
					}
				})).setColors("#FFC300", "#DAF7A6");

		  
			/*String[] lables = {"Buganda North", "Lango", "Bukedi", "Ankole", "Busoga", "Karamoja", "Kigezi", "Toro",
					"Teso", "Acholi", "Elgon", "West Nile", "Bunyoro", "Buganda South", "KAMPALA"};
			Number[] boys = {10,30,40,30,20,37,28,54,100,37,64,36,50,47,41};
			Number[] girls = {20,35,21,40,30,39,46,50,87,80,41,36,50,30,41};*/

			String[] lables =new String[list.size()];
			Number[] boys=new Number[list.size()];
			Number[] girls=new Number[list.size()];
			int counter = 0;
			int validPointCount = 0;
			int expiredPointCount = 0;

			for (DataOutPutByGenderDTO dto : list) {

				lables[counter] = dto.getKey();
				boys[counter]=dto.getMale();
				girls[counter]=dto.getFemale();
				counter++;
			} 

			chart.getXAxis().setCategories(lables);
			chart.addSeries(chart.createSeries().setName("Boys").setPoints(boys));
			chart.addSeries(chart.createSeries().setName("Girls").setPoints(girls));
			
			 /*chart.setBarPlotOptions(
					new BarPlotOptions().setEnableMouseTracking(true).setDataLabels(new DataLabels().setEnabled(true))); */
			
			 chart.setColumnPlotOptions(
					new ColumnPlotOptions().setEnableMouseTracking(true).setDataLabels(new DataLabels().setEnabled(true))); 
		 

		chart.getYAxis().setMin(0).setAxisTitleText("Number of Learners");
		chart.setBorderWidth(1);
		chart.setBorderColor("#CDCFCC");
		chart.setHeight(500);
		return chart;
	}
	
}
