package com.planetsystems.tela.managementapp.client.presenter.dashboard;

import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout; 

public class DashboarTestWindow extends Window {

   
	private VLayout layout;

	public DashboarTestWindow() {
		super();
 

		layout=new VLayout();
		 
		HLayout buttonLayout = new HLayout();
		buttonLayout.setMembers(layout);
		buttonLayout.setAutoHeight();
		buttonLayout.setWidth100();
		buttonLayout.setMargin(5);
		buttonLayout.setMembersMargin(4);

		VLayout layout = new VLayout(); 
		layout.addMember(buttonLayout);

		layout.setMargin(10);
		this.addItem(layout);
		this.setWidth("90%");
		this.setHeight("90%");
		this.setAutoCenter(true);
		this.setTitle("Advanced View");
		this.setIsModal(true);
		this.setShowModalMask(true);
	}

	public VLayout getLayout() {
		return layout;
	} 

	 

}
