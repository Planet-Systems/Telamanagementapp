package com.planetsystems.tela.managementapp.client.widget;

import com.google.gwt.user.client.ui.Frame;
import com.smartgwt.client.types.ContentsType;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.HTMLPane;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.layout.VLayout;

public class PreviewReportWindow extends Window{
	
	public PreviewReportWindow(String url,String title){		
		super(); 
		
		final HTMLPane htmlPane = new HTMLPane();  
        htmlPane.setShowEdges(true);  
        htmlPane.setContentsURL(url);     
        htmlPane.setContentsType(ContentsType.PAGE); 
        //htmlPane.setContents(contents);
         
        
		VLayout layout = new VLayout();
		layout.addMember(htmlPane);
		layout.setWidth100();  
        layout.setHeight100();  
		this.addMember(layout);
		this.setOverflow(Overflow.AUTO);
		 
		this.setWidth("95%");
		this.setHeight("95%");
		this.setAutoCenter(true);
		this.setPadding(50);
		this.setTitle(title); 
		this.addItem(layout);
		this.setIsModal(true);
		this.setShowModalMask(true);
		
	}

}
