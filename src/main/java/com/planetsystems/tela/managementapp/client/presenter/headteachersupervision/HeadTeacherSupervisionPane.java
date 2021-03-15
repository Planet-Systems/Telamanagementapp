package com.planetsystems.tela.managementapp.client.presenter.headteachersupervision;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.VLayout;

public class HeadTeacherSupervisionPane extends VLayout {


	public HeadTeacherSupervisionPane() {
		super();
		Label header = new Label();

		header.setStyleName("crm-ContextArea-Header-Label");
		header.setContents("SuperVisions");
		header.setWidth("100%");
		header.setAutoHeight();
		header.setMargin(10);
		header.setAlign(Alignment.LEFT);


		VLayout layout = new VLayout();
		layout.addMember(header);
		this.addMember(layout);

	}


}
