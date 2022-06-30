package com.planetsystems.tela.managementapp.client.presenter.schoolcategory.schoolclass;

import com.planetsystems.tela.managementapp.client.presenter.filterpaneutils.FilterYearTermDistrictSchool;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class FilterSchoolClassWindow extends Window {

	private IButton filterButton;
	private IButton cancelButton;
	private FilterYearTermDistrictSchool filterYearTermDistrictSchool;

	public FilterSchoolClassWindow() {
		super();

		filterYearTermDistrictSchool = new FilterYearTermDistrictSchool();
		filterYearTermDistrictSchool.getForm().setNumCols(2);

		cancelButton = new IButton("Close");
		cancelButton.setBaseStyle("cancel-button");

		filterButton = new IButton("Filter");

		HLayout buttonLayout = new HLayout();
		buttonLayout.setMembers(cancelButton, filterButton);
		buttonLayout.setAutoHeight();
		buttonLayout.setAutoWidth();
		buttonLayout.setMargin(5);
		buttonLayout.setMembersMargin(4);
		buttonLayout.setLayoutAlign(Alignment.CENTER);

		VLayout layout = new VLayout();
		layout.addMember(filterYearTermDistrictSchool);
		layout.addMember(buttonLayout);

		layout.setMargin(10);
		this.addItem(layout);
		this.setWidth("40%");
		this.setHeight("40%");
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

	public FilterYearTermDistrictSchool getFilterYearTermDistrictSchool() {
		return filterYearTermDistrictSchool;
	}

	public IButton getFilterButton() {
		return filterButton;
	}

}
