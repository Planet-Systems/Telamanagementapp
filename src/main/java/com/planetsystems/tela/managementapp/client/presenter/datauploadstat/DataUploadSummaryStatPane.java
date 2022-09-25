package com.planetsystems.tela.managementapp.client.presenter.datauploadstat;

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.layout.VLayout;

public class DataUploadSummaryStatPane extends VLayout {

	private DataUploadStatSummaryListgrid listgrid;

	public DataUploadSummaryStatPane() {
		super();

		listgrid = new DataUploadStatSummaryListgrid();

		VLayout layout = new VLayout();
		layout.addMember(listgrid);

		layout.setWidth100();
		layout.setHeight100();
		this.addMember(layout);
		this.setOverflow(Overflow.AUTO);

	}

	public DataUploadStatSummaryListgrid getListgrid() {
		return listgrid;
	}

}
