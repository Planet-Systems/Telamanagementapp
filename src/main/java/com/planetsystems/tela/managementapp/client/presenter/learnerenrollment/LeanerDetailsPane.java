package com.planetsystems.tela.managementapp.client.presenter.learnerenrollment;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.VLayout;

public class LeanerDetailsPane extends VLayout {

	private LeanerDetailsListgrid leanerDetailsListgrid;

	public LeanerDetailsPane() {
		super();
		Label header = new Label();

		header.setStyleName("crm-ContextArea-Header-Label");
		header.setContents("Learn Details");
		header.setWidth("100%");
		header.setAutoHeight();
		header.setMargin(10);
		header.setAlign(Alignment.LEFT);

		leanerDetailsListgrid = new LeanerDetailsListgrid();

		this.addMember(leanerDetailsListgrid);
	}

	public LeanerDetailsListgrid getLeanerDetailsListgrid() {
		return leanerDetailsListgrid;
	}

}
