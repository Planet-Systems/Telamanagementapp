package com.planetsystems.tela.managementapp.client.gin;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.gwtplatform.mvp.client.annotations.DefaultGatekeeper;
import com.gwtplatform.mvp.client.proxy.Gatekeeper;
import com.planetsystems.tela.managementapp.shared.RequestConstant;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;

@DefaultGatekeeper
public class LoggedInGatekeeper implements Gatekeeper {

	private static final String densityCookieName = "density_name_selected";
	private static final String skinCookieName = "skin_name_2_4";

	@Override
	public boolean canReveal() {
		// com.google.gwt.core.shared.GWT
		// String host = GWT.getHostPageBaseURL();
		String host = Window.Location.getHostName();
		Cookies.setCookie(RequestConstant.TENANT_URL, host);

		// SC.say("Host:::: "+host);

		GWT.log("Host::: " + host);

		if (Cookies.isCookieEnabled()) {
			String logedIn = Cookies.getCookie(RequestConstant.LOGED_IN);
			if (logedIn != null) {
				if (logedIn.equalsIgnoreCase("true")) { 
					//Canvas.resizeFonts(1);
					Canvas.resizeControls(1);

					//GWT.log("skinCookieName::: " + Cookies.getCookie(skinCookieName));
					return true;
				}
			}

		} else {
			SC.say("Please Enable your cookies to use to proceed");
		}
		return false;
	}

}
