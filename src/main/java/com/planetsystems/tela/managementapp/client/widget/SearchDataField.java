package com.planetsystems.tela.managementapp.client.widget;

import com.smartgwt.client.widgets.HTMLFlow;

public class SearchDataField extends HTMLFlow {

	public SearchDataField(String title, String value) {
		// Label data = new Label();
		// data.setAutoHeight();

		// data.setContents("<span style='font-weight: bold; font-size: 12px;'>" + title
		// + ": </span>"+ "<span style='font-weight: bold; font-size: 11px;'>" + value +
		// ": </span>");

		this.setStyleName("exampleTextBlock");
		this.setContents("<span style='font-weight: bold; font-size: 12px;'>" + title + ": </span>"
				+ "<span style='font-weight: bold; font-size: 12px; color:#929190'>" + value + "</span>");
	}

}
