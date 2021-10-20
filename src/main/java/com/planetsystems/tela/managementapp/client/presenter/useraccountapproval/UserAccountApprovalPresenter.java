package com.planetsystems.tela.managementapp.client.presenter.useraccountapproval;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rpc.shared.DispatchAsync;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.ContentSlot;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.proxy.RevealContentHandler;
import com.planetsystems.tela.managementapp.client.presenter.main.MainPresenter;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkDataUtil;
import com.planetsystems.tela.managementapp.client.presenter.networkutil.NetworkResult;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.planetsystems.tela.managementapp.client.widget.MenuButton;
import com.planetsystems.tela.managementapp.shared.RequestConstant;
import com.planetsystems.tela.managementapp.shared.RequestResult;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.planetsystems.tela.dto.UserAccountRequestDTO;
import com.planetsystems.tela.managementapp.client.gin.SessionManager;
import com.planetsystems.tela.managementapp.client.place.NameTokens;

public class UserAccountApprovalPresenter
		extends Presenter<UserAccountApprovalPresenter.MyView, UserAccountApprovalPresenter.MyProxy> {

	@Inject
	private DispatchAsync dispatcher;

	@Inject
	private PlaceManager placeManager;

	interface MyView extends View {

		public ControlsPane getControlsPane();

		public UserAccountApprovalPane getUserAccountApprovalPane();
	}

	@ContentSlot
	public static final Type<RevealContentHandler<?>> SLOT_UserAccountApproval = new Type<RevealContentHandler<?>>();

	@NameToken(NameTokens.useraccountapproval)
	@ProxyCodeSplit
	interface MyProxy extends ProxyPlace<UserAccountApprovalPresenter> {
	}

	@Inject
	UserAccountApprovalPresenter(EventBus eventBus, MyView view, MyProxy proxy) {
		super(eventBus, view, proxy, MainPresenter.SLOT_Main);

	}

	protected void onBind() {
		super.onBind();
		setControlsPaneMenuButtons();
		getAllRequests();
	}

	public void setControlsPaneMenuButtons() {
		MenuButton approve = new MenuButton("Approve");
		MenuButton reject = new MenuButton("Reject");
		MenuButton filter = new MenuButton("Filter");

		List<MenuButton> buttons = new ArrayList<>();
		buttons.add(approve);
		buttons.add(reject);
		buttons.add(filter);

		getView().getControlsPane().addMenuButtons("User Account Requests", buttons);
		// loadAdvancedFilter();
		// addLearnerAttendance(newButton);
		// selectFilterOption(filter);

		approveUserAccount(approve);

	}

	private void getAllRequests() {

		LinkedHashMap<String, Object> map = new LinkedHashMap<>();

		map.put(NetworkDataUtil.ACTION, RequestConstant.GET_USER_ACCOUNT_REQUEST);

		NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

			@Override
			public void onNetworkResult(RequestResult result) {

				getView().getUserAccountApprovalPane().getListgrid()
						.addRecordsToGrid(result.getUserAccountRequestDTOs());
			}
		});
	}

	private void approveUserAccount(final MenuButton approve) {
		approve.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				if (getView().getUserAccountApprovalPane().getListgrid().anySelected()) {

					SC.ask("Confirm", "Are you sure you want to approve the selected request?", new BooleanCallback() {

						@Override
						public void execute(Boolean value) {
							if (value) {

								UserAccountRequestDTO dto = new UserAccountRequestDTO();
								dto.setId(getView().getUserAccountApprovalPane().getListgrid().getSelectedRecord()
										.getAttribute(UserAccountApprovalListgrid.ID));

								LinkedHashMap<String, Object> map = new LinkedHashMap<>();

								map.put(RequestConstant.APPROVE_USER_ACCOUNT_REQUEST, dto);
								map.put(NetworkDataUtil.ACTION, RequestConstant.APPROVE_USER_ACCOUNT_REQUEST);

								NetworkDataUtil.callNetwork(dispatcher, placeManager, map, new NetworkResult() {

									@Override
									public void onNetworkResult(RequestResult result) {

										if (result.getSystemFeedbackDTO() != null) {
											if (result.getSystemFeedbackDTO().isResponse()) {
												SC.say("Sucess",
														"User account Request approved successfuly and user account setup. The use has been notified through email and sms ");

												getAllRequests();

											} else {
												SC.warn("ERROR", result.getSystemFeedbackDTO().getMessage());
											}
										}

									}
								});
							}

						}
					});
				} else {
					SC.warn("ERROR", "Please select request to approve");
				}

			}
		});
	}

}