package com.planetsystems.tela.managementapp.client.presenter.academicyear.term;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class FilterAcademicTermWindow extends Window {
	
	private IButton cancelButton;
	private FilterAcademicTermsPane filterAcademicTermsPane;

	public FilterAcademicTermWindow() {
		super();
		
		filterAcademicTermsPane = new FilterAcademicTermsPane();
		
		cancelButton = new IButton("Close");
		cancelButton.setBaseStyle("cancel-button");
		
		HLayout buttonLayout = new HLayout();
		buttonLayout.setMembers(cancelButton);
		buttonLayout.setAutoHeight();
		buttonLayout.setAutoWidth();
		buttonLayout.setMargin(5);
		buttonLayout.setMembersMargin(4);
		buttonLayout.setLayoutAlign(Alignment.CENTER);

		VLayout layout = new VLayout();
		layout.addMember(filterAcademicTermsPane);
		layout.addMember(buttonLayout);

		layout.setMargin(10);
		this.addItem(layout);
		this.setWidth("40%");
		this.setHeight("30%");
		this.setAutoCenter(true);
		this.setTitle("Filter Academic years");
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

	public FilterAcademicTermsPane getFilterAcademicTermsPane() {
		return filterAcademicTermsPane;
	}

	
	
	
}
