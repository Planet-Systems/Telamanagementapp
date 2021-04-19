package com.planetsystems.tela.managementapp.client.presenter.systemuser.manage;

import com.planetsystems.tela.managementapp.client.presenter.filterpaneutils.FilterRegionDistrict;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class FilterSchoolsRegionDistrictWindow extends Window {
   FilterRegionDistrict filterRegionDistrict;
	private IButton cancelButton;
	private IButton loadSchoolsButton;
	public FilterSchoolsRegionDistrictWindow() {
		super();
		
		filterRegionDistrict = new FilterRegionDistrict();
		
		
		HLayout buttonHLayout = new HLayout();
		buttonHLayout.setAlign(Alignment.CENTER);
		buttonHLayout.setMembersMargin(5);
		
	    cancelButton = new IButton("Close");        
        loadSchoolsButton = new IButton("Load");
        
        buttonHLayout.setMembers(loadSchoolsButton , cancelButton);
        
        VLayout layout = new VLayout();
        layout.setWidth100();
        layout.setHeight100();
        layout.addMembers(filterRegionDistrict , buttonHLayout);
		
		this.addItem(layout);
		this.setWidth("40%");
		this.setHeight("40%");
		this.setAutoCenter(true);
		this.setTitle("Filter Schools By Region and District");
		this.setIsModal(true);
		this.setShowModalMask(true);
		closeWindow(this);
		
	
	}

	private void closeWindow(final Window window) {
		cancelButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				window.close();
			}
		});
	}

	public FilterRegionDistrict getFilterRegionDistrict() {
		return filterRegionDistrict;
	}

	public IButton getLoadSchoolsButton() {
		return loadSchoolsButton;
	}

	
	
	
	
	
}
