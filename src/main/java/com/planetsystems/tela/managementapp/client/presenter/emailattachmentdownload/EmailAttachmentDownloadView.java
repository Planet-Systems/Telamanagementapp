package com.planetsystems.tela.managementapp.client.presenter.emailattachmentdownload;

import javax.inject.Inject;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.smartgwt.client.widgets.layout.VLayout;

class EmailAttachmentDownloadView extends ViewImpl implements EmailAttachmentDownloadPresenter.MyView {
	private static final String DEFAULT_MARGIN = "0px";
	
	private VLayout panel;
	private EmailAttachmentDownloadPane emailAttachmentDownloadPane; 
	private ControlsPane controlsPane;
	  
    @Inject
    EmailAttachmentDownloadView() {
    	panel = new VLayout();
    	emailAttachmentDownloadPane = new EmailAttachmentDownloadPane(); 
    	controlsPane = new ControlsPane();
    	  
    	panel.addMember(controlsPane);
    	panel.addMember(emailAttachmentDownloadPane);
    	panel.setWidth100();
		panel.setHeight("90%");
		Window.enableScrolling(false);
		Window.setMargin(DEFAULT_MARGIN);
      
    }
    
    public Widget asWidget() {
		return panel;
	}

	public static String getDefaultMargin() {
		return DEFAULT_MARGIN;
	}

	public EmailAttachmentDownloadPane getEmailAttachmentDownloadPane() {
		return emailAttachmentDownloadPane;
	}

	public ControlsPane getControlsPane() {
		return controlsPane;
	}
    
    

}