package com.planetsystems.tela.managementapp.client.widget;

import com.google.gwt.user.client.ui.Frame;
import com.smartgwt.client.widgets.Window;

public class ReportDisplayWindow extends Window{
	public ReportDisplayWindow(String url,String title){		
		super();
		Frame frame=new Frame(url);
		this.setWidth("95%");
		this.setHeight("95%");
		this.setAutoCenter(true);
		this.setPadding(50);
		this.setTitle(title);
		//item.setMargin(15);
		frame.setPixelSize(1250, 600);
		this.addItem(frame);
		this.setIsModal(true);
		this.setShowModalMask(true);
		
	}
}
