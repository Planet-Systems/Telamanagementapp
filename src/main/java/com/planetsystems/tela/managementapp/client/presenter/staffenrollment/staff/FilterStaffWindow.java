package com.planetsystems.tela.managementapp.client.presenter.staffenrollment.staff;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class FilterStaffWindow extends Window {
	
	private IButton filterButton;
	private IButton cancelButton;
	private FilterStaffsPane filterStaffsPane;

	public FilterStaffWindow() {
		super();
		
		filterStaffsPane = new FilterStaffsPane();
		
		cancelButton = new IButton("Close");
		cancelButton.setBaseStyle("cancel-button");
		
		filterButton = new IButton("Ok");
		
		
		HLayout buttonLayout = new HLayout();
		buttonLayout.setMembers(cancelButton , filterButton);
		buttonLayout.setAutoHeight();
		buttonLayout.setAutoWidth();
		buttonLayout.setMargin(5);
		buttonLayout.setMembersMargin(4);
		buttonLayout.setLayoutAlign(Alignment.CENTER);

		VLayout layout = new VLayout();
		layout.addMember(filterStaffsPane);
		layout.addMember(buttonLayout);

		layout.setMargin(10);
		this.addItem(layout);
		this.setWidth("30%");
		this.setHeight("30%");
		this.setAutoCenter(true);
		this.setTitle("Advanced Filter");
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

	public FilterStaffsPane getFilterStaffsPane() {
		return filterStaffsPane;
	}

	
	
	
	
}
