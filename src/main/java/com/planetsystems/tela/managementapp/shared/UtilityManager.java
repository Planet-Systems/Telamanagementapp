package com.planetsystems.tela.managementapp.shared;

import java.util.List;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Window; 

public class UtilityManager {

	private static UtilityManager instance = new UtilityManager();
	
	//private String format="#,##0";

	private UtilityManager() {

	}

	public static UtilityManager getInstance() {
		return instance;
	}

	public String formatCash(float cash) {
		String formatedCash = null;
		NumberFormat numberFormat = NumberFormat.getFormat("#,##0");
		formatedCash = numberFormat.format(cash);
		return formatedCash;
	}

	public String formatCash(int cash) {
		String formatedCash = null;
		NumberFormat numberFormat = NumberFormat.getFormat("#,##0");
		formatedCash = numberFormat.format(cash);
		return formatedCash;
	}

	public String formatCash(Double cash) {
		String formatedCash = null;
		NumberFormat numberFormat = NumberFormat.getFormat("#,##0");
		formatedCash = numberFormat.format(cash);
		return formatedCash;
	}

	public String formatCash(long cash) {
		String formatedCash = null;
		NumberFormat numberFormat = NumberFormat.getFormat("#,##0");
		formatedCash = numberFormat.format(cash);
		return formatedCash;
	}

	public String unformatCash(String cash) {
		String unformatedCash = null;
		try {
			unformatedCash = cash.replaceAll(",", "");
		} catch (Exception e) {
		}
		return unformatedCash;
	}
	
	public String unformatFloatCash(String cash) {
		String unformatedCash = null;
		try {
			unformatedCash = cash.replaceAll(",", "");
		} catch (Exception e) {
		}
		return unformatedCash;
	}

	public boolean isValidInput(String input) {
		if (input != null) {
			if (!(input.replace(" ", "")).isEmpty()) {
				return true;
			}
		}
		return false;
	}

	public boolean isValidInputs(List<String> inputs) {
		boolean output = false;
		if (inputs != null) {
			for (String input : inputs) {
				if (input != null) {
					if (!(input.replace(" ", "")).isEmpty()) {
						output = true;
					} else {
						return false;
					}
				} else {
					return false;
				}

			}

		}
		return output;
	}

	public boolean isLong(String number) {
		try {
			Long.parseLong(number);
			return true;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return false;
	}

	public boolean isInteger(String number) {
		try {
			Integer.parseInt(number);
			return true;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return false;
	}

	public boolean isFloat(String number) {
		try {
			Float.parseFloat(number);
			return true;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return false;
	}
	
	public  String getFileExtension(String fileName) {
		if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		else
			return "";
	}
	
	public void download(final String downloadLink) {

		String NAME = "_self";
		String FEATURES = "width=760, height=480";

		StringBuilder url = new StringBuilder();
		url.append(downloadLink);
		Window.open(url.toString(), NAME, FEATURES);
	}
	
	/*public  void preview(String url,String tittle) {

		ReportDisplayWindow window = new ReportDisplayWindow(url, tittle);
		window.show();

	}*/
	 

}
