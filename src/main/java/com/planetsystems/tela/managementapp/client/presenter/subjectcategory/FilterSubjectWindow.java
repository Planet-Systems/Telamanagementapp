package com.planetsystems.tela.managementapp.client.presenter.subjectcategory;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class FilterSubjectWindow extends Window {
	
	private IButton saveButton;
	private IButton cancelButton;
	private FilterSubjectsPane filterSubjectsPane;

	public FilterSubjectWindow() {
		super();
		
		filterSubjectsPane = new FilterSubjectsPane();
		
		cancelButton = new IButton("Close");
		cancelButton.setBaseStyle("cancel-button");
		
		saveButton = new IButton("Save");
		
		HLayout buttonLayout = new HLayout();
		buttonLayout.setMembers(cancelButton);
		buttonLayout.setAutoHeight();
		buttonLayout.setAutoWidth();
		buttonLayout.setMargin(5);
		buttonLayout.setMembersMargin(4);
		buttonLayout.setLayoutAlign(Alignment.CENTER);

		VLayout layout = new VLayout();
		layout.addMember(filterSubjectsPane);
		layout.addMember(buttonLayout);

		layout.setMargin(10);
		this.addItem(layout);
		this.setWidth("40%");
		this.setHeight("30%");
		this.setAutoCenter(true);
		this.setTitle("Filter Subjects");
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

	public IButton getSaveButton() {
		return saveButton;
	}

	public IButton getCancelButton() {
		return cancelButton;
	}

	public FilterSubjectsPane getFilterSubjectsPane() {
		return filterSubjectsPane;
	}

	
	
	
}
