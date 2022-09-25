package com.planetsystems.tela.managementapp.client.presenter.datauploadstat;

import javax.inject.Inject;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane; 
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

class DataUploadStatView extends ViewImpl implements DataUploadStatPresenter.MyView {

	private static final String DEFAULT_MARGIN = "0px";
	private VLayout panel;

	private DataUploadStatPane dataUploadStatPane;

	private DataUploadSummaryStatPane dataUploadSummaryStatPane;

	private ControlsPane controlsPane;

	private TabSet tabSet;

	public static final String SUMMARY = "Data Upload Summary Statistics";
	public static final String DETAILED = "Data Upload Detailed Statistics";

	@Inject
	DataUploadStatView() {
		panel = new VLayout();
		dataUploadSummaryStatPane = new DataUploadSummaryStatPane();
		dataUploadStatPane = new DataUploadStatPane();
		controlsPane = new ControlsPane();
		
		tabSet = new TabSet();

		Tab tab1 = new Tab();
		tab1.setTitle(SUMMARY);
		tab1.setPane(dataUploadSummaryStatPane);

		Tab tab2 = new Tab();
		tab2.setTitle(DETAILED);
		tab2.setPane(dataUploadStatPane);

		tabSet.addTab(tab1);
		tabSet.addTab(tab2);
		
		panel.addMember(controlsPane);
		panel.addMember(tabSet);
		panel.setWidth100();
		panel.setHeight("90%");
		Window.enableScrolling(false);
		Window.setMargin(DEFAULT_MARGIN);

	}

	public Widget asWidget() {
		return panel;
	}

	public DataUploadStatPane getDataUploadStatPane() {
		return dataUploadStatPane;
	}

	public ControlsPane getControlsPane() {
		return controlsPane;
	}

	public DataUploadSummaryStatPane getDataUploadSummaryStatPane() {
		return dataUploadSummaryStatPane;
	}

	public TabSet getTabSet() {
		return tabSet;
	}
	
	

}