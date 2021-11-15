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
	private IButton saveButton;
	public FilterSchoolsRegionDistrictWindow() {
		super();
		
		filterRegionDistrict = new FilterRegionDistrict();
		
		
		HLayout buttonHLayout = new HLayout();
		buttonHLayout.setAlign(Alignment.CENTER);
		buttonHLayout.setMembersMargin(5);
		
	    cancelButton = new IButton("Cancel");
		cancelButton.setBaseStyle("cancel-button");
		
	    saveButton = new IButton("Assign");
        
        buttonHLayout.setMembers(cancelButton,saveButton);
        
        VLayout layout = new VLayout();
        layout.setWidth100();
        layout.setHeight100();
        layout.addMembers(filterRegionDistrict , buttonHLayout);
		
		this.addItem(layout);
		this.setWidth("40%");
		this.setHeight("40%");
		this.setAutoCenter(true);
		this.setTitle("Assign District to User");
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

	public IButton getCancelButton() {
		return cancelButton;
	}

	public IButton getSaveButton() {
		return saveButton;
	}
 
	
	
	
}
