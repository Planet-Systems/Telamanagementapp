package com.planetsystems.tela.managementapp.client.presenter.devicemanager;

import com.smartgwt.client.types.ContentsType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.HTMLPane;
import com.smartgwt.client.widgets.layout.VLayout;

public class DeviceManagerPane extends VLayout {

	public DeviceManagerPane() {
		super();
		//Frame frame = new Frame("https://headwind.telaschool.org/");
		//frame.setPixelSize(1250, 600);
		//frame.setHeight("100%");
		//frame.setWidth("100%");
		
		
		final HTMLPane htmlPane = new HTMLPane();  
        htmlPane.setShowEdges(true);  
        htmlPane.setContentsURL("https://headwind.telaschool.org/#/applications");  
        //htmlPane.setContentsURL("https://github.com/");  
        htmlPane.setContentsType(ContentsType.PAGE); 
        
		VLayout layout = new VLayout();
		layout.addMember(htmlPane);
		layout.setWidth100();  
        layout.setHeight100();  
		this.addMember(layout);
		this.setOverflow(Overflow.AUTO);

	}

}
