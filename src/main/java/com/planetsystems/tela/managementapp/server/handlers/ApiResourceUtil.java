package com.planetsystems.tela.managementapp.server.handlers;

import com.planetsystems.tela.managementapp.server.APIGateWay;

public interface ApiResourceUtil {
	final String API_LINK = APIGateWay.getInstance().getApLink();
	final String REPORT_GEN_API = APIGateWay.getInstance().getReportGeneratorLink();
}
