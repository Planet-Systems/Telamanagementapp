package com.planetsystems.tela.managementapp.client.presenter.academicyear.schoolCalendar;
 
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.VLayout;

public class SchoolCalendarPane extends VLayout {

	private SchoolCalendarListgrid listGrid;

	public SchoolCalendarPane() {
		super();
		Label header = new Label();
		this.setWidth100();

		header.setStyleName("crm-ContextArea-Header-Label");
		header.setContents("School Calendar Configuration");
		header.setWidth("100%");
		header.setAutoHeight();
		header.setMargin(10);
		header.setAlign(Alignment.LEFT);

		listGrid = new SchoolCalendarListgrid();

		VLayout layout = new VLayout();
		// layout.addMember(header);
		layout.addMember(listGrid);
		this.addMember(layout);
	}

	public SchoolCalendarListgrid getListGrid() {
		return listGrid;
	}
	
	

}
