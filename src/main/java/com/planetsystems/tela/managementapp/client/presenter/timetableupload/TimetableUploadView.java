package com.planetsystems.tela.managementapp.client.presenter.timetableupload;

import javax.inject.Inject;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl; 
import com.smartgwt.client.widgets.layout.VLayout;

public class TimetableUploadView extends ViewImpl implements TimetableUploadPresenter.MyView {

	private Header header;
	private VLayout panel;
	private TimetableUploadHomePane timetableUploadHomePane;
	private static final String DEFAULT_MARGIN = "0px";
	
	private VLayout mainLayout;

	@Inject
	TimetableUploadView() {
		
		panel = new VLayout();
		header = new Header();
		timetableUploadHomePane = new TimetableUploadHomePane();

		mainLayout=new VLayout();
		mainLayout.setMembers(timetableUploadHomePane);
		
		panel.addMember(header);
		panel.addMember(mainLayout);
		panel.setWidth100();
		panel.setHeight("99%");
		panel.setBackgroundColor("#f0f0f0");
		Window.enableScrolling(false);
		Window.setMargin(DEFAULT_MARGIN);
	}

	public Widget asWidget() {
		return panel;
	}

	public TimetableUploadHomePane getTimetableUploadHomePane() {
		return timetableUploadHomePane;
	}

	public VLayout getMainLayout() {
		return mainLayout;
	}

	 
	
	

}