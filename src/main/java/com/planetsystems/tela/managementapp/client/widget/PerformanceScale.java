package com.planetsystems.tela.managementapp.client.widget;
 
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class PerformanceScale extends VLayout {

	public PerformanceScale() {
		super();

		Label keyheader = new Label();
		keyheader.setStyleName("crm-ContextArea-Header-Label");
		keyheader.setContents("KEY");
		keyheader.setWidth("100%");
		keyheader.setAutoHeight();
		keyheader.setMargin(2);
		keyheader.setAlign(Alignment.LEFT);

		HLayout key = new HLayout();
		key.setMembers(new TrafficLightKey("#5eb96d", "Above 95%"), new TrafficLightKey("#fcb143", "94-75%"),
				new TrafficLightKey("#c20215", "74% below"));
		key.setAutoHeight();
		key.setWidth100();
		key.setMargin(2);
		key.setMembersMargin(2);

		VLayout legend = new VLayout();
		legend.addMember(keyheader);
		legend.addMember(key);
		legend.setAutoHeight();

		this.addMember(legend);
		this.setAutoHeight();
	}

}
