package com.planetsystems.tela.managementapp.client.presenter.systemuser.profile;

import java.util.LinkedHashMap;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.planetsystems.tela.dto.SystemUserAdministrationUnitDTO;
import com.planetsystems.tela.dto.SystemUserProfileDTO;
import com.planetsystems.tela.managementapp.client.gin.SessionManager;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.planetsystems.tela.managementapp.client.widget.SwizimaLoader;
import com.planetsystems.tela.managementapp.shared.RequestAction;
import com.planetsystems.tela.managementapp.shared.RequestConstant;
import com.planetsystems.tela.managementapp.shared.RequestResult;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;

public class AdministrativeUnitListGrid extends SuperListGrid {

	public static String ID = "id";
	public static String AdminstrationUnitId = "adminstrationUnitId";
	public static String AdminstrationUnit = "adminstrationUnit";
	public static String AdministrationLevel = "administrationLevel";
	public static String USER_ID = "userId";
	public static String ACTION = "action";

	private DispatchAsync dispatcher;

	public AdministrativeUnitListGrid() {
		super();

		ListGridField idField = new ListGridField(ID, "Id");
		idField.setHidden(true);

		ListGridField adminstrationUnitId = new ListGridField(AdminstrationUnitId, "adminstrationUnitId");
		adminstrationUnitId.setHidden(true);

		ListGridField adminstrationUnit = new ListGridField(AdminstrationUnit, "Location");
		ListGridField administrationLevel = new ListGridField(AdministrationLevel, "Level");

		ListGridField actions = new ListGridField(ACTION, "Action");
		actions.setWidth("10%");

		ListGridField userId = new ListGridField(USER_ID, "UserId");
		userId.setHidden(true);

		this.setFields(idField, userId, adminstrationUnitId, adminstrationUnit, administrationLevel, actions);

		this.setShowRecordComponents(true);
		this.setShowRecordComponentsByCell(true);

	}

	public ListGridRecord addRowData(SystemUserAdministrationUnitDTO dto) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute(ID, dto.getId());

		record.setAttribute(AdminstrationUnitId, dto.getAdminstrationUnitId());
		record.setAttribute(AdminstrationUnit, dto.getAdminstrationUnit());
		record.setAttribute(AdministrationLevel, dto.getAdministrationLevel());

		if (dto.getSystemUserProfile() != null) {
			record.setAttribute(USER_ID, dto.getSystemUserProfile().getId());
		}

		return record;
	}

	public void addRecordsToGrid(List<SystemUserAdministrationUnitDTO> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;
		for (SystemUserAdministrationUnitDTO item : list) {
			records[row] = addRowData(item);
			row++;
		}
		this.setData(records);

	}

	@Override
	protected Canvas createRecordComponent(final ListGridRecord record, Integer colNum) {

		String fieldName = this.getFieldName(colNum);

		if (fieldName.equals(ACTION)) {

			HLayout recordCanvas = new HLayout(3);
			recordCanvas.setHeight(22);
			recordCanvas.setAlign(Alignment.CENTER);

			ImgButton editImg = new ImgButton();
			editImg.setShowDown(false);
			editImg.setShowRollOver(false);
			editImg.setLayoutAlign(Alignment.CENTER);
			editImg.setSrc("silk/delete.png");
			editImg.setPrompt("Delete");
			editImg.setHeight(16);
			editImg.setWidth(16);
			editImg.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {

					SC.ask("Confirm", "Are you sure you want to delete the selected record?", new BooleanCallback() {

						public void execute(Boolean value) {

							if (value) {

								if (record.getAttribute(ID) != null) {

									String id = record.getAttribute(ID);
									String userId = record.getAttribute(USER_ID);

									delete(getDispatcher(), id, userId);

								}

							}

						}
					});

				}
			});

			recordCanvas.addMember(editImg);
			return recordCanvas;

		}
		return null;
	}

	private void delete(DispatchAsync dispatcher, String id, String userId) {

		SystemUserAdministrationUnitDTO dto = new SystemUserAdministrationUnitDTO();
		dto.setId(id);

		SystemUserProfileDTO systemUserProfile = new SystemUserProfileDTO();
		systemUserProfile.setId(userId);

		dto.setSystemUserProfile(systemUserProfile);

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put(RequestConstant.DELETE_USER_ADMIN_UNITS, dto);
		map.put(RequestConstant.LOGIN_TOKEN, SessionManager.getInstance().getLoginToken());

		SC.showPrompt("", "", new SwizimaLoader());

		dispatcher.execute(new RequestAction(RequestConstant.DELETE_USER_ADMIN_UNITS, map),
				new AsyncCallback<RequestResult>() {
					public void onFailure(Throwable caught) {
						System.out.println(caught.getMessage());
						SC.say("ERROR", caught.getMessage());
						SC.clearPrompt();
					}

					public void onSuccess(RequestResult result) {

						SC.clearPrompt();

						if (result != null) {

							if (result.getSystemFeedbackDTO() != null) {
								if (result.getSystemFeedbackDTO().isResponse()) {

									SC.say("SUCCESS", result.getSystemFeedbackDTO().getMessage());

									addRecordsToGrid(result.getSystemUserAdministrationUnitDTOs());

								} else {
									SC.say("ERROR", result.getSystemFeedbackDTO().getMessage());
								}
							}
						} else {
							SC.say("ERROR", "Unknow error");
						}

					}
				});
	}

	public DispatchAsync getDispatcher() {
		return dispatcher;
	}

	public void setDispatcher(DispatchAsync dispatcher) {
		this.dispatcher = dispatcher;
	}

}
