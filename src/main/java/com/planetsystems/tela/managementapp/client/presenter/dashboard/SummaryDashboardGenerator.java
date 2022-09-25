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
import com.planetsystems.tela.dto.dashboard.DataOutPutBySchoolLevelDTO;
import com.planetsystems.tela.dto.dashboard.DataOutPutDTO;
import com.planetsystems.tela.managementapp.shared.UtilityManager;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class SummaryDashboardGenerator {
	


	private static SummaryDashboardGenerator instance = new SummaryDashboardGenerator();

	private SummaryDashboardGenerator() {

	}

	public static SummaryDashboardGenerator getInstance() {
		return instance;
	}

	public void generateDashboard(DashboardPane dashboardPane, final DashboardSummaryDTO dto) {
		  
		long primarySchools = dto.getPrimarySchools();
		long secondarySchools = dto.getSecondarySchools();
		long institutions = dto.getInstitutions();
    
		VLayout col1Summary = new VLayout();
		 
		//#7FBA00,00A4EF,FFB900
		col1Summary.addMember(
				getCard("Public Primary Schools", UtilityManager.getInstance().formatCash(primarySchools), "#ffffff" ,"#7FBA00"));
		col1Summary.addMember(
				getCard("Public Secondary Schools", UtilityManager.getInstance().formatCash(secondarySchools), "#ffffff","#00A4EF"));

		col1Summary.addMember(getCard("Public Certificate Awarding Institutions",
				UtilityManager.getInstance().formatCash(institutions), "#ffffff","#FFB900"));

		col1Summary.setAutoHeight();
		col1Summary.setWidth("20%");
		col1Summary.setPadding(5);
		col1Summary.setBorder("0px solid #CDCFCC");

		  
 

		VLayout schoolLevelByRegion = new VLayout();
		schoolLevelByRegion.addMember(schoolsByRegionStackedChart(dto.getSchoolsLevelByRegion()));
		schoolLevelByRegion.setPadding(5);
  

		HLayout row1 = new HLayout();
		row1.addMember(col1Summary);
		row1.addMember(schoolLevelByRegion);
		//row1.addMember(col2);
 

		VLayout layout2 = new VLayout();
		layout2.addMember(row1);
		//layout2.addMember(row2);
		//layout2.addMember(row3);
		layout2.setMembersMargin(5);

		/*DashboarTestWindow testWindow = new DashboarTestWindow();
		testWindow.getLayout().setMembers(layout2);
		testWindow.show();*/

		dashboardPane.setMembers(layout2);

	}

	private VLayout getCard(String cardTitle, String value, String color,String bgColor) {
		Label label = new Label();
		label.setPadding(2);
		label.setAlign(Alignment.CENTER);
		label.setValign(VerticalAlignment.CENTER);
		label.setWrap(true);
		label.setContents(
				"<div style='box-sizing: border-box; box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);"
				+ "background-color:"+bgColor+";padding: 2px;font-family: \"Lucida Grande\","
						+ " \"Lucida Sans Unicode\", Arial, Helvetica, sans-serif; width:290px;'>"
				+ "<p style='font-weight: bold; font-size: 1.8rem;color:#ffffff !important'>" + value + "</p>"
				+ "<p style='color:" + color + " !important;'> <span style='font-size: 0.9rem; font-weight: bold;'>"
				+ cardTitle + "</span></p></div>");
		label.setCanSelectText(true);

		VLayout layout = new VLayout();
		layout.addMember(label);
		layout.setAutoHeight();

		return layout;
	}


	private Chart teachersPerRegionChart(List<DataOutPutDTO> staffEnrolled) {
		Chart lineChart = new Chart().setType(Series.Type.BAR)
				.setChartTitle(new ChartTitle().setText("Number of Teachers Per Sub-Region"))
				.setChartSubtitle(new ChartSubtitle().setText("Numbers")).setToolTip(new ToolTip().setEnabled(false));

		lineChart.getYAxis().setAxisTitleText("Number of Teachers");

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
		lineChart.addSeries(lineChart.createSeries().setName("Number of Teachers").setPoints(teachers));
		lineChart.setColors("#40E0D0", "#6495ED");

		lineChart.setBorderWidth(1);
		lineChart.setBorderColor("#CDCFCC");
		lineChart.setHeight(500);

		return lineChart;
	}

	private Chart schoolsByRegionStackedChart(List<DataOutPutBySchoolLevelDTO> list) {

		Chart chart = new Chart().setType(Series.Type.COLUMN)
				.setChartTitle(new ChartTitle().setText("Number of Schools/Institutions by Sub-Region"))
				.setChartSubtitle(new ChartSubtitle().setText("Numbers")).setToolTip(new ToolTip().setEnabled(true));
   
		
		int counter = 0;

		String[] lables = new String[list.size()];
		
		Number[] primary = new Number[list.size()];
		Number[] secondary = new Number[list.size()];
		Number[] institutions = new Number[list.size()];

		for (DataOutPutBySchoolLevelDTO dto : list) {

			lables[counter] = dto.getKey();
			primary[counter] = dto.getPrimary();
			secondary[counter] = dto.getSecondary();
			institutions[counter] = dto.getInstitutions();
			counter++;
		}

		chart.getXAxis().setCategories(lables);
		chart.addSeries(chart.createSeries().setName("Public Primary Schools").setPoints(primary));
		chart.addSeries(chart.createSeries().setName("Public Secondary Schools").setPoints(secondary)); 
		chart.addSeries(chart.createSeries().setName("Public Certificate Awarding Institutions").setPoints(institutions));
		
		chart.setBarPlotOptions(
				new BarPlotOptions().setEnableMouseTracking(true).setDataLabels(new DataLabels().setEnabled(true)));

		//#7FBA00,00A4EF,FFB900
		chart.getYAxis().setMin(0).setAxisTitleText("Number of Public Schools and Certificate Awarding Institutions");
		chart.setBorderWidth(0);
		chart.setBorderColor("#CDCFCC");
		chart.setHeight(600);
		chart.setColors("#7FBA00", "#00A4EF","#FFB900"); 
		return chart;
	}

	private Chart learnersPerRegionChart(List<DataOutPutDTO> learnersEnrolled) {
		Chart lineChart = new Chart().setType(Series.Type.COLUMN)
				.setChartTitle(new ChartTitle().setText("Learner Enrollment by Sub-Region"))
				.setChartSubtitle(new ChartSubtitle().setText("Numbers")).setToolTip(new ToolTip().setEnabled(true));

		lineChart.getYAxis().setAxisTitleText("Number of Learners");

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
		lineChart.addSeries(lineChart.createSeries().setName("Number of Learners").setPoints(learners));
		lineChart.setColors("#40E0D0", "#6495ED");

		lineChart.setBorderWidth(1);
		lineChart.setBorderColor("#CDCFCC");
		lineChart.setHeight(500);

		return lineChart;
	}

	private Chart learnersByRegionByGenderStackedChart(List<DataOutPutByGenderDTO> list) {

		final Chart chart = new Chart().setType(Series.Type.COLUMN)
				.setChartTitleText("Learner Enrollment by Sub-Region by Gender")
				.setSeriesPlotOptions(new SeriesPlotOptions().setStacking(PlotOptions.Stacking.NORMAL))
				.setLegend(new Legend().setBackgroundColor("#FFFFFF").setReversed(true))
				.setToolTip(new ToolTip().setFormatter(new ToolTipFormatter() {
					public String format(ToolTipData toolTipData) {
						return toolTipData.getSeriesName() + ": " + toolTipData.getYAsLong() + " "
								+ toolTipData.getXAsString();
					}
				})).setColors("#FFC300", "#DAF7A6");

		

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
		chart.addSeries(chart.createSeries().setName("Boys").setPoints(boys));
		chart.addSeries(chart.createSeries().setName("Girls").setPoints(girls));
 
		chart.setColumnPlotOptions(
				new ColumnPlotOptions().setEnableMouseTracking(true).setDataLabels(new DataLabels().setEnabled(true)));

		chart.getYAxis().setMin(0).setAxisTitleText("Number of Learners");
		chart.setBorderWidth(1);
		chart.setBorderColor("#CDCFCC");
		chart.setHeight(500);
		return chart;
	}



}
