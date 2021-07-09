package com.planetsystems.tela.managementapp.client.presenter.systemuser.manage;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class SelectProfileSchoolWindow extends Window{
	private IButton cancelButton;
	private IButton addSchoolsButton;
	private SelectProfileSchoolListGrid selectProfileSchoolListGrid;
	public SelectProfileSchoolWindow() {
		super();
		selectProfileSchoolListGrid = new SelectProfileSchoolListGrid();
		selectProfileSchoolListGrid.setSelectionAppearance(SelectionAppearance.CHECKBOX);
		selectProfileSchoolListGrid.setSelectionType(SelectionStyle.SIMPLE);
		selectProfileSchoolListGrid.setShowFilterEditor(true);
		selectProfileSchoolListGrid.setHeight("90%");
		
		HLayout buttonHLayout = new HLayout();
		buttonHLayout.setAlign(Alignment.CENTER);
		buttonHLayout.setMembersMargin(5);
		
	    cancelButton = new IButton("Close");        
	    addSchoolsButton = new IButton("Add");
	    addSchoolsButton.disable();
        
        buttonHLayout.setMembers(addSchoolsButton , cancelButton);
        
        VLayout layout = new VLayout();
        layout.setWidth100();
        layout.setHeight100();
        layout.addMembers(selectProfileSchoolListGrid , buttonHLayout);
		

		this.addItem(layout);
		this.setWidth("50%");
		this.setHeight("60%");
		this.setAutoCenter(true);
		this.setTitle("Select Profile Schools");
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


	public IButton getCancelButton() {
		return cancelButton;
	}

	

	public IButton getAddSchoolsButton() {
		return addSchoolsButton;
	}


	public SelectProfileSchoolListGrid getSelectProfileSchoolListGrid() {
		return selectProfileSchoolListGrid;
	}


	


	


	

	
	

}
