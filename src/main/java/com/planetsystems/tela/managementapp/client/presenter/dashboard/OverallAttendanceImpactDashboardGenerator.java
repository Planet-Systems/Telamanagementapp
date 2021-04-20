package com.planetsystems.tela.managementapp.client.presenter.dashboard;

import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.ChartSubtitle;
import org.moxieapps.gwt.highcharts.client.ChartTitle;
import org.moxieapps.gwt.highcharts.client.Legend;
import org.moxieapps.gwt.highcharts.client.Series;
import org.moxieapps.gwt.highcharts.client.ToolTip;
import org.moxieapps.gwt.highcharts.client.ToolTipData;
import org.moxieapps.gwt.highcharts.client.ToolTipFormatter;
import org.moxieapps.gwt.highcharts.client.labels.DataLabels;
import org.moxieapps.gwt.highcharts.client.plotOptions.ColumnPlotOptions;
import org.moxieapps.gwt.highcharts.client.plotOptions.PlotOptions;
import org.moxieapps.gwt.highcharts.client.plotOptions.SeriesPlotOptions;

import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class OverallAttendanceImpactDashboardGenerator {
	


	private static OverallAttendanceImpactDashboardGenerator instance = new OverallAttendanceImpactDashboardGenerator();

	private OverallAttendanceImpactDashboardGenerator() {

	}

	public static OverallAttendanceImpactDashboardGenerator getInstance() {
		return instance;
	}
	
	
	public void generateDashboard(DashboardPane dashboardPane) {
		 

		VLayout chart1 = new VLayout();
		chart1.addMember(learnersPerYearChart());

		VLayout chart2 = new VLayout();
		chart2.addMember(learnersByYearByGenderStackedChart());
		
		VLayout chart3 = new VLayout();
		chart3.addMember(teachersPerYearChart());
		
		VLayout chart4 = new VLayout();
		chart4.addMember(teachersByYearByGenderStackedChart());
		
		/*VLayout chart5 = new VLayout();
		chart5.addMember(learnersPerRegionChart());
		
		VLayout chart6 = new VLayout();
		chart6.addMember(learnersByRegionByGenderStackedChart());*/
		  
		HLayout col1 = new HLayout();
		col1.addMember(chart1);
		col1.addMember(chart2);
		col1.addMember(chart3);
		col1.addMember(chart4);
		col1.setMargin(10);

		HLayout row1 = new HLayout();
		row1.addMember(col1); 
		   
		  
		VLayout layout2 = new VLayout();
		layout2.addMember(row1); 
		layout2.setMembersMargin(5);

		 /* DashboarTestWindow testWindow = new DashboarTestWindow();
		testWindow.getLayout().setMembers(layout2);
		testWindow.show(); */ 

		 dashboardPane.setMembers(layout2); 

	}
	
	
	private Chart learnersPerYearChart() {
		Chart lineChart = new Chart().setType(Series.Type.COLUMN)
				.setChartTitle(new ChartTitle().setText("Learner Attendance by Year"))
				.setChartSubtitle(new ChartSubtitle().setText("Numbers")).setToolTip(new ToolTip().setEnabled(true));

		lineChart.getYAxis().setAxisTitleText("Number of Learners");

		lineChart.setColumnPlotOptions(
				new ColumnPlotOptions().setEnableMouseTracking(true).setDataLabels(new DataLabels().setEnabled(true)));

		String[] lables = { "2019", "2020", "2021" };
		Number[] learners = {10,30,40}; 

		/*int counter = 0;
		for (KeyValueSummaryDTO dto : list) {
			lables[counter] = dto.getName();
			points[counter] = dto.getValue();
			counter++;
		}*/

		lineChart.getXAxis().setCategories(lables);
		lineChart.addSeries(lineChart.createSeries().setName("Number of Learners").setPoints(learners)); 
		lineChart.setColors("#40E0D0","#6495ED");
		 

		lineChart.setBorderWidth(1);
		lineChart.setBorderColor("#CDCFCC");
		lineChart.setHeight(500);

		return lineChart;
	}
	
	private Chart learnersByYearByGenderStackedChart() {

		final Chart chart = new Chart().setType(Series.Type.COLUMN)
				.setChartTitleText("Learner Attendance by Year by Gender")
				.setSeriesPlotOptions(new SeriesPlotOptions().setStacking(PlotOptions.Stacking.NORMAL))
				.setLegend(new Legend().setBackgroundColor("#FFFFFF").setReversed(true))
				.setToolTip(new ToolTip().setFormatter(new ToolTipFormatter() {
					public String format(ToolTipData toolTipData) {
						return toolTipData.getSeriesName() + ": " + toolTipData.getYAsLong() + " "
								+ toolTipData.getXAsString();
					}
				})).setColors("#FFC300", "#DAF7A6");

		  
			String[] lables = {"2019", "2020", "2021"};
			Number[] boys = {10,30,40};
			Number[] girls = {20,35,21};

			/*int counter = 0;
			int validPointCount = 0;
			int expiredPointCount = 0;

			for (StackChartSummary summary : list) {

				lables[counter] = summary.getLable();

				if (summary.getPoints() != null) {

					for (KeyValueSummaryDTO point : summary.getPoints()) {

						if (point.getName().equalsIgnoreCase("Valid Permit")) {

							validPoints[validPointCount] = point.getValue();
							validPointCount++;

						} else if (point.getName().equalsIgnoreCase("Expired Permit")) {

							expiredPoints[expiredPointCount] = point.getValue();
							expiredPointCount++;

						}

					}
				}

				counter++;
			}*/

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
	
	private Chart teachersPerYearChart() {
		Chart lineChart = new Chart().setType(Series.Type.COLUMN)
				.setChartTitle(new ChartTitle().setText("Teacher Attendance by Year"))
				.setChartSubtitle(new ChartSubtitle().setText("Numbers")).setToolTip(new ToolTip().setEnabled(false));

		lineChart.getYAxis().setAxisTitleText("Number of Teachers");

		 lineChart.setColumnPlotOptions(
				new ColumnPlotOptions().setEnableMouseTracking(true).setDataLabels(new DataLabels().setEnabled(true))); 
		
		/*lineChart.setBarPlotOptions(
				new BarPlotOptions().setEnableMouseTracking(true).setDataLabels(new DataLabels().setEnabled(true))); */

		String[] lables = { "2019", "2020", "2021" };
		Number[] teachers = {10,30,40};
		//Number[] female = {20,35,21,40,30,39,46,50,87,80,41,36,50,30,41};

		/*int counter = 0;
		for (KeyValueSummaryDTO dto : list) {
			lables[counter] = dto.getName();
			points[counter] = dto.getValue();
			counter++;
		}*/

		lineChart.getXAxis().setCategories(lables);
		lineChart.addSeries(lineChart.createSeries().setName("Number of Teachers").setPoints(teachers)); 
		lineChart.setColors("#40E0D0","#6495ED");
		 

		lineChart.setBorderWidth(1);
		lineChart.setBorderColor("#CDCFCC");
		lineChart.setHeight(500);

		return lineChart;
	} 
	
	
	private Chart teachersByYearByGenderStackedChart() {

		final Chart chart = new Chart().setType(Series.Type.COLUMN)
				.setChartTitleText("Teacher Attendance by Year by Gender")
				.setSeriesPlotOptions(new SeriesPlotOptions().setStacking(PlotOptions.Stacking.NORMAL))
				.setLegend(new Legend().setBackgroundColor("#FFFFFF").setReversed(true))
				.setToolTip(new ToolTip().setFormatter(new ToolTipFormatter() {
					public String format(ToolTipData toolTipData) {
						return toolTipData.getSeriesName() + ": " + toolTipData.getYAsLong() + " "
								+ toolTipData.getXAsString();
					}
				})).setColors("#FFC300", "#DAF7A6");

		  
			String[] lables = {"2019", "2020", "2021"};
			Number[] male = {10,30,40};
			Number[] female = {20,35,21};

			/*int counter = 0;
			int validPointCount = 0;
			int expiredPointCount = 0;

			for (StackChartSummary summary : list) {

				lables[counter] = summary.getLable();

				if (summary.getPoints() != null) {

					for (KeyValueSummaryDTO point : summary.getPoints()) {

						if (point.getName().equalsIgnoreCase("Valid Permit")) {

							validPoints[validPointCount] = point.getValue();
							validPointCount++;

						} else if (point.getName().equalsIgnoreCase("Expired Permit")) {

							expiredPoints[expiredPointCount] = point.getValue();
							expiredPointCount++;

						}

					}
				}

				counter++;
			}*/

			chart.getXAxis().setCategories(lables);
			chart.addSeries(chart.createSeries().setName("Male").setPoints(male));
			chart.addSeries(chart.createSeries().setName("Female").setPoints(female));
			
			/* chart.setBarPlotOptions(
					new BarPlotOptions().setEnableMouseTracking(true).setDataLabels(new DataLabels().setEnabled(true)));*/ 
			
			chart.setColumnPlotOptions(
					new ColumnPlotOptions().setEnableMouseTracking(true).setDataLabels(new DataLabels().setEnabled(true)));
		 

		chart.getYAxis().setMin(0).setAxisTitleText("Number of Teachers");
		chart.setBorderWidth(1);
		chart.setBorderColor("#CDCFCC");
		chart.setHeight(500);
		return chart;
	}
	
	
	


}
