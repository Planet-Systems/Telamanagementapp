package com.planetsystems.tela.managementapp.client.presenter.devicemanager;

import javax.inject.Inject;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.smartgwt.client.widgets.layout.VLayout;

class DeviceManagerView extends ViewImpl implements DeviceManagerPresenter.MyView {

	private static final String DEFAULT_MARGIN = "0px";
	private VLayout panel;
	private DeviceManagerPane deviceManagerPane;

	@Inject
	DeviceManagerView() {
		panel = new VLayout();
		deviceManagerPane = new DeviceManagerPane();

		panel.addMember(deviceManagerPane);
		panel.setWidth100();
		panel.setHeight("90%");
		Window.enableScrolling(false);
		Window.setMargin(DEFAULT_MARGIN);

	}

	public Widget asWidget() {
		return panel;
	}

}