package com.planetsystems.tela.managementapp.client.presenter.reports.smcperformance;
 
import com.planetsystems.tela.managementapp.client.widget.PerformanceScale;
import com.smartgwt.client.widgets.layout.VLayout;

public class SMCSupervisionReportPane extends VLayout {

	private SMCSupervisionReportListgrid listgrid;

	private PerformanceScale performanceScale;

	public SMCSupervisionReportPane() {
		super();

		listgrid = new SMCSupervisionReportListgrid();
		performanceScale = new PerformanceScale();

		VLayout layout = new VLayout();
		layout.addMember(listgrid);
		layout.addMember(performanceScale);
		this.addMember(layout);
	}

	public SMCSupervisionReportListgrid getListgrid() {
		return listgrid;
	}

	public PerformanceScale getPerformanceScale() {
		return performanceScale;
	}
	
	

}
