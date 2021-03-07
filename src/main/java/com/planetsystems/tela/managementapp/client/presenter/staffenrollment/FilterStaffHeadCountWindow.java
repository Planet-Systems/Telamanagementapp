package com.planetsystems.tela.managementapp.client.presenter.staffenrollment;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class FilterStaffHeadCountWindow extends Window {
	
	private IButton filterButton;
	private IButton cancelButton;
	private FilterStaffHeadCountPane filterStaffHeadCountPane;
	
	public static final String DISTRICT_ID = "DISTRICT_ID";
	public static final String SCHOOL_ID = "SCHOOL_ID";
	public static final String ACADEMIC_YEAR_ID = "ACADEMIC_YEAR_ID";
	public static final String ACADEMIC_TERM_ID = "ACADEMIC_TERM_ID";

	public FilterStaffHeadCountWindow() {
		super();
		
		filterStaffHeadCountPane = new FilterStaffHeadCountPane();
		
		cancelButton = new IButton("Close");
		cancelButton.setBaseStyle("cancel-button");
		
		filterButton = new IButton("Filter");
		filterButton.disable();
		
		HLayout buttonLayout = new HLayout();
		buttonLayout.setMembers(cancelButton , filterButton);
		buttonLayout.setAutoHeight();
		buttonLayout.setAutoWidth();
		buttonLayout.setMargin(5);
		buttonLayout.setMembersMargin(4);
		buttonLayout.setLayoutAlign(Alignment.CENTER);

		VLayout layout = new VLayout();
		layout.addMember(filterStaffHeadCountPane);
		layout.addMember(buttonLayout);

		layout.setMargin(10);
		this.addItem(layout);
		this.setWidth("40%");
		this.setHeight("60%");
		this.setAutoCenter(true);
		this.setTitle("Filter Classes");
		this.setIsModal(true);
		this.setShowModalMask(true);
		cancel(this);
	}

	private void cancel(final Window window) {
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

	

	public IButton getFilterButton() {
		return filterButton;
	}

	public FilterStaffHeadCountPane getFilterStaffHeadCountPane() {
		return filterStaffHeadCountPane;
	}


	
	
	
	
}
