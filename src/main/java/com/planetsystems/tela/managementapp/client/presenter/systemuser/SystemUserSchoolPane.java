package com.planetsystems.tela.managementapp.client.presenter.systemuser;

import com.planetsystems.tela.managementapp.client.presenter.schoolcategory.SchoolListGrid;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class SystemUserSchoolPane  extends VLayout{

	private IButton userSchoolButton;
	private IButton addButton;
	private SchoolListGrid schoolListGrid;
	
	
	public SystemUserSchoolPane() {
		super();
		
		HLayout controlButtonLayout = new HLayout();
        controlButtonLayout.setLayoutAlign(Alignment.RIGHT);
        controlButtonLayout.setAlign(Alignment.RIGHT);
        controlButtonLayout.setLayoutRightMargin(20);
        controlButtonLayout.setMembersMargin(5);
        controlButtonLayout.setAutoHeight();
        controlButtonLayout.setAutoWidth();
        
    	userSchoolButton = new IButton("User Schools");
    	addButton = new IButton("Add");
    	
    	controlButtonLayout.setMembers(userSchoolButton , addButton);
		
    	 schoolListGrid = new SchoolListGrid();
    	 schoolListGrid.setSelectionAppearance(SelectionAppearance.CHECKBOX);
    	 schoolListGrid.setSelectionType(SelectionStyle.SIMPLE);
    	 
    	
    	
    	
        this.addMember(controlButtonLayout);
		this.addMember(schoolListGrid);
        this.setHeight100();
		this.setWidth100();
	}

	

	public IButton getUserSchoolButton() {
		return userSchoolButton;
	}



	public IButton getAddButton() {
		return addButton;
	}

	public SchoolListGrid getSchoolListGrid() {
		return schoolListGrid;
	}
	
	
	
	

	
	
}
