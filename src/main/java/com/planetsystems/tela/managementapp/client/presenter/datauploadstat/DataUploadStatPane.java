package com.planetsystems.tela.managementapp.client.presenter.datauploadstat;

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.layout.VLayout;

public class DataUploadStatPane extends VLayout {

	private DataUploadStatListgrid listgrid;

	public DataUploadStatPane() {
		super();

		listgrid = new DataUploadStatListgrid();
 
		VLayout layout = new VLayout();
		layout.addMember(listgrid);

		layout.setWidth100();
		layout.setHeight100();
		this.addMember(layout);
		this.setOverflow(Overflow.AUTO);

	}

	public DataUploadStatListgrid getListgrid() {
		return listgrid;
	}
	
	

}
