package com.planetsystems.tela.managementapp.client.presenter.region;

import javax.inject.Inject;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

class RegionView extends ViewImpl implements RegionPresenter.MyView {
	private static final String DEFAULT_MARGIN = "0px";
	private VLayout panel;
	private RegionPane regionPane;
	private DistrictPane districtPane;
	private ControlsPane controlsPane;
	private TabSet tabSet;
	
	public static final String REGION_TAB_TITLE="Regions";
	public static final String DISTRICT_TAB_TITLE="Districts";

    @Inject
    RegionView() {
    	panel = new VLayout();
    	regionPane = new RegionPane();
    	districtPane = new DistrictPane();
    	controlsPane = new ControlsPane();
    	tabSet = new TabSet();
    	
    	Tab regionTab = new Tab();
    	regionTab.setTitle(REGION_TAB_TITLE);
    	regionTab.setPane(regionPane);
    	
    	Tab districtTab = new Tab();
    	districtTab.setTitle(DISTRICT_TAB_TITLE);
    	districtTab.setPane(districtPane);
    
    	tabSet.addTab(districtTab);
    	tabSet.addTab(regionTab);
    
    	
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

	public ControlsPane getControlsPane() {
		return controlsPane;
	}

	public TabSet getTabSet() {
		return tabSet;
	}

	public RegionPane getRegionPane() {
		return regionPane;
	}

	public DistrictPane getDistrictPane() {
		return districtPane;
	}
    
	
    
    
}