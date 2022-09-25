package com.planetsystems.tela.managementapp.client.presenter.batchupload;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

public class BatchUploadPane extends VLayout {

	// private IButton uploadLicenses;
	// private IButton uploadSchoolLicenses;

	private TelaLicenseKeyListgrid telaLicenseKeyListgrid;
	private TelaSchoolLicenseListgrid telaSchoolLicenseListgrid;
	private TabSet tabSet;

	public BatchUploadPane() {
		super();

		// uploadLicenses = new IButton();
		// uploadLicenses.setTitle("Upload Licenses");

		// uploadSchoolLicenses = new IButton();
		// uploadSchoolLicenses.setTitle("Upload School Licenses");

		telaLicenseKeyListgrid = new TelaLicenseKeyListgrid();
		telaSchoolLicenseListgrid = new TelaSchoolLicenseListgrid();

		tabSet = new TabSet();

		Tab tab1 = new Tab();
		tab1.setTitle("Tela License Keys");
		tab1.setPane(telaLicenseKeyListgrid);

		Tab tab2 = new Tab();
		tab2.setTitle("Schools Assigned with Tela License Keys");
		tab2.setPane(telaSchoolLicenseListgrid);

		tabSet.addTab(tab1);
		tabSet.addTab(tab2);

		HLayout buttonLayout = new HLayout();
		// buttonLayout.setMembers(uploadLicenses, uploadSchoolLicenses);
		buttonLayout.setAutoHeight();
		buttonLayout.setAutoWidth();
		buttonLayout.setMargin(5);
		buttonLayout.setMembersMargin(4);

		buttonLayout.setLayoutAlign(Alignment.CENTER);

		VLayout layout = new VLayout();
		layout.addMember(buttonLayout);
		layout.addMember(tabSet);

		layout.setWidth100();
		layout.setHeight100();
		this.addMember(layout);
		this.setOverflow(Overflow.AUTO);

	}

	public TelaLicenseKeyListgrid getTelaLicenseKeyListgrid() {
		return telaLicenseKeyListgrid;
	}

	public void setTelaLicenseKeyListgrid(TelaLicenseKeyListgrid telaLicenseKeyListgrid) {
		this.telaLicenseKeyListgrid = telaLicenseKeyListgrid;
	}

	public TelaSchoolLicenseListgrid getTelaSchoolLicenseListgrid() {
		return telaSchoolLicenseListgrid;
	}

	public void setTelaSchoolLicenseListgrid(TelaSchoolLicenseListgrid telaSchoolLicenseListgrid) {
		this.telaSchoolLicenseListgrid = telaSchoolLicenseListgrid;
	}

	public TabSet getTabSet() {
		return tabSet;
	}

	public void setTabSet(TabSet tabSet) {
		this.tabSet = tabSet;
	}

}
