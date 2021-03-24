package com.planetsystems.tela.managementapp.client.presenter.academicyear.gridutil;

import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.planetsystems.tela.managementapp.client.gin.SessionManager;
import com.planetsystems.tela.managementapp.client.presenter.academicyear.AcademicTermListGrid;
import com.planetsystems.tela.managementapp.client.presenter.academicyear.AcademicYearListGrid;
import com.planetsystems.tela.managementapp.client.widget.SwizimaLoader;
import com.planetsystems.tela.managementapp.shared.RequestAction;
import com.planetsystems.tela.managementapp.shared.RequestResult;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;

public class AcademicListGridData {

	private static AcademicListGridData instance = null;
    public static final String ACTION = "action";
	private AcademicListGridData() {
		super();
	}

	public static AcademicListGridData getInstance() {
		if (instance == null) {
			instance = new AcademicListGridData();
		}
		return instance;
	}
	

	public void loadListGrid
	(final DispatchAsync dispatcher , final PlaceManager placeManager , Map<String , Object> map , final AcademicYearListGrid academicYearListGrid , final Window window ,  final IButton button ) {
		SC.showPrompt("", "", new SwizimaLoader());

		dispatcher.execute(new RequestAction((String) map.get(ACTION) , map),
				new AsyncCallback<RequestResult>() {
					public void onFailure(Throwable caught) {
						System.out.println(caught.getMessage());
						SC.warn("ERROR", caught.getMessage());
						GWT.log("ERROR " + caught.getMessage());
						SC.clearPrompt();
					}

					public void onSuccess(RequestResult result) {

						SC.clearPrompt();

						SessionManager.getInstance().manageSession(result, placeManager);
						if (window != null) {
							window.close();
						}

						if (result != null) {

							if (button != null) {
								if (result.getAcademicYearDTOs().isEmpty()) {
									button.disable();
								}else {
									button.enable();
								}
							}
							
							//to be made dynamic by j8 lambdas
							academicYearListGrid
									.addRecordsToGrid(result.getAcademicYearDTOs());
						} else {
							// SC.warn("ERROR", "Unknown error");
							SC.warn("ERROR", "Service Down");
						}

					}
				});
	}
	
	
	public void loadListGrid
	(final DispatchAsync dispatcher , final PlaceManager placeManager , Map<String , Object> map , final AcademicTermListGrid academicTermListGrid , final Window window ,  final IButton button ) {
		SC.showPrompt("", "", new SwizimaLoader());

		dispatcher.execute(new RequestAction((String) map.get(ACTION) , map),
				new AsyncCallback<RequestResult>() {
					public void onFailure(Throwable caught) {
						System.out.println(caught.getMessage());
						SC.warn("ERROR", caught.getMessage());
						GWT.log("ERROR " + caught.getMessage());
						SC.clearPrompt();
					}

					public void onSuccess(RequestResult result) {

						SC.clearPrompt();

						SessionManager.getInstance().manageSession(result, placeManager);
						if (window != null) {
							window.close();
						}

						if (result != null) {

							if (button != null) {
								if (result.getAcademicYearDTOs().isEmpty()) {
									button.disable();
								}else {
									button.enable();
								}
							}
							
							//to be made dynamic by j8 lambdas
							academicTermListGrid
									.addRecordsToGrid(result.getAcademicTermDTOs());
						} else {
							// SC.warn("ERROR", "Unknown error");
							SC.warn("ERROR", "Service Down");
						}

					}
				});
	}
	
}
