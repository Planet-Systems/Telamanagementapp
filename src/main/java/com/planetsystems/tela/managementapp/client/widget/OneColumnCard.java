package com.planetsystems.tela.managementapp.client.widget;

import com.smartgwt.client.widgets.HTMLFlow;

public class OneColumnCard extends HTMLFlow {

	public OneColumnCard(String colOneTtitle,String colOneSubTtitle,String colOneValue) {

		this.setWidth100();
		this.setStyleName("exampleTextBlock");
		String contents = "<div class=\"row\">\r\n"
				+ "                            <div class=\"col-md-6 col-xl-4\">\r\n"
				+ "                                <div class=\"card mb-3 widget-content\">\r\n"
				+ "                                    <div class=\"widget-content-outer\">\r\n"
				+ "                                        <div class=\"widget-content-wrapper\">\r\n"
				+ "                                            <div class=\"widget-content-left\">\r\n"
				+ "                                                <div class=\"widget-heading\">"+colOneTtitle+"</div>\r\n"
				+ "                                                <div class=\"widget-subheading\">"+colOneSubTtitle+"</div>\r\n"
				+ "                                            </div>\r\n"
				+ "                                            <div class=\"widget-content-right\">\r\n"
				+ "                                                <div class=\"widget-numbers text-success\">"+colOneValue+"</div>\r\n"
				+ "                                            </div>\r\n"
				+ "                                        </div>\r\n"
				+ "                                    </div>\r\n" + "                                </div>\r\n"
				+ "                            </div>\r\n"  
				+ "                            <div class=\"d-xl-none d-lg-block col-md-6 col-xl-4\">\r\n"
				+ "                                <div class=\"card mb-3 widget-content\">\r\n"
				+ "                                    <div class=\"widget-content-outer\">\r\n"
				+ "                                        <div class=\"widget-content-wrapper\">\r\n"
				+ "                                            <div class=\"widget-content-left\">\r\n"
				+ "                                                <div class=\"widget-heading\">Income</div>\r\n"
				+ "                                                <div class=\"widget-subheading\">Expected totals</div>\r\n"
				+ "                                            </div>\r\n"
				+ "                                            <div class=\"widget-content-right\">\r\n"
				+ "                                                <div class=\"widget-numbers text-focus\">$147</div>\r\n"
				+ "                                            </div>\r\n"
				+ "                                        </div>\r\n"
				+ "                                        <div class=\"widget-progress-wrapper\">\r\n"
				+ "                                            <div class=\"progress-bar-sm progress-bar-animated-alt progress\">\r\n"
				+ "                                                <div class=\"progress-bar bg-info\" role=\"progressbar\" aria-valuenow=\"54\" aria-valuemin=\"0\" aria-valuemax=\"100\" style=\"width: 54%;\"></div>\r\n"
				+ "                                            </div>\r\n"
				+ "                                            <div class=\"progress-sub-label\">\r\n"
				+ "                                                <div class=\"sub-label-left\">Expenses</div>\r\n"
				+ "                                                <div class=\"sub-label-right\">100%</div>\r\n"
				+ "                                            </div>\r\n"
				+ "                                        </div>\r\n"
				+ "                                    </div>\r\n" + "                                </div>\r\n"
				+ "                            </div>\r\n" + "                        </div>";
		this.setContents(contents);
	}

}
