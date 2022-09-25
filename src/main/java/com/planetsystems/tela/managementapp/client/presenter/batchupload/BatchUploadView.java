package com.planetsystems.tela.managementapp.client.presenter.batchupload;

import javax.inject.Inject;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.smartgwt.client.widgets.layout.VLayout;

class BatchUploadView extends ViewImpl implements BatchUploadPresenter.MyView {

	private static final String DEFAULT_MARGIN = "0px";
	private VLayout panel;
	private BatchUploadPane batchUploadPane;
	
	private ControlsPane controlsPane;

	@Inject
	BatchUploadView() {
		panel = new VLayout();
		batchUploadPane = new BatchUploadPane();
		
		controlsPane = new ControlsPane();
  
		panel.addMember(controlsPane);
		panel.addMember(batchUploadPane);
		panel.setWidth100();
		panel.setHeight("90%");
		Window.enableScrolling(false);
		Window.setMargin(DEFAULT_MARGIN);

	}

	public Widget asWidget() {
		return panel;
	}

	public BatchUploadPane getBatchUploadPane() {
		return batchUploadPane;
	}

	public ControlsPane getControlsPane() {
		return controlsPane;
	}
	
	
	
	
}