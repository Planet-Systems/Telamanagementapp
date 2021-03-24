package com.planetsystems.tela.managementapp.client.widget;

import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.grid.ListGrid;



public class SuperListGrid extends ListGrid{
	public SuperListGrid(){
		super();
		
		
		this.setFilterOnKeypress(true);
		this.setShowFilterEditor(true);
		this.setFetchDelay(500);
		this.setSelectionAppearance(SelectionAppearance.CHECKBOX);
		this.setSelectionType(SelectionStyle.SINGLE);
		this.setWrapCells(false);
		this.setFixedRecordHeights(true);
		this.setMargin(3);
		this.setShowRowNumbers(true);
		
	}
	

}
