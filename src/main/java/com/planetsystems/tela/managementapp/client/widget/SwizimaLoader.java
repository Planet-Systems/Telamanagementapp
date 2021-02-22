package com.planetsystems.tela.managementapp.client.widget;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Dialog;
import com.smartgwt.client.widgets.Img;

public class SwizimaLoader extends Dialog {

	
	public SwizimaLoader() {
		super();
		
		//this.setWidth(120);
		//this.setHeight(70);
		

		Img image = new Img("Spinner_16.gif", 64, 64);
		image.setAlign(Alignment.CENTER);

		this.addItem(image);
		this.setMargin(0);
		this.setAlign(Alignment.CENTER); 
		this.setAutoCenter(true);
		this.setShowTitle(false);
		this.setShowCloseButton(false);
		this.setShowMaximizeButton(false);
		this.setMargin(0);
		this.setShowHeader(false);
		this.setAutoHeight();
		this.setAutoWidth();

	}
}
