package com.planetsystems.tela.managementapp.client.widget;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Dialog;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.VLayout;

public class SwizimaLoader extends Dialog {

	
	public SwizimaLoader() {
		super();
		 
		Img image = new Img("Spinner_16.gif", 64, 64);
		image.setAlign(Alignment.CENTER);
  
		Label message = new Label();
		message.setContents("Please wait..");
		message.setHeight(4);
		message.setAlign(Alignment.CENTER);

		VLayout layout = new VLayout();
		layout.addMember(image);
		layout.addMember(message);
		layout.setAutoHeight();
  
		this.addItem(layout);  
		this.setAlign(Alignment.CENTER); 
		this.setAutoCenter(true);
		this.setShowTitle(false);
		this.setShowCloseButton(false);
		this.setShowMaximizeButton(false); 
		this.setShowHeader(false);
		this.setWidth(100);
		this.setHeight(40);
		this.setMargin(0);

	}
}
