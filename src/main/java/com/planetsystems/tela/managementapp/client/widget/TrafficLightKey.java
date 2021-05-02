package com.planetsystems.tela.managementapp.client.widget;

import com.smartgwt.client.widgets.HTMLFlow;

public class TrafficLightKey extends HTMLFlow {

	public TrafficLightKey(String color, String text) {
		this.setAutoWidth();
		this.setStyleName("exampleTextBlock");
		String contents = "<div style='width:100px'>"
				+ "<div style='padding:20;width:70px;height:10px;float:left;background-color:" + color
				+ "'></div> <div style='width:100px;float:left;margin-left:10px;font-weight:bold;'>" + text + "</div></div>";
		this.setContents(contents);
	}

}
